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
    
    <title>集中器设备匹配</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/loadSelect.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript">
		//让火狐浏览器支持insertAdjacentElement方法
		if(typeof HTMLElement!="undefined" && !HTMLElement.prototype.insertAdjacentElement) 
		{ 
	     	HTMLElement.prototype.insertAdjacentElement = function(where,parsedNode) 
	     	{ 
	        	switch (where) 
	        	{ 
		            case 'beforeBegin': 
		                this.parentNode.insertBefore(parsedNode,this) 
		                break; 
		            case 'afterBegin': 
		                this.insertBefore(parsedNode,this.firstChild); 
		                break; 
		            case 'beforeEnd': 
		                this.appendChild(parsedNode); 
		                break; 
		            case 'afterEnd': 
		                if (this.nextSibling) this.parentNode.insertBefore(parsedNode,this.nextSibling); 
		                    else this.parentNode.appendChild(parsedNode); 
		                break; 
	         	} 
	     	} 
	     	HTMLElement.prototype.insertAdjacentHTML = function (where,htmlStr) 
	     	{ 
		         var r = this.ownerDocument.createRange(); 
		         r.setStartBefore(this); 
		         var parsedHTML = r.createContextualFragment(htmlStr); 
		         this.insertAdjacentElement(where,parsedHTML) 
	     	} 
	
		     HTMLElement.prototype.insertAdjacentText = function (where,txtStr) 
		     { 
		         var parsedText = document.createTextNode(txtStr) 
		         this.insertAdjacentElement(where,parsedText) 
		     } 
		}
		function checkForm()
		{
			/*var slaveConNumber=$("#slaveConNumber").val();
			if(slaveConNumber.length==0)
			{
				alert('集中器从机编号输入不规范，请重新输入...');
				return false;
			}*/
			var rf=$("#rf").val();
			if(rf<1||rf>16)
			{
				Ext.MessageBox.alert('提示','RF信道设置范围为1~16!');
				return false;
			}
			var slaveConNums="";
			var tab=document.getElementById('list');
			for(var i=1;i<tab.rows.length;i++)
			{
				var slaveConNum=$("#row"+i).find("#slaveConNum").val();
				if(slaveConNum.length>0&&slaveConNum.length!=8)
				{
					Ext.MessageBox.alert('提示','集中器编号输入不规范，请重新输入!');
					return false;
				}
				slaveConNums+=slaveConNum+",";
			}
			if(0==slaveConNums.length)
			{
				Ext.MessageBox.alert('提示','集中器的从机编号不能为空！');
				return false;
			}
			slaveConNums=slaveConNums.substring(0,slaveConNums.length-1);
			$("#slaveConNums").val(slaveConNums);
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
		}
		//选择集中器信息
		function showDia()
		{
			var params=window.showModalDialog('${pageContext.request.contextPath}/share/selectConcentrator.jsp',window,'dialogHeight=450px;dialogWidth=750px;center=yes;status=no;');
			if(params!=null)
			{
				$("#slaveConId").val(params[0]);
				$("#slaveConNumber").val(params[1]);
			}
		}
		//清空从机
		function delContentx()
		{
			$("#slaveConNumber").val('');
		}
		var i=1;
		//添加行
		function addRow(row)
		{
			i++;
			var tab = document.getElementById('list');   
	    	if(tab.rows.length > 128)
	    	{
	    		Ext.MessageBox.alert('提示','一个主机最多能添加128个从机');
	    		return;
	    	}   
			var newTR = row.parentNode.parentNode.cloneNode(true);   
    	    newTR.id="row"+i;   
            row.parentNode.parentNode.parentNode.insertAdjacentElement("beforeEnd",newTR);  
            updNo();
		}
		//删除行
		function delRow(row)
		{
			var tab = document.getElementById('list');   
	    	if(tab.rows.length > 2)   
	    	{   
	       		tab.deleteRow(row.parentNode.parentNode.rowIndex);
	   		}   
	  		else  
	   		{   
	       		alert("已经剩下最后一行，不能删除！");
	   		}   
	   		updNo();  
		}
		//更新行号
		function updNo()
		{
			 var table=document.getElementById('list');
			 for(var i=1;i<table.rows.length;i++)
			 {
			 	table.rows[i].cells[0].firstChild.nodeValue=i;
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
														<B>当前位置</B>：[集中器管理]-[集中器维护]-[
														<font color='red'>集中器设备匹配</font>]
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
																	<a href="${pageContext.request.contextPath}/show/concentratorlist.jsp"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp" target="mainFrame"><div class="homebtn"></div></a>
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
							<td width="8" class="tab_12">
							</td>
							<td>
							<!-- 可以写form -->
							<form action="${pageContext.request.contextPath}/hod2000Concentrator!doMapping.do" method="post">
								<input type="hidden" name="conId" id="conId" value="${conId }"/>
								<input type="hidden" name="oldSlaveConId" value="${slaveConId }">
								<input type="hidden" name="slaveConNums" id="slaveConNums"/>
								<table cellpadding="0" cellspacing="0" border="0" align="center" id="tab">
									<!-- 地址信息开始 -->
									<tr><td colspan="2" >&nbsp;</td></tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0" border="0">
											<tr>
												<td>主机集中器编号：</td>
												<td>
													<input type="text" value="${conNum }" readonly="readonly" name="conNum" style="border: 1px solid gray;">
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<td style="text-align:right;">主机RF信道设置：</td>
												<td><input type="text" name="rf" id="rf" value="${rf }" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="2"/><span>范围：1~16</span></td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												
												<td colspan="2">
													<div style="overflow-y: auto;height: 200px;">
													<table cellpadding="0" cellspacing="1" border="0" align="center" width="400" bgcolor="b5d6e6" class="list" id="list">
														<tr>
															<td class="STYLE3" >编号</td>
															<td class="STYLE3" >从机编号</td>
															<td class="STYLE3" >操作</td>
														</tr>
														<c:if test="${empty dataList}">
															<tr id="row1">
																<td class="STYLE1" id="num">1</td>
																<td class="STYLE1"><input type="text" name="slaveConNum" id="slaveConNum" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="8"/></td>
																<td class="STYLE1">
																	<input type="button" value="添加从机" onclick="addRow(this)">
																	<input type="button" value="删除从机" onclick="delRow(this)">
																</td>
															</tr>
														</c:if>
														<c:if test="${!empty dataList}">
															<c:forEach items="${dataList}" var="v" varStatus="num">
																<tr id="row1">
																	<td class="STYLE1" id="num">${num.index+1 }</td>
																	<td class="STYLE1"><input type="text" name="slaveConNum" value="${v.conNumber }" id="slaveConNum" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="8"/></td>
																	<td class="STYLE1">
																		<input type="button" value="添加从机" onclick="addRow(this)">
																		<input type="button" value="删除从机" onclick="delRow(this)">
																	</td>
																</tr>
															</c:forEach>
														</c:if>
													</table>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<td colspan="2" style="text-align: center;">
													<input type="submit" class="savebtn" value="" onclick="return checkForm()">
												</td>
											</tr>
											</table>
										</td>
									<tr>
									
									
								</table>
							</form>
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
