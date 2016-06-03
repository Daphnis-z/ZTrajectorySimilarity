/**
 * 用于调用百度地图进行绘图
 * @author Daphnis
 * 20160601 
 */
	
// 生成随机颜色
function getRandomColor() {
	strCol = "0123456789"
	color = "#"
	for (var i = 0; i < 6; i++) {
		n = Math.random() * 10000
		n = parseInt(n % 10)
		color = color.concat(strCol[n])
	}
	return color
}

//创建并初始化百度地图
function createBaiduMap(logitude,latitude){
	// 百度地图API功能
	var map = new BMap.Map("allmap")
	map.centerAndZoom(new BMap.Point(logitude,latitude), 10)
	map.enableScrollWheelZoom()

	// 添加地图控件
	map.addControl(new BMap.MapTypeControl());
	map.addControl(new BMap.ScaleControl({anchor : BMAP_ANCHOR_TOP_LEFT}))// 左上角比例尺
	map.addControl(new BMap.NavigationControl())// 左上角默认缩放控件
	return map
}

function showMap(strTrajs) {
	// 接收来自后台的轨迹数据
	strTrajs = strTrajs.split('@')
	center = strTrajs[0].split(',')// 为了设置地图中心点
	if(center.length<2){
		center[0]=118.82761
		center[1]=31.97705
	}
	map=createBaiduMap(center[0],center[1])

	//绘制折线轨迹
	zIcon = new BMap.Icon("./jsp/css/images/ptGreen.png", new BMap.Size(20,25))
	for (var ix = 0; ix <2&&ix<strTrajs.length; ix++) {
		strTraj = strTrajs[ix].split(',')
		if(strTraj.length<2){
			break
		}
		var traj = new Array(strTraj.length / 2)
		for (var i = 0, j = 0; i < strTraj.length; i += 2, j++) {
			traj[j] = new BMap.Point(strTraj[i], strTraj[i + 1])
			marker=ix==0? new BMap.Marker(traj[j]):new BMap.Marker(traj[j],{icon:zIcon})
			map.addOverlay(marker)
		}
				
		var polyline = new BMap.Polyline(
			traj, {strokeColor : ix==0? "#B40404":"#07FC17",strokeWeight : 4,strokeOpacity : 0}
		) //创建折线
		map.addOverlay(polyline)//增加折线
	}
}

function showMapWithSubtraj(trajs,subtrajs,points){
	showMap(trajs)

	//绘制子轨迹
	subtrajs=subtrajs.split('@')
	for(var ix=0;ix<subtrajs.length;++ix){
		subtraj=subtrajs[ix].split(',')
		var straj=new Array(subtraj.length/2)
		for (var i = 0, j = 0; i < subtraj.length; i += 2, j++) {
			straj[j] = new BMap.Point(subtraj[i], subtraj[i + 1])
//			map.addOverlay(new BMap.Marker(straj[j],{title:"我是相似度最高的子轨迹"}))
		}
		polyline = new BMap.Polyline(
				straj, {strokeColor : "blue",strokeWeight : 5,strokeOpacity : 0}
			) //创建折线
		map.addOverlay(polyline)//增加折线
	}
	
	//绘制最相似的轨迹点
	zIcon = new BMap.Icon("./jsp/css/images/ptFlat.png", new BMap.Size(40,60))
	points=points.split(',')
	for(var i=0;i<points.length;i+=2){
		pt=new BMap.Point(points[i],points[i+1])
		map.addOverlay(new BMap.Marker(pt,{title:"我是相似度最高的轨迹点",icon:zIcon}))
	}
}

