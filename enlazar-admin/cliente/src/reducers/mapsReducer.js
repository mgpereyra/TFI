import {
    ADD_LAT,
    ADD_LNG,
    ADD_UBICATION
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    lat:-34.670372, 
    lng: -58.564196,
    ubication:''
}

//eslint-disable-next-line
export default function (state = initialState, action){
    switch(action.type){
        case ADD_LAT:
            return {
                ...state,
                lat: action.payload
        }
        case ADD_LNG:
            return {
                ...state,
                lng: action.payload
        }
        case ADD_UBICATION:
            return {
                ...state,
                ubication: action.payload
        }
          
        default:
            return state;
    }
}
