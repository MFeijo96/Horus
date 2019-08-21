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
    	setTimeout(function(){      
            $('#quiz').show();
            $('#loadbar').fadeOut();
           /* something else */
    	}, 500);
    });

    $ans = 3;     
});	
