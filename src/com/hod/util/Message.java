package com.hod.util;
/**
 * 提示信息类
 * @author xyj
 *
 */
public class Message {

	private String msg1="";
	private String error1="";
	private boolean jump = true;
	private String[] link = new String[3];

	public Message() {

	}

	public void setLink(int i, String str) {
		link[i] = str;
	}

	public static Message getInstance() {
		return   new Message();
	}

	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String[] getLink() {
		return link;
	}

	public void setLink(String[] link) {
		this.link = link;
	}

	public String getError1() {
		return error1;
	}

	public void setError1(String error1) {
		this.error1 = error1;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

}
