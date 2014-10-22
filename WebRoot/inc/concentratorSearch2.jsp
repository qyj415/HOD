<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/common/tab/search/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/common/tab/search/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function checkForm()
	{
		var conNumber=document.getElementById('conNumber').value;
		if(conNumber.length>0)
		{
			if(conNumber.length>8)
			{
				Ext.MessageBox.alert('提示','集中器编号输入不规范!');
				//Ext.MessageBox.alert('提示','集中器编号输入不规范!');
				return false;
			}
		}
		return true;
	}
</script>
</head>
		<div>
				<table class="repCnd">
					<tr class="repCnd">
						<td class="repCndLb" nowrap="nowrap">集中器编号:</td>
						<td class="repCndEdit" nowrap="nowrap">
							<span>
								<input class="border" type="text" id="conNumber" name="conNumber" value="${conNumber }" maxlength="8" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/> 
							</span>
						</td>
						<td class="repCndEditRight"><input type="submit" class="btn" value="" onclick="return checkForm()"/></td>
					</tr>
				</table>
		</div>
  
		<div class="repCndToolBar"></div>


