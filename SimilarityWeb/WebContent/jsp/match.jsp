<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!--<link href="./jsp/css/bootstrap-3.3.6/bootstrap.min.css" rel="stylesheet">-->
    <!--<link href="./jsp/css/bootstrap-3.3.6/sticky-footer-navbar.css" rel="stylesheet">-->
    <!--<link rel="stylesheet" type="text/css" href="./jsp/css/common.css">-->
    
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
    <script type="text/javascript" src="./jsp/js/zBaiduMap.js"></script>

    <title>DTW匹配结果查看</title>
</head>
<body>
	<div id="allmap"><h2>地图</h2></div>
</body>
</html>
<script type="text/javascript">
    showDTWResult("${strPoints}");
</script>

