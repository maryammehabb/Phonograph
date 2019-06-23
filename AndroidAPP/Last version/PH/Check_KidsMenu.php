<?php
$con=mysqli_connect("localhost","root","","PH");
if($con)
{
	$RestID=$_POST['RestID'];
	$query="SELECT kidsMenu FROM restaurant WHERE ID = '".$RestID."'";
	$result = mysqli_query($con,$query);
	$row = $result->fetch_array(MYSQLI_BOTH);
	$answer;
	if($row['kidsMenu']==1)
	{
		$answer="yes it has";
	}
	else
	{
		$answer="no it hasn't";
	}
	$array["kidsmenu"]=  $answer;
	echo json_encode($array);
}	
?>