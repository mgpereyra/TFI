import React from 'react';

const Footer = () => {
    let fecha = new Date().getFullYear();

    return ( 
        <footer className='text-center mt-2 py-1'>
            <small className='mb-1 text-muted'>Todos los derechos reservados Enlazar &copy;{fecha}</small>
        </footer>
     );
}
 
export default Footer;
