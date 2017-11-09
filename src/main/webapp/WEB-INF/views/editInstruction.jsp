<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include external CSS. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">

    <!-- Include Editor style. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_editor.pkgd.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_style.min.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
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

<main role="main">
    <div class="jumbotron">
        <div class="container">
            <div>

                <form action="" method="post">
                    <p><b>Heading</b></p>
                    <p><textarea minlength="3" maxlength="15" rows="2" cols="100"
                                 id="heading">${instruction.heading}</textarea></p>
                    <%--TODO:Static--%>
                </form>

            </div>

            <form action="" method="post">
                <p><b>Descriprtion</b></p>

                <textarea style="background-color: #FFF;" minlength="3" maxlength="150" rows="10" cols="100"
                          id="content">${instruction.content}</textarea>
                <br>
                <div>
                    <input type="text" id="w-input-search" style="size: auto">
                    <span>
			        <button id="w-button-search" type="button"
                            onclick="addtags(document.getElementById('w-input-search').value)">Add Tag</button>
		            </span>
                </div>
            </form>
            <div id="steps">
                <c:forEach items="${steps}" var="item">
                    <h3>Step ${item.number}</h3>
                    <textarea class="steps" style="background-color: #FFF;" minlength="3" maxlength="150" rows="10" cols="100"
                              id="content">${item.content}</textarea>
                    <br>
                </c:forEach>
            </div>
        </div>


        <p>
            <button onclick="saveInstruction();saveSteps()">Save</button>
        </p>
        <button onclick="addNewStep()">AddNewStep</button>
    </div>
</main>
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

<!-- Initialize the editor. -->
<script>
    $(function () {
        $('textarea#content').froalaEditor({
            toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', 'color', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'indent', 'outdent', '-', 'insertImage', 'insertLink', 'insertFile', 'insertVideo', 'undo', 'redo']
        })
    });
</script>
</body>
<script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>

<script>
    function saveInstruction() {
        var heading = document.getElementById('heading');
        var content = document.getElementById('content');
        $.ajax({
            url: "/editInstructions",
            type: 'GET',
            data: ({
                "instructionId":${instruction.id},
                "heading": heading.value,
                "content": content.value
            })
        });
    }
    function saveSteps() {
        var steps = document.getElementsByClassName('steps');
        for (var i = 0; i <number; i++) {
            var str = steps[i].value;
            window.alert(str);
            $.ajax({
                url: "/editStep",
                type: 'GET',
                data: ({
                    "number": i + 1,
                    "instructionId":${instruction.id},
                    "content": steps[i].value
                })
            });
        }
    }
    var number = ${steps.size()}+1;
    function addNewStep() {
        var textarea = document.createElement("textarea");
        textarea.className = "steps";
        var heading = document.createElement("h3");
        heading.innerText = "Step " + number;
        var el = document.getElementById("steps");
        el.appendChild(heading);
        el.appendChild(textarea);
        el.appendChild(document.createElement("br"));
        $('textarea.steps').froalaEditor({
            toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', 'color', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'indent', 'outdent', '-', 'insertImage', 'insertLink', 'insertFile', 'insertVideo', 'undo', 'redo']
        });
        number++;
    }

    $(document).ready(function () {

        $('#w-input-search').autocomplete({
            serviceUrl: '${pageContext.request.contextPath}/getTags',
            paramName: "tagName",
            delimiter: ",",
            transformResult: function (response) {
                return {

                    suggestions: $.map($.parseJSON(response), function (response) {
                        return {value: response, data: response};
                    })
                };
            }
        });
    });

    function addtags(TAG) {
        window.alert(TAG)
        $.ajax({
            url: TAG + '/addTag',
            paramName: "tagName",
            type: 'GET'
        })
    }

</script>
</html>