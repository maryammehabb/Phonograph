<?php
$user_name = "root";
$password = "";
$server = "localhost";
$db_name = "PH";

$con = mysqli_connect($server, $user_name, $password, $db_name);
if($con)
{
	$mail = $_POST["mail"];
	$pass = $_POST["password"];
	$query="SELECT * FROM user WHERE mail='".$mail."' AND password='".$pass."'";
	$result = mysqli_query($con, $query);
	$row = mysqli_fetch_assoc($result);
	$y= mysqli_affected_rows($con);
	if($y)
	{
		$status=$row['id'];
		$name=$row['name'];
	}
	else
	{
		$status=-1;
		$name="";
	}
}
	else
	{
		$status=-2;
		$name="";
	} 
$x["response"]=$status;
$x["name"]=$name;
echo json_encode($x);

mysqli_close($con);
?>