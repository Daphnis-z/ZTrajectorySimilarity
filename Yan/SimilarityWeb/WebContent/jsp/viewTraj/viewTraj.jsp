<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="../css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="../css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/common.css">
    <link rel="stylesheet" type="text/css" href="../css/viewTraj.css">

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>

	<title>可视化轨迹</title>
</head>
<body>
  <!-- 导航条 -->
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a class="navbar-brand" href="../../">轨迹相似度计算系统</a>
      </div>
      <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li><a href="../doubleTraj/doubleTraj.jsp">双轨迹模式</a></li>
          <li><a href="../moreTraj/moreTraj.jsp">多轨迹模式</a></li>
          <li class="active"><a href="viewTraj.jsp">可视化轨迹</a></li>
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

  <table>
    <tr height="8%">
      <td><b>上传文件：</td>
      <td><input type="file" name="objectfile" size="10" /></td>
      <td><a href="">显示轨迹</a></td>
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
  // 百度地图API功能
  var map = new BMap.Map("allmap");
  map.centerAndZoom(new BMap.Point(118.95761,31.80705), 10);
  map.enableScrollWheelZoom();

  //添加地图控件
  map.addControl(new BMap.MapTypeControl()); 
  map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));//左上角比例尺        
  map.addControl(new BMap.NavigationControl());//左上角默认缩放控件    
</script>
