<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <script type="text/javascript" src="/resources/js/search.js"></script>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Admin</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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


    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <table class="table table-striped">
            <thead>
            <tr>
                <td>Users</td>
                <td>Settings</td>

            </tr>
            </thead>

                <%--<span class="glyphicon glyphicon-remove">- </span>--%>
            <c:forEach items="${inform}" var="item">

                <tr>
                    <td>
                        <c:if test= "${item[0]==2}">
                            <h3 id = " ${item[1]} " style="color: green"> ${item[1]} </h3>

                        </c:if>
                        <c:if test= "${item[0]==1}">
                            <h3 id = "User${item[1]}" style="color: blue"> ${item[1]} </h3>

                        </c:if>
                        </td>
                        <td>
                            <div class="btn-group" style  = "display: inline;  weight:auto">
                                <button type="button" data-toggle="dropdown" class="dropdown-toggle btn - xs"  ><span class="caret" style = "min-width: 0; min-height: 0"></span></button>
                                <ul class="dropdown-menu" style="min-width:0;" style="color: white">
                                    <li style="width: 65px"><button style = "width:65px;height: 25px"><span id="Lock${item[1]}" onclick='changeStatusUser("${item[1]}")'>Lock</span></button>
                                        <br></li>
                                    <li style="width: 65px"><button style = "width:65px;height: 25px"><span id="MakeAdmin${item[1]}" onclick='makeAdmin("${item[1]}")'>Make Admin</span></button>
                                    <br></li>
                                    <li style="width: 65px"><button style = "width:65px;height: 25px"><a id="View${item[1]}" href="/seeInstructions/${item[1]}">View Instructions</a></button>
                                        <br></li>
                                </ul>
                            </div>
                        </td>
                        </h3>

                    </tr>

            </c:forEach>
        </table>

    </c:if>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
    function makeAdmin(username) {
        $.ajax({
            url: '/makeAdmin',
            type: 'GET',
            data: ({
                "username": username
            })
        });
        window.alert("LOL");
        var user=document.getElementById("User"+username);
        user.removeAttribute("style");
        user.style.color="green";
    }
</script>
</body>
</html>