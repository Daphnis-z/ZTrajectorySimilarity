/**
 * show trajectory with polyline on Baidu map
 * @author Daphnis
 * 20160510
 */
package com.adx.action;

import com.opensymphony.xwork2.Action;
import java.io.IOException;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.adx.resource.Constant;

public class TrajPolyline {
	private String strTrajs;	
	private int numTestTraj;	
	
	public void setNumTestTraj(int numTestTraj) {
		this.numTestTraj = numTestTraj;
	}
	public String getStrTrajs() {
		return strTrajs;
	}
	public void setStrTrajs(String strTrajs) {
		this.strTrajs = strTrajs;
	}

	/**
	 * show a trajectory
	 * @param traj
	 */
	public void showATraj(Trajectory traj){
		StringBuilder sb=new StringBuilder();
		for(Point p:traj.getPoints()){
			sb.append(","+p.getLongitude()+","+p.getLatitude());
		}
		setStrTrajs(sb.substring(1));
	}
	
	/**
	 * show some trajectories
	 * @param trajs
	 */
	public void showSomeTrajs(Vector<Trajectory> trajs){
		StringBuilder sbs=new StringBuilder();
		for(Trajectory traj:trajs){
			StringBuilder sb=new StringBuilder();
			System.out.println("points"+traj.getPoints());
			for(Point p:traj.getPoints()){
				sb.append(","+p.getLongitude()+","+p.getLatitude());
			}
			sbs.append('@'+sb.substring(1));
		}
		setStrTrajs(sbs.substring(1));	
	}
	
	/**
	 * œ‘ æπÏº£—› æ
	 * @throws IOException
	 */
	private void showTrajDemo() throws IOException{
		Trajectory traj1=Constant.objTraj;
		Trajectory traj2=Constant.testTraj;
		System.out.println("pattern:"+Constant.pattern);
		if(Constant.pattern==0){
			traj2=Constant.testTraj;
		}else{
			traj2=Constant.testTraj_more[numTestTraj];
			Vector<Point> points=traj2.getPoints();
			System.out.println("points.size:"+points.size());
		}
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		showSomeTrajs(vt);
	}
	
	public String execute() throws IOException{
		showTrajDemo();
		return Action.SUCCESS;
	}

}
