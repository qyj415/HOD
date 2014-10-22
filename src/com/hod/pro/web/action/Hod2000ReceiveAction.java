package  com.hod.pro.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;
import com.hod.javabean.GetReceive;
import com.hod.javabean.ReceiveSummary;
import com.hod.javabean.UnCollectReceive;
import com.hod.pojo.Hod2000Heatingparameter;
import com.hod.pojo.Hod2000PreReceive;
import com.hod.pojo.Hod2000Price;
import com.hod.pojo.Hod2000Receive;
import com.hod.pojo.Hod2000Room;
import com.hod.pro.model.service.IHod2000ClientService;
import com.hod.pro.model.service.IHod2000HeatingparameterService;
import com.hod.pro.model.service.IHod2000PreReceiveService;
import com.hod.pro.model.service.IHod2000PriceService;
import com.hod.pro.model.service.IHod2000ReceiveService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.util.Arith;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;
import com.hod.util.GraphicReportHelp;

/**
 * Hod2000ReceiveAction �շ���ϸ��
 * @author yixiang
 */
public class Hod2000ReceiveAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000ReceiveService hod2000ReceiveService;
	private Hod2000Receive hod2000Receive;
	private Hod2000Heatingparameter heatingparameter;
	private IHod2000HeatingparameterService heatingparameterService;
	private IHod2000PreReceiveService hod2000PreReceiveService;
	private IHod2000ClientService clientService;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000PriceService hod2000PriceService;
	private List dataList;
	private PrintWriter out;
	private Message msg=Message.getInstance();
	private static Logger log = Logger.getLogger(Hod2000ReceiveAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	private String fileName;
	private InputStream excelStream ;
	
	public String doSave() {
		try {
			request = ServletActionContext.getRequest();
			//���ø��õ��շ�Ϊ���շ�
			Hod2000Room room=(Hod2000Room) hod2000RoomService.findById(hod2000Receive.getRoomId());
			room.setRoomReceiveFlag(1);
			hod2000RoomService.update(room);
			hod2000ReceiveService.save(hod2000Receive);
			msg.setMsg1("�տ�ɹ�!");
			OperatorLog.addOperatorLog("�շ�");
		} catch (Exception e) {
			msg.setError1("������Ϣ��"+e.toString());
			log.error("Hod2000ReceiveAction-->doSave:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/form/charge.jsp\">�����б�</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	public void doSelect() {
		try {
			OperatorLog.addOperatorLog("�շ�ͳ�Ʋ�ѯ");
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//��������ҳ��
			int pageSize=Integer.parseInt(request.getParameter("rows"));//��������ÿҳ��¼��
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String clientName=request.getParameter("clientName");
			String roperator=request.getParameter("roperator");
			//���տ���Ϣ
			String sql="select r_id,address,a.client_name,pr_money,r_money,return_money,fill_money,r_type,r_date,room_size,r_operator from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
			//String sql="select r_id,community_name+'-'+building_name+'-'+room_name address,a.client_name,pr_money,r_money,return_money,fill_money,r_type,r_date,room_size,r_operator from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
			String countSql="select count(*) from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
			Object[] objects;
			Map map;
			dataList=new ArrayList();
			if(startTime!=null&&!startTime.equals(""))
			{
				sql+=" and r_date>='"+startTime+"'";
				countSql+=" and r_date>='"+startTime+"'";
			}
			if(endTime!=null&&!endTime.equals(""))
			{
				sql+=" and r_date<='"+endTime+"'";
				countSql+=" and r_date<='"+endTime+"'";
			}
			if(clientName!=null&&!"".equals(clientName))
			{
				sql+=" and a.client_name ='"+clientName+"'";
				countSql+=" and a.client_name ='"+clientName+"'";
			}
			if(roperator!=null&&!"".equals(roperator))
			{
				sql+=" and r_operator ='"+roperator+"'";
				countSql+=" and r_operator ='"+roperator+"'";
			}
			sql+=" order by r_date desc";
			List list=hod2000ReceiveService.findByNHQL(page, pageSize, sql);
			for (int i = 0; i < list.size(); i++) {
				map = new HashMap();   
			    objects=(Object[])list.get(i);
			    map.put("rid", objects[0]);
			    map.put("address", objects[1]);
			    map.put("clientName", objects[2]);
			    map.put("prMoney", objects[3]);
			    map.put("rmoney", objects[4]);
			    map.put("returnMoney", objects[5]);
			    map.put("fillMoney", objects[6]);
			    map.put("rtype", objects[7]);
			    map.put("rdate", Utils.dateToStrLong(Utils.strToDateLong(objects[8].toString())));
			    map.put("roomSize", objects[9]);
			    map.put("roperator", objects[10]);
			    dataList.add(map);
			}
			int totalRecord =Integer.parseInt(hod2000ReceiveService.findByNHQL(countSql).get(0).toString());
			myjsonOut.OutByObject("Hod2000ReceiveAction-->doSelect:",dataList,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000ReceiveAction-->doSelect:",e);
		}
	}
	
	//δ�տ�ͳ��
	public void doSelect2()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String page=request.getParameter("page");//��������ҳ��
			String pageSize=request.getParameter("rows");//��������ÿҳ��¼��
			String countSql="select count(*) from receive_room_meter where room_receive_flag=0 and client_enable=1 and meter_able=1";
			//δ�տ���Ϣ��ѯ
			List<UnCollectReceive> unCollect=hod2000ReceiveService.findUnCollected(page,pageSize);
			int totalRecord =Integer.parseInt(hod2000ReceiveService.findByNHQL(countSql).get(0).toString());
			myjsonOut.OutByObject("Hod2000ReceiveAction-->doSelect2:",unCollect,totalRecord,Integer.parseInt(page),Integer.parseInt(pageSize),out);
		} catch (Exception e) {
			log.error("Hod2000ReceiveAction-->doSelect2:",e);
		}
	}
	
	//ͳ�ƻ���
	public void doSelect3()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			//�շ�ͳ�ƻ���
			ReceiveSummary summary=hod2000ReceiveService.getSummary();
			DefaultPieDataset dataset=new DefaultPieDataset();
			dataset.setValue("���շѻ���", summary.getUserNum());
			dataset.setValue("δ�շѻ���", summary.getUnCollectUserNum());
			JFreeChart chart=ChartFactory.createPieChart3D("���շ���δ�շѻ���ͳ�ƻ���", dataset, true, true, false);//����3D��ͼ
			GraphicReportHelp.getPieFont(chart);
			String fileName = ServletUtilities.saveChartAsPNG(chart, 400, 300, null,request.getSession());
			out.write("{success:true,fileName:'"+fileName+"',preMoney:"+summary.getPreMoney()+",totalMoney:"+summary.getTotalMoney()+",returnMoney:"+summary.getReturnMoney()+",fillMoney:"+summary.getFillMoney()+"}");
		} catch (Exception e) {
			log.error("Hod2000ReceiveAction-->doSelect3:",e);
			out.write("{success:false}");
		}
	}
	
	//���տ���ϸ��ӡ
	public String getReceivePrint()
	{
		try {
			OperatorLog.addOperatorLog("���տ���ϸ��ӡ");
			request = ServletActionContext.getRequest();
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String clientName=request.getParameter("clientName");
			String roperator=request.getParameter("roperator");
			if(startTime!=null||endTime!=null)
			{
				//���տ���Ϣ
				String sql="select r_id,address,a.client_name,pr_money,r_money,return_money,fill_money,r_type,r_date,room_size,r_operator from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
				//String sql="select r_id,community_name+'-'+building_name+'-'+room_name address,a.client_name,pr_money,r_money,return_money,fill_money,r_type,r_date,room_size,r_operator from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
				String countSql="select count(*) from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
				Object[] objects;
				Map map;
				dataList=new ArrayList();
				if(startTime!=null&&!startTime.equals(""))
				{
					sql+=" and r_date>='"+startTime+"'";
					countSql+=" and r_date>='"+startTime+"'";
				}
				if(endTime!=null&&!endTime.equals(""))
				{
					sql+=" and r_date<='"+endTime+"'";
					countSql+=" and r_date<='"+endTime+"'";
				}
				if(clientName!=null&&!"".equals(clientName))
				{
					sql+=" and a.client_name ='"+clientName+"'";
					countSql+=" and a.client_name ='"+clientName+"'";
				}
				if(roperator!=null&&!"".equals(roperator))
				{
					sql+=" and r_operator ='"+roperator+"'";
					countSql+=" and r_operator ='"+roperator+"'";
				}
				sql+=" order by r_date desc";
				List list=Page.findBySql(request, hod2000ReceiveService, sql, countSql);
				for (int i = 0; i < list.size(); i++) {
					map = new HashMap();   
				    objects=(Object[])list.get(i);
				    map.put("rid", objects[0]);
				    map.put("address", objects[1]);
				    map.put("clientName", objects[2]);
				    map.put("prMoney", objects[3]);
				    map.put("rmoney", objects[4]);
				    map.put("returnMoney", objects[5]);
				    map.put("fillMoney", objects[6]);
				    map.put("rtype", objects[7]);
				    map.put("rdate", objects[8]);
				    map.put("roomSize", objects[9]);
				    map.put("roperator", objects[10]);
				    dataList.add(map);
				}
			}
			return "print1";
		}catch (Exception e) {
			msg.setError1("������Ϣ��"+e.toString());
			log.error("Hod2000ReceiveAction-->getReceivePrint:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * δ�տ���ϸͳ�Ƶ���Ϊexcel
	 */
	public String exportToExcel(){
		try {
			request = ServletActionContext.getRequest();
			//δ�տ���Ϣ��ѯ
			List<UnCollectReceive> unCollect=hod2000ReceiveService.findUnCollected("","");
			//System.out.println(unCollect+"----------------------------------");
			String date = sdf.format(new Date());
			fileName = date;
			
			//������
	        String title[]={"����λ��","�û�����","��ϵ�绰","���","Ԥ�����Ԫ��","Ӧ�����(Ԫ)"};
	        List<String[]> t_list = new ArrayList<String[]>();
	        t_list.add(title);
	        
	        for(UnCollectReceive u : unCollect){
	        	String[] rowData = new String[6];
	        	rowData[0] = u.getAddress();
	        	rowData[1] = u.getClientName();
				rowData[2] = u.getClientTel();
				rowData[3] = u.getMeterName();
				rowData[4] = Double.valueOf(u.getPreMoney()).toString();
				rowData[5] = Double.valueOf(u.getReceiveMoney()).toString();
	        	t_list.add(rowData);
	        }
	        
	        //����Excel����������2.2
			HSSFWorkbook workbook = getWorkbook(t_list,"δ�տ���ϸͳ��");

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
	 * ���տ���ϸͳ�Ƶ���Ϊexcel
	 */
	public String exportToExcel2(){
		try {
			
			String sql="select r_id,address,a.client_name,pr_money,r_money,return_money,fill_money,r_type,r_date,room_size,r_operator from receive_room_meter a inner join hod2000_receive p on a.room_id=p.room_id where client_enable=1 and meter_able=1";
			List list = hod2000PreReceiveService.findByNHQL(sql);
			
			String date = sdf.format(new Date());
			fileName = date;
			
			//������
	        String title[]={"��ַλ��","�û�����","Ԥ�����Ԫ��","�˿��Ԫ��","������Ԫ��","Ӧ�����(Ԫ)","�շѷ���","�շ�ʱ��","����Ա"};
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
					rowData[3] = ((Double)objects[5]).toString();
					rowData[4] = ((Double)objects[6]).toString();
					rowData[5] = ((Double)objects[4]).toString();
					rowData[6] = (String)objects[7].toString();
					if(rowData[6].equals("1"))
					{
						rowData[6]="�����ٲ���";
					}else{
						rowData[6]="�����ٲ�";
					}
					rowData[7] = (String)objects[8].toString();
					rowData[8] = (String)objects[10].toString();
					
					t_list.add(rowData);
					
				}
			}

	        //����Excel����������2.2
			HSSFWorkbook workbook = getWorkbook(t_list,"���տ���ϸͳ��");

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
	   * ��Workbookд�뵽InputStream
	   * 
	   * @param workbook    HSSFWorkbook
	   * @param fileName  String �ļ���
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

		HSSFWorkbook workbook = new HSSFWorkbook();// ����Excel�ļ�

		HSSFCellStyle style = workbook.createCellStyle(); // ��ʽ����
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ָ����Ԫ��ֱ���ж���
		
		HSSFSheet sheet = workbook.createSheet();// ����sheet
		workbook.setSheetName(0,sheetNames);
		
		for (int k = 0; k < listM.size(); k++) {
			String[] columnNames = listM.get(k);
			HSSFRow row = sheet.createRow(k); // ������1�У�Ҳ���������ͷ
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			for (short i = 0; i < columnNames.length; i++) {
				cell = row.createCell(i); // ������i��
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(columnNames[i]);
			}
		}
		return workbook;
	}
	
	public String getUnReceivePrint()
	{
		try {
			OperatorLog.addOperatorLog("δ�տ���ϸ��ӡ");
			request = ServletActionContext.getRequest();
			//δ�տ���Ϣ��ѯ
			List<UnCollectReceive> unCollect=hod2000ReceiveService.findUnCollected("","");
			request.setAttribute("unCollect", unCollect);
			return "print2";
		}catch (Exception e) {
			msg.setError1("������Ϣ��"+e.toString()); 
			log.error("Hod2000ReceiveAction-->getUnReceivePrint:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	//����û�id�õ��շ���Ϣ
	public void getCharge()
	{
		try {
			double preMoney=0;//Ԥ������
			double rMoney=0;//Ӧ�ս��
			double returnMoney=0;//�˿���
			double fillMoney=0;//�������
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String roomId=request.getParameter("roomId");
			//�ж��շѲ����Ƿ��Ѿ�����
			int receiveParams=heatingparameterService.findReceiveParams();
			if(0==receiveParams)
			{
				out.write("{success:false,msg:'���������շѲ���!'}");
				return;
			}
			//��ѯ����ļ۸񷽰����жϸ÷����Ƿ��Ѿ����ڵ�ǰ����
			Hod2000Room room=(Hod2000Room) hod2000RoomService.findById(Integer.parseInt(roomId));
			Hod2000Price price=null;
			price=hod2000PriceService.findByParams(room.getPriceType(),1);
			if(price==null)
			{
				out.write("{success:false,msg:'������δ���ø÷���۸񷽰��ĵ�ǰ����!'}");
				return;
			}
			GetReceive re=hod2000ReceiveService.getCharge(Integer.parseInt(roomId));
			re.setTypes(0);
			//��ѯ�÷����Ԥ�����һ���ǵ�ǰ���ȵģ��������е�Ԥ�տ�״̬Ϊ1
			List preList=hod2000PreReceiveService.findByRoomId(Integer.parseInt(roomId));
			if(preList.size()>0)
			{
				Hod2000PreReceive pre=(Hod2000PreReceive) preList.get(0);
				//�����û���Ԥ�տ�Ϊ����ʱ�����շѷ���ֻ��Ϊ"�����ٲ�"
				if(1==pre.getPrAfter())
				{
					re.setReType(2);
					re.setTypes(1);
				}
				preMoney=pre.getPrMoney();
			}
			re.setPreMoney(preMoney);
			rMoney=re.getMonetyToPay();
			if(rMoney>preMoney)
			{
				//fillMoney=rMoney-preMoney;
				fillMoney=Arith.subtract(rMoney, preMoney, 2);
			}
			else if(rMoney<preMoney)
			{
				//returnMoney=preMoney-rMoney;
				returnMoney=Arith.subtract(preMoney, rMoney, 2);
			}
			if(re.getReType()==1)
				re.setFillMoney(0);
			else
				re.setFillMoney(fillMoney);
			re.setReturnMoney(returnMoney);
			out.write("{success:true,data:"+JSONObject.fromObject(re)+"}");
		} catch (Exception e) {
			msg.setError1("������Ϣ��"+e.toString());
			log.error("Hod2000ReceiveAction-->getCharge:",e);
			out.write("{success:false}");
		}
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
			msg.setError1("������Ϣ��"+e.toString());
			log.error("Hod2000ReceiveAction-->showParam:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}
	
	/**
	 * Ԥ�տ��������
	 * @return
	 */
	public String setParam()
	{
		try {
			request = ServletActionContext.getRequest();
			String id=request.getParameter("id");
			String startMonth=request.getParameter("startMonth");
			String endMonth=request.getParameter("endMonth");
			String hpType=request.getParameter("hpType");
			if(id!=null&&!id.equals(""))
			{
				heatingparameter=(Hod2000Heatingparameter) heatingparameterService.findById(Integer.parseInt(id));
			}
			else
			{
				heatingparameter=new Hod2000Heatingparameter();
			}
			if(startMonth!=null&&!startMonth.equals(""))
			{
				heatingparameter.setHpStart(Utils.strToDate(startMonth));
			}
			if(endMonth!=null&&!endMonth.equals(""))
			{
				heatingparameter.setHpEnd(Utils.strToDate(endMonth));
			}
			heatingparameter.setHpType(Integer.parseInt(hpType));
			//������������Ƿ�Ԥ�տ���Ϊ0 δԤ�տ�
			hod2000RoomService.executeUpdate("update Hod2000Room set roomReceiveFlag=0");
			heatingparameterService.saveOrUpdate(heatingparameter);
			msg.setMsg1("�����շѲ���ɹ�");
			OperatorLog.addOperatorLog("�շѲ�������");
		} catch (Exception e) {
			msg.setError1("������Ϣ��"+e.toString());
			log.error("Hod2000ReceiveAction-->setParam:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Receive!showParam.do\">����</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000ReceiveService(IHod2000ReceiveService hod2000ReceiveService) {
		this.hod2000ReceiveService = hod2000ReceiveService;
	}
 
	public Hod2000Receive getHod2000Receive() {
		return hod2000Receive;
	}

	public void setHod2000Receive(Hod2000Receive hod2000Receive) {
		this.hod2000Receive = hod2000Receive;
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

	public void setHod2000PreReceiveService(
			IHod2000PreReceiveService hod2000PreReceiveService) {
		this.hod2000PreReceiveService = hod2000PreReceiveService;
	}

	public void setClientService(IHod2000ClientService clientService) {
		this.clientService = clientService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}

	public void setHod2000PriceService(IHod2000PriceService hod2000PriceService) {
		this.hod2000PriceService = hod2000PriceService;
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
