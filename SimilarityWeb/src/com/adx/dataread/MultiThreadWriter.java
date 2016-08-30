/**
 * 
 */
package com.adx.dataread;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.adx.datahandler.EigenvalueFilter;
import com.adx.entity.Constant;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

/**
 * file: MultiThreadWriter.java
 * note: �������ݼ��Ļ����ļ�
 * author: Daphnis
 * date: 2016��8��20�� ����9:54:28
 */
public class MultiThreadWriter extends Thread {
	private final int THREAD_NUM=11;
	private Trajectory objTraj;
	private SimularDef sd;
	private String trajsName;
	
	public MultiThreadWriter(Trajectory objTraj,SimularDef sd,String trajsName) {
		this.objTraj=objTraj;
		this.sd=sd;
		this.trajsName=trajsName;
	}
	
	/**
	 * ���̶߳�ȡ�������˵�����
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private List<Trajectory> readServerData(String path,Trajectory objTraj) 
			throws InterruptedException, IOException {					
		List<String> lfiles=GetFiles.getAllFile(path, ".csv");
		String[] files=lfiles.toArray(new String[0]);
		ExecutorService exe = Executors.newFixedThreadPool(THREAD_NUM);
		int fileNum=files.length/THREAD_NUM;
		int start=0,end=-1;
		MultiThreadReader[] threads=new MultiThreadReader[THREAD_NUM];
		int len=files.length;
		for(int i=0;i<THREAD_NUM;++i){
			start=end+1;
			end=(i==THREAD_NUM-1)? len-1:end+fileNum;
			threads[i]=new MultiThreadReader(files, start, end);
			exe.execute(threads[i]);
		}
	    exe.shutdown(); 	 	    
	    exe.awaitTermination(30, TimeUnit.MINUTES);//����ֱ���̳߳�����߳����
	    //�ϲ��켣����
	    List<Trajectory> trajs=threads[0].trajs;	    
	    for(int i=1;i<THREAD_NUM;++i){
		    trajs.addAll(trajs.size(),threads[i].trajs);
	    }	 
	    //��������
	    DataUpload.writeData(trajs, null, Constant.DATA_PATH+trajsName+".cache");
		return EigenvalueFilter.filtrateTraj(trajs,objTraj);			
	}
		
	public void run(){
		try {
			List<Trajectory> trajs=readServerData(Constant.DATA_PATH+trajsName+"/", objTraj);
			trajs.add(objTraj);
			DataUpload.writeData(trajs, sd, Constant.CACHE_FILE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
