/**
 * 将轨迹转换成JS可读的字符串
 * @author Daphnis
 * 20160601
 */
package com.adx.gis;

import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

public class ShowTraj {
	
	/**
	 * 将轨迹转换成一个JS可读的字符串
	 * @param traj
	 * @return String
	 */
	public static String convertTraj(Trajectory traj){
		StringBuilder sb=new StringBuilder();
		for(Point p:traj.getPoints()){
			sb.append(","+p.getLongitude()+","+p.getLatitude());
		}
		return sb.substring(1);
	}
	
	/**
	 * 将多条轨迹转换成一个JS可读的字符串
	 * @param trajs
	 * @return String
	 */
	public static String convertSomeTrajs(Vector<Trajectory> trajs){
		StringBuilder sbs=new StringBuilder();
		for(Trajectory traj:trajs){
			StringBuilder sb=new StringBuilder();
			for(Point p:traj.getPoints()){
				sb.append(","+p.getLongitude()+","+p.getLatitude());
			}
			sbs.append('@'+sb.substring(1));
		}
		return sbs.substring(1);	
	}

}

