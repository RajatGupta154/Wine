<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navBar">
    <a href="<c:url value = "/home"/>" class="menu-links">HOME</a>
    <a href="<c:url value="/wines"/>" class="menu-links">WINES</a>
    <a href="<c:url value = "/categories"/>" class="menu-links">CATEGORIES</a>
    <a href="<c:url value="/shop/1"/>" class="menu-links">SHOP</a>
    <a href="<c:url value="j_spring_security_logout" />" class="logging-link menu-links"> Logout</a>
    <p class="welcome">Welcome, ${userName}</p>
</nav>