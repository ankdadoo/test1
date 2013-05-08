Ext.ns('Boeing');
Boeing.ToolbarButton = Ext.extend(Ext.Button, {
	style: {
		textAlign: 'left'
	},	
	leftMenu: true,
	listeners:{
		mouseover : function(btn, e){
			btn.leftMenu = false;
			if(btn.menu != null){
				btn.menu.menuButton = btn;
			}
			
			var fn = function(btn){
				// if mouse is still on button show menu
				if(!btn.leftMenu){
					btn.showMenu();
				}
			};          				
			fn.defer(500, btn, [btn]);            				
		},
		mouseout : function(btn, e){
			btn.leftMenu = true;
			if(btn.menu != null){
				btn.menu.menuButton = btn;
			}
			
			var fn = function(btn){
				// if mouse is still on button show menu
				if(btn.leftMenu && (btn.menu == null || btn.menu.leftMenu)){
					btn.hideMenu();
				}
			};             				
			fn.defer(500, btn, [btn]);            					
		}
	},
	initComponent:function() {
		Ext.apply(this, {});
		Boeing.ToolbarButton.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('tbbutton', Boeing.ToolbarButton);