package com.hod.timetask;

import java.util.List;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hod.pojo.Hod2000Price;
import com.hod.pro.model.service.IHod2000PriceService;
import com.hod.util.Utils;

public class DoSelectPrice extends TimerTask {
	private Logger log = Logger.getLogger(DoSelectPrice.class.getName());
	ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");
	IHod2000PriceService hod2000PriceService=(IHod2000PriceService) ctx.getBean("springHod2000PriceService");

	@Override
	public void run() {
		try {
//			Object[] objs;
//			List list=hod2000PriceService.findByNHQL("select p_id,p_type from hod2000_price where p_startDate='"+Utils.dateToStr(Utils.getNowDateShort())+"' and p_status=2");
//			if(list.size()>0)
//			{
//				for (int i = 0; i < list.size(); i++) {
//					objs=(Object[]) list.get(0);
//					Hod2000Price hod2000Price=(Hod2000Price) hod2000PriceService.findByHQL("from Hod2000Price where pstatus=1 and ptype="+Integer.parseInt(objs[1].toString())).get(0);
//					hod2000Price.setPstatus(3);//设置该类型的价格方案的当前方案为过期方案
//					hod2000PriceService.update(hod2000Price);
//					Hod2000Price price=(Hod2000Price) hod2000PriceService.findByHQL("from Hod2000Price where pid="+Integer.parseInt(objs[0].toString())).get(0);
//					price.setPstatus(1);//设置当前符合条件的价格方案为当前方案
//					hod2000PriceService.update(price);
//				}
//			}
			log.info("价格方案更新");
			for(int i=1;i<4;i++)
			{
				Hod2000Price hod2000Price=null;
				Hod2000Price price=null;
				List list=hod2000PriceService.findByHQL("from Hod2000Price where pstatus=2 and pstartTime<='"+Utils.dateToStr(Utils.getNowDateShort())+"' and ptype="+i+" order by pstartTime desc");
				if(list.size()>0)
				{
					hod2000Price=(Hod2000Price) list.get(0);
					List list2=hod2000PriceService.findByHQL("from Hod2000Price where pstatus=1 and ptype="+i);
					if(list2.size()>0)
					{
						price=(Hod2000Price)list2.get(0);
						price.setPstatus(3);
						hod2000PriceService.update(price);//当前方案改为过期方案
					}
					hod2000Price.setPstatus(1);
					hod2000PriceService.update(hod2000Price);//预订方案改为当前方案
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
