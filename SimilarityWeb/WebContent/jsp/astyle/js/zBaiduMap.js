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
	map.centerAndZoom(new BMap.Point(logitude,latitude), 12)
	map.enableScrollWheelZoom()

	// 添加地图控件
	map.addControl(new BMap.MapTypeControl());
	map.addControl(new BMap.ScaleControl({anchor : BMAP_ANCHOR_TOP_LEFT}))// 左上角比例尺
	map.addControl(new BMap.NavigationControl())// 左上角默认缩放控件
	return map
}

function showMap(strTrajs,hasTime) {
	// 处理来自后台的轨迹数据
	strTrajs = strTrajs.split('@');
	center = strTrajs[0].split(',');// 为了设置地图中心点
	if(center.length<2){
		center[0]=118.82761;
		center[1]=31.97705;
	}
	map=createBaiduMap(center[0],center[1]);

	//绘制折线轨迹
	zIcon = new BMap.Icon("./jsp/astyle/images/ptGreen.png", new BMap.Size(20,25))
	for (var ix = 0; ix <2&&ix<strTrajs.length; ix++) {
		strTraj = strTrajs[ix].split(',')
		if(strTraj.length<2){
			break;
		}
		var traj = new Array();
		for (var i = 0, j = 0; i < strTraj.length;j++) {
			traj[j] = new BMap.Point(strTraj[i], strTraj[i + 1]);
			var marker=null;
			if(hasTime=="true"){
				var time=strTraj[i]+","+strTraj[i+1]+","+strTraj[i+2];
				if(ix==0){
					if(j==0){
						marker=new BMap.Marker(traj[j],{title:"我是目标轨迹的开始点"});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else if(i+4>=strTraj.length){
						marker=new BMap.Marker(traj[j],{title:"我是目标轨迹的结束点"});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else{
						marker=new BMap.Marker(traj[j],{title:time});
					}
				}else{
					if(j==0){
						marker=new BMap.Marker(traj[j],{title:"我是测试轨迹的开始点",icon:zIcon});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else if(i+4>=strTraj.length){
						marker=new BMap.Marker(traj[j],{title:"我是测试轨迹的结束点",icon:zIcon});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else{
						marker=new BMap.Marker(traj[j],{title:time,icon:zIcon});
					}
				}
				i+=3;
			}else{
				if(ix==0){
					if(j==0){
						marker=new BMap.Marker(traj[j],{title:"我是目标轨迹的开始点"});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else if(i+3>=strTraj.length){
						marker=new BMap.Marker(traj[j],{title:"我是目标轨迹的结束点"});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else{
						marker=new BMap.Marker(traj[j]);
					}
				}else{
					if(j==0){
						marker=new BMap.Marker(traj[j],{title:"我是测试轨迹的开始点",icon:zIcon});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else if(i+3>=strTraj.length){
						marker=new BMap.Marker(traj[j],{title:"我是测试轨迹的结束点",icon:zIcon});
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
					}else{
						marker=new BMap.Marker(traj[j],{icon:zIcon});
					}
				}
				i+=2;
			}
			map.addOverlay(marker)
		}
				
		var polyline = new BMap.Polyline(
			traj, {strokeColor : ix==0? "#B40404":"#07FC17",strokeWeight : 4,strokeOpacity : 0}
		) //创建折线
		map.addOverlay(polyline)//增加折线
	}
}

//可视化带子轨迹段的轨迹
function showMapWithSubtraj(trajs,subtrajs,points,hasTime){
	showMap(trajs,hasTime);

	//绘制子轨迹
	subtrajs=subtrajs.split('@')
	for(var ix=0;ix<subtrajs.length;++ix){
		subtraj=subtrajs[ix].split(',')
		var straj=new Array();
		for (var i = 0, j = 0; i < subtraj.length; j++) {
			straj[j] = new BMap.Point(subtraj[i], subtraj[i + 1]);
			if(hasTime=="true"){
				i+=3;
				continue;
			}
			i+=2;
			// map.addOverlay(new BMap.Marker(straj[j],{title:"我是相似度最高的子轨迹"}))
		}
		polyline = new BMap.Polyline(
				straj, {strokeColor : "blue",strokeWeight : 5,strokeOpacity : 0}
			) //创建折线
		map.addOverlay(polyline)//增加折线
	}
	
	//绘制最相似的轨迹点
	zIcon = new BMap.Icon("./jsp/astyle/images/ptFlat.png", new BMap.Size(36,36))
	points=points.split(',')
	for(var i=0;i<points.length;){
		pt=new BMap.Point(points[i],points[i+1]);
		var marker=null;
		if(hasTime=="true"){
			var time=points[i]+","+points[i+1]+","+points[i+2];
			marker=new BMap.Marker(pt,{title:time,icon:zIcon});
			i+=3;
		}else{
			marker=new BMap.Marker(pt,{title:"我是相似度最高的轨迹点",icon:zIcon});
			i+=2;
		}
		map.addOverlay(marker);
		marker.setAnimation(BMAP_ANIMATION_BOUNCE);
	}
}

//可视化DTW匹配结果
function showDTWResult(strPoints) {
	// 接收来自后台的轨迹数据
	strPoints = strPoints.split(',');
	var map = createBaiduMap(strPoints[0], strPoints[1]);

	var traj = new Array(strPoints.length / 2);
	for (var i = 0,j=0; i < strPoints.length-2; i+=2,++j) {
		traj[j] = new BMap.Point(strPoints[i], strPoints[i + 1]);
		map.addOverlay(new BMap.Marker(traj[j]));
	}

	//绘制两条轨迹
	var traj1=new Array(),traj2=new Array();
	for(var i= 0,j=0;i<traj.length-2;i+=2,++j){
		traj1[j]=traj[i];
		traj2[j]=traj[i+1];
	}
	var polyline = new BMap.Polyline(
		traj1, {strokeColor :"blue",strokeWeight : 4,strokeOpacity : 0}
	) //创建折线
	map.addOverlay(polyline)//增加折线
	polyline = new BMap.Polyline(
		traj2, {strokeColor :"green",strokeWeight : 4,strokeOpacity : 0}
	) //创建折线
	map.addOverlay(polyline)//增加折线

	//绘制匹配点间的线段
	for(var i=0;i<traj.length-2;i+=2){
		var mtraj=[traj[i],traj[i+1]];
		var pl = new BMap.Polyline(
			mtraj, {strokeColor : "red",strokeWeight : 2,strokeOpacity : 80}
		) //创建折线
		map.addOverlay(pl);
	}
}
