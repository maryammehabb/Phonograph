<?php
require "connection.php";
//session_start();
require "header.php";

if (isset($_SESSION["resID"])) {

      $resID = $_SESSION["resID"];
      } else {
           $resID = "";
     }

if(isset($_POST['Edit'])){
 	$numOfOrders = $_POST['numOfOrders'];
 	$items = $_POST['items'];
	$delivery = $_POST['delivery'];
	if($delivery == 'yes'){
            $d2 = 1;
    }
    else
            $d2 = 0;
  	$price = $_POST['price'];
  	$done = $_POST['done'];
  	if($done == 'yes'){
        $d2 = 1;
        }
    else
        $d2 = 0;

 	$id = $_POST['id'];
 	$update = "UPDATE `orders` SET `numOfOrders`='$numOfOrders',`items`='$items',`delivery`='$d2',`price`='$price',`done`='$d2' WHERE id ='$id' and resID='$resID'";
 	$up = mysqli_query($mysqli, $update);
 	if($up)
	{
		$status = 'OK';
		header('Location: ../ven/templates/index.php');
	}
	else
	{
		$status = 'FAILED';
	}
	
	$x["response"]=$status;
	echo json_encode($x);
	mysqli_close($mysqli);

}
elseif(isset($_POST['Delete'])) {
	echo "string";
	if(isset($_POST['idd'])){
		$h = $_POST['idd'];
		$query = "DELETE FROM orders WHERE id = '".$h."' and resID = '".$resID."' ";
 		$up = mysqli_query($mysqli, $query);
 		if($up)
		{
			$status = 'OK';
			header('Location: ../ven/templates/index.php');
		}
		else
		{
			$status = 'FAILED';
		}
		//mysqli_close($mysqli);
		//$x["response"]=$status;
		//echo json_encode($x);
}}
?>
