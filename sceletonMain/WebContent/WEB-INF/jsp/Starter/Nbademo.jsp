<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="background: #fff;">



	<div class="container">
		<div class="row">

			<c:forEach var="NbarssList" items="${NbarssList}">

				<div class="col-lg-3" style="border: 1px solid #000;">
					<img class="img-responsive" src="${NbarssList.imageUrl}"
						alt="Card image cap">
					<div class="card-block">
						<h4 class="card-title">${NbarssList.title}</h4>
						<p class="card-text">${NbarssList.description}</p>
						<a href="#" class="btn btn-primary">Go somewhere</a>
					</div>
				</div>
			</c:forEach>






		</div>
	</div>

</body>
</html>
