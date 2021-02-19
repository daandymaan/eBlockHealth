
export function getRequest(url, callback){
    const HTTP = new XMLHttpRequest();
    HTTP.open("GET", url , true);
    HTTP.send();

    HTTP.onreadystatechange=function(){
        if(HTTP.readyState==4 && HTTP.status==200){
            if (callback) callback(HTTP.response);
        }
    }.bind(this);
}


export function postRequest(url, content){
    var json = JSON.stringify(content);

    const HTTP = new XMLHttpRequest();
    HTTP.open("POST", url, true);
    HTTP.setRequestHeader("Content-type", "application/json; charset=utf-8'");
    HTTP.onreadystatechange=function(){
        if(HTTP.readyState==4 && HTTP.status==200){
            console.log("Connection received");
        }
    }
    HTTP.send(json);
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

export default {getRequest, postRequest, getTransactionsFromUser, getPrescriptionsForUser}