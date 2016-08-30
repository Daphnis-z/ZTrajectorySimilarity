package com.adx.dataread;

import java.text.NumberFormat;
import java.util.StringTokenizer;

import com.adx.datahandler.DataHandler;
import com.adx.datahandler.NAValueHandler;
import com.adx.entity.Point;
import com.adx.entity.Trajectory;

public class ArrayReader {
	
	/**
	 * 读取轨迹文件，在读取同时进行缺失值处理，和特征值计算
	 * 读取数组文件，读取成功轨迹，读取失败返回null
	 * @return 
	 */
	public static Trajectory readArray(String[] array){
		if(array.length<0){
			return null;
		}
		Trajectory traj=new Trajectory();
		double lonMax=-2000,lonMin=2000;
		double latMax=-2000,latMin=2000;
		try {
			String line="";
			Point point=null;
			line=array[0];
			int status=parseFirstLine(line);
//			System.out.println(status);
			if(status==-1){
				return null;
			}
			int pid=0;
			for(int i=1;i<array.length;i++){
				line=array[i];
				point=parseLine(line,status);
				if(point==null){
					int size=traj.getSize();
					if(size>2){
						point=readNALine(line, status);
						NAValueHandler na=new NAValueHandler(point,traj.getPoints().get(size-1),
												traj.getPoints().get(size-2));
						int timeStamp=0;
						if(status==11||status==21){
							timeStamp=1;
						}
						point=na.NAHandle(timeStamp);
					}
				}
				if(point!=null){
					point.pid=pid++;
					traj.addPoint(point);
					lonMax=lonMax<point.getLongitude()? point.getLongitude():lonMax;
					lonMin=lonMin>point.getLongitude()? point.getLongitude():lonMin;
					latMax=latMax<point.getLatitude()? point.getLatitude():latMax;
					latMin=latMin>point.getLatitude()? point.getLatitude():latMin;
				}
				if(traj.getSize()>=100){
					break;
				}
			}
			traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
			traj.setTrajLen(lonMax-lonMin);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		DataHandler handler=new DataHandler(traj);
		traj=handler.dataHandle();
		return traj;
	}
	private static  Point readNALine(String line,int status){
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
						if(status==11||status==21){
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
			if(status==11||status==21){
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

	private static int parseFirstLine(String line){//状态-格式  1：经度，纬度  11：经度，纬度，时间  2：纬度，经度  21：纬度，经度，时间 
		int status=1;
		boolean isTime;
//		System.out.println("line[0]"+line);
//		System.out.println("index:longtitude::"+line.indexOf("经度"));
//		System.out.println("index:latitude::"+line.indexOf("纬度"));
		if(line.indexOf("经度")<line.indexOf("纬度")){
			status=1;
		}else{
			status=2;
		}
		if(line.contains("时间")){
			isTime=true;
		}else{
			isTime=false;
		}
		if(status==1&&isTime){
			status=11;
		}
		if(status==2&&isTime){
			status=21;
		}
		return status;
	}
	private static Point parseLine(String line,int status){
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
			return null;
		}
		return point; 
	}
	private static double round(double x){
		NumberFormat df=NumberFormat.getNumberInstance() ; 
		df.setMaximumFractionDigits(6); 
		String tem=df.format(x);
		x=Double.parseDouble(tem);
		return x;
	}
}
