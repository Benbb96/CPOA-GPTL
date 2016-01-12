<div id="menu">
</div>
<link rel="stylesheet" href="../Web/CSS/Fichier-css.css" type="text/css" />
<section id="container">

<div id="hotel6">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Clos de l'Herminier</legend>
		<label for = "nom_film"><u>Type :</u>&nbsp;Chambre d'hôte</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;25 avenue du Moulin de Tourtoure</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34880</label><br>	
		<label for = "score"><u>Ville :</u></label>&nbsp;Lavérune<br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp; <?php  function Connect_db(){
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
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_arbitres where id_Ahotel=1");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;
?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u></label>&nbsp;NON<br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;NON</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;NON</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;NON</label><br>	
	</fieldset>
</div>

<div id="hotel7">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Hôtel Le Jardin des Sens</legend>
		<label for = "nom_film"><u>Type :</u>&nbsp;Hôtel</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;11 Avenue Saint-Lazare</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34000</label><br>	
		<label for = "score"><u>Ville :</u></label>&nbsp;Montpellier<br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp;<?php 
		 $bdd=Connect_db();
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_arbitres where id_Ahotel=2");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u></label>&nbsp;OUI<br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;NON</label><br>	
	</fieldset>
</div>

</div>
<div id=formulaire style="text-align:center;width:270px">
	<fieldset>
		<legend style="font-size:22px; font-family:'J.M.Nexus-Black'; font-weight:bold;"> Informations Arbitre</legend>
		<form action="traitement_arbitre.php" method="post">
			<label for = "nom_arbitre"> Nom de l'Arbitre </label> <input type = "text" name = "nom_arbitre"/><br>
			<label for = "prenom_arbitre"> Prénom de l'Arbitre </label><input type= "text" name = "prenom_arbitre"/><br>
			<br>
			<select name="attribut" id="attribut" value="attribut">
			   <option value="ligne">Arbitre de Ligne</option>
			   <option value="chaise">Arbitre de Chaise</option>
			   <option value="filet">Arbitre de Filet</option>
		   </select>
	</fieldset>
	
	<br>
	<br>
	
	<fieldset style="width:300px">
		<legend style="font-size:22px; font-family:'J.M.Nexus-Black'; font-weight:bold;">  Hôtels </legend>
		   <input type="radio" name="hotel" value="Clos de l'herminier" id="Clos de l'herminier" /> <label for="Clos de l'herminier">Clos de l'herminier&nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <input type="radio" name="hotel" value="Hôtel Le Jardin Des Sens" id="Hôtel Le Jardin Des Sens" /> <label for="Hôtel Le Jardin Des Sens">Hôtel Le Jardin Des Sens&nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
		   <input type="radio" name="hotel" value="Hotel des Arceaux" id="Hotel des Arceaux" /> <label for="Hotel des Arceaux">Hotel des Arceaux&nbsp;<img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"><img src="../Web/IMG/etoile.png" width="13"></label><br />
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

<div id="hotel8">
	<fieldset style="text-align:center;width:260px">
	<legend style="font-size:21px; font-family:'J.M.Nexus-Black'; font-weight:bold;">Hôtel des Arceaux</legend>
		<label for = "nom_film"><u>Type :</u>&nbsp;Appart-Hôtel</label><br>
		<label for = "annee_film"><u>Adresse :</u>&nbsp;25 Boulevard des Arceaux</label><br>
		<label for = "score"><u>Code postale :</u>&nbsp;34000</label><br>	
		<label for = "score"><u>Ville :</u></label>&nbsp;Montpellier<br>	
		<label for = "score"><u>Nombre de lits dispo :</u>&nbsp; <?php  $bdd=Connect_db();
 $query=$bdd->prepare("Select nbr_lits_dispo from hotel_arbitres where id_Ahotel=3");
 $query->execute();
 while($data=$query->fetch()){
	 $reponse=$data['nbr_lits_dispo'];
 }
 echo $reponse;?></label><br>	
		<label for = "score"><u>Bar :</u>&nbsp;OUI</label><br>	
		<label for = "score"><u>Restaurant :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Petit déjeuner :</u>&nbsp;OUI</label><br>
		<label for = "score"><u>Sauna :</u></label>&nbsp;OUI<br>
		<label for = "score"><u>Salle de sport :</u>&nbsp;NON</label><br>	
		<label for = "score"><u>Coiffeur :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Pressing :</u>&nbsp;OUI</label><br>		
		<label for = "score"><u>Hammam :</u>&nbsp;OUI</label><br>	
	</fieldset>
</div>

</H2>
</body>


</section>