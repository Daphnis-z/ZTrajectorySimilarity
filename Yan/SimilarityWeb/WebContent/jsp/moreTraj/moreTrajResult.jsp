<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/common.css">  
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/moreTrajResult.css">  
	<title>多轨迹结果显示</title>
</head>
<body>
 	<%! String result=""; %>
	<%  
	 	String tem=request.getAttribute("actionResult").toString();
		if(!tem.equals("doNothing")){			
		 	if (tem.equals("error")){
		 		result="服务器端读取数据错误！！";
		 	}else if(tem.equals("input")){
		 		result="文件类型错误！！";
		 	}else if(tem.equals("success")){
		 		result=request.getAttribute("result").toString();
		 	}else{
		 		result=tem;
		 	}
		}else{
	 		result="文件上传成功！！";
		}
 	%>
 	<jsp:include page="//jsp/component/header.jsp"></jsp:include>
    <div class="container-fluid" id="all">
        <div class="row" id="all">
            <div class="col-md-3 col-xs-12 p-l-r-0" id="result">
               <div class="about-content">
                    <table>
                        <tr>
                           <td><a id="cal" onclick="calMultiTrajs()" href="calMultiTrajs.action"  class="white">
                           	<button class="btn btn-primary">开始计算</button>
                           </a></td>
                           <td><select>
               	                <option value="chart">图表</option> 
               	                <option value="map">地图</option>
                           </select></td>
                           <td><select id="trajs">
	                            <option value="volvo">Volvo</option>
            							    <option value="saab">Saab</option>
                           </select></td>                        
                        </tr>
                        <tr><td colspan="3"><h4 class="about-font">计算结果</h4></td></tr>
                        <tr>
                            <td colspan="3" class="about-font">
                            	<textarea readonly="readonly" rows="19" class="normalTA">
                            		<%= result %>
                            	</textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </div> 
                    
            <div class="col-md-6 col-xs-12" id="map">
                <div id="allmap"><h2>可视化轨迹</h2></div>
            </div>
        </div>
    </div>
	<jsp:include page="//jsp/component/footer.jsp"></jsp:include> 

	<!-- js文件 -->
    <script type="text/javascript" src="./jsp/astyle/js/zBaiduMap.js"></script>
    <script type="text/javascript" src="./jsp/astyle/js/moreTrajResult.js"></script>
    
    <!-- 绘制图表相关 -->
    <script src="./jsp/amcharts/amcharts.js" type="text/javascript"></script>
	<script src="./jsp/amcharts/serial.js" type="text/javascript"></script>    
    <script type="text/javascript" src="./jsp/astyle/js/viewTrajInChart.js"></script>
</body>
</html>
<script type="text/javascript">
	var trajsName="${trajsName}";
	var allTrajs="${strTrajs}";
	var strTrajs=init(allTrajs);	
	var strSubtrajs="${strSubtrajs}";
	var strPoints="${strPoints}";

	$(document).ready(function () {
		drawTraj(strTrajs,allTrajs);			
		var sel1=$("select").eq(1);
		sel1.toggle();
		$("select").eq(0).change(function(){
			sel1.toggle("slow");
			drawTraj(strTrajs,allTrajs);			
		});
		sel1.change(function(){
			strTrajs=viewOtherTraj(allTrajs,sel1.val());
			drawTraj(strTrajs,allTrajs);
		});

	});

</script>

