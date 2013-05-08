Ext.ns('Boeing');
Boeing.NavBar = Ext.extend(Ext.Toolbar, {
	initComponent : function() {
		Ext.apply(this, {
			height : 30,
			items : [ {
				xtype : 'tbbutton',
				text : 'Search',
				listeners : {
					click : function(item, event) {
						
						document.location.href = '/TestSpring/view/search.jsp';
					}
				}

			}, {
				xtype : 'tbseparator'
			}, {
				xtype : 'tbbutton',
				text : 'Create SSOW',
				listeners : {
					click : function(item, event) {
					
						document.location.href = '/TestSpring/view/ssow.jsp?ssowId=NULL&action=create';
					}
				}
			}, {
				xtype : 'tbseparator'
			}, {
				xtype : 'tbbutton',
				text : 'SSOW Queue',
				id : 'manageQueue',
				menu : {
					xtype : 'tbmenu',
					items : [ {
						text : 'User Queue',
						xtype : 'tbmenuitem',
						id : 'personalQ',
						listeners : {
							click : function(item, event) {
								document.location.href = '/TestSpring/view/ssowQueue.jsp?queueType=User';
							}
						}
					}, {
						text : 'All Queue',
						xtype : 'tbmenuitem',
						id : 'allQ',

						listeners : {
							click : function(item, event) {
								document.location.href = '/TestSpring/view/ssowQueue.jsp?queueType=ALL';
							}
						}
					} ]
				}
			}
			

			
			, {
				xtype : 'tbseparator' , 
				hidden: (role == 'User')
			}, {
				xtype : 'tbbutton',
				hidden: (role == 'User'),
				text : 'Manage SSOW ',
				id : 'PrListNavBarActions',
				menu : {
					xtype : 'tbmenu',
					items : [ {
//						text : 'Manage Templates', (Renamed - RJM 12/24/12) 
						text : 'Manage Sections',
						xtype : 'tbmenuitem',
						id : 'manageT',
						listeners : {
							click : function(item, event) {
								
								document.location.href = '/TestSpring/view/managesections.jsp';

							}
						}
					}, {
						text : 'Manage CheckList',
						xtype : 'tbmenuitem',
						id : 'manageC',

						listeners : {
							click : function(item, event) {
							
							
								document.location.href = '/TestSpring/view/managechecklist.jsp';
							}
						}
					}, {
						text : 'Manage Rules',
						xtype : 'tbmenuitem',
						id : 'manageR',
						listeners : {
							click : function(item, event) {
							
								document.location.href = '/TestSpring/view/manageRules.jsp';
							}
						}
					} ]
				}
			}, {
				xtype : 'tbseparator',
				hidden: (role == 'User')
			}, {
				xtype : 'tbbutton',
				text : 'Manage Appendix ',
				hidden: (role == 'User'),
				id : 'manageA',
				menu : {
					xtype : 'tbmenu',
					items : [ {
						text : 'Manage SDRL Matrix',
						xtype : 'tbmenuitem',
						id : 'manageS',
						listeners : {
							click : function(item, event) {
								document.location.href = '/TestSpring/view/manageSdrl.jsp';
							}
						}
					}, {
						text : 'Manage Definitions',
						xtype : 'tbmenuitem',
						id : 'manageD',

						listeners : {
							click : function(item, event) {
								document.location.href = '/TestSpring/view/manageDictionary.jsp';
							}
						}
					}, {
						text : 'Manage Acronyms',
						xtype : 'tbmenuitem',
						id : 'manageAc',
						listeners : {
							click : function(item, event) {
								document.location.href = '/TestSpring/view/manageAcronyms.jsp';
							}
						}
					} ]
				}
			}, {
				xtype : 'tbseparator',
				hidden: (role == 'User' || role == 'SuperUser')
			}, {
				xtype : 'tbbutton',
				text : 'Manage Users',
				hidden: (role == 'User' || role == 'SuperUser'),
				
				listeners:{
	   				click:function(item, event){
//	       	  		
	       	  		document.location.href = '/TestSpring/view/manageusers.jsp';
	       	  		
					}
	         	}				
			}, 
			{
				xtype : 'tbseparator',
				
			},
			{
				xtype : 'tbbutton',
				text : 'Logout',
				
				
				listeners:{
	   				click:function(item, event){
//	       	  		
	       	  		document.location.href = '/TestSpring/view/home/logout.jsp';
	       	  		
					}
	         	}				
			}]
		});
		Boeing.NavBar.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('navbar', Boeing.NavBar);