<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>
        <div class="row">
            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form class="form-horizontal" id="filter">
                            <div class="form-group">
                                <label class="control-label" for="startDate" id="lStartDate"><spring:message
                                        code="meal.startDate"/>:</label>
                                <div class='input-group date' id="startDate">
                                    <input  name="startDate" class="form-control" placeholder="YYYY-MM-DD"/>
                                    <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <label class="control-label" for="startTime" id="lStartTime"><spring:message
                                        code="meal.startTime"/>:</label>

                                <div class='input-group date'  id="startTime">
                                    <input  name="startTime" class="form-control" placeholder="HH:mm:ss"/>
                                    <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="startDate" id="lEndDate"><spring:message
                                        code="meal.endDate"/>:</label>
                                <div class='input-group date' id="endDate">
                                    <input name="endDate" class="form-control" placeholder="YYYY-MM-DD"/>
                                    <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <label class="control-label" for="startTime" id="lEndTime"><spring:message
                                        code="meal.endTime"/>:</label>
                                <div class='input-group date'  id="endTime">
                                    <input name="endTime" class="form-control" placeholder="HH:mm:ss"/>
                                    <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer text-right">
                        <a class="btn btn-danger" type="button" onclick="clearFilter()">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                        <a class="btn btn-primary" type="button" onclick="updateTable()">
                            <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                        </a>
                    </div>

                </div>
            </div>
        </div>
        <a class="btn btn-primary" onclick="add()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="common.add"/>
        </a>
        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"><spring:message code="meal.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message
                                code="meal.dateTime"/></label>
                        <div class='input-group date' id="dateTime">
                            <input name="dateTime" class="form-control" placeholder="<spring:message code="meal.dateTime"/>">
                            <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"/>
                            </span>
                        </div>

                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message
                                code="meal.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description"
                                   placeholder="<spring:message code="meal.description"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message
                                code="meal.calories"/></label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories"
                                   placeholder="1000">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="save()">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>


<script type="text/javascript">
    var i18n = [];
    i18n["addTitle"] = '<spring:message code="user.add"/>';
    i18n["editTitle"] = '<spring:message code="user.edit"/>';
    <%@ include file="fragments/i18nKeys.jspf"%>
</script>
</html>