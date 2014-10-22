package com.hod.util;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hod.pojo.Hod2000OperateLog;
import com.hod.pojo.Hod2000Operator;
import com.hod.pro.model.service.IHod2000OperateLogService;

public class OperatorLog {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
	private static IHod2000OperateLogService hod2000OperateLogService=(IHod2000OperateLogService) ctx.getBean("springHod2000OperateLogService");
	private static Hod2000OperateLog hod2000OperateLog = new Hod2000OperateLog();
	
	public static void addOperatorLog(String logContent)
	{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Hod2000Operator user = (Hod2000Operator) request.getSession().getAttribute("user");
			hod2000OperateLog.setLogDate(new Date());// 发生时间;
			hod2000OperateLog.setLogContent(logContent);//日志内容
			hod2000OperateLog.setOperName(user.getLoginName());//操作员
			hod2000OperateLogService.save(hod2000OperateLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
