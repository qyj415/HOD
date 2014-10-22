function suckerfish(type, tag, parentId) {
if (window.attachEvent) {
   window.attachEvent("onload", function() {
    var sfEls = (parentId==null)?document.getElementsByTagName(tag):document.getElementById(parentId).getElementsByTagName(tag);
    type(sfEls);
   });
}
}

sfHover = function(sfEls) {
for (var i=0; i<sfEls.length; i++) {
   sfEls[i].onmouseover=function() {
    this.className+=" sfhover";
   }
   sfEls[i].onmouseout=function() {
    this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
   }
}
}

sfFocus = function(sfEls) {
for (var i=0; i<sfEls.length; i++) {
   sfEls[i].onfocus=function() {
    this.className+=" sffocus";
   }
   sfEls[i].onblur=function() {
    this.className=this.className.replace(new RegExp(" sffocus\\b"), "");
   }
}
}

sfActive = function(sfEls) {
for (var i=0; i<sfEls.length; i++) {
   sfEls[i].onmousedown=function() {
    this.className+=" sfactive";
   }
   sfEls[i].onmouseup=function() {
    this.className=this.className.replace(new RegExp(" sfactive\\b"), "");
   }
}
}

sfTarget = function(sfEls) {
var aEls = document.getElementsByTagName("A");
document.lastTarget = null;
for (var i=0; i<sfEls.length; i++) {
   if (sfEls[i].id) {
    if (location.hash==("#" + sfEls[i].id)) {
     sfEls[i].className+=" sftarget";
     document.lastTarget=sfEls[i];
    }
    for (var j=0; j<aEls.length; j++) {
     if (aEls[j].hash==("#" + sfEls[i].id)) aEls[j].targetEl = sfEls[i];
     aEls[j].onclick = function() {
      if (document.lastTarget) document.lastTarget.className = document.lastTarget.className.replace(new RegExp(" sftarget\\b"), "");
      if (this.targetEl) this.targetEl.className+=" sftarget";
      document.lastTarget=this.targetEl;
      return true;
     }
    }
   }
}
}
suckerfish(sfHover, "p");
suckerfish(sfActive, "p");
suckerfish(sfHover, "INPUT");
suckerfish(sfActive, "TEXTAREA");
suckerfish(sfFocus, "INPUT");
suckerfish(sfFocus, "TEXTAREA");
suckerfish(sfTarget, "H5");
suckerfish(sfHover, "pre");
suckerfish(sfActive, "pre");


//以下是输入时只允许键入数字
 function kd(e)
{
    e = window.event || e;   
    var k = e.keyCode || e.which;  
	 if ((k==46)||(k==8)||(k==189)||(k==109)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) {}
	 else if(k==13){
	        if(window.event)
				window.event.keyCode = 9;
	}
	else{
	        if(window.event)
               window.event.returnValue = false;
            else
                e.preventDefault();//for firefox
	}
} 
