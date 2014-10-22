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
 *LoginAction ��̨��½������
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
	// ��־��Ϣ����ʵ��
	static Logger log = Logger.getLogger(LoginAction.class.getName());

	// �û���½
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
					log.info("ʧЧ���" + obj.sid);
					UserCounter.decrementUserCounter(obj.sid);
					i--;
				} else {
					onlinecount++;
				}
			}
			log.info("UserCounter.userSession.size=" + onlinecount);
			// todo code this
			// ��� onlinecount >= ���+1����ô�Ͳ��ܵ�½�ˡ�ֱ�ӷ���return message
			String file = InitServlet.localpath + "licenseFile1.a";

			String content = encryptData.decrypt(file);
			if (content != null && content.length() > 0) {
				String[] contents = content.split("\n");

				String sequence = contents[1];// ���к�
				String dateuser = info.xuyj.util.License.decodeKey(sequence);// ʹ������+�û���_�û���
				usernumber = Integer
						.parseInt(dateuser.substring(10).split("_")[1]);
				log.info("��ǰ�û�����" + onlinecount + "����û�����" + usernumber);
				if (onlinecount > usernumber) {
					//throw new HodExecption("�����û���������");
					msg.setError1("�����û���������");
					msg.setLink(0, "<a href='" + request.getContextPath()
							+ "/login.jsp'>���ص�½����</a>");
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
			// �����û�����ѯ
			DetachedCriteria dc=DetachedCriteria.forClass(Hod2000Operator.class);
			dc.add(Restrictions.eq("loginName", userName));
			List list=hod2000OperatorService.findByCriteria(dc);
			if (list.size() > 0) {
				hod2000Operator=(Hod2000Operator) list.get(0);
				// ��������������ݿ��в�ѯ�����Ľ��жԱ�
				String pass = MD5.getMd5(userPassWord);
				if (pass.equals(hod2000Operator.getOperPassword())) {
					// �ж��û��Ƿ�Ϊ��Ч
					if (1 != hod2000Operator.getOperEnable()) {
						//throw new HodExecption("�û�״̬Ϊ��Ч");
						msg.setError1("�û�״̬Ϊ��Ч!");
						msg.setLink(0, "<a href='" + request.getContextPath()
								+ "/login.jsp'>���ص�½����</a>");
						msg.setJump(false);
						request.setAttribute("message", msg);
						return "message";
					}
				} else {
					//throw new HodExecption("�û������������");
					msg.setError1("�û������������!");
					msg.setLink(0, "<a href='" + request.getContextPath()
							+ "/login.jsp'>���ص�½����</a>");
					msg.setJump(false);
					request.setAttribute("message", msg);
					return "message";
				}
			} else {
				//throw new HodExecption("�û������������");
				msg.setError1("�û������������!");
				msg.setLink(0, "<a href='" + request.getContextPath()
						+ "/login.jsp'>���ص�½����</a>");
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
			//�û���½�ɹ�����ѯ��Ȩ�ޡ�1�������û���½��id��ѯȨ��id���� 2������Ȩ��id���ϲ�ѯȨ��������·��
			String rolePermission=hod2000RoleService.findByOperatorId(hod2000Operator.getOperId());
			if(rolePermission!=null&&!"".equals(rolePermission))
			{
				String permission=hod2000PermissionService.findByRolePermission(rolePermission);
				request.getSession().setAttribute("permission", permission);
			}
			OperatorLog.addOperatorLog("�û���½");
		} catch (Exception e) {
			// ���ô�����Ϣ
			msg.setError1("������Ϣ��" + e.toString());
			msg.setLink(0, "<a href='" + request.getContextPath()
					+ "/login.jsp'>���ص�½����</a>");
			msg.setJump(false);
			log.error("LoginAction-->doLogin", e);
			request.setAttribute("message", msg);
			return "message";
		}
		return "login";
	}

	// ע����½
	public String logout() throws Exception {
		request = ServletActionContext.getRequest();
		OperatorLog.addOperatorLog("ע����½");
		request.getSession().setAttribute("user", null);
		request.getSession().invalidate();
		return "unlogin";
	}

	// �û�ע��
	public String register() {
		int now = 0;
		HttpServletRequest request = ServletActionContext.getRequest();
		Message message = Message.getInstance();
		String userName = Utils.checkStr(request.getParameter("userName"));
		String sequence = Utils.checkStr(request.getParameter("sequence"));
		try {
			String f = info.xuyj.util.License.decodeKey(sequence);// ����������кŽ��н���
			String[] suq = f.split("_"); // �����ܺ�����ݰ�-���зָ�
			String name = suq[0];// ��ֹ����+�û�������Ϊ������н�ȡ
			String number = suq[1];
			request.setAttribute("number", number);
			String[] date = (name.substring(0, 10)).split("-");// �ļ������ֹ����
			String filename = name.substring(10);// ��ȡ���ڵ�ǰʮλ
			String d = date[0];
			String t = date[1];
			String e = date[2];
			int filedate = Integer.parseInt((d + t + e).substring((d + t + e)
					.length() - 6));// ��ȡ���ڵ�ǰ��λ��ǿתΪ��������
			Date dt = new Date();// �������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// ������ʾ��ʽ
			String nowTime = df.format(dt);// ϵͳ��ǰʱ��
			try {
				String[] nowdate = nowTime.split("-");// �ָǰʱ�䰴��-���ָ�
				String no = nowdate[0];
				String da = nowdate[1];
				String te = nowdate[2];
				String newnowDate = no + da + te;// ϵͳ��ǰʱ�����
				now = Integer.parseInt(newnowDate
						.substring(newnowDate.length() - 6));// ��ϵͳ��ʱ��ת��Ϊ���ֲ���ȡ����λ
				// num=Integer.parseInt(number);//ҳ���������кŽ��������û���
			} catch (Exception err) {
				log.error(err);
				message.setMsg1("����");
				message.setJump(false);
			}
			if (filedate >= now) {// �����ֹ����С��ϵͳ��ǰʱ�䣬����ʾ��������ڣ�����ϵ����Ա
				// ��ҳ��õ����û������������û�����Ƚϡ��û���������汾�Ž�����֤��
				if (userName.equals(filename)) {// && last<now && num>0 &&
												// num<99
					// ע��ɹ��󴴽�licenseFile.dat�ļ������û������û���������汾�š�Ӳ����Ϣд�뵽�ļ��С�
					String address = CheckCodeService.testGetSysInfo();
					int i = 1;
					String ii = info.xuyj.util.License.encodeKey(address, i);
					String content = userName + "\n" + sequence + "\n" + ii
							+ "\n" + InitServlet.VER;
					encryptData.encrypt(content, InitServlet.localpath
							+ "licenseFile1.a");
					message.setMsg1("ע��ɹ��������µ�½��ˢ��ҳ�棬�ѻ�ȡ����Ȩ�ޣ�");
					OperatorLog.addOperatorLog("�û�ע��");
					message.setJump(false);
				} else {
					message.setError1("�û��������кŴ���������ע�ᣡ");
					message.setLink(0, "<a href='" + request.getContextPath()
							+ "/login!registerMessage.do'>�û�ע��</a>");
					message.setJump(false);
				}

			}

		} catch (Exception e) {
			log.error(e);
			message.setError1("�û��������кŴ���������ע�ᣡ");
			message.setLink(0, "<a href='" + request.getContextPath()
					+ "/login!registerMessage.do'>�û�ע��</a>");
		} finally {
			request.setAttribute("message", message);
		}
		return "message";
	}
	
	//ע��ҳ��
	public String registerMessage() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		// ���ж�ȡ�ļ�
		try{ 	
            String file=InitServlet.localpath+"licenseFile1.a";
			String content=encryptData.decrypt(file);//�����ļ����ݣ������ٴ����Ա�ÿһ��
			if(content!=null&&!"".equals(content))
			{
				String[] contents=content.split("\n");
				String sequence=contents[1];//���к�
				String dateuser=info.xuyj.util.License.decodeKey(sequence);
				//ʹ������+�û���_�û���
				String lastdate=dateuser.substring(0, 10);//ʹ������
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
	 * ���licenseFile.dat�ļ� tiansi
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
		// ���ж�ȡ�ļ�
		String file = InitServlet.localpath + "licenseFile1.a";

		String content = encryptData.decrypt(file);// �����ļ����ݣ������ٴ����Ա�ÿһ��

		if (content == null) {
			log.info("ע����Ϣ����ȷ");

			request.getSession().setAttribute("x", 1);
			return bool;
		}
		String[] contents = content.split("\n");

		if (contents.length < 4) {
			log.info("ע����Ϣ����ȷ");
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		String sequence = contents[1];// ���к�
		String dateuser = info.xuyj.util.License.decodeKey(sequence);// ʹ������+�û���_�û���
		String lastdate = dateuser.substring(0, 10);// ʹ������
		log.info("lastdate" + lastdate);
		usernumber = Integer.parseInt(dateuser.substring(10).split("_")[1]);

		String username = dateuser.substring(10).split("_")[0];

		// �õ��ļ��е�ʹ�����ޡ�ϵͳ��ǰʱ����бȽϣ����ʹ�����޴���ϵͳ��ǰʱ�����������
		String[] date = lastdate.split("-");
		String time = date[0];
		String time1 = date[1];
		String time2 = date[2];
		String ortime = time + time1 + time2;
		lasttime = Long.parseLong(ortime);

		Date dt = new Date();// �������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// ������ʾ��ʽ
		String nowTime = df.format(dt);// ��DateFormat��format()������dt�л�ȡ����yyyy/MM/dd
										// HH:mm:ss��ʽ��ʾ
		try {
			now = Long.parseLong(nowTime);// ��ϵͳ��ʱ��ת��Ϊ����
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("", "");
		}
		log.info(now + "lasttime:" + lasttime);

		if (lasttime < now) {
			log.info("ע����Ϣ����ȷ");
			request.getSession().setAttribute("x", 1);
			return bool;
		}
		if (onlinecount > usernumber) {
			log.info("ע����Ϣ����ȷ");
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		if (!contents[0].equals(username)) {
			log.info("ע����Ϣ����ȷ");
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		String address1 = CheckCodeService.testGetSysInfo();
		if (!info.xuyj.util.License.decodeKey(contents[2]).split("_")[0]
				.equals(address1)) {
			log.info("ע����Ϣ����ȷ:" + address1);
			request.getSession().setAttribute("x", 1);
			return bool;
		}

		if (!contents[3].equals(InitServlet.VER)) {
			log.info("ע����Ϣ����ȷ");
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
