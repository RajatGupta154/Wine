<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>


<html>
<head>
    <title>Welcome to the wine gallery!</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">
</head>
<body>
<layout:header-backoffice/>

<div style="text-align:  right">
    <c:url value="/cart-page" var="cartPageLink"/>
    <a href="${cartPageLink}" class="not-underlined">
        <img src="<c:url value="/resources/images/minicart.png"/>" class="cart-logo"/>
        <span class="cart-count">${nrProductsFromCart}</span>&nbsp;&nbsp;&nbsp;&nbsp;
    </a>
</div>

<div>
    <img src="<c:url value="/resources/images/logo.png"/>"/>
</div>

<layout:footer/>
</body>
</html>

