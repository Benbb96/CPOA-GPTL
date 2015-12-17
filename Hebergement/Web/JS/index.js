$(document).ready(function(){
    
    $("#form_inscription").hide();
    
    centerObject();
    
    $("body").css({ "height":$(window).innerHeight() });
    
    $(window).resize(function() {
        centerObject();
        $("body").css({ "height":$(window).innerHeight() });
    });
    
});