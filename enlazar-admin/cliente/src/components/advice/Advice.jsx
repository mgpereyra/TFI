import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { deleteAdviceAction, modifyAdvice } from "../../actions/adviceAction";
import picture from "../../images/png-image.jpg";

const Advice = ({ advice }) => {
  const { tipe, title, content, id, img, uri, likes } = advice;

  const loading = useSelector((state) => state.services.loading);

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
      <div className="card mb-4 border-secondary">
        <div className="row no-gutters">
          <div className="col-md-4">
            {loading ? (
              <img className="card-img" src={picture} alt="cargando"></img>
            ) : (
              <img className="card-img" src={uri} alt={img}></img>
            )}
          </div>
          <div className="col-md-8">
            <div className="card-body">
              <div className="d-flex justify-content-between ">
                <small className="text-muted small-text">{tipe}</small>
                <small className="text-estado activo text-right">
                  <i className="fas fa-heart"></i> {likes}
                </small>
              </div>

              <div className="mb-2 ">
                <h3 className="card-title mb-1 cortar-texto">{title}</h3>
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
