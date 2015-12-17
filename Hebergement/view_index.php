<section id="bg_index">
    <img src="Web/IMG/bg_index.png">
</section>

<section id="formulaires" class="center">
    <section id="form_connexion">
        <h3>Connexion</h3>
        <hr>
        <form method="POST" action="index.php?form=connexion">
            <input type="text" name="mail" value="" placeholder="Adresse Mail">
            <input type="text" name="pass" value="" placeholder="Mot de Passe">
            <div class="index_button_container">
                <div class="inv_button index_sub" onClick="switchObject($('#form_connexion'),$('#form_inscription'))">S'inscrire</div>
                <div class="button index_sub" onClick="checkForm('form_connexion')" style="float:right;">Se Connecter</div>
            </div>
        </form>
    </section>

    <section id="form_inscription">
        <h3>Inscription</h3>
        <hr>
        <form method="POST" action="index.php?form=inscription">
            <input type="text" name="nom" value="" placeholder="Nom">
            <input type="text" name="prenom" value="" placeholder="Prénom" class="separator">
            <input type="text" name="mail" value="" placeholder="Adresse Mail">
            <input type="text" name="confirm_mail" value="" placeholder="Confirmation Adresse Mail" class="separator">
            <input type="text" name="pass" value="" placeholder="Mot de Passe">
            <input type="text" name="confirm_pass" value="" placeholder="Confirmation Mot de Passe">
            <div class="index_button_container">
                <div class="inv_button index_sub" onClick="switchObject($('#form_inscription'),$('#form_connexion'))">Retour</div>
                <div class="button index_sub" onClick="checkForm('form_inscription')" style="float:right;">S'inscrire</div>
            </div>
        </form>
    </section>  
</section>
