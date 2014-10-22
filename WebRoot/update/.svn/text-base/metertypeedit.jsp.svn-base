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
    
    <title>修改表计型号</title>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<script type="text/javascript">
		function checkForm()
		{
			var meterType=$("#meterType").val();
			var meterCode=$("#meterCode").val();
			var product_name=$("#product_name").val();
			var product_num1=$("#product_num1").val();
			var product_num2=$("#product_num2").val();
			var product_num3=$("#product_num3").val();
			if(meterType.length<2||meterType.length>10)
			{
				Ext.MessageBox.alert('提示','表计型号输入不合规范，请重新输入!');
				return false;
			}
			if(meterCode.length!=2)
			{
				Ext.MessageBox.alert('提示','仪表类型输入不合规范，请重新输入!');
				return false;
			}
			if(product_name.length<2||product_name.length>10)
			{
				Ext.MessageBox.alert('提示','厂商名称输入不合规范，请重新输入!');
				return false;
			}
			if(product_num1.length!=2||product_num2.length!=2||product_num3.length!=2)
			{
				Ext.MessageBox.alert('提示','厂商编号输入不合规范，请重新输入!');
				return false;
			}
			$("#product_number").val(product_num1+product_num2+product_num3);
		}
		$(function(){
			document.getElementById('meterTypes').value=${hod2000MeterType.communicationProtocol};
			$('#meterTypeEditForm input').keyup(trimkeyup);
		});
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
														<B>当前位置</B>：[
														<font color='red'>修改表计型号</font>]
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
																	<a href="${pageContext.request.contextPath}/show/metertypelist.jsp"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000MeterType!doUpdate.do" method="post" id="meterTypeEditForm">
								<input type="hidden" name="hod2000MeterType.typeIndex" value="${hod2000MeterType.typeIndex }">
								<input type="hidden" name="hod2000MeterType.companyNum" value="${hod2000MeterType.companyNum }" id="product_number">
								<table cellpadding="0" cellspacing="0" border="0" height="200px" align="center" id="tab">
									<tr>
										<td>表计型号：</td>
										<td><input type="text" id="meterType" name="hod2000MeterType.typeName" value="${hod2000MeterType.typeName }" maxlength="10"/><span>必填，2-10位字符</span></td>
									</tr>
									<tr>
										<td>仪表类型：</td>
										<td><input id="meterCode" name="hod2000MeterType.typeCode" value="${hod2000MeterType.typeCode }" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="2"><span>必填，2个数字</span></td>
									</tr>
									<tr>
										<td>厂商名称：</td>
										<td><input name="hod2000MeterType.companyName" value="${hod2000MeterType.companyName}" id="product_name" maxlength="10"/><span>必填，2-10位字符</span></td>
									</tr>
									<tr>
										<td>厂商编号：</td>
										<td><input id="product_num1" maxlength="2" style="width: 49px;" value="${num1}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>-<input id="product_num2" maxlength="2" style="width: 49px;" value="${num2}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>-<input id="product_num3" maxlength="2" style="width: 49px;" value="${num3}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/><span>必填，每个框2个数字，比如HOD为72-79-68</span></td>
									</tr>
									<tr>
										<td>通信规约：</td>
										<td>
											<select name="hod2000MeterType.communicationProtocol" id="meterTypes">
												<c:forEach items="${protocol}" var="p">
													<option value="${p.pvalue }">${p.pname }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="提交" onclick="return checkForm()"/>
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
