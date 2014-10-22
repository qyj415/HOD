<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 1.0 Transitional//EN">
<html>
	<head>
		<title>历史数据</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-basex.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
		<style type="text/css">
			.sp1{
				background-image: url("${pageContext.request.contextPath}/images/btn2.jpg");
				width: 78px;
				height: 30px;
				color: white;
				display:inline-block;
				cursor: pointer;
				line-height: 30px;
				text-align: center;
			}
			.sp2{
				background-image: url("${pageContext.request.contextPath}/images/btn.jpg");
				width: 78px;
				color: white;
				height: 30px;
				display:inline-block;
				cursor: pointer;
				line-height: 30px;
				text-align: center;
				background-color: white;
			}
			#history{
				border: 1px solid #2EB4B9;
				width: 100%;
			}
			#free{
				border: 1px solid #2EB4B9;
				display: none;
				width: 100%;
			}
			FORM{
				PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px;
			}
			
		</style>
		<script type="text/javascript">
			var tabStatus = 0;//tab状态 0：历史数据 1：月冻结数据
			
			function setTab(m,n,s,p){
			    document.getElementById(m).style.display='inline-block';
			    document.getElementById(n).style.display='none';
			    document.getElementById(s).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn2.jpg')";
			    document.getElementById(p).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			    if(m == "history"){
					tabStatus = 0;
					document.getElementById('meterPositionName').focus();
				}else if(m == "free"){
					tabStatus = 1;
					document.getElementById('meterPositionName2').focus();
				}
			}
			
			function printaa(){
					var printForm = document.createElement('form');
					printForm.method = 'post';
					//打印历史数据
					if(tabStatus == 0){
						var meterId=$("#meterId").val();
						if(meterId=='')
						{
							Ext.MessageBox.alert('提示','请输入要打印的表号!');
							return;
						}
						var meterId=$("#meterId").val();
						var meterPositionName=$("#meterPositionName").val();
						var startTime=$("#startTime").val();
						var endTime=$("#endTime").val();
						printForm.action = '${pageContext.request.contextPath}/hod2000MeterInfoHistory!historyPrint.do';
					}
					//打印月冻结数据
					else{
						var meterId=$("#meterId2").val();
						if(meterId=='')
						{
							Ext.MessageBox.alert('提示','请输入要打印的表号!');
							return;
						}
						var meterId=$("#meterId2").val();
						var startTime=$("#startTime2").val();
						var endTime=$("#endTime2").val();
						var meterPositionName=$("#meterPositionName2").val();
						printForm.action = '${pageContext.request.contextPath}/hod2000MeterInfoHistory!historyFreePrint.do';
					}
					printForm.id = 'printForm';
					printForm.target = '_blank';
					
					var a = document.createElement('input');
					a.type = 'hidden';
					a.name = 'meterName';
					a.value = meterId;
					
					var b = document.createElement('input');
					b.type = 'hidden';
					b.name = 'meterPositionName';
					b.value = meterPositionName;
					
					var c = document.createElement('input');
					c.type = 'hidden';
					c.name = 'startTime';
					c.value = startTime;
					
					var d = document.createElement('input');
					d.type = 'hidden';
					d.name = 'endTime';
					d.value = endTime;
					
					printForm.appendChild(a);
					printForm.appendChild(b);
					printForm.appendChild(c);
					printForm.appendChild(d);
					
					document.body.appendChild(printForm);
					printForm.submit();
					document.body.removeChild(printForm);
			}
			
			$(function(){
	             $("#grid_id").jqGrid({
	                url: '${pageContext.request.contextPath}/hodMeterInfoHistory!doSelect.do',
	                datatype: "local",
	                autowidth:true,
	                height:document.documentElement.clientHeight-195,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                //rownumWidth:50,
	                rowList: [5,10,20,30,40,50,100],
	                //colNames:['表号','累计热量(KWh)','累计流量(m³)','瞬时流量(m³/h)','累计时间(h)','供水温度(℃)','回水温度(℃)','阀门状态','电池状态','在线状态','抄表时间','操作'],
	                colNames:['表号','地理位置','累计热量(KWh)','累计流量(m³)','瞬时流量(m³/h)','累计时间(h)','供水温度(℃)','回水温度(℃)','抄表时间'],
	                colModel:[
	                    {name:'meterName',index:'meterName', align:"center",width:'100',sortable:false},
	                    {name:'meterPositionName',index:'meterPositionName', align:"center",width:'200',sortable:false},
	                    {name:'currentEnergy',index:'currentEnergy',align:"center",sortable:false},
	                    {name:'accumulateFlow',index:'accumulateFlow',align:"center",sortable:false},
	                    {name:'meterFlow',index:'meterFlow',align:"center",sortable:false},
	                    {name:'accumulateTime',index:'accumulateTime',align:"center",sortable:false},
	                    {name:'supplyTemper',index:'supplyTemper',align:"center",sortable:false},
	                    {name:'backTemper',index:'backTemper',align:"center",sortable:false},
	                    /*{name:'valveStatus',index:'valveStatus',align:"center",width:'70',sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "阀开";
		                    	if(cellvalue==1)
		                    		return "阀关";
		                    	if(cellvalue==2)
		                    		return "异常";
		                    }
	                    },
	                    {name:'batteryStatus',index:'batteryStatus',align:"center",width:'70',sortable:false,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "正常";
		                    	if(cellvalue==1)
		                    		return "欠压";
		                    }
	                    },
	                    {name:'isOnline',index:'isOnline',align:"center",width:'70',sortable:false,
		                    formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==1)
		                    		return "在线";
		                    	if(cellvalue==2)
		                    		return "离线";
		                    }
	                    },*/
	                    {name:'readTime',index:'readTime',align:"center",width:'200',sortable:false}
	                    /*{name:'id',index:'id',align:"center",width:'80',sortable:false,formatter: function (value, grid, rows, state) 
	                		{ 
	                			return "<a href=\"#\" style=\"color:#f60\" onclick=\"Delete(" + value + ")\">【删除】</a>";
	                		}
						}*/
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#pager",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                //multiselect: true, 
	                //sortname:'read_time',
	                //sortorder:'desc',
	                emptyrecords: "没有数据!"
	             }).navGrid('#pager',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
	             
	             //冻结数据
	             $("#grid_id2").jqGrid({
	                url: '${pageContext.request.contextPath}/hodMeterInfoHistory!doSelectFree.do',
	                datatype: "local",
	                //autowidth:true,因为父元素隐藏表格宽度无法自适应
	                width:document.body.clientWidth-15,
	                height:document.documentElement.clientHeight-195,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['表号','地理位置','结算日期','累积热量(KWh)','累积流量(m³)'],
	                colModel:[
	                    {name:'meterName',index:'meterName', align:"center",width:100,sortable:false},
	                    {name:'meterPositionName',index:'meterPositionName', align:"center",width:'200',sortable:false},
	                    {name:'cleatDate',index:'cleatDate',align:"center",sortable:false},
	                    {name:'clearEnergy',index:'clearEnergy',align:"center",sortable:false},
	                    {name:'clearFlow',index:'clearFlow',align:"center",sortable:false}
	                    /*{name:'id',index:'id',align:"center",width:'80',sortable:false,formatter: function (value, grid, rows, state) 
	                		{ 
	                			return "<a href=\"#\" style=\"color:#f60\" onclick=\"Delete(" + value + ")\">【删除】</a>";
	                		}
						}*/
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#pager2",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                //multiselect: true, 
	                emptyrecords: "没有数据!"
	             }).navGrid('#pager2',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
            }); 
            
            //删除
            function doDeletes()
            {
		        if(tabStatus == 0){
		        	doDelete('grid_id','${pageContext.request.contextPath}/hodMeterInfoHistory!doDelete.do?method=0',tabStatus);
		        }
		        else{
		        	doDelete('grid_id2','${pageContext.request.contextPath}/hodMeterInfoHistory!doDelete.do?method=1',tabStatus);
		        }
            }
            
            //行删除
            function Delete(id)
            {
            	if(tabStatus == 0){
		        	doDeleteByIdHis(id,'${pageContext.request.contextPath}/hodMeterInfoHistory!doDelete.do?method=0',tabStatus);
		        }
		        else{
		        	doDeleteByIdHis(id,'${pageContext.request.contextPath}/hodMeterInfoHistory!doDelete.do?method=1',tabStatus);
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
														<B>当前位置</B>：[<font color='red'>历史数据</font>]
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
													<!-- <td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a id="deleteById" href="#" onclick="doDeletes();"><div class="delbtn"></div></a>
																</td>
															</tr>
														</table>
													</td> -->
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
								<span id="sp1" onclick="setTab('history','free','sp1','sp2')" class="sp1">历史数据</span>
								<span id="sp2" onclick="setTab('free','history','sp2','sp1')" class="sp2">月冻结数据</span>
								<div></div>
								<div id="history" style="float: left;">
									<%@include file="/inc/historySearch.jsp" %>
									<table id="grid_id"></table>
        							<div id="pager"></div>
								</div>
								
								<div id="free" style="float: left;">
									<%@include file="/inc/freeSearch.jsp" %>
									<table id="grid_id2"></table>
        							<div id="pager2"></div>
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

