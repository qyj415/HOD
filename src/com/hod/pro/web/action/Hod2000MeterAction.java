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
 * Hod2000MeterAction ��ƹ���
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
	 * ���������б�ת����������ҳ��
	 * 
	 * @return
	 */
	public String toAdd() {
		try {
			request = ServletActionContext.getRequest();
			String parent_position=request.getParameter("parent_position");
			//�ϼ�������Ҫ�����ϼ�������������в�ѯ
			if(parent_position!=null&&!"".equals(parent_position))
			{
				//String parent_positionName=hod2000MeterService.getParentName(parent_position);
				String parent_positionName=hod2000MeterService.findNameByMeterPosition(parent_position);
				request.setAttribute("parent_position", parent_position);
				request.setAttribute("parent_positionName", parent_positionName);
			}
			// �������
			DetachedCriteria meterType = DetachedCriteria
					.forClass(Hod2000MeterType.class);
			request.setAttribute("meterType", hod2000MeterTypeService
					.findByCriteria(meterType));
			// �ھ�
			request.setAttribute("caliber", hod2000SysparameterService
					.findByType(1));
			// �˿ں�
			request.setAttribute("com", hod2000SysparameterService.findByType(3));
			// ͨ������
			request.setAttribute("speed", hod2000SysparameterService.findByType(4));
			return "add";
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->toAdd:", e);
			msg.setError1(e.toString());
			msg.setLink(0, "<a href=\"" + request.getContextPath()
					+ "/hod2000Meter!toSelect.do\">�����б�</a>");
			request.setAttribute("message", msg);
			return "message";
		}
	}

	/**
	 * ��ӱ����Ϣ ��Ų����ظ�
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
			// ��֤��ŵ�Ψһ��
			List list = hod2000MeterService.findbyMeterName(meterName);
			if (list.size() > 0) {
				msg.setMsg1("������ͬ�ı��" + meterName + "�����޸ĺ����������!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">�������</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//���ݵ���λ�ñ����ѯ�Ƿ�Ψһ��ֻ��ѯ����ʹ�õı�
			int meterPositionCount=hod2000MeterService.findByMeterPosition(hod2000Meter.getMeterPosition());
			if(meterPositionCount>0)
			{
				msg.setMsg1("��λ���Ѵ��ڱ����Ϣ��������ѡ�����λ��!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">�������</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			else
			{
				//����ǻ��ͱ�
				if (roomId != null && !roomId.equals(""))
				{
					hod2000Meter.setHod2000Room((Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId)));
				}
				else
				{
					hod2000Meter.setHod2000Room(null);
				}
			}
			// ���ɲ������
			int meter_index = hod2000MeterService.getMeterIndex();
			hod2000Meter.setMeterIndex(meter_index);
			hod2000Meter.setMeterAble(1);
			// �����Ĭ��Ϊ0
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
			// ��ѯ���е����ߵļ���������Ϣ
			List con_list = concentratorService.findIsonline("1");

			if (con_list.size() >= 1) {
				
				//�����������ļ�������ʶ���¶Խ����������,������Hod2000Concentrator�б�
				ArrayList<Hod2000Concentrator> temp = conSort(con_list);
				
				//�ѵ����·�Э����뵽��������
				saveCalldownInfo(temp,meterName);
				
				long start_time = System.currentTimeMillis();
				A: while (true) {
					long end_time = System.currentTimeMillis();
					List meter_call_list = hod2000MeterCallDownInfoService
							.findByHQL("from Hod2000MeterCallDownInfo where meterName='"
									+ meterName + "'");
					
					if (end_time - start_time >= ConstantValue.timeout) { // ��ʱ
						for (int j = 0; j < meter_call_list.size(); j++) {
							// ɾ�������������Ӧ�������
							hod2000MeterCallDownInfoService
									.delete((Hod2000MeterCallDownInfo) meter_call_list.get(j));
						}
						msg.setError1("��ӱ����Ϣʧ��[����������ʧ��<��ʱ>]");
						break A;
					}
					if (meter_call_list.size() >= 1) {
						// ����hod2000meterCall.getState() == 2 ���ֵĴ���
						int stataNUm = 0;
						for (int j = 0; j < meter_call_list.size(); j++) {
							Hod2000MeterCallDownInfo hod2000meterCall = (Hod2000MeterCallDownInfo) meter_call_list
									.get(j);
							
							if (hod2000meterCall.getState() == 1) { // �������ѻظ��Ҹñ�����·���������
								// �жϼ������б������
								List meter_List = hod2000MeterService.findByHQL("from Hod2000Meter where hod2000Concentrator.conNumber='"
												+ hod2000meterCall.getConNum()+ "' ORDER BY meterIndex DESC");
								
								if (meter_List.size() < 1024 && meter_List != null) {
									hod2000Meter.setHod2000Concentrator(concentratorService.findByConNum(hod2000meterCall.getConNum()));
									//�жϱ����ѡ�ı�������Ƿ��ѹ����ü�����
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
										msg.setError1("�ñ���ͺ�û�й������������У���ӱ��ʧ�ܣ������ڼ�����"+hod2000meterCall.getConNum()+"�й����ñ���ͺ�!");
										msg.setLink(0, "<a href=\"" + request.getContextPath()
												+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">��ӱ��</a>");
										msg.setJump(false);
										request.setAttribute("message", msg);
									}else{
										//������λ������λ�õ�ƥ�䣬��ֹ���ֵ�������������Ƿ�һ��
										String cons=hod2000Meter.getHod2000Concentrator().getConAddress();
										if(cons!=null&&cons.length()>9)
										{
											cons=cons.substring(0,9);
											String meters=hod2000Meter.getMeterPosition().substring(0,9);
											if(!cons.equals(meters))
											{
												msg.setError1("��������ַ���Ƶ�ַƥ��ʧ�ܣ���ȷ�ϱ�Ƶ�ַ��д�Ƿ���ȷ!");
												msg.setLink(0, "<a href=\"" + request.getContextPath()
														+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">��ӱ��</a>");
												msg.setJump(false);
												request.setAttribute("message", msg);
											}
											else
											{
												//�·��ն����ܱ����ò����ɹ�֮�����ӱ��
												int flag=initParams(hod2000meterCall.getConNum(), "0100"+Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4)+Util.getAddress(hod2000Meter.getMeterName())+Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2)+Util.toByte(hod2000Meter.getMeterBaudrate()), ConstantValue.meter);
												if(2==flag)
												{
													msg.setError1("�ն����ܱ����ò�������ʧ�ܣ���ӱ��ʧ��!");
													msg.setLink(0, "<a href=\"" + request.getContextPath()
															+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">��ӱ��</a>");
													msg.setJump(false);
													request.setAttribute("message", msg);
												}
												else if(0==flag)
												{
													msg.setError1("�ն����ܱ����ò������ó�ʱ����ӱ��ʧ��!");
													msg.setLink(0, "<a href=\"" + request.getContextPath()
															+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">��ӱ��</a>");
													msg.setJump(false);
													request.setAttribute("message", msg);
												}
												else if(1==flag)
												{
													if(hod2000meterCall.getResultContent() == null || "".equals(hod2000meterCall.getResultContent())){
														msg.setMsg1("�����δ����!");
														break;
													}else{
														meter_init = Double.valueOf(hod2000meterCall.getResultContent()).doubleValue();
													}
													
													hod2000Meter.setMeterInit(meter_init);
													hod2000MeterService.save(hod2000Meter);
													msg.setMsg1("��ӱ����Ϣ�ɹ�!");
													OperatorLog.addOperatorLog(meterName+"�����Ϣ���");
													
													//���ͱ����Ϣ
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
													
													//���ͱ����Ϣ
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
															 System.out.println("�����Ϣ���ͳɹ�!");
														}
													} finally {
													    response2.close();
													}
													
												}
											}
										}
										else
										{
											msg.setError1("����ȷ��"+hod2000Meter.getHod2000Concentrator().getConNumber()+"�������ĵ�ַ�Ƿ��Ѿ����!");
											msg.setLink(0, "<a href=\"" + request.getContextPath()
													+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">��ӱ��</a>");
											msg.setJump(false);
											request.setAttribute("message", msg);
										}
									}
									
								}else{
									msg.setError1("��ӱ����Ϣʧ�ܣ�ÿ�����������ֻ�ܹ���1024����ƣ�");
								}
								
								// �Ѽ�����֮ǰ�����ֵ�λ����Ϊ0
								Hod2000Concentrator hod2000Concentrator = temp.get(0);
								concentratorService.updateBySql("UPDATE Hod2000Concentrator set conLastPosition=0 where conId="
												+ hod2000Concentrator.getConId());
								// �Ѽ��������������ֵ�λ����Ϊ1
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
												+ hod2000meterCall.getConNum());
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
										+ hod2000meterCall.getConNum());
								for (int d = 0; d < meter_call_list.size(); d++) {
									// ɾ�������������Ӧ�������
									hod2000MeterCallDownInfoService
											.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
								}
								msg.setLink(0, "<a href=\"" + request.getContextPath()
										+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">�������</a>");
								msg.setJump(false);
								request.setAttribute("message", msg);
								return "message";
							}
							if (hod2000meterCall.getState() == 0) {// ������û�лظ�ʱ������1000MS���ȴ���ƻظ�
								Thread.sleep(1000);
								continue A;
							}
							if (hod2000meterCall.getState() == 2 || hod2000meterCall.getSendFlag() == 2) {// ����ѻظ��Ҹñ�Ʋ����·���������
								stataNUm++;
								if (stataNUm == meter_call_list.size()) {
									msg.setError1("��ӱ����Ϣʧ��[����������ʧ��<�������в����ڸñ������ƺͼ������Ƿ�����>]");
									for (int d = 0; d < meter_call_list.size(); d++) {
										// ɾ�������������Ӧ�������
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
				msg.setError1("��ӱ����Ϣʧ��[����������ʧ��<��ȡ�����������б�ʧ��>]");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterAction-->doSave:", e);
			msg.setError1(e.toString());
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Meter!toAdd.do?parent_position="+meterParent+"\">�������</a>");
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
	 * ������˾�����
	 */
	public void doSaveCompany()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String meterPositionName=request.getParameter("meterPositionName");
			//�ж�������˾�����Ƿ��ظ�
			if(hod2000MeterService.findByPositionName(meterPositionName)>0)
			{
				out.write("{success:false,msg:'�Ѵ��ڸ�������˾����!'}");
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
				out.write("{success:false,msg:'���������˾ʧ�ܣ�������˾����Ҳ�ﵽ�������!'}");
				return;
			}
			hod2000Meter.setHod2000Concentrator(null);
			hod2000Meter.setHod2000MeterType(null);
			hod2000Meter.setHod2000Room(null);
			hod2000Meter.setMeterAble(0);
			hod2000Meter.setMeterStyle(null);
			hod2000MeterService.save(hod2000Meter);
			OperatorLog.addOperatorLog("���������˾");
			out.write("{success:true}");
		} catch (Exception e) {
			log.error(e);
			out.write("{success:false,msg:'���������˾ʧ��!'}");
		}
	}
	
	
	/**
	 * �ѵ����·�Э����뵽��������
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
			log.info("����Э�飺"+sendContent);
			try {
				hod2000MeterCallDownInfoService.save(hmcd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �����������ļ�������ʶ���¶Խ����������,������Hod2000Concentrator�б�
	 */
	public ArrayList<Hod2000Concentrator> conSort(List con_list){
		ArrayList<Hod2000Concentrator> temp = new ArrayList<Hod2000Concentrator>();
		boolean isFind = false;
		int findIndex = 0;
		for(int i=0;i<con_list.size();i++){
			Hod2000Concentrator hod2000Concentrator = (Hod2000Concentrator) con_list.get(i);
			if(isFind){
				if(i == con_list.size()-1){//˵��list�Ѿ����������һ����
					if(findIndex == 0){//˵���������ļ�������ʶΪ��һ��
						break;
					}else{
						for(int j=0;j<findIndex;j++){
							//���������ļ�����ǰ���Ԫ����ӵ�����ĩ��
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
				if(position == 1){ //�ҵ��ϴ��������ļ�������ʶ
					isFind = true;
					findIndex = i;
					temp.add(hod2000Concentrator);
				}
				if(i == con_list.size()-1 && isFind == false){//OMG��ConLastPosition==1 ��δ����
					for(int m=0;m<con_list.size();m++){//������Ԫ�ر�����Temp��
						temp.add((Hod2000Concentrator) con_list.get(m));
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * �ն����ܱ��ѯ�·�
	 * @param conNum ���������
	 * @param data ������
	 * @param identificationId ��ʶ����
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
			log.info("���ģ�"+cache.getSendContent());
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
				if(1==cache.getState())//�ɹ�
				{
					flag=1;
					break;
				}
				if(2==cache.getState())//ʧ��
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
					if(0==cache.getState())//��ʱ
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
			log.info("����Э�飺"+callDownInfo.getSendContent());
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				callDownInfo=(Hod2000MeterCallDownInfo) hod2000MeterCallDownInfoService.findById(callDownInfo.getCallId());
				if(1==callDownInfo.getState())//�ɹ�
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
				if(2==callDownInfo.getState())//ʧ��
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					callDownInfo=(Hod2000MeterCallDownInfo) hod2000MeterCallDownInfoService.findById(callDownInfo.getCallId());
					if(0==callDownInfo.getState())//��ʱ
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
	 * ���÷��ű��
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
						//��ѯ�÷��ű���Ƿ��ظ�
						DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Meter.class);
						dc.add(Restrictions.eq("valveId", valveId));
						dataList=hod2000MeterService.findByCriteria(dc);
						if(dataList.size()>0)
						{
							out.write("{success:false,msg:'���ű�����ظ�!'}");
						}
						else
						{
							hod2000Meter.setValveId(valveId);
							hod2000MeterService.update(hod2000Meter);
							OperatorLog.addOperatorLog("���÷��ű��"+valveId);
							out.write("{success:true,msg:'���÷��ű�ųɹ�!'}");
						}
					}
					else
					{
						out.write("{success:false,msg:'���ű��û�н��б��!'}");
					}
				}
				else
				{
					hod2000Meter.setValveId("");
					hod2000MeterService.update(hod2000Meter);
					OperatorLog.addOperatorLog("������ű��"+valveId);
					out.write("{success:true,msg:'������ű�ųɹ�!'}");
					//out.write("{success:false,msg:'���ű�Ų���Ϊ��!'}");
				}
			}
			else
			{
				out.write("{success:false,msg:'��Ʊ�Ų���Ϊ��!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000MeterAction->setValveId:",e);
			out.write("{success:false}");
		}
	}
	
	/**
	 * ���ſ���
	 */
	public void valveControl()
	{
		try {
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String valveStatus=request.getParameter("valveStatus");
			String meterId=request.getParameter("meterId");
			String data="";
			if(valveStatus.equals("0"))//�ط���
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
					out.write("{success:false,msg:'"+hod2000Meter.getHod2000Concentrator().getConNumber()+"������������!'}");
					return;
				}
				if(hod2000Meter.getValveId()==null||"".equals(hod2000Meter.getValveId()))
				{
					out.write("{success:false,msg:'�ñ��û�����÷��ű�ţ��������÷��ű��!'}");
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
					log.info("���ſ��ƣ�"+cache.getSendContent());
					long start_time = System.currentTimeMillis();
					while (true) {
						long end_time = System.currentTimeMillis();
						cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
						if(1==cache.getState())//�ɹ�
						{
							out.write("{success:true,msg:'���Ų����ɹ�!'}");
							OperatorLog.addOperatorLog("���Ų���");
							break;
						}
						if(2==cache.getState())//ʧ��
						{
							out.write("{success:false,msg:'���Ų���ʧ��!'}");
							break;
						}
						if (end_time - start_time >= ConstantValue.timeout) { 
							cache=(Hod2000ParaDownInfoCache) cacheService.findById(cache.getDownId());
							if(0==cache.getState())//��ʱ
							{
								out.write("{success:false,msg:'���ſ�������ʱ!'}");
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
					+ "/hod2000Meter!toSelect.do\">�����б�</a>");
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
			String changeValue=request.getParameter("changeValue");//�ɱ��ۼ���������λKWh
			hod2000Meter=(Hod2000Meter) hod2000MeterService.findById(meterId);
			if(meterName==null||"".equals(meterName))
			{
				msg.setError1("��Ų���Ϊ��!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//�жϱ���Ƿ����
			if(meterName.equals(hod2000Meter.getMeterName()))
			{
				msg.setError1("���û�н����޸�!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				request.setAttribute("message", msg);
				msg.setJump(false);
				return "message";
			}
			//�жϼ������Ƿ�����
			if(2==hod2000Meter.getHod2000Concentrator().getConIsonline())
			{
				msg.setError1(hod2000Meter.getHod2000Concentrator().getConNumber()+"������������!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			//�Ծɱ��������е���
			flag=meterCall(hod2000Meter.getHod2000Concentrator().getConNumber(),meterName, Util.getAddress(meterName)+Util.getAddress(hod2000Meter.getHod2000MeterType().getCompanyNum()), ConstantValue.callCon);
			if(0==flag)
			{
				msg.setError1("��ӱ����Ϣʧ��[����������ʧ��<��ʱ>]");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			if(2==flag)
			{
				msg.setError1("��ӱ����Ϣʧ��[����������ʧ��<�������в����ڸñ������ƺͼ������Ƿ�����>]!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				request.setAttribute("message", msg);
				return "message";
			}
			// �����Ĭ��Ϊ0
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
			//�·��ն����ܱ����ò����ɹ�֮�����ӱ��
			flag=initParams(hod2000Meter.getHod2000Concentrator().getConNumber(), "0100"+Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4)+Util.getAddress(meterName)+Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2)+Util.toByte(hod2000Meter.getMeterBaudrate()), ConstantValue.meter);
			if(2==flag)
			{
				msg.setError1("�ն����ܱ����ò�������ʧ�ܣ��������ʧ��!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			else if(0==flag)
			{
				msg.setError1("�ն����ܱ����ò������ó�ʱ���������ʧ��!");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/hod2000Meter!toSelect.do\">���ر�ƹ���</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			hod2000MeterService.save(meter);
			//�޸ľɱ��
			if(changeValue!=null&&!"".equals(changeValue))
			{
				hod2000Meter.setChangeValue(changeValue);
			}
			hod2000Meter.setMeterAble(0);
			//hod2000Meter.setChangeMeterTime(new Date());
			hod2000Meter.setChangeMeterTime(NetworkTimeUtil.getDate());
			hod2000MeterService.update(hod2000Meter);
			msg.setMsg1("������Ʋ����ɹ�!");
			OperatorLog.addOperatorLog(hod2000Meter.getMeterName()+"��ű��Ϊ"+meterName);
		} catch (Exception e) {
			msg.setError1("������Ϣ��" + e.toString());
			log.error("Hod2000MeterAction-->doUpdate:", e);
			msg.setJump(false);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Meter!toSelect.do\">�����б�</a>");
		request.setAttribute("message", msg);
		return "message";
	}

	/**
	 * ���״̬��ѯ ����json
	 */
	public void doMonitor() {
		try {
			OperatorLog.addOperatorLog("���״̬��ѯ");
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//��������ҳ��
			int pageSize=Integer.parseInt(request.getParameter("rows"));//��������ÿҳ��¼��
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
	 * ���״̬��ѯ ��ӡ�ã���json
	 */
	public String monitorPrint() {
		try {
			OperatorLog.addOperatorLog("���״̬��ӡԤ��");
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
	
	//��ѯ����ͺţ��ھ�����Ϣת����ƹ���ҳ��
	public String toSelect()
	{
		request = ServletActionContext.getRequest();
		DetachedCriteria meterTypes = DetachedCriteria.forClass(Hod2000MeterType.class);// �������
		request.setAttribute("meterTypes", hod2000MeterTypeService.findByCriteria(meterTypes));
		request.setAttribute("caliber", hod2000SysparameterService.findByType(1));// �ھ�
		return "success";
	}

	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("�����Ϣ��ѯ");
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//��������ҳ��
			int pageSize=Integer.parseInt(request.getParameter("rows"));//��������ÿҳ��¼��
			String meterName = request.getParameter("meterName");// ���
			String terminalNumber = request.getParameter("terminalId");// ���������
			String meterType = request.getParameter("meter_type");// ����ͺ�
			String meterCaliber = request.getParameter("meter_caliber");// �ھ�
			String meterStyle=request.getParameter("meterStyle");//�������
			String meterParent=request.getParameter("meterParent");//�ϼ��������������
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
			OperatorLog.addOperatorLog("�����Ϣ��ӡԤ��");
			request = ServletActionContext.getRequest();
			String meterName = request.getParameter("meterName");// ���
			String terminalNumber = request.getParameter("terminalId");// ���������
			String meterType = request.getParameter("meter_type");// ����ͺ�
			String meterCaliber = request.getParameter("meter_caliber");// �ھ�
			String meterStyle=request.getParameter("meterStyle");//�������
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
					.append("[{id:'0',text:'���н����ǻ���Դ����ϵͳ',meter_position:'',expanded:true,children:[");
			// ��ѯmeter_parent=0�ı���Ϣ
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
				//buffer.append("[{id:'0',text:'��³��н����ǻ���Դ����ϵͳ',meter_position:'',expanded:true,children:[");
				buffer.append("[");
				List list=hod2000MeterService.findByNHQL("select meter_id,meter_position_name,meter_position,meter_parent,meter_name,meter_style from hod2000_meter where meter_parent='"+meterParent+"' and meter_able=1");
				for (int i = 0; i < list.size(); i++) {
					objects = (Object[]) list.get(i);
					buffer.append("{id:'" + objects[0] + "',");
					if(objects[5].equals(1))
						buffer.append("text:'" + objects[1] + "���ñ�',");
					else if(objects[5].equals(2))
						buffer.append("text:'" + objects[1] + "¥����',");
					else
						buffer.append("text:'" + objects[1] + "����վ',");
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
	 * ��ƹ����ͼģ��
	 * ����¥��id��ѯ��¥���µ����б��
	 */
	public void getMeter()
	{
		try {
			OperatorLog.addOperatorLog("��ƹ����ͼҳ���и���¥����Ų�ѯ�����Ϣ");
			request = ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			String buildingId=request.getParameter("terminal");
			//����¥��������Ϣ
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
				out.write("{success:false,msg:'¥����Ų���Ϊ��!'}");
			}
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	//�༭��Ƶ�ַ
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
			//���ݵ���λ�ñ����ѯ�Ƿ�Ψһ��ֻ��ѯ����ʹ�õı�
			int meterPositionCount=hod2000MeterService.findByMeterPosition(meterPosition);
			if(meterPositionCount>0)
			{
				out.write("{success:false,msg:'��λ���Ѵ��ڱ����Ϣ��������ѡ�����λ��!'}");
				return;
			}
			//������λ������λ�õ�ƥ�䣬��ֹ���ֵ�������������Ƿ�һ��
			String cons=hod2000Meter.getHod2000Concentrator().getConAddress();
			if(cons!=null&&cons.length()>9)
			{
				cons=cons.substring(0,9);
				String meters=meterPosition.substring(0,9);
				if(!cons.equals(meters))
				{
					out.write("{success:false,msg:'��������ַ���Ƶ�ַƥ��ʧ�ܣ���ȷ�ϱ�Ƶ�ַ��д�Ƿ���ȷ!'}");
					return;
				}
			}
			else
			{
				out.write("{success:false,msg:'����ȷ��"+hod2000Meter.getHod2000Concentrator().getConNumber()+"�������ĵ�ַ�Ƿ��Ѿ����!'}");
				return;
			}
			//����ǻ��ͱ�
			if (roomId != null && !roomId.equals(""))
			{
				if(hod2000Meter.getMeterStyle()>1)
				{
					out.write("{success:false,msg:'¥�������վ�ĵ�ַ����Ϊ����!'}");
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
			OperatorLog.addOperatorLog("�༭"+hod2000Meter.getMeterName()+"��Ƶĵ�ַ��Ϣ");
			out.write("{success:true,msg:'�༭��Ƶ�ַ��Ϣ�ɹ�!',meterPositionName:'"+meterPositionName+"'}");
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	/**
	 * �����ӱ�
	 */
	public void batchFileUp(){
		
		ArrayList<String> err_al = new ArrayList<String>();//�����������
		
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
		    	//����������ݡ��������µ��������ݱ�
		    	Hod2000BatchMeterError meterError = (Hod2000BatchMeterError)hod2000BatchMeterErrorService.findById(Integer.parseInt(batchMeterId));
		    	updatehod2000MeterClientError(lineRs,meterError);
		    	//errData:��������,�û�����,�û��Ա�,֤������,֤������,��ϵ��ַ,��ϵ�绰,�۸񷽰�, ����λ��,��ע��Ϣ
				out.write("{success:false,errData:'" + lineRs + "'}");
		    }else{
		    	//������ɹ�(������Ϣ�Ѹ���)��ɾ��������Ϣ���еĶ�Ӧ����
		    	hod2000BatchMeterErrorService.deleteByKey(Integer.parseInt(batchMeterId));
		    	out.write("{success:true}");
		    }
			
		} catch (Exception e) {
			out.write("{success:false}");
		}
	}
	
	public void updatehod2000MeterClientError(String errLine,Hod2000BatchMeterError meterError){
		try {
			//����������ݡ��������µ��������ݱ�
			String[] errStrs = errLine.split(",");
			if(errStrs.length < 7){
				String[] temp_errs = new String[10];
				//��ֹ�����±�Խ��
				System.arraycopy(errStrs, 0, temp_errs, 0, errStrs.length);
				errStrs = temp_errs;
			}
			//��������,���,�ھ�,������,����ͺ�,�������,����λ��,�ϼ����
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
			//���,�ھ�,�˿ں�,������,����ͺ�,�������,����λ��,�ϼ����
			String[] lines = line.split(",");
			if (lines.length < 5) {
				return ("���ݸ�ʽ����," + line);
			}
			String meterParentName="";
			if (5==lines.length) 
				meterParentName = "";//�ϼ����
			else
				meterParentName = lines[5];//�ϼ����
			hod2000Meter = new Hod2000Meter();
			
			String meterName = lines[0];
			String meterCaliber = lines[1];
			String meterBaudrate = lines[2];
			String typeName = lines[3];
			String meterPosition = lines[4];
			String meter_type_id="";
			String roomId = null;
			//�жϱ���Ƿ��Ѿ�����
			List list = hod2000MeterService.findbyMeterName(meterName);
			if (list.size() > 0) {
				return ("�ñ���Ѿ�����," + line);
			}
			hod2000Meter.setMeterName(meterName);
			//�ھ�Ҫ��ѯϵͳ�������Ƿ���Чcaliber
			List<Hod2000Sysparameter> caliber=hod2000SysparameterService.findByHQL("FROM Hod2000Sysparameter where ptype=1 and pname="+meterCaliber);
			if(caliber.size()>0)
				hod2000Meter.setMeterCaliber(caliber.get(0).getPvalue());
			else
				return ("�ھ���Ч," + line);
			
			//������Ҫ��ѯϵͳ������
			List<Hod2000Sysparameter> baudrate=hod2000SysparameterService.findByHQL("FROM Hod2000Sysparameter where ptype=4 and pname="+meterBaudrate);
			if(baudrate.size()>0)
				hod2000Meter.setMeterBaudrate(baudrate.get(0).getPvalue());
			else
				return ("ͨѶ������Ч," + line);
			hod2000Meter.setMeterPositionName(meterPosition);
			String meterParent= null;
			if(meterParentName != null && !"".equals(meterParentName)){
				List m_paret = hod2000MeterService.findByHQL("from Hod2000Meter where meterName='" + meterParentName + "'");
				if(m_paret != null && m_paret.size() > 0){
					Hod2000Meter hod2000meter = (Hod2000Meter)m_paret.get(0);
					meterParent = hod2000meter.getMeterPosition();
				}else{
					return ("�ϼ��������," + line);
				}
			}else{
				//��ѯ������˾��ȡ��һ��ֵ
				List<Hod2000Meter> m_paret = hod2000MeterService.findByHQL("from Hod2000Meter where meterParent='0'");
				if(m_paret != null && m_paret.size() > 0){
					meterParent = m_paret.get(0).getMeterPosition();
				}else{
					return ("������ϼ����," + line);
				}
			}
			hod2000Meter.setMeterParent(meterParent);
			//���ݵ���λ�ò�ѯ�����������
			List params=hod2000SysparameterService.findByType(10);
			if(params.size()>0)
			{
				String addressCode=((Hod2000Sysparameter)params.get(0)).getPvalue();//ϵͳĬ�ϵ���λ��
				String sql="";
				if(addressCode.length()==2)//Ĭ�ϵ�ַΪʡ��
				{
					//�����û�����ĵ�ַ��ѯ��+��+�ֵ���+С��+¥��+��Ԫ+�����
					sql="SELECT room_id,addressCode FROM regions WHERE address='"+meterPosition+"'";
				}
				else if(addressCode.length()==4)//Ĭ�ϵ�ַΪ����
				{
					//�����û�����ĵ�ַ��ѯ��+�ֵ���+С��+¥��+��Ԫ+�����
					sql="SELECT room_id,addressCode FROM citys WHERE address='"+meterPosition+"'";
				}
				else if(addressCode.length()==6)//Ĭ�ϵ�ַΪ��
				{
					//�����û�����ĵ�ַ��ѯ�ֵ���+С��+¥��+��Ԫ+�����
					sql="SELECT room_id,addressCode FROM countys WHERE address='"+meterPosition+"'";
				}
				else if(addressCode.length()==9)//Ĭ�ϵ�ַΪ�ֵ���
				{
					//�����û�����ĵ�ַ��ѯС��+¥��+��Ԫ+�����
					sql="SELECT room_id,addressCode FROM client_address WHERE address='"+meterPosition+"'";
				}
				else
				{
					return ("Ĭ�ϵ���λ�ô���," + line); 
				}
				List room_list=hod2000MeterService.findByNHQL(sql);
				if (room_list == null || room_list.size() == 0) {
					return ("����λ��" + meterPosition + "����," + line);
				}else{
					Object[] object=(Object[]) room_list.get(0);
					roomId = object[0].toString();
					hod2000Meter.setHod2000Room((Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId)));
					hod2000Meter.setMeterPosition(addressCode+object[1].toString());
				}
			}
			else
			{
				return ("û������ϵͳĬ�ϵ���λ��," + line); 
			}
			//���ݵ���λ�ñ����ѯ�Ƿ�Ψһ��ֻ��ѯ����ʹ�õı�
			int meterPositionCount=hod2000MeterService.findByMeterPosition(hod2000Meter.getMeterPosition());
			if(meterPositionCount>0)
			{
				return ("��λ���Ѵ��ڱ����Ϣ," + line);
			}
			// ���ɲ������
			int meter_index = hod2000MeterService.getMeterIndex();
			hod2000Meter.setMeterIndex(meter_index);
			hod2000Meter.setMeterAble(1);
	//		// �����Ĭ��Ϊ0
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
				return ("����ͺ�" + typeName + "������," + line);
			// ��ѯ���е����ߵļ���������Ϣ
			List con_list = concentratorService.findIsonline("1");
			if (con_list.size() >= 1) {
				
				//�����������ļ�������ʶ���¶Խ����������,������Hod2000Concentrator�б�
				ArrayList<Hod2000Concentrator> temp = conSort(con_list);
				
				//�ѵ����·�Э����뵽��������
				saveCalldownInfo(temp,meterName);
				
				long start_time = System.currentTimeMillis();
				A: while (true) {
					long end_time = System.currentTimeMillis();
					List meter_call_list = hod2000MeterCallDownInfoService
							.findByHQL("from Hod2000MeterCallDownInfo where meterName='"
									+ meterName + "'");
					
					if (end_time - start_time >= ConstantValue.timeout) { // ��ʱ
						for (int j = 0; j < meter_call_list.size(); j++) {
							// ɾ�������������Ӧ�������
							hod2000MeterCallDownInfoService
									.delete((Hod2000MeterCallDownInfo) meter_call_list.get(j));
						}
						return ("��ӱ����Ϣʧ��[����������ʧ��<��ʱ>]," + line);
					}
					if (meter_call_list.size() >= 1) {
						// ����hod2000meterCall.getState() == 2 ���ֵĴ���
						int stataNUm = 0;
						for (int j = 0; j < meter_call_list.size(); j++) {
							Hod2000MeterCallDownInfo hod2000meterCall = (Hod2000MeterCallDownInfo) meter_call_list
									.get(j);
							if (hod2000meterCall.getState() == 1) { // �������ѻظ��Ҹñ�����·���������
								// �жϼ������б������
								List meter_List = hod2000MeterService.findByHQL("from Hod2000Meter where hod2000Concentrator.conNumber='"
												+ hod2000meterCall.getConNum()+ "' ORDER BY meterIndex DESC");
								
								if (meter_List.size() < 1024 && meter_List != null) {
									hod2000Meter.setHod2000Concentrator(concentratorService.findByConNum(hod2000meterCall.getConNum()));
									//�жϱ����ѡ�ı�������Ƿ��ѹ����ü�����
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
										return ("�ñ���ͺ�û�й�������������ʧ��," + line);
									}else{
										//������λ������λ�õ�ƥ�䣬��ֹ���ֵ�������������Ƿ�һ��
										String cons = hod2000Meter.getHod2000Concentrator().getConAddress();
										if(cons!=null&&cons.length()>9)
										{
											cons=cons.substring(0,9);
											String meters=hod2000Meter.getMeterPosition().substring(0,9);
											if(!cons.equals(meters))
											{
												return ("��������ַ���Ƶ�ַƥ��ʧ��," + line);
											}
											else
											{
												//�·��ն����ܱ����ò����ɹ�֮�����ӱ��
												int flag=initParams(hod2000meterCall.getConNum(), "0100"+Util.decimalToHexadecimal(hod2000Meter.getMeterIndex(), 4)+Util.getAddress(hod2000Meter.getMeterName())+Util.decimalToHexadecimal(hod2000Meter.getHod2000MeterType().getTypeIndex(),2)+Util.toByte(hod2000Meter.getMeterBaudrate()), ConstantValue.meter);
												if(2==flag)
												{
													return ("�ն����ܱ����ò�������ʧ��," + line);
												}
												else if(0==flag)
												{
													return ("�ն����ܱ����ò������ó�ʱ," + line);
												}
												else if(1==flag)
												{
													hod2000Meter.setMeterInit(Double.valueOf(hod2000meterCall.getResultContent()).doubleValue());
													hod2000MeterService.save(hod2000Meter);
//													msg.setMsg1("��ӱ����Ϣ�ɹ�!");
													OperatorLog.addOperatorLog(meterName+"�����Ϣ���");
													
													//���ͱ����Ϣ
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
													//���ͱ����Ϣ
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
															 System.out.println("�����Ϣ���ͳɹ�!");
														}
													} finally {
													    response2.close();
													}
													
												}
											}
										}
										else
										{
											return ("�������ĵ�ַ�Ƿ��Ѿ����," + line);
										}
									}
									
								}else{
									return ("��ӱ����Ϣʧ��->ÿ�����������ֻ�ܹ���1024�����," + line);
								}
								
								// �Ѽ�����֮ǰ�����ֵ�λ����Ϊ0
								Hod2000Concentrator hod2000Concentrator = temp.get(0);
								concentratorService.updateBySql("UPDATE Hod2000Concentrator set conLastPosition=0 where conId="
												+ hod2000Concentrator.getConId());
								// �Ѽ��������������ֵ�λ����Ϊ1
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
												+ hod2000meterCall.getConNum());
								concentratorService.updateBySql("UPDATE hod2000_concentrator set con_last_position=1 where con_number="
										+ hod2000meterCall.getConNum());
								for (int d = 0; d < meter_call_list.size(); d++) {
									// ɾ�������������Ӧ�������
									hod2000MeterCallDownInfoService
											.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
								}
								return null;
							}
							
							if (hod2000meterCall.getState() == 0) {// ������û�лظ�ʱ������1000MS���ȴ���ƻظ�
								Thread.sleep(1000);
								continue A;
							}
							
							if (hod2000meterCall.getState() == 2) {// ����ѻظ��Ҹñ�Ʋ����·���������
								stataNUm++;
								if (stataNUm == meter_call_list.size()) {
									for (int d = 0; d < meter_call_list.size(); d++) {
										// ɾ�������������Ӧ�������
										hod2000MeterCallDownInfoService
												.delete((Hod2000MeterCallDownInfo) meter_call_list.get(d));
									}
									return ("��ӱ����Ϣʧ��[����������ʧ��<�������в����ڸñ�->�����ƺͼ������Ƿ�����>]," + line);
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
		
		return ("��ӱ����Ϣʧ��," + line);
	}
	
	private void saveErrorMeter(String errStr){
		try {
			String[] errStrs = errStr.split(",");
			String meterParentName="";
			if (6==errStrs.length) 
				meterParentName = "";//�ϼ����
			else
				meterParentName = errStrs[6];//�ϼ����
			//������Ϣ,���,�ھ�,������,����ͺ�,�������,����λ��,�ϼ����
			Hod2000BatchMeterError meterError = new Hod2000BatchMeterError();
			meterError.setErrorMsg(errStrs[0]);
			meterError.setMeterName(errStrs[1]);
			meterError.setMeterCaliber(errStrs[2]);
			meterError.setMeterBaudrate(errStrs[3]);
			meterError.setTypeName(errStrs[4]);
			meterError.setMeterPosition(errStrs[5]);
			meterError.setMeterParent(meterParentName);
			//�жϴ��������Ƿ��Ѵ��ڣ����ڲ�����
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
