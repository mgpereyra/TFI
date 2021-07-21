import React, { Fragment, useEffect } from "react";
import { Link } from "react-router-dom";
import Coupon from "./Coupon";
import { useSelector, useDispatch } from "react-redux";
import { listCoupons } from "../../actions/couponAction";
import Spinner from "../Spinner";

const ListCoupon = () => {
  const dispatch = useDispatch();
  const listCoupon = () => dispatch(listCoupons());
  const coupons = useSelector((state) => state.coupons.coupons);
  const error = useSelector((state) => state.coupons.error);
  const loading = useSelector((state) => state.coupons.loading);


  useEffect(() => {
    listCoupon();
    //eslint-disable-next-line
}, []);


  return (
    <Fragment>
      <div className="d-flex justify-content-between px-4 mb-5">
        <h1>
          <i className="fas fa-recycle pr-2"></i>Listado de cupones
        </h1>
        
        <Link to={"/create-coupon"} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle pr-2"></i>
         Nuevo cupón
        </Link>
      </div>
      { loading ? <Spinner /> : null }
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
