package com.daphnis.kMeans;

import java.io.*;
import java.util.*;

import com.adx.entity.*;
import com.daphnis.dataHandle.*;
import com.adx.similarity.*;

public class KMeansDemo {
	
	public String execute()throws IOException{
		Vector<Trajectory> trajs=ReadTaxiData.readTrajs("./src/com/daphnis/dataHandle/taxiData.txt");
//    	KMeans kmeans = new KMeans(trajs,20);
//    	kmeans.init();
//    	kmeans.calculate();
		System.out.println(trajs.size());
		
//		Trajectory tj1=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息2(不含时间).csv");
//		Trajectory tj2=ReadData.readATraj("./src/com/daphnis/dataHandle/trajWithoutTime/坐标点信息3(不含时间).csv");
		
		Trajectory tj1=trajs.get(19),tj2=trajs.get(319);
		System.out.println(tj1);
		System.out.println(tj2);
		System.out.println(SimpleDTW.DTW(tj1, tj2));
		
		return "success";
	}

}

