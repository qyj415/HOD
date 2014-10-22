<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String jreeChartPath = path + "/servlet/DisplayChart?filename=";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>正常用量统计</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<!-- 后加 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"> </script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
			#Analyze1{
				border:1px solid #2EB4B9;
				border-left:none;
				height: 600px;
			}
			#Analyze2{
				border: 1px solid #2EB4B9;
				border-left:none;
				display: none;
				height: 600px;
			}
		</style>
		<script type="text/javascript">
			var code;
			var i=1;
			function setTab(m,n,s,p){
				if(m=="Analyze1")
				{
					i=1;
				}
				else
				{
					i=2;
				}
			   document.getElementById(m).style.display='block';
			   document.getElementById(n).style.display='none';
			   document.getElementById(s).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn2.jpg')";
			   document.getElementById(p).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			}
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
							dataUrl: "hodConcentrator!doTree.do?checkBox=false"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '区域管理',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.checkAreaTree({
						id:"dataAnalyzeTreePanel",
				        renderTo:"dataAnalyzeTree",
				        width:230,
				        height:document.body.clientHeight-100,
				     	loader: topLoader,
						root: topRoot,
						title:'表计信息',
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
			 	topTreePanel.on('click', onToptreeDbclick, topLoader);
			 	//父节点的复选框选中，子节点也选中
			 	topTreePanel.on('checkchange', function(node, checked) {
			        node.attributes.checked = checked;
			        node.eachChild(function(child) {
			                    child.ui.toggleCheck(checked);
			                    child.attributes.checked = checked;
			                    child.fireEvent('checkchange', child,
			                            checked);
			        });
				});
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
				code=node.attributes.text;
				if(i==1)
				{
					checkTime();
				}else{
					showList();
				}
			}
			
			function showList()
			{
				if(code == null){
					Ext.MessageBox.alert('提示','请选择要进行分析的表计!');
					return;
				}
				$("#datatable tr").eq(0).nextAll().remove();
				Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
				if(i==1)
				{
					Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/jFreeChart!doAnalyze.do",
						params:{
							utils:$("#utils").val(),
			            	startTime:$("#startTime").val(),
			            	endTime:$("#endTime").val(),
			            	meterPosition:code
			        	},  
						async : false,
						method : 'POST',
						timeout : 180000,
						success : function(res) {
							Ext.MessageBox.hide();
							var result = Ext.decode(res.responseText);
							if (true == result.success) {
								var url=result.data;
								var mapStr = result.mapStr;
								/*if(mapStr == "")
								{
									Ext.MessageBox.alert('提示',"选择时段无该表计的用量数据!");
								}*/
								document.getElementById('doAnalyzeMaps').innerHTML= mapStr; 
								var img = "<img src=<%= jreeChartPath %>" + url +" width=700 height=400 border=0 usemap='#doAnalyzeMaps' >"; 
								document.getElementById('doAnalyze').innerHTML= img; 
							}
							else
							{
								Ext.MessageBox.alert('提示','请求失败');
							}
						},
						failure : function(res) {
							Ext.MessageBox.hide();
							Ext.MessageBox.alert('提示','请求超时!');
						}
			    	});
		    	}
				else
				{
					Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/jFreeChart!toAnalyze.do",
						params:{
			            	meterPosition:code
			        	},  
						async : false,
						method : 'POST',
						timeout : 180000,
						success : function(res) {
							var result = Ext.decode(res.responseText);
							Ext.MessageBox.hide();
							if (true == result.success) {
								var url=result.data;
								var mapStr = result.mapStr;
								document.getElementById('toAnalyzeMaps').innerHTML= mapStr; 
								var img = "<img src=<%= jreeChartPath %>" + url +" width=700 height=400 border=0 usemap='#toAnalyzeMaps' >"; 
								document.getElementById('toAnalyze').innerHTML= img; 
							}
							else
							{
								Ext.MessageBox.alert('提示','请求失败');
							}
						},
						failure : function(res) {
							Ext.MessageBox.hide();
							Ext.MessageBox.alert('提示','请求超时!');
						}
			    	});
				}
			}
			
		</script>
  </head>
  
<body onload="load('Analyze1',250),load('Analyze2',251),loads('Analyze1',100),loads('Analyze2',100)">
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
														<B>当前位置</B>：[能耗曲线]-[
														<font color='red'>正常用量统计</font>]
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
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=2"><div class="returnbtn"></div></a>
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
								<div>
									<span id="sp1" onclick="setTab('Analyze1','Analyze2','sp1','sp2')" class="sp1">用量分析</span>
									<span id="sp2" onclick="setTab('Analyze2','Analyze1','sp2','sp1')" class="sp2">18个月用量分析</span>
								</div>
								<div style="border: 1px solid #2EB4B9;width: 230px;float: left;" id="dataAnalyzeTree"></div>
								<div id="Analyze1" style="background-color: white;float: left;">
									<form action="">
										<%@include file="/inc/nomalSearch.jsp" %>
									</form>
									<div id="doAnalyze" align="center"></div>
									<map id="doAnalyzeMaps" name="doAnalyzeMaps"></map>

								</div>
								<div id="Analyze2" style="background-color: white;float: left;">
									<div id="toAnalyze" align="center"></div>
									<map id="toAnalyzeMaps" name="toAnalyzeMaps"></map>
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
