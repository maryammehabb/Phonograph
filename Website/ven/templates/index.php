<?php require "../../php/header.php"; 
    if (isset($_SESSION["resID"])) {
        $resID = $_SESSION["resID"];
    } 
    else {
            $resID = "";
    }
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="../../plugins/images/favicon.png">
    <title>PHONOGRAPH</title>
    <!-- Bootstrap Core CSS -->
    <link href="../static/pixel-html/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="../static/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <!-- toast CSS -->
    <link href="../static/plugins/bower_components/toast-master/css/jquery.toast.css" rel="stylesheet">
    <!-- morris CSS -->
    <link href="../static/plugins/bower_components/morrisjs/morris.css" rel="stylesheet">
    <!-- animation CSS -->
    <link href="../static/pixel-html/css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../static/pixel-html/css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="../static/pixel-html/css/colors/blue-dark.css" id="theme" rel="stylesheet">
</head>

<body>
    <body onload="setInterval('window.location.reload()', 60000);">
    <!-- Preloader -->
    <?php
    require "../../php/connection.php";
?>
    <div class="preloader">
        <div class="cssload-speeding-wheel"></div>
    </div>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top m-b-0">
            <div class="navbar-header" style="background-color: #602020;"> <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse"><i class="fa fa-bars"></i></a>
                <div class="top-left-part">
                <a class="logo" href="index.php">
                    <span class="hidden-xs" class="logo"><img src="../static/pixel-html/logo.png" alt="home" /></span>
                    </a>
                </div>
            </div>
        </nav>
        <!-- Left navbar-header -->
        <div class="navbar-default sidebar" role="navigation" style="background-color: #602020;">
            <div class="sidebar-nav navbar-collapse slimscrollsidebar" style="position: fixed;">
                <ul class="nav" id="side-menu">
                    <li style="padding: 10px 0 0;">
                        <a href="index.php" class="waves-effect"><span class="hide-menu" style="color: #d9b38c;">HOME</span></a>
                    </li>
                    <li>
                        <a href="#ord" class="waves-effect"><span class="hide-menu" style="color: #d9b38c;">Orders</span></a>
                    </li>
                    <li>
                        <a href="#cus" class="waves-effect"><span class="hide-menu" style="color: #d9b38c;">Customers</span></a>
                    </li>
                    <li>
                        <a href="#res" class="waves-effect"><span class="hide-menu" style="color: #d9b38c;">Reservations</span></a>
                    </li>
                    <li>
                        <a href="#com" class="waves-effect"><span class="hide-menu" style="color: #d9b38c;">Complains</span></a>
                    </li>
                   <!-- Marwa  - Edit option for admin -->
                    <li>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select id="formSel" style="background-color: #602020; border-color:#602020; color: #d9b38c; overflow-y: hidden;">
                        <option value="-">To edit</option>
                        <option value="1">Restaurant</option>
                        <option value="2">Menu</option>
                        <option value="3">Branches</option>
                        <option value="4">Order</option> 
                        <option value="5">Reservation</option>
                        <option value="6">Table</option>
                    </select>

                    <div id="myModal" class="modal fade">
                        <div class="modal-dialog" style="width: 85%; background: #F2F2F2;">
                            <div class="modal-content">
                                <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <!-- Restaurant Information Edit -->
                                <form id="form1" action="../../php/Restaurant.php" method="POST">
                                    <h4 class="modal-title">Restaurant Details</h4>
                                    <table class="table table-hover table-dark">
                                    <thead>
                                        <tr>
                                            <th scope="col">Name</th>
                                            <th scope="col">Time Start</th>
                                            <th scope="col">Time End</th>
                                            <th scope="col">Kids Menu</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query = "SELECT name, timeStart, timeEnd, kidsMenu FROM restaurant WHERE id = '".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                    throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                } catch(Exception $e ) {
                                                    echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                    echo nl2br($e->getTraceAsString());
                                                }
                                            }
                                            while($row = mysqli_fetch_array($res))
                                            {
                                                $name = $row['name'];
                                                $timeStart = $row['timeStart'];
                                                $timeEnd = $row['timeEnd'];
                                                $kidsMenu = $row['kidsMenu'];
                                                if($kidsMenu == 1)
                                                {
                                                    $k = 'yes';
                                                }
                                                else
                                                    $k = 'no';
                                                echo "<tr>";
                                                echo "<td>"."<input type='text' name='name' value='{$row['name']}'>"."</td>";
                                                echo "<td>"."<input type='text' name='timeStart' value='{$row['timeStart']}'>"."</td>";
                                                echo "<td>"."<input type='text' name='timeEnd' value='{$row['timeEnd']}'>"."</td>";
                                                echo "<td>"."<input type='text' name='kidsMenu' value='$k'>"."</td>";
                                                echo "<td>"."<input type='submit' name='Edit' value='Edit'>"."</td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                    </tbody>
                                    </table>
                                </form>
                                <!--Menu Edit -->
                                <form id="form2" action="../../php/Menu.php" method="POST">
                                    <h4 class="modal-title">Menu Details</h4>
                                    <table class="table table-hover table-dark">
                                        <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Name</th>
                                                <th scope="col">Price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <?php
                                                $query = "SELECT id, name, price FROM item where resID='".$resID."'";
                                                $res = $mysqli->query($query);
                                                if ($mysqli->error) {
                                                    try {    
                                                        throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                    } catch(Exception $e ){
                                                        echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br>";
                                                        echo nl2br($e->getTraceAsString());
                                                    }
                                                }
                                                while($row = mysqli_fetch_array($res))
                                                {
                                                    $h = $row['id'];
                                                    echo "<tr>";
                                                    echo "<th scope='row'>".$row['id']."</th>";
                                                    echo "<td>".$row['name']."</td>";
                                                    echo "<td>".$row['price']."</td>";
                                                    echo "<td>"."<input type='submit' name='Delete' value='Delete'>"."</td>";
                                                    echo "<td>";
                                                    echo "<input type='hidden' name='idd' value='$h'>";
                                                    echo "</td>";
                                                    echo "</tr>";
                                                }
                                            ?>
                                            <tr>
                                                <th scope='row' >ID</th>
                                                <td><input type='text' name='name'></td>
                                                <td><input type='text' name='price'></td>
                                                <td><input type='submit' name='Add' value='Add Item'></td>
                                            </tr>
                                            <tr>
                                                <td><input type='text' name='id'></td>
                                                <td><input type='text' name='name1'></td>
                                                <td><input type='text' name='price1'></td>
                                                <td><input type='submit' name='Edit' value='Edit Item'></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>
                                <!--Branches Edit -->
                                <form id="form3" action="../../php/Branch.php" method="POST">
                                    <h4 class="modal-title">Branches Details</h4>
                                    <table class="table table-hover table-dark"  style="width: 600px;">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Address</th>
                                            <th scope="col"># of Table</th>
                                            <th scope="col">Delivery</th>
                                            <th scope="col">Kids Area</th>
                                            <th scope="col">Smoking Area</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query = "SELECT id, address, numOfTables, delivery, kidsArea, smokingArea FROM branch where resID ='".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                    throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                } catch(Exception $e ) {
                                                    echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                    echo nl2br($e->getTraceAsString());
                                                }
                                            }
                                            while($row = mysqli_fetch_array($res))
                                            {
                                                $address = $row['address'];
                                                $numOfTables = $row['numOfTables'];
                                                $delivery = $row['delivery'];
                                                $kidsArea = $row['kidsArea'];
                                                $smokingArea = $row['smokingArea'];
                                                $h =$row['id'];
                                                echo "<tr>";
                                                echo "<th scope='row'>".$row['id']."</th>";
                                                echo "<td>".$address."</td>";
                                                echo "<td>".$numOfTables."</td>";
                                                if($delivery == 1){
                                                    $d = "yes";
                                                }
                                                else
                                                    $d = "no";
                                                echo "<td>".$d."</td>";
                                                if($kidsArea == 1){
                                                    $k = "yes";
                                                }
                                                else
                                                    $k = "no";
                                                echo "<td>".$k."</td>";
                                                if($smokingArea == 1){
                                                    $s = "yes";
                                                }
                                                else
                                                    $s = "no";
                                                echo "<td>".$s."</td>";
                                                echo "<td>";
                                                echo "<input type='submit' name='Delete' value='Delete'>"."</td>";
                                                echo "<td>";
                                                echo "<input type='hidden' name='idd' value='$h'>";
                                                echo "</td>";
                                                echo "</tr>";                   
                                            }
                                        ?>
                                        <tr>
                                        <th scope='row' >ID</th>
                                            <td><input type='text' name='a'></td>
                                            <td><input type='text' name='n' ></td>
                                            <td><input type='text' name='d' ></td>
                                            <td><input type='text' name='k' ></td>
                                            <td><input type='text' name='s'></td>
                                            <td><input type='submit' name='Add' value='Add Branch'></td>
                                        </tr>
                                        <tr>
                                            <td><input type='text' name='id'></td>
                                            <td><input type='text' name='address'></td>
                                            <td><input type='text' name='numOfTables' ></td>
                                            <td><input type='text' name='delivery' ></td>
                                            <td><input type='text' name='kidsArea' ></td>
                                            <td><input type='text' name='smokingArea'></td>
                                            <td><input type='submit' name='Edit' value='Edit Branch'></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                            <!--Order Edit -->
                            <form id="form4" action="../../php/Order.php" method="POST">
                                <h4 class="modal-title">Orders Details</h4>
                                <table class="table table-hover table-dark" id="orderTable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Meals Number</th>
                                            <th scope="col">Meals Name</th>
                                            <th scope="col">Delivery</th>

                                            <th scope="col">Price</th>
                                            <th scope="col">Done</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query = "SELECT id, numOfOrders, items, delivery, price, done FROM orders WHERE resID = '".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                    throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                } catch(Exception $e ) {
                                                    echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                    echo nl2br($e->getTraceAsString());
                                                }
                                            }
                                            while($row = mysqli_fetch_array($res))
                                            {      
                                                $noOfmeals = $row['numOfOrders'];
                                                $m = $row['items'];
                                                $delivery = $row['delivery'];
                                                $p = $row['price'];
                                                $done = $row['done'];
                                                $h = $row['id'];
                                                echo "<tr>";
                                                echo "<th scope='row' >".$row['id']."</th>";
                                                echo "<td>".$noOfmeals."</td>";
                                                echo "<td>".$m."</td>";
                                                if($delivery == 1){
                                                    $d = "yes";
                                                }
                                                else
                                                    $d = "no";
                                                echo "<td>".$d."</td>";
                                                echo "<td>".$p."</td>";
                                                if($done == 1){
                                                    $do = "yes";
                                                }
                                                else
                                                    $do = "no";
                                                echo "<td>".$do."</td>";
                                                echo "<td>";
                                                echo "<input type='submit' name='Delete' value='Delete'>"."</td>";
                                                echo "<td>";
                                                echo "<input type='hidden' name='idd' value='$h'>";
                                                echo "</td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                        <tr>
                                            <td><input type='text' name='id'></td>
                                            <td><input type='text' name='numOfOrders'></td>
                                            <td><input type='text' name='items'></td>
                                            <td><input type='text' name='delivery'></td>
                                            <td><input type='text' name='price' ></td>
                                            <td><input type='text' name='done'></td>
                                            <td><input type='submit' name='Edit' value='EditOrder'></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                            <!--Reservation Edit -->
                            <form id="form5" action="../../php/Reservation.php" method="POST">
                                <h4 class="modal-title">Reservations Details</h4>
                                <table class="table table-hover table-dark">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col"># of People</th>
                                            <th scope="col">Branch ID</th>
                                            <th scope="col">Table ID</th>
                                            <th scope="col">Time Reserved</th>
                                            <th scope="col">Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query = "SELECT * FROM reservation where resID ='".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                    throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                } catch(Exception $e ) {
                                                    echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                    echo nl2br($e->getTraceAsString());
                                                }
                                            }
                                            while($row = mysqli_fetch_array($res))
                                            {      
                                                $branchID = $row['branchID'];
                                                $numOfPeople = $row['numOfPeople'];
                                                $tableID = $row['tableID'];
                                                $timeReserved = $row['timeReserved'];
                                                $date = $row['date'];  
                                                $h = $row['ID'];      
                                                echo "<tr>";
                                                echo "<th scope='row' >".$row['ID']."</th>";
                                                echo "<td>".$numOfPeople."</td>";
                                                echo "<td>".$branchID."</td>";
                                                echo "<td>".$tableID."</td>";
                                                echo "<td>".$timeReserved."</td>";
                                                echo "<td>".$date."</td>";
                                                echo "<td>";
                                                echo "<input type='submit' name='Delete' value='Delete'>";
                                                echo "</td>";
                                                echo "<td>";
                                                echo "<input type='hidden' name='idd' value='$h'>";
                                                echo "</td>";
                                                echo "</tr>";
                                            }
                                        ?>
                                        <tr>
                                            <td><input type='text' name='id'></td>
                                            <td><input type='text' name='numOfPeople'></td>
                                            <td><input type='text' name='branchID'></td>
                                            <td><input type='text' name='tableID'></td>
                                            <td><input type='text' name='timeReserved'></td>
                                            <td><input type='text' name='date'></td>
                                            <td><input type='submit' name='Edit' value='Edit'></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                            <!--Table Edit -->
                            <form id="form6" method="POST" action="../../php/Table.php">
                                <h4 class="modal-title">Table Details</h4>
                                <table class="table table-hover table-dark">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Branch ID</th>
                                            <th scope="col"># of seats</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query = "SELECT ID, branchID, numOfSeats FROM tables where resID = '".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                    throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                } catch(Exception $e ) {
                                                    echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                    echo nl2br($e->getTraceAsString());
                                                }
                                            }
                                            while($row = mysqli_fetch_assoc($res))
                                            {      
                            $h = $row['ID'];
                            echo "<tr>";
                            echo "<th scope='row' >".$row['ID']."</th>";
                            echo "<td>".$row['branchID']."</td>";
                            echo "<td>".$row['numOfSeats']."</td>";
                            echo "<td>";
                            echo "<input type='submit' name='Delete' value='Delete'></a>";
                            echo "</td>";
                            echo "<td>";
                            echo "<input type='hidden' name='idd' value='$h'></a>";
                            echo "</td>";
                            echo "</tr>";
                       }
                        ?>
                        <tr>
                            <input type="hidden" name="id" value=>
                            <th scope='row' >ID</th>
                            <td><input type='text' name='b' ></td>
                            <td><input type='text' name='n' ></td>
                            <td><input type='submit' name='Add' value='Add Table'></td>
                        </tr>
                        <tr>
                            <td><input type='text' name='id' ></td>
                            <td><input type='text' name='b1' ></td>
                            <td><input type='text' name='n1' ></td>
                            <td><input type='submit' name='Edit' value='Edit Table'></td>
                            </tr>
                    </tbody>
                    </table>
                    </form>

                    </div>
                    </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                    </div><!-- /.modal -->
                    </li>
                    <!-- End Edit Option-->
                    <li>
                        <a href="../../php/LogOut.php" class="waves-effect"><span class="hide-menu" style="color: #d9b38c;">LogOut</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Left navbar-header end -->
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <!-- row -->
                <div class="row">
                    <!--col -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" >
                        <div class="white-box" >
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i data-icon="E" class="linea-icon linea-basic"></i>
                                    <h5 class="text-muted vb">Orders</h5> </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <?php
                                    $query = "SELECT * FROM orders WHERE resID = '".$resID."'";
                                    $result=mysqli_query($mysqli,$query);
                                    $rowcount=mysqli_num_rows($result);
                                    ?>
                                    <h3 class="counter text-right m-t-15 text-danger"><?php echo $rowcount; ?></h3> </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic" data-icon="&#xe00b;"></i>
                                    <h5 class="text-muted vb">Reservations</h5> </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <?php
                                    $query = "SELECT * FROM reservation WHERE resID = '".$resID."'";
                                    $result=mysqli_query($mysqli,$query);
                                    $rowcount=mysqli_num_rows($result);
                                    ?>
                                    <h3 class="counter text-right m-t-15 text-primary"><?php echo $rowcount; ?></h3> </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic" data-icon="&#xe00b;"></i>
                                    <h5 class="text-muted vb">Complains</h5> </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <?php
                                    $query = "SELECT * FROM complaint WHERE resID = '".$resID."'";
                                    $result=mysqli_query($mysqli,$query);
                                    $rowcount=mysqli_num_rows($result);
                                    ?>
                                    <h3 class="counter text-right m-t-15 text-primary"><?php echo $rowcount ?></h3> </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                <!--row -->
                
                <!--row -->
                <div class="row" id="ord">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">Orders</h3>
                            <div class="table-responsive">
                                <table class="table ">
                                    <thead>
                                        <tr>
                                            <th>NAME</th>
                                            <th># Of Orders</th>
                                            <th>Time</th>
                                            <th>Delivery</th>
                                            <th>Price</th>
                                            <th>Delivered at</th>
                                            <th>Number of Customer</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query ="SELECT orders.items, orders.numOfOrders, orders.time, orders.delivery, orders.price , orders.timeDelivered, user.phone FROM orders INNER JOIN user ON orders.userID = user.id where resID = '".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                        throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                    } catch(Exception $e ) {
                                                        echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                        echo nl2br($e->getTraceAsString());
                                                    }
                                                }
                                            while($row = mysqli_fetch_array($res))
                                            {
                                                echo "<tr>";
                                                echo "<td class='txt-oflo'>".$row['items']."</td>";
                                                echo "<td class='txt-oflo'>".$row['numOfOrders']."</td>";
                                                echo "<td class='txt-oflo'>".$row['time']."</td>";
                                                echo "<td class='txt-oflo'>".$row['delivery']."</td>";
                                                echo "<td class='txt-oflo'>".$row['price']."</td>";
                                                echo "<td class='txt-oflo'>".$row['timeDelivered']."</td>";
                                                echo "<td class='txt-oflo'>".$row['phone']."</td>";
                                                echo "</tr>";

                                            }
                                        ?>
                                    </tbody>
                                </table></div>
                        </div>
                    </div>
                </div>
                <div class="row" id="cus">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">Customers</h3>
                            <div class="table-responsive">
                                <table class="table ">
                                    <thead>
                                        <tr>
                                            <th>NAME</th>
                                            <th>ADDRESS</th>
                                            <th>MOBILE</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <?php
                                            $query = "SELECT orders.userID, user.id, user.address, user.name, user.phone FROM orders INNER JOIN user ON orders.userID = user.id where resID = '".$resID."'";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                        throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                    } catch(Exception $e ) {
                                                        echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                        echo nl2br($e->getTraceAsString());
                                                    }
                                                }
                                            while($row = mysqli_fetch_array($res))
                                            {
                                                echo "<tr>";
                                                echo "<td class='txt-oflo'>".$row['name']."</td>";
                                                echo "<td class='txt-oflo'>".$row['address']."</td>";
                                                echo "<td class='txt-oflo'>".$row['phone']."</td>";
                                                echo "</tr>";

                                            }
                                        ?>
                                    </tbody>
                                </table></div>
                        </div>
                    </div>
                </div>
                <div class="row" id="res">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">Reservations</h3>
                            <div class="table-responsive">
                                <table class="table ">
                                    <thead>
                                        <tr>
                                            <th>Number Of People</th>
                                            <th>Branch ID</th>
                                            <th>TABLE</th>
                                            <th>Time</th>
                                            <th>DATE</th>
                                            <th>Time Made</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <?php
                                            $query = "SELECT * FROM reservation where resID ='".$resID."' ";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                        throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                    } catch(Exception $e ) {
                                                        echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                        echo nl2br($e->getTraceAsString());
                                                    }
                                                }
                                            while($row = mysqli_fetch_array($res))
                                            {
                                                echo "<tr>";
                                                echo "<td class='txt-oflo'>".$row['numOfPeople']."</td>";
                                                echo "<td class='txt-oflo'>".$row['branchID']."</td>";
                                                echo "<td class='txt-oflo'>".$row['tableID']."</td>";
                                                echo "<td class='txt-oflo'>".$row['timeReserved']."</td>";
                                                echo "<td class='txt-oflo'>".$row['date']."</td>";
                                                echo "<td class='txt-oflo'>".$row['timeMade']."</td>";
                                                echo "</tr>";

                                            }
                                        ?>
                                    </tbody>
                                </table></div>
                        </div>
                    </div>
                </div>
                <div class="row" id="com">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">Complians</h3>
                            <div class="table-responsive">
                                <table class="table ">
                                    <thead>
                                        <tr>
                                            <th>User ID</th>
                                            <th>Complain</th>
                                            <th>Branch ID</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            
                                            $query = "SELECT userID, file, branchID FROM complaint WHERE resID = '".$resID."' ";
                                            $res = $mysqli->query($query);
                                            if ($mysqli->error) {
                                                try {    
                                                        throw new Exception("MySQL error $mysqli->error <br> Query:<br> $query", $mysqli->errno);    
                                                    } catch(Exception $e ) {
                                                        echo "Error No: ".$e->getCode(). " - ". $e->getMessage() . "<br >";
                                                        echo nl2br($e->getTraceAsString());
                                                    }
                                                }
                                            while($row = mysqli_fetch_array($res))
                                            {
                                                echo "<tr>";
                                                echo "<td class='txt-oflo'>".$row['userID']."</td>";
                                                echo "<td class='txt-oflo'>".$row['file']."</td>";
                                                echo "<td class='txt-oflo'>".$row['branchID']."</td>";
                                                echo "</tr>";

                                            }
                                        ?>
                                    </tbody>
                                </table></div>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                <!-- row -->
                <!-- /.row -->
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- jQuery -->
    <script src="../static/plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../static/pixel-html/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Menu Plugin JavaScript -->
    <script src="../static/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
    <!--slimscroll JavaScript -->
    <script src="../static/pixel-html/js/jquery.slimscroll.js"></script>
    <!--Wave Effects -->
    <script src="../static/pixel-html/js/waves.js"></script>
    <!--Counter js -->
    <script src="../static/plugins/bower_components/waypoints/lib/jquery.waypoints.js"></script>
    <script src="../static/plugins/bower_components/counterup/jquery.counterup.min.js"></script>
    <!--Morris JavaScript -->
    <script src="../static/plugins/bower_components/raphael/raphael-min.js"></script>
    <script src="../static/plugins/bower_components/morrisjs/morris.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../static/pixel-html/js/custom.min.js"></script>
    <script src="../static/pixel-html/js/dashboard1.js"></script>
    <script src="../static/plugins/bower_components/toast-master/js/jquery.toast.js"></script>
    <script src="js/custom.min.js"></script>
    <script src="js/dashboard1.js"></script>
    <script src="../plugins/bower_components/toast-master/js/jquery.toast.js"></script>
    <!-- Script Marwa -->
    <script src="../static/js/formscript.js"></script>
</body>

</html>
