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
import clientAxios from '../config/axios'
import Swal from 'sweetalert2'
import clienteAxios from '../config/axios';

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


//Listar consejos
export function listAdvices(){
    return async(dispatch) => {
        dispatch({
            type: START_DOWNLOAD_ADVICES
        });

        try {
            const response = await clientAxios.get('/api/advice')

            //actualizo el state
            dispatch( downloadAdvicesSuccess(response.data) )    

            //alerta

        } catch (error) {
            dispatch( downloadAdvicesError() )
            
            //alerta
            Swal.fire({
                icon:'error',
                title: 'Oppss..',
                text: 'Ha ocurrido un error, intenta nuevamente'
            })
        }
    }
}

const downloadAdvicesSuccess = advices =>({
    type: DOWNLOAD_ADVICES_SUCCESS,
    payload: advices
})

const downloadAdvicesError = () =>({
    type: DOWNLOAD_ADVICES_ERROR
})

//Eliminar un consejo
export function deleteAdviceAction(id){
    return async(dispatch) => {
        dispatch({
            type: GET_ADVICE_DELETE,
            payload: id
        });
        try {
            const response = await clienteAxios.delete(`/api/advice/delete/${id}`)
        } catch (error) {
            
        }
    }
}

