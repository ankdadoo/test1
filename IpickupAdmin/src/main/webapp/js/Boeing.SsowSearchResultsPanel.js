Ext.ns('Boeing');
Boeing.SsowSearchResultsPanel = Ext.extend(Ext.Panel, {
	extend: 'Ext.panel.Panel',
	alias: 'widget.ssowSearchResultsPanel',
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
			      { xtype : 'ssowSearchResultsList' , id : 'ssowSearchResultsList'} //, 
			   //   {xtype: 'container', height: '30'},
			   //   {xtype : 'loginFormPanel' , id :'loginUserForm'}
	
			],
			listeners:{
				afterrender: function(panel){
					panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				}
			}
	    });
	    Boeing.SsowSearchResultsPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('ssowSearchResultsPanel', Boeing.SsowSearchResultsPanel);





Boeing.SsowSearchResultsList = Ext.extend(Boeing.EditorGridPanel, {
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
    		store : ssowSearchResultsStore,
    		tbar: {xtype: 'searchResultsNavBar'   },
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
				width : 160,
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
							onClick += "showSsow('"+record.data.id+"');";
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
				width : 50,
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
				width : 80,
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
    Boeing.SsowSearchResultsList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('ssowSearchResultsList', Boeing.SsowSearchResultsList);


function showSsow( ssowIdNew) {

//var grid = Ext.getCmp('ssowSearchResultsList');

//var record = grid.getSelectionModel().getSelected();
//var rowIndex = grid.getStore().indexOf(record);

//var ssowIdNew = record.get('id');
var url =  "/TestSpring/view/ssow/showSsow" + "?ssowId=" + ssowIdNew;

window.open( url , "docwindow", "scrollbars=yes,width=100,height=100,left=10,top=10,resizable=yes,location=no");

}




var ssowSearchResultsStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/search/getSearchResults",
	
	root : 'data',
	timeout: 240000,
	autoLoad : true,
	baseParams: {  },

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
						name: 'id'}		 ,  {name: 'fileLocation'}								
	]		
});


Boeing.SearchResultsNavBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, {
	        height: 25,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'},
			       {xtype: 'tbbutton', text: 'Edit' , listeners:{
   				click:function(item, event){
       	  		//	alert ('search');
       	  		//document.location.href = '/TestSpring/view/search.jsp';
   					editSearchSsow();
       	  		
				}
         	}
			},{xtype: 'tbseparator'},
		       {xtype: 'tbbutton', text: 'Clone' , listeners:{
	   				click:function(item, event){
	       	  		//	alert ('search');
	       	  	//	document.location.href = '/TestSpring/view/search.jsp';
	   					cloneSearchSsow();
	       	  		
					}
	         	}
				},{xtype: 'tbseparator' ,  hidden: (role == 'User')},
			       {xtype: 'tbbutton', text: 'Delete' , 
					 hidden: (role == 'User'),
					 listeners:{
		   				click:function(item, event){
		       	  		//	alert ('search');
		       	  		//document.location.href = '/TestSpring/view/search.jsp';
		   					deletSearchSsow();
		       	  		
						}
		         	}
					}, 
					{xtype: 'tbseparator'},
				       {xtype: 'tbbutton', text: 'Rev' , listeners:{
			   				click:function(item, event){
			       	  		//	alert ('search');
			       	  		//document.location.href = '/TestSpring/view/search.jsp';
			   					revSearchSsow();
			       	  		
							}
			         	}
						},{xtype: 'tbseparator' , hidden: (role == 'User' || role == 'SuperUser')},
				       {xtype: 'tbbutton', text: 'Un-Complete' , 
							hidden: (role == 'User' || role == 'SuperUser'),
							listeners:{
			   				click:function(item, event){
			       	  		//	alert ('search');
			       	  		//document.location.href = '/TestSpring/view/search.jsp';
			       	  		unCompleteSearchSsow();
							}
			         	}
						}
			]
        });
        Boeing.SearchResultsNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('searchResultsNavBar', Boeing.SearchResultsNavBar);




function editSearchSsow() {
	
	
	//alert ('edit ssow');
var grid = Ext.getCmp('ssowSearchResultsList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
	//	var rowIndex = grid.getStore().indexOf(record);
		//alert ( rowIndex );
		//alert ("ecd" + record.get('ecd'));
		//alert ("ecd" + record.get('id'));
		var ssowId = record.get('id');
		//var ecd = record.get('ecd');
	//alert ( "ssow id " + ssowId);
		var ssowStatus = record.get('status');
		
		if ( ssowStatus !='Complete') {
			document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=edit';
		
		}else {
			displayErrorMsg('Ssow cant be edited , as its in a Complete Status' , 'errorMessageDiv');
		}
	}
	
	
}


function cloneSearchSsow() {
	
	
	//alert ('clone ssow');
var grid = Ext.getCmp('ssowSearchResultsList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
		//	var rowIndex = grid.getStore().indexOf(record);
			//alert ( rowIndex );
			//alert ("ecd" + record.get('ecd'));
			//alert ("ecd" + record.get('id'));
			var ssowId = record.get('id');
			//var ecd = record.get('ecd');
		//alert ( "ssow id " + ssowId);
		
		document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=clone';
	}

	
	
}

function revSearchSsow() {
	
	
	//alert ('rev ssow');
var grid = Ext.getCmp('ssowSearchResultsList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
	//	var rowIndex = grid.getStore().indexOf(record);
		//alert ( rowIndex );
		//alert ("ecd" + record.get('ecd'));
		//alert ("ecd" + record.get('id'));
		var ssowId = record.get('id');
		//var ecd = record.get('ecd');
	//alert ( "ssow id " + ssowId);
	
	document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=rev';
	
	}
	
	
}


function deletSearchSsow() {
	
	
	var grid = Ext.getCmp('ssowSearchResultsList');
	
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
	// check for status here if the status is complete then it cant be deleted 
		Ext.MessageBox.buttonText.yes = "Yes, Delete Ssow";
		
		Ext.Msg.show({
			   	   title:'',
				   minWidth: 400,
				   height: 300,
			       msg: 'Are you sure , you want to delete Ssow?',
				  buttons: {yes:true,  cancel:true},
				  fn: function(e){    						
						if (e == 'yes') {
							
							
							var grid = Ext.getCmp('ssowSearchResultsList');
							
							var record = grid.getSelectionModel().getSelected();
							var rowIndex = grid.getStore().indexOf(record);
						//	alert ( rowIndex );
							//alert ("ecd" + record.get('ecd'));
							//alert ("ecd" + record.get('id'));
							var ssowId = record.get('id');
							var ssowStatus = record.get('status');
							
							if ( ssowStatus !='Complete') {
							//var ecd = record.get('ecd');
						//alert ( "ssow id " + ssowId);
							
							Ext.Ajax.request({
								url :   "/TestSpring/view/ssow/deleteSsow",
								method: 'GET',
								timeout: 240000,
								headers: {
								    'Accept': 'application/json'
								},	
								params:{
									ssowId: ssowId
									
									//,
									//newOwnerId:newOwner
									//ecd:ecd
						
								},		
								success: function ( result, request) {		
									var data = Ext.decode(result.responseText);
								
									//Ext.getCmp('bclQueueList').store.load();
									displayErrorMsg('SSOW Deleted Successfully' , 'errorMessageDiv');
									var grid = Ext.getCmp('ssowSearchResultsList');
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
							
							}else {
								displayErrorMsg('Ssow cant be Deleted , as its in a Complete Status' , 'errorMessageDiv');
							}
						}    						
						else if (e == 'no') {
							
							document.location.href = '/bcl/buyerCheckListQueue';
				   				
						}    						
				  },
				  animEl: 'elId',			  
				  icon: Ext.MessageBox.QUESTION			  
		});
	}

}












function unCompleteSearchSsow() {
	
	
	//alert ('uncomplete ssow');
var grid = Ext.getCmp('ssowSearchResultsList');
	
	var record = grid.getSelectionModel().getSelected();
	var rowIndex = grid.getStore().indexOf(record);
	//alert ( rowIndex );
	//alert ("ecd" + record.get('ecd'));
	//alert ("ecd" + record.get('id'));
	var ssowId = record.get('id');
	var ssowStatus = record.get('status');
	
	if ( ssowStatus =='Complete') {
	//var ecd = record.get('ecd');
//alert ( "ssow id " + ssowId);
	Ext.Ajax.request({
		url :   "/TestSpring/view/ssow/unCompleteSsow",
		method: 'GET',
		timeout: 240000,
		headers: {
		    'Accept': 'application/json'
		},	
		params:{
			ssowId: ssowId
			
			//,
			//newOwnerId:newOwner
			//ecd:ecd

		},		
		success: function ( result, request) {		
			var data = Ext.decode(result.responseText);
			//accessRules = data.data;
			//alert("delete successfful  done ");
			//document.location.href = '/TestSpring/view/ssowQueue.jsp';
			//Ext.getCmp('bclQueueList').store.load();
			displayErrorMsg('SSOW Un-Completed Successfully' , 'errorMessageDiv');
			var grid = Ext.getCmp('ssowSearchResultsList');
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
//document.location.href = '/TestSpring/view/ssow.jsp' + '?ssowId=' + ssowId + '&action=delete';
	
}else {
	displayErrorMsg('Ssow cant be Un-completed , as its not in a Complete Status' , 'errorMessageDiv');
}
	
	
}
