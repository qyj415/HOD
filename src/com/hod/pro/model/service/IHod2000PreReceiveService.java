package com.hod.pro.model.service;

import java.util.List;
import com.hod.javabean.PreReceive;

/**
 * @author gngyf15
 */
 
public interface IHod2000PreReceiveService extends IService {

	public abstract List findByRoomId(int roomId);

	public abstract PreReceive getPreReceiveInfo();

	public abstract List findNotPay();

}
