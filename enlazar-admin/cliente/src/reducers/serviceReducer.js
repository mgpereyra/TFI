import {
    DOWNLOAD_SERVICES_SUCCESS,
    DOWNLOAD_SERVICES_ERROR,
    START_DOWNLOAD_SERVICES,
    START_DOWNLOAD_SERVICES_PENDINGS,
    DOWNLOAD_SERVICES_PENDINGS_SUCCESS,
    DOWNLOAD_SERVICES_PENDINGS_ERROR,
    START_MODIFY_SERVICES_PENDINGS,
    SERVICES_PENDINGS_MODIFY_SUCCESS,
    SERVICES_PENDINGS_MODIFY_ERROR
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    services: [],
    error: null,
    loading: false,
    serviceToDelete: null,
    servicesPendingsToModify:[],
    servicesPendings:[]
}

//eslint-disable-next-line
export default function (state = initialState, action){
    switch(action.type){
        case START_DOWNLOAD_SERVICES:
        case START_DOWNLOAD_SERVICES_PENDINGS:  
         
            return {
                ...state,
                loading: true
            }
        case DOWNLOAD_SERVICES_PENDINGS_ERROR:    
        case DOWNLOAD_SERVICES_ERROR:    
        case SERVICES_PENDINGS_MODIFY_ERROR:
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
        case DOWNLOAD_SERVICES_PENDINGS_SUCCESS:
            return {
                ...state,
                loading: false,
                error: false,
                servicesPendings: Object.values(action.payload)
        }
        case START_MODIFY_SERVICES_PENDINGS:
            return{
                ...state,
                servicesPendingsToModify: action.payload
            }
        case SERVICES_PENDINGS_MODIFY_SUCCESS:
        return{
            ...state,
            servicesPendingsToModify: []
        }
        default:
            return state;
    }
}