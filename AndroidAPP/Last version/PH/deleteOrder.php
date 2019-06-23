<?php
// Delete Order - Marwa
$con=mysqli_connect("localhost","root","","ph");
if($con)
{
	$cusID = $_POST['cusID'];
	$resID = $_POST['resID'];
	$done = "0";

	$query="DELETE FROM orders where userID='".$cusID."'and resID='".$resID."'and done='".$done."' ";

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