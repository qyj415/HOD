<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>

	<base href="<%=basePath%>" >
    <title>批量开户</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/jquery-ui-1.8.2.custom.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/redmond/ui.jqgrid.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/grid.locale-cn.js"></script>
	<script type="text/javascript">
		//批量上传
		function upload()
		{
			var fileName=$("#fileName").val();
			if(fileName.length==0)
			{
				Ext.MessageBox.alert('提示','请选择要上传的文件!');
				return;
			}
			var currentFilePrefix = fileName.substring(fileName.lastIndexOf('.')+ 1);
			if ('txt' != currentFilePrefix.toLowerCase()) {
				Ext.Msg.alert("提示", "仅支持上传后缀名为txt的文本文件！");
				return;
			}	
			
			Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
			Ext.Ajax.request({
				url : "${pageContext.request.contextPath}/hod2000Client!batchFileUp.do",
				async : false,
				timeout : 180000,
				isUpload : true,
				form : "fileUp",
				success : function(res) {
					Ext.MessageBox.hide();
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						Ext.MessageBox.alert('提示','数据已全部上传!');
					}
					else
					{
						Ext.MessageBox.alert('提示','数据上传失败!');
					}
					reloadTable();//刷新表格
				},
				failure : function(res) {
					Ext.MessageBox.alert('提示','请求失败!');
				}
			});
		}
		
		//数据保存
		function saveRow(rowid)
		{
			/*jQuery("#clientUploadList").jqGrid('saveRow',rowid,  {
				"successfunc" :function(){
					alert('成功');
					reloadTable();
				},
			    "url" : '${pageContext.request.contextPath}/hodBatchClientError!doUpdate.do',
			    "extraparam" : {},
			    "aftersavefunc" : null,
			    "errorfunc":function(){
			    	alert("失败");
			    },
			    "afterrestorefunc" : null,
			    "restoreAfterError" : true,
			    "mtype" : "POST"
			});
			*/
			jQuery("#clientUploadList").saveRow(rowid, null, 'clientArray');
			var id=$("#clientUploadList").getCell(rowid,"id");
			var errorMsg=$("#clientUploadList").getCell(rowid,"errorMsg");
			var clientName=$("#clientUploadList").getCell(rowid,"clientName");
			var clientSex=$("#clientUploadList").getCell(rowid,"clientSex");
			var clientCardType=$("#clientUploadList").getCell(rowid,"clientCardType");
			var clientCardNumber=$("#clientUploadList").getCell(rowid,"clientCardNumber");
			var clientAddress=$("#clientUploadList").getCell(rowid,"clientAddress");
			var clientTel=$("#clientUploadList").getCell(rowid,"clientTel");
			var ptype=$("#clientUploadList").getCell(rowid,"ptype");
			var roomAddress=$("#clientUploadList").getCell(rowid,"roomAddress");
			var clientRemark=$("#clientUploadList").getCell(rowid,"clientRemark");
			//var batchClientError="[{id:"+id+",clientName:'"+clientName+"',clientSex:'"+clientSex+"',clientCardType:'"+clientCardType+"',clientCardNumber:'"+clientCardNumber+"',clientAddress:'"+clientAddress+"',clientTel:'"+clientTel+"',ptype:'"+ptype+"',roomAddress:'"+roomAddress+"',clientRemark:'"+clientRemark+"'}]";
			Ext.MessageBox.wait('正在请求，请稍候......', '请稍候');
			Ext.Ajax.request({
				url : "${pageContext.request.contextPath}/hodBatchClientError!doUpdate.do",
				async : false,
				timeout : 180000,
				params:{  
					//batchClientError:batchClientError
					id:id,
					errorMsg:errorMsg,
					clientName:clientName,
					clientSex:clientSex,
					clientCardType:clientCardType,
					clientCardNumber:clientCardNumber,
					clientAddress:clientAddress,
					clientTel:clientTel,
					ptype:ptype,
					roomAddress:roomAddress,
					clientRemark:clientRemark
		        },  
				success : function(res) {
					Ext.MessageBox.hide();
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						Ext.MessageBox.alert('提示','数据保存成功!');
						//删除该行
						$("#clientUploadList").jqGrid('delRowData', rowid);   
					}
					else
					{
						Ext.MessageBox.alert('提示','数据保存失败!');
						//得到后台传过来的值，设置该行数据
						var msg=result.errData.split(",")[0];
						$("#clientUploadList").jqGrid('setCell',rowid,2,msg); 
					}
					lastsel=0;//点击保存之后可以再编辑
				},
				failure : function(res) {
					Ext.MessageBox.alert('提示','请求失败!');
				}
			});
		}
		
		//表格刷新的方法
		function reloadTable()
		{
			 $("#clientUploadList").jqGrid('setGridParam',{  
		        datatype:'json',  
		        //postData:{'meterName':meterId}, //参数  
		        page:1 
		    }).trigger("reloadGrid"); //重新载入
		}
		var lastsel;
		$(function(){
			$("#clientUploadList").jqGrid({
                url: '${pageContext.request.contextPath}/hodBatchClientError!doSelect.do',
                //editurl: '${pageContext.request.contextPath}/hodBatchClientError!doUpdate.do',
                datatype: "local",
                autowidth:true,
                height:document.documentElement.clientHeight-185,
                mtype: "POST",
                rowNum: 20,
                rownumbers: true,//序号
                rowList: [5,10,20,30,40,50,100],
                colNames:['id','错误信息','用户姓名','用户性别','证件类型','证件号码','联系地址','联系电话','价格方案','地理位置','备注','操作'],
                colModel:[
                	{name:'id',index:'id', hidden:true},
                	{name:'errorMsg',index:'errorMsg', align:"left",width:200,sortable:false,
                    	formatter: function (value, grid, rows, state) 
                		{ 
                			return "<font color=\"red\">"+value+"</font>";
                		}
	                },
                    {name:'clientName',index:'clientName', align:"center",width:100,sortable:false,editable:true},
                    {name:'clientSex',index:'clientSex', align:"center",width:80,sortable:false,editable:true},
                    {name:'clientCardType',index:'clientCardType', align:"center",width:80,sortable:false,editable:true},
                    {name:'clientCardNumber',index:'clientCardNumber', align:"center",width:150,sortable:false,editable:true},
                    {name:'clientAddress',index:'clientAddress', align:"center",width:100,sortable:false,editable:true},
                    {name:'clientTel',index:'clientTel',align:"center",sortable:false,editable:true},
                    {name:'ptype',index:'ptype',align:"center",width:100,sortable:false,editable:true},
                    {name:'roomAddress',index:'roomAddress',align:"center",width:'150',sortable:false,editable:true},
					{name:'clientRemark',index:'clientRemark',align:"center",sortable:false,editable:true},
					{name:'act',index:'act', width:110,sortable:false}
                ],
                gridComplete: function(){
					var ids = jQuery("#clientUploadList").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						se = "<input style='height:22px;width:50px;' type='button' value='保存' onclick=\"saveRow('"+cl+"');\"  />"; 
						ce = "<input style='height:22px;width:50px;' type='button' value='取消' onclick=\"cancelRow('"+cl+"')\" />"; 
						jQuery("#clientUploadList").jqGrid('setRowData',ids[i],{act:se+ce});
					}	
				},
                jsonReader: {  
		            root:"result",       
		            records: "totalCount",  
		            page: "page",     
					total: "total",       
		            repeatitems : false        
		        },  
		        onSelectRow: function(id){
					if(id && id!==lastsel){
						jQuery('#clientUploadList').jqGrid('restoreRow',lastsel);//saveRow
						jQuery('#clientUploadList').jqGrid('editRow',id,true);
						lastsel=id;
					}
				},
                pager: "#clientUploadListPager",
                hidegrid:false,//显示隐藏按钮
                viewrecords: true,
                //multiselect: true, 
                emptyrecords: "没有数据!"
             }).navGrid('#clientUploadListPager',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
		});
		function cancelRow(cl)
		{
			jQuery('#clientUploadList').restoreRow(cl);
			lastsel=0;
		}
		//清除数据库中的错误信息
		function clearError()
		{
			Ext.MessageBox.confirm("请确认","你确定要清除所有的错误信息吗?",function(button,text){ 
		        if(button=="yes")
		        {
		        	Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/hodBatchClientError!doClear.do",
						async : false,
						timeout : 180000,
						success : function(res) {
							Ext.MessageBox.hide();
							var result = Ext.decode(res.responseText);
							if (true == result.success) 
							{
								Ext.MessageBox.alert('提示','错误信息清除成功!');
								reloadTable();
							}
							else
							{
								Ext.MessageBox.alert('提示','错误信息清除失败!');
							}
						},
						failure : function(res) {
							Ext.MessageBox.alert('提示','请求失败!');
						}
					});
		        }
	        }); 
		}	
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
														<B>当前位置</B>：[客户信息]-[
														<font color='red'>批量开户</font>]
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
																	<a href="${pageContext.request.contextPath}/show/clientadd.jsp"><div class="returnbtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp" target="mainFrame"><div class="homebtn"></div></a>
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
							<td width="8" class="tab_12">
							</td>
							<td>
							<!-- 可以写form -->
								<table cellpadding="0" cellspacing="0" border="0" height="70" id="tab" width="100%">
									<tr>
										<td style="width: 60px;">上传文件：</td>
										<td style="width: 400px;">
											<form id="fileUp" enctype="multipart/form-data" method="post">
												<input type="file" id="fileName" name="fileName" style="width: 400px;height: 22px;"/>
											</form>
										</td>
										<td>
											<input type="button" value="" onclick="upload()" class="uploadbtn"/>
											<input type="button" value="" onclick="clearError()" class="clearbtn"/>
										</td>
									</tr>
									<tr>
										<td colspan="3"><span>（文档格式：用户姓名,用户性别,证件类型,证件号码,联系地址,联系电话,价格方案,地理位置1|地理位置2,备注信息）,请使用英文逗号隔开，仅支持txt格式</span></td>
									</tr>
								</table>
								<table id="clientUploadList"></table>
        						<div id="clientUploadListPager"></div>
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
