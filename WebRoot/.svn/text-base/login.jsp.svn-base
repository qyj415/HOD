<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户登陆</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		body{
			background-color: #211919;
			overflow: hidden;
			margin: 0px;
			font-size: 12px;
		}
		.deskDiv{
			position: absolute;
			width:100%;
			height:100%;
			margin:0px;
		}
		#wrapper2{
			width:213px;
			float:left;
			height:292px;
			bottom:0%;
			background:url('images/backgroud-left.gif')/*#409da5*/;
			position: absolute;
		}
		#wrapper3{
			width:147px;
			height:243px;
			float:right;
			top:10%;
			right:0%;
			background:url('images/backgroud-right.gif')/*#409da5*/;
			background-repeat:no-repeat;
			position: absolute;
		}
		#wrapper1{
			width: 859px;
			height: 331px;
			top: 50%;
			left: 50%;
			margin-left:-430px;
			margin-top:-165px;
			background: url("images/logins.gif");
			background-repeat: no-repeat;
			position: absolute;
		}
		#wrapper4{
			width: 434px;
			height: 267px;
			top:50%;
			left: 50%;
			margin-left:-217px;
			margin-top:-134px;
			background: url("images/login.gif");
			background-repeat: no-repeat;
			position: absolute;
		}
		#username{
			margin-top: 147px;
			margin-left: 150px;
		}
		#password{
			margin-top: 28px;
			margin-left: 150px;
		}
		#sub{
			margin-left: 304px;
			margin-top: -26px;
		}
		.btn{
			background: url("images/loginbtn.gif");
			width: 26px;
			height: 25px;
			overflow: hidden;
			cursor: pointer;
			border: 0;
		}
	</style>
	<script type="text/javascript">
		function userLogin()
		{
			var uid=document.getElementById("uid");
			var pwd=document.getElementById("upwd");
			if(uid.value=="")
			{
				document.getElementById("uidInfo").innerHTML='请输入用户名!';
				uid.focus();
				return false;
			}
			else
			{
				if(uid.value.length<2||uid.value.length>16)
				{
					document.getElementById("uidInfo").innerHTML='请输入2-16个字符!';
					uid.focus();
					return false;
				}
			}
			if(pwd.value=="")
			{
				document.getElementById("passInfo").innerHTML='请输入密码!';
				pwd.focus();
				return false;
			}
			else
			{
				if(pwd.value.length<6||pwd.value.length>16)
				{
					document.getElementById("passInfo").innerHTML='请输入6-16个字符!';
					pwd.focus();
					return false;
				}
			}
		}
		function init()
		{
			document.getElementById("uid").focus();
		}
	</script>
  </head>
  
  <body onload="init()">
   <div class="deskDiv">
   		<div id="wrapper3"></div>
		<div id="wrapper2"></div>
		<div id="wrapper1"></div>
		<div id="wrapper4">
			<form action="${pageContext.request.contextPath}/login!doLogin.do" method="post">
				<div id="username">
					<input type="text" name="username" style="width: 180px;height: 25px;" id="uid"/><span id="uidInfo" style="color: red;"></span>
				</div>
				<div id="password">
					<input type="password" name="userpassword" style="width: 155px;height: 25px;" id="upwd"/>
				</div>
				<div id="sub">
					<input type="submit" value="" class="btn" onclick="return userLogin()"><span id="passInfo" style="color: red;"></span>
				</div>
			</form>
		</div>
   </div>
  </body>
</html>
