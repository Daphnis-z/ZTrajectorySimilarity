<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>amCharts examples</title>
<!--         <link rel="stylesheet" href="style.css" type="text/css"> -->
        <script src="./jsp/amcharts/amcharts.js" type="text/javascript"></script>
        <script src="./jsp/amcharts/serial.js" type="text/javascript"></script>

        <script>
            var minLat=1000.0,maxLat=-1000.0;
            var trajs=new Array();//存放所有轨迹
            dataHandle();

            var chart;
            var chartData=new Array();
            fillChartData();

            //处理来自服务器的数据
            function dataHandle() {
                var strTrajs="${strTrajs}";
                strTrajs=strTrajs.split("@");
                for(var i=0;i<strTrajs.length;++i){
                    var traj=new Array();
                    var points=strTrajs[i].split(",");
                    for(var j=0,ix=0;j<points.length;++j){
                        var point=new Object();
                        point.longitude=points[j];
                        point.latitude=points[++j];
                        traj[ix++]=point;
                        minLat=minLat>point.latitude? point.latitude:minLat;
                        maxLat=maxLat<point.latitude? point.latitude:maxLat;
                    }
                    trajs[i]=traj;
                }
            }

            //填充图表数据
            function fillChartData () {
                var ix=0;
                 for(var i=0;i<trajs.length;++i){
                    var name="traj"+i;
                    for(var j=0;j<trajs[i].length;++j){
                        var point={
                            "longitude":trajs[i][j].longitude
                        };
                        point[name]=trajs[i][j].latitude;
                        chartData[ix++]=point;
                    }
                 }
            }

            // var chartData = [
            //     {
            //         "longitude": 118.803068,
            //         "italy": 1,
            //         "germany": 31.977605,
            //         "customBullet": "./amcharts/images/export.png"
            //        "uk": 3
            //     },
            //     {
            //         "longitude": 118.801056,
            //         "italy": 1,
            //         "germany": 31.982138,
            //         "uk": 6
            //     },
            //     {
            //         "longitude": 118.795881,
            //         "italy": 2,
            //         "germany": 31.984465,
            //         "uk": 1
            //     }
            // ];


            AmCharts.ready(function () {
                // SERIAL CHART
                chart = new AmCharts.AmSerialChart();
                chart.dataProvider = chartData;
                chart.categoryField = "longitude";
                chart.startDuration = 0.5;
                chart.balloon.color = "#000000";
                chart.addTitle("经度");

                chart.accessibleTitle="";

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.fillAlpha = 1;
                categoryAxis.fillColor = "#FAFAFA";
                categoryAxis.gridAlpha = 0;
                categoryAxis.axisAlpha = 0;
                categoryAxis.gridPosition = "start";
                categoryAxis.position = "top";

                // value
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.title = "纬度";
                valueAxis.dashLength = 5;
                valueAxis.axisAlpha = 0;
//                 valueAxis.minimum = minLat;
//                 valueAxis.maximum = maxLat;
                valueAxis.integersOnly = false;
                valueAxis.gridCount = 100;
                valueAxis.reversed = true; // this line makes the value axis reversed
                chart.addValueAxis(valueAxis);

                for(var i=0;i<trajs.length;++i){
                    var graph = new AmCharts.AmGraph();
                    graph.title = "traj"+i;
                    graph.valueField =graph.title;
                    graph.balloonText = "（[[category]]，[[value]]）";
                    graph.bullet = "round";
                    chart.addGraph(graph);
                }

                // // Germany graph
                // var graph = new AmCharts.AmGraph();
                // graph.title = "traj0";
                // graph.valueField = "traj0";
                // graph.balloonText = "（[[category]]，[[value]]）";
                // graph.bullet = "round";
                // chart.addGraph(graph);

                // // United Kingdom graph
                // var graph = new AmCharts.AmGraph();
                // graph.title = "traj1";
                // graph.valueField = "traj1";
                // graph.balloonText = "place taken by UK in [[category]]: [[value]]";
                // graph.bullet = "round";
                // chart.addGraph(graph);

                // CURSOR
                var chartCursor = new AmCharts.ChartCursor();
                chartCursor.cursorPosition = "mouse";
                chartCursor.zoomable = false;
                chartCursor.cursorAlpha = 0;
                chart.addChartCursor(chartCursor);

                // SCROLLBAR
                var chartScrollbar = new AmCharts.ChartScrollbar();
                chart.addChartScrollbar(chartScrollbar);


                // LEGEND
                var legend = new AmCharts.AmLegend();
                legend.useGraphSettings = true;
                chart.addLegend(legend);

                // WRITE
                chart.write("chartdiv");
            });
        </script>
    </head>

    <body>
        <div id="chartdiv" style="width: 100%; height: 500px;"></div>
    </body>

</html>
