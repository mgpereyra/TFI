import {
    ADD_ADVICE,
    ADD_ADVICE_SUCCESS,
    ADD_ADVICE_ERROR,
    DOWNLOAD_ADVICES_SUCCESS,
    DOWNLOAD_ADVICES_ERROR,
    START_DOWNLOAD_ADVICES,
    ADVICE_DELETE_ERROR,
    GET_ADVICE_DELETE,
    ADVICE_DELETE_SUCCESS
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    advices: [],
    error: null,
    loading: false,
    adviceToDelete: null
}

export default function (state = initialState, action){
    switch(action.type){
        case ADD_ADVICE:
        case START_DOWNLOAD_ADVICES:    
            return {
                ...state,
                loading: true
            }
            case ADD_ADVICE_SUCCESS:
                return {
                    ...state,
                    loading: false,
                    advices:[...state.advices, action.payload]
            }
            case ADD_ADVICE_ERROR:
            case DOWNLOAD_ADVICES_ERROR:    
                return {
                    ...state,
                    loading: false,
                    error: true
            }
            case DOWNLOAD_ADVICES_SUCCESS:
                return {
                    ...state,
                    loading: false,
                    error: false,
                    advices: action.payload
            }
            case GET_ADVICE_DELETE:
                return{
                    ...state,
                    adviceToDelete: action.payload
                }            

        default:
            return state;
    }
}