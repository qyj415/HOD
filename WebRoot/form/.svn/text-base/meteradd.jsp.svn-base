<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加热量表信息</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<style type="text/css">
		.btn{
			background: url("${pageContext.request.contextPath}/images/search.gif");
			width: 26px;
			height: 26px;
			overflow: hidden;
			cursor: pointer;
			border: 0;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#meterBaudrate").val(4);
			$("#meterCaliber").val(20);
			$('#meterAddForm input').keyup(trimkeyup);
		});
		
		function checkForm()
		{
			//jQuery("#tijiao").attr({"disabled":"disabled"});
			var meterName=$("#meterName").val();
			var meter_position=$("#meter_position").val();
			if(meterName.length!=8)
			{
				Ext.MessageBox.alert('提示','表号输入不合规范，请重新输入!');
				return false;
			}
			if(meter_position.length<1)
			{
				Ext.MessageBox.alert('提示','地理位置信息不能为空，请选择!');
				return false;
			}
			var roomId=$("#roomId").val();
			var meterStyle=$("#meterStyle").val();
			if(roomId.length>0&&meterStyle!=1)
			{
				Ext.MessageBox.alert('提示','根据您设置的地理位置，该表计类型应为户用表!');
				return false;
			}
			if(roomId.length==0&&meterStyle==1)
			{
				Ext.MessageBox.alert('提示','根据您设置的地理位置，该表计类型不应为户用表!');
				return false;
			}
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
		}
	
		//选择地理位置信息
		function showDia()
		{
			var params=window.showModalDialog('${pageContext.request.contextPath}/share/showAddress.jsp',window,'dialogHeight=450px;dialogWidth=750px;center=yes;status=no;');
			if(params!=null)
			{
				$("#meter_position").val(params[0]);
				if(params[1]==7)
					$("#roomId").val(params[2]);
				else
					$("#roomId").val('');
				$("#meter_position_name").val(params[3]);
			}
		}
		
		function showParent()
		{
			var params=window.showModalDialog('${pageContext.request.contextPath}/share/showAddress.jsp',window,'dialogHeight=450px;dialogWidth=750px;center=yes;status=no;');
			if(params!=null)
			{
				$("#meter_parent").val(params[0]);
				$("#meter_parent_name").val(params[3]);
			}
		} 
	</script>
  </head>
  
  <body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30" class="tab_05">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30" class="tab_03"></td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="5%" class="tb"></td>
													<td width="95%" class="STYLE2">
														<B>当前位置</B>：[表计管理]-[
														<font color='red'>添加热量表信息</font>]
													</td>
												</tr>
											</table>
										</td>
										<td width="54%">
											<table border="0" align="right" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="60"></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="16" class="tab_07"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
								<!-- 可以写form -->
								<form action="${pageContext.request.contextPath}/hod2000Meter!doSave.do" method="post" id="meterAddForm">
								<table cellpadding="0" cellspacing="0" border="0" align="center" height="300" id="tab">
									<tr>
										<td width="100px">表号：</td>
										<td><input type="text" id="meterName" name="hod2000Meter.meterName" maxlength="8" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" style="width: 200px;"/><span>必填，8位数字</span></td>
									</tr>
									<tr>
										<td>通信速率：</td>
										<td>
											<select id="meterBaudrate" name="hod2000Meter.meterBaudrate" style="width: 200px;">
												<c:forEach items="${speed}" var="c">
													<option value="${c.pvalue }">${c.pname }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td>口径：</td>
										<td>
											<select id="meterCaliber" name="hod2000Meter.meterCaliber" style="width: 200px;">
												<c:forEach items="${caliber}" var="c">
													<option value="${c.pvalue }">${c.pname }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td >表计型号：</td>
										<td>
											<select id="meterType" name="meterType" style="width: 200px;">
												<c:forEach items="${meterType}" var="c">
													<option value="${c.typeIndex }">${c.typeName }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td >表计类型：</td>
										<td>
											<select id="meterStyle" name="hod2000Meter.meterStyle" style="width: 200px;">
												<option value="1" selected="selected">户用表</option>
												<option value="2">楼栋表</option>
												<option value="3">换能站</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>地理位置：</td>
										<td>
											<input type="hidden" id="roomId" name="roomId">
											<input type="hidden" name="hod2000Meter.meterPosition" id="meter_position"/>
											<input type="text" name="hod2000Meter.meterPositionName" id="meter_position_name" style="width: 175px;height: 26px;"/><input type="button" value="" class="btn" onclick="showDia()">
										</td>
									</tr>
									<tr>
										<td>上级表：</td>
										<td>
											<input type="hidden" name="hod2000Meter.meterParent" id="meter_parent" value="${parent_position}" readonly="readonly"/>
											<input type="text" name="meter_parent_name" id="meter_parent_name" readonly="readonly" value="${parent_positionName }" style="border: 1px solid gray;width: 200px;"/>
											<!-- <input type="button" value="" class="btn" onclick="showParent()"> -->
										</td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="提交" onclick="return checkForm()" id="tijiao"/>
											<input type="reset" value="重置"/>
										</td>
									</tr>
								</table>
								</form>
							</td>
							<td width="8" class="tab_15">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="35" class="tab_19">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="35" class="tab_18">
							</td>
							<td>&nbsp;</td>
							<td width="16" class="tab_20">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
  </body>
</html>
