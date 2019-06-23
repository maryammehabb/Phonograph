<?php
require "connection.php";

require "header.php";

if (isset($_SESSION["resID"])) {
      $resID = $_SESSION["resID"];
    } 
    else {
        $resID = "";
    }

if(isset($_POST['Edit'])){

	$address = $_POST['address'];
 	$numOfTables = $_POST['numOfTables'];
 	$delivery = $_POST['delivery'];
 	if($delivery == 'yes'){
            $d2 = 1;
        }
        else
            $d2 = 0;
 	$kidsArea = $_POST['kidsArea'];
 	if($kidsArea == 'yes'){
            $kk = 1;
        }
        else
            $kk = 0;
  	$smokingArea = $_POST['smokingArea'];
  	if($smokingArea == 'yes'){
            $s = 1;
        }
        else
            $s = 0;
  	$id = $_POST['id'];
 	$update = "UPDATE `branch` SET `address`='$address',`numOfTables`='$numOfTables',`delivery`='$d2',`kidsArea`='$kk',`smokingArea`='$s' WHERE id = '$id' and resID = '$resID'";
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
}
elseif(isset($_POST['Delete'])) {

	if(isset($_POST['idd'])){
	
		$h = $_POST['idd'];
		echo $h." ";
 		$query = "DELETE FROM branch WHERE id = '".$h."' and resID = '".$resID."'";
 		echo $h;
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
	}
}
elseif (isset($_POST['Add'])){
	$address = $_POST['a'];
 	$numOfTables = $_POST['n'];
 	$delivery = $_POST['d'];
 	if($delivery == 'yes'){
            $d2 = 1;
        }
        else
            $d2 = 0;
 	$kidsArea = $_POST['k'];
 	if($kidsArea == 'yes'){
            $kk = 1;
        }
        else
            $kk = 0;
  	$smokingArea = $_POST['s'];
  	if($smokingArea == 'yes'){
            $s = 1;
        }
        else
            $s = 0;
  	$id = $_POST['id'];
 	$query = "INSERT INTO branch(address, numOfTables, delivery, resID, kidsArea, smokingArea) VALUES ('".$address."','".$numOfTables."','".$d2."','".$resID."','".$kk."','".$s."')";
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
?>
