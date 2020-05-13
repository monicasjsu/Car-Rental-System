import React, { Component } from 'react'
import DataService from "../DataService.js";
import MaterialTable from 'material-table';
import Card from 'react-bootstrap/Card';
import { Table, Button, Alert } from 'react-bootstrap';
import Panel from './common/Panel'
import axios from 'axios';
import Cookies from "js-cookie";

class ManageUser extends Component {
    constructor(props) {
        super(props);
        //check for authentication - if null, redirect to login
        var authToken = Cookies.get("token")

        if ( authToken === null || authToken === "" || authToken === undefined){
            window.location = "/login"
        }

        this.reloadUsers = this.reloadUsers.bind(this);
        this.terminateUser = this.terminateUser.bind(this);
        this.approveUser = this.approveUser.bind(this);

        this.state = {
            results: [],
            error: null,
            response: {

            }
        }
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
                    <Panel />
                    <Card>
                        <Card.Header> User Management
                            {this.state.response.message && <Alert variant="info">{this.state.response.message}</Alert>}

                        </Card.Header>
                        <Card.Body>
                            <Table>
                                <thead>
                                    <tr>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Email</th>
                                        <th>Active</th>
                                        <th>Approve</th>
                                        <th>Terminate</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {results.map(result => (
                                        <tr key={result.email}>
                                            <td>{result.firstName}</td>
                                            <td>{result.lastName}</td>
                                            <td>{result.email}</td>
                                            <td>{(result.isActive) ? <span>&#9989;</span> : <span>&#10060;</span>}</td>

                                            <td>
                                                <Button variant="info" onClick={() => this.approveUser(result.email)}>Approve User</Button>
                                            </td>
                                            <td>
                                                <Button variant="info" onClick={() => this.terminateUser(result.email)}>Terminate User</Button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>

                        </Card.Body>
                    </Card>
                </React.Fragment>
            )

        }
        
    }

    componentDidMount() {
        this.reloadUsers();
    }

    reloadUsers() {
        DataService.fetchUsers()
            .then((res) => {
                this.setState({
                    results: res.data
                })
            })
            .catch((error) => {
                this.setState({ error });
            }); 
    }

    terminateUser(userId) {
        DataService.terminateUser(userId)
        .then((res)=>{
            debugger
            console.log(res);
            console.log(res.data);
            alert("User terminated")
            window.location = "/manageUsers"
        })
        .catch(error => {
            alert("User termination failed: ", error.response)
         });
    }

    approveUser(userId) {
        DataService.approveUser(userId)
        .then((res) => {
            debugger
            console.log(res);
            console.log(res.data);
            alert("User Approved")
            window.location = "/manageUsers"
        })
        .catch(error => {
            alert("User Approval failed: ", error.response)
        });
    }

}

export default ManageUser;