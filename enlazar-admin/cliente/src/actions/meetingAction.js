import {
  START_DOWNLOAD_MEETINGS,
  DOWNLOAD_MEETINGS_SUCCESS,
  DOWNLOAD_MEETINGS_ERROR,
  ADD_MEETING,
  ADD_MEETING_SUCCESS,
  ADD_MEETING_ERROR
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
      console.log(response.data)
      //actualizo el state
     dispatch(downloadMeetingsSuccess(response.data));
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
  payload: meetings
});

const downloadMeetingsError = () => ({
  type: DOWNLOAD_MEETINGS_ERROR,
});

//Crear Puntos
export function createNewMeeting(meeting) {
  return async (dispatch) => {
    dispatch({
      type: ADD_MEETING,
    });

    try {
      await clienteAxios.post("/api/meeting", meeting);

      //actualizo el state
      //dispatch(addAdviceSuccess(meeting));

      //alerta
      Swal.fire("Genial!", "El consejo se agregó correctamente", "success");
    
    } catch (error) {
      console.log(error);
     // dispatch(addAdviceError());
      //alerta
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: "Ha ocurrido un error, intenta nuevamente",
      });
    }
  };
}

const addAdviceSuccess = (meeting) => ({
  type: ADD_MEETING_SUCCESS,
  payload: meeting,
});

const addAdviceError = () => ({
  type: ADD_MEETING_ERROR
});
