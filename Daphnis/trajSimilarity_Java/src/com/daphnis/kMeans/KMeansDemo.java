package com.daphnis.kMeans;

import java.io.*;
import java.util.*;

import com.adx.entity.*;
import com.daphnis.dataHandle.*;

public class KMeansDemo {
	
	public static void main(String args[])throws IOException{
		Vector<Trajectory> trajs=ReadTaxiData.readTrajs("./src/com/daphnis/dataHandle/taxiData.txt");
		long t1=System.currentTimeMillis();
    	KMeans kmeans = new KMeans(trajs,20);
    	kmeans.init();
		System.out.println(trajs.size());   	
    	kmeans.calculate();
		long t2=System.currentTimeMillis();
		System.out.println("耗时："+(t2-t1)+"ms");
    	
//		Trajectory tj1=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息2(不含时间).csv");
//		Trajectory tj2=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息3(不含时间).csv");
//		
//		Trajectory tj1=trajs.get(19),tj2=trajs.get(319);
//		System.out.println(tj1);
//		System.out.println(tj2);
//		System.out.println(SimpleDTW.DTW(tj1, tj2));
	}
	
//	public String execute(){
//		Vector<Trajectory> trajs=ReadTaxiData.readTrajs("./src/com/daphnis/dataHandle/taxiData.txt");
//    	KMeans kmeans = new KMeans(trajs,20);
//    	kmeans.init();
//		System.out.println(trajs.size());
//    	
//    	kmeans.calculate();
//		
////		Trajectory tj1=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息2(不含时间).csv");
////		Trajectory tj2=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息3(不含时间).csv");
////		
////		Trajectory tj1=trajs.get(19),tj2=trajs.get(319);
////		System.out.println(tj1);
////		System.out.println(tj2);
////		System.out.println(SimpleDTW.DTW(tj1, tj2));
//		
//		return "success";
//	}

}

