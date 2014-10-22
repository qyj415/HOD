package  com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Client;
import com.hod.pojo.Hod2000Meter;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Unit;
import com.hod.pro.model.service.IHod2000BuildingService;
import com.hod.pro.model.service.IHod2000MeterService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.pro.model.service.IHod2000UnitService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000RoomAction ������Ϣ����
 * @author yixiang
 */
public class Hod2000RoomAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000MeterService hod2000MeterService;
	private IHod2000UnitService hod2000UnitService;
	private Hod2000Room hod2000Room;
	private PrintWriter out;
	private List dataList;
	Message msg=Message.getInstance();
	//��־��Ϣ����ʵ��
	static Logger log = Logger.getLogger(Hod2000RoomAction.class.getName());
	
	public String doSave() {
		String unitId = "";
		try {
			request = ServletActionContext.getRequest();
			unitId=request.getParameter("unitId");
			Hod2000Unit hod2000Unit=(Hod2000Unit) hod2000UnitService.findById(Integer.parseInt(unitId));
			//�жϸ�¥�������Ƿ��Ѿ����ڸ÷����
			int count=hod2000RoomService.findByRoomName(hod2000Unit.getUnitId(),hod2000Room.getRoomName());
			if(count>0)
			{
				msg.setError1("�õ�Ԫ���治������ͬ�ķ����");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/form/roomadd.jsp?unitId="+unitId+"\">�������</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Room.setHod2000Unit(hod2000Unit);
			hod2000Room.setHod2000Client(null);
			hod2000Room.setRoomPreFlag(0);
			hod2000Room.setRoomReceiveFlag(0);
			hod2000Room.setRoomCode(hod2000RoomService.getCode(Integer.parseInt(unitId)));
			hod2000RoomService.save(hod2000Room);
			msg.setMsg1("������Ϣ��ӳɹ�!");
			OperatorLog.addOperatorLog("������Ϣ�����");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doSave",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doDelete() {
		String unitId = "";
		try {
			request = ServletActionContext.getRequest();
			unitId=request.getParameter("unitId");
			String ids = request.getParameter("delIds");
			if (ids!=null&&!ids.equals("")) {
				ids = Page.convertKey(ids);
				//���ɾ��ʱ�ж��Ƿ���ڹ���
				String rooms[] = ids.split(",");
				for(int i=0;i<rooms.length;i++){
					Hod2000Room room = (Hod2000Room)hod2000RoomService.findById(Integer.parseInt(rooms[i]));
					//������뷿���ϵ
					//Set<Hod2000Meter> meters = room.getHod2000Meters();
					List meters=hod2000RoomService.findByNHQL("select meter_name from hod2000_meter where room_id="+room.getRoomId());
					if(meters.size()>0){
						msg.setError1("�÷����ѹ��������Ϣ�����ܽ���ɾ������!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
						request.setAttribute("message", msg);
						return "message";
					}
				}
				hod2000RoomService.deleteByParam(rooms);//ע����������
				msg.setMsg1("ɾ��������Ϣ�ɹ�!");
				OperatorLog.addOperatorLog("������Ϣ��ɾ��");
				
			}else{
				msg.setError1("IDΪ��!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doDelete",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String toUpdate() {
		String unitId = "";
		try {
			request = ServletActionContext.getRequest();
			unitId=request.getParameter("unitId");
			String id=request.getParameter("id");
			if(id!=null&&!id.equals(""))
			{
				hod2000Room = (Hod2000Room) hod2000RoomService.findById(Integer.parseInt(id));
				request.setAttribute("hod2000Room", hod2000Room);
			}
			else
			{
				msg.setMsg1("ID��Ч");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			return "toUpdate";
		} catch (Exception e) {
			//���ô�����Ϣ
			msg.setError1("������Ϣ��"+e.toString());
			msg.setLink(0, "<a href='"+request.getContextPath()
					+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
			log.error("Hod2000RoomAction-->toUpdate",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	public String doUpdate() {
		String unitId="";
		try {
			request = ServletActionContext.getRequest();
			unitId=request.getParameter("unitId");
			String roomName=request.getParameter("roomName");
			String roomSize=request.getParameter("roomSize");
			String roomHouseType=request.getParameter("roomHouseType");
			String roomRemark=request.getParameter("roomRemark");
			hod2000Room=(Hod2000Room) hod2000RoomService.findById(hod2000Room.getRoomId());
			//�÷��������Ƿ��б����Ϣ���������޸�
			//Set<Hod2000Meter> meters = hod2000Room.getHod2000Meters();
			//if(meters.size()>0){
			List meters=hod2000RoomService.findByNHQL("select meter_name from hod2000_meter where room_id="+hod2000Room.getRoomId());
			if(meters.size()>0){
				msg.setError1("�÷����д��ڹ�����ƣ������޸ķ�����Ϣ</br>");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			//�жϸõ�Ԫ�����Ƿ��Ѿ����ڸ÷����
			int count=hod2000RoomService.findByRoomName(hod2000Room.getHod2000Unit().getUnitId(),roomName,hod2000Room.getRoomId());
			if(count>0)
			{
				msg.setError1("�õ�Ԫ���治������ͬ�ķ����");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Room.setRoomName(roomName);
			if(roomSize!=null&&!roomSize.equals(""))
				hod2000Room.setRoomSize(Double.parseDouble(roomSize));
			hod2000Room.setRoomHouseType(roomHouseType);
			hod2000Room.setRoomRemark(roomRemark);
			hod2000RoomService.update(hod2000Room);
			msg.setMsg1("�޸ķ�����Ϣ�ɹ�!");
			OperatorLog.addOperatorLog("������Ϣ���޸�");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Room!doSelect.do?unitId="+unitId+"\">�����б�</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Room.class);
			String unitId=request.getParameter("unitId");
			if(unitId!=null&&!unitId.equals(""))
			{
				dc.add(Restrictions.eq("hod2000Unit.unitId", Integer.parseInt(unitId)));
			}
			Hod2000Unit unit = (Hod2000Unit)hod2000UnitService.findById(Integer.parseInt(unitId));
			dataList = Page.util(request, hod2000RoomService,dc);
			request.setAttribute("unitName", unit.getUnitName());
			request.setAttribute("unitId", unitId);
			return SUCCESS;
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doSelect:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  
	
	//����ʱ���ѡ�񷿼䣬�����Ѿ����������û�а��û��ķ�����Ϣ
	public String doSelect2() {
		try {
			request=ServletActionContext.getRequest();
			String unitId=request.getParameter("unitId");
			String sql="select room_id,meter_position_name,room_name,meter_name,meter_init from meter_address where client_id is null and meter_able=1";
			String countSql="select count(*) from meter_address where client_id is null and meter_able=1";
			if(unitId!=null&&!unitId.equals(""))
			{
				sql+=" and unit_id="+Integer.parseInt(unitId);
				countSql+=" and unit_id="+Integer.parseInt(unitId);
			}
			List list=Page.findBySql(request, hod2000RoomService, sql, countSql);
			Map map;
			Object[] objects;
			dataList=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				map=new HashMap();
				objects=(Object[]) list.get(i);
				map.put("roomId", objects[0]);
				map.put("meterPositionName", objects[1]);
				map.put("meterInit", objects[4]);
				map.put("meterName", objects[3]);
				dataList.add(map);
			}
			return "list";
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doSelect2:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}    
	
	//�û����ʱ���ѡ�񷿼䣬�����Ѿ�������Ƶķ�����Ϣ
	public String doSelect5() {
		try {
			request=ServletActionContext.getRequest();
			String ids=request.getParameter("ids");
			String unitId=request.getParameter("unitId");
			String sql="select room_id,meter_position_name,room_name,meter_name,meter_init from meter_address where (client_id is null or room_id in ("+ids+")) and meter_able=1";
			String countSql="select count(*) from meter_address where (client_id is null or room_id in ("+ids+")) and meter_able=1";
			if(unitId!=null&&!unitId.equals(""))
			{
				sql+=" and unit_id="+Integer.parseInt(unitId);
				countSql+=" and unit_id="+Integer.parseInt(unitId);
			}
			List list=Page.findBySql(request, hod2000RoomService, sql, countSql);
			Map map;
			Object[] objects;
			dataList=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				map=new HashMap();
				objects=(Object[]) list.get(i);
				map.put("roomId", objects[0]);
				map.put("meterPositionName", objects[1]);
				map.put("meterInit", objects[4]);
				map.put("meterName", objects[3]);
				dataList.add(map);
			}
			return "list4";
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doSelect2:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}    
	
	//Ԥ�տ���
	public String doSelect3() {
		try {
			request=ServletActionContext.getRequest();
			String unitId=request.getParameter("unitId");
			String sql="select room_id,unit_name,room_name,client_name from hod2000_unit b inner join hod2000_room r on b.unit_id=r.unit_id inner join hod2000_client c on r.client_id=c.client_id where client_enable=1 and room_pre_flag=0";
			String countSql="select count(*) from hod2000_unit b inner join hod2000_room r on b.unit_id=r.unit_id inner join hod2000_client c on r.client_id=c.client_id where client_enable=1 and room_pre_flag=0";
			if(unitId!=null&&!unitId.equals(""))
			{
				sql+=" and r.unit_id="+Integer.parseInt(unitId);
				countSql+=" and r.unit_id="+Integer.parseInt(unitId);
			}
			List list=Page.findBySql(request, hod2000RoomService, sql, countSql);
			Map map;
			Object[] objects;
			dataList=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				map=new HashMap();
				objects=(Object[]) list.get(i);
				map.put("roomId", objects[0]);
				map.put("buildingName", objects[1]);
				map.put("roomName", objects[2]);
				map.put("clientName", objects[3]);
				dataList.add(map);
			}
			return "list2";
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doSelect2:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  
	
	//�շ�
	public String doSelect4() {
		try {
			request=ServletActionContext.getRequest();
			String unitId=request.getParameter("unitId");
			String sql="select room_id,unit_name,room_name,client_name from receive_room_meter where client_enable=1 and room_receive_flag=0 and meter_able=1";
			String countSql="select count(*) from receive_room_meter where client_enable=1 and room_receive_flag=0 and meter_able=1";
			if(unitId!=null&&!unitId.equals(""))
			{
				sql+=" and unit_id="+Integer.parseInt(unitId);
				countSql+=" and unit_id="+Integer.parseInt(unitId);
			}
			List list=Page.findBySql(request, hod2000RoomService, sql, countSql);
			Map map;
			Object[] objects;
			dataList=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				map=new HashMap();
				objects=(Object[]) list.get(i);
				map.put("roomId", objects[0]);
				map.put("buildingName", objects[1]);
				map.put("roomName", objects[2]);
				map.put("clientName", objects[3]);
				dataList.add(map);
			}
			return "list3";
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000RoomAction-->doSelect2:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
    
	//�����е��ѡ��ťѡ�񷿼���Ϣ����ѯ�������������Ϣ,һ�����䲻���������ű�
	public void showMeter()
	{
		try {
			request=ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			Object[] objects;
			Map map;
			String ids=request.getParameter("ids");
			//�����û�а󶨷���ʱ����ʾ�÷�����Ϣ
			List list=hod2000RoomService.findMeterByRoomId(ids);
			List list2=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				objects=(Object[]) list.get(i);
				map=new HashMap();
				map.put("roomId", objects[0]);
				map.put("roomName", objects[1]);
				map.put("roomHouseType", objects[2]);
				map.put("meterName", objects[3]);
				map.put("meterInit", objects[4]);
				list2.add(map);
			}
			out.write("{success:true,data:"+JSONArray.fromObject(list2).toString()+"}");
		} catch (Exception e) {
			log.error("Hod2000RoomAction-->showMeter",e);
			out.write("{success:false}");
		}
	}
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
    
	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}
 
	public Hod2000Room getHod2000Room() {
		return hod2000Room;
	}

	public void setHod2000Room(Hod2000Room hod2000Room) {
		this.hod2000Room = hod2000Room;
	}

	public void setHod2000UnitService(IHod2000UnitService hod2000UnitService) {
		this.hod2000UnitService = hod2000UnitService;
	}

	public IHod2000MeterService getHod2000MeterService() {
		return hod2000MeterService;
	}

	public void setHod2000MeterService(IHod2000MeterService hod2000MeterService) {
		this.hod2000MeterService = hod2000MeterService;
	}
	
}
