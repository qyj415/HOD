package com.hod.pro.model.service;

import java.util.List;

/**
 * @author JSmart Tools
 */
 
public class Hod2000HeatingparameterService extends Service implements IHod2000HeatingparameterService {

	public int findReceiveParams() {
		List list=genericDAO.findByNHQL("select count(*) from hod2000_heatingparameter where hp_start is not null and hp_end is not null and hp_type is not null");
		if(list.size()>0)
			return Integer.parseInt(list.get(0).toString());
		else
			return 0;
	}

	public int findPreReceiveParams() {
		List list=genericDAO.findByNHQL("select count(*) from hod2000_heatingparameter where hp_months is not null and hp_factor is not null");
		if(list.size()>0)
			return Integer.parseInt(list.get(0).toString());
		else
			return 0;
	}
 

}
