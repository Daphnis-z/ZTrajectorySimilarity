package com.adx.datahandler;

import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

/**
 * CSVReader
 * @author Agnes
 *用于读取轨迹csv文件的点的数据
 */
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
	
	//读取csv文件，1返回读取成功，0返回读取失败文件查找不到，-1所计算轨迹文件类型与输入不匹配
	@SuppressWarnings("resource")
	public int readFile(){
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line="";
			Point point=null;
			line=br.readLine();
			boolean fileIsTimeStampe=line.contains("时间");
			if(fileIsTimeStampe){
				if(timeStamp==0)
					return -1;
			}else{
				if(timeStamp==1)
					return -1;
			}
			
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
				}
			br.close();
			traj.isNA=false;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			if(readNAFile(file)){
				return 1;
			}else{
				return -1;
			}
		}catch(NoSuchElementException e){
			if(readNAFile(file)){
				return 1;
			}else{
				return -1;
			}
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
	private  boolean readNAFile(File file){
		Vector<Point> points=traj.getPoints();
		points.removeAllElements();
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line="";
			Point point=null;
			line=br.readLine();
			String buf="";
			while((line=br.readLine())!=null){
				double longitude=0,latitude=0;
				String time="";
				int attribute=0;
				buf="";
				int length=line.length();
				for(int i=0;i<length;i++){
					char tem=line.charAt(i);
					if(tem==','||i==length-1){
						if(i==length-1&&tem!=','){
							buf=buf+tem;
						}
						switch (attribute) {
						case 0:
							if(buf==""||buf==null){
								buf="0.0";
							}
							longitude=Double.parseDouble(buf);
							attribute=1;
							break;
						case 1:
							if(buf==""||buf==null){
								buf="0.0";
							}
							latitude=Double.parseDouble(buf);
							if(timeStamp==1){
								attribute=2;
							}
							break;
						case 2:
							time=buf;
							break;
						default:
							break;
						}
						buf="";
					}else{
					buf=buf+tem;
					}
				}
			if(timeStamp==1){
				point=new Point(longitude, latitude, time);
			}else{
				point=new Point(longitude, latitude);
				}
			traj.addPoint(point);
			}
			traj.isNA=true;
			br.close();	
		}catch (NumberFormatException e) {
				// TODO: handle exception
			e.printStackTrace();
			return false;
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return false;
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
