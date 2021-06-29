import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import { modifyMeeting, deleteMeetingAction } from "../../actions/meetingAction"

const Meeting = ({ meeting }) => {

  const history = useHistory();
  const dispatch = useDispatch();

  const {calle, date, description, localidad, lugar, time, estado, id}= meeting;
  
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
          <div className="card-header border-secondary">
            {lugar} 
          </div>
        <div className="card-body">
          <p className="card-text">
            <small className="text-muted">Estado {estado}</small>
          </p>
          <h3 className="card-title color-third"> {calle} - {localidad}</h3>
          <h3 className="card-title">{date} - {time}</h3>
          <p className="card-text cortar-texto">{description}</p>
          <div className="acciones">
            <button
               onClick = {() => confirmDelete(id)} 
               className="btn btn-outline-primary w-100 btn-left">
              <i className="far fa-trash-alt"></i>
              Eliminar
            </button>
            <button
              onClick={() => confirmEdit(meeting)}
              className="btn btn-primary mr-2 w-100 btn-right">
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
