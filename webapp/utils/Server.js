

function getRequest(url){
    const HTTP = new XMLHttpRequest();
    HTTP.open("GET", url, true);
    HTTP.send();

    HTTP.onreadystatechange=function(){
        if(this.readyState==4 && this.status==200){
            return HTTP.response
        }
    }.bind(this);
}

function postRequest(url, content){
    var json = JSON.stringify(content);

    const HTTP = new XMLHttpRequest();
    HTTP.open("POST", url, true);
    HTTP.setRequestHeader("Content-type", "application/json; charset=utf-8'");
    HTTP.onreadystatechange=function(){
        if(HTTP.readyState==4 && HTTP.status==200){
            console.log("Connection received");
        }
    }.bind(this);

}