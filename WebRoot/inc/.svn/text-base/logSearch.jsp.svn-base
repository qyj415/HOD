<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hod.util.Utils"%>
<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function searchHis(page)
	{
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var operName=$("#operName").val();
		 $("#operateLogList").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{'startTime':startTime,'endTime':endTime,'operName':operName}, //发送数据  
	        page:page  
	    }).trigger("reloadGrid"); //重新载入
	}
	
	$(function(){
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
		$("#startTime").val('<%=Utils.getTime2(1,1)%>');
	});
</script>
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
					<td class="repCndLb" nowrap="nowrap">开始时间:</td>
					<td class="repCndEdit" nowrap="nowrap">
						<span>
							<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'})" id="startTime" name="startTime"/> 
						</span>
					</td>
					<td class="repCndLb" nowrap="nowrap">结束时间:</td>
					<td class="repCndEdit" nowrap="nowrap">
						<span>
							<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" id="endTime" name="endTime"/> 
						</span>
					</td>
					<td class="repCndLb" nowrap="nowrap">操作员名称(用户名)：</td>
					<td class="repCndEdit" nowrap="nowrap"><span><input type="text" class="border" name="operName" id="operName"/></span></td>
					<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="searchHis(1)"/></td>
				</tr>
			</table>
		</div>
  
		<div class="repCndToolBar">
			  
		</div>


