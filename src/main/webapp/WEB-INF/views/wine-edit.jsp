<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit product</title>
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

<c:url var="editUrl" value="/wine-edit"/>

<form:form  method="post" action="${editUrl}" modelAttribute="productDTO" enctype="multipart/form-data">
    <h1>Edit form</h1>

    <p>Required fields are followed by <strong><abbr title="required">*</abbr></strong>.</p>

    <section>
        <h2> Contact information </h2>

        <p>
            <label for="id">
                <span>Id: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input  path="wineId" id="id" name="wineId"  readonly="true" value="${productDTO.wineId}"/>
        </p>

        <p>
            <label for="name">
                <span>Name: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="name" id="name" name="name" value="${productDTO.name}"/>
            <form:errors path="name" cssClass="error"/>
        </p>

        <p>
            <label for="brand">
                <span>Brand: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="brand" id="brand" name="brand" value="${productDTO.brand}"/>
            <form:errors path="brand" cssClass="error"/>
        </p>

        <p>
            <label for="price">
                <span>Price: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="price" id="price" name="price" value="${productDTO.price}"/>
            <form:errors path="price" cssClass="error"/>
        </p>

        <p>
            <label for="category">
                <span>Category: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <select name="category" requires>
                <c:if test="${! empty categories}">
                    <c:forEach items="${categories}" var = "current">
                        <option value=${current.name}>${current.name}</option>
                    </c:forEach>
                </c:if>
            </select><br>
        </p>

        <p>
            <label for="stock">
                <span>Stock: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="stock" id="stock" name="stock" value="${productDTO.stock}"/>
            <form:errors path="stock" cssClass="error"/>
        </p>

        <p>
            <label for="type">
                <span>Type: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:select  path="wineType" id="type" name="wineType">
                <form:option value="red"> Red </form:option>
                <form:option value="rose"> Rose </form:option>
                <form:option value="sparkling"> Sparkling </form:option>
                <form:option value="white"> White </form:option>
            </form:select>
        </p>

        <p>
            <label for="Country">
                <span>Country: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="countryOfOrigin" id="Country" name="countryOfOrigin" value="${productDTO.countryOfOrigin}"/>
            <form:errors path="countryOfOrigin" cssClass="error"/> <br>
        </p>

        <p>
            <label for="Year">
                <span>Year of made: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="yearOfProduction" id="Year" name="yearOfProduction" value="${productDTO.yearOfProduction}"/>
            <form:errors path="yearOfProduction" cssClass="error"/> <br>
        </p>

        <p>
            <label for="volume">
                <span>Volume: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:input path="volume" id="volume" name="volume"  value="${productDTO.volume}"/>
            <form:errors path="volume" cssClass="error"/> <br>
        </p>
    </section>
    <section>
        <h2> More information </h2>

        <p>
            <label for="Description">
                <span>Description: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:textarea path="description" id="description" name="description" value="${productDTO.description}"/>
        </p>

        <p>
            <label for="recommendations">
                <span>Recommendation: </span>
                <strong><abbr title="required">*</abbr></strong>
            </label>
            <form:textarea path="recommendations" id="recommendations" name="recommendations" value="${productDTO.recommendations}"/>
        </p>

        <p>
            Please load a picture for the product:
            <input type="file" name="picture" accept="image/x-png,image/jpeg" />
        </p>
    </section>
    <section>
        <p>
            <form:button type="submit">Validate the edit</form:button>
        </p>
    </section>
</form:form>

<layout:footer/>
</body>
</html>
