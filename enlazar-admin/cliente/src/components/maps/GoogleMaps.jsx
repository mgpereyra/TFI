import React, { useState, Fragment } from "react";
import { GoogleMap, useLoadScript, Marker } from "@react-google-maps/api";
import Search from './Search'
import "@reach/combobox/styles.css";
import {  useSelector } from "react-redux";
import picture from "../../images/logo-svg.svg"

const libraries = ["places"];
const GoogleMaps = () => {
  const mapContainerStyle = {
    width: "100%",
    height: "400px",
  };

  const datos = useSelector((state) => state.maps);

  const center = {
    lat: datos.lat,
    lng: datos.lng,
  };

  const options = {
    disableDefaultUI: true,
    zoomControl: true,
  };

  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries,
  });
  
  const [markers, setMarkers] = useState({
    lat: datos.lat,
    lng: datos.lng,
    time: Date,
  });
 
   const mapRef = React.useRef();
  const onMapLoad = React.useCallback((map) => {
    mapRef.current = map;
    setMarkers({ lat: datos.lat, lng: datos.lng });
    console.log(datos)
    mapRef.current.panTo({ lat:datos.lat, lng:datos.lng  });
     //eslint-disable-next-line
  }, []);


  const panTo = React.useCallback(({ lat, lng }) => {
    mapRef.current.setZoom(16);
    setMarkers({ lat , lng });

    mapRef.current.panTo({ lat, lng });
  }, []);

  if (loadError) return "Error Loading";
  if (!isLoaded) return "Loading Maps";

  return (
    <Fragment>
      <Search panTo={panTo} />
      <GoogleMap
        id="map"
        mapContainerStyle={mapContainerStyle}
        zoom={14}
        center={center}
        options={options}
        //onClick={onClickMap}
        onLoad={onMapLoad}
        setMarkers={markers}
      >
        <Marker
          key={markers.time}
          position={{
            lat: markers.lat,
            lng: markers.lng,
          }}
          icon={{
            url: picture,
            scaledSize: new window.google.maps.Size(50, 50),
            origin: new window.google.maps.Point(0, 0),
            anchor: new window.google.maps.Point(15, 15),
          }}
        />
      </GoogleMap>
    </Fragment>
  );
};

export default GoogleMaps;
