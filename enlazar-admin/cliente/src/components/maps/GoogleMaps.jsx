import React, {useState, Fragment} from 'react'
import {
    GoogleMap,
    useLoadScript,
    Marker
  } from "@react-google-maps/api";
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
  import "@reach/combobox/styles.css";

  const GoogleMaps = ({meeting, setMeeting}) => {
     
     const libraries = ["places"]
    const mapContainerStyle = {
        width:"100%",
        height:"400px"
    }
    const center={
        lat:-34.751103 ,
        lng:-58.697586
    }

    const options ={
        disableDefaultUI: true,
        zoomControl: true
    }

    const {isLoaded, loadError} = useLoadScript({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        libraries
    });

    const [markers, setMarkers] = useState({
        lat: 0,
        lng: 0,
        time: Date
    });

    const onClickMap = (e) =>{
        console.log(e)
            setMarkers({
                lat: e.latLng.lat(),
                lng: e.latLng.lng(),
                time: new Date()
            })
    }

    const mapRef = React.useRef();
    const onMapLoad = React.useCallback((map)=>{
        mapRef.current = map
    }, [])

    const panTo = React.useCallback(({ lat, lng, name }) => {
        mapRef.current.setZoom(16);
        setMarkers({lat, lng})
       // setMeeting({ ...meeting, ubicacion: name})
        mapRef.current.panTo({ lat, lng });
       // mapRef.current.setCenter({ lat, lng });
      }, []);
      
    

    if(loadError) return "Error Loading";
    if(!isLoaded) return "Loading Maps"



    return (  
        <Fragment>
            <Search panTo={panTo} />
            <GoogleMap
                id="map"
                 mapContainerStyle={mapContainerStyle}
                 zoom={8}
                 center={center}
                 options={options}
                 onClick={onClickMap}
                 onLoad={onMapLoad}>
              
                <Marker
                    key={markers.time}
                    position={{
                        lat : markers.lat,
                        lng: markers.lng}}
                        icon={{
                            url: "./logo1536.png",
                            scaledSize: new window.google.maps.Size(40,40),
                            origin:new window.google.maps.Point(0,0),
                            anchor:new window.google.maps.Point(15,15)
                        }}
                 />
                 </GoogleMap> 
                 </Fragment>
    );
}
 

function Search( { panTo }) {
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
  
    // https://developers.google.com/maps/documentation/javascript/reference/places-autocomplete-service#AutocompletionRequest
    const handleInput = (e) => {
      setValue(e.target.value);
    };
  
    const handleSelect = async (address) => {
      setValue(address, false);
      clearSuggestions();
  
      try {
        const results = await getGeocode({ address });
        const { lat, lng , formatted_address} = await getLatLng(results[0]);
        const name = results[0].formatted_address
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
  }
export default GoogleMaps;