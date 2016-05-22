<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>KMeans</title>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;}
		#allmap {height:100%;width: 100%;}
	</style>
	<link rel="stylesheet" href="./jsp/css/common.css">	
</head>
<body>
	<h1 id="sysHeader">轨迹相似度计算系统</h1>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	//生成随机颜色
	function getRandomColor(){
		strCol="0123456789"
		color="#"
		for(var i=0;i<6;i++){
			n=Math.random()*10000
			n=parseInt(n%10)
			color=color.concat(strCol[n])
		}
		return color
	}
	
	//接收来自后台的轨迹数据
	strTrajs = "${strTrajs}"
	strTrajs = strTrajs.split('@')
	center=strTrajs[0].split(',')//为了设置地图中心点
		
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(center[0], center[1]), 11);
	map.enableScrollWheelZoom();

	//绘制折线轨迹
	for (var ix = 0; ix < strTrajs.length; ix++) {
		strTraj = strTrajs[ix].split(',')
		var traj = new Array(strTraj.length / 2)
		for (var i = 0, j = 0; i < strTraj.length; i += 2, j++) {
			traj[j] = new BMap.Point(strTraj[i], strTraj[i + 1])
		}
				
		color=getRandomColor()		
		var polyline = new BMap.Polyline(traj, {
			strokeColor : color,
			strokeWeight : 4,
			strokeOpacity : 0
		}); //创建折线
		map.addOverlay(polyline); //增加折线
		polyline.enableEditing();//启用编辑
	}
</script>

