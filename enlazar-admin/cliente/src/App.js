import Login from './components/auth/Login'
import NewAccount from './components/auth/NewAccount'
import Dashboard from './components/dashboard/Dashboard'
import ListAdvice from './components/advice/List'
import Sidebar from './components/layout/Sidebar' 
import Header from './components/layout/Header' 
import './index.css'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'

import AlertaState from './context/alerta/alertaState'
import AuthState from './context/auth/authState'


function App() {

  return (
    <AlertaState>
      <AuthState>
        <Router>
        <Switch>
            <Route exact path="/" component={Login}/>
            <Route exact path="/new-account" component={NewAccount}/>
            <div className="contenedor-app">
              <Sidebar/>
              <div className="seccion-principal">
                <Header/>
                <main>
                  <Route exact path="/dashboard" component={Dashboard}/>
                  <Route exact path="/list-advice" component={ListAdvice}/>
              </main>
              </div>
            </div>
          </Switch>
        </Router>
      </AuthState>
    </AlertaState>

  );
}

export default App;
