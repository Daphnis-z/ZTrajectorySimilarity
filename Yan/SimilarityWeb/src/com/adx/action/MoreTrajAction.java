package com.adx.action;

import java.io.File;
import java.util.ArrayList;
import com.adx.entity.Constant;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.DataUpload;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class MoreTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private double[] similarity;
	private String actionResult;
	private ArrayList<String> fileName;
	private int[] indexes;
	private int size;
	private Trajectory[] similarestTraj;
	private Point[] similarestPoint;
	
	//目标轨迹文件数据
	/** 目标轨迹文件 */
	private File objectfile;
	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}
	/** 目标轨迹文件名 */
	private String objectfileFileName;	
	public void setObjectfileFileName(String objectfileFileName) {
		this.objectfileFileName = objectfileFileName;
	}
	
	//数据集相关
	/** 存放客户端上传的文件 */
	private File[] testfile;	
	public void setTestfile(File[] testfile) {
		this.testfile = testfile;
	}
	/** 文件名 */
	private String[] testfileFileName;	
	public void setTestfileFileName(String[] testfileFileName) {
		this.testfileFileName = testfileFileName;
	}
	
	/** 存放在服务器的数据集名称 */
	private String trajsName;	
	public String getTrajsName() {
		return trajsName;
	}
	public void setTrajsName(String trajsName) {
		this.trajsName = trajsName;
	}
	

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
	
	public String getActionResult() {
		return actionResult;
	}

	public double[] getSimilarity() {
		return similarity;
	}
	
	@Override
	public String execute() throws Exception {		
		fileName=new ArrayList<String>();
		Constant.pattern=1;		
		if(objectfile==null||(trajsName.equals("")&&testfile==null)){
			actionResult=NONE;
			return actionResult;//未输入文件						
		}
		
		if(testfile!=null&&DataUpload.saveData(testfile,
				testfileFileName,objectfile,objectfileFileName,simularDef)){
			actionResult="doNothing";
		}else if(DataUpload.saveObjectTraj(objectfile, objectfileFileName, simularDef)){
			actionResult="doNothing";
		}else{
			actionResult=ERROR;
		}
		
//		CSVReader objReader=new CSVReader(objectfile, simularDef.getTimeStamp());
//		int status_obj=objReader.readFile();
//		Trajectory objTraj=objReader.getTraj();
//		Constant.objTraj=objTraj;
//		
//		FileDerecterReader testFileReader=new FileDerecterReader(testfilePath,simularDef.getTimeStamp());
//		int status_test=testFileReader.readAllFile();
//		fileName=testFileReader.fileName;
//		
//		List<Trajectory> testGroup=new ArrayList<Trajectory>(Arrays.asList(testFileReader.getTrajGroup()));
//		
//		if(status_obj==0||status_test==0){
//			actionResult=ERROR;
//			return actionResult;//输入文件名找不到，文件传输有误
//		}
//		if(status_obj==-1||status_test==-1){
//			actionResult=INPUT;
//			return actionResult;//所计算轨迹文件类型与输入文件不匹配
//		}
//		DataHandler obj_handler=new DataHandler(objTraj);
//		objTraj=obj_handler.dataHandle();
//		for (int i=0;i<testGroup.size();i++){
//			Trajectory traj=testGroup.get(i);
//			DataHandler test_handler=new DataHandler(traj);
//			traj=test_handler.dataHandle();
//		}
//
//		//使用轨迹过滤器
//		//获取比较相似的轨迹群
//		List<Trajectory> trajs=EigenvalueFilter.filtrateTraj(testGroup, objTraj);
//		indexes=new int[trajs.size()];
//		similarity=new double[trajs.size()];
//		ArrayList<Similarity> dtwExample=new ArrayList<Similarity>();
//		Similarity dtw;
//		for (int i=0;i<trajs.size();i++){
//			Trajectory traj=trajs.get(i);
//			if(simularDef.getTimeStamp()==0){
//				 dtw=new SimilarityWithoutTime(objTraj, traj,simularDef);
//			}else{
//				 dtw=new SimilarityWithTime(objTraj,traj,simularDef);
//			}
//			dtwExample.add(dtw);
//			System.out.println("timestamp:"+simularDef.getTimeStamp());
//			similarity[i]=dtw.getSimilarity();
//		}
//		indexes=Utility.orderByValue(similarity);
//		similarestTraj=dtwExample.get(indexes[0]).getSimilarestTraj();
//		similarestPoint=dtwExample.get(indexes[0]).getSimilarestPoint();		
//		readyForViewTrajs(objTraj, trajs);
//		
//		actionResult=SUCCESS;
		return actionResult;
	}
		
	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
