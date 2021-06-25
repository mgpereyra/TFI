import {
    ADD_ADVICE,
    ADD_ADVICE_SUCCESS,
    ADD_ADVICE_ERROR
} from '../types'
import clientAxios from '../config/axios'
import Swal from 'sweetalert2'

//Crear Consejos
export function createNewAdvice(advice){
    return async(dispatch) => {
        dispatch({
            type: ADD_ADVICE
        });

        try {
            await clientAxios.post('/api/advice', advice)
            //actualizo el state
            dispatch( addAdviceSuccess(advice) )    

            //alerta
            Swal.fire(
                'Genial!',
                'El consejo se agregó correctamente',
                'success'
            )

        } catch (error) {
            dispatch( addAdviceError() )
            //alerta
            Swal.fire({
                icon:'error',
                title: 'Oppss..',
                text: 'Ha ocurrido un error, intenta nuevamente'
            })
        }
    }
}

const addAdviceSuccess = advice =>({
    type: ADD_ADVICE_SUCCESS,
    payload: advice
})

const addAdviceError = () => ({
    type: ADD_ADVICE_ERROR
})