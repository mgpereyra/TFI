import React from "react";
import { getState } from "../../helpers";
import { Form } from "react-bootstrap";

const ServicePendings = ({ service }) => {
  const { address, id, date, time, estado } = service;

  const get = getState(service);

  return (
    <tr className={get.className}>
      <th scope="row">{date}</th>
      <td>{address}</td>
      <td>{time}</td>
      <td>{get.stateString}</td>
      <td>
        
        <div className="custom-control custom-checkbox checkbox-lg">
          <input type="checkbox" className="custom-control-input" id={id} />
          <label className="custom-control-label pl-2" for={id}>Agregar</label>
        </div>
        
      </td>
    </tr>
  );
};

export default ServicePendings;
