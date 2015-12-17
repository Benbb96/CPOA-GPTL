<?php    
    require("../Include/functions.php");

    if(isset($_SESSION['auth'])) header("Location: ../index.php");

    if(!empty($_GET['id']) && preg_match("#^[0-9]+$#",$_GET['id'])) $user_id = $_GET['id'];
    else $user_id = 0;
        
    $cle = $_GET['cle'];

    $result = $bdd->getData("SELECT * FROM compte WHERE idCompte=$user_id");
    $data = $result->fetch(PDO::FETCH_OBJ);

    if($data && $data->cleConfirmation == $cle){
        $bdd->setData("UPDATE compte SET cle=NULL, dateActivation=CURDATE() WHERE idCompte=$user_id");
        $_SESSION['auth'] = $data;
        
        $message = new Message("Votre compte est activé","Vous pouvez désormais accéder aux différentes sections du site.",false);
        $message->afficher();
    }
    else header("Location: ../index.php");
?>