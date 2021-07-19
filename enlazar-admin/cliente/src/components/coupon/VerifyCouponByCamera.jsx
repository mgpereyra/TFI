import React, { useState } from "react";
import { Fragment, useRef } from "react";
import QrReader from "react-qr-reader";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { verifyCouponCamera } from "../../actions/couponAction";
import CouponVerified from "./CouponVerified";
import Spinner from "../Spinner"

const VerifyCouponByCamera = () => {
  const [scanResultWebCam, setScanResultWebCam] = useState("");
  const couponToVerify = useSelector((state) => state.coupons.couponToVerify);
  const loading = useSelector((state) => state.coupons.loading);
 
  const dispatch = useDispatch();
  const verify = (scanResultWebCam) => dispatch(verifyCouponCamera(scanResultWebCam));


  const handleErrorWebCam = (error) => {
    console.log(error);
  };
  const handleScanWebCam = (result) => {
      console.log(result)
    if (result) {
      setScanResultWebCam(result);
       //Llamando al action
       verify(result)
    }
  };
  return (
    <Fragment>
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Verificar cupón con cámara web
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <Row>
            <Col lg={4}>
                <h6><i className="fas fa-info-circle mr-2"></i>Posiciona el código dentro del siguiente recuadro</h6>
              <QrReader
                delay={200}
                style={{ width: "100%" }}
                onError={handleErrorWebCam}
                onScan={handleScanWebCam}
              />
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

export default VerifyCouponByCamera;
