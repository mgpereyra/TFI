import {
    ADD_RECYCLER,
    ADD_RECYCLER_SUCCESS,
    ADD_RECYCLER_ERROR,
    DOWNLOAD_RECYCLERS_SUCCESS,
    DOWNLOAD_RECYCLERS_ERROR,
    START_DOWNLOAD_RECYCLERS,
    RECYCLER_DELETE_ERROR,
    GET_RECYCLER_DELETE,
    RECYCLER_DELETE_SUCCESS,
    GET_RECYCLER_MODIFY,
    RECYCLER_MODIFY_ERROR,
    RECYCLER_MODIFY_SUCCESS,
  } from "../types";
  import Swal from "sweetalert2";
  import clienteAxios from "../config/axios";
  
  //Listar consejos
  export function getListRecyclers() {
    return async (dispatch) => {
      dispatch({
        type: START_DOWNLOAD_RECYCLERS,
      });
  
      try {
        const response = await clienteAxios.get("/api/recycler");
        //actualizo el state
       dispatch(downloadRecyclersSuccess(response.data));
      } catch (error) {
        dispatch(downloadRecyclersError());
  
        //alerta
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }
  
  const downloadRecyclersSuccess = (recyclers) => ({
    type: DOWNLOAD_RECYCLERS_SUCCESS,
    payload: recyclers
  });
  
  const downloadRecyclersError = () => ({
    type: DOWNLOAD_RECYCLERS_ERROR,
  });
  
  //Crear Puntos
  export function createNewRecycler(recycler) {
    return async (dispatch) => {
      dispatch({
        type: ADD_RECYCLER,
      });
  
      try {
        await clienteAxios.post("/api/recycler", recycler);
  
        //actualizo el state
        dispatch(addRecyclerSuccess(recycler));
  
        //alerta
        Swal.fire("Genial!", "El reciclador se agregó correctamente", "success");
      
      } catch (error) {
        console.log(error);
        dispatch(addRecyclerError());
        //alerta
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }
  
  const addRecyclerSuccess = (recycler) => ({
    type: ADD_RECYCLER_SUCCESS,
    payload: recycler
  });
  
  const addRecyclerError = () => ({
    type: ADD_RECYCLER_ERROR
  });
  
  
  
  //Coloca el elemento a editar en el state
  export function modifyRecycler(recycler) {
    return async (dispatch) => {
      dispatch({
        type: GET_RECYCLER_MODIFY,
        payload: recycler,
      });
    };
  }
  
  //Modificar un consejo
  export function modifyRecyclerAction(recycler) {
    return async (dispatch) => {
      try {
        
        await clienteAxios.put(`/api/recycler/${recycler.id}`, recycler);
  
        dispatch({
          type: RECYCLER_MODIFY_SUCCESS,
        });
        //alerta
        Swal.fire("Genial", "El reciclador se modificó correctamente", "success");
      } catch (error) {
        console.log(error);
        dispatch({
          type: RECYCLER_MODIFY_ERROR,
        });
  
        //alerta
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }
  
  
  //Eliminar un consejo
  export function deleteRecyclerAction(id) {
    return async (dispatch) => {
      dispatch({
        type: GET_RECYCLER_DELETE,
        payload: id,
      });
      try {
        await clienteAxios.delete(`/api/recycler/${id}`);
        dispatch({
          type: RECYCLER_DELETE_SUCCESS,
        });
  
        //alerta
        Swal.fire("Correcto", "El reciclador se eliminó", "success");
      } catch (error) {
        dispatch({
          type: RECYCLER_DELETE_ERROR,
        });
        console.log(error);
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }