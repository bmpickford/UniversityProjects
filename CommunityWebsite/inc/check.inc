<?php
	function checkLogin($email, $password){
		require 'setPDO.inc';
		
		$query = $pdo->prepare('SELECT *
								FROM TEAM94.USER_TB
								WHERE USER_TB.email = :email
                                AND USER_TB.password = SHA2(CONCAT(:password, USER_TB.salt), 0)');

        $query->bindvalue(':password', $password);
		$query->bindvalue(':email', $email);
		$query->execute();
		return $query->rowCount() > 0;	
	}
?>