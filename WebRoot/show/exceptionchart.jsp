<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>异常用量统计</title>
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
			#history{
				border:1px solid #2EB4B9;
				border-left:none;
				overflow:auto;
			}
			#read{
				border: 1px solid #2EB4B9;
				border-left:none;
				display: none;
				overflow:auto;
			}
		</style>
		<script type="text/javascript">
			var i=1;
			function setTab(m,n,s,p){
				if(m=="history")
				{
					i=1;
				}
				else
				{
					i=2;
				}
			    document.getElementById(m).style.display='inline-block';
			    document.getElementById(n).style.display='none';
			    document.getElementById(s).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn2.jpg')";
			    document.getElementById(p).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			}
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
					dataUrl: "hodCity!doTree.do?checkBox=false"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
					text: '区域管理',
					id: '-1' ,
					expanded : true,
					type:'0'
				});
				var topTreePanel = new Ext.ld.checkAreaTree({
			    	id: 'readTreePanel',
			        renderTo:"readTree",
			        width:230,
			        height:document.body.clientHeight-100,
			     	loader: topLoader,
					root: topRoot,
					title:'地址信息',
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
			 	topTreePanel.on('checkchange', function(node, checked) {
			        node.attributes.checked = checked;
			        node.eachChild(function(child) {
			                    child.ui.toggleCheck(checked);
			                    child.attributes.checked = checked;
			                    child.fireEvent('checkchange', child,
			                            checked);
			        });
				});
				topTreePanel.on('click', onToptreeDbclick, topLoader);
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
				var code=node.attributes.code;
				if(i==1)
				{
					checkVale(code);
				}
				else
				{
					checkValue1(code);
				}
			}
		</script>
  </head>
  
<body onload="load('history',250),load('read',251),loads('history',100),loads('read',100)">
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
														<font color='red'>异常用量统计</font>]
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
								<span id="sp1" onclick="setTab('history','read','sp1','sp2')" class="sp1">连续未使用的用户信息</span>
								<span id="sp2" onclick="setTab('read','history','sp2','sp1')" class="sp2">用量异常的用户信息</span>
								<div></div>
								<div style="border: 1px solid #2EB4B9;width: 230px;float: left;" id="readTree"></div>
								<div id="history" style="background-color: white;float: left;">
									<form action="${pageContext.request.contextPath}/hod2000MeterInfoHistory!getUnUsed.do" method="post">
										<%@include file="/inc/exceptionSearch.jsp" %>
									</form>
									
									<table border="0" cellspacing="0" cellpadding="0" style="float: left;">
											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="1"
														bgcolor="b5d6e6" class="list" id="datatable">
														<tr>
															<td class="STYLE5">序号</td>
															<td class="STYLE3">表计编号</td>
															<td class="STYLE3">小区</td>
															<td class="STYLE3">楼栋</td>
															<td class="STYLE3">单元</td>
															<td class="STYLE3">房间号</td>
															<td class="STYLE3">操作</td>
														</tr>
													</table>
												</td>
											</tr>
									</table>
								</div>
								<div id="read" style="background-color: white;float: left;">
									<form action="${pageContext.request.contextPath}/hod2000MeterInfoHistory!doSelect.do">
										<%@include file="/inc/exceptionSearch1.jsp" %>
									</form>
									
									<table border="0" cellspacing="0" cellpadding="0" style="float: left;">
											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="1"
														bgcolor="b5d6e6" class="list" id="datatable2">
														<tr>
															<td class="STYLE5">序号</td>
															<td class="STYLE3">表计编号</td>
															<td class="STYLE3">小区</td>
															<td class="STYLE3">楼栋</td>
															<td class="STYLE3">单元</td>
															<td class="STYLE3">房间号</td>
															<td class="STYLE3">操作</td>
														</tr>
													</table>
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
