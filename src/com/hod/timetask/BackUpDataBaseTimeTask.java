package com.hod.timetask;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hod.pojo.Hod2000DbBackupSchedule;
import com.hod.pro.model.service.IHod2000DbBackupScheduleService;

public class BackUpDataBaseTimeTask extends TimerTask
{
	/** 差异备份时间间隔 */
	private int backTime;
	/** 备份路径 */
	private String backPath;
	/** 完整备份周期 */
	private int backPeriods;
	private String db_name="HOD-2000";
	private Connection conn=null;
	private PreparedStatement statement=null;
	private int count=0;
	private boolean backupFlag=false;
	//日志信息操作实例
	private Logger log = Logger.getLogger(BackUpDataBaseTimeTask.class.getName());
	ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
	IHod2000DbBackupScheduleService backupScheduleService=(IHod2000DbBackupScheduleService) ctx.getBean("springHod2000DbBackupScheduleService");
	
	@Override
	public void run() {
		log.info("进入备份方法...");
		DetachedCriteria dc=DetachedCriteria.forClass(Hod2000DbBackupSchedule.class);
		List dataList=backupScheduleService.findByCriteria(dc);
		if(dataList.size()>0)
		{
			Hod2000DbBackupSchedule backupSchedule=(Hod2000DbBackupSchedule) dataList.get(0);
			this.backPath=backupSchedule.getBackupPath();
			File fp=new File(this.backPath);
			//目录不存在时创建目录
			if(!fp.exists())
			{
				fp.mkdirs();
			}
			this.backTime=backupSchedule.getDiffTime();
			this.backPeriods=backupSchedule.getCompletePeriod();
			Calendar calendar = Calendar.getInstance();
			int day=calendar.get(Calendar.DAY_OF_WEEK);
			int date=calendar.get(Calendar.DATE);
			int hour=calendar.get(Calendar.HOUR_OF_DAY);
			//完整备份，每周一备份一次
			if(0==this.backPeriods)
			{
				if(2==day&&hour==this.backTime)
				{
					completeBackup();
				}
			}
			//完整备份，每月一号备份一次
			else
			{
				if(1==date&&hour==this.backTime)
				{
					completeBackup();
				}
			}
			if(backupFlag==false&&count==0)
			{
				completeBackup();
				backupFlag=true;
			}
			//进行差异备份，每天一次
			if(hour==this.backTime)
			{
				diffBackup();
			}
		}
		else
		{
			log.info("请先创建数据库备份计划");
		}
	} 
	
	/**
	 * 完整备份
	 */
	public void completeBackup()
	{
		log.info("开始完整备份...");
		try {
			File file;
			if(0==count)
			{
				file=new File(this.backPath);
				if(file.isDirectory())
				{
					String[] filelist = file.list(); 
					int mp[] = new int[filelist.length];
					for (int i = 0; i < filelist.length; i++) { 
						if(isNum(filelist[i]))
						{
							mp[i] = Integer.parseInt(filelist[i]);
						}
					}
					if(mp.length>0)
					{
						Arrays.sort(mp);
						count= mp[mp.length - 1]+1;
					}
					else
					{
						count+=1;
					}
					file=new File(this.backPath+"\\"+count);
					if(!file.exists())
					{
						file.mkdir();
					}
				}
			}
			else
			{
				count+=1;
				file=new File(this.backPath+"\\"+count);
				if(!file.exists())
				{
					file.mkdir();
				}
			}
			String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date())+".bak";//备份文件命名规则
			String sql = "backup database \""+db_name+"\" to disk='"+this.backPath+"\\"+count+"\\"+fileName+"'";
			ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
			javax.sql.DataSource dataSource=(javax.sql.DataSource) ctx.getBean("conn");
			conn=dataSource.getConnection();
			statement=conn.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			log.error("数据库完整备份：",e);
		}
		finally{
			try {
				statement.close();
				conn.close();
			} catch (Exception e) {
				log.error("数据库完整备份：",e);
			}
		}
	}
	
	public void diffBackup()
	{
		try {
			log.info("开始差异备份...");
			String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date())+".diff";//差异备份文件命名规则
			String logName= new SimpleDateFormat("yyyyMMdd").format(new Date())+".trn";//日志备份文件
			String sql = "backup database \""+db_name+"\" to disk='"+this.backPath+"\\"+count+"\\"+fileName+"' WITH DIFFERENTIAL";
			sql+=";BACKUP LOG \""+db_name+"\" to disk='"+this.backPath+"\\"+count+"\\"+logName+"'";
			ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
			javax.sql.DataSource dataSource=(javax.sql.DataSource) ctx.getBean("conn");
			conn=dataSource.getConnection();
			statement=conn.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			log.info("数据库差异备份：",e);
		}
		finally{
			try {
				statement.close();
				conn.close();
			} catch (Exception e) {
				log.error("数据库差异备份：",e);
			}
		}
	}
	
	public boolean isNum(String str){		
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");	
	}
}
