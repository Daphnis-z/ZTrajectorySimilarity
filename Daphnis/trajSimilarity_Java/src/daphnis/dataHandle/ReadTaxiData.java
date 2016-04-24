package daphnis.dataHandle;

import java.io.*;
import java.util.Vector;
import daphnis.kMeans.*;

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
			traj.setId(id++);
			for(int i=0;i<strs.length-1;i+=2){
				Point pt=new Point(Double.parseDouble(strs[i]),Double.parseDouble(strs[i+1]));
				traj.addPoint(pt);
			}
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
