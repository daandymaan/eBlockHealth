import Server, { userAuthentication } from './Server.js'

var submitButton = document.getElementById("btnSubmit");
submitButton.onclick = function(){
    var identifier = document.getElementById("identifier");
    var DOB = document.getElementById("DOB");
    var errorMsg = document.getElementById("errormsg");

    if(identifier.value == "" || DOB.value == ""){
        errorMsg.innerHTML = "Please fill out the credentials";
    } else {
        errorMsg.innerHTML = ""
        var loginDetails = {"identifier" : identifier.value, "DOB" : DOB.value};
        Server.isUser(loginDetails, function(value){
            if(value.msg == "success"){
                var inputNo = [];
                while(inputNo.length != 3){
                    var num = ("" + Math.random()).substring(2,3)% 8;
                    if(!inputNo.includes(num)){
                        inputNo.push(num);
                    }
                }

                inputNo.sort();
                var inputs = document.getElementsByClassName("digitinput");
                for(var i = 0; i < inputs.length; i++){
                    inputs[i].disabled = true;
                    inputs[i].value = "*";
                }
                for(var i = 0; i < inputNo.length; i++){
                    inputs[inputNo[i]].disabled = false;
                    inputs[inputNo[i]].value = "";
                }

                $('#modalView').modal('show');

                var verifyButton = document.getElementById("verifyButton");
                verifyButton.onclick = function(){
                    var passcode = "";
                    var pattern = "";
                    for(var i = 0; i < inputNo.length; i++){
                        passcode+= inputs[inputNo[i]].value;
                        pattern += inputNo[i];
                    }
                    var loginCredentials = {"identifier" : loginDetails.identifier, "pattern" : pattern, "passcode" : passcode};
                    Server.userAuthentication(loginCredentials, function(value){
                        console.log(value);
                        if(value.msg == "success"){
                            window.location.href = "http://localhost:8080/app/index.html";
                        } else {
                            var errormsg = document.getElementById("msg");
                            errormsg.style = "color: red";
                            errormsg.innerHTML = "Invalid credentials";
                        }
                    })
                }
            } else{
                errorMsg.innerHTML = "User not found: Please re enter correct credentials";
            }
        })
    }
}

