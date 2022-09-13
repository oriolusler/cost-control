$(document).ready(function () {
    $('#showPendingCostsTableId').DataTable({
        "bInfo": false, "pagingType": "full_numbers", "bLengthChange": false,
    });
    $('.dataTables_length').addClass('bs-select');
});