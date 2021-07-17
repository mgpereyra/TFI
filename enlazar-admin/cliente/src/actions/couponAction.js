import {
    ADD_COUPON,
    ADD_COUPON_SUCCESS,
    ADD_COUPON_ERROR,
    DOWNLOAD_COUPONS_SUCCESS,
    DOWNLOAD_COUPONS_ERROR,
    START_DOWNLOAD_COUPONS,
    COUPON_DELETE_ERROR,
    GET_COUPON_DELETE,
    COUPON_DELETE_SUCCESS,
    GET_COUPON_MODIFY,
    COUPON_MODIFY_ERROR,
    COUPON_MODIFY_SUCCESS
  } from "../types";
  import clienteAxios from "../config/axios";
  import Swal from "sweetalert2";
import firebase from "firebase";

//Listar consejos
export function listCoupons() {
    return async (dispatch) => {
      dispatch({
        type: START_DOWNLOAD_COUPONS,
      });
  
      try {
        const response = await clienteAxios.get("/api/coupon");
        //actualizo el state
        dispatch(downloadCouponsSuccess(response.data));
      } catch (error) {
        dispatch(downloadCouponsError());
  
        //alerta
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: "Ha ocurrido un error, intenta nuevamente",
        });
      }
    };
  }
  
  const downloadCouponsSuccess = (advices) => ({
    type: DOWNLOAD_COUPONS_SUCCESS,
    payload: advices,
  });
  
  const downloadCouponsError = () => ({
    type: DOWNLOAD_COUPONS_ERROR,
  });

  
//Crear Cupones
export function createNewCoupon(coupon) {
  return async (dispatch) => {
    dispatch({
      type: ADD_COUPON,
    });

    try {
      const guardarImagen = async (coupon) => {
        const imagen = coupon.imageData.get("file");
        try {
          var storageRef = firebase.storage();
          var imageRef = storageRef.ref().child("advice_image/" + imagen.name);
          await imageRef.put(imagen).then(async (snapshot) => {
            const uri = await storageRef
              .ref("item_image/" + imagen.name)
              .getDownloadURL();
            coupon.image = uri;
          });
        } catch (error) {
          console.log(error);
        }
      };


      async function save() {
        await guardarImagen(coupon);
        await clienteAxios.post("/api/coupon", coupon);
        dispatch(addCouponSuccess(coupon));
      }
      save();

      //alerta
      Swal.fire("Genial!", "El cupón se agregó correctamente", "success");

    } catch (error) {
      console.log(error);
      dispatch(addCouponError());
      //alerta
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: "Ha ocurrido un error, intenta nuevamente",
      });
    }
  };
}

const addCouponSuccess = (coupon) => ({
  type: ADD_COUPON_SUCCESS,
  payload: coupon,
});

const addCouponError = () => ({
  type: ADD_COUPON_ERROR,
});


//Eliminar un cupon
export function deleteCouponAction(id) {
  return async (dispatch) => {
    dispatch({
      type: GET_COUPON_DELETE,
      payload: id,
    });
    try {
      await clienteAxios.delete(`/api/coupon/${id}`);
      dispatch({
        type: COUPON_DELETE_SUCCESS,
      });

      //alerta
      Swal.fire("Correcto", "El cupón se eliminó", "success");
    } catch (error) {
      dispatch({
        type: COUPON_DELETE_ERROR,
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

//Coloca el cupon a editar en el state
export function modifyCoupon(couponToModify) {
  return async (dispatch) => {
    dispatch({
      type: GET_COUPON_MODIFY,
      payload: couponToModify,
    });
  };
}

//Modificar un consejo
export function modifyCouponAction(coupon) {
  return async (dispatch) => {
    try {
      
      await clienteAxios.put(`/api/coupon/${coupon.id}`, coupon);

      dispatch({
        type: COUPON_MODIFY_SUCCESS,
      });
      //alerta
      Swal.fire("Genial", "El cupón se modificó correctamente", "success");
    } catch (error) {
      console.log(error);
      dispatch({
        type: COUPON_MODIFY_ERROR,
      });
    }
  };
}

//Crear codigo qr
export function generateQrCode(id) {
  return async (dispatch) => {
    try {
      //const urlCode = await QRCode.toDataURL(`${id}hola`)
      await clienteAxios.put(`/api/coupon/qr/${id}`, {urlCode});

      dispatch({
        type: COUPON_MODIFY_SUCCESS,
      });
      //alerta
      Swal.fire("Genial", "El código QR se generó correctamente", "success");
    } catch (error) {
      console.log(error);
      dispatch({
        type: COUPON_MODIFY_ERROR,
      });
    }
  };
}