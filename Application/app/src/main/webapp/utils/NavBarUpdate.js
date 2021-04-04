import Server from "./Server.js";
var loginURL = "/app/login/login.html";
var detailsURL= "/app/v_details/v_details.html";
var indexURL = "/app/index.html";
var createURL = "/app/c_prescriptions/c_prescriptions.html";

Server.verifySession(function(value){
    if(value.msg == "success"){
        var user = value.identifier;
        var sideNavButton = document.getElementById("navSideButton");
        sideNavButton.innerHTML = user;
        sideNavButton.href = detailsURL;
        sideNavButton.setAttribute("data-toggle", "dropdown");

        var navClass = document.getElementById("navClass");

        var createPrescriptionNav = document.getElementById("c_prescription_link");
        if(value.status != "M"){
            createPrescriptionNav.className = "nav-link disabled";
        }

        var dropdownMenu = document.createElement("div");
        dropdownMenu.className = "dropdown-menu";
        navClass.appendChild(dropdownMenu);

        var myAccount = document.createElement("a");
        myAccount.className = "dropdown-item";
        myAccount.href = detailsURL;
        myAccount.innerHTML = "My details";

        var logout = document.createElement("a");
        logout.className = "dropdown-item";
        logout.id = "logoutbtn";
        logout.innerHTML = "Logout";
        logout.href = loginURL;

        logout.onclick = function(){
            Server.endSession(function(value){
                if(value.msg == "success"){
                    console.log("Logged out");
                }
            });
        }

        dropdownMenu.appendChild(myAccount);
        dropdownMenu.appendChild(logout);
    } else {
        window.location.replace(loginURL);
    }

    if(window.location.href == createURL && value.status != "M"){
        window.location.replace(indexURL);
    }
})

