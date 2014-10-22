package com.hod.pro.model.service;

import java.util.List;
import com.hod.javabean.PreReceive;
import com.hod.pojo.Hod2000PreReceive;
import com.hod.pro.model.dao.IHod2000PreReceiveDAO;
import com.hod.util.Arith;

/**
 * @author gngyf15
 */
 
public class Hod2000PreReceiveService extends Service implements IHod2000PreReceiveService {

	private IHod2000PreReceiveDAO hod2000PreReceiveDAO;
	
	public void setHod2000PreReceiveDAO(IHod2000PreReceiveDAO hod2000PreReceiveDAO) {
		this.hod2000PreReceiveDAO = hod2000PreReceiveDAO;
	}
	
	public List findByRoomId(int roomId) {
		return hod2000PreReceiveDAO.findByRoomId(roomId);
	}

	/**
	 * 查询已收款用户数、已收款金额与置零数
	 */
	public PreReceive getPreReceiveInfo() {
		int prAfter=0;
		double preMoney=0;//已收款金额
		PreReceive pre=new PreReceive();
		Hod2000PreReceive preReceive=null;
		pre.setUserNumber(hod2000PreReceiveDAO.findPreReceive(1));//已收款用户数
		List list=hod2000PreReceiveDAO.findPreReceiveRoomId();//查询已付款房间号
		for (int i = 0; i < list.size(); i++) {
			//查询每个房间的最后一次付费记录，如果为后付费prAfter加1
			preReceive=hod2000PreReceiveDAO.findByRoomIdSort((Integer)list.get(i));
			if(1==preReceive.getPrAfter())
			{
				prAfter+=1;
			}
			//preMoney+=preReceive.getPrMoney();
			preMoney=Arith.add(preMoney, preReceive.getPrMoney(), 2);
		}
		pre.setMoneyTotal(preMoney);
		pre.setToZero(prAfter);
		return pre;
	}

	public List findNotPay() {
		return hod2000PreReceiveDAO.findNotPay();
	}
}
