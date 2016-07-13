<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--  <%@page import="com.sun.glass.ui.Window" %>  --%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="./jsp/css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="./jsp/css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="./jsp/css/common.css">
    <link rel="stylesheet" type="text/css" href="./jsp/css/setting.css">

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>

	<title>设置</title>
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
	        <li><a href="viewTraj.action">可视化轨迹</a></li>
	        <li class="active"><a href="">设置</a></li>
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
  <script>
  	msg="${msg}"
  	if(msg!=""){
  	  	alert(msg);
  	}
  </script>
    <form action="setting.action" method="post" enctype="multipart/form-data">
      <table border="0">
      	<tr>
      		<td colspan="3">初始化的相似度轨迹模型的最坏值为：</td>
      	</tr>      
      	<tr>
      		<td></td>
      		<td colspan="2">轨迹距离：2</td>
      		<td colspan="2">轨迹顺序：1000</td>
      		<td colspan="2">时间差：1440</td>
      		<td colspan="2">形状差异：100</td>     		
      	</tr>
        <tr>
          <td><b>各要素最坏值：</td>
          <td>轨迹距离</td>
          <td><input type="text"  name="dtwDis_B"  size=10 /></td>
          <td>轨迹顺序</td>
          <td align="center"><input type="text" name="editDis_B" size=10 /></td>
          <td>时间差</td>
          <td><input type="text"  name="tsum_B" size=10 /></td>
          <td>形状差异</td>
          <td align="center"><input type="text" name="shapeSum_B"  size=10 /></td>
        </tr>            
        <tr>
          <td colspan="9" align="center"><input type="submit" value="应用设置"></td>
        </tr>
      </table>
    </form>
</body>
</html>

