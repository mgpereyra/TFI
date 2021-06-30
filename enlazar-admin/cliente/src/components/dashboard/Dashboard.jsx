import React, { Fragment, useEffect, useContext } from "react";
import authContext from "../../context/auth/authContext";

const Dashboard = () => {
  const { obtenerUsuarioAutenticado } = useContext(authContext);

  useEffect(() => {
    obtenerUsuarioAutenticado();
    //eslint-disable-next-line
  }, []);

  return (
    <div className="jumbotron text-white bg-dark">
      <h1 className="text-white">¡Bienvenido/a a Enlazar para administradores!</h1>
      <hr />
      <p>Mucha suerte en tu labores.</p>
      <span>Ante cualquier duda no dudes en contactarte con nosotros, envianos un email a </span>
      <a className="color-third" href="mailto:enlazarunlam@gmail.com?Subject=Consulta" target="_blank">
        enlazarunlam@gmail.com
      </a>
      <span> , a la brevedad te estaremos respondiendo. &#128522;</span>

    </div>
  );
};

export default Dashboard;
