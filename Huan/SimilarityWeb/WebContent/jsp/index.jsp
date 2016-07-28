<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

	<link href="./jsp/css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
	<link href="./jsp/css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">

	<title>轨迹相似度计算系统</title>
</head>
<body background="./jsp/css/images/bg.jpg">
	<!-- 导航条 -->
	<nav class="navbar navbar-default navbar-fixed-top">
	  <div class="container">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="">轨迹相似度计算系统</a>
	    </div>
	    <div id="navbar" class="collapse navbar-collapse">
	      <ul class="nav navbar-nav">
	        <li><a href="doublePattern.action">双轨迹模式</a></li>
	        <li><a href="morePattern.action">多轨迹模式</a></li>
	        <li><a href="viewTraj.action">可视化轨迹</a></li>
	        <li><a href="setting.action">设置</a></li>
	      </ul>
	    </div>
	  </div>
	</nav>

	<!-- Begin page content -->
	<div class="container">
	  <div class="page-header">
	    <h1 align="center">欢迎使用轨迹相似度计算系统</h1>
	  </div>
	</div>

	<footer class="footer">
	  <div class="container">
	    <p class="text-muted" align="center">Similarity_HHU SC2016 </p>
	  </div>
	</footer>

</body>
</html>