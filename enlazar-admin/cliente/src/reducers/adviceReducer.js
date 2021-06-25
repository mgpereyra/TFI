import {
    ADD_ADVICE,
    ADD_ADVICE_SUCCESS,
    ADD_ADVICE_ERROR
} from '../types'

// cada reducer tiene su propio state

const initialState = {
    advices: [],
    error: null,
    loading: false
}

export default function (state = initialState, action){
    switch(action.type){
        case ADD_ADVICE:
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
                return {
                    ...state,
                    loading: false,
                    error: true
            }            

        default:
            return state;
    }
}