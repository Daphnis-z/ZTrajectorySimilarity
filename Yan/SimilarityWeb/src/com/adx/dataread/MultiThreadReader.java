/**
 * 
 */
package com.adx.dataread;

import java.util.ArrayList;
import java.util.List;

import com.adx.entity.Trajectory;

/**
 * file: MultiThreadReader.java
 * note: 一个用于读取轨迹文件的线程
 * author: Daphnis
 * date: 2016年8月19日 上午10:28:08
 */
public class MultiThreadReader extends Thread {
	private String[] files;
	private int start;
	private int end;
	public List<Trajectory> trajs=new ArrayList<Trajectory>();
	
	public MultiThreadReader(String[] files,int start,int end){
		this.files=files;
		this.start=start;
		this.end=end;
	}
	
	public void run(){
		try {
			for(int i=start;i<=end;++i){
				String sname=files[i];
				String str=ReadData.readATextFile(sname);
				Trajectory traj=ArrayReader.readArray(str.split("\n"));
				traj.name=sname.substring(sname.lastIndexOf('\\')+1);
				trajs.add(traj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
