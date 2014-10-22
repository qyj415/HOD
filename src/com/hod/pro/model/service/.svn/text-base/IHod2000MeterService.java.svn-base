package com.hod.pro.model.service;

import java.util.List;
import java.util.Map;

/**
 * @author yixiang
 */
 
public interface IHod2000MeterService extends IService {

	/**
	 * 用于管网损耗树形结构
	 * @param meterPosition
	 * @param meterAble 
	 * @return
	 */
	public abstract List findByMeterParent(String meterPosition, int meterAble);

	/**
	 * 根据集中器编号查询表计信息，用于实时抄读树形
	 * @param id 集中器编号
	 * @return
	 */
	public abstract List findByConId(int id);

	/**
	 * 根据表号查询表计信息，用来判断是否添加相同表号
	 * @param meterName 表号
	 * @return
	 */
	public abstract List findbyMeterName(String meterName);

	/**
	 * 根据行政编码得到行政区域名称
	 * @param meterParentCode
	 * @return
	 */
	public abstract String getParentName(String meterParentCode);

	/**
	 * 当前集中器中包含表计的数量
	 * @param conId 集中器编号
	 * @return
	 */
	public abstract int getTerminalCount(Integer conId);

	/**
	 * 得到测量点号
	 * @return
	 */
	public abstract int getMeterIndex();

	/**
	 * 根据条件查询表计状态
	 * @param meterName 表号
	 * @param valveStatus 阀门
	 * @param batteryStatus 电池
	 * @param isOnline 在线
	 * @param meterPosition 地理位置行政编码
	 * @return
	 */
	public abstract List findByParams(String meterName, String valveStatus,
			String batteryStatus, String isOnline,String meterPosition);

	/**
	 * 根据上级表号行政编码查询其下级表号
	 * @param meterPosition 上级表号的行政编码
	 * @return
	 */
	public abstract String findChild(String meterPosition);

	/**
	 * 根据楼栋编号查询正在使用表计的表号,地理位置,表底数,表计编号信息（使用视图meter_address）
	 * @param buildingId 楼栋编号
	 * @return
	 * @throws Exception
	 */
	public abstract List findByBuildingById(int buildingId) throws Exception;

	/**
	 * 根据房间编号和表计是否使用状态查询个数
	 * @param roomId 房间编号
	 * @param meterAble 表计是否使用状态
	 * @return
	 */
	public abstract int findByRoomIdAndMeterAble(int roomId, int meterAble);

	/**
	 * 查询正在使用的所有的表号
	 * @return
	 */
	public abstract List findbyUserMeterName();

	/**
	 * 根据表号查询地理位置信息，多个表号
	 * @param meterNames
	 * @param code 
	 * @return
	 */
	public abstract List findbyMeterNames(String meterNames, String code);

	/**
	 * 查询口径为caliber，正在使用的所有的表计
	 * @param caliber 口径
	 * @return
	 */
	public abstract List findByCaliber(String caliber);

	/**
	 * 根据地理位置编码查询是否唯一，只查询正在使用的表
	 * @param meterPosition 地理位置行政编码
	 * @return
	 */
	public abstract int findByMeterPosition(String meterPosition);

	/**
	 * 根据地理位置编码查询地理位置名称与表计类型,用于表计的新增，查询上级表(地理位置+表计类型)
	 * @param parent_position
	 * @return
	 */
	public abstract String findNameByMeterPosition(String parent_position);

	/**
	 * 热力公司行政区域码生成
	 * @return
	 */
	public abstract String getCompanyPosition();

	/**
	 * 判断热力公司名称是否重复
	 * @param meterPositionName
	 * @return
	 */
	public abstract int findByPositionName(String meterPositionName);

	/**
	 * 得到热力公司表号，规则000000+两位数（00-99）
	 * @return
	 */
	public abstract String getCompanyMeterName();

}
