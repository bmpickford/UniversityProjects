<div class="navBar">
    <div class="col-md-12 navigation nav-background">
        <div class=".col-md-6 .col-md-offset-3">
            <div class="row">
                <ul>
                    <div class="col-md-4 col-md-offset-4">
                        <li class="active"><a href="index.php">Home</a></li>
                        <li class="active"><a href="individualEvent.php">Events</a></li>
                        <li class="active"><a href="contact.php">Contact</a></li>
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
    </div>
</div>
