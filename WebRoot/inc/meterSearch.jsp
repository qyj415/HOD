<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	//阀门控制
	function valveControl(){
		var allbox=document.getElementsByName("ckId");
		var valveStatus=$("input[name='valveStatus']:checked").val();
		var meterId=$("input[name='ckId']:checked").val();
		if(!meterId){
			Ext.MessageBox.alert('提示','请先选择表计!');
		}
		else
		{
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
			Ext.Ajax.request({
				url : "${pageContext.request.contextPath}/hodMeter!valveControl.do",
				method:'POST',
				params:{  
	            	meterId:meterId,
	            	valveStatus:valveStatus
	        	}, 
				async : false,
				timeout : 180000,
				success : function(res) {
					var result = Ext.decode(res.responseText);
					Ext.MessageBox.hide();
					Ext.MessageBox.alert('提示',result.msg);
				},
				failure : function(res) {
					Ext.MessageBox.hide();
					Ext.MessageBox.alert('提示','请求失败!');
				}
			});
		} 
	}
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
						<td class="repCndLb" nowrap="nowrap">表号:</td>
						<td class="repCndEdit" nowrap="nowrap"><span>
							<input class="border" type="text" name="meterName" id="meterName" maxlength="8" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/> 
						</span></td>
						<td class="repCndLb" nowrap="nowrap">所属集中器:</td>
						<td class="repCndEdit" nowrap="nowrap"><span><input class="border" type="text" name="terminalId" id="terminalId" maxlength="8"/> </span></td>
					<!-- 
					<td class="repCndLb" nowrap="nowrap">用户姓名：</td>
					<td class="repCndEditRight" nowrap="nowrap"><span><input class="border" type="text" name="clientName" value="${clientName }"/></span></td>
					-->
						<td class="repCndLb" nowrap="nowrap">表计类型:</td>
						<td class="repCndEditRight" nowrap="nowrap">
							<span>
								<select id="meterStyle" name="meterStyle" class="border">
									<option value="">请选择</option>
									<option value="1">户用表</option>
									<option value="2">楼栋表</option>
									<option value="3">换能站</option>
								</select>
							</span>
						</td>
					</tr>
					
					<tr class="repCnd">
						<td class="repCndLb" nowrap="nowrap">表计型号:</td>
						<td class="repCndEdit" nowrap="nowrap">
							<span>
								<select name="meter_type" class="border" id="meter_type">
									<option value="">请选择</option>
									<c:forEach items="${meterTypes}" var="c">
										<option value="${c.typeIndex }">${c.typeName }</option>
									</c:forEach>
								</select>
							</span>
						</td>
						<td class="repCndLb" nowrap="nowrap">口径:</td>
						<td class="repCndEdit" nowrap="nowrap">
							<span>
								<select name="meter_caliber" class="border" id="meter_caliber">
									<option value="">请选择</option>
									<c:forEach items="${caliber}" var="c">
										<option value="${c.pvalue }">${c.pname }</option>
									</c:forEach>
								</select>
							</span>
						</td>
						 <td class="repCndLb"><input type="button" class="btn" value="" onclick="searchList(1)"/></td>	
						 <!-- <td class="repCndEditRight" nowrap="nowrap"></td>
						 <td class="repCndLb" nowrap="nowrap">阀门控制:</td>
						 <td class="repCndEdit" nowrap="nowrap">
							<input type="radio" name="valveStatus" id="valveOpen" value="1" checked="checked">开
						</td>
						<td class="repCndEdit" nowrap="nowrap">
							<input type="radio" name="valveStatus" id="valveClose" value="0">关
						</td>
						<td class="repCndEditRight"><a id="deleteById" href="#" onclick="valveControl()"><div class="confirmbtn"></div></a></td>
						 -->
					</tr>
				</table>
		</div>
  
		<div class="repCndToolBar"></div>


