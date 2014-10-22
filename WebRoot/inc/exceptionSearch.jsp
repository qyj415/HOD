<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<script language="javascript" src="${pageContext.request.contextPath}/common/tab/search/eventUtil.js" charset="UTF-8"></script>
<script language="javascript" src="${pageContext.request.contextPath}/common/tab/search/pubUtil.js" charset="UTF-8"></script>
<script language="javascript" src="${pageContext.request.contextPath}/common/tab/search/report.js" charset="UTF-8"></script>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/GMapInfor.js"> </script>

<script type="text/javascript">  
	function checkVale(code)
	{
		var leastValue=$("#leastValue").val();
		var unusedTime=$("#unusedTime").val();
		var startTime=$("#startTime").val();
		if(leastValue.length==0)
		{
			Ext.MessageBox.alert('提示','每天用户用量的最低值不能为空!');
			return;
		}
		if(leastValue<=0)
		{
			Ext.MessageBox.alert('提示','每月用户用量的最低值输入不合规范，必须大于零!');
			return;
		}
		if(unusedTime.length==0)
		{
			Ext.MessageBox.alert('提示','未使用时间不能为空!');
			return;
		}
		if(unusedTime<=0||unusedTime>28)
		{
			Ext.MessageBox.alert('提示','未使用时间输入不合规范!');
			return;
		}
		if(startTime.length==0)
		{
			Ext.MessageBox.alert('提示','查询月份不能为空!');
			return;
		}
		var year=startTime.substring(0,startTime.indexOf("-"));
		var month=startTime.substring(startTime.indexOf("-")+1);
		var month2=new Date().getMonth()+1;
		var year2=new Date().getYear();
		var date=new Date().getDate();
		if(year==year2&&month==month2)
		{
			if(unusedTime>date)
			{
				Ext.MessageBox.alert('提示','查询本月数据时，未使用时间不能超过当天');
				return;
			}
		}
		$("#datatable tr").eq(0).nextAll().remove();
		Ext.Ajax.request({
			url : "${pageContext.request.contextPath}/hodMeterInfoHistory!getUnUsed.do",
			params:{  
				leastValue:leastValue,
            	unusedTime:unusedTime,
            	startTime:startTime,
            	code:code
        	},  
			async : false,
			method : 'POST',
			timeout : 180000,
			success : function(res) {
				var result = Ext.decode(res.responseText);
				if (true == result.success) {
					var data=result.data;
					$.each(data,function(i,n){
						var row = $("<tr>");
						var td1 = $('<td id="num" class="STYLE1">');
						var td2 = $('<td id="meterName" class="STYLE1">');
						var td3 = $('<td id="communityName" class="STYLE1">');
						var td4 = $('<td id="buildingName" class="STYLE1">');
						var td5 = $('<td id="unitName" class="STYLE1">');
						var td6 = $('<td id="roomName" class="STYLE1">');
						var td7 = $('<td id="detail" class="STYLE1">');
						td1.appendTo(row);
						td2.appendTo(row);
						td3.appendTo(row);
						td4.appendTo(row);
						td5.appendTo(row);
						td6.appendTo(row);
						td7.appendTo(row);
						row.find("#num").text(i+1);
						row.find("#meterName").text(n.meterName);
						row.find("#communityName").text(n.communityName);
						row.find("#buildingName").text(n.buildingName);
						row.find("#unitName").text(n.unitName);
						row.find("#roomName").text(n.roomName);
						var a="selectDetail('"+n.meterName+"','"+startTime+"')";
						row.find("#detail").html('<a style="color:#f60" href="javaScript:'+a+';">用量明细</a>');
						row.appendTo("#datatable");
					});
				}
				else
				{
					Ext.MessageBox.alert('提示',result.msg);
				}
			},
			failure : function(res) {
				Ext.MessageBox.hide();
				Ext.MessageBox.alert('提示','请求失败!');
			}
	    });
	}
	
	function selectDetail(meterName,months)
	{
		var mcontrolDlg = new gHistoryDetailCreate;
		mcontrolDlg.setTitle('历史数据[' + meterName + ']') ;
  		mcontrolDlg.gridStore.load({
			params : {
				meterName : meterName,
				months :months
			}
		});
		mcontrolDlg.show();
		//Ext.MessageBox.alert('提示',meterName);
	}
</script>

</head>
		<div>
				<table class="repCnd">
				<tr class="repCndLb">
				<td class="repCndLb" nowrap="nowrap">每天用户用量的最低值(KWh):</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input style="width: 80px;" type="text" id="leastValue" name="leastValue" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/>
				</span></td>
				<td class="repCndLb" nowrap="nowrap">未使用时间(天):</td>
				<td class="repCndEdit" nowrap="nowrap">
					<span><input style="width: 80px;" type="text" id="unusedTime" name="unusedTime" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/></span>
				</td>
				<td class="repCndLb" nowrap="nowrap">月份:</td>
				<td class="repCndEdit" nowrap="nowrap"><span>
					<input class="Wdate" style="width: 120px;" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%Y-%M'})" id="startTime" name="startTime"/>
				</span></td>
				<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="checkVale()"/></td>
				</tr>
				</table>
		</div>
  
		<div class="repCndToolBar">
		</div>


