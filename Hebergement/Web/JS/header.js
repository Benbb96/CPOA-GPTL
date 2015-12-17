$(document).ready(function(){
    
    var url = $(location).attr('pathname');
    
    $("header nav ul li").each(function(){
        if(("/InforMATHique/Controller/"+$(this).attr("link"))==url){
            $(this).addClass("active");
        }
    });
    
    $("header nav ul li").click(function(){
        var link = $(this).attr("link");
        document.location.href=link; 
    });
});