<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/common.css">  
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/viewTrajByMap.css">  
	<title>在地图中可视化轨迹</title>
</head>
<body>
<jsp:include page="//jsp/component/header.jsp"></jsp:include>
  <table>
    <tr height="8%">
		<td>上传轨迹文件：</td>
		<td><input type="file" name="trajFile" id="trajFile" size="50" /></td>
		<td><button onclick="sendTraj()">显示轨迹</button></td>
    </tr>
  </table>
  <table id="map">
    <tr>
      <td><div id="allmap"><h2>地图</h2></div></td>
    </tr>
  </table>
  
  <jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
      
  <!-- 引入JS文件 -->
  <script type="text/javascript" src="./jsp/astyle/js/jquery-2.1.1.js"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
  <script type="text/javascript" src="./jsp/astyle/js/zBaiduMap.js"></script>
  <script type="text/javascript" src="./jsp/astyle/js/viewTrajInMap.js"></script>
</body>
</html>

