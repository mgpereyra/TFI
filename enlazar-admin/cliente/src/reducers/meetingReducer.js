import {
    START_DOWNLOAD_MEETINGS,
    DOWNLOAD_MEETINGS_SUCCESS,
    DOWNLOAD_MEETINGS_ERROR,
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
        default:
            return state;
    }
}