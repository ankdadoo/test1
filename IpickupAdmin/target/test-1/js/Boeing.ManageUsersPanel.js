// *********************************************************************************
// NOTE: Toolbar for subpanel: Search, Add, Create 
// *********************************************************************************
Ext.ns('Boeing');
Boeing.ManageUsersNavBar = Ext.extend(Ext.Toolbar, { 
    initComponent:function() {
        Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Search' , listeners:{
			    		click:function(item, event){
			    			getUserList();
			    			}
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Add' , listeners:{
			    		click:function(item, event){
			    			addUser();
			    			}
			    		}
			    	}
			]
        });
        Boeing.ManageUsersNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('manageusersNavbar', Boeing.ManageUsersNavBar);

function editUser() { 
	
	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv');
	var grid = Ext.getCmp('usersList');
	var record = grid.getSelectionModel().getSelected();
//	var rowIndex = grid.getStore().indexOf(record);
    
	manageuser_win_submit_url = "/TestSpring/view/manageusers/updateUser";
	manageuser_win_activity = "Edit";	
	
	// check if user was selected before launching editor window
	if(record) {
	    showUserWindow();
	    Ext.getCmp('manageusersWindow').setTitle('<center>Edit User</center>');
	    manageuser_win_title = "Edit User";
	    Ext.getCmp('userid2').setValue(record.get('id'));
	    Ext.getCmp('username2').setValue(record.get('username'));
	    Ext.getCmp('firstname2').setValue(record.get('firstName'));
	    Ext.getCmp('lastname2').setValue(record.get('lastName'));
	    Ext.getCmp('password2').setValue(record.get('password'));
	    Ext.getCmp('userroleTypeDD').setValue(record.get('role.id')); 
//	    Ext.getCmp('activestatusTypeDD').setValue(record.get('active'));
}
    
}

function addUser() { 

	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv');
	manageuser_win_submit_url = "/TestSpring/view/manageusers/addUser";
	manageuser_win_activity = "Add";	
	
    showUserWindow();
    Ext.getCmp('manageusersWindow').setTitle('<center>Create User</center>');
    manageuser_win_title = "Create User";
    Ext.getCmp('userid2').setValue('');
    Ext.getCmp('username2').setValue('');
    Ext.getCmp('firstname2').setValue('');
    Ext.getCmp('lastname2').setValue('');
    Ext.getCmp('password2').setValue(''); 
    Ext.getCmp('userroleTypeDD').setValue('');
//    Ext.getCmp('activestatusTypeDD').hide();
}

function deleteUser() { 
	
	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv');
	var grid = Ext.getCmp('usersList');
	var record = grid.getSelectionModel().getSelected();
//	var rowIndex = grid.getStore().indexOf(record);
	
	manageuser_win_submit_url = "/TestSpring/view/manageusers/deleteUser";
	manageuser_win_activity = "Delete";	
	
	if(record) {
	    showUserWindow();
	    Ext.getCmp('manageusersWindow').setTitle('<center>Delete User</center>');
	    manageuser_win_title = "Delete User";
	    Ext.getCmp('userid2').setValue(record.get('id'));
	    Ext.getCmp('username2').setValue(record.get('username'));
	    Ext.getCmp('firstname2').setValue(record.get('firstName'));
	    Ext.getCmp('lastname2').setValue(record.get('lastName'));
	    Ext.getCmp('password2').setValue(record.get('password')); 
	    Ext.getCmp('userroleTypeDD').setValue(record.get('role.description'));
//	    Ext.getCmp('activestatusTypeDD').setValue(record.get('active'));
	}
}


Ext.ns('Boeing');
Boeing.UserListNavBar = Ext.extend(Ext.Toolbar, { 
    initComponent:function() {
        Ext.apply(this, {
	        height: 25,
//	        width: 900,
			items:[{xtype: 'tbfill'},{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Edit' , listeners:{
			    		click:function(item, event){
			    			editUser();
			    			}
			    		}
			    	}
			    	,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Delete' , listeners:{
			    		click:function(item, event){
			    			deleteUser();
			    			}
			    		}
			    	}
			]
        });
        Boeing.ManageUsersNavBar.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('userlistNavBar', Boeing.UserListNavBar);


// *********************************************************************************
// NOTE: main panel for the manage users which the other items/elements are added to
//*********************************************************************************
Ext.ns('Boeing');
Boeing.ManageUsersPanel = Ext.extend(Ext.Panel, {
	extend : 'Ext.panel.Panel',
	alias : 'widget.userpanel',
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
			},
			items : [ {
				xtype : "navbar"
			}
		    , {xtype :'container' , height:'30' , id : 'errorMessageDiv' }
//			, {xtype : 'container', height : '30'}
			, {xtype : 'manageusersFormPanel', id: 'manageusersForm'}
			, {xtype : 'container', height : '30'}
			,{ xtype:'usersList', id:'usersList'}
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
Ext.reg('manageusersPanel', Boeing.ManageUsersPanel);

//*********************************************************************************
// NOTE: form panel for user input of user name, first name and last name
//*********************************************************************************
Boeing.manageusersFormPanel = Ext.extend(Ext.form.FormPanel, {
	initComponent : function() {
		Ext.apply(this, {
			width : '400px',
			autoHeight : true,
			trackResetOnLoad : true,
			method : 'POST',
			id : 'manageusersForm',
			tbar: {xtype: 'manageusersNavbar'   },
			defaults : {
				// implicitly create Container by specifying xtype
//				xtype : 'container',
//				autoEl : 'div', // This is the default.
//				layout : 'form',
//				waitTitle : '',
//				standardSubmit : true,
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
	    	                    	height : '30'
	    	                    }, {
	    	                    	xtype : 'textfield',
	    	                    	maxLength: 40,
	    	                    	fieldLabel : 'Username', // we can create a component
	    	                    	name : 'username', // with a configuration
	    	                    	value : '', // object
	    	                    	id : "username",
	    	                    }, {
	    	                    	xtype : 'textfield',
	    	                    	maxLength: 40,
	    	                    	fieldLabel : 'First Name', // we can create a component
	    	                    	name : 'firstname', // with a configuration
	    	                    	value : '', // object
	    	                    	id : "firstname"
	    	                    }, {
	    	                    	xtype : 'textfield',
	    	                    	maxLength: 40,
	    	                    	fieldLabel : 'Last Name', // we can create a component
	    	                    	name : 'lastname', // with a configuration
	    	                    	value : '', // object
	    	                    	id : "lastname"
	    	                    }, {
	    	                    	xtype : 'container',
	    	                    	height : '30'
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
		Boeing.manageusersFormPanel.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
}); // eo Epic.buyerCheckListFormPanel
Ext.reg('manageusersFormPanel', Boeing.manageusersFormPanel);

//*********************************************************************************
// NOTE: this Editor Grid Panel shows the reults from a search 
//*********************************************************************************
// RJM - START
Boeing.UsersList = Ext.extend(Boeing.EditorGridPanel, {
	listeners:{
				
	},
    initComponent:function() {
    Ext.apply(this, {    	
    		
    		width: 900, // 900,   
    		height: 400, //600,
    		collapsible: false,
    		titleCollapse: false,
    		headerAsText: false,
    		clicksToEdit: 1,
    		stripeRows : true,
    		store : usersStore,
			tbar: {xtype: 'userlistNavBar'   },
    		
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
			
				dataIndex : 'username',
				align : 'left',
				header : 'User Name',
				sortable : true,
				width : 200,
				editable : false,
				hideable : false,
			},{
				dataIndex : 'firstName',
				align : 'left',
				header : 'First Name',
				sortable : true,
				width : 200,
				editable : false,
				hideable : false
				
			},{
				dataIndex : 'lastName',
				align : 'left',
				header : 'Last Name',
				sortable : true,
				width : 200,
				editable : false,
				hideable : false
			},{
				dataIndex : 'role.description', 
				align : 'left',
				header : 'User Role',
				sortable : true,
				width : 100,
				editable : false,
				hideable : false
			},{
				dataIndex : 'role.id', 
				header : 'User Role ID',
				sortable : true,
				width : 50,
				editable : false,
				hideable : true,
				hidden: true
//			},{
//				dataIndex : 'createdDate',
//				header : 'Created Date',
//				sortable : true,
//				width : 100,
//				editable : false,
//				hideable : false
//				
//			},{
//				dataIndex : 'modifiedDate',
//				header : 'Modified Date',
//				sortable : true,
//				width : 100,
//				editable : false,
//				hideable : false
//			},
//			{
//				dataIndex : 'active',
//				header : 'Active',
//				sortable : true,
//				width : 80,
//				editable : false,
//				hideable : false
			}
			]    		    			
    	});
    Boeing.UsersList.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.SourceSelectionPanel
Ext.reg('usersList', Boeing.UsersList);

var usersStore = new Ext.data.JsonStore( {
//	url : "/TestSpring/view/manageusers/getAllUsers",
	url : "/TestSpring/view/manageusers/getUsers",
	
	root : 'data',
	timeout: 240000,
	autoLoad : true,
//	baseParams: {  },
	baseParams: { username: '', firstname: '', lastname: '' }, 

	fields : [ {
		name : 'username'
		
	}, {
		name : 'firstName'
		
	}, {
		name : 'id'
		
	}, {
		name : 'lastName' 
		
//	}, {
//		name : 'createdDate'
//		
//	},{
//		name : 'modifiedDate'
//		
	}, {
		name: 'role.description'
	}, {
		name: 'role.id'
	}, {
		name: 'active', type: 'int'
	}, {
		name: 'password'
	} 
	]		
});

//*********************************************************************************
//NOTE: window panel for adding / editing / deleting a user
//*********************************************************************************
Boeing.manageusersWindow = Ext.extend(Ext.Window, {
	      
	      title : '<center>User Management</center>', 
	      titleAlign: "center",
	      id:'manageusersWindow',
	      width : 450,
	      height : 300,

//	      center: function() {
//	    	  var xy = this.el.getAlignToXY(this.container, 'c-c');
//	    	  this.setPagePosition(xy);
//	    	  return this;
//	      }, 
//	      listeners:{
//				afterrender: function(){
////					panel.setTitle('<div style="font-size:medium;"><center>' + manageuser_win_title + '</center></div>');				
//					this.setTitle('<div style="font-size:medium;"><center>TESTING</center></div>');				
//				}
//			},   

	      initComponent : function() {
	    	  
	    	  this.items = [ {xtype: 'fieldset', vertical: true,
	    		  layout: 'column',
//	    		  bodyStyle: 'padding-right:5px; padding-left:15px;',
	    		  border: false,
	    		  // defaults are applied to all child items unless otherwise specified by child item
	    		  defaults: {
	    			  columnWidth: 0.5,
	    			  border: false
	    		  }
	    	  ,
	    		  
	    		  items:[{xtype:'edituserFormPanel', id: 'edituserForm'}]
	    		  }
	            ]            
	            
	            ;

	    	  Boeing.manageusersWindow.superclass.initComponent.call(this);
	      }
	});
Ext.reg('manageusersWindow', Boeing.manageusersWindow);

//*********************************************************************************
//NOTE: function for showing the window panel for adding / editing / deleting a user
//*********************************************************************************

// Global manageusersWindow variables:
var manageuser_win_submit_url = "/TestSpring/view/manageusers/addUser";
var manageuser_win_activity = "";
var manageuser_win_title = "";

function showUserWindow()
{
	var win = new Boeing.manageusersWindow({});
	win.show();
}

//*********************************************************************************
//NOTE: edit window form panel for user input of user details
//*********************************************************************************
Boeing.edituserFormPanel = Ext.extend(Ext.form.FormPanel, {
//	headerCfg:{
//    	tag: 'div',
//    	cls: 'x-panel-header epic-page-panel-header-text'
//	},	
	initComponent : function() {
		Ext.apply(this, {
//			width : 400,
			autoWidth: true,
			autoHeight : true,
			trackResetOnLoad : true,
			method : 'POST',
			id : 'edituserForm',
			tbar: {xtype: 'editUserNavBar'   },
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
				xtype : 'container', height : '30' , id : 'errorMessageDiv2' }
			, {
				xtype : 'hidden',
				name : 'screenActivity2', // with a configuration
				value : manageuser_win_activity, // '', // object 
				id : "screenActivity2"
			}, {
				xtype : 'hidden',
				name : 'userid2', // with a configuration
				value : '', // object 
				id : "userid2"
			}, {
				xtype : 'textfield',
                allowBlank: false,
				fieldLabel : 'Username', // we can create a component
				name : 'username2', // with a configuration
				value : '', // object 
				id : "username2"
			}, {
				xtype : 'textfield', 
                allowBlank: false,
				fieldLabel : 'First Name', // we can create a component
				name : 'firstname2', // with a configuration
				value : '', // object
				id : "firstname2"
			}, {
				xtype : 'textfield',
                allowBlank: false,
				fieldLabel : 'Last Name', // we can create a component
				name : 'lastname2', // with a configuration
				value : '', // object
				id : "lastname2"
			}, {
				xtype : 'textfield',
//                allowBlank: false, // NOTE: this field only becomes required if user role is "Admin" or "SuperUser" 
				fieldLabel : 'Password', // we can create a component
				name : 'password2', // with a configuration
				value : '', // object
				id : 'password2'
			},	{ xtype: 'combo',
				    setValue : function(v) {
				    	if (this.store.getCount() == 0) {
				    		if (this.store.getCount() == 0) {
				    			this.store.on('load',
				    	                this.setValue.createDelegate(this, [v]), null, {single: true});
				    	        return;
				    	    }
				    	}
				        var text = v;
				        if(this.valueField){
				            var r = this.findRecord(this.valueField, v);
				            if(r){
				                text = r.data[this.displayField];
				            }else if(this.valueNotFoundText !== undefined){
				                text = this.valueNotFoundText;
				            }
				        }
				        this.lastSelectionText = text;
				        if(this.hiddenField){
				            this.hiddenField.value = v;
				        }
				        Ext.form.ComboBox.superclass.setValue.call(this, text);
				        this.value = v;		    	
				    		
				    },
                    fieldLabel: 'User Role:',
                    labelSeparator: '',
                    width: 220,
                    allowBlank: false,
                    loadingText: "Loading...",
//                    labelStyle: 'width:170px;font-weight: bold;color: #000000;',
//                    height: 30,
//                    onHide: function(){this.getEl().up('.x-form-item').setDisplayed(false);},
//                    onShow: function(){this.getEl().up('.x-form-item').setDisplayed(true);},
                    emptyText: 'Select One',
                          id: 'userroleTypeDD',
                          name: 'userroleTypeDD', 
//                          listWidth : 295,
                          editable : false,
                          triggerAction : 'all',
                          displayField:'description',
                          valueField: 'id', //'description',
                          hiddenName : "idTypeHidden", //'contractTypeHidden',
                          emptyText : 'Select One',
//                          submitValue : true,
                          mode: 'local',    
                          value : '',
                          store: new Ext.data.JsonStore({ 
                         	  url : "/TestSpring/view/manageusers/getUserRoles",
                        	  root: 'data',
                        	  timeout: 240000,
                        	  autoLoad: true, 
                        	  baseParams: {  },
                        	  forceselection: false,

                        	  fields: ['id', 'description'],
                            })               			
            }, 
			{
				xtype : 'container',
				height : '30'
			}

			],
			listeners : {
				afterrender : function(panel) {
//					panel.setTitle('<div style="font-size:medium;">'
//							+ 'Manage Users' + '</div>');
					Ext.getCmp('username2').setReadOnly(false);
					Ext.getCmp('firstname2').setReadOnly(false);
					Ext.getCmp('lastname2').setReadOnly(false);
					Ext.getCmp('password2').setReadOnly(false);
					
					if (Ext.getCmp('screenActivity2').getValue() == "Delete")
					{
						Ext.getCmp('username2').setReadOnly(true);
						Ext.getCmp('firstname2').setReadOnly(true);
						Ext.getCmp('lastname2').setReadOnly(true);
						Ext.getCmp('password2').setReadOnly(true);
						Ext.getCmp('userroleTypeDD').setReadOnly(true);
					}
				}
			}
		});
		Boeing.edituserFormPanel.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
}); // eo Epic.buyerCheckListFormPanel
Ext.reg('edituserFormPanel', Boeing.edituserFormPanel);

//*********************************************************************************
//NOTE: Toolbar for subpanel: Save, Cancel
//*********************************************************************************
Ext.ns('Boeing');
Boeing.EditUserNavBar = Ext.extend(Ext.Toolbar, { 
 initComponent:function() {
     Ext.apply(this, {
	        height: 30,
			items:[{xtype: 'tbfill'}
					,{xtype: 'tbseparator'}
			    	,{xtype: 'tbbutton', text: 'Submit' , listeners:{
			    		click:function(item, event){
			    			submitUserChange();
			    			}
			    		}
			    	}
			]
     });
     Boeing.EditUserNavBar.superclass.initComponent.apply(this, arguments);
 }
});
Ext.reg('editUserNavBar', Boeing.EditUserNavBar);

//*********************************************************************************
//NOTE: function for submitting the form and retrieving users (NO database coming back)
//*********************************************************************************
function submitUserChange(changeType) {

	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv2');
	var form = Ext.getCmp('edituserForm').getForm();
	var validForm = validateUserChanges();
	if (validForm)  {

		form.submit({
		url : manageuser_win_submit_url, 
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
				Ext.getCmp('manageusersWindow').close();

				// clear values from Search panel and show all users for listing
				Ext.getCmp('username').setValue('');
				Ext.getCmp('firstname').setValue('');
				Ext.getCmp('lastname').setValue('');
				usersStore.load({params: { username: '', firstname: '', lastname: ''} });

//				document.location.href = '/TestSpring/view/manageusers.jsp';
		     }
		},
		failure : function(result, request) {
//			alert("function: exception happened while adding user");
			var data = Ext.decode(result.responseText);
//			alert("function: could not find matching user(s) ");
			// displayErrorMsgEpicService(data, data.data);
		}
	});
   }
}

function validateUserChanges() {

	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv');
	var MAX_TEXT_LENGTH = 40;
	var username = Ext.getCmp('username2').getValue();
	var usernameLength = username.length;

	var firstname = Ext.getCmp('firstname2').getValue();
	var firstnameLength = firstname.length;

	var lastname = Ext.getCmp('lastname2').getValue();
	var lastnameLength = lastname.length;

	var password = Ext.getCmp('password2').getValue();
	var passwordLength = password.length;

    var userrole = Ext.getCmp('userroleTypeDD').getRawValue();

    // NOTE: DO NOT NEED STEP#1 CHECK BY CHANGING THE FIELD ATTRIBUTE TO BEING REQUIRED
//	// Step #1: validate all required fields have entries:
//	if (username==null || username=="" || firstname==null || firstname=="" ||
//			lastname==null || lastname=="" || userrole == null || userrole == "") { 
//		//} || password==null || password=="") {
//		displayErrorMsg("Not All of the Required Fields Have Been Entered" , 'errorMessageDiv2');
//	    return false;
//	} 
//	// Step #2: if User Role type = "Admin" or "SuperUser", the password must have a value
//	else 
		if ((userrole == "Admin" || userrole == "SuperUser") && (password==null || password=="")) {
				displayErrorMsg("This role type requires a Password" , 'errorMessageDiv2');
			    return false;
	} 
	// Step #3: verify none of the text fields have exceeded the max length (currently all have the same lenght)
	else if (usernameLength > MAX_TEXT_LENGTH || firstnameLength > MAX_TEXT_LENGTH || 
					lastnameLength > MAX_TEXT_LENGTH || passwordLength > MAX_TEXT_LENGTH ) {
				displayErrorMsg("One of the Entries has Exceed the Max Length Allowed" , 'errorMessageDiv2');
			    return false;
	} 
	return true;
}

function getUserList() {

	// clears the error message from the panel
	displayErrorMsg('' , 'errorMessageDiv');
	var usernameParam= Ext.getCmp('username').getValue();
	var firstnameParam= Ext.getCmp('firstname').getValue();
	var lastnameParam= Ext.getCmp('lastname').getValue();
	usersStore.load({params: { username: usernameParam, firstname: firstnameParam, lastname: lastnameParam } });
	
}

// end-of-Boeing.MangeUsersPanel

