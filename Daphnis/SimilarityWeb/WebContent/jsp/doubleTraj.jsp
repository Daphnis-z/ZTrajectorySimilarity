<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="./jsp/css/doubleTraj.css">
</head>
<body>
	<h1 id="topHead">轨迹相似度计算系统</h1>
	<form action="doubleTrajCul.action" method="post" enctype="multipart/form-data">
		<table border="5">
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
				<td><input type="text" name="dtwDis_W" value=0.55 size=10 /></td>
				<td><input type="text" name="dtwDis_B" value=100 size=10 /></td>
				<td><input type="text" name="editDis_W" value=0.15 size=10 /></td>
				<td><input type="text" name="editDis_B" value=5000 size=10 /></td>
				<td><input type="text" name="tsum_W" value=0.15 size=10 /></td>
				<td><input type="text" name="tsum_B" value=100000000 size=10 /></td>
				<td><input type="text" name="shapeSum_W" value=0.15 size=10 /></td>
				<td><input type="text" name="shapeSum_B" value=100 size=10 /></td>
			</tr>
			<tr>
				<td id="sth" colspan="8" align="center"><h4>其他</h4></td>
			</tr>
			<tr>
				<th colspan="2" align="right">请选择轨迹文件类型：</th>
				<td colspan="6"><input type="checkbox" name="timeStamp" value="1" size="10" />带时间戳</td>
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

