Ext.ns('Boeing');
Boeing.ManageAcronymsPanel = Ext.extend(Ext.Panel, {
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
			      { xtype : 'manageAcronymsList' , id : 'manageAcronymsList'} //, 
			   //   {xtype: 'container', height: '30'},
			   //   {xtype : 'loginFormPanel' , id :'loginUserForm'}
	
			],
			listeners:{
				afterrender: function(panel){
					panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				}
			}
	    });
	    Boeing.ManageAcronymsPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('manageAcronymsPanel', Boeing.ManageAcronymsPanel);





Boeing.ManageAcronymsList = Ext.extend(Boeing.EditorGridPanel, {
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
    		store : manageAcronymsStore,
    		tbar: {xtype: 'manageAcronymsNavBar'   },
    		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
    		  bbar: new Ext.PagingToolbar({
    	            pageSize: 50,
    	            store: manageAcronymsStore,
    	            displayInfo: true,
    	            displayMsg: 'Displaying Records {0} - {1} of {2}',
    	            emptyMsg: "No Records to display"//,
//    	           items:[
//    	                '-', {
//    	                pressed: true,
//    	                enableToggle:true,
//    	                text: 'Show Record Details',
//    	                cls: 'x-btn-text-icon details',
//    	                toggleHandler: function(btn, pressed){
//    	                    var view = grid.getView();
//    	                    view.showPreview = pressed;
//    	                    view.refresh();
//    	                }
//    	            }]
    	        }),
    		listeners: {
				
			    beforerender: function(){	
			   
			    	//this.store.load();
			    	this.store.load({params:{start:0, limit:50}});
						
						}
			},
			
			view: new Ext.grid.GridView({
				//markDirty: false
			}),    		
			columns: [{
				
				dataIndex : 'id',
				align : 'center',
				header : '',
				width : 60,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {

					return rowIndex + 1;
				}
			},
			

		
		
			
			{
			
				dataIndex : 'name',
				header : 'Acronyms Name',
				sortable : true,
				width : 150,
				editable : false,
				hideable : false
				
			
			},{
				dataIndex : 'description',
				header : 'Description',
				sortable : true,
				width : 550,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			}, 
			{
				dataIndex : 'rulesString',
				header : 'Rules Applicable',
				sortable : true,
				width : 200,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			}
			
			
			
			
				
		    	
			
			
			]    		    			
    	});
    Boeing.ManageAcronymsList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('manageAcronymsList', Boeing.ManageAcronymsList);



function columnWrap(val) {
	return '<div style="white-space:normal !important;">' + val + '</div>';
}

var manageAcronymsStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/ssow/getAcronymsList",
	
	root : 'data',
	totalProperty : 'results',
	timeout: 240000,
	autoLoad : false,
	baseParams: {
		
		
	},

	fields : [ {
		name : 'name'
		
	}, {
		name : 'description'
		
	},
				
			 				
					{
						name: 'id'}	 ,  {
							name : 'rulesString'
						}									
	]	
});



function deleteAcronyms() {
	
	Ext.MessageBox.buttonText.yes = "Yes, Delete Acronyms";
	
	Ext.Msg.show({
		   	   title:'',
			   minWidth: 400,
			   height: 300,
		       msg: 'Are you sure , you want to delete Acronyms?',
			  buttons: {yes:true,  cancel:true},
			  fn: function(e){    						
					if (e == 'yes') {
						
						
						var grid = Ext.getCmp('manageAcronymsList');
						
						var record = grid.getSelectionModel().getSelected();
						var rowIndex = grid.getStore().indexOf(record);
					
						var acronymsId = record.get('id');
					
						
						Ext.Ajax.request({
							url :   "/TestSpring/view/ssow/deleteAcronyms",
							method: 'POST',
							timeout: 240000,
							headers: {
							    'Accept': 'application/json'
							},	
							params:{
								acronymsId: acronymsId
								
							
					
							},		
							success: function ( result, request) {		
								var data = Ext.decode(result.responseText);
						//	alert ( "msg " + data.msg);
								displayErrorMsg(data.msg , 'errorMessageDiv');
								var grid = Ext.getCmp('manageAcronymsList');
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
function editAcronyms() {
	
	
						
						
						var grid = Ext.getCmp('manageAcronymsList');
						
						var record = grid.getSelectionModel().getSelected();
					//	var rowIndex = grid.getStore().indexOf(record);
					
						var acronymsId = record.get('id');
					
					//	alert ("rules id " + rulesId);
						Ext.Ajax.request({
							url :   "/TestSpring/view/ssow/getAcronyms",
							method: 'GET',
							timeout: 240000,
							headers: {
							    'Accept': 'application/json'
							},	
							params:{
								acronymsId: acronymsId
								
							
					
							},		
							success: function ( result, request) {		
								var data = Ext.decode(result.responseText);
								acronyms = data.data;
							//	alert ( "rule name from server " + rule.name);
						//	alert ( "msg " + data.msg);
							//	displayErrorMsg(data.msg , 'errorMessageDiv');
								//var grid = Ext.getCmp('ssowQueueList');
								//grid.store.reload();
								
								var win = new Boeing.manageAcronymsWindow({});
								win.show();
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



function addAcronyms () {
	acronyms = null ;
	var win = new Boeing.manageAcronymsWindow({});
	win.show();
	
}


Boeing.ManageAcronymsNavBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, {
	        height: 25,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'},
			       {xtype: 'tbbutton', text: 'Edit' , listeners:{
   				click:function(item, event){
       	  		//	alert ('search');
       	  		//document.location.href = '/TestSpring/view/search.jsp';
   					editAcronyms();
       	  		
				}
         	}
			},{xtype: 'tbseparator'},
		       {xtype: 'tbbutton', text: 'Add' , listeners:{
	   				click:function(item, event){
	       	  		//	alert ('search');
	       	  	//	document.location.href = '/TestSpring/view/search.jsp';
	   					addAcronyms();
	       	  		
					}
	         	}
				},{xtype: 'tbseparator' ,  hidden: (role == 'User')},
			       {xtype: 'tbbutton', text: 'Delete' ,
				 hidden: (role == 'User'),
						
						listeners:{
		   				click:function(item, event){
		       	  		//	alert ('search');
		       	  		//document.location.href = '/TestSpring/view/search.jsp';
		   					deleteAcronyms();
		       	  		
						}
		         	}
					}
			]
        });
        Boeing.ManageAcronymsNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('manageAcronymsNavBar', Boeing.ManageAcronymsNavBar);



Boeing.EditAcronymsNavBar = Ext.extend(Ext.Toolbar, { 
	initComponent:function() {
	   Ext.apply(this, {
		        height: 30,
				items:[{xtype: 'tbfill'}
						,{xtype: 'tbseparator'}
				    	,{xtype: 'tbbutton', text: 'Submit' , listeners:{
				    		click:function(item, event){
				    			submitAcronyms();
				    			}
				    		}
				    	}
				]
	   });
	   Boeing.EditAcronymsNavBar.superclass.initComponent.apply(this, arguments);
	}
	});
	Ext.reg('editAcronymsNavBar', Boeing.EditAcronymsNavBar);


function submitAcronyms() {
	

	
	
	
	
	var form = Ext.getCmp('editAcronymsFormPanel').getForm();	
	
	Ext.getCmp('rulesString').setValue(getCriteriaTrueSVString('acronymsRuleSV'));
	
	if(form.isValid()){
		
		form.submit({
							url : "/TestSpring/view/ssow/saveAcronyms",
							method: 'POST',
							timeout: 240000,
							
							success: function(form, action){
						
							
								var data23 = action.result;
								
						
								
									var msg = data23.msg ;
									
									displayErrorMsg(msg , 'errorMessageDiv');
									Ext.getCmp('manageAcronymsWindow').close();
								
									var grid = Ext.getCmp('manageAcronymsList');
									grid.store.reload();
							
							
							},
							failure: function ( result, request) {
								//	alert("eception happened");
									var data = Ext.decode(result.responseText);
								//	alert(" could not validate user 123 ");
									//displayErrorMsgEpicService(data, data.data);			
								}
						});
		
		

	}
	else {
		Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed. See red highlighted fields for details.");
	}
	
	
	
	
	
	
}





Boeing.manageAcronymsWindow = Ext.extend(Ext.Window, {
    
    title : '<center>Acronyms Management</center>', 
    titleAlign: "center",
    id:'manageAcronymsWindow',
    width : 925,
    height : 500,
    initComponent : function() {
  	  
  	  this.items = [ {xtype: 'fieldset', vertical: true,
  		  layout: 'column',
//  		  bodyStyle: 'padding-right:5px; padding-left:15px;',
  		  border: false,
  		  // defaults are applied to all child items unless otherwise specified by child item
  		  defaults: {
  			//  columnWidth: 0.5,
  			  border: false
  		  }
  	  ,
  		  
  		  items:[{xtype:'editAcronymsFormPanel', id: 'editAcronymsFormPanel'}]
  		  }
          ]            
          
          ;

  	  Boeing.manageAcronymsWindow.superclass.initComponent.call(this);
    }
});
Ext.reg('manageAcronymsWindow', Boeing.manageAcronymsWindow);



function getCriteriaTrueSVString( criteriaSV) {
	
	var criteriaTrueSVValue = "";
	var criteriaTrueSV = Ext.getCmp(criteriaSV);   
	
	if ( criteriaTrueSV != null ) {
		//alert ('total count ' + criteriaTrueSV.store.getCount());
		for (var i=0; i<criteriaTrueSV.store.getCount() ; i++) {
	   //    alert("store value " +  criteriaTrueSV.store.getAt(i).get('value'));
	       criteriaTrueSVValue += criteriaTrueSV.store.getAt(i).get('name') + ";" ;
	    }
	}
	
	return criteriaTrueSVValue;
	
}

function addAcronymsEntry( criteria , criteriaSV) {
	
	var criteriaTrue = Ext.getCmp(criteria);   
		
	//var criteriaTruePV = Ext.getCmp(criteriaPV);   
		
	var finalValue =  criteriaTrue.getRawValue() ;
	if ( finalValue == '') {
		displayErrorMsg('Please select a value in Rules Drop Down to add.' , 'errorMessageDivDialog');
	}else {
		var criteriaTrueSV = Ext.getCmp(criteriaSV);   
	
	
		criteriaTrueSV.store.add(new Ext.data.Record({id: '', name: finalValue}));


		criteriaTrue.setRawValue("");
		displayErrorMsg('Rule has been successfully added' , 'errorMessageDivDialog');
	}
}


function deleteAcronymsEntry (criteriaSV) {
	
	var criteriaTrueSV = Ext.getCmp(criteriaSV); 
	
	criteriaTrueSV.store.remove(criteriaTrueSV.store.getAt(criteriaTrueSV.selectedIndex)); 
	
criteriaTrueSV.setRawValue("");
	
	//criteriaTrueSV.store.add(new Ext.data.Record({id: '', name: ''}));
	displayErrorMsg('Rule  has been successfully deleted' , 'errorMessageDivDialog');
}



Boeing.editAcronymsFormPanel = Ext.extend(Ext.form.FormPanel, {
    initComponent:function() {
    	  Ext.apply(this, {		
  			width: '900px',
  			autoHeight: true, 
  		//	title:"Search",
  			tbar: {xtype: 'editAcronymsNavBar'   },
  			defaults: {
  		    	style: {
  		        	padding: '10px'
  		    	}
  			},    		
  			items:[ {xtype: 'fieldset', vertical: true,
                  layout: 'column',
                  bodyStyle: 'padding-right:5px;',
                  border: false,
                  // defaults are applied to all child items unless otherwise specified by child item
                  defaults: {
                      columnWidth: 1.0,
                      border: false
                  },    			
  	    		items:[ 
  					  {xtype: 'fieldset', vertical: true,
  			                layout: 'column',
  			                bodyStyle: 'padding-right:5px;',
  			                border: false,
  			                // defaults are applied to all child items unless otherwise specified by child item
  			                defaults: {
  			                    columnWidth: 1.0,
  			                    border: false
  			                },
  		    		       	items:[{xtype: 'fieldset', vertical: true,
  		    		       		items:[{xtype: 'hidden',
								  	id: 'acronymsId',
								  	name: 'acronymsId',
								  	value: acronyms == null ? null:acronyms.id
									},{xtype: 'hidden',
									  	id: 'rulesString',
									  	name: 'rulesString'
									  //	value: getCriteriaTrueSVString()
										},{ xtype :'container' , height:'25' , id : 'errorMessageDivDialog' },
									 {xtype: 'textfield', 
				    		       			
					 		    		  	fieldLabel: 'Name:',
					 		    		  //	disabled: (bidScreen == "documentAssessment"),
					 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
					       					id: 'acronymsName',
					       					name: 'acronymsName',
					       					width: 110,
					       					maxLength: 200,
					 		    		  	value:  acronyms == null ? '' : acronyms.name,
					 		    		  	labelSeparator: '',
					 		    		  	allowBlank: false,
					 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
						    	       },  {xtype : 'textarea',						    	
					     					
											fieldLabel: 'Description',
						    		    	//labelSeparator: '',	
											id: 'acronymsDescription',
											name: 'acronymsDescription',
											//onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
										//	onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);}, 
											width : 600,
											maxLength: 1000,
											//height: 40,
											blankText:'If P is checked, comment must be input',
											value: acronyms == null ? ''  : acronyms.description,
											//hidden: (bidScreen != "bidTemplate"),
											height : 40,
											allowBlank : false ,
							    		  	labelStyle: 'width:170px;font-weight: bold;color: #000000;',
							    	    	   listeners:{
				   				   				change: function(f, newValue, oldValue){
					       						//	bclRulesEngineFieldChanged = true;
					       							
					       			   			}
								       	   	}
					    		       	}, {xtype :'container' , height:'20'  }, 
					    		       	
					    		       	{xtype: 'fieldset', vertical: true,
	  						                layout: 'column',
	  						                bodyStyle: 'padding-right:5px;',
	  						                border: false,
	  						                // defaults are applied to all child items unless otherwise specified by child item
	  						                defaults: {
	  						                    columnWidth: 0.33,
	  						                    border: false
	  						                },
	  					    		       	items:[{xtype: 'fieldset', vertical: true ,
	  					    		       		items:[ {xtype: 'combo',
	  								 		    	   fieldLabel: 'Rules Applicable:',
	  										    	   labelSeparator: '',
	  										    	   width: 110,
	  										    	   allowBlank: true,
	  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
	  										    	   height: 30,    		    	   		 		    		  	
	  										    	   emptyText: 'Select One',
	  													   id: 'rulesApplicable',
	  													   name: 'rulesApplicable',		
	  													listWidth : 160,
	  													editable : false,
	  													triggerAction : 'all',
	  													valueField: 'name',
	  								 					displayField:'name',
	  								 					hiddenName : 'rulesApplicable',
	  													submitValue : true,
	  													mode: 'local',   
	  													   store: new Ext.data.JsonStore({     
	  														    autoLoad: true,
	  														method: 'GET',
	  														url: "/TestSpring/view/ssow/getFullRules",
	  														prettyUrls: false,
	  														timeout: 240000, 
	  														root: 'data',        
	  														headers: {
	  														    'Accept': 'application/json'
	  														},
	  														setBaseParams: function(params){
	  															this.baseParams = params;		
	  														},
	  														baseParams: {},
	  														fields: ['id', 'name'],
	  														
	  												})   }   ]}, 
	  								                    
	  							    	      
	  									    	   {xtype: 'fieldset', vertical: true,
											                layout: 'column',
											                bodyStyle: 'padding-left:15px;',
											                border: false,
											                // defaults are applied to all child items unless otherwise specified by child item
											                defaults: {
											                    columnWidth: 0.5,
											                    border: false
											                },
										    		       	items:[{xtype: 'fieldset', vertical: true,
										    		       		items:[ {xtype: 'tbbutton', text: '<= Add', 
				  		  					           				listeners:{click:function(item, event){addAcronymsEntry('rulesApplicable' ,  'acronymsRuleSV');
				  		  											}}}   ]}, 
			  	  		  											{xtype: 'fieldset', vertical: true,
														   items:[{xtype: 'tbbutton', text: 'Remove =>', 
			  	  		  				        				listeners:{click:function(item, event){deleteAcronymsEntry( 'acronymsRuleSV');
		  	  		  											}}} ]}]}
	  									    	  ,
	   									    	{xtype: 'fieldset', vertical: true,
	 		  					    		       		items:[ {xtype: 'combo', 		  							      	    	   
	 		  							 		    	   fieldLabel: 'Selected Values:',
	 		  									    	   labelSeparator: '',
	 		  									    	   width: 110,
	 		  									    	   allowBlank: true,
	 		  									    	 //  disabled:!bcl.editFlag,
	 		  									    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
	 		  									    	   height: 30, 		  									    					    		    	   		 		    		  	
	 		  									    	   emptyText: 'Select One',
	 		  												   id: 'acronymsRuleSV',
	 		  												   name: 'acronymsRuleSV',		
	 		  												listWidth : 160,
	 		  												editable : false,
	 		  												triggerAction : 'all',
	 		  												valueField: 'name',
	 		  							 					displayField:'name',
	 		  							 					hiddenName : 'acronymsRuleSVDD', 		  												
	 		  												submitValue : true,
	 		  												mode: 'local',    		  											
	 		  												   store: new Ext.data.JsonStore({     
	 		  													    autoLoad: true,
	 		  													method: 'GET',
	 		  													url: "/TestSpring/view/ssow/getRulesListByAcronymsId" ,
	 		  													prettyUrls: false,
	 		  													timeout: 240000, 
	 		  													root: 'data',        
	 		  													headers: {
	 		  													    'Accept': 'application/json'
	 		  													},
	 		  												
	 		  													baseParams: {
	 		  														acronymsId : acronyms == null ? null : acronyms.id
	 		  													},
	 		  													fields: ['id', 'name'],
	 		  												   })  } ]} 	       									    	       		  									    	       									    	       
	  								    	       ]}
	  									,{xtype :'container' , height:'20'  },{xtype : 'textarea',						    	
			     					
									fieldLabel: 'Traceability Of Changes',
				    		    	//labelSeparator: '',	
									id: 'auditReason',
									name: 'auditReason',
									maxLength: 2000,
									//onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
								//	onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);}, 
									width : 600,
									//height: 40,
									blankText:'If P is checked, comment must be input',
									value: getSsowDescription(),
									//hidden: (bidScreen != "bidTemplate"),
									height : 40,
									allowBlank : false ,
					    		  	labelStyle: 'width:170px;font-weight: bold;color: #000000;',
					    	    	   listeners:{
		   				   				change: function(f, newValue, oldValue){
			       						//	bclRulesEngineFieldChanged = true;
			       							
			       			   			}
						       	   	}
			    		       	},{xtype :'container' , height:'20'  }
  									]}]} // end of first fieldset 
  									
  				    		       
  									// end of second field set 
  									 // end of crtiera any true 
  								    	     // end of field set for criteia all  true 
  								    	   // end of criteria not true 
		    		       	]}
		    		       	
		    		       
  			
  			],
  			listeners:{
  				afterrender: function(panel){
  					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
  				}
  			}
  	    });
  	    Boeing.editAcronymsFormPanel.superclass.initComponent.apply(this, arguments);
  	}
  });
  Ext.reg('editAcronymsFormPanel', Boeing.editAcronymsFormPanel);











