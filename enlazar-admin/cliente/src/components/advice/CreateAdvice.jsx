import React, {  useState, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { createNewAdvice } from "../../actions/adviceAction";
import { useDispatch, useSelector } from "react-redux";
import alertaContext from "../../context/alerta/alertaContext";
import picture from "../../images/picture-grey.jpg";
import { categories } from "../../helpers";
import Spinner from "../Spinner";
import { Link } from "react-router-dom";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const CreateAdvice = ({ history }) => {
  const { alerta, mostrarAlerta } = useContext(alertaContext);
  const loading = useSelector((state) => state.advices.loading);

  //state del componente
  const [advice, setAdvice] = useState({
    img: "",
    tipe: "",
    title: "",
    content: "",
    imagen: null,
  });

  const [fileUrl, setFileUrl] = useState(null);

  const { img, tipe, title, content } = advice;

  const dispatch = useDispatch();
  const addAdvice = (advice) => dispatch(createNewAdvice(advice));

  const handleChange = (e) => {
    setAdvice({
      ...advice,
      [e.target.name]: e.target.value,
    });
  };
  
  const handleImg = (e) => {
    let f = new FormData();
    f.append("file", e.target.files[0]);

    setAdvice({
      ...advice,
      imagen: f,
      img: e.target.files[0].name,
    });

    const imageUrl = URL.createObjectURL(e.target.files[0]);
    setFileUrl(imageUrl);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //Validar
    if (tipe.trim() === "" || title.trim() === "" || content.trim() === "") {
      mostrarAlerta("Por favor complete todos lo campos", "alerta-error");
      return;
    }
    //Validar
    if (img.trim() === "") {
      mostrarAlerta("Por favor seleccione una imagen", "alerta-error");
      return;
    }

    addAdvice(advice);

    //redireccion
    setTimeout(function () {
      history.push("/list-advice");
    }, 2500);

    //reiniciar el form
    setAdvice({
      img: "",
      tipe: "",
      title: "",
      content: "",
      imagen: null,
      uri: "",
    });
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
              <i className="fas fa-plus-circle pr-2"></i>Crear un nuevo consejo
            </h2>
          </div>
          <div className="card bg-gris py-4 card-advice">
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <Row>
                  <Col>
                    <div className="form-group">
                      <label className="control-label">Categoría</label>

                      <select
                        name="tipe"
                        onChange={handleChange}
                        value={tipe}
                        className="custom-select"
                      >
                        <option value="">Selecciona una categoría...</option>

                        {categories.map((r) => (
                          <option value={r.id} key={r.id}>
                            {r.name}
                          </option>
                        ))}
                      </select>
                    </div>

                    <div className="form-group">
                      <label className="control-label">Título</label>
                      <input
                        type="text"
                        className="input-text"
                        placeholder="Por ejemplo, 'Sobre el reciclaje'"
                        name="title"
                        onChange={handleChange}
                        value={title}
                      />
                    </div>

                    <div className="form-group">
                      <label className="control-label">Descripción</label>
                      <textarea
                        className="input-text"
                        placeholder="Ingresa una descripción..."
                        name="content"
                        onChange={handleChange}
                        value={content}
                      />
                    </div>
                  </Col>
                  <Col lg={5}>
                    <div className="form-group">
                      <label className="control-label pl-2 ">
                        Selecciona una imagen
                      </label>
                      <input
                        type="file"
                        className="input-text text-white"
                        id="img"
                        name="img"
                        onChange={handleImg}
                      />

                      <div className="bg-white fondo-imagen  m-2  align-items-center">
                        {fileUrl !== null ? (
                          <img
                            className="img-edit "
                            src={fileUrl}
                            alt={title}
                          ></img>
                        ) : (
                          <img
                            className="img-edit "
                            src={picture}
                            alt={title}
                          ></img>
                        )}
                      </div>
                    </div>
                  </Col>
                </Row>
                {loading ? <Spinner /> : null}

                <div className="d-grid gap-2 d-md-flex mr-3 justify-content-md-end">
                  <Link
                    to={"/list-advice"}
                    className="btn btn-outline-primary me-md-2 mr-3"
                  >
                    <i className="fas fa-times pr-2"></i>
                    Cancelar
                  </Link>
                  <button
                    className="btn btn-primary me-md-2"
                    type="submit"
                    variant="primary"
                  >
                    <i className="far fa-check"></i>
                    Crear consejo
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
};

export default CreateAdvice;
