import React, { useState, Fragment } from "react";
import { GoogleMap, useLoadScript, Marker } from "@react-google-maps/api";
import Search from './Search'
import "@reach/combobox/styles.css";
import {  useSelector } from "react-redux";


const libraries = ["places"];
const GoogleMaps = ({ meeting, setMeeting }) => {
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
    lat: -34.751103,
    lng:  -58.697586,
    time: Date,
  });
 
  const onClickMap = (e) => {
    console.log(e);
    setMarkers({
      lat: e.latLng.lat(),
      lng: e.latLng.lng(),
      time: new Date(),
    });
  };

  const mapRef = React.useRef();
  const onMapLoad = React.useCallback((map) => {
    //setMarkers({ lat, lng });
    mapRef.current = map;
  }, []);


  const panTo = React.useCallback(({ lat, lng }) => {
    mapRef.current.setZoom(16);
    setMarkers({ lat, lng });
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
            url: "./logo-svg.svg",
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
