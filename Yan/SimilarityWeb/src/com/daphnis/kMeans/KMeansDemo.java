/**
 * KMeans Demo
 * @author Daphnis
 * 20160522
 */
package com.daphnis.kMeans;

import java.io.IOException;
import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.daphnis.dataHandle.ReadData;

public class KMeansDemo {	
	
	public static void main(String[] args) throws IOException{
		Trajectory traj=ReadData.readATraj("./trajData/traj12747.csv","String");
		
    	KMeans kmeans = new KMeans(traj.getPoints());
    	if(kmeans.init()){
	    	kmeans.calculate();
	    	kmeans.removeUnusefulPoints();	    	
	    	kmeans.dataCompression();
    	}		
	}
		
}

