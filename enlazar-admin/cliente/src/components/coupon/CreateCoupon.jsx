import React, { Fragment, useState, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { createNewCoupon } from "../../actions/couponAction";
import { useDispatch } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";

const CreateCoupon = ({ history }) => {
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
    imageData,
    image,
    imageCode,
    title,
    description,
    amount,
    pointsCost,
    imageName
  } = coupon;

  //Llamando al action
  const dispatch = useDispatch();
  const addCoupon = (coupon) => dispatch(createNewCoupon(coupon));

  const handleChange = (e) => {
    setCoupon({
      ...coupon,
      [e.target.name]: e.target.value,
    });
  };

  const handleImg = (e) => {
    let formdata = new FormData();
    formdata.append("file", e.target.files[0]);

    setCoupon({
      ...coupon,
      imageData: formdata,
      image: e.target.files[0].name,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (
      title.trim() === "" ||
      description.trim() === "" ||
      pointsCost.trim() === "" ||
      image.trim() === "" ||
      amount.trim() === ""
    ) {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }

    addCoupon(coupon);

    //reiniciar el form
    setCoupon({
      image: "",
      imageCode: "",
      description: "",
      amount: "",
      pointsCost: "",
      title: "",
    });

    //redireccion
   // setTimeout(function () {
      history.push("/list-coupon");
    //}, 1000);
  };
  return (
    <Fragment>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Crear cupón
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <Row>
              <Col lg={4}>
                <div className="form-group">
                  <label className="control-label ">
                    Selecciona una imagen
                  </label>
                  <input
                    type="file"
                    className="input-text text-white"
                    id="imagen"
                    name="imagen"
                    onChange={handleImg}
                  />
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

export default CreateCoupon;
