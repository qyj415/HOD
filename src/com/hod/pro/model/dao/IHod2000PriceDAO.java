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
	 * ���ݼ۸񷽰����Ʋ�ѯ�۸񷽰��������ж��Ƿ�����
	 * @param pname �۸񷽰�����
	 * @return
	 */
	public abstract List findByPriceName(String pname);

	/**
	 * ���ݼ۸񷽰������뷽��״̬��ѯ�۸񷽰�����
	 * @param priceType ��������
	 * @param priceStatus ����״̬
	 * @return
	 */
	public abstract Hod2000Price findByParams(int priceType, int priceStatus);

	/**
	 * ���ݼ۸񷽰�id��ѯ�۸񷽰���ϸ���������ȵ�����������
	 * @param pid �۸񷽰�id
	 * @return
	 */
	public abstract List<Hod2000PriceDetail> getPriceDetailsById(int pid);

	public abstract List findByPriceName(String pname, Integer pid);

	public abstract List findByParams(int priceType, Date pstartTime);

	public abstract List findByParams(int ptype, Date pstartTime, int pid);

}
