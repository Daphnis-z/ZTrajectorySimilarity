<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>可视化轨迹</title>
</head>
<body>
<jsp:include page="//jsp/component/header.jsp"></jsp:include>
<!--======== our service =========-->
	<table align="center">
		<tr>
			<td>
				<div class="single-slide"><img src="./jsp/astyle/images/showbyline-image.jpg" alt="">
        			<div class="service-overflow text-center">
            		<h3>曲线展现</h3>
            		<p>您可在简单的二维空间展示您的轨迹</p>
            		<a class="btn btn-primary" href="viewTrajInChart.action" role="button">进入<i class="lnr lnr-chevron-right"></i></a>          		
        			</div>
     			</div>				
			</td>
			<td>
				<div class="single-slide"><img src="./jsp/astyle/images/showbymap-image.jpg" alt="">
			        <div class="service-overflow text-center">
			            <h3>地图展现</h3>
			            <p>您可在百度地图上可视化您的轨迹。</p>
			            <a class="btn btn-primary" href="viewTrajByMap.action" role="button">进入<i class="lnr lnr-chevron-right"></i></a>
			        </div>
		        </div>			
			</td>
<!-- 			<td> -->
<!-- 			    <div class="single-slide"><img src="/SimilarityWeb7/jsp/astyle/images/showbygis-image.jpg" alt=""> -->
<!-- 			        <div class="service-overflow text-center"> -->
<!-- 			            <h3>GIS展现</h3> -->
<!-- 			            <p>您可在postGIS上展现您的轨迹。</p> -->
<!-- 			        </div> -->
<!-- 			    </div>                         				 -->
<!-- 			</td>			 -->
		</tr>
	</table>
<!--======== end our service =========-->
<jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
</body>
</html>
