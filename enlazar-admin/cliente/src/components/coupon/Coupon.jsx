import React from "react";

const Coupon = ({ coupon }) => {
  const { image, title, description, imageCode, amount, pointsCost, id } =
    coupon;

  return (
    <div className="col-lg-10">
      <div className="card mb-4 border-secondary">
        <div className="row no-gutters">
          <div className="col-md-4">
            <img className="card-img" src={image} alt={imageCode}></img>
          </div>
          <div className="col-md-8">
            <div className="card-body">
              <p className="card-text  text-right mb-3">
                <small className="text-estado mr-2 contador">
                  Cantidad disponible ~ <b>{amount}</b>
                </small>
                <small className="text-estado activo">
                  Costo ~ <b>{pointsCost}</b> puntos
                </small>
              </p>
              <small className="text-muted small-text">{id}</small>
              <div className="contenedor-titulo">
                <h2 className="card-title color-third"> {title}</h2>
              </div>
              <hr className="mt-2" />
              <p className="text-muted small-text mt-3">{description}</p>

              <div className="acciones">
                <button
                  className="btn btn-outline-primary btn-left"
                >
                  <i className="far fa-trash-alt"></i>
                  Eliminar
                </button>
                <button
                  className="btn btn-primary btn-right"
                >
                  <i className="far fa-edit"></i>
                  Editar
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Coupon;
