<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link href="styles/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<h3><a href="index.html">Home</a></h3>

<h2>Meals list</h2>

<table>
    <tr>
        <th>Date</th>
        >
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <jsp:useBean id="mealsTO" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsTO}" var="item">
        ${item.isExcess() ? "<tr class = excess>" : "<tr>"}
        <td>
            <fmt:parseDate value="${item.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
        </td>
        <td>${item.getDescription()}</td>
        <td>${item.getCalories()}</td>
        </tr>

    </c:forEach>

</table>

</body>
</html>