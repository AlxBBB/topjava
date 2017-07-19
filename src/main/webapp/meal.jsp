
<%--
  Created by IntelliJ IDEA.
  User: AlxB
  Date: 7/18/2017
  Time: 12:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<link rel="stylesheet" type="text/css" href="css/style.css">
<head>
    <title>${meal.id==0?"Новая запись":"Редактирование"}</title>
</head>
<body>
    <div align="center">
        <h1>${meal.id==0?"Новая запись":"Редактирование"}</h1>
    </div>
    <form method="POST" action='meals' name="edit" class="div_block">
        <input type="number" hidden name="id"  value="${meal.id}" /> <br />
        Описание : <input type="text" name="description"  value="${meal.description}" /> <br />
        Время <input type="datetime-local" pattern="dd-MM-yyyy HH:mm" name="dateTime"  value="${meal.dateTime}" required="required"/> <br />
        Калорийность : <input type="number" name="calories" value="${meal.calories}" required="required" /> <br />
        <p align="center">
            <input type="submit" name="button" value="Сохранить"/>
            <input type="submit" name="button" value="Отмена">
        </p>
    </form>
</body>
</html>
