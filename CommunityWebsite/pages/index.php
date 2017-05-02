<!DOCTYPE html>

<?php require '../inc/header.inc'; ?>



<body>
    <div class="container-fluid index-body">
        <!-- HOME NAVIGATION -->
        <div class="col-md-12 navigation">
                <div class="row">
                    <ul>
                        <div class="col-md-4 col-md-offset-4">
                            <li class="active"><a href="index.php">Home</a></li>
                            <li class="active"><a href="individualEvent.php">Events</a></li>
                            <li class=""><a href="contact.php">Contact</a></li>
                            <li class="active"><a href="about.php">About</a></li>
                        </div>
                        <div class="col-md-2 col-md-offset-2">
                            <li class="active"><a href="login.php">
                            <?php
                            session_start();
                            if(!isset($_SESSION['loggedIn'])){
                                echo "Login";
                                } else {
                                    echo "Logout";
                                } 
                              
                                ?>
                            </a></li>
                            <li class="active">
                                <?php
                                if(!isset($_SESSION['loggedIn'])){
                                echo "<a href='register.php'>";
                                echo "Register";
                                echo "</a>";
                                } else {
                                    echo "<a href='individualEvent.php'>";
                                    echo "Donate";
                                    echo "</a>";
                                } 
                              
                                ?>
                            </li>
                        </div>
                    </ul>
            </div>
        </div>
        <!-- END HOME NAVIGATION -->
        <div class="jumbotron text-center main-statement">
            <h1>Come and Join Your Local Community Events<br><small>You'll get a lot out of it</small></h1>
        </div>
        <div class="centerBlock text-center"> <!-- Event button -->
            <button type="button" class="btn btn-event">View Events</button>
        </div>
        <?php
        require '../inc/footer.inc';
        ?>
    </div>
</body>

