import React, { Component } from "react";
import { Redirect } from 'react-router-dom';
import { Button } from 'react-bootstrap/Button'
import Cookies from "js-cookie";


class Logout extends Component{
    state = {
        navigate: false
    }

    logout = () => {
        //clear token cookie
        Cookies.remove("token")
    }


}