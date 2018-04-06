<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add Product</title>
    <style>
        .error {
            color: #ff0000;
            font-style: italic;
            font-weight: bold;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/payment-form.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">
</head>
<body>
<layout:header-backoffice/>

<c:url var="addUrl" value="/addProduct"/>
<form:form action="${addUrl}" method="post" modelAttribute="productDTO" enctype="multipart/form-data">
    <h1>Add product form</h1>
    <p>
        Name: <br>
        <form:input  path="name" name="name" id ="name"/>
        <form:errors path="name" cssClass="error"/>
        <br>
    </p>
    <p>
        Brand: <br>
        <form:input  path="brand" name="brand" id = "brand"/>
        <form:errors path="brand" cssClass="error"/>
        <br>
    </p>
    <p>
        Price: <br>
        <form:input  path="price" id="price" name="price" step="0.1"/>
        <form:errors path="price" cssClass="error"/>
        <br>
    </p>
    <p>
        Stock: <br>
        <form:input path="stock" id="stock" name="stock"/>
        <form:errors path="stock" cssClass="error"/>
        <br>
    </p>
    <p>
        Type: <br>
        <form:select path="wineType" id="wineType" name="wineType">
            <form:option value="red">Red</form:option>
            <form:option value="rose">Rose</form:option>
            <form:option value="sparkling">Sparkling</form:option>
            <form:option value="white">White</form:option>
        </form:select><br>
    </p>
    Category: <br>
    <select name="category" requires>
        <c:if test="${! empty categories}">
            <c:forEach items="${categories}" var = "current">
                <option value=${current.name}>${current.name}</option>
            </c:forEach>
        </c:if>
    </select><br>
    <p>
        Country of origin: <br>
        <form:input path="countryOfOrigin" id="countryOfOrigin" name="countryOfOrigin"/>
        <form:errors path="countryOfOrigin" cssClass="error"/> <br>
    </p>
    <p>
        Year of production: <br>
        <form:input path="yearOfProduction" id="yearOfProduction" name="yearOfProduction"/>
        <form:errors path="yearOfProduction" cssClass="error"/> <br>
    </p>
    <p>
        Volume: <br>
        <form:input path="volume" id="volume" name="volume" step="0.1"/>
        <form:errors path="volume" cssClass="error"/> <br>
    </p>
    <p>
        Description: <br>
        <form:textarea path="description" id="description" name="description"/> <br>
    </p>
    <p>
        Recommendations: <br>
        <form:textarea path="recommendations" id="recommendations" name="recommendations"/> <br>
    </p>

    <p>
        Please load a picture for the product:
        <input type="file" name="picture" accept="image/x-png,image/jpeg" />
    </p>

    <p>
        <form:button id="addProductButton" type="submit">Add</form:button>
    </p>
</form:form>

<layout:footer/>
</body>
</html>