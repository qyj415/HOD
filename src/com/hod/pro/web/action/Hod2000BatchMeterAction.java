package com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.hod.pojo.Hod2000BatchMeterError;
import com.hod.pro.model.service.IHod2000BatchMeterErrorService;
import com.hod.util.Message;


public class Hod2000BatchMeterAction {
	
	private IHod2000BatchMeterErrorService hod2000BatchMeterErrorService;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private List dataList;
	private PrintWriter out;
	private Message msg = Message.getInstance();
	private Logger log = Logger.getLogger(Hod2000BatchMeterAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();

	
	public void doSelect()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000BatchMeterError.class);
			dataList=hod2000BatchMeterErrorService.findByCriteria(dc);
			int totalRecord =(Integer)hod2000BatchMeterErrorService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000BatchMeterAction-->doSelect:",dataList,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000BatchMeterAction-->doSelect:", e);
		}
	}
	
	//错误信息清除
	public void doClear()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			hod2000BatchMeterErrorService.executeUpdate("DELETE FROM Hod2000BatchMeterError");
			out.write("{success:true}");
		} catch (Exception e) {
			msg.setError1("Hod2000BatchMeterErrorAction-->doClear:"+e);
			out.write("{success:false}");
		}
	}


	public IHod2000BatchMeterErrorService getHod2000BatchMeterErrorService() {
		return hod2000BatchMeterErrorService;
	}


	public void setHod2000BatchMeterErrorService(
			IHod2000BatchMeterErrorService hod2000BatchMeterErrorService) {
		this.hod2000BatchMeterErrorService = hod2000BatchMeterErrorService;
	}
}
