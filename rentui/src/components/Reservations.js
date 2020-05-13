import React, { Component } from 'react'
import DataService from "../DataService.js";
import MaterialTable from 'material-table';
import Card from 'react-bootstrap/Card';
import { Table, Button, Alert } from 'react-bootstrap';
import Panel from './common/Panel'
import axios from 'axios';
import Cookies from "js-cookie";
import Form from "react-bootstrap/Form";

class Reservations extends Component {
    constructor(props) {
        super(props);

        //check for authentication - if null, redirect to login
        var authToken = Cookies.get("token")


        if ( authToken === null || authToken === "" || authToken === undefined){
            window.location = "/login"
        }

        this.reloadReservations = this.reloadReservations.bind(this);
        this.cancelReservation = this.cancelReservation.bind(this);

        this.state = {
            results: [],
            error: null,
            response: {

            }
        }
    }

    render(){
        const { error, results} = this.state;

        if(error) {
            return (
              <div>Error: {error.message}</div>
            )
          }
        else{
            return(
                <React.Fragment>
                    <Panel />
                    <Card border="primary">
                        <Card.Header> Reservations
                            {this.state.response.message && <Alert variant="info">{this.state.response.message}</Alert>}

                        </Card.Header>
                        <Card.Body>
                            <Table>
                                <thead>
                                    <tr>
                                        <th>Vehicle Id</th>
                                        <th>Reserve From</th>
                                        <th>Duration(hours)</th>
                                        <th>Estimated Charges</th>
                                        <th>Final Charges</th>
                                        <th>Late Fee</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {results.map(result => (
                                        <tr key={result.reservationId}>
                                            <td>{result.vehicleId}</td>
                                            <td>{new Date(result.reserveFrom).toLocaleString("en-US")}</td>
                                            <td>{result.durationHours}</td>
                                            <td>{result.estimatedCharges}</td>
                                            <td>{result.finalCharges}</td>
                                            <td>{result.lateFee}</td>
                                            <td>{result.reservationStatus}</td>
                                            <td>
                                                <Button variant="info" onClick={() => this.cancelReservation(result.reservationId)}>Cancel</Button>&nbsp;&nbsp;
                                                <Button variant="info" onClick={() => this.returnVehicle(result.reservationId)}>Return Vehicle</Button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                            {/*<MaterialTable*/}
                            {/*    columns={[*/}
                            {/*        {*/}
                            {/*            field: "result.vehicleId",*/}
                            {/*            title: "Vehicle Id"*/}
                            {/*        },*/}
                            {/*        {*/}
                            {/*            field: "new Date(result.reserveFrom).toString()",*/}
                            {/*            title: "Reserve From"*/}
                            {/*        },*/}
                            {/*        {*/}
                            {/*            field: "result.durationHours",*/}
                            {/*            title: "Duration(hours)"*/}
                            {/*        },*/}
                            {/*        {*/}
                            {/*            field: "result.estimatedCharges",*/}
                            {/*            title: "Estimated Charges"*/}
                            {/*        },*/}
                            {/*        {*/}
                            {/*            field: "result.finalCharges",*/}
                            {/*            title: "Final Charges"*/}
                            {/*        },*/}
                            {/*        {*/}
                            {/*            field: "result.lateFee",*/}
                            {/*            title: "Late Fee"*/}
                            {/*        },*/}
                            {/*    ]}*/}
                            {/*    data = {results}*/}

                            {/*    options={{*/}
                            {/*        actionsColumnIndex: -1*/}
                            {/*    }}*/}
                            {/*    title = "Reservatios"*/}
                            {/*>*/}
                            {/*</MaterialTable>*/}

                        </Card.Body>
                    </Card>
                </React.Fragment>
            )

        }
        
    }

    componentDidMount() {
        this.reloadReservations();
    }

    reloadReservations() {
        DataService.fetchReservationsByUser()
            .then((res) => {
                this.setState({
                    results: res.data
                })
            })
            .catch((error) => {
                this.setState({ error });
            }); 
    }

    cancelReservation(resId){
        DataService.cancelReservations(resId)
                    .then((res)=>{
                        debugger
                        console.log(res);
                        console.log(res.data);
                        window.location = "/reservations" 
                        alert("Reservation cancelled")
                    })
                    .catch(error => {
                        debugger
                        alert("Reservation failed: ", error.response)
                         
                     });
  
        
    }
    returnVehicle(resId){
       window.location = "/returnVehicle/" + resId 
    }

}

export default Reservations;