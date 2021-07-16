import React from "react";
import { getState } from "../../helpers";
import { Form } from "react-bootstrap";

const ServicePendings = ({ service, setService, serviceState }) => {
  const { address, id, date, time, estado } = service;
  const properties = getState(service);

  const handleChangeService = (e) => {
    if (e.target.checked) {
      setService([...serviceState, e.target.value]);
    } else {
      setService(serviceState.filter((service) => service !== e.target.value));
    }
  };
  return (
    <tr className={properties.className}>
      <th scope="row">{date}</th>
      <td>{address}</td>
      <td>{time}</td>
      <td>{properties.stateString}</td>
      <td>
        <div className="custom-control custom-checkbox checkbox-lg">
          <input
            type="checkbox"
            className="custom-control-input"
            id={id}
            name="id"
            value={id}
            onChange={handleChangeService}
          />
          <label className="custom-control-label pl-2" htmlFor={id}>
            Agregar
          </label>
        </div>
      </td>
    </tr>
  );
};

export default ServicePendings;
