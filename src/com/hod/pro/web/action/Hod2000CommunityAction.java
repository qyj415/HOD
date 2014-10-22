package  com.hod.pro.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Community;
import com.hod.pojo.Hod2000Village;
import com.hod.pro.model.service.IHod2000CommunityService;
import com.hod.pro.model.service.IHod2000VillageService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000CommunityAction 小区管理
 * @author yixiang
 */
public class Hod2000CommunityAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000CommunityService hod2000CommunityService;
	private Hod2000Community hod2000Community;
	private IHod2000VillageService hod2000VillageService; 
	private List dataList;
	Message msg=Message.getInstance();
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000CommunityAction.class.getName());
	
	public String doSave() {
		request = ServletActionContext.getRequest();
		String villageId=request.getParameter("villageId");
		try {
			Hod2000Village hod2000Village=(Hod2000Village) hod2000VillageService.findById(Integer.parseInt(villageId));
			//判断该街道下面小区名称是否重复
			int count=hod2000CommunityService.findByCommunityName(hod2000Village.getVillageId(),hod2000Community.getCommunityName());
			if(count>0)
			{
				msg.setError1("该街道下面不能有相同的小区名称");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/form/communityadd.jsp?villageId="+villageId+"\">重新添加</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Community.setHod2000Village(hod2000Village);
			hod2000Community.setCommunityCode(hod2000CommunityService.getCode(Integer.parseInt(villageId)));
			hod2000CommunityService.save(hod2000Community);
			msg.setMsg1("小区信息添加成功!");
			OperatorLog.addOperatorLog("小区信息添加");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000CommunityAction-->doSave",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Community!doSelect.do?villageId="+villageId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doDelete() {
		request = ServletActionContext.getRequest();
		String villageId=request.getParameter("villageId");
		String ids = request.getParameter("delIds");
		try {
			if (ids!=null&&!ids.equals("")) {
				ids = Page.convertKey(ids);
				
				//添加删除时判断是否存在关联
				String communities[] = ids.split(",");
				for(int i=0;i<communities.length;i++){
					Hod2000Community community = (Hod2000Community)hod2000CommunityService.findById(Integer.parseInt(communities[i]));
					//Set<Hod2000Building> buildings = community.getHod2000Buildings();
					List buildings=hod2000CommunityService.findByNHQL("select building_id from hod2000_building where community_id="+community.getCommunityId());
					if(buildings.size()>0){
						msg.setError1("请先删除此小区下的楼栋!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Community!doSelect.do?villageId="+villageId+"\">返回列表</a>");
						request.setAttribute("message", msg);
						return "message";
					}
				}
				//添加删除时判断是否存在关联 end
				
				hod2000CommunityService.deleteByParam(communities);//注意主键类型
				msg.setMsg1("删除小区信息成功!");
				OperatorLog.addOperatorLog("小区信息删除");
			}else{
				msg.setError1("ID为空!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Community!doSelect.do?villageId="+villageId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000CommunityAction-->doDelete",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Community!doSelect.do?villageId="+villageId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String toUpdate() {
		try {
			request = ServletActionContext.getRequest();
			String id=request.getParameter("id");
			if(id!=null&&!id.equals(""))
			{
				hod2000Community = (Hod2000Community) hod2000CommunityService.findById(Integer.parseInt(id));
				request.setAttribute("hod2000Community", hod2000Community);
			}
			else
			{
				msg.setMsg1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Community!doSelect.do?villageId="+hod2000Community.getHod2000Village().getVillageId()+"\">返回列表</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			return "toUpdate";
		} catch (Exception e) {
			//设置错误信息
			msg.setError1("错误信息："+e.toString());
			msg.setLink(0, "<a href='"+request.getContextPath()
					+ "/hod2000Community!doSelect.do?villageId="+hod2000Community.getHod2000Village().getVillageId()+"\">返回列表</a>");
			log.error("Hod2000CommunityAction-->toUpdate",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	public String doUpdate() {
		try {
			request = ServletActionContext.getRequest();
			String communityId=request.getParameter("communityId");
			String communityName=request.getParameter("communityName");
			String communityRemark=request.getParameter("communityRemark");
			hod2000Community=(Hod2000Community) hod2000CommunityService.findById(Integer.parseInt(communityId));
			//判断小区是否存在楼栋
			List buildings=hod2000CommunityService.findByNHQL("select building_id from hod2000_building where community_id="+Integer.parseInt(communityId));
			if(buildings.size()>0){
				msg.setError1("该小区下已存在楼栋信息，不能修改小区信息!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Community!doSelect.do?villageId="+hod2000Community.getHod2000Village().getVillageId()+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
//			Set<Hod2000Building> buildings = hod2000Community.getHod2000Buildings();
//			if(buildings.size()>0){
//				msg.setError1("该小区下已存在楼栋信息，不能修改小区信息!");
//				msg.setJump(false);
//				msg.setLink(0, "<a href=\"" + request.getContextPath()
//						+ "/hod2000Community!doSelect.do?villageId="+hod2000Community.getHod2000Village().getVillageId()+"\">返回列表</a>");
//				request.setAttribute("message", msg);
//				return "message";
//			}
			//判断该街道下面小区名称是否重复
			int count=hod2000CommunityService.findByCommunityName(hod2000Community.getHod2000Village().getVillageId(),communityName,hod2000Community.getCommunityId());
			if(count>0)
			{
				msg.setError1("该街道下面不能有相同的小区名称");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Community!doSelect.do?villageId="+hod2000Community.getHod2000Village().getVillageId()+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Community.setCommunityName(communityName);
			hod2000Community.setCommunityRemark(communityRemark);
			hod2000CommunityService.update(hod2000Community);
			msg.setMsg1("修改小区信息成功!");
			OperatorLog.addOperatorLog("小区信息修改");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000CommunityAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Community!doSelect.do?villageId="+hod2000Community.getHod2000Village().getVillageId()+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doSelect() {
		try {
			request = ServletActionContext.getRequest();
			int villageId=Integer.parseInt(request.getParameter("villageId"));
			Hod2000Village village = (Hod2000Village)hod2000VillageService.findById(villageId);
			String sql="select community_id,community_name,community_remark from hod2000_community where village_id="+villageId;
			dataList=Page.findBySql(request, hod2000CommunityService, sql);
			Map map;
			Object[] obj;
			List list=new ArrayList();
			for (int i = 0; i < dataList.size(); i++) {
				obj=(Object[]) dataList.get(i);
				map=new HashMap();
				map.put("communityId", obj[0]);
				map.put("communityName", obj[1]);
				map.put("communityRemark", obj[2]);
				list.add(map);
			}
			request.setAttribute("villageName", village.getVillageName());
			request.setAttribute("villageId", villageId);
			request.setAttribute("list", list);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}    
    
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000CommunityService(IHod2000CommunityService hod2000CommunityService) {
		this.hod2000CommunityService = hod2000CommunityService;
	}
 
	public Hod2000Community getHod2000Community() {
		return hod2000Community;
	}

	public void setHod2000Community(Hod2000Community hod2000Community) {
		this.hod2000Community = hod2000Community;
	}

	public IHod2000VillageService getHod2000VillageService() {
		return hod2000VillageService;
	}

	public void setHod2000VillageService(
			IHod2000VillageService hod2000VillageService) {
		this.hod2000VillageService = hod2000VillageService;
	}

}
