<?php
// Make Order - Marwa
function calculate_price($order , $conn , $RestID)
{
	$query="SELECT price FROM item WHERE resID = '".$RestID."' AND name = '".$order."'";
	$result = mysqli_query($conn,$query);
	$row = $result->fetch_array(MYSQLI_BOTH);
	return $row['price'];
	
}
$x = false;
$y = false;

$con=mysqli_connect("localhost","root","","ph");
if($con)
{
	$timee=$_POST['timee'];
	$delivery="1";
	$restaurant_ID = $_POST['restaurant_ID'];
	//$done=0;
	$CustID=$_POST['CustID'];
	$meals=$_POST['items'];
	$noOfmeals=$_POST['noOfmeals'];
	$done = "0";
	
	$str_arr = explode (",", $meals); 
	$price = 0;
	foreach($str_arr as $item){
		$price = $price + calculate_price($item,$con,$restaurant_ID) * $noOfmeals;
	}
	
	
	$query="SELECT * FROM orders WHERE done ='".$done."' and userID='".$CustID."'and resID='".$restaurant_ID."' ";
	$r = mysqli_query($con,$query);
	$row = $r->fetch_array(MYSQLI_BOTH);
	if($row != "")
	{
		$x = true;
		$status = 'orderMade';
	
	}
	else{	
	}

	if($x == false)
	{
	$str_arr = explode (",", $meals); 
	foreach($str_arr as $item){
	$query="SELECT * FROM item WHERE  name='".$item."' and  resid='".$restaurant_ID."'";
	$result = mysqli_query($con,$query);
	$row = $result->fetch_array(MYSQLI_BOTH);
	if($row == "")
	{
		$y = true;
		$status = 'mealNotAvailable';
	}
	else
	{
	}
	}
	}


	if($x == false && $y == false){
	$query="INSERT INTO orders (time, delivery, price, resID, done, userID, items, numOfOrders) VALUES ('".$timee."', '".$delivery."','".$price."','".$restaurant_ID."','".$done."','".$CustID."','".$meals."','".$noOfmeals."')";
	$result = mysqli_query($con,$query);
	
	if($result)
	{
		$status = 'OK';
	
	}
	else 
	{
		$status = 'FAILED';
	}
	}
	echo json_encode(array("response"=>$status));
	mysqli_close($con);
}	
?>