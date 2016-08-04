<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KPEp2mgApObFYkwzVxYjsDnDtaIoRjxI"></script>
	<script type="text/javascript" src="./jsp/astyle/js/zBaiduMap.js"></script>
	<title>结果显示</title>
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
 			result=result+" <br/>"+"目标轨迹与测试轨迹"+request.getAttribute("fileName.get(indexes["+i+"])").toString()+
 					"的相似度为:"+request.getAttribute("similarity[indexes["+i+"]]").toString()+"%";
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
 	<section id="about-us" class="about-us">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3 col-xs-12 p-l-r-0">
               <div class="about-content">
              		<table border="0"><tr>
                    	<td><h4 class="about-font">计算结果</h4></td>
                    	<td><button class="btn btn-primary" onclick="drawTraj()">可视化轨迹</button></td>
                   		</tr>
                    </table>
					<table border="0"><tr>
                   		 <td class="about-font"><%= result %></td>
                   		</tr>
                    </table>
                </div>

            </div>
            <div class="col-md-6 col-xs-12">
                <div class="about-content">
                	<table border="0"><tr>
                   <td><button class="btn btn-primary" >轨迹属性</button></td>
                   <td><button class="btn btn-primary">轨迹属性</button></td>
                  <td> <button class="btn btn-primary">轨迹属性</button></td>
                   <td><button class="btn btn-primary">轨迹属性</button></td>
                   <td></tr>
                   <tr><td rowspan="2"><div id="allmap"></div></td></tr>
                   </table>
                   <div class="about-content">
                    <img src="/SimilarityWeb7/jsp/astyle/images/map.jpg"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
	 <%-- <table border="0">
	 	<tr>
	 		<td width="30%" valign="top">
	 			<b>计算结果：</b>
	             <br/>&nbsp;&nbsp <%= result %>
	 		</td>
	 		<td rowspan="2"><div id="allmap"><h2>地图<h2></div></td>
	 	</tr>
	 	<tr>
	 		<td align="center" height="10%"><button onclick="drawTraj()">可视化轨迹</button></td>
	 	</tr>
	 </table> --%>
	<jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
</body>
</html>
<script type="text/javascript">
	function drawTraj(){
		showMapWithSubtraj("${strTrajs}","${strSubtrajs}","${strPoints}")
	}
</script>

