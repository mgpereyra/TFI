import React, { useState } from "react";
import { Fragment, useRef } from "react";
import QrReader from "react-qr-reader";
import { Col, Row } from "react-bootstrap";

const VerifyByImage = () => {
  const [scanResultFile, setScanResultFile] = useState("");
  const qrRef = useRef(null);

  const handleErrorFile = (error) => {
    console.log(error);
  };
  const handleScanFile = (result) => {
    console.log(result);
    if (result !== null) {
      setScanResultFile(result);
    } else {
      setScanResultFile("No es un archivo valido");
    }
  };
  const onScanFile = () => {
    qrRef.current.openImageDialog();
  };
    return (
        <Fragment>
              <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Verificar cupón por archivo
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <Row>
            <Col lg={6}>
              <button className="btn btn-primary" onClick={onScanFile}>
                Subir imagen Qr
              </button>
              <QrReader
                ref={qrRef}
                delay={300}
                style={{ width: "100%" }}
                onError={handleErrorFile}
                onScan={handleScanFile}
                legacyMode
              />
              <h3>Resultado: {scanResultFile}</h3>
            </Col>
            </Row>
            </div>
            </div>
        </Fragment>
      );
}
 
export default VerifyByImage;