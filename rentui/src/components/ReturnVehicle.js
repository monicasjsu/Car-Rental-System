import React, { Component } from 'react'
import Form from 'react-bootstrap/Form'
import  Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card, { CardHeader, CardTitle } from 'react-bootstrap/Card'
import axios from 'axios';
import Cookies from 'js-cookie'
import Panel from './common/Panel'
import DataService from '../DataService'


class ReturnVehicle extends Component {
    constructor(props) {
        debugger
        super(props);
        this.state = {
           feedback: "",
           vehicleCondition: "",
           reservationId: this.props.match.params.resId
        }
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      }

    render() {

        const handleSubmit = (event) => {
            event.preventDefault();

            const data = {
                feedback: this.state.feedback,
                vehicleCondition: this.state.vehicleCondition,
                reservationId: this.state.reservationId
              }
              
              DataService.returnVehicle(data)
              .then(res=>{
                        alert("return successful")
                        window.location = "/reservations" 
                    })
                    .catch(error => {
                       alert("return vehicle failed")
                    });
  
        }

        return (
            <React.Fragment>
            <Panel />
                <Row >
                    
                    <Col  md={{ span: 6, offset: 3 }}>
                        <Card>
                            <Card.Header>
                               
                            </Card.Header>
                            <Card.Body>
                                <Form onSubmit={handleSubmit}> 
                                    <Form.Group controlId="formBasicFeedback">
                                        <Form.Label>Feedback</Form.Label>
                                        <Form.Control name="feedback"  onChange={this.onChange} type="text" placeholder="enter text here" />
                                        <Form.Text className="text-muted">
                                        Please share your experience 
                                        </Form.Text>
                                    </Form.Group>
                                    <Form.Group controlId="formBasicCondition">
                                        <Form.Label>Vehicle Condition</Form.Label>
                                        <Form.Control name="vehicleCondition"  onChange={this.onChange} type="text" placeholder="enter text here" />
                                        <Form.Text className="text-muted">

                                        </Form.Text>
                                    </Form.Group>



                                    <Button variant="primary" type="submit">
                                        Return Vehicle
                                    </Button>
                                </Form>
                            </Card.Body>
                        </Card>
                        
                    </Col>
                </Row>
                </React.Fragment>
        )
    }
}


export default ReturnVehicle;