package com.adx.datahandler;

import java.io.File;
import java.util.ArrayList;

import com.adx.entity.Trajectory;

/**
 * FileDerecterReader
 * @author Agnes
 *���ļ����ж�ȡ�ļ��ľ���·��
 */
public class FileDerecterReader {
	private String path="";
	private ArrayList<String> filePath;
	public ArrayList<String> fileName;
	private Trajectory[] trajGroup;
	private int timeStamp;

	public FileDerecterReader(String path,int timeStamp) {
		// TODO Auto-generated constructor stub
		this.path=path;
		filePath=new ArrayList<String>();
		fileName=new ArrayList<String>();
		this.timeStamp=timeStamp;
	}
	
	public Trajectory[] getTrajGroup() {
		return trajGroup;
	}
	
	//��������ļ���·�����ɹ�����true��ʧ�ܷ���false;
	private boolean getAllFilePath(){
		File rootDir=new File(path);
		File[] fs=rootDir.listFiles();
		if(fs==null){
			return false;
		}
		for(int i=0;i<fs.length;i++){
			String tem=fs[i].getAbsolutePath();
			fileName.add(fs[i].getName());
			if(fs[i].isDirectory()){
				return false;
			}
			filePath.add(tem);
		}
		return true;
	}
	//��ȡcsv�ļ���1���ض�ȡ�ɹ���0���ض�ȡʧ���ļ����Ҳ�����-1������켣�ļ����������벻ƥ��
	public int readAllFile(){
		if(!getAllFilePath()){
			return -1; //-1������켣�ļ����������벻ƥ��
		}
		trajGroup=new Trajectory[filePath.size()];
		int status=1;
		int tid=0;
		for (int i=0;i<filePath.size();i++){
			File file=new File(filePath.get(i));
			CSVReader reader=new CSVReader(file, timeStamp);
			status=reader.readFile();
			if(status!=1){
				return status;
			}
			Trajectory traj=reader.getTraj();
			traj.ID=tid++;
			trajGroup[i]=traj;
		}
		return status;
	}
	
	 
	
}
