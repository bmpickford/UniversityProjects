<!DOCTYPE html>
<html>
	<body>
        <?php //Includes
            require '../inc/header.inc';
            require '../inc/setPDO.inc';
            require '../inc/nav.inc';
    		?>
		<div id="container-fluid">
		<?php
			$errors = array();
			$check=$pdo->query("SELECT * FROM USER_TB");
			while ($row = $check->fetch(PDO::FETCH_ASSOC)){ //Makes sure username isn't taken
				$name = $row['email'];
				if ($_POST['email'] == $name){
					echo "</br>";
					echo "Email already taken";
					echo "</br>";
					exit;
				}
			}
			if (isset($_POST['fName']) && isset($_POST['sName']) && isset($_POST['email'])){ //Makes sure all fields are set
					$fname=$_POST['fName'];
					$sname=$_POST['sName'];
					$email=$_POST['email'];
			}
			else { //Redirects back to Register.php if fields aren't set
				header("Location: http://{$_SERVER['HTTP_HOST']}/Team94-SII/pages/register.php");
				echo "All fields are required";
                die();
                exit;
			}
			if ($_POST['password1']==$_POST['password2']){ //Password validation
				$password=$_POST['password1'];
			}
			else {
				header("Location: http://{$_SERVER['HTTP_HOST']}/Team94-SII/pages/register.php");
				echo "Password do not match /php";
                die();
                exit;
			}
			// function generateSalt($max = 15) { //Generates a random salt of 15 chars made of the character list below
			// 	$characterList = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*?";
			// 	$i = 0;
			// 	$salt = "";
			// 	while ($i < $max) { //loops 15 times and assigns it to $salt
			// 		$salt .= $characterList{mt_rand(0, (strlen($characterList) - 1))};
			// 		$i++;
			// 	}
			// 	return $salt;
			// }
            $userType = 'M'; //Basic user type will be member
			$salt = uniqid(); //Generates the salt based on the random characters
			$query=$pdo->prepare("INSERT INTO USER_TB (firstName, lastName, email, userType, password, salt)VALUES(:fname, :sname, :email, :userType, SHA2(CONCAT(:password, :salt), 0), :salt)"); 
			$query->bindvalue(':fname', $fname);
			$query->bindvalue(':sname', $sname);
			$query->bindvalue(':email', $email);
            $query->bindvalue(':userType', $userType);
			$query->bindvalue(':password', $password);
            $query->bindvalue(':salt', $salt);
			$query->execute();//Inserts all the registration data into the DB
			echo "<h5>Thank you, your details have been recorded</h5>";
			?>
		</div>
	<?php
		require '../inc/footer.inc'
	?>
	</body>
</html>