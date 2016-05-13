<%@page import="com.adx.resource.Constant"%>
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
		int length=Integer.parseInt(request.getAttribute("fileLength").toString());
		for(int i=0;i<length;i++){
			result="目标轨迹与测试轨迹"+(i+1)+"的相似度为:"+request.getAttribute("similarity["+i+"]").toString();
			out.println("<h1>"+result+"</h1>");
		}
	}else if(tem.equals("input")){
		result="所选择计算轨迹文件类型与输入文件不匹配";
		out.print("<h1>"+result+"</h1>");
	}else if(tem.equals("error")){
		result="输入文件名找不到，文件传输有误";
		out.print("<h1>"+result+"</h1>");
	}else{
		result="未输入文件";
		out.print("<h1>"+result+"</h1>");
	}
	%>
	<br/>
	<form action="showPolyline.action" method="post" enctype="multipart/form-data">
	<b>选择可视化的轨迹？</b><br/>
	测试轨迹：<input type="text" name="numTestTraj" value="1"/>
	<input type="submit" value="确定"/>
	</form>
	
	
</body>
</html>