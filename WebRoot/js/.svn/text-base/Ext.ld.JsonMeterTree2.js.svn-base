Ext.namespace("Ext.ld");

Ext.ld.JsonMeterTree2 = Ext.extend(Ext.tree.TreePanel, {
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
        	nodeAddr: "-1"
		});
		Ext.Msg.minWidth=300;
		Ext.ld.JsonMeterTree2.superclass.initComponent.call(this);
		//this.on("beforeload", this.nodeBeforeLoad, this);
		this.loader.on('load', this.onToploaderLoad, this.loader);
		this.on("click", this.nodeClick, this);
		//this.on("findnode", this.findnode, this);
	},
	 nodeClick:  function(node, event){
			var meter_position=node.attributes.meter_position;
			searchList(1,meter_position);	
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
		 sets(node);
	}
});
	function sets(node)
	{
		var childNodes = node.childNodes;
		for(var i = 0 ; i < childNodes.length; i++)
		{
			var child = childNodes[i];
		  	if(0==child.id)
		  	{
		  		child.getUI().getIconEl().src = 'images/area/logo.gif'; 
		  	}
		  	else
		  	{
			  	if('0'==child.attributes.meter_parent)
			  	{
			  		child.getUI().getIconEl().src = 'images/area/company.gif'; 
			  	}
			  	else
			  	{
			  		if(1==child.attributes.meter_style)
		  				child.getUI().getIconEl().src = 'images/meter.gif'; 
		  			if(2==child.attributes.meter_style)
		  				child.getUI().getIconEl().src = 'images/building.gif';
		  			if(3==child.attributes.meter_style)
		  				child.getUI().getIconEl().src = 'images/change.gif';
		  		}
		  	}
		  	this.sets(child);//递归
		}
	}