import React, { useReducer} from 'react';
import authReducer from './authReducer';
import authContext from './authContext';
import authToken from '../../config/authToken';
import {REGISTRO_EXITOSO,
        REGISTRO_ERROR,
        OBTENER_USUARIO,
        LOGIN_EXITOSO,
        LOGIN_ERROR,
        CERRAR_SESION} from '../../types';
import clienteAxios from '../../config/axios';

const AuthState = props => {
 
    const inicialState = {
        token: localStorage.getItem('token'),
        autenticado: null,
        usuario: null,
        mensaje: null,
        cargando: true 
    }
    const [state, dispatch] = useReducer(authReducer, inicialState);

    //cuando el usuario se registra
    const registrarUsuario = async datos => {
        try {
            const respuesta = {
                method: 'POST', 
                url: await clienteAxios.post('/api/user', datos)};

            dispatch({
                type:REGISTRO_EXITOSO,
                payload: respuesta.url.data
            });

            //Coloco en el state al usuario que inicia sesion
          obtenerUsuarioAutenticado();
        } catch (error) {
           
            const alerta = {
                msg: error.response.data.msg,
                categoria: 'alerta-error'
            }
            dispatch({
                type: REGISTRO_ERROR,
                payload:alerta
            })
        }
    }     

    const obtenerUsuarioAutenticado = async () => {
        const token = localStorage.getItem('token');
        let config = {
            headers: {
              "Content-Type": "application/json",
              'Access-Control-Allow-Origin': '*',
              }
            }
            
        try {
            if(token){
               authToken(token);
            }
            const respuesta = {
                method: 'GET', 
                url:  await clienteAxios.get('/api/auth', config),
                headers: {autorizacion: localStorage.getItem('token'), accept: "Accept: application/json" }};

            dispatch({
                type:OBTENER_USUARIO,
                payload: respuesta.url.data.userSinPassword
            })
            
        } catch (error) {
            console.log(error.response);
            const alerta = {
                msg: error.response.data.msg,
                categoria: 'alerta-error'
            }
            dispatch({
                type: LOGIN_ERROR,
                payload:alerta
            })
        }
    }

    //cuando el usuario inicia sesion
    const iniciarSesion = async (datos) => {
        try {
            let config = {
                headers: {
                  "Content-Type": "application/json",
                  'Access-Control-Allow-Origin': '*',
                  }
                }

            const respuesta ={
                method: 'POST',
                url: await clienteAxios.post('/api/auth', datos)};

                
            dispatch({
                type: LOGIN_EXITOSO,
                payload: respuesta.url.data
            })
            
            //Coloco en el state al usuario que inicia sesion
            obtenerUsuarioAutenticado();
        } catch (error) {

            const alerta = {
                msg: error.response.data.msg,
                categoria: 'alerta-error'
            }
            dispatch({
                type: LOGIN_ERROR,
                payload:alerta
            })
        }
    }

    const cerrarSesion= () => {
        dispatch({
            type: CERRAR_SESION
        })
    }

    return(
        <authContext.Provider
            value = {{
                token: state.token,
                autenticado: state.autenticado,
                mensaje: state.mensaje,
                usuario: state.usuario,
                cargando: state.cargando,
                registrarUsuario,
                iniciarSesion,
                obtenerUsuarioAutenticado,
                cerrarSesion, 
            }}>
            {props.children}
        </authContext.Provider>
    )
}

export default AuthState;