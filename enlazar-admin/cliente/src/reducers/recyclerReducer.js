import {
    ADD_RECYCLER,
    ADD_RECYCLER_SUCCESS,
    ADD_RECYCLER_ERROR,
    DOWNLOAD_RECYCLERS_SUCCESS,
    DOWNLOAD_RECYCLERS_ERROR,
    START_DOWNLOAD_RECYCLERS,
    RECYCLER_DELETE_ERROR,
    GET_RECYCLER_DELETE,
    RECYCLER_DELETE_SUCCESS,
    GET_RECYCLER_MODIFY,
    RECYCLER_MODIFY_ERROR,
    RECYCLER_MODIFY_SUCCESS,
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    recyclers: [],
    error: null,
    loading: false,
    recyclerToDelete: null,
    recyclerToModify:null
}

//eslint-disable-next-line
export default function (state = initialState, action){
    switch(action.type){
        case ADD_RECYCLER:
        case START_DOWNLOAD_RECYCLERS:    
            return {
                ...state,
                loading: true
            }
        case ADD_RECYCLER_SUCCESS:
            return {
                ...state,
                loading: false,
                recyclers: [...state.recyclers, action.payload]
        }
        case ADD_RECYCLER_ERROR:
        case DOWNLOAD_RECYCLERS_ERROR:    
        case RECYCLER_MODIFY_ERROR:
        case RECYCLER_DELETE_ERROR:    
            return {
                ...state,
                loading: false,
                error: true
        }
        case DOWNLOAD_RECYCLERS_SUCCESS:
            return {
                ...state,
                loading: false,
                error: false,
                recyclers: Object.values(action.payload)
        }
        case GET_RECYCLER_DELETE:
            return{
                ...state,
                recyclerToDelete: action.payload
            }  
        case RECYCLER_DELETE_SUCCESS:
            return{
                ...state,
                recyclers:  Object.values(state.recyclers).filter(recycler => recycler.id !== state.recyclerToDelete) ,
                recyclerToDelete: null
            }       
        case GET_RECYCLER_MODIFY:
            return{
                ...state,
                recyclerToModify: action.payload
            }
        case RECYCLER_MODIFY_SUCCESS:
            return{
                ...state,
                recyclerToModify: null
            }           
        default:
            return state;
    }
}