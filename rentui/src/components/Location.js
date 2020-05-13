import React, { Component } from 'react';
import Panel from './common/Panel';
import DataService from "../DataService.js";
import Cookies from 'js-cookie'
import MaterialTable from 'material-table';

class Location extends Component {
    constructor(props) {
        super(props);
                //check for authentication - if null, redirect to login
                var authToken = Cookies.get("token")


                if ( authToken === null || authToken === "" || authToken === undefined){
                    window.location = "/login"
                }
        
        this.reloadLocationList = this.reloadLocationList.bind(this);
        this.state = {
            locations: [],
        }
    }

    render(){
        return(
            <React.Fragment>
                <Panel />
                <div>
                    <MaterialTable
                    enableRowAdd={true}
                    columns={[ 
                        {
                            field: "locationName",
                            title: "Location Name"
                        },
                        {
                            id: "address",
                            label: "Address"
                        },
                        {
                            field: "vehicleCapacity",
                            title: "Capacity"                    
                        },
                        // {
                        //     id: "vehicles",
                        //     label: "Vehicles"
                        // },
                        {
                            field: "streetName",
                            title: "Street"
                        },
                        {
                            field: "city",
                            title: "City"
                        },
                        {
                            field: "state",
                            title: "State"
                        },
                        {
                            field: "zipCode",
                            title: "Zip"
                        },
                        // {
                        //     id: "rentalLocationId",
                        //     label: "Location"
                        // },
                    ]}
                    data = {this.state.locations}

                    editable = {{
                        onRowAdd: (newData) =>
                            new Promise((resolve, reject) => {
                                setTimeout(() => {
                                    {
                                        var restCall = DataService.addNewLocation(newData)
                                        restCall.then(res => {
                                            if (res.data == true) {
                                                // this.state.locations.concat(newData)
                                                this.setState(prevState => ({
                                                    locations: [...prevState.locations, newData]
                                                }));
                                                this.reloadLocationList();
                                            }
                                        })
                                    }
                                    resolve();
                                }, 1000);
                            }),
                        onRowUpdate: (newData, oldData) =>
                            new Promise((resolve, reject) => {
                                setTimeout(() => {
                                    {
                                        var restCall = DataService.editLocation(oldData, newData)
                                        restCall.then(res => {
                                            if (res.data === true) {
                                                debugger
                                                // var index = this.state.locations.findIndex(a =>
                                                //     a.rentalLocationId === newData.rentalLocationId)
                                                //  this.state.locations[index] = newData;
                                                //  this.state.locations.concat(newData)
                                                // this.setState(prevState => ({
                                                //     locations: [...prevState.locations, newData]
                                                // }));
                                                this.reloadLocationList()
                                            }
                                        })
                                    }
                                    resolve();
                                }, 1000);
                            }),
                        onRowDelete: (oldData) =>
                            new Promise((resolve, reject) => {
                                setTimeout(() => {
                                    {
                                        var restCall = DataService.removeLocation(oldData.rentalLocationId)
                                        restCall.then(res => {
                                            if (res.status === 200) {
                                                // debugger
                                                // var index = this.state.locations.findIndex(a =>
                                                //     a.rentalLocationId === oldData.rentalLocationId)
                                                // this.state.locations.slice(index, 1)

                                                this.reloadLocationList()
                                                
                                            }
                                        })
                                    }
                                    resolve();
                                }, 1000);
                            })
                    }}
                    options={{
                        actionsColumnIndex: -1
                    }}
                    title = "Locations"
                    >
                    </MaterialTable>
                </div>
            </React.Fragment>
        )
    }

    componentDidMount() {
        this.reloadLocationList();
    }

    reloadLocationList() {
        DataService.fetchLocations()
            .then((res) => {
                var locations = res.data.map(( location) => {
                    location.streetName = location.address.streetName;
                    location.city = location.address.city;
                    location.state = location.address.state;
                    location.zipCode = location.address.zipCode;
                    return location;
                });
                this.setState({
                    locations: res.data
                })
            })
            .catch((error) => {
                console.log(error);
            }); 
    }
}

export default Location;