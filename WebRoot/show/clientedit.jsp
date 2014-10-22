<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>变更处理</title>
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/common/tab/show.css">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/util.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
	padding: 5px 1px 1px 0px;
}
</style>
		<script type="text/javascript">
		function toggle(targetid,curDivId)
		{
			 var target=document.getElementById(targetid);
			 var curDiv=document.getElementById(curDivId);
			 if (target.style.display=="block")
			 {
				  target.style.display="block";
				  curDiv.style.display="none";
			 } 
			 else 
			 {
				  target.style.display="none";
				  curDiv.style.display="block";
			 }
		}
		
		function delContentx(roomId,meters){
			delContent(roomId,meters);
			document.getElementById(meters).style.display='none';
		}
		
		function checkForm()
		{
			var clientId=$("#clientId").val();
			var clientName=$("#clientName").val();
			var clientAddress=$("#clientAddress").val();
			var clientTel=$("#clientTel").val();
			var clientRemark=$("#clientRemark").val();
			var roomId=$("#roomId").val();
			var clientSex=$("input[name='clientSex']:checked").val();
			var number = $('#clientIdentity').val();
			var meters=$("#meters").html();
			var priceType = $("#priceType").val();
			$("#clientName2").html(clientName);
			$("#clientSex2").html(clientSex);
			$("#clientIdentity2").html(number);
			$("#clientAddress2").html(clientAddress);
			$("#clientTel2").html(clientTel);
			if(priceType=='1'){
				$("#priceType2").html("定额价格方案");
			}else if(priceType=='2'){
				$("#priceType2").html("阶梯价格方案一");
			}else if(priceType=='3'){
				$("#priceType2").html("阶梯价格方案二");
			}
			$("#meters2").html(meters);
			$("#clientRemark2").html(clientRemark);	
			if(0==clientId.length)
			{
				Ext.MessageBox.alert('提示','请输入要变更人的姓名或证件号码!');
				return false;
			}
			if(clientName.length<2||clientName.length>32)
			{
				Ext.MessageBox.alert('提示','用户名称输入不合规范，请重新输入!');
				return false;
			}
			if(clientAddress.length<2||clientAddress.length>128)
			{
				Ext.MessageBox.alert('提示','地址输入不合规范，请重新输入!');
				return false;
			}
			if(clientTel.length>15)
			{
				Ext.MessageBox.alert('提示','电话输入不合规范，请重新输入!');
				return false;
			}
			if(clientRemark.length>128)
			{
				Ext.MessageBox.alert('提示','备注输入不合规范，请重新输入!');
				return false;
			}
			if(0==roomId.length)
			{
				Ext.MessageBox.alert('提示','请给用户指定房间!');
				return false;
			}
			if(confirm("是否打印变更信息?"))
			{
				toggle('div1','div2');
				window.print();
			}
		}
		
		function checkSearch()
		{
			var clientName=$("#clientNames").val();
			var clientIdentity=$("#clientIdentitys").val();
			if(0==clientName.length&&0==clientIdentity.length)
			{
				Ext.MessageBox.alert('提示','请输入查询条件!');
				return false;
			}
		}
		
		function displayMeter(id)
		{
			if(${ids==null})
			{
				Ext.MessageBox.alert('提示','请先查询要变更的用户信息!');
				return;
			}
			var oldRoomId=$("#roomId").val();
			var meters=document.getElementById('meters');
			var roomId=window.showModalDialog("${pageContext.request.contextPath}/share/selectRoom4.jsp?ids=${ids}",window,'dialogHeight=450px;dialogWidth=850px;center=yes;status=no;scroll=no;');
			if(roomId!=null&&roomId!='')
			{
				document.getElementById(id).value=roomId;
				if(oldRoomId!="")
					roomId=oldRoomId+','+roomId;
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodRoom!showMeter.do",
					params:{  
						ids:roomId
		        	},  
					async : false,
					method : 'POST',
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							var len=result.data.length;
							if(0==len)
							{
								Ext.MessageBox.alert('提示','您选择的房间没有绑定表计信息!');
								return;
							}
							meters.innerHTML='';
							var tab=document.createElement("table");
							tab.width='100%';
							tab.border=1;
							tab.cellPadding=0;
							tab.cellSpacing=0;
							tab.style.textAlign='center';
							var tbody=document.createElement("tbody");
							var th=document.createElement("tr");
							var thtd1=document.createElement("td");
							var thtd2=document.createElement("td");
							var thtd3=document.createElement("td");
							var thtd4=document.createElement("td");
							thtd1.appendChild(document.createTextNode("房间号"));
							thtd2.appendChild(document.createTextNode("户型"));
							thtd3.appendChild(document.createTextNode("表号"));
							thtd4.appendChild(document.createTextNode("表底数"));
							th.appendChild(thtd1);
							th.appendChild(thtd2);
							th.appendChild(thtd3);
							th.appendChild(thtd4);
							tbody.appendChild(th);
							for(var i=0;i<len;i++)
							{
								var tr=document.createElement("tr");
								var td1=document.createElement("td");
								var content1 = document.createTextNode(result.data[i].roomName);
								td1.appendChild(content1);
								var td2=document.createElement("td");
								var content2 = document.createTextNode(result.data[i].roomHouseType);
								td2.appendChild(content2);
								var td3=document.createElement("td");
								var content3 = document.createTextNode(result.data[i].meterName);
								td3.appendChild(content3);
								var td4=document.createElement("td");
								var content4 = document.createTextNode(result.data[i].meterInit);
								td4.appendChild(content4);
								tr.appendChild(td1);
								tr.appendChild(td2);
								tr.appendChild(td3);
								tr.appendChild(td4);
								tbody.appendChild(tr);
								tab.appendChild(tbody);
								meters.appendChild(tab);
							}
						}
						else
						{
							Ext.MessageBox.alert('提示','请求失败');
						}
					},
					failure : function(res) {
						Ext.MessageBox.alert('提示','请求失败!');
					}
				});
				meters.style.display='block';
			}
		}
		$(function(){
			$("#priceType").val(${priceType});
			$('#clientEditForm input').keyup(trimkeyup);
            $('#clientEditForm textarea').keyup(trimkeyup);
		});
	</script>

	</head>

	<body>
		<div id="div1">
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
															<B>当前位置</B>：[客户信息]-[
															<font color='red'>变更</font>]
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
																		<a
																			href="${pageContext.request.contextPath}/subpage.jsp?id=0"><div
																				class="returnbtn"></div> </a>
																	</td>
																</tr>
															</table>
														</td>
														<td width="90">
															<table width="99%" border="0" cellpadding="0"
																cellspacing="0">
																<tr>
																	<td class="STYLE2">
																		<a href="${pageContext.request.contextPath}/main.jsp"><div
																				class="homebtn"></div> </a>
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
								<td width="8" class="tab_12"></td>
								<td>
									<div style="height: 1px; width: 100%;">
										<table>
											<tr>
												<td height="1"></td>
											</tr>
										</table>
									</div>
									<DIV id="searchDIV">
										<form method="post"
											action="${pageContext.request.contextPath}/hod2000Client!toUpdate.do"
											style="margin: 0px" onsubmit="return checkSearch();">
											<%@include file="/inc/clientSearch.jsp"%>
										</form>
									</DIV>
								</td>
								<td width="8px" class="tab_15"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="background-color: white;">
							<tr>
								<td width="8" class="tab_12">
								</td>
								<td>
									<form
										action="${pageContext.request.contextPath}/hod2000Client!doUpdate.do"
										method="post" id="clientEditForm">
										<input type="hidden" id="clientId"
											name="hod2000Client.clientId"
											value="${hod2000Client.clientId}">
										<table cellpadding="0" cellspacing="0" border="0"
											height="400px" align="center" id="tab">
											<tr>
												<td>
													用户姓名：
												</td>
												<td>
													<input type="text" id="clientName"
														value="${hod2000Client.clientName}" name="clientName" />
													<span>必填，2-32位字符</span>
												</td>
											</tr>
											<tr>
												<td>
													用户性别：
												</td>
												<td>
													<c:if test="${hod2000Client.clientSex=='男'}">
														<input type="radio" value="男" name="clientSex"
															checked="checked" />男
												<input type="radio" value="女" name="clientSex" />女
											</c:if>
													<c:if test="${hod2000Client.clientSex=='女'}">
														<input type="radio" value="男" name="clientSex" />男
												<input type="radio" value="女" name="clientSex"
															checked="checked" />女
											</c:if>
													<c:if test="${hod2000Client.clientSex==null}">
														<input type="radio" value="男" name="clientSex" />男
												<input type="radio" value="女" name="clientSex" />女
											</c:if>
												</td>
											</tr>
											<tr>
												<td>
													证件类型：
												</td>
												<td>
													<c:if test="${hod2000Client.clientCardType==0}">
														<input type="text" value="二代身份证" readonly="readonly"/>
													</c:if>
													<c:if test="${hod2000Client.clientCardType==1}">
														<input type="text" value="港澳通行证" readonly="readonly"/>
													</c:if>
													<c:if test="${hod2000Client.clientCardType==2}">
														<input type="text" value="台湾通行证" readonly="readonly"/>
													</c:if>
													<c:if test="${hod2000Client.clientCardType==3}">
														<input type="text" value="护照" readonly="readonly"/>
													</c:if>
													<c:if test="${hod2000Client.clientCardType==null}">
														<input type="text" readonly="readonly"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<td>
													证件号码：
												</td>
												<td>
													<input type="text" id="clientIdentity"
														value="${hod2000Client.clientIdentity}"
														name="clientIdentity" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td>
													联系地址：
												</td>
												<td>
													<input type="text" id="clientAddress"
														value="${hod2000Client.clientAddress}"
														name="clientAddress" />
													<span>必填，2-128位字符</span>
												</td>
											</tr>
											<tr>
												<td>
													联系电话：
												</td>
												<td>
													<input type="text" id="clientTel"
														value="${hod2000Client.clientTel}" name="clientTel" />
													<span>最长不超过15个字符</span>
												</td>
											</tr>
											<tr>
												<td>
													价格方案：
												</td>
												<td>
													<select name="priceType" id="priceType">
														<option value="1" selected="selected">
															定额价格方案
														</option>
														<option value="2">
															阶梯价格方案一
														</option>
														<option value="3">
															阶梯价格方案二
														</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													房间信息：
												</td>
												<td>
													<input type="hidden" name="roomId" id="roomId"
														value="${roomId }">
													<input type="button" value="选择房间信息"
														onclick="displayMeter('roomId')">
													<input type="button" value="清除"
														onclick="delContentx('roomId','meters')">
													<span>只显示未关联用户且已经关联表计的房间信息</span>
													<div id="meters" style="margin-top: 5px;overflow-y: auto;height: 55px;">
															<table cellpadding="0" cellspacing="0" border="1"
																width="100%">
																<tr>
																	<td>
																		房间号
																	</td>
																	<td>
																		户型
																	</td>
																	<td>
																		表号
																	</td>
																	<td>
																		表底数
																	</td>
																</tr>
																<c:if test="${list!=null}">
																	<c:forEach items="${list}" var="v">
																		<tr>
																			<td>
																				${v.roomName }
																			</td>
																			<td>
																				${v.roomHouseType }
																			</td>
																			<td>
																				${v.meterName }
																			</td>
																			<td>
																				${v.meterInit }
																			</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</table>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													备注信息：
												</td>
												<td>
													<textarea cols="30" rows="6" id="clientRemark"
														name="clientRemark">${hod2000Client.clientRemark}</textarea>
													<span>最长不超过128个字符</span>
												</td>
											</tr>
											<tr>
												<td colspan="3" align="center">
													<input type="submit" value="提交"
														onclick="return checkForm()" />
													<input type="button" value="重置"
														onclick="javaScript:window.location.href='${pageContext.request.contextPath}/show/clientedit.jsp';" />
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
								<td>
									&nbsp;
								</td>
								<td width="16" class="tab_20">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="div2" style="display: none">
			<table width="80%" border="1">
				<tr>
					<td height="32" colspan="4" align="center" scope="row">
						<div style="float: left; padding: 5px 1px 1px 15px;">

							<img src="${pageContext.request.contextPath}/images/hodlogin.jpg"
								width="76" height="23" />
						</div>
						<span class="STYLE1">客户变更信息</span>
					</td>
				</tr>
				<tr>
					<td width="29%" height="25" align="center" scope="row">
						用户姓名
					</td>
					<td width="25%" align="center">
						用户性别
					</td>
					<td colspan="2" align="center">
						证件号码
					</td>
				</tr>
				<tr>
					<td height="25" align="center" scope="row" border="1">
						<span class="STYLE13" id="clientName2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="clientSex2"></span>
					</td>
					<td colspan="2" align="center">
						<span class="STYLE13" id="cardType2"></span>
						<span class="STYLE13" id="clientIdentity2"></span>
					</td>
				</tr>
				<tr>
					<td align="center" scope="row" height="25">
						联系地址:
					</td>
					<td colspan="3">
						<span class="STYLE13" id="clientAddress2"></span>
					</td>
				</tr>
				<tr>
					<td align="center" scope="row" height="25">
						联系电话:
					</td>
					<td>
						<span class="STYLE13" id="clientTel2"></span>
					</td>
					<td width="23%" align="center">
						价格方案：
					</td>
					<td width="23%">
						<span class="STYLE13" id="priceType2"></span>
					</td>
				</tr>
				<tr>
					<td height="29" align="center" scope="row">
						房间信息:
					</td>
					<td colspan="3" style="margin-top: 5px 5px 5px 5px;">
						<span class="STYLE13" id="meters2"></span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
