<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>实时数据</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"> </script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript">
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
							dataUrl: "../hodConcentrator!doTree.do?checkBox=true"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '区域管理',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.checkAreaTree2({
				    	id: 'readMeterTreePanle',
				        renderTo:"readMeterTree",
				        width:230,
				        title:'表计信息',
				        height:document.body.clientHeight-66,
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
			 	//父节点的复选框选中，子节点也选中
			 	topTreePanel.on('checkchange', function(node, state) {
					node.ui.checkbox.checked = state; 
					node.attributes.checked = state;
					if(node.attributes.leaf)
					{
						var pNode = node.parentNode; 
						if (state || topTreePanel.getChecked(id, pNode) == "") { 
							pNode.ui.checkbox.checked=state;
							pNode.attributes.checked = state; 
						}
					} 
					else
					{
						//node.expand();
						node.cascade(function(node){ 
							node.ui.checkbox.checked = state; 
							node.attributes.checked = state; 
							return true; 
						}); 
					}
				});
			});
			var count=1;
			var timeID;
			var flag=0;
			var len2=0;
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.code = node.attributes.code;
			}
			//加载表格
			$(function(){
				 $("#meterTempList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodMeterInfoTemp!readTemp.do',
	                datatype: "local",
	                width:document.body.clientWidth-250,
	                height:document.documentElement.clientHeight-115,
	                mtype: "POST",
	                shrinkToFit:false,
	                autoScroll: true,
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                //colNames:['表号','累计热量(KWh)','累计流量(m³)','瞬时流量(m³/h)','累计时间(h)','供水温度(℃)','回水温度(℃)','阀门状态','电池状态','在线状态'],
	                colNames:['表号','地理位置','累计热量(KWh)','累计流量(m³)','瞬时流量(m³/h)','累计时间(h)','供水温度(℃)','回水温度(℃)','电池状态','在线状态','内存状态','流量传感器状态','供水温度传感器状态','回水温度传感器状态'],
	                colModel:[
	                    {name:'meterName',index:'meterName', align:"center",width:80,sortable:false},
	                     {name:'meterPositionName',index:'meterPositionName', align:"center",width:150,sortable:false},
	                    {name:'currentEnergy',index:'currentEnergy',align:"center",sortable:false,width:100,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value!= "")
	                			{
	                				len2 ++;
	                				return value;
	                			}else{
	                				return value;
	                			}
	                		}
	                    },
	                    {name:'accumulateFlow',index:'accumulateFlow',align:"center",sortable:false,width:100},
	                    {name:'meterFlow',index:'meterFlow',align:"center",sortable:false,width:100},
	                    {name:'accumulateTime',index:'accumulateTime',align:"center",sortable:false,width:100},
	                    {name:'supplyTemper',index:'supplyTemper',align:"center",sortable:false,width:80},
	                    {name:'backTemper',index:'backTemper',align:"center",sortable:false,width:80},
	                    /*{name:'valveStatus',index:'valveStatus',align:"center",sortable:false,width:80,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==0)
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/open.png\"/>";
	                			}
	                			if(value==1)
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/close.png\"/>";
	                			}
	                			if(value==2)
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/warm.png\"/>";
	                			}
	                		}
	                    },*/
	                    {name:'batteryStatus',index:'batteryStatus',align:"center",width:80,sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value=='0')
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/yes.png\"/>";
	                			}
	                			else if(value==1)
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/no.png\"/>";
	                			}
	                			else
	                			{
	                				return "";
	                			}
	                		}
						},
						{name:'isOnline',index:'isOnline',align:"center",width:80,sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==1)
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/online.png\"/>";
	                			}
	                			else if(value==2)
	                			{
	                				return "<img src=\"${pageContext.request.contextPath}/images/status/notonline.png\"/>";
	                			}
	                			else
	                			{
	                				return "";
	                			}
	                		}
						},
	                    {name:'eepromStatus',index:'eepromStatus',align:"center",sortable:false,width:100,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue=='0')
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else
		                    		return "";
		                    }
	                    },
	                    {name:'flowsensorStatus',index:'flowsensorStatus',align:"center",sortable:false,width:150,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue=='0')
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else
		                    		return "";
		                    }
	                    },
	                    {name:'tepdownStatus',index:'tepdownStatus',align:"center",sortable:false,width:150,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue=='0')
		                    		return "<img src='${pageContext.request.contextPath}/images/status/normal.png'/>";
		                    	else if(cellvalue==1)
		                    		return "<img src='${pageContext.request.contextPath}/images/status/abnormal.png'/>";
		                    	else
		                    		return "";
		                    }
	                    },
	                    {name:'tepupStatus',index:'tepupStatus',align:"center",sortable:false,width:150,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue=='0')
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
	                pager: "#meterTempListPager",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	             }).navGrid('#meterTempListPager',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
			});
			
			//页面读取终端返回的数据，并写在表格中
			function writeMeter()
			{
				count+=1;
				len2=0;
				if(count>18)
				{
					if(flag==0)
					{
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示','请求超时!');
					}
					count=1;
					flag=0;
					len2 = 0;
					window.clearInterval(timeID);
					return;
				}
				var value=getMeterNo();
				if(value!='')
				{
					$("#meterTempList").jqGrid('setGridParam',{  
				        datatype:'json',  
				        mtype:"post",
				        postData:{'ids':value}, //发送数据  
				        page:1,
				        loadComplete: function(){ 
				        	var len1=value.split(",").length;
							//var len2=$("#meterTempList").getGridParam("reccount");
							if(len2>=0)
							{
								Ext.MessageBox.hide();
								flag=1;
							}
							if(len1==len2)
							{
								count=1;
								len2 = 0;
								window.clearInterval(timeID);
							}
				        }
				    }).trigger("reloadGrid"); //重新载入
				}
			}
			
			//得到树选中的表计节点id
			function getMeterNo()
			{
				var b = Ext.getCmp("readMeterTreePanle").getChecked();
		        var checkid = new Array;// 存放选中id的数组
		        for (var i = 0; i < b.length; i++) {
			        if(b[i].getUI().checkbox.indeterminate){
			        	continue;
		        	}
		        	if(b[i].attributes.type==9)//表计id
		            	checkid.push(b[i].attributes.qtip);// 添加id到数组
		        }
		        return checkid.toString();
			}
			
			//发送读数据指令
			function readMeter()
			{
		        var value=getMeterNo();
		        jQuery("#meterTempList").jqGrid("clearGridData");
		        if(value!='')
		        {
	       			Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/hodMeterInfoTemp!readMeter.do",
						async : false,
						method:'POST',
						params:{  
			            	ids:value
			        	}, 
						timeout : 180000,
						success : function(res) {
							var result = Ext.decode(res.responseText);
							if (true == result.success) {
								Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');//锁屏并启动定时器
								count=1;
								flag=0;
								timeID=window.setInterval("writeMeter()",10000);
							}
							else
							{
								Ext.MessageBox.alert('提示',result.msg);
							}
						},
						failure : function(res) {
							Ext.MessageBox.alert('提示','请求失败!');
						}
					});
				}
				else
				{
					Ext.MessageBox.alert('提示','请选择你要抄读的表!');
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
														<B>当前位置</B>：[<font color='red'>实时数据</font>]
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
																	<a href="javaScript:readMeter();"><div class="readbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/map/ReadMeterMap.jsp;"><div class="mapbtn"></div></a>
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
								<div style="border-right: 1px solid #2EB4B9;border-top:1px solid #2EB4B9;width: 230px;float: left;" id="readMeterTree"></div>
								<div style="float: left;">
									<table id="meterTempList"></table>
        							<div id="meterTempListPager"></div>
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

