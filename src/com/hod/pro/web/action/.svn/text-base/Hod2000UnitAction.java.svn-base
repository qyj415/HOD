package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import java.util.Set;
import com.opensymphony.xwork2.ActionSupport;
import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Unit;
import com.hod.pro.model.service.IHod2000BuildingService;
import com.hod.pro.model.service.IHod2000UnitService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;

/**
 * Hod2000UnitAction 控制器
 * @author JSmart Tools
 */
public class Hod2000UnitAction extends ActionSupport {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private IHod2000UnitService hod2000UnitService;
	private IHod2000BuildingService hod2000BuildingService;
	private Hod2000Unit hod2000Unit;
	private List dataList;
	private String tips;
	Message msg=Message.getInstance();
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000UnitAction.class.getName());
	
	public String doSave() {
		String buildingId = "";
		try {
			request = ServletActionContext.getRequest();
			buildingId=request.getParameter("buildingId");
			Hod2000Building hod2000Building=(Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(buildingId));
			//判断该楼栋下面是否已经存在该房间号
			int count=hod2000UnitService.findByRoomName(hod2000Building.getBuildingId(),hod2000Unit.getUnitName());
			if(count>0)
			{
				msg.setError1("该楼栋下面不能有相同的单元");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/form/unitadd.jsp?buildingId="+buildingId+"\">重新添加</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Unit.setHod2000Building(hod2000Building);
			hod2000Unit.setUnitCode(hod2000UnitService.getCode(Integer.parseInt(buildingId)));
			hod2000UnitService.save(hod2000Unit);
			msg.setMsg1("单元信息添加成功!");
			OperatorLog.addOperatorLog("单元信息的添加");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000UnitAction-->doSave",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doDelete() {
		String buildingId = "";
		try {
			request = ServletActionContext.getRequest();
			buildingId=request.getParameter("buildingId");
			String ids = request.getParameter("delIds");
			if (ids!=null&&!ids.equals("")) {
				ids = Page.convertKey(ids);
				//添加删除时判断是否存在关联
				String units[] = ids.split(",");
				for(int i=0;i<units.length;i++){
					hod2000Unit = (Hod2000Unit)hod2000UnitService.findById(Integer.parseInt(units[i]));
					//解除表与房间关系
					//Set<Hod2000Room> rooms=hod2000Unit.getHod2000Rooms();
					List rooms=hod2000UnitService.findByNHQL("select room_id from hod2000_room where unit_id="+hod2000Unit.getUnitId());
					if(rooms.size()>0){
						msg.setError1("该单元信息下面存在房间信息，请先删除下面的房间信息!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
						request.setAttribute("message", msg);
						return "message";
					}
				}
				hod2000UnitService.deleteByParam(units);//注意主键类型
				msg.setMsg1("删除单元信息成功!");
				OperatorLog.addOperatorLog("单元信息的删除");
				
			}else{
				msg.setError1("ID为空!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000UnitAction-->doDelete",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String toUpdate() {
		String buildingId = "";
		try {
			request = ServletActionContext.getRequest();
			buildingId=request.getParameter("buildingId");
			String id=request.getParameter("id");
			if(id!=null&&!id.equals(""))
			{
				hod2000Unit = (Hod2000Unit) hod2000UnitService.findById(Integer.parseInt(id));
				request.setAttribute("hod2000Unit", hod2000Unit);
			}
			else
			{
				msg.setMsg1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			return "toUpdate";
		} catch (Exception e) {
			//设置错误信息
			msg.setError1("错误信息："+e.toString());
			msg.setLink(0, "<a href='"+request.getContextPath()
					+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
			log.error("Hod2000UnitAction-->toUpdate",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	public String doUpdate() {
		String buildingId="";
		try {
			request = ServletActionContext.getRequest();
			buildingId=request.getParameter("buildingId");
			String unitName=request.getParameter("unitName");
			String unitRemark=request.getParameter("unitRemark");
			hod2000Unit=(Hod2000Unit) hod2000UnitService.findById(hod2000Unit.getUnitId());
			//判断该楼栋下面是否已经存在该单元号
			int count=hod2000UnitService.findByUnitName(hod2000Unit.getHod2000Building().getBuildingId(),unitName,hod2000Unit.getUnitId());
			if(count>0)
			{
				msg.setError1("该楼栋下面不能有相同的单元号");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			List rooms=hod2000UnitService.findByNHQL("select room_id from hod2000_room where unit_id="+hod2000Unit.getUnitId());
			if(rooms.size()>0){
				msg.setError1("该单元信息下面存在房间信息，不能修改该单元信息!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Unit.setUnitName(unitName);
			hod2000Unit.setUnitRemark(unitRemark);
			hod2000UnitService.update(hod2000Unit);
			msg.setMsg1("修改单元信息成功!");
			OperatorLog.addOperatorLog("单元信息的修改");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000UnitAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Unit!doSelect.do?buildingId="+buildingId+"\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Unit.class);
			String buildingId=request.getParameter("buildingId");
			if(buildingId!=null&&!buildingId.equals(""))
			{
				dc.add(Restrictions.eq("hod2000Building.buildingId", Integer.parseInt(buildingId)));
			}
			Hod2000Building building = (Hod2000Building)hod2000BuildingService.findById(Integer.parseInt(buildingId));
			dataList = Page.util(request, hod2000UnitService,dc);
			request.setAttribute("buildingName", building.getBuildingName());
			request.setAttribute("buildingId", buildingId);
			return SUCCESS;
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000UnitAction-->doSelect:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}    
    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000UnitService(IHod2000UnitService hod2000UnitService) {
		this.hod2000UnitService = hod2000UnitService;
	}
 
	public Hod2000Unit getHod2000Unit() {
		return hod2000Unit;
	}

	public void setHod2000Unit(Hod2000Unit hod2000Unit) {
		this.hod2000Unit = hod2000Unit;
	}
    
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public void setHod2000BuildingService(
			IHod2000BuildingService hod2000BuildingService) {
		this.hod2000BuildingService = hod2000BuildingService;
	}
	
}
