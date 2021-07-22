import React, { Fragment, useEffect, useState, useContext } from "react";
import { useSelector, useDispatch } from "react-redux";
import ServicePendings from "./ServicePendings";
import {
  getListServicesPendings,
  createAssignament,
} from "../../actions/serviceAction";
import { getListRecyclers } from "../../actions/recyclerAction";
import { Link } from "react-router-dom";
import alertaContext from "../../context/alerta/alertaContext";
import Spinner from "../Spinner";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const ManageService = ({ history }) => {
  const recyclers = useSelector((state) => state.recyclers.recyclers);
  const services = useSelector((state) => state.services.servicesPendings);
  const error = useSelector((state) => state.recyclers.error);
  const loading = useSelector((state) => state.services.loading);

  const { alerta, mostrarAlerta } = useContext(alertaContext);

  const dispatch = useDispatch();
  const dispatchListRecyclers = () => dispatch(getListRecyclers());
  const dispatchListServicesPendings = () =>
    dispatch(getListServicesPendings());

  useEffect(() => {
    dispatchListRecyclers();
    dispatchListServicesPendings();
    //eslint-disable-next-line
  }, []);

  const [recycler, setRecycler] = useState("");
  const [serviceState, setService] = useState([]);

  const addSelectionServices = (recycler, serviceState) =>
    dispatch(createAssignament(recycler, serviceState));

  const handleChange = (e) => {
    setRecycler(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (recycler.trim() === "" || serviceState.length === 0) {
      mostrarAlerta("Por favor realiza una selección", "alerta-error");
      return;
    }

    addSelectionServices(recycler, serviceState);

    //reiniciar el form
    setRecycler("");
    setService([]);

    //redireccion
    setTimeout(function () {
      history.push("/list-service");
    }, 1000);
  };

  return (
    <div className="contenedor-app">
    <Sidebar />
    <div className="seccion-principal">
      <Header />
      <main>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-street-view"></i>Administrar servicios pendientes
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
           
            {loading ? (
             <Spinner/>
            ) : services.length === 0 && !error ? (
              <div className="alert alert-info text-center p-3">
                <i className="fas fa-exclamation-circle mr-2"></i>No hay servicios pendientes
              </div>
            ) : (
              <Fragment>
                <div className="row">
                  <table className="table table-hover">
                    <thead className="thead-light">
                      <tr>
                        <th scope="col">Fecha</th>
                        <th scope="col">Dirección</th>
                        <th scope="col">Hora</th>
                        <th scope="col">Estado</th>
                        <th scope="col">Agregar</th>
                      </tr>
                    </thead>
                    <tbody>
                      {services.map((service) => (
                        <ServicePendings
                          key={service.id}
                          service={service}
                          serviceState={serviceState}
                          setService={setService}
                        />
                      ))}
                    </tbody>
                  </table>
                </div>

                <div className="row mt-5">
                  <div className="input-group mb-3">
                    <div className="input-group-prepend">
                      <label
                        className="input-group-text"
                        htmlFor="inputGroupSelect01"
                      >
                        Recolector
                      </label>
                    </div>
                    <select
                      name="recycler"
                      onChange={handleChange}
                      value={recycler}
                      className="custom-select"
                    >
                      <option value="">Selecciona un recolector...</option>

                      {recyclers.map((r) => (
                        <option value={r.id} key={r.id}>
                          {r.name} {r.surname} ~ {r.dni} ~ {r.email}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                <div className=" d-grid gap-2 d-md-flex justify-content-md-end">
                  <Link to={'/list-service'} className='btn btn-outline-primary me-md-2 mr-3'>
                  <i className="fas fa-times pr-2"></i>
                      Cancelar
                  </Link>
                  <button
                    className="btn btn-primary me-md-2"
                    type="submit"
                    variant="primary"
                  >
                    <i className="far fa-check pr-2"></i>
                    Guardar asignación
                  </button>
                </div>
              </Fragment>
            )}
          </form>
        </div>
      </div>
      </main>
        <Footer />
      </div>
    </div>
  );
};

export default ManageService;
