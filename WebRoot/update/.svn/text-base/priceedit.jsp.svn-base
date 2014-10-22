<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改定额价格方案</title>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<script type="text/javascript">
		function checkForm()
		{
			var priceName=$("#priceName").val();
			var pdBasePrice=$("#pdBasePrice").val();
			var pdPowerPrice=$("#pdPowerPrice").val();
			var startTime=$("#startTime").val();
			if(priceName.length<2||priceName.length>50)
			{
				Ext.MessageBox.alert('提示','方案名称输入不合规范，请重新输入!');
				return false;
			}
			if(0==startTime.length)
			{
				Ext.MessageBox.alert('提示','启用时间输入不合规范，请重新输入!');
				return false;
			}
			if(0==pdBasePrice.length)
			{
				Ext.MessageBox.alert('提示','基础费单价输入不合规范，请重新输入!');
				return false;
			}
			if(0==pdPowerPrice.length)
			{
				Ext.MessageBox.alert('提示','用热单价输入不合规范，请重新输入!');
				return false;
			}
		}
		
		$(document).ready(function() {
             $('#priceEditForm input').keyup(trimkeyup);
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
														<B>当前位置</B>：[价格方案]-[
														<font color='red'>修改定额价格方案</font>]
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
																	<a href="${pageContext.request.contextPath}/hod2000Price!doSelect.do"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000Price!doUpdate.do" method="post" id="priceEditForm">
							<input type="hidden" value="1" name="method">
							<input type="hidden" value="${hod2000Price.pid }" name="hod2000Price.pid">
							<input type="hidden" value="${hod2000PriceDetail.pdId}" name="hod2000PriceDetail.pdId">
								<table cellpadding="0" cellspacing="0" border="0" height="300px" align="center" id="tab">
									<tr>
										<td>方案名称：</td>
										<td><input id="priceName" name="pname" value="${hod2000Price.pname }"/><span>必填，2-50位字符</span></td>
									</tr>
									<tr>
										<td>启用日期：</td>
										<td><f:formatDate value="${hod2000Price.pstartTime }" pattern="yyyy-MM-dd" var="startTime"/><input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" readonly="readonly" id="startTime" name="pstartTime" value="${startTime }"/><span>必填</span></td>
									</tr>
									<tr>
										<td>基础费单价(元/月/平方米)：</td>
										<td><input type="text" id="pdBasePrice" value="${hod2000PriceDetail.pdBasePrice }" name="hod2000PriceDetail.pdBasePrice" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/><span>必填，只能输入数值型字符</span></td>
									</tr>
									<tr>
										<td>用热单价(元/KWh)：</td>
										<td><input type="text" id="pdPowerPrice" value="${hod2000PriceDetail.pdPowerPrice }" name="hod2000PriceDetail.pdPowerPrice" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/><span>必填，只能输入数值型字符</span></td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="提交" onclick="return checkForm()"/>
											<input type="reset" value="重置"/>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											提示：<br>
											1.定额价格=基础费+能量费；<br>
											2.基础费=面积数×基础费单价×时间（月）；<br>
											3.能量费=耗热量×用热单价。
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
