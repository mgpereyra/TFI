import React from "react";
import { Fragment } from "react";
import { useDispatch, useSelector } from "react-redux";
import { confirmCanjeAction } from "../../actions/couponAction";
import { useHistory } from "react-router-dom";

const CouponVerified = ({ couponToVerify }) => {
  const dispatch = useDispatch();
  const history = useHistory();

  const { title, description, imageCode, estadoCupon, id } =
    couponToVerify.cupon;

  const { name, dni, email, locality, address } = couponToVerify.user;

  const confirmCanje = (couponToVerify) => {
    dispatch(confirmCanjeAction(couponToVerify));

    //reiniciar el form
    history.push("/list-coupon");
  };

  return (
    <Fragment>
      <div className="col-lg-12">
        <h6 className="alert alert-info">
          <i className="fas fa-info-circle mr-2"></i>

          {estadoCupon ? (
            <b> El cupón ingresado ya fue canjeado</b>
          ) : (
            <b> El cupón está pendiente de canje</b>
          )}
        </h6>
        <label>Resultados de la búsqueda: </label>
        <div className="card mb-4 border-secondary px-2 mt-2">
          <div className="card-body px-1">
            <div className="row">
              <div className="col-md-7 border-right px-1">
                <p className="card-text  text-right mb-3">
                  <small className="text-estado mr-2 contador">
                    Estado ~
                    <b>{estadoCupon ? " CANJEADO" : "PENDIENTE"}</b>
                  </small>
                </p>
                <small className="text-secondary">Producto</small>
                <h3 className="card-title color-third"> {title}</h3>
                <hr className="mt-2" />
                <small className="text-secondary">Descripción</small>
                <br />
                <p className="color-third">{description}</p>
                <div className="d-flex">
                  {imageCode !== "default" &&
                  imageCode !== undefined &&
                  imageCode !== "" ? (
                    <div className="border">
                      <a href={imageCode} download={"Código QR-" + title}>
                        <img className="qr-div" src={imageCode} alt="QR" />
                      </a>
                    </div>
                  ) : null}
                </div>
              </div>
              <div className="col-md-5 px-2">
                <p className="card-text  text-right mb-3">
                  <small className="text-estado mr-2 contador">
                    DNI ~ <b>{dni}</b>
                  </small>
                </p>
                <small className="text-secondary">Nombre de usuario</small>
                <h3 className="card-title color-third"> {name}</h3>
                <hr className="mt-2" />
                <small className="text-secondary">Email</small>
                <br />
                <p className="color-third">{email}</p>
                <small className="text-secondary">Ubicación</small>
                <br />
                <p className="text-muted">
                  {address}, {locality}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {estadoCupon ? null : (
        <div className="d-grid gap-2 d-md-flex mr-3 justify-content-md-end">
          <button
            className="btn btn-success me-md-2"
            type="submit"
            variant="primary"
            onClick={() => confirmCanje(couponToVerify)}
          >
            <i className="far fa-check mr-2"></i>
            Confirmar canje
          </button>
        </div>
      )}
    </Fragment>
  );
};

export default CouponVerified;
