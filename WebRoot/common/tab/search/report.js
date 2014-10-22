//������صĺ���

/**
 * �����ܺ�����
 */
Report=function Report(){};

/**
 * �Զ�ˢ�±���.
 * @param interval ʱ��������λs��
 */
Report.autoRefresh=function(interval)
{
  setTimeout("Report.queryRep()",interval*1000);
}

//ԭ����ɫ��
Report.preBgColor="";

/**
 * ����Ƶ�����ʱ�ı䱳��ɫ.
 * @param obj ����,��tr,td.
 */
Report.listMouseOver=function(obj)
{
  if(obj.sectionRowIndex==0)//see listMouseOut for reason
	  return;
  var origBgColor=obj.origBgColor;
  if(!origBgColor || origBgColor=="")//��ʼ��
  {
    if(obj.style.backgroundColor!="")
      obj.origBgColor=obj.style.backgroundColor;
    else
      obj.origBgColor="none";//ԭ��û���豳��ɫ��
  }
  
  Report.preBgColor=obj.style.backgroundColor;
  obj.style.backgroundColor="#FFCC66";
}

/**
 * ����Ƴ�����ʱ�ָ���ɫ.
 * @param obj ������tr,td.
 */
Report.listMouseOut=function(obj)
{
  if(obj.sectionRowIndex==0)//see below
	  return;
  obj.style.backgroundColor=Report.preBgColor;//��������ڵ�һ�д��е�����������г����ر������,��Ч��Զ�������档	
  //if(obj.className=="repList11")//�����Ĵ���ʽЧ�ʲ���
	  //obj.className="repList1";
  //else
	  //obj.className="repList2";
}

/**
 * ��������.
 * @param linkURL ����URL.
 * @param target �����ӵ�target.
 * @param queryInfo ѯ����Ϣ.
 */
Report.handleLink=function(linkURL,target,queryInfo)
{
  if(!target || target=="")
    target="_self";
  if(target.toLowerCase()=="_self" && AppUtil.isFormModified(document.repForm))
  {
    if(!confirm("�����Ѹ��ģ������������Ϣ��ʧ���Ƿ������"))
      return;
  }
  var origCallerPageURL="";
  if(target.toLowerCase()!="_self")
  {
    origCallerPageURL=document.linkForm.callerPageURL.value;
    document.linkForm.callerPageURL.value="";	
  }
  if(queryInfo!=null && queryInfo!="")
  {
    if(!confirm(queryInfo))
      return;
  }
  if(StrUtil.trim(linkURL.toLowerCase()).indexOf("javascript")==0)
    eval(linkURL);//must use eval(),for maybe containing "\'" or "\"" in linkURL
  else   
    AppUtil.doPostSubmit(document.linkForm,linkURL,target);
  if(target.toLowerCase()!="_self")
  {
	  document.linkForm.callerPageURL.value=origCallerPageURL;
  }
}

/**
 * ���ص���ҳ.
 */
Report.handleBack=function()
{
  if(AppUtil.isFormModified(document.repForm))
  {
    if(!confirm("�����Ѹ��ģ������������Ϣ��ʧ���Ƿ������"))
      return;
  }
  AppUtil.doPostSubmit(document.backForm,document.repForm.backActURL.value);
}

/**
 * ��ʾ/��������
 */
Report.showHideCnd=function()
{
  var obj=document.getElementById("showHideCnd");
  if(obj.innerHTML=="��ʾ����")
  {
    obj.innerHTML="��������";
    PubUtil.showHideElement("cndContainer",true);
  }
  else
  {
    obj.innerHTML="��ʾ����";
    PubUtil.showHideElement("cndContainer",false);
  }
}

/**
 * ��ʾ/���ع�����
 */
Report.showHideToolBand=function()
{
  var obj=document.getElementById("showHideToolBand");
  if(obj.innerHTML=="��ʾ������")
  {
    obj.innerHTML="���ع�����";
    PubUtil.showHideElement("topToolBand",true);
  }
  else
  {
    obj.innerHTML="��ʾ������";
    PubUtil.showHideElement("topToolBand",false);
  }
}


/**
 * ��ת��ָ��������ҳ��.
 */
Report.toPage=function(pageNo)
{
  document.repForm.toPageNo.value=pageNo;
  Report.submitRepForm();
}

/**
 * ��ת��ѡ���ı�����ҳ��.
 */
Report.toSelPage=function(isTopBar)
{
  var selObj=null;
  var inputObj=null;
  if(isTopBar)
  {
    selObj=document.repForm.selToPageNo;
    inputObj=document.repForm.inputToPageNo;
  }
  else
  {
    selObj=document.repForm.selToPageNo2;
    inputObj=document.repForm.inputToPageNo2;
  }
  var pageNo="1";
  if(selObj.style.display.toLowerCase()!="none")
	  pageNo=selObj.value;
  else
	  pageNo=inputObj.value;
  if(pageNo=='' || isNaN(pageNo))
  {
    alert("�����ҳ�ŷ�����!");
    return;
  }
  document.repForm.toPageNo.value=pageNo;
  Report.submitRepForm();
}


/**
 * ����ɾ������
 * @param linkURL ����URL.
 * @param target �����ӵ�target.
 * @param queryInfo ѯ����Ϣ.
 */
Report.batchDelete=function(linkURL,target,queryInfo)
{  
  var checkName="col_delrow_no";
  var itemArr=document.repForm.elements;
  var rowNum=1;
  var checkedNum=0;
  for(var i=0;i<itemArr.length;i++)
  {
    if(itemArr[i].name==checkName && itemArr[i].type.toLowerCase()=="checkbox")
    {
      itemArr[i].value=rowNum++;
      if(itemArr[i].checked)
        checkedNum++;
    }
  }
  if(checkedNum==0)
  {
    alert("û���趨Ҫɾ���ļ�¼��");
    return;
  }
  Report.submitRepForm(linkURL,target,queryInfo);
}

/**
 * �����кţ�base 1,��Ҫ����ȡ��ѡ�У��������������ԭ�Ȳ�ͬ��������������ĳ����(Ҫ�����к�ȡ��Ϣ)ǰҪ�����кš�
 * @param rowNoElemName �к�Ԫ�����ơ�
 */
Report.setRowNo=function(rowNoElemName)
{
  var itemArr=document.repForm.elements;
  var rowNum=1;
  for(var i=0;i<itemArr.length;i++)
  {
    if(itemArr[i].name==rowNoElemName && itemArr[i].type.toLowerCase()=="checkbox")
    {
      itemArr[i].value=rowNum;
      rowNum++;
    }
  }
}

/**
 * ��ѯ������ҳ����"��ѯ"��ť��.
 * @param onQueryEvent ��ѯ�¼���
 */
Report.queryRep=function(onQueryEvent)
{  
  if(onQueryEvent!=null && onQueryEvent.length>0)
  {
    if(eval(onQueryEvent)==false)
      return;
  }  
  if(document.repForm.isAgrNoCnd.value=="0")    
  {
    var checkName="selCndID";
    var itemArr=document.repForm.elements;
    var isValid=false;
    for(var i=0;i<itemArr.length;i++)
    {      
      if(itemArr[i].name==checkName)       
      {
      	if(itemArr[i].type.toLowerCase()!="checkbox")//��ѡ�������
      	{
      	  isValid=true;
      	  break;
      	}      
        else if(itemArr[i].type.toLowerCase()=="checkbox" && itemArr[i].checked==true)
        {
      	  isValid=true;
      	  break;
      	}
      }
    }
    if(!isValid)
    {
      alert("�ñ����������������������ѡ������!");
      return;
    }
  }
  //��֤
  var cndIDObjs=document.getElementsByName("selCndID");
  for(var i=0;i<cndIDObjs.length;i++)
  {
    if(!cndIDObjs[i].checked)
       continue;
    var cnds=document.getElementsByName("cnd_"+cndIDObjs[i].value);
    if(cnds.length>0 && cnds[0].value=="")
    {
      var nullable=cnds[0].getAttribute("cndnullable");
      if(nullable!=null && nullable=="0")
      {
        var cndDesc=cnds[0].getAttribute("itemdesc");
        if(cndDesc==null || cndDesc=="")
          cndDesc=cndID;
        alert("����:"+cndDesc+"ֵ������Ϊ��!");
        cnds[0].focus();
        return;
      }
    }
  }
  document.repForm.initQuery.value="1";
  Report.submitRepForm();  
}

/**
 * �ύ����������������
 * @param action repForm��actionֵ��
 * @param target ������target��
 * @param queryInfo ѯ����Ϣ��
 * @param isWithCurPageURL �Ƿ�����curPageURL������
 * @param onSubmitEvent ���ύ�¼���
 * @param isChkModified �Ƿ�����Ƿ���ġ�
 */
Report.submitRepForm=function(action,target,queryInfo,isWithCurPageURL,onSubmitEvent,isChkModified)
{
  if(onSubmitEvent!=null && onSubmitEvent.length>0)
  {
    if(eval(onSubmitEvent)==false)
      return;
  }
  if(target==null || target=="")
    target="_self";
  if(isChkModified && AppUtil.isFormModified(document.repForm))
  {
    if(!confirm("�����Ѹ��ģ������������Ϣ��ʧ���Ƿ������"))
      return;
  }
  if(queryInfo!=null && queryInfo!="")
  {
    if(!confirm(queryInfo))
      return;
  }
  if(action==null || action=="")
    action=Constants.contextRoot+"/sysmng.report.do";
  else if(action.substring(0,1)!="/")
    action=Constants.contextRoot+"/"+action;
  document.repForm.action=action;
  document.repForm.target=target;
  if(isWithCurPageURL!=null&&isWithCurPageURL==true)
    document.repForm.curPageURL.value=document.linkForm.callerPageURL.value;
  document.repForm.submit();
}

/**
 * �ύ�������²�����
 * @param action �����������µ�action,����Ϊ�գ�ȡĬ��ֵ��
 * @param onUpdateEvent ���������ύ�¼���
 */
Report.updRepForm=function(action,onUpdateEvent)
{
  if(!Validate.checkInputFull(document.repForm))
    return;
  if(!AppUtil.isFormModified(document.repForm,true))
  {
    alert("����û�и���,���ر���!");
    return;
  }
  if(onUpdateEvent!=null && onUpdateEvent.length>0)
  {
    if(eval(onUpdteEvent)==false)
      return;
  }
  if(action==null || action.length==0)
    action=Constants.contextRoot+"/sysmng.batchUpd.do";
  else if(action.substring(0,1)!="/")
    action=Constants.contextRoot+"/"+action;
  document.repForm.target="_self";
  document.repForm.action=action;
  document.repForm.submit();
  return;
}

/**
 * �ı䱨��������͡�
 * @param obj ֵ�ı��select����
 */
Report.chgExportType=function(obj)
{
  var value=obj.value;
  var obj2=null;
  if(obj.name=="exportType")
    obj2=document.repForm.exportType2;
  else
    obj2=document.repForm.exportType;
  obj2.value=value;
}

/**
 * ����������
 * @param exportType �������͡�
 * @param exportTplID �������������0����ģ�嵼����������ģ���ʶ��
 */
Report.exportRep=function(exportType,exportTplID)
{
  var isGetAll="0";
  var totalPage=document.repForm.totalPage.value;
  if(totalPage=="1")
    isGetAll="1";
  else if(!document.repForm.cbx_repExport || document.repForm.cbx_repExport.checked)
    isGetAll="1";
  document.repForm.isExportAll.value=isGetAll;
  if(exportType==null)
    exportType="1";//document.repForm.exportType.value;
  document.repForm.exportType.value=exportType;
  if(exportTplID!=null)
    document.repForm.exportTplID.value=exportTplID;
  document.repForm.action=Constants.contextRoot+"/sysmng.repExport.do";
  if(exportType=="3")//html
    document.repForm.target="_blank";
  else
    document.repForm.target="repExportor";//���ie6�Ͱ汾6.0.29�£��ܾ����ʵ�bug,������"_self"
  document.repForm.submit();
  return;
}


/**
 * ��ʼ�����ܱ���༭��
 */
Report.initSrEdit=function()
{
  document.repForm.target="_self";
  document.repForm.action=Constants.contextRoot+"/sysmng.report.do";
  document.repForm.isSrEdit.value="1";
  document.repForm.submit();
}

/**
 * ���ܱ���༭��
 */
Report.srRstSave=function()
{
  document.repForm.target="_self";
  document.repForm.action=Constants.contextRoot+"/sysmng.srRstSave.do";
  document.repForm.submit();
}


/**
 * ��������У�顣
 * @param checkID У��ID��
 */
Report.checkRst=function(checkID)
{
  if(checkID==null || checkID=="")
    alert("����У��ʱ�����ṩУ���ʶ��");
  document.repForm.checkID.value=checkID;
  document.repForm.action=Constants.contextRoot+"/sysmng.repRstCheck.do";
  document.repForm.target="repExportor";//���ie6�Ͱ汾6.0.29�£��ܾ����ʵ�bug,������"_self"
  document.repForm.submit();
}



/**
 * ���ñ���������
 */
Report.resetCnd=function()
{
/*
  var checkName="selCndID";
  var itemArr=document.repForm.elements;  
  for(var i=0;i<itemArr.length;i++)
  {    
    if(itemArr[i].name==checkName && itemArr[i].type.toLowerCase()=="checkbox")
      itemArr[i].checked=itemArr[i].defaultChecked;
    else if(itemArr[i].name.indexOf("cnd_")>-1)
    {
      var type=itemArr[i].type.toLowerCase();
      switch(type)
      {
       	case "text":
        case "textarea":
        case "password":
        case "hidden":
        case "file":
      	  itemArr[i].value=itemArr[i].defaultValue;
      	  break;
      	case "select-one":
      	case "select-multiple":
      	  var optionArr=itemArr[i].options;
      	  var isSelected=false;
          for(var j=0;j<optionArr.length;j++)
          {
            optionArr[j].selected=optionArr[j].defaultSelected;
            if(optionArr[j].defaultSelected)
              isSelected=true;
          }
          if(!isSelected && optionArr.length>0 && type=="select-one")
            optionArr[0].selected=true;
	        break;
	      case "checkbox":
        case "radio":
          itemArr[i].checked=elemArr[i].defaultChecked;
      }      	
    }
  }
  */
  document.getElementById("searchDIV").style.display='none';
  var v2 = document.getElementById("showMode");
  var temp = document.all?"className":"class";
  v2.setAttribute(temp,v2.getAttribute(temp)=="showMode2"?"showModel2":"showModel");
}

/**
 * �����Ƴ�����ʱ,���ڵ����˵��жϡ�
 */
Report.myout=function(statID,layerID)
{
  PubUtil.findObj(statID).value="0";
  window.setTimeout("Report.mycheck('"+statID+"','"+layerID+"')",5);
}

/**
 * �����������ʱ,���ڵ����˵��жϡ�
 */
Report.myin=function(statID)
{
  PubUtil.findObj(statID).value="1";
}


/**
 * ���ڴ����Ƿ����ز㡣
 */
Report.mycheck=function(statID,layerID)
{
  if (PubUtil.findObj(statID).value=="0")
    PubUtil.showHideLayers(layerID,"","hide");
}


/**
 * ��ӡ����.
 * @param subSys ��ϵͳ��ʶ.
 * @param repID �����ʶ��
 */
Report.printRep=function(subSys,repID)
{  
  var container=document.getElementById("printContainer");
  if(container.getAttribute("src")=="")
  {    
    var src=Constants.contextRoot+"/charisma/syspub/report/printRep.jsp?subSys="+subSys+"&repID="+repID+"&opType=print";
    container.setAttribute("src",src);
  }
  else
  {
    //������container�����Ҳ���document.������firefox��ns�л����
    printContainer.focus();
    //firefox��container.content.document.doPrint("print");
    //ie��document.printContainer.doPrint("print");
    printContainer.doPrint("print");
  }
}

/**
 * ��ӡԤ����
 * @param subSys ��ϵͳ��ʶ��
 * @param repID �����ʶ��
 */
Report.previewRep=function(subSys,repID)
{  
  var location=Constants.contextRoot+"/charisma/syspub/report/printRep.jsp?subSys="+subSys+"&repID="+repID+"&opType=preview";
  var win=AppUtil.popWindow(location,'previewRep',630,540,"scrollbars=1");
}

/**
 * �������á�
 * @param subSys ��ϵͳ��ʶ��
 * @param repID �����ʶ��
 */
Report.reportSet=function(subSys,repID)
{
  var repListFmt=PubUtil.myEscape(document.repForm.repListFmt.value);
  var perRows=document.repForm.userPerRows.value;
  var prtZoom=document.repForm.prtZoom.value;
  var dispType=document.repForm.dispType.value;
  var location=Constants.contextRoot+"/charisma/syspub/report/reportSet.jsp?subSys="+subSys+"&repID="+repID+"&repListFmt="+repListFmt
	  +"&userPerRows="+perRows+"&prtZoom="+prtZoom+"&dispType="+dispType;
  var win=AppUtil.popWindow(location,'reportSet',450,400,"scrollbars=1");
}

/**
 * �����������ñ��������ֵ����������û�ָ����URL����
 * @param popDicURL �����ֵ�URL��
 * @param imgObj �򿪵������ڵ�ͼƬԪ�ء�
 */
Report.cndPopSetDic=function(popDicURL,imgObj)
{
  AppUtil.popSetDic2(popDicURL,imgObj,"getRepCndElemValue");
}

/**
 * �����������е��ֵ�����Ԫ��ֵ�������������Ӧ����δѡ�У�����Ӧֵȡ�մ���
 * @param index �ֵ��Ӧ��������ڵ�ǰ���ж�Ӧ��������
 * @param elemName �ֵ�����Ԫ�ر�ǩ���ơ�
 * @return Ԫ��ֵ��
 */
Report.getCndElemValue=function(index,elemName)
{
  var elemValue=getDicCndElemValue(index,elemName);
  var index=elemName.indexOf("cnd_");
  if(index==-1)
	  return elemValue;
  var cndID=elemName.substr(4);
  var cbxElem=PubUtil.findObj("selCndID_"+cndID,document);
  if(!cbxElem || cbxElem.type.toLowerCase()!="checkbox")
	  return elemValue;
  if(!cbxElem.checked)
	  return ""
  else
    return elemValue;
}

/**
 * ����ѡ���ֵ�����
 */
Report.fireSetPopDic=function()
{
  if(top && top.footer)
  {
	  top.footer.setOnePopDic();
  }	
}

/**
 * �л�����ʱ�Ƿ�ȫ������
 * @param cbx checkbox�ؼ���
 */
Report.switchExportAll=function(cbx)
{
  if(cbx.checked)
  {
    document.repForm.cbx_repExport.checked=true;
    document.repForm.cbx_repExport.checked=true;
  }
  else
  {
    document.repForm.cbx_repExport.checked=false;
    document.repForm.cbx_repExport2.checked=false;
  }
}

/**
 * ת��ͼ����ʾ��
 * @param chartNo ͼ����š�
 */
Report.switchChart=function(chartNo)
{
  if(AppUtil.isFormModified(document.repForm))
  {
    if(!confirm("�����Ѹ��ģ������������Ϣ��ʧ���Ƿ������"))
      return;
  }
  if(typeof document.repForm.chartNo == "undefined")
    return;
  document.repForm.chartNo.value=chartNo;
  document.repForm.submit();
}



/**
 * �����ǩҳ��
 * @param pageTagID ��ǩҳ��Ӧ��Tag��ʶ��
 */
Report.clickTabPage=function(pageTagID)
{
  if(!TabUtil.clickTabPage(pageTagID))
    return;
  var pos=pageTagID.lastIndexOf("_");
  var prefix=pageTagID.substring(0,pos);
  var pageIndex=new Number(pageTagID.substring(pos+1));
  var pageObj=document.getElementById(prefix+"c_"+pageIndex);
  if(pageObj==null)
    return;
  if(pageObj.getAttribute("isShowRst")=="0")//pageObj.src��firefox����Ч
  {
    pageObj.innerHTML="<p class=\"redNormal\">���ڲ�ѯ�����Ժ�...</p>";
    var repURL=pageObj.getAttribute("subRepURL");
    var xHttp=new XHttp("sysmng.repRst.do");
    xHttp.method="POST";
    xHttp.reqData="isReLogon=0&repURL="+PubUtil.myEscape(repURL);
    xHttp.callback=function(xmlHttp)
    {
      var repRst=xmlHttp.responseText;
      var isSuccess=false;
      if(repRst.substring(0,8)=="<repRst>")
      {
        repRst=repRst.substring(8,repRst.length-9);
        pageObj.setAttribute("isShowRst","1");
        isSuccess=true;
      }
      else if(repRst.substring(0,7)=="<error>")
        repRst="<p class=\"redNormal\">"+repRst.substring(7,repRst.length-8)+"</p>";
      else
      {
        repRst="<p class=\"redNormal\">"+AppUtil.getErrInfo(repRst)+"</p>";
        if(repRst=="")
          repRst="<p class=\"redNormal\">��ѯʱ������鿴��־!</p>";
      }
      pageObj.innerHTML=repRst;
      if(isSuccess)
        SortableTable_init();//�������б���ʼ������
    }
    xHttp.send();    
  }
};
