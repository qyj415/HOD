package com.hod.pro.model.dao;

import java.util.List;
import com.hod.pojo.Hod2000PreReceive;

/**
 * Hod2000PreReceiveDAO
 * @author yixiang
 */
public class Hod2000PreReceiveDAO extends GenericHibernateDAO<Hod2000PreReceive, String> implements IHod2000PreReceiveDAO {

	/**
	 * 根据房间id查询预付款信息，根据预付款时间降序排序
	 */
	public List findByRoomId(int roomId) {
		return findByHQL("select pre from Hod2000PreReceive pre,Hod2000Room room where pre.roomId=room.roomId and pre.roomId="+roomId+" and room.roomPreFlag=1 order by pr_time desc");
	}
	
	/**
	 * 未付款统计
	 */
	public List findNotPay() {
		String sql="select room_size,address,client_name,p_type,client_tel from pre_receive_room where room_pre_flag=0 and client_enable=1";
		return findByNHQL(sql);
		//return findByNHQL("select room_size,community_name+'-'+building_name+'-'+room_name address,client_name,p_type from pre_receive_room where room_pre_flag=0 and client_enable=1");
	}

	/**
	 * 根据预付款状态的个数
	 */
	public int findPreReceive(int status) {
		return Integer.parseInt(findByNHQL("select count(*) from pre_receive_room where client_enable=1 and room_pre_flag="+status).get(0).toString());
	}

	/**
	 * 查询该季度的已收款房间号
	 */
	public List findPreReceiveRoomId() {
		return findByNHQL("select room_id from pre_receive_room where client_enable=1 and room_pre_flag=1");
	}

	/**
	 * 查询该季度某房间的预收款记录
	 */
	public Hod2000PreReceive findByRoomIdSort(int roomId) {
		List list=findByHQL("from Hod2000PreReceive where roomId="+roomId+" order by prTime desc");
		return (Hod2000PreReceive) list.get(0);
	}
 
}
