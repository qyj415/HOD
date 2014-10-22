<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>批量上传表计信息</title>
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
				url : "${pageContext.request.contextPath}/hodMeter!batchFileUp.do",
				async : false,
				timeout : 180000,
				isUpload : true,
				form : "meterFileUp",
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
		
		//表格刷新的方法
		function reloadTable()
		{
			 $("#meterUploadList").jqGrid('setGridParam',{  
		        datatype:'json',  
		        page:1 
		    }).trigger("reloadGrid"); //重新载入
		}
		var lastsel;
		$(function(){
			$("#meterUploadList").jqGrid({
                url: '${pageContext.request.contextPath}/hodBatchMeterError!doSelect.do',
                //editurl: '${pageContext.request.contextPath}/hodBatchMeterError!doUpdate.do',
                datatype: "local",
                autowidth:true,
                height:document.documentElement.clientHeight-185,
                mtype: "POST",
                rowNum: 20,
                rownumbers: true,//序号
                rowList: [5,10,20,30,40,50,100],
                colNames:['batchMeterId','错误信息','表号','口径','通信速率','表计型号','地理位置','上级表号','操作'],
                colModel:[
                	{name:'batchMeterId',index:'batchMeterId', hidden:true},
                	{name:'errorMsg',index:'errorMsg', align:"left",width:200,sortable:false,
                    	formatter: function (value, grid, rows, state) 
                		{ 
                			return "<font color=\"red\">"+value+"</font>";
                		}
	                },
                    {name:'meterName',index:'meterName', align:"center",width:100,sortable:false,editable:true},
                    {name:'meterCaliber',index:'meterCaliber', align:"center",width:80,sortable:false,editable:true},
                    {name:'meterBaudrate',index:'meterBaudrate', align:"center",width:150,sortable:false,editable:true},
                    {name:'typeName',index:'typeName', align:"center",width:100,sortable:false,editable:true},
                    {name:'meterPosition',index:'meterPosition',align:"center",width:200,sortable:false,editable:true},
                    {name:'meterParent',index:'meterParent',align:"center",width:'150',sortable:false,editable:true},
                    {name:'act',index:'act', width:110,sortable:false}
                ],
                gridComplete: function(){
					var ids = jQuery("#meterUploadList").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						se = "<input style='height:22px;width:50px;' type='button' value='保存' onclick=\"saveRow('"+cl+"');\"  />"; 
						ce = "<input style='height:22px;width:50px;' type='button' value='取消' onclick=\"cancelRow('"+cl+"')\" />"; 
						jQuery("#meterUploadList").jqGrid('setRowData',ids[i],{act:se+ce});
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
						jQuery('#meterUploadList').jqGrid('restoreRow',lastsel);
						jQuery('#meterUploadList').jqGrid('editRow',id,true);
						lastsel=id;
					}
				},
                pager: "#meterUploadListPager",
                hidegrid:false,//显示隐藏按钮
                viewrecords: true,
                //multiselect: true, 
                emptyrecords: "没有数据!"
             }).navGrid('#meterUploadListPager',{view:false,add:false,edit:false,del:false,refresh:true,search:false}); 
		});
		function cancelRow(cl)
		{
			jQuery('#meterUploadList').restoreRow(cl);
			lastsel=0;
		}
		//数据保存
		function saveRow(rowid)
		{
			jQuery("#meterUploadList").saveRow(rowid, null, 'clientArray');
			var batchMeterId=$("#meterUploadList").getCell(rowid,"batchMeterId");
			var errorMsg=$("#meterUploadList").getCell(rowid,"errorMsg");
			var meterName=$("#meterUploadList").getCell(rowid,"meterName");
			var meterCaliber=$("#meterUploadList").getCell(rowid,"meterCaliber");
			var meterBaudrate=$("#meterUploadList").getCell(rowid,"meterBaudrate");
			var typeName=$("#meterUploadList").getCell(rowid,"typeName");
			var meterStyle=$("#meterUploadList").getCell(rowid,"meterStyle");
			var meterPosition=$("#meterUploadList").getCell(rowid,"meterPosition");
			var meterParent=$("#meterUploadList").getCell(rowid,"meterParent");
			Ext.MessageBox.wait('正在请求，请稍候......', '请稍候');
			Ext.Ajax.request({
				url : "${pageContext.request.contextPath}/hodMeter!saveMeterError.do",
				async : false,
				timeout : 180000,
				params:{  
					batchMeterId:batchMeterId,
					errorMsg:errorMsg,
					meterName:meterName,
					meterCaliber:meterCaliber,
					meterBaudrate:meterBaudrate,
					typeName:typeName,
					meterStyle:meterStyle,
					meterPosition:meterPosition,
					meterParent:meterParent,
		        },  
				success : function(res) {
					Ext.MessageBox.hide();
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						Ext.MessageBox.alert('提示','数据保存成功!');
						//删除该行
						$("#meterUploadList").jqGrid('delRowData', rowid);   
					}
					else
					{
						Ext.MessageBox.alert('提示','数据保存失败!');
						//得到后台传过来的值，设置该行数据
						var msg=result.errData.split(",")[0];
						$("#meterUploadList").jqGrid('setCell',rowid,2,msg); 
					}
					lastsel=0;//点击保存之后可以再编辑
				},
				failure : function(res) {
					Ext.MessageBox.alert('提示','请求失败!');
				}
			});
		}
		//清除数据库中的错误信息
		function clearError()
		{
			Ext.MessageBox.confirm("请确认","你确定要清除所有的错误信息吗?",function(button,text){ 
		        if(button=="yes")
		        {
		        	Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/hodBatchMeterError!doClear.do",
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
														<B>当前位置</B>：[表计管理]-[
														<font color='red'>批量上传表计信息</font>]
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
																	<a href="${pageContext.request.contextPath}/hod2000Meter!toSelect.do"><div class="returnbtn"></div></a>
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
											<form id="meterFileUp" enctype="multipart/form-data" method="post">
												<input type="file" id="fileName" name="fileName" style="width: 350px;height: 22px;"/>
											</form>
										</td>
										<td>
											<input type="button" value="" onclick="upload()" class="uploadbtn"/>
											<input type="button" value="" onclick="clearError()" class="clearbtn"/>
										</td>
									</tr>
									<tr>
										<td colspan="3"><span>（文档格式：表号,口径,通信速率,表计型号,地理位置,上级表号）,请使用英文逗号隔开，仅支持txt格式</span></td>
									</tr>
								</table>
								<table id="meterUploadList"></table>
        						<div id="meterUploadListPager"></div>
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
