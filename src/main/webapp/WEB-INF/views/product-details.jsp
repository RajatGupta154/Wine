<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Product Details</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/view-details.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">

</head>
<body>
<layout:header-clients/>

<spring:eval expression="@environment.getProperty('servlet.get.url')" var="servletURL" />
<div class="product-details-wrapper button-right-wrapper">
    <img src="${servletURL}${wine.picture}" width="400" >
    <p>  <b>Name:  </b> ${wine.name} </p>
    <p>  <b>Brand: </b> ${wine.brand} </p>
    <p>  <b>Price: </b> ${wine.price}  </p>
    <p>  <b>Type of wine: </b> ${wine.wineType}  </p>
    <p>  <b>Country of origin: </b> ${wine.countryOfOrigin}  </p>
    <p>  <b>Year of production: </b> ${wine.yearOfProduction}  </p>
    <p>  <b>Volume: </b> ${wine.volume}  </p>
    <p>  <b>Description: </b> ${wine.description}  </p>
    <p>  <b>Recommendations: </b> ${wine.recommendations}  </p>
    <p>  <b>Rating: </b> ${average} </p>
    <form method="POST" action="<c:url value="/addToCart"/>">
        <button type="submit" name="productId"  value="${wine.wineId}">Add to cart</button>
        <input type="number"  name="quantity"  min="1" value="1"/>
    </form>
    <c:forEach items="${reviewsList}" var="review">
        <p> ${review.rating}* : ${review.review} </p>
    </c:forEach>
    <button class="button-right" onclick="window.history.back()">Back</button>
</div>

<layout:footer/>
</body>
</html>
