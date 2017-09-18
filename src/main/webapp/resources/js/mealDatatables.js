var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function setForms() {
    $('#startDate').datetimepicker({
        format:'YYYY-MM-DD'
    });
    $('#startTime').datetimepicker({
        format:'HH:mm:ss'
    });
    $('#endDate').datetimepicker({
        format:'YYYY-MM-DD'
    });
    $('#endTime').datetimepicker({
        format:'HH:mm:ss'
    });
    $('#dateTime').datetimepicker({
        format:'YYYY-MM-DD HH:mm:ss'
    });
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.replace("T"," ");
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).addClass("exceeded");
            }
            else {
                $(row).addClass("normal");
            }
        },
        "initComplete":makeEditable
    });
    setForms();
});


