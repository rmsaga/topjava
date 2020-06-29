<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="ru">
<head>
    <title>Updating or Adding meal </title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="POST" action='meals' name="frmAddUpdateMeal">
    <input type="hidden" name="action" value=${param.action}>
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="${meal.id == null ? "Will be generated" : meal.id}"/><br><br>
    Date : <input
        type="text" name="date"
        <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" var="mealDate"/>
        value="${mealDate}"/> <i>dd.MM.yyyy HH:mm</i><br><br>
    Description : <input
        type="text" name="description"
        value="${meal.getDescription()}"/>
    <br><br>
    Calories : <input
        type="text" name="calories"
        value="${meal.getCalories()}"/>
    <br><br>
    <input
            type="submit" value="Submit"/>
</form>
</body>
</html>