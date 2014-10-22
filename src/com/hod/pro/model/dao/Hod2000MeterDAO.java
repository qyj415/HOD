package com.hod.pro.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hod.pojo.Hod2000Meter;

/**
 * Hod2000MeterDAO
 * @author yixiang
 */
public class Hod2000MeterDAO extends GenericHibernateDAO<Hod2000Meter, String> implements IHod2000MeterDAO {

	/**
	 * 用于管网损耗树形
	 */
	public List findByMeterParent(String meterPosition,int meterAble) {
		String sql="select meter_id,meter_position_name,meter_position,meter_parent,meter_name,meter_style from hod2000_meter where meter_able="+meterAble+" and meter_parent='"+meterPosition+"' and (meter_style is NULL or meter_style>1)";
		return findByNHQL(sql);
	}

	/**
	 * 根据集中器编号查询表计信息，用于实时抄读树形
	 */
	public List findByConId(int id) {
		return findByNHQL("select meter_id,meter_name,meter_position_name from hod2000_meter where meter_able=1 and con_id="+id);
	}

	/**
	 * 根据表号查询表计信息，用来判断是否添加相同表号
	 */
	public List findbyMeterName(String meterName) {
		return findByHQL("from Hod2000Meter where meterName='"+meterName+"'");
	}

	/**
	 * 根据行政编码得到行政区域名称
	 */
	public String getParentName(String meterParentCode) {
		meterParentCode=meterParentCode.substring(9);
		int len=meterParentCode.length();
		String sql="";
		String text="";
		if(len==3)
		{
			sql="select community_name from hod2000_community where community_code='"+meterParentCode.substring(0,len)+"'";
			text=(String)findByNHQL(sql).get(0);
		}
		else if(len==6)
		{
			sql="select community_name+building_name from hod2000_community com inner join hod2000_building buil on com.community_id=buil.community_id where com.community_code='"+meterParentCode.substring(0,3)+"' and buil.building_code='"+meterParentCode.substring(3,len)+"'";
			text=(String)findByNHQL(sql).get(0);
		}
		else
		{
			sql="select community_name+building_name from hod2000_community com inner join hod2000_building buil on com.community_id=buil.community_id where com.community_code='"+meterParentCode.substring(0,3)+"' and buil.building_code='"+meterParentCode.substring(3,6)+"'";
			text=(String)findByNHQL(sql).get(0)+(String)findByNHQL("select room_name from hod2000_room where room_code='"+meterParentCode.substring(6,len)+"'").get(0);
		}
		return text;
	}

	/**
	 * 集中器编号中的表计总数
	 */
	public int getTerminalCount(Integer conId) {
		return Integer.parseInt( findByNHQL("select count(*) from hod2000_meter where meter_able=1 and con_id="+conId).get(0).toString());
	}

	/**
	 * 得到测量点号
	 */
	public int getMeterIndex() {
		int maxValue=0;
		List list1=findByNHQL("select COALESCE(max(meter_index),0) from hod2000_meter");
		if(list1.size()>0)
		{
			maxValue=Integer.parseInt(list1.get(0).toString());
			if(maxValue<65535)
			{
				maxValue=maxValue+1;
			}
			else
			{
				for (int i = 1; i < 65535; i++) {
					List list=findByNHQL("select meter_index from hod2000_meter where meter_index="+i);
					if(0==list.size())
					{
						maxValue=i;
						break;
					}
				}
			}
		}
		return maxValue;
	}

	/**
	 * 表计状态页面，根据条件查询
	 */
	public List findByParams(String meterName, String valveStatus,
			String batteryStatus, String isOnline,String meterPosition) {
		String sql="select meter_name,meter_position_name,valve_status,battery_status,isonline,eeprom_status,flowsensor_status,tepdown_status,tepup_status from hod2000_meter where meter_parent!='0' and meter_able=1";
		if(meterName!=null&&!meterName.equals(""))
			sql+=" and meter_name like '"+meterName+"%'";
		if(valveStatus!=null&&!valveStatus.equals(""))
			sql+=" and valve_status="+Integer.parseInt(valveStatus);
		if(batteryStatus!=null&&!batteryStatus.equals(""))
			sql+=" and battery_status="+Integer.parseInt(batteryStatus);
		if(isOnline!=null&&!isOnline.equals(""))
			sql+=" and isonline="+Integer.parseInt(isOnline);
		if(meterPosition!=null&&!meterPosition.equals(""))
			sql+=" and meter_position like '"+meterPosition+"%'";
		return findByNHQL(sql);
	}
	/**
	 * 查询换表记录，且是在供暖时间段之内的换表记录
	 */
	public List findByRoomId(int roomId,String hpStartTime,String hpEndTime) {
		return findByNHQL("select meter_name,meter_init,change_value from hod2000_meter where meter_able=0 and room_id="+roomId+" and changemeter_time>='"+hpStartTime+"' and changemeter_time<='"+hpEndTime+"' order by changemeter_time");
	}

	public int findByRoomIdAndMeterAble(int roomId, int meterAble) {
		return Integer.parseInt(findByNHQL("select count(*) from hod2000_meter where room_id="+roomId+" and meter_able="+meterAble).get(0).toString());
	}

	public List findbyUserMeterName() {
		return findByNHQL("select meter_name from hod2000_meter where meter_parent!='0' and meter_able=1");
	}

	public List findbyMeterNames(String meterNames,String code) {
		String sql="select meter_name,room_name,building_name,community_name,unit_name from meter_address where meter_name in ("+meterNames+")";
		if(code!=null&&!"".equals(code))
		{
			sql+=" and meter_position like '"+code+"%'";
		}
		return findByNHQL(sql);
	}

	public List findByCaliber(String caliber) {
		return findByNHQL("select meter_name from hod2000_meter where meter_parent!='0' and meter_able=1 and meter_caliber='"+caliber+"'");
	}
}
