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
    
    <title>开户</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/check.js"></script>
	<script type="text/javascript">
		function openwindow(url,name,iWidth,iHeight)
		 {
			  var url;                                 //转向网页的地址;
			  var name;                           //网页名称，可为空;
			  var iWidth;                          //弹出窗口的宽度;
			  var iHeight;                        //弹出窗口的高度;
			  var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
			  var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
			  window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
		 }

		
	</script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
}
</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
             $('#clientAddForm input').keyup(trimkeyup);
             $('#clientAddForm textarea').keyup(trimkeyup);
 		});
 		
		function delContentx(roomId,meters){
			delContent(roomId,meters);
			document.getElementById(meters).style.display='none';
			$('#roomPro').html('只显示未关联用户且已经关联表计的房间信息');
		}
		
		function displayMeter(id)
		{
			var oldRoomId=$("#roomId").val();
			var meters=document.getElementById('meters');
			var roomId=window.showModalDialog("${pageContext.request.contextPath}/share/selectRoom.jsp",window,'dialogHeight=450px;dialogWidth=850px;center=yes;status=no;scroll=no;');
			if(roomId!=null&&roomId!='')
			{
				document.getElementById(id).value=roomId;
				meters.innerHTML='';
				if(oldRoomId!="")
					roomId=oldRoomId+','+roomId;
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodRoom!showMeter.do?ids="+roomId,
					async : false,
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							var len=result.data.length;
							if(0==len)
							{
								Ext.MessageBox.alert('提示','该房间没有绑定表计信息!');
								return;
							}
							var tab=document.createElement("table");
							tab.width='100%';
							tab.border=1;
							tab.cellPadding=0;
							tab.cellSpacing=0;
							tab.style.collapse = "collapse";
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
								td2.innerHTML = result.data[i].roomHouseType+"&nbsp";
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
							$('#roomPro').html('<img style="width:15px;height:15px;" src="./common/js/ok.gif" />');
						}
						else
						{
							Ext.MessageBox.alert('提示','请求失败!');
						}
					},
					failure : function(res) {
						Ext.MessageBox.alert('提示','请求失败!');
					}
				});
				meters.style.display='block';
			}
		}
		
	</script>
  </head>
  
  <body>
  <div id="div3"> 
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
														<font color='red'>开户</font>]
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
																	<a href="${pageContext.request.contextPath}/form/uploadclient.jsp"><div class="uploadbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=0"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp"><div class="homebtn"></div></a>
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
								<form action="${pageContext.request.contextPath}/hod2000Client!doSave.do" method="post" id="clientAddForm">
									<input type="hidden" name="buildingId" id="buildingId">
									<table cellpadding="0" cellspacing="0" border="0" height="500px" align="center" id="tab">
										<tr>
											<td>用户姓名：</td>
											<td ><input type="text" id="clientName" name="hod2000Client.clientName" onkeyup="checkName();" onblur="checkName();"/>
												<span id="namePro">必填，2-32位字符</span>
											</td>
										</tr>
										<tr>
											<td>用户性别：</td>
											<td>
												<input type="radio" value="男" name="hod2000Client.clientSex" checked="checked"/>男
												<input type="radio" value="女" name="hod2000Client.clientSex"/>女
											</td>
										</tr>
										<tr>
											<td>证件类型：</td>
											<td>
												<select name="hod2000Client.clientCardType" onchange="checkCertificate();" id="cardType">
													<option value="0" selected="selected">二代身份证</option>
													<option value="1">港澳通行证</option>
													<option value="2">台湾通行证</option>
													<option value="3">护照</option>
												</select>
											</td>
										</tr>
										<tr>
											<td>证件号码：</td>
											<td><input type="text" id="clientIdentity" name="hod2000Client.clientIdentity" onkeyup="checkCertificate();" onblur="checkCertificate();"/>
											<span id="clientIdentityPro" style="vertical-align:middle;">必填，请准确填写您的证件号码(包括字母、数字)</span></td>
										</tr>
										<tr>
											<td>联系地址：</td>
											<td><input type="text" id="clientAddress" name="hod2000Client.clientAddress" onkeyup="checkAddress()" onblur="checkAddress()" />
											<span id="addressPro">必填，2-128位字符</span></td>
										</tr>
										<tr>
											<td>联系电话：</td>
											<td><input type="text" id="clientTel" name="hod2000Client.clientTel" onkeyup="checkTel()" onblur="checkTel()"/>
											<span id="telPro">最长不超过15个字符,请不要使用字符'-'</span></td>
										</tr>
										<tr>
											<td>价格方案：</td>
											<td>
												<select name="priceType" id="priceType">
													<option value="1" selected="selected" >定额价格方案</option>
													<option value="2">阶梯价格方案一</option>
													<option value="3">阶梯价格方案二</option>
												</select>
												<span id="typePro"></span>
											</td>
										</tr>
										<tr>
											<td>房间信息：</td>
											<td>
												<input type="hidden" name="roomId" id="roomId" readonly="readonly">
												<input type="button" value="选择房间信息" onclick="displayMeter('roomId')">
												<input type="button" value="清除" onclick="delContentx('roomId','meters')">
												<span id="roomPro">只显示未关联用户且已经关联表计的房间信息</span>
											</td>			
										</tr>
										<tr>
											<td></td>
											<td>
												<div id="meters" style="display: none;margin-top: 5px;overflow-y: auto;height: 70px;"></div>
											</td>			
										</tr>
										<tr>
											<td>备注信息：</td>
											<td><textarea cols="42" rows="3" id="clientRemark" name="hod2000Client.clientRemark" onkeyup="checkRemark()" onblur="checkRemark()"></textarea>
											<span id="remarkPro">最长不超过128个字符</span></td>
										</tr>
										<tr>
											<td colspan="2" style="text-align: center;">
												<input type="submit" value="提交" onclick="return checkClientaddForm()"/>
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
		</div>
		<div id="div4" style="display:none">
			<table width="85%" border="1">
				<tr>
					<td height="32" colspan="4" align="center" scope="row">
						<div style="float: left">
							&nbsp;&nbsp;&nbsp;
							<img src="${pageContext.request.contextPath}/images/hodlogin.jpg"
								width="76" height="23" />
						</div>
						<span class="STYLE1">客户开户信息</span>
					</td>
				</tr>
				<tr>
					<td height="25" align="center" scope="row">
						用户姓名
					</td>
					<td align="center">
						用户性别
					</td>
					<td align="center">
						证件类型
					</td>
					<td align="center">
						证件号码
					</td>
				</tr>
				<tr>
					<td align="center" scope="row" border="1">
						<span class="STYLE13" id="clientName2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="clientSex2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="cardType2"></span>
					</td>
					<td align="center">
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
						联系电话：
					</td>
					<td>
						<span class="STYLE13" id="clientTel2"></span>
					</td>
					<td align="center">
						价格方案：
					</td>
					<td>
						<span class="STYLE13" id="priceType2"></span>
					</td>
				</tr>
				<tr>
					<td align="center" scope="row">
						房间信息：
					</td>
					<td colspan="3" style="margin-top: 5px 5px 5px 5px;">
						<span class="STYLE13" id="meters2" ></span>
					</td>
				</tr>
			</table>
		</div>
		
  </body>
</html>
