import React, { Fragment, useState, useEffect, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import {  modifyRecyclerAction } from "../../actions/recyclerAction";
import { useHistory } from "react-router-dom";
import alertaContext from "../../context/alerta/alertaContext";
import GoogleMaps from "../maps/GoogleMaps"
import { mostrar, clearMaps } from "../../actions/mapsAction";
import {Link} from 'react-router-dom'


const EditRecycler = () => {
    const dispatch = useDispatch();
    const history = useHistory();
  
    const { alerta, mostrarAlerta } = useContext(alertaContext);
  
    //state del componente
    const [recycler, setRecycler] = useState({
        name: "",
        surname: "",
        dni: "",
        ubication: "",
        phone:'',
        lat: 0,
        lng: 0,
        email:''
    });
  
    const { name, surname, phone, email , ubication, dni} = recycler;
    const addUbication = (recycler) => dispatch(mostrar(recycler));
    const clear = () => dispatch(clearMaps());

  const datos = useSelector((state) => state.maps);
   
    useEffect(() => {
      setRecycler({
        ...recycler,
        ubication: datos.ubication,
        lat: datos.lat,
        lng:datos.lng
  
      }
      );
      //eslint-disable-next-line
    }, [datos])
  
    const recyclerToModify = useSelector((state) => state.recyclers.recyclerToModify);
  
    //carga los datos del elemento a modificar la 1ra vez
    useEffect(() => {
      setRecycler(recyclerToModify);
      addUbication(recyclerToModify)
    }, [recyclerToModify]);
  
    const handleChange = (e) => {
      setRecycler({
        ...recycler,
        [e.target.name]: e.target.value,
      });
    };
  
    const handleSubmit = (e) => {
      e.preventDefault();
  
      //Validar
      if (
        name.trim() === "" ||
        surname.trim() === "" ||
        ubication.trim() === "" ||
        phone.trim() === ""||
        email.trim() === ""||
        dni.trim() === ""

      ) {
        mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
        return;
      }
  
      dispatch(modifyRecyclerAction(recycler));
      clear();

      setRecycler({
        name: "",
        surname: "",
        dni: "",
        ubication: "",
        phone:'',
        lat: 0,
        lng: 0,
        email:''
      })
      history.push("/list-recycler");
    };
    return ( 
        <Fragment>
        {alerta ? (
          <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
        ) : null}
        <div className="d-flex justify-content-between">
          <h2>
          <i className="far fa-edit pr-2"></i>Editar recolector
          </h2>
        </div>
        <div className="card bg-gris py-4">
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              <Row>
                <Col>
                  <div className="form-group">
                    <label className="control-label">Nombre</label>
                    <input
                      type="text"
                      className="input-text"
                      placeholder="Ingresa un nombre"
                      name="name"
                      onChange={handleChange}
                      value={name}
                    />
                  </div>
                </Col>
                <Col>
                  <div className="form-group">
                    <label className="control-label">Apellido</label>
                    <input
                      type="text"
                      className="input-text"
                      placeholder="Ingresa un apellido"
                      name="surname"
                      onChange={handleChange}
                      value={surname}
                    />
                  </div>
                </Col>
                <Col>
                  <div className="form-group">
                    <label className="control-label">Dni</label>
                    <input
                      type="text"
                      className="input-text"
                      placeholder="Ingresa un dni"
                      name="dni"
                      onChange={handleChange}
                      value={dni}
                    />
                  </div>
                </Col>
               
              </Row>
              
              <Row>
              <Col>
                  <div className="form-group">
                    <label className="control-label">Teléfono</label>
                    <input
                      type="text"
                      className="input-text"
                      placeholder="Ingresa un teléfono"
                      name="phone"
                      onChange={handleChange}
                      value={phone}
                    />
                  </div>
                </Col>
                <Col>
                  <div className="form-group">
                    <label className="control-label">Email</label>
                    <input
                      type="email"
                      className="input-text"
                      placeholder="Ingresa un email válido"
                      name="email"
                      onChange={handleChange}
                      value={email}
                    />
                  </div>
                </Col>
                </Row>
                <Row>
                <Col className="mb-4">
                <label className="control-label">Selecciona una de las sugerencias de ubicación</label>
                <GoogleMaps/>
                </Col>
              </Row>
             
              <div className="mr-3 d-grid gap-2 d-md-flex justify-content-md-end">
              <Link to={'/list-recycler'} className='btn btn-outline-primary me-md-2 mr-3'>
              <i className="fas fa-times pr-2"></i>
                  Cancelar
              </Link>
                <button
                  className="btn btn-primary me-md-2"
                  type="submit"
                  variant="primary"
                >
                  <i className="far fa-check pr-2"></i>
                  Guardar cambios
                </button>
              </div>
            </form>
          </div>
        </div>
      </Fragment>
     );
}
 
export default EditRecycler;