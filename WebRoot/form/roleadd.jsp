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
    
    <title>添加角色信息</title>
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
             $('#roleAddForm input').keyup(trimkeyup);
 		});
	
		function checkForm()
		{
			var roleName=$("#roleName").val();
			var rolePermission=$("#rolePermissionRight option"); 
			if(roleName.length<2||roleName.length>16)
			{
				Ext.MessageBox.alert('提示','角色名称输入不合规范，请重新输入!');
				return false;
			}
			if(rolePermission.length==0)
			{
				Ext.MessageBox.alert('提示','角色的权限不能为空!');
				return false;
			}
			$("#rolePermissionRight option").attr("selected", true);  
		}

		//角色权限右移事件
		function moveRight()
		{
			var op = $("#rolePermissionLeft option:selected"); 
			if(0==op.length)
			{
				$("#rolePermissionLeft option").prependTo("#rolePermissionRight");
			}
			else
			{
				$("#rolePermissionLeft option:selected").prependTo("#rolePermissionRight");
			}
		}
		
		//角色权限设置左移事件
		function moveLeft()
		{
			var op = $("#rolePermissionRight option:selected"); 
			if(0==op.length)
			{
				$("#rolePermissionRight option").prependTo("#rolePermissionLeft");
			}
			else
			{
				$("#rolePermissionRight option:selected").prependTo("#rolePermissionLeft");
			}
		}
		
		//双击右移事件
		function dbMoveLeft()
		{
			$("#rolePermissionLeft option:selected").prependTo("#rolePermissionRight");
		}
		
		//双击左移事件
		function dbMoveRight()
		{
			$("#rolePermissionRight option:selected").prependTo("#rolePermissionLeft");
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
														<font color='red'>添加角色信息</font>]
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
																	<a href="${pageContext.request.contextPath}/show/rolelist.jsp"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000Roles!doSave.do" method="post" name="frmoperatorType" id="roleAddForm">
								<table cellpadding="0" cellspacing="0" align="center" class="tab" id="tab">
									<tr height="40">
										<td>角色名称：</td>
										<td colspan="3"><input id="roleName" name="rname"/><span>必填，2-16位字符</span></td>
									</tr>
									<tr height="270">
										<td>角色权限：</td>
										<td>
											<select multiple="multiple" style="width: 180px;height:250px;margin-right: 20px;" id="rolePermissionLeft" ondblclick="dbMoveLeft()">
												<c:forEach items="${permissionList}" var="v">
													<option value="${v.permId }">${v.permName }</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<input type="button" value="-->" onclick="moveRight()" /><br><br>
								            <input type="button" value="<--" onclick="moveLeft()"/>
										</td>
										<td>
											<select multiple="multiple" style="width: 180px;height:250px;margin-left: 20px;" id="rolePermissionRight" name="rolePermissionRight" ondblclick="dbMoveRight()"></select><span>左边为待选权限，右边为已选权限</span>
										</td>
									</tr>
									<tr>
										<td align="center" colspan="4">
											<input type="submit" value="提交" onclick="return checkForm()"/>
											<input type="button" value="重置" onclick="javaScript:window.location.href='${pageContext.request.contextPath}/hod2000Roles!toAdd.do';"/>
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
