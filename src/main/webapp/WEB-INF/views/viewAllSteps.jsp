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
    <style type="text/css"></style>
    <script src="/resources/js/jquery-2.1.3.js"></script>
    <script src = "/resources/js/jspdf.js"></script>
    <script src = "/resources/js/pdfFromHTML.js"></script>
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

<div>
    <div class="container">
    <div id="HTMLtoPDF">
        <input id="rating" type="hidden" class="rating" value="${naxyu}" />
        <c:forEach items="${Steps}" var="item">
            <p> ${item.heading}</p>
            <p> ${item.content}</p>
        </c:forEach>

    </div>

    <a href="#" onclick="HTMLtoPDF()">Download PDF</a>
    <p><a class="btn btn-info" href="#" role="button" onclick="viewInstructions(${id})">View more</a></p>

    </div>
</div>
</body>
<script src="/resources/js/bootstrap-rating.min.js"></script>
<script>
    $(function () {
        $('.rating').each(function () {
            $('<span class="label label-default"></span>')
                .text("${naxyu}" || ' ')
                .insertAfter(this);
        });
        $('.rating').on('change', function () {

            $.ajax({
                url: "/changeMark",
                type: "GET",
                data:({
                    "userId": ${user},
                    "instrId": ${id},
                    "mark": $(this).val()
                }),success: function (mark) {
                    window.alert(mark);
                    document.getElementsByClassName("label")[0].innerText=mark;
                }
            });
            $(this).next('.label').text("${naxyu}");

        });
    });
    function viewInstructions(current) {
        window.location.replace("/viewInstruction/"+current+"/0");
    }
</script>
</html>
