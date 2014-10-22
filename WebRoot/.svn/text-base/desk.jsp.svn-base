<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<title>城市建筑智慧能源管理系统</title>	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/images/main/desk.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript">
		function onMouse(id)
		{
			document.getElementById(id).style.border="2px white solid";
		}
		function onMouseOut(id)
		{
			document.getElementById(id).style.border="none";
		}
		function onDivClick(num)
		{
			if(${x!=5}&&num!=15&&num!=16&&num!=17)
			{
				Ext.MessageBox.alert('提示','用户未注册或注册已过期，请点击注册按钮进行注册!');
				return;
			}
			if(num=='userInfoManager')//客户信息
			{
				window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=0';
			}
			else if(num=='alarmInformation')//报警信息
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/analyzewam.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='meterStatus')//表计状态
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/monitorlist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='areaInfo')//地址信息
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/regionlist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='concentratorStatus')//集中器状态
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/concentratorState.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='meterInfoHistory')//历史数据
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/historydata.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='realTimeToRead')//实时数据
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/readmeterlist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='loadPattern')//负荷曲线
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/analyzeload.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='energyCurve')//能耗曲线
			{
				window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=2';
			}
			else if(num=='gridironWastage')//管网损耗
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/analyzelose.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='systemManager')//系统管理
			{
				window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=1';
			}
			else if(num=='meterInfoManager')//表计管理
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/hod2000Meter!toSelect.do';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='conCentratorManager')//集中器管理
			{
				window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=6';
			}
			else if(num=='meterTypeManager')//表计型号管理
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/metertypelist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num==15)//软件注册
			{
				window.location.href='${pageContext.request.contextPath}/login!registerMessage.do';
			}
			else if(num==16)//退出按钮
			{
				Ext.MessageBox.confirm("请确认","你确定退出吗?",function(button,text){ 
			        if(button=="yes")
			        {
			        	window.parent.parent.location="${pageContext.request.contextPath}/login!logout.do";
			        }
		        }); 
			}
			else if(num==17)//帮助按钮
			{
				window.open("${pageContext.request.contextPath}/help/help.html","_blank");
			}
			else if(num=='priceProject') //价格方案
			{
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/hod2000Price!doSelect.do';
				else
					Ext.MessageBox.alert('提示','没有权限!');
			}
			else if(num=='preChargeManager') //预收费管理
			{
				window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=5';
			}
			else if(num=='receiveChargeManager') //收费管理
			{
				window.location.href='${pageContext.request.contextPath}/subpage.jsp?id=4';
			}
		}
		
		//查询当前用户对于该模块是否具有权限  
		function findPermission(permissionId)
		{
			var flag=false;
			var permissions='${permission}';
			if(permissions.length>0)
			{
				var obj=permissions.split(',');
				for(var i=0;i<obj.length;i++)
				{
					if(permissionId==obj[i])
					{
						flag=true;
						break;
					}
					else
					{
						continue;
					}
				}
			}
			return flag;
		}
	</script>
	</head>
<body>
	<div class="deskDiv">
		<div id="wrapper5">
			<span style="margin: 0px;"><img align="absmiddle" src="${pageContext.request.contextPath}/images/logo.gif" width="122px" height="48px"/></span>
			<span class="titleDiv">城市建筑智慧能源管理系统</span>
		</div>
		<div id="wrapper7"><div class="userName">${user.loginName }</div></div>
		<div id="wrapper3"></div>
		<div id="wrapper2"></div>
		<div id="wrapper1">
			<div id="thumb1-1" onmouseover="onMouse('thumb1-1')" onmouseout="onMouseOut('thumb1-1')" onclick="onDivClick('userInfoManager')"></div>
			<div id="thumb1-2" onmouseover="onMouse('thumb1-2')" onmouseout="onMouseOut('thumb1-2')" onclick="onDivClick('alarmInformation')"></div>
			<div id="thumb1-3" onmouseover="onMouse('thumb1-3')" onmouseout="onMouseOut('thumb1-3')" onclick="onDivClick('meterStatus')"></div>
			<div id="thumb1-4" onmouseover="onMouse('thumb1-4')" onmouseout="onMouseOut('thumb1-4')" onclick="onDivClick('areaInfo')"></div>
			<div id="thumb1-5" onmouseover="onMouse('thumb1-5')" onmouseout="onMouseOut('thumb1-5')" onclick="onDivClick('concentratorStatus')"></div>
			<div id="thumb1-6" onmouseover="onMouse('thumb1-6')" onmouseout="onMouseOut('thumb1-6')" onclick="onDivClick('priceProject')"></div>
			<div id="thumb1-19" onmouseover="onMouse('thumb1-19')" onmouseout="onMouseOut('thumb1-19')" onclick="onDivClick('preChargeManager')"></div>
			<div id="thumb1-20" onmouseover="onMouse('thumb1-20')" onmouseout="onMouseOut('thumb1-20')" onclick="onDivClick('receiveChargeManager')"></div>
			<div id="thumb1-7" onmouseover="onMouse('thumb1-7')" onmouseout="onMouseOut('thumb1-7')" onclick="onDivClick('realTimeToRead')"></div>
			<div id="thumb1-18" onmouseover="onMouse('thumb1-18')" onmouseout="onMouseOut('thumb1-18')" onclick="onDivClick('meterInfoHistory')"></div>
			<div id="thumb1-8" onmouseover="onMouse('thumb1-8')" onmouseout="onMouseOut('thumb1-8')" onclick="onDivClick('loadPattern')"></div>
			<div id="thumb1-9" onmouseover="onMouse('thumb1-9')" onmouseout="onMouseOut('thumb1-9')" onclick="onDivClick('energyCurve')"></div>
			<div id="thumb1-10" onmouseover="onMouse('thumb1-10')" onmouseout="onMouseOut('thumb1-10')" onclick="onDivClick('gridironWastage')"></div>
			<div id="thumb1-11" onmouseover="onMouse('thumb1-11')" onmouseout="onMouseOut('thumb1-11')" onclick="onDivClick('systemManager')"></div>
			<div id="thumb1-12" onmouseover="onMouse('thumb1-12')" onmouseout="onMouseOut('thumb1-12')" onclick="onDivClick('meterInfoManager')"></div>
			<div id="thumb1-13" onmouseover="onMouse('thumb1-13')" onmouseout="onMouseOut('thumb1-13')" onclick="onDivClick('conCentratorManager')"></div>
			<div id="thumb1-14" onmouseover="onMouse('thumb1-14')" onmouseout="onMouseOut('thumb1-14')" onclick="onDivClick('meterTypeManager')"></div>
		</div><!-- end wrapper1 -->
		<div id="wrapper4">
			<div id="thumb1-15" title="注册" onclick="onDivClick('15')"></div>
			<div id="thumb1-17" title="帮助" onclick="onDivClick('17')"></div>
			<div id="thumb1-16" title="退出" onclick="onDivClick('16')"></div>
		</div>
		<c:if test="${x!=5}">
			<div id="text1">
				<span><font color="white" size="3">用户未注册或注册已过期，请点击注册按钮进行注册!</font></span>
			</div>
		</c:if>
	</div>
</body>
</html>