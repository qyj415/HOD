Ext.namespace("Ext.ld");

Ext.ld.JsonTree = Ext.extend(Ext.tree.TreePanel, {
	initComponent: function()
	{
		Ext.apply(this, {
       		minSize: 250,
       		border: false,
        	autoScroll: true,
        	rootVisible:false,
        	lines: true,
        	split: true,
		    containerScroll : true,// 是否支持滚动条
		    animate : true,
        	singleExpand: false,
        	//enableDD:true,
        	nodeAddr: "-1"
		});
		Ext.Msg.minWidth=300;
		Ext.ld.JsonTree.superclass.initComponent.call(this);
		this.on("beforeload", this.nodeBeforeLoad, this);
		this.loader.on('load', this.onToploaderLoad, this.loader);
		this.on("click", this.nodeClick, this);
		this.on("findnode", this.findnode, this);
	},
	 findnode:function(rootnode,attribute,value){
	 	var node= rootnode.findChild(attribute,value);
    	if (node!=null)
    		return node;
    	var cs = rootnode.childNodes; 	
    	for(var i = 0, len = cs.length; i < len; i++)     
        if (cs[i].hasChildNodes()){
         	node = this.findnode(cs[i],attribute,value);	
         	if (node!=null) return node;
        } 	
     	return null;  	
	 }, 
	nodeBeforeLoad: function(node) {
        this.loader.baseParams.id = node.id;
        this.loader.baseParams.name = node.text;
    },
    //加载图标
	onToploaderLoad:function(tree,  node,  response)
	{
		  var childNodes = node.childNodes;
		  for(var i = 0 ; i < childNodes.length; i++)
		  {
			  	var child = childNodes[i];
			  	/*if(0 == node.attributes.type)//省节点
			  	{
				  	node.getUI().getIconEl().src = '../images/area/house.png';  
			  	}*/
			  	if(1 == child.attributes.type)//省节点
			  	{
				  	child.getUI().getIconEl().src = '../images/area/region.jpg';  
			  	}
			  	else if(2 == child.attributes.type)//城市节点
			  	{
				  	child.getUI().getIconEl().src = '../images/area/city.jpg';  
			  	}
			  	else if(3 == child.attributes.type)//区县节点			
			  	{
			  		child.getUI().getIconEl().src = '../images/area/area.jpg'; 
			  	}
			  	else if(4 == child.attributes.type)//街道节点			
			  	{
			  		child.getUI().getIconEl().src = '../images/area/street.jpg'; 
			  	}		  	
			  	else if(5 == child.attributes.type)//小区节点		
			  	{
			  		child.getUI().getIconEl().src = '../images/area/comm.jpg'; 
			  	}
			  	else if(6 == child.attributes.type)//楼栋节点			
			  	{
			  		child.getUI().getIconEl().src = '../images/area/build.jpg'; 
			  	}	
			  	/*else if(7 == child.attributes.type)//房间节点			
			  	{
			  		child.getUI().getIconEl().src = '../images/area/room.jpg'; 
			  	}*/	
			  	else if(10 == child.attributes.type)//单元节点			
			  	{
			  		child.getUI().getIconEl().src = '../images/area/unit.jpg'; 
			  	}				  	
		  }
	},
	//树节点的点击事件
	nodeClick:function(node, event)
	{
		var type=node.attributes.type;
		var id=node.attributes.qtip;
		//除了根目录，都是根据id查询
		if(0==type)
		{
			window.open("../show/regionlist.jsp","mainFrame");
		}
		if(1==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000City!doSelect.do?regionId="+id;
		}
		if(2==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000County!doSelect.do?cityId="+id;
		}
		if(3==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000Village!doSelect.do?countyId="+id;
		}
		if(4==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000Community!doSelect.do?villageId="+id;
		}
		if(5==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000Building!doSelect.do?communityId="+id;
			
		}
		if(6==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000Unit!doSelect.do?buildingId="+id;
		}
		if(10==type)
		{
			var datatable = document.getElementById('datatable');
			datatable.src = "../hod2000Room!doSelect.do?unitId="+id;
		}
		/*if(7==type)
		{
			//window.open("hod2000Meter!doSelect.do?roomId="+id,"mainFrame");
		}*/
 	}
})