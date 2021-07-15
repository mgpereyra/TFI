import React, { Fragment, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import Service from "./Service";
import { getListServicesPendings } from "../../actions/serviceAction";

const ManageService = () => {
    const services = useSelector((state) => state.services.servicesPendings);
    const error = useSelector((state) => state.services.error);
    const dispatch = useDispatch();
    const dispatchListServicesPendings = () => dispatch(getListServicesPendings());

    useEffect(() => {
        dispatchListServicesPendings();
        //eslint-disable-next-line
    }, []);

    return ( 
        <Fragment>
        <div className="d-flex justify-content-between px-4 mb-5">
          <h1>
           <i className="fas fa-street-view"></i>Listado de servicios pendientes
          </h1>
        </div>
  
        {services.length === 0 && !error ? (
          <div className="alert alert-info text-center p-3">
            <i className="fas fa-exclamation-circle"></i>No hay servicios
          </div>
        ) : (
          <div className="row">
            <table className="table table-hover">
              <thead>
                <tr>
                  <th scope="col">Fecha</th>
                  <th scope="col">Dirección</th>
                  <th scope="col">Hora</th>
                  <th scope="col">Estado</th>
                  
                </tr>
              </thead>
              <tbody>
                {services.map((service) => (
                  <Service key={service.id} service={service} />
                ))}
              </tbody>
            </table>
          </div>
        )}
      </Fragment>
     );
}
 
export default ManageService;