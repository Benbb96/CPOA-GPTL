<div id="menu">
</div>
<link rel="stylesheet" href="../Web/CSS/Fichier-css.css" type="text/css" />
<section id="container">

</div>
<div id="hotel1">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">La Charmance</legend>
	<form action="traitement_joueur.php" method="post">
		<label for = "nom_film"><u>Type :</u> &nbsp;Chambre d'hote</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;5 Rue du Lapin</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34970
</label><br>	
		<label for = "score"><u>Ville :</u>&nbsp;Lattes</label><br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp;<?php  function Connect_db(){
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
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_joueurs where id_Jhotel=1");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;
?>
</label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;NON</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u></label><br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;NON</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;NON</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;NON</label><br>	
	</fieldset>
</div>

<div id="hotel2">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Ibis</legend>
	<form action="traitement_joueur.php" method="post">
		<label for = "nom_film"><u>Type :</u>&nbsp;Hôtel</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;95 Place Vauban Boulevard</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34000</label><br>	
		<label for = "score"><u>Ville :</u></label>&nbsp;Montpellier<br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp;<?php
 $bdd=Connect_db();
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_joueurs where id_Jhotel=2");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;
?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;NON</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u></label>&nbsp;NON<br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;NON</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;NON</label><br>	
	</fieldset>
</div>


<div id="formulaire">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;"> Informations Joueur </legend>
	<form action="traitement_joueur.php" method="post">
		<label for = "nom_film"> Nom du Joueur  </label> <input type = "text" name = "nom_joueur"/><br>
		<label for = "annee_film"> Prénom du Joueur </label><input type= "text" name = "prenom_joueur"/><br>
		<label for = "score"> Classement du Joueur </label><input type = "text" name="classement_joueur"/><br>	
	</fieldset>
	
	<br>
	<br>
	
	<fieldset style="width:300px">
		<legend style="font-size:22px; font-family:'J.M.Nexus-Black'; font-weight:bold;"> Hôtels </legend>
		   <input type="radio" name="hotel" value="La Charmance" id="La Charmance" /> <label for="hotel1">La Charmance &nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <input type="radio" name="hotel" value="Ibis" id="Ibis" /> <label for="hotel2">Ibis&nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <input type="radio" name="hotel" value="Domaine De Verchant" id="Domaine De Verchant" /> <label for="Domaine De Verchant">Domaine De Verchant &nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <input type="radio" name="hotel" value="Hotel Courtyard" id="Hotel Courtyard" /> <label for="Hotel Courtyard">Hotel Courtyard&nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <input type="radio" name="hotel" value="Hotel Balladins" id="Hotel Balladins" /> <label for="Hotel Balladins">Hotel Balladins&nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <br>

		   <label for="nbr_lits">Nombre de lits à réserver</label><br />
		   <select name="nbr_lits" id="nbr_lits">
			   <option value="1">1</option>
			   <option value="2">2</option>
			   <option value="3">3</option>
			   <option value="4">4</option>
			   <option value="5">5</option>
		   </select>
		   <input type= "submit" value = "Réserver" name="Réserver"/><br>
		</form>
	</fieldset>
</div>
<div id="hotel3">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Domaine de Verchant</legend>
	<form action="traitement_joueur.php" method="post">
		<label for = "nom_film"><u>Type :</u>&nbsp;Hôtel</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;1 Bd Philippe Lamour</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34170</label><br>	
		<label for = "score"><u>Ville :</u>&nbsp;Castelnau-le-Lez</label><br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp;<?php 
 $bdd=Connect_db();
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_joueurs where id_Jhotel=3");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;
?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;OUI</label><br>	
	</fieldset>
</div>
<div id="hotel4">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Hotel Courtyard</legend>
	<form action="traitement_joueur.php" method="post">
		<label for = "nom_film"><u>Type :</u>&nbsp;Hôtel</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;105 Place Georges Freche (Rue du Chelia)</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34000</label><br>	
		<label for = "score"><u>Ville :</u>&nbsp;Montpellier</label><br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp;<?php
 $bdd=Connect_db();
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_joueurs where id_Jhotel=4");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;
?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;NON</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;NON</label><br>	
	</fieldset>
</div>
<div id="hotel5">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Hotel Balladins</legend>
	<form action="traitement_joueur.php" method="post">
		<label for = "nom_film"><u>Type :</u>&nbsp;Hôtel</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;Route de Carnon, Parc des Expos</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34470</label><br>	
		<label for = "score"><u>Ville :</u>&nbsp;Perols</label><br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp;<?php
 $bdd=Connect_db();
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_joueurs where id_Jhotel=5");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;
?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u>&nbsp;NON</label><br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;OUI</label><br>	
	</fieldset>
</div>

</H2>
</body>
</section>