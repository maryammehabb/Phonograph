<?php
$con=mysqli_connect("localhost","root","","PH");
if($con)
{
	$RestID=$_POST['RestID'];
	$query="SELECT timeStart , timeEnd FROM restaurant WHERE ID = '".$RestID."'";
	$result = mysqli_query($con,$query);
	//echo $result;
	$row = $result->fetch_array(MYSQLI_BOTH);
	$array["time"]=  "the restaurant opens at ".$row['timeStart'].' and closes at  '.$row['timeEnd'];
	echo json_encode($array);
}	
?>