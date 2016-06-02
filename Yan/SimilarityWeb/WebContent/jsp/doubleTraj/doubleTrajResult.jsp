<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="../css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="../css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/common.css">
    <link rel="stylesheet" type="text/css" href="../css/doubleTrajResult.css">

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
	<script type="text/javascript" src="../js/zBaiduMap.js"></script>

	<title>结果显示</title>
</head>
<body>

 <%! String result=""; %>
	<%  
 	String tem=request.getAttribute("actionResult").toString();
 	if (tem.equals("success")){
		result="两条轨迹的相似度为:"+request.getAttribute("similarity").toString()+"%";
 	}else if(tem.equals("input")){
 		result="所选择计算轨迹文件类型与输入文件不匹配";
 	}else if(tem.equals("error")){
 		result="输入文件名找不到，文件传输有误";
 	}else{
 		result="未输入文件！！";
 	}
 	%>
	
 
 <!-- 导航条 -->
	<nav class="navbar navbar-default navbar-fixed-top">
	  <div class="container">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="../../">轨迹相似度计算系统</a>
	    </div>
	    <div id="navbar" class="collapse navbar-collapse">
	      <ul class="nav navbar-nav">
	        <li class="active"><a href="doubleTraj.jsp">双轨迹模式</a></li>
	        <li><a href="../moreTraj/moreTraj.jsp">多轨迹模式</a></li>
	        <li><a href="../viewTraj/viewTraj.jsp">可视化轨迹</a></li>
	        <li><a href="../setting/setting.jsp">设置</a></li>	        
	      </ul>
	    </div>
	  </div>
	</nav>
	<div class="container"></div>
	<footer class="footer">
	  <div class="container">
	    <p class="text-muted">Similarity_HHU SC2016 </p>
	  </div>
	</footer>

	<table border="0">
		<tr>
			<td width="30%" valign="top">
				计算结果：<br/>&nbsp;&nbsp <%= result %>
			</td>
			<td rowspan="2"><div id="allmap"><h2>地图<h2></div></td>
		</tr>
		<tr>
			<td align="center" height="10%"><button onclick="drawTraj()">可视化轨迹</button></td>
		</tr>
	</table>
</body>
</html>
<script type="text/javascript">
	function drawTraj(){
		showMap("${strTrajs}");
	}
</script>

