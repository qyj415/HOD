package com.hod.pro.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000Community;
import com.hod.pojo.Hod2000Village;
import com.hod.pro.model.dao.IHod2000CommunityDAO;
import com.hod.pro.model.dao.IHod2000VillageDAO;

/**
 * @author yixiang
 */
 
public class Hod2000CommunityService extends Service implements IHod2000CommunityService {

	private IHod2000CommunityDAO hod2000CommunityDAO;
	private IHod2000VillageDAO hod2000VillageDAO;
	
	public void setHod2000CommunityDAO(IHod2000CommunityDAO hod2000CommunityDAO) {
		this.hod2000CommunityDAO = hod2000CommunityDAO;
	}
	
	public void setHod2000VillageDAO(IHod2000VillageDAO hod2000VillageDAO) {
		this.hod2000VillageDAO = hod2000VillageDAO;
	}

	public List findByVillageId(int id) {
		// TODO Auto-generated method stub
		return hod2000CommunityDAO.findByVillageId(id);
	}

	public int findByCommunityName(Integer villageId, String communityName) {
		return Integer.parseInt(hod2000CommunityDAO.findByNHQL("select count(*) from hod2000_community where village_id="+villageId+" and community_name='"+communityName+"'").get(0).toString());
	}

	public int findByCommunityName(Integer villageId, String communityName,
			Integer communityId) {
		return Integer.parseInt(hod2000CommunityDAO.findByNHQL("select count(*) from hod2000_community where village_id="+villageId+" and community_name='"+communityName+"' and community_id!="+communityId).get(0).toString());
	}

	public String getCode(int villageId) {
		//行政编码 自动生成
		String hql = "from Hod2000Community WHERE hod2000Village.villageId="+villageId;
		List<Hod2000Community> lists = genericDAO.findByHQL(hql);
		Collections.sort(lists);
		String saveCode = "";
		int communityCode =0;
		if(lists.size()>0)
			communityCode=Integer.parseInt(lists.get(0).getCommunityCode());
		if(communityCode<999){
			communityCode++;
			saveCode = String.valueOf(communityCode);
		}else{
			for(int i=lists.size()-1;i>=0;i--){
				communityCode = Integer.parseInt(lists.get(i).getCommunityCode());
				if((i==lists.size()-1)&&(communityCode-1>0)){
					communityCode--;
					saveCode = String.valueOf(communityCode);
					break;
				}
				int communityCodeNext = Integer.parseInt(lists.get(i-1).getCommunityCode());
				if((communityCodeNext-communityCode)>1){
					communityCode = --communityCodeNext;
					saveCode = String.valueOf(communityCode);
					break;
				}
			}
		}
		
		if(saveCode.length()==1){
			saveCode = "00"+saveCode;
		}else if(saveCode.length()==2){
			saveCode = "0"+saveCode;
		}
		return saveCode;
	}

	public IHod2000CommunityDAO getHod2000CommunityDAO() {
		return hod2000CommunityDAO;
	}

	public List<Hod2000Community> findCommunityVillageId(int villageId) {
		List list=hod2000CommunityDAO.findByNHQL("select community_id,community_name,village_id from hod2000_community where village_id="+villageId);
		Object[] obj;
		Hod2000Community community;
		List<Hod2000Community> list2=new ArrayList<Hod2000Community>();
		for (int i = 0; i < list.size(); i++) {
			obj=(Object[]) list.get(i);
			community=new Hod2000Community();
			community.setCommunityId(Integer.parseInt(obj[0].toString()));
			community.setCommunityName(obj[1].toString());
			community.setHod2000Village((Hod2000Village)hod2000VillageDAO.findById(Integer.parseInt(obj[2].toString())));
			list2.add(community);
		}
		return list2;
	}
 

}
