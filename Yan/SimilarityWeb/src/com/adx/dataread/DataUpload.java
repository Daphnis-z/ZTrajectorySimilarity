package com.adx.dataread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.adx.datahandler.DataHandler;
import com.adx.datahandler.EigenvalueFilter;
import com.adx.datahandler.KMeans;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.ReadData;

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
	 */
	public static int saveData(File[] files,String[] testName,File objfile,String objName,SimularDef sd){
		Trajectory objTraj = null;
		try{
			CSVReader objReader=new CSVReader(objfile, sd.getTimeStamp());
			int status_obj=objReader.readFile();
			if(status_obj==1){
				objTraj=dataPreprocessing(objReader.getTraj());
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
				if(status_test==1){
					Trajectory testTraj=dataPreprocessing(testReader.getTraj());
					DataHandler obj_handler=new DataHandler(testTraj);
					testTraj=obj_handler.dataHandle();
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
		writeData(trajs,sd);
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
				traj.name=objName;
				List<Trajectory> trajs=new ArrayList<Trajectory>();
				trajs.add(traj);
				writeData(trajs,sd);
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
	 * 使用KMeans进行数据预处理
	 * @param traj
	 * @return
	 */
	private static Trajectory dataPreprocessing(Trajectory traj){
    	KMeans kmeans = new KMeans(traj.getPoints());
    	if(kmeans.init()){
	    	kmeans.calculate();
	    	kmeans.removeUnusefulPoints();	    	
	    	kmeans.dataCompression();
    	}
    	return traj;
	}
	/**
	 * 将数据写入文件
	 * @param trajs
	 */
	private static void writeData(List<Trajectory> trajs,SimularDef sd){
		try{
			confirmCacheFile();
			BufferedWriter write=new BufferedWriter(new FileWriter(SAVE_PATH));
			int ts=trajs.get(0).getTimeStamp();
			write.write(String.format("%f,%f,%f,%f,%d", sd.getDtwDis_W(),
					sd.getEditDis_W(),sd.getShapeSum_W(),sd.getTsum_W(),ts));
			if(ts==1){
				for(Trajectory traj:trajs){
					write.newLine();
					write.write(traj.name);
					write.newLine();
					StringBuilder sb=new StringBuilder("");
					Vector<Point> points=traj.getPoints();
					for(Point p:points){
						sb.append(String.format(",%f,%f,%s", p.getLongitude(),p.getLatitude(),p.getTimestamp()));
					}
					String s=sb.toString();
					write.write(s.substring(1));
				}
			}else{
				for(Trajectory traj:trajs){
					write.newLine();
					write.write(traj.name);
					write.newLine();
					StringBuilder sb=new StringBuilder("");
					Vector<Point> points=traj.getPoints();
					for(Point p:points){
						sb.append(String.format(",%f,%f", p.getLongitude(),p.getLatitude()));
					}
					String s=sb.toString();
					write.write(s.substring(1));
				}
			}
			write.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查缓存文件是否存在，不存在则创建
	 * @throws IOException
	 */
	private static void confirmCacheFile() throws IOException{
		int ix=SAVE_PATH.lastIndexOf("/");
		String dir=SAVE_PATH.substring(0,ix+1);
		File f=new File(dir);
		if(!f.exists()){
			f.mkdirs();
		}
		f=new File(SAVE_PATH.substring(ix+1));
		if(!f.exists()){
			f.createNewFile();
		}
	}
}


