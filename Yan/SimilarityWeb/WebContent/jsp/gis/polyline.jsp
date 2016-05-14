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
		#allmap {height:100%; width: 100%;}
	</style>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(118.95761,31.80705), 12);
	map.enableScrollWheelZoom();

	//轨迹数据
	var traj=new Array()
	traj[0]=new BMap.Point(118.92761,31.92705)
	traj[1]=new BMap.Point(118.93761,31.91705)
	traj[2]=new BMap.Point(118.94761,31.90705)
	traj[3]=new BMap.Point(118.95761,31.80705)
	traj[4]=new BMap.Point(118.96761,31.81705)
	traj[5]=new BMap.Point(118.97761,31.82705)
	traj[6]=new BMap.Point(118.98761,31.83705)

	var polyline = new BMap.Polyline(traj, {strokeColor:"purple",
		strokeWeight:4, strokeOpacity:0});   //创建折线
	map.addOverlay(polyline);   //增加折线
	polyline.enableEditing();//启用编辑
</script>

