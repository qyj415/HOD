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
    
    <title>数据备份与恢复</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" language="javaScript">
	
		function checkFile()
		{
			var completeFilesName=$("#completeFilesName").val();
			var diffFilesName=$("#diffFilesName").val();
			var logFilesName=$("#logFilesName").val();
			var lastName=completeFilesName.substring(completeFilesName.lastIndexOf(".")+1);
			var lastName1=diffFilesName.substring(diffFilesName.lastIndexOf(".")+1);
			var lastName2=logFilesName.substring(logFilesName.lastIndexOf(".")+1);
			if(lastName1!="diff")
			{
				Ext.MessageBox.alert('提示','差异备份文件不能为空!');
				return false;
			}
			if(lastName2!="trn")
			{
				Ext.MessageBox.alert('提示','差异备份日志文件不能为空!');
				return false;
			}
			if(lastName!="bak")
			{
				Ext.MessageBox.alert('提示','完整备份文件不能为空!');
				return false;
			}
			Ext.MessageBox.wait('请不要终止(如果半途终止，数据库将无法使用)，正在请求......', '请稍后');
		}
		
		function checkForm()
		{
			var path=$("#path").val();
			if(0==path.length)
			{
				Ext.MessageBox.alert('提示','备份路径不能为空!');
				return false;
			}
		}
		
		function browseFolder(path) {
			 try {
				 var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
				 var Shell = new ActiveXObject("Shell.Application");
				 var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
				 //var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
				 if (Folder != null) {
					 Folder = Folder.items(); // 返回 FolderItems 对象
					 Folder = Folder.item(); // 返回 Folderitem 对象
					 Folder = Folder.Path; // 返回路径
					 if (Folder.charAt(Folder.length - 1) != "") {
					 	Folder = Folder + "";
			 		 }
			 		document.getElementById(path).value = Folder;
			 		return Folder;
			 	}
			 }
			 catch (e) {
			 	Ext.MessageBox.alert('提示',e.message);
			 }
		}
		
		$(function(){
			$("#periods").val(${backupSchedule.completePeriod});
			$("#backTime").val(${backupSchedule.diffTime });
		});
		
		function searchFile()
		{
			var backupTime=$("#backupTime").val();
			if(backupTime.length==0)
			{
				Ext.MessageBox.alert('提示','请选择备份时间!');
				return;
			}
			Ext.Ajax.request({
				url : "${pageContext.request.contextPath}/databaseRestor!getPath.do",
				params:{  
					backupTime:backupTime
	        	},  
				async : false,
				method : 'POST',
				timeout : 180000,
				success : function(res) {
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						var data=result.data;
						$("#diffFilesName").val(data.diffFilePath);
						$("#logFilesName").val(data.trnFilePath);
						$("#completeFilesName").val(data.bakFilePath);
					}
					else
					{
						$("#diffFilesName").val('');
						$("#logFilesName").val('');
						$("#completeFilesName").val('');
						Ext.MessageBox.alert('提示',result.msg);
					}
				},
				failure : function(res) {
					Ext.MessageBox.hide();
					Ext.MessageBox.alert('提示','请求超时!');
				}
		    });
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
														<font color='red'>数据备份与恢复</font>]
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
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=1"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp"><div class="homebtn"></div></a>
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
								<table cellpadding="0" cellspacing="0" border="0" align="center" id="tab">
									<!-- 主站配置信息开始 -->
									<tr>
										<td colspan="2"><span style="font-weight: bold;font-size: 20px;color: black;">数据库备份计划：</span></td>
									</tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr>
										<td colspan="2">
											<form action="${pageContext.request.contextPath}/database!allToBak.do" method="post">
												<input type="hidden" name="id" value="${backupSchedule.id }"/>
												<table cellpadding="0" cellspacing="0" border="0" height="130px" width="100%">
													<tr>
														<td width="170">选择备份路径：</td>
														<td><input type="text" style="width: 300px;" id="path" value="${backupSchedule.backupPath }" name="path" readonly="readonly"/></td>
														<!-- <td><input type="button" value="浏览" onclick="browseFolder('path')"/></td> -->
													</tr>
													<tr>
														<td>完整备份周期：</td>
														<td>
															<select style="width: 300px;" name="periods" id="periods">
																<option value="0">每周</option>
																<option value="1">每月</option>
															</select>
														</td>
													</tr>
													<tr>
														<td>差异备份时间间隔(每天一次)：</td>
														<td>
															<!-- <input type="text" name="backTime" value="${backupSchedule.diffTime }" id="backTime" style="width: 300px;"/> -->
															<select name="backTime" style="width: 300px;" id="backTime">
																<option value="0">00:00</option>
																<option value="1">01:00</option>
																<option value="2">02:00</option>
																<option value="3">03:00</option>
																<option value="4">04:00</option>
																<option value="5">05:00</option>
																<option value="6">06:00</option>
																<option value="7">07:00</option>
																<option value="8">08:00</option>
																<option value="9">09:00</option>
																<option value="10">10:00</option>
																<option value="11">11:00</option>
																<option value="12">12:00</option>
																<option value="13">13:00</option>
																<option value="14">14:00</option>
																<option value="15">15:00</option>
																<option value="16">16:00</option>
																<option value="17">17:00</option>
																<option value="18">18:00</option>
																<option value="19">19:00</option>
																<option value="20">20:00</option>
																<option value="21">21:00</option>
																<option value="22">22:00</option>
																<option value="23">23:00</option>
															</select>
														</td>
													</tr>
													<tr>
														<td colspan="2" style="text-align: center;"><input type="submit" value="保存计划" onclick="return checkForm()"/></td>
													</tr>
												</table>
											</form>
										</td>
									</tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr><td colspan="2" ><hr color="grey"></td></tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr>
										<td colspan="2"><span style="font-weight: bold;font-size: 20px;color: black;">数据库恢复：</span></td>
									</tr>
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr>
										<td colspan="2">
											<form action="${pageContext.request.contextPath}/database!allToRestore.do" method="post">
												<table cellpadding="0" cellspacing="0" border="0" height="130px" width="100%">
													<tr>
														<td width="170">请选择数据库备份时间：</td>
														<td>
															<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})" id="backupTime"/>
															<input type="button" value="" class="btn" onclick="searchFile()">
														</td>
													</tr>
													<tr>
														<td width="170">差异备份文件：</td>
														<td><input style="width: 300px;" type="text" id="diffFilesName" name="diffFilesName" readonly="readonly"/><span>必填，服务器上面的路径</span></td>
													</tr>
													<tr>
														<td width="170">差异备份日志文件：</td>
														<td><input style="width: 300px;" type="text" id="logFilesName" name="logFilesName" readonly="readonly"/><span>必填，服务器上面的路径</span></td>
													</tr>
													<tr>
														<td width="170">完整备份文件：</td>
														<td><input style="width: 300px;" type="text" id="completeFilesName" name="completeFilesName" readonly="readonly"/><span>必填，服务器上面的路径</span></td>
													</tr>
													<tr>
														<td colspan="2" style="text-align: center;"><input type="submit" value="数据恢复" onclick="return checkFile()"/></td>
													</tr>
												</table>
											</form>
										</td>
									</tr>
								</table>
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
