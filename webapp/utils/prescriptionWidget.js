import Server from './Server.js'
Server.getPrescriptionsForUser(sessionStorage.getItem("ID"), function(value){

    var tempdata = [{"PID": "0385736289", "date":"11/02/2021", "issuer": "GP", "owner": "Me", "product":"LEXAPRO", "productID":"G551",
                    "productPackage":"", "quantity":"20", "doseStrength":"10MG",
                    "doseType":"TABS", "doseQuantity":"1perday"},
                
                    {"PID": "0020555562", "date":"11/02/2021", "issuer": "GP", "owner": "Me", "product":"CHEESESAUCE", "productID":"G5333",
                    "productPackage":"", "quantity":"20", "doseStrength":"10Slices",
                    "doseType":"TABS", "doseQuantity":"1perday"}];
    var data = tempdata;
    
    var widgets = [];

    tempdata.forEach(function(widgetData){
        widgets.push(widgetCreation(widgetData));
    })
})


function widgetCreation(data){

    var widgetDiv = document.createElement("div");
    widgetDiv.className += "card";
    var PIDH1 = document.createElement("h1");
    PIDH1.className += "card-header card-title"
    var DATEH2 = document.createElement("h2");
    DATEH2.className += "card-text";
    var PRODUCTH3 = document.createElement("h3");
    PRODUCTH3.className += "card-text";

    // Buttons
    var openButton = document.createElement("button");
    openButton.className += "btn btn-outline-info"
    openButton.onclick = function(){
        viewPrescriptionModal(data);
    };
    openButton.type = "button";
    openButton.setAttribute("data-toggle", "modal");
    openButton.setAttribute("data-target", "#modalView");

    var sendButton = document.createElement("button");
    sendButton.className += "btn btn-outline-success"
    sendButton.onclick = function(){
        console.log("Transfer prescription");
        transferPrescription(data);
    };
    sendButton.type = "button";
    sendButton.setAttribute("data-toggle", "modal");
    sendButton.setAttribute("data-target", "#modalView");

    PIDH1.textContent = data.PID;
    DATEH2.textContent = data.date;
    PRODUCTH3.textContent = data.product;
    openButton.textContent = "View prescription details";
    sendButton.textContent = "Transfer prescription";

    var dv = document.getElementById("prescription_widget_div");
    dv.appendChild(widgetDiv);
    widgetDiv.appendChild(PIDH1);
    widgetDiv.appendChild(DATEH2);
    widgetDiv.appendChild(PRODUCTH3);
    widgetDiv.appendChild(openButton);
    widgetDiv.appendChild(sendButton);
}



function viewPrescriptionModal(data){
    if(document.getElementById("sButton") != null){
        var div = document.getElementById("modal-footer")
        div.removeChild(document.getElementById("sButton"));
    }
    var contentString = "";
    var modalTitle = document.getElementById("modal-title");
    modalTitle.innerHTML = data.PID;
    var keysCount = Object.keys(data).length;
    for(var i = 0; i < keysCount; i++){
        var currentKey = Object.keys(data)[i];
        contentString += currentKey + " : " + data[currentKey];
        contentString += "<br>"; 
    }
    var modalContent = document.getElementById("modal-body");
    modalContent.innerHTML = "";
    modalContent.innerHTML = contentString;
}

function transferPrescription(data){
    //Button creation
    if(document.getElementById("sButton") != null){
        var div = document.getElementById("modal-footer")
        div.removeChild(document.getElementById("sButton"));
    }
    var submitButton = document.createElement("button");
    submitButton.id = "sButton";
    submitButton.className += "btn btn-success";
    submitButton.textContent = "Submit";

    submitButton.onclick = function(){
        var transferData = {"PID" : undefined, "owner" : undefined, "recipient" : undefined};

        if(inputBox.value != ""){
            console.log(inputBox.value);
            transferData.PID = data.PID;
            transferData.owner = data.owner;
            transferData.recipient = inputBox.value;
            Server.sendPrescriptionToUser(transferData);
        }
    }

    var modalFooter = document.getElementById("modal-footer");
    modalFooter.appendChild(submitButton);

    var modalTitle = document.getElementById("modal-title");
    modalTitle.innerHTML = data.PID;

    var modalContent = document.getElementById("modal-body");
    modalContent.innerHTML = "Please enter recipients address <br><br>";

    var inputBox = document.createElement("input");
    inputBox.type = "search";
    inputBox.className += "form-control";
    inputBox.placeholder = "Recipients wallet address";
    modalContent.appendChild(inputBox);


}