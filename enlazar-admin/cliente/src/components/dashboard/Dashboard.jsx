import React, {Fragment} from 'react'

import ListAdvices from '../advice/List';
import Create from '../advice/Create'



const Dashboard = () => {
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