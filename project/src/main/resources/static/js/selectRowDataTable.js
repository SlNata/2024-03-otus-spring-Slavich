$('#pagin-table tbody').on( 'click', 'tr', function () {
    if ($(this).hasClass('selected')) {
        $(this).removeClass('selected');
    } else {
        DataTable.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
} );