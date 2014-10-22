<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改集中器信息</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/loadSelect.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
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
		function checkForm()
		{
			var conAddress=$("#conAddress").val();
			var op = $("#meterTypeRight option"); 
			var meterType='';
			if(conAddress.length==0)
			{
				Ext.MessageBox.alert('提示','集中器安装位置不能为空!');
				return false;
			}
			if(op.length==0)
			{
				Ext.MessageBox.alert('提示','关联的表计型号不能为空!');
				return false;
			}
			if(op.length>20)
			{
				Ext.MessageBox.alert('提示','关联的表计型号不能超过20个!');
				return false;
			}
			op.each(function(n,v){
				meterType+=$(v).val()+',';
			});
			$("#meterType").val(meterType.substring(0,meterType.length-1));
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
		}
		$(function(){
			var freezeIntervalValue=document.getElementById('freezeIntervalValue');
			for(var i=1;i<60;i++)
			{
				var option1=document.createElement("option");
				option1.text=i;
				option1.value=i;
				freezeIntervalValue.options.add(option1);
			}
			$("#freezeInterval").val(${hod2000Concentrator.freezeInterval});
			typeChange(${hod2000Concentrator.freezeInterval});
			$("#freezeIntervalValue").val(${hod2000Concentrator.freezeIntervalValue});
		});
	
		//跟着值不用，加载不用的冻结间隔数值下拉
		function typeChange(value)
		{
			var freezeIntervalValue=document.getElementById('freezeIntervalValue');
			if(value=="")
			{
				value=$("#freezeInterval").val();
				freezeIntervalValue.length=0;//清空下拉列表中的option
			}
			if(value==0)
			{
				for(var i=1;i<60;i++)
				{
					var option1=document.createElement("option");
					option1.text=i;
					option1.value=i;
					freezeIntervalValue.options.add(option1);
				}
			}
			if(value==1)
			{
				for(var i=1;i<24;i++)
				{
					var option1=document.createElement("option");
					option1.text=i;
					option1.value=i;
					freezeIntervalValue.options.add(option1);
				}
			}
			if(value==2)
			{
				for(var i=1;i<2;i++)
				{
					var option1=document.createElement("option");
					option1.text=i;
					option1.value=i;
					freezeIntervalValue.options.add(option1);
				}
			}
			if(value==3)
			{
				for(var i=1;i<2;i++)
				{
					var option1=document.createElement("option");
					option1.text=i;
					option1.value=i;
					freezeIntervalValue.options.add(option1);
				}
			}
		}
		
		//选择地理位置信息
		function showDia()
		{
			var params=window.showModalDialog('${pageContext.request.contextPath}/share/showAddress1.jsp',window,'dialogHeight=450px;dialogWidth=750px;center=yes;status=no;');
			if(params!=null)
			{
				$("#conAddress").val(params[0]);
				$("#conPositionName").val(params[3]);
			}
		}
		
		//表计类型设置右移事件
		function moveRight()
		{
			var op = $("#meterTypeLeft option:selected"); 
			if(0==op.length)
			{
				$("#meterTypeLeft option").prependTo("#meterTypeRight");
			}
			else
			{
				$("#meterTypeLeft option:selected").prependTo("#meterTypeRight");
			}
		}
		
		//表计类型设置左移事件
		function moveLeft()
		{
			var op = $("#meterTypeRight option:selected"); 
			if(0==op.length)
			{
				$("#meterTypeRight option").prependTo("#meterTypeLeft");
			}
			else
			{
				$("#meterTypeRight option:selected").prependTo("#meterTypeLeft");
			}
		}
		
		//双击右移事件
		function dbMoveLeft()
		{
			$("#meterTypeLeft option:selected").prependTo("#meterTypeRight");
		}
		
		//双击左移事件
		function dbMoveRight()
		{
			$("#meterTypeRight option:selected").prependTo("#meterTypeLeft");
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
														<B>当前位置</B>：[集中器管理]-[集中器维护]-[
														<font color='red'>修改集中器信息</font>]
													</td>
												</tr>
											</table>
										</td>
										<td width="54%">
											<table border="0" align="right" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="60"></td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/show/concentratorlist.jsp"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp" target="mainFrame"><div class="homebtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
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
							<form action="${pageContext.request.contextPath}/hod2000Concentrator!doUpdate.do" method="post">
								<input type="hidden" name="conId" value="${hod2000Concentrator.conId }">
								<input type="hidden" name="meterType" id="meterType"/>
								<input type="hidden" name="conAddress" id="conAddress" value="${hod2000Concentrator.conAddress }"/>
								<table cellpadding="0" cellspacing="0" border="0" height="350px" align="center" id="tab">
									<tr>
										<td style="width:90px">集中器编号：</td>
										<td colspan="3"><input type="text" name="conNumber" value="${hod2000Concentrator.conNumber }" readonly="readonly" style="border: 1px solid gray;"/></td>
									</tr>
									<tr>
										<td>冻结间隔：</td>
										<td colspan="3">
											<select name="freezeInterval" id="freezeInterval" onchange="typeChange('')">
												<c:forEach items="${freeRead}" var="v">
													<option value="${v.pvalue }">${v.pname }</option>
												</c:forEach>
											</select>
											<select id="freezeIntervalValue" name="freezeIntervalValue"></select>
										</td>
									</tr>
									<tr>
										<td>安装位置：</td>
										<td colspan="3">
											<input type="text" value="${hod2000Concentrator.conPositionName}" id="conPositionName" readonly="readonly" name="conPositionName" style="width: 155px;height: 26px;"><input type="button" value="" class="btn" onclick="showDia()"><span>必选，至少要选择到小区级</span>
										</td>
									</tr>
									<tr>
										<td>表计型号关联：</td>
										<td style="width: 170px;">
											<select multiple="multiple" style="width: 150px;height: 150px;" id="meterTypeLeft" ondblclick="dbMoveLeft()">
												<c:forEach items="${meterType}" var="v">
													<option value="${v.typeIndex }">${v.typeName }</option>
												</c:forEach>
											</select>
										</td>
										<td style="width: 50px;">
											<input type="button" value="-->" onclick="moveRight()" /><br> 
										</td>
										<td>
											<select multiple="multiple" style="width: 150px;height: 150px;" id="meterTypeRight">
												<c:forEach items="${meterType2}" var="v">
													<option value="${v.typeIndex }">${v.typeName }</option>
												</c:forEach>
											</select><span>（已关联，1~20个）</span>
										</td>
									</tr>
									<tr>
										<td></td>
										<td colspan="3" ><span>集中器关联的表计型号只能添加，不能删除</span></td></tr>
									<tr><td colspan="4" >&nbsp;</td></tr>
									<tr>
										<td colspan="4" style="text-align: center;">
											<input type="submit" class="savebtn" value="" onclick="return checkForm()">
											<input type="reset" class="resetbtn" value="">
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
