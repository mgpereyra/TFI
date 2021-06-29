import React, { Fragment, useEffect } from "react";
import Advice from "./Advice";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { listAdvices } from "../../actions/adviceAction";

const ListAdvice = () => {

  const dispatch = useDispatch();
  const listAdvice = () => dispatch(listAdvices());
  const advices = useSelector((state) => state.advices.advices);
  const error = useSelector((state) => state.advices.error);

  useEffect(() => {
      listAdvice();
      //eslint-disable-next-line
  }, []);

  return (
    <Fragment>
      <div className="d-flex justify-content-between px-4 mb-5">
        <h1>
          <i className="fas fa-hands-helping"></i>Listado de consejos
        </h1>
        <Link to={"/create-advice"} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"></i>
          Crear un nuevo consejo
        </Link>
      </div>

      {advices.length === 0 && !error ? (
        <div className="alert alert-info text-center p-3">
          <i className="fas fa-exclamation-circle"></i>No hay consejos creados
        </div>
      ) : (
        <div className="row">
          {advices.map((advice) => (
            <Advice 
                key={advice.id} 
                advice={advice} />
          ))}
        </div>
      )}
    </Fragment>
  );
};

export default ListAdvice;
