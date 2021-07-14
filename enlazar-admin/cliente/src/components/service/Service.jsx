import React from 'react'

const Service = ({service}) => {

    const {address, id} = service;

    return (
        <tr>
        <th scope="row">{id}</th>
        <td>
          {address}
        </td>
        <td>
        <div className="acciones">
              <button
                className="btn btn-outline-primary w-100 btn-left"
              >
                <i className="far fa-trash-alt"></i>
              </button>
              <button
                className="btn btn-primary mr-2 w-100 btn-right"
              >
                <i className="far fa-edit"></i>
              </button>
            </div>
        </td>
      </tr>
      );
}
 
export default Service;