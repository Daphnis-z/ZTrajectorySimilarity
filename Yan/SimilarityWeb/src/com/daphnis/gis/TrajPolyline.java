/**
 * show trajectory with polyline on Baidu map
 * @author Daphnis
 * 20160510
 */
package com.daphnis.gis;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.adx.datahandler.CSVReader;
import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.ReadData;

@SuppressWarnings("serial")
public class TrajPolyline extends ActionSupport{
	private String strTrajs;		
	public String getStrTrajs() {
		return strTrajs;
	}
	public void setStrTrajs(String strTrajs) {
		this.strTrajs = strTrajs;
	}

	private File trajFile;	
	public File getTrajFile() {
		return trajFile;
	}
	public void setTrajFile(File trajFile) {
		this.trajFile = trajFile;
	}
	
	private String subtraj;	
	public String getSubtraj() {
		return subtraj;
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
			for(Point p:traj.getPoints()){
				sb.append(","+p.getLongitude()+","+p.getLatitude());
			}
			sbs.append('@'+sb.substring(1));
		}
		setStrTrajs(sbs.substring(1));	
	}
		
	@Override
	public String execute() throws Exception {
		if(trajFile!=null){
			CSVReader objReader=new CSVReader(trajFile,-1);
			objReader.readFile();
			Trajectory objTraj=objReader.getTraj();
			showATraj(objTraj);
						
			//相似度最高的轨迹段测试		
			subtraj="3,7";
		}

		return super.execute();
	}
		
}

