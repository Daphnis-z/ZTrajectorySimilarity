package com.adx.datahandler;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.adx.entity.Point;
import com.adx.entity.Constant;


/**
 * NAValueHandler
 * @author Agnes
 * 用于处理轨迹数据中的 空值 
 */
public class NAValueHandler {
	private Point point;
	private Point p1;
	private Point p2;
	
	public NAValueHandler(Point point,Point point1,Point point2) {
		// TODO Auto-generated constructor stub
		this.point=point;
		this.p1=point1;
		this.p2=point2;
	}
	//处理缺失值，成功返回true,失败返回false
	public Point NAHandle(int timestamps){
			double latitude=point.getLatitude();
			double longtitude=point.getLongitude();
			if((latitude==0.0&&longtitude==0.0)){
				return null;
			}
			//经或纬度 丢失,插值计算
			if((latitude==0.0||longtitude==0.0)){
				double k,b;
				if(p1.getLatitude()==p2.getLatitude()){
					k=0;b=p2.getLongitude();
				}else{
					k=(p2.getLongitude()-p1.getLongitude())/(p2.getLatitude()-p1.getLatitude());
					b=p2.getLongitude()-k*p2.getLatitude();
				}
				NumberFormat df=NumberFormat.getNumberInstance() ; 
				df.setMaximumFractionDigits(6); 
				if(latitude==0.0){
					if(k!=0){
						latitude=(longtitude-b)/k;		//纬度丢失
					}else{
						latitude=(p2.getLatitude()+p1.getLatitude())/2.0;
					}
				}else{
					longtitude=k*latitude+b;//经度丢失
				}
				point.setLatitude(Double.parseDouble(df.format(latitude)));
				point.setLongitude(Double.parseDouble(df.format(longtitude)));
			}
			if(1==timestamps){
				String time=point.getTimestamp();
					SimpleDateFormat sdf=new SimpleDateFormat(Constant.TIME_FORMAT);
					try {
						Date d1=sdf.parse(p1.getTimestamp());
						Date d2=sdf.parse(p2.getTimestamp());
						Calendar c=Calendar.getInstance();
						Date d3=new Date();
						d3.setTime((long) (d2.getTime()-d1.getTime()+d2.getTime()));
						c.setTime(d3);
						time=c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+
								" "+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				point.setTimestamp(time);
			}
		return point;
	}
}
