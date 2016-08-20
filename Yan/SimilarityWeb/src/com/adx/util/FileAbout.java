package com.adx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * file: FileAbout.java
 * note: ����ļ�������ص�һЩ���ú���
 * author: Daphnis
 * date: 2016��8��19�� ����9:48:38
 */
public class FileAbout {
	/**
	 * ��ȡָ��Ŀ¼��ָ�����͵������ļ�������Ŀ¼��
	 * ����ȡȫ�����Ϳɽ�: fileter����Ϊnull
	 * @param path
	 * @param filter
	 * @return
	 */
	public static List<String> getAllFile(String path,String filter){
		List<String> fileList=new ArrayList<String>();
		Queue<String> dirs=new LinkedList<String>();
		dirs.add(path);
		while(dirs.size()>0){
            File[] files = new File(dirs.remove()).listFiles();//��ȡ���ļ��������е��ļ�(��)��
            
            int len=files.length;
            for(int i=0;i<len;i++){
                String tmp=files[i].getAbsolutePath();
                if(files[i].isDirectory()){
                    dirs.add(tmp);
                }
                else{
                	if(filter==null||tmp.endsWith(filter)){
                    	fileList.add(tmp);
                	}
                }
            }
		}	
		return fileList;
	}

	/**
	 * ȷ���ļ��д��ڣ��������򴴽���
	 * @throws IOException
	 */
	public static void confirmDir(String path) throws IOException{
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
	/**
	 * �鿴�ļ����У��Ƿ����
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String filePath){
		File file=new File(filePath);
		return file.exists();
	}
	
	/**
	 * �ж��ļ��Ƿ�ɶ�
	 * @param file
	 * @return
	 */
	public static boolean canRead(String file){
		try{
			BufferedReader reader=new BufferedReader(new FileReader(file));
			reader.readLine();
			reader.close();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}

