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

  const { id, name, surname, district, dni, phone, email, password } = recycler;

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
      <td className='text-center'>
        <button
          onClick={() => confirmDelete(id)}
          className="btn btn-outline-danger btn-circle"
        >
          <i className="far fa-trash-alt"></i>
        </button>
      </td>
      <td className='text-center'>
        <button
          onClick={() => confirmEdit(recycler)}
          className="btn btn-success btn-circle"
        >
          <i className="far fa-edit"></i>
        </button>
      </td>
    </tr>
  );
};

export default Recycler;
