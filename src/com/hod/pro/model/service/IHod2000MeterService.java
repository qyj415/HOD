package com.hod.pro.model.service;

import java.util.List;
import java.util.Map;

/**
 * @author yixiang
 */
 
public interface IHod2000MeterService extends IService {

	/**
	 * ���ڹ���������νṹ
	 * @param meterPosition
	 * @param meterAble 
	 * @return
	 */
	public abstract List findByMeterParent(String meterPosition, int meterAble);

	/**
	 * ���ݼ�������Ų�ѯ�����Ϣ������ʵʱ��������
	 * @param id ���������
	 * @return
	 */
	public abstract List findByConId(int id);

	/**
	 * ���ݱ�Ų�ѯ�����Ϣ�������ж��Ƿ������ͬ���
	 * @param meterName ���
	 * @return
	 */
	public abstract List findbyMeterName(String meterName);

	/**
	 * ������������õ�������������
	 * @param meterParentCode
	 * @return
	 */
	public abstract String getParentName(String meterParentCode);

	/**
	 * ��ǰ�������а�����Ƶ�����
	 * @param conId ���������
	 * @return
	 */
	public abstract int getTerminalCount(Integer conId);

	/**
	 * �õ��������
	 * @return
	 */
	public abstract int getMeterIndex();

	/**
	 * ����������ѯ���״̬
	 * @param meterName ���
	 * @param valveStatus ����
	 * @param batteryStatus ���
	 * @param isOnline ����
	 * @param meterPosition ����λ����������
	 * @return
	 */
	public abstract List findByParams(String meterName, String valveStatus,
			String batteryStatus, String isOnline,String meterPosition);

	/**
	 * �����ϼ�������������ѯ���¼����
	 * @param meterPosition �ϼ���ŵ���������
	 * @return
	 */
	public abstract String findChild(String meterPosition);

	/**
	 * ����¥����Ų�ѯ����ʹ�ñ�Ƶı��,����λ��,�����,��Ʊ����Ϣ��ʹ����ͼmeter_address��
	 * @param buildingId ¥�����
	 * @return
	 * @throws Exception
	 */
	public abstract List findByBuildingById(int buildingId) throws Exception;

	/**
	 * ���ݷ����źͱ���Ƿ�ʹ��״̬��ѯ����
	 * @param roomId ������
	 * @param meterAble ����Ƿ�ʹ��״̬
	 * @return
	 */
	public abstract int findByRoomIdAndMeterAble(int roomId, int meterAble);

	/**
	 * ��ѯ����ʹ�õ����еı��
	 * @return
	 */
	public abstract List findbyUserMeterName();

	/**
	 * ���ݱ�Ų�ѯ����λ����Ϣ��������
	 * @param meterNames
	 * @param code 
	 * @return
	 */
	public abstract List findbyMeterNames(String meterNames, String code);

	/**
	 * ��ѯ�ھ�Ϊcaliber������ʹ�õ����еı��
	 * @param caliber �ھ�
	 * @return
	 */
	public abstract List findByCaliber(String caliber);

	/**
	 * ���ݵ���λ�ñ����ѯ�Ƿ�Ψһ��ֻ��ѯ����ʹ�õı�
	 * @param meterPosition ����λ����������
	 * @return
	 */
	public abstract int findByMeterPosition(String meterPosition);

	/**
	 * ���ݵ���λ�ñ����ѯ����λ��������������,���ڱ�Ƶ���������ѯ�ϼ���(����λ��+�������)
	 * @param parent_position
	 * @return
	 */
	public abstract String findNameByMeterPosition(String parent_position);

	/**
	 * ������˾��������������
	 * @return
	 */
	public abstract String getCompanyPosition();

	/**
	 * �ж�������˾�����Ƿ��ظ�
	 * @param meterPositionName
	 * @return
	 */
	public abstract int findByPositionName(String meterPositionName);

	/**
	 * �õ�������˾��ţ�����000000+��λ����00-99��
	 * @return
	 */
	public abstract String getCompanyMeterName();

}
