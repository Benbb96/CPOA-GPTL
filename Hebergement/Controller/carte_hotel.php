<?php

require("../Include/functions.php");

if(!isset($_SESSION['auth'])) header("Location: ../index.php");

$nom = strtoupper($_SESSION['auth']->nomCompte);
$prenom = ucfirst($_SESSION['auth']->prenomCompte);

$titre = "Carte Hôtels";
require("../Include/header.php");
require("../Views/view_carte_hotel.php");
require("../Include/footer.php");


?>