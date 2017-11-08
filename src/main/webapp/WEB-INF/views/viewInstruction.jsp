<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body onload="hide(${currentStep.number})">
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
<p><textarea style="resize: none" rows="4" cols="100" id="comments"></textarea></p>
<button onclick="addComment()">Comment</button>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
    function first() {
        window.location.replace(0);
    }
    function hide(number) {
        if(number===undefined){
            var step=document.getElementsByClassName("step");
            for(var i=0;i<=step.length;i++) {
                step[i].style.display= 'none';
                document.getElementById("prev").style.display='none';
            }
        }else{
            var steps=document.getElementsByClassName("steps");
            if(number===${lastStep}){
                document.getElementById("next").style.display='none';
            }
            for(var i=0;i<steps.length;i++) {
                steps[i].style.display= 'none';
            }
        }
    }
    function viewStep(number) {
        // window.alert(${contextPath});
        window.location.replace(number);
    }
    function next(number) {
        if(number===undefined)
        {
            window.location.replace(1);
        }else {
            window.location.replace(number + 1);
        }
    }
    function prev(number) {
        window.location.replace(number-1);
    }
function addComment() {
        var el=document.getElementById("comments");
        window.alert(el.value);
    $.ajax({
        url: "/addComment",
        type: 'GET',
        data: ({
            "instructionsId": ${instruction.id},
            "content":el.value
        }),success:function (str) {
            window.alert(str);
        }
    });
    window.alert(el.value);
}
</script>
</html>