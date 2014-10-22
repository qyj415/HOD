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
    
    <title>收费</title>
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
	<script language="JavaScript" type="text/JavaScript">
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
	</script>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
}
.STYLE2 {font-size: 12px}

-->
</style>
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
	</style>
	<script type="text/javascript">
		function checkForm()
		{
			if($("#roomName").val()==""){
				Ext.MessageBox.alert('提示',"您还未选择房间信息!");
				return false;
			}
			if(confirm("是否打印收费单?"))
			{
				toggle('div1','div2');
				window.print();
			}
		}
		function onChange()
		{
			//显示还没有被预收款且已经被开户的房间信息，返回ID
			var roomId=window.showModalDialog("${pageContext.request.contextPath}/share/selectRoom3.jsp",window,'dialogHeight=450px;dialogWidth=850px;center=yes;status=no;scroll=no;');
			if(roomId!=null&&roomId!='')
			{
				$("#roomId").val(roomId);
				//根据id查询付款信息
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodReceive!getCharge.do",
					method:'POST',
					params:{  
		            	roomId:roomId
		        	}, 
					async : false,
					timeout : 5000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							var data=result.data;
							$("#preMoney").val(data.preMoney);
							$("#rMoney").val(data.monetyToPay);
							$("#fillMoney").val(data.fillMoney);
							$("#returnMoney").val(data.returnMoney);
							$("#rdate").val('<%=Utils.dateToStrLong(NetworkTimeUtil.getDate())%>');
							$("#rtype").val(data.reType);
							if(data.reType==1)
							{
								$("#rTypeName").val('多退少不补');
								$("#type3").html('多退少不补');
							}
							else
							{
								$("#rTypeName").val('多退少补');
								$("#type3").html('多退少补');
							}
							if(data.types==1)
							{
								$("#types").html('(后付费用户)');
							}
							else
							{
								$("#types").html('');
							}
							//setCheckedValue('rtype',data.reType);
							$("#roomName").val(data.roomName);
							$("#clientName").val(data.clientName);
							$("#roomSize").val(data.roomSize);
							$("#meterName").val(data.meterName);
							$("#priceType").val(data.priceType);
							$("#startPower").val(data.startPower);
							$("#endPower").val(data.endPower);
							$("#pdPower").val(data.pdPower);
							
							$("#preMoney2").html(data.preMoney);
							$("#rMoney2").html(data.monetyToPay);
							$("#fillMoney2").html(data.fillMoney);
							$("#returnMoney2").html(data.returnMoney);
							$("#rdate2").html('<%=Utils.dateToStrLong(NetworkTimeUtil.getDate())%>');
							$("#roomName2").html(data.roomName);
							$("#clientName2").html(data.clientName);
							$("#roomSize2").html(data.roomSize);
							$("#meterName2").html(data.meterName);
							$("#priceType2").html(data.priceType);
							$("#startPower2").html(data.startPower);
							$("#endPower2").html(data.endPower);
							$("#pdPower2").html(data.pdPower);
						}
						else
						{
							Ext.MessageBox.alert('提示',result.msg);
						}
					},
					failure : function(res) {
						Ext.MessageBox.alert('提示','请求失败!');
					}
				})
			}
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
														<B>当前位置</B>：[收费管理]-[
														<font color='red'>收费</font>]
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
																	<a href="javaScript:window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=4';"><div class="returnbtn"></div></a>
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
							<!-- 可以写form -->
							<form action="${pageContext.request.contextPath}/hod2000Receive!doSave.do" method="post">
							<input type="hidden" name="hod2000Receive.rtype" id="rtype">
							<input type="hidden" id="roomId" name="hod2000Receive.roomId"/>
									<table cellpadding="0" cellspacing="0" border="0" height="450px" width="60%" align="center">
										<tr>
											<td>房间号：</td>
											<td>
												<input type="text" id="roomName" readonly="readonly" name="roomName"/>
												<input type="button" value="选择" onclick="onChange()"/>
											</td>
											<td>用户姓名：</td>
											<td>
												<input type="text" id="clientName" readonly="readonly" name="clientName"/>
											</td>
										</tr>
										<tr>
											<td>面积数(㎡)：</td>
											<td>
												<input type="text" id="roomSize" readonly="readonly" name="roomSize"/>
											</td>
											<td>表号：</td>
											<td>
												<input type="text" id="meterName" readonly="readonly" name="meterName"/>
											</td>
										</tr>
										<tr>
											<td>供暖开始累计热量(KWh)：</td>
											<td>
												<input type="text" id="startPower" readonly="readonly" name="startPower"/>
											</td>
											<td>供暖结束累计热量(KWh)：</td>
											<td>
												<input type="text" id="endPower" readonly="readonly" name="endPower"/>
											</td>
										</tr>
										<tr>
											<td>耗热量(KWh)：</td>
											<td>
												<input type="text" id="pdPower" readonly="readonly" name="pdPower"/>
											</td>
											<td>价格方案：</td>
											<td>
												<input type="text" id="priceType" readonly="readonly" name="priceType"/>
											</td>
										</tr>
										<tr>
											<td>预付款金额(元)：</td>
											<td><input type="text" id="preMoney" readonly="readonly" name="hod2000Receive.prMoney"/></td>
											<td>退款金额(元)：</td>
											<td><input type="text" id="returnMoney" readonly="readonly" name="hod2000Receive.returnMoney"/></td>
										</tr>
										<tr>
											<td>补交金额(元)：</td>
											<td><input type="text" id="fillMoney" readonly="readonly" name="hod2000Receive.fillMoney"/></td>
											<td>应交金额(元)：</td>
											<td><input type="text" id="rMoney" name="hod2000Receive.rmoney" readonly="readonly"/></td>
										</tr>
										<tr>
											<td>收费方式：</td>
											<td>
												<input type="text" readonly="readonly" id="rTypeName"/><span style="color: red;" id="types"></span>
											</td>
											<td>收费时间：</td>
											<td><input type="text" id="rdate" name="hod2000Receive.rdate" readonly="readonly"/></td>
											
										</tr>
										<tr>
											<td>操作员：</td>
											<td>
												<input id="roperator" type="text" name="hod2000Receive.roperator" value="${user.loginName }" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td colspan="4" align="center">
												<input type="submit" value="提交" onclick="return checkForm()" id="tijiao"/>
												<input type="reset" value="重置"/>
											</td>
										</tr>
										<tr>
											<td colspan="4">
												提示：<br>一.定额价格=基础费+能量费：<br>1.基础费=面积数×基础费单价×时间（月）。<br>2.能量费=耗热量×用热单价。<br><br>
														 二.阶梯价格=基础费+阶梯能量费：<br>1.基础费=面积数×基础费单价×时间（月）。<br>2.阶梯方案一：阶梯能量费=耗热量×阶梯单价。<br>3.阶梯方案二：阶梯能量费=Σ（每一阶耗热量×该阶段阶梯单价）。<br><br>
														 三.进行收费时请确定您已经设置了收费参数。<br><br>
														 四.若是在预收款中用户设置为后付费，收费方式将强制设置为多退少补。
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
                <td height="30" colspan="8" align="center" valign="middle">
				<div style="float:left">&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/hodlogin.jpg" width="76" height="23"/></div>
                <span class="STYLE1">收费凭据</span></td>
              </tr>
              <tr>
                <td width="13%" align="center" height="22"><span class="STYLE2">用户姓名</span></td>
                <td colspan="2" align="center" ><span class="STYLE2" id="clientName2" readonly="readonly" name="clientName" ></span></td>
                <td width="13%" align="center"><span class="STYLE2">房间号</span></td>
                <td colspan="2" align="center"><span class="STYLE2" id="roomName2" readonly="readonly"></span></td>
                <td width="10%" align="center"><span class="STYLE2">表号</span></td>
                <td width="21%" align="center"><span class="STYLE2" id="meterName2"></span></td>
              </tr>
              <tr>
                <td align="center" height="22"><span class="STYLE2">供暖开始累计热量</span></td>
                <td colspan="2" align="center"><span class="STYLE2" id="startPower2"></span></td>
                <td align="center"><span class="STYLE2">供暖结束累计热量</span></td>
                <td colspan="2" align="center"><span class="STYLE2" id="endPower2"></span></td>
                <td align="center"><span class="STYLE2">耗热量</span></td>
                <td align="center"><span class="STYLE2" id="pdPower2"></span></td>
              </tr>
              <tr>
                <td align="center" height="22"><span class="STYLE2">收费时间</span></td>
                <td align="left">&nbsp;&nbsp;<span class="STYLE2" id="rdate2"></span></td>
                <td align="center" height="22"><span class="STYLE2">操作员</span></td>
                <td colspan="6" align="left">&nbsp;&nbsp;<span class="STYLE2" id="roperator1">${user.loginName }</span></td>
              </tr>
              <tr>
                <td align="center" height="22"><span class="STYLE2">收费方式</span></td>
                <td width="13%" align="center"><span class="STYLE2">预付款金额</span></td>
                <td width="10%" align="center"><span class="STYLE2">补交金额</span></td>
                <td align="center"><span class="STYLE2">退款金额</span></td>
                <td width="10%" align="center"><span class="STYLE2">应交金额</span></td>
                <td colspan="2" align="center"><span class="STYLE2">面积数</span></td>
                <td align="center" class="STYLE2">价格方案</td>
              </tr>
              <tr>
                <td align="center" height="22"><span class="STYLE2" id="type3"></span></td>
                <td align="center"><span class="STYLE2" id="preMoney2"></span>(元)</td>
                <td align="center"><span class="STYLE2" id="fillMoney2" ></span>(元)</td>
                <td align="center"><span class="STYLE2" id="returnMoney2"></span>(元)</td>
                <td align="center"><span class="STYLE2" id="rMoney2"></span>(元)</td>
                <td colspan="2" align="center"><span  id="roomSize2"></span></td>
                <td align="center" class="STYLE2" id="priceType2"></td>
              </tr>
              <tr>
                <td height="30" align="center"><span class="STYLE2">备注：</span></td>
                <td colspan="7" valign="middle"><span class="STYLE2">一.1.定额价格=基础费+能量费；2.基础费=面积数×基础费单价×时间（月）；3.能量费=耗热量×用热单价。<br />
  二.1.阶梯价格=基础费+阶梯能量费；2.基础费d=面积数×基础费单价×时间（月）；3.阶梯方案一.阶梯能量费=耗热量×阶梯单价；4.阶梯方案二.阶梯能量费=Σ（每一阶耗热量*该阶段阶梯单价） 。</span></td>
              </tr>
            </table>
	</div>
  </body>
</html>
