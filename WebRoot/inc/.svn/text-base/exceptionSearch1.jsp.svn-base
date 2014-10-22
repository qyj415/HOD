<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	function checkValue1(code)
	{
		var average=$("#average").val();
		var coefficient=$("#coefficient").val();
		var meterStyle=$("#meterStyle").val();
		var months=$("#months").val();
		if(average.length==0)
		{
			Ext.MessageBox.alert('提示','月平均用量不能为空!');
			return;
		}
		if(average=='0')
		{
			Ext.MessageBox.alert('提示','月平均用量不能为0!');
			return;
		}
		if(coefficient.length==0)
		{
			Ext.MessageBox.alert('提示','用户异常系数不能为空!');
			return;
		}
		if(months.length==0)
		{
			Ext.MessageBox.alert('提示','查询月份不能为空!');
			return;
		}
		$("#datatable2 tr").eq(0).nextAll().remove();
		Ext.Ajax.request({
			url : "${pageContext.request.contextPath}/hodMeterInfoHistory!getAbnormal.do",
			params:{  
				average:average,
            	coefficient:coefficient,
            	months:months,
            	meterStyle:meterStyle,
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
						var a="selectDetail('"+n.meterName+"','"+months+"')";
						row.find("#detail").html('<a style="color:#f60" href="javaScript:'+a+';">用量明细</a>');
						row.appendTo("#datatable2");
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
					<td class="repCndLb" nowrap="nowrap">月平均用量(KWh):</td>
					<td class="repCndEdit" nowrap="nowrap">
						<span><input style="width: 80px;" type="text" id="average" name="average" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/></span>
					</td>
					<td class="repCndLb" nowrap="nowrap">用户异常系数:</td>
					<td class="repCndEdit" nowrap="nowrap">
						<span><input style="width: 80px;" type="text" id="coefficient" name="coefficient" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>%</span>
					</td>
					<td class="repCndLb" nowrap="nowrap">表计类型:</td>
					<td class="repCndEdit" nowrap="nowrap">
						<span>
							<select style="width: 80px;" name="meterStyle" id="meterStyle">
								<option value="">请选择</option>
								<option value="1">户用表</option>
								<option value="2">楼栋表</option>
								<option value="3">换能站</option>
							</select>
						</span>
					</td>
					<td class="repCndLb" nowrap="nowrap">月份:</td>
					<td class="repCndEdit" nowrap="nowrap"><span>
						<input class="Wdate" style="width: 100px;" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%Y-%M'})" id="months" name="months"/>
					</span></td>
					<td class="repCndEditRight"><input type="button" class="btn" value="" onclick="checkValue1()"/></td>
				</tr>
				</table>
		</div>
  
		<div class="repCndToolBar">
		</div>


