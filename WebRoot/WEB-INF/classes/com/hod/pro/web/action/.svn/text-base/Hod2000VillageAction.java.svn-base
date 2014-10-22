package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import com.hod.pojo.Hod2000Village;
import com.hod.pojo.Hod2000County;
import com.hod.pro.model.service.IHod2000VillageService;
import com.hod.pro.model.service.IHod2000CountyService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000VillageAction 城乡/街道办管理
 * @author yixiang
 */
public class Hod2000VillageAction extends ActionSupport {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private IHod2000VillageService hod2000VillageService;
    private IHod2000CountyService hod2000CountyService;
	private Hod2000Village hod2000Village;
	private List dataList;
	
	public String doSave() {
		try {
			hod2000VillageService.save(hod2000Village);
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
				hod2000VillageService.deleteByParam(ids.split(","));//注意主键类型
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
			hod2000Village = (Hod2000Village) hod2000VillageService.findById(request.getParameter("id"));
			return "toUpdate";
		} catch (Exception e) {
			return ERROR;
		}
	}  

	public String doUpdate() {
		try {
			hod2000VillageService.update(hod2000Village);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			String countyId=request.getParameter("countyId");
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Village.class);
			if(countyId!=null&&!countyId.equals(""))
			{
				dc.add(Restrictions.eq("hod2000County.countyId", Integer.parseInt(countyId)));
			}
			Hod2000County county = (Hod2000County)hod2000CountyService.findById(Integer.parseInt(countyId));
			dataList = Page.util(request, hod2000VillageService, dc);
			request.setAttribute("countyId", countyId);
			request.setAttribute("countyName", county.getCountyName());
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}    
    
    	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
    
	public void setHod2000VillageService(IHod2000VillageService hod2000VillageService) {
		this.hod2000VillageService = hod2000VillageService;
	}
	
	public void setHod2000CountyService(IHod2000CountyService hod2000CountyService) {
		this.hod2000CountyService = hod2000CountyService;
	}
	
	public Hod2000Village getHod2000Village() {
		return hod2000Village;
	}

	public void setHod2000Village(Hod2000Village hod2000Village) {
		this.hod2000Village = hod2000Village;
	}
}
