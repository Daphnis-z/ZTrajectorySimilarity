package com.adx.datahandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;


/**
 * NAValueHandler
 * @author Agnes
 * ���ڴ���켣�����е� ��ֵ 
 */
public class NAValueHandler {
	private Trajectory traj;
	private Vector<Point> points;
	
	public NAValueHandler(Trajectory traj) {
		// TODO Auto-generated constructor stub
		this.traj=traj;
	}
	//����ȱʧֵ���ɹ�����true,ʧ�ܷ���false
	public boolean NAHandle(){
	    points=traj.getPoints();
		for(int i=0;i<points.size();i++){
			Point point=points.get(i);
			Point nPoint;
			double latitude=point.getLatitude();
			double longtitude=point.getLongitude();
			if(latitude==0.0&&longtitude==0.0){
				points.remove(i);		//��γ��ͬʱ��ʧ,�����õ�
			}
			//����γ�� ��ʧ,��ֵ����
			if(latitude==0.0||longtitude==0.0){
				double k,b;
				Point p1=points.get(i-2);
				Point p2=points.get(i-1);
				if(p1.getLatitude()==p2.getLatitude()){
					k=0;b=p2.getLongitude();
				}else{
					k=(p2.getLongitude()-p1.getLongitude())/(p2.getLatitude()-p1.getLatitude());
					b=p2.getLongitude()-k*p2.getLatitude();
				}
				if(latitude==0.0){
					if(k!=0){
						latitude=(longtitude-b)/k;		//γ�ȶ�ʧ
					}else{
						latitude=(p2.getLatitude()+p1.getLatitude())/2.0;
					}
					System.out.println("latitude_NA:"+latitude);
				}else{
					longtitude=k*latitude+b;//���ȶ�ʧ
					System.out.println("longtitude:"+longtitude);
				}
				}
			if(traj.timeStamp==1){
				String time=point.getTimestamp();
				if(time==null||time.equals("")){
					SimpleDateFormat sdf=new SimpleDateFormat(Constant.TIME_FORMAT);
					try {
						Date d1=sdf.parse(points.get(i-2).getTimestamp());
						Date d2=sdf.parse(points.get(i-1).getTimestamp());
						Calendar c=Calendar.getInstance();
						Date d3=new Date();
						d3.setTime((long) (d2.getTime()-d1.getTime()+d2.getTime()));
						c.setTime(d3);
						time=c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+
								" "+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
						System.out.println("time:"+time);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
				 nPoint=new Point(longtitude, latitude, time);
			}else{
				 nPoint=new Point(longtitude, latitude);
			}
			points.set(i, nPoint);
		}
		return true;
	}
	public Trajectory getTraj(){
		traj.setPoints(points);
		return traj;
	}
}
