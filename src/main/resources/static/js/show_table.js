$(document).ready(function () {
    $('#showTableId').DataTable({
        "bInfo": false,
        "pagingType": "full_numbers",
        "bLengthChange": false,
        "aaSorting": [],
        columnDefs: [{
            orderable: false,
            targets: 2
        }, {
            orderable: false,
            targets: 3
        }, {
            orderable: false,
            targets: 4
        }]
    });
    $('.dataTables_length').addClass('bs-select');
});