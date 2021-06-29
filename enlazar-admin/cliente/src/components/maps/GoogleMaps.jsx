import React, { useState, Fragment } from "react";
import { GoogleMap, useLoadScript, Marker } from "@react-google-maps/api";
import Search from './Search'
import "@reach/combobox/styles.css";
import {  useSelector } from "react-redux";


const GoogleMaps = ({ meeting, setMeeting }) => {
  const libraries = ["places"];
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
    lat: 0,
    lng: 0,
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
    mapRef.current = map;
  }, []);

  const panTo = React.useCallback(({ lat, lng, name }) => {
    mapRef.current.setZoom(16);
    setMarkers({ lat, lng });
    // setMeeting({ ...meeting, ubicacion: name})
    mapRef.current.panTo({ lat, lng });
    // mapRef.current.setCenter({ lat, lng });
  }, []);

  if (loadError) return "Error Loading";
  if (!isLoaded) return "Loading Maps";

  return (
    <Fragment>
      <Search panTo={panTo} />
      <GoogleMap
        id="map"
        mapContainerStyle={mapContainerStyle}
        zoom={8}
        center={center}
        options={options}
        //onClick={onClickMap}
        onLoad={onMapLoad}
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
