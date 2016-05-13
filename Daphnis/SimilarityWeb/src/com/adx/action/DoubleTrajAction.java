package com.adx.action;

import java.io.File;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;
import com.adx.similaralg.DTWSimilarity;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class DoubleTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private File testfile;
	private double similarity;
	
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
		if(objectfile==null||testfile==null){
			return ERROR;
		}
		CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
		objReader.readFile();
		Trajectory objTraj=objReader.getTraj();
		CSVReader testReader=new CSVReader(testfile, simularDef.getTimeStamp());
		testReader.readFile();
		Trajectory testTraj=testReader.getTraj();
		
		Vector<Point> points=testTraj.getPoints();
		System.out.println(points.size());
		for(int i=0;i<points.size();i++){
			Point point=points.get(i);
			System.out.println(point.getLatitude()+"::"+point.getLongitude());
			}
		
		DTWSimilarity dtw=new DTWSimilarity(simularDef);
		similarity=dtw.getSimilarity(objTraj, testTraj, simularDef.getTimeStamp());
		System.out.println(similarity);
		return SUCCESS;
	}

	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
