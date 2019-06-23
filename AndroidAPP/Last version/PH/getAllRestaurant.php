<?php
$con=mysqli_connect("localhost","root","","PH");
if($con)
{
	$query="SELECT * FROM restaurant";
	$result = mysqli_query($con,$query);
	$response = array();


$row = $result->fetch_array(MYSQLI_BOTH);
$i = 0;
do {
	//printf ("%s %s %s %s %s \n", $row['id'], $row['name'], $row['timeStart'], $row['timeEnd'], $row['kidsMenu']);
	$arr = array('id' => $row['id'], 'name' => $row['name'], 'timeStart' => $row['timeStart'], 'timeEnd' => $row['timeEnd'], 'kidsMenu' => $row['kidsMenu']);
	$ar[] = array('id' => $row['id'], 'name' => $row['name'], 'timeStart' => $row['timeStart'], 'timeEnd' => $row['timeEnd'], 'kidsMenu' => $row['kidsMenu']);
	$i = $i + 1;

}
while($row = mysqli_fetch_assoc($result));
$myJSON = json_encode($ar);

//echo $myJSON;

echo json_encode($ar);
//echo json_encode(array("response"=>$ar));

	mysqli_close($con);
}	
?>