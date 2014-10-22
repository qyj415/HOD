package com.hod.pro.model.service;

import java.util.List;
import com.hod.javabean.GetReceive;
import com.hod.javabean.ReceiveSummary;
import com.hod.javabean.UnCollectReceive;

/**
 * @author gngyf15
 */
 
public interface IHod2000ReceiveService extends IService {

	public abstract List findByUserId(int userId);

	public abstract List<UnCollectReceive> findUnCollected(String page,String pageSize);

	public abstract ReceiveSummary getSummary();

	public abstract GetReceive getCharge(int roomId);

}
