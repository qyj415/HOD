<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
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
				url : "${pageContext.request.contextPath}/hodCity!batchFileUp.do",
				async : false,
				timeout : 180000,
				isUpload : true,
				form : "addressFileUp",
				params:{  
						villageId:${villageId}
		        },  
				method : 'POST',
				success : function(res) {
					Ext.MessageBox.hide();
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						Ext.MessageBox.alert('提示','数据已全部上传，请点击左边的树手动刷新表格数据!');
					}
					else
					{
						Ext.MessageBox.alert('提示',result.msg);
					}
				},
				failure : function(res) {
					Ext.MessageBox.alert('提示','请求失败!');
				}
			});
	}
</script>
</head>
		<div class="tabContainer" width="100%">
		  <table border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td width="25"><img src="${pageContext.request.contextPath}/common/tab/search/firstLeftSel.gif" width="25" height="24" /></td>
			  <td width="100" class="tabTitleSel" nowrap="nowrap"  >批量上传</td>
			  <td width="50"><img src="${pageContext.request.contextPath}/common/tab/search/lastRightSel.gif" width="50" height="24" /></td>
			  <td class="tabBg"></td>
			</tr>
		  </table>
		</div>
		
		<div>
				<form id="addressFileUp" enctype="multipart/form-data" method="post">
					<table class="repCnd">
						<tr class="repCnd">
							<td class="repCndLb" nowrap="nowrap">文件名:</td>
							<td class="repCndEdit" nowrap="nowrap"><span><input class="border" id="fileName" type="file" name="fileName" /> </span></td>
							<td class="repCndEditRight"><input type="button" class="uploadbtn" value="" onclick="upload();"/><span style="color: gray;">（文档格式：小区,楼栋,单元,房间号,面积数,户型）,请使用英文逗号隔开,仅支持txt
							</span></td>
						</tr>
					</table>
				</form>
		</div>
  
		<div class="repCndToolBar">
		</div>


