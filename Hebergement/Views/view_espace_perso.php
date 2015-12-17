<div id="menu">

    <ul>
    	<li id="onglet_infos">Infos Personnelles</li><!--@whitespace
     --><li id="onglet_defis">Mes défis</li><!--@whitespace
     --><li id="onglet_progression">Ma Progression</li><!--@whitespace
 --></ul>


</div>

<section id="container">


	<div id="infos">
 		<section id="formulaires" class="center">
		 	<section id="form_infos_perso">
				<h3> Informations personnelles</h3>
				<hr>
				<form method="POST">
					<p>Nom</p>
					<p>Prenom</p> 
					<p>Mot de Passe</p>
			  		<p>Email</p>
					<div class="bouton " onClick="switchObject($('#form_infos_perso'),$('#form_infos_perso_modif'))">Modifier son mot de passe</div>
					
				</form>
			</section>
		
		
			<section id="form_infos_perso_modif" >
				<h3>Modification du mot de passe </h3>
				<hr>
				<form method="POST" action="Controller/InfosPersoModif.php">
					<input type="text" name="mdp1" placeholder="mot de passe" >
					<input type="text" name="mdp2" placeholder="confirmation du mot de passe" class="separator">
					<div class="bouton "  onClick="switchObject($('#form_infos_perso_modif'),$('#form_infos_perso'))">Enregistrer les modifications</div>
					</div>
				</form>
			</section>
		</section>
	</div>
	<!--@whitespace

 --><div id="defis">
		
				
	</div>
	<!--@whitespace

 --><div id="progression">
		
 	</div>


</section>