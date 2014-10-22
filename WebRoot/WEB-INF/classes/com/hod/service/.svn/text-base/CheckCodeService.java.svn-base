package com.hod.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

import com.hod.util.InitServlet;

public class CheckCodeService {
	/**
	 * 读取计算机的MAC地址
	 * 
	 * @return
	 * @throws Exception
	 */

	public static String testGetSysInfo() throws Exception {
		 Logger log=Logger.getLogger(CheckCodeService.class.getName());
		String address = "";
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Windows")) {
			try {
				String command = "cmd.exe /c ipconfig /all";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p
						.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("Physical Address") > 0) {
						int index = line.indexOf(":");
						index += 2;
						address = line.substring(index);
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return address.trim();
	}
	
	/**
	 * 读文件的总行数
	 * @return
	 * @throws Exception
	 */
	public static int readLine() throws Exception{
		String file=InitServlet.localpath+"licenseFile.dat";
		BufferedReader bin = new BufferedReader(new FileReader(file));
        int count=0; 
        while(bin.readLine()!=null){ 
        	count=count+1;
		} 
		return count;
	}

}
