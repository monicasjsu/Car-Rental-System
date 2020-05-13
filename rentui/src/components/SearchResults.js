import React, { Component } from 'react'
import DataService from "../DataService.js";
import MaterialTable from 'material-table';
import Card from 'react-bootstrap/Card';
import { Table, Button, Alert } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';


class SearchResults extends Component {
    constructor(props) {
        super(props);
        this.reloadSearchResults = this.reloadSearchResults.bind(this);
        this.state = {
            results: [],
            locations: [],
            error: null,
            response: {
            }
        }


        
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
                                                <th>Hourly Price(6 hours or less)</th>
                                                <th>Hourly Price(7 to 12 hours)</th>
                                                <th>Hourly Price(more than 12 hours)</th>
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
        
    }

    componentDidMount() {
        this.reloadSearchResults();
    }

    reloadSearchResults() {
          

        DataService.fetchSearchResults(0, 0)
            .then((res) => {
                res.data.forEach(entry => {
                    var location = this.state.locations.find(location => location.locationId === entry.rentalLocationId);
                    if (location != null) {
                        entry.rentalLocationName = location.locationName;
                    }
                });           

                this.setState({
                    results: res.data,
                });
            })
            .catch((error) => {
                this.setState({ error });
            }); 
    }


    checkAvailability(vehicleId){
        window.location = "/vehicleReservation/" + vehicleId
    }
}

export default SearchResults;