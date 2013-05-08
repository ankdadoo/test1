Ext.ns('Boeing');
Boeing.Header = Ext.extend(Ext.Container, {
	initComponent:function() {
    	Ext.apply(this, {
		width: panelWidth,
		defaults: {
	    	style: {
	        	padding: '0px'
	    	}
		},    		
		items:[{xtype:"container", cls: "icon-boeing-banner"},
		       {xtype: "blackbar"}
			  ]
    	});
    	Boeing.Header.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
});  // eo Epic.Header
Ext.reg('bheader', Boeing.Header);