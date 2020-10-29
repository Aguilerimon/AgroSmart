<?php
    $con = mysqli_connect("localhost", "root", "", "agrosmart");
    
    $email = $_POST["Email"];
    $password = $_POST["Password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM users WHERE Email = ? AND Password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $User_Id, $Name, $PhoneNumber, $Email, $Password);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement))
    {
        $response["success"] = true;  
        $response["Name"] = $Name;
        $response["PhoneNumber"] = $PhoneNumber;
        $response["Email"] = $Email;
        $response["Password"] = $Password;
    }
    
    echo json_encode($response);
?>