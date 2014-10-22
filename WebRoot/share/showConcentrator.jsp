<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>集中器管理</title>
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"> </script>
		<script type="text/javascript">
			function doClose(id,number)
			{
				//var params=[id,number]
				//window.returnValue =params;
				//window.close();
				var allbox=document.getElementsByName("ckId");
				var delIds="";
				var userName='';
				for(i=0;i<allbox.length;i++){
					if(allbox[i].checked){
						delIds=delIds+allbox[i].value+","
						userName+=allbox[i].parentNode.parentNode.parentNode.cells[2].firstChild.nodeValue+",";  
					}
				}
				if(""==delIds){
					 Ext.MessageBox.alert('提示','请选择至少一条记录!');
				}
				else
				{
					delIds=delIds.substr(0,delIds.length-1);
					userName=userName.substr(0,userName.length-1);
					window.returnValue =[delIds,userName];
					window.close();
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
														<B>当前位置</B>：[<font color='red'>集中器管理</font>]
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
																	<a id="deleteById" href="#" onclick="doClose()"><div class="confirmbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="javaScript:window.close();"><div class="closebtn"></div></a>
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
									<form method="post" action="${pageContext.request.contextPath}/hod2000Concentrator!doSelect2.do" style="margin: 0px">
							 			<%@include file="/inc/concentratorSearch2.jsp" %>
									</form>
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
								<table border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" class="list" id="datatable" width="100%">
									<tr>
										<td class="STYLE3" width="25px">
											<input type="checkbox" onclick="selectAll('1');" id="dataList0" />
										</td>
										<td class="STYLE5">序号</td>
										<td class="STYLE3">集中器编号</td>
										<td class="STYLE3">安装位置</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${dataList}" var="v" varStatus="num">
										<tr>
											<td height="20" bgcolor="#FFFFFF"  class="STYLE3">
												<div align="center"><input type="checkbox" name="ckId" id="dataList" value="${v.conId}" /></div>
											</td>
											<td class="STYLE1">${num.index+1}</td>
											<td class="STYLE1">${v.conNumber}</td>
											<td class="STYLE1">${v.conPositionName}</td>
										</tr>
									</c:forEach>
									<!--------------------------------------------------->
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
							<td><%@ include file="/inc/page.jsp"%></td>
							<td width="16" class="tab_20">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

