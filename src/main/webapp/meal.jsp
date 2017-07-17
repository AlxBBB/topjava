
<%--
  Created by IntelliJ IDEA.
  User: AlxB
  Date: 7/18/2017
  Time: 12:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="POST" action='meals' name="edit">
        Код : <input type="number" readonly="readonly" name="id"  value="${meal.id}" required="required" /> <br />
        Описание : <input type="text" name="description"  value="${meal.description}" required="required" /> <br />
        Время <input type="datetime-local" pattern="dd-MM-yyyy HH:mm" name="dateTime"  value="${meal.dateTime}" required="required"/> <br />
        Калорийность : <input type="number" name="calories" value="${meal.calories}" required="required"/> <br />
        <p>
            <input type="submit" name="button" value="Сохранить"/>
            <input type="submit" name="button" value="Отмена">
        </p>
    </form>
</body>
</html>
