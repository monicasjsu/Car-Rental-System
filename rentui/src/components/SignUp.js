import React, { Component } from 'react'
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie'


class SignUp extends Component{
    constructor(props) {
        super(props);
        this.state = {
            password: "",
            email: "",
            ReEnterPassword: "",
            firstName: "",
            lastName: "",
            age: "",
            address: "",
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
            zip: "",
            stateAdd: ""

        }
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
                var posturl = 'http://localhost:8080/register';

              axios.post(posturl, {
                    "firstName": this.state.firstName,
                    "lastName": this.state.lastName,
                    "password": this.state.password,
                    "email": this.state.email,
                    "age": parseInt(this.state.age),
                    "address": {
                        "streetName": this.state.address,
                        "city": this.state.city,
                        "state": this.state.stateAdd,
                        "zipCode": this.state.zip
                    },
                    "license": {
                        "licenseId": this.state.licenseId,
                        "expiry":  new Date(this.state.expiry),
                        "type": this.state.type,
                        "stateOfIssue": this.state.stateOfIssue
                    },
                    "card": {
                        "cardNumber": this.state.cardNumber,
                        "dateOfIssue":  new Date(this.state.dateOfIssue),
                        "expiryDate":  new Date(this.state.expiryDate),
                        "name": this.state.name,
                        "cvv": this.state.cvv
                    }
               })
                    .then(res=>{
                        window.location = "/login"
                    })
  
        }

        return(
            <React.Fragment>
                 <Container>
                    <Row >
                        <Col  md={{ span: 6, offset: 3 }}>
                            <Card border='secondary'>
                                <Card.Header >
                                    <Card.Title>Register New User</Card.Title>
                                </Card.Header>
                                <Card.Body>
                                    <Card.Text>
                                        <a href="/Login">Existing User? Sign In here</a>
                                    </Card.Text>
                                    <Form onSubmit={handleSubmit}>
                                        <Card border="primary">
                                            <Card.Header>User Details</Card.Header>
                                                <Card.Body>
                                                    <Form.Row>
                                                        <Form.Group as={Col} controlId="formGridFirstName">
                                                            <Form.Label>First Name</Form.Label>
                                                            <Form.Control onChange={this.onChange} name="firstName" required placeholder="First Name" />
                                                            <Form.Control.Feedback type="invalid">
                                                                required
                                                            </Form.Control.Feedback>
                                                        </Form.Group>
                                                        <Form.Group as={Col} controlId="formGridLastName">
                                                            <Form.Label>Last Name</Form.Label>
                                                            <Form.Control onChange={this.onChange} name="lastName" required placeholder="Last Name" />
                                                        </Form.Group>
                                                    </Form.Row>
                                                    

                                                    <Form.Group controlId="formGridEmail">
                                                        <Form.Label>Email</Form.Label>
                                                        <Form.Control onChange={this.onChange} name="email" required type="email" placeholder="email ID" />
                                                    </Form.Group>

                                                    <Form.Row>
                                                        <Form.Group as={Col} controlId="formGridPassword">
                                                        <Form.Label>Password</Form.Label>
                                                        <Form.Control onChange={this.onChange} name="password" required type="password" placeholder="Enter Password" />
                                                        </Form.Group>

                                                        <Form.Group as={Col} controlId="formGridAge">
                                                        <Form.Label>Age</Form.Label>
                                                        <Form.Control onChange={this.onChange} name="age" required placeholder="Age" />
                                                        </Form.Group>
                                                    </Form.Row>
                                                </Card.Body>
                                        </Card>

                                        
                                        <Card border='primary'>
                                            <Card.Header>Driver's License</Card.Header>
                                            <Card.Body>
                                                <Form.Group controlId="formLicenseId">
                                                <Form.Label>License ID</Form.Label>
                                                <Form.Control onChange={this.onChange} name="licenseId" required />
                                                </Form.Group>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formExpiry">
                                                    <Form.Label>Expiry Date</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="expiry" required />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formType">
                                                    <Form.Label>Type</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="type" required />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formStateOfIssue">
                                                    <Form.Label>State of Issue</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="stateOfIssue" required />
                                                    </Form.Group>
                                                </Form.Row>
                                            </Card.Body>
                                        </Card>

                                    
                                        <Card border='primary'>
                                            <Card.Header>Address</Card.Header>
                                            <Card.Body>
                                                <Form.Group controlId="formGridAddress1">
                                                <Form.Label>Address</Form.Label>
                                                <Form.Control onChange={this.onChange} name="address" required placeholder="1234 Main St" />
                                                </Form.Group>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formGridCity">
                                                    <Form.Label>City</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="city" required />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formGridState">
                                                    <Form.Label>State</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="stateAdd" required placeholder="CA" />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formGridZip">
                                                    <Form.Label>Zip</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="zip" required />
                                                    </Form.Group>
                                                </Form.Row>
                                            </Card.Body>
                                        </Card>

                                       

                                        <Card border='primary'>
                                            <Card.Header>Credit Card Information</Card.Header>
                                            <Card.Body>
                                                <Form.Group controlId="formCreditCardNumber">
                                                <Form.Label>Card Number</Form.Label>
                                                <Form.Control onChange={this.onChange} name="cardNumber" required/>
                                                </Form.Group>
                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formIssueDate">
                                                    <Form.Label>Date of Issue</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="dateOfIssue" required />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formExpiryDate">
                                                    <Form.Label>Date of Expiry</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="expiryDate" required />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formCVV">
                                                    <Form.Label>CVV</Form.Label>
                                                    <Form.Control onChange={this.onChange} name="cvv" required />
                                                    </Form.Group>
                                                </Form.Row>
                                                <Form.Group controlId="formCreditCardName">
                                                <Form.Label>Name</Form.Label>
                                                <Form.Control onChange={this.onChange} name="name" required/>
                                                </Form.Group>
                                            </Card.Body>
                                        </Card>

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
}


export default SignUp;