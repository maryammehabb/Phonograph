<?php

$con=mysqli_connect("localhost","root","","ph");

$userID=$_POST['userID'];
$numOfPeople=$_POST['numOfPeople'];
$branchID=$_POST['branchID'];
$resID=$_POST['resID'];
$timeReserved=$_POST['timeReserved'];
$timeMade=$_POST['timeMade'];
$date=$_POST['date'];
function check_date2($conn)
{
	 $date=date("Y-m-d");
	 $time= date("h:i:s");
	 $query1="Select tableID From tablereservation WHERE date > '".$date."'AND timeStart > '".$time."'";
	 $result = mysqli_query($conn,$query1);
	 $r=mysqli_fetch_assoc($result);
	 if(empty($r))
	 {
		 return false;
	}
	else 
		return true; 
}
$yy=check_date2($con);
if($yy==false)
{
	$x["response"]="no Reserve";
	echo json_encode($x);
}
else
{
include 'reserve.php';
include 'cancelReservation.php';
$output1=cancel($userID,$branchID,$resID,$con);
$character1 = json_decode($output1);
$con=mysqli_connect("localhost","root","","ph");
$output2=reserve($userID,$numOfPeople,$branchID, $resID,$timeReserved,$timeMade,$date,$con);
$character2 = json_decode($output2);
if($character1 and $character2)
{
	$x["response"]="OK";
}
else if ($character1  and !$character2)
{
	$x["response"]="no table";
}	
echo json_encode($x);
}

?>