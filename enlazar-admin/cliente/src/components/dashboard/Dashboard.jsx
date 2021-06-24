import React, {Fragment, useEffect, useContext} from 'react'

import Welcome from './Welcome';
import Create from '../advice/Create'
import authContext from '../../context/auth/authContext';

const Dashboard = () => {
  
  const {obtenerUsuarioAutenticado} = useContext(authContext);

  useEffect(()=>{
    obtenerUsuarioAutenticado();
      //eslint-disable-next-line
  },[])
  
    return (
        <Fragment>
              <Welcome/>
            </Fragment>
        );
}
 
export default Dashboard;