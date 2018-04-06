<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navBar">
    <a href="<c:url value="/shop/1"/>" class="menu-links">SHOP</a>
    <a href="<c:url value = "/home"/>" class="menu-links">BACKOFFICE</a>
    <a href="<c:url value="/login" />" class="logging-link menu-links">Login</a>
    <a href="<c:url value="/register" />" class="logging-link menu-links">Register</a>
</nav>

<div style="text-align:  right">
    <c:url value="/cart-page" var="cartPageLink"/>
    <a href="${cartPageLink}" class="not-underlined">
        <div>
            <img src="<c:url value="/resources/images/minicart.png"/>" class="cart-logo"/>
            <span class="cart-count">${nrProductsFromCart}</span>&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
    </a>
</div>
