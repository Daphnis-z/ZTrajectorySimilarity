package com.adx.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.adx.datahandler.EigenvalueFilter;
import com.adx.datahandler.Utility;
import com.adx.dataread.DataUpload;
import com.adx.entity.Constant;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.gis.ShowTraj;
import com.adx.similaralg.Similarity;
import com.adx.similaralg.SimilarityWithTime;
import com.adx.similaralg.SimilarityWithoutTime;
import com.adx.util.FileAbout;
import com.adx.dataread.ReadData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * file: CalMultiTrajsAction.java
 * note: 执行多轨迹的计算
 * author: Daphnis
 * date: 2016年8月10日 下午4:40:50
 */
@SuppressWarnings("serial")
public class CalMultiTrajsAction extends ActionSupport{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private double[] similarity;
	private String actionResult;
	private int[] indexes;
	private Trajectory[] similarestTraj;
	private Point[] similarestPoint;
	private Trajectory objTraj=null;
	
	/** 请求的计算类型 */
	private String reqType;
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	/** 服务器端数据集名称 */
	private String trajsName;	
	public void setTrajsName(String trajsName) {
		this.trajsName = trajsName;
	}	
	public String getTrajsName() {
		return trajsName;
	}
	
	//显示结果
	private String result;	
	public String getResult() {
		return result;
	}
	//可视化原轨迹
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	//可视化最相似的子轨迹
	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}	
	//可视化最相似的轨迹点
	private String strPoints;	
	public String getStrPoints() {
		return strPoints;
	}
	//是否含有时间戳的标志
	private String hasTimestamp;
	public String getHasTimestamp() {
		return hasTimestamp;
	}
	public void setHasTimestamp(String hasTimestamp) {
		this.hasTimestamp = hasTimestamp;
	}

	
	public Trajectory[] getSimilarestTraj() {
		return similarestTraj;
	}

	public Point[] getSimilarestPoint() {
		return similarestPoint;
	}
	
	public int[] getIndexes() {
		return indexes;
	}
	
	public String getActionResult() {
		return actionResult;
	}

	public File getObjectfile() {
		return objectfile;
	}

	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}

	public double[] getSimilarity() {
		return similarity;
	}
		
	/**
	 * 对轨迹群进行数据预处理
	 * @param trajs
	 * @param objtraj
	 * @return
	 */
	private List<Trajectory> dataPreprocessing(List<Trajectory> trajs,Trajectory objtraj){
		if(trajs.get(0).getCenterTraj()==null){
			for(Trajectory traj:trajs){
				calEigenvalue(traj);
			}
		}
		return EigenvalueFilter.filtrateTraj(trajs, objtraj);
	}
	
	/**
	 * 计算轨迹的特征值
	 * @param traj
	 */
	private void calEigenvalue(Trajectory traj){
		double lonMax=-1000,lonMin=1000;
		double latMax=-1000,latMin=1000;
		for(Point pt:traj.getPoints()){
			double lon=pt.getLongitude(),lat=pt.getLatitude();
			lonMax=lonMax<lon? lon:lonMax;
			lonMin=lonMin>lon? lon:lonMin;
			latMax=latMax<lat? lat:latMax;
			latMin=latMin>lat? lat:latMin;
		}
		traj.setCenterTraj(new Point((lonMax+lonMin)/2,(latMax+latMin)/2));
		traj.setTrajLen(lonMax-lonMin);		
	}
										
	/**
	 * 创建在前台显示的计算结果
	 * @param trajs
	 * @param objname
	 */
	private void createResult(List<Trajectory> trajs,String objname){
 		result="目标轨迹：\r\n&nbsp"+objname+"\r\n\r\n测试轨迹群按相似度排序如下：\r\n";
 		int size=similarity.length>10? 10:similarity.length;
 		for(int i=0;i<size;i++){
 			result+=(i+1)+"）.目标轨迹与测试轨迹（"+trajs.get(indexes[i]).name+"）的相似度为："+
 					similarity[indexes[i]]+"%\r\n";
 		}
	}
	/**
	 * 为可视化轨迹做好准备
	 * @param objTraj
	 * @param trajs
	 */
	private void readyForViewTrajs(Trajectory objTraj, List<Trajectory> trajs) {
		strTrajs=packageTrajs(objTraj,trajs);
		strSubtrajs=packageSubTrajs(similarestTraj[0],similarestTraj[1]);
		strPoints=packagePoints(similarestPoint);
		if(objTraj.timeStamp==1){
			hasTimestamp="true";
		}else{
			hasTimestamp="false";
		}
	}	
	/**
	 * 打包待可视化的轨迹
	 * @param objTraj
	 * @param trajs
	 * @return
	 */
	private String packageTrajs(Trajectory objTraj, List<Trajectory> trajs){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(objTraj);
		for(int i=0;i<indexes.length&i<10;++i){
			vt.addElement(trajs.get(indexes[i]));
		}
		return ShowTraj.convertSomeTrajs(vt);
	}
	/**
	 * 打包待可视化的子轨迹
	 * @param traj1
	 * @param traj2
	 * @return
	 */
	private String packageSubTrajs(Trajectory traj1,Trajectory traj2){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		return ShowTraj.convertSomeTrajs(vt);
	}
	/**
	 * 打包最相似的轨迹点
	 * @param points
	 * @return
	 */
	private String packagePoints(Point[] points){
		Trajectory traj=new Trajectory();
		for(int i=0;i<points.length;++i){
			traj.addPoint(points[i]);
		}
		return ShowTraj.convertTraj(traj,false);
	}
		
	/**
	 * 保存目标轨迹
	 * @param traj
	 * @param sd
	 * @throws IOException
	 */
	private void saveObjtraj(Trajectory traj,SimularDef sd) throws IOException{
		File file=new File(Constant.CACHE_FILE);
		file.delete();
		List<Trajectory> trajs=new ArrayList<Trajectory>();
		trajs.add(traj);
		DataUpload.writeData(trajs, sd, Constant.CACHE_FILE);
	}
	
	@Override
	public String execute() throws Exception {
		long t1=System.currentTimeMillis();
		
		List<Trajectory> trajs=null;		
		if(reqType==null){
			actionResult=SUCCESS;
			return actionResult;
		}else if(reqType.equals("cal")){//读取缓存里的数据
			trajs=ReadData.readCacheData(DataUpload.SAVE_PATH,simularDef);
			objTraj=trajs.get(trajs.size()-1);//获取目标轨迹
			trajs.remove(trajs.size()-1);//移除目标轨迹
		}else{//读取服务器端的数据
			try{
				//检查数据集是否已缓存
				boolean isDataExists=false;
				String cachefile=Constant.DATA_PATH+trajsName+".cache";
				File file=new File(cachefile);
				if(file.exists()){//读取已缓存的数据					
					trajs=ReadData.readCacheData(cachefile);
					isDataExists=true;
				}

				if(isDataExists){					
					//读取并处理目标轨迹
					List<Trajectory> ttrajs=ReadData.readCacheData(DataUpload.SAVE_PATH,simularDef);
					objTraj=ttrajs.get(ttrajs.size()-1);
					trajs=dataPreprocessing(trajs, objTraj);
				}else{
					while (true) {
						if(FileAbout.canRead(Constant.CACHE_FILE)){
							break;
						}
						Thread.yield();
						Thread.sleep(200);
					}
					trajs=ReadData.readCacheData(DataUpload.SAVE_PATH,simularDef);
					objTraj=trajs.get(trajs.size()-1);//获取目标轨迹
					saveObjtraj(objTraj, simularDef);
					trajs.remove(trajs.size()-1);//移除目标轨迹
				}
			}catch(Exception e){
				actionResult=ERROR;
				return actionResult;
			}
		}
		
		long t2=System.currentTimeMillis();
	    System.out.println("文件读取结束，耗时："+(t2-t1)+"ms");
				
		indexes=new int[trajs.size()];
		similarity=new double[trajs.size()];
		ArrayList<Similarity> dtwExample=new ArrayList<Similarity>();
		Similarity dtw;
		for (int i=0;i<trajs.size();i++){
			Trajectory traj=trajs.get(i);
			if(simularDef.getTimeStamp()!=1){
				 dtw=new SimilarityWithoutTime(objTraj, traj,simularDef);
			}else{
				 dtw=new SimilarityWithTime(objTraj,traj,simularDef);
			}
			dtwExample.add(dtw);
			similarity[i]=dtw.getSimilarity();
		}
		indexes=Utility.orderByValue(similarity);
		similarestTraj=dtwExample.get(indexes[0]).getSimilarestTraj();
		similarestPoint=dtwExample.get(indexes[0]).getSimilarestPoint();
		createResult(trajs,objTraj.name);
		readyForViewTrajs(objTraj, trajs);
		
	    System.out.println("全部计算结束，耗时："+(System.currentTimeMillis()-t2)+"ms");
		
		actionResult=SUCCESS;
	    return actionResult;
	}

}

