<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
</head>
		<div class="tabContainer" width="100%">
		  <table border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td width="25"><img src="${pageContext.request.contextPath}/common/tab/search/firstLeftSel.gif" width="25" height="24" /></td>
			  <td width="100" class="tabTitleSel" nowrap="nowrap"  >查询条件</td>
			  <td width="50"><img src="${pageContext.request.contextPath}/common/tab/search/lastRightSel.gif" width="50" height="24" /></td>
			  <td class="tabBg"></td>
			</tr>
		  </table>
		</div>
		
		<div>
				<table class="repCnd">
				<tr class="repCnd">
					<td class="repCndLb" nowrap="nowrap">用户姓名:</td>
					<td class="repCndEdit" nowrap="nowrap"><span><input class="border" type="text" name="clientName" id="clientName"/> </span></td>
					<td class="repCndLb" nowrap="nowrap">证件号码：</td>
					<td class="repCndEdit" nowrap="nowrap"><span><input class="border" type="text" name="clientIdentity" id="clientIdentity"/></span></td>
					<td class="repCndLb" nowrap="nowrap">用户状态:</td>
					<td class="repCndEdit" nowrap="nowrap">
						<span>
							<select name="clientEnable" id="clientEnable" class="border">
								<option value="">请选择</option>
								<option value="1">正常</option>
								<option value="0">已销户</option>
							</select>
						</span>
					</td>
					<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="searchClientList()"/></td>	
				</tr>
				
				</table>
		</div>
  
		<div class="repCndToolBar">
		</div>


