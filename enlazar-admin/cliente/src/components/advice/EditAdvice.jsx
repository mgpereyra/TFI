import React, { Fragment, useState, useEffect, useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { modifyAdviceAction } from "../../actions/adviceAction";
import alertaContext from "../../context/alerta/alertaContext";
import { useHistory, useParams } from "react-router-dom";
import Spinner from "../Spinner";
import { categories } from "../../helpers";
import { Link, Redirect } from "react-router-dom";
import { getAdvice } from "../../actions/adviceAction";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const EditAdvice = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const { alerta, mostrarAlerta } = useContext(alertaContext);

  const [advice, setAdvice] = useState({
    img: "",
    tipe: "",
    title: "",
    description: "",
    uri: "",
    imageData: null,
  });

  const [fileUrl, setFileUrl] = useState(null);

  const adviceToModify = useSelector((state) => state.advices.adviceToModify);
  const loading = useSelector((state) => state.advices.loading);
  const error = useSelector((state) => state.advices.error);

  const { tipe, title, content, uri } = advice;

  const { id } = useParams();

  //carga los datos del elemento a modificar la 1ra vez
  useEffect(() => {
    if (adviceToModify !== null) {
      setAdvice(adviceToModify);
    } else {
      dispatch(getAdvice(id));
    }
  }, [adviceToModify]);

  if (error) {
    return <Redirect to="/list-advice" />;
  }
  const handleChange = (e) => {
    setAdvice({
      ...advice,
      [e.target.name]: e.target.value,
    });
  };

  const handleImg = (e) => {
    let formdata = new FormData();
    formdata.append("file", e.target.files[0]);

    setAdvice({
      ...advice,
      imageData: formdata,
      image: null,
    });

    const imageUrl = URL.createObjectURL(e.target.files[0]);
    setFileUrl(imageUrl);
  };

  const submitForm = (e) => {
    e.preventDefault();

    //Validar
    if (title.trim() === "" || content.trim() === "") {
      mostrarAlerta("Por favor complete todos los campos", "alerta-error");
      return;
    }

    dispatch(modifyAdviceAction(advice));

    setTimeout(() => {
      history.push("/list-advice");
    }, 2500);
  };
  return (
    <div className="contenedor-app">
      <Sidebar />
      <div className="seccion-principal">
        <Header />
        <main>
          <div className="d-flex justify-content-between">
            <h2>
              <i className="far fa-edit pr-2"></i>Editar consejo
            </h2>
          </div>
          <div className="card bg-gris py-4">
            <div className="card-body">
              <form onSubmit={submitForm}>
                <Row>
                  <Col lg={7}>
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
                        value={title}
                        onChange={handleChange}
                      />
                    </div>

                    <div className="form-group">
                      <label className="control-label">Descripción</label>
                      <textarea
                        className="input-text"
                        placeholder="Ingresa una descripción..."
                        name="content"
                        rows="6"
                        value={content}
                        onChange={handleChange}
                      />
                    </div>
                  </Col>
                  <Col lg={5}>
                    <div className="form-group">
                      <label className="control-label ml-2">
                        Selecciona una imagen
                      </label>
                      <input
                        type="file"
                        className="input-text text-white"
                        id="imagen"
                        name="imagen"
                        accept="image/jpeg, image/png"
                        onChange={handleImg}
                      />

                      <div className="bg-white fondo-imagen  m-2 align-items-center">
                        {advice.image !== null ? (
                          <img
                            className="img-edit "
                            src={advice.uri}
                            alt={title}
                          ></img>
                        ) : (
                          <img
                            className="img-edit "
                            src={fileUrl}
                            alt={title}
                          ></img>
                        )}
                      </div>
                    </div>
                  </Col>
                </Row>
                {loading ? <Spinner /> : null}
                <div className="d-grid gap-2 d-md-flex justify-content-md-end mr-3">
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
                    <i className="far fa-check pr-2"></i>
                    Guardar cambios
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

export default EditAdvice;
