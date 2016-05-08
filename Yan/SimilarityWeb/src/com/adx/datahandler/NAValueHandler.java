package com.adx.datahandler;

import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

/**
 * NAValueHandler
 * @author Agnes
 * 用于处理轨迹数据中的 空值 
 */
public class NAValueHandler {
	private Trajectory traj;
	
	public NAValueHandler(Trajectory traj) {
		// TODO Auto-generated constructor stub
		this.traj=traj;
	}
	//处理缺失值，成功返回true,失败返回false
	public boolean NAHandle(){
		Vector<Point> points=traj.getPoints();
		for(int i=0;i<points.size();i++){
			Point point=points.get(i);
			double latitude=point.getLatitude();
			double longtitude=point.getLongitude();
			if(latitude==0||longtitude==0){
				
			}
		}
		return true;
	}
}
