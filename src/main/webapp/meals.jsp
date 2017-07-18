<%--
  Created by IntelliJ IDEA.
  User: AlxB
  Date: 7/15/2017
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <title>Список еды</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
<body>
<div align="center">
    <h1>Список еды</h1>
</div>
<div align="center">
    <table>
        <tr>
            <th>Наименование</th>
            <th>Время приема</th>
            <th>Калорийность</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">

        <tr class=${meal.exceed?"exceed":"norm"}>
            <td>
                ${meal.description}
            </td>
            <td>
                ${meal.dateTime.format(localDateTimeFormat)}
            </td>
            <td class="num_td">
                ${meal.calories}
            </td>
            <td><a href="meals?action=edit&id=${meal.id}">Изменить</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
        </c:forEach>
    </table>
    <br/>
    <div>
        <form action="meals" method="get">
            <button type="submit" name="action" value="add">Добавить</button>
            <button type="submit" name="action" value="exit">Вернуться</button>
        </form>
    </div>
</div>

</body>
</html>
