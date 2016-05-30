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
//		String rootPath="Y:/PROGRAMMING/others/ZTrajectorySimilarity/Daphnis/SimularityWeb";
//		Trajectory traj=ReadData.readATraj(rootPath+"/src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息3(不含时间).csv");
//		showATraj(traj);
		
		Trajectory traj1=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithTime/坐标点信息2(含时间).csv"),
				traj2=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息3(不含时间).csv");
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		showSomeTrajs(vt);
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		CSVReader objReader=new CSVReader(trajFile,-1);
		objReader.readFile();
		Trajectory objTraj=objReader.getTraj();
		showATraj(objTraj);

		return super.execute();
	}
		
}

