 
function GetParentElement(obj, tag){ 
	while(obj!=null && obj.tagName!=tag) 
	obj=obj.parentNode; 
	return obj; 
} 

function changeColor(evt){ 
	evt = evt ? evt : window.event; 
	var el = evt.target ? evt.target : evt.srcElement; 
	var tabElement=null; 
	while (true){ 
	el=GetParentElement(el, "TR") 
	if (el){ 
	tabElement=GetParentElement(el, "TABLE"); 
	if (tabElement!=null && tabElement.className.toUpperCase()=="LIST"){ 
	break; 
	} 
	el=tabElement; 
	}else{ 
	return; 
	} 
	} 
	for (var i=0;i <el.children.length;i++){ 
	if (el.children[i].tagName=="TD"){ 
	el.children[i].style.backgroundColor="#eeeeee"; 
	} 
	} 
} 

function revertColor(evt){ 
	evt = evt ? evt : window.event; 
	var el = evt.target ? evt.target : evt.srcElement; 
	var tabElement=null; 
	while (true){ 
	el=GetParentElement(el, "TR") 
	if (el){ 
	tabElement=GetParentElement(el, "TABLE"); 
	if (tabElement!=null && tabElement.className.toUpperCase()=="LIST"){ 
	break; 
	} 
	el=tabElement; 
	}else{ 
	return; 
	} 
	} 
	for (var i=0;i <el.children.length;i++){ 
		if (el.children[i].tagName=="TD"){ 
		el.children[i].style.backgroundColor=""; 
		} 
	} 
} 
document.onmouseover= changeColor; 
document.onmouseout = revertColor; 

 
function selectAll(sobj)
{
	var objs = document.getElementsByTagName("input");
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == "checkbox"&&(objs[i].getAttribute("id")=="dataList"||objs[i].getAttribute("id")=="dataList0"||objs[i].getAttribute("id")=="allcheckbox")){
				if(sobj=="0"){
						objs[i].checked = document.getElementById("allcheckbox").checked;
					}else{
						objs[i].checked = document.getElementById("dataList0").checked;
					}
		   }
		}
}

function selectAlls(sobj)
{
	var objs = document.getElementsByTagName("input");
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == "checkbox"&&(objs[i].getAttribute("id")=="dataList2"||objs[i].getAttribute("id")=="dataList1"||objs[i].getAttribute("id")=="allcheckbox")){
				if(sobj=="0"){
						objs[i].checked = document.getElementById("allcheckbox").checked;
					}else{
						objs[i].checked = document.getElementById("dataList1").checked;
					}
		   }
		}
}


function doDeleteX(delactionurl){
		var allbox=document.getElementsByName("ckId");
		var delIds="";//sava delids
		for(i=0;i<allbox.length;i++){
			if(allbox[i].checked){
				delIds=delIds+allbox[i].value+","
			}
		}
		if(""==delIds){
			 Ext.MessageBox.alert('提示','请选择要删除的记录!');
		}
		else
		{
			delIds=delIds.substr(0,delIds.length-1)
			if(confirm("你确定要删除该记录吗?")){
				document.getElementById("deleteById").href=delactionurl+"&delIds="+delIds;
			}
		} 
	}
	
function doDelete(delactionurl){
		var allbox=document.getElementsByName("ckId");
		var delIds="";//sava delids
		for(i=0;i<allbox.length;i++){
			if(allbox[i].checked){
				delIds=delIds+allbox[i].value+","
			}
		}
		if(""==delIds){
			 Ext.MessageBox.alert('提示','请选择要删除的记录!');
		}
		else
		{
			delIds=delIds.substr(0,delIds.length-1)
			if(confirm("你确定要删除该记录吗?")){
				document.getElementById("deleteById").href=delactionurl+"?delIds="+delIds;
			}
		} 
	}
	
function doDeletes(delactionurl,params){
		var allbox=document.getElementsByName("ckId");
		var delIds="";//sava delids
		for(i=0;i<allbox.length;i++){
			if(allbox[i].checked){
				delIds=delIds+allbox[i].value+","
			}
		}
		if(""==delIds){
			 Ext.MessageBox.alert('提示','请选择要删除的记录!');
		}
		else
		{
			delIds=delIds.substr(0,delIds.length-1)
			if(confirm("你确定要删除该记录吗?")){
				document.getElementById("deleteById").href=delactionurl+"?delIds="+delIds+'&'+params;
			}
		} 
	}

function doDeleteById(delactionurl){
			if(confirm("你确定要删除该记录吗?")){
				return true;
			}else{
				return false;
			}
	}

function doDelById(){
			if(confirm("你确定要对该用户进行销户吗?")){
				return true;
			}else{
				return false;
			}
	}


function hiddenSearch(){
	var v = document.getElementById("searchDIV").style;
	v.display = v.display=='none' ? '' : 'none';

	var v2 = document.getElementById("showMode");
	var temp = document.all?"className":"class";
	v2.setAttribute(temp,v2.getAttribute(temp)=="showModel"?"showModel2":"showModel");

 }   
 