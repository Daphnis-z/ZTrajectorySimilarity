<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>相似度计算结果</title>
</head>
<body>
	<%! String result=""; %>
	<% 
	String tem=request.getAttribute("actionResult").toString();
	if (tem.equals("success")){
		result="两条轨迹的相似度为:"+request.getAttribute("similarity").toString();
	}else if(tem.equals("input")){
		result="所选择计算轨迹文件类型与输入文件不匹配";
	}else if(tem.equals("error")){
		result="输入文件名找不到，文件传输有误";
	}else{
		result="未输入文件";
	}
	%>
	<h1><%= result%></h1>
	<br/>
	<form action="showPolyline.action" method="post" enctype="multipart/form-data">
	<b>是否轨迹可视化？</b>
	<input type="submit" value="确定"/>
	</form>
	
	
</body>
</html>