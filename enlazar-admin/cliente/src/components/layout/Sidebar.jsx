import React from 'react'
import {Link} from 'react-router-dom'
import logo from '../../images/enlazar-ocre-lg.jpg'

const Sidebar = () => {
    return ( 
        <aside>
            <div className="side">
                <h1 className='text-center'>
                <Link to={'/dashboard'} className='color-third navbar-brand'>
                    <img className="w-75 mb-4" src={logo}></img>
                </Link>
                </h1> 

                <Link to={'/'} className='color-third nav-link mb-3 disabled'>
                <i className="fas fa-street-view"></i>
                    Servicios
                </Link>

                <Link to={'/list-advice'} className='color-third nav-link mb-3'>
                <i className="fas fa-hands-helping"></i>
                    Consejos
                </Link>

                <Link to={'/list-meeting'} className='color-third nav-link mb-3'>
                <i className="fas fa-hand-holding-heart"></i>
                    Puntos de encuentro
                </Link>

                <Link to={'/'} className='color-third nav-link disabled'>
                <i className="fas fa-recycle"></i>
                    Recolectores
                </Link>

                <Link to={'/list-coupon'} className='color-third nav-link'>
                <i className="fas fa-recycle"></i>
                    Cupones
                </Link>
            </div>
        </aside>
     );
}
 
export default Sidebar;