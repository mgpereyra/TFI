import {
    DOWNLOAD_SERVICES_SUCCESS,
    DOWNLOAD_SERVICES_ERROR,
    START_DOWNLOAD_SERVICES,
    START_DOWNLOAD_SERVICES_PENDINGS,
    DOWNLOAD_SERVICES_PENDINGS_SUCCESS,
    DOWNLOAD_SERVICES_PENDINGS_ERROR
  } from "../types";
  import Swal from "sweetalert2";
  import clienteAxios from "../config/axios";
  
  //Listar servicios
  export function getListServices() {
    return async (dispatch) => {
      dispatch({
        type: START_DOWNLOAD_SERVICES,
      });
  
      try {
        const response = await clienteAxios.get("/api/service");
        //actualizo el state
       dispatch(downloadServicesSuccess(response.data));

      } catch (error) {
        dispatch(downloadServicesError());
        console.log(error)
        //alerta
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }
  
  const downloadServicesSuccess = (services) => ({
    type: DOWNLOAD_SERVICES_SUCCESS,
    payload: services
  });
  
  const downloadServicesError = () => ({
    type: DOWNLOAD_SERVICES_ERROR,
  });



  //Listar servicios pendientes
  export function getListServicesPendings() {
    return async (dispatch) => {
      dispatch({
        type: START_DOWNLOAD_SERVICES_PENDINGS,
      });
  
      try {
        const response = await clienteAxios.get("/api/service/pendings");

        console.log(response)
        //actualizo el state
       dispatch(downloadServicesPendingsSuccess(response.data));

      } catch (error) {
        dispatch(downloadServicesPendingsError());
        console.log(error)
        //alerta
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }
  
  const downloadServicesPendingsSuccess = (services) => ({
    type: DOWNLOAD_SERVICES_PENDINGS_SUCCESS,
    payload: services
  });
  
  const downloadServicesPendingsError = () => ({
    type: DOWNLOAD_SERVICES_PENDINGS_ERROR,
  });