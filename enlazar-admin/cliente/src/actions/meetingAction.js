import {
  START_DOWNLOAD_MEETINGS,
  DOWNLOAD_MEETINGS_SUCCESS,
  DOWNLOAD_MEETINGS_ERROR,
} from "../types";
import Swal from "sweetalert2";
import clienteAxios from "../config/axios";

//Listar consejos
export function getListMeetings() {
  return async (dispatch) => {
    dispatch({
      type: START_DOWNLOAD_MEETINGS,
    });

    try {
      const response = await clienteAxios.get("/api/meeting");
      //actualizo el state
     dispatch(downloadMeetingsSuccess());
    } catch (error) {
      //dispatch(downloadMeetingsError());

      //alerta
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: "Ha ocurrido un error, intenta nuevamente",
      });
    }
  };
}

const downloadMeetingsSuccess = (meetings) => ({
  type: DOWNLOAD_MEETINGS_SUCCESS,
  payload: meetings,
});

const downloadMeetingsError = () => ({
  type: DOWNLOAD_MEETINGS_ERROR,
});
