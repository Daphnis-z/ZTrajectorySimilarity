package com.adx.action;

import java.io.File;
import java.util.ArrayList;

import com.adx.datahandler.DataHandler;
import com.adx.dataread.CSVReader;
import com.adx.dataread.DataUpload;
import com.adx.dataread.MultiThreadWriter;
import com.adx.entity.Constant;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.util.FileAbout;
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
	
	/**
	 * 读取目标轨迹
	 * @param objfile
	 * @param objName
	 * @param sd
	 * @return
	 */
	private Trajectory readObjtraj(File objfile,String objName,SimularDef sd) {
		CSVReader objReader=new CSVReader(objfile, sd.getTimeStamp());
		int status_obj=objReader.readFile();
		if(status_obj==1){
			Trajectory traj=objReader.getTraj();
			DataHandler obj_handler=new DataHandler(traj);
			traj=obj_handler.dataHandle();
			traj.name=objName;
			return traj;
		}
		return null;
	}
	
	@Override
	public String execute() throws Exception {		
		fileName=new ArrayList<String>();
		Constant.pattern=1;		
		if(objectfile==null||(trajsName.equals("")&&testfile==null)){
			actionResult=NONE;
			return actionResult;//未输入文件						
		}
		int status=0;
		if(testfile!=null){
			status=DataUpload.saveData(testfile,testfileFileName,objectfile,objectfileFileName,simularDef);
			switch (status) {
			case 1:
				actionResult="doNothing";
				break;
			case 0:
				actionResult=ERROR;
				return actionResult;//输入文件名找不到，文件传输有误
			case -1:
				actionResult=INPUT;
				return actionResult;//所计算轨迹文件类型与输入文件不匹配
			default:
				break;
			}
		}else{
			if(FileAbout.exists(Constant.DATA_PATH+trajsName+".cache")){
				status=DataUpload.saveObjectTraj(objectfile, objectfileFileName, simularDef);
			}else{
				File file=new File(Constant.CACHE_FILE);
				file.delete();
				Trajectory objTraj=readObjtraj(objectfile, objectfileFileName, simularDef);
				if(objTraj!=null){
					status=1;
					MultiThreadWriter mtw=new MultiThreadWriter(objTraj, simularDef, trajsName);
					mtw.start();
					Thread.yield();
					Thread.sleep(2000);
				}else{
					status=-1;
				}
			}
			if(status==1){
				actionResult="doNothing";
			}else{
				actionResult=ERROR;
				return actionResult;
			}
		}				
		return actionResult;
	}
		
	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
