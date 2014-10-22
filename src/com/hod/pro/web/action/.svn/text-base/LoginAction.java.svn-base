package com.hod.pro.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.hod.pojo.Hod2000Operator;
import com.hod.pojo.Hod2000Permission;
import com.hod.pojo.Hod2000Role;
import com.hod.pro.model.service.IHod2000OperatorService;
import com.hod.pro.model.service.IHod2000PermissionService;
import com.hod.pro.model.service.IHod2000RoleService;
import com.hod.service.CheckCodeService;
import com.hod.sessionCount.UserCounter;
import com.hod.sessionCount.UserCounter.UserSession;
import com.hod.util.EncryptData;
import com.hod.util.InitServlet;
import com.hod.util.MD5;
import com.hod.util.Message;
import com.hod.util.NetworkTimeUtil;
import com.hod.util.OperatorLog;
import com.hod.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 *LoginAction 后台登陆控制器
 * @author yixiang
 */
public class LoginAction extends ActionSupport {

	private HttpServletRequest request;
	private IHod2000OperatorService hod2000OperatorService;
	private Hod2000Operator hod2000Operator;
	private IHod2000RoleService hod2000RoleService;
	private IHod2000PermissionService hod2000PermissionService;
	Message msg = Message.getInstance();
	EncryptData encryptData = new EncryptData();
	// 日志信息操作实例
	static Logger log = Logger.getLogger(LoginAction.class.getName());

	// 用户登陆
	public String doLogin() {
		int usernumber = 0;
		request = ServletActionContext.getRequest();
		try {
			log.info("UserCounter.userSession.size="
					+ UserCounter.userSession.size());
			int onlinecount = 0;
			for (int i = 0; i < UserCounter.userSession.size(); i++) {
				UserSession obj = (UserSession) UserCounter.userSession.get(i);
				log.info("obj.sid=" + obj.sid);
				log.info("obj.lastonlinetime=" + obj.lastonlinetime);
				log.info("obj.lastonlinetime11="
						+ (System.currentTimeMillis() - obj.lastonlinetime));
				if (System.currentTimeMillis() - obj.lastonlinetime > UserCounter.offtime
						&& (!(request.getSession().getId()).equals(obj.sid))) {
					// UserCounter.userSession.remove(i);
					log.info("失效清除" + obj.sid);
					UserCounter.decrementUserCounter(obj.sid);
					i--;
				} else {
					onlinecount++;
				}
			}
			log.info("UserCounter.userSession.size=" + onlinecount);
			// todo code this
			// 如果 onlinecount >= 最大+1，那么就不能登陆了。直接返回return message
			String file = InitServlet.localpath + "licenseFile1.a";

			String content = encryptData.decrypt(file);
			if (content != null && content.length() > 0) {
				String[] contents = content.split("\n");

				String sequence = contents[1];// 序列号
				String dateuser = info.xuyj.util.License.decodeKey(sequence);// 使用期限+用户名_用户数
				usernumber = Integer
						.parseInt(dateuser.substring(10).split("_")[1]);
				log.info("当前用户数：" + onlinecount + "许可用户数：" + usernumber);
				if (onlinecount > usernumber) {
					//throw new HodExecption("并发用户数超出！");
					msg.setError1("并发用户数超出！");
					msg.setLink(0, "<a href='" + request.getContextPath()
							+ "/login.jsp'>返回登陆界面</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
					
				} else {
					this.checklicenseFile1(onlinecount);
				}
			}
			log.info("UserCounter.userSession.size=" + onlinecount);
			String userName = request.getParameter("username");
			String userPassWord = request.getParameter("userpassword");
			// 根据用户名查询
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Operator.class);
			dc.add(Restrictions.eq("loginName", userName));
			List list=hod2000OperatorService.findByCriteria(dc);
			if (list.size() > 0) {
				hod2000Operator=(Hod2000Operator) list.get(0);
				// 密码加密再与数据库中查询出来的进行对比
				String pass = MD5.getMd5(userPassWord);
				if (pass.equals(hod2000Operator.getOperPassword())) {
					// 判断用户是否为无效
					if (1 != hod2000Operator.getOperEnable()) {
						//throw new HodExecption("用户状态为无效");
						msg.setError1("用户状态为无效!");
						msg.setLink(0, "<a href='" + request.getContextPath()
								+ "/login.jsp'>返回登陆界面</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				} else {
					//throw new HodExecption("用户名或密码错误");
					msg.setError1("用户名或密码错误!");
					msg.setLink(0, "<a href='" + request.getContextPath()
							+ "/login.jsp'>返回登陆界面</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
			} else {
				//throw new HodExecption("用户名或密码错误");
				msg.setError1("用户名或密码错误!");
				msg.setLink(0, "<a href='" + request.getContextPath()
						+ "/login.jsp'>返回登陆界面</a>");
				msg.setJump(false);
				request.setAttribute("message", msg);
				return "message";
			}
			request.getSession().setAttribute("user", hod2000Operator);
			log.info(request.getSession().getId());
			UserCounter.updateUserCounter(request.getSession().getId(), 
					Utils.dateToStrLong(new Date()), request.getRemoteHost(),
					hod2000Operator.getLoginName());
			request.getSession().setMaxInactiveInterval(-1);
			//用户登陆成功，查询其权限。1：根据用户登陆的id查询权限id集合 2：根据权限id集合查询权限名称与路径
			String rolePermission=hod2000RoleService.findByOperatorId(hod2000Operator.getOperId());
			if(rolePermission!=null&&!"".equals(rolePermission))
			{
				String permission=hod2000PermissionService.findByRolePermission(rolePermission);
				request.getSession().setAttribute("permission", permission);
			}
			OperatorLog.addOperatorLog("用户登陆");
		} catch (Exception e) {
			// 设置错误信息
			msg.setError1("错误信息：" + e.toString());
			msg.setLink(0, "<a href='" + request.getContextPath()
					+ "/login.jsp'>返回登陆界面</a>");
			msg.setJump(false);
			log.error("LoginAction-->doLogin", e);
			request.setAttribute("message", msg);
			return "message";
		}
		return "login";
	}

	// 注销登陆
	public String logout() throws Exception {
		request = ServletActionContext.getRequest();
		OperatorLog.addOperatorLog("注销登陆");
		request.getSession().setAttribute("user", null);
		request.getSession().invalidate();
		return "unlogin";
	}

	// 用户注册
	public String register() {
		int now = 0;
		HttpServletRequest request = ServletActionContext.getRequest();
		Message message = Message.getInstance();
		String userName = Utils.checkStr(request.getParameter("userName"));
		String sequence = Utils.checkStr(request.getParameter("sequence"));
		try {
			String f = info.xuyj.util.License.decodeKey(sequence);// 将输入的序列号进行解密
			String[] suq = f.split("_"); // 将解密后的内容按-进行分割
			String name = suq[0];// 截止日期+用户名，因为还须进行截取
			String number = suq[1];
			request.setAttribute("number", number);
			String[] date = (name.substring(0, 10)).split("-");// 文件软件截止日期
			String filename = name.substring(10);// 截取日期的前十位
			String d = date[0];
			String t = date[1];
			String e = date[2];
			int filedate = Integer.parseInt((d + t + e).substring((d + t + e)
					.length() - 6));// 截取日期的前六位并强转为数字类型
			Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置显示格式
			String nowTime = df.format(dt);// 系统当前时间
			try {
				String[] nowdate = nowTime.split("-");// 分割当前时间按“-”分割
				String no = nowdate[0];
				String da = nowdate[1];
				String te = nowdate[2];
				String newnowDate = no + da + te;// 系统当前时间组合
				now = Integer.parseInt(newnowDate
						.substring(newnowDate.length() - 6));// 将系统的时间转化为数字并截取后六位
				// num=Integer.parseInt(number);//页面输入序列号解析到的用户数
			} catch (Exception err) {
				log.error(err);
				message.setMsg1("错误！");
				message.setJump(false);
			}
			if (filedate >= now) {// 如果截止日期小于系统当前时间，则提示，软件过期，请联系管理员
				// 将页面得到的用户名与解析后的用户名相比较、用户数、软件版本号进行验证。
				if (userName.equals(filename)) {// && last<now && num>0 &&
												// num<99
					// 注册成功后创建licenseFile.dat文件，将用户名、用户数、软件版本号、硬件信息写入到文件中。
					String address = CheckCodeService.testGetSysInfo();
					int i = 1;
					String ii = info.xuyj.util.License.encodeKey(address, i);
					String content = userName + "\n" + sequence + "\n" + ii
							+ "\n" + InitServlet.VER;
					encryptData.encrypt(content, InitServlet.localpath
							+ "licenseFile1.a");
					message.setMsg1("注册成功！请重新登陆或刷新页面，已获取最新权限！");
					OperatorLog.addOperatorLog("用户注册");
					message.setJump(false);
				} else {
					message.setError1("用户名或序列号错误，请重新注册！");
					message.setLink(0, "<a href='" + request.getContextPath()
							+ "/login!registerMessage.do'>用户注册</a>");
					message.setJump(false);
				}

			}

		} catch (Exception e) {
			log.error(e);
			message.setError1("用户名或序列号错误，请重新注册！");
			message.setLink(0, "<a href='" + request.getContextPath()
					+ "/login!registerMessage.do'>用户注册</a>");
		} finally {
			request.setAttribute("message", message);
		}
		return "message";
	}
	
	//注册页面
	public String registerMessage() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		// 按行读取文件
		try{ 	
            String file=InitServlet.localpath+"licenseFile1.a";
			String content=encryptData.decrypt(file);//解析文件内容，还须再处理，对比每一行
			if(content!=null&&!"".equals(content))
			{
				String[] contents=content.split("\n");
				String sequence=contents[1];//序列号
				String dateuser=info.xuyj.util.License.decodeKey(sequence);
				//使用期限+用户名_用户数
				String lastdate=dateuser.substring(0, 10);//使用期限
				int usernumber=Integer.parseInt(dateuser.substring(10).split("_")[1]);
				String username = dateuser.substring(10).split("_")[0];
				request.setAttribute("one", username);
				request.setAttribute("four", contents[3]);
				request.setAttribute("usernumber",usernumber);
				request.setAttribute("lastdate",lastdate);
			}
        }catch(Exception e){
        	log.error(e);
        }
		return "help";
	}

	/**
	 * 检查licenseFile.dat文件 tiansi
	 * 
	 * @return
	 */
	public boolean checklicenseFile1(int onlinecount) throws Exception {
		boolean bool = false;
		HttpServletRequest request = ServletActionContext.getRequest();
		EncryptData encryptData = new EncryptData();
		int usernumber = 0;
		long now = 0;
		long lasttime = 0;
		// 按行读取文件
		String file = InitServlet.localpath + "licenseFile1.a";

		String content = encryptData.decrypt(file);// 解析文件内容，还须再处理，对比每一行

		if (content == null) {
			log.info("注册信息不正确");

			request.getSession().setAttribute("x", 1);
			return bool;
		}
		String[] contents = content.split("\n");

		if (contents.length < 4) {
			log.info("注册信息不正确");
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		String sequence = contents[1];// 序列号
		String dateuser = info.xuyj.util.License.decodeKey(sequence);// 使用期限+用户名_用户数
		String lastdate = dateuser.substring(0, 10);// 使用期限
		log.info("lastdate" + lastdate);
		usernumber = Integer.parseInt(dateuser.substring(10).split("_")[1]);

		String username = dateuser.substring(10).split("_")[0];

		// 得到文件中的使用期限、系统当前时间进行比较，如果使用期限大于系统当前时间则软件过期
		String[] date = lastdate.split("-");
		String time = date[0];
		String time1 = date[1];
		String time2 = date[2];
		String ortime = time + time1 + time2;
		lasttime = Long.parseLong(ortime);

		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置显示格式
		String nowTime = df.format(dt);// 用DateFormat的format()方法在dt中获取并以yyyy/MM/dd
										// HH:mm:ss格式显示
		try {
			now = Long.parseLong(nowTime);// 将系统的时间转化为数字
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("", "");
		}
		log.info(now + "lasttime:" + lasttime);

		if (lasttime < now) {
			log.info("注册信息不正确");
			request.getSession().setAttribute("x", 1);
			return bool;
		}
		if (onlinecount > usernumber) {
			log.info("注册信息不正确");
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		if (!contents[0].equals(username)) {
			log.info("注册信息不正确");
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		String address1 = CheckCodeService.testGetSysInfo();
		if (!info.xuyj.util.License.decodeKey(contents[2]).split("_")[0]
				.equals(address1)) {
			log.info("注册信息不正确:" + address1);
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		if (!contents[3].equals(InitServlet.VER)) {
			log.info("注册信息不正确");
			request.getSession().setAttribute("x", 1);
			return bool;
		}
		request.getSession().setAttribute("x", 5);

		return bool;
	}

	public IHod2000OperatorService getHod2000OperatorService() {
		return hod2000OperatorService;
	}

	public void setHod2000OperatorService(
			IHod2000OperatorService hod2000OperatorService) {
		this.hod2000OperatorService = hod2000OperatorService;
	}

	public Hod2000Operator getHod2000Operator() {
		return hod2000Operator;
	}

	public void setHod2000Operator(Hod2000Operator hod2000Operator) {
		this.hod2000Operator = hod2000Operator;
	}

	public IHod2000RoleService getHod2000RoleService() {
		return hod2000RoleService;
	}

	public void setHod2000RoleService(IHod2000RoleService hod2000RoleService) {
		this.hod2000RoleService = hod2000RoleService;
	}

	public void setHod2000PermissionService(
			IHod2000PermissionService hod2000PermissionService) {
		this.hod2000PermissionService = hod2000PermissionService;
	}
	
}
