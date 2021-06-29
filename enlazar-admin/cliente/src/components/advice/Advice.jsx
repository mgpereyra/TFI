import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import { deleteAdviceAction, modifyAdvice } from "../../actions/adviceAction";

const Advice = ({ advice }) => {
  const { tipe, title, content, id, img, uri } = advice;

  const dispatch = useDispatch();
  const history = useHistory();

  //eliminar
  const confirm = (id) => {
    dispatch(deleteAdviceAction(id));
  };

  //modificar
  const confirmEdit = (advice) => {
    dispatch(modifyAdvice(advice));
    history.push(`/edit-advice/${advice.id}`);
  };

  return (
    <div className="col-lg-4">
      <div className="card  border-secondary">
        <img className="card-img-top" src={uri} alt={img}></img>
        <div className="card-body">
          <p className="card-text">
            <small>{tipe}</small>
          </p>
          <h3 className="card-title">{title}</h3>
          <p className="card-text cortar-texto">{content}</p>
          <div className="acciones">
            <button
              onClick={() => confirm(id)}
              className="btn btn-outline-primary w-100 btn-left"
            >
              <i className="far fa-trash-alt"></i>
              Eliminar
            </button>
            <button
              onClick={() => confirmEdit(advice)}
              className="btn btn-primary w-100 btn-right"
            >
              <i className="far fa-edit"></i>
              Editar
            </button>
          </div>
        </div>
        <div className="card-footer  border-secondary">
          <small className="text-muted">{advice.id}</small>
        </div>
      </div>
    </div>
  );
};

export default Advice;