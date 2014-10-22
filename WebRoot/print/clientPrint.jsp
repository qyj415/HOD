<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>档案管理-打印</title>	
		<script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/etimbo/src/css/print.css" type="text/css" media="print" />
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/etimbo/src/css/print-preview.css" type="text/css" media="screen">
		<script type="text/javascript">
			function showDiv(id)
			{
				var di=document.getElementById(id+'id');
					if(di.innerHTML.length==13||di.innerHTML.length==0)//火狐为13，IE为0
					{
						Ext.Ajax.request({
							url : "${pageContext.request.contextPath}/hodClient!showRoom.do?id="+id,
							async : false,
							timeout : 180000,
							success : function(res) {
								var result = Ext.decode(res.responseText);
								if (true == result.success) {
									var len=result.data.length;
									if(len>0)
									{
										for(var i=0;i<len;i++)
										{
											var roomName=result.data[i].roomName;
											var roomSize=result.data[i].roomSize;
											var div=document.createElement("div");
											div.innerHTML="房间号："+roomName+"&nbsp;&nbsp;面积数(㎡)："+roomSize;
											di.appendChild(div);
										}
									}
									else
									{
										di.innerHTML='该用户暂时还没有房间信息!';
									}
								}
							}
						});
					
					
					di.style.display='';
					}
			}
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
										<td class="STYLE3">用户姓名</td>
										<td class="STYLE3">证件号码</td>
										<td class="STYLE5">性别</td>
										<td class="STYLE3">联系地址</td>
										<td class="STYLE3">联系电话</td>
										<td class="STYLE3">状态</td>
										<td class="STYLE3" width="250">房间信息</td>
										<td class="STYLE3">备注</td>
									</tr>
									<!--------------------------------------------------->
									<c:forEach items="${dataList}" var="v" varStatus="num">
										
										<tr>
											<td class="STYLE6">&nbsp;${num.index+1}</td>
											<td class="STYLE6">&nbsp;${v.clientName}</td>
											<td class="STYLE6">&nbsp;${v.clientIdentity}</td>
											<td class="STYLE6">&nbsp;${v.clientSex}</td>
											<td class="STYLE6">&nbsp;${v.clientAddress}</td>
											<td class="STYLE6">&nbsp;${v.clientTel}</td>
											<td class="STYLE6">
												<c:if test="${v.clientEnable==1}">
													正常
												</c:if>
												<c:if test="${v.clientEnable==0}">
													<font color="red">已销户</font>
												</c:if>
											</td>
											<td class="STYLE7">
												
												<div id="${v.clientId }id" style="display: none;">
												</div>
											</td>
											<td class="STYLE6">&nbsp;${v.clientRemark}</td>
										</tr>
										<script type="text/javascript">showDiv('${v.clientId }');</script>
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