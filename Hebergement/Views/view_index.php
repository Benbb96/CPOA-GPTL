<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <noscript><meta http-equiv="refresh" content="0, URL=Controller/nojavascript.php"></noscript>
    <link rel="stylesheet" type="text/css" href="Web/CSS/style.css">
    <link rel="stylesheet" type="text/css" href="Web/CSS/index.css">
    <script src="Web/JS/jquery-2.1.4.min.js"></script>
    <script src="Web/JS/functions.js"></script>
    <script src="Web/JS/index.js"></script>
</head>
<body>
    <header>
        <h1>0pen Sud de France <br> Reservation</h1>
    </header>
        <section id="formulaires" class="center">
            <section id="form_connexion">
                <h3>Connexion</h3>
                <hr>
                <form method="POST" action="Controller/connexion.php">
                    <input type="email" name="mail" value="" placeholder="Adresse Mail">
                    <input type="password" name="pass" value="" placeholder="Mot de Passe">
                    <div class="index_button_container">
                        <div class="button index_sub" onClick="$('#form_connexion form').submit();" style="float:auto;">Se Connecter</div>
                    </div>
                </form>
            </section>
        </section>
		<div id="ucbl">
			<img src="Web//IMG/ucbl.png" width="200" >
		</div>
    </body>
</html>
