<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hod.util.Utils"%>
<%@page import="com.hod.util.NetworkTimeUtil;"%>
<html>
<head>
<script language="javascript" src="${pageContext.request.contextPath}/common/tab/search/eventUtil.js" charset="UTF-8"></script>
<script language="javascript" src="${pageContext.request.contextPath}/common/tab/search/pubUtil.js" charset="UTF-8"></script>
<script language="javascript" src="${pageContext.request.contextPath}/common/tab/search/report.js" charset="UTF-8"></script>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>

<script>  
	function checkTime()
	{
		var utils = document.getElementById("utils").value;
		var startTime = document.getElementById("startTime").value; 
        var endTime = document.getElementById("endTime").value;  
 		
    	if(startTime == "" || endTime == ""){
    		Ext.MessageBox.alert('提示',"请选择开始时间和结束时间!");
    	}
    	
    	var data1 = Date.parse(startTime.replace(/-/g,   "/"));  
    	var data2 = Date.parse(endTime.replace(/-/g,   "/"));    	
    	var datadiff = data2-data1;  

		if(utils == "0")
		{
			var time = 24*60*60*1000; 
			if(startTime.length>0 && endTime.length>0){
				if(datadiff<0||datadiff>time)
				{
					Ext.MessageBox.alert('提示',"开始时间应小于结束时间且间隔不能超过24小时，请检查!");  
            		return false;
				}else{
					showList();
				}
			}
		}

		if(utils == "1")
		{
			var time = 31*24*60*60*1000; 
			if(startTime.length>0 && endTime.length>0){
				if(datadiff<0||datadiff>time)
				{
					 Ext.MessageBox.alert('提示',"开始时间应小于结束时间且间隔不能超过30天，请检查!"); 
            		return false;
				}else{
					showList();
				}
			}
		}

		if(utils == "2")
		{
			var time = 366*24*60*60*1000; 
			if(startTime.length>0 && endTime.length>0){
				if(datadiff<0||datadiff>time)
				{
					 Ext.MessageBox.alert('提示',"开始时间应小于结束时间且间隔不能超12个月，请检查!"); 
            		return false;
				}else{
					showList();
				}
			}
		}
		
		if(utils == "3")
		{
			var time = 5*366*24*60*60*1000; 
			if(startTime.length>0 && endTime.length>0){
				if(datadiff<0||datadiff>time)
				{
					 Ext.MessageBox.alert('提示',"开始时间应小于结束时间且间隔不能超5年，请检查!"); 
            		return false;
				}else{
					showList();
				}
			}
		}

	}
    function changeUtils()
	{
		var utils = document.getElementById("utils").value;
		if(utils == "0")
		{
			setSpan('the0','the1','the2','the3');
			$("#hours").val(1);
			$("#startTime").val('<%=Utils.getTime4(1,1)%>');
		}
		if(utils == "1")
		{
			setSpan('the1','the0','the2','the3');
			$("#days").val(1);
			$("#startTime").val('<%=Utils.getTime2(0,1)%>');
		}
		if(utils == "2")
		{
			setSpan('the2','the0','the1','the3');
			$("#months").val(1);
			$("#startTime").val('<%=Utils.getTime(1,1)%>');
		}
		if(utils == "3")
		{
			setSpan('the3','the0','the1','the2');
			$("#years").val(1);
			$("#startTime").val('<%=Utils.getTime3(1,1)%>');
		}
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
	}
	function setSpan(id1,id2,id3,id4)
	{
		document.getElementById(id1).style.display='block';
		document.getElementById(id2).style.display='none';
		document.getElementById(id3).style.display='none';
		document.getElementById(id4).style.display='none';
	}
	$(function(){
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
		$("#startTime").val('<%=Utils.getTime(1,1)%>');
	});
	function selects()
	{
		var months=$("#months").val();
		if(months==1)
			$("#startTime").val('<%=Utils.getTime(1,1)%>');
		if(months==2)
			$("#startTime").val('<%=Utils.getTime(3,1)%>');
		if(months==3)
			$("#startTime").val('<%=Utils.getTime(6,1)%>');
		if(months==4)
			$("#startTime").val('<%=Utils.getTime(12,1)%>');
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
	}
	
	function selects1()
	{
		var months=$("#hours").val();
		if(months==1)
			$("#startTime").val('<%=Utils.getTime4(1,1)%>');
		if(months==2)
			$("#startTime").val('<%=Utils.getTime4(3,1)%>');
		if(months==3)
			$("#startTime").val('<%=Utils.getTime4(12,1)%>');
		if(months==4)
			$("#startTime").val('<%=Utils.getTime4(24,1)%>');
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
	}
	
	function selects2()
	{
		var months=$("#days").val();
		if(months==1)
			$("#startTime").val('<%=Utils.getTime2(0,1)%>');
		if(months==2)
			$("#startTime").val('<%=Utils.getTime2(7,1)%>');
		if(months==3)
			$("#startTime").val('<%=Utils.getTime2(15,1)%>');
		if(months==4)
			$("#startTime").val('<%=Utils.getTime(1,1)%>');
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
	}
	
	function selects3()
	{
		var months=$("#years").val();
		if(months==1)
			$("#startTime").val('<%=Utils.getTime3(1,1)%>');
		if(months==2)
			$("#startTime").val('<%=Utils.getTime3(2,1)%>');
		if(months==3)
			$("#startTime").val('<%=Utils.getTime3(3,1)%>');
		if(months==4)
			$("#startTime").val('<%=Utils.getTime3(5,1)%>');
		$("#endTime").val('<%=Utils.dateToStrLong(new Date())%>');
	}
</script>

</head>
		<div>
				<table class="repCnd">
				<tr class="repCndLb">
				<td class="repCndLb" nowrap="nowrap">时间单位:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<select style="width: 150px;" name="utils" id="utils" onchange="changeUtils()">
						<option value="0">时</option>
						<option value="1">日</option>
						<option value="2" selected="selected">月</option>
						<option value="3">年</option>
					</select>
				</span></td>
				<td class="repCndLb" nowrap="nowrap">常用时间选择:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span id="the0" style="display: none;">
						<select style="width: 150px;" name="hours" id="hours" onchange="selects1()">
							<option value="1" selected="selected">最近一小时</option>
							<option value="2">最近三小时</option>
							<option value="3">最近半天</option>
							<option value="4">最近一天</option>
						</select>
					</span>
					<span id="the1" style="display: none;">
						<select style="width: 150px;" name="days" id="days" onchange="selects2()">
							<option value="1" selected="selected">今天</option>
							<option value="2">最近一周</option>
							<option value="3">最近半个月</option>
							<option value="4">最近一个月</option>
						</select>
					</span>
					<span id="the2">
						<select style="width: 150px;" name="months" id="months" onchange="selects()">
							<option value="1" selected="selected">最近一个月</option>
							<option value="2">最近三个月</option>
							<option value="3">最近半年</option>
							<option value="4">最近一年</option>
						</select>
					</span>
					<span id="the3" style="display: none;">
						<select style="width: 150px;" name="years" id="years" onchange="selects3()">
							<option value="1" selected="selected">最近一年</option>
							<option value="2">最近两年</option>
							<option value="3">最近三年</option>
							<option value="4">最近五年</option>
						</select>
					</span>
				</td>
				<td class="repCndLb" nowrap="nowrap">开始时间:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:-0});}'})" id="startTime"/>
				</span></td>
				<td class="repCndLb" nowrap="nowrap">结束时间:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})" id="endTime"/> </span>
				</td>
				<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="return checkTime()"/></td>
				</tr>
				</table>
		</div>
  
		<div class="repCndToolBar">
		</div>


