package  com.hod.pro.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;
import com.hod.javabean.GetPreReceive;
import com.hod.javabean.PreReceive;
import com.hod.pojo.Hod2000Client;
import com.hod.pojo.Hod2000Heatingparameter;
import com.hod.pojo.Hod2000PreReceive;
import com.hod.pojo.Hod2000Room;
import com.hod.pro.model.service.IHod2000ClientService;
import com.hod.pro.model.service.IHod2000HeatingparameterService;
import com.hod.pro.model.service.IHod2000PreReceiveService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.util.Arith;
import com.hod.util.GraphicReportHelp;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000PreReceiveAction 预收款明细管理
 * @author yixiang
 */
public class Hod2000PreReceiveAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000PreReceiveService hod2000PreReceiveService;
	private Hod2000PreReceive hod2000PreReceive;
	private Hod2000Heatingparameter heatingparameter;
	private IHod2000HeatingparameterService heatingparameterService;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000ClientService clientService;
	private List dataList;
	private PrintWriter out;
	private Message msg=Message.getInstance();
	private static Logger log = Logger.getLogger(Hod2000PreReceiveAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	private String fileName;
	private InputStream excelStream ;
	
	public String doSave() {
		try {
			request = ServletActionContext.getRequest();
			//设置该用的预收款为已收款
			Hod2000Room room=(Hod2000Room) hod2000RoomService.findById(hod2000PreReceive.getRoomId());
			room.setRoomPreFlag(1);
			hod2000RoomService.update(room);
			hod2000PreReceiveService.save(hod2000PreReceive);
			msg.setMsg1("预收款成功!");
			OperatorLog.addOperatorLog("预收款");
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PreReceiveAction-->doSelect:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/form/precharge.jsp\">返回列表</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public String showParam()
	{
		try {
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
			List list=heatingparameterService.findByCriteria(dc);
			if(list.size()>0)
			{
				heatingparameter=(Hod2000Heatingparameter) list.get(0);
			}
			return "params";
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PreReceiveAction-->showParam:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * 预收款参数设置
	 * @return
	 */
	public String setParam()
	{
		try {
			request = ServletActionContext.getRequest();
			String id=request.getParameter("id");
			String months=request.getParameter("months");
			String modulus=request.getParameter("modulus");
			if(id!=null&&!id.equals(""))
			{
				heatingparameter=(Hod2000Heatingparameter) heatingparameterService.findById(Integer.parseInt(id));
			}
			else
			{
				heatingparameter=new Hod2000Heatingparameter();
			}
			if(months!=null&&!months.equals(""))
			{
				heatingparameter.setHpMonths(Integer.parseInt(months));
			}
			if(modulus!=null&&!modulus.equals(""))
			{
				heatingparameter.setHpFactor(Double.parseDouble(modulus));
			}
			//将房间的所有是否预收款设为0 未预收款
			hod2000RoomService.executeUpdate("update Hod2000Room set roomPreFlag=0");
			heatingparameterService.saveOrUpdate(heatingparameter);
			msg.setMsg1("设置预收款参数成功");
			OperatorLog.addOperatorLog("设置预收款参数");
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PreReceiveAction-->setParam:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000PreReceive!showParam.do\">返回</a>");
		request.setAttribute("message", msg);
		return "message";
	}
	
	/**
	 * 根据用户编号查询预付款信息
	 */
	public void getPreCharge()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String roomId=request.getParameter("roomId");
			Hod2000Room room=(Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId));
			//根据房间号查询用户信息
			List client=hod2000RoomService.findByNHQL("select client_id from hod2000_room where room_id="+room.getRoomId());
			if(0==client.size())
			{
				out.write("{success:false,msg:'该房间无用户信息!'}");
				return;
			}
			Hod2000Client client2=(Hod2000Client)clientService.findById(Integer.parseInt(client.get(0).toString()));
			if(0==client2.getClientEnable())
			{
				out.write("{success:false,msg:'该房间的用户为失效用户!'}");
				return;
			}
//			if(room.getHod2000Client()==null)
//			{
//				out.write("{success:false,msg:'该房间无用户信息!'}");
//				return;
//			}
//			if(0==room.getHod2000Client().getClientEnable())
//			{
//				out.write("{success:false,msg:'该房间的用户为失效用户!'}");
//				return;
//			}
			//判断收费参数是否已经设置
			int preReceiveParams=heatingparameterService.findPreReceiveParams();
			if(0==preReceiveParams)
			{
				out.write("{success:false,msg:'请先设置预收款参数!'}");
				return;
			}
			GetPreReceive pre=new GetPreReceive();
			pre.setRoomId(room.getRoomId());
			pre.setRoomName(room.getRoomName());
			pre.setRoomSize(room.getRoomSize());
			//pre.setClientName(room.getHod2000Client().getClientName());
			pre.setClientName(client2.getClientName());
			//得到预收款参数，即供暖月数与系数单价
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
			List list2=heatingparameterService.findByCriteria(dc);
			if(list2.size()>0)			{
				heatingparameter=(Hod2000Heatingparameter) list2.get(0);
				pre.setPrFactor(heatingparameter.getHpFactor());
				pre.setPrMonths(heatingparameter.getHpMonths());
				//pre.setPrMoney(heatingparameter.getHpFactor()*heatingparameter.getHpMonths()*room.getRoomSize());
				pre.setPrMoney(Arith.multiply(heatingparameter.getHpFactor(), heatingparameter.getHpMonths(), room.getRoomSize(), 2));
			}
			out.write("{success:true,data:"+JSONObject.fromObject(pre)+"}");
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PreReceiveAction-->getPreCharge:",e);
			out.write("{success:false}");
		}
	}
	
	//已收款统计
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("预收款统计查询");
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String clientName=request.getParameter("clientName");
			String prAfter=request.getParameter("prAfter");
			String roperator=request.getParameter("roperator");
			//已收款信息
			String sql="select pr_id,address,a.client_name,room_size,pr_months,pr_factor,pr_money,pr_after,pr_time,p_type,pr_operator from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
			//String sql="select pr_id,community_name+'-'+building_name+'-'+room_name address,a.client_name,room_size,pr_months,pr_factor,pr_money,pr_after,pr_time,p_type,pr_operator from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
			String countSql="select count(*) from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
			Object[] objects;
			Map map;
			dataList=new ArrayList();
			if(startTime!=null&&!startTime.equals(""))
			{
				sql+=" and pr_time>='"+startTime+"'";
				countSql+=" and pr_time>='"+startTime+"'";
			}
			if(endTime!=null&&!endTime.equals(""))
			{
				sql+=" and pr_time<='"+endTime+"'";
				countSql+=" and pr_time<='"+endTime+"'";
			}
			if(clientName!=null&&!"".equals(clientName))
			{
				sql+=" and a.client_name ='"+clientName+"'";
				countSql+=" and a.client_name ='"+clientName+"'";
			}
			if(prAfter!=null&&!prAfter.equals(""))
			{
				sql+=" and pr_after="+prAfter;
				countSql+=" and pr_after="+prAfter;
			}
			if(roperator!=null&&!"".equals(roperator))
			{
				sql+=" and pr_operator ='"+roperator+"'";
				countSql+=" and pr_operator ='"+roperator+"'";
			}
			sql+=" order by pr_time desc";
			List lists=hod2000PreReceiveService.findByNHQL(page, pageSize, sql);
			for (int i = 0; i < lists.size(); i++) {
				map = new HashMap();   
			    objects=(Object[])lists.get(i);
			    map.put("prId", objects[0]);
			    map.put("roomName", objects[1]);
			    map.put("clientName", objects[2]);
			    map.put("roomSize", objects[3]);
			    map.put("prMonths", objects[4]);
			    map.put("prFactor", objects[5]);
			    map.put("prMoney", objects[6]);
			    map.put("prAfter", objects[7]);
			    map.put("prTime", Utils.dateToStrLong(Utils.strToDateLong(objects[8].toString())));
			    map.put("prOperator", objects[10]);
			    dataList.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000PreReceiveService.findByNHQL(countSql).get(0).toString());
			myjsonOut.OutByObject("Hod2000MeterAction-->doSelect:",dataList,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000PreReceiveAction-->doSelect:",e);
		}
	}
	
	//未收款统计
	public void doSelect2()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			double prFactor=0;
			int prMonths=0;
			Object[] obj;
			Map map;
			List unCollect=new ArrayList();
			String sql="select room_size,address,client_name,p_type,client_tel from pre_receive_room where room_pre_flag=0 and client_enable=1";
			//String sql="select room_size,community_name+'-'+building_name+'-'+room_name address,client_name,p_type from pre_receive_room where room_pre_flag=0 and client_enable=1";
			String count="select count(*) from pre_receive_room where room_pre_flag=0 and client_enable=1";
			//List list=hod2000PreReceiveService.findNotPay();
			List list=hod2000PreReceiveService.findByNHQL(page, pageSize, sql);
			if(heatingparameterService.findPreReceiveParams()>0)
			{
				//得到预收款参数，即供暖月数与供暖系数
				DetachedCriteria dc2=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
				List list2=heatingparameterService.findByCriteria(dc2);
				heatingparameter=(Hod2000Heatingparameter) list2.get(0);
				prFactor=heatingparameter.getHpFactor();
				prMonths=heatingparameter.getHpMonths();
				for (int i = 0; i < list.size(); i++) {
					map = new HashMap();   
					obj=(Object[])list.get(i);
					map.put("roomSize", obj[0]);
					map.put("address", obj[1]);
					map.put("clientName", obj[2]);
					map.put("prFactor",prFactor );
					map.put("prMonths", prMonths);
					//map.put("prMoney", prFactor*prMonths*Double.parseDouble(obj[0].toString()));
					map.put("prMoney", Arith.multiply(prFactor, prMonths, Double.parseDouble(obj[0].toString()), 2));
					map.put("clientTel", obj[4]);
					unCollect.add(map);
				}
				int totalRecord =Integer.parseInt(hod2000PreReceiveService.findByNHQL(count).get(0).toString());
				myjsonOut.OutByObject("Hod2000MeterAction-->doSelect:",unCollect,totalRecord,page,pageSize,out);
			}
		} catch (Exception e) {
			log.error("Hod2000PreReceiveAction-->doSelect2:",e);
		}
	}
	
	//预收款统计汇总
	public void doSelect3()
	{
		try {
			//该季度预收款统计汇总
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			double total=0;
			String sql="select room_size,p_type from pre_receive_room where room_pre_flag=0 and client_enable=1";
			Object[] obj;
			List list=hod2000PreReceiveService.findByNHQL(sql);
			if(heatingparameterService.findPreReceiveParams()>0)
			{
				//得到预收款参数，即供暖月数与供暖系数
				DetachedCriteria dc2=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
				List list2=heatingparameterService.findByCriteria(dc2);
				heatingparameter=(Hod2000Heatingparameter) list2.get(0);
				double prFactor=heatingparameter.getHpFactor();
				int prMonths=heatingparameter.getHpMonths();
				for (int i = 0; i < list.size(); i++) {
					obj=(Object[])list.get(i);
					total=Arith.add(total, Arith.multiply(prFactor, prMonths, Double.parseDouble(obj[0].toString()), 2), 2);
				}
			}
			PreReceive pre=hod2000PreReceiveService.getPreReceiveInfo();
			//未收款用户数与未收款金额
			pre.setUnCollectUserNumber(list.size());
			pre.setUnCollectMoneyTotal(total);
			DefaultPieDataset dataset=new DefaultPieDataset();
			dataset.setValue("已收款户数", pre.getUserNumber());
			dataset.setValue("未收款户数", pre.getUnCollectUserNumber());
			dataset.setValue("后付费户数", pre.getToZero());
			JFreeChart chart=ChartFactory.createPieChart3D("户数统计汇总", dataset, true, true, false);//绘制3D饼图
			GraphicReportHelp.getPieFont(chart);
			String fileName = ServletUtilities.saveChartAsPNG(chart, 500, 400, null,request.getSession());
			//request.setAttribute("fileName",fileName);
			
			DefaultPieDataset dataset1=new DefaultPieDataset();
			dataset1.setValue("已收款金额", pre.getMoneyTotal());
			dataset1.setValue("未收款金额", pre.getUnCollectMoneyTotal());
			JFreeChart chart1=ChartFactory.createPieChart3D("金额统计汇总", dataset1, true, true, false);//绘制3D饼图
			GraphicReportHelp.getPieFont(chart1);
			String fileName1 = ServletUtilities.saveChartAsPNG(chart1, 500, 400, null,request.getSession());
			//request.setAttribute("fileName1",fileName1);
			out.write("{success:true,fileName:'"+fileName+"',fileName1:'"+fileName1+"'}");
		} catch (Exception e) {
			log.error("Hod2000PreReceiveAction-->doSelect3:",e);
			out.write("{success:false}");
		}
	}
	
	public String getPreReceivePrint()
	{
		try {
			OperatorLog.addOperatorLog("已收款明细打印");
			request = ServletActionContext.getRequest();
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String clientName=request.getParameter("clientName");
			String prAfter=request.getParameter("prAfter");
			String roperator=request.getParameter("roperator");
			if(startTime!=null||endTime!=null)
			{
				//已收款信息
				String sql="select pr_id,address,a.client_name,room_size,pr_months,pr_factor,pr_money,pr_after,pr_time,p_type,pr_operator from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
				//String sql="select pr_id,community_name+'-'+building_name+'-'+room_name address,a.client_name,room_size,pr_months,pr_factor,pr_money,pr_after,pr_time,p_type,pr_operator from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
				String countSql="select count(*) from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
				Object[] objects;
				Map map;
				dataList=new ArrayList();
				if(startTime!=null&&!startTime.equals(""))
				{
					sql+=" and pr_time>='"+startTime+"'";
					countSql+=" and pr_time>='"+startTime+"'";
				}
				if(endTime!=null&&!endTime.equals(""))
				{
					sql+=" and pr_time<='"+endTime+"'";
					countSql+=" and pr_time<='"+endTime+"'";
				}
				if(clientName!=null&&!"".equals(clientName))
				{
					sql+=" and a.client_name ='"+clientName+"'";
					countSql+=" and a.client_name ='"+clientName+"'";
				}
				if(prAfter!=null&&!prAfter.equals(""))
				{
					sql+=" and pr_after="+prAfter;
					countSql+=" and pr_after="+prAfter;
				}
				if(roperator!=null&&!"".equals(roperator))
				{
					sql+=" and pr_operator ='"+roperator+"'";
					countSql+=" and pr_operator ='"+roperator+"'";
				}
				sql+=" order by pr_time desc";
				List lists=Page.findBySql(request, hod2000PreReceiveService, sql, countSql);
				for (int i = 0; i < lists.size(); i++) {
					map = new HashMap();   
				    objects=(Object[])lists.get(i);
				    map.put("prId", objects[0]);
				    map.put("roomName", objects[1]);
				    map.put("clientName", objects[2]);
				    map.put("roomSize", objects[3]);
				    map.put("prMonths", objects[4]);
				    map.put("prFactor", objects[5]);
				    map.put("prMoney", objects[6]);
				    map.put("prAfter", objects[7]);
				    map.put("prTime", objects[8]);
				    map.put("prOperator", objects[10]);
				    dataList.add(map);
				}
			}
			return "print1";
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PreReceiveAction-->getPreReceivePrint:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	public String getUnPreReceivePrint()
	{
		try {
			OperatorLog.addOperatorLog("未收款明细打印");
			request = ServletActionContext.getRequest();
			//该季度的未收款信息
			double prFactor=0;
			int prMonths=0;
			Object[] obj;
			Map map;
			List unCollect=new ArrayList();
			List list=hod2000PreReceiveService.findNotPay();
			if(heatingparameterService.findPreReceiveParams()>0)
			{
				//得到预收款参数，即供暖月数与供暖系数
				DetachedCriteria dc2=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
				List list2=heatingparameterService.findByCriteria(dc2);
				heatingparameter=(Hod2000Heatingparameter) list2.get(0);
				prFactor=heatingparameter.getHpFactor();
				prMonths=heatingparameter.getHpMonths();
				for (int i = 0; i < list.size(); i++) {
					map = new HashMap();   
					obj=(Object[])list.get(i);
					map.put("roomSize", obj[0]);
					map.put("address", obj[1]);
					map.put("clientName", obj[2]);
					map.put("prFactor",prFactor );
					map.put("prMonths", prMonths);
					//map.put("prMoney", prFactor*prMonths*Double.parseDouble(obj[0].toString()));
					map.put("prMoney", Arith.multiply(prFactor, prMonths,Double.parseDouble(obj[0].toString()), 2));
					map.put("clientTel", obj[4]);
					unCollect.add(map);
				}
				request.setAttribute("unCollect", unCollect);
			}
			return "print2";
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PreReceiveAction-->getUnPreReceivePrint:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * 未收款明细统计导出为excel
	 */
	public String exportToExcel(){
		try {
			
			String sql="select room_size,address,client_name,p_type,client_tel from pre_receive_room where room_pre_flag=0 and client_enable=1";
			List list = hod2000PreReceiveService.findByNHQL(sql);
			
			String date = sdf.format(new Date());
			fileName = date;
			
			//标题行
	        String title[]={"地理位置","用户姓名","联系电话","面积数(平方米)","系数单价(元/月/平方米)","供暖月数","未收款金额(元)"};
	        List<String[]> t_list = new ArrayList<String[]>();
	        t_list.add(title);
			
			if(heatingparameterService.findPreReceiveParams()>0)
			{
				//得到预收款参数，即供暖月数与供暖系数
				DetachedCriteria dc2 = DetachedCriteria.forClass(Hod2000Heatingparameter.class);
				List list2 = heatingparameterService.findByCriteria(dc2);
				heatingparameter = (Hod2000Heatingparameter) list2.get(0);
				Double prFactor = heatingparameter.getHpFactor();
				Integer prMonths = heatingparameter.getHpMonths();
				for (int i = 0; i < list.size(); i++) {
					String[] rowData = new String[7];
					Object[] objects = (Object[])list.get(i);
					rowData[0] = (String)objects[1];
					rowData[1] = (String)objects[2];
					rowData[2] = (String)objects[4];
					rowData[3] = ((Double)objects[0]).toString();
					rowData[4] = prFactor.toString();
					rowData[5] = prMonths + "";
					rowData[6] = Arith.multiply(prFactor, prMonths, Double.parseDouble(objects[0].toString()), 2) + "";
					
					t_list.add(rowData);
				}
			}
			
	        //创建Excel工作簿方法2.2
			HSSFWorkbook workbook = getWorkbook(t_list,"未收款明细统计");

			if (null != workbook) {
				this.workbook2InputStream(workbook,fileName); 
				return "success";
			}

		} catch (Exception e) {
			log.error("Hod2000PreReceiveAction-->doSelect:",e);
			return "message";
		}
		return "message";
	}
	
	
	/**
	 * 已收款明细统计导出为excel
	 */
	public String exportToExcel2(){
		try {
			
			String sql="select pr_id,address,a.client_name,room_size,pr_months,pr_factor,pr_money,pr_after,pr_time,p_type,pr_operator from pre_receive_room a inner join hod2000_pre_receive p on a.room_id=p.room_id where client_enable=1";
			List list = hod2000PreReceiveService.findByNHQL(sql);
			
			String date = sdf.format(new Date());
			fileName = date;
			
			//标题行
	        String title[]={"地址位置","用户姓名","面积数(平方米)","系数单价(元/月/平方米)","供暖月数","预收款金额(元)","收费时间","操作员","是否后付费"};
	        List<String[]> t_list = new ArrayList<String[]>();
	        t_list.add(title);
			
			if(heatingparameterService.findPreReceiveParams()>0)
			{
				
				for (int i = 0; i < list.size(); i++) {
					String[] rowData = new String[9];
					Object[] objects = (Object[])list.get(i);
					rowData[0] = (String)objects[1];
					rowData[1] = (String)objects[2];
					rowData[2] = ((Double)objects[3]).toString();
					rowData[3] = (String)objects[5].toString();
					rowData[4] = (String)objects[4].toString();
					rowData[5] = (String)objects[6].toString();
					rowData[6] = (String)objects[8].toString();
					rowData[7] = (String)objects[10].toString();
					rowData[8] = (String)objects[7].toString();
					if(rowData[8].equals("0"))
					{
						rowData[8]="否";
					}else{
						rowData[8]="是";
					}
					t_list.add(rowData);
					
				}
			}
			
	        //创建Excel工作簿方法2.2
			HSSFWorkbook workbook = getWorkbook(t_list,"已收款明细统计");

			if (null != workbook) {
				this.workbook2InputStream(workbook,fileName); 
				return "success";
			}

		} catch (Exception e) {
			log.error("Hod2000PreReceiveAction-->doSelect:",e);
			return "message";
		}
		return "message";
	}
	/**
	   * 将Workbook写入到InputStream
	   * 
	   * @param workbook    HSSFWorkbook
	   * @param fileName  String 文件名
	   * 
	   * */
	 private void workbook2InputStream(HSSFWorkbook workbook, String fileName) throws Exception {
		 this.setFileName(URLEncoder.encode(fileName,"UTF-8"));
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 workbook.write(baos);
		 baos.flush();
		 byte[] aa = baos.toByteArray();
		 excelStream = new ByteArrayInputStream(aa, 0, aa.length);
		 baos.close();
	 }
	 
	 public void setFileName(String fileName)
			throws UnsupportedEncodingException {
		request = ServletActionContext.getRequest();
		String agent = request.getHeader("User-Agent");
		if (null != agent) {
			agent = agent.toLowerCase();
			if (agent.indexOf("firefox") != -1) {
				this.fileName = new String(URLDecoder.decode(fileName, "UTF-8")
						.getBytes(), "iso-8859-1");
			} else {
				this.fileName = fileName;
			}
		}
	}
	
	private HSSFWorkbook getWorkbook(List<String[]> listM, String sheetNames)
			throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel文件

		HSSFCellStyle style = workbook.createCellStyle(); // 样式对象
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		
		HSSFSheet sheet = workbook.createSheet();// 创建sheet
		workbook.setSheetName(0,sheetNames);
		
		for (int k = 0; k < listM.size(); k++) {
			String[] columnNames = listM.get(k);
			HSSFRow row = sheet.createRow(k); // 创建第1行，也就是输出表头
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			for (short i = 0; i < columnNames.length; i++) {
				cell = row.createCell(i); // 创建第i列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(columnNames[i]);
			}
		}
		return workbook;
	}
	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
    
	public void setHod2000PreReceiveService(IHod2000PreReceiveService hod2000PreReceiveService) {
		this.hod2000PreReceiveService = hod2000PreReceiveService;
	}
 
	public Hod2000PreReceive getHod2000PreReceive() {
		return hod2000PreReceive;
	}

	public void setHod2000PreReceive(Hod2000PreReceive hod2000PreReceive) {
		this.hod2000PreReceive = hod2000PreReceive;
	}
    
	public Hod2000Heatingparameter getHeatingparameter() {
		return heatingparameter;
	}

	public void setHeatingparameter(Hod2000Heatingparameter heatingparameter) {
		this.heatingparameter = heatingparameter;
	}

	public void setHeatingparameterService(
			IHod2000HeatingparameterService heatingparameterService) {
		this.heatingparameterService = heatingparameterService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}

	public void setClientService(IHod2000ClientService clientService) {
		this.clientService = clientService;  
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}
	
}
