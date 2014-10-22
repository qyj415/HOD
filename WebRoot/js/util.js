//获取父类窗口的参数
function getUrlParams(paramName)
{
			var URLParams = new Object();
			var aParams = document.location.search.substr(1).split('&');
			for (var i=0; i < aParams.length; i++) {
			    var aParam = aParams[i].split('=');
			     URLParams[aParam[0]] = aParam[1];
			}
			return URLParams[paramName];
}
//页面加载时根据屏幕大小修改id控件的宽度
function load(id,width)
{
	document.getElementById(id).style.width=document.body.clientWidth-width;
}
//页面加载时根据屏幕大小修改id控件的高度
function loads(id,height)
{
	document.getElementById(id).style.height=document.body.clientHeight-height;
}
//清除文本框中的内容
function delContent(id,id2)
{
	document.getElementById(id).value="";
	if(id2!=null&&id2!='')
		document.getElementById(id2).innerHTML='';
}

//日期格式转换为字符串 格式为yyyy-MM-dd HH:mm:ss
function DateToStr(date)
{
	var xYear=date.year;
    xYear=xYear+1900;
    
    var xMonth=date.month+1;
    if(xMonth<10){
        xMonth="0"+xMonth;
    }

    var xDay=date.date;
    if(xDay<10){
        xDay="0"+xDay;
    }

    var xHours=date.hours;
    if(xHours<10){
        xHours="0"+xHours;
    }

    var xMinutes=date.minutes;
    if(xMinutes<10){
        xMinutes="0"+xMinutes;
    }

    var xSeconds=date.seconds;
    if(xSeconds<10){
        xSeconds="0"+xSeconds;
    }
    return xYear+"-"+xMonth+"-"+xDay+" "+xHours+":"+xMinutes+":"+xSeconds;
}
//设置单选框的值
function setCheckedValue(radioName, newValue) {   
    if(!radioName) return;   
       var radios = document.getElementsByName(radioName);      
       for(var i=0; i<radios.length; i++) {   
          radios[i].checked = false;   
          if(radios[i].value == newValue.toString()) {   
          radios[i].checked = true;   
       }   
        }   
}  

function trimkeyup(e) {
     lucene_objInput = $(this);
     if (e.keyCode != 37 && e.keyCode != 38 && e.keyCode != 39 && e.keyCode != 40 && e.keyCode != 13) {
         var im = $.trim(lucene_objInput.val());
         lucene_objInput.val(im); 
     }
}      

//历史数据页面多选删除	
function doDelete(tableId,url,method)
{
	var rowid = $("#"+tableId).jqGrid('getGridParam','selarrrow');//获取我选中的Id
	if(rowid.length==0)
	{
		Ext.MessageBox.alert('提示','请选择要删除的记录!');
		return;
	}
	Ext.MessageBox.confirm("请确认","你确定要删除该记录吗?",function(button,text){ 
        if(button=="yes")
        {
        	Ext.Ajax.request({
				url : url,
				params:{  
					delIds:rowid
	        	},  
				method : 'POST',
				timeout : 180000,
				success : function(res) {
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						if(method==0) searchHis();//刷新表格
						else searchFree();//刷新表格
					}
					Ext.MessageBox.alert('提示',result.msg);
				},
				failure : function(res) {
					Ext.MessageBox.alert('提示','请求超时!');
				}
		    });
        }
     }); 
}

//历史数据单行删除
function doDeleteByIdHis(id,url,method)
{
	Ext.MessageBox.confirm("请确认","你确定要删除该记录吗?",function(button,text){ 
        if(button=="yes")
        {
        	Ext.Ajax.request({
				url : url,
				params:{  
					delIds:id
	        	},  
				method : 'POST',
				timeout : 180000,
				success : function(res) {
					var result = Ext.decode(res.responseText);
					if (true == result.success) {
						if(method==0) searchHis();//刷新表格
						else searchFree();//刷新表格
					}
					Ext.MessageBox.alert('提示',result.msg);
				},
				failure : function(res) {
					Ext.MessageBox.alert('提示','请求超时!');
				}
		    });
        }
     }); 
}
//价格方案中的弹出层
//定义窗体对象
		var cwxbox = {};
		
		cwxbox.box = function(){
			var bg,wd,cn,ow,oh,o = true,time = null;
			return {
				show:function(c,t,w,h){
					if(o){
						bg = document.createElement('div'); bg.id = 'cwxBg';	
						wd = document.createElement('div'); wd.id = 'cwxWd';
						cn = document.createElement('div'); cn.id = 'cwxCn';
						document.body.appendChild(bg);
						document.body.appendChild(wd);
						wd.appendChild(cn);
						bg.onclick = cwxbox.box.hide;
						window.onresize = this.init;
						window.onscroll = this.scrolls;
						o = false;
					}
					if(w && h){
						var inhtml = '<iframe src="'+ c +'" width="'+ w +'" height="'+ h +'" frameborder="0"></iframe>';
					}else{
						var inhtml	 = c;
					}
					cn.innerHTML = inhtml;
					oh = this.getCss(wd,'offsetHeight');
					ow = this.getCss(wd,'offsetWidth');
					this.init();
					this.alpha(bg,50,1);
					this.drag(wd);
					if(t){
						time = setTimeout(function(){cwxbox.box.hide()},t*1000);
					}
				},
				hide:function(){
					cwxbox.box.alpha(wd,0,-1);
					clearTimeout(time);
				},
				init:function(){
					bg.style.height = cwxbox.page.total(1)+'px';
					bg.style.width = '';
					bg.style.width = cwxbox.page.total(0)+'px';
					var h = (cwxbox.page.height() - oh) /2;
					wd.style.top=(h+cwxbox.page.top())+'px';
					wd.style.left=(cwxbox.page.width() - ow)/2+'px';
				},
				scrolls:function(){
					var h = (cwxbox.page.height() - oh) /2;
					wd.style.top=(h+cwxbox.page.top())+'px';
				},
				alpha:function(e,a,d){
					clearInterval(e.ai);
					if(d==1){
						e.style.opacity=0; 
						e.style.filter='alpha(opacity=0)';
						e.style.display = 'block';
					}
					e.ai = setInterval(function(){cwxbox.box.ta(e,a,d)},40);
				},
				ta:function(e,a,d){
					var anum = Math.round(e.style.opacity*100);
					if(anum == a){
						clearInterval(e.ai);
						if(d == -1){
							e.style.display = 'none';
							if(e == wd){
								this.alpha(bg,0,-1);
							}
						}else{
							if(e == bg){
								this.alpha(wd,100,1);
							}
						}
					}else{
						var n = Math.ceil((anum+((a-anum)*.5)));
						n = n == 1 ? 0 : n;
						e.style.opacity=n/100;
						e.style.filter='alpha(opacity='+n+')';
					}
				},
				getCss:function(e,n){
					var e_style = e.currentStyle ? e.currentStyle : window.getComputedStyle(e,null);
					if(e_style.display === 'none'){
						var clonDom = e.cloneNode(true);
						clonDom.style.cssText = 'position:absolute; display:block; top:-3000px;';
						document.body.appendChild(clonDom);
						var wh = clonDom[n];
						clonDom.parentNode.removeChild(clonDom);
						return wh;
					}
					return e[n];
				},
				drag:function(e){
					var startX,startY,mouse;
					mouse = {
						mouseup:function(){
							if(e.releaseCapture)
							{
								e.onmousemove=null;
								e.onmouseup=null;
								e.releaseCapture();
							}else{
								document.removeEventListener("mousemove",mouse.mousemove,true);
								document.removeEventListener("mouseup",mouse.mouseup,true);
							}
						},
						mousemove:function(ev){
							var oEvent = ev||event;
							e.style.left = oEvent.clientX - startX + "px";  
							e.style.top = oEvent.clientY - startY + "px"; 
						}
					}
					e.onmousedown = function(ev){
						var oEvent = ev||event;
						startX = oEvent.clientX - this.offsetLeft;  
						startY = oEvent.clientY - this.offsetTop;
						if(e.setCapture)
						{
							e.onmousemove= mouse.mousemove;
							e.onmouseup= mouse.mouseup;
							e.setCapture();
						}else{
							document.addEventListener("mousemove",mouse.mousemove,true);
							document.addEventListener("mouseup",mouse.mouseup,true);	
						}
					} 
					
				}
			}
		}()
		
		cwxbox.page = function(){
			return{
				top:function(){return document.documentElement.scrollTop||document.body.scrollTop},
				width:function(){return self.innerWidth||document.documentElement.clientWidth||document.body.clientWidth},
				height:function(){return self.innerHeight||document.documentElement.clientHeight||document.body.clientHeight},
				total:function(d){
					var b=document.body, e=document.documentElement;
					return d?Math.max(Math.max(b.scrollHeight,e.scrollHeight),Math.max(b.clientHeight,e.clientHeight)):
					Math.max(Math.max(b.scrollWidth,e.scrollWidth),Math.max(b.clientWidth,e.clientWidth))
				}
			}	
		}()