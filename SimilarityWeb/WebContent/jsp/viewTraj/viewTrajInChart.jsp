<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/common.css">  
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/viewTrajByMap.css">  
    <title>在图表中可视化轨迹</title>
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
      <td><div id="allmap"><h2>图表</h2></div></td>
    </tr>
  </table>
  
  <jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
      
    <script type="text/javascript" src="./jsp/astyle/js/jquery-2.1.1.js"></script>
    <!-- 绘制图表相关 -->
    <script src="./jsp/amcharts/amcharts.js" type="text/javascript"></script>
    <script src="./jsp/amcharts/serial.js" type="text/javascript"></script>    
    <script type="text/javascript" src="./jsp/astyle/js/viewTrajInChart.js"></script>
</body>
</html>
<script type="text/javascript">
    function sendTraj () {
        var trajFile=$("#trajFile").val();
        if(trajFile==""){
            alert("未选择文件！！");
            return;
        }

    	var loc=window.location.protocol+"//"+window.location.host;
        var formData = new FormData(); 
        formData.append('trajFile', $('#trajFile')[0].files[0]); 
        var info;
        $.ajax({
	        url: loc+"/SimilarityWeb/showATrajInMap.action?'",    
			type: "post",
            data: formData,
            cache:false,
            processData: false,
            contentType: false
        }).done(function(res) { 
            showInChart(res);
        }).fail(function(res) {
            alert("文件上传失败！！");
        });
    }
</script>

