package  com.hod.pro.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000City;
import com.hod.pojo.Hod2000Community;
import com.hod.pojo.Hod2000Region;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Sysparameter;
import com.hod.pojo.Hod2000Unit;
import com.hod.pojo.Hod2000Village;
import com.hod.pro.model.service.IHod2000BuildingService;
import com.hod.pro.model.service.IHod2000CityService;
import com.hod.pro.model.service.IHod2000CommunityService;
import com.hod.pro.model.service.IHod2000CountyService;
import com.hod.pro.model.service.IHod2000RegionService;
import com.hod.pro.model.service.IHod2000RoomService;
import com.hod.pro.model.service.IHod2000SysparameterService;
import com.hod.pro.model.service.IHod2000UnitService;
import com.hod.pro.model.service.IHod2000VillageService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000AreaAction 城市管理
 * @author yixiang
 */
public class Hod2000CityAction extends ActionSupport {

	private HttpServletRequest request ;
	private IHod2000CityService hod2000CityService;
	private IHod2000CountyService hod2000CountyService;
	private IHod2000CommunityService hod2000CommunityService;
	private IHod2000BuildingService hod2000BuildingService;
	private IHod2000UnitService hod2000UnitService;
	private IHod2000RoomService hod2000RoomService;
	private IHod2000VillageService hod2000VillageService;
	private IHod2000RegionService hod2000RegionService;
	private IHod2000SysparameterService hod2000SysparameterService;
	private Hod2000City hod2000City;
	private List dataList;
	private PrintWriter out;
	private File fileName;
	private Logger log = Logger.getLogger(Hod2000CityAction.class.getName());
	
	public String doSave() {
		try {
			hod2000CityService.save(hod2000City);
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
				hod2000CityService.deleteByParam(ids.split(","));//注意主键类型
				return SUCCESS;
			}else{
				request.setAttribute("exceptions","ID IS NULL");
				return ERROR;
			}
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	//地址批量上传，从小区开始
	public void batchFileUp()
	{
		BufferedReader bfr = null;
		try {
			request= ServletActionContext.getRequest();
			ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
			String villageIdStr=request.getParameter("villageId");
			out = ServletActionContext.getResponse().getWriter();
			int villageId=0;
			if(villageIdStr!=null&&!"".equals(villageIdStr))
			{
				villageId=Integer.parseInt(villageIdStr);
			}
			else
			{
				out.write("{success:false,msg:'上传失败，选择的街道办为空!'}");
				return;
			}
			if (fileName != null) {
				FileInputStream fis = new FileInputStream(fileName); 
				BufferedInputStream bis = new BufferedInputStream(fis);                                                                                    
			    byte[] buf = new byte[10485760];   //10MB   
			    byte[] buf1;
			    int len = -1;   
			    String msg="";
		        while ((len = bis.read(buf)) != -1) {   
		    	   buf1 = new byte[len];
		    	   for (int i = 0; i < len; i++) 
		    		  buf1[i] = buf[i];   
		    	   msg = msg + new String(buf1);
		        }   
		        if(!"".equals(msg))
		        {
		        	//List<Hod2000Community> community=hod2000CommunityService.findByHQL("FROM Hod2000Community WHERE hod2000Village.villageId="+villageId);
		        	List<Hod2000Community> community=hod2000CommunityService.findCommunityVillageId(villageId);
		        	//List<Hod2000Building> building=hod2000BuildingService.findByHQL("FROM Hod2000Building");
		        	List<Hod2000Building> building=hod2000BuildingService.findAll(community);
		        	//List<Hod2000Unit> unit=hod2000UnitService.findByHQL("FROM Hod2000Unit");
		        	List<Hod2000Unit> unit=hod2000UnitService.findAll(building);
		        	//List<Hod2000Room> room=hod2000RoomService.findByHQL("FROM Hod2000Room");
		        	List<Hod2000Room> room=hod2000RoomService.findAll(unit);
		        	String line="";
		        	Hod2000Community hod2000Community = null;
		        	Hod2000Building hod2000Building = null;
		        	Hod2000Unit hod2000Unit = null;
		        	Hod2000Room hod2000Room = null;
		        	Hod2000Village hod2000Village=(Hod2000Village) hod2000VillageService.findById(villageId);
		        	String[] lines=msg.split("\r");
		        	int size=msg.split("\r").length;//换行符分割
		        	String[] tempLine=null;
		        	int num=0;//行数
		        	for (int i = 0; i < size; i++) 
		        	{
		        		num=i+1;
		        		line=lines[i].trim();//当前行
		        		String[] lineSplit=line.split(",");//当前行分割
		        		if(lineSplit.length==1)//最后一行为换行符时
		        		{
		        			continue;
		        		}
		        		if(lineSplit.length<6)
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，缺少数据!'}");
		        			return;
		        		}
		        		if(lineSplit[0]==null||"".equals(lineSplit[0]))
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，小区名称不能为空!'}");
		        			return;
		        		}
		        		if(lineSplit[1]==null||"".equals(lineSplit[1]))
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，楼栋名称不能为空!'}");
		        			return;
		        		}
		        		if(lineSplit[2]==null||"".equals(lineSplit[2]))
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，单元名称不能为空!'}");
		        			return;
		        		}
		        		if(lineSplit[3]==null||"".equals(lineSplit[3]))
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，房间名称不能为空!'}");
		        			return;
		        		}
		        		if(lineSplit[4]==null||"".equals(lineSplit[4]))
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，面积数不能为空!'}");
		        			return;
		        		}
		        		if(lineSplit[5]==null||"".equals(lineSplit[5]))
		        		{
		        			out.write("{success:false,msg:'第"+num+"行出现数据格式错误，户型不能为空!'}");
		        			return;
		        		}
		        		//判断小区是否存在
		        		boolean flag=false;
		        		
		        		//如果本行的小区与前一行相同，不需要在判断list
        				if(tempLine!=null&&tempLine[0].equals(lineSplit[0])){
        					flag=true;
							
        				}else{
        					for (int j = 0; j < community.size(); j++) {
    	        				hod2000Community=community.get(j);
    	        				
    							if(hod2000Community.getHod2000Village().getVillageId().intValue()==villageId&&hod2000Community.getCommunityName().equals(lineSplit[0]))
    							{
    								flag=true;
    								break;
    							}
    						}
        				}
		        		
	        			
	        			if(!flag)//如果数据库中不能能找到
	        			{
	        				//小区
	        				hod2000Community=new Hod2000Community();
	        				hod2000Community.setCommunityName(lineSplit[0]);
	        				hod2000Community.setCommunityCode(hod2000CommunityService.getCode(hod2000Village.getVillageId()));
	        				hod2000Community.setHod2000Village(hod2000Village);
	        				hod2000Community.setCommunityRemark(null);
	        				hod2000CommunityService.save(hod2000Community);
	        				community.add(hod2000Community);
	        				
	        				//楼栋
	        				hod2000Building=new Hod2000Building();
	        				hod2000Building.setBuildingName(lineSplit[1]);
	        				hod2000Building.setHod2000Community(hod2000Community);
	        				hod2000Building.setBuildingCode(hod2000BuildingService.getCode(hod2000Community.getCommunityId()));
	        				hod2000Building.setBuildingRemark(null);
	        				hod2000Building.setBuildingLatitude(null);
	        				hod2000Building.setBuildingLongitude(null);
	        				hod2000BuildingService.save(hod2000Building);
	        				building.add(hod2000Building);
	        				
	        				//单元
	        				hod2000Unit=new Hod2000Unit();
	        				hod2000Unit.setUnitName(lineSplit[2]);
	        				hod2000Unit.setHod2000Building(hod2000Building);
	        				hod2000Unit.setUnitCode(hod2000UnitService.getCode(hod2000Building.getBuildingId()));
	        				hod2000Unit.setUnitRemark(null);
	        				hod2000UnitService.save(hod2000Unit);
	        				unit.add(hod2000Unit);
	        				
	        				//房间
	        				hod2000Room=new Hod2000Room();
	        				hod2000Room.setRoomName(lineSplit[3]);
	        				hod2000Room.setRoomSize(Double.parseDouble(lineSplit[4]));
	        				hod2000Room.setRoomHouseType(lineSplit[5]);
	        				hod2000Room.setRoomCode(hod2000RoomService.getCode(hod2000Unit.getUnitId()));
	        				hod2000Room.setHod2000Unit(hod2000Unit);
	        				hod2000Room.setRoomRemark(null);
	        				hod2000Room.setHod2000Client(null);
	        				hod2000Room.setRoomPreFlag(0);
	        				hod2000Room.setRoomReceiveFlag(0);
	        				hod2000RoomService.save(hod2000Room);
	        				room.add(hod2000Room);
	        			}else{
	        				//判断楼栋是否存在
	        				flag=false;
	        				
	        				//如果本行的楼栋、小区与前一行相同，不需要在判断list
	        				if(tempLine!=null
	        						&&tempLine[0].equals(lineSplit[0])
	        						&&tempLine[1].equals(lineSplit[1])
	        						){
	        					flag=true;
								
	        				}else{
	        					for (int j = 0; j < building.size(); j++) {
			        				hod2000Building=building.get(j);
			        				
			        				if(hod2000Building.getBuildingName().equals(lineSplit[1])
			        						&&hod2000Building.getHod2000Community().getCommunityId().intValue()==hod2000Community.getCommunityId().intValue())
			        				{
			        					flag=true;
			        					break;
			        				}
								}
	        				}
	        					        				
		        			
		        			if(!flag)
		        			{
		        				//楼栋
		        				hod2000Building=new Hod2000Building();
		        				hod2000Building.setBuildingName(lineSplit[1]);
		        				hod2000Building.setHod2000Community(hod2000Community);
		        				hod2000Building.setBuildingCode(hod2000BuildingService.getCode(hod2000Community.getCommunityId()));
		        				hod2000Building.setBuildingRemark(null);
		        				hod2000Building.setBuildingLatitude(null);
		        				hod2000Building.setBuildingLongitude(null);
		        				hod2000BuildingService.save(hod2000Building);
		        				building.add(hod2000Building);
		        				
		        				//单元
		        				hod2000Unit=new Hod2000Unit();
		        				hod2000Unit.setUnitName(lineSplit[2]);
		        				hod2000Unit.setHod2000Building(hod2000Building);
		        				hod2000Unit.setUnitCode(hod2000UnitService.getCode(hod2000Building.getBuildingId()));
		        				hod2000Unit.setUnitRemark(null);
		        				hod2000UnitService.save(hod2000Unit);
		        				unit.add(hod2000Unit);
		        				
		        				//房间
		        				hod2000Room=new Hod2000Room();
		        				hod2000Room.setRoomName(lineSplit[3]);
		        				hod2000Room.setRoomSize(Double.parseDouble(lineSplit[4]));
		        				hod2000Room.setRoomHouseType(lineSplit[5]);
		        				hod2000Room.setRoomCode(hod2000RoomService.getCode(hod2000Unit.getUnitId()));
		        				hod2000Room.setHod2000Unit(hod2000Unit);
		        				hod2000Room.setRoomRemark(null);
		        				hod2000Room.setHod2000Client(null);
		        				hod2000Room.setRoomPreFlag(0);
		        				hod2000Room.setRoomReceiveFlag(0);
		        				hod2000RoomService.save(hod2000Room);
		        				room.add(hod2000Room);
		        			}
		        			else
		        			{
		        				//判断单元是否存在
			        			flag=false;
			        			
			        			//如果本行的楼栋、小区、单元与前一行相同，不需要在判断list
		        				if(tempLine!=null
		        						&&tempLine[0].equals(lineSplit[0])
		        						&&tempLine[1].equals(lineSplit[1])
		        						&&tempLine[2].equals(lineSplit[2])
		        						){
		        					flag=true;
									
		        				}else{
		        					for (int j = 0; j < unit.size(); j++) {
				        				hod2000Unit=unit.get(j);

				        				if(hod2000Unit.getUnitName().equals(lineSplit[2])&&hod2000Unit.getHod2000Building().getBuildingId().intValue()==hod2000Building.getBuildingId().intValue())
				        				{
				        					flag=true;
				        					break;
				        				}
									}
		        				}
			        			
			        			
			        			if(!flag)
			        			{
			        				//单元
			        				hod2000Unit=new Hod2000Unit();
			        				hod2000Unit.setUnitName(lineSplit[2]);
			        				hod2000Unit.setHod2000Building(hod2000Building);
			        				hod2000Unit.setUnitCode(hod2000UnitService.getCode(hod2000Building.getBuildingId()));
			        				hod2000Unit.setUnitRemark(null);
			        				hod2000UnitService.save(hod2000Unit);
			        				unit.add(hod2000Unit);
			        				
			        				//房间
			        				hod2000Room=new Hod2000Room();
			        				hod2000Room.setRoomName(lineSplit[3]);
			        				hod2000Room.setRoomSize(Double.parseDouble(lineSplit[4]));
			        				hod2000Room.setRoomHouseType(lineSplit[5]);
			        				hod2000Room.setRoomCode(hod2000RoomService.getCode(hod2000Unit.getUnitId()));
			        				hod2000Room.setHod2000Unit(hod2000Unit);
			        				hod2000Room.setRoomRemark(null);
			        				hod2000Room.setHod2000Client(null);
			        				hod2000Room.setRoomPreFlag(0);
			        				hod2000Room.setRoomReceiveFlag(0);
			        				hod2000RoomService.save(hod2000Room);
			        				room.add(hod2000Room);
			        			}
			        			else
			        			{
			        				//判断房间是否存在
				        			flag=false;
				        			for (int j = 0; j < room.size(); j++) {
				        				hod2000Room=room.get(j);
				        				if(hod2000Room.getRoomName().equals(lineSplit[3])&&hod2000Room.getHod2000Unit().getUnitId().intValue()==hod2000Unit.getUnitId().intValue())
				        				{
				        					flag=true;
				        					break;
				        				}
				        			}
				        			if(!flag)
				        			{
				        				//房间
				        				hod2000Room=new Hod2000Room();
				        				hod2000Room.setRoomName(lineSplit[3]);
				        				hod2000Room.setRoomSize(Double.parseDouble(lineSplit[4]));
				        				hod2000Room.setRoomHouseType(lineSplit[5]);
				        				hod2000Room.setRoomCode(hod2000RoomService.getCode(hod2000Unit.getUnitId()));
				        				hod2000Room.setHod2000Unit(hod2000Unit);
				        				hod2000Room.setRoomRemark(null);
				        				hod2000Room.setHod2000Client(null);
				        				hod2000Room.setRoomPreFlag(0);
				        				hod2000Room.setRoomReceiveFlag(0);
				        				hod2000RoomService.save(hod2000Room);
				        				room.add(hod2000Room);
				        			}
				        			else
				        			{
				        				System.out.println("地址已存在，地址："+line);
				        				//continue;
				        			}
			        			}
		        			}
	        			}
	        			 tempLine=line.split(",");//当前行分割
	        			
		        	}
		        	out.write("{success:true}");
		        }
		        else
		        {
		        	out.write("{success:false,msg:'文件为空!'}");
		        }
			}
			else
			{
				out.write("{success:false,msg:'文件无效!'}");
			}
		} catch (Exception e) {
			log.error("Hod2000CityAction-->batchFileUp:"+e);
			out.write("{success:false,msg:'上传失败'}");
			e.printStackTrace();
		} finally{
			if(bfr!=null){
				try {
					bfr.close();
				} catch (IOException e) {
					log.error("IOException : " + e.getMessage());
					out.write("{success:false,msg:'上传失败'}");
				}
			}
		}
	}
	
	//地址树加载
	public void doTree()
	{
		try {
			request= ServletActionContext.getRequest();
			out = ServletActionContext.getResponse().getWriter();
			List list;
			List list2 = null;
			String checkBox=request.getParameter("checkBox");//判断是否显示复选框
			String codeString=request.getParameter("code");
			String typeString=request.getParameter("type");
			String idString=request.getParameter("id");
			String meters=request.getParameter("meters");
			String rooms=request.getParameter("rooms");
			int id=0;
			if(typeString==null||typeString.equals(""))
			{
				typeString="0";
			}
			if(idString!=null&&!"".equals(idString)&&!"undefined".equals(idString)&&!"-1".equals(idString))
			{
				id=Integer.parseInt(idString);
			}
			String addressCode="";
			List params=hod2000SysparameterService.findByType(10);
			if(params.size()>0)
			{
				addressCode=((Hod2000Sysparameter)params.get(0)).getPvalue();
				//默认地址为省级
				if(addressCode.length()==2)
				{
					if(typeString.equals("0"))
					{
						list=hod2000RegionService.findByCode(addressCode);
						if (list.size()>0) {
							list2=setList(checkBox, list, addressCode.substring(0,2), 1,false,true);
						}
					}
					if(typeString.equals("1"))
					{
						list=hod2000CityService.findByRegionId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 2,false,true);
						}
					}
					if(typeString.equals("2"))
					{
						list=hod2000CountyService.findByCityId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 3,false,false);
						}
					}
					if(typeString.equals("3"))
					{
						list=hod2000VillageService.findByCountyId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 4,false,false);
						}
					}
				}
				//默认地址为市级
				if(addressCode.length()==4)
				{
					if(typeString.equals("0"))
					{
						list=hod2000CityService.findByCityCode(addressCode);
						if (list.size()>0) {
							list2=setList(checkBox, list, addressCode.substring(0, 2), 2,false,true);
						}
					}
					if(typeString.equals("2"))
					{
						list=hod2000CountyService.findByCityId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 3,false,false);
						}
					}
					if(typeString.equals("3"))
					{
						list=hod2000VillageService.findByCountyId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 4,false,false);
						}
					}
				}
				//默认地址为区级
				if(addressCode.length()==6)
				{
					if(typeString.equals("0"))
					{
						list=hod2000CountyService.findByCountyCode(addressCode);
						if (list.size()>0) {
							list2=setList(checkBox, list, addressCode.substring(0, 4), 3,false,false);
						}
					}
					if(typeString.equals("3"))
					{
						list=hod2000VillageService.findByCountyId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 4,false,false);
						}
					}
				}
				//默认地址为街道办级
				if(addressCode.length()==9)
				{
					if(typeString.equals("0"))
					{
						list=hod2000VillageService.findByVillageCode(addressCode);
						if (list.size()>0) {
							list2=setList(checkBox, list, addressCode.substring(0, 6), 4,false,true);
						}
					}
				}
				if(typeString.equals("4"))
				{
					list=hod2000CommunityService.findByVillageId(id);
					if (list.size()>0) {
						list2=setList(checkBox, list, codeString, 5,false,true);
					}
				}
				if(typeString.equals("5"))
				{
					list=hod2000BuildingService.findByCommunityId(id);
					if (list.size()>0) {
						//list2=setList(checkBox, list, codeString, 6,false);
						list2=new ArrayList();
						Map map;
						Object[] object;
						for (int i = 0; i < list.size(); i++) {
							map=new HashMap();
							object=(Object[]) list.get(i);
							map.put("text", object[1]);
							map.put("leaf", false);
							map.put("qtip", object[0]);
							map.put("lng", object[2]);
							map.put("lat", object[3]);
							map.put("type", 6);
							map.put("code", codeString+object[4]);
							if(checkBox.equals("true"))
								map.put("checked", false);
							map.put("expanded", false);
							list2.add(map);
						}
					}
				}
				if(typeString.equals("6"))
				{
					list=hod2000UnitService.findByBuildingId(id);
					if (list.size()>0) {
						if(rooms!=null&&"true".equals(rooms))
							list2=setList(checkBox, list, codeString, 10,false,false);
						else
							list2=setList(checkBox, list, codeString, 10,true,false);
					}
				}
				if(rooms!=null&&"true".equals(rooms))
				{
					if(typeString.equals("10"))
					{
						if(meters!=null&&"false".equals(meters))
							list=hod2000RoomService.findRoomsByBuildingId(id);//查询
						else
							list=hod2000RoomService.findByBuildingId(id);
						if (list.size()>0) {
							list2=setList(checkBox, list, codeString, 7,true,false);
						}
					}
				}
			}
			out.write(JSONArray.fromObject(list2).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List setList(String checkBox,List list,String codeString,int type,boolean leaf,boolean expanded)
	{
		List list2=new ArrayList();
		Map map;
		Object[] object;
		for (int i = 0; i < list.size(); i++) {
			map=new HashMap();
			object=(Object[]) list.get(i);
			map.put("text", object[1]);
			map.put("leaf", leaf);
			map.put("qtip", object[0]);
			map.put("type", type);
			map.put("code", codeString+object[2]);
			if(checkBox.equals("true"))
				map.put("checked", false);
			map.put("expanded", expanded);
			list2.add(map);
		}
		return list2;
	}
    
	public String toUpdate() {
		try {
			hod2000City = (Hod2000City) hod2000CityService.findById(request.getParameter("id"));
			return "toUpdate";
		} catch (Exception e) {
			return ERROR;
		}
	}  

	public String doUpdate() {
		try {
			hod2000CityService.update(hod2000City);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
    
	public String doSelect() {
		try {
			request=ServletActionContext.getRequest();
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000City.class);
			String regionId=request.getParameter("regionId");
			if(regionId!=null&&!regionId.equals(""))
			{
				dc.add(Restrictions.eq("hod2000Region.regionId", Integer.parseInt(regionId)));
			}
			Hod2000Region region = (Hod2000Region)hod2000RegionService.findById(Integer.parseInt(regionId));
			request.setAttribute("regionName", region.getRegionName());
			request.setAttribute("regionId", regionId);
			dataList = Page.util(request, hod2000CityService,dc);
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
	
    
	public void sethod2000CityService(IHod2000CityService hod2000CityService) {
		this.hod2000CityService = hod2000CityService;
	}
 
	public Hod2000City gethod2000City() {
		return hod2000City;
	}

	public void sethod2000City(Hod2000City hod2000City) {
		this.hod2000City = hod2000City;
	}

	public IHod2000CountyService gethod2000CountyService() {
		return hod2000CountyService;
	}

	public void sethod2000CountyService(IHod2000CountyService hod2000CountyService) {
		this.hod2000CountyService = hod2000CountyService;
	}

	public IHod2000CommunityService getHod2000CommunityService() {
		return hod2000CommunityService;
	}

	public void setHod2000CommunityService(
			IHod2000CommunityService hod2000CommunityService) {
		this.hod2000CommunityService = hod2000CommunityService;
	}

	public IHod2000BuildingService getHod2000BuildingService() {
		return hod2000BuildingService;
	}

	public void setHod2000BuildingService(IHod2000BuildingService hod2000BuildingService) {
		this.hod2000BuildingService = hod2000BuildingService;
	}

	public IHod2000RoomService getHod2000RoomService() {
		return hod2000RoomService;
	}

	public void setHod2000RoomService(IHod2000RoomService hod2000RoomService) {
		this.hod2000RoomService = hod2000RoomService;
	}

	public IHod2000CityService gethod2000CityService() {
		return hod2000CityService;
	}

	public IHod2000VillageService getHod2000VillageService() {
		return hod2000VillageService;
	}

	public void setHod2000VillageService(IHod2000VillageService hod2000VillageService) {
		this.hod2000VillageService = hod2000VillageService;
	}

	public IHod2000RegionService getHod2000RegionService() {
		return hod2000RegionService;
	}

	public void setHod2000RegionService(IHod2000RegionService hod2000RegionService) {
		this.hod2000RegionService = hod2000RegionService;
	}

	public void setHod2000UnitService(IHod2000UnitService hod2000UnitService) {
		this.hod2000UnitService = hod2000UnitService;
	}

	public void setHod2000SysparameterService(
			IHod2000SysparameterService hod2000SysparameterService) {
		this.hod2000SysparameterService = hod2000SysparameterService;
	}

	public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	
	
	
}
