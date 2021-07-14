import {
    DOWNLOAD_SERVICES_SUCCESS,
    DOWNLOAD_SERVICES_ERROR,
    START_DOWNLOAD_SERVICES,
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