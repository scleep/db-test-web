<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript" src="/resources/js/app.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.2.0.min.js" ></script>

<title>DB Test</title>
</head>
<body>
	<h1>DB TEST - Select DB Type.</h1>
  	<hr>

  	<div class="form">
    	<form action="selectDB" method="post">
    		<div><input type="radio" checked="checked" name="DBType" value="redis"/>Redis</div>
    		<div><input type="radio" name="DBType" value="mariaDB"/>MariaDB</div>
    		<div><input type="radio" name="DBType" value="rabbitmq"/>RabbitMQ</div>
    		<div><input type="radio" name="DBType" value="kafka"/>Kafka</div>
    		<div><br><input type="submit" value="Submit"></div>
    	</form>
  	</div>
	
<script type="text/javascript">
	
</script>
</body>
</html>