<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>单元信息</title>
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
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
													<td width="95%" class="STYLE2">
														<B>当前位置</B>：[地址信息]-[<font color='red'>${buildingName }</font>]-[
														单元信息]
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
																	<a href="${pageContext.request.contextPath}/form/unitadd.jsp?buildingId=${buildingId }"><div class="addbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a id="deleteById" href="#" onclick="doDeleteX('${pageContext.request.contextPath}/hod2000Unit!doDelete.do?buildingId=${buildingId }');"><div class="delbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/map/AddressMap.jsp;" target="mainFrame"><div class="mapbtn"></div></a>
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
								<table width="100%" border="0" cellpadding="0" cellspacing="1"
									bgcolor="b5d6e6" class="list" id="datatable">
									<tr>
										<td class="STYLE3" width="25px">
											<input type="checkbox" onclick="selectAll('1');" id="dataList0" />
										</td>
										<td class="STYLE5">序号</td>
										<td class="STYLE3">所属楼栋</td>
										<td class="STYLE3">单元号</td>
										<td class="STYLE3">备注</td>
										<td class="STYLE3">
											基本操作
										</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${dataList}" var="v" varStatus="num">
										<tr>
											<td height="20" bgcolor="#FFFFFF"  class="STYLE3"><div align="center"><input type="checkbox" name="ckId" id="dataList" value="${v.unitId}" /></div></td>
											<td class="STYLE1">${num.index+1}</td>
											<td class="STYLE1">${buildingName}</td>
											<td class="STYLE1"><a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Room!doSelect.do?unitId=${v.unitId}">${v.unitName}</a></td>
											<td class="STYLE1">${v.unitRemark}</td>
											<td class="STYLE1">
												<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Unit!toUpdate.do?id=${v.unitId}&buildingId=${buildingId }">【编辑】</a>
												<a style="color:#f60" id="deleteById" href="${pageContext.request.contextPath}/hod2000Unit!doDelete.do?delIds=${v.unitId}&buildingId=${buildingId }" onclick="return doDeleteById();">【删除】</a>
											</td>
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
							<td>
								<jsp:include page="/inc/page.jsp" flush="true">
									<jsp:param name="params" value="&buildingId=${buildingId}"/>
								</jsp:include>
							</td>
							<td width="16" class="tab_20">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

