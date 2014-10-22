package  com.hod.pro.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hod.pojo.Hod2000Price;
import com.hod.pojo.Hod2000PriceDetail;
import com.hod.pro.model.service.IHod2000PriceDetailService;
import com.hod.pro.model.service.IHod2000PriceService;
import com.hod.util.Message;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000PriceAction 价格方案
 * @author yixiang
 */
public class Hod2000PriceAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000PriceService hod2000PriceService;
	private IHod2000PriceDetailService detailService;
	private Hod2000Price hod2000Price;
	private Hod2000PriceDetail hod2000PriceDetail;
	private List dataList;
	private PrintWriter out;
	private Message msg=Message.getInstance();
	static Logger log = Logger.getLogger(Hod2000PriceAction.class.getName());
	
	/**
	 * 价格方案添加
	 * @return
	 */
	public String doSave() {
		request = ServletActionContext.getRequest();
		String method=request.getParameter("method");
		try {
			if(method!=null&&method.equals("1"))
			{
				List list=hod2000PriceService.findByPriceName(hod2000Price.getPname());
				if(list.size()>0)
				{
					msg.setError1("方案名称不能重复!");
					msg.setJump(false);
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				List list2=hod2000PriceService.findByParams(1,hod2000Price.getPstartTime());
				if(list2.size()>0)
				{
					msg.setError1("该启用日期已存在预订方案!");
					msg.setJump(false);
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Price.setPtype(1);//定额价格方案
				hod2000PriceService.save(hod2000Price);
				hod2000PriceDetail.setHod2000Price(hod2000Price);
				detailService.save(hod2000PriceDetail);
			}
			if(method!=null&&method.equals("2"))
			{
				String priceValue=java.net.URLDecoder.decode(request.getParameter("priceValue"),"UTF-8");//乱码问题解决
				String details=request.getParameter("details");
				JSONArray jsonArray = JSONArray.fromObject(priceValue);
				Object[] objs = jsonArray.toArray();                //转成对像数组 
				for (int i = 0; i < objs.length; i++) {
					JSONObject obj = JSONObject.fromObject(objs[i]);
					String time=obj.get("pstartTime").toString();
					hod2000Price=(Hod2000Price) obj.toBean(obj,Hod2000Price.class);
					hod2000Price.setPstartTime(Utils.strToDate(time));
					//如果是价格方案类型为3，调整到第三个TAB页
					if(3==hod2000Price.getPtype())
						method="3";
					List list=hod2000PriceService.findByPriceName(hod2000Price.getPname());
					if(list.size()>0)
					{
						msg.setError1("方案名称不能重复!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
						request.setAttribute("message", msg);
						return "message";
					}
					List list2=hod2000PriceService.findByParams(hod2000Price.getPtype(),hod2000Price.getPstartTime());
					if(list2.size()>0)
					{
						msg.setError1("该启用日期已存在预订方案!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
						request.setAttribute("message", msg);
						return "message";
					}
					hod2000PriceService.save(hod2000Price);
				}
				//明细的保存
				JSONArray jsonArray2 = JSONArray.fromObject(details);
				Object[] objs2 = jsonArray2.toArray();                //转成对像数组 
				for (int i = 0; i < objs2.length; i++) {
					JSONObject obj2 = JSONObject.fromObject(objs2[i]);//再使用JsonObject遍历一个个的对像
					hod2000PriceDetail=(Hod2000PriceDetail) obj2.toBean(obj2,Hod2000PriceDetail.class);
					hod2000PriceDetail.setHod2000Price(hod2000Price);
					detailService.save(hod2000PriceDetail);
				}
			}
			msg.setMsg1("保存价格方案成功!");
			OperatorLog.addOperatorLog("新增价格方案");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000PriceAction-->doSave:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	/**
	 * 价格方案删除
	 * @return
	 */
	public String doDelete() {
		request = ServletActionContext.getRequest();
		String ids = request.getParameter("delIds");
		String method=request.getParameter("method");
		try {
			if (ids!=null&&!ids.equals("")) {
				ids = Page.convertKey(ids);
				hod2000PriceService.deleteByParam(ids.split(","));
				OperatorLog.addOperatorLog("价格方案删除");
			}else{
				msg.setError1("ID无效");
				msg.setLink(0, "<a href=\"" + request.getContextPath()
						+ "/show/pricelist.jsp\">返回价格方案页面</a>");
				request.setAttribute("message", msg);
				return "message";
			}
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000PriceAction-->doDelete:",e);
		}
		msg.setMsg1("删除价格方案成功");
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	/**
	 * 价格方案编辑
	 * @return
	 */
	public String toUpdate() {
		try {
			request = ServletActionContext.getRequest();
			String method=request.getParameter("method");
			int id=Integer.parseInt(request.getParameter("id"));
			hod2000Price = (Hod2000Price) hod2000PriceService.findById(id);
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000PriceDetail.class);
			dc.add(Restrictions.eq("hod2000Price", hod2000Price));
			if(method!=null&&method.equals("1"))
			{
				hod2000PriceDetail=(Hod2000PriceDetail) detailService.findByCriteria(dc).get(0);
				return "toUpdate";
			}
			if(method!=null&&method.equals("2"))
			{
				dataList=detailService.findByCriteria(dc);
				request.setAttribute("basePrice", ((Hod2000PriceDetail)dataList.get(0)).getPdBasePrice());
				return "toUpdate1";
			}
			return "toUpdate";
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PriceAction-->toUpdate:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  

	/**
	 * 修改价格方案
	 * @return
	 */
	public String doUpdate() {
		request = ServletActionContext.getRequest();
		String method=request.getParameter("method");
		try {
			if(method!=null&&method.equals("1"))
			{
				String pname=request.getParameter("pname");
				String startTime=request.getParameter("pstartTime");
				List list=hod2000PriceService.findByPriceName(pname,hod2000Price.getPid());
				if(list.size()>0)
				{
					msg.setError1("方案名称不能重复!");
					msg.setJump(false);
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Price=(Hod2000Price) hod2000PriceService.findById(hod2000Price.getPid());
				List list2=hod2000PriceService.findByParams(hod2000Price.getPtype(),Utils.strToDate(startTime),hod2000Price.getPid());
				if(list2.size()>0)
				{
					msg.setError1("该启用日期已存在预订方案!");
					msg.setJump(false);
					msg.setLink(0, "<a href=\"" + request.getContextPath()
							+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
					request.setAttribute("message", msg);
					return "message";
				}
				hod2000Price.setPname(pname);
				hod2000Price.setPstartTime(Utils.strToDate(startTime));
				hod2000PriceService.update(hod2000Price);
				hod2000PriceDetail.setHod2000Price(hod2000Price);
				detailService.update(hod2000PriceDetail);
			}
			if(method!=null&&method.equals("2"))
			{
				String priceValue=java.net.URLDecoder.decode(request.getParameter("priceValue"),"UTF-8");//乱码问题解决
				String details=request.getParameter("details");
				JSONArray jsonArray = JSONArray.fromObject(priceValue);
				Object[] objs = jsonArray.toArray();                //转成对像数组 
				for (int i = 0; i < objs.length; i++) {
					JSONObject obj = JSONObject.fromObject(objs[i]);
					String time=obj.get("pstartTime").toString();
					hod2000Price=(Hod2000Price) obj.toBean(obj,Hod2000Price.class);
					hod2000Price.setPstartTime(Utils.strToDate(time));
					//如果价格方案类型为3时，调整到第三个TAB页
					if(3==hod2000Price.getPtype())
						method="3";
					List list=hod2000PriceService.findByPriceName(hod2000Price.getPname(),hod2000Price.getPid());
					if(list.size()>0)
					{
						msg.setError1("方案名称不能重复!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
						request.setAttribute("message", msg);
						return "message";
					}
					List list2=hod2000PriceService.findByParams(hod2000Price.getPtype(),hod2000Price.getPstartTime(),hod2000Price.getPid());
					if(list2.size()>0)
					{
						msg.setError1("该启用日期已存在预订方案!");
						msg.setJump(false);
						msg.setLink(0, "<a href=\"" + request.getContextPath()
								+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
						request.setAttribute("message", msg);
						return "message";
					}
					hod2000PriceService.update(hod2000Price);
				}
				//删除明细之后重新添加
				hod2000Price=(Hod2000Price) hod2000PriceService.findById(hod2000Price.getPid());
				for (Hod2000PriceDetail detail : hod2000Price.getHod2000PriceDetails()) {
					detailService.delete(detail);
				}
				//明细的重新添加
				JSONArray jsonArray2 = JSONArray.fromObject(details);
				Object[] objs2 = jsonArray2.toArray();                //转成对像数组 
				for (int i = 0; i < objs2.length; i++) {
					JSONObject obj2 = JSONObject.fromObject(objs2[i]);//再使用JsonObject遍历一个个的对像
					hod2000PriceDetail=(Hod2000PriceDetail) obj2.toBean(obj2,Hod2000PriceDetail.class);
					hod2000PriceDetail.setHod2000Price(hod2000Price);
					detailService.save(hod2000PriceDetail);
				}
			}
			msg.setMsg1("修改价格方案成功!");
			OperatorLog.addOperatorLog("价格方案修改");
		} catch (Exception e) {
			msg.setError1(e.toString());
			log.error("Hod2000PriceAction-->doUpdate:",e);
		}
		msg.setLink(0, "<a href=\"" + request.getContextPath()
				+ "/hod2000Price!doSelect.do?method="+method+"\">返回价格方案页面</a>");
		request.setAttribute("message", msg);
		return "message";
	}
    
	/**
	 * 价格方案查询
	 * @return
	 */
	public String doSelect() {
		try {
			OperatorLog.addOperatorLog("价格方案查询");
			request=ServletActionContext.getRequest();
			String method=request.getParameter("method");
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Price.class);
			dc.add(Restrictions.eq("ptype", 1));
			dataList = Page.util(request, hod2000PriceService,dc);
			DetachedCriteria dc2=DetachedCriteria.forClass(Hod2000Price.class);
			dc2.add(Restrictions.eq("ptype", 2));
			List list = Page.util(request, hod2000PriceService,dc2);
			request.setAttribute("list", list);
			DetachedCriteria dc3=DetachedCriteria.forClass(Hod2000Price.class);
			dc3.add(Restrictions.eq("ptype", 3));
			List list2 = Page.util(request, hod2000PriceService,dc3);
			request.setAttribute("list2", list2);
			if(method!=null&&method.equals("2"))
				return "sp2";
			if(method!=null&&method.equals("3"))
				return "sp3";
			return "sp1";
		} catch (Exception e) {
			msg.setError1("错误信息："+e.toString());
			log.error("Hod2000PriceAction-->doSelect:",e);
			request.setAttribute("message", msg);
			return "message";
		}
	}  
	
	//查看明细
	public void showDetail()
	{
		try {
			OperatorLog.addOperatorLog("查看价格方案明细");
			request=ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			int id=Integer.parseInt(request.getParameter("id"));
			hod2000Price = (Hod2000Price) hod2000PriceService.findById(id);
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000PriceDetail.class);
			dc.add(Restrictions.eq("hod2000Price", hod2000Price));
			dataList=detailService.findByCriteria(dc);
			List list=new ArrayList();
			Map map;
			for (int i = 0; i < dataList.size(); i++) {
				map=new HashMap();
				hod2000PriceDetail=(Hod2000PriceDetail) dataList.get(i);
				map.put("pdId", hod2000PriceDetail.getPdId());
				map.put("pdBasePrice", hod2000PriceDetail.getPdBasePrice());
				map.put("pdPowerPrice", hod2000PriceDetail.getPdPowerPrice());
				map.put("pdPower", hod2000PriceDetail.getPdPower());
				list.add(map);
			}
			out.write("{success:true,data:"+JSONArray.fromObject(list).toString()+"}");
		} catch (Exception e) {
			log.error("Hod2000PriceAction-->showDetail",e);
			out.write("{success:false}");
		}
	}
	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000PriceService(IHod2000PriceService hod2000PriceService) {
		this.hod2000PriceService = hod2000PriceService;
	}
 
	public Hod2000Price getHod2000Price() {
		return hod2000Price;
	}

	public void setHod2000Price(Hod2000Price hod2000Price) {
		this.hod2000Price = hod2000Price;
	}

	public void setDetailService(IHod2000PriceDetailService detailService) {
		this.detailService = detailService;
	}

	public Hod2000PriceDetail getHod2000PriceDetail() {
		return hod2000PriceDetail;
	}

	public void setHod2000PriceDetail(Hod2000PriceDetail hod2000PriceDetail) {
		this.hod2000PriceDetail = hod2000PriceDetail;
	}
	
}
