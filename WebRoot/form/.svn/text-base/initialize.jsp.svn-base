<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>初始化参数</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/loadSelect.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/IP.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<style type="text/css">
		.ipInput{
	    	border:1px solid #ccc;
	    	font-size:12px;
		}
		.ipInput input{
		    border:0px solid #ccc;
		    font-size:12px;
		    height:20px;
		    width:35px;
		    background:transparent;
		    text-align:center;
		}
	</style>
	<script type="text/javascript">
		function checkForm()
		{
			var stationIP=ip2.getValue();//主站IP地址
			var port=$("#port").val();//端口号地址
			var apn=$("#apn").val();//APN
			var delayTime=$("#delayTime").val();//数传机延时时间
			var TransmissionDelayTime=$("#TransmissionDelayTime").val();//传输延时时间
			var ResponseTimeout=$("#ResponseTimeout").val();//响应超时时间
			var resendTimes=$("#resendTimes").val();//重发次数
			var period=$("#period").val();//心跳周期
			if(stationIP.length==4)
			{
				Ext.MessageBox.alert('提示','请输入IP地址!');
				return false;
			}
			$("#stationIP").val(stationIP);
			if(port<1||port>65535)
			{
				Ext.MessageBox.alert('提示','端口输入不合规范(1~65535)，请重新输入!');
				return false;
			}
			if(apn.length<1||apn.length>16)
			{
				Ext.MessageBox.alert('提示','接入点名称输入不合规范，请重新输入!');
				return false;
			}
			if(delayTime.length<1||delayTime.length>8)
			{
				Ext.MessageBox.alert('提示','数传机延时时间输入不合规范，请重新输入!');
				return false;
			}
			if(TransmissionDelayTime<0||TransmissionDelayTime>60)
			{
				Ext.MessageBox.alert('提示','传输延时时间输入不合规范，请重新输入!');
				return false;
			}
			if(ResponseTimeout<1||ResponseTimeout>4095)
			{
				Ext.MessageBox.alert('提示','响应超时时间输入不合规范，请重新输入!');
				return false;
			}
			if(resendTimes==""||resendTimes<0||resendTimes>3)
			{
				Ext.MessageBox.alert('提示','重发次数输入不合规范，请重新输入!');
				return false;
			}
			if(period<1||period>255)
			{
				Ext.MessageBox.alert('提示','心跳周期输入不合规范(1~255)，请重新输入!');
				return false;
			}
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
		}
		//alert('${stationIP}');
		$(function(){
			$("#communicationFlag").val(${deviceInitParameter.pcol5});
			$('#initForm input').keyup(trimkeyup);
		});
	</script>
  </head>
  
  <body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30" class="tab_05">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30" class="tab_03"></td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="5%" class="tb"></td>
													<td width="95%" class="STYLE2">
														<B>当前位置</B>：[集中器管理]-[
														<font color='red'>初始化参数</font>]
													</td>
												</tr>
											</table>
										</td>
										<td width="54%">
											<table border="0" align="right" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="60"></td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="javascript:window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=6';"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp" target="mainFrame"><div class="homebtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="16" class="tab_07"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
							<!-- 可以写form -->
							<form action="${pageContext.request.contextPath}/hod2000Concentrator!setParams.do" method="post" id="initForm">
								<input type="hidden" value="${deviceInitParameter.pid}" name="pid"/>
								<input type="hidden" value="127.0.0.1:8080" name="deviceInitParameter.pcol8"/>
								<input type="hidden" id="stationIP" name="stationIP"/>
								<table cellpadding="0" cellspacing="0" border="0" width="80%" align="center" id="tab">
									<!-- 主站配置信息开始 -->
									<tr>
										<td colspan="2"><span style="font-weight: bold;font-size: 20px;color: black;">服务器配置信息：</span></td>
									</tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0" border="0" height="130px" width="100%">
												<tr>
													<td width="120px" style="text-align:right;">IP地址：</td>
													<td style="width:30%"><span id="span2"></span><span class="concent"> 必填</span></td>
													<td style="text-align:right;">端口：</td>
													<td><input type="text" id="port" name="port" value="${port }" maxlength="5"/><span> 必填，范围在1~65535之间</span></td>
												</tr>
												<tr>
													<td style="text-align:right;">接入点名称：</td>
													<td><input type="text" id="apn" name="deviceInitParameter.pcol9" value="${deviceInitParameter.pcol9 }" maxlength="16"/><span> 必填，1~16个字符</span></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr><td colspan="2" ><hr color="grey"></td></tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<!-- 主站配置信息开始 -->
									
									<!-- 终端配置开始 -->
									<tr>
										<td colspan="2"><span style="font-weight: bold;font-size: 20px;color: black;">集中器配置信息：</span></td>
									</tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0" border="0" height="180px" width="100%">
												<tr style="display: none;">
													<td style="text-align:right;width:150px">数传机延时时间(20ms)：</td>
													<td><input type="text" id="delayTime" name="deviceInitParameter.pcol1" value="2" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')" maxlength="8"/><span> 必填，1~8个数字</span></td>
													<td style="text-align:right;">传输延时时间(min)：</td>
													<td><input type="text" id="TransmissionDelayTime" name="deviceInitParameter.pcol2" value="3" maxlength="2"/><span> 必填，0~60min</span></td>
												</tr>
												<tr>
													<td style="text-align:right;">响应超时时间(s)：</td>
													<td><input type="text" id="ResponseTimeout" name="deviceInitParameter.pcol3" value="${deviceInitParameter.pcol3 }" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/><span> 必填，1~4095秒</span></td>
													<td style="text-align:right;">重发次数：</td>
													<td><input type="text" id="resendTimes" name="deviceInitParameter.pcol4" value="${deviceInitParameter.pcol4 }" maxlength="1" onkeyup="this.value=this.value.replace(/[^0-3]/g,'')"/><span> 必填，0~3次</span></td>
												</tr>
												<tr>
													<td style="text-align:right;">是否需要服务器确认：</td>
													<td>
														<select name="deviceInitParameter.pcol5" id="communicationFlag" style="width: 154px;">
															<option value="0">不需要</option>
															<option value="1" selected="selected">需要</option>
														</select>
														<span> 必选</span>
													</td>
													<td style="text-align:right;">心跳周期(min)：</td>
													<td><input type="text" id="period" name="deviceInitParameter.pcol6" value="${deviceInitParameter.pcol6 }" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')" maxlength="3"/><span> 必填，1~255min</span></td>
												</tr>
												<tr style="text-align: center;">
													<td colspan="4">
														<input type="submit" class="savebtn" value="" onclick="return checkForm()">
														<input type="reset" class="resetbtn" value="">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</form>
							</td>
							<td width="8" class="tab_15">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="35" class="tab_19">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="35" class="tab_18">
							</td>
							<td>&nbsp;</td>
							<td width="16" class="tab_20">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
  </body>
  <script>
	var ip2=new IpV4Box("ip2" , "span2");
	ip2.setValue('${stationIP}');
</script>
</html>
