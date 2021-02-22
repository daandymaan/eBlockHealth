
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

function getRequest(url, callback){
    $.ajax(url,{
        //dataType: 'json,
        dataType: 'text',
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
        //dataType: 'json,
        dataType: 'text',
        method: 'POST',
        data: data,
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
    var url = ""+ loginDetails.PPSN + "/" + loginDetails.DOB;
    var tempvalue = "success";
    // this.getRequest(url, function(value){
    //     if(callback) callback(value);
    if(callback) callback(tempvalue);
    // }); 
}

export function userAuthentication(credentials, callback){
    var url = "";
    var tempvalue = "success";
    // if(credentials.passcode == "333"){
    //     tempvalue = "success"
    // }
    // this.postRequest(url, credentials, function(value){
    //     if(callback) callback(value);
    // });
    callback(tempvalue);
}

export function getDetailsForUser(ID, callback){
    var url = ""+ "/" + ID;
    var details = {"PPSN":"3324784V", "passcode":"7q0z1em9", "DOB" : "01-01-1999", "status" : "C", "address" : "-----BEGIN CERTIFICATE-----\nMIICKTCCAdCgAwIBAgIUVPmv8E5VloDgOsASnNmp5uz0LlkwCgYIKoZIzj0EAwIw\ncDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQH\nEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2Nh\nLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAw\nWjAhMQ8wDQYDVQQLEwZjbGllbnQxDjAMBgNVBAMTBWFkbWluMFkwEwYHKoZIzj0C\nAQYIKoZIzj0DAQcDQgAEFtvm0W4bgpS8+M2woagYbCEAfytE24JqR+kggeaBMFdX\nXSSZ4u7Ng3xaCXqG3twCPcwX/GIjSfBSq6aVQ6KD0qOBljCBkzAOBgNVHQ8BAf8E\nBAMCA6gwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwGA1UdEwEB/wQC\nMAAwHQYDVR0OBBYEFCHs0/qQ2+Q+62TA8U3dQV9otHoLMB8GA1UdIwQYMBaAFCb9\nbhmqXrpO4lcIRw71vuwucn8KMBQGA1UdEQQNMAuCCWxvY2FsaG9zdDAKBggqhkjO\nPQQDAgNHADBEAiBtAXWz59nOkKd/Vy1gkdZ5AkYVChiK3Q8eSOZGT6dBaAIgd6eC\ni8IQFtm1J0kGaECHtmpTZMRDjaYlHdHbRY4FONs=\n-----END CERTIFICATE-----\n", "firstname" : "Daniel", "surname": "simons"};
    // this.getRequest(url, function(value){
    //     if(callback) callback(value);
    // })
    if(callback) callback(details);
}



export default {getDetailsForUser, getRequest, postRequest, getTransactionsFromUser, 
    getPrescriptionsForUser, sendPrescriptionToUser, isUser,
    userAuthentication}