<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Calendar</title>
<script src='jquery/jquery.min.js'></script>
<script src='jquery/moment.min.js'></script>

<!-- Bootstrap core CSS -->
<link href="bootstrap-3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="css/dashboard.css" rel="stylesheet">
<link href="css/home.css" rel="stylesheet">
<!-- Bootstrap JS -->
<script src="bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
</head>

<body>

	<%
		//allow access only if session exists
		String user = null;
		String email = null;
		String picture = null;
		if (session.getAttribute("user") == null) {
			response.sendRedirect("/");
		} else {
			user = (String) session.getAttribute("user");
			email = (String) session.getAttribute("email");
			picture = (String) session.getAttribute("picture");
		}
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("data"))
					userName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>


	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
		
			<div class="dropdown">
				<a class="dropdown-toggle" type="button" data-toggle="dropdown"><img
					class="profile-picture" src="<%=picture%>"></a>
				<ul class="dropdown-menu user-menu">
					<li><a href="/logout">Logout</a></li>

				</ul>
			</div>

			<div class="navbar-header">
				<a class="navbar-brand" href="index.html">Issue Tracker</a>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">Calendar <span class="sr-only">(current)</span>
					</a></li>

				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header"><%=user%> Calendar
				</h1>

				<div id="insert-calendar"></div>

			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#insert-calendar").load("/pages/calendar.jsp");
		});
	</script>


</body>
</html>