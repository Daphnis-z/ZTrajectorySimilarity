package com.adx.action;

import java.io.File;

import com.adx.datahandler.CSVReader;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;
import com.adx.similarity.DTWSimilarity;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class DoubleTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private File testfile;
	private double similarity;
	private String actionResult;
	
	
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

	private void setSimularDef(){
		Constant.simularDef=simularDef;
	}
	
	public double getSimilarity() {
		return similarity;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		setSimularDef();
		Constant.pattern=0;
		if(objectfile==null||testfile==null){
			actionResult=NONE;
			return actionResult;//未输入文件
		}
		CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
		int status_obj=objReader.readFile();
		Trajectory objTraj=objReader.getTraj();
		System.out.println("status_obj:"+status_obj);
		CSVReader testReader=new CSVReader(testfile, simularDef.getTimeStamp());
		int status_test=testReader.readFile();
		Trajectory testTraj=testReader.getTraj();
		System.out.println("status_test:"+status_test);
		if(status_obj==0||status_test==0){
			actionResult=ERROR;
			return actionResult;//输入文件名找不到，文件传输有误
		}
		if(status_obj==-1||status_test==-1){
			actionResult=INPUT;
			return actionResult;//所计算轨迹文件类型与输入文件不匹配
		}
		Constant.objTraj=objTraj;
		Constant.testTraj=testTraj;
//		Vector<Point> points=testTraj.getPoints();
//		System.out.println("points.size:"+points.size());
//		for(int i=0;i<points.size();i++){
//			Point point=points.get(i);
//			System.out.println(point.getLatitude()+"::"+point.getLongitude()+"::"+point.getTimestamp());
//			}
		
		DTWSimilarity dtw=new DTWSimilarity(simularDef);
		System.out.println("timestamp:"+simularDef.getTimeStamp());
		similarity=dtw.getSimilarity(objTraj, testTraj, simularDef.getTimeStamp());
		System.out.println(similarity);
		actionResult=SUCCESS;
		return actionResult;
	}

	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
