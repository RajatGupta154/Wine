<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
</head>
<body>

<h3> Oops, something went wrong. </h3>

<img src="<c:url value="/resources/images/sad-puppy-face.jpg"/>" alt="No photo"/>

<br>

<a href="<c:url value='/home'/>">
    <button>Go back to homepage</button>
</a>

</body>
</html>
