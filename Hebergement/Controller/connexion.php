<?php
    require("../Include/functions.php");

    if(isset($_SESSION['auth'])) header("Location: ../index.php");

    $erreurs = array();

    if(empty($_POST['mail']) || !filter_var($_POST['mail'], FILTER_VALIDATE_EMAIL)){
        $erreurs['mail'] = "L'adresse mail n'est pas valide";
    }

    if(empty($_POST['pass'])){
        $erreurs['pass'] = "Le mot de passe n'est pas valide";
    }

    if(empty($erreurs)){
        $result = $bdd->getData("SELECT * FROM compte WHERE mailCompte='".$_POST['mail']."' AND dateCreation IS NOT NULL");
        $data = $result->fetch(PDO::FETCH_OBJ);
        
        if($data){
            if(password_verify($_POST['pass'], $data->mdpCompte)){  
                $_SESSION['auth'] = $data;
                header("Location: accueil.php");
                exit(); 
            }else{
                $message = new Message("Echec de la connexion","La combinaison adresse mail et mot de passe ne correspond pas.",false);
                $message->afficher();                  
            }
        }else{
            $message = new Message("Echec de la connexion","Ce compte n'existe pas ou n'est pas activé. Veuillez consulter vos mails.",false);
            $message->afficher();   
        }
    }else{
        $message = new Message("Echec de la connexion","Les éléments suivants sont incorrects :",true);
        $message->ajouterElements($erreurs);
        $message->afficher();
    }
?>