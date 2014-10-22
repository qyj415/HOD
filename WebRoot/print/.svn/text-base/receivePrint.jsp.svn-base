<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>收费管理统计分析-已收款明细-打印</title>	
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
											<td class="STYLE3">用户姓名</td>
											<td class="STYLE3">预付款金额</td>
											<td class="STYLE3">退款金额</td>
											<td class="STYLE3">补交金额</td>
											<td class="STYLE3">应交金额</td>
											<td class="STYLE3">收费方案</td>
											<td class="STYLE3">收费时间</td>
											<td class="STYLE3">操作员</td>
										</tr>
										<c:forEach items="${dataList}" var="v" varStatus="num">
											<tr>
												<td class="STYLE1">${num.index+1 }</td>
												<td class="STYLE1">${v.address }</td>
												<td class="STYLE1">${v.clientName }</td>
												<td class="STYLE1">${v.prMoney }</td>
												<td class="STYLE1">${v.returnMoney }</td>
												<td class="STYLE1">${v.fillMoney }</td>
												<td class="STYLE1">${v.rmoney }</td>
												<td class="STYLE1">
													<c:if test="${v.rtype==1 }">多退少不补</c:if>
													<c:if test="${v.rtype==2 }">多退少补</c:if>
												</td>
												<td class="STYLE1"><f:formatDate value="${v.rdate }" pattern="yyyy-MM-dd"/></td>
												<td class="STYLE1">${v.roperator }</td>
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