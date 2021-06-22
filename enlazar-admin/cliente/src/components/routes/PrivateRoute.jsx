import React, {useContext, useEffect} from 'react'
import { Route, Redirect } from 'react-router-dom'
import authContext from '../../context/auth/authContext'

const PrivateRoute = ({component: Component, ...props}) => {
   const {autenticado} = useContext(authContext)

    return ( 
        <Route {...props} render={props=>!autenticado?(
            //no esta autenticado
            <Redirect to="/"/>
        ):(
            <Component {...props}/>
        )}/>
     );
}
 
export default PrivateRoute;