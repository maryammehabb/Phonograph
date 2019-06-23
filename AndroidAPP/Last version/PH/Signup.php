<?php
$con=mysqli_connect("localhost","root","","PH");
if($con)
{
	$Name=$_POST['name'];
	$mail=$_POST['mail'];
	$address=$_POST['address'];
	$phone=$_POST['Phone'];
	$password= $_POST['password'];
	
	$query="insert into User( mail, password, name, phone, address) Values('".$mail."','".$password."','".$Name."','".$phone."','".$address."');";
	$result = mysqli_query($con,$query);
	if($result)
	{
		$status = 'OK';
		$query1="SELECT ID FROM User WHERE mail='".$mail."'";
		$result1 = mysqli_query($con,$query1);
		$row=mysqli_fetch_assoc($result1);
		if($result1)
			$id=$row["ID"];
	
		
	}
	else
	{
		$status = 'FAILED';
	}
	echo json_encode(array("response"=>$status, "ID"=>$id));
	mysqli_close($con);
}	
?>