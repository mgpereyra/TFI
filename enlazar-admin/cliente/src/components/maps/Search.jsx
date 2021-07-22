import React , {useState} from "react";
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
import { useDispatch , useSelector} from "react-redux";

const Search = ({ panTo }) => {
  const {
    ready, 
    //eslint-disable-next-line 
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
 
  const addLat = (lat, lng, ubication) => dispatch(saveData(lat, lng, ubication));
  const datos = useSelector((state) => state.maps);

  const [ubicationInput, setubicationInput] = useState(datos.ubication)

  // https://developers.google.com/maps/documentation/javascript/reference/places-autocomplete-service#AutocompletionRequest
  const handleInput = (e) => {
    // TO DO QUE SEA EDITABLE
    setubicationInput(e.target.value);
    setValue(e.target.value)
  };

  const handleSelect = async (address) => {
    setValue(address, false);
    clearSuggestions();
    setubicationInput(address);
    try {
      const results = await getGeocode({ address });
      const { lat, lng } = await getLatLng(results[0]);
      const ubication = results[0].formatted_address;
      addLat({lat, lng, ubication})
      panTo({ lat, lng, ubication });
    } catch (error) {
      console.log("😱 Error: ", error);
    }
  };

  return (
    <div className="search">
      <Combobox onSelect={handleSelect}>
        <ComboboxInput
          className="form-control form-control-lg mb-3 py-4"
          value={ubicationInput}
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
