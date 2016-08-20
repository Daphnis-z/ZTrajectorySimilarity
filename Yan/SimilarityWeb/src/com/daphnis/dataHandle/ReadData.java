/**
 * 读取测试数据
 * @author Daphnis
 * 20160727
 */
package com.daphnis.dataHandle;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.adx.dataread.DataUpload;
import com.adx.entity.*;

/**
 * file: ReadData.java
 * note: 读取各种格式的轨迹文件
 * author: Daphnis
 * date: 2016年8月10日 下午3:18:00
 */
public class ReadData {
	private static String PATH="./data/trajData/geolife/";
	
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
	 * 从一个文件里读取一条轨迹数据
	 * 忽略时间戳
	 * @param obj
	 * @param type obj的类型（"String"或者"File"）
	 * @return
	 * @throws IOException
	 */
	public static Trajectory readATraj(String path) throws IOException{
		String[] lines = readATextFile(path).split("\n");
 	    
		Vector<Point> points=new Vector<Point>();
		double lonMax=-2000,lonMin=2000;
		double latMax=-2000,latMin=2000;
		int pid=0;
		for(int i=1;i<lines.length;++i){
			String[] jw=lines[i].split(",");
			Point p;
			try{
				if(jw.length<3){
					p=new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]));
				}else{
					p=new Point(Double.parseDouble(jw[0]),Double.parseDouble(jw[1]),jw[2]);
				}
			}catch(Exception e){
				continue;
			}
			p.pid=pid++;
			points.addElement(p);
			lonMax=lonMax<p.getLongitude()? p.getLongitude():lonMax;
			lonMin=lonMin>p.getLongitude()? p.getLongitude():lonMin;
			latMax=latMax<p.getLatitude()? p.getLatitude():latMax;
			latMin=latMin>p.getLatitude()? p.getLatitude():latMin;
			
			if(points.size()>=100){
				break;
			}
		}
		Trajectory traj=new Trajectory(points,-1);
		traj.setTimeStamp(traj.getPoints().get(0).getTimestamp()==null? 0:1);
		traj.name=path.substring(path.lastIndexOf('/')+1);
		traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
		double a=lonMax-lonMin,b=latMax-latMin;
		traj.setTrajLen(a>b? a:b);

		return traj;
	}

	/**
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static String readATextFile(String path) throws FileNotFoundException, IOException {
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
	public static Vector<Trajectory> readSomeTrajs(String path,int num) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		String[] files=getFilenames(path);
		if(files==null){
			return null;
		}
		
		int id=0;
		for(String file:files){
			Trajectory traj=readATraj(path+file);
			traj.ID=id++;
			trajs.addElement(traj);
			if(num>0&&trajs.size()>=num){
				break;
			}
		}
		return trajs;
	}
	
	/**
	 * 读取出租车数据
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Vector<Trajectory> readTaxiTrajs(String fileName) throws IOException{
		Vector<Trajectory> trajs=new Vector<Trajectory>();
		BufferedReader read=new BufferedReader(new FileReader(fileName));		
		String str;
		int id=0;
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
//		setSimularDef(line,ts,sd);
		while((str=read.readLine())!=null){
			String tname=str;
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
			traj.name=tname;
			trajs.add(traj);
		}
		read.close();
		return trajs;
	}
	private static void setSimularDef(String[] strs,int ts,SimularDef sd){
		sd.setDtwDis_W(Double.parseDouble(strs[0]));
		sd.setEditDis_W(Double.parseDouble(strs[1]));
		sd.setShapeSum_W(Double.parseDouble(strs[2]));
		sd.setTsum_W(Double.parseDouble(strs[3]));
		sd.setTimeStamp(ts);
	}
	
 	private static void geolife(Vector<String> vs) throws IOException{
		int num=1;
		for(String file:vs){
			BufferedReader in=new BufferedReader(new FileReader(file));
			for(int i=0;i<6;++i){
				in.readLine();
			}
			String s,outStr="经度,纬度\n";
			int cnt=0;
			while((s=in.readLine())!=null){
				if(++cnt>100){
					break;
				}
				try{
					String[] str=s.split(",");
					outStr+=str[1]+","+str[0]+"\n";
				}catch(Exception e){
					continue;
				}
			}
			in.close();
			String outFile="./geolife/traj"+num+".csv";
			++num;
			BufferedWriter write=new BufferedWriter(new FileWriter(outFile));
			write.write(outStr);
			write.close();
		}
	}
	
 	
	/**
	 * 通过文件缓存的方式读取文件
	 * @throws IOException
	 */
	private static List<Trajectory> caltimeCahche() throws IOException {		
		long t1=System.currentTimeMillis();
		List<Trajectory> trajs=readCacheData(DataUpload.SAVE_PATH, new SimularDef());
		long t2=System.currentTimeMillis();
		System.out.println("通过文件缓存的方式耗时："+(t2-t1)+"ms");
		return trajs;
	}

	/**
	 * @throws IOException
	 */
	private static List<Trajectory> caltimeNormal() throws IOException {
		//测试直接读取一条条原始轨迹消耗的时间
		long t1=System.currentTimeMillis();
		List<Trajectory> trajs=readSomeTrajs(PATH, -1);
		long t2=System.currentTimeMillis();
		System.out.println("原始读轨迹方法耗时："+(t2-t1)+"ms");
		return trajs;
	}
	
	private static void writeData(List<Trajectory> trajs){
		try{
			BufferedWriter write=new BufferedWriter(new FileWriter(DataUpload.SAVE_PATH));
			int ts=trajs.get(0).getTimeStamp();
			write.write(""+ts);
			if(ts==1){
				for(Trajectory traj:trajs){
					write.newLine();
					write.write(traj.name);
					write.newLine();
					StringBuilder sb=new StringBuilder("");
					Vector<Point> points=traj.getPoints();
					for(Point p:points){
						sb.append(String.format(",%f,%f,%s", p.getLongitude(),p.getLatitude(),p.getTimestamp()));
					}
					String s=sb.toString();
					write.write(s.substring(1));
				}
			}else{
				for(Trajectory traj:trajs){
					write.newLine();
					write.write(traj.name);
					write.newLine();
					StringBuilder sb=new StringBuilder("");
					Vector<Point> points=traj.getPoints();
					for(Point p:points){
						sb.append(String.format(",%f,%f", p.getLongitude(),p.getLatitude()));
					}
					String s=sb.toString();
					write.write(s.substring(1));
				}
			}
			write.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

 	/**
 	 * 文件流的打开关闭时间计算
 	 * @param path
 	 * @throws IOException
 	 */
 	private static void caltimeStream(String path) throws IOException {		
		String[] files=getFilenames(path);
		long t1=System.currentTimeMillis();
		for(String file:files){
	 		RandomAccessFile raf=new RandomAccessFile(path+file, "r");
	 		FileChannel fchannel=raf.getChannel();
	 		fchannel.close();

//			BufferedReader reader=new BufferedReader(new FileReader(path+file));
//			reader.close();
		}
		long t2=System.currentTimeMillis();
		System.out.println("打开关闭耗时："+(t2-t1)+"ms");
	}
 	
 	/**
 	 * 多线程加速读取文件
 	 * @throws InterruptedException
 	 */
 	private static void caltimeMultiThread() throws InterruptedException {
		long t1=System.currentTimeMillis();
		
		List<String> lfiles=GetFiles.getAllFile(PATH, ".csv");
		String[] files=lfiles.toArray(new String[0]);
		int threadNum=11;
		ExecutorService exe = Executors.newFixedThreadPool(threadNum);
		int fileNum=files.length/threadNum;
		int start=0,end=-1;
		MultiThread[] threads=new MultiThread[threadNum];
		int len=files.length;
		for(int i=0;i<threadNum;++i){
			start=end+1;
			end=(i==threadNum-1)? len-1:end+fileNum;
			threads[i]=new MultiThread(files, start, end);
			exe.execute(threads[i]);
		}
	    exe.shutdown(); 
	    exe.awaitTermination(60, TimeUnit.MINUTES);//阻塞直到线程池里的线程完成
	    
	    long t3=System.currentTimeMillis();
	    List<Trajectory> trajs=threads[0].trajs;	    
	    for(int i=1;i<threadNum;++i){
		    trajs.addAll(trajs.size(),threads[i].trajs);
	    }
		long t2=System.currentTimeMillis();
		System.out.println("多线程方式耗时："+(t3-t1)+"ms");	
		System.out.println("连接轨迹耗时："+(t2-t3)+"ms");	 		
	}
 	
 	/**
 	 * 异步IO
 	 * @throws IOException 
 	 */
 	public static void asynchronousFileIO() throws IOException{  
 		RandomAccessFile raf=new RandomAccessFile(PATH+"traj1.csv", "r");
 		FileChannel fchannel=raf.getChannel();
 		ByteBuffer buffer=ByteBuffer.allocate((int)raf.length());
 		fchannel.read(buffer);
 		buffer.flip();
 		String encoding = System.getProperty("file.encoding");
 	    CharBuffer s=Charset.forName("gb2312").decode(buffer); 
 	    String string=s.toString();
 	    System.err.println(string.length());
	}
	public static void main(String[] args) throws Exception {
//		Trajectory trajectory=readATraj(PATH+"traj5.csv");
//		trajectory.getSize();
		
//		List<Trajectory> trajs=caltimeNormal();
//		
//		for(Trajectory traj:trajs){
//			KMeans kMeans=new KMeans(traj.getPoints());
//			kMeans.run();
//		}
//		writeData(trajs);	
//		
//		List<Trajectory> trajs2=caltimeCahche();
////		trajs2.size();
//		
//		caltimeStream(PATH);
		caltimeMultiThread();
	}
	

}

