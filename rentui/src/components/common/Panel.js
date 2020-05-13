import React, { Component } from 'react'
import Container from 'react-bootstrap/Container';
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'
import Cookies from 'js-cookie'



class Panel extends Component{

    constructor(props) {
        super(props);
        this.state ={
            isAdmin: Cookies.get("isAdmin")
        }
        
        this.logoutUser = this.logoutUser.bind(this);

    }

    logoutUser(){
        //Remove auth cookie
        Cookies.remove("token")
        //redirect to login
        window.location = "/login"
    }

    render(){
        
        return(
            <React.Fragment>
                        <Card>
                        <Card.Header>
                            GOF Rent-A-Car
                        </Card.Header>
                        <Card.Body>
                            <Row>
                                <Col>
                                    <a href= "/Home">Home</a>
                                </Col>
                                <Col>
                                    <a href="/location" style={{display: this.state.isAdmin == "true" ? 'block' : 'none'}}>Locations</a>
                                </Col>
                                <Col>
                                    <a href= "/VehicleType" style={{display: this.state.isAdmin == "true" ? 'block' : 'none'}}>Vehicle Types</a>
                                </Col>
                                <Col>
                                    <a href= "/Vehicle" style={{display: this.state.isAdmin == "true" ? 'block' : 'none'}}>Vehicles</a>
                                </Col>
                                <Col>
                                    <a href = "/manageusers" style={{display: this.state.isAdmin == "true" ? 'block' : 'none'}}>Manage Users</a>
                                </Col>
                                <Col>
                                    <a href = "/reservations">Reservations</a>
                                </Col>
                                <Col>
                                    <a href = "/accounts" style={{display: this.state.isAdmin == "false" ? 'block' : 'none'}}>Account</a>
                                </Col>
                                <Col>
                                    <Button name="logout" onClick={this.logoutUser}>LogOut </Button>
                                </Col>
                            </Row>
                        </Card.Body>
                    </Card>
            </React.Fragment>
        )
    }
}

export default Panel;