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
    
    <title>添加阶梯价格方案</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
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
		var i=1;
		//克隆行
		function addRow(row)
		{
			i++;
			var tab = document.getElementById('list');
			var newTR = row1.cloneNode(true);   
    	    newTR.id="row"+i;   
            row1.parentNode.insertAdjacentElement("beforeEnd",newTR);  
            //更新行号
            updNo();
		}
		//删除行
		function delRow(row)
		{
			var tab = document.getElementById('list');   
	    	if(tab.rows.length > 2)   
	    	{   
	       		tab.deleteRow(tab.rows.length-1);   
	   		}   
	  		else  
	   		{   
	       		Ext.MessageBox.alert('提示',"已经剩下最后一行，不能删除！");
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
		function checkForm()
		{
			var priceName=$("#priceName").val();
			var pstartTime=$("#startTime").val();
			var pstatus=$("#pstatus").val();
			var basePrice=$("#basePrice").val();
			if(priceName.length<2||priceName.length>50)
			{
				Ext.MessageBox.alert('提示','方案名称输入不合规范，请重新输入!');
				return;
			}
			if(0==pstartTime.length)
			{
				Ext.MessageBox.alert('提示','启用时间输入不合规范，请重新输入!');
				return;
			}
			if(0==basePrice.length)
			{
				Ext.MessageBox.alert('提示','基础费单价输入不合规范，请重新输入!');
				return;
			}
			var priceValue="[{pname:'"+priceName+"',pstartTime:'"+pstartTime+"',pstatus:"+pstatus+",ptype:${param.ptype}}]";
			var tab=document.getElementById('list');
			var objs='[';
			for(var i=1;i<tab.rows.length;i++)
			{
				var powerPrice=$("#row"+i).find("#powerPrice").val();
				if(0==powerPrice.length)
				{
					Ext.MessageBox.alert('提示','用热单价输入不合规范，请重新输入!');
					return;
				}
				var powerValue=$("#row"+i).find("#powerValue").val();
				if(0==powerValue.length)
				{
					Ext.MessageBox.alert('提示','用热量输入不合规范，请重新输入!');
					return;
				}
				objs+='{pdBasePrice:'+basePrice+',pdPowerPrice:'+powerPrice+',pdPower:'+powerValue+'},';
			}
			objs=objs.substring(0,objs.length-1);
			objs+=']';
			location.href='${pageContext.request.contextPath}/hod2000Price!doSave.do?priceValue='+encodeURI(encodeURI(priceValue))+'&details='+objs+'&method=2';
		}
		
		$(document).ready(function() {
             $('#priceAdd1Form input').keyup(trimkeyup);
 		});
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
														<B>当前位置</B>：[价格方案]-[
														<font color='red'>添加阶梯价格方案</font>]
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
																	<a href="javascript:addRow()"><div class="addrowbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="javascript:delRow()"><div class="delrowbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="javascript:checkForm()"><div class="savebtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/hod2000Price!doSelect.do?method=${param.ptype }"><div class="returnbtn"></div></a>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="priceAdd1Form">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
								<table cellpadding="0" cellspacing="0" border="0" align="center" id="tab">
									<tr height="40">
										<td>方案名称：</td>
										<td><input id="priceName" name="pname"/><span>必填，2-50位字符</span></td>
									</tr>
									<tr height="40">
										<td>启用日期：</td>
										<td><input class="Wdate" type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" id="startTime" name="pstartTime"/><span>必填</span></td>
									</tr>
									<tr height="40">
										<td>方案状态：</td>
										<td>
											<select id="pstatus">
												<option value="2">预定方案</option>
											</select>
										</td>
									</tr>
									<tr height="40">
										<td>基础费单价(元/月/平方米)：</td>
										<td>
											<input type="text" id="basePrice" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/><span>必填，只能输入数值型字符</span>
										</td>
									</tr>
									<tr height="210">
										<td colspan="2">
											<div style="overflow-y: auto;height: 200px;">
											<table cellpadding="0" cellspacing="1" border="0" width="100%" bgcolor="b5d6e6" class="list" id="list">
												<tr>
													<td class="STYLE3" >编号</td>
													<td class="STYLE3" >耗热量(KWh)</td>
													<td class="STYLE3" >用热单价(元/KWh)</td>
												</tr>
												<tr id="row1">
													<td class="STYLE1" id="num">1</td>
													<td class="STYLE1"><input type="text" name="powerValue" id="powerValue"  onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/></td>
													<td class="STYLE1"><input type="text" name="powerPrice" id="powerPrice"  onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')"/></td>
												</tr>
											</table>
											</div>
										</td>
									</tr>
									<tr height="100">
										<td colspan="2">
											提示：<br>
											1.阶梯价格=基础费+阶梯能量费。<br>
											2.基础费=面积数×基础费单价×时间（月）。<br>
											<c:if test="${param.ptype==2}">3.阶梯方案一：阶梯能量费=耗热量×阶梯单价。</c:if>
											<c:if test="${param.ptype==3}">3.阶梯方案二：阶梯能量费=Σ（每一阶耗热量*该阶段阶梯单价）。</c:if>
										</td>
									</tr>	
								</table>
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
