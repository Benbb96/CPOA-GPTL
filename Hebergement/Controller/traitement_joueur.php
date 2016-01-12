<section id="container">
<?php

require("../Include/functions.php");

if(!isset($_SESSION['auth'])) header("Location: ../index.php");

$nom = strtoupper($_SESSION['auth']->nomCompte);
$prenom = ucfirst($_SESSION['auth']->prenomCompte);

$titre = "Reservation Joueurs";
require("../Include/header.php");
require("../Include/footer.php");


function Connect_db(){
      $host="localhost";
      $user="root";
      $password="";
      $dbname="hebergement";


  try {
       $bdd=new PDO('mysql:host='.$host.';dbname='.$dbname.
                    ';charset=utf8',$user,$password);
       return $bdd; 
      } catch (Exception $e) {
       die('Erreur : Ca marche pas'.$e->getMessage());
  }
 }
$bdd=Connect_db();
$query=$bdd->prepare('INSERT INTO joueurs
					   (nomJoueur,prenomJoueur,classementJoueur,nbr_lits_reserv)
					   VALUES(?,?,?,?)'
					   );
$query->execute(array($_POST['nom_joueur'],$_POST['prenom_joueur'],$_POST['classement_joueur'],$_POST['nbr_lits']));

$var=$_POST['nbr_lits'];
$var2=$_POST['hotel'];


$reponse = $bdd->query("Update hotel_joueurs set nbr_lits_dispo = nbr_lits_dispo -\"$var\" where nom=\"$var2\"");
$reponse = $bdd->query("Update joueurs set nbr_lits_reserv =\"$var\"");

echo('Réservation effectuée');
?>
</section>

