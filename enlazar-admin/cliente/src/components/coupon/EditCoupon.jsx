import React, { Fragment, useState, useEffect, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { modifyCouponAction } from "../../actions/couponAction";
import { useHistory } from "react-router-dom";
import alertaContext from "../../context/alerta/alertaContext";

const EditCoupon = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const { alerta, mostrarAlerta } = useContext(alertaContext);

  //state del componente
  const [coupon, setCoupon] = useState({
    image: "",
    imageCode: "",
    description: "",
    amount: "",
    pointsCost: "",
    title: "",
    imageData: null,
  });

  const {
 
    image,
    imageCode,
    title,
    description,
    amount,
    pointsCost,
    imageName,
  } = coupon;
/*
  const confirmEdit = (coupon) => {
    dispatch(modifyCoupon(coupon));
    history.push(`/edit-coupon/${coupon.id}`);
  };*/

  const couponToModify = useSelector((state) => state.coupons.couponToModify);

  //carga los datos del elemento a modificar la 1ra vez
  useEffect(() => {
    setCoupon(couponToModify);
  }, [couponToModify]);

  const handleChange = (e) => {
    setCoupon({
      ...coupon,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (
      title.trim() === "" ||
      description.trim() === ""
    ) {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }

    if (
      pointsCost < 0
    ) {
      mostrarAlerta("Los Puntos de Costo deben ser igual o mayor que 0", "alerta-error");
      return;
    }

    if (
      amount < 0
    ) {
      mostrarAlerta("La Cantidad Disponible debe ser igual o mayor que 0", "alerta-error");
      return;
    }


    dispatch(modifyCouponAction(coupon));
    history.push("/list-coupon");
  };
  return (
    <Fragment>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Editar cupón
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <Row>
              <Col lg={4}>
                <div className="form-group">
                  <label className="control-label">Imagen seleccionada</label>
                  <div className="bg-white fondo-imagen  m-1 row align-items-center">
                    <img className="img-edit " src={image} alt={title}></img>
                  </div>
                </div>
              </Col>
              <Col>
                <div className="form-group">
                  <label className="control-label">Título</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Ingresa un título..."
                    name="title"
                    onChange={handleChange}
                    value={title}
                  />
                </div>

                <div className="form-group">
                  <label className="control-label">Puntos de costo</label>
                  <input
                    type="number"
                    className="input-text"
                    name="pointsCost"
                    onChange={handleChange}
                    value={pointsCost}
                  />
                </div>

                <div className="form-group">
                  <label className="control-label">Cantidad disponible</label>
                  <input
                    type="number"
                    className="input-text"
                    name="amount"
                    onChange={handleChange}
                    value={amount}
                  />
                </div>

                <div className="form-group">
                  <label className="control-label">Descripción</label>
                  <textarea
                    className="input-text"
                    placeholder="Ingresa una descripción..."
                    name="description"
                    onChange={handleChange}
                    value={description}
                  />
                </div>
              </Col>
            </Row>
            <div className="d-grid gap-2 d-md-flex mr-3 justify-content-md-end">
              <button
                className="btn btn-primary me-md-2"
                type="submit"
                variant="primary"
              >
                <i className="far fa-check"></i>
                Crear consejo
              </button>
            </div>
          </form>
        </div>
      </div>
    </Fragment>
  );
};

export default EditCoupon;
