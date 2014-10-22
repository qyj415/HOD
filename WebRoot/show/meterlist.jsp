<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>表计管理</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script src="${pageContext.request.contextPath}/js/Ext.ld.JsonMeterTree2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<style type="text/css">
			.contentshow1{padding:5px;border:1px #69f solid;color:red;background-color:#ffffff;position:absolute; width:300px; height:50px;z-index:1;}
			.addMeters{padding:5px;border:1px #69f solid;color:red;background-color:#ffffff;position:absolute; width:400px; height:100px;z-index:1;left: 40%;top: 30%;}
			.add{background-image: url("./images/area/company.gif");}
			.addMeter{background-image: url("./images/meter.gif");}
			.btn1{
				background: url("${pageContext.request.contextPath}/images/search.gif");
				width: 26px;
				height: 26px;
				overflow: hidden;
				cursor: pointer;
				border: 0;
			}
		</style>
		<script type="text/javascript">
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
					dataUrl: "hodMeter!doTree.do"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '表计信息',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.JsonMeterTree2({
				    	id: 'meterInfoTreepanel',
				        renderTo:"meterInfoTree",
				        height:document.body.clientHeight-170,
				        width:230,
				        title:'表计信息',
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
									topTreePanel.expandAll();
			   		  		 	}
			   		  		 }
			   		  	 ],
						contextMenu: new Ext.menu.Menu({  
			                 items: [{  
			                     iconCls: 'add',  
			                     text:'新增热力公司' 
			                 }],
			                 listeners: {  
			                     itemclick: function(item){  
			                         var nodeDataDel = item.parentMenu.contextNode.attributes;  
			                         var parentNodeData = item.parentMenu.contextNode.parentNode;  
			                         var meter_position=nodeDataDel.meter_position;//对应表计的地理位置行政编码，新表的上级表号编码
			                         item.parentMenu.hide();  
			                         $("#meter_position_name1").val('');
									 $("#meter_position").val('');
									 $("#meter_position_name").val('');
			                         document.getElementById('addMeters').style.display='block';
			                    }  
		                 	}  
             			}),
             			contextMenu1: new Ext.menu.Menu({  
			                 items: [{  
			                     iconCls: 'addMeter',  
			                     text:'新增表计信息' 
			                 }],
			                 listeners: {  
			                     itemclick: function(item){  
			                         var nodeDataDel = item.parentMenu.contextNode.attributes;  
			                         var meterStyle=nodeDataDel.meter_style;
			                         if(meterStyle==1)
			                         {
			                         	Ext.MessageBox.alert('提示','户用表下面不能再添加下级表!');
			                         	return;
			                         }
			                         var parentNodeData = item.parentMenu.contextNode.parentNode;  
			                         var parent_position=nodeDataDel.meter_position;//对应表计的地理位置行政编码，新表的上级表号编码
			                         item.parentMenu.hide();//关闭右键菜单  
			                         window.showModalDialog("${pageContext.request.contextPath}/share/meterInfoAdd.jsp?parent_position="+parent_position,window,'dialogHeight=365px;dialogWidth=700px;center=yes;status=no;');
			                         //location.reload();
			                         searchList();
			                         var panel=Ext.getCmp('meterInfoTreepanel');
									 panel.getRootNode().reload();
									 panel.expandAll();
			                    }  
		                 	}  
             			}),
             			listeners: {  
			                 contextmenu: function(node, e){  
			                     appContextmenu = false;  
			                     node.select();  
			                     var tree = node.getOwnerTree(); 
			                     if(0==node.id) 
			                     	var c = tree.contextMenu;  
			                     else 
			                     	var c = tree.contextMenu1;  
			                     c.contextNode = node;  
			                     c.showAt(e.getXY());  
			                 }  
		             	}	
				 });
				topTreePanel.expandAll();
			});
			
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.meter_name = node.attributes.meter_name;
			}
			
			$(function(){
				 $("#meterInfoList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodMeter!doSelect.do',
	                datatype: "json",
	                width:document.body.clientWidth-250,
	                height:document.documentElement.clientHeight-220,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['表号','表底数','口径','波特率','表计型号','地理位置','表计类型','集中器编号','操作'],
	                //colNames:['表号','表底数','阀门编号','口径','波特率','表计型号','地理位置','表计类型','集中器编号','操作'],
	                colModel:[
	                    {name:'meterName',index:'meterName', align:"center",width:'80',sortable:false},
	                    {name:'meterInit',index:'meterInit',align:"center",sortable:false,width:50},
	                    {name:'meterCaliber',index:'meterCaliber',align:"center",sortable:false,width:50},
	                    {name:'meterBaudrate',index:'meterBaudrate',align:"center",sortable:false,width:50,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==2)
		                    		return "1200";
		                    	if(cellvalue==3)
		                    		return "2400";
		                    	if(cellvalue==4)
		                    		return "4800";
		                    	if(cellvalue==5)
		                    		return "7200";
		                    	if(cellvalue==6)
		                    		return "9600";
		                    	if(cellvalue==7)
		                    		return "19200";
		                    }
	                    },
	                    {name:'typeName',index:'typeName',align:"center",sortable:false,width:100},
	                    {name:'meterPositionName',index:'meterPositionName',align:"center",sortable:false,width:150},
	                    {name:'meterStyle',index:'meterStyle',align:"center",sortable:false,width:60,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==1)
		                    		return "户用表";
		                    	if(value==2)
		                    		return "楼栋表";
		                    	if(value==3)
		                    		return "换能站";
	                		}
	                    },
	                    {name:'conNumber',index:'conNumber',align:"center",sortable:false,width:80},
	                    {name:'meterAble',index:'meterAble',align:"center",width:'150',sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==0)
	                				return "【已更换】";
	                			else
	                			{
	                				var meterId=rows["id"];
	                				var values='';
	                				if(${user.hod2000Role.rid==1})
	                				{
	                					values+="<a href='#' onclick='editAddress("+meterId+",this)' style='color:#f60;'>【编辑地址】</a>";
	                				}
	                				values+="<a href='${pageContext.request.contextPath}/hod2000Meter!toUpdate.do?id="+meterId+"' style='color:#f60'>【更换】</a>";
	                				return values;
	                			}
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
	                pager: "#meterInfoListPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                //multiselect: true, 
	                emptyrecords: "没有数据!"
	             }).navGrid('#meterInfoListPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false});
			});
			
			//查询
			function searchList(page,meterParent)
			{
				var meterName=$("#meterName").val();
				var terminalId=$("#terminalId").val();
				var meterStyle=$("#meterStyle").val();
				var meter_type=$("#meter_type").val();
				var meter_caliber=$("#meter_caliber").val();
				$("#meterInfoList").jqGrid('setGridParam',{  
			        datatype:'json',  
			        postData:{'meterName':meterName,'terminalId':terminalId,'meterStyle':meterStyle,'meter_type':meter_type,'meter_caliber':meter_caliber,'meterParent':meterParent}, //发送数据  
			        page:page  
			    }).trigger("reloadGrid"); //重新载入
			}
			
			//阀门设置弹出框清空内容按钮事件
			function clearcard(id)
			{
				$("#"+id).val('');
			}
			
			//阀门设置弹出框关闭窗口按钮事件
			function hiddenGiveCard(id)
			{
				document.getElementById(id).style.display='none';
			}
			
			//确定按钮
			function clickButton(id,meterId)  
			{
				var valveId=$("#"+id).val();
				if(valveId.length!=8&&valveId.length!=0)
				{
					Ext.MessageBox.alert('提示','阀门编号输入不规范，请重新输入!');
				}
				else
				{
					Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/hodMeter!setValveId.do",
						method:'POST',
						params:{  
			            	valveId:valveId,
			            	meterId:meterId
			        	}, 
						async : false,
						timeout : 180000,
						success : function(res) {
							var result = Ext.decode(res.responseText);
							if (true == result.success) {
								$("#sp"+meterId).html(valveId);
								//Ext.MessageBox.alert('提示',result.msg);
								$("#valve"+meterId).css({"display": "none"});
							}
							else
							{
								Ext.MessageBox.alert('提示',result.msg);
								$("#valve"+meterId).css({"display": "none"});
							}
						},
						failure : function(res) {
							Ext.MessageBox.alert('提示','请求失败!');
						}
					});
				}
			} 
			
			//打印表计
			function printaa(){
					var printForm = document.createElement('form');
					printForm.method = 'post';
					printForm.action = '${pageContext.request.contextPath}/hod2000Meter!meterPrint.do';
					printForm.id = 'printForm';
					printForm.target = '_blank';
					
					var a = document.createElement('input');
					a.type = 'hidden';
					a.name = 'meterStyle';
					a.value = $("#meterStyle").val();
					
					var b = document.createElement('input');
					b.type = 'hidden';
					b.name = 'meterName';
					b.value = $("#meterName").val();
					
					var c = document.createElement('input');
					c.type = 'hidden';
					c.name = 'terminalId';
					c.value = $("#terminalId").val();
					
					var d = document.createElement('input');
					d.type = 'hidden';
					d.name = 'meter_type';
					d.value = $("#meter_type").val();
					
					var e = document.createElement('input');
					e.type = 'hidden';
					e.name = 'meter_caliber';
					e.value = $("#meter_caliber").val();
					
					printForm.appendChild(a);
					printForm.appendChild(b);
					printForm.appendChild(c);
					printForm.appendChild(d);
					printForm.appendChild(e);
					document.body.appendChild(printForm);
					printForm.submit();
					document.body.removeChild(printForm);
			}
			
			function showGiveCard(id)
			{
				document.getElementById(id).style.display='block';
			}
			
			//添加热力公司
			function checkForm()
			{
				var meter_position_name1=$("#meter_position_name1").val();
				if(meter_position_name1.length<1||meter_position_name1.length>100)
				{
					Ext.MessageBox.alert('提示','热力公司名称输入不合规范，请重新输入!');
					return false;
				}
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodMeter!doSaveCompany.do",
					method:'POST',
					params:{  
		            	meterPositionName:meter_position_name1
		        	}, 
					async : false,
					timeout : 5000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							Ext.MessageBox.alert('提示','添加热力公司成功！');
							document.getElementById('addMeters').style.display='none';
							var panel=Ext.getCmp('meterInfoTreepanel');
							panel.getRootNode().reload();
							panel.expandAll();
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
			
			function closeDiv()
			{
				document.getElementById('addMeters').style.display='none';
			}
			
			//编辑地址
			function editAddress(meterId,trs)
			{
				var params=window.showModalDialog('${pageContext.request.contextPath}/share/showAddress.jsp',window,'dialogHeight=450px;dialogWidth=750px;center=yes;status=no;');
				var roomId='';
				if(params!=null)
				{
					if(params[1]==7)
					{
						roomId=params[2];
					}
					Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/hodMeter!setAddress.do",
						method:'POST',
						params:{  
			            	meterId:meterId,
			            	roomId:roomId,
			            	meterPosition:params[0],
			            	meterPositionName:params[3]
			        	}, 
						async : false,
						timeout : 180000,
						success : function(res) {
							var result = Ext.decode(res.responseText);
							if (true == result.success) {
								Ext.MessageBox.alert('提示',result.msg);
								trs.parentNode.parentNode.cells[6].innerHTML=result.meterPositionName;
								var panel=Ext.getCmp('meterInfoTreepanel');
								panel.getRootNode().reload();
								panel.expandAll();
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
			}
		</script>
	</head>
	<body>
		<div id="addMeters" class="addMeters" style="display:none;text-align: center;">
			<div class="STYLE3">新增热力公司</div>
			<table cellpadding="0" cellspacing="0" border="0" align="center" height="80" id="tab">
				<tr>
					<td width="100px">热力公司名称：</td>
					<td><input type="text" id="meter_position_name1" name="meter_position_name1" style="width: 155px;height: 20px;"/><span>必填,1~100个字符</span></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="保存" onclick="checkForm()"/>
						<input type="button" value="关闭" onclick="closeDiv()"/>
					</td>
				</tr>
			</table>
		</div>
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
														<B>当前位置</B>：[<font color='red'>表计管理</font>]
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
																	<a href="${pageContext.request.contextPath}/form/uploadmeter.jsp"><div class="uploadbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
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
																	<a href="${pageContext.request.contextPath}/map/MeterMap.jsp;"><div class="mapbtn"></div></a>
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
							 		<%@include file="/inc/meterSearch.jsp" %>
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
								<div style="width:230px;float: left;" id="meterInfoTree"></div>
								<div style="float: left;">
									<table id="meterInfoList"></table>
        							<div id="meterInfoListPage"></div>
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

