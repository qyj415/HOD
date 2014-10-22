package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Community;

/**
 * Hod2000CommunityDAO
 * @author yixiang
 */
public class Hod2000CommunityDAO extends GenericHibernateDAO<Hod2000Community, String> implements IHod2000CommunityDAO {

	public List findByVillageId(int id) {
		// TODO Auto-generated method stub
		return findByNHQL("select community_id,community_name,community_code from hod2000_community where village_id="+id);
	}
 
}
