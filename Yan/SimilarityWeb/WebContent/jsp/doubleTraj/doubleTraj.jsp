<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="../css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="../css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/doubleTraj.css">

	<title>双轨迹模式</title>
</head>
<body background="../css/images/bg.jpg">
	<%! double dtwDis_W=0.55,editDis_W=0.2,tsum_W=0,shapeSum_W=0.25;
		double dtwDis_B=100,editDis_B=5000,shapeSum_B=100;
		long tsum_B=0;
	%>
	<script type="text/javascript">
	function changeSimilarValue()
	{	
		if(document.getElementById("timeStampId").checked){
			dtwDis_W=0.55;dtwDis_B=100;
			editDis_W=0.15;editDis_B=5000;
			tsum_W=0.15;tsum_B=100000000;
			shapeSum_W=0.15;shapeSum_B=100;
			alert("当前选中为带时间的轨迹相似度计算，相似模型因素值将会改变");
			setValue()
		}else{
			dtwDis_W=0.55;dtwDis_B=100;
			editDis_W=0.2;editDis_B=5000;
			tsum_W=0;tsum_B=0;
			shapeSum_W=0.25;shapeSum_B=100;
		    alert("当前选中不带时间的轨迹相似度计算，相似模型因素值将会改变");
		    document.getElementById("dtwDis_W_Id").value=dtwDis_W;
		    setValue();
		}
		function setValue(){
			document.getElementById("dtwDis_W_Id").value=dtwDis_W;
			document.getElementById("editDis_W_Id").value=editDis_W;
			document.getElementById("tsum_W_Id").value=tsum_W;
			document.getElementById("shapeSum_W_Id").value=shapeSum_W;
			//document.getElementById("dtwDis_B_Id").value=dtwDis_B;
			//document.getElementById("editDis_B_Id").value=editDis_B;
			//document.getElementById("tsum_B_Id").value=tsum_B;
			//document.getElementById("shapeSum_B_Id").value=shapeSum_B;
		}
	}
	</script>

	<!-- 导航条 -->
	<nav class="navbar navbar-default navbar-fixed-top">
	  <div class="container">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="../../">轨迹相似度计算系统</a>
	    </div>
	    <div id="navbar" class="collapse navbar-collapse">
	      <ul class="nav navbar-nav">
	        <li class="active"><a href="doubleTraj.jsp">双轨迹模式</a></li>
	        <li><a href="../moreTraj/moreTraj.jsp">多轨迹模式</a></li>
	        <li><a href="../viewTraj/viewTraj.jsp">可视化轨迹</a></li>
	      </ul>
	    </div>
	  </div>
	</nav>
	<div class="container"></div>
	<footer class="footer">
	  <div class="container">
	    <p class="text-muted">Similarity_HHU SC2016 </p>
	  </div>
	</footer>

	<form action="doubleTrajCul.action" method="post" enctype="multipart/form-data">
		<table border="0">
			<tr>
				<td><b>各要素权重：</td>
				<td>DTW距离</td>
				<td><input type="text" name="dtwDis_W" value=0.55 id="dtwDis_W_Id" size=10 /></td>
				<td>Edit距离</td>
				<td align="center"><input type="text" name="editDis_W" id="editDis_W_Id" value=0.15 size=10 /></td>
				<td>时间差之和</td>
				<td><input type="text" name="tsum_W" value=0.15 id="tsum_W_Id" size=10 /></td>
				<td>形状差异值</td>
				<td align="center"><input type="text" name="shapeSum_W" id="shapeSum_W_Id" value=0.15 size=10 /></td>
			</tr>            
			<tr>
				<td><b>上传文件：</td>
				<td>目标轨迹文件：</td>
				<td colspan="7"><input type="file" name="objectfile" size="40" /></td>
			</tr>
			<tr>
				<td></td>
				<td>测试轨迹文件：</td>
				<td colspan="6"><input type="file" name="testfile" size="40"/></td>
				<td><input type="checkbox" name="timeStamp" id="timeStampId" checked="checked"
						value="1" size="10" onchange="changeSimilarValue()"/>带时间戳</td>
			</tr>
			<tr>
 				<td colspan="9" align="center"><input type="submit" value="开始计算"></td>
 
				<!--  <td ><a href="doubleTrajResult.jsp">开始计算</a></td>	-->
			</tr>
		</table>
	</form>
</body>
</html>