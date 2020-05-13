import React, { Component } from 'react'
import DataService from "../DataService.js";
import MaterialTable from 'material-table';
import Card from 'react-bootstrap/Card';
import { Table, Button, Alert } from 'react-bootstrap';
import Panel from './common/Panel'
import axios from 'axios';
import Cookies from 'js-cookie'
import VehicleReservation from './VehicleReservation.js'


class Availability extends Component {
    constructor(props) {
        super(props);
         //check for authentication - if null, redirect to login
         var authToken = Cookies.get("token")


         if ( authToken === null || authToken === "" || authToken === undefined){
            window.location = "/login"
        }

        this.state = {
            results: [],
            error: null,
            response: {

            }
        }
    }

    render(){
        return (
            <React.Fragment>
                <Panel />
                <VehicleReservation />
                <a href="/home">Home</a>
            </React.Fragment>
        )
    }
}

export default Availability;