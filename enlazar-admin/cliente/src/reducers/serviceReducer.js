import {
    DOWNLOAD_SERVICES_SUCCESS,
    DOWNLOAD_SERVICES_ERROR,
    START_DOWNLOAD_SERVICES
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    services: [],
    error: null,
    loading: false,
    serviceToDelete: null,
    serviceToModify:null
}

//eslint-disable-next-line
export default function (state = initialState, action){
    switch(action.type){
        case START_DOWNLOAD_SERVICES:    
            return {
                ...state,
                loading: true
            }
        case DOWNLOAD_SERVICES_ERROR:    
            return {
                ...state,
                loading: false,
                error: true
        }
        case DOWNLOAD_SERVICES_SUCCESS:
            return {
                ...state,
                loading: false,
                error: false,
                services: Object.values(action.payload)
        }
        default:
            return state;
    }
}