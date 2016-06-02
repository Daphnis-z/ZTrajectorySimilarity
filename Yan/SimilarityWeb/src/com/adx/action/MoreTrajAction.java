package com.adx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.datahandler.DataHandlerImp;
import com.adx.datahandler.FileDerecterReader;
import com.adx.datahandler.Utility;
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
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	public ArrayList<String> getFileName() {
		return fileName;
	}

	private int fileLength;
	
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

	private void setSimularDef(){
		Constant.simularDef=simularDef;
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
		setSimularDef();
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
		Similarity dtw;
		for (int i=0;i<fileLength;i++){
			DataHandlerImp test_handler=new DataHandlerImp(testGroup[i]);
			testGroup[i]=test_handler.dataHandle();
			if(simularDef.getTimeStamp()==0){
				 dtw=new SimilarityWithoutTime(objTraj, testGroup[i],simularDef);
			}else{
				 dtw=new SimilarityWithTime(objTraj,testGroup[i],simularDef);
			}
			System.out.println("timestamp:"+simularDef.getTimeStamp());
			similarity[i]=dtw.getSimilarity();
		}
		indexes=Utility.orderByValue(similarity);
		readyForViewTraj(objTraj,testGroup[indexes[1]]);
		
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
