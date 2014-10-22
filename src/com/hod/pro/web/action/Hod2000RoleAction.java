package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000Permission;
import com.hod.pojo.Hod2000Role;
import com.hod.pro.model.service.IHod2000OperatorService;
import com.hod.pro.model.service.IHod2000PermissionService;
import com.hod.pro.model.service.IHod2000RoleService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000RoleAction 角色管理
 * @author yixiang
 */
public class Hod2000RoleAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000RoleService hod2000RoleService;
	private IHod2000PermissionService hod2000PermissionService;
	private IHod2000OperatorService hod2000OperatorService;
	private Hod2000Role hod2000Role;
	private List dataList;
	private Message msg=Message.getInstance();
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000RoleAction.class.getName());
	private PrintWriter out;
	private MyJsonOut myjsonOut = new MyJsonOut();
	
	public String doSave() {
		try {
			request=ServletActionContext.getRequest();
			String rname=request.getParameter("rname");
			String[] rolePermissionRight=request.getParameterValues("rolePermissionRight");
			int[] ids=new int[rolePermissionRight.length];
			for(int i=0;i<rolePermissionRight.length;i++)
			{   
				ids[i]=Integer.parseInt(rolePermissionRight[i]);
			}
			Arrays.sort(ids);//排序
			String rolePermissions="";
			for (int i = 0; i < ids.length; i++) {
				rolePermissions+=ids[i]+",";
			}
			rolePermissions=rolePermissions.substring(0,rolePermissions.length()-1);
			hod2000Role=new Hod2000Role();
			//角色名称不能重复
			if(rname!=null&&!rname.equals(""))
			{
				//根据角色名称查询
				DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Role.class);
				dc.add(Restrictions.eq("rname", rname));
				List list=hod2000RoleService.findByCriteria(dc);
				if(list.size()>0)
				{
					msg.setError1("角色名称不能重复添加");
					msg.setJump(false);
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Roles!toAdd.do\">重新添加角色</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				if(hod2000RoleService.findByRolePermission(rolePermissions)>0)
				{
					msg.setError1("已经存在该权限的角色名称，请重新添加!");
					msg.setJump(false);
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Roles!toAdd.do\">重新添加角色</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Role.setRpurview(rolePermissions);
				hod2000Role.setRname(rname);
				hod2000RoleService.save(hod2000Role);
				msg.setMsg1("角色添加成功");
				OperatorLog.addOperatorLog("角色的添加");
			}
		} catch (Exception e) {
			//设置错误信息
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000RoleAction-->doSave:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/rolelist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	public String toAdd()
	{
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc2=DetachedCriteria.forClass(Hod2000Permission.class);
			List permissionList=hod2000PermissionService.findByCriteria(dc2);
			request.setAttribute("permissionList", permissionList);
			return "toAdd";
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoleAction-->toAdd:",e);
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/show/rolelist.jsp\">返回列表</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}
    
	public void doDelete() {
		try {
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
			String[] ids = request.getParameterValues("delIds");
			if (ids!=null&&!ids.equals("")) {
				//角色下面有用户时先解除用户关系
				int count=0;
				for (int i = 0; i < ids.length; i++) {
					//是否为系统管理员
					if("1".equals(ids[i]))
					{
						out.write("{success:false,msg:'超级管理员不能进行删除，请重新选择'}");
						return;
					}
					//是否存在操作员
					count=hod2000OperatorService.findByRoleId(Integer.parseInt(ids[i]));
					if(count>0)
					{
						hod2000Role=(Hod2000Role) hod2000RoleService.findById(Integer.parseInt(ids[i]));
						out.write("{success:false,msg:'请先删除("+hod2000Role.getRname()+")角色中的操作员再进行角色删除!'}");
						return;
					}
				}
				hod2000RoleService.deleteByParam(ids);//注意主键类型
				out.write("{success:true,msg:'角色删除成功!'}");
				OperatorLog.addOperatorLog("角色删除");
			}else{
				out.write("{success:false,msg:'ID无效'}");
			}
		} catch (Exception e) {
			log.error("Hod2000RoleAction-->doDelete:",e);
			out.write("{success:false,msg:'操作失败!'}");
		}
	}
    
	public String toUpdate() {
		try {
			request=ServletActionContext.getRequest();
			String roleId=request.getParameter("id");
			if(roleId==null||"".equals(roleId))
			{
				msg.setError1("ID无效");
				msg.setLink(0, "<a href='"+request.getContextPath()
						+ "/show/rolelist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Role = (Hod2000Role) hod2000RoleService.findById(Integer.parseInt(roleId));
			//查询角色已经拥有的权限和未拥有的权限
			List permissionList=hod2000PermissionService.findByRole(hod2000Role.getRpurview());
			List permissionListNotIn=hod2000PermissionService.findByRoleNotIn(hod2000Role.getRpurview());
			request.setAttribute("permissionList", permissionList);
			request.setAttribute("permissionListNotIn", permissionListNotIn);
			request.setAttribute("hod2000Role", hod2000Role);
			return "toUpdate";
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000RoleAction-->toUpdate:",e);
			
		}
		msg.setLink(0, "<a href='"+request.getContextPath()
				+ "/show/rolelist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}  

	public String doUpdate() {
		try {
			request=ServletActionContext.getRequest();
			String rname=request.getParameter("rname");
			String[] rolePermissionRight=request.getParameterValues("rolePermissionRight");
			int[] ids=new int[rolePermissionRight.length];
			for(int i=0;i<rolePermissionRight.length;i++)
			{   
				ids[i]=Integer.parseInt(rolePermissionRight[i]);
			}
			Arrays.sort(ids);//排序
			String rolePermissions="";
			for (int i = 0; i < ids.length; i++) {
				rolePermissions+=ids[i]+",";
			}
			rolePermissions=rolePermissions.substring(0,rolePermissions.length()-1);
			hod2000Role=(Hod2000Role) hod2000RoleService.findById(hod2000Role.getRid());
			if(!rname.equals(hod2000Role.getRname()))
			{
				int count=hod2000RoleService.findByRoleName(rname);
				if(count>0)
				{
					msg.setError1("已经存在该角色名称!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/rolelist.jsp\">返回列表</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Role.setRname(rname);
			}
			if(hod2000RoleService.findByRolePermission(rolePermissions,hod2000Role.getRid())>0)
			{
				msg.setError1("已经存在该权限的角色名称!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/rolelist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Role.setRpurview(rolePermissions);
			hod2000RoleService.update(hod2000Role);
			msg.setMsg1("角色修改成功");
			OperatorLog.addOperatorLog("角色修改");
		} catch (Exception e) {
			//设置错误信息
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000RoleAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/rolelist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("角色信息查询");
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
    		int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Role.class);
			dataList=hod2000RoleService.findByCriteria(page, pageSize, dc);
			List list=new ArrayList();
			Map map;
			for (int i = 0; i < dataList.size(); i++) {
				map=new HashMap();
				hod2000Role=(Hod2000Role) dataList.get(i);
				map.put("rname", hod2000Role.getRname());
				map.put("id", hod2000Role.getRid());
				list.add(map);
			}
			int totalRecord =hod2000RoleService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000RoleAction->doSelect:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000RoleAction->doSelect:"+e);
		}
	}    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000RoleService(IHod2000RoleService hod2000RoleService) {
		this.hod2000RoleService = hod2000RoleService;
	}
 
	public Hod2000Role getHod2000Role() {
		return hod2000Role;
	}

	public void setHod2000Role(Hod2000Role hod2000Role) {
		this.hod2000Role = hod2000Role;
	}

	public IHod2000PermissionService getHod2000PermissionService() {
		return hod2000PermissionService;
	}

	public void setHod2000PermissionService(
			IHod2000PermissionService hod2000PermissionService) {
		this.hod2000PermissionService = hod2000PermissionService;
	}

	public void setHod2000OperatorService(
			IHod2000OperatorService hod2000OperatorService) {
		this.hod2000OperatorService = hod2000OperatorService;
	}
	
}
