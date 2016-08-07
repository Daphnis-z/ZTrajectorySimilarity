<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--  <%@page import="com.sun.glass.ui.Window" %>  --%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
                <div class="row">
                    <div class="col-md-4 col-sm-6 col-xs-12">
                        <div class="do-box text-center">
                            <i class="lnr lnr-inbox"></i>
                            <h3>最坏值设置</h3>
                            <p>###########################</p>
                        </div>
                    </div>
                    <div class="col-md-4 col-sm-6 col-xs-12">
                        <div class="do-box bg-color text-center">
                            <i class="lnr lnr-cog"></i>
                            <h3>最坏值设置</h3>
                            <p>###########################</p>
                        </div>
                    </div>
                    <div class="col-md-4 col-sm-6 col-xs-12">
                        <div class="do-box text-center">
                            <i class="lnr lnr-leaf"></i>
                            <h3>最坏值设置</h3>
                            <p>###########################</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="//jsp/component/footer.jsp"></jsp:include> 
</body>
</html>

