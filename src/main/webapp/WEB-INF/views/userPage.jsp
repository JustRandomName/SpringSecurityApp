<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">

    </style>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-default " role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
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

                    <form id="logoutForm" method="POST" action="${contextPath}/logout" hidden>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>

                    <li><a class="navbar-brand"  onclick="document.forms['logoutForm'].submit()">Logout</a>
                    </li>
                <li><a class="navbar-brand" href="/userPage">Go to account</a></li>
            </ul>
            <form class="navbar-form navbar-right " role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <c:if test= "${ROLE==2}">
                    <li><a class="navbar-brand" href="/admin">Admin page</a></li>
                </c:if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading"><h4>User Profile</h4></div>
            <div class="panel-body">
                <div class="col-md-4 col-xs-12 col-sm-6 col-lg-4">
                    <img alt="User Pic"
                         src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"
                         id="profile-image1" class="img-circle img-responsive">
                </div>
                <div class="col-md-8 col-xs-12 col-sm-6 col-lg-8">
                    <div class="container">
                        <h2>${currentUsername}</h2>
                        <p>an <b> Employee</b></p>
                        <a href="/seeInstractions/${currentUsername}">!!!!!!!!!!Q</a>
                    </div>
                    <hr>
                    <ul class="container details">
                        <li><p><span class="glyphicon glyphicon-user one" style="width:50px;"></span></p></li>
                        <li><p><span class="glyphicon glyphicon-envelope one" style="width:50px;"></span>${userName}
                        </p></li>
                    </ul>
                    <hr>
                    <c:forEach items="${instructions}" var="item">
                        <div style="overflow-x: hidden;" class="col-md-4">
                            <h2>${item.heading}</h2>
                            <p><a class="btn btn-light" href="#" role="button" >View more</a></p>
                        </div>

                    </c:forEach>
                    <div class="col-sm-5 col-xs-6 tital ">Date Of Joining: 15 Jun 2016</div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">

    </script>
</body>
</html>
