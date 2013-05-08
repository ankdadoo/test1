// *********************************************************************************
// NOTE: Main panel for the manage templates which the other items/elements are added to
//*********************************************************************************
Ext.ns('Boeing');
Boeing.ManageCheckListPanel = Ext.extend(Ext.Panel, {
	extend : 'Ext.panel.Panel',
	alias : 'widget.sectionspanel',
	headerCfg : {
		tag : 'div',
		cls : 'x-panel-header epic-page-panel-header-text'
	},
	initComponent : function() {
		Ext.apply(this, {
			width : panelWidth,
			height : panelHeight,
			defaults : {
				style : {
					padding : '0px'
				} 
			},  // managesectionsTreePanel
			items : [ { xtype : "navbar" },
			          { xtype :'container' , height:'30' , id : 'errorMessageDiv' },
			          { xtype: 'manageCheckList', id: 'manageCheckList'}
			],
			listeners : {
				afterrender : function(panel) {
//					panel.setTitle('<div style="font-size:medium;">'+ panel.title + '</div>');
				}
			}
		});
		Boeing.ManageCheckListPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('manageCheckListPanel', Boeing.ManageCheckListPanel);


Boeing.ManageCheckListNavBar = Ext.extend(Ext.Toolbar, { 
    initComponent:function() {
        Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'}
					,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Edit' , listeners:{
			    		click:function(item, event){
//			    			alert("Editing");
			    			editCheckList();
			    			} 
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Add' , listeners:{ 
			    		click:function(item, event){
//			    			alert("Adding");
			    			addCheckList();
			    			} 
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Delete' , listeners:{
			    		click:function(item, event){
//			    			alert("Deleting");
			    			deleteCheckList();
			    			}
			    		}
			    	}
			]
        });
        Boeing.ManageCheckListNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('manageCheckListNavbar', Boeing.ManageCheckListNavBar);


Boeing.ManageCheckList = Ext.extend(Boeing.EditorGridPanel, {
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
    		store : manageCheckListStore,
    		tbar: {xtype: 'manageCheckListNavbar'   },
    		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
    		  bbar: new Ext.PagingToolbar({
    	            pageSize: 50,
    	            store: manageCheckListStore,
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
				header : '#',
				width : 60,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {

					return rowIndex + 1;
				}
			},
			

		
		
			
			{
			
				dataIndex : 'itemNumber',
				header : 'Item #',
				sortable : true,
				width : 75,
				editable : false,
				hideable : false
				
			
			},{
				dataIndex : 'question',
				header : 'Question',
				sortable : true,
				width : 700,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			}, 
			{
				dataIndex : 'questionType',
				header : 'Qtype',
				sortable : true,
				width : 50,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			}
			
			
			
			
				
		    	
			
			
			]    		    			
    	});
    Boeing.ManageCheckList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('manageCheckList', Boeing.ManageCheckList);



function columnWrap(val) {
	return '<div style="white-space:normal !important;">' + val + '</div>';
}


var manageCheckListStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/ssow/getCheckList",
	

	root : 'data',
	timeout: 240000,
	totalProperty : 'results',
	autoLoad : false, // false, // true,
	baseParams: {  },

	fields : [ {
		name : 'itemNumber'
		
	}, {
		name : 'question' 
		
	}, {
		name : 'active' 
		
	}, {
		name : 'odrNbr' 
		
	}, {
		name : 'id'
		
	}, {
		name : 'basicQ'
		
	}, {
		name : 'questionType'
		
	} 
	]		
});
//
//
//// RJM - START WINDOW ****************************************************************
////*********************************************************************************
////NOTE: window panel for adding / editing / deleting a Section
////*********************************************************************************
Boeing.manageCheckListWindow = Ext.extend(Ext.Window, {
	      
	      title : '<center>CheckList Management</center>', 
	      titleAlign: "center",
	      id:'manageCheckListWindow',
	      width : 900,
	      height : 500, // 550, // 600, // 450, // 300,
	      initComponent : function() {
	    	  
	    	  this.items = [ {xtype: 'fieldset', vertical: true,
	    		  layout: 'column',
//	    		  bodyStyle: 'padding-right:5px; padding-left:15px;',
	    		  border: false,
	    		  // defaults are applied to all child items unless otherwise specified by child item
	    		  defaults: {
	    			//  columnWidth: 0.5,
	    			  border: false
	    		  }
	    	  ,
	    		  
	    		  items:[{xtype:'editCheckListFormPanel', id: 'editCheckListFormPanel'}]
	    		  }
	            ]            
	            
	            ;

	    	  Boeing.manageCheckListWindow.superclass.initComponent.call(this);
	      }
	});
Ext.reg('manageCheckListWindow', Boeing.manageCheckListWindow);


function editCheckList() {
	
	
	
	
	var grid = Ext.getCmp('manageCheckList');
	
	var record = grid.getSelectionModel().getSelected();
//	var rowIndex = grid.getStore().indexOf(record);

	var checkListId = record.get('id');
	
	

//	alert ("rules id " + rulesId);
	Ext.Ajax.request({
		url :   "/TestSpring/view/ssow/getChk",
		method: 'POST',
		timeout: 240000,
		headers: {
		    'Accept': 'application/json'
		},	
		params:{
			checkListId: checkListId
			
		

		},		
		success: function ( result, request) {		
			var data = Ext.decode(result.responseText);
			checkList = data.data;
		
			
			var win = new Boeing.manageCheckListWindow({});
			win.show();
		
		},		failure: function ( result, request) {
			//	alert("eception happened");
			var data = Ext.decode(result.responseText);
			displayErrorMsgEpicService(data, data.data);			
		}
	}); 	 


}


////*********************************************************************************
////NOTE: edit window form panel for user input of Section details
////*********************************************************************************
Boeing.editCheckListFormPanel = Ext.extend(Ext.form.FormPanel, {
	initComponent : function() {
		Ext.apply(this, {
//			width : 400,
			autoWidth: true,
			autoHeight : true,
			trackResetOnLoad : true,
			method : 'POST',
			id : 'editCheckListForm',
			tbar: {xtype: 'editCheckListNavBar'   },
		    bodyStyle: 'padding-right:5px; padding-left:15px;',

			defaults : {
				// implicitly create Container by specifying xtype
				xtype : 'container',
				autoEl : 'div', // This is the default.
				layout : 'form',
				waitTitle : '',
				standardSubmit : true,
				style : {}
			},
			items : [ {
				xtype : 'container', height : '30' , id : 'errorMessageDivDialog' }
			, {xtype: 'hidden',
			  	id: 'checkListId',
			  	name: 'checkListId',
			  	value: checkList == null ? null:checkList.id
				},{xtype: 'hidden',
				  	id: 'checkListValueString',
				  	name: 'checkListValueString'
				  	//value: checkList == null ? null:checkList.id
					},{xtype: 'hidden',
					  	id: 'checkListProgramString',
					  	name: 'checkListProgramString'
					  	//value: checkList == null ? null:checkList.id
						},{
				xtype : 'displayfield',
//				allowBlank: false,
				fieldLabel : 'CheckList No:',
				  labelStyle: 'width:170px;font-weight: bold;color: #000000;',
				name : 'itemNumber',
				value : checkList == null ? '(To Be Generated)' : checkList.itemNumber, // object 
				id : "itemNumber"
			},  {xtype : 'textarea',						    	
					
				fieldLabel: 'Question Text',
		    	//labelSeparator: '',	
				id: 'questionText',
				name: 'questionText',
				maxLength: 1000,
				width : 600,
				//height: 40,
				blankText:'If P is checked, comment must be input',
				value: checkList == null ? ''  : checkList.question,
				//hidden: (bidScreen != "bidTemplate"),
				height : 40,
				allowBlank : false ,
    		  	labelStyle: 'width:170px;font-weight: bold;color: #000000;',
    	    	   listeners:{
		   				change: function(f, newValue, oldValue){
						//	bclRulesEngineFieldChanged = true;
							
			   			}
	       	   	}
	       	},	{ xtype: 'combo',
			    
			    fieldLabel: 'Question Type:',
			    labelSeparator: '',
			    width: 150,
			    allowBlank: false,
			    loadingText: "Loading...",
			    emptyText: 'Select One',
			    id: 'questionType',
			    name: 'questionType',
//                listWidth : 160,
			    labelStyle: 'width:170px;font-weight: bold;color: #000000;',
			    editable : false,
			    triggerAction : 'all',
			    displayField:'description',
			    valueField: 'questionType',
			    hiddenName : "questionTypeDD", 
			    emptyText : 'Select One',
//                  submitValue : true,
			    mode: 'local',
			    value : '',
			    store: new Ext.data.JsonStore({
			    	url : "/TestSpring/view/ssow/getQuestionTypes",
			    	root: 'data',
			    	timeout: 240000,
			    	autoLoad: true,
			    	baseParams: {  },
			    	forceselection: false,
//			    	fields: ['id', 'description'],
			    	fields: ['questionType', 'description'],
			    	listeners:{		
							load: function(proxy, object, options){	
								Ext.getCmp('questionType').setValue(getQuestionType());
							
							},
							exception: function(proxy, type, action, options, response, arg){
							
							}
						}
			    	
			    }),
			    listeners: {
			    	select: function(combo, record, index) {
			    	//	alert("combo value =" + combo.getValue());
			    		if (combo.getValue() == "DD") {
			    			var comboCity = Ext.getCmp('checkListPV');   
								//comboCity.clearValue();
									comboCity.getStore().setBaseParam("checkListId", checkList.id);
									comboCity.store.reload();
			    		//	Ext.getCmp("checkListPV").getEl().hide();
			    		}
			    		else {
			    			var comboCity = Ext.getCmp('checkListPV');   
							comboCity.clearValue();
			    		//	Ext.getCmp("checkListPV").getEl().show();
			    		}
			    	}
			    }

// RJM - START
			},	{ xtype: 'combo',
			   
			    fieldLabel: 'Possible Values:',
			    labelSeparator: '',
			    width: 250,
			    listWidth : 160,
			    allowBlank: true,
			    loadingText: "Loading...",
			    emptyText: 'Select One',
			    labelStyle: 'width:170px;font-weight: bold;color: #000000;',
			    id: 'checkListPV',
			    name: 'checkListPV',
//                  listWidth : 295,
			  //  editable : true,
			    triggerAction : 'all',
			    displayField:'value',
			    valueField: 'value',
			    hiddenName : "checkListPVDD", 
			    emptyText : 'Select One',
//                  submitValue : true,
			    mode: 'local',
			  //  value : '',
			    store: new Ext.data.JsonStore({
			    	url : "/TestSpring/view/ssow/getCheckListPV",
			    	root: 'data',
			    	timeout: 240000,
			    	autoLoad: true,
			    	baseParams: {
							checkListId : checkList == null ? null : checkList.id
						},
			    	forceselection: false,
//			    	fields: ['id', 'description'],
			    	fields: ['id', 'value'],
			    	
			    })
// RJM - FINISH			    
			},   {xtype: 'fieldset', vertical: true,
                layout: 'column',
                bodyStyle: 'padding-left:15px;',
                border: false,
                // defaults are applied to all child items unless otherwise specified by child item
                defaults: {
                    columnWidth: 0.33,
                    border: false
                },
		       	items:[{xtype: 'fieldset', vertical: true,
		       		items:[ {
						xtype : 'textfield',
//						allowBlank: false,
						fieldLabel : 'CheckList Value:',
						  labelStyle: 'width:130px;font-weight: bold;color: #000000;',
						name : 'checkListNV',
						//value : checkList == null ? '(To Be Generated)' : checkList.itemNumber, // object 
						id : "checkListNV"
					}   ]} , {xtype: 'fieldset', vertical: true,
		       		items:[ {xtype: 'tbbutton', text: '<= Add Value', 
	           				listeners:{click:function(item, event){addCheckListEntry();
							}}}   ]}, 
								{xtype: 'fieldset', vertical: true,
			   items:[{xtype: 'tbbutton', text: 'Remove Value =>', 
	        				listeners:{click:function(item, event){deleteCheckListEntry();
							}}} ]}]}, 
							
							 {xtype: 'fieldset', vertical: true,
					                layout: 'column',
					                bodyStyle: 'padding-right:5px;',
					                border: false,
					                // defaults are applied to all child items unless otherwise specified by child item
					                defaults: {
					                    columnWidth: 0.25,
					                    border: false
					                },
				    		       	items:[{xtype: 'fieldset', vertical: true ,
				    		       		items:[ {xtype: 'combo',
							 		    	   fieldLabel: 'Program Applicability:',
									    	   labelSeparator: '',
									    	   width: 100,
									    	   allowBlank: true,
									    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
									    	   height: 30,    		    	   		 		    		  	
									    	   emptyText: 'Select One',
												   id: 'chkProgram',
												   name: 'chkProgram',		
												listWidth : 140,
												editable : false,
												triggerAction : 'all',
												valueField: 'id',
							 					displayField:'value',
							 					hiddenName : 'chkProgramDD',
												submitValue : true,
												mode: 'local',   
												   store: new Ext.data.JsonStore({     
													    autoLoad: true,
													method: 'GET',
													url: "/TestSpring/view/ssow/getCheckListProgramList",
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
													fields: ['id', 'value'],
													
											})   }   ]}, 
							                    
						    	       {xtype: 'fieldset', vertical: true,
				    		       		items:[{xtype: 'combo',	      	    	   
							 		    	   fieldLabel: 'Possible Values:',
									    	   labelSeparator: '',
									    	   width: 100,
									    	   allowBlank: true,											    	
									    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
									    	   height: 30,											    					    		    	   		 		    		  	
									    	   emptyText: 'Select One',
												   id: 'checkListProgramPV',
												   name: 'checkListProgramPV',		
												listWidth : 140,
												editable : false,
												triggerAction : 'all',
												valueField: 'value',
							 					displayField:'value',
							 					hiddenName : 'checkListProgramPVDD',												
												submitValue : true,
												mode: 'local',   
												
												   store: new Ext.data.JsonStore({     
													    autoLoad: true,
													method: 'GET',
													url: "/TestSpring/view/ssow/getCheckListProgramPV" ,
													prettyUrls: false,
													timeout: 240000, 
													root: 'data',        
											headers: {
											    'Accept': 'application/json'
											},
											baseParams: {
												//checkListId : criteriaTrueCheckListId
											},
											fields: ['id', 'value']
											})  }]
								    	     }, 
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
	  		  					           				listeners:{click:function(item, event){addEntry('chkProgram' , 'checkListProgramPV' , 'checkListProgramSV');
	  		  											}}}   ]}, 
  	  		  											{xtype: 'fieldset', vertical: true,
											   items:[{xtype: 'tbbutton', text: 'Remove =>', 
  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'checkListProgramSV');
	  		  											}}} ]}]}
								    	  ,
								    	{xtype: 'fieldset', vertical: true,
	  					    		       		items:[ {xtype: 'combo', 		  							      	    	   
	  							 		    	   fieldLabel: 'Selected Values:',
	  									    	   labelSeparator: '',
	  									    	   width: 100,
	  									    	   allowBlank: true,
	  									    	 //  disabled:!bcl.editFlag,
	  									    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
	  									    	   height: 30, 		  									    					    		    	   		 		    		  	
	  									    	   emptyText: 'Select One',
	  												   id: 'checkListProgramSV',
	  												   name: 'checkListProgramSV',		
	  												listWidth : 140,
	  												editable : false,
	  												triggerAction : 'all',
	  												valueField: 'value',
	  							 					displayField:'value',
	  							 					hiddenName : 'checkListProgramSVDD', 		  												
	  												submitValue : true,
	  												mode: 'local',    		  											
	  												   store: new Ext.data.JsonStore({     
	  													    autoLoad: true,
	  													method: 'GET',
	  													url: "/TestSpring/view/ssow/getCheckListProgramSV" ,
	  													prettyUrls: false,
	  													timeout: 240000, 
	  													root: 'data',        
	  													headers: {
	  													    'Accept': 'application/json'
	  													},
	  												
	  													baseParams: {
	  														checkListId : checkList == null ? null : checkList.id
	  													},
	  													fields: ['id', 'value'],
	  												   })  } ]} 	       									    	       		  									    	       									    	       
							    	       ]},
							
							
							{xtype : 'textarea',						    	
					
				fieldLabel: 'Traceability Of Changes',
		    	//labelSeparator: '',	
				id: 'auditReason',
				name: 'auditReason',
				//onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
			//	onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);}, 
				width : 600,
				maxLength: 2000,
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
	       	},{
				xtype : 'container',
				height : '30'
			}

			],
			
		});
		Boeing.editCheckListFormPanel.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
}); // eo Epic.buyerCheckListFormPanel
Ext.reg('editCheckListFormPanel', Boeing.editCheckListFormPanel);

////*********************************************************************************
////NOTE: Toolbar for subpanel: Save, Cancel
////*********************************************************************************
Ext.ns('Boeing');
Boeing.EditCheckListNavBar = Ext.extend(Ext.Toolbar, { 
initComponent:function() {
   Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'}
					,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Submit' , listeners:{
			    		click:function(item, event){
			    			submitCheckList();
			    			}
			    		}
			    	}
			]
   });
   Boeing.EditCheckListNavBar.superclass.initComponent.apply(this, arguments);
}
});
Ext.reg('editCheckListNavBar', Boeing.EditCheckListNavBar);

function getCheckListValueString( ) {
	
	var criteriaTrueSVValue = "";
	var criteriaTrueSV = Ext.getCmp('checkListPV');   
	
	if ( criteriaTrueSV != null ) {
		//alert ('total count ' + criteriaTrueSV.store.getCount());
		for (var i=0; i<criteriaTrueSV.store.getCount() ; i++) {
	   //    alert("store value " +  criteriaTrueSV.store.getAt(i).get('value'));
	       criteriaTrueSVValue += criteriaTrueSV.store.getAt(i).get('value') + ";" ;
	    }
	}
	
	return criteriaTrueSVValue;
	
}


function getCheckListProgramString( ) {
	
	var criteriaTrueSVValue = "";
	var criteriaTrueSV = Ext.getCmp('checkListProgramSV');   
	
	if ( criteriaTrueSV != null ) {
		//alert ('total count ' + criteriaTrueSV.store.getCount());
		for (var i=0; i<criteriaTrueSV.store.getCount() ; i++) {
	   //    alert("store value " +  criteriaTrueSV.store.getAt(i).get('value'));
	       criteriaTrueSVValue += criteriaTrueSV.store.getAt(i).get('value') + ";" ;
	    }
	}
	
	return criteriaTrueSVValue;
	
}
function getQuestionType () {
	
	if ( checkList != null ) {
		
		return checkList.questionType;
	}
	
	return '';
}


function submitCheckList() {
	

	
	
	
	
	var form = Ext.getCmp('editCheckListFormPanel').getForm();	
	
	Ext.getCmp('checkListValueString').setValue(getCheckListValueString());
	Ext.getCmp('checkListProgramString').setValue(getCheckListProgramString());
	if(form.isValid()){
		
		form.submit({
							url : "/TestSpring/view/ssow/saveCheckList",
							method: 'POST',
							timeout: 240000,
							
							success: function(form, action){
						
							
								var data23 = action.result;
								
						
								
									var msg = data23.msg ;
									
									displayErrorMsg(msg , 'errorMessageDiv');
									Ext.getCmp('manageCheckListWindow').close();
								
									var grid = Ext.getCmp('manageCheckList');
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



function addCheckListEntry( ) {
	
	var criteriaTrue = Ext.getCmp('checkListNV');   
		
	//var criteriaTruePV = Ext.getCmp(criteriaPV);   
		
	var finalValue =  criteriaTrue.getValue() ;
	if ( finalValue == '') {
		displayErrorMsg('Please enter a value to add.' , 'errorMessageDivDialog');
	}else {
	var criteriaTrueSV = Ext.getCmp('checkListPV');   
	
	
	criteriaTrueSV.store.add(new Ext.data.Record({id: '', value: finalValue}));
	criteriaTrue.setValue('');
	displayErrorMsg('Value has been successfully added' , 'errorMessageDivDialog');
	}

}


function deleteCheckListEntry () {
	
	var criteriaTrueSV = Ext.getCmp('checkListPV'); 
	
	criteriaTrueSV.store.remove(criteriaTrueSV.store.getAt(criteriaTrueSV.selectedIndex)); 
	
	criteriaTrueSV.setRawValue("");
	
	displayErrorMsg('Value has been successfully deleted' , 'errorMessageDivDialog');
}


function addCheckList () {
	checkList = null ;
	var win = new Boeing.manageCheckListWindow({});
	win.show();
	
}






// NOTE: switched to this simplified version of confirming a Delete with user 
function deleteCheckList() {	

//	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv');
	var grid = Ext.getCmp('manageCheckList');
	var record = grid.getSelectionModel().getSelected();

	
	
	var basicQ = record.get('basicQ');
	
	//alert (' basicQ ' + basicQ);
	
	if ( basicQ == true) {
		
		
		// cant delete the basic Q
		displayErrorMsg('CheckList BasicQ cannot be Added or Deleted, only Edited' , 'errorMessageDiv');
		
	}else {
		// check if user was selected before launching editor window
			if(record) {
		Ext.MessageBox.buttonText.yes = "Yes, Delete CheckList";
	
		Ext.Msg.show({
			   	   title:'',
				   minWidth: 400,
				   height: 300,
			       msg: 'Are you sure, you want to delete CheckList?',
				  buttons: {yes:true,  cancel:true},
				  fn: function(e){    						
						if (e == 'yes') {
							
							//var grid = Ext.getCmp('manageCheckList');
							//var record = grid.getSelectionModel().getSelected();
							//var rowIndex = grid.getStore().indexOf(record);
							var checkListId = record.get('id');
						
							Ext.Ajax.request({
								url :   "/TestSpring/view/ssow/deleteCheckList",
								method: 'POST',
								timeout: 240000,
								headers: {
								    'Accept': 'application/json'
								},	
								params:{
									checkListId: checkListId
									
								},		
								success: function ( result, request) {		
									var data = Ext.decode(result.responseText);
							        //	alert ( "msg " + data.msg);
									displayErrorMsg(data.msg , 'errorMessageDiv');
									var grid = Ext.getCmp('manageCheckList');
									grid.store.reload();
								},		
								failure: function ( result, request) {
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
else {
	var msg = "Please select a CheckList item before choosing Delete";
	displayErrorMsg(msg , 'errorMessageDiv');
}
	}

	

}


// end-of-Boeing.MangeCheckListPanel

