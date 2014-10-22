package com.hod.pro.model.service;

import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000Meter;
import com.hod.pro.model.dao.IHod2000MeterDAO;

/**
 * @author yixiang
 */
 
public class Hod2000MeterService extends Service implements IHod2000MeterService {

	private IHod2000MeterDAO hod2000MeterDAO;
	
	public void setHod2000MeterDAO(IHod2000MeterDAO hod2000MeterDAO) {
		this.hod2000MeterDAO = hod2000MeterDAO;
	}

	public List findByMeterParent(String meterPosition,int meterAble) {
		return hod2000MeterDAO.findByMeterParent(meterPosition,meterAble);
	}

	public List findByConId(int id) {
		return hod2000MeterDAO.findByConId(id);
	}

	public List findbyMeterName(String meterName) {
		return hod2000MeterDAO.findbyMeterName(meterName);
	}

	public String getParentName(String meterParentCode) {
		return hod2000MeterDAO.getParentName(meterParentCode);
	}

	public int getTerminalCount(Integer conId) {
		return hod2000MeterDAO.getTerminalCount(conId);
	}

	public int getMeterIndex() {
		return hod2000MeterDAO.getMeterIndex();
	}

	public List findByParams(String meterName, String valveStatus,
			String batteryStatus, String isOnline,String meterPosition) {
		return hod2000MeterDAO.findByParams(meterName,valveStatus,batteryStatus,isOnline,meterPosition);
	}

	public String findChild(String meterPosition) {
		try {
			StringBuffer buffer = new StringBuffer();
			Object[] objects;
			// 根据上级id查询表信息
			List list = hod2000MeterDAO.findByMeterParent(meterPosition,1);
			if (list.size() == 0) {
				buffer.append("leaf:true},");
				return buffer.toString();
			}
			buffer.append("expanded: true,children:[");
			for (int i = 0; i < list.size(); i++) {
				objects = (Object[]) list.get(i);
				buffer.append("{id:'" + objects[0] + "',");
				if(objects[5].equals(1))
					buffer.append("text:'" + objects[1] + "户用表',");
				else if(objects[5].equals(2))
					buffer.append("text:'" + objects[1] + "楼栋表',");
				else
					buffer.append("text:'" + objects[1] + "换能站',");
				buffer.append("meter_position:'" + objects[2] + "',");
				buffer.append("meter_name:'" + objects[4] + "',");
				buffer.append("meter_style:'" + objects[5] + "',");
				buffer.append(findChild(objects[2].toString()));
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("]},");
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public List findByBuildingById(int buildingId) throws Exception {
		return findByNHQL("select meter_name,meter_position_name,meter_init,meter_id,valve_status,battery_status,isonline,eeprom_status,flowsensor_status,tepdown_status,tepup_status from meter_address where meter_able=1 and building_id="+buildingId);
	}

	public int findByRoomIdAndMeterAble(int roomId, int meterAble) {
		return hod2000MeterDAO.findByRoomIdAndMeterAble(roomId,meterAble);
	}

	public List findbyUserMeterName() {
		return hod2000MeterDAO.findbyUserMeterName();
	}

	public List findbyMeterNames(String meterNames,String code) {
		return hod2000MeterDAO.findbyMeterNames(meterNames,code);
	}

	public List findByCaliber(String caliber) {
		return hod2000MeterDAO.findByCaliber(caliber);
	}

	public int findByMeterPosition(String meterPosition) {
		return Integer.parseInt(hod2000MeterDAO.findByNHQL("select count(*) from hod2000_meter where meter_position='"+meterPosition+"' and meter_able=1").get(0).toString());
	}

	public String findNameByMeterPosition(String parent_position) {
		List list=hod2000MeterDAO.findByNHQL("select meter_position_name,meter_style from hod2000_meter where meter_position='"+parent_position+"' and (meter_able=1 or meter_parent='0')");
		String meterPositionName="";
		if(list.size()>0)
		{
			Object[] obj=(Object[]) list.get(0);
			meterPositionName=obj[0].toString();
			if(obj[1]!=null&&obj[1].equals(1))
				meterPositionName+="户用表";
			if(obj[1]!=null&&obj[1].equals(2))
				meterPositionName+="楼栋表";
			if(obj[1]!=null&&obj[1].equals(3))
				meterPositionName+="换能站";
			
		}
		return meterPositionName;
	}

	public String getCompanyPosition() {
		int num=1;
		List list=hod2000MeterDAO.findByNHQL("select COALESCE(max(meter_position),0) from hod2000_meter where meter_parent='0'");
		if(list.size()>0)
			num=Integer.parseInt(list.get(0).toString())+1;
		return num+"";
	}

	public int findByPositionName(String meterPositionName) {
		return Integer.parseInt(hod2000MeterDAO.findByNHQL("select count(*) from hod2000_meter where meter_parent='0' and meter_position_name='"+meterPositionName+"'").get(0).toString());
	}

	public String getCompanyMeterName() {
		String companyMeterName="000000";
		List<Hod2000Meter> list=genericDAO.findByHQL("FROM Hod2000Meter WHERE meterParent='0' ORDER BY meterName DESC");
		if(list.size()>0)
		{
			int data=Integer.parseInt(list.get(0).getMeterName().substring(6, 8));//取最后两位
			if(data<99)
			{
				data=data+1;
			}
			else
			{
				List<Hod2000Meter> list2;
				String dataStrs="";
				for (int i = 0; i <100; i++) {
					dataStrs=""+i;
					if(dataStrs.length()<2)
						dataStrs="0"+i;
					else
						dataStrs=""+i;
					list2=genericDAO.findByHQL("FROM Hod2000Meter WHERE meterParent='0' AND meterName='"+companyMeterName+dataStrs+"'");
					if(0==list2.size())
					{
						data=i;
						break;
					}
				}
			}
			String dataStr=""+data;
			if(dataStr.length()<2)
				companyMeterName=companyMeterName+"0"+dataStr;
			else
				companyMeterName=companyMeterName+dataStr;
		}
		else
		{
			companyMeterName+="00";
		}
		return companyMeterName;
	}
 

}
