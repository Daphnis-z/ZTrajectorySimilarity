<%@page import="com.sun.glass.ui.Window"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="../css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="../css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/common.css">
    <link rel="stylesheet" type="text/css" href="../css/setting.css">

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>

	<title>设置</title>
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
          <li><a href="../viewTraj/viewTraj.jsp">可视化轨迹</a></li>
          <li class="active"><a href="setting.jsp">设置</a></li>
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
  <script>
  	msg="${msg}"
  	if(msg!=""){
  	  	alert(msg);
  	}
  </script>
  
    <form action="setting.action" method="post" enctype="multipart/form-data">
      <table border="0">
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

