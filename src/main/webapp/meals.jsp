<%--
  Created by IntelliJ IDEA.
  User: AlxB
  Date: 7/15/2017
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>


    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
<body>
<header>Список еды</header>
<div>
    <table>
        <tr>
            <th>Наименование</th>
            <th>Время приема</th>
            <th>Калорийность</th>
        </tr>
        <c:forEach items="${meals}" var="meal">

        <tr class=${meal.exceed?"exceed":"norm"}>
            <td>
                <c:out value="${meal.description}"/>
            </td>
            <td>
                <javatime:format value="${meal.dateTime}" pattern="dd-MM-YYYY HH:mm" var="formatDate" />
                <c:out value="${formatDate}"/>
            </td>
            <td class="num_td">
                <c:out value="${meal.calories}"/>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
