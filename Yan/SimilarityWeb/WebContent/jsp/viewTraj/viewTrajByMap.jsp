<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>可视化轨迹</title>
</head>
<body>
<jsp:include page="//jsp/component/header.jsp"></jsp:include>
  <table>
    <tr height="8%">
    	<form action="viewTraj.action" method="post" enctype="multipart/form-data">
			<td><b>上传轨迹文件：</td>
			<td><input type="file" name="trajFile" id="trajFile" size="50" /></td>
			<td><input type="submit" value="显示轨迹" onclick="checkValue()" /></td>
		</form>
    </tr>
  </table>
  <table id="map">
    <tr>
      <td colspan="3"><div id="allmap"><h2>地图<h2></div></td>
    </tr>
  </table>
   <jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
</body>
</html>
<script type="text/javascript">
	function checkValue(){
		if(document.getElementById("trajFile").value==""){
			alert("未选择文件！！")
		}
	}
	showMapWithSubtraj("${strTrajs}","${subtraj}")
</script>

