import React from 'react';
import Login from './components/auth/Login'
import NewAccount from './components/auth/NewAccount'

import Header from './components/layout/Header' 
import Footer from './components/layout/Footer' 
import Sidebar from './components/layout/Sidebar' 
import Dashboard from './components/dashboard/Dashboard'

import ListAdvice from './components/advice/ListAdvice'
import EditAdvice from './components/advice/EditAdvice' 
import CreateAdvice from './components/advice/CreateAdvice' 

import CreateMeeting from './components/meeting/CreateMeeting' 
import ListMeeting from './components/meeting/ListMeeting' 
import EditMeeting from './components/meeting/EditMeeting' 

import CreateCoupon from './components/coupon/CreateCoupon' 
import ListCoupon from './components/coupon/ListCoupon';
import EditCoupon from './components/coupon/EditCoupon';
import VerifyCoupon from './components/coupon/VerifyCoupon';
import VerifyCouponByCamera from './components/coupon/VerifyCouponByCamera';

import CreateRecycler from './components/recycler/CreateRecycler' 
import ListRecycler from './components/recycler/ListRecycler';
import EditRecycler from './components/recycler/EditRecycler';

import ListService from './components/service/ListService';
import ManageService from './components/service/ManageService';


import {conectFirebase } from './config/firebase'

import './index.css'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'

import AlertaState from './context/alerta/alertaState'
import AuthState from './context/auth/authState'
import authToken from './config/authToken'
import PrivateRoute from './components/routes/PrivateRoute'

//REDUX
import { Provider } from 'react-redux'
import store from './store'

//revisar si tenemos un token
const token = localStorage.getItem('token');

if(token){
  authToken(token);
}

conectFirebase()

function App() {
  return (
    <AlertaState>
      <AuthState>
        <Router>
          <Provider store={store}>
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

                      <PrivateRoute exact path="/advice/:id"/>
                      <PrivateRoute exact path="/list-advice" component={ListAdvice}/>
                      <PrivateRoute exact path="/edit-advice/:id" component={EditAdvice}/>
                      <PrivateRoute exact path="/create-advice" component={CreateAdvice}/>

                      <PrivateRoute exact path="/create-meeting" component={CreateMeeting}/>
                      <PrivateRoute exact path="/list-meeting" component={ListMeeting}/>
                      <PrivateRoute exact path="/edit-meeting/:id" component={EditMeeting}/>
                      
                      <PrivateRoute exact path="/create-coupon" component={CreateCoupon}/>
                      <PrivateRoute exact path="/list-coupon" component={ListCoupon}/>
                      <PrivateRoute exact path="/edit-coupon/:id" component={EditCoupon}/>
                      <PrivateRoute exact path="/verify-coupon" component={VerifyCoupon}/>
                      <PrivateRoute exact path="/verify-coupon-archive" component={VerifyCouponByCamera}/>
                     
                      

                      <PrivateRoute exact path="/create-recycler" component={CreateRecycler}/>
                      <PrivateRoute exact path="/list-recycler" component={ListRecycler}/>
                      <PrivateRoute exact path="/edit-recycler/:id" component={EditRecycler}/>

                      <PrivateRoute exact path="/list-service" component={ListService}/>
                      <PrivateRoute exact path="/manage-service" component={ManageService}/>


                    </Switch>
                    </main>
                    <Footer/>
                </div>
            </div>
             
              
        </Provider>
        </Router>
      </AuthState>
    </AlertaState>

  );
}

export default App;
