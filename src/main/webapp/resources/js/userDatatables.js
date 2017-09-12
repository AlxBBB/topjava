var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
});

function getUrl() {
    return ajaxUrl;
}

function customSet() {
    $("#datatable :checkbox").click(function () {
        onOffRow($(this).closest("tr").attr("id"));
    });
}

function onOffRow(id) {
    var enabled=$("#"+id+" :checkbox").attr("checked") != 'checked';
   // alert(enabled+" "+id);
    $.ajax({
        url: ajaxUrl +"enabled/"+ id+"/"+enabled,
        type: "POST",
        success: function () {
            updateTable();
            successNoty("Check");
        }
    });
}