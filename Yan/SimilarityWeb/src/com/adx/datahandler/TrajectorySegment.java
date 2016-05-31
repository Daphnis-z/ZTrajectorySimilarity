package com.adx.datahandler;

import java.util.ArrayList;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

/**
 * TrajctorySegment
 * @author Agnes
 *轨迹分割--按照拐角切割
 */
public class TrajectorySegment {
	private Trajectory traj;
	private ArrayList<Integer> index;
	
	public TrajectorySegment(Trajectory traj) {
		// TODO Auto-generated constructor stub
		this.traj=traj;
		index=traj.subTrajs_index; 
	}
	
	//按拐角轨迹切割
	public boolean trajSegment(){
		Vector<Point> points=traj.getPoints();
		int length=points.size();
		for(int i=1;i<length-1;i++){
			Point p1=points.get(i);
			Point p0=points.get(i-1);
			Point p2=points.get(i+1);
			double p1p0=Math.sqrt((p1.getLatitude()-p0.getLatitude())*(p1.getLatitude()-p0.getLatitude())
					+(p1.getLongitude()-p0.getLongitude())*(p1.getLongitude()-p0.getLongitude()));
			double p2p1=Math.sqrt((p1.getLatitude()-p2.getLatitude())*(p1.getLatitude()-p2.getLatitude())
					+(p1.getLongitude()-p2.getLongitude())*(p1.getLongitude()-p2.getLongitude()));
			double p2p0=Math.sqrt((p2.getLatitude()-p0.getLatitude())*(p2.getLatitude()-p0.getLatitude())
					+(p2.getLongitude()-p0.getLongitude())*(p2.getLongitude()-p0.getLongitude()));
			double cos=(p1p0*p1p0+p2p1*p2p1-p2p0*p2p0)/(2*p1p0*p2p1);
			if(cos>0){
				index.add(i);
			}
			}
		System.out.println("未调整index:"+index);
		//轨迹分割调整，五个点之内需要合并
		int index_j=0;
		while(index_j<index.size()-1){
			int tem1=index.get(index_j);
			int tem2=index.get(index_j+1);
			if((tem2-tem1)<5){
				index.remove(index_j);
			}else{
				index_j++;
			}
		}
		//对最后边界点进行调整
		if(index.size()>0){
			if((traj.getSize()-1-index.get(index.size()-1))<5){
				index.remove(index.size()-1);
			}
		}
		System.out.println("调整index:"+index);
		return true;
	}
	
	//按要求段数轨迹切割同步
	public boolean trajSynch(int n){
		Vector<Trajectory> subTrajs=traj.getSubTrajs();
		int[] sizes=new int[subTrajs.size()];
		for(int i=0;i<subTrajs.size();i++){
			sizes[i]=subTrajs.get(i).getPoints().size();
		}
		if(index.size()==0){
			for(int i=1;i<n;i++){
				index.add(i*(sizes[0]/n));
			}
			System.out.println("同步index:"+index);
			return true;
		}
		if(n==subTrajs.size()){
			System.out.println("同步index:"+index);
			return true;
		}

		if(n>subTrajs.size()){//轨迹段再切割
			int index_max=Utility.getMax(sizes),max,max_tem=0;
			if(index_max!=0){
				if((index_max-2)>0)
					max_tem=index.get(index_max-2);
				else
					max_tem=index.get(index_max-1);
			}
			if(index_max<index.size()){
				max=index.get(index_max);
				index.add(index_max, (max+max_tem)/2);
			}else{
				max=index.get(index.size()-1);
				index.add(index_max-1, (max+max_tem)/2);
			}
			trajSynch(n);
		}
		if(n<subTrajs.size()){//轨迹段合并
			int index_min=Utility.getMin(sizes);
			if(index_min<index.size()){
				index.remove(index_min);
			}else{
				index.remove(index_min-1);
			}
			trajSynch(n);
		}
		return true;
	}
	
}
