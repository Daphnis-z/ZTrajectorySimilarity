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

	<title>结果显示</title>
</head>
<body>

<!-- 	<%! String result=""; %>
<%-- 	<%  --%>
// 	String tem=request.getAttribute("actionResult").toString();
// 	if (tem.equals("success")){
// 		result="两条轨迹的相似度为:"+request.getAttribute("similarity").toString();
// 	}else if(tem.equals("input")){
// 		result="所选择计算轨迹文件类型与输入文件不匹配";
// 	}else if(tem.equals("error")){
// 		result="输入文件名找不到，文件传输有误";
// 	}else{
// 		result="未输入文件！！";
// 	}
<%-- 	%> --%>
 -->	
 
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
				计算结果：<br/>&nbsp;&nbsp 相似度：92.13151235%
			</td>
			<td rowspan="2"><div id="allmap"><h2>地图<h2></div></td>
		</tr>
		<tr>
			<td align="center" height="10%"><button onclick="showMap()">可视化轨迹</button></td>
		</tr>
	</table>
</body>
</html>
<script type="text/javascript">
	function showMap(){
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		map.centerAndZoom(new BMap.Point(118.95761,31.80705), 12);
		map.enableScrollWheelZoom();

		//添加地图控件
		map.addControl(new BMap.MapTypeControl()); 
		map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));//左上角比例尺        
		map.addControl(new BMap.NavigationControl());//左上角默认缩放控件    

		//轨迹数据
		var traj=new Array()
		traj[0]=new BMap.Point(118.92761,31.92705)
		traj[1]=new BMap.Point(118.93761,31.91705)
		traj[2]=new BMap.Point(118.94761,31.90705)
		traj[3]=new BMap.Point(118.95761,31.80705)
		traj[4]=new BMap.Point(118.96761,31.81705)
		traj[5]=new BMap.Point(118.97761,31.82705)
		traj[6]=new BMap.Point(118.98761,31.83705)

		var polyline = new BMap.Polyline(traj, {strokeColor:"purple", strokeWeight:5, strokeOpacity:0});   //创建折线
		map.addOverlay(polyline);   //增加折线
	}
</script>