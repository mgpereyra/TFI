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
    COUPON_MODIFY_SUCCESS,
    COUPON_MODIFY_PROCESS,
    COUPON_VERIFY_ERROR,
    COUPON_VERIFY_SUCCESS,
    GET_COUPON_VERIFY,
    PROCESS_COUPON_VERIFY,
    CLEAN_COUPON_VERIFY
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
          var imageRef = storageRef.ref().child("item_image/" + imagen.name);
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
      
      dispatch({
        type: COUPON_MODIFY_PROCESS
      });

      console.log(coupon) 

        try {
          if(coupon.imageData !==   undefined){
            const imagen = coupon.imageData.get("file");
  
            var storageRef = firebase.storage();
            await storageRef.ref().child("item_image/" + imagen.name).put(imagen);
            coupon.image = await storageRef.ref("item_image/" + imagen.name).getDownloadURL();
          }
            console.log(coupon)
          await clienteAxios.put(`/api/coupon/${coupon.id}`, coupon);

            dispatch({
              type: COUPON_MODIFY_SUCCESS,
              payload: coupon
            });
       
            //alerta
           Swal.fire("Genial", "El cupón se modificó correctamente", "success");
        } catch (error) {
          console.log(error);

        }
     
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
     // await clienteAxios.put(`/api/coupon/qr/${id}`, {urlCode});

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


//verificar cupon con imagen
export function verifyCoupon(ids) {
  return async (dispatch) => {
    try {
      dispatch({
        type: PROCESS_COUPON_VERIFY
      });
      //const urlCode = await QRCode.toDataURL(`${id}hola`)
      const response = await clienteAxios.get(`/api/coupon/${ids.idUser}/${ids.idCoupon}/${ids.idItem}`);

      //const response = await clienteAxios.get(`/api/coupon/${ids.idUser}/${ids.idCoupon}`);
      console.log(response.data)

      dispatch({
        type: GET_COUPON_VERIFY,
        payload: response.data
      });

    
    } catch (error) {
    
      const msg = error.response.data.msg;
      dispatch({
        type: COUPON_VERIFY_ERROR
      });

      if(error.response.status === 400){
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: msg,
        });
      }
      if(error.response.status === 401){
        Swal.fire({
          icon: "info",
          title: "Oppss..",
          text: msg,
        });
    }}
  };
}


//verificar cupon camera
export function verifyCouponCamera(result) {
  return async (dispatch) => {
    try {
      dispatch({
        type: PROCESS_COUPON_VERIFY
      });
      
      const arrayResult = result.split("/")  
      const idUser = arrayResult[0]
      const idItem = arrayResult[2]
      const idCoupon = arrayResult[1]

      const response = await clienteAxios.get(`/api/coupon/${idUser}/${idCoupon}/${idItem}`);

      dispatch({
        type: GET_COUPON_VERIFY,
        payload: response.data
      });

    
    } catch (error) {
      const msg = error.response.data.msg;
      dispatch({
        type: COUPON_VERIFY_ERROR
      });

      if(error.response.status === 400){
        Swal.fire({
          icon: "error",
          title: "Oppss..",
          text: msg,
        });
      }
      }
  };
}


//confirmar verificacion cupon
export function confirmCanjeAction(couponToVerify) {
  return async (dispatch) => {
    try {
      const idUser = couponToVerify.user.id;

      const response = await clienteAxios.put(`/api/coupon/confirm/${idUser}`, couponToVerify);

      dispatch({
        type: COUPON_VERIFY_SUCCESS
      });
      
      //alerta
      Swal.fire("Genial", "Se completó el canje correctamente", "success");
    } catch (error) {
      dispatch({
        type: COUPON_VERIFY_ERROR
      });
      const msg = error.response.data.msg;
      Swal.fire({
        icon: "error",
        title: "Oppss..",
        text: msg,
      });
  };
}}


//Limpia el cupon a verificar
export function cleanCouponToScan(couponToModify) {
  return async (dispatch) => {
    dispatch({
      type: CLEAN_COUPON_VERIFY
    });
  };
}