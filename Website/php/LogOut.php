<?php

if (isset($_SESSION["resID"])) {

      $resID = $_SESSION["resID"];
      } else {

           $resID = "";
      }
session_start();unset($_SESSION["resID"]);
unset($_SESSION["email"]);
session_destroy();
header("Location: ../ven/templates/FirstPage.html");
?>