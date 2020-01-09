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
	<h1>DB TEST - Redis</h1>
  	<hr>
  	
	<div id="container">
		<div id="configSetting">
			<h3>Redis-Config</h3>
			<div>
				IP: <input id="redisIP" value="169.56.94.185">
				&nbsp;&nbsp;&nbsp;
				Port: <input id="redisPort" value="6379">
				&nbsp;&nbsp;&nbsp;
				Credential: <input id="redisPw" value="yqs2TMtolf">
				<br><br>
				* Web에서 변경 불가 - 필요시 변경은 application.properties로 해주세요.
			</div>
			<hr>
		</div> <!-- end configSetting -->
		
		<div id="putData">
			<h3>PUT Data</h3>
			<div>
				Key: <input id="redisKey" value="mac">
				&nbsp;&nbsp;&nbsp;
				Value: <input id="redisValue" value="book">
				&nbsp;&nbsp;&nbsp;
				<button id="putData_btn">전송</button>
				<br><br>
				
				자동 입력 - 주기: <input id="period_randomData" value="1"> sec
				&nbsp;&nbsp;&nbsp;
				
				<input id="start_setTimeout" type="button" value="시작"/>
				<input id="stop_setTimeout" type="button" value="중지"/>
			</div>
			<hr>
		</div> <!-- end putData -->
		
		<div id="getData">
			<h3>GET Data</h3>
			<div>
				<button id="getAllData_btn">All Data</button>
				<br><br>
				
				Key: <input id="redisGetKey" value="mac">
				<button id="getKey_btn">전송</button>
			</div>
			<hr>
		</div> <!-- end getData -->
		
		<div id="delData">
			<h3>DELETE Data</h3>
			<div>
				<button id="deAll_btn">All Delete</button>
				<br><br>
				
				Key: <input id="redisDelKey" value="mac">
				<button id="delKey_btn">삭제</button>
			</div>
			<hr>
		</div> <!-- end delData -->
		
	</div> <!-- end container -->
	
<script type="text/javascript">
	$(document).ready(function() {
			var config = {
					redisIP:$('#redisIP').val(),
					redisPort:$('#redisPort').val(),
					redisPw:$('#redisPw').val()
			};

			var inputId = null;
			var count = 0;
		/* PUT ------------------------------------------------------------------------------------------- */
		$('#putData_btn').click(function() {
			var dataMap = {};
			dataMap[$('#redisKey').val()] = $('#redisValue').val();
			var data = $.extend({},config,{insertData:dataMap});
	        $.ajax({
	            url:'/DBTest/redisPut',
	            type:'PUT',
	            data: data,
	            success: function(res){
	                alert("성공");
	            } // end success
	        });// end ajax
	    });

		$('#putRandomData_btn').click(function() {
			var period_randomData = $('#period_randomData').val();
			var term_randomData = $('#term_randomData').val();
			
			var data = $.extend({},config,{redisPeriod:period_randomData, redisTerm:term_randomData});
	        $.ajax({
	            url:'/DBTest/redisPutRandom',
	            type:'PUT',
	            data: data,
	            success: function(res){
	                alert("성공");
	            } // end success
	        });// end ajax
	    });

		$('#start_setTimeout').click(function() {
			startRepeat(count);
	    });

		$('#stop_setTimeout').click(function() {
			StopRepeat();
	    });

		function startRepeat() {
			count++;
			putData(count);
			var period_randomData = $('#period_randomData').val();
			inputId = setTimeout(startRepeat, period_randomData*1000);

		}

		function StopRepeat() {
		    if(inputId != null) {
		        clearTimeout(inputId);
		        count = 0;
		    }
		}

		function putData(count) {
			var key = "key" + count;
			var value = "value" + count;
			var dataMap = {};
			dataMap[key] = value;
			var data = $.extend({},config,{insertData:dataMap});
			
			$.ajax({
	            url:'/DBTest/redisPut',
	            type:'PUT',
	            data: data,
	            success: function(res){
	                // alert("성공");
	            } // end success
	        });// end ajax
		}

		/* ----------------------------------------------------------------------------------------------- */
		
		/* GET ------------------------------------------------------------------------------------------- */
		$('#getAllData_btn').click(function() {
	        $.ajax({
	            url:'/DBTest/redisGetAll',
	            type:'GET',
	            data: config,
	            success: function(res){
	                alert("성공");
	            } // end success
	        });// end ajax
	    });

		$('#getKey_btn').click(function() {
			var dataMap = {};
			dataMap[$('#redisGetKey').val()] = "-";
			var data = $.extend({},config,{insertData:dataMap});
	        $.ajax({
	            url:'/DBTest/redisGetData',
	            type:'GET',
	            data: data,
	            success: function(res){
	                alert("성공");
	            } // end success
	        });// end ajax
	    });
		/* ----------------------------------------------------------------------------------------------- */
		
		/* DELETE ---------------------------------------------------------------------------------------- */
		$('#delKey_btn').click(function() {
			var dataMap = {};
			dataMap[$('#redisDelKey').val()] = "-";
			var data = $.extend({},config,{insertData:dataMap});
	        $.ajax({
	            url:'/DBTest/redisDelKey',
	            type:'DELETE',
	            data: data,
	            success: function(res){
	                alert("성공");
	            } // end success
	        });// end ajax
	    });

		$('#deAll_btn').click(function() {
	        $.ajax({
	            url:'/DBTest/redisAllDelete',
	            type:'DELETE',
	            data: config,
	            success: function(res){
	                alert("성공");
	            } // end success
	        });// end ajax
	    });
		/* ----------------------------------------------------------------------------------------------- */
	});
</script>
</body>
</html>