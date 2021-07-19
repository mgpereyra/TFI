import React from "react";
const Spinner = () => {
  return (
    <div className="d-flex justify-content-center mt-5">
      <div className="spinner-border spin text-secondary" role="status">
        <span className="sr-only">Cargando...</span>
      </div>
    </div>
  );
};

export default Spinner;
