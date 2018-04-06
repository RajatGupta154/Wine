<%--
  Created by IntelliJ IDEA.
  User: sammuntean
  Date: 3/27/2018
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Picture exception</title>
</head>
<body>
    <c:catch var="exception">Product wasn't added. ${pictureException}</c:catch>
</body>
</html>
