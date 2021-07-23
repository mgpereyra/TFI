import React, {  useState, useContext, useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import { createNewRecycler } from "../../actions/recyclerAction";
import { clearMaps } from "../../actions/mapsAction";
import { useDispatch, useSelector } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";
import GoogleMaps from "../maps/GoogleMaps"
import {Link} from 'react-router-dom'
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const CreateRecycler = ({history}) => {

    const { alerta, mostrarAlerta } = useContext(alertaContext);
    const datos = useSelector((state) => state.maps);
    
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
  
    useEffect(() => {
      setRecycler({
        ...recycler,
        ubication: datos.ubication,
        lat: datos.lat,
        lng:datos.lng
      });
      //eslint-disable-next-line
    }, [datos])
  
    const dispatch = useDispatch();
    const addRecycler = (recycler) => dispatch(createNewRecycler(recycler));
    const clear = () => dispatch(clearMaps());
  
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
        email.trim() === ""
        ) {
        mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
        return;
      }

      addRecycler(recycler);
      clear();
  
      //reiniciar el form
      setRecycler({
        name: "",
        surname: "",
        dni: "",
        ubication: "",
        phone:'',
        lat: 0,
        lng: 0,
        email:''
      });
  
      //redireccion
      setTimeout( function(){
        history.push('/list-recycler')
      },1000)
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
            <i className="fas fa-plus-circle pr-2"></i>Crear reciclador
          </h2>
        </div>
        <div className="card bg-light py-4">
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
                  Crear reciclador
                </button>
              </div>
            </form>
          </div>
        </div>
        </main>
        <Footer />
      </div>
    </div>
     );
}
 
export default CreateRecycler;