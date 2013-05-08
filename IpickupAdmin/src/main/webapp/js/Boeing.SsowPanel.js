Ext.ns('Boeing');
Boeing.SsowPanel = Ext.extend(Ext.Panel, {
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
			, {xtype :'container' , height:'30' , id : 'errorMessageDiv' }
			, {xtype : 'ssowMetaDataPanel' , width:'800px' , hidden: !(ssowScreen == 'metaData') , id : 'ssowMetaDataPanel'}
			, {xtype : 'basicQPanel' , width:'800px' , hidden: !(ssowScreen=='basicQ') , id : 'ssowBasicQPanel' }//,
			,{xtype : 'advancedQPanel' , width:'800px' , hidden: !(ssowScreen=='advancedQ') , id :'advancedQPanel' }
			,{xtype : 'confirmationPanel' , width:'800px' , hidden: !(ssowScreen=='confirmation') , id :'confirmationPanel' }
			      
			
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
Ext.reg('ssowPanel', Boeing.SsowPanel);




function saveSsow () {
	
	

		var form = Ext.getCmp('ssowMetaDataPanel').getForm();	
		
		if(form.isValid()){
			
			form.submit({
								url : "/TestSpring/view/ssow/saveSsow",
								method: 'POST',
								timeout: 240000,
								
								success: function(form, action){

									var data23 = action.result;
										var msg = data23.msg ;
									
										displayErrorMsg(msg , 'errorMessageDiv');
									action = 'edit';

								},
								failure: function ( result, request) {

									}
							});

		}
		else {
			displayErrorMsg("" , 'errorMessageDiv');
			Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed on the Ssow MetaData Form. See red highlighted fields for details.");
		}
		
		
		
		
		
		
	}


function saveSsowContinue () {
	

		var form = Ext.getCmp('ssowMetaDataPanel').getForm();	
		
		if(form.isValid()){
			
			form.submit({
								url : "/TestSpring/view/ssow/saveSsow",
								method: 'POST',
								timeout: 240000,
								
								success: function(form, action){
							
							
									var data23 = action.result;
									
							
									var success = data23.success;
								
									var msg = data23.msg ;
								
									displayErrorMsg(msg , 'errorMessageDiv');
									if ( success == 'false') {
										// dont do anything 
									}else {
										action = 'edit';
										basicQGoTo();
									}
							
									
								
								},
								failure: function ( result, request) {
									
										var data = Ext.decode(result.responseText);
											
									}
							});
			
			

		}
		else {
			displayErrorMsg("" , 'errorMessageDiv');
			Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed on the Ssow MetaData Form. See red highlighted fields for details.");
		}
		
		
		
		
		
		
	}

function saveBasicQ () {
	
	

		var form = Ext.getCmp('ssowBasicQPanel').getForm();	
		

			
			form.submit({
								url : "/TestSpring/view/ssow/saveBasicQ",
								method: 'POST',
								timeout: 240000,
								
								success: function(form, action){
							
							
									var data23 = action.result;
									
								
									
										var msg = data23.msg ;
									
										displayErrorMsg(msg , 'errorMessageDiv');
									
									
								
									
								
								},
								failure: function ( result, request) {
								
										var data = Ext.decode(result.responseText);
										
									}
							});				
	}



function saveAdvancedQ () {
	
	

		var form = Ext.getCmp('advancedQPanel').getForm();	
		
//		if(form.isValid()){
			
			form.submit({
								url : "/TestSpring/view/ssow/saveAdvancedQ",
								method: 'POST',
								timeout: 240000,
								
								success: function(form, action){
							
							
									var data23 = action.result;
									
								
									
										var msg = data23.msg ;
										displayErrorMsg(msg , 'errorMessageDiv');
									
									
								
									
								
								},
								failure: function ( result, request) {
								
										var data = Ext.decode(result.responseText);
												
									}
							});
			
			

//		}
		//else {
		//	Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed on the Buyer CheckList Form. See red highlighted fields for details.");
		//}
		
		
		
		
		
		
	}



function completeSsow() {
	
	
	
	Ext.Ajax.request({
		url :   "/TestSpring/view/ssow/completeSsow",
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
	
			displayErrorMsg(data.msg , 'errorMessageDiv');
			
			document.location.href = '/TestSpring/view/ssowQueue.jsp?queueType=User';
		
					
		},		failure: function ( result, request) {
			//	alert("eception happened");
			var data = Ext.decode(result.responseText);
			displayErrorMsgEpicService(data, data.data);			
		}
	});
	
	
	
	
}


function generateSsow() {
	
	var url =  "/TestSpring/view/ssow/generateSsow" + "?ssowId=" + ssowId;
	
	window.open( url , "docwindow", "scrollbars=yes,width=100,height=100,left=10,top=10,resizable=yes,location=no");

}


function moveBack() {
	
	if ( ssowScreen == 'basicQ') {
		// go to metadata screen 
		metaDataGoTo();
		
	}else if ( ssowScreen == 'advancedQ') {
		// go to basicQ
		basicQGoTo();
	}else if ( ssowScreen == 'confirmation') {
		// go to advancedQ
		advancedQGoTo();
	}
	
	
}




function continueForward() {
	if ( ssowScreen == 'basicQ') {
		// go to metadata screen 
		saveBasicQ();
		advancedQGoTo();
		
	}else if ( ssowScreen == 'advancedQ') {
		// go to basicQ
		saveAdvancedQ();
		confirmationGoTo();
	}else if ( ssowScreen == 'metaData') {
		// go to advancedQ
		saveSsowContinue();
		
	}
	
}









Ext.ns('Boeing');
Boeing.SsowNavBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbtext', text: '', id: 'breadCrumbsText'},{xtype: 'tbfill'},
			     //  {xtype: 'tbseparator'},
			       {xtype: 'tbbutton', text: 'Back' , listeners:{
   				click:function(item, event){
       	  		
   					
       	  		moveBack();
				}
         	}
			}, {xtype: 'tbseparator' , 
				hidden: !(ssowScreen == 'metaData')
				},
		       {xtype: 'tbbutton', text: 'Save' , hidden: !(ssowScreen == 'metaData'),
					listeners:{
	   				click:function(item, event){
	       	  	
	       	  		saveSsow();
					}
	         	}
				}, 
				
				{xtype: 'tbseparator' ,  hidden: !(ssowScreen=='basicQ')},
			       {xtype: 'tbbutton', text: 'Save' ,  hidden: !(ssowScreen=='basicQ') , listeners:{
		   				click:function(item, event){
		       	  	
		       	  		saveBasicQ();
						}
		         	}
					},
					
					{xtype: 'tbseparator' , hidden: !(ssowScreen=='advancedQ') },
				       {xtype: 'tbbutton', text: 'Save' ,  hidden: !(ssowScreen=='advancedQ') ,  listeners:{
			   				click:function(item, event){
			       	  	
			       	  		saveAdvancedQ();
							}
			         	}
						},
						
				 {xtype: 'tbseparator'},
			       {xtype: 'tbbutton', text: 'Generate' , listeners:{
 				click:function(item, event){
     	  	
     	  		generateSsow();
     	  		
				}
       	}
			},
			 {xtype: 'tbseparator' ,  hidden: (ssowScreen=='confirmation')},
		       {xtype: 'tbbutton', hidden: (ssowScreen=='confirmation') , text: 'Continue' , listeners:{
				click:function(item, event){
 	  	
 	  		
					continueForward();
			}
   	}
		}
			]
        });
        Boeing.SsowNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('ssowNavbar', Boeing.SsowNavBar);

Boeing.ssowMetaDataPanel = Ext.extend(Ext.form.FormPanel, {
    initComponent:function() {
    	  Ext.apply(this, {		
  			width: '900px',
  			autoHeight: true, 
  		//	title:"Search",
  			tbar: {xtype: 'ssowNavbar'   },
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
  					    		       		items:[{xtype: 'hidden',
  											  	id: 'ssowId',
  											  	name: 'ssowId',
  											  	value: ssow.id
  												} ,
  											 {xtype: 'textfield', 
  							    		       			
  								 		    		  	fieldLabel: 'SSOW Number',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'ssowNumberC',
  								       					name: 'ssowNumberC',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  	value:  getSsowNumber(),
  								 		    		  	labelSeparator: '',
  								 		    		  	allowBlank: false,
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       }, 
  								    	       {xtype: 'displayfield', 
  							    		       			
  								 		    		  	fieldLabel: 'SSOW Rev#',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'ssowRevC',
  								       					name: 'ssowRevC',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  	value: getRevNbr(),
  								 		    		  	textAlign:'left',
  								 		    		  	labelSeparator: '',
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       },
  									    	       
  									    	       {xtype: 'textfield', 
  							    		       			
  								 		    		  	fieldLabel: 'Supplier',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'supplierC',
  								       					name: 'supplierC',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  	value: getSupplier(),
  								 		    		  	labelSeparator: '',
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       }, {xtype: 'displayfield', 
  							    		       			
  								 		    		  	fieldLabel: 'Status',
  								 		    		  //	disabled: (bidScreen == "documentAssessment"),
  								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  								       					id: 'statusC',
  								       					name: 'statusC',
  								       					width: 110,
  								       					maxLength: 100,
  								 		    		  	value: getStatus(),
  								 		    		  	labelSeparator: '',
  								 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  									    	       },
  									    	       {xtype: 'textfield', 
  							    		       			
  							 		    		  	fieldLabel: 'SRE',
  							 		    		  //	disabled: (bidScreen == "documentAssessment"),
  										 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  										       					id: 'sreC',
  										       					name: 'sreC',
  										       					width: 110,
  										       					maxLength: 100,
  										 		    		  	value: getSre(),
  										 		    		  	labelSeparator: '',
  										 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  											    	       }]}, 
  							    	       {xtype: 'fieldset', vertical: true,
  					    		       		items:[
  												   {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Procurement Specification Number',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'procNumberC',
  									       					name: 'procNumberC',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	value: getProcNumber(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Lead Engineering Focal & Others',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'leadFocalC',
  									       					name: 'leadFocalC',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	value: getLeadFocal(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'IPT Team Lead',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'iptTeamLeadC',
  									       					name: 'iptTeamLeadC',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	value: getIptTeamLead(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'IPT Supplier Manager',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'iptSupplierMgrC',
  									       					name: 'iptSupplierMgrC',
  									       					width: 110,
  									       					textAlign : 'left',
  									       					maxLength: 100,
  									 		    		  	value: getIptSupplierMgr(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Supplier Management Manager',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'supplierMgmtMgrC',
  									       					name: 'supplierMgmtMgrC',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	value: getSupplierMgmtMgr(),
  									 		    		  	labelSeparator: '',
  									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  										    	       }, 
  										    	       {xtype: 'textfield', 
  								    		       			
  									 		    		  	fieldLabel: 'Program Manager',
  									 		    		  //	disabled: (bidScreen == "documentAssessment"),
  									 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
  									       					id: 'programManagerC',
  									       					name: 'programManagerC',
  									       					width: 110,
  									       					maxLength: 100,
  									 		    		  	value: getProgramMgr(),
  									 		    		  	labelSeparator: '',
  													 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
  														    	       }, 
										    	     {xtype: 'textfield', 
  								    		       			
								 		    		  	fieldLabel: 'Procurement Agent',
								 		    		  //	disabled: (bidScreen == "documentAssessment"),
								 		    		  //	allowBlank: (bidScreen == "documentAssessment"),
								       					id: 'procurementAgentC',
								       					name: 'procurementAgentC',
								       					width: 110,
								       					maxLength: 100,
								 		    		  	value: getProcurementAgent(),
								 		    		  	labelSeparator: '',
									 		    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
										    	       }]}]}
		    		       	]},
		    		       	
		    		        {xtype: 'fieldset', vertical: true,
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
													 {xtype : 'textarea',						    	
										     					
																fieldLabel: 'Ssow Description',
											    		    	//labelSeparator: '',	
																id: 'ssowDescription',
																name: 'ssowDescription',
																//onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);}, 
															//	onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);}, 
																width : 600,
																blankText:'If P is checked, comment must be input',
																value: getSsowDescription(),
																//hidden: (bidScreen != "bidTemplate"),
																height : 70,
												    		  	labelStyle: 'width:170px;font-weight: bold;color: #000000;',
												    	    	   listeners:{
									   				   				change: function(f, newValue, oldValue){
										       						//	bclRulesEngineFieldChanged = true;
										       							
										       			   			}
													       	   	}
										    		       	}]}]}, 
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
																		 {xtype: 'displayfield', 
  		  							    		       			
  			  							 		    		  	fieldLabel: 'CreatedBy:',
  			  							 		    		
  			  										       					id: 'createdBy',
  			  										       					name: 'createdBy',
  			  										       					width: 110,
  			  										       					maxLength: 100,
  			  										 		    		  	value: getCreatedBy(),
  			  										 		    		  	labelSeparator: '',
  			  										 		    		  	labelStyle: 'width:100px;font-weight: bold;color: #000000;'
  			  											    	       }
  											    	     ]}, 
														 {xtype: 'fieldset', vertical: true,
															   items:[  {xtype: 'displayfield', 
			
														  	fieldLabel: 'CreatedDate:',
																		id: 'createdDate',
													  					name: 'createdDate',
													  					width: 110,
													  					maxLength: 100,
														    		  	value: getCreatedDate(),
														    		  	labelSeparator: '',
														    		  	labelStyle: 'width:250px;font-weight: bold;color: #000000;'
													   	       } ]}]}
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
  Ext.reg('ssowMetaDataPanel', Boeing.ssowMetaDataPanel);
  	  

	  Boeing.BasicQGridPanel = Ext.extend(Boeing.EditorGridPanel, {
	  	listeners:{
	  				
	  	},
	      initComponent:function() {
	      Ext.apply(this, {    	
	      		
	      		width: 900,  
	      		height: 570,
	      		collapsible: false,
	      		titleCollapse: false,
	      		headerAsText: false,
	      		clicksToEdit: 1,
	      		stripeRows : true,
	      		store : ssowBasicQStore,
	      	//	tbar: {xtype: 'ssowNavbar'   },
	      		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
	      	
	      		listeners: {
	  				
	  			    beforerender: function(){	
	  			 //  alert(" am i getting here ");
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
	  			
	  				dataIndex : 'itemNumber',
	  				header : 'Item Number',
	  				sortable : true,
	  				width : 120,
	  				editable : false,
	  				hideable : false,
	  				renderer : function(data, cell, record, rowIndex, columnIndex, store) {
	  					var link = "";
	  					var qtip = "";
	  					var qtitle = "";
	  					//if ( accessRules.userRole.administrator || accessRules.userRole.systemsIntegrity && record.data.compliance == '' || complianceQueue == true){
	  					//	return data;
	  					//}else {
//	  						var link = "<a hidefocus=\"on\" class=\"epicLink\" href=\"javascript://void()\" ";
//	  							var onClick = "";				
//	  							onClick += "editBcl('"+record.data.bclId+"' ,'"+record.data.id+"' );";
//	  							link +=" onClick=\""+onClick+"\">";
//	  							link += data;
//	  							link += "</a>"; 
//	  							this.attr = 'ext:qtitle="View" ext:qtip="'+data+'"';
//	  							return "<span style=\"white-space:nowrap\"\>"+link+"</span>"; 
	  					//}
	  					
	  					return data;
	  		    	}
	  			
	  			},{
	  				dataIndex : 'question',
	  				header : 'SSOW Question',
	  				sortable : true,
	  				width : 600,
	  				editable : false,
	  				hideable : false,
	  				renderer: function(data, cell, record, 
					         rowIndex, columnIndex, store) {
	  					//var activeTest = record.get('active');
	  					//var sectTypeTest = record.get('sectionType');
	  					return columnWrap(data);
			}
	  				
	  			},
	  			
	  			
	  			
	  			
	  			
	  					
	  					
	  			       
	  			
	  		    {
	  					dataIndex : 'questionType',
	  					header : 'Response',
	  					sortable : true,
	  					width : 140,
	  					editable : false,
	  					hideable : false,
	  					renderer : function(data, cell, record,
								rowIndex, columnIndex, store) {
						//alert ("Data " + data);
						var returnString = "";
						if ( data == "NONE") {
							returnString =  "";
						}
							else if ( data == "YN") {
								if ( record.data.value == 'Y') {
										returnString +=   "<input  type=\"radio\" value=\"Y\"    name=\"ssow_" + record.data.id +"\" checked > Yes </input> ";
								}else {
										returnString +=   "<input  type=\"radio\" value=\"Y\"    name=\"ssow_" + record.data.id +"\" > Yes </input> " ;
								}
								
								if ( record.data.value == 'N') {
									returnString += "<input  type=\"radio\" value=\"N\"    name=\"ssow_" + record.data.id +"\" checked > No </input>";
								}else {
									returnString += "<input  type=\"radio\" value=\"N\"     name=\"ssow_" + record.data.id +"\"  > No </input>";
								}
									
								if ( record.data.value != 'Y'  && record.data.value != 'N')  {
									returnString +=   "<input  type=\"radio\" value=\"NA\"    name=\"ssow_" + record.data.id +"\" checked > NA </input> ";
								}else {
									returnString +=   "<input  type=\"radio\" value=\"NA\"    name=\"ssow_" + record.data.id +"\" > NA </input> " ;
								}
									}
							else if ( data == "DD") {
							
								//alert ( "data value " + record.data.choices.length);
								returnString = "<select  class=\"x-form-text x-form-field x-form-empty-field x-form-trigger x-form-arrow-trigger\" name=\"ssow_" + record.data.id +"\"   style='width: 112px' >";
								returnString += "<option value=\""
								+ ""
								+ "\" >"
								+ ""
								+ "</option>";
								
							for ( var i = 0; i < record.data.choices.length; i++) {
								
							//	alert ( "data value " + record.data.choices.length);
								//alert (" choice id " + record.data.choices[i].id);
								if ( record.data.value == record.data.choices[i].value) {
										returnString += "<option selected=\"" + "selected\"" + " value=\""
										+ record.data.choices[i].value
										+ "\" >"
										+ record.data.choices[i].value
										+ "</option>";
								}else {
									returnString += "<option  value=\""
										+ record.data.choices[i].value
										+ "\" >"
										+ record.data.choices[i].value
										+ "</option>";
								}
										
								
						}
							returnString += "</select>";
							
							//alert ( "return string " + returnString);
					
							}
							else {
								returnString =  data;
							}
							
						return returnString;
						}
	  					
	  				}
	  				
	  		    	
	  				
	  			
	  			
	  			]    		    			
	      	});
	      Boeing.BasicQGridPanel.superclass.initComponent.apply(this, arguments);
	  	} // eo function initComponent
	  });  // eo Epic.SourceSelectionPanel
	  Ext.reg('basicQGridPanel', Boeing.BasicQGridPanel);

	  
	  function columnWrap(val) {
			return '<div style="white-space:normal !important;">' + val + '</div>';
		}
	  
	  Boeing.AdvancedQGridPanel = Ext.extend(Boeing.EditorGridPanel, {
		  	listeners:{
		  				
		  	},
		      initComponent:function() {
		      Ext.apply(this, {    	
		      		
		      		width: 900,  
		      		height: 580,
		      		collapsible: false,
		      		titleCollapse: false,
		      		headerAsText: false,
		      		clicksToEdit: 1,
		      		stripeRows : true,
		      		store : ssowAdvancedQStore,
		      	//	tbar: {xtype: 'ssowNavbar'   },
		      		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
		      	
		      		listeners: {
		  				
		  			    beforerender: function(){	
		  			 //  alert(" am i getting here ");
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
		  			
		  				dataIndex : 'itemNumber',
		  				header : 'Item Number',
		  				sortable : true,
		  				width : 120,
		  				editable : false,
		  				hideable : false,
		  				renderer : function(data, cell, record, rowIndex, columnIndex, store) {
		  					var link = "";
		  					var qtip = "";
		  					var qtitle = "";
		  					//if ( accessRules.userRole.administrator || accessRules.userRole.systemsIntegrity && record.data.compliance == '' || complianceQueue == true){
		  					//	return data;
		  					//}else {
//		  						var link = "<a hidefocus=\"on\" class=\"epicLink\" href=\"javascript://void()\" ";
//		  							var onClick = "";				
//		  							onClick += "editBcl('"+record.data.bclId+"' ,'"+record.data.id+"' );";
//		  							link +=" onClick=\""+onClick+"\">";
//		  							link += data;
//		  							link += "</a>"; 
//		  							this.attr = 'ext:qtitle="View" ext:qtip="'+data+'"';
//		  							return "<span style=\"white-space:nowrap\"\>"+link+"</span>"; 
		  					//}
		  					
		  					return data;
		  		    	}
		  			
		  			},{
		  				dataIndex : 'question',
		  				header : 'SSOW Question',
		  				sortable : true,
		  				width : 600,
		  				editable : false,
		  				hideable : false,
		  				renderer: function(data, cell, record, 
						         rowIndex, columnIndex, store) {
		  					//var activeTest = record.get('active');
		  					//var sectTypeTest = record.get('sectionType');
		  					return columnWrap(data);
		  				}
		  			},
		  			
		  			
		  			
		  			
		  			
		  					
		  					
		  			       
		  			
		  		    {
		  					dataIndex : 'questionType',
		  					header : 'Response',
		  					sortable : true,
		  					width : 140,
		  					editable : false,
		  					hideable : false,
		  					renderer : function(data, cell, record,
									rowIndex, columnIndex, store) {
						
							var returnString = "";
							if ( data == "NONE") {
								returnString =  "";
							}
								else if ( data == "YN") {
									
									//if ( record.data.checkListGroup != null && record.data.checkListGroup.id != null &&
										///	record.data.checkListGroup.id != "") {
										
										
										//alert ("this question has  a group " + record.data.checkListGroup.id);
										
										
										///if ( record.data.value == 'Y') {
										//	returnString +=   "<input  type=\"radio\" value=\"Y\"    name=\"ssow_grp" + record.data.checkListGroup.id +"\" checked > Yes </input> ";
										//}else {
										//returnString +=   "<input  type=\"radio\" value=\"Y\"    name=\"ssow_grp" + record.data.checkListGroup.id +"\" > Yes </input> " ;
										//}
									
										//if ( record.data.value == 'N') {
										//returnString += "<input  type=\"radio\" value=\"N\"    name=\"ssow_grp" + record.data.checkListGroup.id +"\" checked > No </input>";
										//}else {
										//returnString += "<input  type=\"radio\" value=\"N\"     name=\"ssow_grp" + record.data.checkListGroup.id +"\"  > No </input>";
										//}
										
										
									//}else {
									if ( record.data.value == 'Y') {
											returnString +=   "<input  type=\"radio\" value=\"Y\"    name=\"ssow_" + record.data.id +"\" checked > Yes </input> ";
									}else {
										returnString +=   "<input  type=\"radio\" value=\"Y\"    name=\"ssow_" + record.data.id +"\" > Yes </input> " ;
									}
									
									if ( record.data.value == 'N') {
										returnString += "<input  type=\"radio\" value=\"N\"    name=\"ssow_" + record.data.id +"\" checked > No </input>";
									}else {
										returnString += "<input  type=\"radio\" value=\"N\"     name=\"ssow_" + record.data.id +"\"  > No </input>";
									}
									
									if ( record.data.value != 'Y'  && record.data.value != 'N')  {
										returnString +=   "<input  type=\"radio\" value=\"NA\"    name=\"ssow_" + record.data.id +"\" checked > NA </input> ";
									}else {
										returnString +=   "<input  type=\"radio\" value=\"NA\"    name=\"ssow_" + record.data.id +"\" > NA </input> " ;
									}
									//}
											
										}
								else if ( data == "DD") {
								
									//alert ( "data value " + record.data.choices.length);
									returnString = "<select  class=\"x-form-text x-form-field x-form-empty-field x-form-trigger x-form-arrow-trigger\" name=\"ssow_" + record.data.id +"\"   style='width: 112px' >";
									returnString += "<option value=\""
									+ ""
									+ "\" >"
									+ ""
									+ "</option>";
									
								for ( var i = 0; i < record.data.choices.length; i++) {
									
								
									if ( record.data.value == record.data.choices[i].value) {
											returnString += "<option selected=\"" + "selected\"" + " value=\""
											+ record.data.choices[i].value
											+ "\" >"
											+ record.data.choices[i].value
											+ "</option>";
									}else {
										returnString += "<option  value=\""
											+ record.data.choices[i].value
											+ "\" >"
											+ record.data.choices[i].value
											+ "</option>";
									}
											
									
								}
								returnString += "</select>";
								
								
						
								}
								else {
									returnString =  data;
								}
								
							return returnString;
							}
		  					
		  				}
		  				
		  		    	
		  				
		  			
		  			
		  			]    		    			
		      	});
		      Boeing.AdvancedQGridPanel.superclass.initComponent.apply(this, arguments);
		  	} // eo function initComponent
		  });  // eo Epic.SourceSelectionPanel
		  Ext.reg('advancedQGridPanel', Boeing.AdvancedQGridPanel);
	  
	  var ssowBasicQStore = new Ext.data.JsonStore( {
			url : "/TestSpring/view/ssow/getBasicQList" + "?ssowIdNew=" + ssowId,
			
			root : 'data',
			timeout: 240000,
			autoLoad : false,
			baseParams: {
				
				ssowId : ssowId
				
			},

			fields : [ {name:'id'},{
				name : 'itemNumber'
				
			}, {
				name : 'question'
				
			}, {
				name : 'questionType'
				
			}	, { name: 'choices'}		, {
				name:'value'
			}						
			]	
		});
	  
	  
	  
	  
	  var ssowAdvancedQStore = new Ext.data.JsonStore( {
			url : "/TestSpring/view/ssow/getAdvancedQList" + "?ssowIdNew=" + ssowId,
			
			root : 'data',
			timeout: 240000,
			autoLoad : false,
			baseParams: {
				
				ssowId : ssowId
				
			},

			fields : [ {name:'id'},{
				name : 'itemNumber'
				
			}, {
				name : 'question'
				
			}, {
				name : 'questionType'
				
			}	, { name: 'choices'}	, {name: 'checkListGroup'}		, {name: 'value'}					
			]	
		});
	  
	  Boeing.BasicQPanel = Ext.extend(Ext.form.FormPanel, {
		    initComponent:function() {
		    	  Ext.apply(this, {		
		  			width: '900px',
		  			autoHeight: true, 
		  		//	title:"Search",
		  			tbar: {xtype: 'ssowNavbar'   },
		  			defaults: {
		  		    	style: {
		  		        	//padding: '2px'
		  		    	}
		  			},    		
		  			items:[
		  			       {xtype: 'hidden',
						  	id: 'ssowId',
							  	name: 'ssowId',
							  	value: ssow.id
								}, 
								{xtype : 'basicQGridPanel'}
		  			
		  			],
		  			listeners:{
		  				afterrender: function(panel){
		  					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
		  				}
		  			}
		  	    });
		  	    Boeing.BasicQPanel.superclass.initComponent.apply(this, arguments);
		  	}
		  });
		  Ext.reg('basicQPanel', Boeing.BasicQPanel);
		  

		  Boeing.AdvancedQPanel = Ext.extend(Ext.form.FormPanel, {
			    initComponent:function() {
			    	  Ext.apply(this, {		
			  			width: '900px',
			  			autoHeight: true, 
			  		//	title:"Search",
			  			tbar: {xtype: 'ssowNavbar'   },
			  			defaults: {
			  		    	style: {
			  		        //	padding: '10px'
			  		    	}
			  			},    		
			  			items:[
			  			       {xtype: 'hidden',
							  	id: 'ssowId',
								  	name: 'ssowId',
								  	value: ssow.id
									}, 
									{xtype : 'advancedQGridPanel'}
			  			
			  			],
			  			listeners:{
			  				afterrender: function(panel){
			  					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
			  				}
			  			}
			  	    });
			  	    Boeing.AdvancedQPanel.superclass.initComponent.apply(this, arguments);
			  	}
			  });
			  Ext.reg('advancedQPanel', Boeing.AdvancedQPanel);
			
		  
		  
			  Boeing.ConfirmationPanel = Ext.extend(Ext.form.FormPanel, {
				    initComponent:function() {
				    	  Ext.apply(this, {		
				  			width: '900px',
				  			autoHeight: true, 
				  		//	title:"Search",
				  			tbar: {xtype: 'ssowNavbar'   },
				  			defaults: {
				  		    	style: {
				  		        	padding: '10px'
				  		    	}
				  			},    		
				  			items:[
				  			       {xtype: 'hidden',
								  	id: 'ssowId',
									  	name: 'ssowId',
									  	value: ssow.id
										}, 
										{xtype : 'confirmationFormPanel'}
				  			
				  			],
				  			listeners:{
				  				afterrender: function(panel){
				  					//panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				  				}
				  			}
				  	    });
				  	    Boeing.ConfirmationPanel.superclass.initComponent.apply(this, arguments);
				  	}
				  });
				  Ext.reg('confirmationPanel', Boeing.ConfirmationPanel);
				
				  Boeing.confirmationFormPanel = Ext.extend(Ext.form.FormPanel, {
						headerCfg:{
					    	tag: 'div',
					    	cls: 'x-panel-header epic-page-panel-header-text'
						},
					    initComponent:function() {
					    Ext.apply(this, {   		
					    		width:400,  
					    		autoHeight: true,
					    		trackResetOnLoad: true,
								method: 'POST',
								id: 'loginForm',
					    		defaults: {
					       			// implicitly create Container by specifying xtype
					        		xtype: 'container',
					        		autoEl: 'div', // This is the default.
					        		layout: 'form',
					    			waitTitle: '',
					    			padding: '10px',
					        		standardSubmit: true,
					        		style: {
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
											                    columnWidth: 1.0,
											                    border: false
											                },
										    		       	items:[{xtype: 'fieldset', vertical: true,
										    		       		items:[
										    		       		    { xtype :'displayfield', 
										    		        			fieldLabel:'Do you want to mark this SSOW as Complete?' // we can create a component  
										    		                     // with a configuration  
										    		                   // value:'username', //object  
										    		        				,  labelStyle: 'width:300px;font-weight: bold;color: #000000;'
										    		                    
										    		        			}
										    		        			,  {xtype: 'container', height: '20'},   
										    		        			{xtype: 'tbbutton', text: 'Complete', 
										    		            				listeners:{
										    		    	           				click:function(item, event){
										    		    	               	  		//	alert ('Login User');
										    		    	               	  			completeSsow();
										    		    							}
										    		    	                 	}
										    		            			}
																 ]}, 
												    	       {xtype: 'fieldset', vertical: true,
										    		       		items:[
																	   
															    	    ]}]}
							    		       	]}
								
								
								],

					 		      listeners:{
					 					afterrender: function(panel){
					 						panel.setTitle('<div style="font-size:medium;">'+'Confirmation Panel'+'</div>');				
					 					}
					 				} 
					    		});
					    Boeing.confirmationFormPanel.superclass.initComponent.apply(this, arguments);
						} // eo function initComponent
					});  // eo Epic.buyerCheckListFormPanel
					Ext.reg('confirmationFormPanel', Boeing.confirmationFormPanel);

		  function getSsowId() {
			  
			  if ( ssow != null ) {
				  return ssow.id;
			  }
			  return "";
		  }

		  function getSsowNumber() {

		  	if ( ssow != null ) {
		  		return ssow.ssowNumber;
		  	}
		  	return "";
		  }


		  function getProgram() {

		  	if ( ssow != null ) {
		  		return ssow.program;
		  	}
		  	return "";
		  }



		  function getSupplier() {

		  	if ( ssow != null ) {
		  		return ssow.supplier;
		  	}
		  	return "";
		  }



		  function getStatus() {

		  	if ( ssow != null ) {
		  		return ssow.status;
		  	}
		  	return "";
		  }



		  function getSre() {

		  	if ( ssow != null ) {
		  		return ssow.sre;
		  	}
		  	return "";
		  }


		  function getProcNumber() {

		  	if ( ssow != null ) {
		  		return ssow.procSpecNumber;
		  	}
		  	return "";
		  }



		  function getLeadFocal() {

		  	if ( ssow != null ) {
		  		return ssow.leadEngFocal;
		  	}
		  	return "";
		  }



		  function getIptTeamLead() {

		  	if ( ssow != null ) {
		  		return ssow.iptTeamLead;
		  	}
		  	return "";
		  }



		  function getIptSupplierMgr() {

		  	if ( ssow != null ) {
		  		return ssow.iptSupplierMgr;
		  	}
		  	return "";
		  }


		  function getSupplierMgmtMgr() {

		  	if ( ssow != null ) {
		  		return ssow.supplierMgmtMgr;
		  	}
		  	return "";
		  }



		  function getProgramMgr() {

		  	if ( ssow != null ) {
		  		return ssow.programMgr;
		  	}
		  	return "";
		  }


		  function getProcurementAgent() {

		  	if ( ssow != null ) {
		  		return ssow.procurementAgent;
		  	}
		  	return "";
		  }
		  
		  
		  function getRevNbr() {

			  	if ( ssow != null ) {
			  		return ssow.revNbr;
			  	}
			  	return "";
			  }
		  
		  

		  function getCreatedBy() {

			  	if ( ssow != null ) {
			  		return ssow.createdBy;
			  	}
			  	return "";
			  }
		  

		  function getCreatedDate() {

			  	if ( ssow != null ) {
			  		return ssow.createdDateString;
			  	}
			  	return "";
			  }
		  
		  function getSsowDescription() {

			  	if ( ssow != null ) {
			  		return ssow.description;
			  	}
			  	return "";
			  } 
		