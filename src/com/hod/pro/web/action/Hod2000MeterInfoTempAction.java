package  com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.hod.message.ConstantValue;
import com.hod.message.MessageBase;
import com.hod.message.Util;
import com.hod.pojo.Hod2000DownInfoCache;
import com.hod.pojo.Hod2000Meter;
import com.hod.pojo.Hod2000MeterInfoTemp;
import com.hod.pro.model.service.IHod2000DownInfoCacheService;
import com.hod.pro.model.service.IHod2000MeterInfoTempService;
import com.hod.pro.model.service.IHod2000MeterService;
import com.hod.util.Arith;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000MeterInfoTempAction 实时数据
 * @author yixiang
 */
public class Hod2000MeterInfoTempAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000MeterInfoTempService hod2000MeterInfoTempService;
	private IHod2000DownInfoCacheService hod2000DownInfoCacheService;
	private IHod2000MeterService hod2000MeterService;
	private Hod2000MeterInfoTemp hod2000MeterInfoTemp;
	private Hod2000Meter hod2000Meter;
	private PrintWriter out;
	private List dataList;
	private Message msg=Message.getInstance();
	private MyJsonOut myjsonOut = new MyJsonOut();
	private Logger log = Logger.getLogger(Hod2000MeterInfoTempAction.class.getName());
	private static ConcurrentHashMap<String,String> meterHash = new ConcurrentHashMap<String,String>();
	
	/**
	 * 下发实时抄读命令
	 */
	public void readMeter()
	{
		try {
			request=ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String idStr=request.getParameter("ids");
			String[] meterIds=idStr.split(",");
			meterHash.clear();
			List<Hod2000Meter> mList = new ArrayList<Hod2000Meter>();
			for (int i = 0; i < meterIds.length; i++) {
				hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(Integer.parseInt(meterIds[i]));
				mList.add(hod2000Meter);
			}
			List<String> conList = new ArrayList<String>();
			for(int i=0;i<mList.size();i++){
				String conNumber = mList.get(i).getHod2000Concentrator().getConNumber();
				boolean flag =true;
				for(String oldCon:conList){
					if(oldCon.equals(conNumber)){
						flag = false;
						break;
					}
				}
				if(flag){
					conList.add(conNumber);
				}
			}
			Hod2000DownInfoCache downInfo=null;
			Hod2000Meter hMeter=null;
			String meterNames="";//表号
			String data="";//数据域
			int length=mList.size();//用户选择的要读数据的表计个数
			int size=10;//每次最多发10条
			for(String con:conList){
				for (int i = 1; i <=length; i++) {
					hMeter=mList.get(i-1);
					if(2==hMeter.getHod2000Concentrator().getConIsonline())
					{
						out.write("{success:false,msg:'"+hod2000Meter.getHod2000Concentrator().getConNumber()+"集中器不在线!'}");
						return;
					}
					if(con.equals(hMeter.getHod2000Concentrator().getConNumber())){
						meterNames+=hMeter.getMeterName()+",";
						data+=Util.decimalToHexadecimal(hMeter.getMeterIndex(), 4);
						if(0==i%size)
						{
							downInfo=new Hod2000DownInfoCache();
							downInfo.setSendFlag(-1);//发送状态：-1为为发送，0为发送成功，1为发送中，2为发送失败
							downInfo.setSendTimes(0);//发送次数
							meterNames=meterNames.substring(0,meterNames.length()-1);
							downInfo.setMeterName(meterNames);
							downInfo.setConNumber(con);
							downInfo.setProtocolType(ConstantValue.readMeter);//协议类型
							downInfo.setSubmitTime(Utils.getNowDate());
							MessageBase base=new MessageBase(ConstantValue.conType,Util.getAddr(con,ConstantValue.procuNum),1);
							downInfo.setSendContent(Util.bytesToHexString(base.parseDownInfoToBytes(Util.getData(ConstantValue.readMeter,Util.decimalToHexadecimal(size, 4)+data,ConstantValue.SEQ))));
							hod2000DownInfoCacheService.save(downInfo);
							meterNamePutMap(meterNames);
							log.info("实时抄表："+downInfo.getSendContent());
							meterNames="";
							data="";
							continue;
						}
						if(i==length)
						{
							downInfo=new Hod2000DownInfoCache();
							downInfo.setSendFlag(-1);//发送状态：-1为为发送，0为发送成功，1为发送中，2为发送失败
							downInfo.setSendTimes(0);//发送次数
							meterNames=meterNames.substring(0,meterNames.length()-1);
							downInfo.setMeterName(meterNames);
							downInfo.setConNumber(con);
							downInfo.setProtocolType(ConstantValue.readMeter);//协议类型
							downInfo.setSubmitTime(Utils.getNowDate());
							MessageBase base=new MessageBase(ConstantValue.conType,Util.getAddr(con,ConstantValue.procuNum),1);
							downInfo.setSendContent(Util.bytesToHexString(base.parseDownInfoToBytes(Util.getData(ConstantValue.readMeter,Util.decimalToHexadecimal(length%size, 4)+data,ConstantValue.SEQ))));
							hod2000DownInfoCacheService.save(downInfo);
							meterNamePutMap(meterNames);
							log.info("实时抄表："+downInfo.getSendContent());
						}
					}
				}
			}
			//将实时抄读表中的数据全部删除，保证每次读取的数据都是最新数据
			DetachedCriteria criteria=DetachedCriteria.forClass(Hod2000MeterInfoTemp.class);
			List tempList=hod2000MeterInfoTempService.findByCriteria(criteria);
			for (int i = 0; i <tempList.size(); i++) {
				Hod2000MeterInfoTemp temp=(Hod2000MeterInfoTemp) tempList.get(i);
				hod2000MeterInfoTempService.delete(temp);
			}
			OperatorLog.addOperatorLog("下发实时抄读命令");
			out.write("{success:true,msg:'发送抄读信息成功，请等待抄读结果!'}");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000MeterInfoTempAction-->readMeter:",e);
			out.write("{success:false,msg:'发送抄读信息失败，请稍后再试!'}");
		}
	}
	
	/**
	 * 读取实时抄读数据
	 */
	public void readTemp()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			String ids=request.getParameter("ids");
			Object[] objects;
			Map map;
			List list=new ArrayList();
			//dataList=hod2000MeterInfoTempService.getDataByIds(ids);
			String sql="select read_time,temp.meter_name,current_energy,accumulate_flow,meter_flow,accumulate_time,supply_temper,back_temper,valve_status,battery_status,isonline,meter_position_name,eeprom_status,flowsensor_status,tepdown_status,tepup_status from hod2000_meter meter right join hod2000_meterInfoTemp temp on meter.meter_name=temp.meter_name where meter.meter_id in ("+ids+") order by temp.read_time desc";
			String countSql="select count(*) from hod2000_meter meter right join hod2000_meterInfoTemp temp on meter.meter_name=temp.meter_name where meter.meter_id in ("+ids+")";
			dataList=hod2000MeterInfoTempService.findByNHQL(page, pageSize, sql);
			int dataLen = 0;
			if(dataList != null){
				dataLen = dataList.size();
			}
			for (int i = 0; i < dataLen; i++) {
				objects=(Object[]) dataList.get(i);
				if(meterHash.containsKey(objects[1])){
					meterHash.remove(objects[1]);
				}
				map=new HashMap();
				//map.put("clientName", objects[0]);
				map.put("meterName", objects[1]);
				//map.put("currentEnergy", objects[2]);
				map.put("currentEnergy", Arith.dataFormat(objects[2]));
				map.put("accumulateFlow", objects[3]);
				map.put("meterFlow", objects[4]);
				map.put("accumulateTime", objects[5]);
				map.put("supplyTemper", objects[6]);
				map.put("backTemper", objects[7]);
				map.put("valveStatus", objects[8]);
				map.put("batteryStatus", objects[9]);
				map.put("isOnline", objects[10]);
				map.put("readTime", objects[0]);
				map.put("meterPositionName", objects[11]);
				map.put("eepromStatus", objects[12]);
				map.put("flowsensorStatus", objects[13]);
				map.put("tepdownStatus", objects[14]);
				map.put("tepupStatus", objects[15]);
				list.add(map);
			}
			
			if(meterHash.size() > 0){
				if(dataLen < pageSize){//当查询数据小于页面数据条数
					Iterator<String> iter = meterHash.keySet().iterator();
					while (iter.hasNext()) {
						if(dataLen >= pageSize){
							break;
						}
						map=new HashMap();
						map.put("meterName", iter.next());
						map.put("currentEnergy", "");
						map.put("accumulateFlow", "");
						map.put("meterFlow", "");
						map.put("accumulateTime", "");
						map.put("supplyTemper", "");
						map.put("backTemper", "");
						map.put("valveStatus", "");
						map.put("batteryStatus", "");
						map.put("isOnline", "");
						map.put("readTime", "");
						map.put("meterPositionName", "");
						map.put("eepromStatus", "");
						map.put("flowsensorStatus", "");
						map.put("tepdownStatus", "");
						map.put("tepupStatus", "");
						list.add(map);
						dataLen++;
					}
				}
			}
			int totalRecord =Integer.parseInt(hod2000MeterInfoTempService.findByNHQL(countSql).get(0).toString());
			myjsonOut.OutByObject("Hod2000MeterInfoTempAction-->readTemp:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterInfoTempAction-->readTemp:",e);
		}
	}
	
	/**
	 * 地图信息实时抄读
	 */
	public void getReadMeter()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("buildingId");
			String str="";
			//查询该楼栋下的所有表计，再进行抄读
			if(buildingId!=null&&!buildingId.equals(""))
			{
				dataList=hod2000MeterService.findByBuildingById(Integer.parseInt(buildingId));
				List list=new ArrayList();
				Object[] objects;
				for (int i = 0; i < dataList.size(); i++) {
					str=Util.decimalToHexadecimal(1, 4);
					objects=(Object[]) dataList.get(i);
					hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(Integer.parseInt(objects[3].toString()));
					if(2==hod2000Meter.getHod2000Concentrator().getConIsonline())
					{
						out.write("{success:false,msg:'"+hod2000Meter.getHod2000Concentrator().getConNumber()+"集中器不在线!'}");
						return;
					}
					Hod2000DownInfoCache downInfo=new Hod2000DownInfoCache();
					downInfo.setSendFlag(-1);//发送状态：-1为为发送，0为发送成功，1为发送中，2为发送失败
					downInfo.setSendTimes(0);//发送次数
					//downInfo.setHod2000Concentrator(hod2000Meter.getHod2000Concentrator());
					downInfo.setConNumber(hod2000Meter.getHod2000Concentrator().getConNumber());
					downInfo.setProtocolType(ConstantValue.readMeter);//协议类型
					downInfo.setSubmitTime(Utils.getNowDate());
					str+=Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4);
					MessageBase base=new MessageBase(ConstantValue.conType,Util.getAddr(hod2000Meter.getHod2000Concentrator().getConNumber(),ConstantValue.procuNum),1);
					downInfo.setSendContent(Util.bytesToHexString(base.parseDownInfoToBytes(Util.getData(ConstantValue.readMeter,str,ConstantValue.SEQ))));
					hod2000DownInfoCacheService.save(downInfo);
				}
				//将实时抄读表中的数据全部删除，保证每次读取的数据都是最新数据
				DetachedCriteria criteria=DetachedCriteria.forClass(Hod2000MeterInfoTemp.class);
				List tempList=hod2000MeterInfoTempService.findByCriteria(criteria);
				for (int i = 0; i <tempList.size(); i++) {
					Hod2000MeterInfoTemp temp=(Hod2000MeterInfoTemp) tempList.get(i);
					hod2000MeterInfoTempService.delete(temp);
				}
				OperatorLog.addOperatorLog("地图上下发实时抄读命令");
				out.write("{success:true,msg:'发送抄读信息成功，请等待抄读结果!'}");
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000MeterInfoTempAction-->getReadMeter:",e);
			out.write("{success:false,msg:'发送抄读信息失败，请稍后再试!'}");
		}
	}
	
	public void ReadMeter()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("buildingId");
			StringBuffer ids=new StringBuffer();
			if(buildingId!=null&&!buildingId.equals(""))
			{
				dataList=hod2000MeterService.findByBuildingById(Integer.parseInt(buildingId));
				List list=new ArrayList();
				Object[] objects;
				Map map;
				for (int i = 0; i < dataList.size(); i++) {
					objects=(Object[]) dataList.get(i);
					ids.append(objects[3]+",");
				}
				if(ids!=null&&ids.length()>0)
				{
					List list2=hod2000MeterInfoTempService.getDataByIds(ids.substring(0,ids.length()-1).toString());
					for (int i = 0; i <list2.size(); i++) {
						objects=(Object[]) list2.get(i);
						map=new HashMap();
						//map.put("clientName", objects[0]);
						map.put("meterName", objects[1]);
						//map.put("currentEnergy", objects[2]);
						map.put("currentEnergy", Arith.dataFormat(objects[2]));
						map.put("accumulateFlow", objects[3]);
						map.put("meterFlow", objects[4]);
						map.put("accumulateTime", objects[5]);
						map.put("supplyTemper", objects[6]);
						map.put("backTemper", objects[7]);
						map.put("valveStatus", objects[8]);
						map.put("batteryStatus", objects[9]);
						map.put("isOnline", objects[10]);
						map.put("readTime", objects[0]);
						list.add(map);
					}
				}
				out.write("{totalCount:"+String.valueOf(list.size())+",result:"+JSONArray.fromObject(list).toString()+"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{success:false}");
		}
	}
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000MeterInfoTemp.class);
			dataList = Page.util(request, hod2000MeterInfoTempService,  dc);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}    
	
	public void meterNamePutMap(String meterNames){
		if(meterNames != null && !meterNames.equals("")){
			for(String meterName : meterNames.split(",")){
				if(meterName != null && !meterName.equals("")){
					meterHash.put(meterName, meterName);
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
	
    
	public void setHod2000MeterInfoTempService(IHod2000MeterInfoTempService hod2000MeterInfoTempService) {
		this.hod2000MeterInfoTempService = hod2000MeterInfoTempService;
	}
 
	public Hod2000MeterInfoTemp getHod2000MeterInfoTemp() {
		return hod2000MeterInfoTemp;
	}

	public void setHod2000MeterInfoTemp(Hod2000MeterInfoTemp hod2000MeterInfoTemp) {
		this.hod2000MeterInfoTemp = hod2000MeterInfoTemp;
	}

	public IHod2000MeterService getHod2000MeterService() {
		return hod2000MeterService;
	}

	public void setHod2000MeterService(IHod2000MeterService hod2000MeterService) {
		this.hod2000MeterService = hod2000MeterService;
	}

	public Hod2000Meter getHod2000Meter() {
		return hod2000Meter;
	}

	public void setHod2000Meter(Hod2000Meter hod2000Meter) {
		this.hod2000Meter = hod2000Meter;
	}

	public IHod2000DownInfoCacheService getHod2000DownInfoCacheService() {
		return hod2000DownInfoCacheService;
	}

	public void setHod2000DownInfoCacheService(
			IHod2000DownInfoCacheService hod2000DownInfoCacheService) {
		this.hod2000DownInfoCacheService = hod2000DownInfoCacheService;
	}
	
	
}
