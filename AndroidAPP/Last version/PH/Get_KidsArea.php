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
	$arr = array('name' => $row['kidsArea']);
	$kidsArea =  $row['kidsArea'];
	//printf ("%s \n", $row['kidsArea']);

}
while($row = mysqli_fetch_assoc($result));
if($arr['name']==1)
	$answer['KidsArea']="yes it has";
else
	$answer['KidsMenu']="No it hasn't";
echo json_encode($answer);

}	
?>