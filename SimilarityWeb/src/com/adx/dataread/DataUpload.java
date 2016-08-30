package com.adx.dataread;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.adx.datahandler.DataHandler;
import com.adx.datahandler.EigenvalueFilter;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.util.FileAbout;

/**
 * file: DataUpload.java
 * note: 处理上传的轨迹数据
 * author: Daphnis
 * date: 2016年8月10日 下午3:02:35
 */
public class DataUpload {
	public final static String SAVE_PATH="./data/trajData/cache.csv";
	
	/**
	 * 保存上传的目标轨迹、测试轨迹群、权重数据
	 * @param files
	 * @param filesName
	 * @param objfile
	 * @param objName
	 * @param sd
	 * @return
	 * @throws IOException 
	 */
	public static int saveData(File[] files,String[] testName,File objfile,String objName,SimularDef sd) throws IOException{
		Trajectory objTraj = null;
		try{
			CSVReader objReader=new CSVReader(objfile, sd.getTimeStamp());
			int status_obj=objReader.readFile();
			if(status_obj==1){
				objTraj=objReader.getTraj();
				DataHandler obj_handler=new DataHandler(objTraj);
				objTraj=obj_handler.dataHandle();
				objTraj.name=objName;
			}else{
				return status_obj;
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		List<Trajectory> trajs=new ArrayList<Trajectory>();
		for(int i=0;i<files.length;++i){
			try{
				CSVReader testReader=new CSVReader(files[i],sd.getTimeStamp());
				int status_test=testReader.readFile();
				Trajectory testTraj=testReader.getTraj();
				if(status_test==1){
					DataHandler test_handler=new DataHandler(testTraj);
					testTraj=test_handler.dataHandle();
					testTraj.name=testName[i];
					trajs.add(testTraj);
				}else{
					return status_test;
				}
			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}
		}
		trajs=EigenvalueFilter.filtrateTraj(trajs, objTraj);
		
		//将数据保存到临时文件里
		trajs.add(objTraj);
		writeData(trajs,sd,SAVE_PATH);
		return 1;
	}
	
	/**
	 * 缓存目标轨迹和权重
	 * @param objfile
	 * @param objName
	 * @param sd
	 * @return
	 */
	public static int saveObjectTraj(File objfile,String objName,SimularDef sd){		
		try{
			CSVReader objReader=new CSVReader(objfile, sd.getTimeStamp());
			int status_obj=objReader.readFile();
			if(status_obj==1){
				Trajectory traj=objReader.getTraj();
				DataHandler obj_handler=new DataHandler(traj);
				traj=obj_handler.dataHandle();
				traj.name=objName;
				List<Trajectory> trajs=new ArrayList<Trajectory>();
				trajs.add(traj);
				writeData(trajs,sd,SAVE_PATH);
			}else{
				return status_obj;
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}		
		
		return 1;
	}
	
	/**
	 * 将数据写入文件
	 * @param trajs
	 * @throws IOException 
	 */
	public static void writeData(List<Trajectory> trajs,SimularDef sd,String path) throws IOException{
		FileAbout.confirmDir(path.substring(0,path.lastIndexOf('/')+1));
		int ts=trajs.get(0).getTimeStamp();
		StringBuilder strdata=new StringBuilder("");
		if(ts==1){
			for(Trajectory traj:trajs){
				StringBuilder sb=new StringBuilder("");
				Vector<Point> points=traj.getPoints();
				for(Point p:points){
					sb.append(String.format(",%f,%f,%s", p.getLongitude(),p.getLatitude(),p.getTimestamp()));
				}
				String str=String.format("%s,%f,%f,%f", traj.name,traj.getCenterTraj().getLongitude(),
						traj.getCenterTraj().getLatitude(),traj.getTrajLen());
				strdata.append("\n"+str+"\n"+sb.substring(1));
			}
		}else{
			for(Trajectory traj:trajs){
				StringBuilder sb=new StringBuilder("");
				Vector<Point> points=traj.getPoints();
				for(Point p:points){
					sb.append(String.format(",%f,%f", p.getLongitude(),p.getLatitude()));
				}
				String str=String.format("%s,%f,%f,%f", traj.name,traj.getCenterTraj().getLongitude(),
						traj.getCenterTraj().getLatitude(),traj.getTrajLen());
				strdata.append("\n"+str+"\n"+sb.substring(1));
			}
		}		
		String outData=strdata.toString();
		if(sd!=null){
			String s=String.format("%f,%f,%f,%f,%d", sd.getDtwDis_W(),
					sd.getEditDis_W(),sd.getShapeSum_W(),sd.getTsum_W(),ts);
			outData=s+outData;
		}else{
			outData=ts+outData;
		}		
		
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}		
		RandomAccessFile raf = new RandomAccessFile(path, "rw");
		FileChannel fc = raf.getChannel();
		fc.tryLock();
		fc.write(ByteBuffer.wrap(outData.getBytes()));
		raf.close();
	}
		
}


