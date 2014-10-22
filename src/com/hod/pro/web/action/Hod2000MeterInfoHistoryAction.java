package  com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000City;
import com.hod.pojo.Hod2000Community;
import com.hod.pojo.Hod2000County;
import com.hod.pojo.Hod2000MeterInfoHistory;
import com.hod.pojo.Hod2000Region;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Sysparameter;
import com.hod.pojo.Hod2000Unit;
import com.hod.pojo.Hod2000Village;
import com.hod.pro.model.service.Hod2000CommunityService;
import com.hod.pro.model.service.IHod2000BuildingService;
import com.hod.pro.model.service.IHod2000CityService;
import com.hod.pro.model.service.IHod2000CommunityService;
import com.hod.pro.model.service.IHod2000CountyService;
import com.hod.pro.model.service.IHod2000MeterInfoFreezeService;
import com.hod.pro.model.service.IHod2000MeterInfoHistoryService;
import com.hod.pro.model.service.IHod2000MeterService;
import com.hod.pro.model.service.IHod2000RegionService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.pro.model.service.IHod2000SysparameterService;
import com.hod.pro.model.service.IHod2000UnitService;
import com.hod.pro.model.service.IHod2000VillageService;
import com.hod.util.Arith;
import com.hod.util.DateUtil;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000MeterInfoHistoryAction 历史数据
 * @author yixiang
 */
public class Hod2000MeterInfoHistoryAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000MeterInfoHistoryService hod2000MeterInfoHistoryService;
	private IHod2000MeterInfoFreezeService hod2000MeterInfoFreezeService;
	private IHod2000RegionService hod2000RegionService;
	private IHod2000CityService cityService;
	private IHod2000CountyService countyService;
	private IHod2000VillageService hod2000VillageService;
	private IHod2000CommunityService communityService;
	private IHod2000BuildingService buildingService;
	private IHod2000UnitService hod2000UnitService;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000MeterService hod2000MeterService;
	private IHod2000SysparameterService hod2000SysparameterService;
	private Hod2000MeterInfoHistory hod2000MeterInfoHistory;
	private PrintWriter out;
	private List dataList;
	private Message msg=Message.getInstance();
	private Logger log = Logger.getLogger(Hod2000MeterInfoHistoryAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();
	
	/**
	 * 删除数据
	 * method为1时删除历史数据，method为2时删除月冻结数据
	 * @return
	 */
	public void doDelete() {
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String[] ids=request.getParameterValues("delIds");
			String method=request.getParameter("method");
			if (ids!=null&&!ids.equals("")) {
				if(method.equals("0"))
				{
					OperatorLog.addOperatorLog("历史数据的删除");
					hod2000MeterInfoHistoryService.deleteByParam(ids);//注意主键类型
					out.write("{success:true,msg:'历史数据删除成功!'}");
				}
				if(method.equals("1"))
				{
					OperatorLog.addOperatorLog("月冻结数据的删除");
					hod2000MeterInfoFreezeService.deleteByParam(ids);
					out.write("{success:true,msg:'月冻结数据删除成功!'}");
				}
			}else{
				out.write("{success:false,msg:'ID无效!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doDelete:",e);
			out.write("{success:false,msg:'请求失败!'}");
		}
	}
	
	//删除历史数据
	public void doDel()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			if((startTime!=null&&!"".equals(startTime))&&(endTime!=null&&!"".equals(endTime)))
			{
//				String hql="from Hod2000MeterInfoHistory where readTime>='"+startTime+"' and readTime<='"+endTime+"'";
//				List<Hod2000MeterInfoHistory> list=hod2000MeterInfoHistoryService.findByHQL(hql);
//				int size=list.size();
//				if(size>0)
//				{
//					for (int i = 0; i < size; i++) {
//						hod2000MeterInfoHistory=list.get(i);
//						hod2000MeterInfoHistoryService.delete(hod2000MeterInfoHistory);
//					}
//					out.write("{success:true,msg:'删除时间段内历史数据成功!'}");
//				}
//				else
//				{
//					out.write("{success:false,msg:'该时间段内无数据!'}");
//				}
				int flag=hod2000MeterInfoHistoryService.executeUpdate("DELETE FROM Hod2000MeterInfoHistory WHERE readTime>='"+startTime+"' and readTime<='"+endTime+"'");
				if(1==flag)
				{
					out.write("{success:true,msg:'删除时间段内历史数据成功!'}");
				}
				else
				{
					out.write("{success:false,msg:'该时间段内没有历史数据!'}");
				}
			}
			else
			{
				out.write("{success:false,msg:'开始时间和结束时间不能为空!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doDel:",e);
			out.write("{success:false,msg:'请求失败，出现异常!'}");
		}
	}
	
	/**
	 * 读取历史数据
	 * @return
	 */
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("历史数据查询");
    		request=ServletActionContext.getRequest();
    		out= ServletActionContext.getResponse().getWriter();	
    		int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
		    //String sidx=request.getParameter("sidx");//获得排序的字段
		    //String sord=request.getParameter("sord");//获得排序顺序（升序 or 降序）
    		String meterName=request.getParameter("meterName");//表号
    		String startTime=request.getParameter("startTime");
    		String endTime=request.getParameter("endTime");
    		String meterPositionName=request.getParameter("meterPositionName");
    		//valve_status,battery_status,isonline
    		String sql = "select t_id,meter_name,current_energy,accumulate_flow,meter_flow,accumulate_time,supply_temper,back_temper,read_time,meter_position_name from meterHistory where 1=1";
			String count = "select count(*) from meterHistory where 1=1";
			if (meterName != null && !meterName.equals("")) {
				sql += " and meter_name like '" + meterName + "%'";
				count += " and meter_name like '" + meterName + "%'";
			}
			if(meterPositionName!=null&&!"".equals(meterPositionName))
			{
				sql+=" and meter_position_name like '%"+meterPositionName+"%'";
				count+=" and meter_position_name like '%"+meterPositionName+"%'";
			}
			if (startTime != null && !startTime.equals("")) {
				sql+=" and read_time>='"+startTime+"'";
				count+=" and read_time>='"+startTime+"'";
			}
			if (endTime != null && !endTime.equals("")) {
				sql+=" and read_time<='"+endTime+"'";
				count+=" and read_time<='"+endTime+"'";
			}
			//sql+="  order by "+sidx+" "+sord;
			dataList=hod2000MeterInfoHistoryService.findByNHQL(page, pageSize, sql);
    		List list=new ArrayList();
    		Object[] objects;
    		Map map;
    		for (int i = 0; i < dataList.size(); i++) {
    			objects=(Object[]) dataList.get(i);
    			map=new HashMap();
    			map.put("id", objects[0]);
    			map.put("meterName", objects[1]);
    			//map.put("currentEnergy", objects[2]);
    			map.put("currentEnergy", Arith.dataFormat(objects[2]));
    			map.put("accumulateFlow", objects[3]);
    			map.put("meterFlow", objects[4]);
    			//map.put("valveStatus", objects[5]);
    			//map.put("batteryStatus", objects[6]);
    			map.put("accumulateTime", objects[5]);
    			map.put("supplyTemper", objects[6]);
    			map.put("backTemper", objects[7]);
    			map.put("readTime", Utils.dateToStrLong(Utils.strToDateLong(objects[8].toString())));
    			//map.put("isOnline", objects[11]);
    			map.put("meterPositionName", objects[9]);
    			list.add(map);
			}
    		int totalRecord =Integer.parseInt(hod2000MeterInfoHistoryService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000MeterInfoHistoryAction->doSelect:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doSelect:",e);
		}
	}    
	
	/**
	 * 历史数据打印
	 * @return
	 */
	public String historyPrint() {
		try {
			OperatorLog.addOperatorLog("历史数据的打印预览");
			request=ServletActionContext.getRequest();
    		String meterName=request.getParameter("meterName");//表号
    		String startTime=request.getParameter("startTime");
    		String endTime=request.getParameter("endTime");
    		String meterPositionName=request.getParameter("meterPositionName");
    		String sql = "select t_id,meter_name,current_energy,accumulate_flow,meter_flow,accumulate_time,supply_temper,back_temper,read_time,meter_position_name from meterHistory where 1=1";
			if (meterName != null && !meterName.equals("")) {
				sql += " and meter_name like '" + meterName + "%'";
			}
			if(meterPositionName!=null&&!"".equals(meterPositionName))
			{
				sql+=" and meter_position_name like '%"+meterPositionName+"%'";
			}
			if (startTime != null && !startTime.equals("")) {
				sql+=" and read_time>='"+startTime+"'";
			}
			if (endTime != null && !endTime.equals("")) {
				sql+=" and read_time<='"+endTime+"'";
			}
			//sql += "  order by history.read_time desc";
			dataList=hod2000MeterInfoHistoryService.findByNHQL(sql);
    		List list=new ArrayList();
    		Object[] objects;
    		Map map;
    		for (int i = 0; i < dataList.size(); i++) {
    			objects=(Object[]) dataList.get(i);
    			map=new HashMap();
    			map.put("id", objects[0]);
    			map.put("meterName", objects[1]);
    			//map.put("currentEnergy", objects[2]);
    			map.put("currentEnergy", Arith.dataFormat(objects[2]));
    			map.put("accumulateFlow", objects[3]);
    			map.put("meterFlow", objects[4]);
    			//map.put("valveStatus", objects[5]);
    			//map.put("batteryStatus", objects[6]);
    			map.put("accumulateTime", objects[5]);
    			map.put("supplyTemper", objects[6]);
    			map.put("backTemper", objects[7]);
    			map.put("readTime", objects[8]);
    			//map.put("isOnline", objects[11]);
    			map.put("meterPositionName", objects[9]);
    			list.add(map);
			}
    		request.setAttribute("list", list);
			return "historyPrint";
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doSelect:",e);
			msg.setError1(e.toString());
			request.setAttribute("message",msg);
			return "message";
		}
	} 
	/**
	 * 月冻结数据查询
	 * @return
	 */
	public void doSelectFree()
	{
		try {
			OperatorLog.addOperatorLog("月冻结数据的查询");
			request=ServletActionContext.getRequest();
			out= ServletActionContext.getResponse().getWriter();	
			int page=Integer.parseInt(request.getParameter("page"));
			int pageSize=Integer.parseInt(request.getParameter("rows"));
    		String meterName=request.getParameter("meterName");//表号
    		String startTime=request.getParameter("startTime");
    		String endTime=request.getParameter("endTime");
    		String meterPositionName=request.getParameter("meterPositionName");
    		String sql="select t_id,clear_date,meter_name,clear_energy,clear_flow,read_time,meter_position_name from meterFree where 1=1";
    		String count="select count(*) from meterFree where 1=1";
    		if(meterName!=null&&!meterName.equals(""))
    		{
    			sql+=" and meter_name like '"+meterName+"%'";
    			count+=" and meter_name like '"+meterName+"%'";
    		}
    		if(meterPositionName!=null&&!"".equals(meterPositionName))
			{
				sql+=" and meter_position_name like '%"+meterPositionName+"%'";
				count+=" and meter_position_name like '%"+meterPositionName+"%'";
			}
			if (startTime != null && !startTime.equals("")) {
				sql+=" and read_time>='"+startTime+"'";
				count+=" and read_time>='"+startTime+"'";
			}
			if (endTime != null && !endTime.equals("")) {
				sql+=" and read_time<='"+endTime+"'";
				count+=" and read_time<='"+endTime+"'";
			}
			//sql+="  order by free.read_time desc";
			dataList=hod2000MeterInfoHistoryService.findByNHQL(page, pageSize, sql);
    		List list=new ArrayList();
    		Object[] objects;
    		Map map;
    		for (int i = 0; i < dataList.size(); i++) {
    			objects=(Object[]) dataList.get(i);
    			map=new HashMap();
    			map.put("id", objects[0]);
    			map.put("meterName", objects[2]);
    			//map.put("clearEnergy", objects[3]);
    			map.put("clearEnergy", Arith.dataFormat(objects[3]));
    			map.put("clearFlow", objects[4]);
    			map.put("readTime", objects[5]);
    			map.put("cleatDate", Utils.dateToStrLong(Utils.strToDateLong(objects[1].toString())));
    			map.put("meterPositionName", objects[6]);
				list.add(map);
			}
    		int totalRecord =Integer.parseInt(hod2000MeterInfoHistoryService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000MeterInfoHistoryAction->doSelectFree:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doSelectFree:",e);
		}
	}
	/**
	 * 月冻结数据打印
	 * @return
	 */
	public String historyFreePrint()
	{
		try {
			OperatorLog.addOperatorLog("月冻结数据的打印预览");
			request=ServletActionContext.getRequest();
    		String meterName=request.getParameter("meterName");//表号
    		String meterPositionName=request.getParameter("meterPositionName");
    		String startTime=request.getParameter("startTime");
    		String endTime=request.getParameter("endTime");
    		String sql="select t_id,clear_date,meter_name,clear_energy,clear_flow,read_time,meter_position_name from meterFree where 1=1";
    		if(meterName!=null&&!meterName.equals(""))
    		{
    			sql+=" and meter_name like '"+meterName+"%'";
    		}
    		if(meterPositionName!=null&&!"".equals(meterPositionName))
			{
				sql+=" and meter_position_name like '%"+meterPositionName+"%'";
			}
			if (startTime != null && !startTime.equals("")) {
				sql+=" and read_time>='"+startTime+"'";
			}
			if (endTime != null && !endTime.equals("")) {
				sql+=" and read_time<='"+endTime+"'";
			}
			//sql+="  order by free.read_time desc";
			dataList=hod2000MeterInfoFreezeService.findByNHQL(sql);
    		List list=new ArrayList();
    		Object[] objects;
    		Map map;
    		for (int i = 0; i < dataList.size(); i++) {
    			objects=(Object[]) dataList.get(i);
    			map=new HashMap();
    			map.put("id", objects[0]);
    			map.put("meterName", objects[2]);
    			//map.put("clearEnergy", objects[3]);
    			map.put("clearEnergy", Arith.dataFormat(objects[3]));
    			map.put("clearFlow", objects[4]);
    			map.put("readTime", objects[5]);
    			map.put("cleatDate", objects[1]);
    			map.put("meterPositionName", objects[6]);
				list.add(map);
			}
    		request.setAttribute("freeList", list);
			return "historyFreePrint";
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doSelectFree:",e);
			msg.setError1(e.toString());
			request.setAttribute("message",msg);
			return "message";
		}
	}
	public String meterWarmPrint(){
		try {
			OperatorLog.addOperatorLog("表计报警信息打印");
			request=ServletActionContext.getRequest();
			String tab=request.getParameter("tab");
			Object[] objects;
			Map map;
			List list=new ArrayList();
			if(tab!=null&&tab.equals("1"))
			{
				dataList=hod2000MeterInfoHistoryService.findMeterWarm();
				for (int i = 0; i < dataList.size(); i++) {
					objects=(Object[]) dataList.get(i);
					map=new HashMap();
					map.put("tid", objects[0]);
					map.put("meterName", objects[2]);
					map.put("valveStatus", objects[3]);
					map.put("batteryStatus", objects[4]);
					map.put("address", objects[5]);
					map.put("isOnline", objects[1]);
					map.put("eepromStatus", objects[6]);
					map.put("flowsensorStatus", objects[7]);
					map.put("tepdownStatus", objects[8]);
					map.put("tepupStatus", objects[9]);
					list.add(map);
				}
	    		request.setAttribute("dataList", list);
			}
			return "meterWarmPrint";
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->meterWarmPrint:",e);
			msg.setError1(e.toString());
			request.setAttribute("message",msg);
			return "message";
		}
	}
	
	public void doSelectMeterWarm()
	{
		try {
			request=ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			Object[] objects;
			Map map;
			List list=new ArrayList();
			//dataList=hod2000MeterInfoHistoryService.findMeterWarm();
			//有阀门的情况下：String sql="select meter_id, isonline,meter_name,valve_status,battery_status,meter_position_name from hod2000_meter where meter_able=1 and ( valve_status=2 or battery_status=1 or isonline=2)";
			String sql="select meter_id, isonline,meter_name,valve_status,battery_status,meter_position_name,eeprom_status,flowsensor_status,tepdown_status,tepup_status from hod2000_meter where meter_able=1 and (battery_status=1 or isonline=2)";
			String count="select count(*) from hod2000_meter where meter_able=1 and (battery_status=1 or isonline=2)";
			dataList=hod2000MeterInfoHistoryService.findByNHQL(page, pageSize, sql);
			for (int i = 0; i < dataList.size(); i++) {
				objects=(Object[]) dataList.get(i);
				map=new HashMap();
				map.put("tid", objects[0]);
				map.put("meterName", objects[2]);
				map.put("valveStatus", objects[3]);
				map.put("batteryStatus", objects[4]);
				map.put("address", objects[5]);
				map.put("isOnline", objects[1]);
				map.put("eepromStatus", objects[6]);
				map.put("flowsensorStatus", objects[7]);
				map.put("tepdownStatus", objects[8]);
				map.put("tepupStatus", objects[9]);
				list.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000MeterInfoHistoryService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000MeterInfoHistoryAction->doSelectMeterWarm:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->doSelectMeterWarm:",e);
		}
	}
	
	//查询口径调转至异常用量统计页面
	public String toException()
	{
		request=ServletActionContext.getRequest();
		// 口径
		request.setAttribute("caliber", hod2000SysparameterService.findByType(1));
		return "exceptiontochart";
	}
	
	//连续未使用用户信息
	public void getUnUsed()
	{
		try {
			request=ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			//每月用户用量的最低值
			String leastValue=request.getParameter("leastValue");
			//未使用月数
			String unusedTime=request.getParameter("unusedTime");
			//查询月份
			String startTime=request.getParameter("startTime");
			//行政编码
			String code=request.getParameter("code");
			if(leastValue==null||"".equals(leastValue))
			{
				out.write("{success:false,msg:'每月用户用量的最低值不能为空!'}");
				return;
			}
			if(unusedTime==null||"".equals(unusedTime))
			{
				out.write("{success:false,msg:'未使用月数不能为空!'}");
				return;
			}
			if(startTime==null||"".equals(startTime))
			{
				out.write("{success:false,msg:'查询的月份不能为空!'}");
				return;
			}
			String meterName="";
			String meterNames="";
			List list=null;
			//查询正在正常使用的所有的表号
			dataList=hod2000MeterService.findbyUserMeterName();
			for (int i = 0; i < dataList.size(); i++) {
				meterName=dataList.get(i).toString();
				list=hod2000MeterInfoHistoryService.findByMeterName(meterName,startTime);
				if(list.size()>0)
				{
					if(A(list,leastValue,unusedTime,startTime))
					{
						meterNames+=meterName+",";
					}
				}
			}
			if(meterNames.length()>0)
			{
				meterNames=meterNames.substring(0,meterNames.length()-1);
				//根据表号查询表的地理位置
				Map map;
				Object[] obj;
				List list2=new ArrayList();
				dataList=hod2000MeterService.findbyMeterNames(meterNames,code);
				for (int i = 0; i < dataList.size(); i++) {
					obj=(Object[]) dataList.get(i);
					map=new HashMap();
					map.put("meterName", obj[0]);
					map.put("roomName", obj[1]);
					map.put("buildingName", obj[2]);
					map.put("communityName", obj[3]);
					map.put("unitName", obj[4]);
					list2.add(map);
				}
				OperatorLog.addOperatorLog("连续未使用用户信息查询");
				out.write("{success:true,data:"+JSONArray.fromObject(list2).toString()+"}");
			}
			else
			{
				out.write("{success:false,msg:'没有数据!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->getUnUsed:",e);
			out.write("{success:false,msg:'"+e.toString()+"'}");
		}
	}
	
	//每个月用户用量连续若干天低于操作员设置的用量
	public boolean A(List list,String leastValue,String unusedTime,String startTime)
	{
		List<String> list2=new ArrayList<String>();
		boolean flag=false;
		Object[] obj;
		int day=DateUtil.getDay(startTime);//这个月的天数
		int last=0;
		int first=0;
		int index=1;
		int unusedDay=Integer.parseInt(unusedTime);
		String startHeat="";
		String endHeat="";
		while(true){
			list2=new ArrayList<String>();
			for(int i=index;i<=index+unusedDay;i++){
				for (int j = 0; j <list.size(); j++) {//j可以放while外面定义
					obj=(Object[]) list.get(j);
					first=Integer.parseInt(obj[1].toString().substring(8,10));//截取抄表时间的日期
					if(i==first){
						list2.add(obj[0].toString());
					}
				}
			}
			if(list2.size()>0){
				startHeat=list2.get(0);
				endHeat=list2.get(list2.size()-1);
				//应该同时满足startHeat与endHeat不在同一天，Zx+1-Zo<未使用时间(天)*每月用户用量的最低值(KWh)
				if(Arith.dataFormat(Arith.subtract(endHeat,startHeat, 2))<Double.parseDouble(leastValue)*Integer.parseInt(unusedTime))
				{
					flag=true;
					break;
				}
			}
			//循环到这个月的最后一天后退出循环
			if(index+unusedDay==day){
				break;
			}
			index++;
		}
		return flag;
	}
	
	//根据表号与月份查询历史记录
	public void getHistoryDetail()
	{
		try {
			request=ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String months=request.getParameter("months");
			String meterName=request.getParameter("meterName");
			//根据表号查询表的地理位置
			Map map;
			Object[] obj;
			List list=new ArrayList();
			dataList=hod2000MeterInfoHistoryService.findByMeterName(meterName,months);
			for (int i = 0; i < dataList.size(); i++) {
				obj=(Object[]) dataList.get(i);
				map=new HashMap();
				//map.put("currentEnergy", obj[0]);
				map.put("currentEnergy", Arith.dataFormat(obj[0]));
				map.put("readTime", obj[1].toString());
				map.put("meterName", obj[2]);
				map.put("accumulateFlow", obj[3]);
				map.put("meterFlow", obj[4]);
				map.put("accumulateTime", obj[5]);
				map.put("supplyTemper", obj[6]);
				map.put("backTemper", obj[7]);
				list.add(map);
			}
			out.write("{totalCount:"+String.valueOf(list.size())+",result:"+JSONArray.fromObject(list).toString()+"}");
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->getHistoryDetail:",e);
			out.write("{success:false,msg:'"+e.toString()+"'}");
		}
	}
	
	//用量异常
	public void getAbnormal()
	{
		try {
			request=ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String average=request.getParameter("average");
			String coefficient=request.getParameter("coefficient");
			String months=request.getParameter("months");
			String meterStyle=request.getParameter("meterStyle");
			String code=request.getParameter("code");
			String sql="select meter_name from hod2000_meter where meter_parent!='0' and meter_able=1";
			if(average==null||"".equals(average))
			{
				out.write("{success:false,msg:'月平均用量不能为空!'}");
				return;
			}
			if(coefficient==null||"".equals(coefficient))
			{
				out.write("{success:false,msg:'用户异常系数不能为空!'}");
				return;
			}
			if(months==null||"".equals(months))
			{
				out.write("{success:false,msg:'查询的月份不能为空!'}");
				return;
			}
			if(meterStyle!=null&&!"".equals(meterStyle))
			{
				sql+=" and meter_style="+Integer.parseInt(meterStyle);
			}
			double averageInt=Double.parseDouble(average);
			int coefficientInt=Integer.parseInt(coefficient);
			String meterName="";
			String meterNames="";
			List list;
			//查询表计类型，正在使用的所有的表计
			dataList=hod2000MeterService.findByNHQL(sql);
			for (int i = 0; i < dataList.size(); i++) {
				meterName=dataList.get(i).toString();
				list=hod2000MeterInfoHistoryService.findByMeterName(meterName,months);
				if(list.size()>0)
				{
					double startEnergy=Double.parseDouble(((Object[]) list.get(0))[0].toString());//月初累计用量
					double endEnergy=Double.parseDouble(((Object[]) list.get(list.size()-1))[0].toString());//月末累计用量
					double current_energy=Arith.dataFormat(Arith.subtract(endEnergy,startEnergy,2));//用户用量，转换为千瓦时
					double difference=Math.abs(current_energy-averageInt);//用量差值绝对值
					double userCoefficient=difference/averageInt;//用户用量系数
					if(userCoefficient*100>coefficientInt)
					{
						meterNames+=meterName+",";
					}
				}
			}
			if(meterNames.length()>0)
			{
				meterNames=meterNames.substring(0,meterNames.length()-1);
				//根据表号查询表的地理位置
				Map map;
				Object[] obj;
				List list2=new ArrayList();
				dataList=hod2000MeterService.findbyMeterNames(meterNames,code);
				for (int i = 0; i < dataList.size(); i++) {
					obj=(Object[]) dataList.get(i);
					map=new HashMap();
					map.put("meterName", obj[0]);
					map.put("roomName", obj[1]);
					map.put("buildingName", obj[2]);
					map.put("communityName", obj[3]);
					map.put("unitName", obj[4]);
					list2.add(map);
				}
				OperatorLog.addOperatorLog("用量异常信息查询");
				out.write("{success:true,data:"+JSONArray.fromObject(list2).toString()+"}");
			}
			else
			{
				out.write("{success:false,msg:'没有数据!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterInfoHistoryAction-->getAbnormal:",e);
			out.write("{success:false,msg:'"+e.toString()+"'}");
		}
	}
	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000MeterInfoHistoryService(IHod2000MeterInfoHistoryService hod2000MeterInfoHistoryService) {
		this.hod2000MeterInfoHistoryService = hod2000MeterInfoHistoryService;
	}
 
	public Hod2000MeterInfoHistory getHod2000MeterInfoHistory() {
		return hod2000MeterInfoHistory;
	}

	public void setHod2000MeterInfoHistory(Hod2000MeterInfoHistory hod2000MeterInfoHistory) {
		this.hod2000MeterInfoHistory = hod2000MeterInfoHistory;
	}

	public void setHod2000RegionService(IHod2000RegionService hod2000RegionService) {
		this.hod2000RegionService = hod2000RegionService;
	}

	public void setCityService(IHod2000CityService cityService) {
		this.cityService = cityService;
	}

	public void setCountyService(IHod2000CountyService countyService) {
		this.countyService = countyService;
	}

	public void setHod2000VillageService(
			IHod2000VillageService hod2000VillageService) {
		this.hod2000VillageService = hod2000VillageService;
	}

	public void setCommunityService(IHod2000CommunityService communityService) {
		this.communityService = communityService;
	}

	public void setBuildingService(IHod2000BuildingService buildingService) {
		this.buildingService = buildingService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}

	public IHod2000MeterInfoFreezeService getHod2000MeterInfoFreezeService() {
		return hod2000MeterInfoFreezeService;
	}

	public void setHod2000MeterInfoFreezeService(
			IHod2000MeterInfoFreezeService hod2000MeterInfoFreezeService) {
		this.hod2000MeterInfoFreezeService = hod2000MeterInfoFreezeService;
	}

	public void setHod2000MeterService(IHod2000MeterService hod2000MeterService) {
		this.hod2000MeterService = hod2000MeterService;
	}

	public void setHod2000SysparameterService(
			IHod2000SysparameterService hod2000SysparameterService) {
		this.hod2000SysparameterService = hod2000SysparameterService;
	}

	public void setHod2000UnitService(IHod2000UnitService hod2000UnitService) {
		this.hod2000UnitService = hod2000UnitService;
	}
	
	
}
