/**
 * point of trajectory
 * author: Daphnis
 */
package com.adx.entity;

public class Point {
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

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
}
