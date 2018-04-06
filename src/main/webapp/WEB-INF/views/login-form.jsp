<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login-form.css"/>">
</head>
<body>
<c:if test="${success}">
    <h2 align="center">You have succesfully registered to this website!</h2>
</c:if>
<div class="main-container">

<form method="post" action="<c:url value="j_spring_security_check"/>">
    <div class="img-container">
        <img src="<c:url value="/resources/images/img-avatar.png"/>" alt="Avatar" class="avatar"/>
    </div>

    <div class="container">
        <label for="email"><b>Email</b></label>
        <input type="email" placeholder="Enter Email" name="email" id="email" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="password" required>

        <button type="submit">Login</button>

    </div>

    <div class="container" style="background-color:#f1f1f1">
        <a href="<c:url value='/login'/>">
            <button type="button" class="cancel-button">Cancel</button>
        </a>
        <a href="<c:url value ='/register' />">
            <button type="button" class="registerBtn">Register</button>
        </a>
        <a href="<c:url value='/shop/1'/>">
            <button type="button" class="guest-button">Continue as guest</button>
        </a>

    </div>
</form>
</div>
</body>
</html>
