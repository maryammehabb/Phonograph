<?php
$con=mysqli_connect("localhost","root", "" ,"PH");

if($con)
{
	$id= $_POST["id"];
	$query="SELECT * FROM user WHERE id = '".$id."'";
	$result = mysqli_query($con,$query);


$row = $result->fetch_array(MYSQLI_BOTH);
do {
	//printf ("%s %s %s %s %s \n", $row['name'], $row['timeStart'], $row['timeEnd'], $row['kidsMenu']);
	$arr = array('name' => $row['name'], 'mail' => $row['mail'], 'password' => $row['password'], 'phone' => $row['phone'], 'address' => $row['address']);
	$ar1 = array('name' => $row['name'], 'mail' => $row['mail'], 'password' => $row['password'], 'phone' => $row['phone'], 'address' => $row['address']);

}
while($row = mysqli_fetch_assoc($result));


//echo $myJSON;

echo json_encode($ar1);
//echo json_encode(array("response"=>$ar));

	mysqli_close($con);
}	
?>