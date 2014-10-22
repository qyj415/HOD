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
	 * 打印预览未收款明细，无分页
	 */
	public abstract List findUnPay();

	public abstract int getReceiveNum(int status);

	public abstract List findReceiveRoomId();

	public abstract Hod2000Receive findByRoomIdSort(Integer roomId);

	/**
	 * 分页查询未收款明细
	 */
	public abstract List findUnPay(int page, int pageSize);

}
