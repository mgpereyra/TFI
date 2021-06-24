import React, {Fragment} from 'react'
import Advice from './Advice'
import {Link} from 'react-router-dom'

const List = () => {

const advices = [
    {title:'Advice 1'},
    {title:'Advice 2'},
    {title:'Advice 3'},
    {title:'Advice 4'},
    {title:'Advice 5'}


]

    return (  
        <Fragment>       
             <Link to={'/advice/create'} className='btn btn-primary'>
                <i className="fas fa-user color-third"></i>
                   Crear un nuevo consejo
              </Link>
             <div className="row">
                {advices.map( advice => (
                    <Advice
                        key={advice.title}
                        advice={advice}                
                    />  
                ))
                }
             </div>

        </Fragment>
    );
}
 
export default List;