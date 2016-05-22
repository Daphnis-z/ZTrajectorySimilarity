package com.daphnis.kMeans;

import java.io.*;
import java.util.*;

import com.adx.entity.*;
import com.daphnis.dataHandle.*;
import com.adx.similarity.DTWSimilarity;
import com.adx.similarity.SimpleDTW;

class Ans{
	public int trajId;
	public double similarity;
	
	public Ans(int trajId,double similarity){
		this.trajId=trajId;
		this.similarity=similarity;
	}
}
class AnsComparator implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		if(((Ans)o1).similarity-((Ans)o2).similarity>0){
			return 1;
		}
		return -1;
	}	
}

public class KMeansDemo {
	
	public static void findMostSimiTraj(Trajectory traj,Vector<Trajectory> trajs,DTWSimilarity dtw){
		Vector<Ans> va=new Vector<Ans>();
		double simi=0.0;
		int trajId=-1;
		for(Trajectory tra:trajs){
			if(tra.getId()==traj.getId()){
				continue;
			}
			double ts=dtw.getSimilarity(traj, tra, 0);
			
			Ans ans=new Ans(tra.getId(),ts);
			va.addElement(ans);
			
			if(ts>simi){
				simi=ts;
				trajId=tra.getId();
			}
		}
		va.sort(new AnsComparator());
		int num=va.size();
		for(Ans a:va){
			System.out.println((num--)+"\t: "+a.trajId+','+a.similarity);
		}
		System.out.println("Without cluster :");
		System.out.println(String.format("\tMost likely traj id : %d \n\tsimilarity: %f", trajId,simi));
	}
	
	public static void findMostSimiTrajKMeans(Trajectory traj,Vector<Trajectory> trajs,DTWSimilarity dtw){
		System.out.println("Clustered :");
		
		//使用KMeans对轨迹进行聚类
    	KMeans kmeans = new KMeans(trajs,trajs.size()/20);
    	kmeans.init();
		System.out.println("\tInitialized ..");
		System.out.println(String.format("\tTotal : %d trajs",trajs.size() ));   	
		System.out.println("\tStart clustering ..");
    	kmeans.calculate();
		System.out.println("\tCluster complete ..");
				
		//找到应该进入的群
		Cluster cluster=null;
		double tmp=0.0;
		for(Cluster clu:kmeans.getClusters()){
			double d=dtw.getSimilarity(traj, clu.getCentroid(), 0);
			if(d>tmp){
				tmp=d;
				cluster=clu;
			}
		}
		
		//进入到目标群寻找最相似的轨迹				
		double simi=0.0;
		int trajId=-1;
		if(cluster.getTrajs().size()==0){
			simi=dtw.getSimilarity(traj, cluster.getCentroid(), 0);
			trajId=cluster.getCentroid().getId();
		}
		else{
			for(Trajectory tra:cluster.getTrajs()){
				double d=dtw.getSimilarity(traj, tra, 0);
				if(tra.getId()!=traj.getId()&&d>simi){
					simi=d;
					trajId=tra.getId();
				}
			}
		}
		
		System.out.println(String.format("\tMost likely traj id : %d \n\tsimilarity: %f", trajId,simi));
	}
	
	public static void main(String args[])throws IOException{
		Vector<Trajectory> trajs=ReadTaxiData.readTrajs("./src/com/daphnis/dataHandle/taxiData.txt");
				
		//扩充数据量
		for(int i=0;i<2;++i){
			@SuppressWarnings("unchecked")
			Vector<Trajectory> vt=(Vector<Trajectory>)trajs.clone();
			for(Trajectory traj:vt){
				trajs.addElement(traj);
			}
		}
		
//		long t1=System.currentTimeMillis();
//    	KMeans kmeans = new KMeans(trajs,trajs.size()/20);
//    	kmeans.init();
//		System.out.println(String.format("Total : %d trajs",trajs.size() ));   	
//
//		System.out.println("Start calculating ..");
//    	kmeans.calculate();
//		long t2=System.currentTimeMillis();
//		System.out.println("耗时："+(t2-t1)+"ms");
    	
		SimularDef sim=new SimularDef();
		sim.setDtwDis_W(0.55);sim.setDtwDis_B(100);
		sim.setEditDis_W(0.15);sim.setEditDis_B(5000);
		sim.setTsum_W(0.15);sim.setTsum_B(100000);
		sim.setShapeSum_W(0.15);sim.setShapeSum_B(100);		
		DTWSimilarity dtw=new DTWSimilarity(sim);
		
		long t1=System.currentTimeMillis();
		findMostSimiTraj(trajs.get(11),trajs,dtw);
		long t2=System.currentTimeMillis();
		System.out.println("耗时："+(t2-t1)+"ms");
		KMeansDemo.findMostSimiTrajKMeans(trajs.get(11),trajs,dtw);
		long t3=System.currentTimeMillis();
		System.out.println("耗时："+(t3-t2)+"ms");
				
//		System.out.println(dtw.getSimilarity(trajs.get(11), trajs.get(11), 0));
	}
	
}

