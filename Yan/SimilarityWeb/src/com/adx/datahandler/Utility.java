package com.adx.datahandler;

public class Utility {
	

	public static int getMax(int[] sizes){
		int index=0;
		double max=0;
		for(int i=0;i<sizes.length;i++){
			if(max<sizes[i]){
				max=sizes[i];
				index=i;
			}
		}
		return index;
	}
	public static int getMin(int[] sizes){
		int index=0;
		double min=0;
		for(int i=0;i<sizes.length;i++){
			if(min>sizes[i]){
				min=sizes[i];
				index=i;
			}
		}
		return index;
	}
}
