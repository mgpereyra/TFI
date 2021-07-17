import React, { Fragment, useEffect } from "react";
import { Link } from "react-router-dom";
import Coupon from "./Coupon";
import { useSelector, useDispatch } from "react-redux";
import { listCoupons } from "../../actions/couponAction";

const ListCoupon = () => {
  const dispatch = useDispatch();
  const listCoupon = () => dispatch(listCoupons());
  const coupons = useSelector((state) => state.coupons.coupons);
  const error = useSelector((state) => state.coupons.error);

  useEffect(() => {
    listCoupon();
    //eslint-disable-next-line
}, []);


  return (
    <Fragment>
      <div className="d-flex justify-content-between px-4 mb-5">
        <h1>
          <i className="fas fa-recycle mr-2"></i>Listado de cupones
        </h1>
        <Link to={"/verify-coupon"} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle mr-2"></i>
          Verificar cupón
        </Link>
        <Link to={"/create-coupon"} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle mr-2"></i>
          Crear un nuevo cupón
        </Link>
      </div>

      {coupons.length === 0 && !error ? (
        <div className="alert alert-info text-center p-3">
          <i className="fas fa-exclamation-circle"></i>No hay cupones creados
        </div>
      ) : (
        <div className="row">
          {coupons.map((coupon) => (
            <Coupon
              key={coupon.id} 
              coupon={coupon} />
          ))}
        </div>
      )}
    </Fragment>
  );
};

export default ListCoupon;
