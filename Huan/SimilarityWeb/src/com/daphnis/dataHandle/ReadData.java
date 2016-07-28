/**
 * ��ȡ��������
 * @author Daphnis
 * 20160727
 */
package com.daphnis.dataHandle;

import java.io.*;
import java.util.Vector;

import com.adx.entity.*;

public class ReadData {
	/**
	 * ��ȡָ��·�������е��ļ���
	 * @param path
	 * @return
	 */
	private static String[] getFilenames(String path){
		File dir=new File(path);
		String[] files=dir.list();
		return files;
	}

	/**
	 * ��һ���ļ����ȡһ���켣����
	 * ����ʱ���
	 * @param fileName
	 * @return Trajectory
	 * @throws IOException
	 */
	public static Trajectory readATraj(String fileName) throws IOException{
		Vector<Point> points=new Vector<Point>();		
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		in.readLine();
		String s;
		int pid=0;
		double lonMax=-2000,lonMin=2000;
		double latMax=-2000,latMin=2000;
		while((s=in.readLine())!=null){
			String[] jw=s.split(",");
			Point p;
			try{
				p=new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]));
			}catch(Exception e){
				continue;
			}
			p.pid=pid++;
			points.addElement(p);
			lonMax=lonMax<p.getLongitude()? p.getLongitude():lonMax;
			lonMin=lonMin>p.getLongitude()? p.getLongitude():lonMin;
			latMax=latMax<p.getLatitude()? p.getLatitude():latMax;
			latMin=latMin>p.getLatitude()? p.getLatitude():latMin;
			
			if(points.size()==100){
				break;
			}
		}
		in.close();
		Trajectory traj=new Trajectory(points,-1);
		traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
		double a=lonMax-lonMin,b=latMax-latMin;
		traj.setTrajLen(a>b? a:b);

		return traj;
	}

	/**
	 * ��ȡָ��·���µ����й켣�ļ�
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Vector<Trajectory> readSomeTrajs(String path) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		String[] files=getFilenames(path);
		if(files==null){
			return null;
		}
		
		int id=0;
		for(String file:files){
			Trajectory traj=readATraj(path+file);
			traj.ID=id++;
			trajs.addElement(traj);
		}
		return trajs;
	}
	
	/**
	 * ��ȡ���⳵����
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

	
	public static void main(String[] args) throws IOException {
		String path="./src/com/daphnis/dataHandle/trajWithoutTime";
		String[] files=getFilenames(path);
		for(String fn:files){
			fn=path+'/'+fn;
			BufferedReader in=new BufferedReader(new FileReader(fn));
			in.readLine();
			String s;
			while((s=in.readLine())!=null){
				String[] jw=s.split(",");
				System.out.println(Double.parseDouble(jw[0])+"\t"+Double.parseDouble(jw[1]));
			}
			in.close();
		}
	}

}
