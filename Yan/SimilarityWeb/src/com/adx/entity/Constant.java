package com.adx.entity;

import java.util.ArrayList;

import com.adx.entity.Trajectory;

public class Constant {
	public static Trajectory objTraj;
	public static Trajectory testTraj;
	public static Trajectory[] testTraj_more;
	public static int pattern;//模式1，0
	public static String TIME_FORMAT="yyyy/MM/dd HH:mm";
	
	public  static double  dtwDis_B_VALUE=2;	//dtw的距离度量最坏值
	public  static double editDis_B_VALUE=100;	//编辑距离度量最坏值
	public  static long tsum_B_VALUE=1440;	//时间戳度量最坏值
	public  static double shapeSum_B_VALUE=100;	//形状度量最坏值
	public static double dtwDis_B=dtwDis_B_VALUE;	//dtw的距离度量最坏值
	public static double editDis_B=editDis_B_VALUE;	//编辑距离度量最坏值
	public static long tsum_B=tsum_B_VALUE;	//时间戳度量最坏值
	public static double shapeSum_B=shapeSum_B_VALUE;	//形状度量最坏值
	
}
