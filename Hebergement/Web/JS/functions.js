function centerObject(){
    
    var obj = $(".center");
    var windowHeight = $(window).innerHeight();
    var marginTopAbsolu = (windowHeight/2) - (obj.innerHeight()/2);
    var marginTop = marginTopAbsolu - obj.position().top;
    
    if(marginTop<50) marginTop = 50;
    
    obj.css({ "margin-top" : marginTop+"px" });
}

function switchObject(obj1,obj2){
    obj1.hide();
    obj2.show();
    centerObject();
}


