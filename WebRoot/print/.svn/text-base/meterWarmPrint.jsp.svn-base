<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>表计报警-打印</title>	
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/etimbo/src/css/print.css" type="text/css" media="print" />
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/etimbo/src/css/print-preview.css" type="text/css" media="screen">
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			
					
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="1"
									 class="list" bgcolor="b5d6e6" id="datatable">
									<tr>
											<td class="STYLE5">序号</td>
											<td class="STYLE3">表号</td>
											<td class="STYLE3">地址</td>
											<!-- <td class="STYLE3">阀门状态</td> -->
											<td class="STYLE3">电池状态</td>
											<td class="STYLE3">在线状态</td>
											<td class="STYLE3">内存状态</td>
											<td class="STYLE3">流量传感器状态</td>
											<td class="STYLE3">供水温度传感器状态</td>
											<td class="STYLE3">回水温度传感器状态</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${dataList}" var="v" varStatus="num">
										<tr>
											<td class="STYLE3">&nbsp;${num.index+1}</td>
											<td class="STYLE1">&nbsp;${v.meterName}</td>
											<td class="STYLE1">&nbsp;${v.address}</td>
											<!-- 
											<td class="STYLE1">
												<c:if test="${v.valveStatus==0}">&nbsp;阀开</c:if>
												<c:if test="${v.valveStatus==1}">&nbsp;阀关</c:if>
												<c:if test="${v.valveStatus==2}">&nbsp;<font color="red">异常</font></c:if>
											</td>
											 -->
											<td class="STYLE1">
												<c:if test="${v.batteryStatus==0}">&nbsp;正常</c:if>
												<c:if test="${v.batteryStatus==1}">&nbsp;<font color="red">欠压</font></c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.isOnline==1}">&nbsp;在线</c:if>
												<c:if test="${v.isOnline==2}">&nbsp;离线</c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.eepromStatus==0}">&nbsp;正常</c:if>
												<c:if test="${v.eepromStatus==1}">&nbsp;错误</c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.flowsensorStatus==0}">&nbsp;正常</c:if>
												<c:if test="${v.flowsensorStatus==1}">&nbsp;错误</c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.tepdownStatus==0}">&nbsp;正常</c:if>
												<c:if test="${v.tepdownStatus==1}">&nbsp;错误</c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.tepupStatus==0}">&nbsp;正常</c:if>
												<c:if test="${v.tepupStatus==1}">&nbsp;错误</c:if>
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
			<tr style="height:100px;"></tr>
		</table>
		<div id="aside" class="grid_3 push_1">
		    <a class="print-preview" onclick="javascript:print();">print</a>
		</div>
		
	</body>
</html>