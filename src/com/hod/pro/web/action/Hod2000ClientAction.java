package  com.hod.pro.web.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hod.pojo.Hod2000BatchClientError;
import com.hod.pojo.Hod2000Client;
import com.hod.pojo.Hod2000Room;
import com.hod.pro.model.service.IHod2000BatchClientErrorService;
import com.hod.pro.model.service.IHod2000ClientService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.util.Message;
import com.hod.util.NetworkTimeUtil;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;
import com.hod.util.BatchFileLineUtil;


/**
 * Hod2000ClientAction 档案管理
 * @author yixiang
 */
public class Hod2000ClientAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000ClientService hod2000ClientService;
	private Hod2000Client hod2000Client;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000BatchClientErrorService hod2000BatchClientErrorService;
	private Hod2000Room hod2000Room;
	private List dataList;
	private Message msg=Message.getInstance();
	private PrintWriter out;
	//日志信息操作实例
	static Logger log = Logger.getLogger(Hod2000ClientAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();
	private int maxSize=100000;//最大注册用户数
	private File fileName;
	
	/**
	 * 开户
	 * @return
	 */
	public String doSave() {
		try {
			request=ServletActionContext.getRequest();
			//判断当前注册用户数是否小于最大用户数
			int size=hod2000ClientService.findCount();
			if(maxSize<=size)
			{
				msg.setError1("最大用户数为："+maxSize);
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/subpage.jsp?id=0\">返回客户管理</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			String roomId=request.getParameter("roomId");
			if(roomId==null||roomId.equals(""))
			{
				msg.setError1("请给用户指定房间!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/clientadd.jsp\">重新开户</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			//判断证件号码的唯一性（有效的）
			int count=hod2000ClientService.findByCardNo(hod2000Client.getClientIdentity());
			if(count>0)
			{
				msg.setError1("已存在该证件号的注册信息!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/clientadd.jsp\">重新开户</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			String priceType=request.getParameter("priceType");
			hod2000Client.setClientEnable(1);//状态默认为正常
			//hod2000Client.setClientOpenTime(new Date());
			hod2000Client.setClientOpenTime(NetworkTimeUtil.getDate());
			hod2000ClientService.save(hod2000Client);
			//给用户指定的房间设置其价格方案，并关联用户id
			String[] rooms=roomId.split(",");
			for (int i = 0; i < rooms.length; i++) {
				hod2000Room=(Hod2000Room) hod2000RoomService.findById(Integer.parseInt(rooms[i]));
				hod2000Room.setPriceType(Integer.parseInt(priceType));
				hod2000Room.setHod2000Client(hod2000Client);
				hod2000RoomService.update(hod2000Room);
			}
			msg.setMsg1("开户成功!");
			OperatorLog.addOperatorLog("开户");
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->doSave",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/clientadd.jsp\">继续开户</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	/**
	 * 销户，需解除用户与房间的关系
	 * @return
	 */
	public String doDelete()
	{
		try {
			request=ServletActionContext.getRequest();
			String ids = request.getParameter("delIds");
			if (ids!=null&&!ids.equals("")) {
				hod2000Client=(Hod2000Client) hod2000ClientService.findById(Integer.parseInt(ids));
				hod2000Client.setClientEnable(0);//设置为无效
				hod2000ClientService.update(hod2000Client);
				//解除用户与房间的关系
				hod2000RoomService.executeUpdate("UPDATE Hod2000Room SET hod2000Client.clientId=null where hod2000Client.clientId="+hod2000Client.getClientId());
//				for (Hod2000Room room : hod2000Client.getHod2000Rooms()) {
//					room.setHod2000Client(null);
//					hod2000RoomService.update(room);
//				}
				msg.setMsg1("销户成功");
				OperatorLog.addOperatorLog("销户");
			}else{
				msg.setMsg1("ID无效!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/clientdel.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->doDelete",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/clientdel.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	/**
	 * 用户编辑
	 * @return
	 */
	public String toUpdate() 
	{
		try {
			request=ServletActionContext.getRequest();
			String clientName=request.getParameter("clientName");
			String clientIdentity=request.getParameter("clientIdentity");
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Client.class);
			if(clientName!=null&&!clientName.equals(""))
			{
				dc.add(Restrictions.eq("clientName", clientName));
			}
			if(clientIdentity!=null&&!clientIdentity.equals(""))
			{
				dc.add(Restrictions.eq("clientIdentity", clientIdentity));
			}
			dc.add(Restrictions.eq("clientEnable", 1));
			dataList=hod2000ClientService.findByCriteria(dc);
			String ids="";
			if(dataList.size()>0)
			{
				hod2000Client=(Hod2000Client) dataList.get(0);
				//查询该用户的房间信息
				List rooms=hod2000ClientService.findByNHQL("select room_id,p_type from hod2000_room where client_id="+hod2000Client.getClientId());
				Object[] obj;
				for (int i = 0; i < rooms.size(); i++) {
					obj=(Object[]) rooms.get(i);
					ids+=obj[0]+",";
					request.setAttribute("priceType", obj[1]);
				}
//				for (Hod2000Room room : hod2000Client.getHod2000Rooms()) {
//					ids+=room.getRoomId()+",";
//					request.setAttribute("priceType", room.getPriceType());
//				}
				if(ids!=null&&!ids.equals(""))
				{
					ids=ids.substring(0,ids.length()-1);
					Object[] objects;
					//当表计没有绑定房间时不显示该房间信息
					List list=hod2000RoomService.findMeterByRoomId(ids.toString());
					List list2=new ArrayList();
					Map map;
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
					request.setAttribute("ids", ids);
					request.setAttribute("list", list2);
				}
			}
			else
			{
				msg.setError1("查不到用户的有效记录!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/clientedit.jsp\">用户变更</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			request.setAttribute("roomId", ids);
			request.setAttribute("hod2000Client", hod2000Client);
			request.setAttribute("clientName", clientName);
			request.setAttribute("clientIdentity", clientIdentity);
			return "toUpdate";
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->toUpdate",e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	/**
	 * 变更处理
	 * @return
	 */
	public String doUpdate()
	{
		try {
			request=ServletActionContext.getRequest();
			hod2000Client=(Hod2000Client) hod2000ClientService.findById(hod2000Client.getClientId());
			String clientName=request.getParameter("clientName");
			String clientSex=request.getParameter("clientSex");
			String clientAddress=request.getParameter("clientAddress");
			String clientTel=request.getParameter("clientTel");
			String clientRemark=request.getParameter("clientRemark");
			String roomId=request.getParameter("roomId");
			if(roomId==null||roomId.equals(""))
			{
				msg.setError1("请给用户指定房间!");
				msg.setJump(false);
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/clientadd.jsp\">重新开户</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000Client.setClientName(clientName);
			hod2000Client.setClientSex(clientSex);
			hod2000Client.setClientAddress(clientAddress);
			hod2000Client.setClientTel(clientTel);
			hod2000Client.setClientRemark(clientRemark);
			hod2000ClientService.update(hod2000Client);
			String priceType=request.getParameter("priceType");
			//解除用户与房间之间的关联
			hod2000RoomService.executeUpdate("UPDATE Hod2000Room SET hod2000Client.clientId=null where hod2000Client.clientId="+hod2000Client.getClientId());
//			for (Hod2000Room room : hod2000Client.getHod2000Rooms()) {
//				room.setHod2000Client(null);
//				hod2000RoomService.update(room);
//			}
			//给用户指定的房间设置其价格方案，并关联用户id
			String[] rooms=roomId.split(",");
			for (int i = 0; i < rooms.length; i++) {
				hod2000Room=(Hod2000Room) hod2000RoomService.findById(Integer.parseInt(rooms[i]));
				hod2000Room.setPriceType(Integer.parseInt(priceType));
				hod2000Room.setHod2000Client(hod2000Client);
				hod2000RoomService.update(hod2000Room);
			}
			msg.setMsg1("用户变更成功!");
			OperatorLog.addOperatorLog("用户变更");
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->doUpdate",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/clientedit.jsp\">返回</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	/**
	 * 档案查询
	 * @return
	 */
	public void doSelect() 
	{
		try {
			OperatorLog.addOperatorLog("档案信息查询");
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
    		int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Client.class);
			String clientName=request.getParameter("clientName");
			String clientIdentity=request.getParameter("clientIdentity");
			String clientEnable=request.getParameter("clientEnable");
			if(clientName!=null&&!clientName.equals(""))
			{
				dc.add(Restrictions.eq("clientName", clientName));
			}
			if(clientIdentity!=null&&!clientIdentity.equals(""))
			{
				dc.add(Restrictions.eq("clientIdentity", clientIdentity));
			}
			if(clientEnable!=null&&!clientEnable.equals(""))
			{
				dc.add(Restrictions.eq("clientEnable", Integer.parseInt(clientEnable)));
			}
			dataList=hod2000ClientService.findByCriteria(page, pageSize, dc);
			List list=new ArrayList();
    		Map map;
    		for (int i = 0; i < dataList.size(); i++) {
    			hod2000Client=(Hod2000Client) dataList.get(i);
    			map=new HashMap();
    			map.put("id", hod2000Client.getClientId());
    			map.put("clientName", hod2000Client.getClientName());
    			map.put("clientCardType", hod2000Client.getClientCardType());
    			map.put("clientIdentity", hod2000Client.getClientIdentity());
    			map.put("clientSex", hod2000Client.getClientSex());
    			map.put("clientAddress", hod2000Client.getClientAddress());
    			map.put("clientTel", hod2000Client.getClientTel());
    			map.put("clientEnable", hod2000Client.getClientEnable());
    			map.put("clientRemark", hod2000Client.getClientRemark());
    			list.add(map);
    		}
			int totalRecord =hod2000ClientService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000ClientAction->doSelect:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->doSelect",e);
		}
	}
	
	/**
	 * 打印
	 * @return
	 */
	public String print() 
	{
		try {
			OperatorLog.addOperatorLog("档案信息打印预览");
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Client.class);
			String clientName=request.getParameter("clientName");
			String clientIdentity=request.getParameter("clientIdentity");
			String clientEnable=request.getParameter("clientEnable");
			if(clientName!=null&&!clientName.equals(""))
			{
				dc.add(Restrictions.eq("clientName", clientName));
			}
			if(clientIdentity!=null&&!clientIdentity.equals(""))
			{
				dc.add(Restrictions.eq("clientIdentity", clientIdentity));
			}
			if(clientEnable!=null&&!clientEnable.equals(""))
			{
				dc.add(Restrictions.eq("clientEnable", Integer.parseInt(clientEnable)));
			}
			dataList=hod2000ClientService.findByCriteria(dc);
			return "print";
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->doSelect",e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * 预收款与收款时点击选择用户按钮弹出选择框
	 * 显示的用户应该都为正常用户
	 * @return
	 */
	public String doSelect2()
	{
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Client.class);
			String clientName=request.getParameter("clientName");
			String clientIdentity=request.getParameter("clientIdentity");
			if(clientName!=null&&!clientName.equals(""))
			{
				dc.add(Restrictions.eq("clientName", clientName));
			}
			if(clientIdentity!=null&&!clientIdentity.equals(""))
			{
				dc.add(Restrictions.eq("clientIdentity", clientIdentity));
			}
			dc.add(Restrictions.eq("clientEnable", 1));//正常用户
			dataList = Page.util(request, hod2000ClientService,dc);
			request.setAttribute("clientName", clientName);
			request.setAttribute("clientIdentity", clientIdentity);
			return "show";
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->doSelect2",e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	//根据用户编号查询用户拥有的房间信息
	public void showRoom()
	{
		try {
			request=ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String id=request.getParameter("id");
			dataList=hod2000ClientService.findRoomInfoByClientId(Integer.parseInt(id));
			Object[] obj;
			Map map;
			List list=new ArrayList();
			for (int i = 0; i < dataList.size(); i++) {
				obj=(Object[]) dataList.get(i);
				map=new HashMap();
				map.put("roomName", obj[0]);
				map.put("roomSize", obj[1]);
				list.add(map);
			}
			out.write("{success:true,data:"+JSONArray.fromObject(list).toString()+"}");
		} catch (Exception e) {
			log.error("Hod2000ClientAction-->showRoom",e);
			out.write("{success:false}");
		}
	}
    
	public void batchFileUp(){
		
		ArrayList<String> err_al = new ArrayList<String>();//保存错误数据
		
		if (fileName != null) {
			BufferedReader bfr = null;
			try {
				out = ServletActionContext.getResponse().getWriter();
				bfr = new BufferedReader(new FileReader(fileName));
				String line = null;
				while((line = bfr.readLine()) != null){
					String lineRs = BatchFileLineUtil.addClientlineUtil(line, hod2000ClientService, hod2000RoomService, maxSize);
					if(lineRs != null){
						err_al.add(lineRs);
					}
				}
				
				//处理错误数据——》插入错误数据表
				BatchFileLineUtil.addClientsavahod2000BatchClientError(err_al, hod2000BatchClientErrorService);
				
				out.write("{success:true}");
			} catch (FileNotFoundException e) {
				out.write("{success:false}");
				log.error("FileNotFoundException : " + e.getMessage());
			}catch (Exception e) {
				out.write("{success:false}");
				e.printStackTrace();
				log.error("Exception : " + e.getMessage());
			}finally{
				if(bfr!=null){
					try {
						bfr.close();
					} catch (IOException e) {
						log.error("IOException : " + e.getMessage());
					}
				}
			}
		}
	}
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
	public void setHod2000ClientService(IHod2000ClientService hod2000ClientService) {
		this.hod2000ClientService = hod2000ClientService;
	}
 
	public Hod2000Client getHod2000Client() {
		return hod2000Client;
	}

	public void setHod2000Client(Hod2000Client hod2000Client) {
		this.hod2000Client = hod2000Client;
	}

	public IHod2000RoomService getHod2000RoomService() {
		return hod2000RoomService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}

	public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}

	public IHod2000BatchClientErrorService getHod2000BatchClientErrorService() {
		return hod2000BatchClientErrorService;
	}

	public void setHod2000BatchClientErrorService(
			IHod2000BatchClientErrorService hod2000BatchClientErrorService) {
		this.hod2000BatchClientErrorService = hod2000BatchClientErrorService;
	}
	
}
