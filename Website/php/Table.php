<?php
require "connection.php";

require "header.php";

if (isset($_SESSION["resID"])) {
      $resID = $_SESSION["resID"];
      } else {
           $resID = "";
     }
$h = $_POST['id'];
if(isset($_POST['Edit'])){
 $branchID = $_POST['b1'];
 $numOfSeats = $_POST['n1'];
 $id = $_POST['id'];

 $update = "UPDATE tables SET branchID='$branchID', numOfSeats='$numOfSeats' WHERE id= '".$id."' and resID = '".$resID."'";
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
	//$x["response"]=$status;
	//echo json_encode($x);
	mysqli_close($mysqli);

}
elseif(isset($_POST['Delete'])) {
	echo "string";
	if(isset($_POST['idd'])){
		echo "string";
	$h = $_POST['idd'];
	echo $h;
	$query="DELETE FROM tables WHERE ID = '".$h."' and resID= '".$resID."'";
	echo $h;
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
	//mysqli_close($mysqli);
}
}
elseif (isset($_POST['Add'])){
$branchID = $_POST['b'];
$numOfSeats = $_POST['n'];

 $query = "INSERT INTO tables(branchID, numOfSeats, resID) VALUES ('".$branchID."','".$numOfSeats."','".$resID."')";

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
	$x["response"]=$status;
	//echo json_encode($x);
	//mysqli_close($mysqli);

}
echo "ehhhh";
?>
