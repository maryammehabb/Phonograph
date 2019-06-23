<?php

$con=mysqli_connect("localhost","root","","ph");
if($con)
{
	$branchID=$_POST['branchID'];
	$resID = $_POST['resID'];
    $userID=$_POST['userID'];
	$file=$_POST['file'];
	
	$query="INSERT INTO complaint( branchID, resID, userID, file) VALUES ( '".$branchID."','".$resID."','".$userID."','".$file."')";

	$result = mysqli_query($con,$query);
	$response = array();
	if($result)
	{
		$status = 'OK';
	
	}
	else
	{
		$status = 'FAILED';
	}
	echo json_encode(array("response"=>$status));
	mysqli_close($con);
}	

?>
