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
    
    <title>收费参数设置</title>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<script type="text/javascript">
		function checkForm()
		{
			var startMonth=$("#startMonth").val();
			var endMonth=$("#endMonth").val();
			if(0==startMonth.length)
			{
				Ext.MessageBox.alert('提示','供暖开始时间不能为空!');
				return false;
			}
			if(0==endMonth.length)
			{
				Ext.MessageBox.alert('提示','供暖结束时间不能为空!');
				return false;
			}
			if(startMonth>endMonth)
			{
				Ext.MessageBox.alert('提示','输入不合法，开始时间不能大于结束时间!');
				return false;
			}
		}
		$(function(){
			<c:if test="${!empty heatingparameter}">
				setCheckedValue('hpType',${heatingparameter.hpType});
			</c:if>
			$('#chargeSetForm input').keyup(trimkeyup);
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
														<B>当前位置</B>：[收费管理]-[
														<font color='red'>收费参数设置</font>]
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
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=4"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000Receive!setParam.do" method="post" id="chargeSetForm">
								<input type="hidden" value="${heatingparameter.hpId }" name="id"/>
								<table cellpadding="0" cellspacing="0" border="0" height="200px" align="center" id="tab">
									<tr>
										<td>供暖开始月份：</td>
										<td>
											<f:formatDate value="${heatingparameter.hpStart}" pattern="yyyy-MM-dd" var="start"/>
											<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startMonth" name="startMonth" value="${start}"/>
											<span>必填</span>
										</td>
									</tr>
									<tr>
										<td>供暖结束月份：</td>
										<td>
											<f:formatDate value="${heatingparameter.hpEnd }" pattern="yyyy-MM-dd" var="end"/>
											<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="endMonth" name="endMonth" value="${end}"/>
											<span>必填</span>
										</td>
									</tr>
									<tr>
										<td>收 费 方 式：</td>
										<td>
											<input type="radio" name="hpType" value="2" id="type1"  checked="checked"/>多退少补
											<input type="radio" name="hpType" value="1" id="type2"/>多退少不补
										</td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="提交" onclick="return checkForm()"/>
											<input type="reset" value="重置"/>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											提示：<br>一.供暖收费完成之前，请不要更改参数设置，以免造成系统错误。
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
