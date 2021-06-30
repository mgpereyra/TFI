import React, { Fragment, useState, useContext, useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import { createNewMeeting } from "../../actions/meetingAction";
import { clearMaps } from "../../actions/mapsAction";
import { useDispatch, useSelector } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";
import GoogleMaps from "../maps/GoogleMaps"

const CreateMeeting = ({ history }) => {
  const { alerta, mostrarAlerta } = useContext(alertaContext);
  const datos = useSelector((state) => state.maps);
  
  //state del componente
  const [meeting, setMeeting] = useState({
    date: "",
    title: "",
    description: "",
    time: "",
    estado: "",
    asistentes: {},
    ubication:'',
    lat: 0,
    lng: 0
  });

  const { date, description, title, time , ubication} = meeting;

  useEffect(() => {
    setMeeting({
      ...meeting,
      ubication: datos.ubication,
      lat: datos.lat,
      lng:datos.lng
    });
    //eslint-disable-next-line
  }, [datos])

  const dispatch = useDispatch();
  const addMeeting = (meeting) => dispatch(createNewMeeting(meeting));
  const clear = () => dispatch(clearMaps());

  const handleChange = (e) => {
    setMeeting({
      ...meeting,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (
      title.trim() === "" ||
      time.trim() === "" ||
      date.trim() === "" ||
      description.trim() === ""||
      ubication.trim() === ""
    ) {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }

    addMeeting(meeting);
    clear();

    //reiniciar el form
    setMeeting({
      date: "",
      title: "",
      description: "",
      time: "",
      estado: "",
      asistentes: [],
      ubication:'',
      lat: 0,
      lng: 0
    });

    //redireccion
      history.push("/list-meeting");
  };
  return (
    <Fragment>
      {alerta ? (
        <div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>
      ) : null}
      <div className="d-flex justify-content-between">
        <h2>
          <i className="fas fa-plus-circle"></i>Crear punto de
          encuentro
        </h2>
      </div>
      <div className="card bg-gris py-4">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <Row>
              <Col>
                <div className="form-group">
                  <label className="control-label">Título del evento</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Por ejemplo, evento por el dia del reciclaje"
                    name="title"
                    onChange={handleChange}
                    value={title}
                  />
                </div>
              </Col>
             
            </Row>
            <Row>
              <Col className="my-4">
              <label className="control-label">Selecciona una de las sugerencias de ubicación</label>
              <GoogleMaps meeting={meeting} setMeeting={setMeeting}/>
              </Col>
            </Row>
            <Row>
            <Col>
                <div className="form-group">
                  <label className="control-label">Día</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Por ejemplo, 24/05/21..."
                    name="date"
                    onChange={handleChange}
                    value={date}
                  />
                </div>
              </Col>
              <Col>
                <div className="form-group">
                  <label className="control-label">Hora</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="De 14 a 18 hs"
                    name="time"
                    onChange={handleChange}
                    value={time}
                  />
                </div>
              </Col>
              </Row>
            <Row>
              <Col>
                <div className="form-group">
                  <label className="control-label">Descripción</label>
                  <textarea
                    className="input-text"
                    placeholder="Ingresa una descripción del evento..."
                    name="description"
                    onChange={handleChange}
                    value={description}
                  />
                </div>
              </Col>
            </Row>
            <div className="mr-3 d-grid gap-2 d-md-flex justify-content-md-end">
              <button
                className="btn btn-primary me-md-2"
                type="submit"
                variant="primary"
              >
                <i className="far fa-check"></i>
                Crear punto de encuentro
              </button>
            </div>
          </form>
        </div>
      </div>
    </Fragment>
  );
};

export default CreateMeeting;
