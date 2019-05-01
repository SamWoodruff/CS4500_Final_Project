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
        <%@include file="/WEB-INF/css/homepage.css"%>
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
        <%@include file="/WEB-INF/javascript/home.js"%>
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
                        <li draggable="true" class="draggedTiles">
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

       <br>
       <br>
       <button class="button" onclick="executeAsm()">Execute</button>
       <button class="button" onclick="exportFile()">Export</button>
       <input class="button"type="button"  onclick="location.href='/'" value="Clear List" >
    </div>
   <div id="output" class="console">
       Console output here.
   </div>
   </div>
    <div class="split right">
        <h3>Instruction List</h3>
        <button class="button" onclick="document.getElementById('id01').style.display='block'">Help</button>
        <div id="devnull" class="button">
            Drag tile here to remove:
        </div>
        <div id="id01" class="help" style="display:none">
            <span onclick="this.parentElement.style.display='none'" class="">x</span>
            <p>Instruction Set (# arguments, meaning)</p>
            <p>BR (1, jump to arg)</p>
            <p>BRNEG (1, jump to arg if ACC <0)</p>
            <p>BRZNEG (1, jump to arg if ACC <=0)</p>
            <p>BRPOS (1, jump to arg if ACC >0)</p>
            <p>BRZPOS (1, jump to arg if ACC >=0)</p>
            <p>BRZERO (1, jump to arg if ACC ==0)</p>
            <p>COPY (2, arg1 = arg2)</p>
            <p>ADD (1, ACC = ACC +arg)</p>
            <p>SUB (1, ACC = ACC - arg)</p>
            <p>DIV (1, ACC = ACC / arg)</p>
            <p>MULT (1, ACC = ACC * arg)</p>
            <p>READ (1, arg=input integer)</p>
            <p>WRITE (1, print integer)</p>
            <p>STOP (0, stop program)</p>
            <p>STORE (1, arg = ACC)</p>
            <p>LOAD (1, ACC=arg)</p>
            <p>NOOP (0, nothing)</p>
        </div>

        <br><br>
        <div class="centered">
            <br>
                <!--Lists all instructions to choose from-->
                <ul id="instructionList" class="listUL">
                 <c:forEach items="${instructions}" var="ins">
                        <li class="dragTiles">${ins.name}</li>
                 </c:forEach>
                </ul>
            </div>
        <form action="/importFile" method="POST" enctype="multipart/form-data">
                   <span style="display: inline;">
                  <input class="button" type="file" name="file">
                  <button class="button" type="submit">Import File</button>
                   </span>
        </form>
    </div>
<script><%@include file="/WEB-INF/javascript/UILists.js"%> </script>
</body>
</html>
<!--<input type="text" maxlength="8" style="width: 15px; height:20px;">-->
<!--<span> <input id="input" class="form"  type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""></span>-->
