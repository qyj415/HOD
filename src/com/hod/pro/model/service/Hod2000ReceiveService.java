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
	 * δ�տ�ͳ��
	 */
	public List<UnCollectReceive> findUnCollected(String page,String pageSize) {
		List<UnCollectReceive> list=new ArrayList<UnCollectReceive>();
		Object[] obj;
		double total=0;
		double preMoney=0;//Ԥ�տ���
		double roomSize=0;//�����
		double startPower=0;//��ů��ʼ�ۼ�����
		double endPower=0;//��ů�����ۼ�����
		double pdPower=0;//������
		int priceType=1;//�۸񷽰�
		if(heatingparameterDAO.findReceiveParams()>0)
		{
			//��ѯ��ů��ʼʱ���빩ů����ʱ��
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
			List heat=heatingparameterDAO.findByCriteria(dc);
			Hod2000Heatingparameter heatingparameter=(Hod2000Heatingparameter) heat.get(0);
			//��������
			int months=Utils.getMonths(heatingparameter.getHpStart(), heatingparameter.getHpEnd());
			String hpStartTime=Utils.dateToStrLong(heatingparameter.getHpStart());//��ů��ʼʱ��
			String hpEndTime=Utils.dateToStrLong(heatingparameter.getHpEnd());//��ů����ʱ��
			//δ������û���Ϣ������ţ���ŵȵȣ�
			List info=new ArrayList();
			if(page!=null&&pageSize!=null&&!"".equals(page)&&!"".equals(pageSize))
				info=hod2000ReceiveDAO.findUnPay(Integer.parseInt(page),Integer.parseInt(pageSize));
			else
				info=hod2000ReceiveDAO.findUnPay();
			double basePrice=0;//�������õ���
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
				//����������ů��ʼ�ۼ���������ů�����ۼ�����
				//startPower=freezeDAO.getStartPower(obj[2].toString(),heatingparameter.getHpStart());
				//endPower=freezeDAO.getEndPower(obj[2].toString(),heatingparameter.getHpEnd());
				//startPower=freezeDAO.findFirst(re.getMeterName(),hpStartTime,hpEndTime);//��ů��ʼ�ۼ��������¶�������
				//endPower=freezeDAO.findEnd(re.getMeterName(),hpStartTime,hpEndTime);//��ů�����ۼ��������¶�������
				//�ϸ��������һ���¶�������
				List last=freezeDAO.findLastEnergy(re.getMeterName(),hpStartTime);
				if(last.size()>0)
					startPower=Double.parseDouble(last.get(0).toString());
				else
					startPower=re.getMeterInit();
				endPower=freezeDAO.findEnd(re.getMeterName(),hpStartTime,hpEndTime);//��ů�����ۼ��������¶�������
				//�����ڹ�ů��֮�ڵĻ����¼
				List oldMeterList=hod2000MeterDAO.findByRoomId(re.getRoomId(),hpStartTime,hpEndTime);
				if(oldMeterList.size()>0)
				{
					Object[] obj2;
					for (int j = 0; j <oldMeterList.size(); j++) {
						obj2=(Object[]) oldMeterList.get(j);
						if(j==0)//��һ�������¼
						{
							//�ϼ������һ���������ݣ�����Ҳ���ȡ�����������ʱ����ľɱ��ۼ�������λΪKWh��ͳһת��ΪMJ�����������תΪKWh
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
						if(j==oldMeterList.size()-1)//�����±�����
						{
							pdPower=Arith.add(pdPower,Arith.subtract(endPower,re.getMeterInit(),4), 4);
						}
					}
				}
				else
				{
					//������¿����û���������=��ů�����ۼ�����-��������ж��û��Ƿ��¿�������ͨ����ѯ�շ���ϸ���Ƿ��й������Ѽ�¼�жϣ���û�У������¿����û�
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
				pdPower=Arith.dataFormat(pdPower);//MJתǧ��ʱ
				//���ݼ۸񷽰������뷽��״̬���ң����ҵ�ǰ������ֻ��һ������,��ǰ����Ϊ1
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
					else if(2==priceType)//���ݷ���һ
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
							int n=getPriceDetail(pdPower,details);//��ǰ�û����������ڵڼ���
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
							int n=getPriceDetail(pdPower,details);//��ǰ�û����������ڵڼ���
							double pdPowers=0;//����������
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
				//��ѯ���û���Ԥ������
				List preList=hod2000PreReceiveDAO.findByRoomId(Integer.parseInt(obj[6].toString()));
				if(preList.size()>0)
				{
					Hod2000PreReceive pre=(Hod2000PreReceive) preList.get(0);
					preMoney=pre.getPrMoney();
				}
				//Ӧ����Ϊtotal
				re.setReceiveMoney(total);
				re.setPreMoney(preMoney);
				list.add(re);
			}
		}
		return list;
	}

	/**
	 * �շ�ͳ��-->ͳ�ƻ���
	 */
	public ReceiveSummary getSummary() {
		ReceiveSummary summary=new ReceiveSummary();
		Hod2000Receive hod2000Receive=null;
		double returnMoney=0;//�˿���
		double fillMoney=0;//�������
		double preMoney=0;//Ԥ�տ���
		double totalMoney=0;//Ӧ���ܽ��
		summary.setUserNum(hod2000ReceiveDAO.getReceiveNum(1));
		summary.setUnCollectUserNum(hod2000ReceiveDAO.getReceiveNum(0));
		//��ѯδ�շѵķ�����
		List list=hod2000ReceiveDAO.findReceiveRoomId();
		for (int i = 0; i <list.size(); i++) {
			//��ѯÿ����������һ�θ��Ѽ�¼�����Ϊ�󸶷�prAfter��1
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
	 * ���ݷ���Ž����շ�
	 */
	public GetReceive getCharge(int roomId) {
		double totalMoney=0;//�۸񷽰����
		double startPower=0;//��ů��ʼ�ۼ�����
		double endPower=0;//��ů�����ۼ�����
		double pdPower=0;//������
		double basePrice=0;//�������õ���
		GetReceive re=new GetReceive();
		Object[] obj;
		//���ݷ���Ų�ѯ����Լ�������Ϣ
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
			//�����շѷ�ʽ
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Heatingparameter.class);
			List heat=heatingparameterDAO.findByCriteria(dc);
			if(heat.size()>0)
			{
				Hod2000Heatingparameter heatingparameter=(Hod2000Heatingparameter) heat.get(0);
				re.setReType(heatingparameter.getHpType());
				int months=Utils.getMonths(heatingparameter.getHpStart(), heatingparameter.getHpEnd());//��������
				String hpStartTime=Utils.dateToStrLong(heatingparameter.getHpStart());
				String hpEndTime=Utils.dateToStrLong(heatingparameter.getHpEnd());
				//startPower=historyDAO.findEnergyMeterNameFirst(re.getMeterName(),hpStartTime,hpEndTime);//��ů��ʼ�ۼ�����
				//endPower=historyDAO.findEnergyMeterNameEnd(re.getMeterName(),hpStartTime,hpEndTime);//��ů�����ۼ�����
				//startPower=freezeDAO.findFirst(re.getMeterName(),hpStartTime,hpEndTime);//��ů��ʼ�ۼ��������¶�������
				//�ϸ��������һ���¶�������
				List last=freezeDAO.findLastEnergy(re.getMeterName(),hpStartTime);
				if(last.size()>0)
					startPower=Double.parseDouble(last.get(0).toString());
				else
					startPower=re.getMeterInit();
				endPower=freezeDAO.findEnd(re.getMeterName(),hpStartTime,hpEndTime);//��ů�����ۼ��������¶�������
				//�����ڹ�ů��֮�ڵĻ����¼
				List oldMeterList=hod2000MeterDAO.findByRoomId(roomId,hpStartTime,hpEndTime);
				if(oldMeterList.size()>0)
				{
					Object[] obj2;
					for (int i = 0; i <oldMeterList.size(); i++) {
						obj2=(Object[]) oldMeterList.get(i);
						if(i==0)//��һ�������¼
						{
							//�ϼ������һ���������ݣ�����Ҳ���ȡ�����������ʱ����ľɱ��ۼ�������λΪKWh��ͳһת��ΪMJ�����������תΪKWh
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
						else//�������ϻ���ľɱ�����
						{
							pdPower=Arith.add(pdPower,Arith.subtract(Arith.KWHToMJ(obj2[2].toString()),obj2[1].toString(),4), 4);
						}
						if(i==oldMeterList.size()-1)//��������ʹ�õ��±�����
						{
							pdPower=Arith.add(pdPower,Arith.subtract(endPower,re.getMeterInit(),4), 4);
						}
					}
				}
				else
				{
					//������¿����û���������=��ů�����ۼ�����-��������ж��û��Ƿ��¿�������ͨ����ѯ�շ���ϸ���Ƿ��й������Ѽ�¼�жϣ���û�У������¿����û�
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
				//���ݼ۸񷽰������뷽��״̬���ң����ҵ�ǰ������ֻ��һ������,��ǰ����Ϊ1
				Hod2000Price price=hod2000PriceDAO.findByParams(re.getPType(),1);
				if(price!=null)
				{
					List<Hod2000PriceDetail> details=hod2000PriceDAO.getPriceDetailsById(price.getPid());
					//����۸񷽰���������+������
					if(1==re.getPType())
					{
						re.setPriceType("����۸񷽰�");
						for (Hod2000PriceDetail hod2000PriceDetail : details) {
							//totalMoney=(re.getRoomSize()*hod2000PriceDetail.getPdBasePrice()*months)+(pdPower*hod2000PriceDetail.getPdPowerPrice());
							totalMoney=Arith.add(Arith.multiply(re.getRoomSize(), hod2000PriceDetail.getPdBasePrice(), months, 4), Arith.multiply(pdPower, hod2000PriceDetail.getPdPowerPrice(), 4), 2);
						}
					}
					//���ݷ���һ,������+����������
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
							int n=getPriceDetail(pdPower,details);//��ǰ�û����������ڵڼ���
							if(n==0) n=1;//Ϊ�������߲������Ľ��ݵ�ʱ�򰴵�һ���ݼ۸���
							totalMoney=Arith.add(Arith.multiply(re.getRoomSize(), basePrice, months, 4), Arith.multiply(pdPower, map.get(n).getPdPowerPrice(), 4), 2);
						}
						re.setPriceType("���ݼ۸񷽰�һ");
					}
					//���ݷ�����,������+����������
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
							int n=getPriceDetail(pdPower,details);//��ǰ�û����������ڵڼ���
							double pdPowers=0;//����������
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
						re.setPriceType("���ݼ۸񷽰���");
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
