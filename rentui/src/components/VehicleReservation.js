import React, { Component } from 'react';
import { render } from 'react-dom';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import './CalenStyle.css';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import DataService from "../DataService";
import Panel from "./common/Panel";
import Card from "react-bootstrap/Card";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';

const localizer = momentLocalizer(moment);

class VehicleReservation extends Component {
    constructor(props) {
        super(props);
        const now = new Date();

        const startTimeEvent = [
            {
                id: 1,
                title: 'Point in Time Event',
                start: now,
                end: now,
            }
        ]
        this.state = {
            name: 'React',
            events: []
        }
        this.getVehicleReservedSlots = this.getVehicleReservedSlots.bind(this)
    }

    render() {
        return (
            <React.Fragment>
                <Panel />
                <Card>
                    <Card.Body>
                        <Calendar
                            selectable={true}
                            onSelectSlot={(slotInfo) => this.onSlotChange(slotInfo)}
                            events={this.state.events}
                            startAccessor="start"
                            endAccessor="end"
                            defaultDate={moment().toDate()}
                            localizer={localizer}
                            defaultView='week'
                            defaultDate={new Date()}
                            step={15}
                            timeslots={4}
                            length={1}
                            toolbar={true}

                        />
                    </Card.Body>
                </Card>
                <a href="/home">Home</a>
            </React.Fragment>
        );
    }

    onSlotChange(slotInfo) {
        debugger
        console.log(this.props)
        if (slotInfo.slots.length % 4 === 1) {
            var startDate = slotInfo.start.getTime()
            if (startDate > Date.now()) {
                var endDate = slotInfo.end.getTime()

                // var startDate = moment(slotInfo.start.toLocaleString()).format("YYYY-MM-DD HH:mm:ss");
                // var endDate = moment(slotInfo.end.toLocaleString()).format("YYYY-MM-DD HH:mm:ss");
                console.log('startTime', startDate); //shows the start time chosen
                console.log('endTime', endDate); //shows the end time chosen
                this.submit(slotInfo)
            } else {
                alert("Please select slots after the current time")
            }
        } else {
            alert("Please pick slots on hour basis")
        }
    }

    submit(slotInfo)  {
        var startDate = slotInfo.start.getTime()
        var endDate = slotInfo.end.getTime()
        var startDateString = moment(slotInfo.start.toLocaleString()).format("YYYY-MM-DD HH:mm");
        var endDateString = moment(slotInfo.end.toLocaleString()).format("YYYY-MM-DD HH:mm");
        confirmAlert({
            // var startDate = moment(slotInfo.start.toLocaleString()).format("YYYY-MM-DD HH:mm:ss");
            // var endDate = moment(slotInfo.end.toLocaleString()).format("YYYY-MM-DD HH:mm:ss");
            title: 'Reserve it',
            message: startDateString + ' -- ' + endDateString,
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => {
                        var data = {}
                        data.vehicleId = this.props.match.params.vehicleId
                        data.fromTime = startDate
                        data.duration = (endDate - startDate) / (1000 * 60 * 60) // converting to hours
                        DataService.makeReservation(data)
                            .then(res => {
                                var reservation = {}
                                reservation.id = res.data.reservationId
                                reservation.estimatedCharges = res.data.estimatedCharges
                                alert(JSON.stringify(reservation))
                                this.getVehicleReservedSlots()
                            })
                            .catch(error => {
                                console.error(error.message);
                                alert("Reservation failed, please retry.")
                            })
                    }
                },
                {
                    label: 'No',
                    onClick: () => ""
                }
            ]
        });
    };

    componentDidMount() {
        this.getVehicleReservedSlots();
    }

    getVehicleReservedSlots() {
        console.log(this.props)
        return DataService.getVehicleReservedSlots(this.props.match.params.vehicleId)
            .then((res) => {
                debugger
                var reservationEvents = []
                for (const [index, value] of res.data.entries()) {
                    var temp = {
                        id: index,
                        title: 'Reserved',
                        start: new Date(value[0]),
                        end:  new Date(value[1])
                    }
                    reservationEvents.push(temp);
                }
                this.setState({
                    events: reservationEvents
                })
            })
            .catch((error) => {
                this.setState({ error });
            });
    }
}
export default VehicleReservation;