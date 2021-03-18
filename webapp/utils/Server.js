
var URL = "http://localhost:8080/app/api/";

function getRequest(url, callback){
    $.ajax(url,{
        dataType: 'json',
        // dataType: 'text',
        method: 'GET',
        timeout: 500,
        success: function(data,status,xhr){
            if (callback) callback(data);
        },
        error: function(jqXhr, textStatus, errorMessage){
            console.log(errorMessage);
        }
    });
}

function postRequest(url, data, callback){
    $.ajax(url, {
        dataType: 'json',
        // dataType: 'text',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (data, status, xhr) {
            if (callback) callback(data);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(errorMessage);
        }

    })
}

export function getTransactionsFromUser(callback){
    var url = URL + "prescriptionRequests/getPrescriptionHistory";
    var ID = getIdentity();
    this.postRequest(url, ID, function(value){
        console.log(value);
        if (callback) callback(value);
    })

}

export function getPrescriptionsForUser(callback){
    var url = URL + "prescriptionRequests/getPrescriptionsForUser";
    var ID = getIdentity();
    console.log(ID);
    this.postRequest(url, ID,  function(value){
        console.log(value);
        if(callback) callback(value);
    });
}

export function sendPrescriptionToUser(request, callback){
    var url = URL + "prescriptionRequests/updatePrescriptionOwner";
    request.user = getIdentity();
    console.log(request);
    this.postRequest(url, request, function(value){
        console.log(value);
        if(callback) callback(value);
    });
}

export function isUser(loginDetails, callback){
    console.log(loginDetails);
    var url = URL + "userRequestsGateway/userExists";
    this.postRequest(url, loginDetails, function(value){
        console.log(value);
        if(callback) callback(value);
    });
}

export function userAuthentication(credentials, callback){
    var url = URL + "userRequestsGateway/authenticateUser";    
    console.log(credentials);
    this.postRequest(url, credentials, function(value){
        console.log(value);
        var tempvalue = {"msg" : "success"}
        if(callback) callback(value);
    });
}

export function getDetailsForUser(request, callback){
    console.log(request);
    var url = URL + "userRequestsGateway/getUser"
    this.postRequest(url, request, function(value){
        console.log(value);
        if(callback) callback(value);
    })
}

export function createPrescription(request, callback){
    console.log(request);
    request.user = getIdentity();
    console.log(request)
    var url = URL + "prescriptionRequests/createPrescription";
    this.postRequest(url, request, function(value){
        console.log(value);
        if(callback) callback(value);
    })
}

/*{
    identity: "dan"
} */
function getIdentity(){
    var user =  { identifier :  localStorage.getItem("ID"), cert : localStorage.getItem("cert")};
    return user;
}



export default {getDetailsForUser, getRequest, postRequest, getTransactionsFromUser, 
    getPrescriptionsForUser, sendPrescriptionToUser, isUser,
    userAuthentication, createPrescription}