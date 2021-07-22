import React, {  useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import Recycler from "./Recycler";
import { getListRecyclers } from "../../actions/recyclerAction";
import { clearMaps } from "../../actions/mapsAction";
import Spinner from "../Spinner";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const ListRecycler = () => {
  const recyclers = useSelector((state) => state.recyclers.recyclers);
  const error = useSelector((state) => state.recyclers.error);
  const loading = useSelector((state) => state.recyclers.loading);

  const dispatch = useDispatch();
  const dispatchListRecyclers = () => dispatch(getListRecyclers());
  const clearUbication = () => dispatch(clearMaps());

  useEffect(() => {
    dispatchListRecyclers();
    clearUbication();
    //eslint-disable-next-line
  }, []);
  return (
    <div className="contenedor-app">
      <Sidebar />
      <div className="seccion-principal">
        <Header />
        <main>
          <div className="d-flex justify-content-between px-3 mb-5">
            <h1>
              <i className="fas fa-recycle pr-2"></i>Listado de recolectores
            </h1>
            <Link to={"/create-recycler"} className="btn btn-primary mb-3">
              <i className="fas fa-plus-circle pr-2"></i>
              Nuevo recolector
            </Link>
          </div>

          {loading ? (
            <Spinner />
          ) : recyclers.length === 0 && !error ? (
            <div className="alert alert-info text-center p-3">
              <i className="fas fa-exclamation-circle"></i>No hay recolectores
              creados
            </div>
          ) : (
            <div className="row">
            <div className="card mb-4 border-secondary">
            <div className="row">
              <table className="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">Dni</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Teléfono</th>
                    <th scope="col">Email</th>
                    <th scope="col">Password</th>
                    <th scope="col">Eliminar</th>
                    <th scope="col">Editar</th>
                  </tr>
                </thead>
                <tbody>
                  {recyclers.map((recycler) => (
                    <Recycler key={recycler.id} recycler={recycler} />
                  ))}
                </tbody>
              </table>
            </div>
            </div>
            </div>
          )}
        </main>
        <Footer />
      </div>
    </div>
  );
};

export default ListRecycler;
