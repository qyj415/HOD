package com.hod.pro.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import com.hod.javabean.GetReceive;
import com.hod.javabean.ReceiveSummary;
import com.hod.javabean.Recevie;
import com.hod.javabean.UnCollectReceive;
import com.hod.pojo.Hod2000Heatingparameter;
import com.hod.pojo.Hod2000PreReceive;
import com.hod.pojo.Hod2000Price;
import com.hod.pojo.Hod2000PriceDetail;
import com.hod.pojo.Hod2000Receive;
import com.hod.pro.model.dao.IHod2000ClientDAO;
import com.hod.pro.model.dao.IHod2000HeatingparameterDAO;
import com.hod.pro.model.dao.IHod2000MeterDAO;
import com.hod.pro.model.dao.IHod2000MeterInfoFreezeDAO;
import com.hod.pro.model.dao.IHod2000MeterInfoHistoryDAO;
import com.hod.pro.model.dao.IHod2000PreReceiveDAO;
import com.hod.pro.model.dao.IHod2000PriceDAO;
import com.hod.pro.model.dao.IHod2000ReceiveDAO;
import com.hod.pro.model.dao.IHod2000RoomDAO;
import com.hod.util.Arith;
import com.hod.util.Utils;

/**
 * @author gngyf15
 */
 
public class Hod2000ReceiveService extends Service implements IHod2000ReceiveService {

	private IHod2000ReceiveDAO hod2000ReceiveDAO;
	private IHod2000RoomDAO hod2000RoomDAO;
	private IHod2000PriceDAO hod2000PriceDAO;
	private IHod2000HeatingparameterDAO heatingparameterDAO;
	private IHod2000MeterInfoFreezeDAO freezeDAO;
	private IHod2000PreReceiveDAO hod2000PreReceiveDAO;
	private IHod2000ClientDAO clientDAO;
	private IHod2000MeterDAO hod2000MeterDAO;
	private IHod2000MeterInfoHistoryDAO historyDAO;
	
	public void setHod2000ReceiveDAO(IHod2000ReceiveDAO hod2000ReceiveDAO) {
		this.hod2000ReceiveDAO = hod2000ReceiveDAO;
	}

	public void setHod2000RoomDAO(IHod2000RoomDAO hod2000RoomDAO) {
		this.hod2000RoomDAO = hod2000RoomDAO;
	}

	public List findByUserId(int userId) {
		return hod2000ReceiveDAO.findByUserId(userId);
	}
	
	public void setHod2000PriceDAO(IHod2000PriceDAO hod2000PriceDAO) {
		this.hod2000PriceDAO = hod2000PriceDAO;
	}

	public void setHeatingparameterDAO(
			IHod2000HeatingparameterDAO heatingparameterDAO) {
		this.heatingparameterDAO = heatingparameterDAO;
	}
	
	public void setFreezeDAO(IHod2000MeterInfoFreezeDAO freezeDAO) {
		this.freezeDAO = freezeDAO;
	}
	
	public void setHod2000PreReceiveDAO(IHod2000PreReceiveDAO hod2000PreReceiveDAO) {
		this.hod2000PreReceiveDAO = hod2000PreReceiveDAO;
	}

	public void setClientDAO(IHod2000ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public void setHod2000MeterDAO(IHod2000MeterDAO hod2000MeterDAO) {
		this.hod2000MeterDAO = hod2000MeterDAO;
	}

	public void setHistoryDAO(IHod2000MeterInfoHistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}

	/**
	 * 未收款统计
	 */
	public List<UnCollectReceive> findUnCollected(String page,String pageSize) {
		List<UnCollectReceive> list=new ArrayList<UnCollectReceive>();
		Object[] obj;
		double total=0;
		double preMoney=0;//预收款金额
		double roomSize=0;//面积数
		double startPower=0;//供暖开始累计热量
		double endPower=0;//供暖结束累计热量
		double pdPower=0;//耗热量
		int priceType=1;//价格方案
		if(heatingparameterDAO.findReceiveParams()>0)
		{
			//查询供暖开始时间与供暖结束时间
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
			List heat=heatingparameterDAO.findByCriteria(dc);
			Hod2000Heatingparameter heatingparameter=(Hod2000Heatingparameter) heat.get(0);
			//用热月数
			int months=Utils.getMonths(heatingparameter.getHpStart(), heatingparameter.getHpEnd());
			String hpStartTime=Utils.dateToStrLong(heatingparameter.getHpStart());//供暖开始时间
			String hpEndTime=Utils.dateToStrLong(heatingparameter.getHpEnd());//供暖结束时间
			//未付款的用户信息（房间号，表号等等）
			List info=new ArrayList();
			if(page!=null&&pageSize!=null&&!"".equals(page)&&!"".equals(pageSize))
				info=hod2000ReceiveDAO.findUnPay(Integer.parseInt(page),Integer.parseInt(pageSize));
			else
				info=hod2000ReceiveDAO.findUnPay();
			double basePrice=0;//基础费用单价
			for (int i = 0; i < info.size(); i++) {
				preMoney=0;
				obj=(Object[]) info.get(i);
				UnCollectReceive re=new UnCollectReceive();
				re.setAddress(obj[0].toString());
				re.setClientName(obj[4].toString());
				re.setMeterName(obj[2].toString());
				re.setMeterInit(Double.parseDouble(obj[5].toString()));
				re.setRoomId(Integer.parseInt(obj[6].toString()));
				re.setClientTel(obj[7]==null?"":obj[7].toString());
				priceType=Integer.parseInt(obj[3].toString());
				roomSize=Double.parseDouble(obj[1].toString());
				//耗热量，供暖开始累计热量，供暖结束累计热量
				//startPower=freezeDAO.getStartPower(obj[2].toString(),heatingparameter.getHpStart());
				//endPower=freezeDAO.getEndPower(obj[2].toString(),heatingparameter.getHpEnd());
				//startPower=freezeDAO.findFirst(re.getMeterName(),hpStartTime,hpEndTime);//供暖开始累计热量，月冻结数据
				//endPower=freezeDAO.findEnd(re.getMeterName(),hpStartTime,hpEndTime);//供暖结束累计热量，月冻结数据
				//上个季度最后一条月冻结数据
				List last=freezeDAO.findLastEnergy(re.getMeterName(),hpStartTime);
				if(last.size()>0)
					startPower=Double.parseDouble(last.get(0).toString());
				else
					startPower=re.getMeterInit();
				endPower=freezeDAO.findEnd(re.getMeterName(),hpStartTime,hpEndTime);//供暖结束累计热量，月冻结数据
				//查找在供暖季之内的换表记录
				List oldMeterList=hod2000MeterDAO.findByRoomId(re.getRoomId(),hpStartTime,hpEndTime);
				if(oldMeterList.size()>0)
				{
					Object[] obj2;
					for (int j = 0; j <oldMeterList.size(); j++) {
						obj2=(Object[]) oldMeterList.get(j);
						if(j==0)//第一条换表记录
						{
							//上季度最后一条冻结数据，如果找不到取表底数，换表时输入的旧表累计热量单位为KWh，统一转换为MJ进行运算后再转为KWh
							List lastEnergy=freezeDAO.findLastEnergy(obj2[0].toString(),hpStartTime);
							if(lastEnergy.size()>0)
							{
								pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),lastEnergy.get(0).toString(),4), 4);
							}
							else
							{
								pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),obj2[1].toString(),4), 4);
							}
						}
						else
						{
							pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),obj2[1].toString(),4), 4);
						}
						if(j==oldMeterList.size()-1)//处理新表数据
						{
							pdPower=Arith.add(pdPower,Arith.subtract(endPower,re.getMeterInit(),4), 4);
						}
					}
				}
				else
				{
					//如果是新开的用户，耗热量=供暖结束累计热量-表底数，判断用户是否新开，可以通过查询收费明细中是否有过往交费记录判断，若没有，则是新开的用户
					if(hod2000ReceiveDAO.findByUserId(Integer.parseInt(obj[6].toString())).size()>0)
					{
						//pdPower=endPower-startPower;
						pdPower=Arith.subtract(endPower, startPower, 4);
					}
					else
					{
						//pdPower=endPower-Double.parseDouble(obj[5].toString());
						pdPower=Arith.subtract(endPower, obj[5].toString(), 4);
					}
				}
				pdPower=Arith.dataFormat(pdPower);//MJ转千瓦时
				//根据价格方案类型与方案状态查找，查找当前方案且只有一条数据,当前方案为1
				Hod2000Price price=hod2000PriceDAO.findByParams(priceType,1);
				if(price!=null)
				{
					List<Hod2000PriceDetail> details=hod2000PriceDAO.getPriceDetailsById(price.getPid());
					if(1==priceType)
					{
						for (Hod2000PriceDetail hod2000PriceDetail : details) {
							//total=(roomSize*hod2000PriceDetail.getPdBasePrice()*months)+(pdPower*hod2000PriceDetail.getPdPowerPrice());
							total=Arith.add(Arith.multiply(roomSize, hod2000PriceDetail.getPdBasePrice(), months,4), Arith.multiply(pdPower, hod2000PriceDetail.getPdPowerPrice(), 4), 2);
						}
					}
					else if(2==priceType)//阶梯方案一
					{
						Map<Integer,Hod2000PriceDetail> map=new HashMap<Integer, Hod2000PriceDetail>();
						for (int j = 0; j <details.size(); j++) {
							map.put(j+1, details.get(j));
							basePrice=details.get(j).getPdBasePrice();
						}
						if(0==pdPower)
						{
							total=Arith.multiply(roomSize, basePrice, months, 4);
						}
						else
						{
							int n=getPriceDetail(pdPower,details);//当前用户耗热量属于第几阶
							if(n==0) n=1;
							total=Arith.add(Arith.multiply(roomSize, basePrice, months, 4), Arith.multiply(pdPower, map.get(n).getPdPowerPrice(), 4), 2);
						}
					}
					else
					{
						Map<Integer,Hod2000PriceDetail> map=new HashMap<Integer, Hod2000PriceDetail>();
						for (int j = 0; j <details.size(); j++) {
							map.put(j+1, details.get(j));
							basePrice=details.get(j).getPdBasePrice();
						}
						if(0==pdPower)
						{
							total=Arith.multiply(roomSize, basePrice, months, 4);
						}
						else
						{
							int n=getPriceDetail(pdPower,details);//当前用户耗热量属于第几阶
							double pdPowers=0;//阶梯能量费
							for(int j=n;j>0;j--){
								if(j==1)
								{
									pdPowers+=(pdPower)*map.get(j).getPdPowerPrice();
								}
								else
								{
									pdPowers+=(pdPower-map.get(j-1).getPdPower())*map.get(j).getPdPowerPrice();
									pdPower=map.get(j-1).getPdPower();
								}
								
							}
							total=Arith.add(Arith.multiply(roomSize, basePrice, months, 4), pdPowers, 2);
						}
					}
				}
				//查询该用户的预付款金额
				List preList=hod2000PreReceiveDAO.findByRoomId(Integer.parseInt(obj[6].toString()));
				if(preList.size()>0)
				{
					Hod2000PreReceive pre=(Hod2000PreReceive) preList.get(0);
					preMoney=pre.getPrMoney();
				}
				//应付款为total
				re.setReceiveMoney(total);
				re.setPreMoney(preMoney);
				list.add(re);
			}
		}
		return list;
	}

	/**
	 * 收费统计-->统计汇总
	 */
	public ReceiveSummary getSummary() {
		ReceiveSummary summary=new ReceiveSummary();
		Hod2000Receive hod2000Receive=null;
		double returnMoney=0;//退款金额
		double fillMoney=0;//补交金额
		double preMoney=0;//预收款金额
		double totalMoney=0;//应收总金额
		summary.setUserNum(hod2000ReceiveDAO.getReceiveNum(1));
		summary.setUnCollectUserNum(hod2000ReceiveDAO.getReceiveNum(0));
		//查询未收费的房间编号
		List list=hod2000ReceiveDAO.findReceiveRoomId();
		for (int i = 0; i <list.size(); i++) {
			//查询每个房间的最后一次付费记录，如果为后付费prAfter加1
			hod2000Receive=hod2000ReceiveDAO.findByRoomIdSort((Integer)list.get(i));
			//returnMoney+=hod2000Receive.getReturnMoney();
			//fillMoney+=hod2000Receive.getFillMoney();
			//preMoney+=hod2000Receive.getPrMoney();
			//totalMoney+=hod2000Receive.getRmoney();
			if(hod2000Receive!=null)
			{
				returnMoney=Arith.add(returnMoney, hod2000Receive.getReturnMoney(), 2);
				fillMoney=Arith.add(fillMoney, hod2000Receive.getFillMoney(), 2);
				preMoney=Arith.add(preMoney, hod2000Receive.getPrMoney(), 2);
				totalMoney=Arith.add(totalMoney, hod2000Receive.getRmoney(), 2);
			}
		}
		summary.setReturnMoney(returnMoney);
		summary.setFillMoney(fillMoney);
		summary.setPreMoney(preMoney);
		summary.setTotalMoney(totalMoney);
		return summary;
	}

	/**
	 * 根据房间号进行收费
	 */
	public GetReceive getCharge(int roomId) {
		double totalMoney=0;//价格方案金额
		double startPower=0;//供暖开始累计热量
		double endPower=0;//供暖结束累计热量
		double pdPower=0;//耗热量
		double basePrice=0;//基础费用单价
		GetReceive re=new GetReceive();
		Object[] obj;
		//根据房间号查询表号以及房间信息
		List list=hod2000RoomDAO.findByRoomId(roomId);
		if(list.size()>0)
		{
			for (int i = 0; i < list.size(); i++) {
				obj=(Object[]) list.get(0);
				re.setRoomName(obj[0].toString());
				re.setClientName(obj[1].toString());
				re.setRoomSize(Double.parseDouble(obj[2].toString()));
				re.setPType(Integer.parseInt(obj[3].toString()));
				re.setMeterName(obj[4].toString());
				re.setMeterInit(Double.parseDouble(obj[5].toString()));
			}
			//设置收费方式
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
			List heat=heatingparameterDAO.findByCriteria(dc);
			if(heat.size()>0)
			{
				Hod2000Heatingparameter heatingparameter=(Hod2000Heatingparameter) heat.get(0);
				re.setReType(heatingparameter.getHpType());
				int months=Utils.getMonths(heatingparameter.getHpStart(), heatingparameter.getHpEnd());//用热月数
				String hpStartTime=Utils.dateToStrLong(heatingparameter.getHpStart());
				String hpEndTime=Utils.dateToStrLong(heatingparameter.getHpEnd());
				//startPower=historyDAO.findEnergyMeterNameFirst(re.getMeterName(),hpStartTime,hpEndTime);//供暖开始累计热量
				//endPower=historyDAO.findEnergyMeterNameEnd(re.getMeterName(),hpStartTime,hpEndTime);//供暖结束累计热量
				//startPower=freezeDAO.findFirst(re.getMeterName(),hpStartTime,hpEndTime);//供暖开始累计热量，月冻结数据
				//上个季度最后一条月冻结数据
				List last=freezeDAO.findLastEnergy(re.getMeterName(),hpStartTime);
				if(last.size()>0)
					startPower=Double.parseDouble(last.get(0).toString());
				else
					startPower=re.getMeterInit();
				endPower=freezeDAO.findEnd(re.getMeterName(),hpStartTime,hpEndTime);//供暖结束累计热量，月冻结数据
				//查找在供暖季之内的换表记录
				List oldMeterList=hod2000MeterDAO.findByRoomId(roomId,hpStartTime,hpEndTime);
				if(oldMeterList.size()>0)
				{
					Object[] obj2;
					for (int i = 0; i <oldMeterList.size(); i++) {
						obj2=(Object[]) oldMeterList.get(i);
						if(i==0)//第一条换表记录
						{
							//上季度最后一条冻结数据，如果找不到取表底数，换表时输入的旧表累计热量单位为KWh，统一转换为MJ进行运算后再转为KWh
							List lastEnergy=freezeDAO.findLastEnergy(obj2[0].toString(),hpStartTime);
							if(lastEnergy.size()>0)
							{
								pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),lastEnergy.get(0).toString(),4), 4);
							}
							else
							{
								pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),obj2[1].toString(),4), 4);
							}
						}
						else//两条以上换表的旧表数据
						{
							pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),obj2[1].toString(),4), 4);
						}
						if(i==oldMeterList.size()-1)//处理正在使用的新表数据
						{
							pdPower=Arith.add(pdPower,Arith.subtract(endPower,re.getMeterInit(),4), 4);
						}
					}
				}
				else
				{
					//如果是新开的用户，耗热量=供暖结束累计热量-表底数，判断用户是否新开，可以通过查询收费明细中是否有过往交费记录判断，若没有，则是新开的用户
					if(hod2000ReceiveDAO.findByUserId(roomId).size()>0)
					{
						//pdPower=endPower-startPower;
						pdPower=Arith.subtract(endPower, startPower, 4);
					}
					else
					{
						//pdPower=endPower-re.getMeterInit();
						pdPower=Arith.subtract(endPower, re.getMeterInit(), 4);
					}
				}
				startPower=Arith.dataFormat(startPower);
				endPower=Arith.dataFormat(endPower);
				pdPower=Arith.dataFormat(pdPower);
				re.setStartPower(startPower);
				re.setEndPower(endPower);
				re.setPdPower(pdPower);
				//根据价格方案类型与方案状态查找，查找当前方案且只有一条数据,当前方案为1
				Hod2000Price price=hod2000PriceDAO.findByParams(re.getPType(),1);
				if(price!=null)
				{
					List<Hod2000PriceDetail> details=hod2000PriceDAO.getPriceDetailsById(price.getPid());
					//定额价格方案，基础费+能量费
					if(1==re.getPType())
					{
						re.setPriceType("定额价格方案");
						for (Hod2000PriceDetail hod2000PriceDetail : details) {
							//totalMoney=(re.getRoomSize()*hod2000PriceDetail.getPdBasePrice()*months)+(pdPower*hod2000PriceDetail.getPdPowerPrice());
							totalMoney=Arith.add(Arith.multiply(re.getRoomSize(), hod2000PriceDetail.getPdBasePrice(), months, 4), Arith.multiply(pdPower, hod2000PriceDetail.getPdPowerPrice(), 4), 2);
						}
					}
					//阶梯方案一,基础费+阶梯能量费
					else if(2==re.getPType())
					{
						Map<Integer,Hod2000PriceDetail> map=new HashMap<Integer, Hod2000PriceDetail>();
						for (int i = 0; i <details.size(); i++) {
							map.put(i+1, details.get(i));
							basePrice=details.get(i).getPdBasePrice();
						}
						if(0==pdPower)
						{
							totalMoney=Arith.multiply(re.getRoomSize(), basePrice, months, 4);
						}
						else
						{
							int n=getPriceDetail(pdPower,details);//当前用户耗热量属于第几阶
							if(n==0) n=1;//为负数或者不属于哪阶梯的时候按第一阶梯价格算
							totalMoney=Arith.add(Arith.multiply(re.getRoomSize(), basePrice, months, 4), Arith.multiply(pdPower, map.get(n).getPdPowerPrice(), 4), 2);
						}
						re.setPriceType("阶梯价格方案一");
					}
					//阶梯方案二,基础费+阶梯能量费
					else
					{
						Map<Integer,Hod2000PriceDetail> map=new HashMap<Integer, Hod2000PriceDetail>();
						for (int i = 0; i <details.size(); i++) {
							map.put(i+1, details.get(i));
							basePrice=details.get(i).getPdBasePrice();
						}
						if(0==pdPower)
						{
							totalMoney=Arith.multiply(re.getRoomSize(), basePrice, months, 4);
						}
						else
						{
							int n=getPriceDetail(pdPower,details);//当前用户耗热量属于第几阶
							double pdPowers=0;//阶梯能量费
							for(int i=n;i>0;i--){
								if(i==1)
								{
									pdPowers+=(pdPower)*map.get(i).getPdPowerPrice();
								}
								else
								{
									pdPowers+=(pdPower-map.get(i-1).getPdPower())*map.get(i).getPdPowerPrice();
									pdPower=map.get(i-1).getPdPower();
								}
								
							}
							totalMoney=Arith.add(Arith.multiply(re.getRoomSize(), basePrice, months, 4), pdPowers, 2);
						}
						re.setPriceType("阶梯价格方案二");
					}
				}
				re.setMonetyToPay(totalMoney);
			}
			
		}
		return re;
	}
	
	public int getPriceDetail(double pdPower,List<Hod2000PriceDetail> details)
	{
		int n=0;
		double b=0;
		for (int i = details.size()-1; i >=0; i--) {
			double a=details.get(i).getPdPower();
			if(i>0)
				b=details.get(i-1).getPdPower();
			else 
				b=0;
			if(pdPower>b&&pdPower<=a)
			{
				n=i+1;
			}
			if(i==details.size()-1&&pdPower>a)
			{
				n=details.size();
			}
		}
		return n;
	}
}
