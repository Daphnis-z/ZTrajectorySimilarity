<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Similarity</title>
</head>
<body>
	<h1 align="center">Welcome to Similarity</h1>
	<b>请选择不同的轨迹相似度计算模式：</b><br/>
	<form action="doublePattern.action" method="post" enctype="multipart/form-data">
	<input type="submit" value="双轨迹计算模式"/>
	</form><br/>
	<form action="morePattern.action" method="post" enctype="multipart/form-data">
	<input type="submit" value="多轨迹计算模式"/>
	</form><br/>
</body>
</html>