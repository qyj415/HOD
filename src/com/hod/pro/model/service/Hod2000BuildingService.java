package com.hod.pro.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Community;
import com.hod.pro.model.dao.IHod2000BuildingDAO;
import com.hod.pro.model.dao.IHod2000CommunityDAO;

/**
 * @author yixiang
 */
 
public class Hod2000BuildingService extends Service implements IHod2000BuildingService {

	private IHod2000BuildingDAO buildingDAO;
	private IHod2000CommunityDAO communityDAO;
	
	
	public void setBuildingDAO(IHod2000BuildingDAO buildingDAO) {
		this.buildingDAO = buildingDAO;
	}

	public void setCommunityDAO(IHod2000CommunityDAO communityDAO) {
		this.communityDAO = communityDAO;
	}

	public List findByLngLat(String lng1, String lng2, String lat1, String lat2) {
		return buildingDAO.findByLngLat(lng1,lng2,lat1,lat2);
	}


	public List findByCommunityId(int id) {
		return buildingDAO.findByCommunityId(id);
	}


	public List findByBuildingId(int buildingId) {
		//return genericDAO.findByNHQL("select meter_name,meter_position_name,valve_status,battery_status,isonline from meter_address where meter_able=1 and building_id="+buildingId+" and (valve_status=2 or battery_status=1 or isonline=2)");
		return genericDAO.findByNHQL("select meter_name,meter_position_name,valve_status,battery_status,isonline,eeprom_status,flowsensor_status,tepdown_status,tepup_status from meter_address where meter_able=1 and building_id="+buildingId+" and (battery_status=1 or isonline=2 or eeprom_status=1 or flowsensor_status=1 or tepdown_status=1 or tepup_status=1)");
	}


	public int findByBuildingName(Integer communityId, String buildingName) {
		return Integer.parseInt(buildingDAO.findByNHQL("select count(*) from hod2000_building where community_id="+communityId+" and building_name='"+buildingName+"'").get(0).toString());
	}


	public int findByBuildingName(Integer communityId, String buildingName,
			Integer buildingId) {
		return Integer.parseInt(buildingDAO.findByNHQL("select count(*) from hod2000_building where community_id="+communityId+" and building_name='"+buildingName+"' and building_id!="+buildingId).get(0).toString());
	}


	public List getLnglat(String conAddress) {
		String sql="select distinct building_id,building_longitude,building_latitude,building_name from address_con where addressCode='"+conAddress+"'";
		return buildingDAO.findByNHQL(sql);
		//return buildingDAO.findByNHQL("select distinct building_id,building_longitude,building_latitude,building_name from address_con where region_code+city_code+county_code+village_code+community_code+building_code='"+conAddress+"'");
	}


	public List findConEventByCode(String buildingCode) {
		return buildingDAO.findByHQL("select distinct conNumber,conPositionName,conFlashStatus,conIsonline,conCom1Status,conCom2Status,conCom3Status,conStrong from ConEventMap where conAddress like concat(regionCode,cityCode,countyCode,villageCode,communityCode,'"+buildingCode+"%') and (conFlashStatus=1 or conIsonline=2 or conCom1Status=1 or conCom2Status=1 or conCom3Status=1 or conStrong=0)");
		//return buildingDAO.findByNHQL("select distinct con_number,con_position_name,con_flash_status,con_isonline,con_com1_status,con_com2_status,con_com3_status from address_con where con_address like region_code+city_code+county_code+village_code+community_code+'"+buildingCode+"%' and (con_flash_status=1 or con_isonline=2 or con_com1_status=1 or con_com2_status=1 or con_com3_status=1)");
	}


	public String getCode(int communityId) {
		//行政编码 自动生成
		String hql = "from Hod2000Building WHERE hod2000Community.communityId="+communityId;
		List<Hod2000Building> lists = genericDAO.findByHQL(hql);
		Collections.sort(lists);
		String saveCode = "";
		int buildingCode =0;
		if(lists.size()>0)
			buildingCode=Integer.parseInt(lists.get(0).getBuildingCode());
		if(buildingCode<999){
			buildingCode++;
			saveCode = String.valueOf(buildingCode);
		}else{
			for(int i=lists.size()-1;i>=0;i--){
				buildingCode = Integer.parseInt(lists.get(i).getBuildingCode());
				if((i==lists.size()-1)&&(buildingCode-1>0)){
					buildingCode--;
					saveCode = String.valueOf(buildingCode);
					break;
				}
				int buildingCodeNext = Integer.parseInt(lists.get(i-1).getBuildingCode());
				if((buildingCodeNext-buildingCode)>1){
					buildingCode = --buildingCodeNext;
					saveCode = String.valueOf(buildingCode);
					break;
				}
			}
		}
		if(saveCode.length()==1){
			saveCode = "00"+saveCode;
		}else if(saveCode.length()==2){
			saveCode = "0"+saveCode;
		}
		return saveCode;
	}


	public List<Hod2000Building> findAll(List<Hod2000Community> hod2000Community) {
		List list=genericDAO.findByNHQL("select building_id,building_name,community_id from hod2000_building");
		Object[] obj;
		Hod2000Building building;
		Hod2000Community community;
		List<Hod2000Building> list2=new ArrayList<Hod2000Building>();
		for (int i = 0; i < list.size(); i++) {
			obj=(Object[]) list.get(i);
			building=new Hod2000Building();
			building.setBuildingId(Integer.parseInt(obj[0].toString()));
			building.setBuildingName(obj[1].toString());
			for (int j = 0; j < hod2000Community.size(); j++) {
				community=hod2000Community.get(j);
				if(community.getCommunityId()==Integer.parseInt(obj[2].toString()))
				{
					building.setHod2000Community(community);
					list2.add(building);
					break;
				}
			}
			
		}
		return list2;
	}
 

}
