<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./jsp/css/doubleTraj.css">
<link rel="stylesheet" href="./jsp/css/common.css">
</head>
<body>
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
			document.getElementById("dtwDis_B_Id").value=dtwDis_B;
			document.getElementById("editDis_B_Id").value=editDis_B;
			document.getElementById("tsum_B_Id").value=tsum_B;
			document.getElementById("shapeSum_B_Id").value=shapeSum_B;
		}
	}
	</script>

	<h1 id="sysHeader">轨迹相似度计算系统</h1>
	<form action="doubleTrajCul.action" method="post" enctype="multipart/form-data">
		<table border="5">
			<tr>
				<th colspan="2" align="right">请选择轨迹文件类型：</th>
				<td colspan="6"><input type="checkbox" id="timeStampId" name="timeStamp" value="1" size="10" 
				onchange="changeSimilarValue()"/>带时间戳</td>
			</tr>
			<tr>
				<td id="sth" colspan="8" align="center"><h4>设置轨迹相似度各因素所占权重（值在0-0.5之间）和最坏值</h4></td>
			</tr>
			<tr>
				<th colspan="2">DTW距离</th>
				<th colspan="2">Edit距离</th>
				<th colspan="2">时间差之和</th>
				<th colspan="2">形状差异值</th>
			</tr>
			<tr>
				<th>权重</th><th>最坏值</th>
				<th>权重</th><th>最坏值</th>
				<th>权重</th><th>最坏值</th>
				<th>权重</th><th>最坏值</th>
			</tr>
			<tr>
				<td><input type="text" name="dtwDis_W" id="dtwDis_W_Id" value=<%= dtwDis_W %> size=10 /></td>
				<td><input type="text" name="dtwDis_B" id="dtwDis_B_Id" value=<%= dtwDis_B %> size=10 /></td>
				<td><input type="text" name="editDis_W" id="editDis_W_Id" value=<%= editDis_W %> size=10 /></td>
				<td><input type="text" name="editDis_B" id="editDis_B_Id" value=<%= editDis_B %> size=10 /></td>
				<td><input type="text" name="tsum_W" id="tsum_W_Id" value=<%= tsum_W %> size=10 /></td>
				<td><input type="text" name="tsum_B" id="tsum_B_Id" value=<%= tsum_B %> size=10 /></td>
				<td><input type="text" name="shapeSum_W" id="shapeSum_W_Id" value=<%= shapeSum_W %> size=10 /></td>
				<td><input type="text" name="shapeSum_B" id="shapeSum_B_Id" value=<%= shapeSum_B %> size=10 /></td>
			</tr>
			<tr>
				<td id="sth" colspan="8" align="center"><h4>其他</h4></td>
			</tr>
			<tr>
				<th colspan="2" align="right">提交目标轨迹文件：</th>
				<td colspan="2"><input type="file" name="objectfile" size="30" /></td>
				<th colspan="2" align="right">测试轨迹文件：</th>
				<td colspan="2"><input type="file" name="testfile" size=30 /><br /></td>
			</tr>
			<tr>
				<td colspan="8" align="center"><input type="submit" value="提交"></td>
			</tr>
		</table>
	</form>
</body>
</html>