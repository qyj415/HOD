package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Room;
import com.hod.pojo.Hod2000Unit;

/**
 * @author yixiang
 */
 
public interface IHod2000RoomService extends IService {

	public abstract List findMeterByRoomId(String ids);

	public abstract List findByBuildingId(int id);

	public abstract List findByClientId(int clientId);

	public abstract int findByRoomName(Integer buildingId, String roomName);

	public abstract int findByRoomName(Integer buildingId, String roomName,
			Integer roomId);

	public abstract List findRoomsByBuildingId(int id);

	/**
	 * �õ�����������������
	 * @return
	 */
	public abstract String getCode(int unitId);

	/**
	 * ��ѯ���з�����Ϣ
	 * @param unit
	 * @return
	 */
	public abstract List<Hod2000Room> findAll(List<Hod2000Unit> unit);

}
