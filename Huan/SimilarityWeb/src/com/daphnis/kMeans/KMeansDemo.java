/**
 * KMeans Demo
 * @author Daphnis
 * 20160522
 */
package com.daphnis.kMeans;

import java.io.IOException;
import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.ReadData;

public class KMeansDemo {	
	private String strTrajs;	
	public String getStrTrajs() {
		return strTrajs;
	}
	public void setStrTrajs(String strTrajs) {
		this.strTrajs = strTrajs;
	}

	public String execute() throws IOException{
		Trajectory traj=ReadData.readATraj("./trajData/�������Ϣ1(��ʱ��).csv");
    	KMeans kmeans = new KMeans(traj.getPoints());
    	if(kmeans.init()){
	    	kmeans.calculate();
	    	kmeans.removeUnusefulPoints();	    	
	    	kmeans.dataCompression();
    	}
    	
    	showATraj(traj);
		return "success";
	}
		
	public void showATraj(Trajectory traj){
		StringBuilder sb=new StringBuilder();
		for(Point p:traj.getPoints()){
			sb.append(","+p.getLongitude()+","+p.getLatitude());
		}
		setStrTrajs(sb.substring(1));
	}
	
	public static void main(String[] args) throws IOException{
		KMeansDemo kmd=new KMeansDemo();
		kmd.execute();
		
	}
		
}
