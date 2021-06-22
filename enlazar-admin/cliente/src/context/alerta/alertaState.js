import React, {useReducer} from 'react';
import alertaContext from './alertaContext';
import alertaReducer from './alertaReducer';
import {MOSTRAR_ALERTA, OCULTAR_ALERTA} from '../../types';


const AlertaState = props => {

    const inicialState = {
        alerta: null
    }

    const [state, dispatch] = useReducer(alertaReducer, inicialState);

    const mostrarAlerta = (msg, categoria) =>{
        dispatch({
            type: MOSTRAR_ALERTA,
            payload: {
                msg,
                categoria
            }
        });

        setTimeout( () => {
            dispatch({
                type: OCULTAR_ALERTA
            })
        }, 3000);
    }

    return(

        <alertaContext.Provider
            value = {{
                alerta: state.alerta,
                mostrarAlerta
            }}
        >

            {props.children}
        </alertaContext.Provider>
    )


}

export default AlertaState;