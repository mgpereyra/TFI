import React, { Fragment, useState, useEffect, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { modifyCouponAction } from "../../actions/couponAction";
import { useHistory } from "react-router-dom";
import alertaContext from "../../context/alerta/alertaContext";
import Spinner from "../Spinner";
import {Link} from 'react-router-dom'

const EditCoupon = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const { alerta, mostrarAlerta } = useContext(alertaContext);
  //const [loading, setloading] = useState(false)
  const loading = useSelector((state) => state.coupons.loading);

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

  const [fileUrl, setFileUrl] = useState(null);

  const { title, description, amount, pointsCost } = coupon;
 
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

  const handleImg = (e) => {
    let formdata = new FormData();
    formdata.append("file", e.target.files[0]);

    setCoupon({
      ...coupon,
      imageData: formdata,
      image: null,
    });

    const imageUrl = URL.createObjectURL(e.target.files[0]);
    setFileUrl(imageUrl);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (title.trim() === "" || description.trim() === "") {
      mostrarAlerta("Por favor complete todos los campos", "alerta-error");
      return;
    }

    if (pointsCost < 0) {
      mostrarAlerta(
        "Los Puntos de Costo deben ser igual o mayor que 0",
        "alerta-error"
      );
      return;
    }

    if (amount < 0) {
      mostrarAlerta(
        "La Cantidad Disponible debe ser igual o mayor que 0",
        "alerta-error"
      );
      return;
    }

    dispatch(modifyCouponAction(coupon));

    setTimeout(() => {
      history.push("/list-coupon");
    }, 2500);
  };
  return (
    <Fragment>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}

      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle pr-2"></i>Editar cupón
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <Row>
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
              <Col lg={5}>
                <div className="form-group">
                  <label className="control-label ml-2">
                    Selecciona una imagen
                  </label>
                  <input
                    type="file"
                    className="input-text text-white"
                    id="imagen"
                    name="imagen"
                    accept="image/jpeg, image/png"
                    onChange={handleImg}
                  />

                  <label className="control-label pl-2">
                    Imagen seleccionada
                  </label>
                  <div className="bg-white fondo-imagen  m-2 align-items-center">
                    {coupon.image !== null ? (
                      <img
                        className="img-edit "
                        src={coupon.image}
                        alt={title}
                      ></img>
                    ) : (
                      <img
                        className="img-edit "
                        src={fileUrl}
                        alt={title}
                      ></img>
                    )}
                  </div>
                </div>
              </Col>
            </Row>
            { loading ? <Spinner /> : null }
            <div className="d-grid gap-2 d-md-flex mr-3 justify-content-md-end">
              <Link to={'/list-coupon'} className='btn btn-outline-primary me-md-2 mr-3'>
              <i className="fas fa-times pr-2"></i>
                  Cancelar
              </Link>
              <button
                className="btn btn-primary me-md-2"
                type="submit"
                variant="primary"
              >
                <i className="far fa-check pr-2"></i>
                Guardar cambios
              </button>
            </div>
          </form>
        </div>
      </div>
    </Fragment>
  );
};

export default EditCoupon;
