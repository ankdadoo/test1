// *********************************************************************************
// NOTE: Toolbar for subpanel: Search, Add, Create 
// *********************************************************************************
Ext.ns('Boeing');
Boeing.ManageSectionsNavBar = Ext.extend(Ext.Toolbar, { 
    initComponent:function() {
        Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Create Main Section' , listeners:{
			    		click:function(item, event){
			    			addMainSection();
			    			}
			    		}
			    	}
			]
        });
        Boeing.ManageSectionsNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('ManageSectionsNavBar', Boeing.ManageSectionsNavBar);

// *********************************************************************************
// NOTE: Main panel for the manage templates which the other items/elements are added to
//*********************************************************************************
Ext.ns('Boeing');
Boeing.ManageSectionsPanel = Ext.extend(Ext.Panel, {
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
			          { xtype :'container' , height:'25' , id : 'errorMessageDiv' },
			          { xtype: 'selectSectionFormPanel', id: 'selectSectionFormPanel'},
			          { xtype :'container' , height:'25'},
			          { xtype: 'manageSectionsList', id: 'manageSectionsList'}
			],
			listeners : {
				afterrender : function(panel) {
//					panel.setTitle('<div style="font-size:medium;">'+ panel.title + '</div>');
				}
			}
		});
		Boeing.MainPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('managesectionsPanel', Boeing.ManageSectionsPanel);

Boeing.selectSectionFormPanel = Ext.extend(Ext.form.FormPanel, {
	initComponent : function() {
		Ext.apply(this, {
			width : '400px',
			autoHeight : true,
			trackResetOnLoad : true,
			method : 'POST',
			id : 'selectSectionForm',
			tbar: {xtype: 'ManageSectionsNavBar'   },
			defaults : {
				style : {
					padding: '0px',
					bodyStyle: 'padding-left:10px'
				}
			},
			items : [
			         {xtype: 'fieldset', vertical: true,
			        	 bodyStyle: 'padding-right:5px; padding-left:10px;',
	    	             border: false,
	    	             // defaults are applied to all child items unless otherwise specified by child item
	    	             defaults: {
	    	                columnWidth: 1.0,
	    	                border: false
	    	             },
	    	             items:[
	    	                    {
	    	                    	xtype : 'container',
	    	                    	height : '20'
	    	                    } , 
	    	                    { xtype: 'combo',
	    	                        fieldLabel: 'Section:',
	    	                        labelSeparator: '',
	    	                        width: 100,
	    	                        allowBlank: false,
	    	                        loadingText: "Loading...",
	    	                        emptyText: 'Select One',
	    	                        id: 'mainSectionDD',
	    	                        name: 'mainSectionDD',
	    	                        editable : false,
	    	                        triggerAction : 'all',
	    	                        displayField:'sectionNumber', // 'text',
	    	                        valueField: 'sectionNumber', // 'id', //'description',
	    	                        hiddenName : "idTypeHidden", //'contractTypeHidden',
	    	                        emptyText : 'Select One',
	    	                        mode: 'local',
	    	                        value : '', 
	    	                        store: mainsectionsStore,
	    	                    	listeners: {
	    	                			'select': function() {
	    	                				var sectionValue = Ext.getCmp('mainSectionDD').getValue();
	    	                				
	    	                				// Pagination Effort:
	    	                				managesection_mainsection = sectionValue;
	    	                				sectionsStore.setBaseParam('sectionNumber',  managesection_mainsection);
	    	                				sectionsStore.load({params: {start:0, limit:50}}); // Pagination Effort
	    	                			}
	    	                    	}
	    	                    }	    	                    
	    	                    , {
	    	                    	xtype : 'container', 
	    	                    	height : '10'
	    	                    } 
	    	         ]}   // end-of fieldset 
			],
			listeners : {
				afterrender : function(panel) {
//					panel.setTitle('<div style="font-size:medium;">'
//							+ 'Manage Users' + '</div>');
				}
			}
		});
		Boeing.selectSectionFormPanel.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
}); // eo Epic.buyerCheckListFormPanel
Ext.reg('selectSectionFormPanel', Boeing.selectSectionFormPanel);


Ext.ns('Boeing');
Boeing.ManageSectionListNavBar = Ext.extend(Ext.Toolbar, { 
    initComponent:function() {
        Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'}
					,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Edit' , listeners:{
			    		click:function(item, event){
			    			editSection();
			    			} 
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Add' , listeners:{ 
			    		click:function(item, event){
			    			addSection();
			    			} 
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Delete' , listeners:{
			    		click:function(item, event){
			    			deleteSection();
			    			}
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'UnDelete' , listeners:{
			    		click:function(item, event){
			    			undeleteSection();
			    			}
			    		}
			    	}
			]
        });
        Boeing.ManageSectionListNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('manageSectionListNavBar', Boeing.ManageSectionListNavBar);

Boeing.ManageSectionsList = Ext.extend(Boeing.EditorGridPanel, {
	listeners:{
				
	},
    initComponent:function() {
    Ext.apply(this, {    	
    		
    		width: 900, // 900,   
    		height: 465, // 400, //600,
    		collapsible: false,
    		titleCollapse: false,
    		headerAsText: false,
    		clicksToEdit: 1,
    		stripeRows : true,
    		store : sectionsStore,
			tbar: {xtype: 'manageSectionListNavBar'   },
    		
    		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
    	
    		// Pagination Effort
    		 bbar: new Ext.PagingToolbar({
 	            pageSize: 50,
 	            store: sectionsStore,
 	            displayInfo: true,
 	            displayMsg: 'Displaying Records {0} - {1} of {2}',
 	            emptyMsg: "No Records to display",
 	            handler: function() {
 	            	var fv = managesection_mainsection;
 	            	this.store.setBaseParam("sectionNumber", fv);
 	            }
// 	           ,items:[
// 	                '-', {
// 	                pressed: true,
// 	                enableToggle:true,
// 	                text: 'Show Record Details',
// 	                cls: 'x-btn-text-icon details',
// 	                toggleHandler: function(btn, pressed){
// 	                    var view = grid.getView();
// 	                    view.showPreview = pressed;
// 	                    view.refresh();
// 	                }
// 	            }]
 	        }),
    		
    		listeners: {
			    beforerender: function(){	
//			    	this.store.load({params: {start:0, limit:50}}); // Pagination Effort
				}
			}, 
			
			view: new Ext.grid.GridView({
				//markDirty: false
				viewConfig: {
					getRowClass: function(record, rowIndex, rp, ds) {
						var c = record.get('active'); 
						//alert("active = " + c);
						if (c == '1') {
							return 'deleted-row';
						}
					}
				} // viewConfig
			}),    		
			columns: [
			 {
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
				},{
					dataIndex : 'sectionNumber', 
					align : 'center',
					header : 'Section',
					sortable : false,
					width : 75,
					editable : false,
					hideable : false,
					hidden: false
				},{
					dataIndex : 'text',  
					align : 'left',
					header : 'Description',
					sortable : false,
					width : 650, 
					editable : false,
					hideable : false,
					renderer: function(data, cell, record, 
							         rowIndex, columnIndex, store) {
						var activeTest = record.get('active');
						var sectTypeTest = record.get('sectionType');
						return columnWrap(data, activeTest, sectTypeTest);
					}
				},{
					dataIndex : 'rulesString', 
					header : 'Rules Name',
					sortable : false,
					width : 75,
					editable : false,
					hideable : true,
					renderer : function(data, cell, record,
							rowIndex, columnIndex, store) {
				
					
					
					
								return columnWrapRule(data);	
									
						
						
					}
				},{
					dataIndex : 'sectionType', 
					header : 'Type',
					sortable : false,
					width : 55,
					editable : false,
					hideable : true, 
					hidden: false,
				}
			]    		     			
    	});
    Boeing.ManageSectionsList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('manageSectionsList', Boeing.ManageSectionsList);


function columnWrapRule(val) {
	return '<div style="white-space:normal !important;">' + val + '</div>';
}
function columnWrap(val, activeTest, sectTypeTest) {
	
	if (activeTest == "0") {
//		alert("need to use RED");
		var fontStartTag = '<font color="red">';
		var fontEndTag = '</font>';
	}
	else {
		var fontStartTag = "";
		var fontEndTag = "";
	}

	if (sectTypeTest == "H" || sectTypeTest == "S") {
//		alert("need to BOLD text");
		var boldStartTag = "<b>";
		var boldEndTag = "</b>";
	}
	else {
		var boldStartTag = "";
		var boldEndTag = "";
	} 
	
	return '<div style="white-space:normal !important;">' + fontStartTag + boldStartTag + val + boldEndTag + fontEndTag + '</div>';
}

var sectionsStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/managesections/getMainSubSections",
	

	root : 'data',
	totalProperty : 'results',
	timeout: 240000,
	autoLoad : false,
	baseParams: { sectionNumber: managesection_mainsection },   

	fields : [ {
		name : 'sectionNumber'
		
	}, {
		name : 'text' 
		
	}, {
		name : 'active' 
		
	}, {
		name : 'orderNumber' 
		
	}, {
		name : 'sectionType' 
	}, {
		name : 'successorId' 
		
	}, {
		name : 'rulesString'
		
	}, {
		name : 'id'
		
	} 
	]		
});

var mainsectionsStore = new Ext.data.JsonStore( {
	url : "/TestSpring/view/managesections/getMainSections",
	

	root : 'data',
	timeout: 240000,
	autoLoad : true,
	baseParams: { idTypeHidden: '0' }, 

	fields : [{
		name : 'sectionNumber'
	}]		
});



//Global manageusersWindow variables:
var managesection_win_submit_url = ""; 
var managesection_win_activity = "";
var managesection_win_title = "";
var managesection_win_audit_reason = "";

var managesection_mainsection = "1";





//*********************************************************************************
//NOTE: Toolbar for subpanel: Save, Cancel
//*********************************************************************************
Ext.ns('Boeing');
Boeing.EditSectionNavBar = Ext.extend(Ext.Toolbar, { 
initComponent:function() {
   Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'}
					,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Submit' , listeners:{
			    		click:function(item, event){
			    			submitSectionChange();
			    			}
			    		}
			    	}
			]
   });
   Boeing.EditSectionNavBar.superclass.initComponent.apply(this, arguments);
}
});
Ext.reg('editSectionNavBar', Boeing.EditSectionNavBar);


//*********************************************************************************
//NOTE: function for submitting the form and retrieving users (NO database coming back)
//*********************************************************************************
function submitSectionChange(changeType) {

	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv2');

	var form = Ext.getCmp('editsectionForm').getForm();
	
//	var validForm = validateUserChanges();
//	if (validForm)  {

		form.submit({
		url : managesection_win_submit_url, 
		method : 'POST', 
		timeout : 240000,

		success : function(form, action) {
			
			var data23 = action.result;
			var msg = data23.msg ;
			var success = data23.success; 
			if ( success == 'false' ) {
				displayErrorMsg(msg , 'errorMessageDiv2');
	        }else {
				displayErrorMsg(msg , 'errorMessageDiv');
				Ext.getCmp('managesectionsWindow').close();

				// TODO: how should screen reload???
				if (managesection_win_activity == "AddMain") {
					mainsectionsStore.load({params: { idTypeHidden: '0' } });
//					sectionsStore.load({params: { sectionNumber: ''} });
				} else {
					var mainSectiontestID = Ext.getCmp('mainSectionDD').getValue();
					managesection_mainsection = mainSectiontestID;
    				sectionsStore.setBaseParam('sectionNumber',  managesection_mainsection);
    				sectionsStore.load({params: {start:0, limit:50}}); // Pagination Effort
				}
		     }
		},
		failure : function(result, request) {
			var data = Ext.decode(result.responseText);
			// displayErrorMsgEpicService(data, data.data);
		}
	});
// } // if-end
}




Boeing.EditSectionNavBar = Ext.extend(Ext.Toolbar, { 
	initComponent:function() {
	   Ext.apply(this, {
		        height: 30,
				items:[{xtype: 'tbfill'}
						,{xtype: 'tbseparator'}
				    	,{xtype: 'tbbutton', text: 'Submit' , listeners:{
				    		click:function(item, event){
				    			submitSection();
				    			}
				    		}
				    	}
				]
	   });
	   Boeing.EditSectionNavBar.superclass.initComponent.apply(this, arguments);
	}
	});
	Ext.reg('editSectionNavBar', Boeing.EditSectionNavBar);


function submitSection() {
	

	
	
	
	
	var form = Ext.getCmp('editSectionFormPanel').getForm();	
	
	Ext.getCmp('rulesString').setValue(getCriteriaTrueSVString('sectionRuleSV'));
	
	if(form.isValid()){
		
		form.submit({
							url : "/TestSpring/view/managesections/saveSection",
							method: 'POST',
							timeout: 240000,
							
							success: function(form, action){
						
							
								var data23 = action.result;
								
						
								
									var msg = data23.msg ;
									
									displayErrorMsg(msg , 'errorMessageDiv');
									Ext.getCmp('manageSectionWindow').close();
								
									var grid = Ext.getCmp('manageSectionsList');
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





Boeing.manageSectionWindow = Ext.extend(Ext.Window, {
    
    title : '<center>Section Management</center>', 
    titleAlign: "center",
    id:'manageSectionWindow',
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
  		  
  		  items:[{xtype:'editSectionFormPanel', id: 'editSectionFormPanel'}]
  		  }
          ]            
          
          ;

  	  Boeing.manageSectionWindow.superclass.initComponent.call(this);
    }
});
Ext.reg('manageSectionWindow', Boeing.manageSectionWindow);



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

function addSectionEntry( criteria , criteriaSV) {
	
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


function deleteSectionEntry (criteriaSV) {
	
	var criteriaTrueSV = Ext.getCmp(criteriaSV); 
	
	criteriaTrueSV.store.remove(criteriaTrueSV.store.getAt(criteriaTrueSV.selectedIndex)); 
	
	criteriaTrueSV.setRawValue("");
	
	//criteriaTrueSV.store.add(new Ext.data.Record({id: '', name: ''}));
	displayErrorMsg('Rule  has been successfully deleted' , 'errorMessageDivDialog');
}



Boeing.editSectionFormPanel = Ext.extend(Ext.form.FormPanel, {
    initComponent:function() {
    	  Ext.apply(this, {		
  			width: '900px',
  			autoHeight: true, 
  		//	title:"Search",
  			tbar: {xtype: 'editSectionNavBar'   },
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
								  	id: 'sectionId',
								  	name: 'sectionId',
								  	value: section == null ? currentSectionId:section.id
									},{xtype: 'hidden',
									  	id: 'rulesString',
									  	name: 'rulesString'
									  //	value: getCriteriaTrueSVString()
										},{xtype: 'hidden',
										  	id: 'sectionStatus',
										  	name: 'sectionStatus',
										  	value: sectionStatus
											}, { xtype :'container' , height:'25' , id : 'errorMessageDivDialog' },
											
									  {xtype : 'textarea',						    	
					     					
											fieldLabel: 'Section Text',
						    		    	//labelSeparator: '',	
											id: 'sectionText',
											name: 'sectionText',
											
											//onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
										//	onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);}, 
											width : 600,
											maxLength: 3900,
											//height: 40,
											blankText:'If P is checked, comment must be input',
											value: section == null ? ''  : section.text,
											//hidden: (bidScreen != "bidTemplate"),
											height : 80,
											allowBlank : false ,
							    		  	labelStyle: 'width:170px;font-weight: bold;color: #000000;',
							    	    	   listeners:{
				   				   				change: function(f, newValue, oldValue){
					       						//	bclRulesEngineFieldChanged = true;
					       							
					       			   			}
								       	   	}
					    		       	}, {xtype :'container' , height:'20'  }, 
					    		       	{ xtype: 'combo',
					    				    
					    				    fieldLabel: 'Section Types:',
					    				    labelSeparator: '',
					    				    width: 220,
					    					labelStyle: 'width:170px;font-weight: bold;color: #000000;',
					    				    allowBlank: false,
					    				    loadingText: "Loading...",
					    				    emptyText: 'Select One',
					    				    id: 'sectionTypeDD',
					    				    name: 'sectionTypeDD',
					                          listWidth : 295,
					    				    editable : false,
					    				    triggerAction : 'all',
					    				    displayField:'description',
					    				    valueField: 'id',
					    				    hiddenName : "idSectionTypeHidden", 
					    				    emptyText : 'Select One',
					                          submitValue : true,
					                         // disabled: section == null ? false :true ,
					    				    mode: 'local',
					    				    value : '',
					    				    store: new Ext.data.JsonStore({
					    				    	url : "/TestSpring/view/managesections/getSectionTypes",
					    				    	root: 'data',
					    				    	timeout: 240000,
					    				    	autoLoad: true,
					    				    	baseParams: {  },
					    				    	forceselection: false,
					    				    	fields: ['id', 'description'],
					    				    	listeners:{		
					    							load: function(proxy, object, options){	
					    								Ext.getCmp('sectionTypeDD').setValue(getSectionType());
					    								
					    								
					    							
					    							},
					    							exception: function(proxy, type, action, options, response, arg){
					    							
					    							}
					    						}
					    				    }) , 
					    				    listeners:{load:{fn:function(combo, value) {
  								                  
					    				    	
					    				    	if ( section == null ) // which mean going for add 
					    				    		{
					    				    		// dont do anything as its already enabled 
					    				    		}else {
					    				    			//Ext.getCmp('sectionTypeDD').setReadOnly(true);
					    				    		}
											
													
								                    }} } 
					    			},{xtype :'container' , height:'20'  },
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
	  										    	   width: 130,
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
				  		  					           				listeners:{click:function(item, event){addSectionEntry('rulesApplicable' ,  'sectionRuleSV');
				  		  											}}}   ]}, 
			  	  		  											{xtype: 'fieldset', vertical: true,
														   items:[{xtype: 'tbbutton', text: 'Remove =>', 
			  	  		  				        				listeners:{click:function(item, event){deleteSectionEntry( 'sectionRuleSV');
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
	 		  												   id: 'sectionRuleSV',
	 		  												   name: 'sectionRuleSV',		
	 		  												listWidth : 160,
	 		  												editable : false,
	 		  												triggerAction : 'all',
	 		  												valueField: 'name',
	 		  							 					displayField:'name',
	 		  							 					hiddenName : 'sectionRuleSVDD', 		  												
	 		  												submitValue : true,
	 		  												mode: 'local',    		  											
	 		  												   store: new Ext.data.JsonStore({     
	 		  													    autoLoad: true,
	 		  													method: 'GET',
	 		  													url: "/TestSpring/view/ssow/getRulesListBySectionId" ,
	 		  													prettyUrls: false,
	 		  													timeout: 240000, 
	 		  													root: 'data',        
	 		  													headers: {
	 		  													    'Accept': 'application/json'
	 		  													},
	 		  												
	 		  													baseParams: {
	 		  														sectionId : section == null ? null : section.id
	 		  													},
	 		  													fields: ['id', 'name'],
	 		  												   })  } ]} 	       									    	       		  									    	       									    	       
	  								    	       ]}
	  									,{xtype :'container' , height:'20'  },{xtype : 'textarea',						    	
			     					
									fieldLabel: 'Traceability Of Changes',
				    		    	//labelSeparator: '',	
									id: 'auditReason',
									name: 'auditReason',
									//onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
								//	onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);}, 
									width : 600,
									maxLength: 1990,
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
  	    Boeing.editSectionFormPanel.superclass.initComponent.apply(this, arguments);
  	}
  });
  Ext.reg('editSectionFormPanel', Boeing.editSectionFormPanel);

  

  function getSectionType () {
  	
  	if ( section != null ) {
  		
  		return section.sectionType;
  	}
  	
  	return '';
  }


  
  
  
  

  
  
  
  
  
  
  function undeleteSection() {
		
	  
	  
	  var grid = Ext.getCmp('manageSectionsList');
		var record = grid.getSelectionModel().getSelected();
		
		if(record) {
			var activeCheck = record.get('active');
			if (activeCheck == "1") {
		        displayErrorMsg("Section already in Active State - Can't be Un-Deleted!" , 'errorMessageDiv');
			} 
			else {
				Ext.MessageBox.buttonText.yes = "Yes, Un-Delete Section";
				
				Ext.Msg.show({
					   	   title:'',
						   minWidth: 400,
						   height: 300,
					       msg: 'Are you sure , you want to Un-Delete Section?',
						  buttons: {yes:true,  cancel:true},
						  fn: function(e){    						
								if (e == 'yes') {
									
									
									var grid = Ext.getCmp('manageSectionsList');
									
									var record = grid.getSelectionModel().getSelected();
							
								
									var sectionId = record.get('id');
								
							
									Ext.Ajax.request({
										url :   "/TestSpring/view/managesections/undeleteSection",
										method: 'POST',
										timeout: 240000,
										headers: {
										    'Accept': 'application/json'
										},	
										params:{
											sectionId: sectionId
											
										
								
										},		
										success: function ( result, request) {		
											var data = Ext.decode(result.responseText);
								
											displayErrorMsg(data.msg , 'errorMessageDiv');
											var grid = Ext.getCmp('manageSectionsList');
											grid.store.reload();
										
													
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
				

	}
  function deleteSection() {
		
	  
	  
	  var grid = Ext.getCmp('manageSectionsList');
		var record = grid.getSelectionModel().getSelected();
		
		if(record) {
			var activeCheck = record.get('active');
			if (activeCheck == "0") {
			    displayErrorMsg("Section already in Delete State!" , 'errorMessageDiv');
			} 
			else {
				Ext.MessageBox.buttonText.yes = "Yes, Delete Section";
				
				Ext.Msg.show({
					   	   title:'',
						   minWidth: 400,
						   height: 300,
					       msg: 'Are you sure , you want to delete Section?',
						  buttons: {yes:true,  cancel:true},
						  fn: function(e){    						
								if (e == 'yes') {
									
									
									var grid = Ext.getCmp('manageSectionsList');
									
									var record = grid.getSelectionModel().getSelected();
								//	var rowIndex = grid.getStore().indexOf(record);
								
									var sectionId = record.get('id');
								
							
									Ext.Ajax.request({
										url :   "/TestSpring/view/managesections/deleteSection",
										method: 'POST',
										timeout: 240000,
										headers: {
										    'Accept': 'application/json'
										},	
										params:{
											sectionId: sectionId
											
										
								
										},		
										success: function ( result, request) {		
											var data = Ext.decode(result.responseText);
								
											displayErrorMsg(data.msg , 'errorMessageDiv');
											var grid = Ext.getCmp('manageSectionsList');
											grid.store.reload();
										
													
										},		failure: function ( result, request) {
											
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
				

	}


  function editSection() {
		
		
		
		
		var grid = Ext.getCmp('manageSectionsList');
		
		var record = grid.getSelectionModel().getSelected();
	
	
		sectionStatus = "EDIT";
		
		if(record) {
			var activeCheck = record.get('active');
			if (activeCheck == "0") {
			    displayErrorMsg("Section cannot be edited - must be undeleted before able to Edit!" , 'errorMessageDiv');
			} else {
					var sectionId = record.get('id');
				
				//	alert ("rules id " + rulesId);
					Ext.Ajax.request({
						url :   "/TestSpring/view/ssow/getSection",
						method: 'GET',
						timeout: 240000,
						headers: {
						    'Accept': 'application/json'
						},	
						params:{
							sectionId: sectionId
							
						
				
						},		
						success: function ( result, request) {		
							var data = Ext.decode(result.responseText);
							section = data.data;
						
							
							var win = new Boeing.manageSectionWindow({});
							win.show();
					
									
						},		failure: function ( result, request) {
							//	alert("eception happened");
							var data = Ext.decode(result.responseText);
							displayErrorMsgEpicService(data, data.data);			
						}
					}); 	
			}
		}


}





  function addMainSection() {  

		
	  
		displayErrorMsg('' , 'errorMessageDiv');
		sectionStatus = "MAIN";// clears the error message from the panel
		section = null;
		var win = new Boeing.manageSectionWindow({});
		win.show();
		//displayErrorMsg('' , 'errorMessageDiv');
	//	managesection_win_submit_url = "/TestSpring/view/managesections/addMainSection";
		//managesection_win_activity = "AddMain";	

		//showSectionWindow();
	  //  Ext.getCmp('managesectionsWindow').setTitle('<center>Create Main Section</center>');
	  //  managesection_win_title = "Create Main Section";
	  //  managesection_win_audit_reason = "Reason for Main Section Creation:"
	  //  Ext.getCmp('sectionId2').setValue('');
	  //  Ext.getCmp('sectionText2').setValue('');
	  //  Ext.getCmp('sectionTypeDD').setValue('H');  
	  //  Ext.getCmp('ruleDD').setValue(''); 

	}

function addSection() { 

	// validate that the Section Type is not of type Instruction or Paragraph
	var grid = Ext.getCmp('manageSectionsList');
	var record = grid.getSelectionModel().getSelected();
	
	if(record) {
		var activeCheck = record.get('active');
		if (activeCheck == "0") {
		    displayErrorMsg("Section cannot be added - must be undeleted before able to Add!" , 'errorMessageDiv');
		} else {
			var checkSectionType = record.get('sectionType');
			
			if (checkSectionType == "I" || checkSectionType == "P" || checkSectionType == "T") {
				displayErrorMsg('ADD is not allowed for Instructions, Paragraphs or Tables - Please select a Header or Subsection!' , 'errorMessageDiv');
			} else {
				// clears the error message from the panel
				displayErrorMsg('' , 'errorMessageDiv');
				sectionStatus = "ADD";
//				managesection_win_submit_url = "/TestSpring/view/managesections/addSection";
//				managesection_win_activity = "Add";	
//
//				showSectionWindow();
//			    Ext.getCmp('managesectionsWindow').setTitle('<center>Create Section</center>');
//			    managesection_win_title = "Create Section";
//			    Ext.getCmp('sectionId2').setValue(record.get('id'));
//			    Ext.getCmp('sectionText2').setValue('');
//			    Ext.getCmp('sectionTypeDD').setValue('');  
//			    Ext.getCmp('ruleDD').setValue(''); 
//			    Ext.getCmp('sectionNumber2').setValue(record.get('sectionNumber'));  
//			    Ext.getCmp('currSectionType2').setValue(record.get('sectionType'));
				
				
				section = null ;
				currentSectionId = record.get('id');
				var win = new Boeing.manageSectionWindow({});
				win.show();
			}
		}
	}

}







