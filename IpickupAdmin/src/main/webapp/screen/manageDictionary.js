Ext.ns('Boeing');
Ext.onReady(function(){
	new Boeing.Header({renderTo: 'headerDiv'});	
	new Boeing.ManageDictionaryPanel({renderTo: 'mainDiv', title: 'Phantom Works Manage Dictionary'});//, items:[{xtype: "navbar"}]});
	new Boeing.Footer({renderTo: 'footerDiv'});
});