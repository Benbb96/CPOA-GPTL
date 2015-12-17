<?php
require("../Models/ConnexionBD.php");
require("../Models/Message.php");
require("../Models/Operations.php");
$bdd = ConnexionBD::getInstance();

session_start();

function str_random($length){
    $chaine = "0123456789azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN";
    return substr(str_shuffle(str_repeat($chaine,$length)), 0, $length);
}
?>