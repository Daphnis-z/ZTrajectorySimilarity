package com.adx.action;

import java.io.File;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.datahandler.DataHandlerImp;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;
import com.adx.similaralg.Similarity;
import com.adx.similaralg.SimilarityWithTime;
import com.adx.similaralg.SimilarityWithoutTime;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.daphnis.gis.ShowTraj;

@SuppressWarnings("serial")
public class DoubleTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private File testfile;
	private double similarity;
	private Trajectory[] similarestTraj;
	private Point[] similarestPoint;
	public Trajectory[] getSimilarestTraj() {
		return similarestTraj;
	}

	public Point[] getSimilarestPoint() {
		return similarestPoint;
	}

	private String actionResult;
	
	//用于轨迹可视化
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}

	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}

	//可视化最相似的轨迹点
	private String strPoints;	
	public String getStrPoints() {
		return strPoints;
	}

	public String getActionResult() {
		return actionResult;
	}

	public File getObjectfile() {
		return objectfile;
	}

	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}

	public File getTestfile() {
		return testfile;
	}

	public void setTestfile(File testfile) {
		this.testfile = testfile;
	}
	
	public double getSimilarity() {
		return similarity;
	}

	@Override
	public String execute() throws Exception {
		Constant.pattern=0;
		if(objectfile==null||testfile==null){
			actionResult=NONE;
			return actionResult;//未输入文件
		}
		CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
		int status_obj=objReader.readFile();
		Trajectory objTraj=objReader.getTraj();
		CSVReader testReader=new CSVReader(testfile, simularDef.getTimeStamp());
		int status_test=testReader.readFile();
		Trajectory testTraj=testReader.getTraj();
		if(status_obj==0||status_test==0){
			actionResult=ERROR;
			return actionResult;//输入文件名找不到，文件传输有误
		}
		if(status_obj==-1||status_test==-1){
			actionResult=INPUT;
			return actionResult;//所计算轨迹文件类型与输入文件不匹配
		}
		//数据预处理
		DataHandlerImp obj_handler=new DataHandlerImp(objTraj);
		objTraj=obj_handler.dataHandle();
		DataHandlerImp test_handler=new DataHandlerImp(testTraj,objTraj.getSubTrajs().size());
		testTraj=test_handler.dataHandle();
		
		Constant.objTraj=objTraj;
		Constant.testTraj=testTraj;
		
		//已分割的轨迹调用实例
//		Vector<Point> points=testTraj.getSubTrajs().get(0).getPoints();
//		for(int i=0;i<points.size();i++){
//			Point point=points.get(i);
//			System.out.println(point.getLatitude()+"::"+point.getLongitude()+"::"+point.getTimestamp());
//		}
		Similarity dtw;
		if(simularDef.getTimeStamp()==0){
			 dtw=new SimilarityWithoutTime(objTraj, testTraj,simularDef);
		}else{
			 dtw=new SimilarityWithTime(objTraj,testTraj,simularDef);
		}
		
		similarity=dtw.getSimilarity();
		similarestPoint=dtw.getSimilarestPoint();
		
		strTrajs=packageTrajs(objTraj,testTraj);
		System.out.println("similarestTraj---------------"+similarestTraj);
		System.out.println("similarestPoint---------------"+similarestPoint);
		if(similarestTraj!=null){
			strSubtrajs=packageTrajs(similarestTraj[0],similarestTraj[1]);
		}
		if(similarestPoint!=null){
			strPoints=packagePoints(similarestPoint);
		}
		actionResult=SUCCESS;
		return actionResult;
	}
	
	private String packageTrajs(Trajectory traj1,Trajectory traj2){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		return ShowTraj.convertSomeTrajs(vt);
	}
	private String packagePoints(Point[] points){
		Trajectory traj=new Trajectory();
		for(int i=0;i<points.length;++i){
			traj.addPoint(points[i]);
		}
		return ShowTraj.convertTraj(traj);
	}

	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}

