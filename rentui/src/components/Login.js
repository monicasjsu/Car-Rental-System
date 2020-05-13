import React, { Component } from 'react'
import Form from 'react-bootstrap/Form'
import  Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card, { CardHeader, CardTitle } from 'react-bootstrap/Card'
import axios from 'axios';
import Cookies from 'js-cookie'


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            password: "",
            email: "",
            isAdmin: false
        }
        this.handleAdminChange = this.handleAdminChange.bind(this);

    }
    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      }
    
      handleAdminChange(event) {
        const target = event.target;
        this.setState({
            isAdmin: target.checked
        });
      }
      
    render() {

        const handleSubmit = (event) => {
            event.preventDefault();
            
            const login = {
                password: this.state.password,
                email: this.state.email,
              }
            var url = this.state.isAdmin ? 'http://localhost:8080/admin/login' : 'http://localhost:8080/login';
            // var url = this.state.isAdmin ? 'https://geethupadachery.com:8080/admin/login' : 'https://geethupadachery.com:8080/login';
              axios.post(url, {
                "email": this.state.email,
                "password": this.state.password

               }
              
               )
                    .then(res=>{
                        console.log(res);
                        console.log(res.data);
                        
                        Cookies.set("token", res.data)
                        Cookies.set("isAdmin", this.state.isAdmin)
                        axios.defaults.headers.common['Authorization'] = "Bearer " + res.data;

                        window.location = "/home" 
                    })
                    .catch(error => {
                       alert("Login failed")
                        
                    });
  
        }

        return (
            <Container>
                <Row >
                    <Col  md={{ span: 6, offset: 3 }}>
                        <Card>
                            <Card.Header>
                                GOF Rent-A-Car
                            </Card.Header>
                            <Card.Body>
                                <Card.Text><a href="/SignUp">New User Sign Up</a><br />
                                <a href="/SignUpAdmin">New Admin Sign Up</a></Card.Text>
                                <Form onSubmit={handleSubmit}> 
                                    <Form.Group controlId="formBasicEmail">
                                        <Form.Label>Email address</Form.Label>
                                        <Form.Control name="email"  onChange={this.onChange} type="email" placeholder="Enter email" />
                                        <Form.Text className="text-muted">
                                        Please enter the email address used to create account
                                        </Form.Text>
                                    </Form.Group>

                                    <Form.Group controlId="formBasicPassword">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control type="password"  name="password" onChange={this.onChange} placeholder="Password" />
                                    </Form.Group>


                                    {['checkbox'].map((type) => (
                                        <div key={this.state.isAdmin} className="mb-3">
                                        <Form.Check 
                                            type={type}
                                            id={"idAdminCheck"}
                                            label={"Login as admin"}
                                            checked={this.state.isAdmin}
                                            onChange={this.handleAdminChange} 
                                        />
                                        </div>
                                    ))}


                                    <Button variant="primary" type="submit">
                                        Submit
                                    </Button>
                                </Form>
                            </Card.Body>
                        </Card>
                        
                    </Col>
                </Row>
                
            </Container>
        )
    }
}


export default Login;