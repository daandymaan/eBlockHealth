import Server from './Server.js'
Server.getPrescriptionsForUser( function(value){

    var tempdata = [{"pID": "0385736289", "date":"11/02/2021", "issuer": "GP", "owner": "Me", "product":"LEXAPRO", "productID":"G551",
                    "productPackage":"", "quantity":"20", "doseStrength":"10MG",
                    "doseType":"TABS", "doseQuantity":"1perday"},
                
                    {"pID": "0020555562", "date":"11/02/2021", "issuer": "GP", "owner": "Me", "product":"CHEESESAUCE", "productID":"G5333",
                    "productPackage":"", "quantity":"20", "doseStrength":"10Slices",
                    "doseType":"TABS", "doseQuantity":"1perday"}];
    var data = value;
    
    var widgets = [];

    if(data.length > 0){
        data.forEach(function(widgetData){
            widgets.push(widgetCreation(widgetData));
        });
    }
})


function widgetCreation(data){

    var widgetDiv = document.createElement("div");
    widgetDiv.className += "card";
    var pIDH1 = document.createElement("h1");
    pIDH1.className += "card-header card-title"
    var DATEH2 = document.createElement("h2");
    DATEH2.className += "card-text";
    var PRODUCTH3 = document.createElement("h3");
    PRODUCTH3.className += "card-text";

    // Buttons
    var openButton = document.createElement("button");
    openButton.className += "btn btn-outline-info"
    openButton.onclick = function(){
        viewPrescriptionModal(data);
        $('#modalView').modal('show')

    };
    openButton.type = "button";

    var sendButton = document.createElement("button");
    sendButton.className += "btn btn-outline-success"
    sendButton.onclick = function(){
        transferPrescription(data);
        $('#transferView').modal('show')
    };
    sendButton.type = "button";

    pIDH1.textContent = data.pID;
    DATEH2.textContent = data.date;
    PRODUCTH3.textContent = data.product;
    openButton.textContent = "View prescription details";
    sendButton.textContent = "Transfer prescription";

    var dv = document.getElementById("prescription_widget_div");
    dv.appendChild(widgetDiv);
    widgetDiv.appendChild(pIDH1);
    widgetDiv.appendChild(DATEH2);
    widgetDiv.appendChild(PRODUCTH3);
    widgetDiv.appendChild(openButton);
    widgetDiv.appendChild(sendButton);
}



function viewPrescriptionModal(data){
    var contentString = "";
    var modalTitle = document.getElementById("modal-title");
    modalTitle.innerHTML = data.pID;
    var keysCount = Object.keys(data).length;
    for(var i = 0; i < keysCount; i++){
        var currentKey = Object.keys(data)[i];
        if(currentKey != "owner" && currentKey != "issuer"){
            contentString += currentKey.replace(/([a-z0-9])([A-Z])/g, '$1 $2') + " : " + data[currentKey];
            contentString += "<br>"; 
        }
    }
    var modalContent = document.getElementById("modal-body");
    modalContent.innerHTML = "";
    modalContent.innerHTML = contentString;

    new ClipboardJS('#issuerbtn');
    new ClipboardJS('#ownerbtn');
    var ownerTextInput = document.getElementById("owner");
    ownerTextInput.value = data.owner;
    var issuerTextInput = document.getElementById("issuer");
    issuerTextInput.value = data.issuer;
}

function transferPrescription(data){
    //Button creation
    if(document.getElementById("sButton") != null){
        var div = document.getElementById("transferView-footer")
        div.removeChild(document.getElementById("sButton"));
    }
    var submitButton = document.createElement("button");
    submitButton.id = "sButton";
    submitButton.className += "btn btn-success";
    submitButton.textContent = "Submit";

    submitButton.onclick = function(){
        var transferData = {"pID" : undefined, "owner" : undefined, "recipient" : undefined};

        if(inputBox.value != ""){
            transferData.pID = data.pID;
            transferData.owner = data.owner;
            transferData.recipient = inputBox.value;
            Server.sendPrescriptionToUser(transferData, function(value){
                var toastBody = document.getElementById("toast_notification_body");
                var toastHeader = document.getElementById("toast_notification_header");
                if(value.owner == transferData.recipient){
                    toastHeader.innerHTML = "Prescription sent successfully";
                    toastBody.innerHTML = value.pID +" sent succesfully";
                } else {
                    toastHeader.innerHTML = "Prescription was not sent";
                }
                $('#toast_notifcation').toast('show');
                setTimeout(function(){
                    window.location.reload(1);
                }, 2000);
            });
        }
    }

    var modalFooter = document.getElementById("transferView-footer");
    modalFooter.appendChild(submitButton);

    var modalTitle = document.getElementById("transferView-title");
    modalTitle.innerHTML = data.pID;

    var modalContent = document.getElementById("transferView-body");
    modalContent.innerHTML = "Please enter recipients address <br><br>";

    var inputBox = document.createElement("input");
    inputBox.type = "search";
    inputBox.className += "form-control";
    inputBox.placeholder = "Recipients wallet address";
    modalContent.appendChild(inputBox);


}