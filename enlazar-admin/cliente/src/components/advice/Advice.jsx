import React from 'react'

const Advice = ({advice}) => {
    return (
        <div className="col-lg-4">
            <div className="card">
                <img className="card-img-top" src="http://www.florespedia.com/Imagenes/flores-bonitas-girasoles.jpg" alt={advice.title}></img>
                <div className="card-body">
                    <p className="card-text"><small>{advice.tipe}</small></p>

                    <h3 className="card-title">{advice.title}</h3>
                    <p className="card-text">{advice.content}</p>
                    <div className='acciones'>
                <button
                    type='button'
                    className='btn btn-primary mr-2 w-100'
                ><i class="far fa-edit"></i>
                  Editar  
                </button>

                <button
                    type='button'
                    className="btn btn-outline-primary w-100"
                ><i class="far fa-trash-alt"></i>
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