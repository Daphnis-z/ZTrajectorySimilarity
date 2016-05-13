<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>Trajectory（弧线）</title>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI">
	</script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js">
	</script>
	<style type="text/css">
		html,body{
			width:100%;
			height:100%;
			margin:0;
			overflow:hidden;
			font-family:"微软雅黑";
		}
	</style>
</head>
<body>
	<div style="width:100%;height:100%;border:1px solid gray" id="container"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("container");
	map.centerAndZoom(new BMap.Point(118.94761,31.90705), 12);
	map.enableScrollWheelZoom();

	//初始化轨迹
	var traj=new Array()
	traj[0]=new BMap.Point(118.92761,31.92705)
	traj[1]=new BMap.Point(118.93761,31.91705)
	traj[2]=new BMap.Point(118.94761,31.90705)
	traj[3]=new BMap.Point(118.95761,31.80705)
	traj[4]=new BMap.Point(118.96761,31.81705)
	traj[5]=new BMap.Point(118.97761,31.82705)
	traj[6]=new BMap.Point(118.98761,31.83705)
	var curve = new BMapLib.CurveLine(traj, {strokeColor:"purple", strokeWeight:3, strokeOpacity:0.5}); //创建弧线对象
	map.addOverlay(curve); //添加到地图中
	curve.enableEditing(); //开启编辑功能
</script>
