import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import SignUp from './components/SignUp'

import Location from './components/Location'
import Vehicle from './components/Vehicle'
import Home from './components/Home'
import Login from './components/Login'
import Account from './components/Account'
import ManageUser from './components/ManageUser'
import Cookies from 'js-cookie'
import Reservations from './components/Reservations'
import Availability from './components/Availability';
import VehicleReservation from "./components/VehicleReservation";
import VehicleType from "./components/VehicleType";
import EditAccount from "./components/EditAccount"
import ReturnVehicle from "./components/ReturnVehicle"
import SignUpAdmin from "./components/SignUpAdmin"

export const getSession = () => {
  const jwt = Cookies.get('__session')
  let session
  try {
    if (jwt) {
      const base64Url = jwt.split('.')[1]
      const base64 = base64Url.replace('-', '+').replace('_', '/')
      session = JSON.parse(window.atob(base64))
    }
  } catch (error) {
    console.log(error)
  }
  return session
}

export const logOut = () => {
  Cookies.remove('__session')
  Cookies.remove('token')
}


function App() {
  return (
    <div className="App">
      <Router>
          <Switch>
              <Route path="/" exact component={Login} />
              <Route path="/vehicle" component={Vehicle} />
              <Route path="/location" component={Location} />
              <Route path="/home" component={Home} />
              <Route path="/login" component={Login} />
              <Route path="/accounts" component={Account} />
              <Route path="/signup" component={SignUp} />
              <Route path="/manageusers" component={ManageUser} />
              <Route path="/reservations" component={Reservations} />
              <Route path="/availability" component={Availability} />
              <Route path="/vehicleReservation/:vehicleId" component= {VehicleReservation} />
              <Route path="/returnvehicle/:resId" component={ReturnVehicle} />
              <Route path="/vehicleType" component={VehicleType} />
              <Route path="/editAccount" component={EditAccount} />
               <Route path="/SignUpAdmin" component={SignUpAdmin} />

          </Switch>
      </Router>
    </div>
  );
}

export default App;
