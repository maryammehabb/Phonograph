<?php
	require "connection.php";
	require "header.php";

	session_start();

	if(isset($_POST['Edit'])){
 		$name = $_POST['name'];
 		$timeStart = $_POST['timeStart'];
 		$timeEnd = $_POST['timeEnd'];
 		$kidsMenu = $_POST['kidsMenu'];
 		if($kidsMenu == 'yes'){
            $k = 1;
        }
        else
            $k = 0;
		if (isset($_SESSION["resID"])) {
    		$resID = $_SESSION["resID"];    
    	} 
    	else {
        	$resID = "";
        }
 		$update = "UPDATE restaurant SET name='$name', timeStart='$timeStart',timeEnd='$timeEnd',kidsMenu='$k' WHERE id = '".$resID."'";
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
?>
