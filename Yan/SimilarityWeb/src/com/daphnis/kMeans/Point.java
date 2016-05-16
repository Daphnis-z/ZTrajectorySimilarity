/**
 * point of trajectory
 * author: Daphnis
 */
package com.daphnis.kMeans;

public class Point implements Cloneable{
	private double longitude;
	private double latitude;
	private String timestamp;
	
	/**
	 * point without timestamp
	 * @param longitude
	 * @param latitude
	 */
	public Point(double longitude,double latitude){
		this.longitude=longitude;
		this.latitude=latitude;
	}
	
	/**
	 * point with timestamp
	 * @param longitude
	 * @param latitude
	 * @param timestamp
	 */
	public Point(double longitude,double latitude,String timestamp){
		this.longitude=longitude;
		this.latitude=latitude;
		this.timestamp=timestamp;
	}

	public double getLongitude(){
		return this.longitude;
	}
	public double getLatitude(){
		return this.latitude;
	}
	public String getTimestamp(){
		return this.timestamp;
	}
		
//	/**
//	 * clone a object
//	 */
//	@Override
//	public Object clone(){
//		Point pt=null;
//		try{
//			pt=(Point)super.clone();
//		}catch(CloneNotSupportedException e){
//			e.printStackTrace();
//		}
//		return pt;
//	}
	
	
}
