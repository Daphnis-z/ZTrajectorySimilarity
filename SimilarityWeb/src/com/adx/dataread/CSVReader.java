package com.adx.dataread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.StringTokenizer;

import com.adx.datahandler.NAValueHandler;
import com.adx.entity.Point;

/**
 * CSVReader
 * @author Agnes
 *用于读取轨迹csv文件的点的数据
 */
public class CSVReader extends MyFileReader {

	public CSVReader(File file, int timeStamp) {
		// TODO Auto-generated constructor stub
		super(file, timeStamp);
	}
	/**
	 * 读取轨迹文件，在读取同时进行缺失值处理，和特征值计算
	 * 读取csv文件，1返回读取成功，0返回读取失败文件查找不到，-1所计算轨迹文件类型与输入不匹配
	 * @return int
	 */
	@SuppressWarnings("resource")
	public int readFile(){
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line="";
			Point point=null;
			line=br.readLine();
			int status=parseFirstLine(line);
			if(status==-1){
				return status;
			}
			int pid=0;
			while((line=br.readLine())!=null){
				point=parseLine(line,status);
				if(point!=null){
					point.pid=pid++;
					traj.addPoint(point);
					lonMax=lonMax<point.getLongitude()? point.getLongitude():lonMax;
					lonMin=lonMin>point.getLongitude()? point.getLongitude():lonMin;
					latMax=latMax<point.getLatitude()? point.getLatitude():latMax;
					latMin=latMin>point.getLatitude()? point.getLatitude():latMin;
				}else{
					return 0;
				}
				if(traj.getSize()>=100){
					break;
				}
			}
			br.close();
			traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
			traj.setTrajLen(lonMax-lonMin);
			traj.name=file.getName();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	private  Point readNALine(String line,int status){
		Point point=null;
		double longitude=0,latitude=0;
		String time="",buf="";
		int attribute=1;//解析字符的顺序
		try{
			int length=line.length();
			for(int i=0;i<length;i++){
				char tem=line.charAt(i);
				if(tem==','||i==length-1){
					if(i==length-1&&tem!=','){
						buf=buf+tem;
					}
				switch (attribute) {
					case 1:
						if(buf==""||buf==null){
							buf="0.0";
						}
						if(status==1||status==11){
							longitude=Double.parseDouble(buf);
						}else{
							latitude=Double.parseDouble(buf);
						}
						attribute=2;
						break;
					case 2:
						if(buf==""||buf==null){
							buf="0.0";
						}
						if(status==1||status==11){
							latitude=Double.parseDouble(buf);
						}else{
							longitude=Double.parseDouble(buf);
						}
						if(timeStamp==1){
							attribute=3;
						}
						break;
					case 3:
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
				point=new Point(round(longitude), round(latitude), time);
			}else{
				point=new Point(round(longitude), round(latitude));
				}
		}catch (NumberFormatException e) {
				// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return point;
	}

	private int parseFirstLine(String line){//状态-格式  1：经度，纬度  11：经度，纬度，时间  2：纬度，经度  21：纬度，经度，时间 
		int status=1;
		boolean isTime;
		if(line.indexOf("经度")<line.indexOf("纬度")){
			status=1;
		}else{
			status=2;
		}
		if(line.contains("时间")){
			isTime=true;
			if(timeStamp==0)
				return -1;
		}else{
			isTime=false;
			if(timeStamp==1)
				return -1;
		}
		if(status==1&&isTime){
			status=11;
		}
		if(status==2&&isTime){
			status=21;
		}
		return status;
	}
	private Point parseLine(String line,int status){
		Point point=null;
		double longitude=0,latitude=0;
		String time="";
		StringTokenizer st=new StringTokenizer(line, ",");
		try {
			if(st.hasMoreTokens()){
				switch (status) {
				case 1:
					longitude=Double.parseDouble(st.nextToken());
					latitude=Double.parseDouble(st.nextToken());
					point=new Point(round(longitude), round(latitude));
					break;
				case 11:
					longitude=Double.parseDouble(st.nextToken());
					latitude=Double.parseDouble(st.nextToken());
					time=st.nextToken();
					point=new Point(round(longitude), round(latitude), time);
					break;
				case 2:
					latitude=Double.parseDouble(st.nextToken());
					longitude=Double.parseDouble(st.nextToken());
					point=new Point(round(longitude), round(latitude));
					break;
				case 21:
					latitude=Double.parseDouble(st.nextToken());
					longitude=Double.parseDouble(st.nextToken());
					time=st.nextToken();
					point=new Point(round(longitude), round(latitude), time);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			point=readNALine(line, status);
			int size=traj.getSize();
			if(point==null){
				return null;
			}
			if(size<2){
				return null;
			}
			NAValueHandler na=new NAValueHandler(point,traj.getPoints().get(size-1),
			traj.getPoints().get(size-2));
			point=na.NAHandle(timeStamp);
			return point; 
			
		}
		return point; 
	}
	public static double round(double x){
		NumberFormat df=NumberFormat.getNumberInstance() ; 
		df.setMaximumFractionDigits(6); 
		String tem=df.format(x);
		x=Double.parseDouble(tem);
		return x;
	}

}
