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
        .form {
            border: 1px solid black;
            text-align: center;
            outline: none;
            min-width:4px;
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
        .instructionBorder{

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
        $(document).ready(function(){
            /*$("button").click(function(){
                var editorList = $('#editorList li').map(function(){ return $(this).text(); });
                $.ajax({
                    type: "POST",
                    url: "/retrieveList",
                    data: {
                        "editorList": editorList.toArray()
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
            })

            });*/

        });


    function processDrop(){
        var editorList = $('#editorList li').map(function(){ return $(this).text(); });
        $.ajax({
            type: "POST",
            url: "/retrieveList",
            data: {
                "editorList": editorList.toArray()
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

    </script>
</head>
<body>
   <div class="split left">
        <div class="centered">
            <h3>Compiler Page</h3>
            <h2>Editor</h2>
            <br>
            <div ondrop="processDrop()">
            <ul id="editorList" class="list-group" ondrop="drop(event)">
                <c:if test="${empty selectedInstructions}">
                    Drop instructions here:
                </c:if>
                <c:if test="${!empty selectedInstructions}">
                    <c:forEach items="${selectedInstructions}" var="ins">
                        <li draggable="true" class="list-group-item">${ins.name}
                            <c:forEach items="${ins.argCounts}" var="ins2">
                            <cell></cell><span> <input id="input" class="form"  type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""></span></cell>
                            </c:forEach>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
            </div>
            <button>Execute</button>

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
<!--<input type="text" maxlength="8" style="width: 15px; height:20px;">-->
<!--<span> <input id="input" class="form"  type="text" style="width: 15px;" onkeypress="this.style.width = ((this.value.length + 1) * 15) + 'px';" placeholder=""></span>-->
