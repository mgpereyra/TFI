import React from "react";

const Meeting = ({ meeting }) => {
  const {calle, date, description, localidad, lugar, time, estado, asistentes}= meeting;


  return (
    <div className="col-lg-4">
      <div className="card ">
        <div className="card-body">
          <p className="card-text">
            <small>{calle} - {localidad}</small>
          </p>
          <h3 className="card-title">{lugar} - {estado}</h3>
          <h4 className="card-title">{date} - {time}</h4>
          <p className="card-text cortar-texto">{description}</p>
          <div className="acciones">
            <button className="btn btn-primary mr-2 w-100">
              <i className="far fa-edit"></i>
              Editar
            </button>

            <button className="btn btn-outline-primary w-100">
              <i className="far fa-trash-alt"></i>
              Eliminar
            </button>
          </div>
        </div>
        <div className="card-footer">
          <small className="text-muted">{meeting.id}</small>
        </div>
      </div>
    </div>
  );
};

export default Meeting;
