
//绘制轨迹
function drawTraj(strTrajs,allTrajs){
	if($("select").eq(0).val()=="map"){
		if($('option:selected', '#trajs').index()==0){
			showMapWithSubtraj(strTrajs,strSubtrajs,strPoints);
		}else{
			showMap(strTrajs,strSubtrajs,strPoints);
		}
	}else{
		showInChart(allTrajs);
	}
}


//初始化
function init (strTrajs) {
	strTrajs=strTrajs.split("@");
	var sel=$('#trajs');
	sel.empty();
	for(var i=1;i<strTrajs.length;++i){
		var tname="traj"+i;
		sel.append("<option value='"+strTrajs[i]+"'>"+tname+"</option>"); 
	}
	return strTrajs[0]+'@'+strTrajs[1];
}

//可视化不同的轨迹
function viewOtherTraj (strTrajs,traj) {
	strTrajs=strTrajs.split("@");
	return strTrajs[0]+"@"+traj;
}
