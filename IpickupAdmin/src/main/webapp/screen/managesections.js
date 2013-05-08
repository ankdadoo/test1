Ext.ns('Boeing');
Ext.onReady(function(){
	new Boeing.Header({renderTo: 'headerDiv'});	
	new Boeing.ManageSectionsPanel({renderTo: 'mainDiv', title: 'Manage Sections Screen'});//, items:[{xtype: "navbar"}]});
	new Boeing.Footer({renderTo: 'footerDiv'});
});