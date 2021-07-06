import React from "react";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import {
  modifyRecycler,
  deleteRecyclerAction,
} from "../../actions/recyclerAction";


const Recycler = ({ recycler }) => {
    const history = useHistory();
    const dispatch = useDispatch();

  const {id, name, surname, district, dni, phone, email, password } = recycler;

  //eliminar
  const confirmDelete = (id) => {
    dispatch(deleteRecyclerAction(id));
  };

  const confirmEdit = (recycler) => {
    dispatch(modifyRecycler(recycler));
    history.push(`/edit-recycler/${id}`);
  };

  return (
    <tr>
      <th scope="row">{dni}</th>
      <td>
        {name} {surname}
      </td>
      <td>{district}</td>
      <td>{phone}</td>
      <td>{email}</td>
      <td>{password}</td>
      <td>
      <div className="acciones">
            <button
              onClick={() => confirmDelete(id)}
              className="btn btn-outline-primary w-100 btn-left"
            >
              <i className="far fa-trash-alt"></i>
            </button>
            <button
              onClick={() => confirmEdit(recycler)}
              className="btn btn-primary mr-2 w-100 btn-right"
            >
              <i className="far fa-edit"></i>
            </button>
          </div>
      </td>
    </tr>
  );
};

export default Recycler;
