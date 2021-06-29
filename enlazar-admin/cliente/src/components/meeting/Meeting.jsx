import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import {
  modifyMeeting,
  deleteMeetingAction,
} from "../../actions/meetingAction";

const Meeting = ({ meeting }) => {
  const history = useHistory();
  const dispatch = useDispatch();

  const { date, description, title, time, estado, id, ubication, asistentes } = meeting;

  //eliminar
  const confirmDelete = (id) => {
    dispatch(deleteMeetingAction(id));
  };

  const confirmEdit = (meeting) => {
    dispatch(modifyMeeting(meeting));
    history.push(`/edit-meeting/${id}`);
  };

  return (
    <div className="col-lg-4">
      <div className="card border-secondary">
        <div className="card-body">
          <p className="card-text  text-right mb-3">
            <small className="text-estado mr-2 contador">Asistentes {asistentes !== undefined ? Object.values(asistentes).length : "0"}</small>
            <small className="text-estado activo">
              {estado === 1 ? "Activo" : "Innactivo"}
            </small>
          </p>
          <div className="contenedor-titulo">
            <h2 className="card-title color-third "> {title}</h2>
          </div>
          <h3 className="card-title font-weight-bold">
            {date} - {time}
          </h3>
          <div className="cortar-texto">
            <h4 className="card-title ">
              {ubication}

              <hr className="mt-2" />
              <p className="text-muted small-text mt-3">{description}</p>
            </h4>
          </div>
          <div className="acciones">
            <button
              onClick={() => confirmDelete(id)}
              className="btn btn-outline-primary w-100 btn-left"
            >
              <i className="far fa-trash-alt"></i>
              Eliminar
            </button>
            <button
              onClick={() => confirmEdit(meeting)}
              className="btn btn-primary mr-2 w-100 btn-right"
            >
              <i className="far fa-edit"></i>
              Editar
            </button>
          </div>
        </div>
        <div className="card-footer">
          <small className="text-muted">{id}</small>
        </div>
      </div>
    </div>
  );
};

export default Meeting;
