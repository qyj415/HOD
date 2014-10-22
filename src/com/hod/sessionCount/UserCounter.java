package com.hod.sessionCount;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

import org.apache.log4j.Logger;
/**
 * 计数器
 * @author xp1204 Email:xp1204@gmail.com
 * @see pagerHelper.java
 * 
 * 使用方法，
 * 1，在web.xml中加上
 * <listener>
 <listener-class>
 com.xp1204.util.UserCounter
 </listener-class>
 </listener>
 
 2，在登陆后掉用
 UserCounter.updateUserCounter(session1.getId(),DateUtil.formatyyyyMMddHHmmss(new Date()),request.getRemoteHost(),user.getUId()+":"+user.getUName());
 */
public class UserCounter implements HttpSessionListener {
	// public static int counter;
	public static ArrayList<UserSession> userSession = new ArrayList<UserSession>();

	public static long offtime = 15*1000;//15s外没有响应的，认为不在线，剔除，15s的认为在线
	
	public static java.util.HashMap<String,javax.servlet.http.HttpSession> sessionl = new java.util.HashMap<String,javax.servlet.http.HttpSession>();

	public static Logger log = Logger.getLogger("");

	public void sessionCreated(HttpSessionEvent hse) {

		log.info("Session created,session id is:" + hse.getSession().getId());
		incrementUserCounter(hse.getSession().getId());
		sessionl.put(hse.getSession().getId(), hse.getSession());

	}

	public void sessionDestroyed(HttpSessionEvent hse) {
		log.info("Session destroyed,session id is:" + hse.getSession().getId());
		decrementUserCounter(hse.getSession().getId());
		sessionl.remove(hse.getSession());

	}

	synchronized void incrementUserCounter(String sid) {
		// counter++;
		UserSession us = new UserSession();
		us.sid = sid;
		userSession.add(us);
		us = null;
		log.info("User Count: " + userSession.size());
	}

	public static synchronized void decrementUserCounter(String sid) {
		// counter--;
		javax.servlet.http.HttpSession ss =(javax.servlet.http.HttpSession) UserCounter.sessionl.get(sid);
		if (ss!=null) {
			ss.invalidate();
			 UserCounter.sessionl.remove(ss); 
		}
		for (int i = 0; i < userSession.size(); i++) {
			if (sid.equals(((UserSession) userSession.get(i)).sid)) {
				userSession.remove(i);
				break;
			}
		}
		log.info("User Count: " + userSession.size());
	}

	public static synchronized void updateUserCounter(String sid, String date,
			String ip, String userid) {
		// counter--;
		for (int i = 0; i < userSession.size(); i++) {
			if (sid.equals(((UserSession) userSession.get(i)).sid)) {
				// userSession.remove(i);
				UserSession obj = (UserSession) userSession.get(i);
				obj.ip = ip;
				obj.userid = userid;
				obj.date = date;
				obj.lastonlinetime = System.currentTimeMillis();
				userSession.set(i, obj);
				obj = null;
				break;
			}
		}
		//log.info("update User info:" + sid);
	}

	public class UserSession {
		public String sid;

		public String date;

		public String ip;

		public String userid;

		public long lastonlinetime = 0;
		public UserSession() {

		}
	}
}
