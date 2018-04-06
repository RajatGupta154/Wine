<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<html>
<head>
<title>Import</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-bar.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/footer.css"/>">
</head>

<body>
<layout:header-backoffice/>

<p>Successfully exported products.</p>
<form method="get" action= <c:url value="/wines"/>>
        <input type="submit"  value="Back">
    </form>

<layout:footer/>
</body>
</html>