<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>价格方案</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
			.sp1{
				background-image: url("${pageContext.request.contextPath}/images/btn2.jpg");
				height: 30px;
				width:180px;
				color: white;
				cursor: pointer;
				display:inline-block;
				line-height: 30px;
				text-align: center;
			}
			.sp2{
				background-image: url("${pageContext.request.contextPath}/images/btn.jpg");
				color: white;
				height: 30px;
				display:inline-block;
				width:180px;
				cursor: pointer;
				line-height: 30px;
				text-align: center;
				background-color: white;
			}
			.sp3{
				background-image: url("${pageContext.request.contextPath}/images/btn.jpg");
				color: white;
				height: 30px;
				display:inline-block;
				width:180px;
				cursor: pointer;
				line-height: 30px;
				text-align: center;
				background-color: white;
			}
			#history{
				border:1px solid #2EB4B9;
				height: 600px;
				overflow:auto;
			}
			#read{
				border: 1px solid #2EB4B9;
				display: none;
				height: 600px;
				overflow:auto;
			}
			#total{
				border: 1px solid #2EB4B9;
				display: none;
				height: 600px;
				overflow:auto;
			}
			form {
				margin: 0px;
			}
			.botton{ color:#F00; cursor:pointer;}
			.mybody{width:600px; margin:0 auto; height:1500px; border:1px solid #ccc; padding:20px 25px; background:#fff}
			#cwxBg{ position:absolute; display:none; background:#000; width:100%; height:100%; left:0px; top:0px; z-index:1000;}
			#cwxWd{ position:absolute; display:none; border:10px solid #CCC; padding:10px;background:#FFF; z-index:1500;}
			#cwxCn{ background:#FFF; display:block;}
			.imgd{ width:400px; height:300px;}
		</style>
		<script type="text/javascript">
			$(function(){
				if(${param.method }==1)
				{
					setTab('history','read','total','sp1','sp2','sp3');
				}
				else if(${param.method }==2)
				{
					setTab('read','history','total','sp2','sp1','sp3');
				}
				else
				{
					setTab('total','history','read','sp3','sp1','sp1');
				}
			});
			function setTab(a,b,c,d,e,f){
			  document.getElementById(a).style.display='inline-block';
			  document.getElementById(b).style.display='none';
			  document.getElementById(c).style.display='none';
			  document.getElementById(d).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn2.jpg')";
			  document.getElementById(e).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			  document.getElementById(f).style.backgroundImage="url('${pageContext.request.contextPath}/images/btn.jpg')";
			}
			//查看明细
			function searchInfo(id,method)
			{
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodPrice!showDetail.do?id="+id,
					async : false,
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							var len=result.data.length;
							if(len>0)
							{
								if(method==1)
									var neirong="<div style='width:420px;heigth:500px;'><table width='100%' border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"b5d6e6\" class=\"list\" id=\"datatable\"><tr><td class='STYLE1'>序号</td><td class='STYLE1'>基础费单价(元/月/平方米)</td><td class='STYLE1'>用热单价(元/KWh)</td></tr>";
								else
									var neirong="<div style='width:550px;heigth:500px;'><table width='100%' border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"b5d6e6\" class=\"list\" id=\"datatable\"><tr><td class='STYLE1'>序号</td><td class='STYLE1'>基础费单价(元/月/平方米)</td><td class='STYLE1'>用热单价(元/KWh)</td><td class='STYLE1'>耗热量(KWh)</td></tr>";
								for(var i=0;i<len;i++)
								{
									var pdBasePrice=result.data[i].pdBasePrice;
									var pdPowerPrice=result.data[i].pdPowerPrice;
									var pdPower=result.data[i].pdPower;
									var num=i+1;
									if(method==1)
										neirong+="<tr><td class='STYLE1'>"+num+"</td><td class='STYLE1'>"+pdBasePrice+"</td><td class='STYLE1'>"+pdPowerPrice+"</td></tr>";
									else
										neirong+="<tr><td class='STYLE1'>"+num+"</td><td class='STYLE1'>"+pdBasePrice+"</td><td class='STYLE1'>"+pdPowerPrice+"</td><td class='STYLE1'>"+pdPower+"</td></tr>";
								}
								neirong+="</table></div>";
								cwxbox.box.show(neirong);
							}
						}
						else
						{
							Ext.MessageBox.alert('提示','请求失败!');
						}
					},
					failure : function(res) {
						Ext.MessageBox.alert('提示','请求失败!');
					}
				});
			}
		</script>
  </head>
  
<body onload="load('history',15),load('read',15),load('total',15),loads('history',100),loads('read',100),loads('total',100)">
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
														<B>当前位置</B>：[
														<font color='red'>价格方案</font>]
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
																	<a href="${pageContext.request.contextPath}/main.jsp"><div class="homebtn"></div></a>
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
								<span id="sp1" onclick="setTab('history','read','total','sp1','sp2','sp3')" class="sp1">定额价格方案</span>
								<span id="sp2" onclick="setTab('read','history','total','sp2','sp1','sp3')" class="sp2">阶梯价格方案一</span>
								<span id="sp3" onclick="setTab('total','history','read','sp3','sp1','sp2')" class="sp3">阶梯价格方案二</span>
								<div></div>
								<div id="history" style="background-color: white;width: 100%;">
									<form action="" method="post">
										<%@include file="/inc/priceSearch.jsp" %>
									</form>
									<table border="0" cellspacing="0" cellpadding="0" style="float: left;">
											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="1"
														bgcolor="b5d6e6" class="list" id="datatable">
														<tr>
															<td class="STYLE5">序号</td>
															<td class="STYLE3">方案名称</td>
															<td class="STYLE3">方案类型</td>
															<td class="STYLE3">方案状态</td>
															<td class="STYLE3">启用日期</td>
															<td class="STYLE3">明细</td>
															<td class="STYLE3">操作</td>
														</tr>
														<c:forEach items="${dataList}" var="v" varStatus="num">
															<tr>
																<td class="STYLE1">${num.index+1 }</td>
																<td class="STYLE1">${v.pname }</td>
																<td class="STYLE1">定额价格方案</td>
																<td class="STYLE1">
																	<c:if test="${v.pstatus==1}">当前方案</c:if>
																	<c:if test="${v.pstatus==2}">预定方案</c:if>
																	<c:if test="${v.pstatus==3}">过期方案</c:if>
																</td>
																<td class="STYLE1"><f:formatDate value="${v.pstartTime }" pattern="yyyy-MM-dd"/></td>
																<td class="STYLE1">
																	<a href="javaScript:searchInfo(${v.pid },1);" style="color:#f60">【查看明细】</a>
																</td>
																<td class="STYLE1">
																	<c:if test="${v.pstatus==2}">
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!toUpdate.do?id=${v.pid }&method=1">【编辑】</a>
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!doDelete.do?delIds=${v.pid }&method=1" onclick="return doDeleteById();">【删除】</a>
																	</c:if>
																	<c:if test="${v.pstatus==3}">
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!doDelete.do?delIds=${v.pid }&method=1" onclick="return doDeleteById();">【删除】</a>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
									</table>
								</div>
								<div id="read" style="background-color: white;width: 100%;">
									<form action="" method="post">
										<%@include file="/inc/priceSearch1.jsp" %>
									</form>
									<table border="0" cellspacing="0" cellpadding="0" style="float: left;">
											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="1"
														bgcolor="b5d6e6" class="list" id="datatable">
														<tr>
															<td class="STYLE5">序号</td>
															<td class="STYLE3">方案名称</td>
															<td class="STYLE3">方案类型</td>
															<td class="STYLE3">方案状态</td>
															<td class="STYLE3">启用日期</td>
															<td class="STYLE3">明细</td>
															<td class="STYLE3">操作</td>
														</tr>
														<c:forEach items="${list}" var="v" varStatus="num">
															<tr>
																<td class="STYLE1">${num.index+1 }</td>
																<td class="STYLE1">${v.pname }</td>
																<td class="STYLE1">阶梯价格方案</td>
																<td class="STYLE1">
																	<c:if test="${v.pstatus==1}">当前方案</c:if>
																	<c:if test="${v.pstatus==2}">预定方案</c:if>
																	<c:if test="${v.pstatus==3}">过期方案</c:if>
																</td>
																<td class="STYLE1"><f:formatDate value="${v.pstartTime }" pattern="yyyy-MM-dd"/></td>
																<td class="STYLE1">
																	<a href="javaScript:searchInfo(${v.pid },2);" style="color:#f60">【查看明细】</a>
																</td>
																<td class="STYLE1">
																	<c:if test="${v.pstatus==2}">
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!toUpdate.do?id=${v.pid }&method=2">【编辑】</a>
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!doDelete.do?delIds=${v.pid }&method=2" onclick="return doDeleteById();">【删除】</a>
																	</c:if>
																	<c:if test="${v.pstatus==3}">
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!doDelete.do?delIds=${v.pid }&method=2" onclick="return doDeleteById();">【删除】</a>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
									</table>
								</div>
								<div id="total" style="background-color: white;width: 100%;">
									<form action="" method="post">
										<%@include file="/inc/priceSearch2.jsp" %>
									</form>
									<table border="0" cellspacing="0" cellpadding="0" style="float: left;">
											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="1"
														bgcolor="b5d6e6" class="list" id="datatable">
														<tr>
															<td class="STYLE5">序号</td>
															<td class="STYLE3">方案名称</td>
															<td class="STYLE3">方案类型</td>
															<td class="STYLE3">方案状态</td>
															<td class="STYLE3">启用日期</td>
															<td class="STYLE3" style="width: 250px;">明细</td>
															<td class="STYLE3">操作</td>
														</tr>
														<c:forEach items="${list2}" var="v" varStatus="num">
															<tr>
																<td class="STYLE1">${num.index+1 }</td>
																<td class="STYLE1">${v.pname }</td>
																<td class="STYLE1">阶梯价格方案</td>
																<td class="STYLE1">
																	<c:if test="${v.pstatus==1}">当前方案</c:if>
																	<c:if test="${v.pstatus==2}">预订方案</c:if>
																	<c:if test="${v.pstatus==3}">过期方案</c:if>
																</td>
																<td class="STYLE1"><f:formatDate value="${v.pstartTime }" pattern="yyyy-MM-dd"/></td>
																<td class="STYLE1">
																	<a href="javaScript:searchInfo(${v.pid },2);" style="color:#f60">【查看明细】</a>
																</td>
																<td class="STYLE1">
																	<c:if test="${v.pstatus==2}">
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!toUpdate.do?id=${v.pid }&method=2">【编辑】</a>
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!doDelete.do?delIds=${v.pid }&method=3" onclick="return doDeleteById();">【删除】</a>
																	</c:if>
																	<c:if test="${v.pstatus==3}">
																		<a style="color:#f60" href="${pageContext.request.contextPath}/hod2000Price!doDelete.do?delIds=${v.pid }&method=3" onclick="return doDeleteById();">【删除】</a>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
									</table>
								</div>
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
