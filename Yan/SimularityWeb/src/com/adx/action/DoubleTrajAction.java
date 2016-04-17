package com.adx.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.adx.entity.SimularDef;
import com.adx.resource.Constant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class DoubleTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private File testfile;
	public File getObjectfile() {
		return objectfile;
	}

	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}

	public File getTestfile() {
		return testfile;
	}

	public void setTestfile(File testfile) {
		this.testfile = testfile;
	}

	private void setSimularDef(){
		Constant.simularDef=simularDef;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		setSimularDef();
		System.out.println(simularDef.getDtwDis_B());
		System.out.println(simularDef.getEditDis_B());
		System.out.println(simularDef.getShapeSum_B());
		try {
			BufferedReader br = new BufferedReader(new FileReader(objectfile));
			// ��ȡֱ�����һ��
			String line = "";
			while ((line = br.readLine()) != null) {
				// ��һ�����ݷָ�ɶ���ֶ�
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					// ÿһ�еĶ���ֶ���TAB������ʾ
					System.out.print(st.nextToken() + "\t");
				}
				System.out.println();
			}
				br.close();
		} catch (FileNotFoundException e) {
			// ����File��������ʱ���쳣
			e.printStackTrace();
		} catch (IOException e) {
			// ����BufferedReader����ر�ʱ���쳣
			e.printStackTrace();
		} 
		return SUCCESS;
	}

	//ģ������ʵ���������ݵķ�װ
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
