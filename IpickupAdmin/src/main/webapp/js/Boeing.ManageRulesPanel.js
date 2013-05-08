Ext.ns('Boeing');
Boeing.ManageRulesPanel = Ext.extend(Ext.Panel, {
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
			      { xtype : 'manageRulesList' , id : 'manageRulesList'} //, 
			   //   {xtype: 'container', height: '30'},
			   //   {xtype : 'loginFormPanel' , id :'loginUserForm'}
	
			],
			listeners:{
				afterrender: function(panel){
					panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				}
			}
	    });
	    Boeing.ManageRulesPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('manageRulesPanel', Boeing.ManageRulesPanel);





Boeing.ManageRulesList = Ext.extend(Boeing.EditorGridPanel, {
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
    		store : manageRulesStore,
    		tbar: {xtype: 'manageRulesNavBar'   },
    		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
    		  bbar: new Ext.PagingToolbar({
    	            pageSize: 50,
    	            store: manageRulesStore,
    	            displayInfo: true,
    	            displayMsg: 'Displaying Records {0} - {1} of {2}',
    	            emptyMsg: "No Records to display"///,
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
				width : 30,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {

					return rowIndex + 1;
				}
			},
			

		
		
			
			{
			
				dataIndex : 'name',
				header : 'Rule Name',
				sortable : true,
				width : 80,
				editable : false,
				hideable : false
				
			
			},{
				dataIndex : 'checklistCriteriaTrueID',
				header : 'True',
				sortable : true,
				width : 120,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			},{
				dataIndex : 'checklistCriteriaAnyTrueID',
				header : 'Any True',
				sortable : true,
				width : 140,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			},
			{
				dataIndex : 'checklistCriteriaAllTrueID',
				header : 'All True',
				sortable : true,
				width : 140,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			},{
				dataIndex : 'checklistCriteriaAnyNotTrueID',
				header : 'Not True',
				sortable : true,
				width : 140,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			},{
				dataIndex : 'rulesCriteriaAnyTrueID',
				header : 'Rules True',
				sortable : true,
				width : 140,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
				
			},
			
			
			
			{
				dataIndex : 'rulesCriteriaAllTrueID',
				header : 'Rules All True',
				sortable : true,
				width : 140,
				editable : false,
				hideable : false,
				renderer : function(data, cell, record,
						rowIndex, columnIndex, store) {
			
				
				
				
							return columnWrap(data);	
								
					
					
				}
					
					
			       
			
		    	},{
					dataIndex : 'rulesCriteriaNotTrueID',
					header : 'Rules Not True',
					sortable : true,
					width : 140,
					editable : false,
					hideable : false,
					renderer : function(data, cell, record,
							rowIndex, columnIndex, store) {
				
					
					
					
								return columnWrap(data);	
									
						
						
					}
					
				}
				
		    	
			
			
			]    		    			
    	});
    Boeing.ManageRulesList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('manageRulesList', Boeing.ManageRulesList);



function columnWrap(val) {
	return '<div style="white-space:normal !important;">' + val + '</div>';
}

var manageRulesStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/ssow/getRules",
	
	root : 'data',
	totalProperty : 'results',
	timeout: 240000,
	autoLoad : false,
	baseParams: {
		
		
	},

	fields : [ {
		name : 'name'
		
	}, {
		name : 'checklistCriteriaTrueID'
		
	}, {
		name : 'checklistCriteriaAnyTrueID'
		
	}, {
		name : 'checklistCriteriaAllTrueID'
		
	},{
		name : 'checklistCriteriaAnyNotTrueID'
		
	}, {
		name: 'rulesCriteriaAnyTrueID'} ,
		{
			name: 'rulesCriteriaAllTrueID'} ,
			
			{
				name: 'rulesCriteriaNotTrueID'} ,
				
			 				
					{
						name: 'id'}										
	]	
});



function deleteRule() {
	
	Ext.MessageBox.buttonText.yes = "Yes, Delete Rule";
	
	Ext.Msg.show({
		   	   title:'',
			   minWidth: 400,
			   height: 300,
		       msg: 'Are you sure , you want to delete Rule?',
			  buttons: {yes:true,  cancel:true},
			  fn: function(e){    						
					if (e == 'yes') {
						
						
						var grid = Ext.getCmp('manageRulesList');
						
						var record = grid.getSelectionModel().getSelected();
						var rowIndex = grid.getStore().indexOf(record);
					
						var ruleId = record.get('id');
					
						
						Ext.Ajax.request({
							url :   "/TestSpring/view/ssow/deleteRule",
							method: 'POST',
							timeout: 240000,
							headers: {
							    'Accept': 'application/json'
							},	
							params:{
								ruleId: ruleId
								
							
					
							},		
							success: function ( result, request) {		
								var data = Ext.decode(result.responseText);
						//	alert ( "msg " + data.msg);
								displayErrorMsg(data.msg , 'errorMessageDiv');
								var grid = Ext.getCmp('manageRulesList');
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
function editRule() {
	
	
						
						
						var grid = Ext.getCmp('manageRulesList');
						
						var record = grid.getSelectionModel().getSelected();
					//	var rowIndex = grid.getStore().indexOf(record);
					
						var rulesId = record.get('id');
					
					//	alert ("rules id " + rulesId);
						Ext.Ajax.request({
							url :   "/TestSpring/view/ssow/getRule",
							method: 'GET',
							timeout: 240000,
							headers: {
							    'Accept': 'application/json'
							},	
							params:{
								ruleId: rulesId
								
							
					
							},		
							success: function ( result, request) {		
								var data = Ext.decode(result.responseText);
								rule = data.data;
							//	alert ( "rule name from server " + rule.name);
						//	alert ( "msg " + data.msg);
							//	displayErrorMsg(data.msg , 'errorMessageDiv');
								//var grid = Ext.getCmp('ssowQueueList');
								//grid.store.reload();
								
								var win = new Boeing.manageRulesWindow({});
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



function addRule () {
	rule = null ;
	var win = new Boeing.manageRulesWindow({});
	win.show();
	
}


Boeing.ManageRulesNavBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, {
	        height: 25,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'},
			       {xtype: 'tbbutton', text: 'Edit' , listeners:{
   				click:function(item, event){
       	  		//	alert ('search');
       	  		//document.location.href = '/TestSpring/view/search.jsp';
   					editRule();
       	  		
				}
         	}
			},{xtype: 'tbseparator'},
		       {xtype: 'tbbutton', text: 'Add' , listeners:{
	   				click:function(item, event){
	       	  		//	alert ('search');
	       	  	//	document.location.href = '/TestSpring/view/search.jsp';
	   					addRule();
	       	  		
					}
	         	}
				},{xtype: 'tbseparator' ,  hidden: (role == 'User')},
			       {xtype: 'tbbutton', text: 'Delete' ,
				 hidden: (role == 'User'),
						
						listeners:{
		   				click:function(item, event){
		       	  		//	alert ('search');
		       	  		//document.location.href = '/TestSpring/view/search.jsp';
		   					deleteRule();
		       	  		
						}
		         	}
					}
			]
        });
        Boeing.ManageRulesNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('manageRulesNavBar', Boeing.ManageRulesNavBar);



Boeing.EditRuleNavBar = Ext.extend(Ext.Toolbar, { 
	initComponent:function() {
	   Ext.apply(this, {
		        height: 30,
				items:[{xtype: 'tbfill'}
						,{xtype: 'tbseparator'}
				    	,{xtype: 'tbbutton', text: 'Submit' , listeners:{
				    		click:function(item, event){
				    			submitRule();
				    			}
				    		}
				    	}
				]
	   });
	   Boeing.EditRuleNavBar.superclass.initComponent.apply(this, arguments);
	}
	});
	Ext.reg('editRuleNavBar', Boeing.EditRuleNavBar);


function submitRule() {
	
	//alert ( " doing save ");
	
	// set the value to the string - 
	
	
	
	//alert ("criteriaTrueSVString ::" + getCriteriaTrueSVString());
	Ext.getCmp('criteriaTrueSVString').setValue(getRulesCriteriaTrueSVString('criteriaTrueSV'));
	Ext.getCmp('criteriaAnyTrueSVString').setValue(getRulesCriteriaTrueSVString('criteriaAnyTrueSV'));
	Ext.getCmp('criteriaAllTrueSVString').setValue(getRulesCriteriaTrueSVString('criteriaAllTrueSV'));
	Ext.getCmp('criteriaNotTrueSVString').setValue(getRulesCriteriaTrueSVString('criteriaNotTrueSV'));
	
	Ext.getCmp('rulesNotTrueSVString').setValue(getRulesCriteriaTrueSVString('rulesNotTrueSV'));
	Ext.getCmp('rulesAllTrueSVString').setValue(getRulesCriteriaTrueSVString('rulesAllTrueSV'));
	Ext.getCmp('rulesAnyTrueSVString').setValue(getRulesCriteriaTrueSVString('rulesAnyTrueSV'));
	var form = Ext.getCmp('editRulesFormPanel').getForm();	
	
	if(form.isValid()){
		
		form.submit({
							url : "/TestSpring/view/ssow/saveRule",
							method: 'POST',
							timeout: 240000,
							
							success: function(form, action){
						
						
								var data23 = action.result;
								
							
								
									var msg = data23.msg ;
								
									displayErrorMsg(msg , 'errorMessageDiv');
									Ext.getCmp('manageRulesWindow').close();
								
									var grid = Ext.getCmp('manageRulesList');
									grid.store.reload();
							
								
							
							},
							failure: function ( result, request) {
								
									var data = Ext.decode(result.responseText);
										
								}
						});
		
		

	}
	else {
		Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed. See red highlighted fields for details.");
	}
	
	
	
	
	
	
}





Boeing.manageRulesWindow = Ext.extend(Ext.Window, {
    
    title : '<center>Rules Management</center>', 
    titleAlign: "center",
    id:'manageRulesWindow',
    width : 925,
    height : 550,
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
  		  
  		  items:[{xtype:'editRulesFormPanel', id: 'editRulesFormPanel'}]
  		  }
          ]            
          
          ;

  	  Boeing.manageRulesWindow.superclass.initComponent.call(this);
    }
});
Ext.reg('manageRulesWindow', Boeing.manageRulesWindow);




function getRulesCriteriaTrueSVString( criteriaSV) {
	
	//alert ("in the method ");
	var criteriaTrueSVValue = "";
	var criteriaTrueSV = Ext.getCmp(criteriaSV);   
	
	if ( criteriaTrueSV != null ) {
		//alert ('total count ' + criteriaTrueSV.store.getCount());
		for (var i=0; i<criteriaTrueSV.store.getCount() ; i++) {
	  //     alert("store value " +  criteriaTrueSV.store.getAt(i).get('value'));
	       criteriaTrueSVValue += criteriaTrueSV.store.getAt(i).get('value') + ";" ;
	    }
	}
	
	return criteriaTrueSVValue;
	
}
function addEntry( criteria , criteriaPV , criteriaSV) {
	
	var criteriaTrue = Ext.getCmp(criteria);   
		
	var criteriaTruePV = Ext.getCmp(criteriaPV);   
	
	
		
	var finalValue =  criteriaTrue.getRawValue() + "=>" + criteriaTruePV.getRawValue();
	if ( criteriaTrue.getRawValue() == '' || criteriaTruePV.getRawValue() =='') {
		displayErrorMsg('Please select a value to add.' , 'errorMessageDivDialog');
	}else {
	var criteriaTrueSV = Ext.getCmp(criteriaSV);   
	
	
	criteriaTrueSV.store.add(new Ext.data.Record({id: '', value: finalValue}));
	
	criteriaTrue.setRawValue("");
	
	criteriaTruePV.setRawValue("");
	displayErrorMsg('Value has been successfully added' , 'errorMessageDivDialog');
	}

}


function addRulesEntry( criteria  , criteriaSV) {
	
	var criteriaTrue = Ext.getCmp(criteria);   
		
	 
		
	var finalValue =  criteriaTrue.getRawValue() ;
	
	if ( finalValue == '' ) {
		displayErrorMsg('Please select a value to add.' , 'errorMessageDivDialog');
	}else {
		var criteriaTrueSV = Ext.getCmp(criteriaSV);   
	
	
		criteriaTrueSV.store.add(new Ext.data.Record({id: '', value: finalValue}));

		criteriaTrue.setRawValue("");
		displayErrorMsg('Value has been successfully added' , 'errorMessageDivDialog');
	}
}


function deleteEntry (criteriaSV) {
	
	var criteriaTrueSV = Ext.getCmp(criteriaSV); 
	
	criteriaTrueSV.store.remove(criteriaTrueSV.store.getAt(criteriaTrueSV.selectedIndex)); 
	
	criteriaTrueSV.setRawValue("");
	displayErrorMsg('Value  has been successfully deleted' , 'errorMessageDivDialog');
}



Boeing.editRulesFormPanel = Ext.extend(Ext.form.FormPanel, {
    initComponent:function() {
    	  Ext.apply(this, {		
  			width: '900px',
  			autoHeight: true, 
  		//	title:"Search",
  			tbar: {xtype: 'editRuleNavBar'   },
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
								  	id: 'ruleId',
								  	name: 'ruleId',
								  	value: rule == null ? null:rule.id
									},
									{xtype: 'hidden',
									  	id: 'criteriaAnyTrueSVString',
										  	name: 'criteriaAnyTrueSVString'
										  //	value: getCriteriaTrueSVString()
											}, 
											{xtype: 'hidden',
											  	id: 'criteriaAllTrueSVString',
												  	name: 'criteriaAllTrueSVString'
												  //	value: getCriteriaTrueSVString()
													}, 
													{xtype: 'hidden',
													  	id: 'criteriaNotTrueSVString',
														  	name: 'criteriaNotTrueSVString'
														  //	value: getCriteriaTrueSVString()
															},
  		    		       		       
  		    		       		       {xtype: 'hidden',
									  	id: 'criteriaTrueSVString',
										  	name: 'criteriaTrueSVString'
										  //	value: getCriteriaTrueSVString()
											}, {xtype: 'hidden',
											  	id: 'rulesNotTrueSVString',
											  	name: 'rulesNotTrueSVString'
											  //	value: getCriteriaTrueSVString()
												}, {xtype: 'hidden',
												  	id: 'rulesAnyTrueSVString',
												  	name: 'rulesAnyTrueSVString'
												  //	value: getCriteriaTrueSVString()
													}, {xtype: 'hidden',
													  	id: 'rulesAllTrueSVString',
													  	name: 'rulesAllTrueSVString'
													  //	value: getCriteriaTrueSVString()
														},{ xtype :'container' , height:'25' , id : 'errorMessageDivDialog' },
														{xtype: 'displayfield', 
  		    	  	       			
  		    	  	    		  	fieldLabel: 'Rule Name',
  		    	  	    		
  		    	  						id: 'ruleName',
  		    	  						name: 'ruleName',
  		    	  						width: 110,
  		    	  						maxLength: 100,
  		    	  	    		  	value: rule == null ? '(To Be Generated)' : rule.name,
  		    	  	    		  	labelSeparator: '',
  		    	  	    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  		    	  		       },  {xtype : 'textarea',						    	
			     					
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
			    		       	},{xtype :'container' , height:'10'  }
  									]}]}, // end of first fieldset 
  									
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
  								 		    	   fieldLabel: 'Chk Criteria True:',
  										    	   labelSeparator: '',
  										    	   width: 110,
  										    	   allowBlank: true,
  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  										    	   height: 30,    		    	   		 		    		  	
  										    	   emptyText: 'Select One',
  													   id: 'criteriaTrue',
  													   name: 'criteriaTrue',		
  													listWidth : 160,
  													editable : false,
  													triggerAction : 'all',
  													valueField: 'id',
  								 					displayField:'itemNumber',
  								 					hiddenName : 'criteriaTrueDD',
  													submitValue : true,
  													mode: 'local',   
  													   store: new Ext.data.JsonStore({     
  														    autoLoad: true,
  														method: 'GET',
  														url: "/TestSpring/view/ssow/getCheckList",
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
  														fields: ['id', 'itemNumber'],
  														
  												}), listeners:{select:{fn:function(combo, value) {
  								                  
  													criteriaTrueCheckListId = combo.getValue();
  													var comboCity = Ext.getCmp('criteriaTruePV');   
  													//comboCity.clearValue();
  														comboCity.getStore().setBaseParam("checkListId", combo.getValue());
  														comboCity.store.reload();
  								                    }} }   }   ]}, 
  								                    
  							    	       {xtype: 'fieldset', vertical: true,
  					    		       		items:[{xtype: 'combo',	      	    	   
									 		    	   fieldLabel: 'Possible Values:',
											    	   labelSeparator: '',
											    	   width: 110,
											    	   allowBlank: true,											    	
											    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
											    	   height: 30,											    					    		    	   		 		    		  	
											    	   emptyText: 'Select One',
														   id: 'criteriaTruePV',
														   name: 'criteriaTruePV',		
														listWidth : 160,
														editable : false,
														triggerAction : 'all',
														valueField: 'value',
									 					displayField:'value',
									 					hiddenName : 'criteriaTruePVDD',												
														submitValue : true,
														mode: 'local',   
														
														   store: new Ext.data.JsonStore({     
															    autoLoad: false,
															method: 'GET',
															url: "/TestSpring/view/ssow/getCheckListPV" ,
															prettyUrls: false,
															timeout: 240000, 
															root: 'data',        
													headers: {
													    'Accept': 'application/json'
													},
													baseParams: {
														checkListId : criteriaTrueCheckListId
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
			  		  					           				listeners:{click:function(item, event){addEntry('criteriaTrue' , 'criteriaTruePV' , 'criteriaTrueSV');
			  		  											}}}   ]}, 
		  	  		  											{xtype: 'fieldset', vertical: true,
													   items:[{xtype: 'tbbutton', text: 'Remove =>', 
		  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'criteriaTrueSV');
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
 		  												   id: 'criteriaTrueSV',
 		  												   name: 'criteriaTrueSV',		
 		  												listWidth : 160,
 		  												editable : false,
 		  												triggerAction : 'all',
 		  												valueField: 'value',
 		  							 					displayField:'value',
 		  							 					hiddenName : 'criteriaTrueSVDD', 		  												
 		  												submitValue : true,
 		  												mode: 'local',    		  											
 		  												   store: new Ext.data.JsonStore({     
 		  													    autoLoad: true,
 		  													method: 'GET',
 		  													url: "/TestSpring/view/ssow/getCheckListSV" ,
 		  													prettyUrls: false,
 		  													timeout: 240000, 
 		  													root: 'data',        
 		  													headers: {
 		  													    'Accept': 'application/json'
 		  													},
 		  												
 		  													baseParams: {
 		  														criteriaId : rule == null ? null : rule.checklistCriteriaTrueID
 		  													},
 		  													fields: ['id', 'value'],
 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  								    	       ]}
  									,{xtype :'container' , height:'10'  }// end of second field set 
  									,   {xtype: 'fieldset', vertical: true,
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
  								 		    	   fieldLabel: 'Chk Criteria Any True:',
  										    	   labelSeparator: '',
  										    	   width: 110,
  										    	   allowBlank: true,
  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  										    	   height: 30,    		    	   		 		    		  	
  										    	   emptyText: 'Select One',
  													   id: 'criteriaAnyTrue',
  													   name: 'criteriaAnyTrue',		
  													listWidth : 160,
  													editable : false,
  													triggerAction : 'all',
  													valueField: 'id',
  								 					displayField:'itemNumber',
  								 					hiddenName : 'criteriaAnyTrueDD',
  													submitValue : true,
  													mode: 'local',   
  													   store: new Ext.data.JsonStore({     
  														    autoLoad: true,
  														method: 'GET',
  														url: "/TestSpring/view/ssow/getCheckList",
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
  														fields: ['id', 'itemNumber'],
  														
  												}), listeners:{select:{fn:function(combo, value) {
  								                  
  													criteriaTrueCheckListId = combo.getValue();
  													var comboCity = Ext.getCmp('criteriaAnyTruePV');   
  													//comboCity.clearValue();
  														comboCity.getStore().setBaseParam("checkListId", combo.getValue());
  														comboCity.store.reload();
  								                    }} }   }   ]}, 
  								                    
  							    	       {xtype: 'fieldset', vertical: true,
  					    		       		items:[{xtype: 'combo',	      	    	   
									 		    	   fieldLabel: 'Possible Values:',
											    	   labelSeparator: '',
											    	   width: 110,
											    	   allowBlank: true,											    	
											    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
											    	   height: 30,											    					    		    	   		 		    		  	
											    	   emptyText: 'Select One',
														   id: 'criteriaAnyTruePV',
														   name: 'criteriaAnyTruePV',		
														listWidth : 160,
														editable : false,
														triggerAction : 'all',
														valueField: 'value',
									 					displayField:'value',
									 					hiddenName : 'criteriaAnyTruePVDD',												
														submitValue : true,
														mode: 'local',   
														
														   store: new Ext.data.JsonStore({     
															    autoLoad: false,
															method: 'GET',
															url: "/TestSpring/view/ssow/getCheckListPV" ,
															prettyUrls: false,
															timeout: 240000, 
															root: 'data',        
													headers: {
													    'Accept': 'application/json'
													},
													baseParams: {
														checkListId : ''
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
			  		  					           				listeners:{click:function(item, event){addEntry('criteriaAnyTrue' , 'criteriaAnyTruePV' , 'criteriaAnyTrueSV');
			  		  											}}}   ]}, 
		  	  		  											{xtype: 'fieldset', vertical: true,
													   items:[{xtype: 'tbbutton', text: 'Remove =>', 
		  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'criteriaAnyTrueSV');
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
 		  												   id: 'criteriaAnyTrueSV',
 		  												   name: 'criteriaAnyTrueSV',		
 		  												listWidth : 160,
 		  												editable : false,
 		  												triggerAction : 'all',
 		  												valueField: 'value',
 		  							 					displayField:'value',
 		  							 					hiddenName : 'criteriaAnyTrueSVDD', 		  												
 		  												submitValue : true,
 		  												mode: 'local',    		  											
 		  												   store: new Ext.data.JsonStore({     
 		  													    autoLoad: true,
 		  													method: 'GET',
 		  													url: "/TestSpring/view/ssow/getCheckListSV" ,
 		  													prettyUrls: false,
 		  													timeout: 240000, 
 		  													root: 'data',        
 		  													headers: {
 		  													    'Accept': 'application/json'
 		  													},
 		  												
 		  													baseParams: {
 		  														criteriaId : rule == null ? null : rule.checklistCriteriaAnyTrueID
 		  													},
 		  													fields: ['id', 'value'],
 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  								    	       ]}, // end of crtiera any true 
  								    	     {xtype :'container' , height:'10'  },     
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
  								 		    	   fieldLabel: 'Chk Criteria All True:',
  										    	   labelSeparator: '',
  										    	   width: 110,
  										    	   allowBlank: true,
  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  										    	   height: 30,    		    	   		 		    		  	
  										    	   emptyText: 'Select One',
  													   id: 'criteriaAllTrue',
  													   name: 'criteriaAllTrue',		
  													listWidth : 160,
  													editable : false,
  													triggerAction : 'all',
  													valueField: 'id',
  								 					displayField:'itemNumber',
  								 					hiddenName : 'criteriaAllTrueDD',
  													submitValue : true,
  													mode: 'local',   
  													   store: new Ext.data.JsonStore({     
  														    autoLoad: true,
  														method: 'GET',
  														url: "/TestSpring/view/ssow/getCheckList",
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
  														fields: ['id', 'itemNumber'],
  														
  												}), listeners:{select:{fn:function(combo, value) {
  								                  
  													criteriaTrueCheckListId = combo.getValue();
  													var comboCity = Ext.getCmp('criteriaAllTruePV');   
  													//comboCity.clearValue();
  														comboCity.getStore().setBaseParam("checkListId", combo.getValue());
  														comboCity.store.reload();
  								                    }} }   }   ]}, 
  								                    
  							    	       {xtype: 'fieldset', vertical: true,
  					    		       		items:[{xtype: 'combo',	      	    	   
									 		    	   fieldLabel: 'Possible Values:',
											    	   labelSeparator: '',
											    	   width: 110,
											    	   allowBlank: true,											    	
											    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
											    	   height: 30,											    					    		    	   		 		    		  	
											    	   emptyText: 'Select One',
														   id: 'criteriaAllTruePV',
														   name: 'criteriaAllTruePV',		
														listWidth : 160,
														editable : false,
														triggerAction : 'all',
														valueField: 'value',
									 					displayField:'value',
									 					hiddenName : 'criteriaAllTruePVDD',												
														submitValue : true,
														mode: 'local',   
														
														   store: new Ext.data.JsonStore({     
															    autoLoad: false,
															method: 'GET',
															url: "/TestSpring/view/ssow/getCheckListPV" ,
															prettyUrls: false,
															timeout: 240000, 
															root: 'data',        
													headers: {
													    'Accept': 'application/json'
													},
													baseParams: {
														checkListId : criteriaTrueCheckListId
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
			  		  					           				listeners:{click:function(item, event){addEntry('criteriaAllTrue' , 'criteriaAllTruePV' , 'criteriaAllTrueSV');
			  		  											}}}   ]}, 
		  	  		  											{xtype: 'fieldset', vertical: true,
													   items:[{xtype: 'tbbutton', text: 'Remove =>', 
		  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'criteriaAllTrueSV');
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
 		  												   id: 'criteriaAllTrueSV',
 		  												   name: 'criteriaAllTrueSV',		
 		  												listWidth : 160,
 		  												editable : false,
 		  												triggerAction : 'all',
 		  												valueField: 'value',
 		  							 					displayField:'value',
 		  							 					hiddenName : 'criteriaAllTrueSVDD', 		  												
 		  												submitValue : true,
 		  												mode: 'local',    		  											
 		  												   store: new Ext.data.JsonStore({     
 		  													    autoLoad: true,
 		  													method: 'GET',
 		  													url: "/TestSpring/view/ssow/getCheckListSV" ,
 		  													prettyUrls: false,
 		  													timeout: 240000, 
 		  													root: 'data',        
 		  													headers: {
 		  													    'Accept': 'application/json'
 		  													},
 		  												
 		  													baseParams: {
 		  														criteriaId : rule == null ? null : rule.checklistCriteriaAllTrueID
 		  													},
 		  													fields: ['id', 'value'],
 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  								    	       ]} // end of field set for criteia all  true 
  								    	   ,{xtype :'container' , height:'10'  },  {xtype: 'fieldset', vertical: true,
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
  			  								 		    	   fieldLabel: 'Chk Criteria Not True:',
  			  										    	   labelSeparator: '',
  			  										    	   width: 110,
  			  										    	   allowBlank: true,
  			  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  			  										    	   height: 30,    		    	   		 		    		  	
  			  										    	   emptyText: 'Select One',
  			  													   id: 'criteriaNotTrue',
  			  													   name: 'criteriaNotTrue',		
  			  													listWidth : 160,
  			  													editable : false,
  			  													triggerAction : 'all',
  			  													valueField: 'id',
  			  								 					displayField:'itemNumber',
  			  								 					hiddenName : 'criteriaNotTrueDD',
  			  													submitValue : true,
  			  													mode: 'local',   
  			  													   store: new Ext.data.JsonStore({     
  			  														    autoLoad: true,
  			  														method: 'GET',
  			  														url: "/TestSpring/view/ssow/getCheckList",
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
  			  														fields: ['id', 'itemNumber'],
  			  														
  			  												}), listeners:{select:{fn:function(combo, value) {
  			  								                  
  			  													criteriaTrueCheckListId = combo.getValue();
  			  													var comboCity = Ext.getCmp('criteriaNotTruePV');   
  			  													//comboCity.clearValue();
  			  														comboCity.getStore().setBaseParam("checkListId", combo.getValue());
  			  														comboCity.store.reload();
  			  								                    }} }   }   ]}, 
  			  								                    
  			  							    	       {xtype: 'fieldset', vertical: true,
  			  					    		       		items:[{xtype: 'combo',	      	    	   
  												 		    	   fieldLabel: 'Possible Values:',
  														    	   labelSeparator: '',
  														    	   width: 110,
  														    	   allowBlank: true,											    	
  														    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  														    	   height: 30,											    					    		    	   		 		    		  	
  														    	   emptyText: 'Select One',
  																	   id: 'criteriaNotTruePV',
  																	   name: 'criteriaNotTruePV',		
  																	listWidth : 160,
  																	editable : false,
  																	triggerAction : 'all',
  																	valueField: 'value',
  												 					displayField:'value',
  												 					hiddenName : 'criteriaNotTruePVDD',												
  																	submitValue : true,
  																	mode: 'local',   
  																	
  																	   store: new Ext.data.JsonStore({     
  																		    autoLoad: false,
  																		method: 'GET',
  																		url: "/TestSpring/view/ssow/getCheckListPV" ,
  																		prettyUrls: false,
  																		timeout: 240000, 
  																		root: 'data',        
  																headers: {
  																    'Accept': 'application/json'
  																},
  																baseParams: {
  																	checkListId : ''
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
  					  		  					           				listeners:{click:function(item, event){addEntry('criteriaNotTrue' , 'criteriaNotTruePV' , 'criteriaNotTrueSV');
  					  		  											}}}   ]}, 
  				  	  		  											{xtype: 'fieldset', vertical: true,
  															   items:[{xtype: 'tbbutton', text: 'Remove =>', 
  				  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'criteriaNotTrueSV');
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
  			 		  												   id: 'criteriaNotTrueSV',
  			 		  												   name: 'criteriaNotTrueSV',		
  			 		  												listWidth : 160,
  			 		  												editable : false,
  			 		  												triggerAction : 'all',
  			 		  												valueField: 'value',
  			 		  							 					displayField:'value',
  			 		  							 					hiddenName : 'criteriaNotTrueSVDD', 		  												
  			 		  												submitValue : true,
  			 		  												mode: 'local',    		  											
  			 		  												   store: new Ext.data.JsonStore({     
  			 		  													    autoLoad: true,
  			 		  													method: 'GET',
  			 		  													url: "/TestSpring/view/ssow/getCheckListSV" ,
  			 		  													prettyUrls: false,
  			 		  													timeout: 240000, 
  			 		  													root: 'data',        
  			 		  													headers: {
  			 		  													    'Accept': 'application/json'
  			 		  													},
  			 		  												
  			 		  													baseParams: {
  			 		  														criteriaId : rule == null ? null : rule.checklistCriteriaAnyNotTrueID
  			 		  													},
  			 		  													fields: ['id', 'value'],
  			 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  			  								    	       ]} // end of criteria not true 
  								    	   , {xtype :'container' , height:'10'  },  {xtype: 'fieldset', vertical: true,
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
  			  								 		    	   fieldLabel: 'Rules Criteria Any True:',
  			  										    	   labelSeparator: '',
  			  										    	   width: 110,
  			  										    	   allowBlank: true,
  			  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  			  										    	   height: 30,    		    	   		 		    		  	
  			  										    	   emptyText: 'Select One',
  			  													   id: 'rulesAnyTrue',
  			  													   name: 'rulesAnyTrue',		
  			  													listWidth : 160,
  			  													editable : false,
  			  													triggerAction : 'all',
  			  													valueField: 'id',
  			  								 					displayField:'name',
  			  								 					hiddenName : 'rulesAnyTrueDD',
  			  													submitValue : true,
  			  													mode: 'local',   
  			  													   store: new Ext.data.JsonStore({     
  			  														    autoLoad: true,
  			  														method: 'GET',
  			  													url : "/TestSpring/view/managesections/getAllRules",
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
  			  														
  			  												}), listeners:{select:{fn:function(combo, value) {
  			  								                  
  			  													criteriaTrueCheckListId = combo.getValue();
  			  													var comboCity = Ext.getCmp('criteriaNotTruePV');   
  			  													//comboCity.clearValue();
  			  														comboCity.getStore().setBaseParam("checkListId", combo.getValue());
  			  														comboCity.store.reload();
  			  								                    }} }   }   ]}, 
  			  								                    
  			  							    	       
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
  					  		  					           				listeners:{click:function(item, event){addRulesEntry('rulesAnyTrue' , 'rulesAnyTrueSV' );
  					  		  											}}}   ]}, 
  				  	  		  											{xtype: 'fieldset', vertical: true,
  															   items:[{xtype: 'tbbutton', text: 'Remove =>', 
  				  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'rulesAnyTrueSV');
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
  			 		  												   id: 'rulesAnyTrueSV',
  			 		  												   name: 'rulesAnyTrueSV',		
  			 		  												listWidth : 160,
  			 		  												editable : false,
  			 		  												triggerAction : 'all',
  			 		  												valueField: 'value',
  			 		  							 					displayField:'value',
  			 		  							 					hiddenName : 'rulesAnyTrueSVDD', 		  												
  			 		  												submitValue : true,
  			 		  												mode: 'local',    		  											
  			 		  												   store: new Ext.data.JsonStore({     
  			 		  													    autoLoad: true,
  			 		  													method: 'GET',
  			 		  													url: "/TestSpring/view/ssow/getRuleSV" ,
  			 		  													prettyUrls: false,
  			 		  													timeout: 240000, 
  			 		  													root: 'data',        
  			 		  													headers: {
  			 		  													    'Accept': 'application/json'
  			 		  													},
  			 		  												
  			 		  													baseParams: {
  			 		  														criteriaId : rule == null ? null : rule.rulesCriteriaAnyTrueID
  			 		  													},
  			 		  													fields: ['id', 'value'],
  			 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  			  								    	       ]} // end of rules criteria 1
  								    	   
  								    	   , {xtype :'container' , height:'10'  },  {xtype: 'fieldset', vertical: true,
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
  			  								 		    	   fieldLabel: 'Rules Criteria All True:',
  			  										    	   labelSeparator: '',
  			  										    	   width: 110,
  			  										    	   allowBlank: true,
  			  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  			  										    	   height: 30,    		    	   		 		    		  	
  			  										    	   emptyText: 'Select One',
  			  													   id: 'rulesAllTrue',
  			  													   name: 'rulesAllTrue',		
  			  													listWidth : 160,
  			  													editable : false,
  			  													triggerAction : 'all',
  			  													valueField: 'id',
  			  								 					displayField:'name',
  			  								 					hiddenName : 'rulesAllTrueDD',
  			  													submitValue : true,
  			  													mode: 'local',   
  			  													   store: new Ext.data.JsonStore({     
  			  														    autoLoad: true,
  			  														method: 'GET',
  			  													url : "/TestSpring/view/managesections/getAllRules",
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
  					  		  					           				listeners:{click:function(item, event){addRulesEntry('rulesAllTrue' , 'rulesAllTrueSV' );
  					  		  											}}}   ]}, 
  				  	  		  											{xtype: 'fieldset', vertical: true,
  															   items:[{xtype: 'tbbutton', text: 'Remove =>', 
  				  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'rulesAllTrueSV');
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
  			 		  												   id: 'rulesAllTrueSV',
  			 		  												   name: 'rulesAllTrueSV',		
  			 		  												listWidth : 160,
  			 		  												editable : false,
  			 		  												triggerAction : 'all',
  			 		  												valueField: 'value',
  			 		  							 					displayField:'value',
  			 		  							 					hiddenName : 'rulesAllTrueSVDD', 		  												
  			 		  												submitValue : true,
  			 		  												mode: 'local',    		  											
  			 		  												   store: new Ext.data.JsonStore({     
  			 		  													    autoLoad: true,
  			 		  													method: 'GET',
  			 		  													url: "/TestSpring/view/ssow/getRuleSV" ,
  			 		  													prettyUrls: false,
  			 		  													timeout: 240000, 
  			 		  													root: 'data',        
  			 		  													headers: {
  			 		  													    'Accept': 'application/json'
  			 		  													},
  			 		  												
  			 		  													baseParams: {
  			 		  														criteriaId : rule == null ? null : rule.rulesCriteriaAllTrueID
  			 		  													},
  			 		  													fields: ['id', 'value'],
  			 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  			  								    	       ]} // end of rules criteria 2
  								    	   , {xtype :'container' , height:'10'  },  {xtype: 'fieldset', vertical: true,
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
  			  								 		    	   fieldLabel: 'Rules Criteria Not True:',
  			  										    	   labelSeparator: '',
  			  										    	   width: 110,
  			  										    	   allowBlank: true,
  			  										    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  			  										    	   height: 30,    		    	   		 		    		  	
  			  										    	   emptyText: 'Select One',
  			  													   id: 'rulesNotTrue',
  			  													   name: 'rulesNotTrue',		
  			  													listWidth : 160,
  			  													editable : false,
  			  													triggerAction : 'all',
  			  													valueField: 'id',
  			  								 					displayField:'name',
  			  								 					hiddenName : 'rulesNotTrueDD',
  			  													submitValue : true,
  			  													mode: 'local',   
  			  													   store: new Ext.data.JsonStore({     
  			  														    autoLoad: true,
  			  														method: 'GET',
  			  													url : "/TestSpring/view/managesections/getAllRules",
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
  					  		  					           				listeners:{click:function(item, event){addRulesEntry('rulesNotTrue' , 'rulesNotTrueSV' );
  					  		  											}}}   ]}, 
  				  	  		  											{xtype: 'fieldset', vertical: true,
  															   items:[{xtype: 'tbbutton', text: 'Remove =>', 
  				  	  		  				        				listeners:{click:function(item, event){deleteEntry( 'rulesNotTrueSV');
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
  			 		  												   id: 'rulesNotTrueSV',
  			 		  												   name: 'rulesNotTrueSV',		
  			 		  												listWidth : 160,
  			 		  												editable : false,
  			 		  												triggerAction : 'all',
  			 		  												valueField: 'value',
  			 		  							 					displayField:'value',
  			 		  							 					hiddenName : 'rulesNotTrueSVDD', 		  												
  			 		  												submitValue : true,
  			 		  												mode: 'local',    		  											
  			 		  												   store: new Ext.data.JsonStore({     
  			 		  													    autoLoad: true,
  			 		  													method: 'GET',
  			 		  													url: "/TestSpring/view/ssow/getRuleSV" ,
  			 		  													prettyUrls: false,
  			 		  													timeout: 240000, 
  			 		  													root: 'data',        
  			 		  													headers: {
  			 		  													    'Accept': 'application/json'
  			 		  													},
  			 		  												
  			 		  													baseParams: {
  			 		  														criteriaId : rule == null ? null : rule.rulesCriteriaNotTrueID
  			 		  													},
  			 		  													fields: ['id', 'value'],
  			 		  												   })  } ]} 	       									    	       		  									    	       									    	       
  			  								    	       ]} // end of rules criteria 3
		    		       	]}
		    		       	
		    		       
  			
  			],
  			listeners:{
  				afterrender: function(panel){
  					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
  				}
  			}
  	    });
  	    Boeing.ssowMetaDataPanel.superclass.initComponent.apply(this, arguments);
  	}
  });
  Ext.reg('editRulesFormPanel', Boeing.editRulesFormPanel);











