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
    <div className="col-lg-10">
      <div class="card mb-4 border-secondary">
        <div class="row no-gutters">
          <div class="col-md-4">
            <img className="card-img" src={uri} alt={img}></img>
          </div>
          <div class="col-md-8">
            <div class="card-body">
                <small className="text-muted small-text">{advice.id}</small>
              <div className="mb-2 ">
                <h3 className="card-title mb-1 cortar-texto">{title}</h3>
                  <small class="text-muted">{tipe}</small>
              </div>
              <p className="card-text cortar-parrafo small-text">{content}</p>

              <div className="acciones">
                <button
                  onClick={() => confirm(id)}
                  className="btn btn-outline-primary btn-left"
                >
                  <i className="far fa-trash-alt"></i>
                  Eliminar
                </button>
                <button
                  onClick={() => confirmEdit(advice)}
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

export default Advice;
