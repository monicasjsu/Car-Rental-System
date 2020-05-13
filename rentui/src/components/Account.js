import React, { Component } from 'react'
import Panel from './common/Panel'
import DataService from "../DataService.js";
import Cookies from 'js-cookie'
import Card from 'react-bootstrap/Card';
import { Button } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

class Account extends Component {
    constructor(props) {
        super(props);
         //check for authentication - if null, redirect to login
         var authToken = Cookies.get("token")

         if ( authToken === null || authToken === "" || authToken === undefined){
            window.location = "/login"
        }
        this.reloadAccount = this.reloadAccount.bind(this);
        this.state = {
            accounts: [],
            address: [],
            license: [],
            card: []
        }
    }

    render(){
        return(
            <React.Fragment>
                <Panel />
                <Card>
                    <Card.Header>
                        Account Information
                    </Card.Header>
                    <Card.Body>
                        <Card border="primary">
                            <Card.Header>User</Card.Header>
                            <Card.Body>
                                <Row>
                                    <Col>
                                        Email
                                    </Col>
                                    <Col>
                                        {this.state.accounts.email}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Name
                                    </Col>
                                    <Col>
                                        {this.state.accounts.firstName}, {this.state.accounts.lastName}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Age
                                    </Col>
                                    <Col>
                                        {this.state.accounts.age}
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                        <Card border="primary">
                            <Card.Header>Address</Card.Header>
                            <Card.Body>
                                <Row>
                                    <Col>
                                        Street Name
                                    </Col>
                                    <Col>
                                        {this.state.address.streetName}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        City
                                    </Col>
                                    <Col>
                                        {this.state.address.city}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        State
                                    </Col>
                                    <Col>
                                        {this.state.address.state}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        ZipCode
                                    </Col>
                                    <Col>
                                        {this.state.address.zipCode}
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                        <Card border="primary">
                            <Card.Header>Driver License</Card.Header>
                            <Card.Body>
                                <Row>
                                    <Col>
                                        License Id
                                    </Col>
                                    <Col>
                                        {this.state.license.licenseId}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Expiry
                                    </Col>
                                    <Col>
                                        {new Date(this.state.license.expiry).toLocaleDateString("en-US")}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Type
                                    </Col>
                                    <Col>
                                        {this.state.license.type}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        State Issued
                                    </Col>
                                    <Col>
                                        {this.state.license.stateOfIssue}
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                        <Card border="primary">
                            <Card.Header>Credit Card</Card.Header>
                            <Card.Body>
                                <Row>
                                    <Col>
                                        Credit Card Number
                                    </Col>
                                    <Col>
                                        {this.state.card.cardNumber}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Issued Date
                                    </Col>
                                    <Col>
                                        {new Date(this.state.card.dateOfIssue).toLocaleDateString("en-US")}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Expiry Date
                                    </Col>
                                    <Col>
                                        {new Date(this.state.card.expiryDate).toLocaleDateString("en-US")}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Name On Card
                                    </Col>
                                    <Col>
                                        {this.state.card.name}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        Give CVV
                                    </Col>
                                    <Col>
                                        {this.state.card.cvv}
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                        <br />
                        <Row>
                            <Col>
                                <Button onClick={this.deactivateAccount}>De-activate Account</Button>
                            </Col>
                            <Col>
                                <Button onClick={this.editAccount}>Edit Account</Button>
                            </Col>
                        </Row>
                    </Card.Body>
                </Card>
            </React.Fragment>
        )
    }

    componentDidMount() {
        this.reloadAccount();
    }

    reloadAccount() {
        DataService.getUser()
            .then((res) => {
                debugger
                this.setState({
                    accounts: res.data,
                    address: res.data.address,
                    license: res.data.license,
                    card: res.data.card
                })
            })
            .catch((error) => {
                console.log(error);
            }); 
    }

    deactivateAccount(){
        DataService.deactivateAccount()
        .then((res) => {
            //logout user
            Cookies.remove("token")
            window.location = "/login"
        })
        .catch((error) => {
            alert("error in deactivate account");
        }); 
    }

    editAccount(){
        window.location = "/editAccount/"
    }
}

export default Account;