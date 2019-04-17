<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: swood
  Date: 3/3/2019
  Time: 9:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
    <style>
        .form {
            border: 1px solid black;
            text-align: center;
            outline: none;
            min-width:4px;
        }

        #output {
            border-style: solid;
            border-width: 2px;
            margin-top: 20px;
            width: 100%;
            height: 200px;
        }

        span {
            display: inline-block;
        }
        /* Split the screen in half */
        .split {
            height: 100%;
            width: 50%;
            position: fixed;
            z-index: 1;
            top: 0;
            overflow-x: hidden;
            padding-top: 20px;
        }

        /* Control the left side */
        .left {
            left: 0;
            background-color: white;
            border: 5px solid gray;
            padding: 10px;
        }

        /* Control the right side */
        .right {
            right: 0;
            background-color: white;
            border: 5px solid gray;
            padding: 10px;
        }
    </style>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <!-- Sortable.js -->
    <script src="https://raw.githack.com/SortableJS/Sortable/master/Sortable.js"></script>
    <!--jQuery from google CDN(Content Delievery Network)-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

    <script>
        function executeAsm() {
            var arguments = $("#editorList li input[name='arg']").map(function(){return $(this).val();}).get();
            $.ajax({
               type: "POST",
               url: "/executeAsm",
                data: {
                    "arguments": arguments
                },
                success: function (data) {
                    document.getElementById("output").innerHTML = data;
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
    </script>
</head>
<body>
   <div class="split left">
            <h2>Tile editor</h2>
            <br>
            <div ondrop="processDrop()">
            <ul id="editorList" class="list-group" ondrop="drop(event)">
                <c:if test="${empty selectedInstructions}">
                    Drop instructions here:
                </c:if>
                <c:if test="${!empty selectedInstructions}">
                    <c:forEach items="${selectedInstructions}" var="ins">
                        <li draggable="true" class="list-group-item">
                            ${ins.lineNum})
                            <c:if test="${ins.name != 'Variable' && ins.name != 'Label:'}">
                                ${ins.name}
                            </c:if>
                            <c:if test="${!empty ins.args}">
                                <c:forEach items="${ins.args}" var="ins2">
                                    <c:if test="${ins.name != 'Label:'}">
                                        <cell></cell><span> <input name="arg" id="arg" class="form" type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""
                                                                    value="${ins2}"></span></cell>
                                    </c:if>
                                    <c:if test="${ins.name == 'Label:'}">
                                        <cell></cell><span> <input name="arg" id="arg" class="form" type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""
                                                                   value="${ins2}"></span></cell>:
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty ins.args}">
                                <c:forEach items="${ins.argCounts}" var="ins3">
                                    <c:if test="${ins.name != 'Label:'}">
                                        <cell></cell><span> <input name="arg" id="arg" class="form" type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""></span></cell>
                                    </c:if>
                                    <c:if test="${ins.name == 'Label:'}">
                                        <cell></cell><span> <input name="arg" id="arg" class="form" type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""></span></cell>:
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
            </div>
            <button onclick="executeAsm()">Execute</button>
            <button onclick="exportFile()">Export</button>
            <input type="button"  onclick="location.href='/'" value="Clear List" >
            <!--<input type="button"  onclick="location.href='/importFile'" value="Import"/>-->
            <br><br>
       <form action="/importFile" method="POST" enctype="multipart/form-data">
           <input type="file" name="file"><br>
           <button type="submit">Import File</button>
       </form>
       <br>
            <div id="output">
                Console output here.
            </div>
        </div>
    </div>
    <div class="split right">
        <div class="centered">
            <h3>Instruction List</h3>
            Drag tile here to remove:
            <div id="devnull" class="list-group">

            </div>
            <!--Lists all instructions to choose from-->
            <ul id="instructionList" class="list-group">
            <c:forEach items="${instructions}" var="ins">
                    <li class="list-group-item">${ins.name}</li>
            </c:forEach>
            </ul>
        </div>
    </div>
<script><%@include file="/WEB-INF/javascript/UILists.js"%> </script>

</body>
</html>
<!--<input type="text" maxlength="8" style="width: 15px; height:20px;">-->
<!--<span> <input id="input" class="form"  type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""></span>-->
