package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Community;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Sysparameter;
import com.hod.pojo.Hod2000Unit;
import com.hod.pro.model.service.IHod2000BuildingService;
import com.hod.pro.model.service.IHod2000CommunityService;
import com.hod.pro.model.service.IHod2000SysparameterService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000BuildingAction 楼栋管理
 * @author yixiang
 */
public class Hod2000BuildingAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000BuildingService hod2000BuildingService;
	private IHod2000CommunityService hod2000CommunityService;
	private Hod2000Building hod2000Building;
	private IHod2000SysparameterService hod2000SysparameterService;
	private List dataList;
	Message msg=Message.getInstance();
	private PrintWriter out;
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000BuildingAction.class.getName());
	
	public String doSave() {
		String communityId = "";
		try {
			request = ServletActionContext.getRequest();
			communityId=request.getParameter("communityId");
			Hod2000Community hod2000Community=(Hod2000Community) hod2000CommunityService.findById(Integer.parseInt(communityId));
			//判断该小区下面是否已经存在该楼栋名称
			int count=hod2000BuildingService.findByBuildingName(hod2000Community.getCommunityId(),hod2000Building.getBuildingName());
			if(count>0)
			{
				msg.setError1("该小区下面不能有相同的楼栋名称");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/form/buildingadd.jsp?communityId="+communityId+"\">重新添加</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Building.setHod2000Community(hod2000Community);
			hod2000Building.setBuildingCode(hod2000BuildingService.getCode(Integer.parseInt(communityId)));
			hod2000BuildingService.save(hod2000Building);
			msg.setMsg1("楼栋信息添加成功!");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000BuildingAction-->doSave",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doDelete() {
		String communityId = "";
		try {
			request = ServletActionContext.getRequest();
			communityId=request.getParameter("communityId");
			String ids = request.getParameter("delIds");
			if (ids!=null&&!ids.equals("")) {
				ids = Page.convertKey(ids);
				
				//添加删除时判断是否存在关联
				String buildings[] = ids.split(",");
				for(int i=0;i<buildings.length;i++){
					Hod2000Building building = (Hod2000Building)hod2000BuildingService.findById(Integer.parseInt(buildings[i]));
//					Set<Hod2000Room> rooms = building.getHod2000Rooms();
//					if(rooms.size()>0){
//						msg.setError1("请先删除此楼栋下的房间!");
//						msg.setJump(false);
//						msg.setLink(0, "<a href=\"" + request.getContextPath()
//								+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
//						request.setAttribute("message", msg);
//						return "message";
//					}
					List units=hod2000BuildingService.findByNHQL("select unit_id from hod2000_unit where building_id="+building.getBuildingId());
					if(units.size()>0){
						msg.setError1("请先删除此楼栋下的单元!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
						request.setAttribute("message", msg);
						return "message";
					}
					
				}
				//添加删除时判断是否存在关联 end
				
				hod2000BuildingService.deleteByParam(buildings);//注意主键类型
				msg.setMsg1("删除楼栋信息成功!");
				OperatorLog.addOperatorLog("删除楼栋信息");
			}else{
				msg.setError1("ID为空!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000BuildingAction-->doDelete",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String toUpdate() {
		String communityId = "";
		try {
			request = ServletActionContext.getRequest();
			communityId=request.getParameter("communityId");
			String id=request.getParameter("id");
			if(id!=null&&!id.equals(""))
			{
				hod2000Building = (Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(id));
				request.setAttribute("hod2000Building", hod2000Building);
			}
			else
			{
				msg.setMsg1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			return "toUpdate";
		} catch (Exception e) {
			//设置错误信息
			msg.setError1("错误信息："+e.toString());
			msg.setLink(0, "<a href='"+request.getContextPath()
					+ "/hod2000Building!doSelect.do?communityId="+communityId+"\">返回列表</a>");
			log.error("Hod2000BuildingAction-->toUpdate",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	public String doUpdate() {
		try {
			request = ServletActionContext.getRequest();
			String buildingId=request.getParameter("buildingId");
			String buildingName=request.getParameter("buildingName");
			String buildingRemark=request.getParameter("buildingRemark");
			hod2000Building=(Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(buildingId));
			//判断是否存在房间
//			Set<Hod2000Room> rooms = hod2000Building.getHod2000Rooms();
//			if(rooms.size()>0){
//				msg.setError1("该楼栋下已存在房间信息，不能修改楼栋信息!");
//				msg.setJump(false);
//				msg.setLink(0, "<a href=\"" + request.getContextPath()
//						+ "/hod2000Building!doSelect.do?communityId="+hod2000Building.getHod2000Community().getCommunityId()+"\">返回列表</a>");
//				request.setAttribute("message", msg);
//				return "message";
//			}
			//Set<Hod2000Unit> units = hod2000Building.getHod2000Units();
			//if(units.size()>0){
			List units=hod2000BuildingService.findByNHQL("select unit_id from hod2000_unit where building_id="+Integer.parseInt(buildingId));
			if(units.size()>0){
				msg.setError1("该楼栋下已存在单元信息，不能修改楼栋信息!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Building!doSelect.do?communityId="+hod2000Building.getHod2000Community().getCommunityId()+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			//判断该小区下面是否已经存在该楼栋名称
			int count=hod2000BuildingService.findByBuildingName(hod2000Building.getHod2000Community().getCommunityId(),buildingName,hod2000Building.getBuildingId());
			if(count>0)
			{
				msg.setError1("该小区下面不能有相同的楼栋名称");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Building!doSelect.do?communityId="+hod2000Building.getHod2000Community().getCommunityId()+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Building.setBuildingName(buildingName);
			hod2000Building.setBuildingRemark(buildingRemark);
			hod2000BuildingService.update(hod2000Building);
			msg.setMsg1("修改楼栋信息成功!");
			OperatorLog.addOperatorLog("修改楼栋："+hod2000Building.getBuildingName());
		} catch (Exception e) {
			msg.setError1("Hod2000BuildingAction-->doUpdate:"+e);
			log.error("Hod2000BuildingAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Building!doSelect.do?communityId="+hod2000Building.getHod2000Community().getCommunityId()+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doSelect() {
		try {
			request = ServletActionContext.getRequest();
			int communityId=Integer.parseInt(request.getParameter("communityId"));
			Hod2000Community community = (Hod2000Community)hod2000CommunityService.findById(communityId);
			String sql="select building_id,building_name,building_remark from hod2000_building where community_id="+communityId;
			dataList=Page.findBySql(request, hod2000BuildingService, sql);
			Map map;
			Object[] obj;
			List list=new ArrayList();
			for (int i = 0; i < dataList.size(); i++) {
				obj=(Object[]) dataList.get(i);
				map=new HashMap();
				map.put("buildingId", obj[0]);
				map.put("buildingName", obj[1]);
				map.put("buildingRemark", obj[2]);
				list.add(map);
			}
			request.setAttribute("communityName", community.getCommunityName());
			request.setAttribute("communityId", communityId);
			request.setAttribute("list", list);
			return SUCCESS;
		} catch (Exception e) {
			msg.setError1("Hod2000BuildingAction-->doSelect:"+e);
			log.error("Hod2000BuildingAction-->doSelect:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}    
    
	//更新楼栋经纬度
	public void dosavelatlng()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("buildingId");
			String lat=request.getParameter("lat");
			String lng=request.getParameter("lng");
			hod2000Building=(Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(buildingId));
			hod2000Building.setBuildingLatitude(lat);
			hod2000Building.setBuildingLongitude(lng);
			hod2000BuildingService.update(hod2000Building);
			out.write("{success:true}");
			OperatorLog.addOperatorLog("地址信息地图中更新楼栋的经纬度");
		} catch (Exception e) {
			log.error("Hod2000BuildingAction-->dosavelatlng",e);
			out.write("{success:false}");
		}
	}
	
	//地图页面打开就显示当前区域内所有的楼栋图标
	public void getmarkbylatlng()
	{
		try {
			request =  ServletActionContext.getRequest();
	    	out = ServletActionContext.getResponse().getWriter();
	    	String lng1	=	request.getParameter("lng1");
			String lng2	=	request.getParameter("lng2");
			String lat1	=	request.getParameter("lat1");
			String lat2	=	request.getParameter("lat2");
			//根据经纬度查询楼栋图标
			List list=hod2000BuildingService.findByLngLat(lng1,lng2,lat1,lat2);
			List list2 =  new ArrayList();
			Object[] objects;
			Map map;
			int buildingId=0;
			for (int i = 0; i < list.size(); i++) {
				map = new HashMap();   
			    objects=(Object[])list.get(i);
			    buildingId=Integer.parseInt(objects[1].toString());
				map.put("name", objects[0]);
				map.put("comid", objects[1]);
				map.put("lng", objects[2]);
				map.put("lat", objects[3]);
				//查询该楼栋下的表计是否有异常
				List count=hod2000BuildingService.findByBuildingId(buildingId);
				map.put("eventid", count.size());
				//查询该楼栋下的集中器是否有异常
				hod2000Building=(Hod2000Building) hod2000BuildingService.findById(buildingId);
				dataList=hod2000BuildingService.findConEventByCode(hod2000Building.getBuildingCode());
				map.put("terminalEvent", dataList.size());
				list2.add(map);
			}
			OperatorLog.addOperatorLog("地址信息地图中显示当前区域内的所有楼栋信息");
			out.write(JSONArray.fromObject(list2).toString());
		} catch (Exception e) {
			log.error("Hod2000BuildingAction-->getmarkbylatlng",e);
			out.write("{success:false}");
		}
	}
	
	//清除楼栋标注
	public void dodeletelatlng()
	{
		try {
			request =  ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String id=request.getParameter("buildingId");
			hod2000Building=(Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(id));
			hod2000Building.setBuildingLatitude("-1");
			hod2000Building.setBuildingLongitude("-1");
			hod2000BuildingService.update(hod2000Building);
			out.write("{success:true}");
			OperatorLog.addOperatorLog("清除地址信息地图中的楼栋标注");
		} catch (Exception e) {
			log.error("Hod2000BuildingAction-->dodeletelatlng",e);
			out.write("{success:false}");
		}
	}
	
	//表计报警地图
	public void getEventList()
	{
		try {
			request =  ServletActionContext.getRequest();
	    	out = ServletActionContext.getResponse().getWriter();
	    	String buildingId=request.getParameter("buildingId");
	    	if(buildingId!=null&&!buildingId.equals(""))
	    	{
	    		dataList=hod2000BuildingService.findByBuildingId(Integer.parseInt(buildingId));
	    		Object[] obj;
	    		Map map;
	    		List list=new ArrayList();
	    		for (int i = 0; i < dataList.size(); i++) {
					obj=(Object[]) dataList.get(i);
					map=new HashMap();
					map.put("meterName", obj[0]);
					map.put("positionName", obj[1]);
					map.put("valveStatus", obj[2]);
					map.put("batteryStatus", obj[3]);
					map.put("isOnline", obj[4]);
					map.put("eepromStatus", obj[5]);
					map.put("flowsensorStatus", obj[6]);
					map.put("tepdownStatus", obj[7]);
					map.put("tepupStatus", obj[8]);
					list.add(map);
				}
	    		out.write("{totalCount:"+String.valueOf(list.size())+",result:"+JSONArray.fromObject(list).toString()+"}");
	    		OperatorLog.addOperatorLog("表计报警地图显示");
	    	}
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	//得到地图中心
	public void getMapCenter()
	{
		try {
			request =  ServletActionContext.getRequest();
	    	out = ServletActionContext.getResponse().getWriter();
	    	List list= hod2000SysparameterService.findByType(9);
	    	if(list.size()>0)
	    	{
	    		Hod2000Sysparameter hod2000Sysparameter=(Hod2000Sysparameter) hod2000SysparameterService.findByType(9).get(0);
	    		String[] lnglat=hod2000Sysparameter.getPvalue().split(",");
	    		out.write("{success:true,lng:"+lnglat[0]+",lat:"+lnglat[1]+"}");
	    	}
	    	else
	    	{
	    		out.write("{success:false,msg:'地图中心无效!'}");
	    	}
		} catch (Exception e) {
			log.error("得到地图中心："+e);
			out.write("{success:false}");
		}
	}
	
	//保存地图中心
	public void dosavemapcenter()
	{
		try {
			request =  ServletActionContext.getRequest();
	    	out = ServletActionContext.getResponse().getWriter();
	    	String lat=request.getParameter("lat");
	    	String lng=request.getParameter("lng");
	    	String latlng="";
	    	List list= hod2000SysparameterService.findByType(9);
	    	if(list.size()>0)
	    	{
		    	Hod2000Sysparameter hod2000Sysparameter=(Hod2000Sysparameter) hod2000SysparameterService.findByType(9).get(0);
		    	if(lng!=null&&!"".equals(lng))
		    	{
		    		latlng+=lng+",";
		    	}
		    	if(lat!=null&&!"".equals(lat))
		    	{
		    		latlng+=lat;
		    	}
		    	hod2000Sysparameter.setPvalue(latlng);
		    	hod2000SysparameterService.update(hod2000Sysparameter);
		    	out.write("{success:true}");
	    	}
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	//根据集中器地址查询所属楼栋的经纬度
	public void getLnglat()
	{
		try {
			request =  ServletActionContext.getRequest();
	    	out = ServletActionContext.getResponse().getWriter();
	    	String conAddress=request.getParameter("conAddress");
	    	if(conAddress!=null&&!"".equals(conAddress))
	    	{
	    		dataList=hod2000BuildingService.getLnglat(conAddress);
	    		if(dataList.size()>0)
	    		{
	    			Object[] obj=(Object[]) dataList.get(0);
	    			out.write("{success:true,id:"+obj[0]+",lng:"+obj[1]+",lat:"+obj[2]+",name:'"+obj[3]+"'}");
	    		}
	    	}
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000BuildingService(IHod2000BuildingService hod2000BuildingService) {
		this.hod2000BuildingService = hod2000BuildingService;
	}
 
	public Hod2000Building getHod2000Building() {
		return hod2000Building;
	}

	public void setHod2000Building(Hod2000Building hod2000Building) {
		this.hod2000Building = hod2000Building;
	}
    
	public IHod2000CommunityService getHod2000CommunityService() {
		return hod2000CommunityService;
	}

	public void setHod2000CommunityService(
			IHod2000CommunityService hod2000CommunityService) {
		this.hod2000CommunityService = hod2000CommunityService;
	}

	public void setHod2000SysparameterService(
			IHod2000SysparameterService hod2000SysparameterService) {
		this.hod2000SysparameterService = hod2000SysparameterService;
	}
	
}
