package com.hod.pro.web.action;

import net.sf.json.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.hod.pro.model.service.IService;
public class MyJsonOut {
	private JSONArray array;
	private String jsonString;

	public void getfiltervalue(String modulename,IService server,HttpServletRequest request,PrintWriter out ,String sql) throws IOException{
		List list;
		List list2 ;
		Object[] objects;
		Map map ;
		try {
			list = server.findByNHQL(sql);
			list2=  new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			map = new HashMap();  
			try {
				objects=(Object[])list.get(i);
				map.put("value", objects[0]);
				map.put("text", objects[1]);
			} catch (Exception e) {
				map.put("value", list.get(i).toString());
				map.put("text", list.get(i).toString());
			}
			list2.add(map);
		}
		OutByList(modulename,
				list2,
				out);
		} catch (Exception e) {
			System.out.println(modulename + "  " +e.toString());
		}
		list = null;
		list2 = null;
		objects = null;
		map = null;
	}
	
	public  String toMD5Encode(String s)
    {
		String a[] = {
		        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
		        "a", "b", "c", "d", "e", "f"
		    };
    	Object obj = new StringBuffer();
    	try{
    		byte[] byteArray = ((MessageDigest) ( MessageDigest.getInstance("MD5"))).digest(s.getBytes());
        
        int i = 0;
        do
        {
            if(i >= byteArray.length)
                break;
            int j = byteArray[i];
            if(j < 0)
                j += 256;
            int k = j / 16;
            ((StringBuffer) (obj)).append(a[k] + a[j %= 16]);
            i++;
        }
        while(true);
    	}
    	catch(Exception e){
    		
    	}
    	String resultString =obj.toString();
    	obj = null;
        return  resultString;
    }
	public List JSONArrayToList(String msg)
	{
		 JSONArray ja = JSONArray.fromObject(msg);
		return JSONArray.toList(ja);
	}
	public void OutByObject(String modulename,Object Listobject,String Item,PrintWriter out ) {
		
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  "{"  + Item + ":" + this.array.toString() + "}"; 
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	public void OutByObjectResult(String modulename,Object Listobject,String Item,PrintWriter out ) {
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  "success:true,datas:{"  + Item + ":" + this.array.toString() + "}"; 
		jsonString = "{" + jsonString + "}";
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	
	public void OutByObject(String modulename,Object Listobject,PrintWriter out ) {
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  this.array.toString();
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	public void OutByObjectResult(String modulename,Object Listobject,PrintWriter out ) {
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  "success:true,datas:"  + this.array.toString()+"";
		jsonString = "{" + jsonString + "}";
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	
	/**
	 * 
	 * @param modulename
	 * @param Listobject
	 * @param count 总记录条数
	 * @param page 当前页数
	 * @param pageSize 每页显示条数
	 * @param out
	 */
	public void OutByObject(String modulename,Object Listobject,Integer count,Integer page,Integer pageSize,PrintWriter out ) {
		int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1; //总页数
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  "{\"totalCount\":" +  String.valueOf(count)  + ",\"total\":" +  String.valueOf(totalPage)  + ",\"page\":" +  String.valueOf(page)  + ",\"result\":"  + this.array.toString()+"}";
		//System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	public void OutByList(String modulename,List Listobject,PrintWriter out ) {
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  "{totalCount:" +  String.valueOf(Listobject.size())  + ",result:"  + this.array.toString()+"}";
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	public void OutByListResult(String modulename,List Listobject,PrintWriter out ) {
		this. array = JSONArray.fromObject(Listobject);
		this. jsonString =  "success:true,datas:"  + this.array.toString();
		jsonString = "{" + jsonString + "}";
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	
	public void OutByString(String modulename,String OutString,PrintWriter out ){
		this. jsonString =  "{"  + OutString + "}";
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	public void OutByStringResult(String modulename,String OutString,Boolean signBoolean,PrintWriter out ){
		
				
		if (signBoolean){
			if (OutString!=null && ! OutString.equals(""))
				this. jsonString =  "success:true,datas:"  + OutString + "";
			else 
				this. jsonString =  "success:true";
		}else{
			if (OutString!=null && ! OutString.equals(""))
				this. jsonString =  "success:false,errors:{info:'"  + OutString + "'}";
			else 
				this. jsonString =  "success:false";
		}
		jsonString = "{" + jsonString + "}";
		System.out.println(modulename + "  " +jsonString);
		out.write(jsonString);
	}
	
	
}
