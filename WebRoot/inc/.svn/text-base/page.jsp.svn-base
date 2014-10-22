<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
<!--

.STYLE1 {font-size: 12px}
.STYLE4 {
	color: #03515d;
	font-size: 12px;
}
-->
</style>
<script type="text/javascript">
 function goPage()
 {
 	var pageNO=document.getElementById("txtgoPageNo").value;
 	var pagesize=document.getElementById("pagesize").value;
 	if(${totalPage==0})
 	{
 		 Ext.MessageBox.alert('提示',"没有数据！");
         document.getElementById("txtgoPageNo").select();
         document.getElementById("txtgoPageNo").value=${currpage};
         document.getElementById("txtgoPageNo").focus();
         return;
 	}
 	if(isNaN(pageNO)||pageNO==""||pageNO>${totalPage}||pageNO<1)
    {
         Ext.MessageBox.alert('提示',"请正确填写您要到达的页面！");
         document.getElementById("txtgoPageNo").select();
         document.getElementById("txtgoPageNo").value=${currpage};
         document.getElementById("txtgoPageNo").focus();
     }
     else
     {
     	document.getElementById("goPage").href="?currpage="+pageNO+"&pagesize="+pagesize+"${param.params}";
	 }
 }
 function modifyPage(obj){
 		window.location.href="?pagesize="+obj.value+"${param.params}";
		//$.get("?pagesize="+obj.value,function (data, textStatus){});
 }
 $(function() {
  		$("#pagesize").val("${pagesize}");
 }); 
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="STYLE4">&nbsp;&nbsp;共有 ${pagecount} 条记录，当前第 ${currpage}/${totalPage} 页</td>
            <td>
	            <table border="0" align="right" cellpadding="0" cellspacing="0">
	                <tr>
	                  <!-- 每页显示条数 -->
	                  <td width="150" class="STYLE4">
	                  	每页显示&nbsp;&nbsp;<select style="height: 18px;font-size:11px;" name='pagesize' id='pagesize' onchange="modifyPage(this)">
										<option value='5'>5</option>
										<option value='10'>10</option>
										<option value='20' selected='selected'>20</option>
										<option value='30'>30</option>
										<option value='40'>40</option>
										<option value='50'>50</option>
										<option value='100'>100</option>
										</select>&nbsp;&nbsp;行
	                  </td>
	                  <!-- - 如果 有上一页--- -->
	                  <c:if test="${currpage>1}">
	                  <td width="40">
	                  	<a href="?currpage=1&pagesize=${pagesize}${param.params}">
	                  	 <img src="${pageContext.request.contextPath}/common/tab/first.gif"  style="width:37px;height:15px;border:0;margin-top: 3px;" />
	                  	</a>
	                  </td>
	                  <td width="45">
	                  	<a href="?currpage=${currpage-1}&pagesize=${pagesize}${param.params}">
	                  	 <img src="${pageContext.request.contextPath}/common/tab/back.gif"  style="width:43px;height:15px;border:0;margin-top: 3px;" />
	                  	</a>
	                  </td>
	                  </c:if>
	                  <!-- 如果 有下一页 -->
	                  <c:if test="${currpage*1 < totalPage*1}">
	                  <td width="45">
	                  	<a href="?currpage=${currpage+1}&pagesize=${pagesize}${param.params}">
	                  	 <img src="${pageContext.request.contextPath}/common/tab/next.gif" style="width:43px;height:15px;border:0;margin-top: 3px;"/>
	                  	</a>
	                  </td>
	                  <td width="40">
	                  	<a href="?currpage=${totalPage}&pagesize=${pagesize}${param.params}">
	                  	 <img src="${pageContext.request.contextPath}/common/tab/last.gif" style="width:37px;height:15px;border:0;margin-top: 3px;" />
	                  	</a>
	                  </td>
	                  </c:if>
	                  <td width="110">
		                  <div align="center">
		                  <span class="STYLE4">转到第
		                    <input name="textfield" id="txtgoPageNo" type="text" size="6" style="height:18px; width:40px; border:1px solid #999999;color: red;font-size:11px" value="${currpage}"  /> 
		             		页 </span>
		             	 </div>
		              </td>
	                  <td width="40">
	                  	<a id="goPage">
	                  	<img src="${pageContext.request.contextPath}/common/tab/go.gif" style="width:37px;height:15px;border:0;margin-top: 3px;" onclick="goPage();" />
	                  	</a>
	                  </td>
	                </tr>
	            </table>
            </td>
          </tr>
        </table>