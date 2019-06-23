<?php
$con=mysqli_connect("localhost","root","","ph");
if($con)
{
	$ID = $_POST['ID'];
	$mail=$_POST['mail'];
	$password=$_POST['password'];
	$name=$_POST['name'];
	$phone = $_POST['phone'];
	$address=$_POST['address'];

	$query="UPDATE user SET mail='".$mail."', password='".$password."' , name='".$name."' , phone='".$phone."' , address='".$address."' WHERE ID='".$ID."'";

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