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


class SignUpAdmin extends Component{
    constructor(props) {
        super(props);
        this.state = {
            password: "",
            email: "",
            ReEnterPassword: "",
            firstName: "",
            lastName: "",
        }
    }
    
    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      }


    render(){
      
        const handleSubmit = (event) => {
            debugger
            event.preventDefault();
                var posturl = 'http://localhost:8080/admin/register';
                // var posturl = 'https://geethupadachery.com:8080/register';

              axios.post(posturl, {
                    "firstName": this.state.firstName,
                    "lastName": this.state.lastName,
                    "password": this.state.password,
                    "email": this.state.email
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
                                    <Card.Title>Register New Admin</Card.Title>
                                </Card.Header>
                                <Card.Body>
                                    <Card.Text>
                                        <a href="/Login">Existing Admin? Sign In here</a>
                                    </Card.Text>
                                    <Form onSubmit={handleSubmit}>
                                        <Card border="primary">
                                            <Card.Header>Admin Details</Card.Header>
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
                                                    </Form.Row>
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


export default SignUpAdmin;