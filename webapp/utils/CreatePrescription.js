import Server from "./Server.js";

var exampleItems = [ {"product":"LEXAPRO", "productID":"G551",
"productPackage":"", "quantity":"20", "doseStrength":"10MG",
"doseType":"TABS", "doseQuantity":"20", "instruction" : "TAKE ONE ONCE DAILY"},

{"product":"ASACOLON", "productID":"G13072",
"productPackage":"", "quantity":"28", "doseStrength":"400MG",
"doseType":"TABS", "doseQuantity":"28", "instruction" : "TAKE TWO TWICE DAILY"}, 

{"product":"PREDFOAM", "productID":"G33387",
"productPackage":"CANISTER", "quantity":"14", "doseStrength":"25G",
"doseType":"AEROSOL", "doseQuantity":"14", "instruction" : "ONE APPLICANCE DAILY"},];

var selectBox = document.getElementById("c_prescription_form_product_select");
var submitButton = document.getElementById("c_prescription_btn");
var innerHtmlContent = "<option selected disabled> Please select a product  </option>";
var commentBox = document.getElementById("c_prescription_form_product_comment");
var errordiv = document.getElementById("errormsg");
exampleItems.forEach(function(product){
    var option = "<option value='"+ product.productID +"'>"
        Object.keys(product).forEach(function(key){
            option += product[key] + "     ";
        })
    option += "</option>"
    innerHtmlContent += option;
});
selectBox.innerHTML = innerHtmlContent;
submitButton.addEventListener("click", function(event){
    if(selectBox.value == "Please select a product"){
        errordiv.innerText = "Please select an option from the list";
    } else {
        exampleItems.forEach(function(product){
            if(product.productID == selectBox.value){
                formatPrescriptionData(product);
            }
        })
    }
})

function formatPrescriptionData(prescription){
    console.log(commentBox.value);
    prescription.date = getDateTime();
    prescription.issuer = localStorage.getItem("cert");
    prescription.comment = commentBox.value;
    Server.createPrescription(prescription, function(value){
        var toastBody = document.getElementById("toast_notification_body");
        var toastHeader = document.getElementById("toast_notification_header");
        if(value.pID != null){
            toastHeader.innerHTML = "Prescription created successfully";
            toastBody.innerHTML = value.pID +" created succesfully";
        } else {
            toastHeader.innerHTML = "Prescription was not created";
            toastBody.innerHTML = "The prescription was not created"
        }
        $('#toast_notifcation').toast('show');
    })
}

function getDateTime(){
    var currentDateTime = new Date();
    var formattedDateTime = currentDateTime.getDate() + "-" + (currentDateTime.getMonth()+1) + "-" + currentDateTime.getFullYear() + " " + currentDateTime.getHours() + "-" + currentDateTime.getMinutes() + "-" + currentDateTime.getSeconds();
    return formattedDateTime;
}