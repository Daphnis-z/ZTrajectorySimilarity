/**
 * 读取测试数据
 * @author Daphnis
 * 20160727
 */
package com.daphnis.dataHandle;

import java.io.*;
import java.util.List;
import java.util.Vector;

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
	 * 从一个文件里读取一条轨迹数据
	 * 忽略时间戳
	 * @param obj
	 * @param type obj的类型（"String"或者"File"）
	 * @return
	 * @throws IOException
	 */
	public static Trajectory readATraj(Object obj,String type) throws IOException{
		Vector<Point> points=new Vector<Point>();		
		BufferedReader in;
		if(type.equals("File")){
			in=new BufferedReader(new FileReader((File)obj));
		}else{
			in=new BufferedReader(new FileReader((String)obj));
		}
		in.readLine();
		String s;
		int pid=0;
		double lonMax=-2000,lonMin=2000;
		double latMax=-2000,latMin=2000;
		while((s=in.readLine())!=null){
			String[] jw=s.split(",");
			Point p;
			try{
				if(jw.length<3){
					p=new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]));
				}else{
					p=new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]),jw[2]);
				}
			}catch(Exception e){
				continue;
			}
			p.pid=pid++;
			points.addElement(p);
			lonMax=lonMax<p.getLongitude()? p.getLongitude():lonMax;
			lonMin=lonMin>p.getLongitude()? p.getLongitude():lonMin;
			latMax=latMax<p.getLatitude()? p.getLatitude():latMax;
			latMin=latMin>p.getLatitude()? p.getLatitude():latMin;
			
			if(points.size()>=100){
				break;
			}
		}
		in.close();
		Trajectory traj=new Trajectory(points,-1);
		traj.setTimeStamp(traj.getPoints().get(0).getTimestamp()==null? 0:1);
		String name=obj.toString();
		traj.name=name.substring(name.lastIndexOf('/')+1);
		traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
		double a=lonMax-lonMin,b=latMax-latMin;
		traj.setTrajLen(a>b? a:b);

		return traj;
	}

	/**
	 * 读取指定路径下给定数目的轨迹文件
	 * @param path
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static Vector<Trajectory> readSomeTrajs(String path,int num) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		String[] files=getFilenames(path);
		if(files==null){
			return null;
		}
		
		int id=0;
		for(String file:files){
			Trajectory traj=readATraj(path+file,"String");
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
			if(ts!=1){
				for(int i=0;i<strs.length-1;i+=2){
					points.addElement(new Point(Double.parseDouble(strs[i]),Double.parseDouble(strs[i+1])));
				}
			}else{
				for(int i=0;i<strs.length-2;i+=3){
					points.addElement(new Point(Double.parseDouble(strs[i]),
							Double.parseDouble(strs[i+1]),strs[i+2]));
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
	
 	private static void geolife(Vector<String> vs) throws IOException{
		int num=1;
		for(String file:vs){
			BufferedReader in=new BufferedReader(new FileReader(file));
			for(int i=0;i<6;++i){
				in.readLine();
			}
			String s,outStr="经度,纬度\n";
			int cnt=0;
			while((s=in.readLine())!=null){
				if(++cnt>100){
					break;
				}
				try{
					String[] str=s.split(",");
					outStr+=str[1]+","+str[0]+"\n";
				}catch(Exception e){
					continue;
				}
			}
			in.close();
			String outFile="./geolife/traj"+num+".csv";
			++num;
			BufferedWriter write=new BufferedWriter(new FileWriter(outFile));
			write.write(outStr);
			write.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		List<Trajectory> trajs=readCacheData("./data/trajData/cache.csv");
		
//		String path="./src/com/daphnis/dataHandle/trajWithoutTime";
//		String[] files=getFilenames(path);
//		for(String fn:files){
//			fn=path+'/'+fn;
//			BufferedReader in=new BufferedReader(new FileReader(fn));
//			in.readLine();
//			String s;
//			while((s=in.readLine())!=null){
//				String[] jw=s.split(",");
//				System.out.println(Double.parseDouble(jw[0])+"\t"+Double.parseDouble(jw[1]));
//			}
//			in.close();
//		}
		
//		FileList fl=new FileList("Y:\\U\\Downloads\\Geolife Trajectories 1.3\\Data","geolife.txt");
//		fl.getList();
		
		Vector<String> vs=new Vector<String>();
		BufferedReader in=new BufferedReader(new FileReader("geolife.txt"));
		String s;
		while((s=in.readLine())!=null){
//			String str=s.substring(s.length()-3);
			if(s.substring(s.length()-3).equals("plt")){
				vs.addElement(s);
			}
		}
		in.close();
		geolife(vs);
	}

}

