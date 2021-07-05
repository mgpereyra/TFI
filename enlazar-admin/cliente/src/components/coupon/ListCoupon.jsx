
import React, {Fragment} from 'react'
import { Link } from "react-router-dom";
import Coupon from './Coupon'
import { useSelector, useDispatch } from "react-redux";

const ListCoupon = () => {

    let coupons =[]
  const error = useSelector((state) => state.advices.error);

    return ( 
        <Fragment>
        <div className="d-flex justify-content-between px-4 mb-5">
          <h1>
            <i className="fas fa-hands-helping"></i>Listado de cupones
          </h1>
          <Link to={"/create-coupon"} className="btn btn-primary mb-3">
            <i className="fas fa-plus-circle"></i>
            Crear un nuevo cupon
          </Link>
        </div>

        {coupons.length === 0 && !error ? (
        <div className="alert alert-info text-center p-3">
          <i className="fas fa-exclamation-circle"></i>No hay cupones creados
        </div>
      ) : (
        <div className="row">
          {coupons.map((advice) => (
            <Coupon 
                 />
          ))}
        </div>
      )}
        </Fragment>
     );
}
 
export default ListCoupon;