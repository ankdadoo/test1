Ext.ns('Boeing');
Boeing.Panel = Ext.extend(Ext.Panel, {
	headerCfg:{
    	tag: 'div',
    	cls: 'x-panel-header x-panel-header-text'
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
			items:[]
    	});
	Boeing.Panel.superclass.initComponent.apply(this, arguments);
	} 
});
Ext.reg('bpanel', Boeing.Panel);