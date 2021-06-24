import React, {Fragment, useEffect, useContext} from 'react'
import authContext from '../../context/auth/authContext';

const Dashboard = () => {
  
  const {obtenerUsuarioAutenticado} = useContext(authContext);

  useEffect(()=>{
    obtenerUsuarioAutenticado();
      //eslint-disable-next-line
  },[])
  
    return (
        <Fragment>
          <h1>Welcome</h1>
        </Fragment>
    );
}
 
export default Dashboard;