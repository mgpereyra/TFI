import {
  ADD_ADVICE,
  ADD_ADVICE_SUCCESS,
  ADD_ADVICE_ERROR,
  DOWNLOAD_ADVICES_SUCCESS,
  DOWNLOAD_ADVICES_ERROR,
  START_DOWNLOAD_ADVICES,
  ADVICE_DELETE_ERROR,
  GET_ADVICE_DELETE,
  ADVICE_DELETE_SUCCESS,
  GET_ADVICE_MODIFY,
  ADVICE_MODIFY_ERROR,
  ADVICE_MODIFY_SUCCESS,
  ADVICE_MODIFY_PROCESS
} from "../types";
import clienteAxios from "../config/axios";
import Swal from "sweetalert2";
import firebase from "firebase";

//Crear Consejos
export function createNewAdvice(advice) {
  return async (dispatch) => {
    dispatch({
      type: ADD_ADVICE,
    });

    try {
      const guardarImagen = async (advice) => {
        const imagen = advice.imagen.get("file");
        try {
          var storageRef = firebase.storage();
          var imageRef = storageRef.ref().child("advice_image/" + imagen.name);
          await imageRef.put(imagen).then(async (snapshot) => {
            const uri = await storageRef
              .ref("advice_image/" + advice.img)
              .getDownloadURL();
            advice.uri = uri;
          });
        } catch (error) {
          console.log(error);
        }
      };

      async function save() {
        await guardarImagen(advice);
        await clienteAxios.post("/api/advice", advice);
        dispatch(addAdviceSuccess(advice));
      }
      save();

      //actualizo el state

      //alerta
      Swal.fire("Genial!", "El consejo se agregó correctamente", "success");
    } catch (error) {
      console.log(error);
      dispatch(addAdviceError());
      //alerta
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: "Ha ocurrido un error, intenta nuevamente",
      });
    }
  };
}

const guardarImageng = async (advice) => {
  const imagen = advice.imagen.get("file");
  try {
    var storageRef = firebase.storage();
    var imageRef = storageRef.ref().child("advice_image/" + imagen.name);
    await imageRef.put(imagen).then(async (snapshot) => {
      const uri = await storageRef
        .ref("advice_image/" + advice.img)
        .getDownloadURL();
      console.log(uri);
      return uri;
    });
  } catch (error) {
    console.log(error);
  }
};

const addAdviceSuccess = (advice) => ({
  type: ADD_ADVICE_SUCCESS,
  payload: advice,
});

const addAdviceError = () => ({
  type: ADD_ADVICE_ERROR,
});

//Listar consejos
export function listAdvices() {
  return async (dispatch) => {
    dispatch({
      type: START_DOWNLOAD_ADVICES,
    });

    try {
      const response = await clienteAxios.get("/api/advice");
      //actualizo el state
      dispatch(downloadAdvicesSuccess(response.data));
    } catch (error) {
      dispatch(downloadAdvicesError());

      //alerta
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: "Ha ocurrido un error, intenta nuevamente",
      });
    }
  };
}

const downloadAdvicesSuccess = (advices) => ({
  type: DOWNLOAD_ADVICES_SUCCESS,
  payload: advices,
});

const downloadAdvicesError = () => ({
  type: DOWNLOAD_ADVICES_ERROR,
});

//Eliminar un consejo
export function deleteAdviceAction(id) {
  return async (dispatch) => {
    dispatch({
      type: GET_ADVICE_DELETE,
      payload: id,
    });
    try {
      await clienteAxios.delete(`/api/advice/${id}`);
      dispatch({
        type: ADVICE_DELETE_SUCCESS,
      });

      //alerta
      Swal.fire("Correcto", "El consejo se eliminó", "success");
    } catch (error) {
      dispatch({
        type: ADVICE_DELETE_ERROR,
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

//Coloca el consejo a editar en el state
export function modifyAdvice(adviceToModify) {
  return async (dispatch) => {
    dispatch({
      type: GET_ADVICE_MODIFY,
      payload: adviceToModify,
    });
  };
}

//Modificar un consejo
export function modifyAdviceAction(advice) {
  return async (dispatch) => {
    try {

      dispatch({
        type: ADVICE_MODIFY_PROCESS
      });


      if(advice.imageData !==   undefined){
        const imagen = advice.imageData.get("file");
        var storageRef = firebase.storage();
        await storageRef.ref().child("item_image/" + imagen.name).put(imagen);
        advice.uri = await storageRef.ref("item_image/" + imagen.name).getDownloadURL();
      }
      await clienteAxios.put(`/api/advice/${advice.id}`, advice);

      dispatch({
        type: ADVICE_MODIFY_SUCCESS,
        payload:advice
      });
      //alerta
      Swal.fire("Genial", "El consejo se modificó correctamente", "success");
    } catch (error) {
      console.log(error);
      dispatch({
        type: ADVICE_MODIFY_ERROR,
      });
    }
  };
}
