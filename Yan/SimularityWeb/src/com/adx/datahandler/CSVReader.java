package com.adx.datahandler;

import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

public class CSVReader {
	private File file;
	private Trajectory traj;
	private int timeStamp;
	
	public CSVReader(File file,int timeStamp){
		this.file=file;
		traj=new Trajectory(timeStamp);
		this.timeStamp=timeStamp;
	}
	
	public Trajectory getTraj() {
		return traj;
	}
	
	//读取csv文件，1返回读取成功，0返回读取失败
	public int readFile(){
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line="";
			Point point=null;
			line=br.readLine();
			while((line=br.readLine())!=null){
				StringTokenizer st=new StringTokenizer(line, ",");
				if(st.hasMoreTokens()){
					double longitude=Double.parseDouble(st.nextToken());
					double latitude=Double.parseDouble(st.nextToken());
					if(timeStamp==1){
						String time=st.nextToken();
						point=new Point(longitude, latitude, time);
					}else{
						point=new Point(longitude, latitude);
					}
				}
				traj.addPoint(point);
				System.out.println(point.getLatitude()+":"+point.getLongitude()+":"+point.getTimestamp());
				}
			br.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return 0;
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
}
