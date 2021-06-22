import React from 'react'

function Header (){
    return (
        <header className="app-header">
            <p className="nombre-usuario">Hola <span>Marcia</span></p>
            <nav className="nav-principal">
                <a href="#!">Cerrar sesion</a>
            </nav>
        </header>
    )
}

export default Header;