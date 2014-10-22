package com.hod.pro.web.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.criterion.DetachedCriteria;
import com.hod.pro.model.service.IService;




/**
 * 分页类 
 * @author yixiang
 *
 */
public class Page{

	static public int PAGESIZE = 20;
	
	/**
	 * 多条件分页公共解决方案
	 * @param request
	 * @param service
	 * @param dc
	 * @param reultName
	 * @param pagename
	 * @param param
	 * @param pagesize
	 * @throws Exception
	 */
	public static List util(HttpServletRequest request,IService service, DetachedCriteria dc) throws Exception {
		//int PAGESIZE=10;
		String currpage = request.getParameter("currpage");//获得当前页
		String pageSize=request.getParameter("pagesize");
		if(pageSize!=null&&pageSize.length()>0)
		{
			PAGESIZE=Integer.parseInt(pageSize);
		}
		if (currpage == null) {currpage = "1";}
		List list =service.findByCriteria(Integer.parseInt(currpage), PAGESIZE, dc);
		int pagecount = service.getRowCount(dc);//求出满足条件的总行数
		request.setAttribute("currpage", currpage);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("pagesize", PAGESIZE);
		request.setAttribute("totalPage", pagecount % PAGESIZE > 0 ? pagecount / PAGESIZE + 1: pagecount / PAGESIZE);
		return list;
	}
	
	/**
	 * 根据sql语句分页查询
	 * @param request
	 * @param service
	 * @param sql
	 * @param countSql
	 * @return
	 * @throws Exception
	 */
	public static List findBySql(HttpServletRequest request,IService service, String sql,String countSql) throws Exception {
		//int PAGESIZE=10;
		String currpage = request.getParameter("currpage");//获得当前页
		String pageSize=request.getParameter("pagesize");
		if(pageSize!=null&&pageSize.length()>0)
		{
			PAGESIZE=Integer.parseInt(pageSize);
		}
		if (currpage == null) {currpage = "1";}
		List list =service.findByNHQL(Integer.parseInt(currpage), PAGESIZE, sql);
		int pagecount = Integer.parseInt(service.findByNHQL(countSql).get(0).toString());//求出满足条件的总行数
		request.setAttribute("currpage", currpage);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("pagesize", PAGESIZE);
		request.setAttribute("totalPage", pagecount % PAGESIZE > 0 ? pagecount / PAGESIZE + 1: pagecount / PAGESIZE);
		return list;
	}
	
	public static List findBySql(HttpServletRequest request,IService service, String sql) throws Exception {
		//int PAGESIZE=10;
		String currpage = request.getParameter("currpage");//获得当前页
		String pageSize=request.getParameter("pagesize");
		if(pageSize!=null&&pageSize.length()>0)
		{
			PAGESIZE=Integer.parseInt(pageSize);
		}
		if (currpage == null) {currpage = "1";}
		List list =service.findByNHQL(Integer.parseInt(currpage), PAGESIZE, sql);
		int pagecount = service.findByNHQL(sql).size();//求出满足条件的总行数
		request.setAttribute("currpage", currpage);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("pagesize", PAGESIZE);
		request.setAttribute("totalPage", pagecount % PAGESIZE > 0 ? pagecount / PAGESIZE + 1: pagecount / PAGESIZE);
		return list;
	}
	
	/**
	 * get方式提交乱码解决方案
	 * @param map
	 * @param key
	 * @param value
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String putKey(Map map,String key,String value,HttpServletRequest request) throws UnsupportedEncodingException{
		map.put(key, value);
		if (request.getMethod().equals("GET")) {
			String var = new String(value.getBytes("ISO-8859-1"),"UTF-8");
			map.put(key, var);
			return var;
		}else{
			return value;
		} 
	}


	/**
	 * get方式编码
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertKey(String key) throws UnsupportedEncodingException{
		return new String(key.getBytes("iso-8859-1"),"UTF-8");
	}

 
	
}
