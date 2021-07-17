import {
    ADD_COUPON,
    ADD_COUPON_SUCCESS,
    ADD_COUPON_ERROR,
    DOWNLOAD_COUPONS_SUCCESS,
    DOWNLOAD_COUPONS_ERROR,
    START_DOWNLOAD_COUPONS,
    COUPON_DELETE_ERROR,
    GET_COUPON_DELETE,
    COUPON_DELETE_SUCCESS,
    GET_COUPON_MODIFY,
    COUPON_MODIFY_ERROR,
    COUPON_MODIFY_SUCCESS,
    COUPON_VERIFY_ERROR,
    COUPON_VERIFY_SUCCESS
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    coupons: [],
    error: null,
    loading: false,
    couponToDelete: null,
    couponToModify:null,
    couponToVerify: null
}

//eslint-disable-next-line
export default function (state = initialState, action){
    switch(action.type){
        case ADD_COUPON:
        case START_DOWNLOAD_COUPONS:    
            return {
                ...state,
                loading: true
            }
        case ADD_COUPON_SUCCESS:
            return {
                ...state,
                loading: false,
                coupons: [...state.coupons, action.payload]
        }
        case ADD_COUPON_ERROR:
        case DOWNLOAD_COUPONS_ERROR:    
        case COUPON_MODIFY_ERROR:
        case COUPON_DELETE_ERROR:
        case COUPON_VERIFY_ERROR:    
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
        case GET_COUPON_DELETE:
            return{
                ...state,
               couponToDelete: action.payload
            }  
        case COUPON_DELETE_SUCCESS:
            return{
                ...state,
                coupons:  Object.values(state.coupons).filter(coupon => coupon.id !== state.couponToDelete) ,
                couponToDelete: null
            }       
        case GET_COUPON_MODIFY:
            return{
                ...state,
                couponToModify: action.payload
            }
        case COUPON_MODIFY_SUCCESS:
            return{
                ...state,
                couponToModify: null
            } 
        case COUPON_VERIFY_SUCCESS:
            return{
                ...state,
                couponToVerify: action.payload
            }              
        default:
            return state;
    }
}