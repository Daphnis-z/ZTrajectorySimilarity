<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Similarity</title>
<link rel="stylesheet" href="./jsp/css/common.css">
<link rel="stylesheet" href="./jsp/css/start.css">
</head>
<body>
	<h1 id="sysHeader">轨迹相似度计算系统</h1>
	<table border="1">
		<tr>
			<th colspan="2" id="sth"><h2>请选择不同的轨迹相似度计算模式</h2></th>
		</tr>
		<tr>
			<td>
				<form action="doublePattern.action" method="post" enctype="multipart/form-data">
					<input type="submit" value="双轨迹计算模式"/>
				</form>
			</td>
			<td>
				<form action="morePattern.action" method="post" enctype="multipart/form-data">
				<input type="submit" value="多轨迹计算模式"/>
				</form>
			</td>
		</tr>
	</table>

</body>
</html>