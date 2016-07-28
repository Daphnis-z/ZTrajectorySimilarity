<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="./jsp/css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="./jsp/css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="./jsp/css/multiTraj.css">

	<title>多轨迹模式</title>
</head>
<body background="./jsp/css/images/bg.jpg">
 	<%! double dtwDis_W=0.45,editDis_W=0.1,tsum_W=0,shapeSum_W=0.45;
	%>
	<script type="text/javascript">
	function changeSimilarValue()
	{	
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
	</script>

   <!-- 导航条 -->
   <nav class="navbar navbar-default navbar-fixed-top">
     <div class="container">
       <div class="navbar-header">
         <a class="navbar-brand" href="./">轨迹相似度计算系统</a>
       </div>
       <div id="navbar" class="collapse navbar-collapse">
         <ul class="nav navbar-nav">
	        <li><a href="doublePattern.action">双轨迹模式</a></li>
	        <li class="active"><a href="">多轨迹模式</a></li>
	        <li><a href="viewTraj.action">可视化轨迹</a></li>
	        <li><a href="setting.action">设置</a></li>
         </ul>
       </div>
     </div>
   </nav>
   <div class="container"></div>
   <footer class="footer">
     <div class="container">
       <p class="text-muted" align="center">Similarity_HHU SC2016 </p>
     </div>
   </footer>

   <form action="morePattern.action" method="post" enctype="multipart/form-data" onsubmit="return checkFile()">
     <table border="0">
       <tr>
         <td><b>各要素权重：</td>
         <td>轨迹距离</td>
         <td><input type="text" name="dtwDis_W" id="dtwDis_W_Id" value=<%= dtwDis_W%> size=10 /></td>
         <td>轨迹顺序</td>
         <td align="center"><input type="text" name="editDis_W" id="editDis_W_Id" value=<%= editDis_W%> size=10 /></td>
         <td>时间差</td>
         <td><input type="text" name="tsum_W" value=<%= tsum_W%> id="tsum_W_Id" size=10 /></td>
         <td>形状差异</td>
         <td align="center"><input type="text" name="shapeSum_W" id="shapeSum_W_Id" value=<%= shapeSum_W%> size=10 /></td>
       </tr>
       <tr>
         <td><b>上传文件：</td>
         <td>目标轨迹文件：</td>
         <td colspan="6"><input type="file" name="objectfile" id="ofile" size="40" /></td>
         <td><input type="checkbox" name="timeStamp"id="timeStampId"
						value="1" size="10" onchange="changeSimilarValue()" />带时间戳</td>
       </tr>
       <tr>
       <td></td>
         <td>测试轨迹文件路径：</td>
         <td colspan="6"><input type="text" name="testfilePath" id="tpath" size="51" /></td>
       </tr>
       <tr>
         <td colspan="9" align="center"><input type="submit" value="开始计算"></td>
  
       <!--  <td ><a href="moreTrajResult.jsp">开始计算</a></td>   -->
       </tr>
     </table>
   </form>
</body>
</html>
<script type="text/javascript">	
	function checkFile(){
		if(document.getElementById("ofile").value==""||
				document.getElementById("tpath").value==""){
			alert("请选择目标轨迹文件和填写测试轨迹文件路径！！")
			return false
		}
		return true
	}
</script>

