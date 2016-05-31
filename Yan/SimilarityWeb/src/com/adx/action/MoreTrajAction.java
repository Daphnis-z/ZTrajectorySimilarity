package com.adx.action;

import java.io.File;
import java.util.ArrayList;

import com.adx.datahandler.CSVReader;
import com.adx.datahandler.DataHandlerImp;
import com.adx.datahandler.FileDerecterReader;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;
import com.adx.similaralg.DTWSimilarity;
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
	public ArrayList<String> getFileName() {
		return fileName;
	}

	private int fileLength;
	
	public int getFileLength() {
		return fileLength;
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

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		setSimularDef();
		fileName=new ArrayList<String>();
		Constant.pattern=1;
		
		if(objectfile==null||testfilePath.equals("")){
			actionResult=NONE;
			return actionResult;//δ�����ļ�
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
			return actionResult;//�����ļ����Ҳ������ļ���������
		}
		if(status_obj==-1||status_test==-1){
			actionResult=INPUT;
			return actionResult;//������켣�ļ������������ļ���ƥ��
		}
		DataHandlerImp obj_handler=new DataHandlerImp(objTraj);
		objTraj=obj_handler.dataHandle();
		
		fileLength=testGroup.length;
		similarity=new double[fileLength];
		DTWSimilarity dtw=new DTWSimilarity(simularDef);
		for (int i=0;i<fileLength;i++){
			DataHandlerImp test_handler=new DataHandlerImp(testGroup[i]);
			testGroup[i]=test_handler.dataHandle();
			similarity[i]=dtw.getSimilarity(objTraj, testGroup[i], simularDef.getTimeStamp());
		}
		actionResult=SUCCESS;
		return actionResult;
	}
	
	//ģ������ʵ���������ݵķ�װ
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
