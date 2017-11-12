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
    <meta property="og:url" content="https://selectize.github.io/selectize.js/"/>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include external CSS. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">

    <!-- Include Editor style. -->
    <link href="/resources/css/selectize.bootstrap3.css" rel="stylesheet" , type="text/css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_editor.pkgd.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_style.min.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src="/resources/js/selectize.min.js"></script>
    <script type="text/javascript" src="/resources/js/search.js"></script>
</head>
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

<body>
<div class="container">

    <div class="demo">

        <div class="control-group" id="select">
            <label for="select-state">Tags:</label>
            <select id="select-state" name="state[]" multiple class="demo-default" style="width:50%"
                    placeholder="Select a tag...">
                <c:forEach items="${tagsCloud}" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </select>
            <div id="searches">
            </div>
        </div>
        <script>
            var i = 0;
            var mas = [];
            var tags;
            var $select = $('#select-state').selectize({
                maxItems: 1,
                onChange: function (value) {
                    mas[i] = value;

                    tags = mas.join('1');
                    $.ajax({
                        url: "/findInstructions",
                        type: 'GET',
                        data: ({
                            "tags": tags
                        }), success: function (arr) {
                            document.getElementById("searches").remove();
                            var searches=document.createElement("div");
                            searches.id="searches";
                            document.getElementById("select").appendChild(searches);
                            var el = document.getElementById('searches');
                            for (var i = 0; i < arr.length; i++) {
                                var el1 = document.createElement('div');
                                var heading = document.createElement('p');
                                var content = document.createElement('p');
                                heading.innerHTML = arr[i].heading;
                                content.innerHTML = arr[i].content;
                                el1.appendChild(heading);
                                el1.appendChild(content);
                                el.appendChild(el1);
                                var btn = document.createElement("button");
                                btn.className = "btn btn-info";
                                window.alert(arr[i].id);
                                btn.setAttribute("onclick", "viewInstructions("+arr[i].id+")");
                                btn.innerText = "View more";
                                el.appendChild(btn);
                            }
                        }
                    });
                },
                create: true
            });
        </script>

    </div>

    <hr>
</div>
<script src="/resources/js/selectize.min.js"></script>
<!-- Include external JS libs. -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/mode/xml/xml.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.autocomplete.min.js"></script>
<!-- Include Editor JS files. -->
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/js/froala_editor.pkgd.min.js"></script>
<script type="text/javascript" src="/resources/js/selectize.min.js"></script>

</body>
<script>
    function viewInstructions(current) {
        window.location.replace("/viewAllSteps/" + current);
    }
</script>
</html>