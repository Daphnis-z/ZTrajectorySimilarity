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
 * note: 存放文件操作相关的一些常用函数
 * author: Daphnis
 * date: 2016年8月19日 上午9:48:38
 */
public class FileAbout {
	/**
	 * 获取指定目录下指定类型的所有文件（含子目录）
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

	/**
	 * 确保文件夹存在（不存在则创建）
	 * @throws IOException
	 */
	public static void confirmDir(String path) throws IOException{
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
	/**
	 * 查看文件（夹）是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String filePath){
		File file=new File(filePath);
		return file.exists();
	}
	
	/**
	 * 判断文件是否可读
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

