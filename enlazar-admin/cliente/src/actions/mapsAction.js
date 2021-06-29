import {
    ADD_LAT,
    ADD_LNG,
    ADD_UBICATION,
    CLEAR
} from '../types'


//Eliminar un consejo
export function saveData(lat) {
    return async (dispatch) => {
      dispatch({
        type: ADD_LAT,
        payload: lat.lat,
      });
      dispatch({
        type: ADD_LNG,
        payload: lat.lng,
      });
      dispatch({
        type: ADD_UBICATION,
        payload: lat.ubication,
      });
    };
  }

  export function mostrar(meet) {
    return async (dispatch) => {
      dispatch({
        type: ADD_LAT,
        payload: meet.lat,
      });
      dispatch({
        type: ADD_LNG,
        payload: meet.lng,
      });
      dispatch({
        type: ADD_UBICATION,
        payload: meet.ubication,
      });
    };
  }

  export function clearMaps(meet) {
    return async (dispatch) => {
      dispatch({
        type: CLEAR
      });
    
    };
  }