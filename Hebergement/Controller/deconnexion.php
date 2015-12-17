<?php

require("../Include/functions.php");

unset($_SESSION['auth']);
header("Location: ../index.php");

?>