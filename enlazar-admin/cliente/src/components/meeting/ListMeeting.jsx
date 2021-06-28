import React from 'react'
import {Link} from 'react-router-dom'

const ListMeeting = () => {
    return ( 
        <div className="d-flex justify-content-between">
            <h2><i className="fas fa-hand-holding-heart"></i>Listado de punto de encuentro</h2>
            <Link to={'/create-meeting'} className='btn btn-primary mb-3'>
            <i className="fas fa-plus-circle"></i>
                Crear un nuevo encuentro
            </Link>
         </div>
     );
}
 
export default ListMeeting;