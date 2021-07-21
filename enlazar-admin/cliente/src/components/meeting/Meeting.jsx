import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import {
  modifyMeeting,
  deleteMeetingAction,
  modifyStateMeeting
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
  const handleState = (meeting) => {
    dispatch(modifyStateMeeting(meeting));
    history.push(`/list-meeting`);

  };

  const formatDate = (date) =>{
    let dateToday = new Date(date)
    let day = dateToday.getDate() +1
    let month = dateToday.getMonth() + 1
    let year = dateToday.getFullYear()
    let today= '';
  
    if(month < 10){
      today = `${day}/0${month}/${year}`
    }else{
      today = `${day}/${month}/${year}`
    }
    return today;
  }
  


  return (
    <div className="col-lg-4">
      <div className="card border-secondary">
        <div className="card-body px-3">
          <div className="text-right mb-3">
            <small className="text-estado mr-2 contador p-2">Asistentes <b>{asistentes !== undefined ? Object.values(asistentes).length : "0"}</b></small>
            { asistentes === undefined ? 
              (estado === 1 ? 
                <button className=" text-estado activo btn py-0 px-2 tooltip"
                    onClick={() => handleState(meeting)}>
                    <small><b>Activo</b> </small> 
                    <span className="tooltiptext">Cambiar estado</span>

                </button>  
                : 
                  <button className=" text-estado inactivo btn py-0 px-2 tooltip"
                    onClick={() => handleState(meeting)}>
                    <small>Inactivo</small>
                    <span className="tooltiptext">Cambiar estado</span>
                  </button>)
            : null }            
                
          </div>
          <div className="contenedor-titulo mb-2">
            <h2 className="card-title color-third "> {title}</h2>
          </div>
          <h3 className="card-title font-weight-bold">
            {formatDate(date)} ~ {time}
          </h3>
          <div className="cortar-texto-meet">
            <h6>
              {ubication} </h6>

              <hr className="mt-2" />
              <p className="text-muted small-text mt-3">{description}</p>
           
          </div>
          <div className="acciones">
            <button
              onClick={() => confirmDelete(id)}
              className="btn btn-outline-danger btn-circle mr-2 tooltip"
            >
              <i className="far fa-trash-alt"></i>
              <span className="tooltiptext">Eliminar</span>

            </button>
            <button
              onClick={() => confirmEdit(meeting)}
              className="btn btn-success  btn-circle tooltip"
            >
              <i className="far fa-edit"></i>
              <span className="tooltiptext">Editar</span>

            </button>
          </div>
        </div>
        <div className="card-footer py-1">
          <small className="text-muted">{id}</small>
        </div>
      </div>
    </div>
  );
};

export default Meeting;
