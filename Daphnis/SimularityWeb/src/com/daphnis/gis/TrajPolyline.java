/**
 * show trajectory with polyline on Baidu map
 * @author Daphnis
 * 20160510
 */
package com.daphnis.gis;

import com.opensymphony.xwork2.Action;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.ReadData;

public class TrajPolyline {
	private String strTrajs;	
	
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
			for(Point p:traj.getPoints()){
				sb.append(","+p.getLongitude()+","+p.getLatitude());
			}
			sbs.append('@'+sb.substring(1));
		}
		setStrTrajs(sbs.substring(1));	
	}
	
	/**
	 * 显示轨迹演示
	 * @throws IOException
	 */
	private void showTrajDemo() throws IOException{
//		File dir=new File("");
//		System.out.println(dir.getCanonicalPath());
//		System.out.println(dir.getAbsolutePath());
		
//		String rootPath="Y:/PROGRAMMING/others/ZTrajectorySimilarity/Daphnis/SimularityWeb";
//		Trajectory traj=ReadData.readATraj(rootPath+"/src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息3(不含时间).csv");
//		showATraj(traj);
		
		String rootPath="Y:/PROGRAMMING/others/ZTrajectorySimilarity/Daphnis/SimularityWeb/src/com/daphnis/dataHandle/trajWithoutTime";
		Trajectory traj1=ReadData.readATraj(rootPath+"/坐标点信息2(不含时间).csv"),
				traj2=ReadData.readATraj(rootPath+"/坐标点信息3(不含时间).csv");
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
