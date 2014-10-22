<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>请选择地理位置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<!-- 后加 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree.js"></script>
	<script type="text/javascript">
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
							dataUrl: "hodCity!doTree.do?checkBox=false&meters=false&rooms=true"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '区域管理',
						id: '-1' ,
						expanded : true,
						//checked:false,
						type:'0'
				});
				var topTreePanel = new Ext.ld.checkAreaTree({
						id:"showAddress",
				        renderTo:"showAddressTree",
				        width:740,
				        height:440,
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
				loader.baseParams.code=node.attributes.code;
			}
			//单击事件
			function onToptreeDbclick(node,event)
			{
				var type=node.attributes.type;
				var code=node.attributes.code;
				var id=node.attributes.qtip;
				var positionName='';
				if(type>4)
				{
					if(7==type)//房间
					{
						positionName=node.parentNode.parentNode.parentNode.attributes.text+node.parentNode.parentNode.attributes.text+node.parentNode.attributes.text+node.attributes.text;
					}
					else if(10==type)//单元
					{
						positionName=node.parentNode.parentNode.attributes.text+node.parentNode.attributes.text+node.attributes.text;
					}
					else if(6==type)//楼栋
					{
						positionName=node.parentNode.attributes.text+node.attributes.text;
					}
					else//小区
					{
						positionName=node.attributes.text;
					}
					//var parentCode=node.parentNode.attributes.code;
					window.returnValue =[code,type,id,positionName];
					window.close();
				}
				else
					 Ext.MessageBox.alert('提示','请选择小区及以下的区域!');
			}
		</script>
  </head>
  
  <body>
    <div style="border: 1px solid #2EB4B9;" id="showAddressTree"></div>
  </body>
</html>
