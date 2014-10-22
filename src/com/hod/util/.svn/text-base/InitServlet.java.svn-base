package com.hod.util;

/**
 * log4j初始化
 * tomcat启动时间
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hod.pojo.Hod2000DbBackupSchedule;
import com.hod.pro.model.service.IHod2000DbBackupScheduleService;
import com.hod.timetask.BackUpDataBaseTimeTask;
import com.hod.timetask.DoSelectPrice;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	static Logger log = Logger.getLogger(InitServlet.class.getName());
	public static String path = "";
	public static final  String VER = "1.0";
	public static String localpath = "";

	public static String configpath = "E:\\workspace\\HODsocket\\";
	public static String uploadlogpath = "E:\\workspace\\HODsocket\\";
	public static String uploadcfg = "E:\\workspace\\HODsocket\\";
	 
	
	public static DataSource dataSource;
	public static ServletContext ctx;

	public static boolean blnShortMsgSend = false;
	public static String  strShortMsgServerIp = "";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String prefix = config.getServletContext().getRealPath("/");
		localpath = prefix;
		configpath = InitServlet.localpath + "configfile\\";
		uploadlogpath = InitServlet.localpath + "uploadlog\\";
		uploadcfg = InitServlet.localpath + "uploadcfg\\";
		Properties props = null;
		FileInputStream istream = null;
		try {
			//log4j
			String fileLog = config.getInitParameter("log4j-init-file");
			String fileLogPath = prefix + fileLog;
			props = new Properties();
			istream = new FileInputStream(fileLogPath);
			props.load(istream);
			istream.close();
			String logFile = prefix
					+ props.getProperty("log4j.appender.FILE.File");// 设置路径
			props.setProperty("log4j.appender.FILE.File", logFile);
			PropertyConfigurator.configure(props);// 装入log4j配置信息
			Logger logger = Logger.getLogger(InitServlet.class.getName());
			logger.info("创建日志文件："
					+ props.getProperty("log4j.appender.FILE.File"));

			path = config.getServletContext().getContextPath();
			logger.info("初始化项目备份参数...");
			//设置默认的备份路径，为项目路径
			ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
			IHod2000DbBackupScheduleService backupScheduleService=(IHod2000DbBackupScheduleService) ctx.getBean("springHod2000DbBackupScheduleService");
			List<Hod2000DbBackupSchedule> list=backupScheduleService.findByHQL("FROM Hod2000DbBackupSchedule");
			if(0==list.size())
			{
				File file=new File(localpath+"\\dataBase");
				if(!file.exists())
				{
					file.mkdir();
				}
				Hod2000DbBackupSchedule backupSchedule=new Hod2000DbBackupSchedule();
				backupSchedule.setBackupPath(localpath+"\\dataBase");
				backupSchedule.setCompletePeriod(0);
				backupSchedule.setDiffTime(23);
				backupScheduleService.save(backupSchedule);
			}
			doTask();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("初始化...",e);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	public void doTask()
	{
		Properties properties=ParseProperties.getProperties();
		if(properties.getProperty("database").equals("MSSQL"))
		{
			//启动线程进行完整与差异的定时备份
			Timer timer2 = new Timer(); 
			long delay2 = 3000; 
			long period2 = 60*60*1000;  
			// 从现在开始 3 秒钟之后，每隔 1个小时执行一次
			timer2.schedule(new BackUpDataBaseTimeTask(), delay2, period2); 
		}
		
		Timer timer = new Timer(); 
		long delay = 3000; 
		long period = 60*60*1000; 
		// 从现在开始 3 秒钟之后，每隔 1个小时执行一次
		timer.schedule(new DoSelectPrice(), delay, period);  
	}
}