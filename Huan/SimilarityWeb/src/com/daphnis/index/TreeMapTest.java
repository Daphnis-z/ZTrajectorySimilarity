package com.daphnis.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

public class TreeMapTest {
	public static void main(String[] args){
		Map<Double,Double> tdi=new TreeMap<Double,Double>();
		Random rd;
		List<Double> ld=new ArrayList<Double>();
		for(int i=0;i<10000;++i){
			rd=new Random(System.currentTimeMillis());
			for(int j=0;j<200;++j){
				double num=rd.nextDouble()*10;
				ld.add(num);
				tdi.put(num,num);
			}
		}
		double search=8.65;
		
		//ÆÕÍ¨Ñ­»·
		long time=System.currentTimeMillis();
		double sea=10000;
		double fv=0;
		for(int i=0;i<ld.size();++i){			
			double tmp=Math.abs(ld.get(i)-search);
			if(tmp<sea){
				sea=tmp;
				fv=ld.get(i);
			}
		}
		long duration=System.currentTimeMillis()-time;
		System.out.println(fv+","+sea+": "+duration);
		
		//TreeMap
		time=System.currentTimeMillis();
		Object[] keys=tdi.keySet().toArray();
		System.out.println(System.currentTimeMillis()-time);
		int a=0,b=keys.length-1;
		if(search<(double)keys[a]){
//			System.out.print(keys[a]+","+tdi.get(keys[a])+": ");
		}else if(search>(double)keys[b]){
//			System.out.print(keys[b]+","+tdi.get(keys[b])+": ");
		}else{
			while(b-a>1){
				int c=(a+b)/2;
				if(search<(double)keys[c]){
					b=c;
				}else{
					a=c;
				}
			}
//			if(search-(double)keys[a]<(double)keys[b]-search){
//				System.out.print(keys[a]+","+tdi.get(keys[a])+": ");
//			}else{
//				System.out.print(keys[b]+","+tdi.get(keys[b])+": ");
//			}
		}
		duration=System.currentTimeMillis()-time;
		System.out.println(duration);
		
//		Object[] keys=tdi.keySet().toArray();
//		for(Object key:keys){
//			System.out.println(key.toString()+","+tdi.get(key));
//		}
	}
}
