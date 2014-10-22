/*
 * ! Ext JS Library 3.0.0 Copyright(c) 2006-2009 Ext JS, LLC licensing@extjs.com
 * http://www.extjs.com/license
 */
var store1 = null;

Ext.onReady(function() {

			// NOTE: This is an example showing simple state management. During
			// development,
			// it is generally best to disable state management as
			// dynamically-generated ids
			// can change across page loads, leading to unpredictable results.
			// The developer
			// should ensure that stable state ids are set for stateful
			// components in real apps.
			//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
           
			var myData = [];

			// example of custom renderer function
			function change(val) {
				if (val > 0) {
					return '<span style="color:green;">' + val + '</span>';
				} else if (val < 0) {
					return '<span style="color:red;">' + val + '</span>';
				}
				return val;
			}

			// example of custom renderer function
			function pctChange(val) {
				if (val > 0) {
					return '<span style="color:green;">' + val + '%</span>';
				} else if (val < 0) {
					return '<span style="color:red;">' + val + '%</span>';
				}
				return val;
			}
			function setsvc(val) {
				if (val == 'Y') {
					return '<span style="color:green;">服务中</span>';
				} else if (val == 'N') {
					return '<span style="color:gray;">终止</span>';
				} else if (val == 'P') {
					return '<span style="color:yellow;">暂停</span>';
				} else if (val == 'T') {
					return '<span style="color:blue;">已过期</span>';
				}
				return val;
			}
			function settype(val) {
				if (val == 'T') {
					return '<span style="color:gray;">试用</span>';
				} else if (val == 'N') {
					return '<span>普通</span>';
				} else if (val == 'V') {
					return '<span style="color:red;">VIP</span>';
				}
				return val;
			}
			function setpid(val) {
				var pid =val.split('[')[1].split(']')[0]; 
			   return '<span onclick="showPatManage(\''+pid+'\')" style="cursor:hand;">'+val.split('[')[0]+' '+pid+'</span>';
			}
			
			function setdateymd(val) {
				
			   return val.split('T')[0];
			}
			
			
			//function setdo(val) {
			//   return "详细";
			//}
			// showPatManage(document.getElementById(
			// create the data store
			// alert("start store");
			store1 = new Ext.data.ArrayStore({
						fields : [{
									name : 'patients',
									type : 'string'
								}, {
									name : 'type',
									type : 'string'
								}, {
									name : 'svc',
									type : 'string'
								}, {
									name : 'svctype',
									type : 'string'
								}, {
									name : 'first',
									type : 'string'
								}, {
									name : 'nur',
									type : 'string'
								}, {
									name : 'doc',
									type : 'string'
								}, {
									name : 'start',
									type : 'string'
								}, {
									name : 'last',
									type : 'string'
								}]
					});
				//	alert("end store"+store1);
			store1.loadData(myData);
         
			var cm = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), // 行号列
					{
						id : 'pat',
						header : "患者",
						width : 130,
						sortable : true,
						renderer : setpid,
						dataIndex : 'patients'
					}, {
						header : "类别",
						width : 50,
						sortable : true,
						renderer : settype,
						dataIndex : 'type'
					}, {
						header : "状态",
						width : 55,
						sortable : true,
						renderer : setsvc,
						dataIndex : 'svc'
					}, {
						header : "服务包",
						width : 65,
						sortable : true,
						// renderer : pctChange,
						dataIndex : 'svctype'
					}, {
						header : "首诊信息",
						sortable : true,
						width : 135,
						// renderer : pctChange,
						dataIndex : 'first'
					}, {
						header : "责任护士",
						width : 65,
						sortable : true,
						// renderer : pctChange,
						dataIndex : 'nur'
					}, {
						header : "责任医生",
						width : 65,
						sortable : true,
						// renderer :  setdo,
						dataIndex : 'doc'
					}, {
						header : "开始服务时间",
						width : 100,
						sortable : true,
						renderer : setdateymd,
						dataIndex : 'start'
					}, {
						header : "最后处理时间",
						width : 100,
						sortable : true,
						renderer : setdateymd,
						dataIndex : 'last'
					}]);

			// create the Grid
			var grid = new Ext.grid.GridPanel({
				store : store1,
				cm : cm,
				//stripeRows : true,
				//autoExpandColumn : 'pat',
				emptyMsg: "No result to display", 
				height : 485,
				width : 810,
				title : 'Array Grid',
				header : false, // 不创建标题栏
				headerAsText : false
					// 如果有标题栏, 隐藏标题栏
				});
			 grid.render('grid-example');
             retrieveBook('','','');
		});

var bXmlHttpSupport = (typeof XMLHttpRequest != "undefined" || window.ActiveXObject);
     
    if (typeof XMLHttpRequest == "undefined" && window.ActiveXObject) {
        function XMLHttpRequest() {
            var arrSignatures = ["MSXML2.XMLHTTP.5.0", "MSXML2.XMLHTTP.4.0",
                                 "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP",
                                 "Microsoft.XMLHTTP"];
                             
            for (var i=0; i < arrSignatures.length; i++) {
                try {        
                    var oRequest = new ActiveXObject(arrSignatures[i]);            
                    return oRequest;        
                } catch (oError) { /*ignore*/ }
            }          
    
            throw new Error("MSXML is not installed on your system.");               
        }
    }    
   
      
    function retrieveBook(pageMethod,totalRows,currentPage) {  
     
    	document.getElementById("divHalfTransparent").style.display="";
        if(bXmlHttpSupport) {
            //var sUrl = '/bpm/PatList!docPatList.action?3777=s&pid='+document.getElementById("pid").value+'&sta='+document.getElementById("sta").value+'&hos='+document.getElementById("hos").value+'&service='+document.getElementById("service").value+'&psex='+document.getElementById("psex").value+'&pageMethod='+pageMethod+'&totalRows='+totalRows+'&currentPage='+currentPage;
        	var sUrl = '/bpm/PatList!docPatList.action';
        	var cccc ='sss=333&pid='+document.getElementById("pid").value+'&sta='+document.getElementById("sta").value+'&hos='+document.getElementById("hos").value+'&service='+document.getElementById("service").value+'&psex='+document.getElementById("psex").value+'&pageMethod='+pageMethod+'&totalRows='+totalRows+'&currentPage='+currentPage;
           // alert(sUrl);
            //var sUrl = 'Ajax!getPatList.action';
            var oRequest = new XMLHttpRequest();
            oRequest.onreadystatechange = function() {
           // alert("ddddd");
                if(oRequest.readyState == 4) {   
                //alert("eeeeeee");
                   
                    var oBook = eval('(' + oRequest.responseText + ')');
                   // window.open(oBook);
                   // alert(oBook.docPatList.length);
                    // alert(oBook.docPatList[0].length);
                   // var bookHolder = document.getElementById('bookHolder');
                    
                   //  bookHolder.innerHTML =      oRequest.responseText;            
                     
                   // document.getElementById('grid-example111').innerHTML =  oRequest.responseText;
                    
                   // var sBook = "";
                   
                   
                   var myData = new Array();
                   myData = oBook.docPatList;
                   if (myData.length<1){
                   	myData[0]=['无相关记录![]'];
                   }
                   /**
                     for(i = 0; i < oBook.docPatList.length; i++) {
                      try{
                      
                        //var a=' <a target="_blank" href="report/reportPdf/'+oBook.hisRecord[i].split("desc:")[1].split("报告文件:")[1]+'">报告文件</a>'; 
                        //sBook +=  oBook.hisRecord[i].split("desc:")[1].split("报告文件:")[0] +a+" <br/>";
                        // alert(oBook.docPatList[i]);
                         myData[i] = oBook.docPatList[i];
                        // alert(myData[i]);
                        }catch(fail){
                        }
                    }
                    **/
                   // bookHolder.innerHTML = "";
                    /**
                    var myData = [
					["3m Co", 71.72, 0.02, 0.03, "9/1 12:00am"],
					["General Electric Company", 34.14, -0.08, -0.23,
							"9/1 12:00am","dd"],
					["Verizon Communications", 35.57, 0.39, 1.11, "9/1 12:00am","dd"],
					["Wal-Mart Stores, Inc.", 45.45, 0.73, 1.63, "9/1 12:00am","dd"]];
					**/
					//alert(oBook.docPatList);
					//var mydata1 = [oBook.docPatList];
					//load data
                  // alert("start show"+store1);
					store1.loadData(myData);
					//翻页连接
					
					var  currpage = oBook.pager.currentPage;
					var  totalpage = oBook.pager.totalPages;
					//alert(currpage+"/"+totalpage);
					document.getElementById('pagerlink').innerHTML ="";
					printPagerHref(currpage,totalpage);
					
		          document.getElementById("divHalfTransparent").style.display="none";
                }
                
            };
            oRequest.open('post', sUrl,true);
            oRequest.setRequestheader("cache-control","no-cache");
            oRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            oRequest.send(cccc);
        }
    }

function printPage(x,y,currpage,totalpage){
 var aaaa = "";
 if (currpage>1){
   aaaa =aaaa + (" <span class='pagebox_pre'><a href='#' onclick='retrieveBook("+(currpage-1)+","+totalpage+","+currpage+")'>上一页</a></span> ");
   }
   
  if (x>1){
   aaaa =aaaa + ("<span class='pagebox_pre'><a href='#' onclick='retrieveBook(1,"+totalpage+","+currpage+")'>1...</a></span> ");
  }
 for (i=x;i<=y;i++){

 if (i==currpage){
   aaaa =aaaa + (" <span class='pagebox_num_nonce'>"+i+"</span> ");
  }else{
   aaaa =aaaa + (" <span class='pagebox_num'><a href='#' onclick='retrieveBook("+i+","+totalpage+","+currpage+")'>"+i+"</a></span> ");
 }
 }
 
 if (y<totalpage){
    aaaa =aaaa + (" <span class='pagebox_next'><a href='#' onclick='retrieveBook("+totalpage+","+totalpage+","+currpage+")'>..."+totalpage+"</a></span>");
  }
   
if (currpage<totalpage){
    aaaa =aaaa + (" <span class='pagebox_next'><a href='#' onclick='retrieveBook("+(currpage+1)+","+totalpage+","+currpage+")'>下一页</a></span> ");
 }
 
 document.getElementById('pagerlink').innerHTML = aaaa;
}

function printPagerHref(currpage,totalpage){
var x = currpage-5;
var y = currpage+5;
//alert("x="+x);
//alert("y="+y);
if (x<1) x=1;
if (y>totalpage) y=totalpage;
if ((x==1) && (y==1)){
 }else{
  printPage(x,y,currpage,totalpage);
 }
} 