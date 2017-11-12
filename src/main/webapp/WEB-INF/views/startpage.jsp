<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css"> </style>
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/search.js"></script>
</head>

<body>

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

<div class="container">

    <form method="POST" action="${contextPath}/login3" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>

            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
            <h4 class="text-center"><a href="/inputEmail">Forgot password?</a></h4>
            <a onclick="goToVK()" class="btn btn-info" style="color: cornflowerblue; width: auto">VK</a>

            <a onclick="goToTW()" class="btn btn-info" style="color: #5bc0de; width: auto">Twitter</a>

            <a onclick="goToFK()" class="btn btn-info" style="color: #337ab7; width: auto">Facebook</a>

        </div>

    </form>


    <%--<span>${message}</span>--%>


</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>


<script type="text/javascript">
    function goToTW() {
        window.location = "/twitter"
    }
</script>

<script type="text/javascript">
    function goToVK() {
        window.location = "/vk"
    }
</script>
<script type="text/javascript">
    function goToFK() {
        window.location = "/facebook"
    }
</script>
</body>
</html>