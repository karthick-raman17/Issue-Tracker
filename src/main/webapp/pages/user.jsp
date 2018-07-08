<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>User profile</title>
<!--     Fonts and icons     -->
<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />
<link rel="stylesheet" href="../assets/css/material-dashboard.css?v=2.0.0">

</head>

<body class="">
	<div class="wrapper">
		<div class="sidebar" data-color="purple" data-background-color="white">

			<div class="logo">
				<span class="simple-text logo-normal">LOGO</span>
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li class="nav-item "><a class="nav-link" href="/calendar"
						id="calendar-nav"> <div class="material-icons">date_range</div>
							<!-- <p>Calendar</p> -->
					</a></li>
					<li class="nav-item  active"><a class="nav-link"
						href="../pages/user.jsp"> <div class="material-icons">person</div>
							<!-- <p>User Profile</p> -->
					</a></li>

				</ul>
			</div>
		</div>
		<div class="main-panel">
			<!-- Navbar -->
			<nav
				class="navbar navbar-expand-lg navbar-transparent  navbar-absolute fixed-top">
				<div class="container-fluid">
					<!-- div class="navbar-wrapper">
                        <a class="navbar-brand" href="#">Profile</a>
                    </div> -->

					<div class="collapse navbar-collapse justify-content-end"
						id="navigation">

						<ul class="navbar-nav">

							<li class="nav-item"><a class="nav-link" href="#">
									<i class="material-icons">person</i>

							</a></li>
						</ul>
					</div>
				</div>
			</nav>
			<!-- End Navbar -->
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-8">
							<div class="card">
								<div class="card-header card-header-primary">
									<h3 class="card-title">Profile</h3>
									<!-- <h4 class="card-category">Complete your profile</h4> -->
								</div>
								<div class="card-body">
									<form>
										<div class="row">
											<div class="col-md-5">
												<div class="form-group">
													<label class="bmd-label-floating">Email address
														(Username)</label> <input type="text" class="form-control"
														disabled>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-8">
												<div class="form-group">
													<label class="bmd-label-floating">Name</label> <input
														type="text" class="form-control">
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<label class="bmd-label-floating">Mobile</label>
													<div class="row">
														<div class="col-md-2">
															<input type="text" class="form-control">
														</div>
														<div class="col-md-3">
															<input type="text" class="form-control">
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<label class="bmd-label-floating">Address</label>
													<input type="text" class="form-control">
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label class="bmd-label-floating">City</label> <input
														type="text" class="form-control">
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="bmd-label-floating">State</label> <input
														type="text" class="form-control">
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="bmd-label-floating">Zip Code</label> <input
														type="text" class="form-control">
												</div>
											</div>
										</div>
										<!-- <div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<label>About Me</label>
													<div class="form-group">
														 <label class="bmd-label-floating"></label>
														<textarea class="form-control" rows="5"></textarea>
													</div>
												</div>
											</div>
										</div> -->
					<button type="submit" class="btn btn-primary pull-right">Update Profile</button>
										<div class="clearfix"></div>
									</form>
								</div>
							</div>
						</div>
						<div class="col-md-4">

							<div class="card card-profile">
								<div class="card-avatar">
									<a href="#"> <img class="img"
										src="https://lh5.googleusercontent.com/-hGJ-59qiOjg/AAAAAAAAAAI/AAAAAAAAAAA/BSPPzdu2fjs/photo.jpg" />
									</a>
								</div>
								<div class="card-body">
									<h6 class="card-category text-gray">User</h6>
									<h4 class="card-title">Karthick Raman</h4>
									<p class="card-description">Description</p>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<!--  <footer class="footer ">
                <div class="container-fluid">
                    <nav class="pull-left">
                        <ul>
                            <li>
                                <a href="#">
                                  Test
                                </a>
                            </li>   
                        </ul>
                    </nav>
                 
                </div>
            </footer> -->
		</div>
	</div>
</body>
<script src="../assets/js/core/jquery.min.js"></script>
<script>
	document.getElementById("calendar-nav").onclick = function() {
		window.location = "calendar.jsp";
	}
</script>


</html>
