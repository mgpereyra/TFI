import React from "react";
import "../spinner.css"

const Spinner = () => {
  return (
    <div className="d-flex justify-content-center mt-5">
      <div className="spinner">
        <div className="double-bounce1"></div>
        <div className="double-bounce2"></div>
      </div>
    </div>
  );
};

export default Spinner;
