<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>
    <table id="table">
         <thead>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Amount</th>
                <th>Total</th>
                <th>Actions</th>
            </tr>
         </thead>
        <tbody>
        <spring:eval expression="@environment.getProperty('servlet.get.url')" var="servletURL" />
        <c:set var="total" value="${0}"/>
        <c:forEach items="${cart.listOfEntries}" var="cartEntry">
            <c:set var="totalProduct" ><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${cartEntry.quantity * cartEntry.product.price}"/></c:set>
            <c:set var="total" value="${total + totalProduct}" />
            <form id="products" action="<c:url value = "/cart-page" />" method="POST" >
                <tr>
                    <td>
                        <img src="${servletURL}${cartEntry.product.picture}" width="150" height="150" >
                        ${cartEntry.product.name}
                        ${cartEntry.product.brand}
                    </td>
                    <td>
                        ${cartEntry.product.price}
                    </td>
                    <td>
                        <c:if test="${cartEntry.product.stock == null||cartEntry.product.stock == 0}">
                            <i><font color="red"> out of stock!</font></i>
                            <button type="submit" class="button" name="deleteId" value="${cartEntry.entryId}" formaction="/wine-1/cart-page/delete" formmethod="post">Delete</button>
                        </c:if>
                        <c:if test="${cartEntry.product.stock != null && cartEntry.product.stock > 0}">
                            <input type="number" name="amount" value='${cartEntry.quantity}' min="0" />
                            <input type="hidden" name="entryId" value='${cartEntry.entryId}' />
                            <input type="hidden" name="cartId" value="${cart.cartId}" />

                    </td>
                    <td>
                        <c:out value="${totalProduct}"/>
                    </td>
                    <td>
                        <button type="submit" class="button" name="updateId" value="${cartEntry.entryId}" formaction="/wine-1/cart-page/update" formmethod="post">Update quantity</button>
                        <button type="submit" class="button" name="deleteId" value="${cartEntry.entryId}" formaction="/wine-1/cart-page/delete" formmethod="post">Delete</button>
                    </td>
                    </c:if>
                </tr>
            </form>
        </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td>
                    <h1>
                        Subtotal<br>
                        ${cart.totalCost}
                    </h1>
                    <h1>
                        TVA<br>
                        ${cart.TVA}
                    </h1>
                </td>
            </tr>
        </tfoot>
    </table>
    <a href="<c:url value = "/shop/1"/>"><button>RETURN TO SHOP</button></a>
</body>
</html>
