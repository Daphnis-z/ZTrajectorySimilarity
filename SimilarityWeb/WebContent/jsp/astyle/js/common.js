/**
 * 
 */
function weightCheck(){
	var sum=0.0;
	$(":text").each(function(){
		var v=this.value;
		if(v!=""){
			sum+=parseFloat(v);
		}
	});
	if(Math.abs(sum-1)>0.0000001){
		alert("权重和不为1！！");
		return false;
	}
	return true;
}

