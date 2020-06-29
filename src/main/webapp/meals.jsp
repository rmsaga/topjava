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
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <jsp:useBean id="mealsTO" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsTO}" var="item">
        ${item.isExcess() ? "<tr class = excess>" : "<tr>"}
        <td>${item.getId()}</td>
        <td>
            <fmt:parseDate value="${item.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
        </td>
        <td>${item.getDescription()}</td>
        <td>${item.getCalories()}</td>
        <td><a href="?action=edit&id=<c:out value="${item.getId()}"/>">Update</a></td>
        <td><a href="?action=delete&id=<c:out value="${item.getId()}"/>">Delete</a></td>
        </tr>

    </c:forEach>

</table>
<p><a href="?action=add">Add meal</a></p>
</body>
</html>