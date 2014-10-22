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

import com.hod.pojo.Hod2000Operator;
import com.hod.pojo.Hod2000Role;
import com.hod.pro.model.service.IHod2000OperatorService;
import com.hod.pro.model.service.IHod2000RoleService;
import com.hod.util.MD5;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000OperatorAction 操作员管理
 * @author yixiang
 */
public class Hod2000OperatorAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000OperatorService hod2000OperatorService;
	private Hod2000Operator hod2000Operator;
	private IHod2000RoleService hod2000RoleService;
	private List dataList;
	private Message msg=Message.getInstance();
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000OperatorAction.class.getName());
	private PrintWriter out;
	private MyJsonOut myjsonOut = new MyJsonOut();
	
	public String doSave() {
		try {
			request=ServletActionContext.getRequest();
			String oper_enable=request.getParameter("oper_enable");//有效性
			String operLoginName=request.getParameter("operLoginName");//用户名
			String operLoginPass=request.getParameter("operLoginPass");//登陆密码
			String oper_role=request.getParameter("oper_role");//操作员角色
			hod2000Operator=new Hod2000Operator();
			//检查登陆名的唯一性
			List list=hod2000OperatorService.findByLoginName(operLoginName);
			if(list.size()>0)
			{
				msg.setError1("该登陆名已经存在!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Operator!toAdd.do\">重新添加</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Operator.setLoginName(operLoginName);
			hod2000Operator.setOperEnable(Integer.parseInt(oper_enable));
			Hod2000Role hod2000Role=(Hod2000Role) hod2000RoleService.findById(Integer.parseInt(oper_role));
			hod2000Operator.setHod2000Role(hod2000Role);
			hod2000Operator.setOperPassword(MD5.getMd5(operLoginPass));
			hod2000OperatorService.save(hod2000Operator);
			msg.setMsg1("新增操作员成功!");
			OperatorLog.addOperatorLog("添加操作员");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000OperatorAction-->doSave:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/operatorlist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public void doDelete() {
		try {
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
			String[] ids = request.getParameterValues("delIds");
			if (ids!=null&&!ids.equals("")) {
				for (int i = 0; i < ids.length; i++) {
					hod2000Operator=(Hod2000Operator) hod2000OperatorService.findById(Integer.parseInt(ids[i]));
					if("admin".equals(hod2000Operator.getLoginName()))
					{
						out.write("{success:false,msg:'admin用户不能删除，请重新选择!'}");
						return;
					}
				}
				hod2000OperatorService.deleteByParam(ids);//注意主键类型
				out.write("{success:true,msg:'删除操作员成功!'}");
				OperatorLog.addOperatorLog("删除操作员");
			}else{
				out.write("{success:false,msg:'ID为空!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000OperatorAction-->doDelete:",e);
			out.write("{success:false,msg:'操作失败!'}");
		}
	}
	
	public String toAdd()
	{
		request=ServletActionContext.getRequest();
		//查询角色
		DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Role.class);
		List roleList=hod2000RoleService.findByCriteria(dc);
		request.setAttribute("roleList", roleList);
		return "toAdd";
	}
    
	public String toUpdate() {
		try {
			request=ServletActionContext.getRequest();
			String operatorId=request.getParameter("id");
			if(operatorId!=null&&!operatorId.equals(""))
			{
				if ("admin".equals(operatorId)){
					msg.setError1("'admin' 用户不能修改！");
					msg.setLink(0, "<a href='"+request.getContextPath()
							+ "/show/operatorlist.jsp\">返回列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);	
					return "message";
				}
				hod2000Operator = (Hod2000Operator) hod2000OperatorService.findById(Integer.parseInt(operatorId));
			}
			else
			{
				msg.setMsg1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/operatorlist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//查询角色
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Role.class);
			List roleList=hod2000RoleService.findByCriteria(dc);
			request.setAttribute("hod2000Operator", hod2000Operator);
			request.setAttribute("roleList", roleList);
			return "toUpdate";
		} catch (Exception e) {
			//设置错误信息
			msg.setError1("错误信息："+e.toString());
			msg.setLink(0, "<a href='"+request.getContextPath()
					+ "/show/operatorlist.jsp\">返回列表</a>");
			log.error("",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	public String doUpdate() {
		try {
			request=ServletActionContext.getRequest();
			hod2000Operator=(Hod2000Operator) hod2000OperatorService.findById(hod2000Operator.getOperId());
			String loginName=request.getParameter("loginName");
			String operPassword=request.getParameter("operPassword");
			String hod2000Role=request.getParameter("hod2000Role");
			String operEnable=request.getParameter("operEnable");
			if(loginName!=null&&!"".equals(loginName))
			{
				if(!loginName.equals(hod2000Operator.getLoginName()))
				{
					//判断是否重复
					List list=hod2000OperatorService.findByLoginName(loginName);
					if(list.size()>0)
					{
						msg.setError1("该登陆名已经存在!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/operatorlist.jsp\">重新添加</a>");
						request.setAttribute("message", msg);
						return "message";
					}
					hod2000Operator.setLoginName(loginName);
				}
			}
			if(operPassword!=null&&!"".equals(operPassword))
			{
				hod2000Operator.setOperPassword(MD5.getMd5(operPassword));
			}
			hod2000Operator.setHod2000Role((Hod2000Role) hod2000RoleService.findById(Integer.parseInt(hod2000Role)));
			hod2000Operator.setOperEnable(Integer.parseInt(operEnable));
			hod2000OperatorService.update(hod2000Operator);
			msg.setMsg1("修改操作员信息成功!");
			OperatorLog.addOperatorLog("修改操作员信息");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000OperatorAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/operatorlist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("操作员信息查询");
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
    		int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Operator.class);
			dataList=hod2000OperatorService.findByCriteria(page, pageSize, dc);
			Map map;
			List list=new ArrayList();
			for (int i = 0; i <dataList.size(); i++) {
				map=new HashMap();
				hod2000Operator=(Hod2000Operator) dataList.get(i);
				map.put("id", hod2000Operator.getOperId());
				map.put("loginName", hod2000Operator.getLoginName());
				map.put("operEnable", hod2000Operator.getOperEnable());
				map.put("rname", hod2000Operator.getHod2000Role().getRname());
				list.add(map);
			}
			int totalRecord =hod2000OperatorService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000OperatorAction->doSelect:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000OperatorAction-->doSelect:"+e);
		}
	}    
	
	//密码修改
	public String doChangePwd()
	{
		try {
			request=ServletActionContext.getRequest();
			String pwd=request.getParameter("pwd");
			hod2000Operator=(Hod2000Operator) hod2000OperatorService.findById(hod2000Operator.getOperId());
			if(pwd!=null&&!pwd.equals(""))
			{
				hod2000Operator.setOperPassword(MD5.getMd5(pwd));
				hod2000OperatorService.update(hod2000Operator);
				msg.setMsg1("修改用户密码成功，新密码在下次登陆的时候生效!");
				OperatorLog.addOperatorLog("操作员密码修改");
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000OperatorAction-->doChangePwd:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/update/changepwd.jsp\">返回</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000OperatorService(IHod2000OperatorService hod2000OperatorService) {
		this.hod2000OperatorService = hod2000OperatorService;
	}
 
	public Hod2000Operator getHod2000Operator() {
		return hod2000Operator;
	}

	public void setHod2000Operator(Hod2000Operator hod2000Operator) {
		this.hod2000Operator = hod2000Operator;
	}

	public IHod2000RoleService getHod2000RoleService() {
		return hod2000RoleService;
	}

	public void setHod2000RoleService(IHod2000RoleService hod2000RoleService) {
		this.hod2000RoleService = hod2000RoleService;
	}
	
}
