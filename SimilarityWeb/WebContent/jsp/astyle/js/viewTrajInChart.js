/**
 * 在图表中显示轨迹
 */

 //处理来自服务器的数据
function dataHandle(strTrajs,hasTime) {
    var trajs=new Array();
    for(var i=0;i<strTrajs.length;++i){
        var traj=new Array();
        var points=strTrajs[i].split(",");
        for(var j=0,ix=0;j<points.length;){
           var point=new Object();
           point.longitude=points[j];
           point.latitude=points[j];
           j=hasTime=="true"? j+3:j+2;
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

//初始化图表
function initChart () {
    // SERIAL CHART
    // chart.dataProvider =chartData;
    var chart = new AmCharts.AmSerialChart();
    chart.categoryField = "longitude";
    chart.startDuration = 0.5;
    chart.balloon.color = "#000000";
    chart.addTitle("经度");
    chart.categoryAxis.labelsEnabled=false;//隐藏横坐标标签
    chart.mouseWheelScrollEnabled=true;

    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.fillAlpha = 1;
    categoryAxis.fillColor = "#FAFAFA";
    categoryAxis.gridAlpha = 30;
    categoryAxis.axisAlpha = 100;
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

    return chart;
}

function addPolyline (chart,chartData,traj,tname) {
    var lix=0;//上一次插入的下标
    for(var i=0;i<traj.length;++i){
        //定义待插入的点
        var point={
            "longitude":traj[i].longitude
        };
        point[tname]=traj[i].latitude;
        if(lix>=chartData.length-1){
            chartData.push(point);
            ++lix;
            continue;
        }

        var isInserted=false;
        for(var j=lix;j<chartData.length;++j){
            if(Math.abs(traj[i].longitude-chartData[j].longitude)<0.0000001){
                chartData[j][tname]=traj[i].latitude;
                lix=j;
                isInserted=true;
                break;
            }else if(j+1<chartData.length){
                if((traj[i].longitude-chartData[j].longitude)*(traj[i].longitude-chartData[j+1].longitude)<0){
                    chartData.splice(j,0,point);
                    lix=j+1;
                    isInserted=true;
                    break;
                }
            }
        }

        if (!isInserted) {//没有找到可插入的地方
            if(i==0&&traj[i].longitude<chartData[0].longitude){
               chartData.unshift(point); 
               lix=0;
            }else{
                lix=chartData.length;
                chartData[chartData.length]=point;

            }
        }
    }
    // var ix=chartData.length;
    // for(;i<traj.length;++i){
    //     var point={
    //         "longitude":traj[i].longitude
    //     };
    //     point[tname]=traj[i].latitude;
    //     chartData[ix++]=point;
    // }
}

//仅仅绘制轨迹
function showInChart (strTrajs,hasTime) {
    var chart=initChart();
    var trajs=dataHandle(strTrajs.split("@"),hasTime);
    var chartData=new Array();
    for(var i=0;i<trajs.length;++i){
        var name="traj"+i;
        addPolyline(chart,chartData,trajs[i],name);

        var graph = new AmCharts.AmGraph();
        graph.title = name;
        graph.valueField =name;
        graph.balloonText = "（[[category]]，[[value]]）";
        graph.bullet = "round";
        chart.addGraph(graph);
        if(i>1){
            graph.hidden=true;
        }
    }
    chart.dataProvider =chartData;
    chart.write("allmap");
}

function showInChartWithSubtraj (strTrajs,strSubtrajs,strPoints) {
    showInChart(strTrajs+"@"+strSubtrajs+"@"+strPoints);
}
