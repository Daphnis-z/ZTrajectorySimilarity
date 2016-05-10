/**
 * 读取官方测试数据
 * @author Daphnis
 * 20160510
 */
package com.daphnis.dataHandle;

import java.io.*;
import java.util.Vector;

import com.adx.entity.*;

public class ReadData {
	
	/**
	 * 从一个文件里读取一条轨迹数据
	 * @param fileName
	 * @return Trajectory
	 * @throws IOException
	 */
	public static Trajectory readATraj(String fileName) throws IOException{
		Vector<Point> points=new Vector<Point>();
		
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		in.readLine();
		String s;
		while((s=in.readLine())!=null){
			String[] jw=s.split(",");
			Point p=new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]));
			points.addElement(p);
		}
		in.close();
		
		return new Trajectory(points,-1);
	}

	public static String[] getFilenames(String path){
		File dir=new File(path);
		String[] files=dir.list();
		return files;
	}
	
	public static void main(String[] args) throws IOException {
		String path="./src/com/daphnis/dataHandle/trajWithoutTime";
		String[] files=getFilenames(path);
		for(String fn:files){
			fn=path+'/'+fn;
			BufferedReader in=new BufferedReader(new FileReader(fn));
			in.readLine();
			String s;
			while((s=in.readLine())!=null){
				String[] jw=s.split(",");
				System.out.println(Double.parseDouble(jw[0])+"\t"+Double.parseDouble(jw[1]));
			}
			in.close();
		}
	}

}