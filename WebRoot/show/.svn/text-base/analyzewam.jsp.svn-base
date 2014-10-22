<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>报警信息</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<style type="text/css">
			.sp1{
				background-image: url("${pageContext.request.contextPath}/images/btn2.jpg");
				height: 30px;
				width:180px;
				color: white;
				cursor: pointer;
				display:inline-block;
				line-height: 30px;
				text-align: center;
			}
			.sp2{
				background-image: url("${pageContext.request.contextPath}/images/btn.jpg");
				color: white;
				height: 30px;
				display:inline-block;
				width:180px;
				cursor: pointer;
				line-height: 30px;
				text-align: center;
				background-color: white;
			}
			#meter{
				border:1px solid #2EB4B9;
				float:left;
				width: 100%;
			}
			#terminal{
				border: 1px solid #2EB4B9;
				float:left;
				display: none;
				width: 100%;
			}
		</style>
		<script type="text/javascript">
			var tabStatus = 0;//tab状态 0：表计 1：集中器
			function setTab(m,n,s,p){
			  if(m == "meter"){
			  	tabStatus = 0;
			  }else if(m == "terminal"){
				tabStatus = 1;
			  }
			  document.getElementById(m).style.display='inline-block';
			  document.getElementById(n).style.display='none';
			  document.getElementById(s).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn2.jpg')";
			  document.getElementById(p).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			}
			
			$(function(){
				 $("#meterWarm").jqGrid({
	                url: '${pageContext.request.contextPath}/hod2000MeterInfoWarm!doSelectMeterWarm.do',
	                datatype: "json",
	                autowidth:true,
	                height:document.documentElement.clientHeight-145,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                //colNames:['表号','地址','阀门状态','电池状态','在线状态'],
	                colNames:['表号','地址','电池状态','在线状态','内存状态','流量传感器状态','供水温度传感器状态','回水温度传感器状态'],
	                colModel:[
	                    {name:'meterName',index:'meterName', align:"center",sortable:false},
	                    {name:'address',index:'address',align:"center",sortable:false,width:200},
	                    /*{name:'valveStatus',index:'valveStatus',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/open.png'/>";
		                    	if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/close.png'/>";
	                    		if(cellvalue==2)
	                    			return "<img src='${pageContext.request.contextPath}/images/status/warm.png'/>";
		                    }
	                    },*/
	                    {name:'batteryStatus',index:'batteryStatus',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/yes.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/no.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'isOnline',index:'isOnline',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/online.png'/>";
		                    	else if(cellvalue==2)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/notonline.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'eepromStatus',index:'eepromStatus',align:"center",sortable:false,width:100,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'flowsensorStatus',index:'flowsensorStatus',align:"center",sortable:false,width:200,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'tepdownStatus',index:'tepdownStatus',align:"center",sortable:false,width:200,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'tepupStatus',index:'tepupStatus',align:"center",sortable:false,width:200,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
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
	                pager: "#meterWarmPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	            }).navGrid('#meterWarmPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
	            //集中器报警
	             $("#terminalWarm").jqGrid({
	                url: '${pageContext.request.contextPath}/hodConcentrator!concentratorWarm.do',
	                datatype: "json",
	                width:document.body.clientWidth-18,
	                height:document.documentElement.clientHeight-145,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['集中器编号','安装地址','内存状态','通讯口1','通讯口2','通讯口3','在线状态','GPRS信号'],
	                colModel:[
	                    {name:'conNum',index:'conNum', align:"center",sortable:false},
	                    {name:'conAddress',index:'conAddress',align:"center",sortable:false},
	                    {name:'conFlashStatus',index:'conFlashStatus',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'conCom1Status',index:'conCom1Status',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'conCom2Status',index:'conCom2Status',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'conCom3Status',index:'conCom3Status',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'conIsonline',index:'conIsonline',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/online.png'/>";
		                    	else if(cellvalue==2)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/notonline.png'/>";
		                    	else 
		                    		return "";
		                    }
	                    },
	                    {name:'conStrong',index:'conStrong',align:"center",sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/0.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/1.png'/>";
		                    	else if(cellvalue==2)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/2.png'/>";
		                    	else if(cellvalue==3)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/3.png'/>";
		                    	else 
		                    		return "";
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
	                pager: "#terminalWarmPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	            }).navGrid('#terminalWarmPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
			});
			
			window.setInterval("refreshTab()",60000);
			//表格刷新
			function refreshTab()
			{
				$("#meterWarm").jqGrid('setGridParam',{  
			        datatype:'json',  
			        page:1  
			    }).trigger("reloadGrid");
			    $("#terminalWarm").jqGrid('setGridParam',{  
			        datatype:'json',  
			        page:1  
			    }).trigger("reloadGrid");
			}
			
			//打印报警信息
			function printaa(){
					var printForm = document.createElement('form');
					printForm.method = 'post';
					if(tabStatus == 0){
						//表计报警
						printForm.action = '${pageContext.request.contextPath}/hod2000MeterInfoHistory!meterWarmPrint.do?tab=1';
					}else{
						//集中器报警
						printForm.action = '${pageContext.request.contextPath}/hod2000Concentrator!concentratorWarmPrint.do';
					}
					printForm.id = 'printForm';
					printForm.target = '_blank';
					
					document.body.appendChild(printForm);
					printForm.submit();
					document.body.removeChild(printForm);	
			}
			
			//进入地图
			function toMap()
			{
				if(tabStatus==0)
				{
					window.location.href='${pageContext.request.contextPath}/map/AlarmMap.jsp';
				}
				else
				{
					window.location.href='${pageContext.request.contextPath}/map/terminalAlarmMap.jsp';
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
														<B>当前位置</B>：[<font color='red'>报警信息</font>]
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
																	<a href="javascript:printaa();"><div class="printbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="#" onclick="toMap()"><div class="mapbtn"></div></a>
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
								<span id="sp1" onclick="setTab('meter','terminal','sp1','sp2')" class="sp1">表计报警信息</span>
								<span id="sp2" onclick="setTab('terminal','meter','sp2','sp1')" class="sp2">集中器报警信息</span>
								<div id="meter">
									<table id="meterWarm"></table>
        							<div id="meterWarmPage"></div>
								</div>
								<div id="terminal">
									<table id="terminalWarm"></table>
        							<div id="terminalWarmPage"></div>
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
							<td style="text-align: right;">
								备注：
								<!-- <img src="${pageContext.request.contextPath}/images/status/open.png"/><span class="sps">阀开</span>
								<img src="${pageContext.request.contextPath}/images/status/warm.png"/><span class="sps">异常</span>
								<img src="${pageContext.request.contextPath}/images/status/close.png"/><span class="sps">阀关</span> -->
								<img src="${pageContext.request.contextPath}/images/status/yes.png"/><span class="sps">正常</span>
								<img src="${pageContext.request.contextPath}/images/status/no.png"/><span class="sps">欠压</span>
								<img src="${pageContext.request.contextPath}/images/status/normal.png"/><span class="sps">正常</span>
								<img src="${pageContext.request.contextPath}/images/status/abnormal.png"/><span class="sps">错误</span>
								<img src="${pageContext.request.contextPath}/images/status/online.png"/><span class="sps">在线</span>
								<img src="${pageContext.request.contextPath}/images/status/notonline.png"/><span class="sps">离线</span>
								<img src="${pageContext.request.contextPath}/images/status/0.png"/><span class="sps">无信号</span>
								<img src="${pageContext.request.contextPath}/images/status/1.png"/><span class="sps">弱</span>
								<img src="${pageContext.request.contextPath}/images/status/2.png"/><span class="sps">一般</span>
								<img src="${pageContext.request.contextPath}/images/status/3.png"/><span class="sps">强</span>
							</td>
							<td width="16" class="tab_20">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

