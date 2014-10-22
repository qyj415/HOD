<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hod.util.Utils"%>
<%@page import="com.hod.util.NetworkTimeUtil;"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预收款</title>
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
	<style type="text/css">
		#div1 {
			display: block;
		}
		
		#div2 {
			display: none;
			font-family:"宋体";
			margin:10 10 10 10;
			
		}
	    .print{
		    font-size:15px;
		    font-family:"宋体";
	    }
	    .print1{
		    font-size:15px;
		    font-family:"宋体";
		    align:right;
	    }
	    .STYLE5 {
			font-size: 18px;
			font-weight: bold;
		}
	</style>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
}
#Layer1 {
	position:absolute;
	width:128px;
	height:24px;
	z-index:1;
	left: 16px;
	top: 18px;
}
.STYLE13 {
	font-size: 12px;
}
-->
</style>
	
	<script type="text/javascript">
		function toggle(targetid,curDivId)
		{
			 var target=document.getElementById(targetid);
			 var curDiv=document.getElementById(curDivId);
			 if (target.style.display=="none")
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
		
		function checkForm()
		{
			if($("#roomName").val()==""){
				Ext.MessageBox.alert('提示','您还未选择要预收款的房间信息');
				return false;
			}
			var value=$("input[name='hod2000PreReceive.prAfter']:checked").val();
	 		if(value==0){
	 		    $("#prAfter").html("否");
	 		}else{
	 		 	$("#prAfter").html("是");
	 		}
	 		$("#prMoney1").html($("#prMoney").val());
	 		$("#prechargeTime1").html($("#prechargeTime").val());
			if(confirm("是否打印收费单?"))
			{
				toggle('div1','div2');
			    window.print();
			}
		}
		
		function onChange()
		{
			//显示还没有被预收款且已经被开户的房间信息，返回ID
			var roomId=window.showModalDialog("${pageContext.request.contextPath}/share/selectRoom2.jsp",window,'dialogHeight=450px;dialogWidth=850px;center=yes;status=no;scroll=no;');
			if(roomId!=null&&roomId!='')
			{
				$("#roomId").val(roomId);
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodPreReceive!getPreCharge.do",
					method:'POST',
					params:{  
		            	roomId:roomId
		        	}, 
					async : false,
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							setCheckedValue('hod2000PreReceive.prAfter',0);
							var data=result.data;//预收款信息
							$("#modulus").val(data.prFactor);
							$("#months").val(data.prMonths);
							$("#roomSize").val(data.roomSize);
							$("#prMoney").val(data.prMoney);
							$("#prechargeTime").val('<%=Utils.dateToStrLong(NetworkTimeUtil.getDate())%>');
							$("#roomName").val(data.roomName);
							$("#clientName").val(data.clientName);
							$("#modulus1").html(data.prFactor);
							$("#months1").html(data.prMonths);
							$("#roomSize1").html(data.roomSize);
							$("#prMoney3").val(data.prMoney);
							$("#roomName1").html(data.roomName);
							$("#clientName1").html(data.clientName);
						}
						else
						{
							alert(result.msg);
						}
					},
					failure : function(res) {
						alert('请求失败!');
					}
				});
			}
		}
		//置零
		function toZero()
		{
			if($("#roomName").val()==""){
				alert("您还未选择房间信息");
				setCheckedValue('hod2000PreReceive.prAfter',0);
				return;
			}
			$("#prMoney2").val($("#prMoney").val());
			$("#prMoney").val(0);
		}
		
		//取消置零
		function restore()
		{
			if($("#roomName").val()==""){
				alert("您还未选择房间信息");
				setCheckedValue('hod2000PreReceive.prAfter',0);
				return;
			}
			$("#prMoney").val($("#prMoney2").val());
		}
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
														<B>当前位置</B>：[预收款管理]-[
														<font color='red'>预收款</font>]
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
																	<a href="javaScript:window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=5';"><div class="returnbtn"></div></a>
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
							<form action="${pageContext.request.contextPath}/hod2000PreReceive!doSave.do" method="post">
								<input type="hidden" id="roomId" readonly="readonly" name="hod2000PreReceive.roomId"/>
								<input type="hidden" id="prMoney2"/>
									<table cellpadding="0" cellspacing="0" border="0" height="300px" width="60%" align="center">
										<tr>
											<td>房间号：</td>
											<td>
												<input type="text" id="roomName" readonly="readonly"/>
												<input type="button" value="选择" onclick="onChange()"/>
											</td>
											<td>用户姓名：</td>
											<td><input type="text" id="clientName" readonly="readonly" name="clientName"/></td>
										</tr>
										<tr>
											<td>供暖月数：</td>
											<td><input type="text" id="months" readonly="readonly" name="hod2000PreReceive.prMonths"/></td>
											<td>系数单价(元/月/平方米)：</td>
											<td><input type="text" id="modulus" readonly="readonly" name="hod2000PreReceive.prFactor"/></td>
										</tr>
										<tr>
											<td>面积数(㎡)：</td>
											<td><input type="text" id="roomSize" readonly="readonly"/></td>
											<td>预收款金额(元)：</td>
											<td><input type="text" id="prMoney" readonly="readonly" name="hod2000PreReceive.prMoney"/></td>
										</tr>
										<tr>
											<td>是否后付费：</td>
											<td>
												<input id="prAfter1" type="radio" name="hod2000PreReceive.prAfter" value="0" checked="checked" onclick="restore()"/>否
												<input id="prAfter2" type="radio" name="hod2000PreReceive.prAfter" value="1" onclick="toZero()"/>是
											</td>
											<td>预收款时间：</td>
											<td><input type="text" id="prechargeTime" name="hod2000PreReceive.prTime" readonly="readonly"/></td>
										</tr>
										<tr>
											<td>操作员：</td>
											<td>
												<input id="prOperator" type="text" name="hod2000PreReceive.prOperator" value="${user.loginName }" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td colspan="4" align="center">
												<input type="submit" value="提交" onclick="return checkForm()" id="tijiao"/>
												<input type="button" value="返回" onclick="javaScript:window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=5';">
											</td>
										</tr>
										<tr>
											<td colspan="4">
												提示：<br>1.预收款金额=面积数*供暖月数*系数单价。<br>2.选择后付费，系统会自动将预收款金额置零。
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
		<div id="div2">
		<table border="1" width="98%"  style="align:center;">
              <tr>
                <td height="32" colspan="9" align="center">
				<div  style="float:left">&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/hodlogin.jpg"  width="76" height="23" /></div>
                <span class="STYLE1">预收款凭据</span></td>
              </tr>
              <tr>
                <td width="12%" align="center" height="28"><span class="STYLE13">用户姓名</span></td>
                <td width="10%" align="center"><span class="STYLE13">房间号</span></td>
                <td width="12%" align="center"><span class="STYLE13">供暖月数</span></td>
                <td width="12%" align="center"><span class="STYLE13">供暖系数</span></td>
                <td width="10%" align="center"><span class="STYLE13">面积数</span></td>
                <td width="14%" align="center"><span class="STYLE13">预收款金额</span></td>
                <td width="10%" align="center"><span class="STYLE13">后付费</span></td>
                <td width="15%" align="center"><span class="STYLE13">预收款时间</span></td>
                <td width="5%" align="center"><span class="STYLE13">操作员</span></td>
              </tr>
              <tr>
                <td align="center" height="28"><span class="STYLE13" id="clientName1"></span></td>
                <td align="center"><span class="STYLE13" id="roomName1"></span></td>
                <td align="center"><span class="STYLE13" id="months1"></span></td>
                <td align="center"><span class="STYLE13" id="modulus1"></span></td>
                <td align="center"><span class="STYLE13" id="roomSize1"></span></td>
                <td align="center"><span class="STYLE13" id="prMoney1"></span>(元)</td>
                <td align="center"><span class="STYLE13" id="prAfter"></span></td>
                <td align="center"><span class="STYLE13" id="prechargeTime1"></span></td>
                <td align="center"><span class="STYLE13" id="prOperator1">${user.loginName }</span></td>
              </tr>
              <tr>
                <td height="30" align="center"><span class="STYLE13">备注：</span></td>
                <td colspan="8" valign="middle"><span class="STYLE13">&nbsp;&nbsp;1.预收款金额=面积数*供暖使用月数*供暖系数。 </span></td>
              </tr>
            </table>
           </div>
  </body>
</html>
