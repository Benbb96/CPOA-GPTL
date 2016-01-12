<?php

require("../Include/functions.php");

if(!isset($_SESSION['auth'])) header("Location: ../index.php");

$nom = strtoupper($_SESSION['auth']->nomCompte);
$prenom = ucfirst($_SESSION['auth']->prenomCompte);

$titre = "Reservation Joueurs";

$ListeOperations = new ListeOperations();

require("../Include/header.php");
require("../Views/view_joueurs.php");
require("../Include/footer.php");


?>