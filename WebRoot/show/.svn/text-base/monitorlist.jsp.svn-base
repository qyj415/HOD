<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>表计状态</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<!-- 后加 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript">
			var meterPosition = "";
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
							dataUrl: "../hodCity!doTree.do?checkBox=false"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '区域管理',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.checkAreaTree2({
				    	id: 'monitorTreePanle',
				        renderTo:"monitorTree",
				        width:230,
				        title : '地址信息',
				        height:document.body.clientHeight-145,
				     	loader: topLoader,
						root: topRoot,
						tools : [		//在标题栏上，显示图标，告诉用户图标代表什么
			   		  		 {
			   		  	 		id: 'westtools-collapseAll',	//对应的样式.x-tool-westtools-collapseAll
			   		  	 		qtip : '闭合所有节点',
			   		  	 		handler: function()
			   		  	 		{
			   		  	 			topTreePanel.collapseAll();
			   		  	 		}.createDelegate(this)
			   		  		 },{
			   		  	 		id: 'westtools-expandAll',	//对应的样式.x-tool-westtools-expandAll
			   		  	 		qtip : '展开所有节点',
			   		  	 		handler: function()
			   		  	 		{
			   		  	 			topTreePanel.expandAll();
			   		  	 		}.createDelegate(this)
			   		  		 },{
			   		  	 		id: 'westtools-terminal',  //对应的样式..x-tool-westtools-refresh
			   		  	 		qtip : '刷新',
			   		  	 		handler: function()
   		  	 					{
   		  	 						topTreePanel.getRootNode().reload();
									//topTreePanel.expandAll();
			   		  		 	}
			   		  		 }
			   		  	 ]
				 });
				//topTreePanel.expandAll();
			 	topLoader.on('beforeload',onToploaderBeforeLoad, topLoader);
			 	topTreePanel.on('click', onToptreeDbclick, topTreePanel);
			});
		
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.code = node.attributes.code;
			}
			//单击事件
			function onToptreeDbclick(node,event)
			{
				meterPosition=node.attributes.code;
				showList();
			}
			$(function(){
				$("#meterStatus").jqGrid({
	                url: '${pageContext.request.contextPath}/hodMeter!doMonitor.do',
	                datatype: "json",
	                width:document.body.clientWidth-250,
	                height:document.documentElement.clientHeight-190,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                //colNames:['表号','地址','阀门状态','电池状态','在线状态'],
	                colNames:['表号','地址','电池状态','在线状态','内存状态','流量传感器状态','供水温度传感器状态','回水温度传感器状态'],
	                colModel:[
	                    {name:'meterName',index:'meterName', align:"center",sortable:false},
	                    {name:'meterPositionName',index:'meterPositionName',align:"center",sortable:false,width:300},
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
	                pager: "#meterStatusPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	            }).navGrid('#meterStatusPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
			});
			window.setInterval("showList()",60*1000);
			function showList()
			{
				$("#meterStatus").jqGrid('setGridParam',{  
			        datatype:'json',  
			        postData:{'meterName':$("#meterName").val(),'valveStatus':$("#valveStatus").val(),'batteryStatus':$("#batteryStatus").val(),'isOnline':$("#isOnline").val(),'meterPosition':meterPosition}, //发送数据  
			        page:1  
			    }).trigger("reloadGrid"); //重新载入
			}
			
			function printaa(){
					var printForm = document.createElement('form');
					printForm.method = 'post';
					printForm.action = '${pageContext.request.contextPath}/hod2000Meter!monitorPrint.do';
					printForm.id = 'printForm';
					printForm.target = '_blank';
					
					var a = document.createElement('input');
					a.type = 'hidden';
					a.name = 'meterName';
					a.value = $("#meterName").val();
					
					var b = document.createElement('input');
					b.type = 'hidden';
					b.name = 'valveStatus';
					b.value = $("#valveStatus").val();
					
					var c = document.createElement('input');
					c.type = 'hidden';
					c.name = 'batteryStatus';
					c.value = $("#batteryStatus").val();
					
					var d = document.createElement('input');
					d.type = 'hidden';
					d.name = 'isOnline';
					d.value = $("#isOnline").val();
					
					var e = document.createElement('input');
					e.type = 'hidden';
					e.name = 'meterPosition';
					e.value = meterPosition;
					
					printForm.appendChild(a);
					//printForm.appendChild(b);阀门
					//printForm.appendChild(c);电池状态
					printForm.appendChild(d);
					printForm.appendChild(e);
					document.body.appendChild(printForm);
					printForm.submit();
					document.body.removeChild(printForm);
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
														<B>当前位置</B>：[<font color='red'>表计状态</font>]
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
							<td width="8" class="tab_12"></td>
							<td>
								<div style="height: 1px;width: 100%;"><table><tr><td height="1"></td></tr></table></div>
								<DIV id="searchDIV">
							 			<%@include file="/inc/monitorSearch.jsp" %>
								</DIV>
							</td>
							<td width="8px" class="tab_15"></td>
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
								<div style="border-right: 1px solid #2EB4B9;border-top:1px solid #2EB4B9;width:230px;float: left;" id="monitorTree"></div>
								<div style="float: left;">
									<table id="meterStatus"></table>
        							<div id="meterStatusPage"></div>
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
								<img src="${pageContext.request.contextPath}/images/status/online.png"/><span class="sps">在线</span>
								<img src="${pageContext.request.contextPath}/images/status/notonline.png"/><span class="sps">离线</span>
								<img src="${pageContext.request.contextPath}/images/status/normal.png"/><span class="sps">正常</span>
								<img src="${pageContext.request.contextPath}/images/status/abnormal.png"/><span class="sps">错误</span>
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

