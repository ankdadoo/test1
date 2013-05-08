Ext.ns('Boeing');
Boeing.EditorGridPanel = Ext.extend(Ext.grid.EditorGridPanel, {
	stripeRows: true,
	headerCfg:{
    	tag: 'div',
    	cls: 'x-panel-header x-panel-header-text'
	},
    initComponent:function() {
    	Ext.apply(this, {    		
    	});
    	Boeing.EditorGridPanel.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.GridPanel
Ext.reg('beditorgridpanel', Boeing.EditorGridPanel);