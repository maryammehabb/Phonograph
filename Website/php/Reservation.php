<?php
require "connection.php";
require "header.php"; 
if (isset($_SESSION["resID"])) {
      $resID = $_SESSION["resID"];
                                           } else {
                                            $resID = "";
                                            }


if(isset($_POST['Edit'])){
 $branchID = $_POST['branchID'];
 $numOfPeople = $_POST['numOfPeople'];
 $tableID = $_POST['tableID'];
 $timeReserved = $_POST['timeReserved'];
 $id = $_POST['id'];
$date = $_POST['date'];
 $query1="DELETE FROM tablereservation WHERE reservationID='".$id."'";
 $update = "UPDATE reservation SET branchID='$branchID', numOfPeople='$numOfPeople',tableID='$tableID',timeReserved='$timeReserved' , date = '$date' where resID = '".$resID."' and ID = '".$id."'";
 $up = mysqli_query($mysqli, $update);
 $qu = mysqli_query($mysqli, $query1);
 $ReserveISA= explode(" ", $tableID);
 foreach ($ReserveISA as $r){
		$query1="INSERT INTO tablereservation (tableID, date, timeStart,reservationID) values ('".$r."', '".$date."', '".$timeReserved."', '".$id."')";
		$result2=mysqli_query($mysqli,$query1);
		}
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
$query = "DELETE FROM reservation WHERE ID = '".$h."' and resID ='".$resID."'";
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
	//$x["response"]=$status;
	//echo json_encode($x);
	mysqli_close($mysqli);

}
}

?>
