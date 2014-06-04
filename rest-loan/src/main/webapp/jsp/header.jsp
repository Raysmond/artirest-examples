<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Raysmond">
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" />
<style type="text/css" media="screen">
@import
url(<c:url
value=
"/css/style.css"
/>);
</style>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<c:url value="/js/jquery.js"/>"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<title>RESTful Loan Process Manangement</title>
</head>
<body>
	<!-- Fixed navbar -->
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/rest-loan/">RESTful Loan BPM</a>
			</div>
			<div class="navbar-collapse collapse">
				<%
					String userRole = (String) request.getSession().getAttribute("USER_ROLE");
					boolean isAdmin = userRole != null && userRole.equals("manager");
				%>
				<ul id="main-nav" class="nav navbar-nav">
					<li><a href="/rest-loan/loan/create">Apply</a></li>
					<c:if test="<%=isAdmin%>">
						<li><a href="/rest-loan/loan/list">Administration</a></li>
					</c:if>
					<li><a href="/rest-loan/jsp/about.jsp">About</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="https://github.com/Raysmond/loan" target="_blank">Github
							Repo</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>