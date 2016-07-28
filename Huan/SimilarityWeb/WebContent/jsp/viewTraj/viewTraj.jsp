<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="./jsp/css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="./jsp/css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="./jsp/css/common.css">
    <link rel="stylesheet" type="text/css" href="./jsp/css/viewTraj.css">

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
	<script type="text/javascript" src="./jsp/js/zBaiduMap.js"></script>

	<title>可视化轨迹</title>
</head>
<body>
  <!-- 导航条 -->
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a class="navbar-brand" href="./">轨迹相似度计算系统</a>
      </div>
      <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
	        <li><a href="doublePattern.action">双轨迹模式</a></li>
	        <li><a href="morePattern.action">多轨迹模式</a></li>
	        <li class="active"><a href="">可视化轨迹</a></li>
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

  <table>
    <tr height="8%">
    	<form action="viewTraj.action" method="post" enctype="multipart/form-data">
			<td><b>上传轨迹文件：</td>
			<td><input type="file" name="trajFile" id="trajFile" size="50" /></td>
			<td><input type="submit" value="显示轨迹" onclick="checkValue()" /></td>
		</form>
    </tr>
  </table>
  <table id="map">
    <tr>
      <td colspan="3"><div id="allmap"><h2>地图<h2></div></td>
    </tr>
  </table>
</body>
</html>
<script type="text/javascript">
	function checkValue(){
		if(document.getElementById("trajFile").value==""){
			alert("未选择文件！！")
		}
	}
	showMapWithSubtraj("${strTrajs}","${subtraj}")
</script>

