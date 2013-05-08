Ext.ns('Boeing');
Boeing.SearchPanel = Ext.extend(Ext.Panel, {
	extend: 'Ext.panel.Panel',
	alias: 'widget.searchpanel',
	headerCfg:{
    	tag: 'div',
    	cls: 'x-panel-header epic-page-panel-header-text'
	},	
    initComponent:function() {
	    Ext.apply(this, {		
			width: panelWidth,
			height: panelHeight,
		//	title:"Search",
			defaults: {
		    	style: {
		        	padding: '0px'
		    	}
			},    		
			items:[{xtype: "navbar"} 
			, {xtype :'container' , height : '70px'}
			, {xtype : 'searchSubPanel' , width:'900px' , id:'searchSubFormPanel'}
			//,
			      //{ xtype : 'bmpanel2'}
			
			],
			listeners:{
				afterrender: function(panel){
					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				}
			}
	    });
	    Boeing.SearchPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('searchPanel', Boeing.SearchPanel);



Ext.ns('Boeing');
Boeing.SearchNavBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, {
	        height: 25,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'},{xtype: 'tbbutton', text: 'Search' , listeners:{
   				click:function(item, event){
       	  		//	alert ('search');
       	  		//document.location.href = '/TestSpring/view/search.jsp';
   					doSearch();
       	  		
				}
         	}
			}
			]
        });
        Boeing.SearchNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('searchNavbar', Boeing.SearchNavBar);


function doSearch () {
	
	
//	alert ( " doing search ");
		var form = Ext.getCmp('searchSubFormPanel').getForm();	
		
//		if(form.isValid()){
			
			form.submit({
								url : "/TestSpring/view/search/doSearch",
								method: 'POST',
								timeout: 240000,
								
								success: function(form, action){
							
							//		alert ("submitted successfully ");
									var data23 = action.result;
									
									
									document.location.href = '/TestSpring/view/search/searchResults.jsp';
									
								
								},
								failure: function ( result, request) {
									//	alert("eception happened");
										var data = Ext.decode(result.responseText);
										//alert(" could not validate user 123 ");
										//displayErrorMsgEpicService(data, data.data);			
									}
							});
			
			

//		}
		//else {
		//	Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed on the Buyer CheckList Form. See red highlighted fields for details.");
		//}
		
		
		
		
		
		
	}



Boeing.SearchSubPanel = Ext.extend(Ext.form.FormPanel, {
    initComponent:function() {
    	  Ext.apply(this, {		
  			width: '900px',
  			autoHeight: true, 
  		//	title:"Search",
  			tbar: {xtype: 'searchNavbar'   },
  			defaults: {
  		    	style: {
  		        	padding: '10px'
  		    	}
  			},    		
  			items:[  {xtype: 'fieldset', vertical: true,
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
  		    		       		items:[
  									]}]}, 
  				    		        {xtype: 'fieldset', vertical: true,
  						                layout: 'column',
  						                bodyStyle: 'padding-right:5px;',
  						                border: false,
  						                // defaults are applied to all child items unless otherwise specified by child item
  						                defaults: {
  						                    columnWidth: 0.5,
  						                    border: false
  						                },
  					    		       	items:[{xtype: 'fieldset', vertical: true,
  					    		       		items:[
  											 {xtype: 'textfield', 
  							    		       			
  								 		    		  	fieldLabel: 'SSOW Number',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'ssowNumber',
  								       					name: 'ssowNumber',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  //	value: getBclOwnerName(),
  								 		    		  	labelSeparator: '',
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       }, 
  								    	       {xtype: 'datefield', 
  							    		       			
  								 		    		  	fieldLabel: 'SSOW Date',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'ssowDate',
  								       					name: 'ssowDate',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  //	value: getBclOwnerName(),
  								 		    		  	labelSeparator: '',
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       },
  									    	     
  									    	       {xtype: 'textfield', 
  							    		       			
  								 		    		  	fieldLabel: 'Supplier',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'supplier',
  								       					name: 'supplier',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  //	value: getBclOwnerName(),
  								 		    		  	labelSeparator: '',
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       },{xtype: 'combo',
  									    	    	   
  								       		    	   fieldLabel: 'Status:',
  								    		    	   labelSeparator: '',
  								    		    	   width: 110,
  								    		    	   allowBlank: true,
  								    		    	 //  disabled:!bcl.editFlag,
  								    		    	   labelStyle: 'width:100px;font-weight: bold;color: #000000;',
  								    		    	   height: 30,
  								    		    	 //  onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
  													 //  onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);},				    		    	   		 		    		  	
  								    		    	   emptyText: 'Select One',
  								   					   id: 'statusDD',
  								   					   name: 'statusDD',		
  														listWidth : 200,
  														editable : false,
  														triggerAction : 'all',
  							        					valueField: 'description',
  							           					displayField:'description',
  							           					hiddenName : 'statusDDHidden',
  														//emptyText : 'Select One',
  														submitValue : true,
  								     					mode: 'local',   
  								     					//value : getBclManager(),
  								   					   store: new Ext.data.JsonStore({     
  								   						    autoLoad: true,
  															method: 'GET',
  															url: "/TestSpring/view/ssow/getStatuses",
  															prettyUrls: false,
  															timeout: 240000, 
  															root: 'data',        
  															headers: {
  															    'Accept': 'application/json'
  															},
  															setBaseParams: function(params){
  																this.baseParams = params;		
  															},
  															baseParams: {
  															},
  															fields: ['id', 'description'],
  															listeners:{		
  																load: function(proxy, object, options){	
  																
  																
  																},
  																exception: function(proxy, type, action, options, response, arg){
  																
  																}
  															}
  													}) 
  										    	       },
  									    	       {xtype: 'textfield', 
  							    		       			
  							 		    		  	fieldLabel: 'SRE',
  							 		    		  //	disabled: (bidScreen == "documentAssessment"),
  										 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  										       					id: 'sre',
  										       					name: 'sre',
  										       					width: 110,
  										       					maxLength: 100,
  										 		    		  //	value: getBclOwnerName(),
  										 		    		  	labelSeparator: '',
  										 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  											    	       }]}, 
  							    	       {xtype: 'fieldset', vertical: true,
  					    		       		items:[
  												   {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Procurement Specification Number',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'procNumber',
  									       					name: 'procNumber',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Lead Engineering Focal & Others',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'leadFocal',
  									       					name: 'leadFocal',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'IPT Team Lead',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'iptTeamLead',
  									       					name: 'iptTeamLead',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'IPT Supplier Manager',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'iptSupplierMgr',
  									       					name: 'iptSupplierMgr',
  									       					width: 110,
  									       					textAlign : 'left',
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Supplier Management Manager',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'supplierMgmtMgr',
  									       					name: 'supplierMgmtMgr',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Program Manager',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'programManager',
  									       					name: 'programManager',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  													 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  														    	       }, 
										    	     {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Procurement Agent',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'procurementAgent',
  									       					name: 'procurementAgent',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	//value: displayCreatedByDate(),
  									 		    		  	labelSeparator: '',
  													 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  														    	       }]}]}
		    		       	]}
  			//,
  			      //{ xtype : 'bmpanel2'}
  			
  			],
  			listeners:{
  				afterrender: function(panel){
  					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
  				}
  			}
  	    });
  	    Boeing.SearchSubPanel.superclass.initComponent.apply(this, arguments);
  	}
  });
  Ext.reg('searchSubPanel', Boeing.SearchSubPanel);




