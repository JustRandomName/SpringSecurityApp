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
<h1>${instruction.heading}</h1>
<p>${instruction.content}</p>
<c:forEach items="${steps}" var="item">
    <button class='steps' onclick='viewStep("${item.number}")'>Step${item.number}</button>
    <div id="currentStep"></div>
</c:forEach>
<h1 class="step">${currentStep.heading}</h1>
<h3 class="step">${currentStep.content}</h3>
<button id="prev" onclick="prev(${currentStep.number})">Prev</button>
<button id="next" onclick="next(${currentStep.number})">Next</button>
<button id="first" onclick="first()">First</button>

    <div class="comments">
    <c:forEach items="${comments}" var="item">
        <a href="#">JohnDoe</a> says :
        <br>
        <span>${item.content}</span>
        <div class="like">
        <span id="Likes${item.id}">${item.likes}</span>
        <span style="cursor: pointer" class="glyphicon glyphicon-heart" onclick="addLike(${item.id})"></span>
        </div>
            </c:forEach>
    </div>
<style>
    .like{
        text-align: right;
    }
    .comments{
        width: 70%;
        border: 1px solid #cccccc;
        margin-bottom: 20px;
        margin-left: 0px;
        margin-right: 0px;
        padding: 10px 20px;
        position: relative;
        border-radius: 4px;
    }
    textarea#comment{
        width:70%;
    }
</style>
<p><textarea style="resize: none" rows="4" cols="100" id="comment"></textarea></p>
<button onclick="addComment()">Comment</button>
</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/websocket.js"></script>
<script type="text/javascript" src="/resources/js/whiteboard.js"></script>
<script  type="text/javascript">
    function addComment() {
        var el = document.getElementById("comment");
        $.ajax({
            url: "/addComment",
            type: 'GET',
            data: ({
                "instructionsId": ${instruction.id},
                "content": el.value
            }), success: function (str) {
                if (str === 0) {
                    window.alert("user not found")
                } else {
                    defineComment(el.value, ${instruction.id}, str);
                }
            }
        });
    }
    function first() {
        window.location.replace(0);
    }
    function hide(number) {
        if (number === undefined) {
            var step = document.getElementsByClassName("step");
            for (var i = 0; i <= step.length; i++) {
                step[i].style.display = 'none';
                document.getElementById("prev").style.display = 'none';
            }
        } else {
            var steps = document.getElementsByClassName("steps");
            if (number ===${lastStep}) {
                document.getElementById("next").style.display = 'none';
            }
            for (var i = 0; i < steps.length; i++) {
                steps[i].style.display = 'none';
            }
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
                    window.alert("user not found")
                } else {
                    document.getElementById("Likes"+commentId).innerText=str;
                }
            }
        });
    }
</script>

</html>