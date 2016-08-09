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
	 	int size=Integer.parseInt(request.getAttribute("size").toString());
	 	if (tem.equals("success")){
	 		result="";
	 		if(size>10){
	 			size=10;
	 		}
	 		for(int i=0;i<size;i++){
	 			result+=(i+1)+".目标轨迹与测试轨迹（"+request.getAttribute("fileName.get(indexes["+i+"])").toString()+
	 					"）的相似度为："+request.getAttribute("similarity[indexes["+i+"]]").toString()+"%\r\n";
	 		}
	 	}else if(tem.equals("input")){
	 		result="所选择计算轨迹文件类型与输入文件不匹配";
	 	}else if(tem.equals("error")){
	 		result="输入文件名找不到，文件传输有误";
	 	}else{
	 		result="未输入文件";
	 	}
 	%>
 	<jsp:include page="//jsp/component/header.jsp"></jsp:include>
    <div class="container-fluid" id="all">
        <div class="row" id="all">
            <div class="col-md-3 col-xs-12 p-l-r-0" id="result">
               <div class="about-content">
                    <table>
                        <tr>
                           <td><button class="btn btn-primary">开始计算</button></td>
                           <td><select>
                           		<option value="map">地图</option>
               	                <option value="chart">图表</option>                        		
                           </select></td>
                           <td><select id="trajs">
	                            <option value="volvo">Volvo</option>
							    <option value="saab">Saab</option>
							    <option value="opel">Opel</option>
							    <option value="audi">Audi</option>                         
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
                <div id="allmap"><h2>地图</h2></div>
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
	var allTrajs="${strTrajs}";
	var strTrajs=init(allTrajs);
	
	var strSubtrajs="${strSubtrajs}";
	var strPoints="${strPoints}";
	$(document).ready(function () {
		drawTraj(strTrajs,allTrajs);			
		var sel1=$("select").eq(1);
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

