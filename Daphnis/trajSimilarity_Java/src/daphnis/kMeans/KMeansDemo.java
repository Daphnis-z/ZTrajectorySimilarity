package daphnis.kMeans;

import java.io.*;

public class KMeansDemo {

	public static void main(String[] args) throws IOException {
		String path="./src/daphnis/dataHandle/trajWithTime/坐标点信息2(含时间).csv";
		BufferedReader in=new BufferedReader(new FileReader(path));
		in.readLine();
		String s;
		Trajectory traj=new Trajectory();
		traj.hasTimestamp=true;
		while((s=in.readLine())!=null){
			String[] jw=s.split(",");
			traj.addPoint(new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]),jw[2]));
		}
		in.close();
		System.out.println(traj);
	}

}
