import React from 'react'

const Advice = ({advice}) => {
    return (
        <div class="col-lg-4">
            <div className="card">
                <img class="card-img-top" src="http://www.florespedia.com/Imagenes/flores-bonitas-girasoles.jpg" alt="Card image cap"></img>
                <div class="card-body">
                    <p class="card-text"><small class="text-muted">Categoria</small></p>
                    <h5 class="card-title">{advice.title}</h5>
                    <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
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