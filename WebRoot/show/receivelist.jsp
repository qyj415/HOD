<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String jreeChartPath = path + "/servlet/DisplayChart?filename=";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>统计分析</title>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/common/tab/show.css"></link>
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css"
			type="text/css"></link>
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css"
			type="text/css"></link>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/util.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
.sp1 {
	background-image:
	url("${pageContext.request.contextPath}/images/btn2.jpg");
	height: 30px;
	width: 180px;
	color: white;
	cursor: pointer;
	display: inline-block;
	line-height: 30px;
	text-align: center;
}

#div1 {
	display: block;
}

#div2 {
	display: none;
	font-family: "宋体";
	margin: 10 10 10 10;
}
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
	margin: auto;
}

.sp2 {
	background-image:
		url("${pageContext.request.contextPath}/images/btn.jpg");
	color: white;
	height: 30px;
	display: inline-block;
	width: 180px;
	cursor: pointer;
	line-height: 30px;
	text-align: center;
	background-color: white;
}

.sp3 {
	background-image:
		url("${pageContext.request.contextPath}/images/btn.jpg");
	color: white;
	height: 30px;
	display: inline-block;
	width: 180px;
	cursor: pointer;
	line-height: 30px;
	text-align: center;
	background-color: white;
}

#history {
	border: 1px solid #2EB4B9;
	width: 100%;
}

#read {
	border: 1px solid #2EB4B9;
	display: none;
}

#total {
	border: 1px solid #2EB4B9;
	display: none;
	height: 600px;
	overflow: auto;
}

form {
	margin: 0px;
}
</style>
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
		<script type="text/javascript">
			var tabStatus = 0;//tab状态 0：已收款明细 1：未收款明细
			function setTab(a,b,c,d,e,f){
			     if(a == "history"){
			     	document.getElementById('print').style.display='block';
			     	document.getElementById('export').style.display='block';
				  	tabStatus = 0;
				 }else if(a== "read"){
				 	document.getElementById('print').style.display='block';
				 	document.getElementById('export').style.display='block';
					tabStatus = 1;
				 }else{
				 	document.getElementById('print').style.display='none';
				 	document.getElementById('export').style.display='none';
				 }
			  	document.getElementById(a).style.display='inline-block';
			 	document.getElementById(b).style.display='none';
			  	document.getElementById(c).style.display='none';
			  	document.getElementById(d).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn2.jpg')";
			  	document.getElementById(e).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			  	document.getElementById(f).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			}
			function piaojudaying(address,clientName,prMoney,returnMoney,fillMoney,rmoney,rtype,rdate,roperator){
					
					if(confirm("是否打印收费单?"))
					{
						$("#address2").html(address);
						$("#clientName2").html(clientName);
						$("#prMoney2").html(prMoney);
						$("#returnMoney2").html(returnMoney);
						$("#fillMoney2").html(fillMoney);
						$("#rmoney2").html(rmoney);
						if(rtype==1){
							$("#rtype2").html("多退少不补");
						}else{
							$("#rtype2").html("多退少补");
						}
						$("#rdate2").html(rdate);
						$("#roperator2").html(roperator);
						
						toggle('div1','div2');
						window.print();
					}
			}
			$(function(){
					 $("#receiveList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodReceive!doSelect.do',
	                datatype: "local",
	                autowidth:true,
	                height:document.documentElement.clientHeight-200,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['地理位置','用户姓名','预付款金额','退款金额','补交金额','应交金额','收费方案','收费时间','操作员','票据补打'],
	                colModel:[
	                    {name:'address',index:'address', align:"center",sortable:false,width:200},
	                    {name:'clientName',index:'clientName',align:"center",sortable:false},
	                    {name:'prMoney',index:'prMoney',align:"center",sortable:false},
	                    {name:'returnMoney',index:'returnMoney',align:"center",sortable:false},
	                    {name:'fillMoney',index:'fillMoney',align:"center",sortable:false},
	                    {name:'rmoney',index:'rmoney',align:"center",sortable:false},
	                    {name:'rtype',index:'rtype',align:"center",sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==1)
	                				return "多退少不补";
	                			if(value==2)
	                				return "多退少补";
	                		}
						},
	                    {name:'rdate',index:'rdate',align:"center",sortable:false},
	                    {name:'roperator',index:'roperator',align:"center",sortable:false},
	                    {name:'rid',index:'rid',align:"center",sortable:false,
		                    formatter: function (value, grid, rows, state) 
		                		{ 
		                			var address=rows["address"];
		                			var clientName=rows["clientName"];
		                			var prMoney=rows["prMoney"];
		                			var returnMoney=rows["returnMoney"];
		                			var fillMoney=rows["fillMoney"];
		                			var rmoney=rows["rmoney"];
		                			var rtype=rows["rtype"];
		                			var rdate=rows["rdate"];
		                			var roperator=rows["roperator"];
		                		    return '<a href="#" onclick=\'piaojudaying("'+address+'","'+clientName+'","'+prMoney+'","'+returnMoney+'","'+fillMoney+'","'+rmoney+'","'+rtype+'","'+rdate+'","'+roperator+'")\'  style="color:#f60;">【打印票据】</a>';
		                		}
	                    }
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#receiveListPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	             }).navGrid('#receiveListPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
	              //未收款统计
	             $("#unReceiveList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodReceive!doSelect2.do',
	                datatype: "json",
	                 width:document.body.clientWidth-15,
	                height:document.documentElement.clientHeight-150,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [1,5,10,20,30,40,50,100],
	                colNames:['地理位置','用户姓名','联系电话','表号','预付款金额','应交金额'],
	                colModel:[
	                    {name:'address',index:'address', align:"center",sortable:false,width:200},
	                    {name:'clientName',index:'clientName',align:"center",sortable:false},
	                     {name:'clientTel',index:'clientTel',align:"center",sortable:false},
	                    {name:'meterName',index:'meterName',align:"center",sortable:false},
	                    {name:'preMoney',index:'preMoney',align:"center",sortable:false},
	                    {name:'receiveMoney',index:'receiveMoney',align:"center",sortable:false}
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#unReceiveListPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	             }).navGrid('#unReceiveListPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
	             //统计汇总
	             Ext.MessageBox.wait('加载报表中，请稍后......', '请稍后');
	             Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodReceive!doSelect3.do",
					method:'POST',
					async : false,
					timeout : 180000,
					success : function(res) {
						Ext.MessageBox.hide();
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							$("#img1").html("<img src=<%= jreeChartPath %>"+result.fileName+" width=400 height=300 border=0>");
							$("#preMoney").html(result.preMoney);
							$("#totalMoney").html(result.totalMoney);
							$("#returnMoney").html(result.returnMoney);
							$("#fillMoney").html(result.fillMoney);
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
			});
			//打印收款信息
			function printReceiveInfo(){
				var printForm = document.createElement('form');
				printForm.method = 'post';
				printForm.id = 'printForm';
				printForm.target = '_blank';
				//已收款明细
				if(tabStatus == 0){
					var startTime=$("#startTime").val();
					var endTime=$("#endTime").val();
					var clientName=$("#clientName").val();
					if(0==startTime.length&&0==endTime.length)
					{
						Ext.MessageBox.alert('提示','开始时间和结束时间不能同时为空!');
						return;
					}
					var a = document.createElement('input');
					a.type = 'hidden';
					a.name = 'startTime';
					a.value = startTime;
					
					var b = document.createElement('input');
					b.type = 'hidden';
					b.name = 'endTime';
					b.value = endTime;
					
					var c = document.createElement('input');
					c.type = 'hidden';
					c.name = 'clientName';
					c.value = clientName;
					
					var d = document.createElement('input');
					d.type = 'hidden';
					d.name = 'roperator';
					d.value = $('#roperator').val();
					
					printForm.appendChild(a);
					printForm.appendChild(b);
					printForm.appendChild(c);
					printForm.appendChild(d);
					printForm.action = '${pageContext.request.contextPath}/hod2000Receive!getReceivePrint.do';
				}
				//未收款明细
				else if(tabStatus == 1){
					printForm.action = '${pageContext.request.contextPath}/hod2000Receive!getUnReceivePrint.do';
				}
				document.body.appendChild(printForm);
				printForm.submit();
				document.body.removeChild(printForm);	
			}
			//导出execl
			function exportToExcel(){
				if(tabStatus==0){ 
					location.href="${pageContext.request.contextPath}/hod2000Receive3!exportToExcel2.do";
				}
				if(tabStatus==1){ 
					location.href="${pageContext.request.contextPath}/hod2000Receive2!exportToExcel.do";
				}
			}
		</script>
	</head>

	<body onload="loads('total',100)">
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
															<font color='red'>统计分析</font>]
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
																		<a href="javascript:exportToExcel()"><div
																				class="exportbtn" id="export"></div>
																		</a>
																	</td>
																</tr>
															</table>
														</td>
														<td width="90">
															<table width="99%" border="0" cellpadding="0"
																cellspacing="0">
																<tr>
																	<td class="STYLE2">
																		<a href="javascript:printReceiveInfo();"><div
																				class="printbtn" id="print"></div>
																		</a>
																	</td>
																</tr>
															</table>
														</td>
														<td width="90">
															<table width="99%" border="0" cellpadding="0"
																cellspacing="0">
																<tr>
																	<td class="STYLE2">
																		<a
																			href="javaScript:window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=4';"><div
																				class="returnbtn"></div>
																		</a>
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
																				class="homebtn"></div>
																		</a>
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
									<span id="sp1"
										onclick="setTab('history','read','total','sp1','sp2','sp3')"
										class="sp1">已收款明细</span>
									<span id="sp2"
										onclick="setTab('read','history','total','sp2','sp1','sp3')"
										class="sp2">未收款明细</span>
									<span id="sp3"
										onclick="setTab('total','history','read','sp3','sp1','sp2')"
										class="sp3">统计汇总</span>
									<div></div>
									<div id="history" style="background-color: white; width: 100%;">
										<%@include file="/inc/receiveSearch.jsp"%>
										<table id="receiveList"></table>
										<div id="receiveListPage"></div>
									</div>
									<div id="read" style="background-color: white; width: 100%;">
										<table id="unReceiveList"></table>
										<div id="unReceiveListPage"></div>
									</div>
									<div id="total" style="background-color: white; width: 100%;">
										<table border="0" cellspacing="0" cellpadding="0"
											style="width: 50%; height: 200px; text-align: center;"
											align="center">
											<tr>
												<td colspan="4" id="img1"></td>
											</tr>
											<tr height="30">
												<td>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td>
													预收款总金额：
													<span id="preMoney"></span>元
												</td>
												<td>
													应交总金额：
													<span id="totalMoney"></span>元
												</td>
												<td>
													退款金额：
													<span id="returnMoney"></span>元
												</td>
												<td>
													补交金额：
													<span id="fillMoney"></span>元
												</td>
											</tr>
										</table>
									</div>
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
		<div id="div2" style="align: center;">
			<table width="95%" border="1" align="center" style="align: center;">
				<tr>
					<td height="32" colspan="8" align="center" valign="bottom">
						<div style="float: left">
							&nbsp;&nbsp;
							<img src="${pageContext.request.contextPath}/images/hodlogin.jpg"
								width="76" height="23" />
						</div>
						<span class="STYLE1">收费凭据</span>
					</td>
				</tr>
				<tr>
					<td width="12%" height="28" align="center">
						<span class="STYLE13">用户姓名</span>
					</td>
					<td width="12%" align="center">
						<span class="STYLE13">预付款金额</span>
					</td>
					<td width="12%" align="center">
						<span class="STYLE13">退款金额</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">补交金额</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">应交金额</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">收费方案</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">收费时间</span>
					</td>
					<td width="12%" align="center">
						<span class="STYLE13">操作员</span>
					</td>
				</tr>
				<tr>
					<td height="28" align="center">
						<span class="STYLE13" id="clientName2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="prMoney2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="returnMoney2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="fillMoney2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="rmoney2"></span>(元)
					</td>
					<td align="center">
						<span class="STYLE13" id="rtype2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="rdate2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="roperator2"></span>
					</td>
				</tr>
				<tr>
					<td height="30" align="center">
						<span class="STYLE13">地理位置：</span><span class="STYLE13">&nbsp;&nbsp;</span>
					</td>
					<td height="30" colspan="7" align="center">
						<span class="STYLE13" id="address2"></span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
