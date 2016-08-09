<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!--======== All Stylesheet =========-->
 <link href="/SimilarityWeb7/jsp/bootstrap/css/bootstrap.min.css" rel="stylesheet">
 <link href="/SimilarityWeb7/jsp/astyle/css/font-awesome.min.css" rel="stylesheet">
 <link href="/SimilarityWeb7/jsp/astyle/css/linearicons.css" rel="stylesheet">
 <!--=== plugins ===-->
 <link href="/SimilarityWeb7/jsp/astyle/css/owl.carousel.css" rel="stylesheet">
 <link href="/SimilarityWeb7/jsp/astyle/css/owl.theme.css" rel="stylesheet">
 <!--=== end plugins ===-->
 <link href="/SimilarityWeb7/jsp/astyle/css/style.css" rel="stylesheet">
 <link href="/SimilarityWeb7/jsp/astyle/css/responsive.css" rel="stylesheet">
<!--======== header =========-->

<script type="text/javascript" src="./jsp/astyle/js/jquery-2.1.1.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        // $("#pages li").removeClass("active");
        var tle=$("title").text();
        switch (tle) {
        case "Similarity":
            $("#pages li").eq(0).addClass("active");
            break;
        case "双轨迹模式":
        case "双轨迹结果显示":
            $("#pages li").eq(1).addClass("active");
            break;
        case "多轨迹模式":
        case "多轨迹结果显示":
            $("#pages li").eq(2).addClass("active");
            break;
        case "可视化轨迹":
        case "在地图中可视化轨迹":
        case "在图表中可视化轨迹":
            $("#pages li").eq(3).addClass("active");
            break;
        case "系统设置":
            $("#pages li").eq(4).addClass("active");
            break;
        default:
            // statements_def
            break;
    	}
	});
</script>

<header id="home">
    <div class="small-menu small-menu-2">
        <div class="container">
            <div class="row">
                <div class="col-md-7 col-sm-8 col-xs-5">
                    <ul class="list-inline info-link">
                        <li title="Language"><i class="fa fa-globe"></i><span class="hidden-xs">语言 : 中文</span></li>
                        <li title="team"><i class="fa fa-group"></i><span class="hidden-xs hidden-sm">队名：Similarity</span></li>
                        <li title="member"><i class="fa fa-user"></i><span class="hidden-xs">队员：吴胜艳、邹由超、徐永欢</span></li>
                    </ul>
                </div>
                <div class="col-md-5 col-sm-4 col-xs-7">
                    <ul class="list-inline pull-right social-link">
                        <li title="Email"><i class="fa fa-paper-plane"></i><span class="hidden-sm hidden-xs">agnes777@163.com</span></li>
                        <li title="github"><i class="fa fa-github"></i><span class="hidden-sm hidden-xs">https://github.com/Daphnis-z/ZTrajectorySimilarity</span></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <!-- menu 4 -->
            <div class="navbar navbar-default default-menu menu-4" role="navigation">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-4" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <b class="navbar-brand default-logo">轨迹相似度查询系统</b>
                    </div>

                    <div class="collapse navbar-collapse" id="menu-4">
                        <ul class="nav navbar-nav navbar-right" id="pages">
                            <li><a class="scroll" href="index.action">首页</a></li>
                            <li><a class="scroll" href="doublePattern.action">双轨迹模式</a></li>
                            <li><a class="scroll" href="morePattern.action">多轨迹模式</a></li>
                            <li><a class="scroll" href="viewTraj.action">轨迹可视化</a></li>
                            <li><a class="scroll" href="setting.action">系统设置</a></li>
                            <li><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-comment"><span class="cart-number">！</span></i></a>
                                <ul class="dropdown-menu cart">
                                    <li><i class="fa fa-group"><span>Similarity</span></i>
                                                <span>来自开发团队的祝福 :愿您在轨迹之旅中能达到您得目的地，希望我们的系统能帮助到您，若你有任何的疑问，可向我们团队发送email，在页面上端有联系方式
                                                    ，亦或者你想学习一些代码知识，可在github上获取代码，祝您旅途愉快</span></li>
                                    <li class="text-center"><p><a class="btn btn-default" href="#">确定</a></p></li>
                                </ul>
                            </li>
                            <li><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-search"></i></a>
                                <ul class="dropdown-menu list-search">
                                    <li>
                                        <form class="navbar-form navbar-left nav-search" role="search">
                                            <div class="form-group">
                                                <input type="text" class="form-control" placeholder="Search">
                                                <i class="fa fa-search"></i>
                                            </div>
                                        </form>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div><!-- end menu -->
        </div>
    </div>
</header>
<!--======== end header =========-->
