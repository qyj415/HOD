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

		
			
			
			//function setdo(val) {
			//   return "详细";
			//}
			// showPatManage(document.getElementById(
			// create the data store
			// alert("start store");
			store1 = new Ext.data.ArrayStore({
						fields : [{
									name : 'buiName',
									type : 'string'
								}, {
									name : 'buiAddress',
									type : 'string'
								}, {
									name : 'buiDescribe',
									type : 'string'
								}]
					});
				//	alert("end store"+store1);
			store1.loadData(myData);
         
			var cm = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), // 行号列
					{
						id : 'pat',
						header : "建筑名称",
						width : 200,
						sortable : true,
						//renderer : setpid,
						dataIndex : 'buiName'
					}, {
						header : "所在地址",
						width : 300,
						sortable : true,
						//renderer : settype,
						dataIndex : 'buiAddress'
					}, {
						header : "备注",
						width : 300,
						sortable : true,
						//renderer : setsvc,
						dataIndex : 'buiDescribe'
					}]);

			// create the Grid
			var grid = new Ext.grid.GridPanel({
				store : store1,
				cm : cm,
				//stripeRows : true,
				//autoExpandColumn : 'pat',
				emptyMsg: "No result to display", 
				height : 485,
				width : 820,
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
        	var sUrl = '/IPnet2kLC/BuildingAction!bList.action';
        	var cccc ='sss=333';
        
            var oRequest = new XMLHttpRequest();
            oRequest.onreadystatechange = function() {
               alert("ddddd");
                if(oRequest.readyState == 4) {   
                  alert("eeeee---ddddddddde");
                   
                    var oBook = eval('(' + oRequest.responseText + ')');
                 alert("0");
                   
                   
                   var myData = new Array();
                   alert("1");
                   myData = oBook.buildingList;
                   alert("2");
                   if (myData.length<1){
                   	myData[0]=['无相关记录![]'];
                   }
                 alert("length:"+myData.length);
                  alert("myData 1 :"+myData[1]);
					store1.loadData(myData);
					//翻页连接
					
				
					//alert(currpage+"/"+totalpage);
					document.getElementById('pagerlink').innerHTML ="";
				
					
		          document.getElementById("divHalfTransparent").style.display="none";
                }
                
            };
            oRequest.open('post', sUrl,true);
            oRequest.setRequestheader("cache-control","no-cache");
            oRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            oRequest.send(cccc);
        }
    }
 