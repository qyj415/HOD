<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>ErrorPage</title>
	</head>
	<body bgcolor="#D8E9EC">
		异常：
		<hr />
		${requestScope.exceptions}
		<h3>
			Error Detected
		</h3>
		<table width="99%" border="0" bgcolor="#666666" cellpadding="1"
			cellspacing="1">


			<%if(exception!=null){ %>
			<tr valign="top" bgcolor='#ffffff'>
				<td>
					<b>Error:</b>
				</td>
				<td>
					${pageContext.exception}
				</td>
			</tr>

			<tr valign="top" bgcolor='#ffffff'>
				<td>
					<b>URI:</b>
				</td>
				<td>
					${pageContext.errorData.requestURI}
				</td>
			</tr>


			<tr valign="top" bgcolor='#ffffff'>
				<td>
					<b>Status code:</b>
				</td>
				<td>
					${pageContext.errorData.statusCode}
				</td>
			</tr>
			<%} %>
			<tr valign="top" bgcolor='#ffffff'>
				<td>
					<b>Stack trace:</b>
				</td>

				<td>
					<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
						<p>
							${trace}
						</p>
					</c:forEach>
				</td>
			</tr>

		</table>

	</body>
</html>


