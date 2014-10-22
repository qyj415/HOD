<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	//查询
	function searchFree(page)
	{
		var meterId=$("#meterId2").val();
		var startTime=$("#startTime2").val();
		var endTime=$("#endTime2").val();
		var meterPositionName=$("#meterPositionName2").val();
        $("#grid_id2").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{'meterName':meterId,'startTime':startTime,'endTime':endTime,'meterPositionName':meterPositionName}, //发送数据  
	        page:page  
	    }).trigger("reloadGrid"); //重新载入
	}
</script>
</head>
	<div>
		<table class="repCnd">
			<tr class="repCnd">
				<td class="repCndLb" nowrap="nowrap">地理位置:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input type="text" id="meterPositionName2" name="meterPositionName" style="width: 150px;" class="border">
				</span></td>
				<td class="repCndLb" nowrap="nowrap">表号:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input type="text" id="meterId2" name="meterName" maxlength="8" style="width: 150px;" class="border" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">
				</span></td>
				<td class="repCndLb" nowrap="nowrap">开始时间:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:-0});}'})" name="startTime" id="startTime2" style="width: 150px;"/>
				</span></td>
				<td class="repCndLb" nowrap="nowrap">结束时间:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})" name="endTime" id="endTime2" style="width: 150px;"/> </span>
				</td>
				<td class="repCndEdit" nowrap="nowrap"></td>
				<td class="repCndEdit" nowrap="nowrap"></td>
				<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="searchFree(1)"/></td>
			</tr>
		</table>
	</div>
	<div class="repCndToolBar" style="text-align: right;"></div>


