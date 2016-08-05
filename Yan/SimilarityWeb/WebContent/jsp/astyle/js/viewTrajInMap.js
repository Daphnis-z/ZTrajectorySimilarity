function sendTraj () {
	var trajFile=$("#trajFile").val();
	if(trajFile==""){
		alert("未选择文件！！");
		return;
	}

	var formData = new FormData(); 
	formData.append('trajFile', $('#trajFile')[0].files[0]); 
	var info;
	$.ajax({
	    url: 'http://localhost:8080/SimilarityWeb7/showATrajInMap.action?',
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