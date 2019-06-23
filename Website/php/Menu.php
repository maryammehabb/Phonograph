<?php
require "connection.php";
require "header.php";

if (isset($_SESSION["resID"])) {

      $resID = $_SESSION["resID"];
      } else {

           $resID = "";
        }

if(isset($_POST['Edit'])){

 	$name = $_POST['name1'];
 	$price = $_POST['price1'];
 	$id = $_POST['id'];
 	$update = "UPDATE item SET name='".$name."', price='".$price."' WHERE id = '".$id."' and resID= '".$resID."' ";
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
	//echo json_encode($x);
	//mysqli_close($mysqli);
}
elseif(isset($_POST['Delete'])) {

	if(isset($_POST['idd'])){
		echo "string";
		$h = $_POST['idd'];
		$query="DELETE FROM item WHERE id = '".$h."' and resID= '".$resID."'";
		$result = mysqli_query($mysqli,$query);
		$response = array();
		if($result)
	{
		$status = 'OK';
		header('Location: ../ven/templates/index.php');
	
	}
	else
	{
		$status = 'FAILED';
	}
	//echo json_encode(array("response"=>$status));
	mysqli_close($mysqli);
}
}
elseif (isset($_POST['Add'])){
	$name = $_POST['name'];
	$price = $_POST['price'];
	$query= "INSERT INTO `item`(`resID`, `name`, `price`) VALUES ('$resID','$name','$price')";
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
	mysqli_close($mysqli);
}
?>
