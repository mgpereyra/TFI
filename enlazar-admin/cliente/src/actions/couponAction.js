import {
    ADD_ADVICE,
    ADD_ADVICE_SUCCESS,
    ADD_ADVICE_ERROR,
    DOWNLOAD_COUPONS_SUCCESS,
    DOWNLOAD_COUPONS_ERROR,
    START_DOWNLOAD_COUPONS,
    ADVICE_DELETE_ERROR,
    GET_ADVICE_DELETE,
    ADVICE_DELETE_SUCCESS,
    GET_ADVICE_MODIFY,
    ADVICE_MODIFY_ERROR,
    ADVICE_MODIFY_SUCCESS,
  } from "../types";
  import clienteAxios from "../config/axios";
  import Swal from "sweetalert2";

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