<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
<c:forEach items="${instructions}" var="item">
    <h1>${item.content}</h1>
    <a href="/editInstruction/${item.id}">Edit </a>
</c:forEach>

</body>
<script>

</script>
</html>