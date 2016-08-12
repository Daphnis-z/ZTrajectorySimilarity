package com.daphnis.dataHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.daphnis.kMeans.KMeans;
import com.daphnis.trajFilter.EigenvalueFilter;

/**
 * file: DataUpload.java
 * note: �����ϴ��Ĺ켣����
 * author: Daphnis
 * date: 2016��8��10�� ����3:02:35
 */
public class DataUpload {
	public final static String SAVE_PATH="./data/trajData/cache.csv";
	
	/**
	 * �����ϴ���Ŀ��켣�����Թ켣Ⱥ��Ȩ������
	 * @param files
	 * @param filesName
	 * @param objfile
	 * @param objName
	 * @param sd
	 * @return
	 */
	public static boolean saveData(File[] files,String[] filesName,File objfile,String objName,SimularDef sd){
		Trajectory objtraj;
		try{
			objtraj=dataPreprocessing(ReadData.readATraj(objfile, "File"));
			objtraj.name=objName;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		List<Trajectory> trajs=new ArrayList<Trajectory>();
		for(int i=0;i<files.length;++i){
			try{
				Trajectory traj=dataPreprocessing(ReadData.readATraj(files[i], "File"));
				traj.name=filesName[i];
				trajs.add(traj);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		trajs=EigenvalueFilter.filtrateTraj(trajs, objtraj);
		
		//�����ݱ��浽��ʱ�ļ���
		trajs.add(objtraj);
		writeData(trajs,sd);
		return true;
	}
	
	/**
	 * ����Ŀ��켣��Ȩ��
	 * @param objfile
	 * @param objName
	 * @param sd
	 * @return
	 */
	public static boolean saveObjectTraj(File objfile,String objName,SimularDef sd){
		try{
			Trajectory traj=dataPreprocessing(ReadData.readATraj(objfile, "File"));
			traj.name=objName;
			List<Trajectory> trajs=new ArrayList<Trajectory>();
			trajs.add(traj);
			writeData(trajs,sd);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	/**
	 * ʹ��KMeans��������Ԥ����
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
	 * ������д���ļ�
	 * @param trajs
	 */
	private static void writeData(List<Trajectory> trajs,SimularDef sd){
		try{
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
}


