<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>多轨迹模式</title>
</head>
<body >
<jsp:include page="//jsp/component/header.jsp"></jsp:include>
 	<%! double dtwDis_W=0.45,editDis_W=0.1,tsum_W=0,shapeSum_W=0.45;%>
<!--======== our expets =========-->
<section id="team" class="our-experts">
    <div class="container">
        <form action="morePattern.action" method="post" enctype="multipart/form-data" onsubmit="return checkFile()">
        <table border="0">
        <div class="row">
           <h4 class="section-title">各要素权重：</h4>
           <tr>
	           	<td>
		            <div class="col-md-4 col-md-6 col-xs-12">
		         	轨迹距离：<input type="text" name="dtwDis_W" id="dtwDis_W_Id" value=<%= dtwDis_W%> size=10 />
		         	轨迹顺序：<input type="text" name="editDis_W" id="editDis_W_Id" value=<%= editDis_W%> size=10 /></br>
		     	            时间差异：<input type="text" name="tsum_W" value=<%= tsum_W%> id="tsum_W_Id" size=10 /></br>
		      	            形状差异：<input type="text" name="shapeSum_W" id="shapeSum_W_Id" value=<%= shapeSum_W%> size=10 />
		            </div>
	            </td>
	            <div class="col-md-8">
	            <div class="three-slide">
	            <td>
	               <div class="single-slide">
	               <img class="img-circle" src="/SimilarityWeb/jsp/astyle/images/objtraj-image.jpg" alt="">
	                   <div class="text-center">
	                       <h3 class="expert-name">目标轨迹</h3>
	                       <p class="expert-tag">选择需要进行相似比较的目标轨迹</p>
	                  	<input type="file" name="objectfile" id="ofile" size="40"/>	                  		                
	                   </div>
	               </div>
	            </td>
	            <td>
	               <div class="single-slide">
	               <img class="img-circle" src="/SimilarityWeb/jsp/astyle/images/testtraj-image.jpg" alt="">
	                   <div class="text-center">
	                       <h3 class="expert-name">测试轨迹群</h3>
	                       <p class="expert-tag">选择与目标轨迹进行比较的测试轨迹群</p>
	                       <table>
	                       	<tr>
	                       		<td>	                       
		                       		<select>
		                           		<option value="file">上传数据集</option>
		               	                <option value="text">填写数据集</option>                        		
	                           		</select>
	                       		</td>
	                       		<td><input multiple="multiple" type="file" name="testfile" id="tfile" size="40"/></td>
	                       		<td>
	                       			<input type="text" name="trajsName" id="tname" size="30" 
	                      			placeholder="输入服务器端数据集名称）" />
	                      		</td>
	                       	</tr>
	                       </table>
	                   </div>
	               </div>
	            </td>
	            <td>
	               <div class="single-slide">
	               <img class="img-circle" src="/SimilarityWeb/jsp/astyle/images/timestamp-image.jpg" alt="">
	                   <div class="text-center">
	                       <h3 class="expert-name">时间戳</h3>
	                       <p class="expert-tag">请选择目标/测试轨迹是否带时间戳</p>
	                  		   带时间戳<input type="checkbox" name="timeStamp" id="timeStampId" value="1" size="10" onchange="changeSimilarValue()"/>
	                   </div>
	               </div>
	               </td>
                </div>
             </tr>
            </div>
           <tr><td colspan="4"><input class="btn btn-primary" type="submit" value="上传文件"></td></tr>
        </div>
        </table>
      </form>
    </div>
</section>
   <jsp:include page="//jsp/component/footer.jsp"></jsp:include>
   <script type="text/javascript" src="./jsp/astyle/js/common.js"></script>
</body>
</html>
<script type="text/javascript">	
	$(document).ready(function(){
		$("#tname").hide();
		var sel0=$("select").eq(0);
		sel0.change(function(){
			$("#tname").toggle("slow").val("");
			$("#tfile").toggle("slow").val("");
		});

	});

	function changeSimilarValue(){	
		if(document.getElementById("timeStampId").checked){
			dtwDis_W=0.3;editDis_W=0.1;tsum_W=0.3;shapeSum_W=0.3;
			alert("当前选中为带时间的轨迹相似度计算，相似模型因素值将会改变");
			setValue()
		}else{
			dtwDis_W=0.45;editDis_W=0.1;tsum_W=0;shapeSum_W=0.45;
		    alert("当前选中不带时间的轨迹相似度计算，相似模型因素值将会改变");
		    document.getElementById("dtwDis_W_Id").value=dtwDis_W;
		    setValue();
		}
		function setValue(){
			document.getElementById("dtwDis_W_Id").value=dtwDis_W;
			document.getElementById("editDis_W_Id").value=editDis_W;
			document.getElementById("tsum_W_Id").value=tsum_W;
			document.getElementById("shapeSum_W_Id").value=shapeSum_W;
		}
	}

	function checkFile(){
		if($("#ofile").val()==""||($("#tfile").val()==""&&$("#tname").val()=="")){
			alert("数据不完整，请检查！！");
			return false;
		}
		var fileCnt=$("#tfile").get(0).files.length;
		if(fileCnt>500&&fileCnt<1000){
			alert("文件数目过多，建议联系服务器管理员，采用其他方式上传数据。\n点击确定开始上传文件。");
		}else if (fileCnt>1000) {
			alert("文件数目太多，无法上传文件！！，请联系服务器管理员，采用其他方式上传数据。");
			return false;
		}
		return weightCheck();
	}
</script>

