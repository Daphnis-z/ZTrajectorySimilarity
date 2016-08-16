<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
    <script type="text/javascript" src="./jsp/astyle/js/zBaiduMap.js"></script>
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/common.css">  
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/doubleTrajResult.css">    
    <title>双轨迹结果显示</title> 
</head>
<body>

 <%! String result=""; %>
    <%  
        String tem=request.getAttribute("actionResult").toString();
        if (tem.equals("success")){
            result="两条轨迹的相似度为:"+request.getAttribute("similarity").toString()+"%";
        }else if(tem.equals("input")){
            result="所选择计算轨迹文件类型与输入文件不匹配";
        }else if(tem.equals("error")){
            result="输入文件名找不到，文件传输有误";
        }else{
            result="未输入文件！！";
        }
    %>
    <jsp:include page="//jsp/component/header.jsp"></jsp:include>
    
    <div class="container-fluid" id="all">
        <div class="row" id="all">
            <div class="col-md-3 col-xs-12 p-l-r-0">
               <div class="about-content">
                    <table>
                        <tr>
                           <td>可视化方式：</td>
                           <td><select>
               	                <option value="chart">图表</option>                        	
                                <option value="map">地图</option>	
                           </select></td>
                        </tr>
                        <tr><td colspan="2"><h4 class="about-font"><br/>计算结果：</h4></td></tr>
                        <tr>
                            <td colspan="2" class="about-font">&nbsp<%= result %></td>
                        </tr>
                    </table>
                </div>
            </div>
            
            <div class="col-md-6 col-xs-12" id="map">
                <div id="allmap"><h2>地图</h2></div>
            </div>
        </div>
    </div>
	
    <jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
    
    <!-- 绘制图表相关 -->
    <script src="./jsp/amcharts/amcharts.js" type="text/javascript"></script>
	<script src="./jsp/amcharts/serial.js" type="text/javascript"></script>    
    <script type="text/javascript" src="./jsp/astyle/js/viewTrajInChart.js"></script>
</body>
</html>
<script type="text/javascript">
	var strTrajs="${strTrajs}";	
	var strSubtrajs="${strSubtrajs}";
	var strPoints="${strPoints}";
	$(document).ready(function () {
		drawTraj(strTrajs);			
		$("select").eq(0).change(function(){
			drawTraj(strTrajs);			
		});
	});
	
	//绘制轨迹
	function drawTraj(strTrajs){
		if($("select").eq(0).val()=="map"){
			showMapWithSubtraj(strTrajs,strSubtrajs,strPoints);
		}else{
			showInChart(strTrajs);
		}
	}
</script>

