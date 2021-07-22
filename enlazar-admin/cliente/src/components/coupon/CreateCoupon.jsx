import React, { useState, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { createNewCoupon } from "../../actions/couponAction";
import { useDispatch, useSelector } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";
import picture from "../../images/picture-grey.jpg";
import { Link } from "react-router-dom";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";
import Spinner from "../Spinner";

const CreateCoupon = ({ history }) => {
  const { alerta, mostrarAlerta } = useContext(alertaContext);
  const loading = useSelector((state) => state.coupons.loading);

  //state del componente
  const [coupon, setCoupon] = useState({
    image: "",
    description: "",
    amount: "",
    pointsCost: "",
    title: "",
    imageData: null,
  });

  const [fileUrl, setFileUrl] = useState(null);

  const { imageData, image, title, description, amount, pointsCost } = coupon;

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
    });

    const imageUrl = URL.createObjectURL(e.target.files[0]);
    setFileUrl(imageUrl);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (
      title.trim() === "" ||
      description.trim() === "" ||
      pointsCost.trim() === "" ||
      fileUrl.trim() === "" ||
      amount.trim() === ""
    ) {
      mostrarAlerta("Por favor complete todos los campos", "alerta-error");
      return;
    }

    addCoupon(coupon);

    //redireccion
    setTimeout(function () {
      history.push("/list-coupon");
      //reiniciar el form
      setCoupon({
        image: "",
        imageCode: "",
        description: "",
        amount: "",
        pointsCost: "",
        title: "",
      });
    }, 1500);
  };
  return (
    <div className="contenedor-app">
      <Sidebar />
      <div className="seccion-principal">
        <Header />
        <main>
          {alerta ? (
            <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
          ) : null}
          <div className="d-flex justify-content-between">
            <h2>
              <i className="fas fa-plus-circle pr-2"></i>Crear nuevo cupón
            </h2>
          </div>
          <div className="card bg-light py-4">
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <Row>
                  <Col lg={7}>
                    <Row>
                      <Col lg={12}>
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
                      </Col>
                    </Row>
                    <Row>
                      <Col lg={6}>
                        <div className="form-group">
                          <label className="control-label">
                            Puntos de costo
                          </label>
                          <input
                            type="number"
                            placeholder="0"
                            className="input-text"
                            name="pointsCost"
                            onChange={handleChange}
                            value={pointsCost}
                          />
                        </div>
                      </Col>
                      <Col>
                        <div className="form-group">
                          <label className="control-label">
                            Cantidad disponible
                          </label>
                          <input
                            type="number"
                            placeholder="0"
                            className="input-text"
                            name="amount"
                            onChange={handleChange}
                            value={amount}
                          />
                        </div>
                      </Col>
                    </Row>
                    <Row>
                      <Col>
                        <div className="form-group">
                          <label className="control-label">Descripción</label>
                          <textarea
                            className="input-text"
                            placeholder="Ingresa una descripción..."
                            name="description"
                            rows="3"
                            onChange={handleChange}
                            value={description}
                          />
                        </div>
                      </Col>
                    </Row>
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

                      <div className="bg-white fondo-imagen  my-3 mx-2  align-items-center">
                        {fileUrl !== null ? (
                          <img
                            className="img-edit "
                            src={fileUrl}
                            alt={title}
                          ></img>
                        ) : (
                          <img
                            className="img-edit "
                            src={picture}
                            alt={title}
                          ></img>
                        )}
                      </div>
                    </div>
                  </Col>
                </Row>
                {loading ? <Spinner /> : null}
                <div className="d-grid gap-2 d-md-flex mr-3 justify-content-md-end">
                  <Link
                    to={"/list-coupon"}
                    className="btn btn-outline-primary me-md-2 mr-3"
                  >
                    <i className="fas fa-times pr-2"></i>
                    Cancelar
                  </Link>
                  <button
                    className="btn btn-primary me-md-2"
                    type="submit"
                    variant="primary"
                  >
                    <i className="far fa-check pr-2"></i>
                    Crear cupón
                  </button>
                </div>
              </form>
            </div>
          </div>
        </main>
        <Footer />
      </div>
    </div>
  );
};

export default CreateCoupon;
