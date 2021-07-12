import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import { deleteCouponAction, modifyCoupon } from "../../actions/couponAction";

const Coupon = ({ coupon }) => {
  const { image, title, description, imageCode, amount, pointsCost, id } =
    coupon;

    
  const dispatch = useDispatch();
  const history = useHistory();

     //eliminar
  const confirmDelete = (id) => {
    dispatch(deleteCouponAction(id));
  };

  //modificar
  const confirmEdit = (coupon) => {
    dispatch(modifyCoupon(coupon));
    history.push(`/edit-coupon/${coupon.id}`);
  };


  return (
    <div className="col-lg-10">
      <div className="card mb-4 border-secondary">
        <div className="row no-gutters">
          <div className="col-md-4">
            <img className="card-img-coupon" src={image} alt={title}></img>
          </div>
          <div className="col-md-8">
            <div className="card-body">
              <p className="card-text  text-right mb-3">
                <small className="text-estado mr-2 contador">
                  Cantidad disponible ~ <b>{amount}</b>
                </small>
                <small className="text-estado activo">
                  Costo ~ <b>{pointsCost}</b> puntos
                </small>
              </p>
              <small className="text-muted small-text">{id}</small>
              <div className="contenedor-titulo">
                <h2 className="card-title color-third"> {title}</h2>
              </div>
              <hr className="mt-2" />
              <p className="text-muted small-text mt-3 cortar-texto">{description}</p>

              <div className="acciones">
                <button
                  onClick={() => confirmDelete(id)}
                  className="btn btn-outline-primary btn-left"
                >
                  <i className="far fa-trash-alt"></i>
                  Eliminar
                </button>
                <button
                  onClick={() => confirmEdit(coupon)}
                  className="btn btn-primary btn-right"
                >
                  <i className="far fa-edit"></i>
                  Editar
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Coupon;
