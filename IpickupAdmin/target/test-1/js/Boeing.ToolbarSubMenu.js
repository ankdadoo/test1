Ext.ns('Boeing');
Boeing.ToolbarSubMenu = Ext.extend(Ext.menu.Menu, {
	parentMenu: null,
	style: {
		textAlign: 'left'
	},	
	leftMenu: true,
	listeners:{
		mouseover: function(menu, e, menuItem){
			menu.leftMenu = false;
		},
		mouseout: function(menu, e, menuItem){
			menu.leftMenu = true;
			var fn = function(menu, menuItem){
				//if mouse is still on button show menu
				if(menu.leftMenu){
					menu.hide();
					if(menu.parentMenu != null && menu.parentMenu.leftMenu){
						menu.parentMenu.hide();
					}
				}
			};             				
			fn.defer(500, menu, [menu, menuItem]);             					
		}            				
	},
	initComponent:function() {
		Ext.apply(this, {});
		Boeing.ToolbarSubMenu.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('tbsubmenu', Boeing.ToolbarSubMenu);