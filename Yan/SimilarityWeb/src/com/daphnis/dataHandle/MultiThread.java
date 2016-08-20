/**
 * 
 */
package com.daphnis.dataHandle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import com.adx.entity.Trajectory;

/**
 * file: MultiThread.java
 * note: java
 * author: Daphnis
 * date: 2016年8月18日 下午12:23:50
 */
public class MultiThread extends Thread {
	private String[] files;
	private int start;
	private int end;
	public List<Trajectory> trajs=new ArrayList<Trajectory>();
	
	public MultiThread(String[] files,int start,int end){
		this.files=files;
		this.start=start;
		this.end=end;
	}
	
	public void run(){		
		try {
//			for(int i=start;i<=end;++i){
//				trajs.add(ReadData.readATraj(files[i]));
//			}
			
			BufferedWriter writer=new BufferedWriter(new FileWriter("a.txt"));
			for(int i=0;i>-1;++i){
				writer.write(""+(i+1));
				writer.newLine();
			}
			
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean fileLocked(String file) {
		try{
			BufferedReader reader=new BufferedReader(new FileReader(file));
			System.err.println(reader.readLine());
			reader.close();
			return false;
		}catch(Exception e){
			return true;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
