import {
  START_DOWNLOAD_MEETINGS,
  DOWNLOAD_MEETINGS_SUCCESS,
  DOWNLOAD_MEETINGS_ERROR,
  ADD_MEETING,
  ADD_MEETING_SUCCESS,
  ADD_MEETING_ERROR,
  GET_MEETING_MODIFY,
  MEETING_MODIFY_SUCCESS,
  MEETING_MODIFY_ERROR
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
      dispatch(downloadMeetingsError());

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
      dispatch(addMeetingSuccess(meeting));

      //alerta
      Swal.fire("Genial!", "El encuentro se agregó correctamente", "success");
    
    } catch (error) {
      console.log(error);
      dispatch(addMeetingError());
      //alerta
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: "Ha ocurrido un error, intenta nuevamente",
      });
    }
  };
}

const addMeetingSuccess = (meeting) => ({
  type: ADD_MEETING_SUCCESS,
  payload: meeting
});

const addMeetingError = () => ({
  type: ADD_MEETING_ERROR
});



//Coloca el elemento a editar en el state
export function modifyMeeting(meeting) {
  return async (dispatch) => {
    dispatch({
      type: GET_MEETING_MODIFY,
      payload: meeting,
    });
  };
}

//Modificar un consejo
export function modifyMeetingAction(meeting) {
  return async (dispatch) => {
    try {
      
      await clienteAxios.put(`/api/meeting/${meeting.id}`, meeting);

      dispatch({
        type: MEETING_MODIFY_SUCCESS,
      });
      //alerta
      Swal.fire("Genial", "El consejo se modificó correctamente", "success");
    } catch (error) {
      console.log(error);
      dispatch({
        type: MEETING_MODIFY_ERROR,
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
