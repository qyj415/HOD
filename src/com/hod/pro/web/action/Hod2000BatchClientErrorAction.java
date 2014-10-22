package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.hod.pojo.Hod2000BatchClientError;
import com.hod.pro.model.service.IHod2000BatchClientErrorService;
import com.hod.pro.model.service.IHod2000ClientService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.util.BatchFileLineUtil;
import com.hod.util.Message;

/**
 * Hod2000BatchClientErrorAction 控制器
 * @author JSmart Tools
 */
public class Hod2000BatchClientErrorAction extends ActionSupport {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private IHod2000BatchClientErrorService hod2000BatchClientErrorService;
	private Hod2000BatchClientError hod2000BatchClientError;
	private int maxSize=100000;//最大注册用户数
	private IHod2000ClientService hod2000ClientService;
	private IHod2000RoomService hod2000RoomService;
	private List dataList;
	private PrintWriter out;
	private Message msg = Message.getInstance();
	private Logger log = Logger.getLogger(Hod2000BatchClientErrorAction.class.getName());
	private MyJsonOut myjsonOut = new MyJsonOut();
	
	public String doSave() {
		try {
			hod2000BatchClientErrorService.save(hod2000BatchClientError);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
    
	public String doDelete() {
		try {
			String ids = request.getParameter("delIds");
			if (ids!=null) {
				ids = Page.convertKey(ids);
				hod2000BatchClientErrorService.deleteByParam(ids.split(","));//注意主键类型
				return SUCCESS;
			}else{
				request.setAttribute("exceptions","ID IS NULL");
				return ERROR;
			}
		} catch (Exception e) {
			return ERROR;
		}
	}
    
	public String toUpdate() {
		try {
			hod2000BatchClientError = (Hod2000BatchClientError) hod2000BatchClientErrorService.findById(request.getParameter("id"));
			return "toUpdate";
		} catch (Exception e) {
			return ERROR;
		}
	}  

	public void doUpdate() {
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			String id = request.getParameter("id");
			String clientName = request.getParameter("clientName");
		    String clientSex = request.getParameter("clientSex") ;
		    String clientCardType = request.getParameter("clientCardType") ;
		    String clientCardNumber = request.getParameter("clientCardNumber") ;
		    String clientAddress = request.getParameter("clientAddress") ;
		    String clientTel = request.getParameter("clientTel") ;
		    String ptype = request.getParameter("ptype") ;
		    String roomAddress = request.getParameter("roomAddress") ;
		    String roomRemark = request.getParameter("roomRemark") ;
		    
		    StringBuilder sb = new StringBuilder();
		    sb.append(clientName).append(",");
		    sb.append(clientSex).append(",");
		    sb.append(clientCardType).append(",");
		    sb.append(clientCardNumber).append(",");
		    sb.append(clientAddress).append(",");
		    sb.append(clientTel).append(",");
		    sb.append(ptype).append(",");
		    sb.append(roomAddress).append(",");
		    sb.append(roomRemark);
		    
		    String lineRs = BatchFileLineUtil.addClientlineUtil(sb.toString(), hod2000ClientService, hod2000RoomService, maxSize);
		    if(lineRs != null){
		    	
		    	 //处理错误数据——》更新到错误数据表
		    	Hod2000BatchClientError hod2000BatchClientError = (Hod2000BatchClientError)hod2000BatchClientErrorService.findById(Integer.parseInt(id));
		    	updatehod2000BatchClientError(lineRs,hod2000BatchClientError);
		    	
				//errData:错误数据,用户姓名,用户性别,证件类型,证件号码,联系地址,联系电话,价格方案, 地理位置,备注信息
				out.write("{success:false,errData:'" + lineRs + "'}");
		    }else{
		    	//当保存成功(错误信息已更正)，删除错误信息表中的对应数据
		    	hod2000BatchClientErrorService.deleteByKey(Integer.parseInt(id));
		    	out.write("{success:true}");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{success:false}");
		}
	}
    
	public void doSelect() {
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			int page=Integer.parseInt(request.getParameter("page"));//获得请求的页码
			int pageSize=Integer.parseInt(request.getParameter("rows"));//获得请求的每页记录数
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000BatchClientError.class);
			dataList=hod2000BatchClientErrorService.findByCriteria(page, pageSize, dc);
			int totalRecord =(Integer)hod2000BatchClientErrorService.getRowCount(dc);
			myjsonOut.OutByObject("Hod2000BatchClientErrorAction-->doSelect:",dataList,totalRecord,page,pageSize,out);
		} catch (Exception e) {
			log.error("Hod2000BatchClientErrorAction-->doSelect:", e);
		}
	}    
	
	public void updatehod2000BatchClientError(String errLine,Hod2000BatchClientError hod2000BatchClientError){
		try {
			//处理错误数据——》更新到错误数据表
			String[] errs = errLine.split(",");
			if(errs.length < 10){
				String[] temp_errs = new String[10];
				//防止数组下标越界
				System.arraycopy(errs, 0, temp_errs, 0, errs.length);
				errs = temp_errs;
			}
			//错误数据,用户姓名,用户性别,证件类型,证件号码,联系地址,联系电话,价格方案, 地理位置,备注信息
			hod2000BatchClientError.setErrorMsg(errs[0]);
			hod2000BatchClientError.setClientName(errs[1]);
			hod2000BatchClientError.setClientSex(errs[2]);
			hod2000BatchClientError.setClientCardType(errs[3]);
			hod2000BatchClientError.setClientCardNumber(errs[4]);
			hod2000BatchClientError.setClientAddress(errs[5]);
			hod2000BatchClientError.setClientTel(errs[6]);
			hod2000BatchClientError.setPtype(errs[7]);
			hod2000BatchClientError.setRoomAddress(errs[8]);
			hod2000BatchClientError.setRoomRemark(errs[9]);
			
			hod2000BatchClientErrorService.update(hod2000BatchClientError);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	//错误信息清除
	public void doClear()
	{
		try {
			request = ServletActionContext.getRequest();
			out=ServletActionContext.getResponse().getWriter();
			hod2000BatchClientErrorService.executeUpdate("DELETE FROM Hod2000BatchClientError");
			out.write("{success:true}");
		} catch (Exception e) {
			msg.setError1("Hod2000BatchClientErrorAction-->doClear:"+e);
			out.write("{success:false}");
		}
	}
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000BatchClientErrorService(IHod2000BatchClientErrorService hod2000BatchClientErrorService) {
		this.hod2000BatchClientErrorService = hod2000BatchClientErrorService;
	}
 
	public Hod2000BatchClientError getHod2000BatchClientError() {
		return hod2000BatchClientError;
	}

	public void setHod2000BatchClientError(Hod2000BatchClientError hod2000BatchClientError) {
		this.hod2000BatchClientError = hod2000BatchClientError;
	}

	public IHod2000ClientService getHod2000ClientService() {
		return hod2000ClientService;
	}

	public void setHod2000ClientService(IHod2000ClientService hod2000ClientService) {
		this.hod2000ClientService = hod2000ClientService;
	}

	public IHod2000RoomService getHod2000RoomService() {
		return hod2000RoomService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}
}
