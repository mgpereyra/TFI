import React from 'react'
import GoogleMapReact from 'google-map-react';
import apiKey from "../../config/apiKey"

const GoogleMap = (props) => {
    return (   
    <div className="google-maps">
        <div className="m-5">
            <GoogleMapReact
                apiKey={apiKey}
                style={{height:"300px", width:"300px"}}
                yesIWantToUseGoogleMapApiInternals
                zoom={14}
                center={{
                    lat: 40.42,
                    lng: -3.6
                }}>
                <Marker lat={40.42} lng={-3.6} text="dhsajda" />
            
                </GoogleMapReact>
        </div>
        </div>
    );
}

const Marker = props => {
    return <div className="SuperAwesomePin"></div>
  }
 
export default GoogleMap;