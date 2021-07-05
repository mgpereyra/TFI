import {
    ADD_ADVICE,
    ADD_ADVICE_SUCCESS,
    ADD_ADVICE_ERROR,
    DOWNLOAD_COUPONS_SUCCESS,
    DOWNLOAD_COUPONS_ERROR,
    START_DOWNLOAD_COUPONS,
    ADVICE_DELETE_ERROR,
    GET_ADVICE_DELETE,
    ADVICE_DELETE_SUCCESS,
    GET_ADVICE_MODIFY,
    ADVICE_MODIFY_ERROR,
    ADVICE_MODIFY_SUCCESS,
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    coupons: [],
    error: null,
    loading: false,
    couponToDelete: null,
    couponToModify:null
}

//eslint-disable-next-line
export default function (state = initialState, action){
    switch(action.type){
        case ADD_ADVICE:
        case START_DOWNLOAD_COUPONS:    
            return {
                ...state,
                loading: true
            }
        case ADD_ADVICE_SUCCESS:
            return {
                ...state,
                loading: false,
                coupons: [...state.coupons, action.payload]
        }
        case ADD_ADVICE_ERROR:
        case DOWNLOAD_COUPONS_ERROR:    
        case ADVICE_MODIFY_ERROR:
        case ADVICE_DELETE_ERROR:    
            return {
                ...state,
                loading: false,
                error: true
        }
        case DOWNLOAD_COUPONS_SUCCESS:
            return {
                ...state,
                loading: false,
                error: false,
                coupons: Object.values(action.payload)
        }
        case GET_ADVICE_DELETE:
            return{
                ...state,
                adviceToDelete: action.payload
            }  
        case ADVICE_DELETE_SUCCESS:
            return{
                ...state,
                coupons:  Object.values(state.coupons).filter(advice => advice.id !== state.adviceToDelete) ,
                adviceToDelete: null
            }       
        case GET_ADVICE_MODIFY:
            return{
                ...state,
                adviceToModify: action.payload
            }
        case ADVICE_MODIFY_SUCCESS:
            return{
                ...state,
                adviceToModify: null
            }           
        default:
            return state;
    }
}