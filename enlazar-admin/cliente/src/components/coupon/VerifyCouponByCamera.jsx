import React, { useState, useEffect, Fragment, useContext } from "react";
import QrReader from "react-qr-reader";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import {
  verifyCouponCamera,
  cleanCouponToScan,
} from "../../actions/couponAction";
import alertaContext from "../../context/alerta/alertaContext";
import CouponVerified from "./CouponVerified";
import Spinner from "../Spinner";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const VerifyCouponByCamera = () => {
  //eslint-disable-next-line
  const [scanResultWebCam, setScanResultWebCam] = useState("");
  const couponToVerify = useSelector((state) => state.coupons.couponToVerify);
  const loading = useSelector((state) => state.coupons.loading);
  const { alerta, mostrarAlerta } = useContext(alertaContext);

  const dispatch = useDispatch();
  const verify = (scanResultWebCam) =>
    dispatch(verifyCouponCamera(scanResultWebCam));
  const cleanCoupon = () => dispatch(cleanCouponToScan());

  useEffect(() => {
    cleanCoupon();
    //eslint-disable-next-line  
  }, []);

  const handleErrorWebCam = (error) => {
    console.log(error);
    mostrarAlerta("Ha ocurrido un error", "alerta-error");
  };
  const handleScanWebCam = (result) => {
    console.log(result);
    if (result) {
      setScanResultWebCam(result);
      //Llamando al action
      verify(result);
    }
  };
  return (
    <Fragment>
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
                <i className="fas fa-plus-circle pr-2"></i>Verificar cupón con
                cámara web
              </h2>
            </div>
            <div className="card bg-gris py-4">
              <div className="card-body">
                <Row>
                  <Col lg={4}>
                    <h6>
                      <i className="fas fa-info-circle mr-2"></i>Posiciona el
                      código dentro del siguiente recuadro
                    </h6>
                    <QrReader
                      delay={200}
                      style={{ width: "100%" }}
                      onError={handleErrorWebCam}
                      onScan={handleScanWebCam}
                    />
                  </Col>
                  <Col lg={8}>
                    {loading ? (
                      <Spinner />
                    ) : couponToVerify !== null ? (
                      <CouponVerified
                        key={couponToVerify.cupon.id_item}
                        couponToVerify={couponToVerify}
                      />
                    ) : null}
                  </Col>
                </Row>
              </div>
            </div>
          </main>
          <Footer />
        </div>
      </div>
    </Fragment>
  );
};

export default VerifyCouponByCamera;
