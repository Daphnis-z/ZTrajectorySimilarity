/**
 * 用于调用百度地图进行绘图
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

function showMap(strTrajs) {
	// 接收来自后台的轨迹数据
//	strTrajs = "${strTrajs}"
	strTrajs = strTrajs.split('@')
	center = strTrajs[0].split(',')// 为了设置地图中心点
	if(center.length<2){
		center[0]=118.82761
		center[1]=31.97705
	}

	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(center[0],center[1]), 10);
	map.enableScrollWheelZoom();

	// 添加地图控件
	map.addControl(new BMap.MapTypeControl());
	map.addControl(new BMap.ScaleControl({anchor : BMAP_ANCHOR_TOP_LEFT}));// 左上角比例尺
	map.addControl(new BMap.NavigationControl());// 左上角默认缩放控件

	//绘制折线轨迹
	for (var ix = 0; ix < strTrajs.length; ix++) {
		strTraj = strTrajs[ix].split(',')
		var traj = new Array(strTraj.length / 2)
		for (var i = 0, j = 0; i < strTraj.length; i += 2, j++) {
			traj[j] = new BMap.Point(strTraj[i], strTraj[i + 1])
			map.addOverlay(new BMap.Marker(traj[j]));
		}
				
		color=getRandomColor()		
		var polyline = new BMap.Polyline(traj, {
			strokeColor : color,
			strokeWeight : 4,
			strokeOpacity : 0
		}); //创建折线
		map.addOverlay(polyline); //增加折线
	}
}

