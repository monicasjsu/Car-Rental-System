import React, { Component } from 'react'
import Panel from './common/Panel'
import SearchBy from '../components/SearchBy'
import SearchResults from '../components/SearchResults'
import Cookies from 'js-cookie'

class Home extends Component{

    constructor(props) {
        super(props)
        //check for authentication - if null, redirect to login
        var authToken = Cookies.get("token")

        if ( authToken === null || authToken === "" || authToken === undefined){
            window.location = "/login"
        }
    }

    render(){
        return(
            <React.Fragment>
                <Panel />
                <SearchBy />
            </React.Fragment>
        )
    }
}

export default Home;
