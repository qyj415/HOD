package com.hod.pro.web.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hod.message.ConstantValue;
import com.hod.message.MessageBase;
import com.hod.message.Util;
import com.hod.pojo.Hod2000BatchMeterError;
import com.hod.pojo.Hod2000Concentrator;
import com.hod.pojo.Hod2000Meter;
import com.hod.pojo.Hod2000MeterCallDownInfo;
import com.hod.pojo.Hod2000MeterType;
import com.hod.pojo.Hod2000ParaDownInfoCache;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Sysparameter;
import com.hod.pro.model.service.IHod2000BatchMeterErrorService;
import com.hod.pro.model.service.IHod2000ConcentratorService;
import com.hod.pro.model.service.IHod2000DownInfoCacheService;
import com.hod.pro.model.service.IHod2000MeterCallDownInfoService;
import com.hod.pro.model.service.IHod2000MeterService;
import com.hod.pro.model.service.IHod2000MeterTypeService;
import com.hod.pro.model.service.IHod2000ParaDownInfoCacheService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.pro.model.service.IHod2000SysparameterService;
import com.hod.util.Arith;
import com.hod.util.MD5;
import com.hod.util.Message;
import com.hod.util.NetworkTimeUtil;
import com.hod.util.OperatorLog;
import com.hod.util.ParseProperties;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000MeterAction 表计管理
 * 
 * @author yixiang
 */
public class Hod2000MeterAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000MeterService hod2000MeterService;
	private IHod2000ConcentratorService concentratorService;
	private IHod2000DownInfoCacheService hod2000DownInfoCacheService;
	private IHod2000SysparameterService hod2000SysparameterService;
	private IHod2000MeterTypeService hod2000MeterTypeService;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000MeterCallDownInfoService hod2000MeterCallDownInfoService;
	private IHod2000BatchMeterErrorService hod2000BatchMeterErrorService;
	private Hod2000Sysparameter sysparameter;
	private Hod2000Meter hod2000Meter;
	private Hod2000Meter meter;
	private List dataList;
	private PrintWriter out;
	private Message msg = Message.getInstance();
	private Logger log = Logger.getLogger(Hod2000MeterAction.class.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private MessageBase message;
	private IHod2000ParaDownInfoCacheService cacheService;
	private Hod2000ParaDownInfoCache cache;
	private File fileName;
	private MyJsonOut myjsonOut = new MyJsonOut();
	private CloseableHttpClient httpclient = HttpClients.createDefault();


	/**
	 * 加载下拉列表，转发到表计添加页面
	 * 
	 * @return
	 */
	public String toAdd() {
		try {
			request = ServletActionContext.getRequest();
			String parent_position=request.getParameter("parent_position");
			//上级表名称要根据上级表行政编码进行查询
			if(parent_position!=null&&!"".equals(parent_position))
			{
				//String parent_positionName=hod2000MeterService.getParentName(parent_position);
				String parent_positionName=hod2000MeterService.findNameByMeterPosition(parent_position);
				request.setAttribute("parent_position", parent_position);
				request.setAttribute("parent_positionName", parent_positionName);
			}
			// 表计类型
			DetachedCriteria meterType = DetachedCriteria
					.forClass(Hod2000MeterType.class);
			request.setAttribute("meterType", hod2000MeterTypeService
					.findByCriteria(meterType));
			// 口径
			request.setAttribute("caliber", hod2000SysparameterService
					.findByType(1));
			// 端口号
			request.setAttribute("com", hod2000SysparameterService.findByType(3));
			// 通信速率
			request.setAttribute("speed", hod2000SysparameterService.findByType(4));
			return "add";
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->toAdd:", e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/hod2000Meter!toSelect.do\">返回列表</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}

	/**
	 * 添加表计信息 表号不能重复
	 */
	public String doSave() {
		request = ServletActionContext.getRequest();
		String roomId = request.getParameter("roomId");
		String meterType = request.getParameter("meterType");
		//String units=request.getParameter("units");
		String meterName = hod2000Meter.getMeterName();
		String meterParent = hod2000Meter.getMeterParent();
		double meter_init = 0;
		try {
			// 验证表号的唯一性
			List list = hod2000MeterService.findbyMeterName(meterName);
			if (list.size() > 0) {
				msg.setMsg1("存在相同的表号" + meterName + "，请修改后再重新添加!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">重新添加</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//根据地理位置编码查询是否唯一，只查询正在使用的表
			int meterPositionCount=hod2000MeterService.findByMeterPosition(hod2000Meter.getMeterPosition());
			if(meterPositionCount>0)
			{
				msg.setMsg1("该位置已存在表计信息，请重新选择地理位置!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">重新添加</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			else
			{
				//如果是户型表
				if (roomId != null && !roomId.equals(""))
				{
					hod2000Meter.setHod2000Room((Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId)));
				}
				else
				{
					hod2000Meter.setHod2000Room(null);
				}
			}
			// 生成测量点号
			int meter_index = hod2000MeterService.getMeterIndex();
			hod2000Meter.setMeterIndex(meter_index);
			hod2000Meter.setMeterAble(1);
			// 表底数默认为0
//			if (hod2000Meter.getMeterInit() == null
//					|| hod2000Meter.getMeterInit().equals(""))
//				hod2000Meter.setMeterInit(0d);
//			else
//			{
//				if(units.equals("1"))
//				{
//					hod2000Meter.setMeterInit(Arith.KWHToMJ(Double.toString(hod2000Meter.getMeterInit())));
//				}
//			}
			hod2000Meter.setValveStatus(1);
			hod2000Meter.setBatteryStatus(0);
			hod2000Meter.setIsOnline(2);
			hod2000Meter.setEepromStatus(0);
			hod2000Meter.setFlowsensorStatus(0);
			hod2000Meter.setTepdownStatus(0);
			hod2000Meter.setTepupStatus(0);
			hod2000Meter.setHod2000Concentrator(null);
			hod2000Meter.setHod2000MeterType((Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(meterType)));
			// 查询所有的在线的集中器表信息
			List con_list = concentratorService.findIsonline("1");

			if (con_list.size() >= 1) {
				
				//根据最后关联的集中器标识重新对结果进行排序,并返回Hod2000Concentrator列表
				ArrayList<Hod2000Concentrator> temp = conSort(con_list);
				
				//把点名下发协议插入到点名表中
				saveCalldownInfo(temp,meterName);
				
				long start_time = System.currentTimeMillis();
				A: while (true) {
					long end_time = System.currentTimeMillis();
					List meter_call_list = hod2000MeterCallDownInfoService
							.findByHQL("from Hod2000MeterCallDownInfo where meterName='"
									+ meterName + "'");
					
					if (end_time - start_time >= ConstantValue.timeout) { // 超时
						for (int j = 0; j < meter_call_list.size(); j++) {
							// 删除点名表里面对应表的数据
							hod2000MeterCallDownInfoService
									.delete((Hod2000MeterCallDownInfo) meter_call_list.get(j));
						}
						msg.setError1("添加表计信息失败[关联集中器失败<超时>]");
						break A;
					}
					if (meter_call_list.size() >= 1) {
						// 计算hod2000meterCall.getState() == 2 出现的次数
						int stataNUm = 0;
						for (int j = 0; j < meter_call_list.size(); j++) {
							Hod2000MeterCallDownInfo hod2000meterCall = (Hod2000MeterCallDownInfo) meter_call_list
									.get(j);
							
							if (hod2000meterCall.getState() == 1) { // 集中器已回复且该表计在下发集中器中
								// 判断集中器中表计数量
								List meter_List = hod2000MeterService.findByHQL("from Hod2000Meter where hod2000Concentrator.conNumber='"
												+ hod2000meterCall.getConNum()+ "' ORDER BY meterIndex DESC");
								
								if (meter_List.size() < 1024 && meter_List != null) {
									hod2000Meter.setHod2000Concentrator(concentratorService.findByConNum(hod2000meterCall.getConNum()));
									//判断表计所选的表计类型是否已关联该集中器
									boolean relateConFlag=false;
									String[] typeIndexs=hod2000Meter.getHod2000Concentrator().getTypeIndexs().split(",");
									String type=""+hod2000Meter.getHod2000MeterType().getTypeIndex();
									for (String str : typeIndexs) {
										if(str.equals(type))
										{
											relateConFlag=true;
											break;
										}
									}
									if(!relateConFlag)
									{
										msg.setError1("该表计型号没有关联到集中器中，添加表计失败，请先在集中器"+hod2000meterCall.getConNum()+"中关联该表计型号!");
										msg.setLink(0, "<a href=\"" + request.getContextPath()
												+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">添加表计</a>");
										msg.setJump(false);
										request.setAttribute("message", msg);
									}else{
										//集中器位置与表计位置的匹配，截止到街道办的行政代码是否一致
										String cons=hod2000Meter.getHod2000Concentrator().getConAddress();
										if(cons!=null&&cons.length()>9)
										{
											cons=cons.substring(0,9);
											String meters=hod2000Meter.getMeterPosition().substring(0,9);
											if(!cons.equals(meters))
											{
												msg.setError1("集中器地址与表计地址匹配失败，请确认表计地址填写是否正确!");
												msg.setLink(0, "<a href=\"" + request.getContextPath()
														+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">添加表计</a>");
												msg.setJump(false);
												request.setAttribute("message", msg);
											}
											else
											{
												//下发终端热能表配置参数成功之后才添加表计
												int flag=initParams(hod2000meterCall.getConNum(), "0100"+Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4)+Util.getAddress(hod2000Meter.getMeterName())+Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2)+Util.toByte(hod2000Meter.getMeterBaudrate()), ConstantValue.meter);
												if(2==flag)
												{
													msg.setError1("终端热能表配置参数设置失败，添加表计失败!");
													msg.setLink(0, "<a href=\"" + request.getContextPath()
															+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">添加表计</a>");
													msg.setJump(false);
													request.setAttribute("message", msg);
												}
												else if(0==flag)
												{
													msg.setError1("终端热能表配置参数设置超时，添加表计失败!");
													msg.setLink(0, "<a href=\"" + request.getContextPath()
															+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">添加表计</a>");
													msg.setJump(false);
													request.setAttribute("message", msg);
												}
												else if(1==flag)
												{
													if(hod2000meterCall.getResultContent() == null || "".equals(hod2000meterCall.getResultContent())){
														msg.setMsg1("表底数未返回!");
														break;
													}else{
														meter_init = Double.valueOf(hod2000meterCall.getResultContent()).doubleValue();
													}
													
													hod2000Meter.setMeterInit(meter_init);
													hod2000MeterService.save(hod2000Meter);
													msg.setMsg1("添加表计信息成功!");
													OperatorLog.addOperatorLog(meterName+"表计信息添加");
													
													//发送表计信息
													StringBuilder sb = new StringBuilder();
													sb.append("meter_name=").append(meterName).append("&");
													sb.append("meter_index=").append(Integer.toString(meter_index)).append("&");
													if(hod2000Meter.getMeterCaliber() == null){
														sb.append("meter_caliber=").append("").append("&");
													}else{
														sb.append("meter_caliber=").append(hod2000Meter.getMeterCaliber()).append("&");
													}
													sb.append("type_index=").append(meterType).append("&");
													sb.append("meter_init=").append(meter_init).append("&");
													if(hod2000Meter.getMeterPort() == null){
														sb.append("meter_port=").append("").append("&");
													}else{
														sb.append("meter_port=").append(hod2000Meter.getMeterPort()).append("&");
													}
													if(hod2000Meter.getMeterBaudrate() == null){
														sb.append("meter_baudrate=").append("").append("&");
													}else{
														sb.append("meter_baudrate=").append(hod2000Meter.getMeterBaudrate()).append("&");
													}
													sb.append("meter_parent=").append(meterParent).append("&");
													if(hod2000Meter.getMeterPosition() == null){
														sb.append("meter_position=").append("").append("&");
													}else{
														sb.append("meter_position=").append(hod2000Meter.getMeterPosition()).append("&");
													}
													if(hod2000Meter.getMeterPositionName() == null){
														sb.append("meter_position_name=").append("").append("&");
													}else{
														sb.append("meter_position_name=").append(hod2000Meter.getMeterPositionName()).append("&");
													}
													sb.append("con_number=").append(hod2000meterCall.getConNum()).append("&");
													sb.append("room_id=").append(roomId).append("&");
													String sign = MD5.getMd5(sb.toString() + "key").toUpperCase();
													
													System.out.println(sb.toString());
													
													//发送表计信息
													String http="http://"+ParseProperties.getProperties().getProperty("serverIP")+"/HOD-2000/hodMeter!httpClientMeterInterface.do";
													//HttpPost httpPost = new HttpPost("http://200.10.10.120:8080/HOD-2000/hodMeter!httpClientMeterInterface.do");
													HttpPost httpPost = new HttpPost(http);
													List <NameValuePair> nvps = new ArrayList <NameValuePair>();
													nvps.add(new BasicNameValuePair("meter_name", meterName));
													nvps.add(new BasicNameValuePair("meter_index", Integer.toString(meter_index)));
													nvps.add(new BasicNameValuePair("meter_caliber", hod2000Meter.getMeterCaliber()));
													nvps.add(new BasicNameValuePair("type_index", meterType));
													nvps.add(new BasicNameValuePair("meter_init", hod2000meterCall.getResultContent()));
													nvps.add(new BasicNameValuePair("meter_port", hod2000Meter.getMeterPort()));
													nvps.add(new BasicNameValuePair("meter_baudrate", hod2000Meter.getMeterBaudrate()));
													nvps.add(new BasicNameValuePair("meter_parent", meterParent));
													nvps.add(new BasicNameValuePair("meter_position", hod2000Meter.getMeterPosition()));
													nvps.add(new BasicNameValuePair("meter_position_name", hod2000Meter.getMeterPositionName()));
													nvps.add(new BasicNameValuePair("con_number", hod2000meterCall.getConNum()));
													nvps.add(new BasicNameValuePair("room_id", roomId));
													nvps.add(new BasicNameValuePair("sign", sign));
													httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
													CloseableHttpResponse response2 = httpclient.execute(httpPost);

													try {
														if(response2.getStatusLine().getStatusCode() == 200){
															 System.out.println("表计信息发送成功!");
														}
													} finally {
													    response2.close();
													}
													
												}
											}
										}
										else
										{
											msg.setError1("请先确认"+hod2000Meter.getHod2000Concentrator().getConNumber()+"集中器的地址是否已经添加!");
											msg.setLink(0, "<a href=\"" + request.getContextPath()
													+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">添加表计</a>");
											msg.setJump(false);
											request.setAttribute("message", msg);
										}
									}
									
								}else{
									msg.setError1("添加表计信息失败，每个集中器最多只能关联1024个表计！");
								}
								
								// 把集中器之前最后出现的位置置为0
								Hod2000Concentrator hod2000Concentrator = temp.get(0);
								concentratorService.updateBySql("UPDATE Hod2000Concentrator set conLastPosition=0 where conId="
												+ hod2000Concentrator.getConId());
								// 把集中器现在最后出现的位置置为1
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
												+ hod2000meterCall.getConNum());
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
										+ hod2000meterCall.getConNum());
								for (int d = 0; d < meter_call_list.size(); d++) {
									// 删除点名表里面对应表的数据
									hod2000MeterCallDownInfoService
											.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
								}
								msg.setLink(0, "<a href=\"" + request.getContextPath()
										+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">继续添加</a>");
								msg.setJump(false);
								request.setAttribute("message", msg);
								return "message";
							}
							if (hod2000meterCall.getState() == 0) {// 存在有没有回复时，休眠1000MS，等待表计回复
								Thread.sleep(1000);
								continue A;
							}
							if (hod2000meterCall.getState() == 2 || hod2000meterCall.getSendFlag() == 2) {// 表计已回复且该表计不在下发集中器中
								stataNUm++;
								if (stataNUm == meter_call_list.size()) {
									msg.setError1("添加表计信息失败[关联集中器失败<集中器中不存在该表，请检查表计和集中器是否连接>]");
									for (int d = 0; d < meter_call_list.size(); d++) {
										// 删除点名表里面对应表的数据
										hod2000MeterCallDownInfoService
												.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
									}
									break A;
								}
							}
						}
					}

				}
			} else {
				msg.setError1("添加表计信息失败[关联集中器失败<获取集中器在线列表失败>]");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->doSave:", e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">重新添加</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	public void httpClientMeterInterface(){
		try {
			request = ServletActionContext.getRequest();
			
			String meter_name = request.getParameter("meter_name");
			String meter_index = request.getParameter("meter_index");
			String meter_caliber = request.getParameter("meter_caliber");
			String type_index = request.getParameter("type_index");
			String meter_init = request.getParameter("meter_init");
			String meter_port = request.getParameter("meter_port");
			String meter_baudrate = request.getParameter("meter_baudrate");
			String meter_parent = request.getParameter("meter_parent");
			String meter_position = request.getParameter("meter_position");
			String meter_position_name = request.getParameter("meter_position_name");
			String con_number = request.getParameter("con_number");
			String room_id = request.getParameter("room_id");
//			String sign = request.getParameter("sign");
			
//			StringBuilder sb = new StringBuilder();
//			sb.append("meter_name=").append(meter_name).append("&");
//			sb.append("meter_index=").append(meter_index).append("&");
//			sb.append("meter_caliber=").append(meter_caliber).append("&");
//			sb.append("type_index=").append(type_index).append("&");
//			sb.append("meter_init=").append(meter_init).append("&");
//			sb.append("meter_port=").append(meter_port).append("&");
//			sb.append("meter_baudrate=").append(meter_baudrate).append("&");
//			sb.append("meter_parent=").append(meter_parent).append("&");
//			sb.append("meter_position=").append(meter_position).append("&");
//			sb.append("meter_position_name=").append(meter_position_name).append("&");
//			sb.append("con_number=").append(con_number).append("&");
//			sb.append("room_id=").append(room_id).append("&");
//			
//			System.out.println(sb.toString());
//			
//			String ver_sign = MD5.getMd5(sb.toString() + "key").toUpperCase();
			
			//if(ver_sign.equals(sign)){
				Hod2000Meter hod2000Meter = new Hod2000Meter();
				hod2000Meter.setMeterName(meter_name);
				hod2000Meter.setMeterIndex(Integer.parseInt(meter_index));
				hod2000Meter.setMeterCaliber(meter_caliber);
				//hod2000Meter.setHod2000MeterType((Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(type_index)));
				hod2000Meter.setHod2000MeterType(null);
				hod2000Meter.setMeterInit(Double.valueOf(meter_init));
				hod2000Meter.setMeterPort(meter_port);
				hod2000Meter.setMeterBaudrate(meter_baudrate);
				hod2000Meter.setMeterParent(meter_parent);
				hod2000Meter.setMeterPosition(meter_position);
				hod2000Meter.setMeterPositionName(meter_position_name);
				hod2000Meter.setMeterAble(1);
				hod2000Meter.setMeterStyle(1);
				//hod2000Meter.setHod2000Concentrator(concentratorService.findByConNum(con_number));
				hod2000Meter.setHod2000Concentrator(null);
//				if (room_id != null && !room_id.equals(""))
//				{
//					hod2000Meter.setHod2000Room((Hod2000Room) hod2000RoomService.findById(Integer.parseInt(room_id)));
//				}
//				else
//				{
//					hod2000Meter.setHod2000Room(null);
//				}
				hod2000Meter.setHod2000Room(null);
				hod2000Meter.setValveStatus(1);
				hod2000Meter.setBatteryStatus(0);
				hod2000Meter.setIsOnline(2);
				hod2000MeterService.save(hod2000Meter);
			//}
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * 热力公司的添加
	 */
	public void doSaveCompany()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String meterPositionName=request.getParameter("meterPositionName");
			//判断热力公司名称是否重复
			if(hod2000MeterService.findByPositionName(meterPositionName)>0)
			{
				out.write("{success:false,msg:'已存在该热力公司名称!'}");
				return;
			}
			hod2000Meter=new Hod2000Meter();
			hod2000Meter.setMeterParent("0");
			hod2000Meter.setMeterPosition(hod2000MeterService.getCompanyPosition());
			hod2000Meter.setMeterPositionName(meterPositionName);
			String companyMeterName=hod2000MeterService.getCompanyMeterName();
			if(companyMeterName!=null&&!"".equals(companyMeterName))
				hod2000Meter.setMeterName(companyMeterName);
			else
			{
				out.write("{success:false,msg:'添加热力公司失败，热力公司数量也达到最大限制!'}");
				return;
			}
			hod2000Meter.setHod2000Concentrator(null);
			hod2000Meter.setHod2000MeterType(null);
			hod2000Meter.setHod2000Room(null);
			hod2000Meter.setMeterAble(0);
			hod2000Meter.setMeterStyle(null);
			hod2000MeterService.save(hod2000Meter);
			OperatorLog.addOperatorLog("添加热力公司");
			out.write("{success:true}");
		} catch (Exception e) {
			log.error(e);
			out.write("{success:false,msg:'添加热力公司失败!'}");
		}
	}
	
	
	/**
	 * 把点名下发协议插入到点名表中
	 * 
	 * @param hod2000ConcentratorList
	 */
	public void saveCalldownInfo(ArrayList<Hod2000Concentrator> hod2000ConcentratorList,String meterName){
		
		if(hod2000ConcentratorList == null){
			return;
		}
		
		for (int i = 0; i < hod2000ConcentratorList.size(); i++) {
			Hod2000Concentrator con = hod2000ConcentratorList.get(i);
			message=new MessageBase(ConstantValue.conType,Util.getAddr(con.getConNumber(),ConstantValue.procuNum),1);
			String sendContent=Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(ConstantValue.callCon,Util.getAddress(meterName)+Util.getAddress(hod2000Meter.getHod2000MeterType().getCompanyNum()),ConstantValue.SEQ)));
			Hod2000MeterCallDownInfo hmcd = new Hod2000MeterCallDownInfo();
			hmcd.setMeterName(meterName);
			hmcd.setConNum(con.getConNumber());
			hmcd.setSendContent(sendContent);
			hmcd.setProtocolType(ConstantValue.callCon);
			//hmcd.setSubmitTime(sdf.format(new Date()));
			hmcd.setSubmitTime(sdf.format(NetworkTimeUtil.getDate()));
			hmcd.setSendFlag(-1);
			hmcd.setState(0);
			log.info("点名协议："+sendContent);
			try {
				hod2000MeterCallDownInfoService.save(hmcd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据最后关联的集中器标识重新对结果进行排序,并返回Hod2000Concentrator列表
	 */
	public ArrayList<Hod2000Concentrator> conSort(List con_list){
		ArrayList<Hod2000Concentrator> temp = new ArrayList<Hod2000Concentrator>();
		boolean isFind = false;
		int findIndex = 0;
		for(int i=0;i<con_list.size();i++){
			Hod2000Concentrator hod2000Concentrator = (Hod2000Concentrator) con_list.get(i);
			if(isFind){
				if(i == con_list.size()-1){//说明list已经遍历到最后一个了
					if(findIndex == 0){//说明最后关联的集中器标识为第一个
						break;
					}else{
						for(int j=0;j<findIndex;j++){
							//把最后关联的集中器前面的元素添加到链表末端
							temp.add((Hod2000Concentrator) con_list.get(j));
						}
					}
				}else{
					temp.add(hod2000Concentrator);
				}
			}else{
				Integer integer = hod2000Concentrator.getConLastPosition();
				int position = 0;
				if(integer != null){
					position = integer.intValue();
				}
				if(position == 1){ //找到上次最后关联的集中器标识
					isFind = true;
					findIndex = i;
					temp.add(hod2000Concentrator);
				}
				if(i == con_list.size()-1 && isFind == false){//OMG，ConLastPosition==1 从未出现
					for(int m=0;m<con_list.size();m++){//把所有元素遍历到Temp中
						temp.add((Hod2000Concentrator) con_list.get(m));
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * 终端热能表查询下发
	 * @param conNum 集中器编号
	 * @param data 数据域
	 * @param identificationId 标识编码
	 * @return
	 */
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
	
	public int meterCall(String conNum,String meterName, String data,
			String identificationId) {
		int flag=0;
		try {
			message=new MessageBase(ConstantValue.conType,Util.getAddr(conNum,ConstantValue.procuNum),1);
			double meter_init;
			Hod2000MeterCallDownInfo callDownInfo=new Hod2000MeterCallDownInfo();
			callDownInfo.setConNum(conNum);
			callDownInfo.setMeterName(meterName);
			callDownInfo.setProtocolType(identificationId);
			callDownInfo.setSendFlag(-1);
			callDownInfo.setState(0);
			callDownInfo.setSubmitTime(Utils.dateToStrLong(NetworkTimeUtil.getDate()));
			callDownInfo.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(identificationId,data,ConstantValue.SEQ))));
			hod2000MeterCallDownInfoService.save(callDownInfo);
			log.info("点名协议："+callDownInfo.getSendContent());
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				callDownInfo=(Hod2000MeterCallDownInfo) hod2000MeterCallDownInfoService.findById(callDownInfo.getCallId());
				if(1==callDownInfo.getState())//成功
				{
					if(callDownInfo.getResultContent() != null && !"".equals(callDownInfo.getResultContent())){
						flag=1;
						meter_init = Double.valueOf(callDownInfo.getResultContent()).doubleValue();
						meter.setMeterInit(meter_init);
					}else{
						flag=2;
						break;
					}
				}
				if(2==callDownInfo.getState())//失败
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					callDownInfo=(Hod2000MeterCallDownInfo) hod2000MeterCallDownInfoService.findById(callDownInfo.getCallId());
					if(0==callDownInfo.getState())//超时
					{
						flag=0;
					}
					break;
				}
			}
			hod2000MeterCallDownInfoService.delete(callDownInfo);
		} catch (Exception e) {
			flag=2;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 设置阀门编号
	 */
	public void setValveId()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String valveId=request.getParameter("valveId");
			String meterId=request.getParameter("meterId");
			if(meterId!=null&&!"".equals(meterId))
			{
				hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(Integer.parseInt(meterId));
				if(valveId!=null&&!"".equals(valveId))
				{
					if(!valveId.equals(hod2000Meter.getValveId()))
					{
						//查询该阀门编号是否重复
						DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Meter.class);
						dc.add(Restrictions.eq("valveId", valveId));
						dataList=hod2000MeterService.findByCriteria(dc);
						if(dataList.size()>0)
						{
							out.write("{success:false,msg:'阀门编号有重复!'}");
						}
						else
						{
							hod2000Meter.setValveId(valveId);
							hod2000MeterService.update(hod2000Meter);
							OperatorLog.addOperatorLog("设置阀门编号"+valveId);
							out.write("{success:true,msg:'设置阀门编号成功!'}");
						}
					}
					else
					{
						out.write("{success:false,msg:'阀门编号没有进行变更!'}");
					}
				}
				else
				{
					hod2000Meter.setValveId("");
					hod2000MeterService.update(hod2000Meter);
					OperatorLog.addOperatorLog("清除阀门编号"+valveId);
					out.write("{success:true,msg:'清除阀门编号成功!'}");
					//out.write("{success:false,msg:'阀门编号不能为空!'}");
				}
			}
			else
			{
				out.write("{success:false,msg:'表计编号不能为空!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterAction->setValveId:",e);
			out.write("{success:false}");
		}
	}
	
	/**
	 * 阀门控制
	 */
	public void valveControl()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String valveStatus=request.getParameter("valveStatus");
			String meterId=request.getParameter("meterId");
			String data="";
			if(valveStatus.equals("0"))//关阀门
			{
				data="0099";
			}
			else
			{
				data="0055";
			}
			if(meterId!=null&&!"".equals(meterId))
			{
				hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(Integer.parseInt(meterId));
				if(2==hod2000Meter.getHod2000Concentrator().getConIsonline())
				{
					out.write("{success:false,msg:'"+hod2000Meter.getHod2000Concentrator().getConNumber()+"集中器不在线!'}");
					return;
				}
				if(hod2000Meter.getValveId()==null||"".equals(hod2000Meter.getValveId()))
				{
					out.write("{success:false,msg:'该表计没有设置阀门编号，请先设置阀门编号!'}");
				}
				else
				{
					//message=new MessageBase(ConstantValue.meterType,Util.getAddr(hod2000Meter.getValveId(),ConstantValue.procuNum),2);
					message=new MessageBase(ConstantValue.valveType,Util.convert2HexArray(hod2000Meter.getValveId()+"002222"),2);
					cache=new Hod2000ParaDownInfoCache();
					cache.setConNum(hod2000Meter.getHod2000Concentrator().getConNumber());
					cache.setProtocolType(ConstantValue.valve);
					cache.setSendFlag(-1);
					cache.setState(0);
					//cache.setSubmitTime(new Date());
					cache.setSubmitTime(NetworkTimeUtil.getDate());
					cache.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(ConstantValue.valve,data,""))));
					cacheService.save(cache);
					log.info("阀门控制："+cache.getSendContent());
					long start_time = System.currentTimeMillis();
					while (true) {
						long end_time = System.currentTimeMillis();
						cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
						if(1==cache.getState())//成功
						{
							out.write("{success:true,msg:'阀门操作成功!'}");
							OperatorLog.addOperatorLog("阀门操作");
							break;
						}
						if(2==cache.getState())//失败
						{
							out.write("{success:false,msg:'阀门操作失败!'}");
							break;
						}
						if (end_time - start_time >= ConstantValue.timeout) { 
							cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
							if(0==cache.getState())//超时
							{
								out.write("{success:false,msg:'阀门控制请求超时!'}");
							}
							break;
						}
					}
					cacheService.delete(cache);
				}
			}
		} catch (Exception e) {
			log.error("Hod2000MeterAction->valveControl:",e);
			out.write("{success:false}");
		}
	}
	
	public String toUpdate() {
		try {
			request = ServletActionContext.getRequest();
			int id = Integer.parseInt(request.getParameter("id"));
			hod2000Meter = (Hod2000Meter) hod2000MeterService.findById(id);
			hod2000Meter.setMeterInit(Arith.dataFormat(hod2000Meter.getMeterInit()));
			return "toUpdate";
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->toUpdate", e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/hod2000Meter!toSelect.do\">返回列表</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}

	public String doUpdate() {
		try {
			request = ServletActionContext.getRequest();
			meter=new Hod2000Meter();
			int flag=0;
			int meterId = Integer.parseInt(request.getParameter("meterId"));
			String meterName=request.getParameter("meter_name");
			//String units=request.getParameter("units");
			//String meter_init = request.getParameter("meter_init");
			String changeValue=request.getParameter("changeValue");//旧表累计热量，单位KWh
			hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(meterId);
			if(meterName==null||"".equals(meterName))
			{
				msg.setError1("表号不能为空!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//判断表号是否更改
			if(meterName.equals(hod2000Meter.getMeterName()))
			{
				msg.setError1("表号没有进行修改!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//判断集中器是否在线
			if(2==hod2000Meter.getHod2000Concentrator().getConIsonline())
			{
				msg.setError1(hod2000Meter.getHod2000Concentrator().getConNumber()+"集中器不在线!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			//对旧表集中器进行点名
			flag=meterCall(hod2000Meter.getHod2000Concentrator().getConNumber(),meterName, Util.getAddress(meterName)+Util.getAddress(hod2000Meter.getHod2000MeterType().getCompanyNum()), ConstantValue.callCon);
			if(0==flag)
			{
				msg.setError1("添加表计信息失败[关联集中器失败<超时>]");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			if(2==flag)
			{
				msg.setError1("添加表计信息失败[关联集中器失败<集中器中不存在该表，请检查表计和集中器是否连接>]!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			// 表底数默认为0
//			if (meter_init == null || meter_init.equals(""))
//			{
//				meter.setMeterInit(0d);
//			}
//			else
//			{
//				if(units.equals("1"))
//				{
//					meter.setMeterInit(Arith.KWHToMJ(meter_init));
//				}
//			}
			meter.setMeterName(meterName);
			meter.setMeterAble(1);
			meter.setBatteryStatus(hod2000Meter.getBatteryStatus());
			meter.setHod2000Concentrator(hod2000Meter.getHod2000Concentrator());
			meter.setHod2000MeterType(hod2000Meter.getHod2000MeterType());
			meter.setHod2000Room(hod2000Meter.getHod2000Room());
			meter.setIsOnline(hod2000Meter.getIsOnline());
			meter.setEepromStatus(hod2000Meter.getEepromStatus());
			meter.setFlowsensorStatus(hod2000Meter.getFlowsensorStatus());
			meter.setTepdownStatus(hod2000Meter.getTepdownStatus());
			meter.setTepupStatus(hod2000Meter.getTepupStatus());
			meter.setMeterBaudrate(hod2000Meter.getMeterBaudrate());
			meter.setMeterCaliber(hod2000Meter.getMeterCaliber());
			meter.setMeterIndex(hod2000Meter.getMeterIndex());
			meter.setMeterParent(hod2000Meter.getMeterParent());
			meter.setMeterPort(hod2000Meter.getMeterPort());
			meter.setMeterPosition(hod2000Meter.getMeterPosition());
			meter.setMeterPositionName(hod2000Meter.getMeterPositionName());
			meter.setValveStatus(hod2000Meter.getValveStatus());
			meter.setValveId(hod2000Meter.getValveId());
			meter.setMeterStyle(hod2000Meter.getMeterStyle());
			//下发终端热能表配置参数成功之后才添加表计
			flag=initParams(hod2000Meter.getHod2000Concentrator().getConNumber(), "0100"+Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4)+Util.getAddress(meterName)+Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2)+Util.toByte(hod2000Meter.getMeterBaudrate()), ConstantValue.meter);
			if(2==flag)
			{
				msg.setError1("终端热能表配置参数设置失败，更换表计失败!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			else if(0==flag)
			{
				msg.setError1("终端热能表配置参数设置超时，更换表计失败!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">返回表计管理</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000MeterService.save(meter);
			//修改旧表计
			if(changeValue!=null&&!"".equals(changeValue))
			{
				hod2000Meter.setChangeValue(changeValue);
			}
			hod2000Meter.setMeterAble(0);
			//hod2000Meter.setChangeMeterTime(new Date());
			hod2000Meter.setChangeMeterTime(NetworkTimeUtil.getDate());
			hod2000MeterService.update(hod2000Meter);
			msg.setMsg1("更换表计操作成功!");
			OperatorLog.addOperatorLog(hod2000Meter.getMeterName()+"表号变更为"+meterName);
		} catch (Exception e) {
			msg.setError1("错误信息：" + e.toString());
			log.error("Hod2000MeterAction-->doUpdate:", e);
			msg.setJump(false);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Meter!toSelect.do\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}

	/**
	 * 表计状态查询 返回json
	 */
	public void doMonitor() {
		try {
			OperatorLog.addOperatorLog("表计状态查询");
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			String meterName = request.getParameter("meterName");
			String valveStatus = request.getParameter("valveStatus");
			String batteryStatus = request.getParameter("batteryStatus");
			String isOnline = request.getParameter("isOnline");
			String meterPosition = request.getParameter("meterPosition");
			List list=new ArrayList();
			Object[] objs;
			Map map;
			String sql="select meter_name,meter_position_name,valve_status,battery_status,isonline,eeprom_status,flowsensor_status,tepdown_status,tepup_status from hod2000_meter where meter_parent!='0' and meter_able=1";
			String count="select count(*) from hod2000_meter where meter_parent!='0' and meter_able=1";
			if(meterName!=null&&!meterName.equals(""))
			{
				sql+=" and meter_name like '"+meterName+"%'";
				count+=" and meter_name like '"+meterName+"%'";
			}
			if(valveStatus!=null&&!valveStatus.equals(""))
			{
				sql+=" and valve_status="+Integer.parseInt(valveStatus);
				count+=" and valve_status="+Integer.parseInt(valveStatus);
			}
			if(batteryStatus!=null&&!batteryStatus.equals(""))
			{
				sql+=" and battery_status="+Integer.parseInt(batteryStatus);
				count+=" and battery_status="+Integer.parseInt(batteryStatus);
			}
			if(isOnline!=null&&!isOnline.equals(""))
			{
				sql+=" and isonline="+Integer.parseInt(isOnline);
				count+=" and isonline="+Integer.parseInt(isOnline);
			}
			if(meterPosition!=null&&!meterPosition.equals(""))
			{
				sql+=" and meter_position like '"+meterPosition+"%'";
				count+=" and meter_position like '"+meterPosition+"%'";
			}
			dataList=hod2000MeterService.findByNHQL(page, pageSize, sql);
			for (int i = 0; i < dataList.size(); i++) {
				objs = (Object[]) dataList.get(i);
				map=new HashMap();
				map.put("meterName", objs[0]);
				map.put("meterPositionName", objs[1]);
				map.put("valveStatus", objs[2]);
				map.put("batteryStatus", objs[3]);
				map.put("isOnline", objs[4]);
				map.put("eepromStatus", objs[5]);
				map.put("flowsensorStatus", objs[6]);
				map.put("tepdownStatus", objs[7]);
				map.put("tepupStatus", objs[8]);
				list.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000MeterService.findByNHQL(count).get(0).toString());
			myjsonOut.OutByObject("Hod2000MeterAction-->doMonitor:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->doMonitor:", e);
		}
	}

	/**
	 * 表计状态查询 打印用，非json
	 */
	public String monitorPrint() {
		try {
			OperatorLog.addOperatorLog("表计状态打印预览");
			request = ServletActionContext.getRequest();
			String meterName = request.getParameter("meterName");
			String valveStatus = request.getParameter("valveStatus");
			String batteryStatus = request.getParameter("batteryStatus");
			String isOnline = request.getParameter("isOnline");
			String meterPosition = request.getParameter("meterPosition");
			List list=new ArrayList();
			Object[] objs;
			Map map;
			dataList = hod2000MeterService.findByParams(meterName, valveStatus,
					batteryStatus, isOnline, meterPosition);
			for (int i = 0; i < dataList.size(); i++) {
				objs = (Object[]) dataList.get(i);
				map=new HashMap();
				map.put("meterName", objs[0]);
				map.put("meterPositionName", objs[1]);
				map.put("valveStatus", objs[2]);
				map.put("batteryStatus", objs[3]);
				map.put("isOnline", objs[4]);
				map.put("eepromStatus", objs[5]);
				map.put("flowsensorStatus", objs[6]);
				map.put("tepdownStatus", objs[7]);
				map.put("tepupStatus", objs[8]);
				list.add(map);
			}
			request.setAttribute("list", list);
			return "monitorPrint";
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->doSelectMonitor:", e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	//查询表计型号，口径等信息转到表计管理页面
	public String toSelect()
	{
		request = ServletActionContext.getRequest();
		DetachedCriteria meterTypes = DetachedCriteria.forClass(Hod2000MeterType.class);// 表计类型
		request.setAttribute("meterTypes", hod2000MeterTypeService.findByCriteria(meterTypes));
		request.setAttribute("caliber", hod2000SysparameterService.findByType(1));// 口径
		return "success";
	}

	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("表计信息查询");
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			String meterName = request.getParameter("meterName");// 表号
			String terminalNumber = request.getParameter("terminalId");// 集中器编号
			String meterType = request.getParameter("meter_type");// 表计型号
			String meterCaliber = request.getParameter("meter_caliber");// 口径
			String meterStyle=request.getParameter("meterStyle");//表计类型
			String meterParent=request.getParameter("meterParent");//上级表行政区域代码
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Meter.class);
			if(meterName!=null&&!meterName.equals(""))
			{
				dc.add(Restrictions.like("meterName", meterName+"%"));
			}
			if(terminalNumber!=null&&!terminalNumber.equals(""))
			{
				Hod2000Concentrator concentrator = concentratorService.findByConNums(terminalNumber);
				if (concentrator != null)
				{
					dc.add(Restrictions.eq("hod2000Concentrator", concentrator));
				}
				else
				{
					dc.add(Restrictions.eq("hod2000Concentrator", null));
				}
			}
			if(meterType!=null&&!meterType.equals(""))
			{
				Hod2000MeterType hod2000MeterType =(Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(meterType));// hod2000MeterTypeService.findByTypeCode(meterType);
				if (hod2000MeterType != null)
				{
					dc.add(Restrictions.eq("hod2000MeterType", hod2000MeterType));
				}
			}
			if(meterCaliber!=null&&!meterCaliber.equals(""))
			{
				dc.add(Restrictions.eq("meterCaliber", meterCaliber));
			}
			if(meterStyle!=null&&!meterStyle.equals(""))
			{
				dc.add(Restrictions.eq("meterStyle", Integer.parseInt(meterStyle)));
			}
			if(meterParent!=null&&!"".equals(meterParent))
			{
				dc.add(Restrictions.eq("meterParent", meterParent));
			}
			dc.add(Restrictions.ne("meterParent", "0"));
			dataList=hod2000MeterService.findByCriteria(page, pageSize, dc);
			Map ma;
			List list=new ArrayList();
			for (int i = 0; i < dataList.size(); i++) {
				hod2000Meter=(Hod2000Meter) dataList.get(i);
				ma=new HashMap();
				ma.put("id", hod2000Meter.getMeterId());
				ma.put("meterName", hod2000Meter.getMeterName());
				ma.put("meterInit", hod2000Meter.getMeterInit());
				ma.put("meterCaliber", hod2000Meter.getMeterCaliber());
				ma.put("meterBaudrate", hod2000Meter.getMeterBaudrate());
				if(null==hod2000Meter.getHod2000MeterType())
					ma.put("typeName", "");
				else
					ma.put("typeName", hod2000Meter.getHod2000MeterType().getTypeName());
				ma.put("meterPositionName", hod2000Meter.getMeterPositionName());
				if(null==hod2000Meter.getHod2000Concentrator())
					ma.put("conNumber", "");
				else
					ma.put("conNumber", hod2000Meter.getHod2000Concentrator().getConNumber());
				ma.put("meterStyle", hod2000Meter.getMeterStyle());
				ma.put("meterAble", hod2000Meter.getMeterAble());
				ma.put("valveId", hod2000Meter.getValveId());
				list.add(ma);
			}
			int totalRecord =(Integer)hod2000MeterService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000MeterAction-->doSelect:",list,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->doSelect:", e);
		}
	}

	public String meterPrint() {
		try {
			OperatorLog.addOperatorLog("表计信息打印预览");
			request = ServletActionContext.getRequest();
			String meterName = request.getParameter("meterName");// 表号
			String terminalNumber = request.getParameter("terminalId");// 集中器编号
			String meterType = request.getParameter("meter_type");// 表计型号
			String meterCaliber = request.getParameter("meter_caliber");// 口径
			String meterStyle=request.getParameter("meterStyle");//表计类型
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Meter.class);
			if(meterName!=null&&!meterName.equals(""))
			{
				dc.add(Restrictions.like("meterName", meterName+"%"));
			}
			if(terminalNumber!=null&&!terminalNumber.equals(""))
			{
				Hod2000Concentrator concentrator = concentratorService.findByConNums(terminalNumber);
				if (concentrator != null)
				{
					dc.add(Restrictions.eq("hod2000Concentrator", concentrator));
				}
			}
			if(meterType!=null&&!meterType.equals(""))
			{
				Hod2000MeterType hod2000MeterType =(Hod2000MeterType) hod2000MeterTypeService.findById(Integer.parseInt(meterType));// hod2000MeterTypeService.findByTypeCode(meterType);
				if (hod2000MeterType != null)
				{
					dc.add(Restrictions.eq("hod2000MeterType", hod2000MeterType));
				}
			}
			if(meterCaliber!=null&&!meterCaliber.equals(""))
			{
				dc.add(Restrictions.eq("meterCaliber", meterCaliber));
			}
			if(meterStyle!=null&&!meterStyle.equals(""))
			{
				dc.add(Restrictions.eq("meterStyle", Integer.parseInt(meterStyle)));
			}
			dc.add(Restrictions.ne("meterParent", "0"));
			dataList=hod2000MeterService.findByCriteria(dc);
			return "meterPrint";
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->meterPrint:", e);
			msg.setError1(e.toString());
			request.setAttribute("message", msg);
			return "message";
		}
	}

	public void doTree() {
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			Object[] objects;
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("[{id:'0',text:'城市建筑智慧能源管理系统',meter_position:'',expanded:true,children:[");
			// 查询meter_parent=0的表信息
			List list = hod2000MeterService.findByMeterParent("0",0);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					objects = (Object[]) list.get(i);
					buffer.append("{id:'" + objects[0] + "',");
					buffer.append("text:'" + objects[1] + "',");
					buffer.append("meter_position:'" + objects[2] + "',");
					buffer.append("meter_parent:'0',");
					buffer.append("meter_style:'" + objects[5] + "',");
					buffer.append(hod2000MeterService.findChild(objects[2].toString()));
				}
				buffer.deleteCharAt(buffer.length() - 1);
			}
			buffer.append("]}]");
			out.write(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getMeters()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String meterParent=request.getParameter("meterParent");
			if(meterParent!=null&&!"".equals(meterParent))
			{
				StringBuffer buffer = new StringBuffer();
				Object[] objects;
				//buffer.append("[{id:'0',text:'宏德城市建筑智慧能源管理系统',meter_position:'',expanded:true,children:[");
				buffer.append("[");
				List list=hod2000MeterService.findByNHQL("select meter_id,meter_position_name,meter_position,meter_parent,meter_name,meter_style from hod2000_meter where meter_parent='"+meterParent+"' and meter_able=1");
				for (int i = 0; i < list.size(); i++) {
					objects = (Object[]) list.get(i);
					buffer.append("{id:'" + objects[0] + "',");
					if(objects[5].equals(1))
						buffer.append("text:'" + objects[1] + "户用表',");
					else if(objects[5].equals(2))
						buffer.append("text:'" + objects[1] + "楼栋表',");
					else
						buffer.append("text:'" + objects[1] + "换能站',");
					buffer.append("meter_position:'" + objects[2] + "',");
					buffer.append("meter_name:'" + objects[4] + "',");
					buffer.append("meter_style:'" + objects[5] + "',leaf:true},");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append("]");
				out.write(buffer.toString());
			}
			else
			{
				out.write("");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->getMeters:"+e);
			
		}
	}
	
	/**
	 * 表计管理地图模块
	 * 根据楼栋id查询该楼栋下的所有表计
	 */
	public void getMeter()
	{
		try {
			OperatorLog.addOperatorLog("表计管理地图页面中根据楼栋编号查询表计信息");
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("terminal");
			//根据楼栋查表计信息
			if(buildingId!=null&&!buildingId.equals(""))
			{
				dataList=hod2000MeterService.findByBuildingById(Integer.parseInt(buildingId));
				List list=new ArrayList();
				Object[] objects;
				Map map;
				for (int i = 0; i < dataList.size(); i++) {
					map=new HashMap();
					objects=(Object[]) dataList.get(i);
					map.put("meterName", objects[0]);
					map.put("meterPositionName", objects[1]);
					map.put("meterInit", objects[2]);
					map.put("valveStatus", objects[4]);
					map.put("batteryStatus", objects[5]);
					map.put("isOnline", objects[6]);
					map.put("eepromStatus", objects[7]);
					map.put("flowsensorStatus", objects[8]);
					map.put("tepdownStatus", objects[9]);
					map.put("tepupStatus", objects[10]);
					list.add(map);
				}
				out.write("{totalCount:"+String.valueOf(list.size())+",result:"+JSONArray.fromObject(list).toString()+"}");
			}
			else
			{
				out.write("{success:false,msg:'楼栋编号不能为空!'}");
			}
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	//编辑表计地址
	public void setAddress()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String meterPosition=request.getParameter("meterPosition");
			String meterId=request.getParameter("meterId");
			String roomId=request.getParameter("roomId");
			String meterPositionName=request.getParameter("meterPositionName");
			hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(Integer.parseInt(meterId));
			//根据地理位置编码查询是否唯一，只查询正在使用的表
			int meterPositionCount=hod2000MeterService.findByMeterPosition(meterPosition);
			if(meterPositionCount>0)
			{
				out.write("{success:false,msg:'该位置已存在表计信息，请重新选择地理位置!'}");
				return;
			}
			//集中器位置与表计位置的匹配，截止到街道办的行政代码是否一致
			String cons=hod2000Meter.getHod2000Concentrator().getConAddress();
			if(cons!=null&&cons.length()>9)
			{
				cons=cons.substring(0,9);
				String meters=meterPosition.substring(0,9);
				if(!cons.equals(meters))
				{
					out.write("{success:false,msg:'集中器地址与表计地址匹配失败，请确认表计地址填写是否正确!'}");
					return;
				}
			}
			else
			{
				out.write("{success:false,msg:'请先确认"+hod2000Meter.getHod2000Concentrator().getConNumber()+"集中器的地址是否已经添加!'}");
				return;
			}
			//如果是户型表
			if (roomId != null && !roomId.equals(""))
			{
				if(hod2000Meter.getMeterStyle()>1)
				{
					out.write("{success:false,msg:'楼栋表或换能站的地址不能为房间!'}");
					return;
				}
				hod2000Meter.setHod2000Room((Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId)));
				hod2000Meter.setMeterStyle(1);
			}
			else
			{
				hod2000Meter.setHod2000Room(null);
			}
			hod2000Meter.setMeterPosition(meterPosition);
			hod2000Meter.setMeterPositionName(meterPositionName);
			hod2000MeterService.update(hod2000Meter);
			OperatorLog.addOperatorLog("编辑"+hod2000Meter.getMeterName()+"表计的地址信息");
			out.write("{success:true,msg:'编辑表计地址信息成功!',meterPositionName:'"+meterPositionName+"'}");
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	/**
	 * 批量加表
	 */
	public void batchFileUp(){
		
		ArrayList<String> err_al = new ArrayList<String>();//保存错误数据
		
		if (fileName != null) {
			BufferedReader bfr = null;
			try {
				out = ServletActionContext.getResponse().getWriter();
				bfr = new BufferedReader(new FileReader(fileName));
				String line = null;
				while((line = bfr.readLine()) != null){
					line = addMeterlineUtil(line);
					if(line != null){
						err_al.add(line);
					}
				}
				
				if(err_al.size() > 0){
					for(String errStr : err_al){
						saveErrorMeter(errStr);
					}
				}
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
	
	public void saveMeterError(){

		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			
			String batchMeterId = request.getParameter("batchMeterId");
			String errorMsg = request.getParameter("errorMsg");
		    String meterName = request.getParameter("meterName") ;
		    String meterCaliber = request.getParameter("meterCaliber") ;
		    String meterBaudrate = request.getParameter("meterBaudrate") ;
		    String typeName = request.getParameter("typeName") ;
		    String meterPosition = request.getParameter("meterPosition") ;
		    String meterParent = request.getParameter("meterParent") ;
		    
		    StringBuilder sb = new StringBuilder();
		    //sb.append(batchMeterId).append(",");
		    //sb.append(errorMsg).append(",");
		    sb.append(meterName).append(",");
		    sb.append(meterCaliber).append(",");
		    sb.append(meterBaudrate).append(",");
		    sb.append(typeName).append(",");
		    sb.append(meterPosition).append(",");
		    sb.append(meterParent);
		    
		    String lineRs = addMeterlineUtil(sb.toString());
		    if(lineRs != null){
		    	//处理错误数据——》更新到错误数据表
		    	Hod2000BatchMeterError meterError = (Hod2000BatchMeterError)hod2000BatchMeterErrorService.findById(Integer.parseInt(batchMeterId));
		    	updatehod2000MeterClientError(lineRs,meterError);
		    	//errData:错误数据,用户姓名,用户性别,证件类型,证件号码,联系地址,联系电话,价格方案, 地理位置,备注信息
				out.write("{success:false,errData:'" + lineRs + "'}");
		    }else{
		    	//当保存成功(错误信息已更正)，删除错误信息表中的对应数据
		    	hod2000BatchMeterErrorService.deleteByKey(Integer.parseInt(batchMeterId));
		    	out.write("{success:true}");
		    }
			
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	public void updatehod2000MeterClientError(String errLine,Hod2000BatchMeterError meterError){
		try {
			//处理错误数据——》更新到错误数据表
			String[] errStrs = errLine.split(",");
			if(errStrs.length < 7){
				String[] temp_errs = new String[10];
				//防止数组下标越界
				System.arraycopy(errStrs, 0, temp_errs, 0, errStrs.length);
				errStrs = temp_errs;
			}
			//错误数据,表号,口径,波特率,表计型号,表计类型,地理位置,上级表号
			meterError.setErrorMsg(errStrs[0]);
			meterError.setMeterName(errStrs[1]);
			meterError.setMeterCaliber(errStrs[2]);
			meterError.setMeterBaudrate(errStrs[3]);
			meterError.setTypeName(errStrs[4]);
			meterError.setMeterPosition(errStrs[5]);
			meterError.setMeterParent(errStrs[6]);
			
			hod2000BatchMeterErrorService.update(meterError);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String addMeterlineUtil(String line) {
		
		try {
			//表号,口径,端口号,波特率,表计型号,表计类型,地理位置,上级表号
			String[] lines = line.split(",");
			if (lines.length < 5) {
				return ("数据格式错误," + line);
			}
			String meterParentName="";
			if (5==lines.length) 
				meterParentName = "";//上级表号
			else
				meterParentName = lines[5];//上级表号
			hod2000Meter = new Hod2000Meter();
			
			String meterName = lines[0];
			String meterCaliber = lines[1];
			String meterBaudrate = lines[2];
			String typeName = lines[3];
			String meterPosition = lines[4];
			String meter_type_id="";
			String roomId = null;
			//判断表号是否已经存在
			List list = hod2000MeterService.findbyMeterName(meterName);
			if (list.size() > 0) {
				return ("该表号已经存在," + line);
			}
			hod2000Meter.setMeterName(meterName);
			//口径要查询系统参数表，是否有效caliber
			List<Hod2000Sysparameter> caliber=hod2000SysparameterService.findByHQL("FROM Hod2000Sysparameter where ptype=1 and pname="+meterCaliber);
			if(caliber.size()>0)
				hod2000Meter.setMeterCaliber(caliber.get(0).getPvalue());
			else
				return ("口径无效," + line);
			
			//波特率要查询系统参数表
			List<Hod2000Sysparameter> baudrate=hod2000SysparameterService.findByHQL("FROM Hod2000Sysparameter where ptype=4 and pname="+meterBaudrate);
			if(baudrate.size()>0)
				hod2000Meter.setMeterBaudrate(baudrate.get(0).getPvalue());
			else
				return ("通讯速率无效," + line);
			hod2000Meter.setMeterPositionName(meterPosition);
			String meterParent= null;
			if(meterParentName != null && !"".equals(meterParentName)){
				List m_paret = hod2000MeterService.findByHQL("from Hod2000Meter where meterName='" + meterParentName + "'");
				if(m_paret != null && m_paret.size() > 0){
					Hod2000Meter hod2000meter = (Hod2000Meter)m_paret.get(0);
					meterParent = hod2000meter.getMeterPosition();
				}else{
					return ("上级表号有误," + line);
				}
			}else{
				//查询热力公司，取第一个值
				List<Hod2000Meter> m_paret = hod2000MeterService.findByHQL("from Hod2000Meter where meterParent='0'");
				if(m_paret != null && m_paret.size() > 0){
					meterParent = m_paret.get(0).getMeterPosition();
				}else{
					return ("无相关上级表号," + line);
				}
			}
			hod2000Meter.setMeterParent(meterParent);
			//根据地理位置查询行政区域代码
			List params=hod2000SysparameterService.findByType(10);
			if(params.size()>0)
			{
				String addressCode=((Hod2000Sysparameter)params.get(0)).getPvalue();//系统默认地理位置
				String sql="";
				if(addressCode.length()==2)//默认地址为省份
				{
					//根据用户输入的地址查询市+区+街道办+小区+楼栋+单元+房间表
					sql="SELECT room_id,addressCode FROM regions WHERE address='"+meterPosition+"'";
				}
				else if(addressCode.length()==4)//默认地址为城市
				{
					//根据用户输入的地址查询区+街道办+小区+楼栋+单元+房间表
					sql="SELECT room_id,addressCode FROM citys WHERE address='"+meterPosition+"'";
				}
				else if(addressCode.length()==6)//默认地址为区
				{
					//根据用户输入的地址查询街道办+小区+楼栋+单元+房间表
					sql="SELECT room_id,addressCode FROM countys WHERE address='"+meterPosition+"'";
				}
				else if(addressCode.length()==9)//默认地址为街道办
				{
					//根据用户输入的地址查询小区+楼栋+单元+房间表
					sql="SELECT room_id,addressCode FROM client_address WHERE address='"+meterPosition+"'";
				}
				else
				{
					return ("默认地理位置错误," + line); 
				}
				List room_list=hod2000MeterService.findByNHQL(sql);
				if (room_list == null || room_list.size() == 0) {
					return ("地理位置" + meterPosition + "有误," + line);
				}else{
					Object[] object=(Object[]) room_list.get(0);
					roomId = object[0].toString();
					hod2000Meter.setHod2000Room((Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId)));
					hod2000Meter.setMeterPosition(addressCode+object[1].toString());
				}
			}
			else
			{
				return ("没有设置系统默认地理位置," + line); 
			}
			//根据地理位置编码查询是否唯一，只查询正在使用的表
			int meterPositionCount=hod2000MeterService.findByMeterPosition(hod2000Meter.getMeterPosition());
			if(meterPositionCount>0)
			{
				return ("该位置已存在表计信息," + line);
			}
			// 生成测量点号
			int meter_index = hod2000MeterService.getMeterIndex();
			hod2000Meter.setMeterIndex(meter_index);
			hod2000Meter.setMeterAble(1);
	//		// 表底数默认为0
	//		if (meterInit == null || meterInit.equals(""))
	//			hod2000Meter.setMeterInit(0d);
	//		else
	//		{
	//			hod2000Meter.setMeterInit(Arith.KWHToMJ(meterInit.toString()));
	//		}
			hod2000Meter.setValveStatus(1);
			hod2000Meter.setBatteryStatus(0);
			hod2000Meter.setIsOnline(2);
			hod2000Meter.setMeterStyle(1);
			hod2000Meter.setEepromStatus(0);
			hod2000Meter.setFlowsensorStatus(0);
			hod2000Meter.setTepdownStatus(0);
			hod2000Meter.setTepupStatus(0);
			hod2000Meter.setHod2000Concentrator(null);
			List<Hod2000MeterType> meterType=hod2000MeterTypeService.findByHQL("FROM Hod2000MeterType where typeName='"+typeName+"'");
			if(meterType.size()>0)
			{
				hod2000Meter.setHod2000MeterType(meterType.get(0));
				meter_type_id=meterType.get(0).getTypeIndex().toString();
			}
			else
				return ("表计型号" + typeName + "不存在," + line);
			// 查询所有的在线的集中器表信息
			List con_list = concentratorService.findIsonline("1");
			if (con_list.size() >= 1) {
				
				//根据最后关联的集中器标识重新对结果进行排序,并返回Hod2000Concentrator列表
				ArrayList<Hod2000Concentrator> temp = conSort(con_list);
				
				//把点名下发协议插入到点名表中
				saveCalldownInfo(temp,meterName);
				
				long start_time = System.currentTimeMillis();
				A: while (true) {
					long end_time = System.currentTimeMillis();
					List meter_call_list = hod2000MeterCallDownInfoService
							.findByHQL("from Hod2000MeterCallDownInfo where meterName='"
									+ meterName + "'");
					
					if (end_time - start_time >= ConstantValue.timeout) { // 超时
						for (int j = 0; j < meter_call_list.size(); j++) {
							// 删除点名表里面对应表的数据
							hod2000MeterCallDownInfoService
									.delete((Hod2000MeterCallDownInfo) meter_call_list.get(j));
						}
						return ("添加表计信息失败[关联集中器失败<超时>]," + line);
					}
					if (meter_call_list.size() >= 1) {
						// 计算hod2000meterCall.getState() == 2 出现的次数
						int stataNUm = 0;
						for (int j = 0; j < meter_call_list.size(); j++) {
							Hod2000MeterCallDownInfo hod2000meterCall = (Hod2000MeterCallDownInfo) meter_call_list
									.get(j);
							if (hod2000meterCall.getState() == 1) { // 集中器已回复且该表计在下发集中器中
								// 判断集中器中表计数量
								List meter_List = hod2000MeterService.findByHQL("from Hod2000Meter where hod2000Concentrator.conNumber='"
												+ hod2000meterCall.getConNum()+ "' ORDER BY meterIndex DESC");
								
								if (meter_List.size() < 1024 && meter_List != null) {
									hod2000Meter.setHod2000Concentrator(concentratorService.findByConNum(hod2000meterCall.getConNum()));
									//判断表计所选的表计类型是否已关联该集中器
									boolean relateConFlag=false;
									String[] typeIndexs=hod2000Meter.getHod2000Concentrator().getTypeIndexs().split(",");
									String type=""+hod2000Meter.getHod2000MeterType().getTypeIndex();
									for (String str : typeIndexs) {
										if(str.equals(type))
										{
											relateConFlag=true;
											break;
										}
									}
									if(!relateConFlag)
									{
										return ("该表计型号没有关联到集中器中失败," + line);
									}else{
										//集中器位置与表计位置的匹配，截止到街道办的行政代码是否一致
										String cons = hod2000Meter.getHod2000Concentrator().getConAddress();
										if(cons!=null&&cons.length()>9)
										{
											cons=cons.substring(0,9);
											String meters=hod2000Meter.getMeterPosition().substring(0,9);
											if(!cons.equals(meters))
											{
												return ("集中器地址与表计地址匹配失败," + line);
											}
											else
											{
												//下发终端热能表配置参数成功之后才添加表计
												int flag=initParams(hod2000meterCall.getConNum(), "0100"+Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4)+Util.getAddress(hod2000Meter.getMeterName())+Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2)+Util.toByte(hod2000Meter.getMeterBaudrate()), ConstantValue.meter);
												if(2==flag)
												{
													return ("终端热能表配置参数设置失败," + line);
												}
												else if(0==flag)
												{
													return ("终端热能表配置参数设置超时," + line);
												}
												else if(1==flag)
												{
													hod2000Meter.setMeterInit(Double.valueOf(hod2000meterCall.getResultContent()).doubleValue());
													hod2000MeterService.save(hod2000Meter);
//													msg.setMsg1("添加表计信息成功!");
													OperatorLog.addOperatorLog(meterName+"表计信息添加");
													
													//发送表计信息
													StringBuilder sb = new StringBuilder();
													sb.append("meter_name=").append(meterName).append("&");
													sb.append("meter_index=").append(Integer.toString(meter_index)).append("&");
													if(hod2000Meter.getMeterCaliber() == null){
														sb.append("meter_caliber=").append("").append("&");
													}else{
														sb.append("meter_caliber=").append(hod2000Meter.getMeterCaliber()).append("&");
													}
													sb.append("type_index=").append(meter_type_id).append("&");
													sb.append("meter_init=").append(Double.valueOf(hod2000meterCall.getResultContent()).doubleValue()).append("&");
													if(hod2000Meter.getMeterPort() == null){
														sb.append("meter_port=").append("").append("&");
													}else{
														sb.append("meter_port=").append(hod2000Meter.getMeterPort()).append("&");
													}
													if(hod2000Meter.getMeterBaudrate() == null){
														sb.append("meter_baudrate=").append("").append("&");
													}else{
														sb.append("meter_baudrate=").append(hod2000Meter.getMeterBaudrate()).append("&");
													}
													sb.append("meter_parent=").append(meterParent).append("&");
													if(hod2000Meter.getMeterPosition() == null){
														sb.append("meter_position=").append("").append("&");
													}else{
														sb.append("meter_position=").append(hod2000Meter.getMeterPosition()).append("&");
													}
													if(hod2000Meter.getMeterPositionName() == null){
														sb.append("meter_position_name=").append("").append("&");
													}else{
														sb.append("meter_position_name=").append(hod2000Meter.getMeterPositionName()).append("&");
													}
													sb.append("con_number=").append(hod2000meterCall.getConNum()).append("&");
													sb.append("room_id=").append(roomId).append("&");
													String sign = MD5.getMd5(sb.toString() + "key").toUpperCase();
													
													System.out.println(sb.toString());
													String http="http://"+ParseProperties.getProperties().getProperty("serverIP")+"/HOD-2000/hodMeter!httpClientMeterInterface.do";
													//HttpPost httpPost = new HttpPost("http://200.10.10.120:8080/HOD-2000/hodMeter!httpClientMeterInterface.do");
													HttpPost httpPost = new HttpPost(http);
													//发送表计信息
													//HttpPost httpPost = new HttpPost("http://200.10.10.172:8080/HOD-2000/hodMeter!httpClientMeterInterface.do");
													List <NameValuePair> nvps = new ArrayList <NameValuePair>();
													nvps.add(new BasicNameValuePair("meter_name", meterName));
													nvps.add(new BasicNameValuePair("meter_index", Integer.toString(meter_index)));
													nvps.add(new BasicNameValuePair("meter_caliber", hod2000Meter.getMeterCaliber()));
													nvps.add(new BasicNameValuePair("type_index", meter_type_id));
													nvps.add(new BasicNameValuePair("meter_init", hod2000meterCall.getResultContent()));
													nvps.add(new BasicNameValuePair("meter_port", hod2000Meter.getMeterPort()));
													nvps.add(new BasicNameValuePair("meter_baudrate", hod2000Meter.getMeterBaudrate()));
													nvps.add(new BasicNameValuePair("meter_parent", meterParent));
													nvps.add(new BasicNameValuePair("meter_position", hod2000Meter.getMeterPosition()));
													nvps.add(new BasicNameValuePair("meter_position_name", hod2000Meter.getMeterPositionName()));
													nvps.add(new BasicNameValuePair("con_number", hod2000meterCall.getConNum()));
													nvps.add(new BasicNameValuePair("room_id", roomId));
													nvps.add(new BasicNameValuePair("sign", sign));
													httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
													CloseableHttpResponse response2 = httpclient.execute(httpPost);
	
													try {
														if(response2.getStatusLine().getStatusCode() == 200){
															 System.out.println("表计信息发送成功!");
														}
													} finally {
													    response2.close();
													}
													
												}
											}
										}
										else
										{
											return ("集中器的地址是否已经添加," + line);
										}
									}
									
								}else{
									return ("添加表计信息失败->每个集中器最多只能关联1024个表计," + line);
								}
								
								// 把集中器之前最后出现的位置置为0
								Hod2000Concentrator hod2000Concentrator = temp.get(0);
								concentratorService.updateBySql("UPDATE Hod2000Concentrator set conLastPosition=0 where conId="
												+ hod2000Concentrator.getConId());
								// 把集中器现在最后出现的位置置为1
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
												+ hod2000meterCall.getConNum());
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
										+ hod2000meterCall.getConNum());
								for (int d = 0; d < meter_call_list.size(); d++) {
									// 删除点名表里面对应表的数据
									hod2000MeterCallDownInfoService
											.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
								}
								return null;
							}
							
							if (hod2000meterCall.getState() == 0) {// 存在有没有回复时，休眠1000MS，等待表计回复
								Thread.sleep(1000);
								continue A;
							}
							
							if (hod2000meterCall.getState() == 2) {// 表计已回复且该表计不在下发集中器中
								stataNUm++;
								if (stataNUm == meter_call_list.size()) {
									for (int d = 0; d < meter_call_list.size(); d++) {
										// 删除点名表里面对应表的数据
										hod2000MeterCallDownInfoService
												.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
									}
									return ("添加表计信息失败[关联集中器失败<集中器中不存在该表->请检查表计和集中器是否连接>]," + line);
								}
							}
						}
					}
	
				}
			}
		} catch (FileNotFoundException e) {
			out.write("{success:false}");
			log.error("FileNotFoundException : " + e.getMessage());
		}catch (Exception e) {
			out.write("{success:false}");
			e.printStackTrace();
			log.error("Exception : " + e.getMessage());
		}
		
		return ("添加表计信息失败," + line);
	}
	
	private void saveErrorMeter(String errStr){
		try {
			String[] errStrs = errStr.split(",");
			String meterParentName="";
			if (6==errStrs.length) 
				meterParentName = "";//上级表号
			else
				meterParentName = errStrs[6];//上级表号
			//错误信息,表号,口径,波特率,表计型号,表计类型,地理位置,上级表号
			Hod2000BatchMeterError meterError = new Hod2000BatchMeterError();
			meterError.setErrorMsg(errStrs[0]);
			meterError.setMeterName(errStrs[1]);
			meterError.setMeterCaliber(errStrs[2]);
			meterError.setMeterBaudrate(errStrs[3]);
			meterError.setTypeName(errStrs[4]);
			meterError.setMeterPosition(errStrs[5]);
			meterError.setMeterParent(meterParentName);
			//判断错误数据是否已存在，存在不插入
			if(hod2000BatchMeterErrorService.findByCardNo(errStrs[1]) == 0 ){ 
				hod2000BatchMeterErrorService.save(meterError);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
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

	public IHod2000ConcentratorService getConcentratorService() {
		return concentratorService;
	}

	public void setConcentratorService(
			IHod2000ConcentratorService concentratorService) {
		this.concentratorService = concentratorService;
	}

	public IHod2000DownInfoCacheService getHod2000DownInfoCacheService() {
		return hod2000DownInfoCacheService;
	}

	public void setHod2000DownInfoCacheService(
			IHod2000DownInfoCacheService hod2000DownInfoCacheService) {
		this.hod2000DownInfoCacheService = hod2000DownInfoCacheService;
	}

	public void setHod2000SysparameterService(
			IHod2000SysparameterService hod2000SysparameterService) {
		this.hod2000SysparameterService = hod2000SysparameterService;
	}

	public void setHod2000MeterTypeService(
			IHod2000MeterTypeService hod2000MeterTypeService) {
		this.hod2000MeterTypeService = hod2000MeterTypeService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}

	public void setHod2000MeterCallDownInfoService(
			IHod2000MeterCallDownInfoService hod2000MeterCallDownInfoService) {
		this.hod2000MeterCallDownInfoService = hod2000MeterCallDownInfoService;
	}

	public void setCacheService(IHod2000ParaDownInfoCacheService cacheService) {
		this.cacheService = cacheService;
	}

	public IHod2000BatchMeterErrorService getHod2000BatchMeterErrorService() {
		return hod2000BatchMeterErrorService;
	}

	public void setHod2000BatchMeterErrorService(
			IHod2000BatchMeterErrorService hod2000BatchMeterErrorService) {
		this.hod2000BatchMeterErrorService = hod2000BatchMeterErrorService;
	}

	public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	
}
