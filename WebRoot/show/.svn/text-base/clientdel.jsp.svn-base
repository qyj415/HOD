<%@ page language="java"  pageEncoding="UTF-8" errorPage="/inc/exception.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>销户</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
				<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-basex.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/check.js"></script>
		<script type="text/javascript">
			function showDiv(id)
			{
				var di=document.getElementById(id+'id');
				if(di.style.display=='')
				{
					di.style.display='none';
				}
				else
				{
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
											div.innerHTML="地理位置："+roomName+"&nbsp;&nbsp;面积数(㎡)："+roomSize;
											di.appendChild(div);
										}
									}
									else
									{
										di.innerHTML='该用户暂时还没有房间信息!';
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
					di.style.display='';
				}
			}
			
			//销户
			function clientDel(id,thisHref)
			{
				if(confirm("您确定要进行销户吗?"))
				{
					if(confirm("是否打印销户信息?"))
					{
						var row=thisHref.parentNode.parentNode;
						var roomInfo=document.getElementById('clientId2');
						//根据id查询该用户信息与该用户的房间信息填充到div2
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
											//var div=document.createElement("div");
											//div.innerHTML="房间号："+roomName+"&nbsp;&nbsp;面积数(㎡)："+roomSize;
											//roomInfo.appendChild(div);
											$("#clientId2").html("房间号："+roomName+"&nbsp;&nbsp;面积数(㎡)："+roomSize);
										}
									}
									else
									{
										//roomInfo.innerHTML='该用户暂时还没有房间信息!';
										$("#clientId2").html('该用户暂时还没有房间信息!');
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
						$("#clientName2").html(row.cells[1].innerHTML);
						$("#clientSex2").html(row.cells[4].innerHTML);
						$("#clientIdentity2").html(row.cells[3].innerHTML);
						$("#clientAddress2").html(row.cells[5].innerHTML);
						$("#clientTel2").html(row.cells[6].innerHTML);
						$("#clientRemark2").html(row.cells[9].innerHTML);
						toggle('div1','div2');
						window.print();
					}
					window.location.href='${pageContext.request.contextPath}/hod2000Client!doDelete.do?delIds='+id;
				}
			}
			$(function(){
				$("#clientDelList").jqGrid({
	                url: '${pageContext.request.contextPath}/hodClient!doSelect.do',
	                datatype: "local",
	                autowidth:true,
	                height:document.documentElement.clientHeight-190,
	                mtype: "POST",
	                rowNum: 20,
	                rownumbers: true,//序号
	                rowList: [5,10,20,30,40,50,100],
	                colNames:['用户姓名','证件类型','证件号码','性别','联系地址','联系电话','状态','房间信息','备注','基本操作'],
	                colModel:[
	                    {name:'clientName',index:'clientName', align:"center",width:100,sortable:false},
	                    {name:'clientCardType',index:'clientCardType', align:"center",width:100,sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==0)
	                				return "二代身份证";
	                			if(value==1)
	                				return "港澳通行证";
	                			if(value==2)
	                				return "台湾通行证";
	                			if(value==3)
	                				return "护照";
	                		}
	                    },
	                    {name:'clientIdentity',index:'clientIdentity', align:"center",width:120,sortable:false},
	                    {name:'clientSex',index:'clientSex', align:"center",width:50,sortable:false},
	                    {name:'clientAddress',index:'clientAddress', align:"center",width:150,sortable:false},
	                    {name:'clientTel',index:'clientTel',align:"center",sortable:false},
	                    {name:'clientEnable',index:'clientEnable',align:"center",width:50,sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==0)
	                				return "<font color=\"red\">已销户</font>";
	                			if(value==1)
	                				return "正常";
	                		}
	                    },
	                    {name:'id',index:'id',align:"center",width:'300',sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			return "<a href=\"#\" style=\"color:#f60\" onclick=\"showDiv(" + value + ")\">点击显示/隐藏信息</a><div id='"+value+"id' style='display: none;'></div>";
	                		}
						},
						{name:'clientRemark',index:'clientRemark',align:"center",sortable:false},
						{name:'clientEnable',index:'clientEnable',align:"center",width:50,sortable:false,
	                    	formatter: function (value, grid, rows, state) 
	                		{ 
	                			if(value==1)
	                			{
	                				var id=rows["id"];
	                				return "<a href=\"#\" style=\"color:#f60\" onclick=\"clientDel(" + id + ",this)\">【销户】</a>";
	                			}
	                			else
	                			{
	                				return "";
	                			}
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
	                pager: "#clientDelListPager",
	                hidegrid:false,//显示隐藏按钮
	                viewrecords: true,
	                //multiselect: true, 
	                emptyrecords: "没有数据!"
	             }).navGrid('#clientDelListPager',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
			});
			function searchClientList()
			{
				$("#clientDelList").jqGrid('setGridParam',{  
			        datatype:'json',  
			        postData:{'clientName':$("#clientName").val(),'clientIdentity':$("#clientIdentity").val(),'clientEnable':$("#clientEnable").val()}, //发送数据  
			        page:1 
			    }).trigger("reloadGrid"); //重新载入
			}
		</script>
	</head>
	<body>
		<div id="div1">
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
														<B>当前位置</B>：[客户信息]-[
														<font color='red'>销户</font>]
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
																	<a href="${pageContext.request.contextPath}/subpage.jsp?id=0"><div class="returnbtn"></div></a>
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
							 		<%@include file="/inc/clientinfoSearch.jsp" %>
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
								<table id="clientDelList"></table>
        						<div id="clientDelListPager"></div>
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
		</div>
		<div id="div2" style="display: none;">
			<table align="center" border="1" width="80%">
				<tr height="32">
					<td colspan="7" align="center" valign="middle" scope="row">
						<div style="float: left; padding: 5px 1px 1px 15px;">
							<img src="${pageContext.request.contextPath}/images/hodlogin.jpg"
								width="76" height="23" />
						</div>
						<span class="STYLE1">客户销户信息</span>
					</td>
				</tr>
				<tr height="25">
					<td width="12%" align="center">
						<span>用户名</span>
					</td>
					<td width="8%" align="center">
						<span>性别</span>
					</td>
					<td width="12%" align="center">
						<span>证件号码</span>
					</td>
					<td width="18%" align="center">
						<span>地址信息</span>
					</td>
					<td width="12%" align="center">
						<span>电话号码</span>
					</td>
					<td width="28%" align="center">
						<span>房间信息</span>
					</td>
					<td width="12%" align="center">
						<span>备注</span>
					</td>
				</tr>
				<tr height="25">
					<td align="center">
						<span id="clientName2"></span>
					</td>
					<td align="center">
						<span id="clientSex2"></span>
					</td>
					<td align="center">
						<span id="clientIdentity2"></span>
					</td>
					<td align="center">
						<span id="clientAddress2"></span>
					</td>
					<td align="center">
						<span id="clientTel2"></span>
					</td>
					<td align="center">
						<div id="clientId2"></div>
					</td>
					<td align="center">
						<span id="clientRemark2"></span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

