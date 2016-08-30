package com.adx.datahandler;

public class Utility {
	public static int[] orderByValue(double[] similarity){
		int index=0;
		int[] indexes=new int[similarity.length];
		double[] tem=similarity.clone();
		for(int i=0;i<similarity.length;i++){
			index=getMax(tem);
			tem[index]=-1;
			indexes[i]=index;
		}
		return indexes;
	}
	

	public static int getMax(double[] sizes){
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
	public static int getMin(double[] array){
		int index=0;
		@SuppressWarnings("unused")
		double min=0;
		int length=array.length;
		for(int i=0;i<length;i++){
			if(array[i]<array[index]){
				index=i;
				min=array[i];
			}
		}
		return index;
	}
}
