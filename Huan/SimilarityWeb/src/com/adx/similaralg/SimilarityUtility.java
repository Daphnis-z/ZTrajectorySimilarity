package com.adx.similaralg;

import com.adx.entity.Point;


/**
 * SimilarityUtility
 * @author Agnes
 *计算相似度的工具类，有一些公共的方法
 */
public class SimilarityUtility {
	//计算两轨迹的欧式距离
	public static double r_distance(Point p1,Point p2){//真实距离
		
		double alat=Math.abs(p1.getLatitude()*Math.PI/180.0-p2.getLatitude()*Math.PI/180.0);
		double alon=Math.abs(p1.getLongitude()*Math.PI/180.0-p2.getLongitude()*Math.PI/180.0);
		double h=HaverSin(alat) + Math.cos(p1.getLatitude()*Math.PI/180.0) * Math.cos(p2.getLatitude()*Math.PI/180.0) * HaverSin(alon);
		double ans=2*6378.137*Math.asin(Math.sqrt(h));
		return ans;
	}
	public static double HaverSin(double theta)
	  {
	      double v = Math.sin(theta/2.0);
	      return v * v;
	  }

	
	public static double min(double x,double y,double z)//最小值
	{
		double t=x<y?x:y;
		return t<z?t:z;
	}
	
}
