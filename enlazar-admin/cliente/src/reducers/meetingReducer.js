import {
    START_DOWNLOAD_MEETINGS,
    DOWNLOAD_MEETINGS_SUCCESS,
    DOWNLOAD_MEETINGS_ERROR
  } from "../types";

// cada reducer tiene su propio state
const initialState = {
    meetings: [],
    error: null,
    loading: false,
    meetingToDelete: null,
    meetingToModify:null
}

export default function (state = initialState, action){
    switch(action.type){
        case START_DOWNLOAD_MEETINGS:    
            return {
                ...state,
                loading: true
            }
        case DOWNLOAD_MEETINGS_SUCCESS:
            return {
                ...state,
                loading: false,
                error: false,
                meetings: Object.values(action.payload)
        }  
        case DOWNLOAD_MEETINGS_ERROR:    
            return {
                ...state,
                loading: false,
                error: true
        }  
        default:
            return state;
    }
}