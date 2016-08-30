/**
 * 读取测试数据
 * @author Daphnis
 * 20160727
 */
package com.adx.dataread;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Vector;

import com.adx.datahandler.DataHandler;
import com.adx.entity.*;
import com.daphnis.dataHandle.MultiThread;

/**
 * file: ReadData.java
 * note: 读取各种格式的轨迹文件
 * author: Daphnis
 * date: 2016年8月10日 下午3:18:00
 */
public class ReadData {
	/**
	 * 读取指定路径下所有的文件名
	 * @param path
	 * @return
	 */
	private static String[] getFilenames(String path){
		File dir=new File(path);
		String[] files=dir.list();
		return files;
	}
	
	/**
	 * 读取一个文本文件
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static String readATextFile(String path) throws FileNotFoundException, IOException {
		//读取整个轨迹文件
 		RandomAccessFile raf=new RandomAccessFile(path, "r");
 		FileChannel fchannel=raf.getChannel();
 		ByteBuffer buffer=ByteBuffer.allocate((int)raf.length());
 		fchannel.read(buffer);
 		fchannel.close();
 		buffer.flip();
 		String encoding = System.getProperty("file.encoding");
 	    CharBuffer cb=Charset.forName(encoding).decode(buffer); 
 	    return cb.toString();
	}


	/**
	 * 读取指定路径下给定数目的轨迹文件
	 * @param path
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static Vector<Trajectory> readSomeTrajs(String path,int num,int timestamp) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		String[] files=getFilenames(path);
		if(files==null){
			return null;
		}
		
		int id=0;
		for(String file:files){			
			File readFile=new File(path+file);
			CSVReader reader=new CSVReader(readFile,timestamp);
			int status_reader=reader.readFile();
			if (status_reader!=1){
				return null;
			}
			Trajectory traj=reader.getTraj();
			DataHandler handler=new DataHandler(traj);
			traj=handler.dataHandle();
			
			traj.ID=id++;
			trajs.addElement(traj);
			if(num>0&&trajs.size()>=num){
				break;
			}
		}
		return trajs;
	}
	
	/**
	 * 读取大量轨迹的缓存数据
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<Trajectory> readCacheData(String path) throws FileNotFoundException, IOException {
		List<Trajectory> trajs=new Vector<Trajectory>();			
		String[] strTrajs=readATextFile(path).split("\n");		
		int ts=Integer.parseInt(strTrajs[0]);		
		for(int ix=2;ix<strTrajs.length-1;ix+=2){
			Trajectory traj=new Trajectory();
			String[] strs=strTrajs[ix].split(",");
			int len=ts!=1? strs.length-1:strs.length-2;
			for(int i=0;i<len;){
				double lon=Double.parseDouble(strs[i]),lat=Double.parseDouble(strs[i+1]);
				Point pt;
				if(ts!=1){
					pt=new Point(lon,lat);
					i+=2;
				}else{
					pt=new Point(lon,lat,strs[i+2]); 
					i+=3;
				}
				traj.addPoint(pt);
			}
			setTrajAttr(traj, strTrajs[ix-1]);
			trajs.add(traj);
		}			
		return trajs;
	}
	/**
	 * 读取缓存文件
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<Trajectory> readCacheData(String path,SimularDef sd) throws IOException{
		List<Trajectory> trajs=new Vector<Trajectory>();
		BufferedReader read=new BufferedReader(new FileReader(path));
		String str=read.readLine();
		String[] line=str.split(","); 
		int ts=Integer.parseInt(line[line.length-1]);
		setSimularDef(line,ts,sd);
		while((str=read.readLine())!=null){
			String attrs=str;
			str=read.readLine();
			Vector<Point> points=new Vector<Point>();
			String[] strs=str.split(",");
			int pid=0;
			if(ts!=1){
				for(int i=0;i<strs.length-1;i+=2){
					Point pt=new Point(Double.parseDouble(strs[i]),Double.parseDouble(strs[i+1]));
					pt.pid=pid++;
					points.addElement(pt);
				}
			}else{
				for(int i=0;i<strs.length-2;i+=3){
					Point pt=new Point(Double.parseDouble(strs[i]),Double.parseDouble(strs[i+1]),strs[i+2]);
					pt.pid=pid++;
					points.addElement(pt);
				}
			}
			Trajectory traj=new Trajectory(points,ts);
			setTrajAttr(traj, attrs);
			trajs.add(traj);
		}
		read.close();
		return trajs;
	}
	/**
	 * 设置轨迹 属性
	 * @param traj
	 * @param line
	 */
	private static void setTrajAttr(Trajectory traj,String line) {
		String[] attrs=line.split(",");
		traj.name=attrs[0];
		traj.setCenterTraj(new Point(Double.parseDouble(attrs[1]), Double.parseDouble(attrs[2])));
		traj.setTrajLen(Double.parseDouble(attrs[3]));
	}
	private static void setSimularDef(String[] strs,int ts,SimularDef sd){
		sd.setDtwDis_W(Double.parseDouble(strs[0]));
		sd.setEditDis_W(Double.parseDouble(strs[1]));
		sd.setShapeSum_W(Double.parseDouble(strs[2]));
		sd.setTsum_W(Double.parseDouble(strs[3]));
		sd.setTimeStamp(ts);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		RandomAccessFile raf = new RandomAccessFile("a.txt", "rw");
		FileChannel fc = raf.getChannel();
		FileLock fLock=fc.tryLock();
		if(fLock.isValid()){
//			fc.lock();
			System.err.println("success");
//			raf.close();
		}
		MultiThread mThread=new MultiThread(new String[5], 0, 5);
		mThread.start();
		Thread.sleep(1000);
		System.err.println("wake..");
		raf.close();

	}
	
}

