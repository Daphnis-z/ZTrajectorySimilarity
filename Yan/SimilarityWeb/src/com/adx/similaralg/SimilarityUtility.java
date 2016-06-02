package com.adx.similaralg;

import com.adx.entity.Point;


/**
 * SimilarityUtility
 * @author Agnes
 *�������ƶȵĹ����࣬��һЩ�����ķ���
 */
public class SimilarityUtility {
	//�������켣��ŷʽ����
	public static double r_distance(Point p1,Point p2){//��ʵ����
		
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

	
	public static double min(double x,double y,double z)//��Сֵ
	{
		double t=x<y?x:y;
		return t<z?t:z;
	}
	
}
