import React, { Component } from 'react'
// eslint-disable-next-line
import Panel from './common/Panel'
import MaterialTable from 'material-table';
import DataService from "../DataService.js";
import Card from 'react-bootstrap/Card';
import { Table, Button, Alert } from 'react-bootstrap';
import Cookies from 'js-cookie'

class Vehicle extends Component {
    constructor(props) {
        super(props);
        //check for authentication - if null, redirect to login
        var authToken = Cookies.get("token")


        if ( authToken === null || authToken === "" || authToken === undefined){
            window.location = "/login"
        }

        this.reloadVehicleList = this.reloadVehicleList.bind(this);
        this.state = {
            vehicles: [],
            vehiclesType: [],
            locations: []
        }

    }

    getColumns() {
        return [
            // {
            //     id: "vehicleId",
            //     label: "vehicle id",
            //
            // },
            {
                field: "type.vehicleType",
                title: "Vehicle Type" ,
                lookup: this.state.vehiclesType
            },
            {
                field: "rentalLocationId",
                title: "Location",
                lookup: this.state.locations
            },
            {
                field: "make",
                title: "Make"

            },
            {
                field: "model",
                title: "Model"
            },
            {
                field: "year",
                title: "Year"
            },
            {
                field: "registrationTag",
                title: "Registration Tag"
            },

            {
                field: "vehicleCondition",
                title: "Condition"
            },
            // {
            //     field: "vehicleStatus",
            //     title: "Vehicle Status"
            // },
            // {
            //     field: "Time",
            //     title: "Time"
            // },
            {
                field: "currentMileage",
                title: "Millage"
            },

        ]
    }

    render(){
        
        return(
            <React.Fragment>
                <Panel />
                <Card>
                    {/* <Card.Header>
                        Vehicles
                    </Card.Header> */}
                    <Card.Body>
                        <MaterialTable
                        enableRowDelete={true}
                        enableRowAdd={true}
    
                        columns={this.getColumns()}
                        data = {this.state.vehicles}
                        
                        editable = {{
                            onRowAdd: (newData) =>
                                new Promise((resolve, reject) => {
                                    setTimeout(() => {
                                        {
                                            var restCall = DataService.addNewVehicle(newData)
                                            restCall.then(res => {
                                                if (res.data == true) {
                                                    // this.state.vehicles.concat(newData)
                                                    this.setState(prevState => ({
                                                        vehicles: [...prevState.vehicles, newData]
                                                    }));
                                                }
                                            })
                                            .catch(error => {
                                                alert("error on add")
                                             });
                                        }
                                        resolve();
                                    }, 1000);
                                }),
                            onRowUpdate: (newData, oldData) =>
                                new Promise((resolve, reject) => {
                                    setTimeout(() => {
                                        {
                                            var restCall = DataService.editVehicle(oldData, newData)
                                            restCall.then(res => {
                                                this.reloadVehicleList()
                                            })
                                            .catch(error => {

                                                alert("error on update")

                                             });
                                        }
                                        resolve();
                                    }, 1000);
                                }),
                            onRowDelete: (oldData) =>
                                new Promise((resolve, reject) => {
                                    setTimeout(() => {
                                        {
                                            var restCall = DataService.removeVehicle(oldData.vehicleId)
                                            restCall.then(res => {
                                                if (res.status === 200) {
                                                    this.reloadVehicleList()
                                                }
                                            })
                                            .catch(error => {

                                                alert("error delete")

                                             });
                                        }
                                        resolve();
                                    }, 1000);
                                })
                        }}

                        options={{
                            actionsColumnIndex: -1
                        }}
                        title = "Vehicles"
                        
                        >
                        </MaterialTable>
                    </Card.Body>
                    
                </Card>
            </React.Fragment>
        )
    }

    componentDidMount() {
        this.reloadVehicleList();
    }

    reloadVehicleList() {
        DataService.fetchVehicles().then((vehicles) => {
                DataService.fetchLocations().then(locations => {
                    DataService.fetchVehicleTypes()
                        .then(vehicleTypes => {
                            let locationsLookUp = {}
                            locations.data.forEach(entry => {
                                locationsLookUp[entry.rentalLocationId] = entry.locationName
                            })
                            let vehicleTypeLookUp = {}
                            vehicleTypes.data.forEach(entry => {
                                vehicleTypeLookUp[entry.vehicleType] = entry.vehicleType
                            })
                            this.setState({
                                vehicles: vehicles.data,
                                locations: locationsLookUp,
                                vehiclesType: vehicleTypeLookUp
                            })
                        })
                        .catch(error3 => {
                            console.log(error3);
                        })
                }).catch(error2 => {
                    console.log(error2);
                })
        })
        .catch((error) => {
            console.log(error);
        });
    }

}

export default Vehicle;