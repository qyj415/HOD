package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Community;

/**
 * Hod2000CommunityDAO
 * @author yixiang
 */
public interface IHod2000CommunityDAO  extends GenericDAO<Hod2000Community,String> {

	public abstract List findByVillageId(int id);

}
