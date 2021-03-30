import Server from './Server.js'
Server.getTransactionsFromUser( function(data){
    if(data.msg){
        var jumbotron = document.getElementById("v_history_header");
        var errormsg = document.createElement("h2");
        errormsg.className = "display-3";
        errormsg.textContent = data.msg;
        jumbotron.appendChild(errormsg);
    } else{
        data.forEach(function(prescription){
            widgetCreation(prescription);
        });
    }
});

function widgetCreation(data){

    var widgetDiv = document.createElement("div");
    widgetDiv.className += "card";
    var pIDH1 = document.createElement("h1");
    pIDH1.className += "card-header card-title"
    var DATEH2 = document.createElement("h2");
    DATEH2.className += "card-text";

    var viewHistory = document.createElement("button");
    viewHistory.className += "btn btn-outline-success"
    viewHistory.onclick = function(){
        generatePagination(data);
        if(data.length > 1){
            loadOwnerTimeline(data[0].owner, data[1].owner);
        } else {
            loadOwnerTimeline(data[0].owner);
        }
        loadTableData(data[0]);

    };
    viewHistory.type = "button";
    pIDH1.textContent = data[0].pID;
    DATEH2.textContent = data[0].date;
    viewHistory.textContent = "View prescription history";

    var dv = document.getElementById("prescription_widget");
    dv.appendChild(widgetDiv);
    widgetDiv.appendChild(pIDH1);
    widgetDiv.appendChild(DATEH2);
    widgetDiv.appendChild(viewHistory);
}

function generatePagination(data){
    var paginationBar = '<h2 class="display-4">'+ data[0].pID +' prescription history</h1> <nav aria-label="prescription history pagination tabs"><ul class="pagination">';
    for(var i = 0; i < data.length; i++){
        var pagTab = '<li class="page-item"><a class="page-link">'+ i +'</a></li>';
        paginationBar += pagTab;
    }
    paginationBar += "  </ul></nav>";
    var table_div = document.getElementById("pagination_history");
    table_div.innerHTML = paginationBar;
    $('.page-link').click(function(e){
        var index =  parseInt($(e.target).text());
        var nextIndex = index+1;
        loadTableData(data[index]);
        if(data.length-1 > index){
            loadOwnerTimeline(data[index].owner, data[nextIndex].owner);
        } else{
            loadOwnerTimeline(data[index].owner);
        }
    })
}

function loadTableData(tableData){
    // Create table 
    var table = document.createElement("table");
    table.className += "table";
    table.className += " table-hover";
    table.className += " table-striped";
    table.className += " table-active";
    var columnCount = Object.keys(tableData).length;

     //Add the header row.
     var row = table.insertRow(-1);
     for (var i = 0; i < columnCount; i++) {
        if(Object.keys(tableData)[i] != "owner" && Object.keys(tableData)[i] != "issuer"){
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = Object.keys(tableData)[i];
            row.appendChild(headerCell);
        }

    }

        row = table.insertRow(-1);
        for(var j = 0; j < columnCount; j++){
            if(Object.keys(tableData)[j] != "owner" && Object.keys(tableData)[j] != "issuer"){
                var cell = row.insertCell(-1);
                cell.innerHTML = tableData[Object.keys(tableData)[j]];
            }
        }

     var dvTable = document.getElementById("prescription_history_table");
     dvTable.innerHTML = "";
     dvTable.appendChild(table);
}

function loadOwnerTimeline(owner, prevOwner){
    var ownerSpan = document.getElementById("owner_cert");
    var prevOwnerSpan = document.getElementById("previousOwner_cert");
    ownerSpan.innerHTML = "";
    prevOwnerSpan.innerHTML = "";
    ownerSpan.innerHTML = "Owner:\t" + owner.substring(50, 60);
    ownerSpan.title = owner;
    if(prevOwner){
        prevOwnerSpan.innerHTML =   "Previous owner:\t" + prevOwner.substring(50, 60);;
        prevOwnerSpan.title = prevOwner;
    }

    $('[data-toggle="owner_tooltip"]').tooltip();   
    $('[data-toggle="previousOwner_tooltip"]').tooltip(); 
}
