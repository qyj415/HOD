package  com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.hod.pojo.Hod2000OperateLog;
import com.hod.pro.model.service.IHod2000OperateLogService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000OperateLogAction 操作员日志管理
 * @author yixiang
 */
public class Hod2000OperateLogAction extends ActionSupport {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private IHod2000OperateLogService hod2000OperateLogService;
	private Hod2000OperateLog hod2000OperateLog;
	private List dataList;
	Message msg=Message.getInstance();
	private PrintWriter out;
	private MyJsonOut myjsonOut = new MyJsonOut();
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000OperateLogAction.class.getName());
	
	public void doDelete() {
		try {
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
			String[] ids=request.getParameterValues("delIds");
			if (ids!=null&&!ids.equals("")) {
				hod2000OperateLogService.deleteByParam(ids);//注意主键类型
				OperatorLog.addOperatorLog("删除操作日志");
				out.write("{success:true,msg:'删除操作日志成功!'}");
			}else{
				out.write("{success:false,msg:'ID无效!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000OperateLogAction-->doDelete:",e);
			out.write("{success:false,msg:'删除失败!'}");
		}
	}
    
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("操作日志查询");
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
    		int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String operName=request.getParameter("operName");
			String sql="select log_id,oper_name,log_date,log_content from hod2000_operatelog where 1=1";
			String count="select count(*) from hod2000_operatelog where 1=1";
			if(startTime!=null&&!startTime.equals(""))
			{
				sql+=" and log_date>='"+startTime+"'";
				count+=" and log_date>='"+startTime+"'";
			}
			if(endTime!=null&&!endTime.equals(""))
			{
				sql+=" and log_date<='"+endTime+"'";
				count+=" and log_date<='"+endTime+"'";
			}
			if(operName!=null&&!operName.equals(""))
			{
				sql+=" and oper_name like '"+operName+"%'";
				count+=" and oper_name like '"+operName+"%'";
			}
			sql+=" order by log_date desc";
			List list=hod2000OperateLogService.findByNHQL(page, pageSize, sql);
			Map map;
			Object[] objects;
			dataList=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				objects=(Object[]) list.get(i);
				map=new HashMap();
				map.put("id", objects[0]);
				map.put("operName", objects[1]);
				map.put("logDate", Utils.dateToStrLong(Utils.strToDateLong(objects[2].toString())));
				map.put("logContent", objects[3]);
				dataList.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000OperateLogService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000OperateLogAction->doSelect:",dataList,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000OperateLogAction-->doSelect:",e);
		}
	}    
    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000OperateLogService(IHod2000OperateLogService hod2000OperateLogService) {
		this.hod2000OperateLogService = hod2000OperateLogService;
	}
 
	public Hod2000OperateLog getHod2000OperateLog() {
		return hod2000OperateLog;
	}

	public void setHod2000OperateLog(Hod2000OperateLog hod2000OperateLog) {
		this.hod2000OperateLog = hod2000OperateLog;
	}
}
