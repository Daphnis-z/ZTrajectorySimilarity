/**
 * @author Daphnis
 * 20160726
 */
package com.daphnis.dataHandle;
import java.io.*;
import java.util.Vector;
import com.adx.entity.Point;
import com.adx.entity.Trajectory;

public class ReadTaxiData {

	public static Vector<Trajectory> readTrajs(String fileName) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		BufferedReader read=new BufferedReader(new FileReader(fileName));		
		String str;
		int id=1;
		while((str=read.readLine())!=null){
			str=str.replaceAll("\\[|\\]| ", "");
			String[] strs=str.split(",");
			Trajectory traj=new Trajectory();
			traj.ID=id++;
			double lonMax=-2000,lonMin=2000;
			double latMax=-2000,latMin=2000;
			for(int i=0;i<strs.length-1&&i<198;i+=2){
				double lon=Double.parseDouble(strs[i]),
						lat=Double.parseDouble(strs[i+1]);
				lonMax=lonMax<lon? lon:lonMax;
				lonMin=lonMin>lon? lon:lonMin;
				latMax=latMax<lat? lat:latMax;
				latMin=latMin>lat? lat:latMin;
				Point pt=new Point(lon,lat);
				traj.addPoint(pt);
			}
			traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
			double a=lonMax-lonMin,b=latMax-latMin;
			traj.setTrajLen(a>b? a:b);
			trajs.addElement(traj);
		}
		read.close();
		
		return trajs;
	}
	
	public static void main(String[] args) throws IOException {
		Vector<Trajectory> trajs=ReadTaxiData.readTrajs("./src/daphnis/dataHandle/taxiData.txt");
		for(Trajectory traj:trajs)
			System.out.println(traj);
	}

}
