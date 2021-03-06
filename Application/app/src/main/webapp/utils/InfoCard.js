import Server from "./Server.js";

new ClipboardJS('.btn');

Server.getDetailsForUser(sessionStorage.getItem("ID"), function(value){
    var ppsn = document.getElementById("PPSNNO");
    ppsn.innerHTML = value.PPSN;
    var fullname = document.getElementById("NAME");
    fullname.innerHTML = value.firstname + " " + value.surname;
    var dob = document.getElementById("DOB");
    dob.innerHTML = value.DOB
    var status = document.getElementById("STATUS");
    status.innerHTML = value.status;
    var cert = document.getElementById("cert");
    cert.value = value.address;
})