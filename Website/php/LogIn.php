<?php


session_start();
$message="";

if(count($_POST)>0) {

    $con = mysqli_connect('localhost','root','','ph') or die('Unable To connect');
    $result = mysqli_query($con,"SELECT * FROM admin WHERE email='" . $_POST["email"] . "' and password = '". $_POST["password"]."' and resID = '". $_POST["resID"]."'");
    $row  = mysqli_fetch_array($result);
    if(is_array($row)) {

        $_SESSION["id"] = $row[id];
        $_SESSION["email"] = $row[email];
        $_SESSION["resID"] = $row[resID];
    } 
    else {
        $message = "Invalid Username or Password!";
        //echo "<script type='text/javascript'>alert('$message');</script>";
        header("Location: ../ven/templates/FirstPage.html");
    }
}
else{
    $message = "Please Enter Your Data";
    //echo "<script type='text/javascript'>alert('$message');</script>";
    header("Location: ../ven/templates/FirstPage.html");
}

if(isset($_SESSION["resID"])) {
    header("Location: ../ven/templates/index.php");
}
?>