import React, { Fragment, useEffect, useContext } from "react";
import authContext from "../../context/auth/authContext";
import { Link } from "react-router-dom";

const Dashboard = () => {
  const { obtenerUsuarioAutenticado } = useContext(authContext);

  useEffect(() => {
    obtenerUsuarioAutenticado();
    //eslint-disable-next-line
  }, []);

  return (
    <Fragment>
      <div className="jumbotron text-white bg-dark py-4">
        <h1 className="text-white">
          <i className ="fas fa-comment mr-2"></i> ¡Bienvenido/a a Enlazar para
          administradores!
        <hr />
        </h1>
        <span>
        <b>Mucha suerte en tu labores.  </b>
          Ante cualquier duda no dudes en contactarte con nosotros, envianos un
          email a{" "}
        </span>
        <a
          className="text-success"
          href="mailto:enlazarunlam@gmail.com?Subject=Consulta"
          target="_blank"
        >
          enlazarunlam@gmail.com
        </a>
        <span> , a la brevedad te estaremos respondiendo. &#128522;</span>
      </div>

      <div className="card border-dark mb-0" >
        <div className="card-body text-dark">
        <h1>
          <i className="fas fa-qrcode mr-2"></i>Acceso a verificación de código
          Qr
          <hr />
        </h1>
          <div className="d-flex justify-content-between mb-3">
            <Link
              to={"/verify-coupon-camera"}
              className="btn btn-primary  px-5"
            >
              <i className="fas fa-video mr-2"></i>
             Cámara web
            </Link>
            <Link
              to={"/verify-coupon-manual"}
              className="btn btn-primary px-5"
            >
              <i className="fas fa-hand-point-right mr-2"></i>
             Manual
            </Link>
            <Link
              to={"/verify-coupon-image"}
              className="btn btn-primary px-5"
            >
              <i className="fas fa-image mr-2"></i>
              Con imagen
            </Link>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default Dashboard;
