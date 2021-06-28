import {
    START_DOWNLOAD_MEETINGS,
    DOWNLOAD_MEETINGS_SUCCESS,
    DOWNLOAD_MEETINGS_ERROR,
    ADD_MEETING,
    ADD_MEETING_SUCCESS,
    ADD_MEETING_ERROR,
    GET_MEETING_MODIFY,
    MEETING_MODIFY_SUCCESS,
    MEETING_MODIFY_ERROR
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
        case ADD_MEETING:  
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
        case ADD_MEETING_SUCCESS:
            return {
                ...state,
                loading: false,
                meetings: [...state.meetings, action.payload]
        }
        case ADD_MEETING_ERROR:
        case DOWNLOAD_MEETINGS_ERROR: 
        case MEETING_MODIFY_ERROR:   
            return {
                ...state,
                loading: false,
                error: true
        } 
        case GET_MEETING_MODIFY:
            return{
                ...state,
                meetingToModify: action.payload
            }
        case MEETING_MODIFY_SUCCESS:
            return{
                ...state,
                meetingToModify: null
            }  
        default:
            return state;
    }
}