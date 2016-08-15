package com.adx.dataread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.adx.datahandler.NAValueHandler;
import com.adx.entity.Point;

/**
 * CSVReader
 * @author Agnes
 *���ڶ�ȡ�켣csv�ļ��ĵ������
 */
public class CSVReader extends MyFileReader {

	public CSVReader(File file, int timeStamp) {
		// TODO Auto-generated constructor stub
		super(file, timeStamp);
	}
	//��ȡcsv�ļ���1���ض�ȡ�ɹ���0���ض�ȡʧ���ļ����Ҳ�����-1������켣�ļ����������벻ƥ��
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
		int attribute=1;//�����ַ���˳��
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
						if(status==1||status==1){
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
						if(status==1||status==1){
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
				point=new Point(longitude, latitude, time);
			}else{
				point=new Point(longitude, latitude);
				}
		}catch (NumberFormatException e) {
				// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return point;
	}

	private int parseFirstLine(String line){//״̬-��ʽ  1�����ȣ�γ��  11�����ȣ�γ�ȣ�ʱ��  2��γ�ȣ�����  21��γ�ȣ����ȣ�ʱ�� 
		int status=1;
		boolean isTime;
		if(line.indexOf("����")<line.indexOf("γ��")){
			status=1;
		}else{
			status=2;
		}
		if(line.contains("ʱ��")){
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
					point=new Point(longitude, latitude);
					break;
				case 11:
					longitude=Double.parseDouble(st.nextToken());
					latitude=Double.parseDouble(st.nextToken());
					time=st.nextToken();
					point=new Point(longitude, latitude, time);
					break;
				case 2:
					latitude=Double.parseDouble(st.nextToken());
					longitude=Double.parseDouble(st.nextToken());
					point=new Point(longitude, latitude);
					break;
				case 21:
					latitude=Double.parseDouble(st.nextToken());
					longitude=Double.parseDouble(st.nextToken());
					time=st.nextToken();
					point=new Point(longitude, latitude, time);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			point=readNALine(line, status);
			int size=traj.getSize();
			NAValueHandler na=new NAValueHandler(point,traj.getPoints().get(size-1),
									traj.getPoints().get(size-2));
			point=na.NAHandle(timeStamp);
			return point; 
		}
		return point; 
	}
}
