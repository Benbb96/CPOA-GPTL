<?php
    require("../Include/functions.php");

    if(isset($_SESSION['auth'])) header("Location: ../index.php");

    $erreurs = array();

    if(empty($_POST['nom']) || !preg_match("#^[a-zA-Z]+$#",$_POST['nom'])){
        $erreurs['nom'] = "Le nom n'est pas valide";
    }
    
    if(empty($_POST['prenom']) || !preg_match("#^[a-zA-Z-]+$#",$_POST['prenom'])){
        $erreurs['prenom'] = "Le prenom n'est pas valide";
    }

    if(empty($erreurs)){
        $result = $bdd->getData("SELECT idCompte FROM compte WHERE nomCompte='".strtolower($_POST['nom'])."' AND prenomCompte='".strtolower($_POST['prenom'])."'");
        $data = $result->fetch(PDO::FETCH_OBJ);
        if($data) $erreurs['nom_prenom'] = "Ce nom et ce prénom existent déjà";
    }

    if(empty($_POST['mail']) || !filter_var($_POST['mail'], FILTER_VALIDATE_EMAIL) || $_POST['mail'] != $_POST['confirm_mail']){
        $erreurs['mail'] = "L'adresse mail n'est pas valide";
    }else{
        $result = $bdd->getData("SELECT idCompte FROM compte WHERE mailCompte='".strtolower($_POST['mail'])."'");
        $data = $result->fetch(PDO::FETCH_OBJ);
        if($data) $erreurs['mail'] = "Cette adresse mail existe déjà";
    }

    if(empty($_POST['pass']) || $_POST['pass'] != $_POST['confirm_pass']){
        $erreurs['pass'] = "Le mot de passe n'est pas valide";
    }

    if(empty($erreurs)){
        
        $password = password_hash($_POST['pass'], PASSWORD_BCRYPT);
        
        $cle = str_random(100);
        
        $bdd->setData("INSERT INTO compte VALUES(NULL,'".strtolower($_POST['nom'])."','".strtolower($_POST['prenom'])."','".strtolower($_POST['mail'])."','".$password."',CURDATE(),'".$cle."',NULL,DEFAULT,DEFAULT,DEFAULT)");
        $user_id = $bdd->getLastId();
        
        /*mail($_POST['mail'],"Confirmation de votre compte InforMATHique","Pour valider ton compte, merci de cliquer sur le lien ci-dessous :\n\nhttp://localhost/InforMATHique/Controller/confirmation.php?id=$user_id&cle=$cle");*/
        
        $message = new Message("Inscription réussi","Votre inscription à bien été enregistré. Veuillez vérifier vos mails pour activer votre compte.",false);
        $message->afficher();
    }else{
        $message = new Message("Echec de l'inscription","Les éléments suivants sont incorrects :",true);
        $message->ajouterElements($erreurs);
        $message->afficher();
    }
?>