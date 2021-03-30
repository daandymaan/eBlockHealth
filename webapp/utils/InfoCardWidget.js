import Server from "./Server.js";

new ClipboardJS('.btn');
Server.getDetailsForUser(function(value){
    console.log("value ", value);
    var ppsn = document.getElementById("PPSNNO");
    ppsn.innerHTML = value.identifier;
    var fullname = document.getElementById("NAME");
    fullname.innerHTML = value.firstname + " " + value.surname;
    var dob = document.getElementById("DOB");
    dob.innerHTML = value.dob
    var status = document.getElementById("STATUS");
    status.innerHTML = value.status;
    var cert = document.getElementById("cert");
    cert.value = value.cert;
})