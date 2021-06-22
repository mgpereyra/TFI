import React, {useContext, useEffect} from 'react'
import authContext from '../../context/auth/authContext';

const Header = () => {
    const {obtenerUsuarioAutenticado, usuario, cerrarSesion} = useContext(authContext);

    useEffect(()=>{
      obtenerUsuarioAutenticado();
    },[])

    return (
        <header className="app-header">
            {usuario ?
            <p className="nombre-usuario">Hola <span>{usuario.name}</span></p>
            :null}
            <nav className="nav-principal">
                <button className='btn btn-dark'
                    onClick={() => cerrarSesion()}>
                    Cerrar sesión
                </button>
            </nav>
        </header>
    )
}

export default Header;