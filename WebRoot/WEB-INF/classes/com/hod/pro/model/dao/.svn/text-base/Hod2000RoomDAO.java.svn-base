package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Room;

/**
 * Hod2000RoomDAO
 * @author yixiang
 */
public class Hod2000RoomDAO extends GenericHibernateDAO<Hod2000Room, String> implements IHod2000RoomDAO {

	public List findMeterByRoomId(String ids) {
		List list=findByNHQL("select r.room_id,room_name,room_house_type,meter_name,meter_init from hod2000_room r inner join hod2000_meter m on r.room_id=m.room_id where meter_able=1 and r.room_id in ("+ids+")");
		return list;
	}

	public List findByBuildingId(int id) {
		return findByNHQL("select room_id,room_name,room_code from hod2000_room where unit_id="+id);
	}

	public List findByClientId(int clientId) {
		return findByNHQL("select room_id,room_name,room_size,p_type from hod2000_room where client_id="+clientId);
	}

	public List findByInfoClientId(int clientId) {
		return findByNHQL("select room_name,room_size,meter_name,p_type,meter_init,r.room_id from hod2000_room r inner join hod2000_meter m on r.room_id=m.room_id where meter_able=1 and client_id="+clientId);
	}

	public List findByRoomId(int roomId) {
		return findByNHQL("select room_name,client_name,room_size,p_type,meter_name,meter_init from receive_room_meter where meter_able=1 and room_id="+roomId);
	}
 
}
