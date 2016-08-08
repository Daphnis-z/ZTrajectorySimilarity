/**
 * 
 */
 //处理来自服务器的数据
function dataHandle(strTrajs) {
    var trajs=new Array();
    strTrajs=strTrajs.split("@");
    for(var i=0;i<strTrajs.length;++i){
        var traj=new Array();
        var points=strTrajs[i].split(",");
        for(var j=0,ix=0;j<points.length;++j){
           var point=new Object();
           point.longitude=points[j];
           point.latitude=points[++j];
           traj[ix++]=point;
       }
       trajs[i]=traj;
   }
   return trajs;
}

//填充图表数据
function fillChartData (trajs) {
    var chartData=new Array();
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
    return chartData;
}


function showInChartWithSubtraj (strTrajs,strSubtrajs,strPoints) {
    var trajs=dataHandle(strTrajs);
    var chart;
    var chartData=fillChartData(trajs);

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
    chart.write("allmap");
}
