import React, { Fragment, useState, useEffect, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { modifyMeetingAction } from "../../actions/meetingAction";
import { useHistory } from "react-router-dom";
import alertaContext from "../../context/alerta/alertaContext";
import GoogleMaps from "../maps/GoogleMaps"
import { mostrar, clearMaps } from "../../actions/mapsAction";

const EditMeeting = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const { alerta, mostrarAlerta } = useContext(alertaContext);

  //state del componente
  const [meeting, setMeeting] = useState({
    date: "",
    title: "",
    description: "",
    time: "",
    estado: "",
    asistentes: {},
    ubication: "",
    lat: 0,
    lng: 0,
  });

  const meetingToModify = useSelector(
    (state) => state.meetings.meetingToModify
  );
  const datos = useSelector((state) => state.maps);
  //if(!meetingToModify) return null;

  const { date, description, title, time, ubication } = meeting;
  const addUbication = (meet) => dispatch(mostrar(meet));
  const clear = () => dispatch(clearMaps());

  //carga los datos del elemento a modificar la 1ra vez
  useEffect(() => {
    setMeeting(meetingToModify);
    
    addUbication(meetingToModify)
  }, [meetingToModify]);

  

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
      ubication.trim() === "" ||
      date.trim() === "" ||
      description.trim() === ""
    ) {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }

    dispatch(modifyMeetingAction(meeting));
    clear();
    //reiniciar el form
    setMeeting({
      date: "",
      title: "",
      description: "",
      time: "",
      estado: "",
      asistentes: {},
      ubication: "",
      lat: 0,
      lng: 0,
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
          <i className="far fa-edit"></i>Editar nuevo punto de encuentro
        </h2>
      </div>
      <div className="card bg-gris">
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
                <label className="control-label">
                  Selecciona una de las sugerencias de ubicación
                </label>
                <GoogleMaps />
              </Col>
            </Row>
            <Row>
              <Col>
                <div className="form-group">
                  <label className="control-label">Día</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Ingresa una descripcion..."
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
                    placeholder="Ingresa una descripcion..."
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
                    placeholder="Ingresa una descripcion..."
                    name="description"
                    onChange={handleChange}
                    value={description}
                  />
                </div>
              </Col>
            </Row>
            <div className="mr-4 d-grid gap-2 d-md-flex justify-content-md-end">
              <button
                className="btn btn-primary me-md-2"
                type="submit"
                variant="primary"
              >
                <i className="far fa-check"></i>
                Guardar cambios
              </button>
            </div>
          </form>
        </div>
      </div>
    </Fragment>
  );
};

export default EditMeeting;
