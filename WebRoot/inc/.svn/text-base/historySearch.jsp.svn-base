<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function searchHis(page)
	{
		var meterId=$("#meterId").val();
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var meterPositionName=$("#meterPositionName").val();
        $("#grid_id").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{'meterName':meterId,'startTime':startTime,'endTime':endTime,'meterPositionName':meterPositionName}, //发送数据  
	        page:page  
	    }).trigger("reloadGrid"); //重新载入
	}
	$(function(){
		document.getElementById('meterPositionName').focus();
	});
	
	//得到去年的今天的日期
	function getDay()
	{
		var date=new Date();
		var strYear=date.getFullYear()-1;
		var strMonth = date.getMonth()+1;  
		var strDay = date.getDate();    
		if(strMonth<10)     
	    {     
	        strMonth="0"+strMonth;     
	    }   
	    if(strDay<10)     
	    {     
	        strDay="0"+strDay;     
	    }   
	    var datastr = strYear+"-"+strMonth+"-"+strDay;
	    return datastr;
	}
	
	//历史数据的删除
	function del()
	{
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var date=getDay();//去年的今天
		if(startTime.length==0||endTime.length==0)
		{
			Ext.MessageBox.alert('提示','请选择时间段进行删除操作!');
			return;
		}	
		if(startTime.length>0)
		{
			var date1=startTime.substring(0,10);
			if(date1>=date)
			{
				Ext.MessageBox.alert('提示','不能删除一年内的历史数据!');
				return;
			}
		}
		if(endTime.length>0)
		{
			var date1=endTime.substring(0,10);
			if(date1>=date)
			{
				Ext.MessageBox.alert('提示','不能删除一年内的历史数据!');
				return;
			}
		}
		Ext.MessageBox.confirm("请确认","你确定删除该时间段的历史数据吗?",function(button,text){ 
	        if(button=="yes")
	        {
	        	Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodMeterInfoHistory!doDel.do",
					params:{  
		            	startTime:startTime,
		            	endTime:endTime
		        	},  
					async : false,
					method : 'POST',
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示',result.msg);
						searchHis(1);
					},
					failure : function(res) {
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示','请求超时!');
					}
			    });
	        }
        }); 
	}
</script>
</head>
	<div>
		<table class="repCnd">
			<tr class="repCnd">
				<td class="repCndLb" nowrap="nowrap">地理位置:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input type="text" id="meterPositionName" name="meterPositionName" style="width: 150px;" class="border">
				</span></td>
				<td class="repCndLb" nowrap="nowrap">表号:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input type="text" id="meterId" name="meterName" class="border" style="width: 150px;" maxlength="8" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">
				</span></td>
				<td class="repCndLb" nowrap="nowrap">开始时间:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:-0});}'})" name="startTime" id="startTime" style="width: 150px;"/>
				</span></td>
				<td class="repCndLb" nowrap="nowrap">结束时间:</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})" name="endTime" id="endTime" style="width: 150px;"/> </span>
				</td>
				<c:if test="${user.hod2000Role.rid==1}">
					<td class="repCndEdit"><a href="#" onclick="searchHis(1)"><div class="btn"></div></a></td>
					<td class="repCndEditRight"><a href="#" onclick="del()"><div class="del-btn"></div></a></td>
				</c:if>
				<c:if test="${user.hod2000Role.rid!=1}">
					<td class="repCndEditRight"><a href="#" onclick="searchHis(1)"><div class="btn"></div></a></td>
				</c:if>
			</tr>
		</table>
	</div>
	<div class="repCndToolBar"></div>


