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
    
    <title>添加操作员信息</title>
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
		$(document).ready(function() {
             $('#operLoginName').keyup(trimkeyup);
 		});
	
		function checkForm()
		{
			var operLoginName=$("#operLoginName").val();
			var operLoginPass=$("#operLoginPass").val();
			var operPass=$("#operPass").val();
			if(operLoginName.length<2||operLoginName.length>16)
			{
				Ext.MessageBox.alert('提示','用户名输入不合规范，请重新输入!'); 
				return false;
			}
			if(operLoginPass.length<6||operLoginPass.length>16)
			{
				Ext.MessageBox.alert('提示','登陆密码输入不合规范，请重新输入!');
				return false;
			}
			if(operPass.length<6||operPass.length>16)
			{
				Ext.MessageBox.alert('提示','确认密码输入不合规范，请重新输入!');
				return false;
			}
			if(operLoginPass!=operPass)
			{
				Ext.MessageBox.alert('提示','登陆密码与确认密码输入不一致，请重新输入!');
				return false;
			}
		}
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
														<B>当前位置</B>：[系统管理]-[
														<font color='red'>添加操作员信息</font>]
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
																	<a href="${pageContext.request.contextPath}/show/operatorlist.jsp"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000Operator!doSave.do" method="post">
								<input type="hidden" value="${hod2000Role.rid }">
								<table cellpadding="0" cellspacing="0" border="0" height="200px" align="center" id="tab">
									<tr>
										<td>用户名：</td>
										<td><input id="operLoginName" name="operLoginName" style="width: 155px;"/><span>必填，2-16位字符</span></td>
									</tr>
									<tr>
										<td>登陆密码：</td>
										<td><input type="password" id="operLoginPass" name="operLoginPass" style="width: 155px;"/><span>必填，6-16位字符</span></td>
									</tr>
									<tr>
										<td>确认密码：</td>
										<td><input type="password" id="operPass" style="width: 155px;"/><span>必填，6-16位字符</span></td>
									</tr>
									<tr>
										<td>角色：</td>
										<td>
											<select name="oper_role" class="border">
												<c:forEach items="${roleList}" var="r">
													<option value="${r.rid }">${r.rname }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td>有效性：</td>
										<td><select name="oper_enable">
											<option value="1">有效</option>
											<option value="0">无效</option>
										</select></td>
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
