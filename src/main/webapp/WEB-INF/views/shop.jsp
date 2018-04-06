<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<html>
<head >
    <title>Store</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/shop.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/view-details.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">
</head>

<body id="bodybkg" >

<layout:header-clients/>

<br><br>

<center>
    <div id = "filterOptions">
        <form action = "/wine-1/shop/${currentPage}" method = "GET">
            <input type = "submit" value = "Reset">
            <br>
        </form>
        <form class="facets" data-page="${currentPage}" data-sort="${argSort}">
            <c:forEach items="${categories}" var="current">
                <c:if test="${current.state eq true}">
                   <input onChange="this.form.submit()" type="checkbox" id="${current.name}" name="category" value="${current.name}" checked/>
                </c:if>
                <c:if test="${current.state eq false}">
                    <input onChange="this.form.submit()" type="checkbox" id="${current.name}" name="category" value="${current.name}"/>
                </c:if>
                <p>${current.name}</p>
            </c:forEach><br>

            <c:forEach items="${years}" var="current">
                <c:if test="${current.state eq true}">
                    <input onChange="this.form.submit()" id = "${current.name}" name = "year" type = "checkbox" value ="${current.name}" checked />
                </c:if>

                <c:if test="${current.state eq false}">
                    <input onChange="this.form.submit()" id = "${current.name}" name = "year" type = "checkbox" value ="${current.name}" />
                </c:if>
                <p>${current.name} : ${numberOfProductsPerYear[current.name]}</p>
            </c:forEach><br>

            <c:forEach items="${types}" var="current">
                <c:if test="${current.state eq true}">
                    <input onChange="this.form.submit()" id = "${current.name}" name = "type" type = "checkbox" value ="${current.name}" checked/>
                </c:if>

                <c:if test="${current.state eq false}">
                    <input onChange="this.form.submit()" id = "${current.name}" name = "type" type = "checkbox" value ="${current.name}" />
                </c:if>
                <p>${current.name} : ${numberOfProductsPerType[current.name]}</p>
            </c:forEach><br>
        </form>
        <br>
        <c:if test="${number ne 1}">
            <h4 style="padding-bottom: 30px">There are ${number} products</h4>
        </c:if>
        <c:if test="${number eq 1}">
            <h4 style="padding-bottom: 30px">There is one product</h4>
        </c:if>

    </div>
<div id="content">
    <img src="<c:url value="/resources/images/logo2.png"/>" width="900" >
    <div id="title">WINE SHOP</div>
    <br><br>

    <%--sort--%>
    <div class="dropdown">
        <button class="dropbtn">Sort</button>
        <div class="dropdown-content">
            <a href="<c:url value="/shop/1">
                        <c:param name="argSort" value="nameasc"/>
                        <c:param name = "category" value = "${category}"/>
                       <c:param name = "year" value = "${year}"/>
                       <c:param name = "type" value = "${type}"/>
                    </c:url>
                ">Name - Ascending
            </a>
            <a href="<c:url value="/shop/1">
                        <c:param name="argSort" value="namedesc"/>
                        <c:param name = "category" value = "${category}"/>
                       <c:param name = "year" value = "${year}"/>
                       <c:param name = "type" value = "${type}"/>
                    </c:url>
                ">Name - Descending
            </a>
            <a href="<c:url value="/shop/1">
                        <c:param name="argSort" value="priceasc"/>
                        <c:param name = "category" value = "${category}"/>
                       <c:param name = "year" value = "${year}"/>
                       <c:param name = "type" value = "${type}"/>
                    </c:url>
                ">Price - Ascending
            </a>
            <a href="<c:url value="/shop/1">
                        <c:param name="argSort" value="pricedesc"/>
                        <c:param name = "category" value = "${category}"/>
                       <c:param name = "year" value = "${year}"/>
                       <c:param name = "type" value = "${type}"/>
                    </c:url>
                ">Price - Descending
            </a>
        </div>
    </div>

    <br><br>

<table class="wines" width="900" border="1">
    <% int contor=0;%>
    <spring:eval expression="@environment.getProperty('servlet.get.url')" var="servletURL" />
    <c:forEach items="${wineList}" var="wine">
        <%if(contor%3==0){%><tr><%}%>
        <td class="wines">
            <a href="<c:url value = "/product/${wine.wineId}"/>"><img src="${servletURL}${wine.picture}" width="250" ></a>
            <br>
            <div class="left">
                &nbsp;&nbsp;&nbsp;&nbsp;<a  href="<c:url value = "/product/${wine.wineId}"/>">${wine.name}</a>
                 <br>  &nbsp;&nbsp;&nbsp;&nbsp;${wine.price} Ron
            </div>
            <form method="POST" action="<c:url value="/addToCart"/>">
                <button type="submit" name="productId"  value="${wine.wineId}">Add to cart</button>
                <input type="number"  name="quantity"  min="1" value="1"/>
            </form>
        </td>
        <%contor++;if(contor%3==0){%></tr><%}%>
    </c:forEach>
</table>
</div>
     <br><br>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a class="inactive" href="<c:url value = "/shop/${currentPage-1}">
                    <c:param name = "argSort" value = "${argSort}"/>
                    <c:param name = "category" value = "${category}"/>
                    <c:param name = "year" value = "${year}"/>
                    <c:param name = "type" value = "${type}"/>

                    </c:url>" class="inactive">&laquo;

                    </a>

                </c:if>
                <c:if test="${noPageNb eq false}">
                    <span class="active">${currentPage}</span>
                </c:if>
                <c:if test="${nextArrow}">
                    <a href="<c:url  value = "/shop/${currentPage+1}">
                        <c:param name = "argSort" value = "${argSort}"/>
                       <c:param name = "category" value = "${category}"/>
                       <c:param name = "year" value = "${year}"/>
                       <c:param name = "type" value = "${type}"/>


                        </c:url>" class="inactive">&raquo;
                    </a>
                </c:if>

            </div>
            <br><br>
            <br><br>
</center>

<layout:footer/>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/main.js"/>"></script>
</body>
</html>
