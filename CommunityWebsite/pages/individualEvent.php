<!DOCTYPE html>

<?php require '../inc/header.inc'; ?>



<body>
    <div class="container-fluid event">
        <!-- HOME NAVIGATION -->
        <?php
            require '../inc/nav.inc';
        ?>
        <!-- END HOME NAVIGATION -->


		<div class = "event-head">
			<h1>City Meetup at Domino's Pizza!</h1>
		</div>

		<div class = "event-donations">
			<h3>Donation Goal: <b>$150</b></h3>
			<h3>Amounted Donated: <b>$85</b></h3>
		</div>

		<div class = "event-sponsors">
			<h2>Sponsors</h2>
			<h4>Coca-Cola</h4>
			<h4>Queensland University of Technology</h4>
		</div>

		<!-- Event information and description row -->

		<div class = "row">
			<div class = "col-md-2"></div>
			<div class = "col-md-4 event-information">
				<h3>Details</h3>
				<table class = "table table-bordered">
					<tr>
						<td>Address</td><td>105 Albert Street, BRISBANE QLD 4000</td>
					</tr>
					<tr>
						<td>Date</td><td>25th September 2016 7:00pm</td>
					</tr>
					<tr>
						<td>Finish</td><td>25th September 2016 9:00pm</td>
					</tr>
					<tr>
						<td>Cost</td><td>To be decided...</td>
					</tr>
				</table>
			</div>
			<div class = "col-md-4 event-description">
				<h3>What it's about</h3>
				<p>Hey guys! It's (hopefully) the end of everyone's mid-semester exams! Let's celebrate by grabbing some pizza together in the city after our classes!</p>
			</div>
			<div class = "col-md-2"></div>
		</div>

		<!-- # people going and button row -->
		<div class = "row">
			<div class = "col-md-4"></div>
			<div class = "col-md-4 event-rsvp">
				<p><b>23</b> people have RSVP'd to this event. Click the button below to RSVP now!</p>
				<button type="button" class="btn btn-event">RSVP</button>
			</div>
			<div class = "col-md-4"></div>
		</div>

        <?php
        require '../inc/footer.inc';
        ?>
    </div>
</body>

