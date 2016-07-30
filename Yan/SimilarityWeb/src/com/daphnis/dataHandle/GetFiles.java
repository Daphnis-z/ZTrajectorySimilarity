/**
 * ��ȡ����Ŀ¼�������ļ������������ļ����ľ���·��
 * ����Ŀ¼
 * @author Daphnis
 * 20160729
 */
package com.daphnis.dataHandle;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GetFiles {
	/**
	 * ��ȡָ��Ŀ¼��ָ�����͵������ļ�
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
	
	//ʹ����ʾ
	public static void main(String[] args){
		List<String> files=getAllFile("Y:\\U\\Downloads\\Geolife Trajectories 1.3\\Data",".plt");
		files.size();
	}
}

