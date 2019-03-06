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
    <title>Compiler-UI</title>

    <style>
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
        .instructionBorder{

        }
    </style>
</head>
<body>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"/>

<!-- Latest Sortable -->
<script src="https://raw.githack.com/SortableJS/Sortable/master/Sortable.js"></script>

   <div class="split left">
        <div class="centered">
            <h3>Compiler Page</h3>
            <h2>Editor</h2>
            <br>
            Drop instructions here:
            <br>
            <ul id="editorList" class="list-group">
            </ul>

        </div>
    </div>
    <div class="split right">
        <div class="centered">
            <h3>Instruction List</h3>
            Drag tile here to remove:
            <div id="devnull" class="list-group">

            </div>

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
