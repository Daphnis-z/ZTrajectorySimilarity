<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Similarity</title>
	</head>	
    <body>
    <jsp:include page="component/header.jsp"></jsp:include>
        <!--======== intro =========-->
        <section class="intro">
            <div class="row">
                <div class="col-md-6 col-md-offset-3 col-sm-12 col-xs-12">
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        </ol>

                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <div class="intro-content text-center">
                                    <h1>欢迎来到轨迹相似度查询系统</h1>
                                    <p>希望您在本次的系统使用获得较好的体验，并能达到您的目的。若您有兴趣，可了解更多关于系统的介绍</p>
                                    <a class="btn btn-default" href="/SimilarityWeb7/jsp/comment/help.jsp" role="button">了解更多<i class="lnr lnr-chevron-right"></i></a>
                                    <a class="btn btn-primary scroll" href="doublePattern.action"  role="button">进入系统<i class="lnr lnr-chevron-down"></i></a>
                                </div>
                            </div>
                            <div class="item">
                                <div class="intro-content text-center">
                                    <h1>我们可以帮您</h1>
                                    <p>计算两条轨迹的相似度，搜索最相似轨迹，查找最相似轨迹点（段）等功能。若您有兴趣，可了解更多关于系统的功能</p>
                                    <a class="btn btn-default" href="/SimilarityWeb7/jsp/comment/help.jsp" role="button">了解更多 <i class="lnr lnr-chevron-right"></i></a>
                                    <a class="btn btn-primary scroll" href="doublePattern.action" role="button">进入系统 <i class="lnr lnr-chevron-down"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!--======== end intro =========-->
        <div><br/></div>
        <!--======== recent event =========-->
        <section class="author-intro">
            <div class="row">
                <div class="col-md-3 col-md-offset-1 col-sm-12">
                    <div class="event-box text-center">
                        <h3>开发人员介绍</h3>
                        <p>此部分将对相关开发人员进行介绍,包括指导老师和开发团队.</p>
                        <a class="btn btn-primary" href="/SimilarityWeb7/jsp/comment/authorIntro.html" role="button">了解更多<i class="lnr lnr-chevron-right "></i></a>
                    </div>
                </div>
                <div class="col-md-8 col-xs-12">
                    <div class="row">
                        <div class="col-sm-3 p-l-r-0">
                            <div class="event-img">
                                <img src="/SimilarityWeb7/jsp/images/event-2.png" alt="image">
                                <div class="event-overflow">
                                    <p>叶枫</p>
                                    <small>指导老师</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 p-l-r-0">
                            <div class="event-img">
                                <img src="/SimilarityWeb7/jsp/images/event-1.png" alt="image">
                                <div class="event-overflow">
                                    <p>吴胜艳</p>
                                    <small>队长</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 p-l-r-0">
                            <div class="event-img">
                                <img src="/SimilarityWeb7/jsp/images/event-2.png" alt="image">
                                <div class="event-overflow">
                                    <p>邹由超</p>
                                    <small>队员</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 p-l-r-0">
                            <div class="event-img">
                                <img src="/SimilarityWeb7/jsp/images/event-1.png" alt="image">
                                <div class="event-overflow">
                                    <p>徐永欢</p>
                                    <small>队员</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!--======== end author Intro =========-->
        <!--======== contact =========-->
        <section id="contact" class="contact">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 text-center">
                        
                        <p class="small-tag">若有疑问请联系</p>
                        <h2 class="section-title">联系我们</h2>
                        <div class="border"><span class="border-l-r"><i class="fa fa-envelope-o"></i></span></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputEmail3">姓名</label>
                                    <input type="text" class="form-control" id="exampleInputEmail1" placeholder="姓名">
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputEmail3">Email</label>
                                    <input type="Email" class="form-control" id="exampleInputEmail2" placeholder="Email">
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputEmail3">电话</label>
                                    <input type="tel" class="form-control" id="exampleInputEmail3" placeholder="电话">
                                </div>
                            </div>
                        </div>    
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputEmail3">内容</label>
                                    <textarea class="form-control textarea" rows="5" placeholder="内容"></textarea>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-default btn-block">发送</button>
                                </div>
                            </div>
                        </div>    
                    </div>
                </div>
            </div>    
        </section>
        <!--======== end contact =========-->
        <jsp:include page="component/footer.jsp"></jsp:include>
    </body>
</html>