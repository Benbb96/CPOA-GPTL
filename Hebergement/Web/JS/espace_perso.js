$(document).ready(function(){

    
    $("#defis").hide();
    $("#progression").hide();

    $("#onglet_infos").click(function(){
        $("#infos").show();
	    $("#defis").hide();
	    $("#progression").hide();
    });

    $("#onglet_defis").click(function(){
        $("#infos").hide();
	    $("#defis").show();
	    $("#progression").hide();
    });

    $("#onglet_progression").click(function(){
        $("#infos").hide();
	    $("#defis").hide();
	    $("#progression").show();
    });

    $("#form_infos_perso_modif").hide();
    
    centerObject();
    
});