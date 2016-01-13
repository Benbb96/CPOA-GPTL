<section id="container">
<link rel= "stylesheet" type="text/css" href="../Web/CSS/Fichier-css.css" />
<HTML>
<meta charset = "utf-8">
<HEAD>

<div id="bouton"> 
	<a href="reservation_arbitres.php"> <div href="reservation_arbitres.php" class="button">Réserver un logement pour Arbitres</div>
</div>
<br>
<div id="bouton">
	<a href="reservation_joueurs.php"> <div class="button">Réserver un logement pour Joueurs</div>
</div> 
 <br>


<div id="joueurs">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Joueurs</legend>
	<?php
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
$query=$bdd->prepare('SELECT *
					   FROM joueurs'
					   );
 $query->execute();
 
while($data = $query->fetch()) {
	echo('<br>');
	echo('Nom :'.$data['nomJoueur']);
	echo('<br>');
	echo('Prenom :'.$data['prenomJoueur']);
	echo('<br>');
	echo('Classement :'.$data['classementJoueur']);
	echo('<br>');
	echo('Nombre de places réservés :'.$data['nbr_lits_reserv']);
	echo('<br>');
 }
$query->closeCursor();
?>
</div>

<div id="arbitres">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Arbitres</legend>
	<?php
	
$bdd=Connect_db();
$query=$bdd->prepare('SELECT *
					   FROM arbitres'
					   );
 $query->execute();
 
while($data = $query->fetch()) {
	echo('<br>');
	echo('Nom :'.$data['nomArbitre']);
	echo('<br>');
	echo('Prenom :'.$data['prenomArbitre']);
	echo('<br>');
	echo('Attribut :'.$data['attributArbitre']);
	echo('<br>');
	echo('Nombre de places réservés :'.$data['nbr_lits_reserv']);
	echo('<br>');
 }
$query->closeCursor();
?>

	</fieldset>
</div>

</script> 
</BODY>
</HTML>
</section>