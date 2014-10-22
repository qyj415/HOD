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
	margin:auto;
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
			function piaojudaying(preid,clientName,roomSize,prFactor,prMonths,prMoney,prTime,prOperator,prAfter){
					
					if(confirm("是否打印收费单?"))
					{
						$("#roomName2").html(preid);
						$("#clientName2").html(clientName);
						$("#roomSize2").html(roomSize);
						$("#prFactor2").html(prFactor);
						$("#prMonths2").html(prMonths);
						$("#prMoney2").html(prMoney);
						$("#prTime2").html(prTime);
						$("#prOperator2").html(prOperator);
						if(prAfter==0){
							$("#prAfter2").html("否");
						}else{
							$("#prAfter2").html("是");
						}
						toggle('div1','div2');
						window.print();
					}
			}
			$(function(){
				 $("#preReceiveList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodPreReceive!doSelect.do',
	                datatype: "local",
	                autowidth:true,
	                height:document.documentElement.clientHeight-200,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['地理位置','用户姓名','面积数(㎡)','系数单价(元/月/平方米)','供暖月数','预收款金额(元)','收费时间','操作员','是否后付费','票据补打'],
	                colModel:[
	                    {name:'roomName',index:'roomName', align:"center",sortable:false,width:200},
	                    {name:'clientName',index:'clientName',align:"center",sortable:false},
	                    {name:'roomSize',index:'roomSize',align:"center",sortable:false},
	                    {name:'prFactor',index:'prFactor',align:"center",sortable:false},
	                    {name:'prMonths',index:'prMonths',align:"center",sortable:false},
	                    {name:'prMoney',index:'prMoney',align:"center",sortable:false},
	                    {name:'prTime',index:'prTime',align:"center",sortable:false},
	                    {name:'prOperator',index:'prOperator',align:"center",sortable:false},
	                    {name:'prAfter',index:'prAfter',align:"center",sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==0)
	                				return "否";
	                			if(value==1)
	                				return "是";
	                		}
						},
						{name:'prid',index:'prid',align:"center",width:'120',sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			var preid=rows["roomName"];
	                			var clientName=rows["clientName"];
	                			var roomSize=rows["roomSize"];
	                			var prFactor=rows["prFactor"];
	                			var prMonths=rows["prMonths"];
	                			var prMoney=rows["prMoney"];
	                			var prTime=rows["prTime"];
	                			var prOperator=rows["prOperator"];
	                			var prAfter=rows["prAfter"];
	                		    return '<a href="#" onclick=\'piaojudaying("'+preid+'","'+clientName+'","'+roomSize+'","'+prFactor+'","'+prMonths+'","'+prMoney+'","'+prTime+'","'+prOperator+'","'+prAfter+'")\'  style="color:#f60;">【打印票据】</a>';
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
	                pager: "#preReceiveListPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	             }).navGrid('#preReceiveListPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
	             //未收款统计
	             $("#unPreReceiveList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodPreReceive!doSelect2.do',
	                datatype: "json",
	                width:document.body.clientWidth-15,
	                height:document.documentElement.clientHeight-150,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['地理位置','用户姓名','联系电话','面积数(㎡)','系数单价(元/月/平方米)','供暖月数','未收款金额(元)'],
	                colModel:[
	                    {name:'address',index:'address', align:"center",sortable:false,width:200},
	                    {name:'clientName',index:'clientName',align:"center",sortable:false},
	                    {name:'clientTel',index:'clientTel',align:"center",sortable:false},
	                    {name:'roomSize',index:'roomSize',align:"center",sortable:false},
	                    {name:'prFactor',index:'prFactor',align:"center",sortable:false},
	                    {name:'prMonths',index:'prMonths',align:"center",sortable:false},
	                    {name:'prMoney',index:'prMoney',align:"center",sortable:false}
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#unPreReceiveListPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	             }).navGrid('#unPreReceiveListPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
	             //统计汇总
	             Ext.MessageBox.wait('加载报表中，请稍后......', '请稍后');
	             Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodPreReceive!doSelect3.do",
					method:'POST',
					async : false,
					timeout : 180000,
					success : function(res) {
						Ext.MessageBox.hide();
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							$("#img1").html("<img src=<%= jreeChartPath %>"+result.fileName+" width=400 height=300 border=0>");
							$("#img2").html("<img src=<%= jreeChartPath %>"+result.fileName1+" width=400 height=300 border=0>");
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
			//打印预收款信息
			function printPreReceiveInfo(){
				var printForm = document.createElement('form');
				printForm.method = 'post';
				printForm.id = 'printForm';
				printForm.target = '_blank';
				if(tabStatus == 0){
					//已收款明细
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
					c.name = 'prAfter';
					c.value = $("#prAfter").val();
					
					var d = document.createElement('input');
					d.type = 'hidden';
					d.name = 'clientName';
					d.value = clientName;
					
					var e = document.createElement('input');
					e.type = 'hidden';
					e.name = 'roperator';
					e.value = $('#roperator').val();
					
					printForm.appendChild(a);
					printForm.appendChild(b);
					printForm.appendChild(c);
					printForm.appendChild(d);
					printForm.appendChild(e);
					printForm.action = '${pageContext.request.contextPath}/hod2000PreReceive!getPreReceivePrint.do?tab=1';
				}else if(tabStatus == 1){
					//未收款明细
					printForm.action = '${pageContext.request.contextPath}/hod2000PreReceive!getUnPreReceivePrint.do';
				}
				document.body.appendChild(printForm);
				printForm.submit();
				document.body.removeChild(printForm);	
			}
			//导出execl
			function exportToExcel(){
				if(tabStatus==0){ 
					location.href="${pageContext.request.contextPath}/hod2000PreReceive3!exportToExcel2.do";
				}
				if(tabStatus==1){ 
					location.href="${pageContext.request.contextPath}/hod2000PreReceive2!exportToExcel.do";
				}
			}
		</script>
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
															<B>当前位置</B>：[预收款管理]-[
															<font color='red'>统计分析</font>]
														</td>
													</tr>
												</table>
											</td>
											<td width="54%">
												<table border="0" align="right" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="60">
														<td width="90">
															<table width="99%" border="0" cellpadding="0"
																cellspacing="0">
																<tr>
																	<td class="STYLE2">
																		<a href="javascript:exportToExcel()">
																		<div class="exportbtn" id="export">
																		</div>
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
																		<a href="javascript:printPreReceiveInfo();"><div
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
																			href="javaScript:window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=5';"><div
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
										<%@include file="/inc/prereceiveSearch.jsp"%>
										<table id="preReceiveList"></table>
										<div id="preReceiveListPage"></div>
									</div>
									<div id="read" style="background-color: white; width: 100%;">
										<table id="unPreReceiveList"></table>
										<div id="unPreReceiveListPage"></div>
									</div>
									<div id="total" style="background-color: white; width: 100%;">
										<table border="0" cellspacing="0" cellpadding="0"
											style="width: 80%; height: 200px;" align="center">
											<tr>
												<td id="img1"></td>
												<td id="img2"></td>
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
						<span class="STYLE1">预收款凭据</span>
					</td>
				</tr>
				<tr>
					<td width="12%" height="28" align="center">
						<span class="STYLE13">用户姓名</span>
					</td>
					<td width="12%" align="center">
						<span class="STYLE13">供暖月数</span>
					</td>
					<td width="12%" align="center">
						<span class="STYLE13">系数单价(元/月/平方米)</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">面积数（㎡）</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">预收款金额</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">是否后付费</span>
					</td>
					<td width="13%" align="center">
						<span class="STYLE13">预收款时间</span>
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
						<span class="STYLE13" id="prMonths2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="prFactor2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="roomSize2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="prMoney2"></span>(元)
					</td>
					<td align="center">
						<span class="STYLE13" id="prAfter2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="prTime2"></span>
					</td>
					<td align="center">
						<span class="STYLE13" id="prOperator2"></span>
					</td>
				</tr>
				<tr>
					<td height="30" align="center">
						<span class="STYLE13">地理位置：</span><span class="STYLE13">&nbsp;&nbsp;</span>
					</td>
					<td height="30" colspan="7" align="center">
						<span class="STYLE13" id="roomName2"></span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
