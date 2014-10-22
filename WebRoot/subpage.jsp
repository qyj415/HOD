<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/images/main/desk.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		var id=getUrlParams('id');
		if(id==0)
		{
			document.getElementById('tit').innerHTML='-- 客户信息';
			document.getElementById('div0').style.display='block';
		}
		else if(id==1)
		{
			document.getElementById('tit').innerHTML='-- 系统管理';
			document.getElementById('div1').style.display='block';
		}
		else if(id==2)
		{
			document.getElementById('tit').innerHTML='-- 能耗曲线';
			document.getElementById('div2').style.display='block';
		}
		else if(id==4)
		{
			document.getElementById('tit').innerHTML='-- 收费管理';
			document.getElementById('div4').style.display='block';
		}
		else if(id==5)
		{
			document.getElementById('tit').innerHTML='-- 预收款管理';
			document.getElementById('div5').style.display='block';
		}
		else if(id==6)
		{
			document.getElementById('tit').innerHTML='-- 集中器管理';
			document.getElementById('div6').style.display='block';
		}
		else
		{
			Ext.MessageBox.alert('提示','找不到页面!');
		}
		/*
		switch(id)
		{
			case "0"://客户信息
				document.getElementById('tit').innerHTML='-- 客户信息';
				document.getElementById('div0').style.display='block';
				break;
			case "1"://系统管理
				document.getElementById('tit').innerHTML='-- 系统管理';
				document.getElementById('div1').style.display='block';
				break;
			case "2"://能量损耗
				document.getElementById('tit').innerHTML='-- 能量损耗';
				document.getElementById('div2').style.display='block';
				break;
			case "4"://收费管理
				document.getElementById('tit').innerHTML='-- 收费管理';
				document.getElementById('div4').style.display='block';
				break;
			case "5"://预收款管理
				document.getElementById('tit').innerHTML='-- 预收款管理';
				document.getElementById('div5').style.display='block';
				break;
			case "6"://表计管理
				document.getElementById('tit').innerHTML='-- 集中器管理';
				document.getElementById('div6').style.display='block';
				break;
			defalut:
				Ext.MessageBox.alert('提示','找不到页面!');
				break;
		}
		*/
	});
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
		if(num=="addUserInfo")//开户
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/clientadd.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="editUserInfo")//变更处理
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/clientedit.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="selectUserInfo")//档案管理
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/clientlist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="delUserInfo")//销户
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/clientdel.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="4")//返回首页
			window.location.href='${pageContext.request.contextPath}/main.jsp';
		else if(num=="5")//回退按钮
			window.location.href='${pageContext.request.contextPath}/main.jsp';
		else if(num=="6")//帮助按钮
			window.open("${pageContext.request.contextPath}/help/help.html","_blank");
		else if(num=="7")//退出按钮
			Ext.MessageBox.confirm("请确认","你确定退出吗?",function(button,text){ 
		        if(button=="yes")
		        {
		        	window.parent.parent.location="${pageContext.request.contextPath}/login!logout.do";
		        }
	        }); 
		else if(num=="roleManager")//角色管理
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/rolelist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="operatorManager")//操作员管理
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/operatorlist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="operatorLogManager")//操作日志管理
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/operateloglist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="databaseBackupAndRestore")//数据库备份与还原
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/database!toUpdate.do';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="updatePassword")//修改密码
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/update/changepwd.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="normalUsageTotal")//正常用量统计
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/dataanalyze.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="abnormalUsageTotal")//异常用量统计
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/hod2000MeterInfoHistory!toException.do';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="receiveCharge")//收费
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/form/charge.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="receiveStatistics")//收费统计
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/receivelist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="receiveChargeSet")//收费参数设置
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/hod2000Receive!showParam.do';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="preCharge")//预收款
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/form/precharge.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="preStatistics")//预收款统计
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/prereceivelist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="preChargeSet")//预收款参数设置
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/hod2000PreReceive!showParam.do';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="initParam")//初始化参数
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/hod2000Concentrator!showParams.do';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		else if(num=="concentratorInfoManager")//集中器维护
			if(findPermission(num))
				window.location.href='${pageContext.request.contextPath}/show/concentratorlist.jsp';
			else
				Ext.MessageBox.alert('提示','没有权限！');
		/*
		switch(num)
		{
			case "addUserInfo"://开户
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/clientadd.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "editUserInfo"://变更处理
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/clientedit.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "selectUserInfo"://档案管理
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/clientlist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "delUserInfo"://销户
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/clientdel.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "4"://返回首页
				window.location.href='${pageContext.request.contextPath}/main.jsp';
				break;
			case "5"://回退按钮
				window.location.href='${pageContext.request.contextPath}/main.jsp';
				break;
			case "6"://帮助按钮
				window.open("${pageContext.request.contextPath}/help/help.html","_blank");
				break;
			case "7"://退出按钮
				Ext.MessageBox.confirm("请确认","你确定退出吗?",function(button,text){ 
			        if(button=="yes")
			        {
			        	window.parent.parent.location="${pageContext.request.contextPath}/login!logout.do";
			        }
		        }); 
				break;
			case "roleManager"://角色管理
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/rolelist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "operatorManager"://操作员管理
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/operatorlist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "operatorLogManager"://操作日志管理
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/operateloglist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "databaseBackupAndRestore"://数据库备份与还原
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/database!toUpdate.do';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "updatePassword"://修改密码
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/update/changepwd.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "normalUsageTotal"://正常用量统计
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/dataanalyze.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "abnormalUsageTotal"://异常用量统计
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/hod2000MeterInfoHistory!toException.do';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "receiveCharge"://收费
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/form/charge.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "receiveStatistics"://收费统计
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/receivelist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "receiveChargeSet"://收费参数设置
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/hod2000Receive!showParam.do';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "preCharge"://预收款
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/form/precharge.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "preStatistics"://预收款统计
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/prereceivelist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "preChargeSet"://预收款参数设置
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/hod2000PreReceive!showParam.do';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "initParam"://初始化参数
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/hod2000Concentrator!showParams.do';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
			case "concentratorInfoManager"://集中器维护
				if(findPermission(num))
					window.location.href='${pageContext.request.contextPath}/show/concentratorlist.jsp';
				else
					Ext.MessageBox.alert('提示','没有权限！');
				break;
		}
		*/
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
<body>
	<div class="deskDiv">
		<div id="wrapper5">
			<img align="absmiddle" src="${pageContext.request.contextPath}/images/logo.gif" width="122px" height="48px"/>
			<span class="titleDiv">城市建筑智慧能源管理系统</span>
			<span id="tit"></span>
		</div>
		<div id="wrapper7"><div class="userName">${user.loginName }</div></div>
		<div id="wrapper3"></div>
		<div id="wrapper2"></div>
		<div id="div0" style="display: none;">
			<div id="thumb2-1" onmouseover="onMouse('thumb2-1')" onmouseout="onMouseOut('thumb2-1')" onclick="onDivClick('addUserInfo')"></div>
			<div id="thumb2-2" onmouseover="onMouse('thumb2-2')" onmouseout="onMouseOut('thumb2-2')" onclick="onDivClick('editUserInfo')"></div>
			<div id="thumb2-3" onmouseover="onMouse('thumb2-3')" onmouseout="onMouseOut('thumb2-3')" onclick="onDivClick('selectUserInfo')"></div>
			<div id="thumb2-4" onmouseover="onMouse('thumb2-4')" onmouseout="onMouseOut('thumb2-4')" onclick="onDivClick('delUserInfo')"></div>
		</div>
		<div id="div1" style="display: none;">
			<div id="thumb2-10" onmouseover="onMouse('thumb2-10')" onmouseout="onMouseOut('thumb2-10')" onclick="onDivClick('roleManager')"></div>
			<div id="thumb2-11" onmouseover="onMouse('thumb2-11')" onmouseout="onMouseOut('thumb2-11')" onclick="onDivClick('operatorManager')"></div>
			<div id="thumb2-12" onmouseover="onMouse('thumb2-12')" onmouseout="onMouseOut('thumb2-12')" onclick="onDivClick('operatorLogManager')"></div>
			<div id="thumb2-15" onmouseover="onMouse('thumb2-15')" onmouseout="onMouseOut('thumb2-15')" onclick="onDivClick('databaseBackupAndRestore')"></div>
			<div id="thumb2-16" onmouseover="onMouse('thumb2-16')" onmouseout="onMouseOut('thumb2-16')" onclick="onDivClick('updatePassword')"></div>
		</div>
		<div id="div2" style="display: none;">
			<div id="thumb2-17" onmouseover="onMouse('thumb2-17')" onmouseout="onMouseOut('thumb2-17')" onclick="onDivClick('normalUsageTotal')"></div>
			<div id="thumb2-18" onmouseover="onMouse('thumb2-18')" onmouseout="onMouseOut('thumb2-18')" onclick="onDivClick('abnormalUsageTotal')"></div>
		</div>
		<div id="div4" style="display: none;">
			<div id="thumb2-22" onmouseover="onMouse('thumb2-22')" onmouseout="onMouseOut('thumb2-22')" onclick="onDivClick('receiveCharge')"></div>
			<div id="thumb2-23" onmouseover="onMouse('thumb2-23')" onmouseout="onMouseOut('thumb2-23')" onclick="onDivClick('receiveStatistics')"></div>
			<div id="thumb2-24" onmouseover="onMouse('thumb2-24')" onmouseout="onMouseOut('thumb2-24')" onclick="onDivClick('receiveChargeSet')"></div>
		</div>
		<div id="div5" style="display: none;">
			<div id="thumb2-25" onmouseover="onMouse('thumb2-25')" onmouseout="onMouseOut('thumb2-25')" onclick="onDivClick('preCharge')"></div>
			<div id="thumb2-26" onmouseover="onMouse('thumb2-26')" onmouseout="onMouseOut('thumb2-26')" onclick="onDivClick('preStatistics')"></div>
			<div id="thumb2-27" onmouseover="onMouse('thumb2-27')" onmouseout="onMouseOut('thumb2-27')" onclick="onDivClick('preChargeSet')"></div>
		</div>
		<div id="div6" style="display: none;">
			<div id="thumb2-28" onmouseover="onMouse('thumb2-28')" onmouseout="onMouseOut('thumb2-28')" onclick="onDivClick('initParam')"></div>
			<div id="thumb2-29" onmouseover="onMouse('thumb2-29')" onmouseout="onMouseOut('thumb2-29')" onclick="onDivClick('concentratorInfoManager')"></div>
		</div>
		<div id="wrapper6">
			<div id="thumb2-5" title="首页" onclick="onDivClick('4')"></div>
			<div id="thumb2-7" title="返回" onclick="onDivClick('5')"></div>
			<div id="thumb2-8" title="帮助" onclick="onDivClick('6')"></div>
			<div id="thumb2-9" title="退出" onclick="onDivClick('7')"></div>
		</div>
	</div>
</body>