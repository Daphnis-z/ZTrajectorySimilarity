package com.adx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.datahandler.DataHandlerImp;
import com.adx.datahandler.FileDerecterReader;
import com.adx.datahandler.Utility;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;
import com.adx.similaralg.Similarity;
import com.adx.similaralg.SimilarityWithTime;
import com.adx.similaralg.SimilarityWithoutTime;
import com.daphnis.gis.ShowTraj;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class MoreTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private String testfilePath;
	private double[] similarity;
	private String actionResult;
	private ArrayList<String> fileName;
	private int[] indexes;
	private int fileLength;
	private Trajectory[] similarestTraj;
	private Point[] similarestPoint;
	
	//可视化原轨迹
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	
	//可视化最相似的子轨迹
	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}
	
	//可视化最相似的轨迹点
	private String strPoints;	
	public String getStrPoints() {
		return strPoints;
	}
	
	public ArrayList<String> getFileName() {
		return fileName;
	}
	public Trajectory[] getSimilarestTraj() {
		return similarestTraj;
	}

	public Point[] getSimilarestPoint() {
		return similarestPoint;
	}
	
	public int getFileLength() {
		return fileLength;
	}
	public int[] getIndexes() {
		return indexes;
	}
	public void setTestfilePath(String testfilePath) {
		this.testfilePath = testfilePath;
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

	public double[] getSimilarity() {
		return similarity;
	}
	
	private void readyForViewTraj(Trajectory traj1,Trajectory traj2){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		strTrajs=ShowTraj.convertSomeTrajs(vt);
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		fileName=new ArrayList<String>();
		Constant.pattern=1;
		
		if(objectfile==null||testfilePath.equals("")){
			actionResult=NONE;
			return actionResult;//未输入文件
		}
		CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
		int status_obj=objReader.readFile();
		Trajectory objTraj=objReader.getTraj();
		Constant.objTraj=objTraj;
		
		FileDerecterReader testFileReader=new FileDerecterReader(testfilePath,simularDef.getTimeStamp());
		int status_test=testFileReader.readAllFile();
		fileName=testFileReader.fileName;
		Trajectory[] testGroup=testFileReader.getTrajGroup();
		
		if(status_obj==0||status_test==0){
			actionResult=ERROR;
			return actionResult;//输入文件名找不到，文件传输有误
		}
		if(status_obj==-1||status_test==-1){
			actionResult=INPUT;
			return actionResult;//所计算轨迹文件类型与输入文件不匹配
		}
		DataHandlerImp obj_handler=new DataHandlerImp(objTraj);
		objTraj=obj_handler.dataHandle();

		fileLength=testGroup.length;
		indexes=new int[fileLength];
		similarity=new double[fileLength];
		ArrayList<Similarity> dtwExample=new ArrayList<Similarity>();
		Similarity dtw;
		for (int i=0;i<fileLength;i++){
			DataHandlerImp test_handler=new DataHandlerImp(testGroup[i]);
			testGroup[i]=test_handler.dataHandle();
			if(simularDef.getTimeStamp()==0){
				 dtw=new SimilarityWithoutTime(objTraj, testGroup[i],simularDef);
			}else{
				 dtw=new SimilarityWithTime(objTraj,testGroup[i],simularDef);
			}
			dtwExample.add(dtw);
			similarity[i]=dtw.getSimilarity();
		}
		indexes=Utility.orderByValue(similarity);
		similarestTraj=dtwExample.get(indexes[0]).getSimilarestTraj();
		similarestPoint=dtwExample.get(indexes[0]).getSimilarestPoint();
		
		strTrajs=packageTrajs(objTraj,testGroup[indexes[0]]);
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
