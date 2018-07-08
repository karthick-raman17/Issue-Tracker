<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/signup.css">

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 -->
 <script src="https://smartlock.google.com/client"></script>
</head>
<body>
<div class="voice-box-container"><span id="voice-box" class="noDisplay" style="display: none;"></span></div>

	<div class="signupWindow">
		<div class="container wrapper">
			<div class="row">
				<div class="col">
				<form action="/#" method="post" onsubmit="javascript :return validate()" autocomplete="on">
					<label>Name</label>	<input type="text" id="name" name="name" placeholder="Name">
					<!-- <label>Last Name(optional)</label>	<input type="text" id="lastName" name="lastName" placeholder="Last Name"> --> 
					<label>E-mail</label>	<input type="email" id="email" name="email" placeholder="E-mail(Username)">
					<label>Password</label>	<input type="password" id="password" name="password" placeholder="Password">
					<label>Confirm Password</label>	<input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password">
						<input type="submit" class="signup-submit" value="Submit"> <input type="button" id="backToIndex" class="back-button" value="Cancel">
					
				</div>
				<!-- <div class="col">
				<input type="file" name="pic" accept="image/*">
				</div> -->
				</form>	
			</div>
			</div>
		</div>
	</div>
	<script src="assets/js/signup.js"></script>
	
</body>
</html>
