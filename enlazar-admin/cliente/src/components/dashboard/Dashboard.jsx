import React, {Fragment, useEffect, useContext} from 'react'

import ListAdvices from '../advice/List';
import Create from '../advice/Create'
import authContext from '../../context/auth/authContext';

const Dashboard = () => {
  
  const {obtenerUsuarioAutenticado} = useContext(authContext);

  useEffect(()=>{
    obtenerUsuarioAutenticado();
  },[])
  
    return (
        <Fragment>
            <Create/>
            <div className="contenedor-tareas"> 
              <ListAdvices/>
            </div>
            </Fragment>
        );
}
 
export default Dashboard;