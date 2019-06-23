<?php
$con=mysqli_connect("localhost","root","","PH");
if($con)
{
	$branchID=$_POST['branchID'];
	$RestID=$_POST['RestID'];
	$query="SELECT * FROM branch WHERE id = '".$branchID."' AND resID = '".$RestID."'";
	$result = mysqli_query($con,$query);
	//echo $result;
	
	$row = $result->fetch_array(MYSQLI_BOTH);
do {
	//printf ("%s %s %s %s %s \n", $row['name'], $row['timeStart'], $row['timeEnd'], $row['kidsMenu']);
	$arr = array('name' => $row['smokingArea']);
	$smokingArea =  $row['delivery'];
	//printf ("%s \n", $row['smokingArea']);

}
while($row = mysqli_fetch_assoc($result));
if($arr['name']=="1")
	$answer['SMokingArea']="yes it has";
else
	$answer['SMokingArea']="No it hasn't";	
echo json_encode($answer);

}	
?>