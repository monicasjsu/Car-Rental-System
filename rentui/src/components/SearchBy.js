import React, { Component } from 'react'
import Dropdown from 'react-bootstrap/Dropdown'
import ButtonGroup from 'react-bootstrap/ButtonGroup'
import DropdownButton from 'react-bootstrap/DropdownButton'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import DataService from "../DataService.js";
import Card from 'react-bootstrap/Card';
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select";
import { Table, Button, Alert } from 'react-bootstrap';

class SearchBy extends Component {

    constructor(props) {
        super(props);
        this.loadVehicles = this.loadVehicles.bind(this);
        this.loadLocations = this.loadLocations.bind(this);
        this.handleChangeLocation = this.handleChangeLocation.bind(this);
        this.handleChangeVehicle = this.handleChangeVehicle.bind(this)
        this.reloadSearchResults = this.reloadSearchResults.bind(this);

        this.state = {
            locations: [],
            vehicles: [],
            selectedLocId: "",
            selectedVehicleId: "",
            results: [],
            error: null,
            response: {
            }
        }
    }
    componentDidMount() {
        this.loadVehicles();
        this.loadLocations();
        this.reloadSearchResults();

    }

    loadVehicles() {
        DataService.fetchVehicleTypes()
            .then((res) => {
                var vehiclesList = res.data.map((vehicle) => {
                    return {
                            vehicleType: vehicle.vehicleType,
                            vehicleTypeId: vehicle.vehicleTypeId }
                });

                this.setState({
                    vehicles: vehiclesList
                })
            })
            .catch((error) => {
                console.log(error);
            }); 
    }
    loadLocations() {
        DataService.fetchLocations()
            .then((res) => {
                var locationsList = res.data.map((location) => {
                    return {
                            locationName: location.locationName,
                             locationId: location.rentalLocationId }
                });

                this.setState({
                    locations: locationsList
                })
            })
            .catch((error) => {
                console.log(error);
            });     
    }


    handleChangeLocation(e){
        this.setState({selectedLocId:e.target.value});
    }

    handleChangeVehicle(e){
        this.setState({selectedVehicleId:e.target.value});
    }

    render(){
        const { error, results} = this.state;

        return(
            <React.Fragment>
                    <Card border='primary' fluid>
                        <Card.Header>
                            Search By
                        </Card.Header>
                        <Card.Body>
                            <Container fluid>
                            <Row>
                                <Col xs={1}>
                                </Col>
                                <Col xs={4}>
                                    <Row>
                                        <label>Location</label>
                                        {/* <DropdownButton id="dropdown-location"  value={this.state.selectedLocId} onSelect = { this.handleChangeLocation }  title="Location">
                                        <DropdownButton id="dropdown-location"  value={this.state.selectedLocId} onSelect = { this.handleChangeLocation }  title="Location">
                                            {this.createLocationItems()}
                                        </DropdownButton> */}

                                        <Select
                                            style={{ width: "100%" }}
                                            value={this.state.selectedLocId}
                                            onChange={this.handleChangeLocation }
                                            name="location"
                                        >
                                            {this.createLocationItems()}
                                        </Select>
                                    </Row>
                                </Col>
                                <Col xs={1}>
                                </Col>
                                <Col xs={4}>
                                    <Row>
                                        <label>Vehicle Type</label>
                                        {/* <DropdownButton id="dropdown-vehicle" value={this.state.selectedVehicleId} onSelect = { this.handleChangeVehicle }  title="Vehicle">
                                            {this.createVehicleItems()}
                                        </DropdownButton> */}

                                        <Select
                                            style={{ width: "100%" }}
                                            value={this.state.selectedVehicleId}
                                            onChange= { this.handleChangeVehicle }
                                            name="Vehicle Type"
                                        >
                                        {/*<DropdownButton id="dropdown-vehicle" value={this.state.selectedVehicleId} onSelect = { this.handleChangeVehicle }  title="Vehicle Type">*/}
                                            {this.createVehicleItems()}
                                        </Select>
                                    </Row>
                                   
                                </Col>
                                <Col>
                                        <input type="button" className="btn btn-primary" name="Search" value="Search" onClick={this.reloadSearchResults} />&nbsp;&nbsp;
                                        <input type="button" className="btn btn-primary" name="Clear" value="Clear" onClick={this.ClearSearch} />
                                </Col>
                                
                            </Row>
                            </Container>
                        </Card.Body>
                    </Card>
                     <Card border="primary">
                     <Card.Header> Search Results
                         {this.state.response.message && <Alert variant="info">{this.state.response.message}</Alert>}

                     </Card.Header>
                     <Card.Body>
                         <Row>
                             <Col>
                                 <Table>
                                     <thead>
                                         <tr>
                                             <th>Location</th>
                                             <th>Make</th>
                                             <th>Model</th>
                                             <th>Vehicle Type</th>
                                             <th>Hourly Price</th>
                                             <th>Hourly Price(7 - 12 hours)</th>
                                             <th>Hourly Price(> 12 hours)</th>
                                             <th>Late Return Fee</th>
                                             <th>Action</th>
                                         </tr>
                                     </thead>
                                     <tbody>
                                         {results.map(result => (
                                             <tr key={result.vehicleId}>
                                                 <td>{result.rentalLocationName}</td>
                                                 <td>{result.make}</td>
                                                 <td>{result.model}</td>
                                                 <td>{result.type.vehicleType}</td>
                                                 <td>{result.type.hourlyPrice}</td>
                                                 <td>{result.type.hourly6To12HoursPrice}</td>
                                                 <td>{result.type.hourlyMoreThan12HoursPrice}</td>
                                                 <td>{result.type.lateReturnFee}</td>
                                                 <td>
                                                     <Button variant="info" onClick={() =>
                                                         this.checkAvailability(result.vehicleId)}>Check Availability
                                                     </Button>
                                                 </td>
                                             </tr>
                                         ))}
                                     </tbody>
                                 </Table>
                             </Col>
                         </Row>
                         

                     </Card.Body>
                 </Card>
                 </React.Fragment>
                 
                
        )
    }


    createVehicleItems() {
        let items = []; 
        if (this.state.vehicles.length > 0){
            for (let i = 0; i < this.state.vehicles.length; i++) {  
                var k = this.state.vehicles[i].vehicleTypeId
                var v = this.state.vehicles[i].vehicleType
                 items.push(<MenuItem value={k}>{v}</MenuItem>); 
            }
        }        
        
        return items;
    }

    createLocationItems() {
        let items = [];         
        if (this.state.locations.length > 0){
            for (let i = 0; i < this.state.locations.length; i++) {  
                
                var k = this.state.locations[i].locationId
                var v = this.state.locations[i].locationName
                 items.push(<MenuItem value={k}>{v}</MenuItem>); 
            }
        }
        return items;
    }  
   
   onDropdownSelected(e) {
       console.log("THE VAL", e.target.value);
   }

    reloadSearchResults() {
        debugger
        var locId = this.state.selectedLocId 
        var vehicleId = this.state.selectedVehicleId
    
        DataService.fetchSearchResults(parseInt(locId), parseInt(vehicleId))
            .then((res) => {
                res.data.forEach(entry => {
                    var location = this.state.locations.find(location =>
                        location.locationId === entry.rentalLocationId);
                    if (location != null) {
                        entry.rentalLocationName = location.locationName;
                    }
                });
                this.setState({
                    results: res.data
                })
            })
            .catch((error) => {
                this.setState({ error });
            });
    }


    checkAvailability(vehicleId){
        window.location = "/vehicleReservation/" + vehicleId
    }

    ClearSearch(){
        window.location = "/home"
    }
}

export default SearchBy;