package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000MeterType;
import com.hod.pro.model.service.IHod2000MeterTypeService;
import com.hod.pro.model.service.IHod2000SysparameterService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000MeterTypeAction 表计类型
 * @author yixiang
 */
public class Hod2000MeterTypeAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000MeterTypeService hod2000MeterTypeService;
	private IHod2000SysparameterService hod2000SysparameterService;
	private Hod2000MeterType hod2000MeterType;
	private List dataList;
	private Message msg=Message.getInstance();
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000MeterTypeAction.class.getName());
	private PrintWriter out;
	private MyJsonOut myjsonOut = new MyJsonOut();
	/**
	 * 查询通信规约下拉框，转发到表计类型添加界面
	 * @return
	 */
	public String toAdd()
	{
		request=ServletActionContext.getRequest();
		request.setAttribute("protocol", hod2000SysparameterService.findByType(2));
		return "add";
	}
	
	public String doSave() {
		try {
			request=ServletActionContext.getRequest();
			//hod2000MeterType.setCompanyNum(hod2000MeterType.getCompanyNum().toUpperCase());//厂商编码都为大写
			//检查是否存在相同表计型号
			int count=hod2000MeterTypeService.findByName(hod2000MeterType.getTypeName());
			if(count>0)
			{
				msg.setError1("已经存在相同的表计型号");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000MeterType!toAdd.do\">请重新添加</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000MeterTypeService.save(hod2000MeterType);
			msg.setMsg1("添加表计型号成功!");
			OperatorLog.addOperatorLog("添加表计型号");
		} catch (Exception e) {
			log.error("Hod2000MeterTypeAction-->doSelect",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/metertypelist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doDelete() {
		try {
			request=ServletActionContext.getRequest();
			String ids = request.getParameter("delIds");
			if (ids!=null&&!ids.equals("")) {
				ids = Page.convertKey(ids);
				hod2000MeterTypeService.deleteByParam(ids.split(","));//注意主键类型
				msg.setMsg1("删除表计型号成功!");
				OperatorLog.addOperatorLog("删除表计型号");
			}else{
				msg.setMsg1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/metertypelist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			log.error("Hod2000MeterTypeAction-->doDelete",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/metertypelist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String toUpdate() {
		try {
			request=ServletActionContext.getRequest();
			String id=request.getParameter("id");
			if(id!=null&&!id.equals(""))
			{
				hod2000MeterType = (Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(id));
				request.setAttribute("num1", hod2000MeterType.getCompanyNum().substring(0,2));
				request.setAttribute("num2", hod2000MeterType.getCompanyNum().substring(2,4));
				request.setAttribute("num3", hod2000MeterType.getCompanyNum().substring(4,6));
				request.setAttribute("hod2000MeterType", hod2000MeterType);
			}
			else
			{
				msg.setMsg1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/metertypelist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			request.setAttribute("protocol", hod2000SysparameterService.findByType(2));
			return "toUpdate";
		} catch (Exception e) {
			log.error("Hod2000MeterTypeAction-->toUpdate",e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	public String doUpdate() {
		try {
			request=ServletActionContext.getRequest();
			//检查是否存在相同表计型号
			int count=hod2000MeterTypeService.findByName(hod2000MeterType.getTypeName(),hod2000MeterType.getTypeIndex());
			if(count>0)
			{
				msg.setError1("已经存在相同的表计型号，请重新修改!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/metertypelist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			//hod2000MeterType.setCompanyNum(hod2000MeterType.getCompanyNum().toUpperCase());//厂商编码都为大写
			hod2000MeterTypeService.update(hod2000MeterType);
			msg.setMsg1("修改表计型号成功!");
			OperatorLog.addOperatorLog("修改表计型号");
		} catch (Exception e) {
			log.error("Hod2000MeterTypeAction-->doUpdate",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/metertypelist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		request.setAttribute("message", msg);
		return "message";
	}
    
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("表计型号查询");
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
    		int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000MeterType.class);
			dataList=hod2000MeterTypeService.findByCriteria(page, pageSize, dc);
			List list=new ArrayList();
			Map map;
			for (int i = 0; i < dataList.size(); i++) {
				map=new HashMap();
				hod2000MeterType=(Hod2000MeterType) dataList.get(i);
				map.put("id", hod2000MeterType.getTypeIndex());
				map.put("typeName", hod2000MeterType.getTypeName());
				map.put("typeCode", hod2000MeterType.getTypeCode());
				map.put("companyName", hod2000MeterType.getCompanyName());
				map.put("companyNum", hod2000MeterType.getCompanyNum());
				map.put("communicationProtocol", hod2000MeterType.getCommunicationProtocol());
				list.add(map);
			}
			int totalRecord =hod2000MeterTypeService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000MeterTypeAction-->doSelect:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterTypeAction-->doSelect:",e);
		}
	}    
    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000MeterTypeService(IHod2000MeterTypeService hod2000MeterTypeService) {
		this.hod2000MeterTypeService = hod2000MeterTypeService;
	}
 
	public Hod2000MeterType getHod2000MeterType() {
		return hod2000MeterType;
	}

	public void setHod2000MeterType(Hod2000MeterType hod2000MeterType) {
		this.hod2000MeterType = hod2000MeterType;
	}

	public void setHod2000SysparameterService(
			IHod2000SysparameterService hod2000SysparameterService) {
		this.hod2000SysparameterService = hod2000SysparameterService;
	}
    
}
