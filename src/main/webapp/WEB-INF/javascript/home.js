var output;
function setOutput(data){
    output = data;
    if(output != "READ") {
        output = output.split(" ");
        document.getElementById("output").innerHTML = "";
        for(var i = 0; i < output.length;i++){
                if(isNaN(output[i])){
                    document.getElementById("output").innerHTML+=output[i];
                }else{
                    document.getElementById("output").innerHTML+=output[i];
                    document.getElementById("output").innerHTML+="<br>";
                }
            document.getElementById("output").innerHTML+=" ";
        }
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
function expand(textbox) {
    if (!textbox.startW) { textbox.startW = textbox.offsetWidth; }

    var style = textbox.style;

    //Force complete recalculation of width
    //in case characters are deleted and not added:
    style.width = 0;

    //http://stackoverflow.com/a/9312727/1869660
    var desiredW = textbox.scrollWidth;
    //Optional padding to reduce "jerkyness" when typing:
    desiredW += textbox.offsetHeight;

    style.width = Math.max(desiredW, textbox.startW) + 'px';
}