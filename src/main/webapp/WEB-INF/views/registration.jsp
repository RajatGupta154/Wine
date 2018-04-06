<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: andreipop
  Date: 3/21/2018
  Time: 1:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/registration.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/message-errors.css" />"/>
</head>
<body>
<c:url value="/register" var="registerURL"/>
<form:form method="post" modelAttribute="user" action="${registerURL}">

    <div class="container">
        <h1>Sign Up</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for ="name"><b>Name</b></label>
        <form:errors path="name" id="error-message" />
        <form:input path="name" type="text" id="name" placeholder="Enter Name: "/>


        <label for ="phoneNumber"><b>Phone Number</b></label>
        <form:errors path="phoneNumber" id="error-message"/>
        <form:input path="phoneNumber" type="text" id="phoneNumber" placeholder="Enter Phone Number: "/>

        <label for="email"><b>Email</b></label>
        <form:errors path="email" id="error-message"/>
        <form:input path="email" type="text" id="email" placeholder="Enter Email:"/>

        <label for="password"><b>Password</b></label>
        <form:errors path="password" id="error-message"/>
        <form:input path="password" type="password" id="password" placeholder="Enter Password: "/>

        <label for="matchingPassword"><b>Repeat Password</b></label>
        <form:errors path="matchingPassword" id="error-message"/>
        <form:input path="matchingPassword" type="password" id="matchingPassword" placeholder="Retype Password:" />

        <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

        <div class="clearfix">
            <div>
                <a href="<c:url value="/home"/>">
                <button type="button" class="cancelbtn">Cancel</button>
                </a>
            </div>
            <button type="submit" class="signupbtn">Sign Up</button>
        </div>
    </div>
</form:form>
</body>
</html>
