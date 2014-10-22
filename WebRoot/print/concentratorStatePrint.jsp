<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>集中器状态-打印</title>	
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
									 class="list" id="datatable" bgcolor="b5d6e6" >
									<tr>
										<td class="STYLE5">序号</td>
										<td class="STYLE3">集中器编号</td>
										<td class="STYLE3">安装位置</td>
										<td class="STYLE3">内存状态</td>
										<td class="STYLE3">通讯口1</td>
										<td class="STYLE3">通讯口2</td>
										<td class="STYLE3">通讯口3</td>
										<td class="STYLE3">在线状态</td>
										<td class="STYLE3">GPRS信号</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${list}" var="v" varStatus="num">
										<tr>
											<td class="STYLE3">&nbsp;${num.index+1}</td>
											<td class="STYLE1">&nbsp;${v.conNum }</td>
											<td class="STYLE1">&nbsp;${v.conAddress }</td>
											<td class="STYLE1">
												<c:if test="${v.conFlashStatus==0}">&nbsp;正常</c:if>
												<c:if test="${v.conFlashStatus==1}">&nbsp;<font color="red">错误</font></c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.conCom1Status==0}">&nbsp;正常</c:if>
												<c:if test="${v.conCom1Status==1}">&nbsp;<font color="red">错误</font></c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.conCom2Status==0}">&nbsp;正常</c:if>
												<c:if test="${v.conCom2Status==1}">&nbsp;<font color="red">错误</font></c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.conCom3Status==0}">&nbsp;正常</c:if>
												<c:if test="${v.conCom3Status==1}">&nbsp;<font color="red">错误</font></c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.conIsonline==1}">&nbsp;在线</c:if>
												<c:if test="${v.conIsonline==2}">&nbsp;离线</c:if>
											</td>
											<td class="STYLE1">
												<c:if test="${v.conStrong==0}">&nbsp;无</c:if>
												<c:if test="${v.conStrong==1}">&nbsp;弱</c:if>
												<c:if test="${v.conStrong==2}">&nbsp;一般</c:if>
												<c:if test="${v.conStrong==3}">&nbsp;强</c:if>
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