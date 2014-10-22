
PubUtil=function PubUtil(){};

/**
 * ȡ��ǰ����Ԫ�ء�
 */
PubUtil.getActiveElement=function()
{
  return document.activeElement;//ֻie֧��
};

/**
 * ����ĳһ����
 * @param n �����ʶ��
 * @param d document����
 */
PubUtil.findObj=function(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=PubUtil.findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
};


/**
 * ��ʾ/���ز�.
 * ������ʽ:layerId,"",show(hide),layerId,"",show(hide)...
 */
PubUtil.showHideLayers=function() { //v3.0
  var i,p,v,obj,args=PubUtil.showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=PubUtil.findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
};


/**
 * ��ʾ/���ض���
 * @param id ���id.
 * @param isShow �Ƿ���ʾ.
 * @param doc document.
 */
PubUtil.showHideElement=function(id,isShow,doc)
{
  if(!doc)
    doc=document;
  var obj=doc.getElementById(id);
  if(!obj)
    return;
  if(isShow)
  {
    if(obj.style.display!=null) 
      obj.style.display = ""; //obj.style.display = "block"; 
    else 
      obj.visibility = "show"; 
  }
  else
  {
    if(obj.style.display!=null)
      obj.style.display = "none";
    else
      obj.visibility = "hiden";
  }
};

/**
 * �л�Ԫ�ص���ʾ״̬��
 * @param id Ԫ�ص�id.
 * @param doc document.
 */
PubUtil.toggleVisible=function(id,doc)
{
  if(!doc)
    doc=document;
  var obj=doc.getElementById(id);
  if(!obj)
    return;
  if(obj.style.display!=null) 
  {
    if(obj.style.display=="none")
      obj.style.display = "";//obj.style.display = "block";
    else
      obj.style.display = "none";
  }
  else
  {
    if(obj.visibility=="hiden")
      obj.visibility = "show";
    else
      obj.visibility = "hiden";
  }
};

/**
 * ���ַ�������HTML���ˣ���"&lt;"��Ϊ"&amp;lt;"��<br>
 * @param input Ҫת�����ַ�����<br>
 * @param isIgnEnter �Ƿ���Իس����з���<br>
 * @return ��������ַ�����<br>
 */
PubUtil.htmlFilter=function(input)
{
  if(!input || input=="")
    return "";
  var origIns=null;
  var filterStrs=null;
  origIns=new Array('<','>','"','\'','&','\r','\n','?','=');
  filterStrs=new Array("&lt;","&gt;","&quot;","&#39;","&amp;","","<br>","&#63;","&#61;");
  var filtered = "";
  var c = null;
  for(var i=0; i<input.length; i++)
  {
    c = input.charAt(i);
    var isFind=false;
    for(var j=0;j<origIns.length;j++)
    {
      if(c==origIns[j])
      {
        filtered+=filterStrs[j];
        isFind=true;
        break;
      }
    }
    if(!isFind)//û���ҵ�
      filtered+=c;
  }    
  return filtered;
};

/**
 * ������HTML���˺���ַ���ת������ַ����ָ���ԭʼֵ����"&amp;lt;"ת��Ϊ"&lt;".
 * @param input �ȴ�����ַ�����
 */
PubUtil.antiHtmlFilter=function(input)

{

  if(input==null || input=="")
 
     return "";
    //����û�д����Сд���Ժ�Ҫ����
    
  var origStrArr=new Array("&nbsp;","&lt;","&gt;","&quot;","&#39;","&amp;","<br>","&#63;","&#61;");
 
  var newStrArr=new Array(" ","<",">","\"","\'","&","\n","?","=");
 
  for(var i=0;i<origStrArr.length;i++)

  {
     
    var re = new RegExp(origStrArr[i],"gi");
    input=input.replace(re,newStrArr[i]);
  }
  
  return input;
 
};


/**
 * ������Ƶ��������
 * @param elem �����
 */
PubUtil.moveToTextLast=function(elem)
{
  if(!elem)
	  elem=event.srcElement;
  var r =elem.createTextRange();
  r.moveStart("character",elem.value.length);
  r.collapse(true);
  r.select();
};

/**
 * �Զ�����textArea�ĸ߶ȡ�
 * @param textArea textarea�ؼ���
 */
PubUtil.autoTextAreaHeight=function(textArea)
{
  height=textArea.scrollHeight;
  if(height<20)
    height=20;
  textArea.style.posHeight=height;
};

/**
 * ����XMLHTTP����
 * @return XMLHTTP����
 */
PubUtil.createXMLHttp=function()
{
  var xmlHttp; 
  if (document.all){        // document.all means IE 
    try { 
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");         
    } catch (e) { 
        try { 
            xmlHttp=new ActiveXObject("Msxml2.XMLHTTP"); //���л������⣬ÿ�η��صĶ�һ��
        } catch (e) { 
            xmlHttp=null; 
        } 
    } 
  }else{ 
      try { 
          xmlHttp = new XMLHttpRequest(); 
      } catch (e) { 
          xmlHttp=null; 
      } 
  } 
  if(xmlHttp==null)
    alert("����XMLHTTP���󣬿��������������֧��!");
  return xmlHttp; 
};


/**
 * ���ַ��������пո񡢱�㡢�������Ŷ��� %xx ������棬���滻����.
 * @param str ��ת�����ַ�����
 */
PubUtil.myEscape=function(str)
{
  str+="";//����������������֣���������ַ���������str.length="undefine"
  var newStr="";
  for(var i=0;i<str.length;i++)
  {
    var curChar=str.charAt(i);
    if(str.charCodeAt(i)<255)
      newStr=newStr.concat(escape(curChar));
    else
      newStr=newStr.concat(curChar);
  }
  return newStr;
};

/**
 * ȡ���ӽڵ��Ӧ��ָ����ǩ���͸��ڵ㡣
 * @param childNode �ӽڵ㡣
 * @param parentTagName ���ڵ��ǩ���͡�
 * @return ���ڵ㣬û���ҵ�����null.
 */
PubUtil.getParentNode=function(childNode,parentTagName)
{
  var tmpObj=childNode.parentNode;
  parentTagName=parentTagName.toLowerCase();
  while(childNode!=null && tmpObj.tagName.toLowerCase()!=parentTagName)
  {
    tmpObj=tmpObj.parentNode;
  }
  return tmpObj;
};

/**
 * ���ж��Ԫ�ؾ�����ͬ����ʱ����ָ����Ԫ�ص�ֵ����Ϊǰһ��Ԫ�ص�ֵ��
 * @param elemNames Ԫ�����Ƽ�����","�����
 * @param curIndex ��ǰ�����Ԫ�����������Ϊnull��-1����ȡ���һԪ�ء�
 * @param isInnerHTML ���û��value���ԣ��Ƿ�����innerHTML,Ĭ��Ϊtrue.
 */
PubUtil.copyElemValues=function(elemNames,curIndex,isInnerHTML)
{
  if(isInnerHTML==null)
    isInnerHTML=true;
  var elemNameArr=elemNames.split(",");
  for(var i=0;i<elemNameArr.length;i++)
  {
    var elemName=StrUtil.trim(elemNameArr[i]);
    var elemArr=document.getElementsByName(elemName);
    if(elemArr.length<2)
      continue;
    if(curIndex==null || curIndex==-1)
      curIndex=elemArr.length-1;
    else if(curIndex==0 || curIndex>elemArr.length-1)
      continue;
    if(isInnerHTML && (typeof elemArr[curIndex].value=="undefined"))
    {
      elemArr[curIndex].innerHTML=elemArr[curIndex-1].innerHTML;
    }
    if(typeof elemArr[curIndex].type=="undefined")
        continue;
    switch(elemArr[curIndex].type.toLowerCase())
    {
      case "text":
      case "textarea":
      case "password":
      case "hidden":
      case "file":
        elemArr[curIndex].value=elemArr[curIndex-1].value;
        elemArr[curIndex].defaultValue=elemArr[curIndex-1].defaultValue;
        break;
      case "select-one":
      case "select-multiple":          
        var optionArr=elemArr[curIndex].options;
        var optionArr2=elemArr[curIndex-1].options;
        for(var j=0;j<optionArr.length;j++)
        {
          optionArr[j].selected=optionArr2[j].selected;
          optionArr[j].defaultSelected=optionArr2[j].defaultSelected;
        }
        if(typeof elemArr[curIndex].selectedIndex!="undefined")//��ѡ����û���������
        {
          elemArr[curIndex].selectedIndex=elemArr[curIndex-1].selectedIndex;
        }
        break;
      case "checkbox":
      case "radio":
        elemArr[curIndex].checked=elemArr[curIndex-1].checked;
        elemArr[curIndex].defaultChecked=elemArr[curIndex-1].defaultChecked;
        break;
      default:        
        break;
    }
  }
};

/**
 * �������ָ��Ԫ�ص�ֵ��
 * @param elemNames Ԫ�����Ƽ�����","�����
 * @param fromIndex ���ڶ��ͬ��Ԫ��ʱ����ʼԪ�����������Ϊnull��-1����ȡ���һԪ�ء�
 * @param endIndex ���ڶ��ͬ��Ԫ��ʱ����ֹԪ�����������Ϊnull��-1����ȡ���һԪ�ء�
 * @param isInnerHTML ���û��value���ԣ��Ƿ���innerHTML,Ĭ��Ϊtrue.
 */
PubUtil.clearElemValues=function(elemNames,startIndex,endIndex,isInnerHTML)
{
  if(isInnerHTML==null)
    isInnerHTML=true;
  var elemNameArr=elemNames.split(",");
  for(var i=0;i<elemNameArr.length;i++)
  {
    var elemName=StrUtil.trim(elemNameArr[i]);
    var elemArr=document.getElementsByName(elemName);
    if(elemArr.length==0)
      continue;
    if(startIndex==null || startIndex==-1)
      startIndex=elemArr.length-1;
    if(endIndex==null || endIndex==-1)
      endIndex=elemArr.length-1;
    for(var j=startIndex;j<=endIndex;j++)
    {
      if(isInnerHTML && (typeof elemArr[j].value=="undefined"))
      {
        elemArr[j].innerHTML="";
      }
      if(typeof elemArr[j].type=="undefined")
        continue;
      switch(elemArr[j].type.toLowerCase())
      {
        case "text":
        case "textarea":
        case "password":
        case "hidden":
        case "file":
          elemArr[j].value="";
          break;
        case "select-one":
        case "select-multiple": 
          elemArr[j].value="";
          var optionArr=elemArr[j].options;
          for(var k=0;k<optionArr.length;k++)
          {
            optionArr[k].selected=false;
          }
          break;
        case "checkbox":
        case "radio":
          elemArr[j].checked=false;
          break;
        default:        
          break;
      }
    }
  }
};


/**
 * ��Ԫ�ص�ǰֵ����ĳֵ.
 * @param elem Ԫ�ض���
 * @param addValue ���ӵ�ֵ��
 * @param isInnerHTML ���û��value���ԣ��Ƿ�����innerHTML,Ĭ��Ϊtrue.
 */
PubUtil.addElemValue=function(elem,addValue,isInnerHTML)
{
  if(elem==null)
    return;
  if(isInnerHTML==null)
    isInnerHTML=true;
  if(elem.tagName=="input" && (elem.type.toLowerCase()=="checkbox" || elem.type.toLowerCase()=="radio"))
    return;
  if(typeof elem.value !="undefined")
  {
    var newValue=parseFloat(addValue)+parseFloat(elem.value);
    if(!isNaN(newValue))
      elem.value=newValue;
  }
  else if(isInnerHTML && (typeof elem.innerHTML!="undefiend"))
  {
    if(isNumber(elem.innerHTML))
    {
      var newValue=parseFloat(addValue)+parseFloat(elem.innerHTML);
      if(!isNaN(newValue))
        elem.innerHTML=newValue;
    }    
  }
};


/**
 * ����Ԫ��ֵ.
 * @param elem Ԫ�ض���
 * @param value Ԫ��ֵ��
 * @param isInnerHTML ���û��value���ԣ��Ƿ�����innerHTML,Ĭ��Ϊtrue.
 */
PubUtil.setElemValue=function(elem,value,isInnerHTML)
{
  if(elem==null)
    return;
  if(isInnerHTML==null)
    isInnerHTML=true;
  if(elem.tagName=="input" && (elem.type.toLowerCase()=="checkbox" || elem.type.toLowerCase()=="radio"))
  {
    if(value==elem.value)
      elem.checked=true;
    else
      elem.checked=false;
    return;
  }
  if(typeof elem.value !="undefined")
    elem.value=value;//����select��ѡҲ��Ч��
  else if(isInnerHTML && (typeof elem.innerHTML!="undefiend"))
    elem.innerHTML=value;
};


/**
 * ȡԪ��ֵ.
 * @param elem Ԫ�ض���
 * @param isInnerHTML ���û��value���ԣ��Ƿ���innerHTML,Ĭ��Ϊtrue.
 * @return Ԫ��ֵ��
 */
PubUtil.getElemValue=function(elem,isInnerHTML)
{
  if(elem==null)
    return;
  if(isInnerHTML==null)
    isInnerHTML=true;
  if(typeof elem.value !="undefined")
    return elem.value;//����select��ѡҲ��Ч��
  else if(isInnerHTML && (typeof elem.innerHTML!="undefiend"))
    return elem.innerHTML;
};

/**
 * ����Ԫ������ȡԪ�ض���
 * @param elemName Ԫ�����ƣ���id.
 * @param index ������ڶ��ͬ��Ԫ�أ�Ԫ�ص�λ��������=-1��nullʱȡ���һ��.
 * @return Ԫ�ض���
 */
PubUtil.getElemObj=function(elemName,index)
{
  var elemArr=document.getElementsByName(elemName);
  if(elemArr.length==0)
  {
    var elem=document.getElementByID(elemName);
    return elem;
  }
  if(index==-1 || index==null)
    return elemArr[elemArr.length-1];
  if(index>elemArr.length-1)
    return null;
  else
    return elemArr[index];
};

/**
 * ������Ԫ��ֵ��
 * @param parentElem ��Ԫ�ؼ���
 */
PubUtil.resetChildValues=function(parentElem)
{
  var childElems=PubUtil.getEditChildElems(parentElem);
  for(var e=0;e<childElems.length;e++)
  {
    var elem=childElems[e];
    var elemType=elem.type.toLowerCase();
    switch(elemType)
    {
      case "text":
      case "textarea":
      case "password":
      case "hidden":
      case "file":   
        elem.value=elem.defaultValue;
	      break;   
      case "select-one":        
      case "select-multiple":
        elem.value=elem.defaultValue;//�����������selectedǰ�棬
        //optionsԭ��û��һ��ѡ��,����selected����ı�value��
        var optionArr=elem.options;
        for(var e2=0;e2<optionArr.length;e2++)
        {
          optionArr[e2].selected=optionArr[e2].defaultSelected;
        }
	      break;
      case "checkbox":
      case "radio":
        elem.checked=elem.defaultChecked;
	      break;
    }
  }
};

/**
 * ȡ�༭��Ԫ�ؼ���
 * @param parentElem ��Ԫ�ض���
 * @return �༭��Ԫ�ؼ���
 */
PubUtil.getEditChildElems=function(parentElem)
{
  var tmp=parentElem.childNodes;
  if(tmp==null)
    return new Array(0);
  var objArr=new Array(tmp.length);
  for(var i=0;i<tmp.length;i++)//ֱ����tmp.concat()����
  {
    objArr[i]=tmp[i];
  }
  var editElems=new Array();
  var counter=0;
  for(var i=0;i<objArr.length;i++)
  {
    if(typeof objArr[i]!="object")
      continue;
    var isAddChildNodes=true;
    if(typeof objArr[i].type !="undefined")
    {
      isAddChildNodes=false;
      switch(objArr[i].type.toLowerCase())
      {
        case "text":
        case "textarea":
        case "password":
        case "hidden":
        case "file":
        case "select-one":
        case "select-multiple":          
        case "checkbox":
        case "radio":
          editElems[counter++]=objArr[i];
          break;
        default:
          isAddChildNodes=true;
          break;
      }
    }
    if(!isAddChildNodes)
      continue;
    if(objArr[i].childNodes==null)
      continue;
    for(var j=0;j<objArr[i].childNodes.length;j++)
    {
      objArr[objArr.length]=objArr[i].childNodes[j];
    }
    //objArr=objArr.concat(objArr[i].childNodes);//������
  }
  return editElems;
};
