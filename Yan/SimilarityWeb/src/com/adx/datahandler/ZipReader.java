package com.adx.datahandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class ZipReader {
	public static void main(String[] args) {
		ZipReader z=new ZipReader(null);
				
		z.readFile();
	}
	private File file;
	public ZipReader(File file) {
		// TODO Auto-generated constructor stub
		this.file=file;
	}
	public int readFile(){
			ZipInputStream zis = null;
			InputStream is = null;

			try {
				ZipFile zf = new ZipFile("E:/第五届软件杯轨迹分析/轨迹数据/1_20160318100328_gjaaw.zip");
				zis = new ZipInputStream(new FileInputStream("E:/第五届软件杯轨迹分析/轨迹数据/1_20160318100328_gjaaw.zip"));

				// 从压缩文件中获取一个条目
				ZipEntry entry = zis.getNextEntry();
				System.out.println(entry);
				if(entry.isDirectory()){
							System.out.println("123");
				}
				// 获得该条目对象的数据流
				is = zf.getInputStream(entry);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					is.close();
					zis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 1;
		}
}
