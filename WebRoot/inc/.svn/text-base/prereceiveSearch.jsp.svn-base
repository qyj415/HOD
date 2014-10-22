<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function searchPreList()
	{
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var clientName=$("#clientName").val();
		var prAfter=$("#prAfter").val();
		if(0==startTime.length&&0==endTime.length)
		{
			Ext.MessageBox.alert('提示','开始时间和结束时间不能同时为空!');
			return;
		}
		$("#preReceiveList").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{'startTime':startTime,'endTime':endTime,'clientName':clientName,'prAfter':prAfter,'roperator':$("#roperator").val()}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); //重新载入
	}
</script>
</head>
		<div>
				<table class="repCnd">
				<tr class="repCnd">
				<td class="repCndLb" nowrap="nowrap">开始时间:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:-0});}'})" name="startTime" id="startTime"/>
				</span></td>
				<td class="repCndLb" nowrap="nowrap">结束时间:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})" name="endTime" id="endTime"/> </span>
				</td>
				<td class="repCndLb" nowrap="nowrap">用户姓名:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input class="border" type="text" name="clientName" id="clientName"/> </span>
				</td>
				<td class="repCndLb" nowrap="nowrap">是否后付费:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span>
						<select class="border" name="prAfter" id="prAfter">
							<option value="">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</span>
				</td>
				<td class="repCndLb" nowrap="nowrap">操作员:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input class="border" type="text" name="roperator" id="roperator"/> </span>
				</td>
				<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="searchPreList()"/></td>
				</tr>
				</table>
		</div>
  
		<div class="repCndToolBar">
		</div>


