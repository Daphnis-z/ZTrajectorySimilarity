/**
 * 
 */
$(document).ready(function () {
	drawTraj();
	$("select").eq(0).change(function(){
		drawTraj()
	});
});

function drawTraj(){
	if($("select").eq(0).val()=="map"){
		showMapWithSubtraj(strTrajs,strSubtrajs,strPoints);
	}else{
		showInChartWithSubtraj (strTrajs,strSubtrajs,strPoints);
	}

}
