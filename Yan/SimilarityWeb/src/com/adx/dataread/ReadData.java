/**
 * 读取测试数据
 * @author Daphnis
 * 20160727
 */
package com.adx.dataread;

import java.io.*;
import java.util.List;
import java.util.Vector;

import com.adx.datahandler.DataHandler;
import com.adx.entity.*;

/**
 * file: ReadData.java
 * note: 读取各种格式的轨迹文件
 * author: Daphnis
 * date: 2016年8月10日 下午3:18:00
 */
public class ReadData {
	/**
	 * 读取指定路径下所有的文件名
	 * @param path
	 * @return
	 */
	private static String[] getFilenames(String path){
		File dir=new File(path);
		String[] files=dir.list();
		return files;
	}

	/**
	 * 读取指定路径下给定数目的轨迹文件
	 * @param path
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static Vector<Trajectory> readSomeTrajs(String path,int num,int timestamp) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		String[] files=getFilenames(path);
		if(files==null){
			return null;
		}
		
		int id=0;
		for(String file:files){
			
			File readFile=new File(path+file);
			CSVReader reader=new CSVReader(readFile,timestamp);
			int status_reader=reader.readFile();
			if (status_reader!=1){
				return null;
			}
			Trajectory traj=reader.getTraj();
			DataHandler handler=new DataHandler(traj);
			traj=handler.dataHandle();
			
			traj.ID=id++;
			trajs.addElement(traj);
			if(num>0&&trajs.size()>=num){
				break;
			}
		}
		return trajs;
	}
	
	/**
	 * 读取出租车数据
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Vector<Trajectory> readTaxiTrajs(String fileName) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		BufferedReader read=new BufferedReader(new FileReader(fileName));		
		String str;
		int id=0;
		while((str=read.readLine())!=null){
			str=str.replaceAll("\\[|\\]| ", "");
			String[] strs=str.split(",");
			Trajectory traj=new Trajectory();
			traj.ID=id++;
			double lonMax=-2000,lonMin=2000;
			double latMax=-2000,latMin=2000;
			for(int i=0;i<strs.length-1&&i<198;i+=2){
				double lon=Double.parseDouble(strs[i]),
						lat=Double.parseDouble(strs[i+1]);
				lonMax=lonMax<lon? lon:lonMax;
				lonMin=lonMin>lon? lon:lonMin;
				latMax=latMax<lat? lat:latMax;
				latMin=latMin>lat? lat:latMin;
				Point pt=new Point(lon,lat);
				traj.addPoint(pt);
			}
			traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
			double a=lonMax-lonMin,b=latMax-latMin;
			traj.setTrajLen(a>b? a:b);
			trajs.addElement(traj);
		}
		read.close();
		
		return trajs;
	}

	/**
	 * 读取缓存文件
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<Trajectory> readCacheData(String path,SimularDef sd) throws IOException{
		List<Trajectory> trajs=new Vector<Trajectory>();
		BufferedReader read=new BufferedReader(new FileReader(path));
		String str=read.readLine();
		String[] line=str.split(","); 
		int ts=Integer.parseInt(line[line.length-1]);
		setSimularDef(line,ts,sd);
		while((str=read.readLine())!=null){
			String tname=str;
			str=read.readLine();
			Vector<Point> points=new Vector<Point>();
			String[] strs=str.split(",");
			int pid=0;
			if(ts!=1){
				for(int i=0;i<strs.length-1;i+=2){
					Point pt=new Point(Double.parseDouble(strs[i]),Double.parseDouble(strs[i+1]));
					pt.pid=pid++;
					points.addElement(pt);
				}
			}else{
				for(int i=0;i<strs.length-2;i+=3){
					Point pt=new Point(Double.parseDouble(strs[i]),Double.parseDouble(strs[i+1]),strs[i+2]);
					pt.pid=pid++;
					points.addElement(pt);
				}
			}
			Trajectory traj=new Trajectory(points,ts);
			traj.name=tname;
			trajs.add(traj);
		}
		read.close();
		return trajs;
	}
	private static void setSimularDef(String[] strs,int ts,SimularDef sd){
		sd.setDtwDis_W(Double.parseDouble(strs[0]));
		sd.setEditDis_W(Double.parseDouble(strs[1]));
		sd.setShapeSum_W(Double.parseDouble(strs[2]));
		sd.setTsum_W(Double.parseDouble(strs[3]));
		sd.setTimeStamp(ts);
	}

}

