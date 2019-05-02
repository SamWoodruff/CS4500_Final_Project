var output;
function setOutput(data){
    output = data;
    if(output != "READ") {
        document.getElementById("output").innerHTML = output;
    }
}
function executeAsm() {
    var arguments = $("#editorList li input[name='arg']").map(function(){return $(this).val();}).get();
    $.ajax({
        type: "POST",
        url: "/executeAsm",
        data: {
            "arguments": arguments
        },
        success: function (data1){
            setOutput(data1);
            if(output == "READ") {
                do{
                    var readVal = prompt("Give number: ", "");
                    $.ajax({
                        type: "POST",
                        url: "/passRead",
                        async: false,
                        data: {
                            "readVal": readVal
                        },
                        success: function (data2) {
                            setOutput(data2);
                        }
                    });
                    }while(output == "READ");
            }
        }
    });
}
function processDrop(){
    var editorList = $('#editorList li').map(function(){ return $(this).text(); });
    var arguments = $("#editorList li input[name='arg']").map(function(){return $(this).val();}).get();
    $.ajax({
        type: "POST",
        url: "/retrieveList",
        data: {
            "editorList": editorList.toArray(),
            "arguments": arguments.toString()
        },
        success: function (data) {
            document.open();
            document.write(data);
            document.close();
        },
        error: function (e) {
            alert('error ' + e);
        }
    });
}
function passArgs(){
    var arguments = $("#editorList li input[name='arg']").map(function(){return $(this).val();}).get();
    $.ajax({
        type: "POST",
        url: "/passArgs",
        data: {
            "arguments": arguments
        },
        success: function (data) {
            document.open();
            document.write(data);
            document.close();
        },
        error: function (e) {
            alert('error ' + e);
        }
    });
}
function exportFile() {
    var arguments = $("#editorList li input[name='arg']").map(function(){return $(this).val();}).get();
    $.ajax({
        type: "POST",
        url: "/createFile",
        data: {
            "arguments": arguments
        },
        success: function (data) {
            window.location.replace("/downloadFile")
        }
    });
}