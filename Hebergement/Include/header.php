<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><?php echo $titre; ?></title>
    <noscript><meta http-equiv="refresh" content="0, URL=nojavascript.php"></noscript>
    <link rel="stylesheet" type="text/css" href="../Web/CSS/style.css">
    <link rel="stylesheet" type="text/css" href="../Web/CSS/header.css">
    <link rel="stylesheet" type="text/css" href="../Web/CSS/accueil.css">
    <link rel="stylesheet" type="text/css" href="../Web/CSS/cours.css">
    <link rel="stylesheet" type="text/css" href="../Web/CSS/espace_perso.css">

    <script src="../Web/JS/jquery-2.1.4.min.js"></script>
    <script src="../Web/JS/functions.js"></script>
    <script src="../Web/JS/header.js"></script>
    <script src="../Web/JS/news.js"></script>
    <script src="../Web/JS/espace_perso.js"></script>

</head>
<body>
	<div id="gradient">
    <header>
        <div id="top">
            <div id="left"> 
                <h1>Open Sud de France Reservation</h1>
            </div><!--@whitespace
         --><div id="right">
                <span>
                    <p><?php echo "$prenom $nom"; ?></p>
                    <a href="deconnexion.php">Se déconnecter</a> 
                </span>
            </div>
        </div>
        <nav>
            <ul>
                <li link="accueil.php">Accueil</li>
				<li link="espace_perso.php">Carte des Hôtels</li>
				<li link="forum.php">Mon Espace Perso</li>
            </ul>
        </nav>
    </header>
	</div>