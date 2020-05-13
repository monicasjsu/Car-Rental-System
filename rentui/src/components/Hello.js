import DataService from "../DataService.js";
import React, { Component, Fragment } from 'react'
import Panel from './common/Panel'
// import {Datatable} from "@o2xp/react-datatable";


class Hello extends Component {

    constructor(props) {
        super(props);
        this.reloadUserList = this.reloadUserList.bind(this);
        this.state = {
            data: "Hi"
        }
    }


    render(){
        return(
            <Fragment>
                <Panel />
                <div> {this.state.data} </div>
            </Fragment>
        )
    }
    componentDidMount() {
        this.reloadUserList();
    }

    reloadUserList() {
        DataService.fetchHello()
            .then((res) => {
                this.setState({data: res.data})
            });
    }

}

export default Hello;