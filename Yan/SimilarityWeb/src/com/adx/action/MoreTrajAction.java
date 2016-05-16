package com.adx.action;

import java.io.File;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;
import com.adx.similarity.DTWSimilarity;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class MoreTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private File[] testfile;
	private double[] similarity;
	private String actionResult;
	private int fileLength;
	
	public int getFileLength() {
		return fileLength;
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

	public File[] getTestfile() {
		return testfile;
	}

	public void setTestfile(File[] testfile) {
		this.testfile = testfile;
	}

	private void setSimularDef(){
		Constant.simularDef=simularDef;
	}
	
	public double[] getSimilarity() {
		return similarity;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		setSimularDef();
		Constant.pattern=1;
		fileLength=testfile.length;
		Trajectory[] testTraj=new Trajectory[fileLength];
		int[] status_test=new int[fileLength];
		similarity=new double[fileLength];
		if(objectfile==null||testfile==null){
			actionResult=NONE;
			return actionResult;//未输入文件
		}
		CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
		int status_obj=objReader.readFile();
		Trajectory objTraj=objReader.getTraj();
		Constant.objTraj=objTraj;
		for (int i=0;i<fileLength;i++){
			CSVReader testReader=new CSVReader(testfile[i], simularDef.getTimeStamp());
			status_test[i]=testReader.readFile();
			testTraj[i]=testReader.getTraj();
		}
		
		if(status_obj==0||checkStatus(status_test,0)){
			actionResult=ERROR;
			return actionResult;//输入文件名找不到，文件传输有误
		}
		if(status_obj==-1||checkStatus(status_test,-1)){
			actionResult=INPUT;
			return actionResult;//所计算轨迹文件类型与输入文件不匹配
		}
		Constant.testTraj_more=testTraj;
		DTWSimilarity dtw=new DTWSimilarity(simularDef);
		for (int i=0;i<testfile.length;i++){
			similarity[i]=dtw.getSimilarity(objTraj, testTraj[i], simularDef.getTimeStamp());
			System.out.println(similarity[i]);
		}
		actionResult=SUCCESS;
		return actionResult;
	}
	
	public boolean checkStatus(int[] status,int flag){
		int i=0;
		while(status[i]==flag){
			System.out.println("status:"+i+status[i]);
			i++;
		}
		if(i!=status.length-1){
			return false;
		}
		return true;
	}
	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
