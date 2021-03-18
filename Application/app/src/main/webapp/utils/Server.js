
var URL = "http://localhost:8080/app/api/";

function getRequest(url, callback){
    console.log("URL:", url);
    $.ajax(url,{
        dataType: 'json',
        // dataType: 'text',
        method: 'GET',
        success: function(data,status,xhr){
            console.log("response:", data);
            if (callback) callback(data);
        },
        error: function(jqXhr, textStatus, errorMessage){
            console.log(errorMessage);
        }
    });
}

function postRequest(url, data, callback){
    console.log("URL:", url);
    console.log("request:", data);
    $.ajax(url, {
        dataType: 'json',
        // dataType: 'text',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (data, status, xhr) {
            console.log("response:", data);
            if (callback) callback(data);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(errorMessage);
        }

    })
}

export function getTransactionsFromUser(callback){
    var url = URL + "prescriptionRequests/getPrescriptionHistory";
    var self = this;
    this.verifySession(function(value){
        var request = {"user" : ""};
        value.msg = "";
        request.user = value;
        self.postRequest(url, request, function(response){
            if(callback) callback(response);
        });
    });
}

export function getPrescriptionsForUser(callback){
    var url = URL + "prescriptionRequests/getPrescriptionsForUser";
    var self = this;
    this.verifySession(function(value){
        var request = {"user" : ""};
        value.msg = "";
        request.user = value;
        self.postRequest(url, request, function(response){
            if(callback) callback(response);
        });
    });
}

export function sendPrescriptionToUser(request, callback){
    var url = URL + "prescriptionRequests/updatePrescriptionOwner";
    var self = this;
    this.verifySession(function(value){
        value.msg = "";
        request.user = value;
        self.postRequest(url, request, function(response){
            if(callback) callback(response);
        });
    });
}

export function isUser(loginDetails, callback){
    var url = URL + "userRequestsGateway/userExists";
    this.postRequest(url, loginDetails, function(value){
        if(callback) callback(value);
    });
}

export function userAuthentication(credentials, callback){
    var url = "/app/userRequestsGateway/authenticateUser";
    this.postRequest(url, credentials, function(value){
        var tempvalue = {"msg" : "success"}
        if(callback) callback(value);
    });
}

export function getDetailsForUser(callback){
    var url = URL + "userRequestsGateway/getUser"
    var self = this;
    this.verifySession(function(value){
        var request = {"user" : "", "identifier" : ""};
        value.msg = "";
        request.user = value;
        request.identifier = value.identifier;
        self.postRequest(url, request, function(response){
            if(callback) callback(response);
        });
    });
}

export function createPrescription(request, callback){
    var url = URL + "prescriptionRequests/createPrescription";
    var self = this;
    this.verifySession(function(value){
        value.msg = "";
        request.user = value;
        self.postRequest(url, request, function(response){
            if(callback) callback(response);
        });
    });
}

export function verifySession(callback){
    var url = "/app/userRequestsGateway/verifySession";
    this.getRequest(url, function(value){
        if(callback) callback(value);
    });
}

export function endSession(callback){
    var url = "/app/userRequestsGateway/logout";
    this.getRequest(url, function(value){
        if (callback) callback(value);
    })
}

export default {getDetailsForUser, getRequest, postRequest, getTransactionsFromUser, 
    getPrescriptionsForUser, sendPrescriptionToUser, isUser,
    userAuthentication, createPrescription, verifySession, endSession}