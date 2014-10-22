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
				<td class="repCndLb" nowrap="nowrap">集中器编号:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
				<input class="border" type="text" name="conNum" id="conNum" maxlength="8" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/> 
				</span></td>
				<td class="repCndLb" nowrap="nowrap">在线状态:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span>
						<select class="border" name="conIsonline" id="conIsonline">
							<option value="">请选择</option>
							<option value="1">在线</option>
							<option value="2">离线</option>
						</select> 
					</span>
				</td>
				<td class="repCndLb" nowrap="nowrap">内存状态:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span>
						<select class="border" name="conFlashStatus" id="conFlashStatus">
							<option value="">请选择</option>
							<option value="0">正常</option>
							<option value="1">错误</option>
						</select> 
					</span>
				</td>
				<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="showList()"/></td>
				</tr>
				</table>
		</div>
  
		<div class="repCndToolBar"></div>


