<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="doubleTrajCul.action" method="post" enctype="multipart/form-data">
	<p><b>请设置轨迹相似度各因素所占权重（值在0-0.5之间）</b><br/>
	dtw权重：<input type="text" name="dtwDis_W" value=0.4 size=10/>
	edit权重：<input type="text" name="editDis_W" value=0.3 size=10/>
	tSum权重：<input type="text" name="tsum_W" value=0.1 size=10/>
	shapeSum权重：<input type="text" name="shapeSum_W" value=0.2 size=10/>
	<br/><b>请设置轨迹相似度各因素最坏值</b><br/>
	dtw最坏值：<input type="text" name="dtwDis_B" value=10 size=10/>
	edit最坏值：<input type="text" name="editDis_B" value=10 size=10/>
	tSum最坏值：<input type="text" name="tsum_B" value=10 size=10/>
	shapeSum最坏值：<input type="text" name="shapeSum_B" value=10 size=10/></p>
	
	<br/><b>请选择轨迹文件类型：</b>
	<input type="checkbox" name="timeStamp" value="1" size="10"/>带时间戳<br/>
	<br/><b>请提交目标轨迹文件</b><br/>
	<input type="file" name="objectfile" size="30"/><br/>
	<br/><b>请提交测试轨迹文件</b><br/>
	<input type="file" name="testfile" size=30/><br/><br/>
	<input type="submit" value="提交">
	</form>
	<br>
</body>
</html>