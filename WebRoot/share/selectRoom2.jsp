<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>房间信息</title>
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"> </script>
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
				    	id: 'roomTreePanel',
				        renderTo:"roomTree",
				        width:170,
				        height:document.body.clientHeight-40,
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
			 	topTreePanel.on('click', onToptreeDbclick, topTreePanel);
			});
		
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
			}
			
			//单击事件
			function onToptreeDbclick(node,event)
			{
				var type=node.attributes.type;
				var id=node.attributes.qtip;
				if(10==type)
				{
					var datas = document.getElementById('datas');
					datas.src = "../hod2000Room!doSelect3.do?unitId="+id;
				}
			}
		</script>
	</head>
	<body onload="load('datas',190)">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
								<div style="border-right: 1px solid #2EB4B9;border-top:1px solid #2EB4B9;width: 170px;float: left;" id="roomTree"></div>
								<iframe src="${pageContext.request.contextPath}/share/defaultIframe.jsp" frameborder="0"  bgcolor="b5d6e6"  height="100%" id="datas">
								</iframe>
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

