<?php
$con=mysqli_connect("localhost","root", "" ,"PH");

if($con)
{
	$id= $_POST["id"];
	$query="SELECT * FROM restaurant WHERE id = '".$id."'";
	$result = mysqli_query($con,$query);


$row = $result->fetch_array(MYSQLI_BOTH);
do {
	//printf ("%s %s %s %s %s \n", $row['name'], $row['timeStart'], $row['timeEnd'], $row['kidsMenu']);
	$arr = array('id' => $row['id'], 'name' => $row['name'], 'timeStart' => $row['timeStart'], 'timeEnd' => $row['timeEnd'], 'kidsMenu' => $row['kidsMenu']);
	$ar1[] = array('id' => $row['id'], 'name' => $row['name'], 'timeStart' => $row['timeStart'], 'timeEnd' => $row['timeEnd'], 'kidsMenu' => $row['kidsMenu']);

}
while($row = mysqli_fetch_assoc($result));

$query="SELECT * FROM branch WHERE resID = '".$id."'";
$result = mysqli_query($con,$query);

$row = $result->fetch_array(MYSQLI_BOTH);
do {
	//printf ("%s %s \n", $row['id'], $row['address']);
	$arr = array('id' => $row['id'], 'address' => $row['address']);
	$ar2[] = array('id' => $row['id'], 'address' => $row['address']);

}
while($row = mysqli_fetch_assoc($result));

$query="SELECT * FROM Item WHERE resID = '".$id."'";
$result = mysqli_query($con,$query);

$row = $result->fetch_array(MYSQLI_BOTH);
do {
	//printf ("%s %s %s %s\n", $row['id'], $row['name'], $row['price'], $row['kids']);
	$arr = array('id' => $row['id'], 'name' => $row['name'], 'price' => $row['price'], 'kids' => $row['kids']);
	$ar3[] = array('id' => $row['id'], 'name' => $row['name'], 'price' => $row['price'], 'kids' => $row['kids']);

}
while($row = mysqli_fetch_assoc($result));

$finalArr = array($ar1, $ar2, $ar3);

$myJSON = json_encode($finalArr);

//echo $myJSON;

echo json_encode($finalArr);
//echo json_encode(array("response"=>$ar));

	mysqli_close($con);
}	
?>