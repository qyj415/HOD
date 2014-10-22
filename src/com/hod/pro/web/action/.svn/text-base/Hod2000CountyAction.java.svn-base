package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import com.hod.pojo.Hod2000City;
import com.hod.pojo.Hod2000County;
import com.hod.pro.model.service.IHod2000CityService;
import com.hod.pro.model.service.IHod2000CountyService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000CountyAction 区县管理
 * @author yixiang
 */
public class Hod2000CountyAction extends ActionSupport {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private IHod2000CountyService hod2000CountyService;
	private IHod2000CityService hod2000CityService;
	private Hod2000County hod2000County;
	private List dataList;
	
	public String doSave() {
		try {
			hod2000CountyService.save(hod2000County);
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
				hod2000CountyService.deleteByParam(ids.split(","));//注意主键类型
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
			hod2000County = (Hod2000County) hod2000CountyService.findById(request.getParameter("id"));
			return "toUpdate";
		} catch (Exception e) {
			return ERROR;
		}
	}  

	public String doUpdate() {
		try {
			hod2000CountyService.update(hod2000County);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000County.class);
			String cityId=request.getParameter("cityId");
			if(cityId!=null&&!cityId.equals(""))
			{
				dc.add(Restrictions.eq("hod2000City.cityId", Integer.parseInt(cityId)));
			}
			Hod2000City city = (Hod2000City)hod2000CityService.findById(Integer.parseInt(cityId));
			request.setAttribute("cityName", city.getCityName());
			request.setAttribute("cityId", cityId);
			dataList = Page.util(request, hod2000CountyService,dc);
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
	
    
	public void setHod2000CountyService(IHod2000CountyService hod2000CountyService) {
		this.hod2000CountyService = hod2000CountyService;
	}
	
	public void setHod2000CityService(IHod2000CityService hod2000CityService){
		this.hod2000CityService = hod2000CityService;
	}
 
	public Hod2000County getHod2000County() {
		return hod2000County;
	}

	public void setHod2000County(Hod2000County hod2000County) {
		this.hod2000County = hod2000County;
	}
}
