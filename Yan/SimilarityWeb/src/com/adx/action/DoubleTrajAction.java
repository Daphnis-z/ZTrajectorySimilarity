package com.adx.action;

import java.io.File;
import java.util.Vector;

import com.adx.datahandler.DataHandler;
import com.adx.dataread.CSVReader;
import com.adx.entity.Point;
import com.adx.entity.Constant;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.gis.ShowTraj;
import com.adx.similaralg.Similarity;
import com.adx.similaralg.SimilarityWithTime;
import com.adx.similaralg.SimilarityWithoutTime;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

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
	
	//���ڹ켣���ӻ�
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}

	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}

	//���ӻ������ƵĹ켣��
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
	public String execute(){
		Constant.pattern=0;
		if(objectfile==null||testfile==null){
			actionResult=NONE;
			return actionResult;//δ�����ļ�
		}
		int status_obj,status_test;
		Trajectory objTraj,testTraj;
		
		try{
			CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
			status_obj=objReader.readFile();		
			objTraj=objReader.getTraj();
			CSVReader testReader=new CSVReader(testfile, simularDef.getTimeStamp());
			status_test=testReader.readFile();
			testTraj=testReader.getTraj();
		}catch(Exception e){
			actionResult=ERROR;
			return actionResult;
		}
		
		if(status_obj==0||status_test==0){
			actionResult=ERROR;
			return actionResult;//�����ļ����Ҳ������ļ���������
		}
		if(status_obj==-1||status_test==-1){
			actionResult=INPUT;
			return actionResult;//������켣�ļ������������ļ���ƥ��
		}
		//����Ԥ����
		DataHandler obj_handler=new DataHandler(objTraj);
		objTraj=obj_handler.dataHandle();
		DataHandler test_handler=new DataHandler(testTraj,objTraj.getSubTrajs().size());
		testTraj=test_handler.dataHandle();
		
		Constant.objTraj=objTraj;
		Constant.testTraj=testTraj;
		
		Similarity dtw;
		if(simularDef.getTimeStamp()==0){
			 dtw=new SimilarityWithoutTime(objTraj, testTraj,simularDef);
		}else{
			 dtw=new SimilarityWithTime(objTraj,testTraj,simularDef);
		}
		
		similarity=dtw.getSimilarity();
		System.out.println(similarity);
		similarestTraj=dtw.getSimilarestTraj();
		similarestPoint=dtw.getSimilarestPoint();
		
		strTrajs=packageTrajs(objTraj,testTraj);
		strSubtrajs=packageTrajs(similarestTraj[0],similarestTraj[1]);
		strPoints=packagePoints(similarestPoint);
			
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

	//ģ������ʵ���������ݵķ�װ
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}

