//$('#myModal').modal('show');

$('#formSel').change( function() {
    var id = $(this).val();
    var zindex = -10;
    if( id != '-' )
    {
        $('form').hide();
        $('#form'+id).show();
        $('#myModal').modal('show');
    }
});
