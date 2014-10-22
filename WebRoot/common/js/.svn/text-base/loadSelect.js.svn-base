
 /*
 The level of contact to select in add concentrator
 */
 var isLoadOver = false;

 function loadSelect(type,name,historyId){
 	isLoadOver = false;
 	//清空操作
 	switch(type){
 	
	case 1:
	    $("#region").empty();
		$("#city").empty();
		$("#county").empty();
		$("#village").empty();
		$("#community").empty();
		$("#building").empty();	
		break;	
	case 2:
		$("#city").empty();
		$("#county").empty();
		$("#village").empty();
		$("#community").empty();
		$("#building").empty();		
		break;		
	case 3:
		$("#county").empty();
		$("#village").empty();
		$("#community").empty();
		$("#building").empty();		
		break;	
	case 4:
		$("#village").empty();
		$("#community").empty();
		$("#building").empty();			
		break;	
	case 5:
		$("#community").empty();
		$("#building").empty();		
		break;
	case 6:
		$("#building").empty();		
		break;
	}
	
 	var value='';
 	if(historyId==""||historyId==undefined){
 		value = $('#'+name).val();
 	}else{
 		value = historyId;
 	}
 	Ext.Ajax.request({
					url : "/HOD-2000/hodConcentrator!showAddr.do",
					method:'POST',
					params:{  
		            	type:type,
		          		selectedId:value
		        	}, 
					async : false,
					timeout : 5000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							var data=result.data;
							var regionList = data.regionList;
							var cityList = data.cityList;
							var countyList = data.countyList;
							var villageList = data.villageList;
							var communityList = data.communityList;
							var buildingList = data.buildingList;
							if(regionList.length>0){
								$.each(regionList,function(i,n){
									$("<option id='regionOption"+n.id+"' value='" + n.id + "'>" + n.name + "</option>").appendTo("#region");
								});
							}
							if(cityList.length>0){
								$.each(cityList,function(i,n){
									$("<option id='cityOption"+n.id+"' value='" + n.id + "'>" + n.name + "</option>").appendTo("#city");
								});
							}
							if(countyList.length>0){
								$.each(countyList,function(i,n){
									$("<option id='countyOption"+n.id+"' value='" + n.id + "'>" + n.name + "</option>").appendTo("#county");
								});
							}
							if(villageList.length>0){
								$.each(villageList,function(i,n){
									$("<option id='villageOption"+n.id+"' value='" + n.id + "'>" + n.name + "</option>").appendTo("#village");
								});
							}
							if(communityList.length>0){
								$.each(communityList,function(i,n){
									$("<option id='communityOption"+n.id+"' value='" + n.id + "'>" + n.name + "</option>").appendTo("#community");
								});
							}
							if(buildingList.length>0){
								$.each(buildingList,function(i,n){
									$("<option id='buildingOption"+n.id+"' value='" + n.id + "'>" + n.name + "</option>").appendTo("#building");
								});
							}
							isLoadOver = true;
						}
						else
						{
							 Ext.MessageBox.alert('提示','请求失败');
						}
					},
			failure : function(res) {
				 Ext.MessageBox.alert('提示','请求失败');
				isLoadOver = true;
			}
	});
 }
 
 
var regionMethod;
var cityMethod;
var countyMethod;
var villageMethod;
var communityMethod;
var regionIsOver = true;
var cityIsOver = true;
var countyIsOver = true;
var villageIsOver = true;
var communityIsOver = true;
function regionHistory(value){
 	regionMethod = setInterval(function(){
 		regionIsOver = false;
 		if(isLoadOver){
 			$("#region option[id=regionOption"+value+"]").attr('selected', true);
 			loadSelect(2,"region",value);
 			regionIsOver = true;
 			clearInterval(regionMethod);
 		}
 	},500);
}

function cityHistory(value){
 	cityMethod = setInterval(function(){
 		cityIsOver = false;
 		if(isLoadOver&&regionIsOver){
 			$("#city option[id=cityOption"+value+"]").attr('selected', true);
 			loadSelect(3,"city",value);
 			cityIsOver = true;
 			clearInterval(cityMethod);
 		}
 	},500);
}

function countyHistory(value){
 	countyMethod = setInterval(function(){
 		countyIsOver = false;
 		if(isLoadOver&&cityIsOver){
 			$("#county option[id=countyOption"+value+"]").attr('selected', true);
 			loadSelect(4,"county",value);
 			countyIsOver = true;
 			clearInterval(countyMethod);
 		}
 	},500);
}

function villageHistory(value){
 	villageMethod = setInterval(function(){
 		villageIsOver = false;
 		if(isLoadOver&&countyIsOver){
 			$("#village option[id=villageOption"+value+"]").attr('selected', true);
 			loadSelect(5,"village",value);
 			villageIsOver = true;
 			clearInterval(villageMethod);
 		}
 	},500);
}

function communityHistory(value){
 	communityMethod = setInterval(function(){
 		if(isLoadOver&&villageIsOver){
 			communityIsOver = false;
 			$("#community option[id=communityOption"+value+"]").attr('selected', true);
 			loadSelect(6,"community",value);
 			communityIsOver = true;
 			clearInterval(communityMethod);
 		}
 	},500);
}