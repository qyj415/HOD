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
    
    <title>修改热量表信息</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript">
		function checkForm()
		{
			var meterName=$("#meter_name").val();
			var changeValue=$("#changeValue").val();
			if(meterName.length!=8)
			{
				Ext.MessageBox.alert('提示','表号输入不合规范，请重新输入!');
				return false;
			}
			if(changeValue.length==0)
			{
				Ext.MessageBox.alert('提示','旧表累计热量不能为空，请重新输入!');
				return false;
			}
			if(changeValue.length>16)
			{
				Ext.MessageBox.alert('提示','旧表累计热量输入不规范，请重新输入!');
				return false;
			}
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
		}
		
		$(document).ready(function() {
             $('#meterEditForm input').keyup(trimkeyup);
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
														<B>当前位置</B>：[表计管理]-[
														<font color='red'>更换热量表信息</font>]
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
																	<a href="javascript:window.location.href='${pageContext.request.contextPath}/hod2000Meter!toSelect.do';"><div class="returnbtn"></div></a>
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
								<form action="${pageContext.request.contextPath}/hod2000Meter!doUpdate.do" method="post" id="meterEditForm">
									<input type="hidden" name="meterId" value="${hod2000Meter.meterId }">
									<table cellpadding="0" cellspacing="0" border="0" height="300px" align="center" id="tab">
										<tr>
											<td width="80">旧表号：</td>
											<td><span style="border-bottom: 1px solid gray;width: 150px;">${hod2000Meter.meterName }</span></td>
										</tr>
										<tr>
											<td width="80">新表号：</td>
											<td><input type="text" id="meter_name" name="meter_name" maxlength="8" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/><span>必填，8位数字</span></td>
										</tr>
										<tr>
										<td>旧表累计热量：</td>
										<td>
											<input type="text" id="changeValue" name="changeValue" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/>
											<span>KWh</span>
										</td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="提交" onclick="return checkForm()"/>
											<input type="button" value="取消" onclick="javaScript:window.location.href='${pageContext.request.contextPath}/hod2000Meter!toSelect.do'"/>
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
</html>
