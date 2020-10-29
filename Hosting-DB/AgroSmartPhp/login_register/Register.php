<?php
    $con = mysqli_connect("localhost", "root", "", "agrosmart");
    
    $name = $_POST["Name"];
    $phone = $_POST["PhoneNumber"];
    $email = $_POST["Email"];
    $password = $_POST["Password"];
    $statement = mysqli_prepare($con, "INSERT INTO users (Name, PhoneNumber, Email, Password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $name, $phone, $email, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>