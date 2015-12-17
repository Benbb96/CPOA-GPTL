<?php
    session_start();

    if(isset($_SESSION['auth'])) header("Location: Controller/accueil.php");
    require("Views/view_index.php");
?>