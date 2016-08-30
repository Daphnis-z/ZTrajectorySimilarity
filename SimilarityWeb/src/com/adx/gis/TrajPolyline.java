package com.adx.gis;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.adx.datahandler.DataHandler;
import com.adx.datahandler.KMeans;
import com.adx.dataread.CSVReader;
import com.adx.entity.Trajectory;

/**
 * file: TrajPolyline.java
 * note: Ϊ�ڵ�ͼ����ʾ�켣����ش���
 * author: Daphnis
 * date: 2016��8��5�� ����10:22:32
 */
@SuppressWarnings("serial")
public class TrajPolyline extends ActionSupport implements ServletResponseAware{
	
	/** ���켣�������ַ�������ʽ���ؿͻ��� */
	private String strTrajs;		
	public String getStrTrajs() {
		return strTrajs;
	}
	public void setStrTrajs(String strTrajs) {
		this.strTrajs = strTrajs;
	}
	
	private String dataHandle;
	public String getDataHandle() {
		return dataHandle;
	}
	public void setDataHandle(String dataHandle) {
		this.dataHandle = dataHandle;
	}

	/** ��ȡ�ϴ����ļ����� */
	private File trajFile;	
	public File getTrajFile() {
		return trajFile;
	}
	public void setTrajFile(File trajFile) {
		this.trajFile = trajFile;
	}		
	
	/** ��ȡresponse���� */
	private HttpServletResponse response;
	public void setServletResponse(HttpServletResponse response){
		this.response=response;
	}
						
	@Override
	public String execute() throws Exception {
		if(trajFile!=null){
			CSVReader objReader=new CSVReader(trajFile,-1);
			objReader.readFile();
			Trajectory objTraj=objReader.getTraj();	
			//�Ƿ���������Ԥ������
			if(dataHandle!=null&&dataHandle.equals("true")){
				KMeans km=new KMeans(objTraj.getPoints());
				km.run();
			}			
			strTrajs=ShowTraj.convertTraj(objTraj,true);								
		}
	   response.setContentType("text/html");
	   PrintWriter out = response.getWriter();
	   out.print(strTrajs);
	   out.close();

	   return super.execute();
	}
	
}

