package com.adx.datahandler;

import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

/**
 * NAValueHandler
 * @author Agnes
 * ���ڴ���켣�����е� ��ֵ 
 */
public class NAValueHandler {
	private Trajectory traj;
	
	public NAValueHandler(Trajectory traj) {
		// TODO Auto-generated constructor stub
		this.traj=traj;
	}
	//����ȱʧֵ���ɹ�����true,ʧ�ܷ���false
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
