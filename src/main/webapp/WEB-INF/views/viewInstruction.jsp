<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/search.js"></script>
</head>

<body onload="hide(${currentStep.number})">
<nav class="navbar navbar-default " role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/login">Home</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <form id="logoutForm" method="POST" action="${contextPath}/logout" hidden>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <li><a class="navbar-brand" style="cursor: pointer" onclick="document.forms['logoutForm'].submit()">Logout</a>
                    </li>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <li><a class="navbar-brand" href="/startpage">Log In</a></li>
                </c:if>
                <li><a class="navbar-brand" href="/userPage">Go to account</a></li>
            </ul>
            <div class="navbar-form navbar-right ">
                <div class="form-group">
                    <input id="search" type="text" class="form-control" placeholder="Search">
                </div>
                <button class="btn btn-default" onclick="searchInstructions()">Search</button>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li><a class="navbar-brand" onclick="seeTags()" style="cursor: pointer">Tags cloud</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<p style="display: none" id="modelAttr">${instruction.id}</p>
<div class="container">
    <h1 style="text-align: center">${instruction.heading}</h1>
    <p>    ${instruction.content}</p>
    <div style="display: none" class="steps btn-group btn-group-justified" role="group">
        <div class="btn-group mr-2 col-md-0" role="group" aria-label="First group">
        <c:forEach items="${steps}" var="item">
            <span type="button" style="font-size: 15px; cursor: pointer; " class="btn btn-default" onclick='viewStep("${item.number}")'>
                ${item.number}
            <div id="currentStep"></div>
            </span>
        </c:forEach>
        </div>
    </div>
    <br>
    <div class="currentStep" style="display: none">
    <h1 class="step">${currentStep.heading}</h1>
    <h3 class="step">${currentStep.content}</h3>
    </div>
    <div class="arr text-center">
        <span id="prev" class="arrows glyphicon glyphicon-arrow-left pull-left" onclick="prev(${currentStep.number})"></span>
        <span id="first" style="font-weight: bold" class="arrows" onclick="first()">First</span>
        <span id="next" class="arrows glyphicon glyphicon-arrow-right pull-right" onclick="next(${currentStep.number})"></span>
    </div>
    <br>
    <br>

    <div class="comments" id="comments">
        <br>
        <c:forEach items="${information}" var="item">
            <div class="posted-comments">
                <a style="" href="/user/${item[4]}">${item[0]}</a>
                <span> says :</span>
                <br>
                <span>${item[2]}</span>
                <div class="like">
                    <span id="Likes${item[1]}">${item[3]}</span>
                    <span style="cursor: pointer" class="glyphicon glyphicon-heart"
                          onclick="addLike(${item[1]})"></span>
                </div>
            </div>
        </c:forEach>
    </div>
    <style>
        .arr{
        }
        .comments {
            border-top: 1px solid #5bc0de;
            width: 70%;
        }

        .like {
            text-align: right;

        }


        .arrows {
            cursor: pointer;
            font-size: 30px;
        }

        .posted-comments {
            border: 1px solid #cccccc;
            margin-bottom: 20px;
            margin-left: 0px;
            margin-right: 0px;
            padding: 10px 20px;
            position: relative;
            border-radius: 4px;
        }

        textarea#comment {
            width: 70%;
        }
        .currentStep{
            width:auto;
            border: 1px dotted #000000;
            border-radius: 7px;
            background-color: rgb(248, 247, 238);
        }

    </style>
    <p><textarea style="resize: none;" rows="4" id="comment"></textarea></p>
    <button class="btn btn-info" onclick="addComment()">Comment</button>
</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/websocket.js"></script>
<script type="text/javascript" src="/resources/js/whiteboard.js"></script>
<script type="text/javascript">
    function addComment() {
        var el = document.getElementById("comment");
        $.ajax({
            url: "/addComment",
            type: 'GET',
            data: ({
                "instructionsId": ${instruction.id},
                "content": el.value
            }), success: function (str) {

                if (str === "0") {
                    window.location.replace("/startpage")
                } else {
                    defineComment(el.value, ${instruction.id}, str, "${user.name}","${user.username}");
                }
                el.value = "";
            }
        });
    }
    function first() {
        window.location.replace(0);
    }
    function hide(number) {
        if (number === undefined) {
           document.getElementsByClassName("steps").item(0).style.display='inline-block';
            var prev=document.getElementById("prev");
            prev.removeAttribute("onclick");
            prev.style.cursor="default";
            prev.style.color="rgba(51, 51, 51, 0.44)";
            if (0 ===${lastStep}) {
                var next=document.getElementById("next");
                next.removeAttribute("onclick");
                next.style.cursor="default";
                next.style.color="rgba(51, 51, 51, 0.44)";
            }
        } else {
            var steps = document.getElementsByClassName("currentStep");
            if (number ===${lastStep}) {
                var next1=document.getElementById("next");
                next1.removeAttribute("onclick");
                next1.style.cursor="default";
                next1.style.color="rgba(51, 51, 51, 0.44)";
            }
                steps[0].style.display = '';
        }
    }
    function viewStep(number) {
        // window.alert(${contextPath});
        window.location.replace(number);
    }
    function next(number) {
        if (number === undefined) {
            window.location.replace(1);
        } else {
            window.location.replace(number + 1);
        }
    }
    function prev(number) {
        window.location.replace(number - 1);
    }
    function addLike(commentId) {
        $.ajax({
            url: "/addLike",
            type: 'GET',
            data: ({
                "commentId": commentId
            }), success: function (str) {
                window.alert(str);
                if (str === 0) {
                    window.location.replace("/startpage");
                } else {
                    document.getElementById("Likes" + commentId).innerText = str;
                }
            }
        });
    }
</script>

</html>