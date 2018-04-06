<%--
  Created by IntelliJ IDEA.
  User: florinbologheanu
  Date: 3/14/2018
  Time: 2:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>Categories</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">
</head>
<body>
<layout:header-backoffice/>

    <c:if test= "${! empty success}">
        <c:if test="${success eq true}">
            <p>Success!</p>
        </c:if>

        <c:if test="${success ne true}">
            <p>Something went wrong!</p>
        </c:if>

    </c:if>

    <c:forEach var="entry" items="${Categories}">
        <br>
        Category: ${entry.key} <br>
        Number of Products: ${entry.value}<br><br>
    </c:forEach>

    <form method="post">
        Category<br>
        <input type="text" name="categoryName"><br>
        <input type="submit" value="Add new category">
    </form>

<layout:footer/>
</body>
</html>
