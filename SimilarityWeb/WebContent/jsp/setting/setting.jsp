<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--  <%@page import="com.sun.glass.ui.Window" %>  --%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="./jsp/astyle/css/setting.css">  
    <title>系统设置</title>
</head>
<body>
  <jsp:include page="//jsp/component/header.jsp"></jsp:include>
<!--======== what we do =========-->
<section id="what-we-do" class="what-we-do">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3 text-center">
                <p class="small-tag">系统设置</p>
                <h2 class="section-title">可调整参数</h2>
                <div class="border"><span class="border-l-r"><i class="lnr lnr-diamond"></i></span></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="row" id="yg">
				    <form action="setting.action" method="post" enctype="multipart/form-data">
				      <table align="center">
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
				</div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
<script>
	msg="${msg}"
	if(msg!=""){
	  	alert(msg);
	}
</script>

</body>
</html>

