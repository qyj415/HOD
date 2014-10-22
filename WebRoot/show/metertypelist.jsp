<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>表计型号</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript">
			$(function(){
					$("#meterTypeList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodMeterType!doSelect.do',
	                datatype: "json",
	                autowidth:true,
	                height:document.documentElement.clientHeight-115,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['表计型号','仪表类型','厂商名称','厂商编号','通信规约','基本操作'],
	                colModel:[
	                    {name:'typeName',index:'typeName', align:"center",sortable:false},
	                    {name:'typeCode',index:'typeCode', align:"center",sortable:false},
	                    {name:'companyName',index:'companyName', align:"center",sortable:false},
	                    {name:'companyNum',index:'companyNum', align:"center",sortable:false},
	                    {name:'communicationProtocol',index:'communicationProtocol', align:"center",sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==3)
	                				return "CJ/T188-2004";
	                			if(value==4)
	                				return "HOD";
	                		}
	                    },
	                    {name:'id',index:'id',align:"center",sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			return "<a href=\"${pageContext.request.contextPath}/hod2000MeterType!toUpdate.do?id="+value+"\" style=\"color:#f60\">【编辑】</a>";
	                		}
	                    }
	                ],
	                jsonReader: {  
			            root:"result",       
			            records: "totalCount",  
			            page: "page",     
						total: "total",       
			            repeatitems : false        
			        },  
	                pager: "#meterTypeListPager",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                emptyrecords: "没有数据!"
	             }).navGrid('#meterTypeListPager',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
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
														<B>当前位置</B>：[<font color='red'>表计型号</font>]
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
																	<a href="${pageContext.request.contextPath}/hod2000MeterType!toAdd.do"><div class="addbtn"></div></a>
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
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
								<table id="meterTypeList"></table>
        						<div id="meterTypeListPager"></div>
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

