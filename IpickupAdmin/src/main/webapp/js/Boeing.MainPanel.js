Ext.ns('Boeing');
Boeing.MainPanel = Ext.extend(Ext.Panel, {
	extend: 'Ext.panel.Panel',
	alias: 'widget.bmpanel',
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
			items:[
			       
			     //  {xtype: "navbar"} 
			//, 
			{xtype :'container' , height:'30'  }
		
			,
			      { xtype : 'infopanel'} , 
			       {xtype :'container' , height:'30' , id : 'errorMessageDiv' },
			      {xtype : 'loginFormPanel' , id :'loginUserForm'}
	
			],
			listeners:{
				afterrender: function(panel){
					panel.setTitle('<div style="font-size:medium;">'+panel.title+'</div>');				
				}
			}
	    });
	    Boeing.MainPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('bmpanel', Boeing.MainPanel);


Boeing.InfoPanel = Ext.extend(Ext.form.FormPanel, {
	
	headerCfg:{
    	tag: 'div',
    	cls: 'x-panel-header epic-page-panel-header-text'
	},	
    initComponent:function() {
	    Ext.apply(this, {		
			width: '750px',
			autoHeight: true, 
			defaults: {
		    	style: {
		        	padding: '20px'
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
					    		       		items:[{xtype: 'container', height: '30'},
					    		       		     //  {xtype :'displayfield' , 
					    		       			//fieldLabel: 'You are logged in as an administrator.' , 
					    		       			//labelStyle: 'width:700px;font-weight: bold;color: #000000;'},
					    			              //  {xtype :'displayfield' , value: ''}, 
					    			                {xtype :'displayfield' , 
					    			                	fieldLabel: 'Only users authorized by Boeing may utilize or access this electronic system or database for approved purposes.'
					    			                		, labelStyle: 'width:700px;font-weight: bold;color: #000000;'},
					    						       {xtype :'displayfield' , value: ''},
					    						       {xtype :'displayfield' , 
					    						    	   fieldLabel: 'Any unauthorized use, inappropriate use, or disclosure of this system or any information contained therein is prohibited and may result in revocation of access, disciplinary action (up to and including termination) and/or legal action.' , 
					    						    	   labelStyle: 'width:700px;font-weight: bold;color: #000000;'},
					    						       {xtype :'displayfield' , value: ''}, 
					    						       {xtype :'displayfield' , fieldLabel: 'Boeing retains the right to monitor, record, distribute, or review any user’s electronic activity, files data, or messages. Any evidence of illegal or actionable activity may be disclosed to law enforcement officials or used by Boeing in any legal action against the offending user.', 
					    						    	   labelStyle: 'width:700px;font-weight: bold;color: #000000;'},
					    						       {xtype :'displayfield' , value: ''}, 
					    						       {xtype :'displayfield' , fieldLabel: 'Reminder: Non-public information found in or created on this system is the proprietary, confidential and/or trade secret information of Boeing or others entrusting their information to Boeing. In addition, information transmitted to a foreign person through the use of this system may be subject to U.S. Export Control laws. If appropriate, contact your Export Coordinator the Law Department for assistance.' , 
					    						    	   labelStyle: 'width:700px;font-weight: bold;color: #000000;'}
					    						       ,{xtype :'displayfield' , value: ''},
					    						      
					    		       		   
					    		        			  {xtype: 'container', height: '10'}
											 ]}, 
							    	       {xtype: 'fieldset', vertical: true,
					    		       		items:[
												   
										    	    ]}]}
		    		       	]}
			//,
			      //{ xtype : 'bmpanel2'}
			
			],
			
//			items:[{
//		        // Fieldset in Column 1
//		        xtype:'fieldset',
//		        columnWidth: 1.0,
//		       // title: 'Fieldset 1',
//		     //   collapsible: true,
//		        autoHeight:true,
//		        defaults: {
//		          //  anchor: '-20' // leave room for error icon
//		        },
//		       // defaultType: 'textfield',
//		        
//		        
//		        items :[{xtype: 'container', height: '30'},{xtype :'displayfield' , value: 'You are logged in as an administrator.' , textAlign:'left'},
//		                {xtype :'displayfield' , value: ''}, {xtype :'displayfield' , value: 'Only users authorized by Boeing may utilize or access this electronic system or database for approved purposes.'},
//				       {xtype :'displayfield' , value: ''},
//				       {xtype :'displayfield' , value: 'Any unauthorized use, inappropriate use, or disclosure of this system or any information contained therein is prohibited and may result in revocation of access, disciplinary action (up to and including termination) and/or legal action.'},
//				       {xtype :'displayfield' , value: ''}, {xtype :'displayfield' , value: 'Boeing retains the right to monitor, record, distribute, or review any user’s electronic activity, files data, or messages. Any evidence of illegal or actionable activity may be disclosed to law enforcement officials or used by Boeing in any legal action against the offending user.'},
//				       {xtype :'displayfield' , value: ''}, {xtype :'displayfield' , value: 'Reminder: Non-public information found in or created on this system is the proprietary, confidential and/or trade secret information of Boeing or others entrusting their information to Boeing. In addition, information transmitted to a foreign person through the use of this system may be subject to U.S. Export Control laws. If appropriate, contact your Export Coordinator the Law Department for assistance.'}
//				       ,{xtype :'displayfield' , value: ''},
//				       {xtype :'displayfield' , value: ''}
//		        ]
//		    }
//		
//			//,
//			      //{ xtype : 'bmpanel2'}
//			
//			],
			listeners:{
				afterrender: function(panel){
					panel.setTitle('<div style="font-size:medium;">'+'Information Control Notice'+'</div>');				
				}
			}
	    });
	    Boeing.InfoPanel.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('infopanel', Boeing.InfoPanel);


Boeing.loginFormPanel = Ext.extend(Ext.form.FormPanel, {
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
					    		       		    { xtype :'textfield', 
					    		        			fieldLabel:'Username', // we can create a component  
					    		                    name:'username', // with a configuration  
					    		                   // value:'username', //object  
					    		                    id:"username"  
					    		        			}, 
					    		        			{ xtype :'textfield', 
					    		            			fieldLabel:'Password', // we can create a component  
					    		                        name:'password', // with a configuration  
					    		                    //    value:'password', //object  
					    		                        id:"password"  
					    		            			},  {xtype: 'container', height: '20'},   {xtype: 'tbbutton', text: 'Login User', 
					    		            				listeners:{
					    		    	           				click:function(item, event){
					    		    	               	  		//	alert ('Login User');
					    		    	               	  			loginUser();
					    		    							}
					    		    	                 	}
					    		            			}
											 ]}, 
							    	       {xtype: 'fieldset', vertical: true,
					    		       		items:[
												   
										    	    ]}]}
		    		       	]}
			//,
			      //{ xtype : 'bmpanel2'}
			
			],
//    		items:[ {xtype: 'container', height: '30'},
//    		       { xtype :'textfield', 
//    			fieldLabel:'Username', // we can create a component  
//                name:'username', // with a configuration  
//               // value:'username', //object  
//                id:"username"  
//    			}, 
//    			{ xtype :'textfield', 
//        			fieldLabel:'Password', // we can create a component  
//                    name:'password', // with a configuration  
//                //    value:'password', //object  
//                    id:"password"  
//        			},  {xtype: 'container', height: '30'},   {xtype: 'tbbutton', text: 'Login User', 
//        				listeners:{
//	           				click:function(item, event){
//	               	  		//	alert ('Login User');
//	               	  			loginUser();
//							}
//	                 	}
//        			}
//        			
// 		       ] ,
 		      listeners:{
 					afterrender: function(panel){
 						panel.setTitle('<div style="font-size:medium;">'+'Login User'+'</div>');				
 					}
 				} 
    		});
    Boeing.loginFormPanel.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.buyerCheckListFormPanel
Ext.reg('loginFormPanel', Boeing.loginFormPanel);


function loginUser () {
	
	
//alert ( " logging user ");
	var form = Ext.getCmp('loginUserForm').getForm();	
	
//	if(form.isValid()){
		
		form.submit({
							url : "/TestSpring/view/home/loginUser",
							method: 'POST',
							timeout: 240000,
							
							success: function(form, action){
						
								//alert ("submitted successfully ");
								var data23 = action.result;
								//alert ("success" + data23.success);
								var success = data23.success;
								if ( success == 'false' ) {
									var msg = data23.msg ;
									displayErrorMsg(msg , 'errorMessageDiv');
								}else {
								//alert ("msg " + data23.msg);
									document.location.href = '/TestSpring/view/search.jsp';
								}
							
							},
							failure: function ( result, request) {
								//	alert("eception happened");
									var data = Ext.decode(result.responseText);
									//alert(data.msg);
									//alert(data.data.msg);
									//displayErrorMsgEpicService(data, data.data);			
								}
						});
		
		

//	}
	//else {
	//	Ext.MessageBox.alert("", "All required fields must be filled out and errors fixed on the Buyer CheckList Form. See red highlighted fields for details.");
	//}
	
	
	
	
	
	
}
