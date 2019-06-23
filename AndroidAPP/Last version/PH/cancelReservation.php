<?php
$con=mysqli_connect("localhost","root","","ph");
function check_date($conn)
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

function cancel($userID,$branchID,$resID,$con)
{
	if($con)
{
	$query0="SELECT Max(ID) max FROM reservation WHERE userID='".$userID."' and branchID='".$branchID."' and resID='".$resID."'";
	$result0 = mysqli_query($con,$query0);
	$row = mysqli_fetch_assoc($result0);
	$IDD =  $row['max'];

	$query="DELETE FROM reservation WHERE ID='".$IDD."'";
	$x= mysqli_affected_rows($con);
	$query1="DELETE FROM tablereservation WHERE reservationID='".$IDD."'";
	$y= mysqli_affected_rows($con);
	$result = mysqli_query($con,$query);
	$result1 = mysqli_query($con,$query1);

	$response = array();
	if($x && $result1)
	{
		$status = 'OK';
	}
	else
	{
		$status = 'FAILED';
	}
	echo json_encode(array("response"=>$status));
	mysqli_close($con);
}
}
	
	$userID=$_POST['userID'];
	$branchID=$_POST['branchID'];
	$resID=$_POST['resID'];
if(check_date($con)==false)
{
	$status="No Reserve";
	echo json_encode(array("response"=>$status));
}
else{
	cancel($userID,$branchID,$resID,$con);
}
?>
