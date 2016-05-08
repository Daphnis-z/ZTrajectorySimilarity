<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>Trajectory（折线）</title>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap {height:100%; width: 90%;}
	</style>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(118.81761,31.98705), 12);
	map.enableScrollWheelZoom();

	//轨迹数据
		strTrajs="${strTrajs}"
		strTrajs=strTrajs.split(',')
		var traj=new Array(strTrajs.length/2)
		for(var i=0,j=0;i<strTrajs.length;i+=2,j++){
			traj[j]=new BMap.Point(strTrajs[i],strTrajs[i+1])
		}

	var polyline = new BMap.Polyline(traj, {strokeColor:"purple",
		strokeWeight:4, strokeOpacity:0});   //创建折线
	map.addOverlay(polyline);   //增加折线
	polyline.enableEditing();//启用编辑
</script>

