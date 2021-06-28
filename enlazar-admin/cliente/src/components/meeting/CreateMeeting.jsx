import React, { Fragment, useState, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { createNewMeeting } from "../../actions/meetingAction";
import { useDispatch } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";

const CreateMeeting = ({ history }) => {
  const { alerta, mostrarAlerta } = useContext(alertaContext);

  //state del componente
  const [meeting, setMeeting] = useState({
    calle: "",
    date: "",
    lugar: "",
    description: "",
    localidad: "",
    time: "",
    estado: "",
    asistentes: {},
  });

  const { calle, date, description, localidad, lugar, time } = meeting;

  const dispatch = useDispatch();
  const addMeeting = (meeting) => dispatch(createNewMeeting(meeting));

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
      localidad.trim() === "" ||
      lugar.trim() === "" ||
      time.trim() === "" ||
      calle.trim() === "" ||
      date.trim() === "" ||
      description.trim() === ""
    ) {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }

    addMeeting(meeting);

    //reiniciar el form
    setMeeting({
      calle: "",
      date: "",
      lugar: "",
      description: "",
      localidad: "",
      lugar: "",
      time: "",
      estado: "",
      asistentes: [],
    });

    //redireccion
    setTimeout(function () {
      history.push("/list-meeting");
    }, 2500);
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
      <div className="card bg-gris">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <Row>
              <Col>
                <div className="form-group">
                  <label className="control-label">Lugar de encuentro</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Por ejemplo, Plaza Hipolito Yrigoyen"
                    name="lugar"
                    onChange={handleChange}
                    value={lugar}
                  />
                </div>
              </Col>
             
            </Row>
            <Row>
            <Col>
                <div className="form-group">
                  <label className="control-label">Calle</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Calle..."
                    name="calle"
                    onChange={handleChange}
                    value={calle}
                  />
                </div>
              </Col>
              <Col>
                <div className="form-group">
                  <label className="control-label">Localidad</label>
                  <input
                    type="text"
                    className="input-text"
                    placeholder="Ingresa una descripcion..."
                    name="localidad"
                    onChange={handleChange}
                    value={localidad}
                  />
                </div>
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
