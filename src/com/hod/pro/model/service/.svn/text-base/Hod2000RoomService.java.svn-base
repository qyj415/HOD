package com.hod.pro.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Unit;
import com.hod.pro.model.dao.IHod2000RoomDAO;
import com.hod.pro.model.dao.IHod2000UnitDAO;

/**
 * @author yixiang
 */
 
public class Hod2000RoomService extends Service implements IHod2000RoomService {

	private IHod2000RoomDAO hod2000RoomDAO;
	private IHod2000UnitDAO hod2000UnitDAO;
	
	public void setHod2000RoomDAO(IHod2000RoomDAO hod2000RoomDAO) {
		this.hod2000RoomDAO = hod2000RoomDAO;
	}
	
	public void setHod2000UnitDAO(IHod2000UnitDAO hod2000UnitDAO) {
		this.hod2000UnitDAO = hod2000UnitDAO;
	}

	public List findMeterByRoomId(String ids) {
		return hod2000RoomDAO.findMeterByRoomId(ids);
	}
	public List findByBuildingId(int id) {
		return hod2000RoomDAO.findByBuildingId(id);
	}
	public List findByClientId(int clientId) {
		return hod2000RoomDAO.findByClientId(clientId);
	}
	public int findByRoomName(Integer unitId, String roomName) {
		return Integer.parseInt(hod2000RoomDAO.findByNHQL("select count(*) from hod2000_room where unit_id="+unitId+" and room_name='"+roomName+"'").get(0).toString());
	}
	public int findByRoomName(Integer unitId, String roomName,
			Integer roomId) {
		return Integer.parseInt(hod2000RoomDAO.findByNHQL("select count(*) from hod2000_room where unit_id="+unitId+" and room_name='"+roomName+"' and room_id!="+roomId).get(0).toString());
	}
	public List findRoomsByBuildingId(int id) {
		return genericDAO.findByNHQL("select room_id,room_name,room_code from hod2000_room where unit_id="+id+" and room_id not in (select room_id from hod2000_meter where room_id is not null)");
	}
	public String getCode(int unitId) {
		//行政编码 自动生成
		String hql = "from Hod2000Room where hod2000Unit.unitId="+unitId;
		List<Hod2000Room> lists = genericDAO.findByHQL(hql);
		Collections.sort(lists);
		String saveCode = "";
		int roomCode =0; 
		if(lists.size()>0)
			roomCode=Integer.parseInt(lists.get(0).getRoomCode());
		if(roomCode<999){
			roomCode++;
			saveCode = String.valueOf(roomCode);
		}else{
			for(int i=lists.size()-1;i>=0;i--){
				roomCode = Integer.parseInt(lists.get(i).getRoomCode());
				if((i==lists.size()-1)&&(roomCode-1>0)){
					roomCode--;
					saveCode = String.valueOf(roomCode);
					break;
				}
				int roomCodeNext = Integer.parseInt(lists.get(i-1).getRoomCode());
				if((roomCodeNext-roomCode)>1){
					roomCode = --roomCodeNext;
					saveCode = String.valueOf(roomCode);
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
	public List<Hod2000Room> findAll(List<Hod2000Unit> unit) {
		List list=genericDAO.findByNHQL("select room_id,room_name,unit_id from hod2000_room");
		Object[] obj;
		Hod2000Room hod2000Room;
		Hod2000Unit hod2000Unit;
		List<Hod2000Room> list2=new ArrayList<Hod2000Room>();
		for (int i = 0; i < list.size(); i++) {
			obj=(Object[]) list.get(i);
			hod2000Room=new Hod2000Room();
			hod2000Room.setRoomId(Integer.parseInt(obj[0].toString()));
			hod2000Room.setRoomName(obj[1].toString());
			for (int j = 0; j < unit.size(); j++) {
				hod2000Unit=unit.get(j);
				if(hod2000Unit.getUnitId()==Integer.parseInt(obj[2].toString()))
				{
					hod2000Room.setHod2000Unit(hod2000Unit);
					list2.add(hod2000Room);
					break;
				}
			}
			
		}
		return list2;
	}
}
