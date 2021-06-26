import React from 'react'
import {Link} from 'react-router-dom'
import { useDispatch } from 'react-redux'
import {deleteAdviceAction} from '../../actions/adviceAction'

const Advice = ({advice}) => {
    const {tipe, title, content, id} = advice;

    const dispatch = useDispatch();

    const confirm = id => {

        console.log(id)
        dispatch(deleteAdviceAction(id))
    }
    return (
        <div className="col-lg-4">
            <div className="card">
                <img className="card-img-top" src="http://www.florespedia.com/Imagenes/flores-bonitas-girasoles.jpg" alt={advice.title}></img>
                <div className="card-body">
                    <p className="card-text"><small>{tipe}</small></p>

                    <h3 className="card-title">{title}</h3>
                    <p className="card-text">{content}</p>
                    <div className='acciones'>
                <Link
                    to={`/advice/edit/${id}`}
                    className='btn btn-primary mr-2 w-100'
                ><i className="far fa-edit"></i>
                  Editar  
                </Link>

                <button
                    onClick={()=> confirm(id)}
                    className="btn btn-outline-primary w-100"
                ><i className="far fa-trash-alt"></i>
                  Eliminar  
                </button>
                
            </div>    
                </div>
                <div className="card-footer"><small className="text-muted">{advice.id}</small></div>
            </div>
        </div>
      );
}
 
export default Advice;