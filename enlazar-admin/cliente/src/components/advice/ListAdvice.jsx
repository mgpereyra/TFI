import React, { useEffect } from "react";
import Advice from "./Advice";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { listAdvices } from "../../actions/adviceAction";
import Spinner from "../Spinner";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const ListAdvice = () => {
  const dispatch = useDispatch();
  const listAdvice = () => dispatch(listAdvices());
  const advices = useSelector((state) => state.advices.advices);
  const error = useSelector((state) => state.advices.error);
  const loading = useSelector((state) => state.advices.loading);

  useEffect(() => {
    listAdvice();
    //eslint-disable-next-line
  }, []);

  return (
    <div className="contenedor-app">
      <Sidebar />
      <div className="seccion-principal">
        <Header />
        <main>
          <div className="d-flex justify-content-between px-4 mb-5">
            <h1>
              <i className="fas fa-hands-helping pr-2"></i>Listado de consejos
            </h1>
            <Link to={"/create-advice"} className="btn btn-primary mb-3">
              <i className="fas fa-plus-circle pr-2"></i>
              Nuevo consejo
            </Link>
          </div>
          {loading ? (
            <Spinner />
          ) : advices.length === 0 && !error ? (
            <div className="alert alert-info text-center p-3">
              <i className="fas fa-exclamation-circle"></i>No hay consejos
              creados
            </div>
          ) : (
            <div className="row">
              {advices.map((advice) => (
                <Advice key={advice.id} advice={advice} />
              ))}
            </div>
          )}
        </main>
        <Footer />
      </div>
    </div>
  );
};

export default ListAdvice;
