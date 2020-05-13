import React, { Component } from 'react'
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Panel from './common/Panel'
import { useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie'
import DataService from "../DataService";

class EditAccount extends Component{
    constructor(props) {
        super(props);
        this.state = {
            password: "",
            email: "",
            ReEnterPassword: "",
            firstName: "",
            lastName: "",
            age: "",
            streetName: "",
            license: "",
            card: "", 
            licenseId: "",
            expiry: "",
            type: "",
            stateOfIssue: "",
            cardNumber: "",
            dateOfIssue: "",
            expiryDate: "",
            name: "",
            cvv: "",
            city: "",
            zipCode: "",
            stateAdd: "",
            accounts: [],
            addresss: [],
            licenses: [],
            cards: []
        }
        this.reloadAccount = this.reloadAccount.bind(this);
    }
    
    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      }

    render(){

        // const [validated, setValidated] = useState(false);
      
        const handleSubmit = (event) => {
            debugger
            //   const form = event.currentTarget;
            //   if (form.checkValidity() === false) {
            //     event.preventDefault();
            //     event.stopPropagation();
            //   }

            //   setValidated(true);
            event.preventDefault();
            var data = {
                "firstName": this.state.accounts.firstName,
                "lastName": this.state.accounts.lastName,
                "password": this.state.password, // Has to be empty if we are not setting any thing
                "email": this.state.accounts.email,
                "age": this.state.accounts.age,
                "address": {
                    "streetName": this.state.streetName ? this.state.streetName: this.state.addresss.streetName,
                    "city": this.state.city ? this.state.city : this.state.addresss.city,
                    "state": this.state.state ? this.state.state : this.state.addresss.state,
                    "zipCode": this.state.zipCode ? this.state.zipCode : this.state.addresss.zipCode
                },
                "license": {
                    "licenseId": this.state.licenseId ? this.state.licenseId : this.state.licenses.licenseId,
                    "expiry": this.state.expiry? new Date(this.state.expiry) : this.state.licenses.expiry,
                    "type": this.state.type ? this.state.type : this.state.licenses.type,
                    "stateOfIssue": this.state.stateOfIssue ? this.state.stateOfIssue : this.state.licenses.stateOfIssue
                },
                "card": {
                    "cardNumber": this.state.cardNumber ? this.state.cardNumber : this.state.cards.cardNumber,
                    "dateOfIssue": this.state.dateOfIssue ? new Date(this.state.dateOfIssue) : this.state.cards.dateOfIssue,
                    "expiryDate": this.state.expiryDate ? new Date(this.state.expiryDate) : this.state.cards.expiryDate,
                    "name": this.state.name ? this.state.name : this.state.cards.name,
                    "cvv": this.state.cvv ? this.state.cvv : this.state.cards.cvv
                }
            }
            console.log(JSON.stringify(data))

            DataService.editUserAccount(data)
                .then(res => {
                    alert("Editing successful");
                    window.location = "/accounts"
                })
                .catch(err => {
                    console.log(err)
                    alert("Error in editing the Account");
                })
        }
        return(
            <React.Fragment>
                <Panel />
                <Container>
                    <Row >
                        <Col  md={{ span: 10, offset: 1 }}>
                            <Card border='secondary'>
                                <Card.Header >
                                    <Card.Title>Edit User</Card.Title>
                                </Card.Header>
                                <Card.Body>
                                    <Form onSubmit={handleSubmit}>
                                        <Card border="primary">
                                            <Card.Header >User Details</Card.Header>
                                                <Card.Body>
                                                    <Form.Row>
                                                        <Form.Group as={Col} controlId="formGridFirstName">
                                                            <Form.Label>First Name</Form.Label>
                                                            <Form.Control readOnly name="firstName" defaultValue={this.state.accounts.firstName} />
                                                        </Form.Group>
                                                        <Form.Group as={Col} controlId="formGridLastName">
                                                            <Form.Label>Last Name</Form.Label>
                                                            <Form.Control readOnly name="lastName" defaultValue={this.state.accounts.lastName} />
                                                        </Form.Group>
                                                    </Form.Row>

                                                    <Form.Group controlId="formGridEmail">
                                                        <Form.Label>Email</Form.Label>
                                                        <Form.Control readOnly name="email" defaultValue={this.state.accounts.email} />
                                                    </Form.Group>

                                                    <Form.Row>
                                                        <Form.Group as={Col} controlId="formGridPassword">
                                                        <Form.Label>New Password</Form.Label>
                                                        <Form.Control onChange={this.onChange} name="password"  type="password" placeholder="Enter Password" />
                                                        </Form.Group>

                                                        <Form.Group as={Col} controlId="formGridAge">
                                                        <Form.Label>Age</Form.Label>
                                                        <Form.Control readOnly name="age" defaultValue={this.state.accounts.age} />
                                                        </Form.Group>
                                                    </Form.Row>
                                                </Card.Body>
                                        </Card>

                                        <br />
                                        <Card border='primary'>
                                            <Card.Header>Driver's License</Card.Header>
                                            <Card.Body>
                                                <Form.Group controlId="formLicenseId">
                                                <Form.Label>License ID</Form.Label>
                                                <Form.Control onChange={this.onChange} name="licenseId" defaultValue={this.state.licenses.licenseId} />
                                                </Form.Group>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formExpiry">
                                                    <Form.Label>Expiry Date</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="expiry" defaultValue={this.state.licenses.expiry} />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formType">
                                                    <Form.Label>Type</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="type" defaultValue={this.state.licenses.type} />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formStateOfIssue">
                                                    <Form.Label>State of Issue</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="stateOfIssue" defaultValue={this.state.licenses.stateOfIssue} />
                                                    </Form.Group>
                                                </Form.Row>
                                            </Card.Body>
                                        </Card>

                                        <br />
                                        <Card border='primary'>
                                            <Card.Header>Address</Card.Header>
                                            <Card.Body>
                                                <Form.Group controlId="formGridAddress1">
                                                <Form.Label>Street Name</Form.Label>
                                                <Form.Control onChange={this.onChange} name="streetName" defaultValue={this.state.addresss.streetName} />
                                                </Form.Group>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formGridCity">
                                                    <Form.Label>City</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="city" defaultValue={this.state.addresss.city} />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formGridState">
                                                    <Form.Label>State</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="state" defaultValue={this.state.addresss.state} />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formGridZip">
                                                    <Form.Label>Zip</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="zipCode" defaultValue={this.state.addresss.zipCode} />
                                                    </Form.Group>
                                                </Form.Row>
                                            </Card.Body>
                                        </Card>
                                        <br />
                                        <Card border='primary'>
                                            <Card.Header>Credit Card Information</Card.Header>
                                            <Card.Body>
                                                <Form.Group controlId="formCreditCardNumber">
                                                <Form.Label>Card Number</Form.Label>
                                                <Form.Control onChange={this.onChange} name="cardNumber" defaultValue={this.state.cards.cardNumber} />
                                                </Form.Group>
                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formIssueDate">
                                                    <Form.Label>Date of Issue</Form.Label>
                                                        <Form.Control onChange={this.onChange} name="dateOfIssue" defaultValue={this.state.cards.dateOfIssue} />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formExpiryDate">
                                                    <Form.Label>Date of Expiry</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="expiryDate" defaultValue={this.state.cards.expiryDate} />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formCVV">
                                                    <Form.Label>CVV</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="cvv" defaultValue={this.state.cards.cvv} />
                                                    </Form.Group>
                                                </Form.Row>
                                                <Form.Group controlId="formCreditCardName">
                                                <Form.Label>Name</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="name" defaultValue={this.state.cards.name} />
                                                </Form.Group>
                                            </Card.Body>
                                        </Card>
                                        <br />
                                        <Button variant="primary" type="submit">
                                            Submit
                                        </Button>
                                        </Form>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </Container>
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
                    addresss: res.data.address,
                    licenses: res.data.license,
                    cards: res.data.card
                })
            })
            .catch((error) => {
                console.log(error);
            });
    }
}


export default EditAccount;