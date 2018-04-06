<%--
  Created by IntelliJ IDEA.
  User: sammuntean
  Date: 3/13/2018
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/add-button.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/wines-table.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">

</head>
<body>
<layout:header-backoffice/>

<form method="get" action= <c:url value="/addProduct"/>>
    <input type="submit" class="button" value="Add wine">
</form>
<form method="POST" action= <c:url value="/importProducts"/> enctype="multipart/form-data">
    Upload .csv file: <input  type="file" name="file"><input type="submit"  value="Upload">
</form>

<button type="submit" form="table" formaction="/wine-1/deleteAll" formmethod="post" value="Submit">Delete All</button>

<button type="submit" form="table" formaction="/wine-1/exportAll" formmethod="post" value="Submit">Export All</button>

<form id="table">
    <table id="wines">
        <caption><h1>WINES</h1></caption>
        <thead>
        <tr>
            <th>Select</th>
            <th>ID</th>
            <th>Name</th>
            <th>Brand</th>
            <th>Stock</th>
            <th>Price</th>
            <th colspan="2">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${wineList}" var="wine">
            <tr>
            <td>
                <input type="checkbox" name="getAll" value="${wine.wineId}"/>
            </td>
                <td>${wine.wineId}</td>
                <td>${wine.name}</td>
                <td>${wine.brand}</td>
                <td>${wine.stock}</td>
                <td>${wine.price}</td>
                <td>
                    <button type="submit" class="button" formaction="<c:url value="/wine-edit/${wine.wineId}"/>" formmethod="get">Edit</button><br>
                </td>
                <td>
                    <button type="submit"class="button" name="deleteId" value="${wine.wineId}" formaction="/wine-1/delete" formmethod="post">Delete</button>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>

<layout:footer/>
</body>
</html>
