Ext.ns('Boeing');
Boeing.ToolbarMenu = Ext.extend(Ext.menu.Menu, {
	menuButton: null,
	style: {
		textAlign: 'left'
	},	
	leftMenu: true,
	listeners:{
		mouseover: function(menu, e, menuItem){
			menu.leftMenu = false;
			var items = menu.items.items;
			for(var i=0; i < items.length; i++){
				if(items[i].menu != null){
					items[i].menu.menuParent = menu;
				}
			}
		},
		mouseout: function(menu, e, menuItem){
			menu.leftMenu = true;
			var items = menu.items.items;
			for(var i=0; i < items.length; i++){
				if(items[i].menu != null){
					items[i].menu.menuParent = menu;
				}
			}
			
			var fn = function(menu, menuItem){
				//if mouse is still on button show menu
				if(menu.leftMenu && (menu.menuButton == null || menu.menuButton.leftMenu)){
					var items = menu.items.items;
					var hideMenu = true;
					for(var i=0; i < items.length; i++){
						if(!items[i].hidden && items[i].menu != null && !items[i].menu.leftMenu){
							hideMenu = false;
						}
					}
					
					if(hideMenu){
						menu.hide();
					}
					
					
				}
			};             				
			fn.defer(500, menu, [menu, menuItem]);             					
		}            				
	},
	initComponent:function() {
		Ext.apply(this, {});
		Boeing.ToolbarMenu.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('tbmenu', Boeing.ToolbarMenu);