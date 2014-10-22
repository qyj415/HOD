<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>城市建筑智慧能源管理系统</title>
<SCRIPT language="JavaScript">
<!--
function toFull(){
      if(window.name=="fullscreen")return;
     var a =window.open("","fullscreen","fullscreen=yes","")
     a.location = window.location.href
     window.opener=null
     window.close()
}
//toFull();
function untoFull(){
      if(window.name=="fullscreen");
     var a =window.open("","fullscreen","fullscreen=yes","")
     a.location = window.location.href
     window.opener=null
     window.close()
}

//-->
var bXmlHttpSupport = (typeof XMLHttpRequest != "undefined" || window.ActiveXObject);
window.setInterval("updateSession()",30000);
function updateSession() {  

        if(bXmlHttpSupport) {
       
            var sUrl = 'servlet/UpdateSession?s=R'+Math.random();

            var oRequest = new XMLHttpRequest();

            oRequest.onreadystatechange = function() {

                if(oRequest.readyState == 4) {
                }
            };
            oRequest.open('get', sUrl,true);
            oRequest.send(null);
        }
    }
</SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script></head>
<frameset rows="100" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src="${pageContext.request.contextPath}/desk.jsp" name="mainFrame" id="mainFrame" scrolling="no"/>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
