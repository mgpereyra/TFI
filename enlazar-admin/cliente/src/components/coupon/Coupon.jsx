import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { deleteCouponAction, modifyCoupon } from "../../actions/couponAction";
import picture from "../../images/picture-grey.jpg";

const Coupon = ({ coupon }) => {
  const loading = useSelector((state) => state.coupons.loading);

  const { image, title, description , amount, pointsCost, id } =
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
            {loading ? (
              <img
                className="card-img-coupon"
                src={picture}
                alt="Cargando..."
              ></img>
            ) : (
              <img className="card-img-coupon" src={image} alt={title}></img>
            )}
          </div>
          <div className="col-md-8">
            <div className="card-body">
              <p className="card-text  text-right mb-3">
                <small className="text-estado mr-2 contador p-2">
                  Cantidad disponible ~ <b>{amount}</b>
                </small>
                <small className="text-estado activo p-2">
                  Costo ~ <b>{pointsCost}</b> puntos
                </small>
              </p>
              <h3 className="card-title color-third"> {title}</h3>
              <hr className="mt-2" />
              <p className="text-muted small-text mt-3 mb-1 cortar-texto">
                {description}
              </p>

              <div className="d-flex">
                <div className="ml-auto mt-4">
                  <button
                    onClick={() => confirmDelete(id)}
                    className="btn btn-outline-danger btn-circle mr-2 tooltip"
                  >
                    <i className="far fa-trash-alt"></i>
                 <span className="tooltiptext">Eliminar</span>

                  </button>
                  <button
                    onClick={() => confirmEdit(coupon)}
                    className="btn btn-success  btn-circle tooltip"
                  >
                    <i className="far fa-edit"></i>
                  <span className="tooltiptext">Editar</span>

                  </button>
                </div>
              </div>
            </div>
            <div className="card-footer py-2">
              <small className="text-muted">{id}</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Coupon;
