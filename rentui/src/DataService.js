import axios from 'axios';
import Cookies from "js-cookie";

// const API_BASE_URL_USER = 'https://geethupadachery.com:8080/api/user';
// const API_BASE_URL_ADMIN = 'https://geethupadachery.com:8080/api/admin';

const API_BASE_URL_USER = 'http://localhost:8080/api/user';
const API_BASE_URL_ADMIN = 'http://localhost:8080/api/admin';


class DataService {

    requestHeader = {
        'Authorization' : 'Bearer ' + Cookies.get("token"),
        'Content-Type' : "application/json"
    }

    fetchVehicles() {
        var url = API_BASE_URL_USER + "/" + "getAllVehicle"
        return axios.get(url, {headers: this.requestHeader});
    }

    fetchVehicleTypes() {
        console.log(Cookies.get("token"));
        var url = API_BASE_URL_USER + "/" + "getAllVehicleType"
        return axios.get(url, {headers: this.requestHeader});

    }

    fetchLocations(){
        var url = API_BASE_URL_USER + "/" + "getAllRentalLocation"
        return axios.get(url, {headers: this.requestHeader});
    }

    //Fix this
    fetchUsers(){
        var url = API_BASE_URL_ADMIN + "/" + "getAllUsers"
        return axios.get(url, {headers: this.requestHeader});
    }

    //fix this
    fetchAccounts(){
        var url = API_BASE_URL_USER + "/" + "getAllRentalLocation"
        return axios.get(url, {headers: this.requestHeader});
    }

    fetchHello() {
        //return axios.get('http://geethupadachery.com:8080/api/hello', {headers: this.requestHeader});
    }

    fetchSearchResults(location, vehicleType){
        debugger
        if ((location === 0 ||  isNaN(location) ) && (vehicleType === 0 || isNaN(vehicleType))) {
            var url = API_BASE_URL_USER + "/" + "getVehicles";
        } else if (location === 0 ||  isNaN(location)) {
            var url = API_BASE_URL_USER + "/" + "getVehicles?vehicleType=" + vehicleType
        } else if (vehicleType === 0 ||  isNaN(vehicleType)) {
            var url = API_BASE_URL_USER + "/" + "getVehicles?location=" + location
        }
        else{
            var url = API_BASE_URL_USER + "/" + "getVehicles?location=" + location + "&vehicleType=" + vehicleType;
        }
        return axios.get(url, {headers: this.requestHeader});
    }

    fetchReservationsByUser(){
        var url = API_BASE_URL_USER + "/" + "getReservations"
        return axios.get(url, {headers: this.requestHeader});
    }

    addNewLocation(newLocationData){
        var url = API_BASE_URL_ADMIN + "/saveRentalLocation"
        return axios.post(url, {
            "locationName": newLocationData.locationName,
            "address": {
                "streetName": newLocationData.streetName,
                "city": newLocationData.city,
                "state": newLocationData.state,
                "zipCode": newLocationData.zipCode
            },
            "vehicleCapacity": newLocationData.vehicleCapacity
        }, {headers: this.requestHeader})
    }

    editLocation(oldLocationData, newLocationData) {
        var url = API_BASE_URL_ADMIN + "/editRentalLocation"
        return axios.put(url, {
            "rentalLocationId": oldLocationData.rentalLocationId,
            "locationName": newLocationData.locationName,
            "address": {
                "streetName": newLocationData.streetName,
                "city": newLocationData.city,
                "state": newLocationData.state,
                "zipCode": newLocationData.zipCode
            },
            "vehicleCapacity": newLocationData.vehicleCapacity
        }, {headers: this.requestHeader})
    }

    removeLocation(locationId) {
        var url = API_BASE_URL_ADMIN + "/deleteRentalLocation?rentalLocationId=" + locationId
        return axios.delete(url, {headers: this.requestHeader})
    }

    addNewVehicle(newVehicleData){
        
        var url = API_BASE_URL_ADMIN + "/saveVehicle"
        return axios.post(url, {
            "vehicleId": 0,
                        "type": {
                        "vehicleTypeId": newVehicleData.vehicleTypeId,
                        "vehicleType": newVehicleData.type.vehicleType,
                        "hourlyPrice": newVehicleData.hourlyPrice,
                        "lateReturnFee": newVehicleData.lateReturnFee
                },            
            "make": newVehicleData.make,
            "model": newVehicleData.model,
            "year": newVehicleData.year,
            "registrationTag": newVehicleData.registrationTag,
            "currentMileage": newVehicleData.currentMileage,
            "vehicleCondition": newVehicleData.vehicleCondition,
            "rentalLocationId": newVehicleData.rentalLocationId
        }, {headers: this.requestHeader})
    }

    editVehicle(oldVehicleData, newVehicleData) {
        
        var url = API_BASE_URL_ADMIN + "/editVehicle"
        return axios.put(url, {
            "vehicleId": oldVehicleData.vehicleId,
            // "vehicleTypeId": oldVehicleData.vehicleTypeId,
            "type": {
                "vehicleType": newVehicleData.type.vehicleType,
            },
            "model": newVehicleData.model,
            "make": newVehicleData.make,
            "year": newVehicleData.year,
            "registrationTag": newVehicleData.registrationTag,
            "currentMileage": newVehicleData.currentMileage,
            "vehicleCondition": newVehicleData.vehicleCondition,
            "rentalLocationId": newVehicleData.rentalLocationId,
        }, {headers: this.requestHeader})
    }

    removeVehicle(vehicleId) {
        
        var url = API_BASE_URL_ADMIN + "/deleteVehicle?vehicleId=" + vehicleId
        return axios.delete(url, {headers: this.requestHeader})
    }

    cancelReservations(id) {
        var url = API_BASE_URL_USER + "/" + "cancelReservation?id=" + id
        return axios.get(url, {headers: this.requestHeader});
    }

    terminateUser(userId) {
        var url = API_BASE_URL_ADMIN + "/" + "terminateUser?email=" + userId
        return axios.get(url, {headers: this.requestHeader});
    }

    approveUser(userId) {
        var url = API_BASE_URL_ADMIN + "/" + "approve?email=" + userId
        return axios.get(url, {headers: this.requestHeader});
    }


    deactivateAccount() {
        var url = API_BASE_URL_USER + "/" + "deactivate"
        return axios.get(url, {headers: this.requestHeader});
    }

    getUser() {
        var url = API_BASE_URL_USER + "/" + "getUser"
        return axios.get(url, {headers: this.requestHeader});
    }

    getAvailability(vehicleId){
        var url = API_BASE_URL_USER + "/" + "checkAvailability?vehicleId=" + vehicleId
        return axios.get(url, {headers: this.requestHeader});
    }

    getVehicleReservedSlots(vehicleId){
        var url = API_BASE_URL_USER + "/" + "getReservedSlots?vehicleId=" + vehicleId
        return axios.get(url, {headers: this.requestHeader});
    }

    makeReservation(data) {
        var url = API_BASE_URL_USER + "/reserveVehicle"
        return axios.post(url, {
            "vehicleId": data.vehicleId,
            "fromTime": data.fromTime,
            "durationHours": data.duration
        }, {headers: this.requestHeader})
    }

    //vehicle type calls
    addNewVehicleType(newVehicleData){
        var url = API_BASE_URL_ADMIN + "/saveVehicleType"
        return axios.post(url, {
            "vehicleType": newVehicleData.vehicleType,
            "hourlyPrice": newVehicleData.hourlyPrice,
            "hourly6To12HoursPrice": newVehicleData.hourly6To12HoursPrice,
            "hourlyMoreThan12HoursPrice": newVehicleData.hourlyMoreThan12HoursPrice,
            "lateReturnFee": newVehicleData.lateReturnFee,
            
        }, {headers: this.requestHeader})
    }

    editVehicleType(oldVehicleData, newVehicleData) {
        var url = API_BASE_URL_ADMIN + "/editVehicleType"
        return axios.put(url, {
            "vehicleTypeId": oldVehicleData.vehicleTypeId,
            "vehicleType": newVehicleData.vehicleType,
            "hourlyPrice": newVehicleData.hourlyPrice,
            "hourly6To12HoursPrice": newVehicleData.hourly6To12HoursPrice,
            "hourlyMoreThan12HoursPrice": newVehicleData.hourlyMoreThan12HoursPrice,
            "lateReturnFee": newVehicleData.lateReturnFee,
        }, {headers: this.requestHeader})
    }

    removeVehicleType(vehicleTypeId) {
        var url = API_BASE_URL_ADMIN + "/deleteVehicleType?vehicleTypeId=" + vehicleTypeId
        return axios.delete(url, {headers: this.requestHeader})
    }

    editUserAccount(data) {
        var url = API_BASE_URL_USER + "/modify"
        return axios.post(url, data, {headers: this.requestHeader})
    }


    returnVehicle(data){
        var url = API_BASE_URL_USER + "/returnVehicle?reservationId=" + data.reservationId;
        return axios.post(url, {
            "feedback": data.feedback,
            "vehicleCondition": data.vehicleCondition
        }, {headers: this.requestHeader})
    }
}

export default new DataService();