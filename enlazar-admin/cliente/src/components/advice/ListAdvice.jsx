import React, { Fragment, useEffect } from 'react'
import Advice from './Advice'
import {Link} from 'react-router-dom'
import {   useSelector, useDispatch } from 'react-redux'
import { listAdvices } from '../../actions/adviceAction'

const ListAdvice = () => {

    let newArrayAdvices =[]
    const dispatch = useDispatch();
    const listAdvice = () => dispatch (listAdvices());
    const advices = useSelector(state => state.advices.advices)
    const error = useSelector(state => state.advices.error)

   
    useEffect(() => {
       listAdvice()
    }, [])

    /*Object.values(advices).forEach(logArrayElements);

    function logArrayElements(element, index) {
         var key =  Object.keys(advices)[index];
         const data = element;
         data.id= key;
         newArrayAdvices.push(data)
     }*/
    
        return (  
        <Fragment>    
             <Link to={'/advice/create'} className='btn btn-primary mb-3'>
             <i className="fas fa-plus-circle"></i>
                   Crear un nuevo consejo
              </Link>
                 {advices.length === 0 && !error ?
                 <div className='alert alert-info text-center p-3'><i className="fas fa-exclamation-circle"></i>No hay consejos creados</div>
                 :
                 
                 <div className="row">
                    { Object.values(advices).map( advice => (
                        <Advice
                            key={advice.id}
                            advice={advice}                
                        />  
                    ))
                    }
                 </div>
 
                 } 
            
        </Fragment>
    );
}
 
export default ListAdvice;