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
                <li><a class="navbar-brand" href="/startpage">Log In</a></li>
                <li><a class="navbar-brand" href="/userPage">Go to account</a></li>
            </ul>
            <form class="navbar-form navbar-right " role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<body>

<main role="main">

    <div class="jumbotron">
        <div class="container">
            <div>
                <form action="" method="post">
                    <p><b>Text you instraction</b></p>
                    <p><textarea rows="2" cols="100" name="text"></textarea></p>
                    <p><input type="submit" value="Save"></p>
                </form>

            </div>
            <form action="" method="post">
                <p><b>Text you instraction</b></p>
                <p><textarea rows="10" cols="100" name="text"></textarea></p>
                <p><input type="submit" value="Save"></p>
            </form>
        </div>
    </div>



</main>
<div class="container">

    <form:form method="POST" modelAttribute="instructionsForm">
        <spring:bind path="content">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="content" class="form-control" placeholder="LOLIKON"
                            autofocus="true"></form:input>
                <form:errors path="content"></form:errors>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
</body>
</html>