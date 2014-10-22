<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>日志管理</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#operateLogList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodOperateLog!doSelect.do',
	                datatype: "local",
	                autowidth:true,
	                height:document.documentElement.clientHeight-190,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                //colNames:['操作员名称(用户名)','操作时间','操作内容','基本操作'],
	                colNames:['操作员名称(用户名)','操作时间','操作内容'],
	                colModel:[
	                    {name:'operName',index:'operName', align:"center",width:'100',sortable:false},
	                    {name:'logDate',index:'logDate',align:"center",sortable:false},
	                    {name:'logContent',index:'logContent',align:"center",sortable:false}
	                    /*{name:'id',index:'id',align:"center",width:'80',sortable:false,formatter: function (value, grid, rows, state) 
	                		{ 
	                			return "<a href=\"#\" style=\"color:#f60\" onclick=\"doDeleteByIdHis("+value+",'${pageContext.request.contextPath}/hodOperateLog!doDelete.do',0)\">【删除】</a>";
	                		}
						},*/
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#operateLogListPager",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                multiselect: false, 
	                emptyrecords: "没有数据!"
	             }).navGrid('#operateLogListPager',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
			});
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
														<B>当前位置</B>：[系统管理]-[
														<font color='red'>日志管理</font>]
													</td>
												</tr>
											</table>
										</td>
										<td width="54%">
											<table border="0" align="right" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="60"></td>
													<!-- <td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<div align="center">
																		<a id="deleteById" href="#" onclick="doDelete('operateLogList','${pageContext.request.contextPath}/hodOperateLog!doDelete.do',0);"><div class="delbtn"></div></a>
																	</div>
																</td>
															</tr>
														</table>
													</td> -->
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=1"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
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
								<div style="height: 1px;width: 100%;"><table><tr><td height="1"></td></tr></table></div>
								<DIV id="searchDIV">
							 		<%@include file="/inc/logSearch.jsp" %>
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
								<table id="operateLogList"></table>
        						<div id="operateLogListPager"></div>
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
								&nbsp;
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

