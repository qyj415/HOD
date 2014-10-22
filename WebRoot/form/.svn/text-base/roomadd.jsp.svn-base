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
    
    <title>添加房间信息</title>
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
	<script type="text/javascript">
		function checkForm()
		{
			var roomName=$("#roomName").val();
			var roomRemark=$("#roomRemark").val();
			var roomArea=$("#roomArea").val();
			var roomHouseType=$("#roomHouseType").val();
			if(roomName.length<2||roomName.length>32)
			{
				Ext.MessageBox.alert('提示','房间名称输入不合规范，请重新输入!');
				return false;
			}
			if(roomArea.length<1||roomArea.length>20)
			{
				Ext.MessageBox.alert('提示','面积数输入不合规范，请重新输入!');
				return false;
			}
			if(roomHouseType.length<2||roomHouseType.length>32)
			{
				Ext.MessageBox.alert('提示','户型输入不合规范，请重新输入!');
				return false;
			}
			if(roomRemark.length>128)
			{
				Ext.MessageBox.alert('提示','备注输入不合规范，请重新输入!');
				return false;
			}
		}
		
		$(document).ready(function() {
             $('#roomAddForm input').keyup(trimkeyup);
             $('#roomAddForm textarea').keyup(trimkeyup);
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
														<B>当前位置</B>：[区域管理]-[
														<font color='red'>添加房间信息</font>]
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
																	<a href="${pageContext.request.contextPath}/hod2000Room!doSelect.do?unitId=${param.unitId }"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000Room!doSave.do" method="post" id="roomAddForm">
								<input type="hidden" name="unitId" id="unitId" value="${param.unitId }">
								<table cellpadding="0" cellspacing="0" border="0" height="350px" width="50%" align="center" id="tab">
									<tr>
										<td>房间名称：</td>
										<td><input type="text" id="roomName" name="hod2000Room.roomName"/><span>必填，2-32位字符</span></td>
									</tr>
									<tr>
										<td>面积数(㎡)：</td>
										<td><input type="text" id="roomArea" name="hod2000Room.roomSize" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/><span>必填，1-20位字符</span></td>
									</tr>
									<tr>
										<td>户型：</td>
										<td>
											<input name="hod2000Room.roomHouseType" id="roomHouseType"><span>必填，2-32个字符</span>
										</td>
									</tr>
									<tr>
										<td>备注信息：</td>
										<td><textarea cols="30" rows="6" id="roomRemark" name="hod2000Room.roomRemark"></textarea><span>最长不超过128个字符</span></td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="提交" onclick="return checkForm()"/>
											<input type="reset" value="重置"/>
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
