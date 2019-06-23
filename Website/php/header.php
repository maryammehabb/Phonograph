<?php
session_start();
    if (isset($_SESSION['resID'])) {
        //echo "User ID:", $_SESSION['resID'], "<br />";
    } else {
        $_SESSION['username'] = "yourloginprocesshere";
        $_SESSION['id'] = 444;
    }
?>