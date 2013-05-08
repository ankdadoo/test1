Ext.ns('Boeing');
Boeing.ToolbarMenuItem = Ext.extend(Ext.menu.Item, {
	style: {
		textAlign: 'left'
	},
	initComponent:function() {
		Ext.apply(this, {});
		Boeing.ToolbarMenuItem.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('tbmenuitem', Boeing.ToolbarMenuItem);