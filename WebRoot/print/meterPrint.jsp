<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>表计管理-打印</title>	
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/etimbo/src/css/print.css" type="text/css" media="print" />
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/etimbo/src/css/print-preview.css" type="text/css" media="screen">
		<script type="text/javascript">
		</script>
		
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
									 class="list" id="datatable" bgcolor="b5d6e6" >
									<tr>
										<td class="STYLE5">序号</td>
										<td class="STYLE3">表号</td>
										<td class="STYLE3">表底数</td>
										<td class="STYLE3">口径</td>
										<td class="STYLE3">波特率</td>
										<td class="STYLE3">表计型号</td>
										<td class="STYLE3" width="200">地理位置</td>
										<td class="STYLE3">集中器编号</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${dataList}" var="v" varStatus="num">
										<tr>
											<td class="STYLE3">&nbsp;${num.index+1}</td>
											<td class="STYLE1">&nbsp;${v.meterName}</td>
											<td class="STYLE1">&nbsp;${v.meterInit}</td>
											<td class="STYLE1">&nbsp;${v.meterCaliber}</td>
											<td class="STYLE1">
												<c:if test="${v.meterBaudrate==0}">300</c:if>
												<c:if test="${v.meterBaudrate==1}">600</c:if>
												<c:if test="${v.meterBaudrate==2}">1200</c:if>
												<c:if test="${v.meterBaudrate==3}">2400</c:if>
												<c:if test="${v.meterBaudrate==4}">4800</c:if>
												<c:if test="${v.meterBaudrate==5}">7200</c:if>
												<c:if test="${v.meterBaudrate==6}">9600</c:if>
												<c:if test="${v.meterBaudrate==7}">19200</c:if>
											</td>
											<td class="STYLE1">&nbsp;${v.hod2000MeterType.typeName}</td>
											<td class="STYLE1">&nbsp;${v.meterPositionName}</td>
											<td class="STYLE1">&nbsp;${v.hod2000Concentrator.conNumber}</td>
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