import React, { Fragment, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import ServicePendings from "./ServicePendings";
import { getListServicesPendings } from "../../actions/serviceAction";
import { getListRecyclers } from "../../actions/recyclerAction";

const ManageService = () => {
  const recyclers = useSelector((state) => state.recyclers.recyclers);
  const services = useSelector((state) => state.services.servicesPendings);
  const error = useSelector((state) => state.recyclers.error);
  const loading = useSelector((state) => state.services.loading);

  const dispatch = useDispatch();
  const dispatchListRecyclers = () => dispatch(getListRecyclers());
  const dispatchListServicesPendings = () =>
    dispatch(getListServicesPendings());

  useEffect(() => {
    dispatchListRecyclers();
    dispatchListServicesPendings();
    //eslint-disable-next-line
  }, []);

  return (
    <Fragment>
      <div className="d-flex justify-content-between px-4 mb-5">
        <h1>
          <i className="fas fa-street-view"></i>Administrar servicios pendientes
        </h1>
      </div>
      {loading ? (
        <div className="d-flex justify-content-center">
          <div className="spinner-border spin text-secondary" role="status">
            <span className="sr-only">Loading...</span>
          </div>
        </div>
      ) : services.length === 0 && !error ? (
        <div className="alert alert-info text-center p-3">
          <i className="fas fa-exclamation-circle"></i>No hay servicios
        </div>
      ) : (
        <div>
          <div className="row">
            <table className="table table-hover">
              <thead>
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
                  <ServicePendings key={service.id} service={service} />
                ))}
              </tbody>
            </table>
          </div>

          <div className="row mt-5">
            <div className="input-group mb-3">
              <div className="input-group-prepend">
                <label className="input-group-text" for="inputGroupSelect01">
                  Recolector
                </label>
              </div>
              <select className="custom-select" id="inputGroupSelect01">
                <option selected>Selecciona un recolector...</option>
                {recyclers.map((r) => (
                  <option value={r.id}>
                    {r.name} {r.surname} ~ {r.dni} ~ {r.email}
                  </option>
                ))}
              </select>
            </div>
          </div>
        </div>
      )}
    </Fragment>
  );
};

export default ManageService;
