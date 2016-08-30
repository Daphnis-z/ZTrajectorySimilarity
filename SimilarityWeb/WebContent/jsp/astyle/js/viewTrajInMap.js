function sendTraj () {
	var trajFile=$("#trajFile").val();
	if(trajFile==""){
		alert("未选择文件！！");
		return;
	}

	var loc=window.location.protocol+"//"+window.location.host;
	var formData = new FormData(); 
	formData.append('trajFile', $('#trajFile')[0].files[0]);	
	if($("#dataHandle").is(':checked')==true){
		formData.append('dataHandle', "true");	
	}else{
		formData.append('dataHandle', "false");	
	}
	
	var info;
	$.ajax({
	    url: loc+"/SimilarityWeb/showATrajInMap.action?'",
	    type: "post",
	    data: formData,
	    cache:false,
//	    async:false,
	    processData: false,
	    contentType: false
	}).done(function(res) { 
		showMap(res);
	}).fail(function(res) {
		alert("文件上传失败！！");
	});
}