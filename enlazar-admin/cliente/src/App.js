import Login from './components/auth/Login'
import NewAccount from './components/auth/NewAccount'
import Header from './components/layout/Header' 
import Sidebar from './components/layout/Sidebar' 
import Dashboard from './components/dashboard/Dashboard'
import ListAdvice from './components/advice/List'
import EditAdvice from './components/advice/EditAdvice' 
import CreateAdvice from './components/advice/Create' 

import './index.css'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'

import AlertaState from './context/alerta/alertaState'
import AuthState from './context/auth/authState'
import authToken from './config/authToken'
import PrivateRoute from './components/routes/PrivateRoute'

//revisar si tenemos un token
const token = localStorage.getItem('token');

if(token){
  authToken(token);
}


function App() {
  return (
    <AlertaState>
      <AuthState>
        <Router>
        <Switch>
            <Route exact path="/" component={Login}/>
            <Route exact path="/new-account" component={NewAccount}/>
            </Switch>
            <div className="contenedor-app">
              <Sidebar/>
              <div className="seccion-principal">
                <Header/>
                <main>
                <Switch>
                  <PrivateRoute exact path="/dashboard" component={Dashboard}/>
                  <PrivateRoute exact path="/list-advice" component={ListAdvice}/>
                  <PrivateRoute exact path="/advice/edit/:id" component={EditAdvice}/>
                  <PrivateRoute exact path="/advice/create" component={CreateAdvice}/>

                </Switch>
              </main>
              </div>
            </div>
         
        </Router>
      </AuthState>
    </AlertaState>

  );
}

export default App;
