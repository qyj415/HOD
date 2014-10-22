<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>集中器维护</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript">
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
				    	id: 'concentratorTreePanel',
				        renderTo:"concentratorTree",
				        width:230,
				        title:'地址信息',
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
			 	topTreePanel.on('click', onToptreeClick, topTreePanel);
			});
		
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.code = node.attributes.code;
			}
			var code='';
			//单击事件
			function onToptreeClick(node,event)
			{
				code=node.attributes.code;
				searchCon(1);
			}
			//查询数据
			function searchCon(page)
			{
				$("#terminalList").jqGrid('setGridParam',{  
			        datatype:'json',  
			        postData:{'conNumber':$("#conNumber").val(),'code':code}, //发送数据  
			        page:page  
			    }).trigger("reloadGrid"); //重新载入
			}
			
			$(function(){
				 $("#terminalList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodConcentrator!doSelect.do',
	                datatype: "json",
	                width:document.body.clientWidth-250,
	                height:document.documentElement.clientHeight-195,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['集中器编号','安装位置','冻结间隔','间隔数值','结算日','版本信息','设备匹配','操作'],
	                colModel:[
	                    {name:'conNumber',index:'conNumber', align:"center",width:'100',sortable:false},
	                    {name:'conPositionName',index:'conPositionName',align:"center",sortable:false},
	                    {name:'freezeInterval',index:'freezeInterval',align:"center",sortable:false,width:50,
	                    	formatter:function(cellvalue, options, rowObject)
		                    {
		                    	if(cellvalue==0)
		                    		return "分钟";
		                    	else if(cellvalue==1)
		                    		return "小时";
		                    	else if(cellvalue==2)
		                    		return "天";
		                    	else if(cellvalue==3)
		                    		return "月";
		                    	else
		                    		return "";
		                    	 
		                    }
	                    },
	                    {name:'freezeIntervalValue',index:'freezeIntervalValue',align:"center",sortable:false,width:50},
	                    {name:'clearDate',index:'clearDate',align:"center",sortable:false,width:150},
	                    {name:'id',index:'id',align:"left",sortable:false,width:200,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			var conAble=rows["conAble"];
	                			if(conAble==0)
	                				return "";
	                			else
	                				return "<a href=\"#\" style=\"color:#f60\" onclick=\"showDiv(" + value + ")\">读取版本信息</a><div id='"+value+"id' style='display: none;'></div>";
	                		}
	                    },
	                    {name:'masterConId',index:'masterConId',align:"center",width:'80',sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			var conAble=rows["conAble"];
	                			if(conAble==0)
	                				return "";
	                			else
	                			{
		                			if(value!=null)
		                				return "【已匹配】";
		                			else
		                			{
		                				var conId=rows["id"];
		                				var conNum=rows["conNumber"];
		                				return "<a href=\"${pageContext.request.contextPath}/hod2000Concentrator!toMapping.do?id="+conId+"&conNum="+conNum+"\" style=\"color:#f60\">【匹配】</a>";
		                			}
	                			}
	                		}
						},
	                    {name:'id',index:'id',align:"center",width:'120',sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			//<a href=\"#\" style=\"color:#f60\" onclick=\"factoryReset(" + value + ")\">【恢复出厂设置】</a>
	                			var conAble=rows["conAble"];
	                			if(conAble==0)
	                				return "【已更换】";
	                			else
	                				return "<a href=\"${pageContext.request.contextPath}/hod2000Concentrator!toUpdate.do?id="+value+"\" style=\"color:#f60\">【编辑】</a><a href=\"${pageContext.request.contextPath}/hod2000Concentrator!toChange.do?id="+value+"\" style=\"color:#f60\">【更换】</a>";
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
	                pager: "#terminalListPage",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                multiselect: true, 
	                emptyrecords: "没有数据!"
	             }).navGrid('#terminalListPage',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
			});
			
			//打印集中器管理
			function printConcentratorInfo(){
					var printForm = document.createElement('form');
					printForm.method = 'post';
					printForm.action = '${pageContext.request.contextPath}/hod2000Concentrator!print.do';
					printForm.id = 'printForm';
					printForm.target = '_blank';
					
					var a = document.createElement('input');
					a.type = 'hidden';
					a.name = 'conNumber';
					a.value = $("#conNumber").val();
					printForm.appendChild(a);
					
					var b = document.createElement('input');
					b.type = 'hidden';
					b.name = 'code';
					b.value = code;
					printForm.appendChild(b);
					
					document.body.appendChild(printForm);
					printForm.submit();
					document.body.removeChild(printForm);
			}
			
			//设置结算日
			function setClearDate()
			{
				var rowid = $("#terminalList").jqGrid('getGridParam','selarrrow');//获取我选中的Id
				var clearDate=$("#clearDate").val();
				if(rowid.length==0)
				{
					Ext.MessageBox.alert('提示','请选择要设置结算日的集中器!');
					return;
				}
				if(clearDate<1||clearDate>28)
				{
					Ext.MessageBox.alert('提示','结算日范围为1~28!!');
					return;
				}
				//searchCon();
				batchProcess('${pageContext.request.contextPath}/hodConcentrator!setClearDate.do',clearDate,0,rowid.length,rowid);
			}
			
			function batchProcess(url,clearDate,index, length, params)
			{  
				Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
			    if(index >= length){  
			        Ext.MessageBox.hide();
			        return;  
			    }else{  
			        Ext.Ajax.request({  
			            url:url,  
			            params:{
			            	delIds:params[index],
			            	clearDate:clearDate
			            },  
			            method:'POST', 
			            timeout : 180000,
			            success:function(response) { 
			            	var result = Ext.decode(response.responseText);
			            	$("#terminalList").setCell(params[index],"clearDate",result.msg);
			            	if(result.success==true)
			            	{
				                batchProcess(url,clearDate,++index, length, params);  
			                }
			                else
			                {
			                	if(index+1==length)
			                	{
				                	Ext.MessageBox.hide();
				                	return;
			                	}
			                	Ext.Msg.confirm("提示", "请求失败，是否继续往下执行？", function(btn, text) {
									 if (btn == 'yes') 
									 {
									 	batchProcess(url,clearDate,++index, length, params);
									 }
									 else
									 {
									 	Ext.MessageBox.hide();
									 }
								});
			                }
			            },  
			            scope:this  
			        });   
			    }  
			}  
			
			
			//恢复出厂设置
			function factoryReset(value)
			{
				Ext.MessageBox.confirm("请确认","你确定要进行此操作?",function(button,text){ 
			        if(button=="yes")
			        {
			        	Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
						Ext.Ajax.request({
							url : "${pageContext.request.contextPath}/hodConcentrator!factoryReset.do",
							method:'POST',
							params:{  
				            	id:value
				        	}, 
							async : false,
							timeout : 180000,
							success : function(res) {
								var result = Ext.decode(res.responseText);
								if(result.success==true)
								{
									searchCon();
								}
								Ext.MessageBox.hide();
								Ext.MessageBox.alert('提示',result.msg);
							},
							failure : function(res) {
								Ext.MessageBox.hide();
								Ext.MessageBox.alert('提示','请求超时!');
							}
						});
			        }
		        }); 
			}
			//读取集中器版本信息
			function showDiv(id)
			{
				var di=document.getElementById(id+'id');
				di.innerHTML='';
				Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodConcentrator!readVersion.do",
					method:'POST',
					params:{  
		            	conId:id
		        	}, 
					async : false,
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						Ext.MessageBox.hide();
						if (true == result.success) {
							var data=result.data;
							var div=document.createElement("div");
							div.innerHTML=data;
							di.appendChild(div);
						}
						else
						{
							var msg=result.msg;
							di.innerHTML=msg;
						}
					},
					failure : function(res) {
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示','请求超时!');
					}
				});
				di.style.display='block';
			}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
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
														<B>当前位置</B>：[集中器管理]-[<font color='red'>集中器维护</font>]
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
																	<a href="javascript:printConcentratorInfo();"><div class="printbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/hod2000Concentrator!toAdd.do"><div class="addbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/map/TerminalMap.jsp;"><div class="mapbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=6"><div class="returnbtn"></div></a>
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
							 		<%@include file="/inc/concentratorSearch.jsp" %>
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
								<div style="width:230px;float: left;" id="concentratorTree"></div>
								<div style="float: left;">
									<table id="terminalList"></table>
        							<div id="terminalListPage"></div>
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

