import React from 'react'

const Advice = ({advice}) => {
    return (
        <div className="col-lg-4">
            <div className="card">
                <img className="card-img-top" src="http://www.florespedia.com/Imagenes/flores-bonitas-girasoles.jpg" alt={advice.title}></img>
                <div className="card-body">
                    <p className="card-text"><small className="text-muted">Categoria</small></p>
                    <h5 className="card-title">{advice.title}</h5>
                    <p className="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                    <div className='acciones'>
                <button
                    type='button'
                    className='btn btn-primary mr-2'
                >
                  Editar  
                </button>

                <button
                    type='button'
                    className="btn btn-outline-primary"
                ><i className="far fa-user "></i>
                  Eliminar  
                </button>
                
            </div>    
                </div>
            </div>
        </div>
      );
}
 
export default Advice;