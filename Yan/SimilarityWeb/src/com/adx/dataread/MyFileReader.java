package com.adx.dataread;

import java.io.File;

import com.adx.entity.Trajectory;

public class MyFileReader {
	protected File file;
	protected Trajectory traj;
	protected int timeStamp;
	protected double lonMax=-2000,lonMin=2000;
	protected double latMax=-2000,latMin=2000;
	
	public MyFileReader(File file,int timeStamp){
		this.file=file;
		traj=new Trajectory(timeStamp);
		this.timeStamp=timeStamp;
	}
	
	public File getFile() {
		return file;
	}

	public int getTimeStamp() {
		return timeStamp;
	}
	
	public Trajectory getTraj() {
		return traj;
	}

	public double getLonMax() {
		return lonMax;
	}

	public double getLonMin() {
		return lonMin;
	}

	public double getLatMax() {
		return latMax;
	}

	public double getLatMin() {
		return latMin;
	}
}
