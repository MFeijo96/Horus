$(function(){
    var loading = $('#loadbar').hide();
    $(document)
    .ajaxStart(function () {
        loading.show();
    }).ajaxStop(function () {
    	loading.hide();
    });
    
    $("label.btn").on('click',function () {
    	$('#loadbar').show();
    	$('#quiz').fadeOut();
    });

    $ans = 3;     
});	
