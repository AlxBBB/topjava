var ajaxUrl = "ajax/meals/";
var filterUrl="filter";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function getUrl() {
    var newUrl=ajaxUrl;
    if (document.location.href.indexOf(filterUrl)!==-1) {
        newUrl+=filterUrl+"?startDate="+$('input[name=startDate]').val()+
                          "&startTime="+$('input[name=startTime]').val()+
                          "&endDate="+$('input[name=endDate]').val()+
                          "&endTime="+$('input[name=endTime]').val();
    }
    return newUrl;
}

function customSet() {}