package com.adx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.datahandler.DataHandler;
import com.adx.datahandler.FileDerecterReader;
import com.adx.datahandler.Utility;
import com.adx.entity.Constant;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.similaralg.Similarity;
import com.adx.similaralg.SimilarityWithTime;
import com.adx.similaralg.SimilarityWithoutTime;
import com.daphnis.gis.ShowTraj;
import com.daphnis.trajFilter.EigenvalueFilter;
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
	private int size;
	private Trajectory[] similarestTraj;
	private Point[] similarestPoint;
	
	//可视化原轨迹
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	public int getSize(){
		size=similarity.length;
		return size;
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
	
	@Override
	public String execute() throws Exception {
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
		
		List<Trajectory> testGroup=new ArrayList<Trajectory>(Arrays.asList(testFileReader.getTrajGroup()));
		
		if(status_obj==0||status_test==0){
			actionResult=ERROR;
			return actionResult;//输入文件名找不到，文件传输有误
		}
		if(status_obj==-1||status_test==-1){
			actionResult=INPUT;
			return actionResult;//所计算轨迹文件类型与输入文件不匹配
		}
		DataHandler obj_handler=new DataHandler(objTraj);
		objTraj=obj_handler.dataHandle();
		for (int i=0;i<testGroup.size();i++){
			Trajectory traj=testGroup.get(i);
			DataHandler test_handler=new DataHandler(traj);
			traj=test_handler.dataHandle();
		}

		//使用轨迹过滤器
		//获取比较相似的轨迹群
		List<Trajectory> trajs=EigenvalueFilter.filtrateTraj(testGroup, objTraj);
		indexes=new int[trajs.size()];
		similarity=new double[trajs.size()];
		ArrayList<Similarity> dtwExample=new ArrayList<Similarity>();
		Similarity dtw;
		for (int i=0;i<trajs.size();i++){
			Trajectory traj=trajs.get(i);
			if(simularDef.getTimeStamp()==0){
				 dtw=new SimilarityWithoutTime(objTraj, traj,simularDef);
			}else{
				 dtw=new SimilarityWithTime(objTraj,traj,simularDef);
			}
			dtwExample.add(dtw);
			System.out.println("timestamp:"+simularDef.getTimeStamp());
			similarity[i]=dtw.getSimilarity();
		}
		indexes=Utility.orderByValue(similarity);
		similarestTraj=dtwExample.get(indexes[0]).getSimilarestTraj();
		similarestPoint=dtwExample.get(indexes[0]).getSimilarestPoint();		
		readyForViewTrajs(objTraj, trajs);
		
		actionResult=SUCCESS;
		return actionResult;
	}
	
	/**
	 * 为可视化轨迹做好准备
	 * @param objTraj
	 * @param trajs
	 */
	private void readyForViewTrajs(Trajectory objTraj, List<Trajectory> trajs) {
		strTrajs=packageTrajs(objTraj,trajs);
		strSubtrajs=packageSubTrajs(similarestTraj[0],similarestTraj[1]);
		strPoints=packagePoints(similarestPoint);
	}	
	/**
	 * 打包待可视化的轨迹
	 * @param objTraj
	 * @param trajs
	 * @return
	 */
	private String packageTrajs(Trajectory objTraj, List<Trajectory> trajs){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(objTraj);
		for(int i=0;i<indexes.length&i<10;++i){
			vt.addElement(trajs.get(indexes[i]));
		}
		return ShowTraj.convertSomeTrajs(vt);
	}
	/**
	 * 打包待可视化的子轨迹
	 * @param traj1
	 * @param traj2
	 * @return
	 */
	private String packageSubTrajs(Trajectory traj1,Trajectory traj2){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		return ShowTraj.convertSomeTrajs(vt);
	}
	/**
	 * 打包最相似的轨迹点
	 * @param points
	 * @return
	 */
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
