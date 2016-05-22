package com.daphnis.kMeans;

import java.io.IOException;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.ReadData;

public class KMeansDemo {
	
	public static void main(String args[]) throws IOException{
		Trajectory traj=ReadData.readATraj("./src/com/daphnis/dataHandle/坐标点信息2(不含时间).csv");
		long t1=System.currentTimeMillis();
    	KMeans kmeans = new KMeans(traj.getPoints());
    	kmeans.init();
    	kmeans.calculate();
    	
    	for(Cluster cluster:kmeans.getClusters()){
    		if(cluster.getPoints().size()==1){
    			traj.getPoints().remove(cluster.getPoints().get(0));
    		}
    	}

//		for(int i=0;i<10000;++i){
//	    	KMeans kmeans = new KMeans(traj.getPoints());
//	    	kmeans.init();
//	    	kmeans.calculate();
//		}
    	long t2=System.currentTimeMillis();
    	System.out.println("耗时："+(t2-t1)+" ms");
		
	}
	
}

