package  com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hod.message.ConstantValue;
import com.hod.message.MessageBase;
import com.hod.message.Util;
import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Concentrator;
import com.hod.pojo.Hod2000DeviceInitParameter;
import com.hod.pojo.Hod2000Meter;
import com.hod.pojo.Hod2000MeterType;
import com.hod.pojo.Hod2000ParaDownInfoCache;
import com.hod.pojo.Hod2000Sysparameter;
import com.hod.pro.model.service.IHod2000BuildingService;
import com.hod.pro.model.service.IHod2000CityService;
import com.hod.pro.model.service.IHod2000CommunityService;
import com.hod.pro.model.service.IHod2000ConcentratorService;
import com.hod.pro.model.service.IHod2000CountyService;
import com.hod.pro.model.service.IHod2000DeviceInitParameterService;
import com.hod.pro.model.service.IHod2000MeterService;
import com.hod.pro.model.service.IHod2000MeterTypeService;
import com.hod.pro.model.service.IHod2000ParaDownInfoCacheService;
import com.hod.pro.model.service.IHod2000RegionService;
import com.hod.pro.model.service.IHod2000SysparameterService;
import com.hod.pro.model.service.IHod2000VillageService;
import com.hod.util.Message;
import com.hod.util.NetworkTimeUtil;
import com.hod.util.OperatorLog;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000ConcentratorAction 集中器管理
 * @author yixiang
 */
public class Hod2000ConcentratorAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000ConcentratorService hod2000ConcentratorService;
	private Hod2000Concentrator hod2000Concentrator;
	private List dataList;
	private PrintWriter out;
	private IHod2000CityService hod2000CityService;
	private IHod2000CountyService hod2000CountyService;
	private IHod2000CommunityService hod2000CommunityService;
	private IHod2000BuildingService hod2000BuildingService;
	private IHod2000VillageService hod2000VillageService;
	private IHod2000RegionService hod2000RegionService;
	private IHod2000MeterService hod2000MeterService;
	private IHod2000SysparameterService hod2000SysparameterService;
	private IHod2000ParaDownInfoCacheService cacheService;
	private Hod2000ParaDownInfoCache cache;
	private Hod2000DeviceInitParameter deviceInitParameter;
	private IHod2000DeviceInitParameterService deviceInitParameterService;
	private IHod2000MeterTypeService hod2000MeterTypeService;
	private MessageBase message;
	//日志信息操作实例
	private Message msg = Message.getInstance();
	private Logger log = Logger.getLogger(Hod2000ConcentratorAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();
	
	/**
	 * 转到新增集中器页面之前要查询冻结间隔参数值
	 * @return
	 */
	public String toAdd()
	{
		request = ServletActionContext.getRequest();
		//request.setAttribute("agency", hod2000SysparameterService.findByType(5));
		//request.setAttribute("agencyConn", hod2000SysparameterService.findByType(6));
		//request.setAttribute("meterRead", hod2000SysparameterService.findByType(7));
		request.setAttribute("freeRead", hod2000SysparameterService.findByType(8));
		// 表计类型
		DetachedCriteria meterType = DetachedCriteria.forClass(Hod2000MeterType.class);
		request.setAttribute("meterType", hod2000MeterTypeService.findByCriteria(meterType));
		return "add";
	}
	
	/**
	 * 恢复出厂设置
	 * @return
	 */
	public void factoryReset()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String conId=request.getParameter("id");
			String masterConNum="";//从机的主机编号
			if(conId!=null&&!conId.equals(""))
			{
				hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(conId));
				//判断集中器是否在线，不在线时退出
				if(2==hod2000Concentrator.getConIsonline())
				{
					out.write("{success:false,msg:'"+hod2000Concentrator.getConNumber()+"集中器不在线!'}");
					return;
				}
				if(hod2000Concentrator.getMasterConId()!=null&&!"".equals(hod2000Concentrator.getMasterConId()))//如果是从机
				{	
					masterConNum=((Hod2000Concentrator)hod2000ConcentratorService.findById(hod2000Concentrator.getMasterConId())).getConNumber();
				}
				else//如果是主机
				{
					masterConNum=hod2000Concentrator.getConNumber();
				}
				message=new MessageBase(ConstantValue.conType,Util.getAddr(hod2000Concentrator.getConNumber(),ConstantValue.procuNum),1);
				cache=new Hod2000ParaDownInfoCache();
				cache.setConNum(masterConNum);
				cache.setProtocolType(ConstantValue.factory);
				cache.setSendFlag(-1);
				cache.setState(0);
				//cache.setSubmitTime(new Date());
				cache.setSubmitTime(NetworkTimeUtil.getDate());
				cache.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(ConstantValue.factory,"",ConstantValue.SEQ))));
				cacheService.save(cache);
				log.info("恢复出厂设置:"+cache.getSendContent());
				long start_time = System.currentTimeMillis();
				while (true) {
					long end_time = System.currentTimeMillis();
					cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
					if(1==cache.getState())//成功
					{
						out.write("{success:true,msg:'恢复出厂设置成功!'}");
						OperatorLog.addOperatorLog(hod2000Concentrator.getConNumber()+"集中器恢复出厂设置");
						break;
					}
					if(2==cache.getState())//失败
					{
						out.write("{success:false,msg:'恢复出厂设置失败!'}");
						break;
					}
					if (end_time - start_time >= ConstantValue.timeout) { 
						cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
						if(0==cache.getState())//超时
						{
							out.write("{success:false,msg:'请求超时!'}");
						}
						break;
					}
				}
				cacheService.delete(cache);
			}
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->factoryReset:", e);
			out.write("{success:false}");
		}
	}
	
	/**
	 * 设置结算日
	 * @return
	 */
	public void setClearDate()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String clearDate=request.getParameter("clearDate");
			String ids=request.getParameter("delIds");
			hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(ids));
			String masterConNum="";//从机的主机编号
			//判断集中器是否在线，不在线时退出
			if(2==hod2000Concentrator.getConIsonline())
			{
				out.write("{success:false,msg:'"+hod2000Concentrator.getConNumber()+"集中器不在线!'}");
			}
			else
			{
				if(hod2000Concentrator.getMasterConId()!=null&&!"".equals(hod2000Concentrator.getMasterConId()))//如果是从机
				{	
					masterConNum=((Hod2000Concentrator)hod2000ConcentratorService.findById(hod2000Concentrator.getMasterConId())).getConNumber();
				}
				else//如果是主机
				{
					masterConNum=hod2000Concentrator.getConNumber();
				}
				int flag=initParams(masterConNum,hod2000Concentrator.getConNumber(), Util.decimalToHexadecimal(Integer.parseInt(clearDate),2), ConstantValue.readDate);
				if(1==flag)
				{
					hod2000Concentrator.setClearDate(Integer.parseInt(clearDate));
					hod2000ConcentratorService.update(hod2000Concentrator);
					out.write("{success:true,msg:'设置结算日成功!'}");
				}	
				if(2==flag)
				{
					out.write("{success:false,msg:'设置结算日失败!'}");
				}
				if(0==flag)
				{
					out.write("{success:false,msg:'请求超时!'}");
				}
			}
			OperatorLog.addOperatorLog(hod2000Concentrator.getConNumber()+"设置结算日");
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->setClearDate:", e);
			out.write("{success:false,msg:'请求失败!'}");
		}
	}
	
	/**
	 * 初始化参数
	 */
	public String setParams()
	{
		try {
			request = ServletActionContext.getRequest();
			String stationIP=request.getParameter("stationIP");
			String port=request.getParameter("port");
			String pid=request.getParameter("pid");
			deviceInitParameter.setPcol7(stationIP+":"+port);
			if(pid!=null&&!pid.equals(""))
			{
				Hod2000DeviceInitParameter de=(Hod2000DeviceInitParameter) deviceInitParameterService.findById(Integer.parseInt(pid));
				List<Hod2000Concentrator> list=hod2000ConcentratorService.findByHQL("from Hod2000Concentrator where conAble=1");//查询所有有效集中器
				String masterConNum="";//从机的主机编号
				int flag=0;//执行状态
				//备用IP地址、端口和APN
				String ip2=deviceInitParameter.getPcol8().split(":")[0];
				String port2=deviceInitParameter.getPcol8().split(":")[1];
				for (Hod2000Concentrator hod2000Concentrator : list) {
					if(hod2000Concentrator.getMasterConId()!=null&&!"".equals(hod2000Concentrator.getMasterConId()))//如果是从机
					{	
						//查询其主机编号
						masterConNum=((Hod2000Concentrator)hod2000ConcentratorService.findById(hod2000Concentrator.getMasterConId())).getConNumber();
					}
					else//如果是主机
					{
						masterConNum=hod2000Concentrator.getConNumber();
					}
					if(!de.getPcol7().equals(deviceInitParameter.getPcol7())||!de.getPcol9().equals(deviceInitParameter.getPcol9()))
					{
						if(2==hod2000Concentrator.getConIsonline())
						{
							msg.setError1(hod2000Concentrator.getConNumber()+"集中器不在线!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/hod2000Concentrator!showParams.do\">返回</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						flag=initParams(masterConNum,hod2000Concentrator.getConNumber(),Util.getIpStr(stationIP.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port), 4)+Util.getIpStr(ip2.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port2), 4)+Util.getAPN(deviceInitParameter.getPcol9(),32),ConstantValue.IP);
						if(2==flag)
						{
							msg.setError1("保存失败，集中器"+hod2000Concentrator.getConNumber()+"主站IP地址与端口设置失败!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/hod2000Concentrator!showParams.do\">返回</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						if(0==flag)
						{
							msg.setError1("保存失败，集中器"+hod2000Concentrator.getConNumber()+"主站IP地址与端口设置超时!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/hod2000Concentrator!showParams.do\">返回</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
					}
					if(!de.getPcol3().equals(deviceInitParameter.getPcol3())||!de.getPcol4().equals(deviceInitParameter.getPcol4())||!de.getPcol5().equals(deviceInitParameter.getPcol5())||!de.getPcol6().equals(deviceInitParameter.getPcol6()))
					{
						if(2==hod2000Concentrator.getConIsonline())
						{
							msg.setError1(hod2000Concentrator.getConNumber()+"集中器不在线!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/hod2000Concentrator!showParams.do\">返回</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						flag=initParams(masterConNum,hod2000Concentrator.getConNumber(),Util.decimalToHexadecimal(deviceInitParameter.getPcol1(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol2(),2)+Util.toByte(deviceInitParameter.getPcol3(), deviceInitParameter.getPcol4())+Util.decimalToHexadecimal(deviceInitParameter.getPcol5(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol6(),2),ConstantValue.communication);
						if(2==flag)
						{
							msg.setError1("保存失败，集中器"+hod2000Concentrator.getConNumber()+"终端上行通信口通信参数设置失败!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/hod2000Concentrator!showParams.do\">返回</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						if(0==flag)
						{
							msg.setError1("保存失败，集中器"+hod2000Concentrator.getConNumber()+"终端上行通信口通信参数设置超时!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/hod2000Concentrator!showParams.do\">返回</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
					}
				}
				de.setPcol1(deviceInitParameter.getPcol1());
				de.setPcol2(deviceInitParameter.getPcol2());
				de.setPcol3(deviceInitParameter.getPcol3());
				de.setPcol4(deviceInitParameter.getPcol4());
				de.setPcol5(deviceInitParameter.getPcol5());
				de.setPcol6(deviceInitParameter.getPcol6());
				de.setPcol7(deviceInitParameter.getPcol7());
				de.setPcol8(deviceInitParameter.getPcol8());
				de.setPcol9(deviceInitParameter.getPcol9());
				deviceInitParameterService.update(de);
			}
			else
			{
				deviceInitParameterService.save(deviceInitParameter);
			}
			msg.setMsg1("设置初始化参数成功!");
			OperatorLog.addOperatorLog("集中器初始化参数的保存");
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->setParams2:", e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Concentrator!showParams.do\">返回</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	/**
	 * 查询初始化参数
	 * @return
	 */
	public String showParams()
	{
		try {
			request = ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000DeviceInitParameter.class);
			dataList=deviceInitParameterService.findByCriteria(dc);
			if(dataList.size()>0)
			{
				deviceInitParameter=(Hod2000DeviceInitParameter) dataList.get(0);
				String[] ids=deviceInitParameter.getPcol7().split(":");
				request.setAttribute("stationIP",ids[0]);
				request.setAttribute("port",ids[1]);
			}
			OperatorLog.addOperatorLog("查询集中器初始化参数");
			return "params";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->showParams:", e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/subpage.jsp?id=6\">返回</a>");
			request.setAttribute("message", msg);
			return "message";
		}
		
	}
	
	/**
	 * 集中器的新增
	 * @return
	 */
	public String doSave() {
		try {
			int flag=1;
			request = ServletActionContext.getRequest();
			String conNum=request.getParameter("conNum");//集中器编号
			String init=request.getParameter("init");//是否初始化，初始化为服务器用，非初始化为客户用
			//集中器编号是否重复
			dataList=hod2000ConcentratorService.findByConNum2(conNum);
			if(dataList.size()>0)
			{
				msg.setError1("集中器编号不允许重复，请重新添加!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			String freezeInterval=request.getParameter("freezeInterval");
			String freezeIntervalValue=request.getParameter("freezeIntervalValue");
			String conAddress=request.getParameter("conAddress");
			String meterType=request.getParameter("meterType");
			if(init.equals("1"))
			{
				DetachedCriteria dc=DetachedCriteria.forClass(Hod2000DeviceInitParameter.class);
				dataList=deviceInitParameterService.findByCriteria(dc);
				if(dataList.size()>0)
				{
					deviceInitParameter=(Hod2000DeviceInitParameter) dataList.get(0);
					//终端上行通信口通信参数设置
					flag=initParams(conNum,Util.decimalToHexadecimal(deviceInitParameter.getPcol1(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol2(),2)+Util.toByte(deviceInitParameter.getPcol3(), deviceInitParameter.getPcol4())+Util.decimalToHexadecimal(deviceInitParameter.getPcol5(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol6(),2),ConstantValue.communication);
					if(2==flag)
					{
						msg.setError1("终端上行通信口通信参数设置失败，添加集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					if(0==flag)
					{
						msg.setError1("终端上行通信口通信参数设置超时，添加集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					//主站IP地址与端口
					String ip=deviceInitParameter.getPcol7().split(":")[0];
					String port=deviceInitParameter.getPcol7().split(":")[1];
					String ip2=deviceInitParameter.getPcol8().split(":")[0];
					String port2=deviceInitParameter.getPcol8().split(":")[1];
					flag=initParams(conNum,Util.getIpStr(ip.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port), 4)+Util.getIpStr(ip2.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port2), 4)+Util.getAPN(deviceInitParameter.getPcol9(),32),ConstantValue.IP);
					if(2==flag)
					{
						msg.setError1("主站IP地址与端口设置失败，添加集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					if(0==flag)
					{
						msg.setError1("主站IP地址与端口设置超时，添加集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					hod2000Concentrator.setTypeIndexs(null);
					//hod2000Concentrator.setConAddress(null);
					hod2000Concentrator.setFreezeInterval(null);
					hod2000Concentrator.setFreezeIntervalValue(null);
				}
				else
				{
					msg.setError1("请先设置集中器的初始化参数!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回列表</a>");
					request.setAttribute("message", msg);
					return "message";
				}
			}
			else
			{
				//冻结间隔 抄表间隔协议删除了 用00表示
				flag=initParams(conNum,"00"+Util.freezeInterval(freezeInterval, freezeIntervalValue),ConstantValue.free);
				if(2==flag)
				{
					msg.setError1("冻结间隔设置失败，添加集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("冻结间隔设置超时，添加集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//地理位置
				flag=initParams(conNum, Util.getConAddress(conAddress), ConstantValue.address);
				if(2==flag)
				{
					msg.setError1("地理位置设置失败，添加集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("地理位置设置超时，添加集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//表计类型关联
				if(meterType!=null&&!"".equals(meterType))
				{
					Hod2000MeterType metertype;
					StringBuffer data=new StringBuffer();
					String[] type=meterType.split(",");
					for (int i = 0; i < type.length; i++) {
						metertype=(Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(type[i]));
						data.append(Util.decimalToHexadecimal(type.length, 2));//本次配置表计信息索引个数
						data.append(Util.decimalToHexadecimal(Integer.parseInt(type[i]), 2));//表计类型信息索引编号
						data.append(Util.getAddress(metertype.getCompanyNum()));//厂商编号
						data.append(metertype.getTypeCode());//表计类型编码
						data.append(Util.decimalToHexadecimal(metertype.getCommunicationProtocol(), 2));//通信规约
						data.append(ConstantValue.msComm);
					}
					flag=initParams(conNum, data.toString(), ConstantValue.meterTypeSet);
					if(2==flag)
					{
						msg.setError1("表计类型关联失败，添加集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					if(0==flag)
					{
						msg.setError1("表计类型关联超时，添加集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Concentrator!toAdd.do\">添加集中器</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				}
				hod2000Concentrator.setTypeIndexs(meterType);
				//hod2000Concentrator.setConAddress(conAddress);
				hod2000Concentrator.setFreezeInterval(Integer.parseInt(freezeInterval));
				hod2000Concentrator.setFreezeIntervalValue(Integer.parseInt(freezeIntervalValue));
			}
			hod2000Concentrator.setConAddress(conAddress);
			hod2000Concentrator.setConAble(1);
			hod2000Concentrator.setConNumber(conNum);
			hod2000Concentrator.setClearDate(25);
			hod2000Concentrator.setConIsonline(1);
			hod2000Concentrator.setConFlashStatus(0);
			hod2000Concentrator.setConCom1Status(0);
			hod2000Concentrator.setConCom2Status(0);
			hod2000Concentrator.setConCom3Status(0);
			hod2000Concentrator.setConStrong(0);
			hod2000ConcentratorService.save(hod2000Concentrator);
			msg.setMsg1("添加集中器信息成功!");
			OperatorLog.addOperatorLog(conNum+"集中器的添加");
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doSave",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Concentrator!toAdd.do\">继续添加</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	//初始化参数通用方法
	public int initParams(String conNum, String data,
			String identificationId) {
		int flag=0;
		try {
			message=new MessageBase(ConstantValue.conType,Util.getAddr(conNum,ConstantValue.procuNum),1);
			cache=new Hod2000ParaDownInfoCache();
			cache.setConNum(conNum);
			cache.setProtocolType(identificationId);
			cache.setSendFlag(-1);
			cache.setState(0);
			//cache.setSubmitTime(new Date());
			cache.setSubmitTime(NetworkTimeUtil.getDate());
			cache.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(identificationId,data,ConstantValue.SEQ))));
			cacheService.save(cache);
			log.info("报文："+cache.getSendContent());
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
				if(1==cache.getState())//成功
				{
					flag=1;
					break;
				}
				if(2==cache.getState())//失败
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
					if(0==cache.getState())//超时
					{
						flag=0;
					}
					break;
				}
			}
			cacheService.delete(cache);
		} catch (Exception e) {
			flag=2;
			e.printStackTrace();
		}
		return flag;
	}
	
	//从机下发参数
	public int initParams(String masterConNum,String conNum, String data,
			String identificationId) {
		int flag=0;
		try {
			message=new MessageBase(ConstantValue.conType,Util.getAddr(conNum,ConstantValue.procuNum),1);
			cache=new Hod2000ParaDownInfoCache();
			cache.setConNum(masterConNum);
			cache.setProtocolType(identificationId);
			cache.setSendFlag(-1);
			cache.setState(0);
			//cache.setSubmitTime(new Date());
			cache.setSubmitTime(NetworkTimeUtil.getDate());
			cache.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(identificationId,data,ConstantValue.SEQ))));
			cacheService.save(cache);
			log.info("报文："+cache.getSendContent());
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
				if(1==cache.getState())//成功
				{
					flag=1;
					break;
				}
				if(2==cache.getState())//失败
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
					if(0==cache.getState())//超时
					{
						flag=0;
					}
					break;
				}
			}
			cacheService.delete(cache);
		} catch (Exception e) {
			flag=2;
			e.printStackTrace();
		}
		return flag;
	}
    
	/**
	 * 转到修改页面
	 * @return
	 */
	public String toUpdate() {
		try {
			request = ServletActionContext.getRequest();
			String id=request.getParameter("id");
			hod2000Concentrator = (Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(id));
			request.setAttribute("freeRead", hod2000SysparameterService.findByType(8));
			// 表计类型未关联
			dataList=hod2000MeterTypeService.findByHQL("from Hod2000MeterType where typeIndex not in ("+hod2000Concentrator.getTypeIndexs()+")");
			request.setAttribute("meterType", dataList);
			//表计类型已关联
			dataList=hod2000MeterTypeService.findByHQL("from Hod2000MeterType where typeIndex in ("+hod2000Concentrator.getTypeIndexs()+")");
			request.setAttribute("meterType2", dataList);
			return "toUpdate";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->toUpdate",e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/show/concentratorlist.jsp\">返回列表</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}  
	
	//转到更换集中器页面
	public String toChange()
	{
		try {
			request = ServletActionContext.getRequest();
			String id=request.getParameter("id");
			hod2000Concentrator = (Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(id));
			return "toChange";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->toChange",e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/show/concentratorlist.jsp\">返回列表</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	//更换集中器
	public String doChange()
	{
		try {
			request = ServletActionContext.getRequest();
			OperatorLog.addOperatorLog("集中器更换");
			int flag=0;
			String conId=request.getParameter("conId");
			String conNumber=request.getParameter("conNumber");
			hod2000Concentrator = (Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(conId));
			//集中器编号是否重复
			dataList=hod2000ConcentratorService.findByConNum2(conNumber);
			if(dataList.size()>0)
			{
				msg.setError1("集中器编号不允许重复，请重新输入!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			String masterConNum="";
			//初始化参数查询
			List list=deviceInitParameterService.findByCriteria(DetachedCriteria.forClass(Hod2000DeviceInitParameter.class));
			if(list.size()>0&&hod2000Concentrator.getConAddress()!=null&&!"".equals(hod2000Concentrator.getConAddress()))
			{
				deviceInitParameter=(Hod2000DeviceInitParameter) list.get(0);
				if(hod2000Concentrator.getMasterConId()!=null&&!"".equals(hod2000Concentrator.getMasterConId()))//如果是从机
				{	
					//查询其主机编号
					masterConNum=((Hod2000Concentrator)hod2000ConcentratorService.findById(hod2000Concentrator.getMasterConId())).getConNumber();
				}
				else//如果是主机
				{
					masterConNum=conNumber;
				}
				//终端上行通信口通信参数设置
				flag=initParams(masterConNum,conNumber,Util.decimalToHexadecimal(deviceInitParameter.getPcol1(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol2(),2)+Util.toByte(deviceInitParameter.getPcol3(), deviceInitParameter.getPcol4())+Util.decimalToHexadecimal(deviceInitParameter.getPcol5(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol6(),2),ConstantValue.communication);
				if(2==flag)
				{
					msg.setError1("终端上行通信口通信参数设置失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("终端上行通信口通信参数设置超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//主站IP地址与端口
				String ip=deviceInitParameter.getPcol7().split(":")[0];
				String port=deviceInitParameter.getPcol7().split(":")[1];
				String ip2=deviceInitParameter.getPcol8().split(":")[0];
				String port2=deviceInitParameter.getPcol8().split(":")[1];
				flag=initParams(masterConNum,conNumber,Util.getIpStr(ip.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port), 4)+Util.getIpStr(ip2.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port2), 4)+Util.getAPN(deviceInitParameter.getPcol9(),32),ConstantValue.IP);
				if(2==flag)
				{
					msg.setError1("主站IP地址与端口设置失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("主站IP地址与端口设置超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//冻结间隔 抄表间隔协议删除了 用00表示
				flag=initParams(masterConNum,conNumber,"00"+Util.freezeInterval(hod2000Concentrator.getFreezeInterval().toString(), hod2000Concentrator.getFreezeIntervalValue().toString()),ConstantValue.free);
				if(2==flag)
				{
					msg.setError1("冻结间隔设置失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("冻结间隔设置超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//地理位置
				flag=initParams(masterConNum,conNumber, Util.getConAddress(hod2000Concentrator.getConAddress()), ConstantValue.address);
				if(2==flag)
				{
					msg.setError1("地理位置设置失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("地理位置设置超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//表计类型关联
				if(hod2000Concentrator.getTypeIndexs()!=null&&!"".equals(hod2000Concentrator.getTypeIndexs()))
				{
					Hod2000MeterType metertype;
					StringBuffer data=new StringBuffer();
					String[] type=hod2000Concentrator.getTypeIndexs().split(",");
					data.append(Util.decimalToHexadecimal(type.length, 2));//本次配置表计信息索引个数
					for (int i = 0; i < type.length; i++) {
						metertype=(Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(type[i]));
						data.append(Util.decimalToHexadecimal(Integer.parseInt(type[i]), 2));//表计类型信息索引编号
						data.append(Util.getAddress(metertype.getCompanyNum()));//厂商编号
						data.append(metertype.getTypeCode());//表计类型编码
						data.append(Util.decimalToHexadecimal(metertype.getCommunicationProtocol(), 2));//通信规约
						data.append(ConstantValue.msComm);
					}
					flag=initParams(masterConNum,conNumber, data.toString(), ConstantValue.meterTypeSet);
					if(2==flag)
					{
						msg.setError1("表计类型关联失败，修改集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					if(0==flag)
					{
						msg.setError1("表计类型关联超时，修改集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				}
				//结算日
				if(hod2000Concentrator.getClearDate()!=null&&!"".equals(hod2000Concentrator.getClearDate()))
				{
					flag=initParams(masterConNum,conNumber,Util.decimalToHexadecimal(hod2000Concentrator.getClearDate(),2), ConstantValue.readDate);
					if(2==flag)
					{
						msg.setError1("设置结算日失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					if(0==flag)
					{
						msg.setError1("请求超时!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				}
				//rf信道
				if(hod2000Concentrator.getConRfValue()!=null&&!"".equals(hod2000Concentrator.getConRfValue()))
				{
					flag=initParams(masterConNum,conNumber, Util.decimalToHexadecimal(hod2000Concentrator.getConRfValue(), 2), ConstantValue.rf);
					if(2==flag)
					{
						msg.setError1("RF信道设置失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					if(0==flag)
					{
						msg.setError1("RF信道设置超时!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				}
				//终端热能表配置参数下发
				List<Hod2000Meter> meters=hod2000MeterService.findByHQL("from Hod2000Meter where meterAble=1 and hod2000Concentrator.conId="+hod2000Concentrator.getConId());
				if(meters.size()>0)
				{
					StringBuffer data=new StringBuffer();
					data.append(Util.decimalToHexadecimal(meters.size(),4));
					for (Hod2000Meter hod2000Meter : meters) {
						data.append(Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4));
						data.append(Util.getAddress(hod2000Meter.getMeterName()));
						data.append(Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2));
						data.append(Util.toByte(hod2000Meter.getMeterBaudrate()));
					}
					flag=initParams(masterConNum,conNumber,data.toString(), ConstantValue.meter);
					if(2==flag)
					{
						msg.setError1("终端热能表配置参数设置失败，更换集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
					else if(0==flag)
					{
						msg.setError1("终端热能表配置参数设置超时，更换集中器失败!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				}
				Hod2000Concentrator con=new Hod2000Concentrator();
				con.setConNumber(conNumber);
				con.setConAble(1);
				con.setConAddress(hod2000Concentrator.getConAddress());
				con.setConPositionName(hod2000Concentrator.getConPositionName());
				con.setFreezeInterval(hod2000Concentrator.getFreezeInterval());
				con.setFreezeIntervalValue(hod2000Concentrator.getFreezeIntervalValue());
				con.setConCom1Status(hod2000Concentrator.getConCom1Status());
				con.setConCom2Status(hod2000Concentrator.getConCom2Status());
				con.setConCom3Status(hod2000Concentrator.getConCom3Status());
				con.setConIsonline(hod2000Concentrator.getConIsonline());
				con.setConFlashStatus(hod2000Concentrator.getConFlashStatus());
				con.setTypeIndexs(hod2000Concentrator.getTypeIndexs());
				con.setClearDate(hod2000Concentrator.getClearDate());
				con.setConCapacityInfoCode(hod2000Concentrator.getConCapacityInfoCode());
				con.setConCompanyNum(hod2000Concentrator.getConCompanyNum());
				con.setConDeviceNum(hod2000Concentrator.getConDeviceNum());
				con.setConHardDate(hod2000Concentrator.getConHardDate());
				con.setConHardVersion(hod2000Concentrator.getConHardVersion());
				con.setConLastPosition(hod2000Concentrator.getConLastPosition());
				con.setConProtocolVersion(hod2000Concentrator.getConProtocolVersion());
				con.setConRfValue(hod2000Concentrator.getConRfValue());
				con.setConSoftDate(hod2000Concentrator.getConSoftDate());
				con.setConSoftVersion(hod2000Concentrator.getConSoftVersion());
				con.setMasterConId(null);//用户手动重新设置从机
				hod2000ConcentratorService.save(con);
				//更改表计中的集中器编号
				if(meters.size()>0)
				{
//					for (Hod2000Meter hod2000Meter : meters) {
//						hod2000Meter.setHod2000Concentrator(con);
//						hod2000MeterService.update(hod2000Meter);
//					}
					hod2000MeterService.executeUpdate("UPDATE Hod2000Meter set hod2000Concentrator.conId="+con.getConId()+" where hod2000Concentrator.conId="+hod2000Concentrator.getConId());
				}
				//更改旧集中器信息
				hod2000Concentrator.setConAble(0);
				hod2000Concentrator.setMasterConId(null);
				hod2000ConcentratorService.update(hod2000Concentrator);
				msg.setMsg1("集中器更换成功!");
			}
			else
			{
				msg.setError1("请先设置集中器的初始化参数和地理位置等信息!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/concentratorlist.jsp\">返回列表</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doChange",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/concentratorlist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	/**
	 * 查询集中器的主机编号，转到集中器匹配设备界面
	 * @return
	 */
	public String toMapping()
	{
		try {
			request = ServletActionContext.getRequest();
			String slaveConId="";//改主机集中器中所有从机集中器id字符串，以逗号隔开
			String id=request.getParameter("id");//主机id
			String conNum=request.getParameter("conNum");//主机编号
			//查询masterConId为id的集中器编号，以","隔开
			if(id!=null&&!"".equals(id))
			{
				DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Concentrator.class);
				dc.add(Restrictions.eq("masterConId", Integer.parseInt(id)));
				dataList=hod2000ConcentratorService.findByCriteria(dc);
				for (int i = 0; i < dataList.size(); i++) {
					hod2000Concentrator=(Hod2000Concentrator) dataList.get(i);
					slaveConId+=hod2000Concentrator.getConId()+",";
				}
				if(slaveConId.length()>0)
				{
					slaveConId=slaveConId.substring(0, slaveConId.lastIndexOf(","));
				}
				request.setAttribute("slaveConId", slaveConId);
			}
			else
			{
				msg.setError1("ID无效!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			request.setAttribute("conId", id);//主机id
			hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(id));
			request.setAttribute("rf",hod2000Concentrator.getConRfValue());
			request.setAttribute("conNum", conNum);//主机编号
			return "toMapping";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->toMapping",e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/show/concentratorlist.jsp\">返回列表</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * 集中器设备匹配
	 * @return
	 */
	public String doMapping()
	{
		try {
			request = ServletActionContext.getRequest();
			String id=request.getParameter("conId");//主机id
			String slaveConNums=request.getParameter("slaveConNums");//从机编号，是以逗号隔开的
			String rf=request.getParameter("rf");//RF信道
			String oldSlaveConId=request.getParameter("oldSlaveConId");//以前的从机，未变更之前的
			hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(id));//主机对象
			String conNum=hod2000Concentrator.getConNumber();//主机编号
			Hod2000Concentrator concentrator=null;//从机对象
			StringBuffer data=new StringBuffer();//数据域
			int flag=0;
			if(2==hod2000Concentrator.getConIsonline())
			{
				msg.setError1(conNum+"集中器不在线!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/concentratorlist.jsp\">返回列表</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			if(slaveConNums!=null&&!"".equals(slaveConNums))
			{
				if(slaveConNums.contains(conNum))
				{
					msg.setError1("不能添加自身为从机!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				String[] slaveConNum=slaveConNums.split(",");
				data.append(Util.decimalToHexadecimal(slaveConNum.length, 2));
				for (int i = 0; i < slaveConNum.length; i++) {
					data.append(Util.getAddress(slaveConNum[i],ConstantValue.procuNum));
				}
				flag=initParams(conNum,data.toString(), ConstantValue.mapping);
				if(2==flag)
				{
					msg.setError1("集中器设备匹配失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("集中器设备匹配超时!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//将以前的从机进行清空
				if(oldSlaveConId.length()>0)
				{
					String[] oldSlaveConIds=oldSlaveConId.split(",");
					for (int i = 0; i <oldSlaveConIds.length; i++) {
						concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(oldSlaveConIds[i]));
						concentrator.setMasterConId(null);//清空从机
						hod2000ConcentratorService.update(concentrator);
					}
				}
				//添加新的从机
				for (int i = 0; i < slaveConNum.length; i++) {
					//先判断集中器编号是否已经存在
					concentrator=hod2000ConcentratorService.findByConNum(slaveConNum[i]);
					if(concentrator==null)
					{
						concentrator=new Hod2000Concentrator();
						concentrator.setConIsonline(1);
						concentrator.setConCom1Status(0);
						concentrator.setConCom2Status(0);
						concentrator.setConCom3Status(0);
						concentrator.setConFlashStatus(0);
						concentrator.setConStrong(0);
						concentrator.setTypeIndexs(hod2000Concentrator.getTypeIndexs());
						concentrator.setConNumber(slaveConNum[i]);
						concentrator.setMasterConId(Integer.parseInt(id));
						concentrator.setClearDate(25);//默认结算日为25
						concentrator.setConAble(1);
						hod2000ConcentratorService.save(concentrator);
					}
					else
					{
						concentrator.setMasterConId(Integer.parseInt(id));
						hod2000ConcentratorService.update(concentrator);
					}
				}
				msg.setMsg1("集中器设备匹配成功!");
			}
			//清空从机
			else
			{
				data.append(Util.decimalToHexadecimal(0, 2));
				flag=initParams(conNum, data.toString(), ConstantValue.mapping);
				if(2==flag)
				{
					msg.setError1("集中器设备匹配失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("集中器设备匹配超时!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				//将主机为id的从机的主机id字段清空
				DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Concentrator.class);
				dc.add(Restrictions.eq("masterConId", Integer.parseInt(id)));
				dataList=hod2000ConcentratorService.findByCriteria(dc);
				for (int i = 0; i < dataList.size(); i++) {
					concentrator=(Hod2000Concentrator) dataList.get(i);
					concentrator.setMasterConId(null);
					hod2000ConcentratorService.update(concentrator);
				}
				msg.setMsg1("集中器设备匹配成功!");
			}
			//主机RF信道设置
			if(rf!=null&&!"".equals(rf))
			{
				flag=initParams(conNum, Util.decimalToHexadecimal(Integer.parseInt(rf), 2), ConstantValue.rf);
				if(2==flag)
				{
					msg.setError1("<br>RF信道设置失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("<br>RF信道设置超时!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Concentrator.setConRfValue(Integer.parseInt(rf));
				hod2000ConcentratorService.update(hod2000Concentrator);
			}
			OperatorLog.addOperatorLog(conNum+"集中器设备匹配");
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doMapping",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/concentratorlist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	/**
	 * 读取集中器版本信息
	 */
	public void readVersion()
	{
		try {
			request = ServletActionContext.getRequest();
			StringBuffer versionInfo=new StringBuffer();
			out=ServletActionContext.getResponse().getWriter();
			String conId=request.getParameter("conId");
			String masterConNum="";
			if(conId!=null&&!"".equals(conId))
			{
				hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(conId));
				if(2==hod2000Concentrator.getConIsonline())
				{
					out.write("{success:false,msg:'集中器不在线!'}");
					return;
				}
				if(hod2000Concentrator.getMasterConId()!=null&&!"".equals(hod2000Concentrator.getMasterConId()))//如果是从机
				{	
					masterConNum=((Hod2000Concentrator)hod2000ConcentratorService.findById(hod2000Concentrator.getMasterConId())).getConNumber();
				}
				else//如果是主机
				{
					masterConNum=hod2000Concentrator.getConNumber();
				}
				hod2000Concentrator.setConCompanyNum(null);
				hod2000Concentrator.setConDeviceNum(null);
				hod2000Concentrator.setConSoftVersion(null);
				hod2000Concentrator.setConSoftDate(null);
				hod2000Concentrator.setConCapacityInfoCode(null);
				hod2000Concentrator.setConProtocolVersion(null);
				hod2000Concentrator.setConHardVersion(null);
				hod2000Concentrator.setConHardDate(null);
				hod2000ConcentratorService.update(hod2000Concentrator);
				message=new MessageBase(ConstantValue.conType,Util.getAddr(hod2000Concentrator.getConNumber(),ConstantValue.procuNum),1);
				cache=new Hod2000ParaDownInfoCache();
				cache.setConNum(masterConNum);
				cache.setProtocolType(ConstantValue.version);
				cache.setSendFlag(-1);
				cache.setState(0);
				//cache.setSubmitTime(new Date());
				cache.setSubmitTime(NetworkTimeUtil.getDate());
				cache.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(ConstantValue.version,"",ConstantValue.SEQ))));
				cacheService.save(cache);
				log.info("读取集中器版本信息："+cache.getSendContent());
				long start_time = System.currentTimeMillis();
				while (true) {
					long end_time = System.currentTimeMillis();
					cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
					if(1==cache.getState())//成功
					{
						hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(conId));
						if(hod2000Concentrator.getConCompanyNum()!=null&&!"".equals(hod2000Concentrator.getConCompanyNum()))
							versionInfo.append("厂商代号:"+hod2000Concentrator.getConCompanyNum()+"<br>");
						if(hod2000Concentrator.getConDeviceNum()!=null&&!"".equals(hod2000Concentrator.getConDeviceNum()))
							versionInfo.append("设备编号:"+hod2000Concentrator.getConDeviceNum()+"<br>");
						if(hod2000Concentrator.getConSoftVersion()!=null&&!"".equals(hod2000Concentrator.getConSoftVersion()))
							versionInfo.append("终端软件版本号:"+hod2000Concentrator.getConSoftVersion()+"<br>");
						if(hod2000Concentrator.getConSoftDate()!=null&&!"".equals(hod2000Concentrator.getConSoftDate()))
							versionInfo.append("终端软件发布日期(年月日):"+hod2000Concentrator.getConSoftDate()+"<br>");
						if(hod2000Concentrator.getConCapacityInfoCode()!=null&&!"".equals(hod2000Concentrator.getConCapacityInfoCode()))
							versionInfo.append("终端配置容量信息码:"+hod2000Concentrator.getConCapacityInfoCode()+"<br>");
						if(hod2000Concentrator.getConProtocolVersion()!=null&&!"".equals(hod2000Concentrator.getConProtocolVersion()))
							versionInfo.append("终端通信协议.版本号:"+hod2000Concentrator.getConProtocolVersion()+"<br>");
						if(hod2000Concentrator.getConHardVersion()!=null&&!"".equals(hod2000Concentrator.getConHardVersion()))
							versionInfo.append("终端硬件版本号:"+hod2000Concentrator.getConHardVersion()+"<br>");
						if(hod2000Concentrator.getConHardDate()!=null&&!"".equals(hod2000Concentrator.getConHardDate()))
							versionInfo.append("终端硬件发布日期(年月日):"+hod2000Concentrator.getConHardDate());
						out.write("{success:true,msg:'读取集中器版本信息成功!',data:'"+versionInfo.toString()+"'}");
						OperatorLog.addOperatorLog("读取集中器版本信息");
						break;
					}
					if(2==cache.getState())//失败
					{
						out.write("{success:false,msg:'读取集中器版本信息失败!'}");
						break;
					}
					if (end_time - start_time >= ConstantValue.timeout) { 
						cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
						if(0==cache.getState())//超时
						{
							out.write("{success:false,msg:'请求超时!'}");
						}
						break;
					}
				}
				cacheService.delete(cache);
			}
			else
			{
				out.write("{success:false,msg:'ID无效!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->readVersion:",e);
			out.write("{success:false}");
		}
	}

	/**
	 * 集中器的修改
	 * @return
	 */
	public String doUpdate() {
		try {
			int flag=1;
			request = ServletActionContext.getRequest();
			String conId=request.getParameter("conId");
			String freezeInterval=request.getParameter("freezeInterval");
			String conPositionName=request.getParameter("conPositionName");
			String freezeIntervalValue=request.getParameter("freezeIntervalValue");
			String conAddress=request.getParameter("conAddress");
			String meterType=request.getParameter("meterType");
			hod2000Concentrator=(Hod2000Concentrator) hod2000ConcentratorService.findById(Integer.parseInt(conId));
			if(2==hod2000Concentrator.getConIsonline())
			{
				msg.setError1(hod2000Concentrator.getConNumber()+"集中器不在线!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000DeviceInitParameter.class);
			dataList=deviceInitParameterService.findByCriteria(dc);
			String masterConNum="";//从机的主机编号
			if(hod2000Concentrator.getMasterConId()!=null&&!"".equals(hod2000Concentrator.getMasterConId()))//从机
			{
				masterConNum=((Hod2000Concentrator)hod2000ConcentratorService.findById(hod2000Concentrator.getMasterConId())).getConNumber();
				//如果第一次编辑从机（第一次编辑时，只能判断安装位置了）
				if(hod2000Concentrator.getConAddress()==null||"".equals(hod2000Concentrator.getConAddress()))
				{
					if(dataList.size()>0)
					{
						deviceInitParameter=(Hod2000DeviceInitParameter) dataList.get(0);
						//终端上行通信口通信参数设置
						flag=initParams(masterConNum,hod2000Concentrator.getConNumber(),Util.decimalToHexadecimal(deviceInitParameter.getPcol1(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol2(),2)+Util.toByte(deviceInitParameter.getPcol3(), deviceInitParameter.getPcol4())+Util.decimalToHexadecimal(deviceInitParameter.getPcol5(),2)+Util.decimalToHexadecimal(deviceInitParameter.getPcol6(),2),ConstantValue.communication);
						if(2==flag)
						{
							msg.setError1("终端上行通信口通信参数设置失败，修改集中器失败!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						if(0==flag)
						{
							msg.setError1("终端上行通信口通信参数设置超时，修改集中器失败!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						//主站IP地址与端口
						String ip=deviceInitParameter.getPcol7().split(":")[0];
						String port=deviceInitParameter.getPcol7().split(":")[1];
						String ip2=deviceInitParameter.getPcol8().split(":")[0];
						String port2=deviceInitParameter.getPcol8().split(":")[1];
						flag=initParams(masterConNum,hod2000Concentrator.getConNumber(),Util.getIpStr(ip.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port), 4)+Util.getIpStr(ip2.split("\\."))+Util.decimalToHexadecimal(Integer.parseInt(port2), 4)+Util.getAPN(deviceInitParameter.getPcol9(),32),ConstantValue.IP);
						if(2==flag)
						{
							msg.setError1("主站IP地址与端口设置失败，修改集中器失败!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
						if(0==flag)
						{
							msg.setError1("主站IP地址与端口设置超时，修改集中器失败!");
							msg.setLink(0, "<a href=\"" + request.getContextPath()
									+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
							msg.setJump(false);
							request.setAttribute("message", msg);
							return "message";
						}
					}
					else
					{
						msg.setError1("请先设置集中器的初始化参数!");
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/show/concentratorlist.jsp\">返回列表</a>");
						request.setAttribute("message", msg);
						return "message";
					}
				}
			}
			else//主机
			{
				masterConNum=hod2000Concentrator.getConNumber();
			}
			//冻结间隔 抄表间隔协议删除了 用00表示
			if(hod2000Concentrator.getFreezeInterval()==null||Integer.parseInt(freezeInterval)!=hod2000Concentrator.getFreezeInterval())
			{
				flag=initParams(masterConNum,hod2000Concentrator.getConNumber(),"00"+Util.freezeInterval(freezeInterval, freezeIntervalValue),ConstantValue.free);
				if(2==flag)
				{
					msg.setError1("冻结间隔设置失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("冻结间隔设置超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Concentrator.setFreezeInterval(Integer.parseInt(freezeInterval));
				hod2000Concentrator.setFreezeIntervalValue(Integer.parseInt(freezeIntervalValue));
			}
			//地理位置
			if(!conAddress.equals(hod2000Concentrator.getConAddress()))
			{
				flag=initParams(masterConNum,hod2000Concentrator.getConNumber(), Util.getConAddress(conAddress), ConstantValue.address);
				if(2==flag)
				{
					msg.setError1("地理位置设置失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("地理位置设置超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Concentrator.setConAddress(conAddress);
				hod2000Concentrator.setConPositionName(conPositionName);
			}
			//表计类型关联
			if(meterType!=null&&!"".equals(meterType))
			{
				Hod2000MeterType metertype;
				StringBuffer data=new StringBuffer();
				String[] type=meterType.split(",");
				data.append(Util.decimalToHexadecimal(type.length, 2));//本次配置表计信息索引个数
				for (int i = 0; i < type.length; i++) {
					metertype=(Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(type[i]));
					data.append(Util.decimalToHexadecimal(Integer.parseInt(type[i]), 2));//表计类型信息索引编号
					data.append(Util.getAddress(metertype.getCompanyNum()));//厂商编号
					data.append(metertype.getTypeCode());//表计类型编码
					data.append(Util.decimalToHexadecimal(metertype.getCommunicationProtocol(), 2));//通信规约
					data.append(ConstantValue.msComm);
				}
				flag=initParams(masterConNum,hod2000Concentrator.getConNumber(), data.toString(), ConstantValue.meterTypeSet);
				if(2==flag)
				{
					msg.setError1("表计类型关联失败，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				if(0==flag)
				{
					msg.setError1("表计类型关联超时，修改集中器失败!");
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/show/concentratorlist.jsp\">返回集中器列表</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Concentrator.setTypeIndexs(meterType);
			}
			hod2000ConcentratorService.update(hod2000Concentrator);
			msg.setMsg1("修改集中器信息成功!");
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doUpdate",e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/show/concentratorlist.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	/**
	 * 集中器的查询
	 * @return
	 */
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("集中器信息查询");
			request=ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Concentrator.class);
			String conNumber=request.getParameter("conNumber");
			String code=request.getParameter("code");
			if(conNumber!=null&&!conNumber.equals(""))
			{
				dc.add(Restrictions.like("conNumber", conNumber+"%"));
			}
			if(code!=null&&!code.equals(""))
			{
				dc.add(Restrictions.like("conAddress", code+"%"));
			}
			//dataList = Page.util(request, hod2000ConcentratorService,dc);
			dataList=hod2000ConcentratorService.findByCriteria(page, pageSize, dc);
			Map map;
			List list=new ArrayList();
			for (int i = 0; i < dataList.size(); i++) {
				hod2000Concentrator=(Hod2000Concentrator) dataList.get(i);
				map=new HashMap();
				map.put("id", hod2000Concentrator.getConId());
				map.put("conNumber", hod2000Concentrator.getConNumber());
				map.put("conPositionName",hod2000Concentrator.getConPositionName());
				map.put("freezeInterval", hod2000Concentrator.getFreezeInterval());
				map.put("freezeIntervalValue", hod2000Concentrator.getFreezeIntervalValue());
				map.put("clearDate", hod2000Concentrator.getClearDate());
				map.put("isOnline", hod2000Concentrator.getConIsonline());
				map.put("masterConId", hod2000Concentrator.getMasterConId());
				map.put("conAble", hod2000Concentrator.getConAble());
				list.add(map);
			}
			int totalRecord =(Integer)hod2000ConcentratorService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000MeterInfoHistoryAction->doSelectMeterWarm:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doSelect",e);
		}
	}  
	
	/**
	 * 集中器列表打印
	 * @return
	 */
	public String print() {
		try {
			OperatorLog.addOperatorLog("集中器信息打印预览");
			request = ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Concentrator.class);
			String conNumber=request.getParameter("conNumber");
			String code=request.getParameter("code");
			if(conNumber!=null&&!conNumber.equals(""))
			{
				dc.add(Restrictions.like("conNumber", conNumber+"%"));
			}
			if(code!=null&&!code.equals(""))
			{
				dc.add(Restrictions.like("conAddress", code+"%"));
			}
			dataList=hod2000ConcentratorService.findByCriteria(dc);
			return "print";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doSelect",e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * 所属集中器的弹出框
	 * @return
	 */
	public String doSelect2() {
		try {
			request = ServletActionContext.getRequest();
			String conNumber=request.getParameter("conNumber");
			String sql="SELECT A.con_id,A.con_number,A.con_position_name FROM hod2000_concentrator A WHERE A.master_con_id is null and NOT exists (select * from hod2000_concentrator B where A.con_id=B.master_con_id)";
			String countSql="SELECT count(*) FROM hod2000_concentrator A WHERE A.master_con_id is null and NOT exists (select * from hod2000_concentrator B where A.con_id=B.master_con_id)";
			if(conNumber!=null&&!"".equals(conNumber))
			{
				sql+=" and con_number like '"+conNumber+"%'";
				countSql+=" and con_number like '"+conNumber+"%'";
			}
			List list=Page.findBySql(request, hod2000ConcentratorService, sql, countSql);
			Map map;
			Object[] objects;
			dataList=new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				map=new HashMap();
				objects=(Object[]) list.get(i);
				map.put("conId", objects[0]);
				map.put("conNumber", objects[1]);
				map.put("conPositionName", objects[2]);
				dataList.add(map);
			}
			request.setAttribute("conNumber", conNumber);
			return "show";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doSelect2",e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}    
    
	/**
	 * 实时抄读树
	 */
	public void doTree()
	{
		try {
			request= ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			List list;
			Map map;
			Object[] object;
			List list2=new ArrayList();
			String checkBox=request.getParameter("checkBox");//判断是否显示复选框
			String typeString=request.getParameter("type");
			String codeString=request.getParameter("code");
			String idString=request.getParameter("id");
			int id=0;
			if(typeString==null||typeString.equals(""))
			{
				typeString="0";
			}
			if(idString!=null&&!"".equals(idString)&&!"undefined".equals(idString)&&!"-1".equals(idString))
			{
				id=Integer.parseInt(idString);
			}
			String addressCode="";
			List params=hod2000SysparameterService.findByType(10);
			if(params.size()>0)
			{
				addressCode=((Hod2000Sysparameter)params.get(0)).getPvalue();
				//默认地址为省级
				if(addressCode.length()==2)
				{
					if(typeString.equals("0"))
					{
						list=hod2000RegionService.findByCode(addressCode);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 1);
								map.put("expanded", true);
								map.put("code", addressCode);
								list2.add(map);
							}
						}
					}
					if(typeString.equals("1"))
					{
						list=hod2000CityService.findByRegionId(id);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 2);
								map.put("expanded", true);
								map.put("code", codeString+object[2]);
								list2.add(map);
							}
						}
					}
					if(typeString.equals("2"))
					{
						list=hod2000CountyService.findByCityId(id);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 3);
								map.put("expanded", false);
								map.put("code", codeString+object[2]);
								list2.add(map);
							}
						}
					}
					if(typeString.equals("3"))
					{
						list=hod2000VillageService.findByCountyId(id);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 4);
								map.put("expanded", false);
								map.put("code", codeString+object[2]);
								list2.add(map);
							}
						}
					}
				}
				//默认地址为市级
				if(addressCode.length()==4)
				{
					if(typeString.equals("0"))
					{
						list=hod2000CityService.findByCityCode(addressCode);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 2);
								map.put("expanded", true);
								map.put("code", addressCode);
								list2.add(map);
							}
						}
					}
					if(typeString.equals("2"))
					{
						list=hod2000CountyService.findByCityId(id);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 3);
								map.put("expanded", false);
								map.put("code", codeString+object[2]);
								list2.add(map);
							}
						}
					}
					if(typeString.equals("3"))
					{
						list=hod2000VillageService.findByCountyId(id);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 4);
								map.put("expanded", false);
								map.put("code", codeString+object[2]);
								list2.add(map);
							}
						}
					}
				}
				//默认地址为区级
				if(addressCode.length()==6)
				{
					if(typeString.equals("0"))
					{
						list=hod2000CountyService.findByCountyCode(addressCode);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 3);
								map.put("expanded", false);
								map.put("code", addressCode);
								list2.add(map);
							}
						}
					}
					if(typeString.equals("3"))
					{
						list=hod2000VillageService.findByCountyId(id);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 4);
								map.put("expanded", false);
								map.put("code", codeString+object[2]);
								list2.add(map);
							}
						}
					}
				}
				//默认地址为街道办级
				if(addressCode.length()==9)
				{
					if(typeString.equals("0"))
					{
						list=hod2000VillageService.findByVillageCode(addressCode);
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								map=new HashMap();
								object=(Object[]) list.get(i);
								map.put("text", object[1]);
								map.put("leaf", false);
								map.put("qtip", object[0]);
								map.put("type", 4);
								map.put("expanded", true);
								map.put("code", addressCode);
								list2.add(map);
							}
						}
					}
				}
				if(typeString.equals("4"))
				{
					list=hod2000CommunityService.findByVillageId(id);
					if (list.size()>0) {
						for (int i = 0; i < list.size(); i++) {
							map=new HashMap();
							object=(Object[]) list.get(i);
							map.put("text", object[1]);
							map.put("leaf", false);
							map.put("qtip", object[0]);
							map.put("type", 5);
							map.put("expanded", true);
							map.put("code", codeString+object[2]);
							list2.add(map);
						}
					}
				}
				if(typeString.equals("5"))
				{
					//根据地理位置编码查询小区下面的集中器信息
					list=hod2000ConcentratorService.findByAddress(codeString);
					if (list.size()>0) {
						for (int i = 0; i < list.size(); i++) {
							map=new HashMap();
							object=(Object[]) list.get(i);
							map.put("text", object[1]+"集中器");
							map.put("leaf", false);
							map.put("qtip", object[0]);
							map.put("type", 8);
							map.put("expanded", false);
							if(checkBox.equals("true"))
							{
								map.put("checked", false);
							}
							map.put("code", object[2]);
							list2.add(map);
						}
					}
				}
				if(typeString.equals("8"))
				{
					//根据集中器id查询表计信息
					list=hod2000MeterService.findByConId(id);
					if (list.size()>0) {
						for (int i = 0; i < list.size(); i++) {
							map=new HashMap();
							object=(Object[]) list.get(i);
							map.put("text", object[1]);
							map.put("leaf", true);
							map.put("qtip", object[0]);
							map.put("type", 9);
							if(checkBox.equals("true"))
							{
								map.put("checked", false);
							}
							map.put("expanded", false);
							map.put("code", object[2]);
							list2.add(map);
						}
					}
				}
			}
			out.write(JSONArray.fromObject(list2).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public void addDataToJsonArray(JSONArray array,List list){
		JSONObject data = new JSONObject();
		Object[] building;
		for(int i=0;i<list.size();i++){
			building = (Object[])list.get(i);
			data.put("name", building[1]);
			data.put("id", building[0]);
			array.add(data);
		}
	}
	
	/**
	 * 集中器管理中的地图显示
	 * 右击楼栋节点，显示集中器信息
	 */
	public void getTerminalInfo()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("buildingId");
			if(buildingId!=null&&!buildingId.equals(""))
			{
				Hod2000Building building=(Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(buildingId));
				dataList=hod2000ConcentratorService.findByBuildingCode(building.getBuildingCode());
				List list=new ArrayList();
				Object[] objs;
				Map map;
				for (int i = 0; i <dataList.size(); i++) {
					map=new HashMap();
					objs=(Object[]) dataList.get(i);
					map.put("conNum", objs[0]);
					map.put("positionName", objs[1]);
					list.add(map);
				}
				OperatorLog.addOperatorLog("集中器管理地图查询集中器信息");
				out.write("{totalCount:"+String.valueOf(list.size())+",result:"+JSONArray.fromObject(list).toString()+"}");
			}
		} catch (Exception e) {
			out.write("{success:false}");
			log.error(e);
		}
	}
	
	/**
	 * 集中器报警信息，地图显示
	 */
	public void getEventList()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("buildingId");
			if(buildingId!=null&&!buildingId.equals(""))
			{
				Hod2000Building building=(Hod2000Building) hod2000BuildingService.findById(Integer.parseInt(buildingId));
				dataList=hod2000BuildingService.findConEventByCode(building.getBuildingCode());
				List list=new ArrayList();
				Object[] objs;
				Map map;
				for (int i = 0; i <dataList.size(); i++) {
					map=new HashMap();
					objs=(Object[]) dataList.get(i);
					map.put("conNumber", objs[0]);
					map.put("conAddress", objs[1]);
					map.put("conFlashStatus", objs[2]);
					map.put("isOnline", objs[3]);
					map.put("conCom1Status", objs[4]);
					map.put("conCom2Status", objs[5]);
					map.put("conCom3Status", objs[6]);
					list.add(map);
				}
				OperatorLog.addOperatorLog("地图集中器报警信息查询");
				out.write("{totalCount:"+String.valueOf(list.size())+",result:"+JSONArray.fromObject(list).toString()+"}");
			}
		} catch (Exception e) {
			out.write("{success:false}");
			log.error(e);
		}
	}
	
	//集中器状态
	public void doState()
	{
		try {
			OperatorLog.addOperatorLog("集中器状态查询");
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			String conNum=request.getParameter("conNum");
			String conIsonline=request.getParameter("conIsonline");//在线状态
			String conFlashStatus=request.getParameter("conFlashStatus");//内存状态
			String conAddress=request.getParameter("conAddress");//地理位置行政编码
			List list=new ArrayList();
			Object[] objects;
			Map map;
			String sql="select con_number,con_position_name,con_isonline,con_flash_status,con_com1_status,con_com2_status,con_com3_status,con_strong from hod2000_concentrator where con_able=1";
			String count="select count(*) from hod2000_concentrator where con_able=1";
			if(conNum!=null&&!conNum.equals(""))
			{
				sql+=" and con_number like '"+conNum+"%'";
				count+=" and con_number like '"+conNum+"%'";
			}
			if(conIsonline!=null&&!conIsonline.equals(""))
			{
				sql+=" and con_isonline="+Integer.parseInt(conIsonline);
				count+=" and con_isonline="+Integer.parseInt(conIsonline);
			}
			if(conFlashStatus!=null&&!conFlashStatus.equals(""))
			{
				sql+=" and con_flash_status="+Integer.parseInt(conFlashStatus);
				count+=" and con_flash_status="+Integer.parseInt(conFlashStatus);
			}
			if(conAddress!=null&&!conAddress.equals(""))
			{
				sql+=" and con_address like '"+conAddress+"%'";
				count+=" and con_address like '"+conAddress+"%'";
			}
			dataList=hod2000ConcentratorService.findByNHQL(page, pageSize, sql);
			for (int i = 0; i < dataList.size(); i++) {
				objects = (Object[]) dataList.get(i);
				map=new HashMap();
				map.put("conNum", objects[0]);
				map.put("conAddress", objects[1]);
				map.put("conIsonline", objects[2]);
				map.put("conFlashStatus", objects[3]);
				map.put("conCom1Status", objects[4]);
				map.put("conCom2Status", objects[5]);
				map.put("conCom3Status", objects[6]);
				map.put("conStrong", objects[7]);
				list.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000ConcentratorService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000ConcentratorAction-->doState:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->doState:",e);
		}
	}
	
	/**
	 * 集中器报警
	 */
	public void concentratorWarm()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			List list=new ArrayList();
			Object[] objects;
			Map map;
			String sql="select con_number,con_position_name,con_isonline,con_flash_status,con_com1_status,con_com2_status,con_com3_status,con_strong from hod2000_concentrator where con_able=1 and (con_flash_status=1 or con_isonline=2 or con_com1_status=1 or con_com2_status=1 or con_com3_status=1 or con_strong=0)";
			String count="select count(*) from hod2000_concentrator where con_able=1 and (con_flash_status=1 or con_isonline=2 or con_com1_status=1 or con_com2_status=1 or con_com3_status=1 or con_strong=0)";
			dataList=hod2000ConcentratorService.findByNHQL(page, pageSize, sql);
			for (int i = 0; i < dataList.size(); i++) {
				objects = (Object[]) dataList.get(i);
				map=new HashMap();
				map.put("conNum", objects[0]);
				map.put("conAddress", objects[1]);
				map.put("conIsonline", objects[2]);
				map.put("conFlashStatus", objects[3]);
				map.put("conCom1Status", objects[4]);
				map.put("conCom2Status", objects[5]);
				map.put("conCom3Status", objects[6]);
				map.put("conStrong", objects[7]);
				list.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000ConcentratorService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000ConcentratorAction->concentratorWarm:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->concentratorWarm:",e);
		}
	}
	
	/**
	 * 控制器报警信息打印
	 * @return
	 */
	public String concentratorWarmPrint()
	{
		try {
			OperatorLog.addOperatorLog("集中器报警信息打印预览");
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			List list=new ArrayList();
			Object[] objects;
			Map map;
			dataList=hod2000ConcentratorService.findWarmInfo();
			for (int i = 0; i < dataList.size(); i++) {
				objects = (Object[]) dataList.get(i);
				map=new HashMap();
				map.put("conNum", objects[0]);
				map.put("conAddress", objects[1]);
				map.put("conIsonline", objects[2]);
				map.put("conFlashStatus", objects[3]);
				map.put("conCom1Status", objects[4]);
				map.put("conCom2Status", objects[5]);
				map.put("conCom3Status", objects[6]);
				map.put("conStrong", objects[7]);
				list.add(map);
			}
			request.setAttribute("dataList", list);
			return "concentratorWarmPrint";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->concentratorWarmPrint:",e);
			msg.setError1(e.toString());
			request.setAttribute("message",msg);
			return "message";
		}
	}
	
	/**
	 * 集中器状态的打印
	 * @return
	 */
	public String statePrint()
	{
		try {
			OperatorLog.addOperatorLog("集中器状态信息打印预览");
			request = ServletActionContext.getRequest();
			String conNum=request.getParameter("conNum");
			String conIsonline=request.getParameter("conIsonline");//在线状态
			String conFlashStatus=request.getParameter("conFlashStatus");//FLASH状态
			String conAddress=request.getParameter("conAddress");//地理位置行政编码
			List list=new ArrayList();
			Object[] objects;
			Map map;
			dataList=hod2000ConcentratorService.findByParams(conNum,conIsonline,conFlashStatus,conAddress);
			for (int i = 0; i < dataList.size(); i++) {
				objects = (Object[]) dataList.get(i);
				map=new HashMap();
				map.put("conNum", objects[0]);
				map.put("conAddress", objects[1]);
				map.put("conIsonline", objects[2]);
				map.put("conFlashStatus", objects[3]);
				map.put("conCom1Status", objects[4]);
				map.put("conCom2Status", objects[5]);
				map.put("conCom3Status", objects[6]);
				map.put("conStrong", objects[7]);
				list.add(map);
			}
			request.setAttribute("conNum", conNum);
			request.setAttribute("conFlashStatus", conFlashStatus);
			request.setAttribute("conIsonline", conIsonline);
			request.setAttribute("conAddress", conAddress);
			request.setAttribute("list", list);
			return "statePrint";
		} catch (Exception e) {
			log.error("Hod2000ConcentratorAction-->statePrint:", e);
			msg.setError1(e.toString());
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
	
    
	public void setHod2000ConcentratorService(IHod2000ConcentratorService hod2000ConcentratorService) {
		this.hod2000ConcentratorService = hod2000ConcentratorService;
	}
 
	public Hod2000Concentrator getHod2000Concentrator() {
		return hod2000Concentrator;
	}

	public void setHod2000Concentrator(Hod2000Concentrator hod2000Concentrator) {
		this.hod2000Concentrator = hod2000Concentrator;
	}
    
	public IHod2000CityService getHod2000CityService() {
		return hod2000CityService;
	}

	public void setHod2000CityService(IHod2000CityService hod2000CityService) {
		this.hod2000CityService = hod2000CityService;
	}

	public IHod2000CountyService getHod2000CountyService() {
		return hod2000CountyService;
	}

	public void setHod2000CountyService(IHod2000CountyService hod2000CountyService) {
		this.hod2000CountyService = hod2000CountyService;
	}

	public IHod2000CommunityService getHod2000CommunityService() {
		return hod2000CommunityService;
	}

	public void setHod2000CommunityService(
			IHod2000CommunityService hod2000CommunityService) {
		this.hod2000CommunityService = hod2000CommunityService;
	}

	public IHod2000VillageService getHod2000VillageService() {
		return hod2000VillageService;
	}

	public void setHod2000VillageService(
			IHod2000VillageService hod2000VillageService) {
		this.hod2000VillageService = hod2000VillageService;
	}

	public IHod2000RegionService getHod2000RegionService() {
		return hod2000RegionService;
	}
	
	public IHod2000BuildingService getHod2000BuildingService() {
		return hod2000BuildingService;
	}
	
	public void setHod2000BuildingService(IHod2000BuildingService hod2000BuildingService){
		this.hod2000BuildingService = hod2000BuildingService;
	}
	public void setHod2000RegionService(IHod2000RegionService hod2000RegionService) {
		this.hod2000RegionService = hod2000RegionService;
	}

	public IHod2000ConcentratorService getHod2000ConcentratorService() {
		return hod2000ConcentratorService;
	}

	public IHod2000MeterService getHod2000MeterService() {
		return hod2000MeterService;
	}

	public void setHod2000MeterService(IHod2000MeterService hod2000MeterService) {
		this.hod2000MeterService = hod2000MeterService;
	}

	public void setHod2000SysparameterService(
			IHod2000SysparameterService hod2000SysparameterService) {
		this.hod2000SysparameterService = hod2000SysparameterService;
	}

	public void setCacheService(IHod2000ParaDownInfoCacheService cacheService) {
		this.cacheService = cacheService;
	}

	public Hod2000DeviceInitParameter getDeviceInitParameter() {
		return deviceInitParameter;
	}

	public void setDeviceInitParameter(
			Hod2000DeviceInitParameter deviceInitParameter) {
		this.deviceInitParameter = deviceInitParameter;
	}

	public void setDeviceInitParameterService(
			IHod2000DeviceInitParameterService deviceInitParameterService) {
		this.deviceInitParameterService = deviceInitParameterService;
	}

	public void setHod2000MeterTypeService(
			IHod2000MeterTypeService hod2000MeterTypeService) {
		this.hod2000MeterTypeService = hod2000MeterTypeService;
	}
	
	
}
