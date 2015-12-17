<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Message</title>
    <noscript><meta http-equiv="refresh" content="0, URL=nojavascript.php"></noscript>
    <link rel="stylesheet" type="text/css" href="../Web/CSS/style.css">
    <link rel="stylesheet" type="text/css" href="../Web/CSS/header.css">
    <script src="../Web/JS/jquery-2.1.4.min.js"></script>
    <script src="../Web/JS/functions.js"></script>
</head>
<body>
    <header>
        <div id="top">
            <div id="left"> 
                <h1>Open Sud de France : Reservation</h1>
            </div>
        </div>
        <nav>
            <h3 style="text-align:center;"><?php echo $titreMessage; ?> !</h3>
        </nav>
    </header>
    <section id="container" style="width:50%">
        <?php echo $contenuMessage; ?>
        <div class="button" style="width:20%;margin-top:20px;font-size:1em;"><a href="../index.php">Retour</a></div>
    </section>
</body>
</html>