import React, { useState, useContext, useEffect, Fragment , useRef  } from "react";
import QrReader from "react-qr-reader";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { verifyCouponCamera, cleanCouponToScan } from "../../actions/couponAction";
import alertaContext from "../../context/alerta/alertaContext";
import CouponVerified from "./CouponVerified";
import Spinner from "../Spinner"

const VerifyCouponByImage = () => {
  const { alerta, mostrarAlerta } = useContext(alertaContext);
  const couponToVerify = useSelector((state) => state.coupons.couponToVerify);
  const loading = useSelector((state) => state.coupons.loading);

  const [scanResultFile, setScanResultFile] = useState("");
  const qrRef = useRef(null);
  
  const dispatch = useDispatch();
  const verify = (scanResultWebCam) => dispatch(verifyCouponCamera(scanResultWebCam));
  const cleanCoupon = () => dispatch(cleanCouponToScan());
  
  useEffect(() => {
    cleanCoupon()
  }, [])

  const handleErrorFile = (error) => {
    mostrarAlerta("Ha ocurrido un error", "alerta-error");

    console.log(error);
  };
  const handleScanFile = (result) => {
    if (result !== null) {
      setScanResultFile(result);
      verify(result);
    } else {
      mostrarAlerta("La imagen ingresada no es válida", "alerta-error");
      setScanResultFile("");
    }
  };
  const onScanFile = () => {
    qrRef.current.openImageDialog();
  };
  return (
    <Fragment>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Verificar cupón por archivo
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <Row>
            <Col lg={4}>
           
              <QrReader
                ref={qrRef}
                delay={300}
                style={{ width: "100%" }}
                onError={handleErrorFile}
                onScan={handleScanFile}
                legacyMode
              />
              <button className="btn btn-primary m-2" onClick={onScanFile}>
                Subir imagen con código Qr
              </button>
            </Col>
            <Col lg={8}>
                {loading ?
                    <Spinner/>
                :  (couponToVerify !== null ? 
                    <CouponVerified
                        key={couponToVerify.cupon.id_item} 
                        couponToVerify={couponToVerify} />
                : null)}

            </Col>
          </Row>
        </div>
      </div>
    </Fragment>
  );
};

export default VerifyCouponByImage;
