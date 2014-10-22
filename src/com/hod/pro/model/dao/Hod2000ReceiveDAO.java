package com.hod.pro.model.dao;

import java.util.List;
import com.hod.pojo.Hod2000Receive;

/**
 * Hod2000ReceiveDAO
 * @author yixiang
 */
public class Hod2000ReceiveDAO extends GenericHibernateDAO<Hod2000Receive, String> implements IHod2000ReceiveDAO {

	/**
	 * �����û�id��ѯ�Ѿ����ѵ���Ϣ
	 */
	public List findByUserId(int userId) {
		return findByHQL("from Hod2000Receive where roomId="+userId+" order by rdate desc");
	}

	/**
	 * û�и��ѵ���Ϣ
	 * Ϊ��Ч�û�
	 */
	public List findUnPay() {
		String sql="select address,room_size,meter_name,p_type,client_name,meter_init,room_id,client_tel from receive_room_meter where room_receive_flag=0 and client_enable=1 and meter_able=1";
		return findByNHQL(sql);
		//return findByNHQL("select community_name+'-'+building_name+'-'+room_name address,room_size,meter_name,p_type,client_name,meter_init,room_id from receive_room_meter where room_receive_flag=0 and client_enable=1 and meter_able=1");
	}

	public int getReceiveNum(int status) {
		return Integer.parseInt(findByNHQL("select count(*) from receive_room_meter where client_enable=1 and meter_able=1 and room_receive_flag="+status).get(0).toString());
	}

	/**
	 * ��ѯ�ü����Ѿ��շѵķ�����
	 */
	public List findReceiveRoomId() {
		return findByNHQL("select room_id from receive_room_meter where client_enable=1 and meter_able=1 and room_receive_flag=1");
	}

	/**
	 * ��ѯ�ü���ĳ������շѼ�¼
	 */
	public Hod2000Receive findByRoomIdSort(Integer roomId) {
		List list=findByHQL("from Hod2000Receive where roomId="+roomId+" order by rdate desc");
		if(list.size()>0)
			return (Hod2000Receive) list.get(0);
		else
			return null;
	}
	/**
	 * ��ҳ��ѯδ�տ���ϸ
	 */
	public List findUnPay(int page, int pageSize) {
		String sql="select address,room_size,meter_name,p_type,client_name,meter_init,room_id,client_tel from receive_room_meter where room_receive_flag=0 and client_enable=1 and meter_able=1";
		return findByNHQL(page,pageSize,sql);
		//return findByNHQL(page,pageSize,"select community_name+'-'+building_name+'-'+room_name address,room_size,meter_name,p_type,client_name,meter_init,room_id from receive_room_meter where room_receive_flag=0 and client_enable=1 and meter_able=1");
	}
}
