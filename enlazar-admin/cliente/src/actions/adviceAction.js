import {
    ADD_ADVICE,
    ADD_ADVICE_SUCCESS,
    ADD_ADVICE_ERROR
} from '../types'

export function createNewAdvice(){
    return() => {
        console.log('action')
    }
}