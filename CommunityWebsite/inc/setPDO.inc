<?php
	$pdo = new PDO('mysql:host=team94.cte25mh2xbhe.ap-southeast-2.rds.amazonaws.com:3306;dbname=TEAM94', 'team94', 'stickitin');
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
 /*

    $pdo = new PDO('mysql:host=54.191.161.12;dbname=TEAM94;port=3306', 'root', 'password');
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);



    $pdo = new PDO('mysql:host=localhost;dbname=TEAM94', 'root', 'password');
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 
    */
?>
