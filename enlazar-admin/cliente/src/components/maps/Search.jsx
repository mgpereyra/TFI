import React from "react";
import usePlacesAutocomplete, {
  getGeocode,
  getLatLng,
} from "use-places-autocomplete";
import {
  Combobox,
  ComboboxInput,
  ComboboxPopover,
  ComboboxList,
  ComboboxOption,
} from "@reach/combobox";
import { saveData } from "../../actions/mapsAction";
import { useDispatch } from "react-redux";

const Search = ({ panTo }) => {
  const {
    ready,
    value,
    suggestions: { status, data },
    setValue,
    clearSuggestions,
  } = usePlacesAutocomplete({
    requestOptions: {
      location: { lat: () => -34.751103, lng: () => -58.697586 },
      radius: 100 * 1000,
    },
  });

  const dispatch = useDispatch();
  const addLat = (lat, lng, name) => dispatch(saveData(lat, lng, name));
  // https://developers.google.com/maps/documentation/javascript/reference/places-autocomplete-service#AutocompletionRequest
  const handleInput = (e) => {
    setValue(e.target.value);
  };

  const handleSelect = async (address) => {
    setValue(address, false);
    clearSuggestions();

    try {
      const results = await getGeocode({ address });
      const { lat, lng, formatted_address } = await getLatLng(results[0]);
      const name = results[0].formatted_address;
      addLat({lat, lng, name})
      panTo({ lat, lng, name });
    } catch (error) {
      console.log("😱 Error: ", error);
    }
  };

  return (
    <div className="search">
      <Combobox onSelect={handleSelect}>
        <ComboboxInput
          className="form-control form-control-lg mb-3 py-4"
          value={value}
          onChange={handleInput}
          disabled={!ready}
          placeholder="Ingresa una ubicación..."
        />
        <ComboboxPopover>
          <ComboboxList>
            {status === "OK" &&
              data.map(({ place_id, description }) => (
                <ComboboxOption key={place_id} value={description} />
              ))}
          </ComboboxList>
        </ComboboxPopover>
      </Combobox>
    </div>
  );
};

export default Search;
