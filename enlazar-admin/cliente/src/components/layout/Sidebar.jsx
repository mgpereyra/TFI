import React from 'react'
import {Link} from 'react-router-dom'


const Sidebar = () => {
    return ( 
        <aside>
            <div className="side">
                <h1>
                <Link to={'/dashboard'} className='color-third navbar-brand'>
                    Enlazar
                </Link>
                </h1> 
                <Link to={'/list-advice'} className='color-third nav-link'>
                <i className="fas fa-user color-third"></i>
                    Listado de consejos
                </Link>
            </div>
        </aside>
     );
}
 
export default Sidebar;