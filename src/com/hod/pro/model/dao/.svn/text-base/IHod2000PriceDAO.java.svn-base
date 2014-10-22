package com.hod.pro.model.dao;

import java.util.Date;
import java.util.List;
import com.hod.pojo.Hod2000Price;
import com.hod.pojo.Hod2000PriceDetail;

/**
 * Hod2000PriceDAO
 * @author yixiang
 */
public interface IHod2000PriceDAO  extends GenericDAO<Hod2000Price,String> {

	/**
	 * 根据价格方案名称查询价格方案，用于判断是否重名
	 * @param pname 价格方案名称
	 * @return
	 */
	public abstract List findByPriceName(String pname);

	/**
	 * 根据价格方案类型与方案状态查询价格方案对象
	 * @param priceType 方案类型
	 * @param priceStatus 方案状态
	 * @return
	 */
	public abstract Hod2000Price findByParams(int priceType, int priceStatus);

	/**
	 * 根据价格方案id查询价格方案明细，并按用热单价升序排序
	 * @param pid 价格方案id
	 * @return
	 */
	public abstract List<Hod2000PriceDetail> getPriceDetailsById(int pid);

	public abstract List findByPriceName(String pname, Integer pid);

	public abstract List findByParams(int priceType, Date pstartTime);

	public abstract List findByParams(int ptype, Date pstartTime, int pid);

}
