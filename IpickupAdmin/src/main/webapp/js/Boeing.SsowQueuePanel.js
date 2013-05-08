Ext.ns('Boeing');
Boeing.SsowQueuePanel = Ext.extend(Ext.Panel, {
	extend: 'Ext.panel.Panel',
	alias: 'widget.ssowpanel',
	headerCfg:{
    	tag: 'div',
    	cls: 'x-panel-header epic-page-panel-header-text'
	},	
    initComponent:function() {
	    Ext.apply(this, {		
			width: panelWidth,
			height: panelHeight,
			defaults: {
		    	style: {
		        	padding: '0px'
		    	}
			},    		
			items:[{xtype: "navbar"} ,
			  {xtype :'container' , height:'30' , id : 'errorMessageDiv' }
		
			,
			      { xtype : 'ssowQueueList' , id : 'ssowQueueList'} //, 
			   //   {xtype: 'container', height: '30'},
			   //   {xtype : 'loginFormPanel' , id :'loginUserForm'}
	
			],
			listeners:{
				afterrender: function(panel){
					panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				}
			}
	    });
	    Boeing.SsowQueuePanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('ssowQueuePanel', Boeing.SsowQueuePanel);





Boeing.SsowQueueList = Ext.extend(Boeing.EditorGridPanel, {
	listeners:{
				
	},
    initComponent:function() {
    Ext.apply(this, {    	
    		
    		width: 900,  
    		height: 600,
    		collapsible: false,
    		titleCollapse: false,
    		headerAsText: false,
    		clicksToEdit: 1,
    		stripeRows : true,
    		store : ssowQueueStore,
    		tbar: {xtype: 'queueNavbar'   },
    		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
    	
    		listeners: {
				
			    beforerender: function(){	
			   
			    	this.store.load();
						
						}
			},
			
			view: new Ext.grid.GridView({
				//markDirty: false
			}),    		
			columns: [{
				
				dataIndex : 'id',
				align : 'center',
				header : '',
				width : 30,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {

					return rowIndex + 1;
				}
			},
			

		
		
			
			{
			
				dataIndex : 'ssowNumber',
				header : 'SSOW Number',
				sortable : true,
				width : 200,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record, rowIndex, columnIndex, store) {
					var link = "";
					var qtip = "";
					var qtitle = "";
					if (  record.data.fileLocation == '' || record.data.fileLocation == null){
						return data;
					}else {
						var link = "<a hidefocus=\"on\" class=\"epicLink\" href=\"javascript://void()\" ";
							var onClick = "";				
							onClick += "showSsowQueue('"+record.data.id+"');";
							link +=" onClick=\""+onClick+"\">";
							link += data;
							link += "</a>"; 
							this.attr = 'ext:qtitle="View" ext:qtip="'+data+'"';
							return "<span style=\"white-space:nowrap\"\>"+link+"</span>"; 
					}
					
					return data;
		    	}
			
			},{
				dataIndex : 'revNbr',
				header : 'Rev#',
				sortable : true,
				width : 40,
				editable : false,
				hideable : false
				
			},{
				dataIndex : 'description',
				header : 'SSOW Description',
				sortable : true,
				width : 200,
				editable : false,
				hideable : false
				
			},
			{
				dataIndex : 'createdDateString',
				header : 'Created Dt',
				sortable : true,
				width : 80,
				editable : false,
				hideable : false
				
			},{
				dataIndex : 'modifiedDateString',
				header : 'Modified Dt',
				sortable : true,
				width : 80,
				editable : false,
				hideable : false
				
			},
			
			
			
			{
				dataIndex : 'status',
				header : 'Status',
				sortable : true,
				width : 70,
				editable : false,
				hideable : false, 
				renderer : function(data, cell, record, rowIndex, columnIndex, store) {
					var link = "";
					var qtip = "";
					var qtitle = "";
//						var link = "<a hidefocus=\"on\" class=\"epicLink\" href=\"javascript://void()\" ";
//							var onClick = "";
//							
//							onClick += "showBclHistory('"+record.data.bclId+"' ,'"+record.data.id+"' );";
//							
//							
//							link +=" onClick=\""+onClick+"\">";
//						
//						
//						link += data;
//						
//						link += "</a>"; 
//						
//						this.attr = 'ext:qtitle="View" ext:qtip="'+data+'"';
//						return "<span style=\"white-space:nowrap\"\>"+link+"</span>"; 
					return data;
						
					
				}
					
					
			       
			
		    	},{
					dataIndex : 'createdBy',
					header : 'CreatedBy',
					sortable : true,
					width : 100,
					editable : false,
					hideable : false
					
				},
				
		    	{
					dataIndex : 'modifiedBy',
					header : 'ModifiedBy',
					sortable : true,
					width : 100,
					editable : false,
					hideable : false
					
				}
				
			
			
			]    		    			
    	});
    Boeing.SsowQueueList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('ssowQueueList', Boeing.SsowQueueList);


function showSsowQueue( ssowIdNew) {
	
	
///var grid = Ext.getCmp('ssowQueueList');
	
	//var record = grid.getSelectionModel().getSelected();
	///var rowIndex = grid.getStore().indexOf(record);

	//var ssowIdNew = record.get('id');
	var url =  "/TestSpring/view/ssow/showSsow" + "?ssowId=" + ssowIdNew;
	
	window.open( url , "docwindow", "scrollbars=yes,width=100,height=100,left=10,top=10,resizable=yes,location=no");
	
}







var ssowQueueStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/ssowQueue/getQueue",
	
	root : 'data',
	timeout: 240000,
	autoLoad : true,
	baseParams: {
		
		queueType : queueType
	},

	fields : [ {
		name : 'ssowNumber'
		
	}, {
		name : 'status'
		
	}, {
		name : 'description'
		
	}, {
		name : 'revNbr'
		
	},{
		name : 'program'
		
	}, {
		name: 'compliance'} ,
		{
			name: 'createdDateString'} ,
			
			{
				name: 'modifiedDateString'} ,
				{
					name: 'createdBy'} ,
					
					{
						name: 'modifiedBy'} ,
		{
			name: 'supplier'}, 
			 				
					{
						name: 'id'}	,  {name: 'fileLocation'}									
	]	
});


Boeing.QueueNavBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, {
	        height: 25,
			items:[{xtype: 'tbfill'},//{xtype: 'tbseparator'},
			       {xtype: 'tbbutton', text: 'Edit' , listeners:{
   				click:function(item, event){
       
   					editSsow();
       	  		
				}
         	}
			},{xtype: 'tbseparator'},
		       {xtype: 'tbbutton', text: 'Clone' , listeners:{
	   				click:function(item, event){
	       	
	   					cloneSsow();
	       	  		
					}
	         	}
				},{xtype: 'tbseparator' ,  hidden: (role == 'User')},
			       {xtype: 'tbbutton', text: 'Delete' ,
				 hidden: (role == 'User'),
						
						listeners:{
		   				click:function(item, event){
		       	 
		   					deleteSsow();
		       	  		
						}
		         	}
					},{xtype: 'tbseparator'},
				       {xtype: 'tbbutton', text: 'Rev' , listeners:{
			   				click:function(item, event){
			       	  	
			   					revSsow();
			       	  		
							}
			         	}
						}
			]
        });
        Boeing.QueueNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('queueNavbar', Boeing.QueueNavBar);




function editSsow() {
	

	var grid = Ext.getCmp('ssowQueueList');
	
	var record = grid.getSelectionModel().getSelected();

	
	if(record) {
	

			var ssowId = record.get('id');
			
		
			document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=edit';
	}

	
	
}


function cloneSsow() {
	
	
	
var grid = Ext.getCmp('ssowQueueList');
	
	var record = grid.getSelectionModel().getSelected();
	

	
	if(record) {
	
		
		var ssowId = record.get('id');
		
	
		document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=clone';
	}

	
	
}





function deleteSsow() {
	var grid = Ext.getCmp('ssowQueueList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
		Ext.MessageBox.buttonText.yes = "Yes, Delete Ssow";
		
		Ext.Msg.show({
			   	   title:'',
				   minWidth: 400,
				   height: 300,
			       msg: 'Are you sure , you want to delete Ssow?',
				  buttons: {yes:true,  cancel:true},
				  fn: function(e){    						
						if (e == 'yes') {
							
							
							var grid = Ext.getCmp('ssowQueueList');
							
							var record = grid.getSelectionModel().getSelected();
							var rowIndex = grid.getStore().indexOf(record);
						
							var ssowId = record.get('id');
						
							
							Ext.Ajax.request({
								url :   "/TestSpring/view/ssow/deleteSsow",
								method: 'GET',
								timeout: 240000,
								headers: {
								    'Accept': 'application/json'
								},	
								params:{
									ssowId: ssowId
									
								
						
								},		
								success: function ( result, request) {		
									var data = Ext.decode(result.responseText);
							//	alert ( "msg " + data.msg);
									displayErrorMsg(data.msg , 'errorMessageDiv');
									var grid = Ext.getCmp('ssowQueueList');
									grid.store.reload();
								//	var parameterMap = {};
									//parameterMap["isBidTemplateScreen"] = true;
									//new Epic.BuyerCheckListQueueScreen({renderTo: 'mainDiv', id: 'buyerCheckListScreenObj', parameterMap: parameterMap});
											
								},		failure: function ( result, request) {
									//	alert("eception happened");
									var data = Ext.decode(result.responseText);
									displayErrorMsgEpicService(data, data.data);			
								}
							}); 	 
						}    						
						   						
				  },
				  animEl: 'elId',			  
				  icon: Ext.MessageBox.QUESTION			  
		});
	
	}
	

}



function revSsow() {
	
	
	//alert ('rev ssow');
var grid = Ext.getCmp('ssowQueueList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
	
			//var rowIndex = grid.getStore().indexOf(record);
		//	alert ( rowIndex );
			//alert ("ecd" + record.get('ecd'));
			//alert ("ecd" + record.get('id'));
			var ssowId = record.get('id');
			//var ecd = record.get('ecd');
		//alert ( "ssow id " + ssowId);
		
		document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=rev';
	}	

	
	
}



function unCompleteSsow() {
	
	
	//alert ('uncomplete ssow');
	var grid = Ext.getCmp('ssowQueueList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
			//var rowIndex = grid.getStore().indexOf(record);
			//alert ( rowIndex );
			//alert ("ecd" + record.get('ecd'));
			//alert ("ecd" + record.get('id'));
			var ssowId = record.get('id');
			//var ecd = record.get('ecd');
		//alert ( "ssow id " + ssowId);
		
		document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=delete';
	
	}
	
	
}
