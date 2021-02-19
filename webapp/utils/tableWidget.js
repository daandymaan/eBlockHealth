import Server from './Server.js'

if(sessionStorage.getItem("ID") == null){
    console.log("Not logged in");
}
Server.getTransactionsFromUser("cheese", function(data){
    var sampleList = [{"PID" : "389201303", "Owner": "admin", "Issuer":"GP"},
                      {"PID" : "00005006", "Owner": "dan", "Issuer":"GP"}];

    var tableData = sampleList;
    //Create table 
    var table = document.createElement("table");
    table.className += "table";
    table.className += " table-hover";
    table.className += " table-striped";
    table.className += " table-active";
    var columnCount = Object.keys(tableData[0]).length;

     //Add the header row.
     var row = table.insertRow(-1);
     for (var i = 0; i < columnCount; i++) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = Object.keys(tableData[0])[i];
        row.appendChild(headerCell);
    }

    for(var i = 0; i < tableData.length; i++){
        row = table.insertRow(-1);
        for(var j = 0; j < columnCount; j++){
            var cell = row.insertCell(-1);
            console.log(tableData[i][Object.keys(tableData[0])[j]]);
            cell.innerHTML = tableData[i][Object.keys(tableData[0])[j]]
        }
    }
     var dvTable = document.getElementById("table_div");
     dvTable.innerHTML = "";
     dvTable.appendChild(table);

});