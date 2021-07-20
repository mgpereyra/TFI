import React, { Fragment, useState, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { verifyCoupon } from "../../actions/couponAction";
import { useDispatch, useSelector } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";
import CouponVerified from "./CouponVerified";
import Spinner from "../Spinner"

const VerifyCouponManual = () => {
  const { alerta, mostrarAlerta } = useContext(alertaContext);

  const couponToVerify = useSelector((state) => state.coupons.couponToVerify);
  const loading = useSelector((state) => state.coupons.loading);

  //state del componente
  const [ids, setIds] = useState({
    idUser: "",
    idCoupon: "",
    idItem:""
  });

  const { idUser, idCoupon, idItem } = ids;

  //Llamando al action
  const dispatch = useDispatch();
  const verify = (ids) => dispatch(verifyCoupon(ids));

  const handleChange = (e) => {
    setIds({
      ...ids,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (idUser.trim() === "" || idCoupon.trim() === "") {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }

    verify(ids);
  };
  return (
    <Fragment>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Verificar cupón
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <Row>
              <Col>
                <div className="form-group">
                  <label className="control-label">
                    Ingresa el código de usuario (1)
                  </label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Código de usuario..."
                    name="idUser"
                    onChange={handleChange}
                    value={idUser}
                  />
                </div>
              </Col>
              <Col>
                <div className="form-group">
                  <label className="control-label">
                    Ingresa el código de cupón (2)
                  </label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Código de cupón..."
                    name="idCoupon"
                    onChange={handleChange}
                    value={idCoupon}
                  />
                </div>
              </Col>
              <Col>
                <div className="form-group">
                  <label className="control-label">
                    Ingresa el código de item (3)
                  </label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Código de item..."
                    name="idItem"
                    onChange={handleChange}
                    value={idItem}
                  />
                </div>
              </Col>
              
            </Row>
            <div className="d-grid gap-2 d-md-flex mr-3 justify-content-md-end">
              <button
                className="btn btn-primary me-md-2 mb-3"
                type="submit"
                variant="primary"
              >
                <i className="far fa-check mr-2"></i>
                Verificar datos
              </button>
            </div>
          </form>
          {loading ?
                    <Spinner/>
                :  (couponToVerify !== null ? 
                    <CouponVerified
                        key={couponToVerify.cupon.id_item} 
                        couponToVerify={couponToVerify} />
                : null)}
        </div>
      </div>
    </Fragment>
  );
};

export default VerifyCouponManual;
