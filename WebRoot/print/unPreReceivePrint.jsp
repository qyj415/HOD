<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>预收款统计分析-未收款明细-打印</title>	
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
											<td class="STYLE3">地理位置</td>
											<td class="STYLE3">用户名</td>
											<td class="STYLE3">联系电话</td>
											<td class="STYLE3">面积数(㎡)</td>
											<td class="STYLE3">系数单价(元/月/平方米)</td>
											<td class="STYLE3">供暖月数</td>
											<td class="STYLE3">未收款金额(元)</td>
										</tr>
										<c:forEach items="${unCollect}" var="v" varStatus="num">
											<tr>
												<td class="STYLE1">${num.index+1 }</td>
												<td class="STYLE1">${v.address }</td>
												<td class="STYLE1">${v.clientName }</td>
												<td class="STYLE1">${v.clientTel }</td>
												<td class="STYLE1">${v.roomSize }</td>
												<td class="STYLE1">${v.prFactor }</td>
												<td class="STYLE1">${v.prMonths }</td>
												<td class="STYLE1">${v.prMoney }</td>
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