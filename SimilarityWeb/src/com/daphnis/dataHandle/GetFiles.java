package com.daphnis.dataHandle;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * file: GetFiles.java
 * note: 获取给定目录下所有文件（给定类型文件）的绝对路径
 * author: Daphnis
 * date: 2016年8月19日 上午9:45:22
 */
public class GetFiles {
	/**
	 * 获取指定目录下指定类型的所有文件
	 * 若获取全部类型可将: fileter设置为null
	 * @param path
	 * @param filter
	 * @return
	 */
	public static List<String> getAllFile(String path,String filter){
		List<String> fileList=new ArrayList<String>();
		Queue<String> dirs=new LinkedList<String>();
		dirs.add(path);
		while(dirs.size()>0){
            File[] files = new File(dirs.remove()).listFiles();//获取该文件夹下所有的文件(夹)名
            
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
	
	//使用演示
	public static void main(String[] args){
		List<String> files=getAllFile("Y:\\U\\Downloads\\Geolife Trajectories 1.3\\Data",".plt");
		files.size();
	}
}

