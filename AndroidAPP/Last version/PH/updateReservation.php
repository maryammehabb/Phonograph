<?php
$con=mysqli_connect("localhost","root","","ph");
if($con)
{
	$id=$_POST['id'];
	$cusID = $_POST['cusID'];
	$resID = $_POST['resID'];
	$branchID = $_POST['branchID'];
	$numOfPeople=$_POST['numOfPeople'];
	$timeReserved=$_POST['timeReserved'];
	$query = "UPDATE reservation SET numOfPeople ='".$numOfPeople."' and timeReserved='".$timeReserved."' WHERE id ='".$id."' and cusID ='".$cusID."' and resID ='".$resID."' and branchID ='".$branchID."'";
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
	$x["response"]=$status;
	echo json_encode($x);
	mysqli_close($con);
}	
?>
