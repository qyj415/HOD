<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title></title>
		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/css.css" type="text/css"></link></head>
	<body>

		<table border="0" cellspacing="1" width="250px" align="center" style="margin: auto;">
			<caption>

				<br>
			</caption>

			<thead>
				<tr>

					<th>
						操作结果提示
					</th>
				</tr>
			</thead>
			<tbody class="tb1">

				<tr>
					<td style="padding: 10px;">
						<label style="color: blue" id="okmsg">
							<s:property value="#request.message.msg1" escape="link" />
						</label>
						<label style="color: red">
							<s:property value="#request.message.error1" escape="link" />
							<Br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							如有任何疑问，请联系系统管理员！
						</label>
					</td>
				</tr>
				<tr>
					<td style="padding: 10px;">
					   <s:if test="#request.message.error1 == \"\"">
                          <s:if test="#request.message.jump == true">
					        系统在 <label id="jumpTo" style="color: red"></label> 秒钟后将自动跳转到下面的第一个链接......
						<br>
							</s:if>
                       </s:if>
						<s:iterator value="#request.message.link">
								<!-- escape是把String转换成HTML -->
								<s:property escape="link" />			<br>
						</s:iterator>
					</td>
				</tr>
			</tbody>
		</table>


		<script type="text/javascript"> 
			//var links=document.all.tags("a");火狐不兼容
			var links=document.getElementsByTagName("a");
			countDown(2,links[0]);      
			function countDown(secs,surl){     
				 var jumpTo = document.getElementById('jumpTo');
				 if(jumpTo!=null){
				 	jumpTo.innerHTML=secs;  
				 if(--secs>0){     
				     setTimeout("countDown("+secs+",'"+surl+"')",1000);     
				     }     
				 else{       
				     location.href=surl;     
				     }     
				 }
		   }
		</script>

	</body>
</html>
