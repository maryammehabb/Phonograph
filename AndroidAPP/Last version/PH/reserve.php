<?php
$con=mysqli_connect("localhost","root","","ph");


class Table 
{
 
	private $tableID;
	private $numOfSeats;
 
	function __construct( $ID, $seats ) {
		$this->tableID = $ID;
		$this->numOfSeats = $seats;
	}
 
	function getTableID() {
		return $this->tableID;
	}
	
	function setID($ID)
	{
		$this->tableID=$ID;
	}
 
 
	function setNumOfSeats($seats)
	{
		$this->numOfSeats=$seats;
	}
 
	function getNumOFSeats() {
		return $this->numOfSeats;
	}
}

function checkOpenAndCloseTime($conn , $resID ,  $time)
{
	$query="Select timeStart , timeEnd From restaurant WHERE id ='".$resID."' ";
	$result1 = mysqli_query($conn,$query);
	while($r=mysqli_fetch_assoc($result1))
	{
		if($time<$r['timeEnd'] && $time >=$r['timeStart'])
		{
			return true;
		}
		return false;
	}
}

function check_date1($conn)
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
function AvailableTables($branchID,$resID, $date, $time ,$conn)
{
	$tables=array();
	$excludedTables=array();
	$query="Select ID , numOfSeats From tables WHERE branchID= '".$branchID."' AND resID ='".$resID."' ";
	$result1 = mysqli_query($conn,$query);
	while($r=mysqli_fetch_assoc($result1))
	{
	
		$table= new Table($r['ID'], $r['numOfSeats']);
		array_push($tables,$table);
	}
	$x= (int) $time - 3;
	$y= (int) $time + 3;
# WHERE  timeStart < '".$y."'ANDdate= '".$date."' AND timeStart < '".$y."
	$query1="Select tableID From tablereservation WHERE date= '".$date."'AND timeStart > '".$x."' AND timeStart < '".$y."' ";
	$result = mysqli_query($conn,$query1);
	while ($row=mysqli_fetch_assoc($result))
	{		
		
		array_push($excludedTables,$row['tableID']);
	}

	#$final=array_diff($tables,$excludedTables);
	$final=array();
	$i=0;
	foreach($tables as $r)
	{
		if(!(in_array( $r->getTableID(),$excludedTables)))
		{
			array_push($final , $tables[$i]);
			}
			$i++;
		}

	return $final;
}

function comparator($object1, $object2)
	{ return $object1->getNumOfSeats() > $object2->getNumOfSeats(); }	

function reserve($cusID, $numOfPeople ,$branchID,$resID,$timeReserved, $timeMade,$date, $con)
{

	if ($con)
	{
		$tables = AvailableTables($branchID,$resID, $date, $timeReserved,$con);
	    usort($tables, 'comparator'); 
	    $tableID="";
	    $ReserveISA=array();
		$t=2;
	if(count($tables) > 0)
	{
		$t=$numOfPeople;
		$tt=" ";
		$i=0;
		while ($t>0 and $i<sizeof($tables))
		{
			$tt=$tt.$tables[$i]->getTableID(). " ";
			array_push($ReserveISA , $tables[$i]->getTableID());
			
			$t=$t-$tables[$i]->getNumOFSeats();
			$i++;
			
		}
		
		 $tableID=$tt;
	} 	
 
	$query="INSERT INTO reservation (userID, numOfPeople, branchID, tableID, resID, timeReserved, timeMade, date) values ('".$cusID."', '".$numOfPeople."', '".$branchID."', '".$tableID."', '".$resID."', '".$timeReserved."', '".$timeMade."','".$date."')";
	$q3="SELECT MAX(ID) `max` FROM reservation WHERE 1";
	
	$result=0;
	$result2=0;
	if($t<1)
	{
		$result = mysqli_query($con,$query);
		$re=mysqli_query($con,$q3);
		$row=mysqli_fetch_assoc($re);
		$reservationID=$row["max"];
		foreach ($ReserveISA as $r){
		$query1="INSERT INTO tablereservation (tableID, date, timeStart,reservationID) values ('".$r."', '".$date."', '".$timeReserved."', '".$reservationID."')";
		$result2=mysqli_query($con,$query1);
		}
		
	}
	if($result && $result2)
	{
		
		$status = 'OK';
	
	}
	else
	{	
		$status = 'FAILED';
	}
	$answer["response"]=$status;
	echo json_encode($answer);
	mysqli_close($con);
		
	}
}	
$cusID=$_POST['userID'];
$numOfPeople=$_POST['numOfPeople'];
$branchID=$_POST['branchID'];
$resID=$_POST['resID'];
$timeReserved=$_POST['timeReserved'];
$timeMade=$_POST['timeMade'];
$date=$_POST['date'];
$xx=checkOpenAndCloseTime($con , $resID,$timeReserved);
if($xx==false)
{
	$status="notime";
	$answer["response"]=$status;
	echo json_encode($answer);	
}
else{
$yy=check_date1($con);
if($yy==true)
{
	$status="already_Reserved";
	$answer["response"]=$status;
	echo json_encode($answer);	
}
else
	reserve($cusID,$numOfPeople,$branchID, $resID,$timeReserved,$timeMade,$date,$con);
}
?>