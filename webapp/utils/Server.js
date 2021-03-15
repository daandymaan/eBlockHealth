
// export function getRequest(url, callback){
//     const HTTP = new XMLHttpRequest();
//     HTTP.open("GET", url , true);
//     HTTP.send();

//     HTTP.onreadystatechange=function(){
//         if(HTTP.readyState==4 && HTTP.status==200){
//             if (callback) callback(HTTP.response);
//         }
//     }.bind(this);
// }

// export function postRequest(url, content){
//     var json = JSON.stringify(content);

//     const HTTP = new XMLHttpRequest();
//     HTTP.open("POST", url, true);
//     HTTP.setRequestHeader("Content-type", "application/json; charset=utf-8'");
//     HTTP.onreadystatechange=function(){
//         if(HTTP.readyState==4 && HTTP.status==200){
//             console.log("Connection received");
//         }
//     }
//     HTTP.send(json);
// }

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

export function getTransactionsFromUser(ID, callback){
    console.log("getTransactionsFromUser");
    var url = "http://localhost:8080/app/api/test/getJSON"
    var url2 = "" + ID;
    this.getRequest(url, function(value){
        if(callback) callback(value);
    });
}

export function getPrescriptionsForUser(ID, callback){
    var url = "" + ID;
    url = "http://localhost:8080/app/api/test/getJSON"
    this.getRequest(url, function(value){
        if(callback) callback(value);
    });
}

export function sendPrescriptionToUser(data, callback){
    var url = "";
    this.postRequest(url, data, function(value){
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
        if(callback) callback(tempvalue);
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



export default {getDetailsForUser, getRequest, postRequest, getTransactionsFromUser, 
    getPrescriptionsForUser, sendPrescriptionToUser, isUser,
    userAuthentication}