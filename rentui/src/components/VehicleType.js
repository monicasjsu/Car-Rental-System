import React, { Component } from 'react'
import Panel from './common/Panel'
import MaterialTable from 'material-table';
import DataService from "../DataService.js";
import Card from 'react-bootstrap/Card';
import { Table, Button, Alert } from 'react-bootstrap';
import Cookies from 'js-cookie'

class VehicleType extends Component {
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
            columns: [
                // {
                //     id: "vehicleTypeId",
                //     label: "vehicleTypeid",
                //
                // },
                {
                    field: "vehicleType",
                    title: "Vehicle Type"
                },
                {
                    field: "hourlyPrice",
                    title: "Hourly Price(6 hours or less)"
                },
                {
                    field: "hourly6To12HoursPrice",
                    title: "Hourly Price(7 to 12 hours)"
                },
                {
                    field: "hourlyMoreThan12HoursPrice",
                    title: "Hourly Price(12 hours to 3 days)"
                },
                {
                    field: "lateReturnFee",
                    title: "Late Return Fee"                    
                }

            ]
        }

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
    
                        columns={this.state.columns}
                        data = {this.state.vehicles}
                        
                        editable = {{
                            onRowAdd: (newData) =>
                                new Promise((resolve, reject) => {
                                    setTimeout(() => {
                                        {
                                            var restCall = DataService.addNewVehicleType(newData)
                                            restCall.then(res => {
                                                if (res.data == true) {
                                                    debugger
                                                    // this.state.vehicles.concat(newData)
                                                    this.setState(prevState => ({
                                                        vehicles: [...prevState.vehicles, newData]
                                                    }));
                                                    this.reloadVehicleList();
                                                }
                                            })
                                            .catch(error => {
                                                debugger
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
                                        var restCall = DataService.editVehicleType(oldData, newData)
                                        restCall.then(res => {
                                            this.reloadVehicleList();
                                        })
                                        .catch(error => {
                                            debugger
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
                                        var restCall = DataService.removeVehicleType(oldData.vehicleTypeId)
                                        restCall.then(res => {
                                            if (res.status === 200) {
                                                this.reloadVehicleList();
                                            }
                                        })
                                        .catch(error => {
                                            debugger
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
                        title = "Vehicle Type"
                        
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
        DataService.fetchVehicleTypes()
            .then((res) => {
                
                this.setState({
                    vehicles: res.data
                })  
            })
            .catch((error) => {
                console.log(error);
            }); 
    }

}

export default VehicleType;