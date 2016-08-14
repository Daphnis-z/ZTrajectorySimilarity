package com.adx.similaralg;

import java.io.File;
import java.util.Vector;

import com.adx.datahandler.Utility;
import com.adx.dataread.CSVReader;
import com.adx.entity.Point;
import com.adx.entity.Trajectory;
import com.adx.similaralg.SimilarityUtility;

/**
 * HausdorffDistance
 * @author Agnes
 *计算两条轨迹的Hausdorff 距离
 */
public class HausdorffDistance {
	private double hau1,hau2;
	private Trajectory objTraj,testTraj;
	public double HauDis;
	
	public HausdorffDistance () {
		
	}
	
	private void calHauDis() {
		Vector<Point> objPoint=objTraj.getPoints();
		Vector<Point> testPoint=testTraj.getPoints();
		double[] hau1Array=new double[testPoint.size()];
		double[] hau1Result=new double[objPoint.size()];
		double[] hau2Array=new double[objPoint.size()];
		double[] hau2Result=new double[testPoint.size()];
		//计算单向的hausdorff距离
		for(int i=0;i<objPoint.size();i++){
			for(int j=0;j<testPoint.size();j++){
				hau1Array[j]=SimilarityUtility.r_distance(objPoint.get(i), testPoint.get(j));
			}
			int minIndex=Utility.getMin(hau1Array);
			hau1Result[i]=hau1Array[minIndex];
			System.out.print(+hau1Result[i]+"	");
		}
		hau1=hau1Result[Utility.getMax(hau1Result)];
		System.out.println("hau1:"+hau1);
		for(int i=0;i<testPoint.size();i++){
			for(int j=0;j<objPoint.size();j++){
				hau2Array[j]=SimilarityUtility.r_distance(testPoint.get(i), objPoint.get(j));
			}
			int minIndex=Utility.getMin(hau2Array);
			hau2Result[i]=hau2Array[minIndex];
			System.out.print(+hau2Result[i]+"	");
		}
		hau2=hau2Result[Utility.getMax(hau2Result)];
		System.out.println("hau2:"+hau2);
		if(hau1>hau2){
			HauDis=hau1;
		}else{
			HauDis=hau2;
		}
	}
	


	public double getHauDis(Trajectory objTraj,Trajectory testTraj) {
		this.objTraj=objTraj;
		this.testTraj=testTraj;
		calHauDis();
		return HauDis;
	}



	public static void main(String[] args) {
		File file1=new File("E:/第五届软件杯轨迹分析/轨迹数据/南京南-安德门（地铁路线）.csv");
		File file2=new File("E:/第五届软件杯轨迹分析/轨迹数据/南京南-国贸大厦（驾车路线）.csv");
		File file3=new File("E:/第五届软件杯轨迹分析/轨迹数据/南京南-小龙湾（地铁路线）.csv");
		CSVReader reader1=new CSVReader(file1, 0);
		CSVReader reader2=new CSVReader(file2, 0);
		CSVReader reader3=new CSVReader(file3, 0);
		int i=reader1.readFile();
		int j=reader2.readFile();
		int k=reader3.readFile();
		Trajectory t1=reader1.getTraj();
		Trajectory t2=reader2.getTraj();
		Trajectory t3=reader3.getTraj();
		HausdorffDistance haus=new HausdorffDistance();
		System.out.println("-------------------------------");
		System.out.println("hausdorff distance between t1 and t2:"+haus.getHauDis(t1, t2));
		System.out.println("-------------------------------");
		System.out.println("hausdorff distance between t1 and t3:"+haus.getHauDis(t1, t3));
		System.out.println("-------------------------------");
		System.out.println("hausdorff distance between t2 and t3:"+haus.getHauDis(t2, t3));
	}
}
