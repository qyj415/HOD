package com.hod.pro.model.service;

import java.util.ArrayList;
import java.util.List;

import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Community;
import com.hod.pojo.Hod2000Unit;
import com.hod.pro.model.dao.IHod2000BuildingDAO;

/**
 * @author JSmart Tools
 */
 
public class Hod2000UnitService extends Service implements IHod2000UnitService {
	private IHod2000BuildingDAO buildingDAO;
	
	public void setBuildingDAO(IHod2000BuildingDAO buildingDAO) {
		this.buildingDAO = buildingDAO;
	}

	public List findByBuildingId(int id) {
		return genericDAO.findByNHQL("select unit_id,unit_name,unit_code from hod2000_unit where building_id="+id);
	}

	public int findByUnitName(Integer buildingId, String unitName,
			Integer unitId) {
		return Integer.parseInt(genericDAO.findByNHQL("select count(*) from hod2000_unit where building_id="+buildingId+" and unit_name='"+unitName+"' and unit_id!="+unitId).get(0).toString());
	}

	public int findByRoomName(Integer buildingId, String unitName) {
		return Integer.parseInt(genericDAO.findByNHQL("select count(*) from hod2000_unit where building_id="+buildingId+" and unit_name='"+unitName+"'").get(0).toString());
	}

	public List findByUnitId(int id) {
		return genericDAO.findByNHQL("select unit_id,unit_name from hod2000_unit where building_id="+id);
	}

	public String getCode(int buildingId) {
		//行政编码 自动生成
		String hql = "from Hod2000Unit where hod2000Building.buildingId="+buildingId+" order by unitCode desc";
		List<Hod2000Unit> lists = genericDAO.findByHQL(hql);
		String saveCode = "";
		int unitCode =0; 
		if(lists.size()>0)
			unitCode=Integer.parseInt(lists.get(0).getUnitCode());
		if(unitCode<999){
			unitCode++;
			saveCode = String.valueOf(unitCode);
		}else{
			for(int i=lists.size()-1;i>=0;i--){
				unitCode = Integer.parseInt(lists.get(i).getUnitCode());
				if((i==lists.size()-1)&&(unitCode-1>0)){
					unitCode--;
					saveCode = String.valueOf(unitCode);
					break;
				}
				int roomCodeNext = Integer.parseInt(lists.get(i-1).getUnitCode());
				if((roomCodeNext-unitCode)>1){
					unitCode = --roomCodeNext;
					saveCode = String.valueOf(unitCode);
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

	public List<Hod2000Unit> findAll(List<Hod2000Building> hod2000Building) {
		List list=genericDAO.findByNHQL("select unit_id,unit_name,building_id from hod2000_unit");
		Object[] obj;
		Hod2000Unit hod2000Unit;
		Hod2000Building building;
		List<Hod2000Unit> list2=new ArrayList<Hod2000Unit>();
		for (int i = 0; i < list.size(); i++) {
			obj=(Object[]) list.get(i);
			hod2000Unit=new Hod2000Unit();
			hod2000Unit.setUnitId(Integer.parseInt(obj[0].toString()));
			hod2000Unit.setUnitName(obj[1].toString());
			for (int j = 0; j < hod2000Building.size(); j++) {
				building=hod2000Building.get(j);
				if(building.getBuildingId()==Integer.parseInt(obj[2].toString()))
				{
					hod2000Unit.setHod2000Building(building);
					list2.add(hod2000Unit);
					break;
				}
			}
			
		}
		return list2;
	}
 

}
