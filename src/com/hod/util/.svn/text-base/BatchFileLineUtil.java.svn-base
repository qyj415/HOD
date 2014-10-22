package com.hod.util;

import java.util.List;
import com.hod.pojo.Hod2000BatchClientError;
import com.hod.pojo.Hod2000Client;
import com.hod.pojo.Hod2000Room;
import com.hod.pro.model.service.IHod2000BatchClientErrorService;
import com.hod.pro.model.service.IHod2000ClientService;
import com.hod.pro.model.service.IHod2000RoomService;

public class BatchFileLineUtil {
	
	/**
	 * 字符串中包含,的个数
	 * @param str
	 * @return
	 */
	public static int getSize(String str)
	{
		int num = 0; 
		char[] arr = str.toCharArray(); 
		for(int i = 0; i < arr.length; i++){ 
		  if(",".equals(String.valueOf(arr[i]))){ 
			  num= num+1; 
		  } 
		} 
		return num;
	}
	/**
	 * 批量开户功能行数据处理
	 * @param line
	 * @param hod2000ClientService
	 * @param hod2000RoomService
	 * @param maxSize
	 * @return
	 */
	public static String addClientlineUtil(String line,IHod2000ClientService hod2000ClientService,IHod2000RoomService hod2000RoomService,int maxSize){

		try {
			//用户姓名,用户性别,证件类型,证件号码,联系地址,联系电话,价格方案, 地理位置,备注信息
			String[] lines = line.split(",");
			if ( getSize(line)< 8) {
				return ("数据格式错误," + line);
			}
			//判断当前注册用户数是否小于最大用户数
			int size = hod2000ClientService.findCount();
			if (maxSize <= size) {
				return ("注册用户数大于最大用户数," + line);
			}
			if (lines[7] != null && !"".equals(lines[7])) {
				String[] addrs = lines[7].split("\\|");
				for (int a = 0; a < addrs.length; a++) {
					String sql = "SELECT room_id FROM client_address WHERE address='"
							+ addrs[a] + "'";
					List room_list = hod2000ClientService.findByNHQL(sql);
					if (room_list == null || room_list.size() == 0) {
						return ("地址位置" + addrs[a] + "有误," + line);
					}
					//该房间是否已经存在用户
					String sql1="SELECT count(client_id) FROM hod2000_room where room_id="+room_list.get(0).toString();
					List client_list=hod2000ClientService.findByNHQL(sql1);
					int client_count=Integer.parseInt(client_list.get(0).toString());
					if(client_count>0)
					{
						return ("该房间信息中已经绑定用户," + line);
					}
					//该房间是否已经存在表
					String sql2="SELECT count(*) FROM meter_address where meter_able=1 and room_id="+room_list.get(0).toString();
					List meter_list=hod2000ClientService.findByNHQL(sql2);
					int meter_count=Integer.parseInt(meter_list.get(0).toString());
					if(meter_count==0)
					{
						return ("该房间信息未关联表计信息," + line);
					}
					//判断证件号码的唯一性（有效的）
					int count = hod2000ClientService.findByCardNo(lines[3]);
					if (count > 0) {
						return ("证件号码已注册," + line);
					}

					//确定价格方案类型
					int priceType = 0;
					if (lines[6] != null && lines[6].equals("定额价格方案")) {
						priceType = 1;
					} else if (lines[6] != null && lines[6].equals("阶梯价格方案一")) {
						priceType = 2;
					} else if (lines[6] != null && lines[6].equals("阶梯价格方案二")) {
						priceType = 3;
					} else {
						return ("价格方案类型错误," + line);
					}

					//确定证件类型
					int cardType = 0;
					if (lines[2] != null && lines[2].endsWith("身份证")) {
						cardType = 0;
					} else if (lines[2] != null && lines[2].equals("港澳通行证")) {
						cardType = 1;
					} else if (lines[2] != null && lines[2].equals("台湾通行证")) {
						cardType = 2;
					} else if (lines[2] != null && lines[2].equals("护照")) {
						cardType = 3;
					} else {
						return ("身份证类型错误," + line);
					}

					//保存开户信息-》开户信息表
					Hod2000Client hod2000Client = new Hod2000Client();
					hod2000Client.setClientName(lines[0]);
					hod2000Client.setClientSex(lines[1]);
					hod2000Client.setClientCardType(cardType);
					hod2000Client.setClientIdentity(lines[3]);
					hod2000Client.setClientAddress(lines[4]);
					hod2000Client.setClientTel(lines[5]);
					hod2000Client.setClientOpenTime(NetworkTimeUtil.getDate());
					hod2000Client.setClientEnable(1);//状态默认为正常

					hod2000ClientService.save(hod2000Client);
					Hod2000Room hod2000Room = (Hod2000Room) hod2000RoomService
							.findById(Integer.parseInt(room_list
									.get(0).toString()));
					hod2000Room.setPriceType(priceType);
					hod2000Room.setHod2000Client(hod2000Client);
					hod2000RoomService.update(hod2000Room);
					return null;
				}
			}else{
				return ("地理位置错误," + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ("数据异常：" + e.getMessage() + ","  + line);
		}
		return null;
	}
	
	public static void addClientsavahod2000BatchClientError(List<String> err_al,IHod2000BatchClientErrorService hod2000BatchClientErrorService){
		try {
					//处理错误数据——》插入错误数据表
		if(err_al.size() > 0){
			for(String err_str : err_al){
				String[] errs = err_str.split(",");
				if(errs.length < 10){
					String[] temp_errs = new String[10];
					//防止数组下标越界
					System.arraycopy(errs, 0, temp_errs, 0, errs.length);
					errs = temp_errs;
				}
				//错误数据,用户姓名,用户性别,证件类型,证件号码,联系地址,联系电话,价格方案, 地理位置,备注信息
				Hod2000BatchClientError hod2000BatchClientError = new Hod2000BatchClientError();
				hod2000BatchClientError.setErrorMsg(errs[0]);
				hod2000BatchClientError.setClientName(errs[1]);
				hod2000BatchClientError.setClientSex(errs[2]);
				hod2000BatchClientError.setClientCardType(errs[3]);
				hod2000BatchClientError.setClientCardNumber(errs[4]);
				hod2000BatchClientError.setClientAddress(errs[5]);
				hod2000BatchClientError.setClientTel(errs[6]);
				hod2000BatchClientError.setPtype(errs[7]);
				hod2000BatchClientError.setRoomAddress(errs[8]);
				hod2000BatchClientError.setRoomRemark(errs[9]);
				//判断错误数据是否已存在，存在不插入
				if((hod2000BatchClientErrorService.findByCardNo(errs[4])) == 0 ){ 
					hod2000BatchClientErrorService.save(hod2000BatchClientError);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
