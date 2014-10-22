package  com.hod.pro.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import java.util.List;
import com.hod.pojo.Hod2000Region;
import com.hod.pro.model.service.IHod2000RegionService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Hod2000RegionAction 省份管理
 * @author yixiang
 */
public class Hod2000RegionAction extends ActionSupport {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private IHod2000RegionService hod2000RegionService;
	private Hod2000Region hod2000Region;
	private List dataList;
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Region.class);
			dataList = Page.util(request, hod2000RegionService,  dc);
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
	
    
	public void setHod2000RegionService(IHod2000RegionService hod2000RegionService) {
		this.hod2000RegionService = hod2000RegionService;
	}
 
	public Hod2000Region getHod2000Region() {
		return hod2000Region;
	}

	public void setHod2000Region(Hod2000Region hod2000Region) {
		this.hod2000Region = hod2000Region;
	}
}
