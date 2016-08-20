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
	
	//Ŀ��켣�ļ�����
	/** Ŀ��켣�ļ� */
	private File objectfile;
	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}
	/** Ŀ��켣�ļ��� */
	private String objectfileFileName;	
	public void setObjectfileFileName(String objectfileFileName) {
		this.objectfileFileName = objectfileFileName;
	}
	
	//���ݼ����
	/** ��ſͻ����ϴ����ļ� */
	private File[] testfile;	
	public void setTestfile(File[] testfile) {
		this.testfile = testfile;
	}
	/** �ļ��� */
	private String[] testfileFileName;	
	public void setTestfileFileName(String[] testfileFileName) {
		this.testfileFileName = testfileFileName;
	}
	
	/** ����ڷ����������ݼ����� */
	private String trajsName;	
	public String getTrajsName() {
		return trajsName;
	}
	public void setTrajsName(String trajsName) {
		this.trajsName = trajsName;
	}
	

	//���ӻ�ԭ�켣
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	public int getSize(){
		size=similarity.length;
		return size;
	}
	//���ӻ������Ƶ��ӹ켣
	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}
	
	//���ӻ������ƵĹ켣��
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
	 * ��ȡĿ��켣
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
			return actionResult;//δ�����ļ�						
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
				return actionResult;//�����ļ����Ҳ������ļ���������
			case -1:
				actionResult=INPUT;
				return actionResult;//������켣�ļ������������ļ���ƥ��
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
		
	//ģ������ʵ���������ݵķ�װ
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
