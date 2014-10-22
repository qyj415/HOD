<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>房间信息</title>
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<script type="text/javascript">
			function doClose()
			{
				var allbox=document.getElementsByName("ckId");
				var delIds="";
				for(i=0;i<allbox.length;i++){
					if(allbox[i].checked){
						delIds=delIds+allbox[i].value+","
					}
				}
				if(""==delIds){
					 Ext.MessageBox.alert('提示','请选择至少一条记录!');
				}
				else
				{
					delIds=delIds.substr(0,delIds.length-1);
					window.returnValue =delIds;
					window.close();
				} 
			}
		</script>
	</head>
	<body style="overflow-y: auto;">
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
													<td width="95%" class="STYLE2"></td>
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
								<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" class="list" id="datatable">
									<tr>
										<td class="STYLE3" width="25px">
											<input type="checkbox" onclick="selectAll('1');" id="dataList0" />
										</td>
										<td class="STYLE5">序号</td>
										<td class="STYLE3">地理位置</td>
										<td class="STYLE3">表号</td>
										<td class="STYLE3">表底数</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${dataList}" var="v" varStatus="num">
										<tr>
											<td height="20" bgcolor="#FFFFFF"  class="STYLE3">
												<div align="center"><input type="checkbox" name="ckId" id="dataList" value="${v.roomId}" /></div>
											</td>
											<td class="STYLE1">${num.index+1}</td>
											<td class="STYLE1">${v.meterPositionName}</td>
											<td class="STYLE1">${v.meterName}</td>
											<td class="STYLE1">${v.meterInit}</td>
										</tr>
									</c:forEach>
									<!--------------------------------------------------->
								</table>
						</td>
						<td width="8" class="tab_15">&nbsp;</td>
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

