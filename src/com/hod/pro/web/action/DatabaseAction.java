package com.hod.pro.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hod.javabean.Files;
import com.hod.pojo.Hod2000DbBackupSchedule;
import com.hod.pro.model.service.IHod2000DbBackupScheduleService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;


/**
 * DatabaseAction 数据库备份与还原
 * @author yixiang
 */
public class DatabaseAction extends ActionSupport {
	private HttpServletRequest request;
	private PrintWriter out;
	/**数据库名称*/
	private String db_name="HOD-2000";
	private Connection conn=null;
	private PreparedStatement statement=null;
	private Message msg=Message.getInstance();
	private IHod2000DbBackupScheduleService backupScheduleService;
	private Hod2000DbBackupSchedule backupSchedule;
	//日志信息操作实例
	static Logger log = Logger.getLogger(DatabaseAction.class.getName());
	private String filePath="";
	
	//数据库全备份计划保存
	public String allToBak()
	{
		request = ServletActionContext.getRequest();
		String id=request.getParameter("id");
		String periods=request.getParameter("periods");//完整备份周期
		String backTime=request.getParameter("backTime");//差异备份时间间隔
		String path=request.getParameter("path");//如果路径不存，则退出备份
		File fp=new File(path);
		//目录不存在时创建目录
		if(!fp.exists())
		{
//			msg.setError1("目录不存在，请重新输入...");
//			msg.setLink(0, "<a href=\"" + request.getContextPath()
//					+ "/database!toUpdate.do\">返回</a>");
//			request.setAttribute("message", msg);
//			return "message";
			fp.mkdirs();
		}
		try {
			if(id!=null&&!"".equals(id))//修改作业
			{
				backupSchedule=(Hod2000DbBackupSchedule) backupScheduleService.findById(Integer.parseInt(id));
				backupSchedule.setBackupPath(path);
				backupSchedule.setDiffTime(Integer.parseInt(backTime));
				backupSchedule.setCompletePeriod(Integer.parseInt(periods));
				backupScheduleService.update(backupSchedule);
			}
			else//创建新作业
			{
				backupSchedule=new Hod2000DbBackupSchedule();
				backupSchedule.setBackupPath(path);
				backupSchedule.setDiffTime(Integer.parseInt(backTime));
				backupSchedule.setCompletePeriod(Integer.parseInt(periods));
				backupScheduleService.save(backupSchedule);
			}
			msg.setMsg1("数据库备份计划保存成功!");
			OperatorLog.addOperatorLog("数据库备份计划保存");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("DatabaseAction-->allToBak",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/database!toUpdate.do\">返回</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	//数据库全恢复
	public String allToRestore()
	{
		try {
			OperatorLog.addOperatorLog("数据库恢复");
			request = ServletActionContext.getRequest();
			ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
			javax.sql.DataSource dataSource=(javax.sql.DataSource) ctx.getBean("conn");
			String diffFilesName=request.getParameter("diffFilesName");
			String logFilesName=request.getParameter("logFilesName");
			String completeFilesName=request.getParameter("completeFilesName");
			String sql="USE master;Alter Database \""+db_name+"\" SET SINGLE_USER With ROLLBACK IMMEDIATE;RESTORE DATABASE \""+db_name+"\" FROM DISK='"+completeFilesName+"' WITH NORECOVERY, REPLACE";
			sql+=";RESTORE DATABASE \""+db_name+"\" FROM DISK='"+diffFilesName+"' WITH NORECOVERY";
			sql+=";RESTORE LOG \""+db_name+"\" FROM DISK='"+logFilesName+"' WITH RECOVERY";
			sql+=";Use \""+db_name+"\";";
			conn=dataSource.getConnection();
			statement=conn.prepareStatement(sql);
			statement.executeUpdate();
			msg.setMsg1("恢复数据库成功!");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("DatabaseAction-->allToRestore",e);
		}
		finally{
			try {
				statement.close();
				conn.close();
			} catch (Exception e) {
				msg.setError1(e.toString());
				log.error("DatabaseAction-->allToRestore",e);
			}
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/database!toUpdate.do\">返回</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	/**
	 * 点击数据库备份与恢复，查询备份路径、周期等信息
	 * @return
	 */
	public String toUpdate()
	{
		try {
			request = ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000DbBackupSchedule.class);
			List dataList=backupScheduleService.findByCriteria(dc);
			if(dataList.size()>0)
			{
				backupSchedule=(Hod2000DbBackupSchedule) dataList.get(0);
			}
			return "update";
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("DatabaseAction-->toUpdate:",e);
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/subpage.jsp?id=1\">返回系统管理</a>");
			request.setAttribute("message", msg);
			return "message";
		}
		
	}
	
	public void getPath()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			Files outFiles=new Files();
			String backupTime=request.getParameter("backupTime");
			if(backupTime!=null&&!"".equals(backupTime))
			{
				backupTime=backupTime.replaceAll("-", "");
				DetachedCriteria dc=DetachedCriteria.forClass(Hod2000DbBackupSchedule.class);
				List dataList=backupScheduleService.findByCriteria(dc);
				if(dataList.size()>0)
				{
					filePath="";
					backupSchedule=(Hod2000DbBackupSchedule) dataList.get(0);
					if(!new File(backupSchedule.getBackupPath()).exists())
					{
						out.write("{success:false,msg:'数据库备份文件夹不存在!'}");
						return;
					}
					fileList(backupSchedule.getBackupPath(), backupTime+".diff");
					if(!"".equals(filePath))
						outFiles.setDiffFilePath(filePath);
					else
					{
						out.write("{success:false,msg:'找不到该日期的差异备份文件!'}");
						return;
					}
					fileList(backupSchedule.getBackupPath(), backupTime+".trn");
					if(!"".equals(filePath))
						outFiles.setTrnFilePath(filePath);
					else
					{
						out.write("{success:false,msg:'找不到该日期的差异备份日志文件!'}");
						return;
					}
					if(!"".equals(filePath))
					{
						filePath=filePath.substring(0,filePath.lastIndexOf("\\"));
						File bakFile=new File(filePath);
						if(bakFile.isDirectory())
						{
					        File[] files = bakFile.listFiles();
					        if (files != null) {
					            for (File f : files) {
					            	if(f.getName().contains("bak"))
					            	{
					            		filePath=f.getAbsolutePath();
					            		outFiles.setBakFilePath(filePath);
					            	}
					            }
					        }
						}
					}
					else
					{
						out.write("{success:false,msg:'找不到该日期的完整备份文件!'}");
						return;
					}
					out.write("{success:true,data:"+JSONObject.fromObject(outFiles).toString()+"}");
				}
				else
				{
					out.write("{success:false,msg:'暂时没有创建备份计划，找不到备份文件!'}");
				}
			}
		} catch (Exception e) {
			log.error("DatabaseAction-->getPath:",e);
			out.write("{success:false}");
		}
	}
	
	//遍历文件夹目录
	public void fileList(String path,String fileName) 
	{
		File file=new File(path);
		if(file.isDirectory())
		{
	        File[] files = file.listFiles();
	        if (files != null) {
	            for (File f : files) {
	                if(f.isDirectory())
	                {
	                	fileList(f.getAbsolutePath(),fileName);
	                }
	                else
	                {
	                    if(fileName.equals(f.getName()))
		                {
	                    	filePath=f.getAbsolutePath();
	                    	break;
		                }
	                }
	            }
	        }
		}
   }

	public void setBackupScheduleService(
			IHod2000DbBackupScheduleService backupScheduleService) {
		this.backupScheduleService = backupScheduleService;
	}

	public Hod2000DbBackupSchedule getBackupSchedule() {
		return backupSchedule;
	}

	public void setBackupSchedule(Hod2000DbBackupSchedule backupSchedule) {
		this.backupSchedule = backupSchedule;
	}

}
