package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Receive;

/**
 * Hod2000ReceiveDAO
 * @author yixiang
 */
public interface IHod2000ReceiveDAO  extends GenericDAO<Hod2000Receive,String> {

	public abstract List findByUserId(int userId);

	/**
	 * ��ӡԤ��δ�տ���ϸ���޷�ҳ
	 */
	public abstract List findUnPay();

	public abstract int getReceiveNum(int status);

	public abstract List findReceiveRoomId();

	public abstract Hod2000Receive findByRoomIdSort(Integer roomId);

	/**
	 * ��ҳ��ѯδ�տ���ϸ
	 */
	public abstract List findUnPay(int page, int pageSize);

}
